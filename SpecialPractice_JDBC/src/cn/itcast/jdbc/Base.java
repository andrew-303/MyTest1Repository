package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Base {
	public static void main(String[] args) throws Exception {
		//template();
		for(int i=0;i<11;i++){
			Connection conn = JdbcUtils.getConnection();
			System.out.println(conn);
			JdbcUtils.closeConnection(null, null, conn);
		}
	}
	 
	static void template() throws Exception{
		
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//���÷ǵ���ģʽ��JdbcUtils���������ʵ����
			conn = JdbcUtils.getConnection();
			//���õ���ģʽ��JdbcUtils,�����ʵ����
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.�������
			st = conn.createStatement();			
			//4.ִ�����
			String sql = "select * from user";
			rs = st.executeQuery(sql);
			//5.������
			while(rs.next()){
				System.out.println(rs.getObject(1)+"\t"+rs.getObject(2)+"\t"+rs.getObject(3)+"\t"+rs.getObject(4));
				
			}
			//6.�ر�����
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
