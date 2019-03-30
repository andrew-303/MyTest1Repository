package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD {

	public static void main(String[] args) throws Exception {
		//create();
		//delete();
		update();
		read();
	}
	//�����ķ���
	static void create() throws Exception{
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
			String sql = "insert into user(name,birthday,money) values('zhaoyi','1985-03-03',40000)";
			int i = st.executeUpdate(sql);
			System.out.println(i);
			
			//6.�ر�����
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//ɾ���ķ���
	static void delete() throws Exception{
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
			String sql = "delete from user where id=4";
			int i = st.executeUpdate(sql);
			System.out.println(i);
			
			//6.�ر�����
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�޸ĵķ���
	static void update() throws Exception{
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
			String sql = "update user set money=money+1000";
			int i = st.executeUpdate(sql);
			System.out.println(i);
			
			//6.�ر�����
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ѯ�ķ���
	static void read() throws Exception{
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
			String sql = "select id,name,birthday,money from user";
			rs = st.executeQuery(sql);
			//5.������
			while(rs.next()){
				System.out.println(rs.getObject("id")+"\t"
						+rs.getObject("name")+"\t"
						+rs.getObject("birthday")+"\t"
						+rs.getObject("money"));
				
			}
			//6.�ر�����
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
