package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) throws Exception {
		//create("chenliu",new Date(),12000);
		System.out.println(read(4));
	}
	/**
	 * �������͵�ת��
	 * @param name
	 * @param birthday  �����е�birthday��java.util���е�����,��sql���ݿ��е�birthday��java.sql.Date�е����ڣ�
	 * 	                 ����java.sql.Date�Ǽ̳���java.util.Date
	 * @param money
	 * @throws Exception 
	 */
	static void create(String name,Date birthday,float money) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//���÷ǵ���ģʽ��JdbcUtils���������ʵ����
			conn = JdbcUtils.getConnection();
			//���õ���ģʽ��JdbcUtils,�����ʵ����
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.�������
			String sql = "insert into user(name,birthday,money) values(?,?,?)";			
			ps = conn.prepareStatement(sql);			
			ps.setString(1, name);
			ps.setDate(2, new java.sql.Date(birthday.getTime()));//���಻�ܸ������࣬������Ҫ��������ת��
			ps.setFloat(3, money);
			//4.ִ�����
			
			int i = ps.executeUpdate();
			System.out.println(i);
			
			//6.�ر�����
			JdbcUtils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ѯ�ķ���
		static Date read(int id) throws Exception{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			Date d = null;
			try {
				//���÷ǵ���ģʽ��JdbcUtils���������ʵ����
				conn = JdbcUtils.getConnection();
				//���õ���ģʽ��JdbcUtils,�����ʵ����
				//conn = JdbcUtilsSingleton.getInstance().getConnection();
				//3.�������
				String sql = "select id,name,birthday,money from user where id=?";
				ps = conn.prepareStatement(sql);			
				ps.setInt(1, id);
				//4.ִ�����				
				rs = ps.executeQuery();
				//5.������
				
				while(rs.next()){
					d = rs.getDate("birthday");	//���������ำ�����࣬����Ҫ��ת��
					//System.out.println(d);	
				}
				//6.�ر�����
				JdbcUtils.closeConnection(rs, ps, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return d;
		}
}
