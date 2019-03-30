package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ͨ��PreparedStatement�еķ�������ȡ�ող������ݵ�������ֵ
 * @author Administrator
 *
 */
public class GetGenerateKeys {

	public static void main(String[] args) throws Exception {
		int id = create();
		System.out.println(id);
	}
	
	//�����ķ���
		static int create() throws Exception{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection conn = null;
			int id = 0;
			try {
				//���÷ǵ���ģʽ��JdbcUtils���������ʵ����
				conn = JdbcUtils.getConnection();
				//���õ���ģʽ��JdbcUtils,�����ʵ����
				//conn = JdbcUtilsSingleton.getInstance().getConnection();
				//3.�������							
				//4.ִ�����
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
				//6.�ر�����
				JdbcUtils.closeConnection(rs, ps, conn);
			}
			return id;
		}
}
