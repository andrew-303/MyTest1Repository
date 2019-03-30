package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) throws Exception {
		//create("chenliu",new Date(),12000);
		System.out.println(read(4));
	}
	/**
	 * 日期类型的转换
	 * @param name
	 * @param birthday  参数中的birthday是java.util包中的日期,而sql数据库中的birthday是java.sql.Date中的日期，
	 * 	                 并且java.sql.Date是继承自java.util.Date
	 * @param money
	 * @throws Exception 
	 */
	static void create(String name,Date birthday,float money) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			//调用单例模式的JdbcUtils,会进行实例化
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.创建语句
			String sql = "insert into user(name,birthday,money) values(?,?,?)";			
			ps = conn.prepareStatement(sql);			
			ps.setString(1, name);
			ps.setDate(2, new java.sql.Date(birthday.getTime()));//父类不能赋给子类，所以需要进行类型转换
			ps.setFloat(3, money);
			//4.执行语句
			
			int i = ps.executeUpdate();
			System.out.println(i);
			
			//6.关闭连接
			JdbcUtils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//查询的方法
		static Date read(int id) throws Exception{
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
				String sql = "select id,name,birthday,money from user where id=?";
				ps = conn.prepareStatement(sql);			
				ps.setInt(1, id);
				//4.执行语句				
				rs = ps.executeQuery();
				//5.处理结果
				
				while(rs.next()){
					d = rs.getDate("birthday");	//这里是子类赋给父类，不需要做转换
					//System.out.println(d);	
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
