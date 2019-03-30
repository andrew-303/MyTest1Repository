package cn.itcast.jdbc;
import java.io.InputStream;
/**
 * ����಻����ʵ��
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import cn.itcast.jdbc.datasource.MyDataSource;
import cn.itcast.jdbc.datasource.MyDataSource2;

public final class JdbcUtils {
	private static String url="jdbc:mysql://localhost:3306/itcast_jdbc";
	private static String user="root";
	private static String password="";
	//ʹ���Լ������ĸ��õ����ӳ�
	private static DataSource myDataSource = null;
	
	//��������˽�еģ���粻��ʵ����
	private JdbcUtils(){
		System.out.println("JdbcUtils���췽��");
	}
	//1.ע��������ֻ��һ�Σ����ھ�̬���У�JVM�����������ʱ�����
	static{
		System.out.println("JdbcUtils��̬�����");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//ʹ���Զ��������Դ
			//myDataSource = new MyDataSource2();
						
			//ʹ��DBCP�ṩ������Դ
			//��������
			Properties prop = new Properties();
			//��ȡ�����ļ���dbcpconfig.properties
			InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			//�������ļ����õ�������
			prop.load(is);
			//����DataSourceFactory����������Դ
			myDataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	//��ȡ����Դ�ķ���
	public static DataSource getDataSource(){
		return myDataSource;
	}
	//2.��������
	public static Connection getConnection() throws Exception{
		System.out.println("JdbcUtils�������ӵķ���");
		//return DriverManager.getConnection(url, user, password);
		return myDataSource.getConnection();
	}
	
	//6.�ر�����
	public static void closeConnection(ResultSet rs ,Statement st,Connection conn) throws SQLException{
		System.out.println("JdbcUtils�ر����ӵķ���");
		try {
				if(rs != null){
				rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}finally{
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(conn != null){
				conn.close();
				//myDataSource.free(conn);
			}
		}
	}
	
	
}
