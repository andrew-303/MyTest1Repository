package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 通过PreparedStatement中的方法，获取刚刚插入数据的主键的值
 * @author Administrator
 *
 */
public class GetGenerateKeys {

	public static void main(String[] args) throws Exception {
		int id = create();
		System.out.println(id);
	}
	
	//创建的方法
		static int create() throws Exception{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			int id = 0;
			try {
				//调用非单例模式的JdbcUtils，不会进行实例化
				conn = JdbcUtils.getConnection();
				//调用单例模式的JdbcUtils,会进行实例化
				//conn = JdbcUtilsSingleton.getInstance().getConnection();
				//3.创建语句							
				//4.执行语句
				String sql = "insert into user(name,birthday,money) values('zhaoyi','1985-03-03',40000)";
				ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				
				if(rs.next()){
					id = rs.getInt(1);		
				}
										
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//6.关闭连接
				JdbcUtils.closeConnection(rs, ps, conn);
			}
			return id;
		}
}
