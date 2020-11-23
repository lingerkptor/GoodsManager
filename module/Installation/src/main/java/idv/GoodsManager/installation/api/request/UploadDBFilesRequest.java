package idv.GoodsManager.installation.api.request;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class UploadDBFilesRequest implements Request {
//	private File JDBC = null;
//	private File SQLZIP = null;
	private Map<String,File> fileMap = new HashMap<String,File>();
	private String DBName = "";

	@Override
	public void setAttribute(HttpSession session) {

	}

	public String getDBName() {
		return DBName;
	}

	public File getJDBC() {
//		return JDBC;
		return fileMap.get("JDBC");
	}

	public File getSQLZIP() {
//		return SQLZIP;
		return fileMap.get("SQLZIP");
	}
}