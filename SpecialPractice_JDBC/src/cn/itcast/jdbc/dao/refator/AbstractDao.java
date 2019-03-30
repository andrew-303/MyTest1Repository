package cn.itcast.jdbc.dao.refator;
/**
 * 模板模式处理DAO中的增删改查的方法
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.itcast.jdbc.JdbcUtils;

public abstract class AbstractDao {
	//抽取出来的查询的方法
	public Object find(String sql,Object[]args) throws Exception{
		System.out.println("模板方法设计模式中的AbstractDao.find()方法");
		System.out.println(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				System.out.println("args[i]="+args[i]);
				ps.setObject(i+1, args[i]);
			}
			rs = ps.executeQuery();
			//System.out.println("rs.next()="+rs.next());
			Object obj = null;
			if(rs.next()){
				System.out.println("进入rs.next()");
				obj = rowMapper(rs);
				//System.out.println(obj);
			}
			return obj;
		}catch(Exception e){
			throw new Exception();
		}finally{
			JdbcUtils.closeConnection(rs, ps, conn);
		} 
		
	}
	
	abstract protected Object rowMapper(ResultSet rs) throws SQLException;
	
	
	//抽取出来的修改的方法
	public int update(String sql,Object[]args) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				ps.setObject(i+1, args[i]);
			}
			return ps.executeUpdate();
		}catch(Exception e){
			throw new Exception();
		}finally{
			JdbcUtils.closeConnection(rs, ps, conn);
		}
		
		
	}
	
}
