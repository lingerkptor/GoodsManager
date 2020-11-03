package idv.GoodsManager.installation.api.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class UploadDBFilesRequest implements Request {
	private File JDBC = null;
	private File SQLZIP = null;
	private String DBName = "";
	/**
	 * 暫存位址
	 */
	private final String tempDirPath = ConfigReader.getWebAddr() + "//temp";

	@Override
	public void setAttribute(HttpSession session) {

	}

	@Override
	public void setAttribute(String attrName, Object obj) {
		File tempDir = new File(tempDirPath);
		if (!tempDir.exists())
			tempDir.mkdirs();
		List<File> tempFiles = Arrays.<File>asList(tempDir.listFiles());
		Part part;
		switch (attrName) {
		case "DBName":
			this.DBName = (String) obj;
			break;
		case "JDBC":
			part = (Part) obj;
			String JDBCName = part.getName();
			// 刪除之前存的檔案
			for (File file : tempFiles) {
				if (file.getName().equals(JDBCName)) {
					file.delete();
				}
			}
			try {
				this.JDBC = this.saveFile(JDBCName, part.getInputStream());
			} catch (IOException e) {
				MessageInit.getMsgManager()
						.deliverMessage(new Message(Message.Category.err, "連線出錯"));
				e.printStackTrace();
				return;
			}
			break;
		case "SQLZIP":
			part = (Part) obj;
			String SQLZIPName = part.getName();
			// 刪除之前存的檔案
			for (File file : tempFiles) {
				if (file.getName().equals(SQLZIPName)) {
					file.delete();
				}
			}
			try {
				File file = this.saveFile(SQLZIPName, part.getInputStream());
				if (file.exists())
					this.SQLZIP = file;
			} catch (IOException e) {
				MessageInit.getMsgManager()
						.deliverMessage(new Message(Message.Category.err, "連線出錯"));
				e.printStackTrace();
				return;
			}
			break;
		}
	}

	private File saveFile(String name, InputStream input) {
		String filePath = this.tempDirPath + name;
		File file = new File(filePath);
		try {
			// 輸入流
			BufferedInputStream in = new BufferedInputStream(input, 1024);
			// 輸出流
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file), 1024);
			// 存檔
			int buffContent = -1;
			while ((buffContent = in.read()) != -1) {
				out.write(buffContent);
			}
			out.flush();
			out.close();
			// 存檔結束
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.err, "存檔失敗  Message =>  " + e.getMessage()));
			e.printStackTrace();
			return null;
		}
		return file;
	}

	public String getDBName() {
		return DBName;
	}

	public File getJDBC() {
		return JDBC;
	}

	public File getSQLZIP() {
		return SQLZIP;
	}
}