package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 滚动结果集
 * @author Administrator
 *
 */

public class ScrollTest {

	public static void main(String[] args) throws Exception {
		scroll();
	}
	
	// 查询的方法
	static void scroll() throws Exception {
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			// 调用非单例模式的JdbcUtils，不会进行实例化
			conn = JdbcUtils.getConnection();
			// 调用单例模式的JdbcUtils,会进行实例化
			// conn = JdbcUtilsSingleton.getInstance().getConnection();
			// 3.创建语句,增加滚动结果集的参数
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			// 4.执行语句
			String sql = "select id,name,birthday,money from user";
			rs = st.executeQuery(sql);			
			// 5.处理结果
			while (rs.next()) {
				System.out.println(rs.getObject("id") + "\t" + rs.getObject("name") + "\t" + rs.getObject("birthday")
						+ "\t" + rs.getObject("money"));

			}
			System.out.println("-------------------");
			rs.absolute(5);  //定位到地5行，结合while语句可以实现分页查询，第5行开始，显示10行
			int i=0;
			while(rs.next()&&i<10){
				i++;
				System.out.println(rs.getObject("id") + "\t" + rs.getObject("name") + "\t" + rs.getObject("birthday")
				+ "\t" + rs.getObject("money"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// 6.关闭连接
			JdbcUtils.closeConnection(rs, st, conn);
		}
		
	}
}
