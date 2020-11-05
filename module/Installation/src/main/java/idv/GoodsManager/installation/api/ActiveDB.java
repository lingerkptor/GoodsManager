package idv.GoodsManager.installation.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponceType.Json)
	public Responce process(Request requestObj) {
		ActiveDBRequest request = (ActiveDBRequest) requestObj;
		JDBC = request.getJDBC();
		SQLZIP = request.getSQLZIP();
		// 找不到檔案
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
		case 0:
			break;
		case 3:
			return ActiveDBResponce
					.activeFailure(new String[] { JDBC.getName(), SQLZIP.getName() });
		case 1:
			return ActiveDBResponce.activeFailure(JDBC.getName());
		case 2:
			return ActiveDBResponce.activeFailure(SQLZIP.getName());
		}
		// 找不到檔案end
		// 搬移JDBC
		Path activeJDBCPath = Paths.get(ConfigReader.getWebAddr() + "//lib");
		try {
			Files.move(JDBC.toPath(), activeJDBCPath.resolve(JDBC.getName()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, JDBC.getName() + " IO error."));
			e.printStackTrace();
			return ActiveDBResponce.activeFailure(JDBC.getName());
		}

		// 解壓縮SQL檔
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
			e.printStackTrace();
			return ActiveDBResponce.activeFailure(SQLZIP.getName());
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, SQLZIP.getName() + " IO error."));
			e.printStackTrace();
			return ActiveDBResponce.activeFailure(SQLZIP.getName());
		}
		// 搬移檔案 end

		MessageInit.getMsgManager().deliverMessage(
				new Message(Message.Category.info, request.getDBName() + " is actived."));
		this.JDBC.delete();
		this.SQLZIP.delete();
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
					outputfile.getName() + " decompress failure about." + SQLZIP.getName()));
			e.printStackTrace();
			throw e;
		}
	}
}
