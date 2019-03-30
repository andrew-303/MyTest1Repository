package cn.itcast.jdbc.dao.refator;
/**
 * 模板模式处理DAO中的增删改查的方法
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.itcast.jdbc.JdbcUtils;

public class MyDaoTemplate {
	//抽取出来的查询的方法
	public Object find(String sql,Object[]args,RowMapper rowMapper) throws Exception{
		System.out.println("策略模式修改的模板方法设计模式中的AbstractDao.find()方法");
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
				obj = rowMapper.mapRow(rs);
				//System.out.println(obj);
			}
			return obj;
		}catch(Exception e){
			throw new Exception();
		}finally{
			JdbcUtils.closeConnection(rs, ps, conn);
		} 
		
	}
	
	
	
}
