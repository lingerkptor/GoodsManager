package idv.GoodsManager.installation.api;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import idv.GoodsManager.installation.api.request.ActiveDBRequest;
import idv.GoodsManager.installation.api.responce.ActiveDBResponce;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

@WebServlet("/api/ActiveDB")
@SuppressWarnings("serial")
public class ActiveDB extends Service {
	private File JDBC, SQLZIP;
	private Responce resp = null;

	/**
	 * STEP1. 確認檔案是否存在
	 */
	private void confirmFile() {
		int i = 0;
		if (JDBC.exists()) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "JDBC isn't exitst."));
			i++;
		}
		if (SQLZIP.exists()) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "SQLZIP isn't exitst."));
			i = i + 2;
		}
		switch (i) {
		case 0:// 都有找到
			break;
		case 3: // JDBC與SQLZIP都沒找到
			resp = ActiveDBResponce
					.activeFailure(new String[] { JDBC.getName(), SQLZIP.getName() });
			break;
		case 1:// 只找到SQLZIP
			resp = ActiveDBResponce.activeFailure(JDBC.getName());
			break;
		case 2:// 只找到JDBC
			resp = ActiveDBResponce.activeFailure(SQLZIP.getName());
			break;
		}
	}

	/**
	 * STEP2. 搬移JDBC
	 */
	private void moveJDBC() {
		Path activeJDBCPath = Paths.get(ConfigReader.getWebAddr() + "//lib");
		try {
			Files.move(JDBC.toPath(), activeJDBCPath.resolve(JDBC.getName()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, JDBC.getName() + " IO error."));
			System.err.println(e.getMessage());
			resp = ActiveDBResponce.activeFailure(JDBC.getName());
		}
	}

	/**
	 * STEP3. 解壓縮SQLZIP
	 */
	private void decompress() {
		try {
			ZipInputStream input = new ZipInputStream(new FileInputStream(SQLZIP));
			File sqlDir = new File(ConfigReader.getWebAddr() + "//sql");
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
			System.err.println(e.getMessage());
			resp = ActiveDBResponce.activeFailure(SQLZIP.getName());
		} catch (ZipException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, SQLZIP.getName() + "ZIP file error."));
			System.err.println(e.getMessage());
			resp = ActiveDBResponce.activeFailure(SQLZIP.getName());
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, SQLZIP.getName() + " IO error."));
			System.err.println(e.getMessage());
			resp = ActiveDBResponce.activeFailure(SQLZIP.getName());
		}
	}

	/**
	 * 解壓縮檔案
	 * 
	 * @param outputfile 要解壓縮出來的地方
	 * @param input      解壓的來源
	 * @throws IOException
	 */
	private void unzip(File outputfile, ZipInputStream input) throws IOException {
		try {
			BufferedInputStream in = new BufferedInputStream(input, 1024);
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputfile));
			int content = -1;
			while ((content = in.read()) != -1) {
				out.write(content);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err,
					outputfile.getName() + " decompress failure about." + this.SQLZIP.getName()));
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 紀錄已被啟用的資料庫資訊 <br/>
	 * 存放於 /WEB-INF/sql/activeDB.json
	 */
	private void recordActivedDB(String dbName) {
		Gson gson = new Gson();
		File activeDBListFile = new File(ConfigReader.getWebAddr() + "//sql//activeDB.json");

		// 讀取紀錄Start
		LinkedList<DB> activeDBList = null;
		try {
			activeDBList = gson.fromJson(new FileReader(activeDBListFile),
					new TypeToken<LinkedList<DB>>() {
					}.getType());
			for (DB db : activeDBList) {
				if (db.getDBName().equals(dbName))
					db.setJDBCName(JDBC.getName()); // 設定新的JDBC
			}
		} catch (JsonIOException | JsonSyntaxException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "sql/activeDB.json 剖析失敗．"));
			resp = ActiveDBResponce.activeFailure("sql/activeDB.json");
		} catch (FileNotFoundException e) {
			try {
				activeDBListFile.createNewFile();
			} catch (IOException e1) {
				System.err.println(e1.getMessage());
			}
			activeDBList = new LinkedList<DB>();
			// 存放資料庫名稱以及對應的JDBC名稱
			activeDBList.add(new DB(dbName, JDBC.getName()));
		}
		// 讀取紀錄end

		// 寫入紀錄 start
		try {
			FileWriter writer = new FileWriter(activeDBListFile);
			gson.toJson(activeDBList, writer);
			writer.flush();
			writer.close();
		} catch (JsonIOException e) {
			System.err.println("寫入記錄失敗，");
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "sql/activeDB.json 寫入失敗．"));
			resp = ActiveDBResponce.activeFailure("sql/activeDB.json");
		} catch (IOException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "sql/activeDB.json 寫入失敗．"));
			resp = ActiveDBResponce.activeFailure("sql/activeDB.json");
		}
		// 寫入紀錄 end
	}

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponceType.Json)
	public Responce process(Request requestObj) {
		resp = null;// 清空回應物件(怕之前有用到)
		ActiveDBRequest request = (ActiveDBRequest) requestObj;
		JDBC = request.getJDBC();
		SQLZIP = request.getSQLZIP();
		String DBName = SQLZIP.getName().split("\\.")[0];

		// STEP1. 確認檔案是否存在
		this.confirmFile();
		if (resp != null)
			return resp;

		// STEP2. 搬移JDBC
		this.moveJDBC();
		if (resp != null)
			return resp;

		// STEP3. 解壓縮SQL檔
		this.decompress();
		if (resp != null)
			return resp;

		// STEP4. 紀錄已安裝的DB以及對應的JDBC，存放於 /WEB-INF/sql/activeDB.json
		this.recordActivedDB(DBName);
		if (resp != null)
			return resp;

		// STEP5. 以上都做完沒問題就是完成了
		MessageInit.getMsgManager().deliverMessage(
				new Message(Message.Category.info, request.getDBName() + " is actived."));
		JDBC.delete(); // 刪除temp內的JDBC檔
		SQLZIP.delete(); // 刪除temp內的SQLZIP檔
		return ActiveDBResponce.activeSucess(request.getDBName());
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = ActiveDBRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
