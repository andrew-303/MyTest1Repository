package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInject {

	public static void main(String[] args) throws Exception {
		//read("''or 1 or''");//在sql查询中，用字符串拼接的方式，就会形成SQL注入，所以需要将Statement换成PrepareStatement
		read("zhangsan");
		
	}
	
	static void read(String name) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			//调用单例模式的JdbcUtils,会进行实例化
			//conn = JdbcUtilsSingleton.getInstance().getConnection();
			//3.创建语句
			String sql = "select id,name,birthday,money from user where name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			//4.执行语句
			//String sql = "select id,name,birthday,money from user where name="+name;
			System.out.println(sql);
			rs = ps.executeQuery();
			//5.处理结果
			while(rs.next()){
				System.out.println(rs.getObject("id")+"\t"
						+rs.getObject("name")+"\t"
						+rs.getObject("birthday")+"\t"
						+rs.getObject("money"));
				
			}
			//6.关闭连接
			JdbcUtils.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
