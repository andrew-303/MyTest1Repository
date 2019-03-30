package cn.itcast.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;


public class ProcedureTest {

	public static void main(String[] args) throws Exception {
		proced();
	}

	// 调用Mysql数据库中的存储过程
	static void proced() throws Exception {
		CallableStatement cs = null;//调用存储过程需要用CallableStatement
		ResultSet rs = null;
		Connection conn = null;
		try {
			// 调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			// 调用单例模式的JdbcUtils,会进行实例化
			// conn = JdbcUtilsSingleton.getInstance().getConnection();
			// 3.创建语句
			String sql = "call addUser(?,?,?,?)";			
			cs = conn.prepareCall(sql);
			// 4.执行语句
			cs.registerOutParameter(4, Types.INTEGER);//存储过程中的输出参数，需要先注册
			cs.setString(1, "procdName1");
			cs.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			cs.setFloat(3, 1000);
			
			cs.executeUpdate();
			int id = cs.getInt(4);
			System.out.println("id=" + id);

			// 6.关闭连接
			JdbcUtils.closeConnection(rs, cs, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
