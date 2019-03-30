package cn.itcast.jdbc.dao.refator;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.itcast.jdbc.dao.UserDao;
import cn.itcast.jdbc.domain.User;

public class UserDaoImpl2 {	
	MyDaoTemplate template = new MyDaoTemplate();
	
	//用模板模式执行查询的方法
	public User findUser(String loginName,String password) throws Exception{
		String sql = "select id,name,money,birthday from user where name=?";
		Object[] args = new Object[]{loginName};
		System.out.println(sql);
		RowMapper rowMapper = new UserRowMapper();
		Object user = this.template.find(sql, args,rowMapper);
		System.out.println("user="+user);
		return (User) user;
	}

	public String findUserName(int id) throws Exception{
		String sql = "select name from user where id=?";
		Object[] args = new Object[]{id};
		System.out.println("args="+args.length);
		//new RowMapper()是匿名内部类的形式
		Object name = this.template.find(sql, args,new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs) throws Exception {
				// TODO Auto-generated method stub
				return rs.getString("name");
			}		
		});
		System.out.println("user.name="+name);
		return (String)name;
	}

		
}

class  UserRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs) throws Exception{
		System.out.println("UserDaoImpl2中的mapRow()方法");
		User user = new User();
		user.setName(rs.getString("name"));
		user.setId(rs.getInt("id"));
		user.setMoney(rs.getFloat("money"));
		user.setBirthday(rs.getDate("birthday"));
		System.out.println("UserDaoImpl2中的mapRow()方法获得的User的对象="+user);
		return user;
		}

}


