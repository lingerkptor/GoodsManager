package idv.GoodsManager.installation.api.request;

import java.io.File;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class ActiveDBRequest implements Request {
	/**
	 * 要啟用的DBName
	 */
	private String DBName = null;
	/**
	 * 要啟用的JDBCName
	 */
	private String JDBCName = null;
	/**
	 * 要啟用的SQLName
	 */
	private String SQLName = null;

	/**
	 * 暫存位址
	 */
	private final String tempDirPath = ConfigReader.getWebAddr() + "//temp";

	private File JDBC = null;
	private File SQLZIP = null;

	@Override
	public void setAttribute(HttpSession session) {
		this.JDBC = new File(tempDirPath + this.JDBCName);
		this.SQLZIP = new File(tempDirPath + this.SQLName);
	}

	@Override
	public void setAttribute(String attrName, Object obj) {

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
