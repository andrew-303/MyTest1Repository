package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL的批处理
 * @author Administrator
 *
 */
public class BatchTest {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		for(int i=0;i<100;i++){
			create(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("create:"+ (end-start));
		
		start = System.currentTimeMillis();		
			createBatch();		
		end = System.currentTimeMillis();
		System.out.println("createBatch:"+ (end-start));
	}
	
	// 创建的方法
	static void create(int i) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			// 调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			// 调用单例模式的JdbcUtils,会进行实例化
			// conn = JdbcUtilsSingleton.getInstance().getConnection();
			// 3.创建语句
			// 4.执行语句
			String sql = "insert into user(name,birthday,money) values(?,?,?)";						
			ps = conn.prepareStatement(sql);
			ps.setString(1, "name"+i);
			ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			ps.setFloat(3, 1000+i);		
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// 6.关闭连接
			JdbcUtils.closeConnection(rs, ps, conn);
		}
	}
	
	// 批量创建的方法
	static void createBatch() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			// 调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			// 调用单例模式的JdbcUtils,会进行实例化
			// conn = JdbcUtilsSingleton.getInstance().getConnection();
			// 3.创建语句
			// 4.执行语句
			String sql = "insert into user(name,birthday,money) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			for(int i=0;i<100;i++){
				ps.setString(1, "batch name" + i);
				ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
				ps.setFloat(3, 1000 + i);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 6.关闭连接
			JdbcUtils.closeConnection(rs, ps, conn);
		}
	}
}
