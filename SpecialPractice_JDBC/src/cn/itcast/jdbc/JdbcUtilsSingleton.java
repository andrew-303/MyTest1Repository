package cn.itcast.jdbc;
/**
 * �����ֻ��һ��ʵ�������Խе���
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
	
	
	//��������˽�еģ���粻��ʵ����,�����ڲ�����ʵ����
	private JdbcUtilsSingleton(){
		System.out.println("JdbcUtils��˽�й��췽��");
	}
	//������ʵ�ִ���
	private static JdbcUtilsSingleton instance = new JdbcUtilsSingleton();
	public static JdbcUtilsSingleton getInstance(){
		return instance;
	}
	
	//1.ע��������ֻ��һ�Σ����ھ�̬���У�JVM�����������ʱ�����
	static{
		System.out.println("JdbcUtils��̬�����");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	//2.��������
	public  Connection getConnection() throws SQLException{
		System.out.println("JdbcUtils�������ӵķ���");
		return DriverManager.getConnection(url, user, password);
	}
	
	//6.�ر�����
	public  void closeConnection(ResultSet rs ,Statement st,Connection conn){
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
