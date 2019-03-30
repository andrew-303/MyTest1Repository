package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL��������
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
	
	// �����ķ���
	static void create(int i) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			// ���÷ǵ���ģʽ��JdbcUtils���������ʵ����
			conn = JdbcUtils.getConnection();
			// ���õ���ģʽ��JdbcUtils,�����ʵ����
			// conn = JdbcUtilsSingleton.getInstance().getConnection();
			// 3.�������
			// 4.ִ�����
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
			// 6.�ر�����
			JdbcUtils.closeConnection(rs, ps, conn);
		}
	}
	
	// ���������ķ���
	static void createBatch() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			// ���÷ǵ���ģʽ��JdbcUtils���������ʵ����
			conn = JdbcUtils.getConnection();
			// ���õ���ģʽ��JdbcUtils,�����ʵ����
			// conn = JdbcUtilsSingleton.getInstance().getConnection();
			// 3.�������
			// 4.ִ�����
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
			// 6.�ر�����
			JdbcUtils.closeConnection(rs, ps, conn);
		}
	}
}
