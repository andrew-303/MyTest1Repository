package cn.itcast.jdbc;
/**
 * 这个类只有一个实例。所以叫单例
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtilsSingleton {
	private String url="jdbc:mysql://localhost:3306/itcast_jdbc";
	private String user="root";
	private String password="";
	
	
	//构造器是私有的，外界不能实例化,但是内部可以实例化
	private JdbcUtilsSingleton(){
		System.out.println("JdbcUtils的私有构造方法");
	}
	//单例的实现代码
	private static JdbcUtilsSingleton instance = new JdbcUtilsSingleton();
	public static JdbcUtilsSingleton getInstance(){
		return instance;
	}
	
	//1.注册驱动，只有一次，放在静态域中，JVM虚拟机启动的时候加载
	static{
		System.out.println("JdbcUtils静态代码块");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	//2.建立连接
	public  Connection getConnection() throws SQLException{
		System.out.println("JdbcUtils建立连接的方法");
		return DriverManager.getConnection(url, user, password);
	}
	
	//6.关闭连接
	public  void closeConnection(ResultSet rs ,Statement st,Connection conn){
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
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
