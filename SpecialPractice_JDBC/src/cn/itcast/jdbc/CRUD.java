package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD {

	public static void main(String[] args) throws Exception {
		//create();
		//delete();
		update();
		read();
	}
	//创建的方法
	static void create() throws Exception{
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			//调用单例模式的JdbcUtils,会进行实例化
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.创建语句
			st = conn.createStatement();			
			//4.执行语句
			String sql = "insert into user(name,birthday,money) values('zhaoyi','1985-03-03',40000)";
			int i = st.executeUpdate(sql);
			System.out.println(i);
			
			//6.关闭连接
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//删除的方法
	static void delete() throws Exception{
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			//调用单例模式的JdbcUtils,会进行实例化
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.创建语句
			st = conn.createStatement();			
			//4.执行语句
			String sql = "delete from user where id=4";
			int i = st.executeUpdate(sql);
			System.out.println(i);
			
			//6.关闭连接
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//修改的方法
	static void update() throws Exception{
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			//调用单例模式的JdbcUtils,会进行实例化
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.创建语句
			st = conn.createStatement();			
			//4.执行语句
			String sql = "update user set money=money+1000";
			int i = st.executeUpdate(sql);
			System.out.println(i);
			
			//6.关闭连接
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//查询的方法
	static void read() throws Exception{
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			//调用单例模式的JdbcUtils,会进行实例化
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.创建语句
			st = conn.createStatement();			
			//4.执行语句
			String sql = "select id,name,birthday,money from user";
			rs = st.executeQuery(sql);
			//5.处理结果
			while(rs.next()){
				System.out.println(rs.getObject("id")+"\t"
						+rs.getObject("name")+"\t"
						+rs.getObject("birthday")+"\t"
						+rs.getObject("money"));
				
			}
			//6.关闭连接
			JdbcUtils.closeConnection(rs, st, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
