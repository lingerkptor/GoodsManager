package idv.GoodsManager.installation.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import idv.GoodsManager.installation.api.request.UploadDBFilesRequest;
import idv.GoodsManager.installation.api.response.UploadDBFilesResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

@WebServlet("/api/UploadDBFiles")
@MultipartConfig
public class UploadDBFiles extends Service {

	private static final long serialVersionUID = 607009177805954705L;

	/**
	 * 確認檔案是否為zip檔
	 * 
	 * @param 要確認的檔案
	 * @return 布林值，如果是壓縮檔就回傳true，反之則回傳false
	 */
	@SuppressWarnings("resource")
	private boolean checkFileIsZipFile(File file) {
		try {
			new ZipFile(file);
		} catch (ZipException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "SQLZIP不是zip檔，請重新上傳"));
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "資料有錯誤，請重新上傳"));
			return false;
		}
		return true;
	}

	/**
	 * 搬移JDBC 到lib資料夾下
	 * 
	 * @param source old JDBC Path
	 * @return new JDBC Path
	 * @throws IOException
	 */
	private Path moveJDBC(Path source) throws IOException {
		Path activeJDBCPath = Paths.get(ConfigReader.getConfigReader().getWebAddr() + "//lib");
		Path newJDBC = null;
		try {
			newJDBC = Files.move(source, activeJDBCPath.resolve(source.getFileName().toString()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err,
					source.getFileName().toString() + "移動失敗（IOException）."));
			throw e;
		}
		return newJDBC;
	}

	/**
	 * 解壓縮SQLZip檔案
	 * 
	 * @param SQLZIP
	 * @return 布林值，如果是解壓縮成功就回傳true，反之則回傳false
	 */
	private boolean decompressSQLZip(File SQLZIP) {
		ZipInputStream input = null;
		try {
			input = new ZipInputStream(new FileInputStream(SQLZIP));
			File sqlDir = new File(ConfigReader.getConfigReader().getWebAddr() + "//sql");
			ZipEntry entry;
			while ((entry = input.getNextEntry()) != null) {
				if (entry.isDirectory())
					continue;
				String filePath = entry.getName();
				File outFile = new File(sqlDir, filePath);
				if (outFile.exists())
					outFile.delete();
				outFile.getParentFile().mkdirs();
				outFile.createNewFile();
				this.unzip(outFile, input);
			}
		} catch (FileNotFoundException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, SQLZIP.getName() + " File Not Found."));
			e.printStackTrace();
			return false;
		} catch (ZipException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, SQLZIP.getName() + "ZIP file error."));
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, SQLZIP.getName() + " IO error."));
			e.printStackTrace();
			return false;
		} finally {
			try {
				input.close();
				SQLZIP.delete();
			} catch (IOException e) {
				e.printStackTrace();
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err,
						SQLZIP.getName() + " input Stream closing fail."));
				return false;
			}
		}
		return true;
	}

	/**
	 * 解壓縮檔案
	 * 
	 * @param outputfile 要解壓縮出來的地方
	 * @param input      解壓的來源
	 * @throws IOException
	 */
	private void unzip(File outputfile, ZipInputStream input) throws IOException {
		BufferedOutputStream out = null;
		try {
			BufferedInputStream in = new BufferedInputStream(input, 1024);
			out = new BufferedOutputStream(new FileOutputStream(outputfile));
			int content = -1;
			while ((content = in.read()) != -1) {
				out.write(content);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err,
					outputfile.getName() + " decompress failure about." + outputfile.getName()));
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 
	 * @param DBName
	 * @param JDBCName
	 * @return
	 */
	private boolean recordActivedDB(String DBName, String JDBCName) {
		Gson gson = new Gson();
		File activeDBListFile = new File(
				ConfigReader.getConfigReader().getWebAddr() + "//sql//activeDB.json");

		// 讀取紀錄Start
		LinkedList<DB> activeDBList = null;
		DB oldDB = null;
		FileReader reader = null;
		try {
			reader = new FileReader(activeDBListFile);
			activeDBList = gson.fromJson(reader, new TypeToken<LinkedList<DB>>() {
			}.getType());
			for (DB db : activeDBList) {
				if (db.getDBName().equals(DBName))
					oldDB = db;
			}
		} catch (JsonIOException | JsonSyntaxException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "sql/activeDB.json 剖析失敗．"));
			return false;
		} catch (FileNotFoundException e) {
			try {
				activeDBListFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err,
						"建立 sql/activeDB.json 檔案失敗，訊息如下\n" + e.getMessage()));
				return false;
			}
			activeDBList = new LinkedList<DB>();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err,
						" sql/activeDB.json reader.close()失敗，訊息如下\n" + e.getMessage()));
				return false;
			}
		}
		// 讀取紀錄end

		if (oldDB == null)
			// 存放資料庫名稱以及對應的JDBC名稱
			activeDBList.add(new DB(DBName, JDBCName));
		else
			oldDB.setJDBCName(JDBCName); // 設定新的JDBC

		// 寫入紀錄 start
		FileWriter writer = null;
		try {
			writer = new FileWriter(activeDBListFile);
			gson.toJson(activeDBList, writer);
			writer.flush();
		} catch (JsonIOException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "sql/activeDB.json 寫入失敗．"));
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "sql/activeDB.json 寫入失敗．"));
			return false;
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err,
						" sql/activeDB.json writer.close()失敗，訊息如下\n" + e.getMessage()));
				return false;
			}
		}
		return true;
	}

	@Override
	@ContentType(reqType = ContentType.RequestType.MultiPart, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		UploadDBFilesRequest request = (UploadDBFilesRequest) requestObj;

		StringBuilder message = new StringBuilder();
		if (request.getDBName().isEmpty()) {
			message.append(" 資料庫名稱為空白 ");
		}
		if (request.getJDBC() == null) {
			message.append(" 沒有JDBC檔 ");
		}
		if (request.getSQLZIP() == null) {
			message.append(" 沒有SQLzip檔");
		}
		if (!message.toString().isEmpty()) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, message.toString()));
			return UploadDBFilesResponse.uploadFailure();
		}

		/**
		 * STEP1. 確認SQLZIP是否為zip檔
		 */
		if (!checkFileIsZipFile(request.getSQLZIP()))
			return UploadDBFilesResponse.uploadFailure();
		/**
		 * STEP2. 搬移JDBC
		 */
		Path newJDBC = null;
		try {
			newJDBC = moveJDBC(request.getJDBC().toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return UploadDBFilesResponse.uploadFailure();
		}

		/**
		 * STEP3. 解壓縮SQLZIP
		 */
		if (!decompressSQLZip(request.getSQLZIP())) {
			return UploadDBFilesResponse.uploadFailure();
		}

		/**
		 * STEP4. 紀錄已被啟用的資料庫資訊 <br/>
		 * 存放於 /WEB-INF/sql/activeDB.json
		 */
		if (!recordActivedDB(request.getDBName(), newJDBC.getFileName().toString()))
			return UploadDBFilesResponse.uploadFailure();

		/**
		 * 上傳成功
		 */
		return UploadDBFilesResponse.uploadSusscess(request.getDBName());
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = UploadDBFilesRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
