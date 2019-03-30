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
 * JDBC访问大文本数据
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
			//调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			//调用单例模式的JdbcUtils,会进行实例化
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.创建语句
			String sql = "insert into clob_test(big_text) values(?)";			
			ps = conn.prepareStatement(sql);			
			/**
			 * 操作文本文件的io操作
			 * 读取同级目录下的JdbcUtils.java文件中的内容，并写入clob_test表中的big_test字段
			 */
			File file = new File("src/cn/itcast/jdbc/JdbcUtils.java");
			Reader reader = new BufferedReader(new FileReader(file));
			ps.setCharacterStream(1, reader, (int)file.length());
			//4.执行语句			
			int i = ps.executeUpdate();
			System.out.println(i);
			reader.close();
			//6.关闭连接
			JdbcUtils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//查询的方法
		static Date read() throws Exception{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			Date d = null;
			try {
				//调用非单例模式的JdbcUtils，不会进行实例化
				conn = JdbcUtils.getConnection();
				//调用单例模式的JdbcUtils,会进行实例化
				//conn = JdbcUtilsSingleton.getInstance().getConnection();
				//3.创建语句
				String sql = "select big_text from clob_test";
				ps = conn.prepareStatement(sql);			
				
				//4.执行语句				
				rs = ps.executeQuery();
				//5.处理结果
				
				while(rs.next()){
					Clob clob = rs.getClob(1);
					Reader reader = clob.getCharacterStream();
					
					File file = new File("clobtest.txt");
					Writer writer = new BufferedWriter(new FileWriter(file));
					char[] buffer = new char[1024];
					//reader.read(buffer)方法读取字符到一个数组中,此方法返回读取的字符数，或如果流的末尾已到达返回-1
					for (int i=0; (i=reader.read(buffer))>0;) {
						System.out.println(reader.read(buffer));
						writer.write(buffer, 0, i);
					}
					writer.close();
					reader.close();
				}
				//6.关闭连接
				JdbcUtils.closeConnection(rs, ps, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return d;
		}
}
