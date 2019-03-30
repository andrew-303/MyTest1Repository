package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInject {

	public static void main(String[] args) throws Exception {
		//read("''or 1 or''");//��sql��ѯ�У����ַ���ƴ�ӵķ�ʽ���ͻ��γ�SQLע�룬������Ҫ��Statement����PrepareStatement
		read("zhangsan");
		
	}
	
	static void read(String name) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//���÷ǵ���ģʽ��JdbcUtils���������ʵ����
			conn = JdbcUtils.getConnection();
			//���õ���ģʽ��JdbcUtils,�����ʵ����
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.�������
			String sql = "select id,name,birthday,money from user where name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			//4.ִ�����
			//String sql = "select id,name,birthday,money from user where name="+name;
			System.out.println(sql);
			rs = ps.executeQuery();
			//5.������
			while(rs.next()){
				System.out.println(rs.getObject("id")+"\t"
						+rs.getObject("name")+"\t"
						+rs.getObject("birthday")+"\t"
						+rs.getObject("money"));
				
			}
			//6.�ر�����
			JdbcUtils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
