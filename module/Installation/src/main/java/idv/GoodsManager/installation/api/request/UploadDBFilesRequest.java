package idv.GoodsManager.installation.api.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

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