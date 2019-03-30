package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ���������
 * @author Administrator
 *
 */

public class ScrollTest {

	public static void main(String[] args) throws Exception {
		scroll();
	}
	
	// ��ѯ�ķ���
	static void scroll() throws Exception {
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			// ���÷ǵ���ģʽ��JdbcUtils���������ʵ����
			conn = JdbcUtils.getConnection();
			// ���õ���ģʽ��JdbcUtils,�����ʵ����
			// conn = JdbcUtilsSingleton.getInstance().getConnection();
			// 3.�������,���ӹ���������Ĳ���
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			// 4.ִ�����
			String sql = "select id,name,birthday,money from user";
			rs = st.executeQuery(sql);			
			// 5.������
			while (rs.next()) {
				System.out.println(rs.getObject("id") + "\t" + rs.getObject("name") + "\t" + rs.getObject("birthday")
						+ "\t" + rs.getObject("money"));

			}
			System.out.println("-------------------");
			rs.absolute(5);  //��λ����5�У����while������ʵ�ַ�ҳ��ѯ����5�п�ʼ����ʾ10��
			int i=0;
			while(rs.next()&&i<10){
				i++;
				System.out.println(rs.getObject("id") + "\t" + rs.getObject("name") + "\t" + rs.getObject("birthday")
				+ "\t" + rs.getObject("money"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// 6.�ر�����
			JdbcUtils.closeConnection(rs, st, conn);
		}
		
	}
}
