package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLCondition {

	public void appendCondition(StringBuilder builder);

	public int insertData(PreparedStatement stat, int parameterIndex) throws SQLException;

}
