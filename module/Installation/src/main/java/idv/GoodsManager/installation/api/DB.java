package idv.GoodsManager.installation.api;

public class DB {
	private String DBName = null;
	private String JDBCName = null;

	DB(String name, String JDBCName) {
		this.DBName = name;
		this.JDBCName = JDBCName;
	}

	/**
	 * @return the DBName
	 */
	public String getDBName() {
		return DBName;
	}

	/**
	 * @return the JDBCName
	 */
	public String getJDBCName() {
		return JDBCName;
	}

	/**
	 * @param jDBCName the jDBCName to set
	 */
	public void setJDBCName(String jDBCName) {
		JDBCName = jDBCName;
	}
	
}
