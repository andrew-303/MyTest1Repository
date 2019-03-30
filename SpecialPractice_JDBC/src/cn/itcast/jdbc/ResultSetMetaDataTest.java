package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结果集的元数据
 * @author Administrator
 *
 */
public class ResultSetMetaDataTest {
	public static void main(String[] args) throws Exception {
		read("select * from user where id =3");
		System.out.println("---------------------");
		Map<String, Object> readMap = readMap("select * from user where id =3");
		System.out.println(readMap);
		System.out.println("---------------------");
		List<Map<String,Object>> readList = readList("select * from user where id <=5");
		System.out.println(readList);
	}
	
	static void read(String sql) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =  null;
		
		conn = JdbcUtils.getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData metaData =  rs.getMetaData();
		int count = metaData.getColumnCount(); //结果集中元数据有几列
		System.out.println(count);
		String[] colNames = new String[count];
		for(int i=1;i<=count;i++){
			System.out.print(metaData.getColumnClassName(i) +"\t");
			System.out.print(metaData.getColumnName(i) +"\t");
			System.out.println(metaData.getColumnLabel(i) +"\t");
		}
		
	}
	
	static Map<String,Object> readMap(String sql) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =  null;
		
		conn = JdbcUtils.getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData metaData =  rs.getMetaData();
		int count = metaData.getColumnCount(); //结果集中元数据有几列
		
		String[] colNames = new String[count];
		for(int i=1;i<=count;i++){
			colNames[i-1] = metaData.getColumnName(i);
		}
		Map<String,Object> data = new HashMap<String,Object>();
		if(rs.next()){
			for(int i=0;i< colNames.length;i++){
				data.put(colNames[i], rs.getObject(colNames[i]));
			}
		}
		return data;
	}
	
	static List<Map<String,Object>> readList(String sql) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs =  null;
		
		conn = JdbcUtils.getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData metaData =  rs.getMetaData();
		int count = metaData.getColumnCount(); //结果集中元数据有几列
		
		String[] colNames = new String[count];
		for(int i=1;i<=count;i++){
			colNames[i-1] = metaData.getColumnName(i);
		}
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		
		while(rs.next()){
			Map<String,Object> data = new HashMap<String,Object>();
			for(int i=0;i< colNames.length;i++){
				data.put(colNames[i], rs.getObject(colNames[i]));
			}
			datas.add(data);
		}
		return datas;
	}
}
