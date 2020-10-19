package idv.GoodsManager.installation.DataAccess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class CreateTable implements PreparedStatementCreator  {

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) {
		Properties sqlMap = new Properties();
		try {
			sqlMap.load(new FileReader(new File(ConfigReader.getDBConfig().getSqlUri()+"/CreateTable.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String SQL =sqlMap.getProperty("GOODS");
		System.out.println("Goods SQL = "+SQL);
		
		return null;
	}


}
