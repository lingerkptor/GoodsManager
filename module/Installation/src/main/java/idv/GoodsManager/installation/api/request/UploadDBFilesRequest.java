package idv.GoodsManager.installation.api.request;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class UploadDBFilesRequest implements Request {
	private Map<String,File> fileMap = new HashMap<String,File>();
	private String DBName = "";


	public String getDBName() {
		return DBName;
	}

	public File getJDBC() {
		return fileMap.get("JDBC");
	}

	public File getSQLZIP() {
		return fileMap.get("SQLZIP");
	}
}