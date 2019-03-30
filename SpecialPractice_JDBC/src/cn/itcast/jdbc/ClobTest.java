package cn.itcast.jdbc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

/**
 * JDBC���ʴ��ı�����
 * @author Administrator
 *
 */
public class ClobTest {

	public static void main(String[] args) throws Exception {
		//create();
		read();
	}
	static void create() throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//���÷ǵ���ģʽ��JdbcUtils���������ʵ����
			conn = JdbcUtils.getConnection();
			//���õ���ģʽ��JdbcUtils,�����ʵ����
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.�������
			String sql = "insert into clob_test(big_text) values(?)";			
			ps = conn.prepareStatement(sql);			
			/**
			 * �����ı��ļ���io����
			 * ��ȡͬ��Ŀ¼�µ�JdbcUtils.java�ļ��е����ݣ���д��clob_test���е�big_test�ֶ�
			 */
			File file = new File("src/cn/itcast/jdbc/JdbcUtils.java");
			Reader reader = new BufferedReader(new FileReader(file));
			ps.setCharacterStream(1, reader, (int)file.length());
			//4.ִ�����			
			int i = ps.executeUpdate();
			System.out.println(i);
			reader.close();
			//6.�ر�����
			JdbcUtils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��ѯ�ķ���
		static Date read() throws Exception{
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
				String sql = "select big_text from clob_test";
				ps = conn.prepareStatement(sql);			
				
				//4.ִ�����				
				rs = ps.executeQuery();
				//5.������
				
				while(rs.next()){
					Clob clob = rs.getClob(1);
					Reader reader = clob.getCharacterStream();
					
					File file = new File("clobtest.txt");
					Writer writer = new BufferedWriter(new FileWriter(file));
					char[] buffer = new char[1024];
					//reader.read(buffer)������ȡ�ַ���һ��������,�˷������ض�ȡ���ַ��������������ĩβ�ѵ��ﷵ��-1
					for (int i=0; (i=reader.read(buffer))>0;) {
						System.out.println(reader.read(buffer));
						writer.write(buffer, 0, i);
					}
					writer.close();
					reader.close();
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
