package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import idv.lingerkptor.GoodsManager.Operator.api.request.DeleteTagRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.DeleteTagResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DeleteTagDAO implements PreparedStatementCreator {


    private final Properties prop = new Properties();
    private DeleteTagRequest request;

    @SuppressWarnings("unused")
    private DeleteTagDAO() {
    }

    public DeleteTagDAO(DeleteTagRequest request) {
        this.request = request;
    }



    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        // Step1. 載入存放SQL的檔案
        this.loadSQLFile();
        // Step2. 取得標籤ID
        int tid = this.geTagID(connection);
        // Step3. 刪除與商品的對應的關聯
        this.deleteRelationshipWithGoods(tid, connection);
        // Step4. 刪除標籤
        this.deleteTag(tid, connection);
        return null;
    }


    /**
     * Step1. 載入存放SQL的檔案
     */
    private void loadSQLFile() {
        try {
            prop.load(new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL() + "/DeleteTag.properties")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new DAORuntimeException("SQL檔案讀取發生問題", DeleteTagResponse.SQLFILEERROR);
        }
    }

    /**
     * Step2. 取得標籤ID
     *
     * @param connection 資料庫連線
     * @return 標籤ID
     */
    private int geTagID(Connection connection) throws SQLException {
        String SQL = prop.getProperty("searchTID");
        PreparedStatement stat = connection.prepareStatement(SQL);
        stat.setString(1, request.getTagName());
        ResultSet rs = stat.executeQuery();
        if (rs.next())
            return rs.getInt(1);
        throw new DAORuntimeException("標籤不存在", DeleteTagResponse.TAGISNOTEXIST);
    }

    /**
     * Step3. 刪除與商品的對應的關聯
     */
    private void deleteRelationshipWithGoods(int tid, Connection connection) throws SQLException {
        String SQL = prop.getProperty("deleteTagLink");
        PreparedStatement stat = connection.prepareStatement(SQL);
        stat.setInt(1, tid);
        stat.executeUpdate();
    }

    /**
     * Step4. 刪除標籤
     */
    private void deleteTag(int tid, Connection connection) throws SQLException {
        String SQL = prop.getProperty("deleteTag");
        PreparedStatement stat = connection.prepareStatement(SQL);
        stat.setInt(1, tid);
        stat.executeUpdate();
    }
}
