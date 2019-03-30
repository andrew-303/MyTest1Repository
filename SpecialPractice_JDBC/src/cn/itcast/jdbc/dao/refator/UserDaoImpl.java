package cn.itcast.jdbc.dao.refator;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.itcast.jdbc.dao.UserDao;
import cn.itcast.jdbc.domain.User;

public class UserDaoImpl extends AbstractDao implements UserDao{
	
	public void update(User user) throws Exception{
		String sql = "update user set name=?,birthday=?,money=? where id=?";
		Object[] args = new Object[]{user.getName(),user.getBirthday(),user.getMoney(),user.getId()};
		super.update(sql, args);
	}

	//用模板模式执行查询的方法
	public User findUser(String loginName,String password) throws Exception{
		String sql = "select id,name,money,birthday from user where name=?";
		Object[] args = new Object[]{loginName};
		System.out.println(sql);
		Object user = super.find(sql, args);
		System.out.println("user="+user);
		return (User) user;
	}
	
	@Override
	protected Object rowMapper(ResultSet rs) throws SQLException {
		System.out.println("UserDaoImpl中的rowMapper()方法");
		User user = new User();
		user.setName(rs.getString("name"));
		user.setId(rs.getInt("id"));
		user.setMoney(rs.getFloat("money"));
		user.setBirthday(rs.getDate("birthday"));
		System.out.println("UserDaoImpl中的rowMapper()方法获得的User的对象="+user);
		return user;
	}


	
}
