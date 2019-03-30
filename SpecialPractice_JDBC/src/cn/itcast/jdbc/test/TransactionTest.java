package cn.itcast.jdbc.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import cn.itcast.jdbc.JdbcUtils;

public class TransactionTest {

	public static void main(String[] args) throws Exception {
		read();
	}

	//��ѯ�ķ���
		static void read() throws Exception{
			Statement st = null;
			ResultSet rs = null;
			Connection conn = null;
			Savepoint sp = null;
			try {
				//���÷ǵ���ģʽ��JdbcUtils���������ʵ����
				conn = JdbcUtils.getConnection();
				conn.setAutoCommit(false);
				//���õ���ģʽ��JdbcUtils,�����ʵ����
				//conn = JdbcUtilsSingleton.getInstance().getConnection();
				//3.�������
				st = conn.createStatement();			
				//4.ִ�����
				String sql = "update user set money=money-100 where id=1";
				st.executeUpdate(sql);
				sp = conn.setSavepoint();
				
				String sql1 = "update user set money=money-100 where id=3";
				st.executeUpdate(sql1);
				
				sql = "select money from user where id=2";
				rs = st.executeQuery(sql);
				float money = 0.0f;
				//5.������
				if(rs.next()){
					money = rs.getFloat("money");
					System.out.println(money);
				}
				if(money>3000)					
					throw new RuntimeException("�������ֵ����");
				
				sql = "update user set money=money+100 where id=2";
				st.executeUpdate(sql);
				conn.commit();					
			} catch (RuntimeException e) {
				if (conn != null && sp != null) {
					conn.rollback(sp);
					conn.commit();
				}
				throw e;
			}	catch (SQLException e) {
				if(conn!=null && sp!=null){
					conn.rollback(sp);
					throw e;
				}
			}finally{
				JdbcUtils.closeConnection(rs, st, conn);
			}
		}
}
