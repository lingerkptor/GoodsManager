package idv.GoodsManager.installation.api.request;

import java.io.File;

import javax.servlet.http.HttpSession;
import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class UploadDBFilesRequest implements Request {
	private File JDBC = null;
	private File SQLZIP = null;
	private String DBName = "";

	@Override
	public void setAttribute(HttpSession session) {

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