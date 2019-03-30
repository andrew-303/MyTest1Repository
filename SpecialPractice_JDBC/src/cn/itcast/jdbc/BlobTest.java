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
 * JDBC访问二进制类型的数据
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
			//调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			//调用单例模式的JdbcUtils,会进行实例化
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.创建语句
			String sql = "insert into blob_test(big_bit) values(?)";			
			ps = conn.prepareStatement(sql);			
			/**
			 * 操作文本文件的io操作
			 * 读取同级目录下的JdbcUtils.java文件中的内容，并写入clob_test表中的big_test字段
			 */
			File file = new File("1-120F414331cP.jpg");
			//这里使用字节输入流,读取图片文件，因为图片是二进制的，不能用字符流，如果是文本文件是可以用字符流的方法的
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			ps.setBinaryStream(1, is, (int)file.length());
			//4.执行语句			
			int i = ps.executeUpdate();
			System.out.println(i);
			is.close();
			//6.关闭连接
			//JdbcUtils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeConnection(rs, ps, conn);
		}
	}
	
	//查询的方法
		static void read() throws Exception{
			Statement st = null;
			ResultSet rs = null;
			Connection conn = null;
			Date d = null;
			try {
				//调用非单例模式的JdbcUtils，不会进行实例化
				conn = JdbcUtils.getConnection();
				//调用单例模式的JdbcUtils,会进行实例化
				//conn = JdbcUtilsSingleton.getInstance().getConnection();
				//3.创建语句
				//String sql = "select big_bit from blob_test";
				//ps = conn.prepareStatement(sql);			
				st = conn.createStatement();
				//4.执行语句				
				//rs = ps.executeQuery();
				String sql = "select big_bit from blob_test";
				rs = st.executeQuery(sql);
				//5.处理结果
				
				while(rs.next()){
					//读取出字节流文件
					Blob blob = rs.getBlob(1);
					InputStream is = blob.getBinaryStream();
					//InputStream is = rs.getBinaryStream("big_bit");
					//准备文件
					File file = new File("blobtest.jpg");
					//将文件写出到输出字节流中
					OutputStream ops = new BufferedOutputStream(new FileOutputStream(file));
					byte[] buffer = new byte[1024];
					//is.read(buffer)方法读取字节到一个数组中,此方法返回读取的字节数，或如果流的末尾已到达返回-1
					for (int i=0; (i=is.read(buffer))>0;) {
						//System.out.println(is.read(buffer)); 这里的打印语句，不能输出，否则写入的文件会损坏
						ops.write(buffer, 0, i);
					}
					ops.close();
					is.close();
				}
				//6.关闭连接
				//JdbcUtils.closeConnection(rs, st, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				JdbcUtils.closeConnection(rs, st, conn);
			}
			
		}
}
