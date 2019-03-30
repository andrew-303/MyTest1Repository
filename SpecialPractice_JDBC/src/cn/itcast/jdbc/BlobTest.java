package cn.itcast.jdbc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

/**
 * JDBC���ʶ��������͵�����
 * @author Administrator
 *
 */
public class BlobTest {

	public static void main(String[] args) throws Exception {
		//create();
		read();
//		System.out.println(123456789);
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
			String sql = "insert into blob_test(big_bit) values(?)";			
			ps = conn.prepareStatement(sql);			
			/**
			 * �����ı��ļ���io����
			 * ��ȡͬ��Ŀ¼�µ�JdbcUtils.java�ļ��е����ݣ���д��clob_test���е�big_test�ֶ�
			 */
			File file = new File("1-120F414331cP.jpg");
			//����ʹ���ֽ�������,��ȡͼƬ�ļ�����ΪͼƬ�Ƕ����Ƶģ��������ַ�����������ı��ļ��ǿ������ַ����ķ�����
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			ps.setBinaryStream(1, is, (int)file.length());
			//4.ִ�����			
			int i = ps.executeUpdate();
			System.out.println(i);
			is.close();
			//6.�ر�����
			//JdbcUtils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeConnection(rs, ps, conn);
		}
	}
	
	//��ѯ�ķ���
		static void read() throws Exception{
			Statement st = null;
			ResultSet rs = null;
			Connection conn = null;
			Date d = null;
			try {
				//���÷ǵ���ģʽ��JdbcUtils���������ʵ����
				conn = JdbcUtils.getConnection();
				//���õ���ģʽ��JdbcUtils,�����ʵ����
				//conn = JdbcUtilsSingleton.getInstance().getConnection();
				//3.�������
				//String sql = "select big_bit from blob_test";
				//ps = conn.prepareStatement(sql);			
				st = conn.createStatement();
				//4.ִ�����				
				//rs = ps.executeQuery();
				String sql = "select big_bit from blob_test";
				rs = st.executeQuery(sql);
				//5.������
				
				while(rs.next()){
					//��ȡ���ֽ����ļ�
					Blob blob = rs.getBlob(1);
					InputStream is = blob.getBinaryStream();
					//InputStream is = rs.getBinaryStream("big_bit");
					//׼���ļ�
					File file = new File("blobtest.jpg");
					//���ļ�д��������ֽ�����
					OutputStream ops = new BufferedOutputStream(new FileOutputStream(file));
					byte[] buffer = new byte[1024];
					//is.read(buffer)������ȡ�ֽڵ�һ��������,�˷������ض�ȡ���ֽ��������������ĩβ�ѵ��ﷵ��-1
					for (int i=0; (i=is.read(buffer))>0;) {
						//System.out.println(is.read(buffer)); ����Ĵ�ӡ��䣬�������������д����ļ�����
						ops.write(buffer, 0, i);
					}
					ops.close();
					is.close();
				}
				//6.�ر�����
				//JdbcUtils.closeConnection(rs, st, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				JdbcUtils.closeConnection(rs, st, conn);
			}
			
		}
}
