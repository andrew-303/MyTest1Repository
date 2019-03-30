package cn.itcast.jdbc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.jdbc.domain.User;

public class ORMTest {
	public static void main(String[] args) throws Exception {
	User user = getUser("select id Id,name,Name,birthday Birthday,money Money from user where id=1");
	System.out.println(user);
	}
	
	static User getUser(String sql) throws Exception{		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs =  null;
			
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData =  rs.getMetaData();//获取结果集中的元数据
			int count = metaData.getColumnCount(); //结果集中元数据有几列
			
			String[] colNames = new String[count];
			for(int i=1;i<=count;i++){
				colNames[i-1] = metaData.getColumnLabel(i);//获取别名
			}
			User user = null;
			if(rs.next()){
				user = new User();				
				for(int i=0;i< colNames.length;i++){
					String colName = colNames[i];
					String methodName = "set" + colName;
					Method[] methods = user.getClass().getMethods();
					for(Method m :methods){
						if(methodName.equals(m.getName())){
							m.invoke(user, rs.getObject(colName));
						}
					}					
				}
				
			}
			return user;
		}		
	
	static Object getObject(String sql,Class clazz) throws Exception{		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =  null;
		
		conn = JdbcUtils.getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData metaData =  rs.getMetaData();//获取结果集中的元数据
		int count = metaData.getColumnCount(); //结果集中元数据有几列
		
		String[] colNames = new String[count];
		for(int i=1;i<=count;i++){
			colNames[i-1] = metaData.getColumnLabel(i);//获取别名
		}
		Object object = null;
		Method[] ms = clazz.getMethods(); 
		if(rs.next()){
			object = clazz.newInstance();				
			for(int i=0;i< colNames.length;i++){
				String colName = colNames[i];
				String methodName = "set" + colName;
				
				for(Method m :ms){
					if(methodName.equals(m.getName())){
						m.invoke(object, rs.getObject(colName));
					}
				}					
			}
			
		}
		return object;
	}		
}
