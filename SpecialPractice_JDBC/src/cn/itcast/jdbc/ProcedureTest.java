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

	// ����Mysql���ݿ��еĴ洢����
	static void proced() throws Exception {
		CallableStatement cs = null;//���ô洢������Ҫ��CallableStatement
		ResultSet rs = null;
		Connection conn = null;
		try {
			// ���÷ǵ���ģʽ��JdbcUtils���������ʵ����
			conn = JdbcUtils.getConnection();
			// ���õ���ģʽ��JdbcUtils,�����ʵ����
			// conn = JdbcUtilsSingleton.getInstance().getConnection();
			// 3.�������
			String sql = "call addUser(?,?,?,?)";			
			cs = conn.prepareCall(sql);
			// 4.ִ�����
			cs.registerOutParameter(4, Types.INTEGER);//�洢�����е������������Ҫ��ע��
			cs.setString(1, "procdName1");
			cs.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			cs.setFloat(3, 1000);
			
			cs.executeUpdate();
			int id = cs.getInt(4);
			System.out.println("id=" + id);

			// 6.�ر�����
			JdbcUtils.closeConnection(rs, cs, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
