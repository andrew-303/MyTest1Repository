package cn.itcast.jdbc;
import java.io.InputStream;
/**
 * 这个类不会有实例
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
	//使用自己创建的复用的连接池
	private static DataSource myDataSource = null;
	
	//构造器是私有的，外界不能实例化
	private JdbcUtils(){
		System.out.println("JdbcUtils构造方法");
	}
	//1.注册驱动，只有一次，放在静态域中，JVM虚拟机启动的时候加载
	static{
		System.out.println("JdbcUtils静态代码块");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//使用自定义的数据源
			//myDataSource = new MyDataSource2();
						
			//使用DBCP提供的数据源
			//设置属性
			Properties prop = new Properties();
			//读取配置文件：dbcpconfig.properties
			InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			//将配置文件设置到属性中
			prop.load(is);
			//调用DataSourceFactory，创建数据源
			myDataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	//获取数据源的方法
	public static DataSource getDataSource(){
		return myDataSource;
	}
	//2.建立连接
	public static Connection getConnection() throws Exception{
		System.out.println("JdbcUtils建立连接的方法");
		//return DriverManager.getConnection(url, user, password);
		return myDataSource.getConnection();
	}
	
	//6.关闭连接
	public static void closeConnection(ResultSet rs ,Statement st,Connection conn) throws SQLException{
		System.out.println("JdbcUtils关闭连接的方法");
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
