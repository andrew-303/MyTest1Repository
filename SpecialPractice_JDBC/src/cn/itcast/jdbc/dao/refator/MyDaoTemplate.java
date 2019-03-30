package cn.itcast.jdbc.dao.refator;
/**
 * ģ��ģʽ����DAO�е���ɾ�Ĳ�ķ���
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.itcast.jdbc.JdbcUtils;

public class MyDaoTemplate {
	//��ȡ�����Ĳ�ѯ�ķ���
	public Object find(String sql,Object[]args,RowMapper rowMapper) throws Exception{
		System.out.println("����ģʽ�޸ĵ�ģ�巽�����ģʽ�е�AbstractDao.find()����");
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
				System.out.println("����rs.next()");
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
