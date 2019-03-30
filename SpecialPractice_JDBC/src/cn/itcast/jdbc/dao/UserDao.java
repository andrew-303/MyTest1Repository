package cn.itcast.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.itcast.jdbc.domain.User;

public interface UserDao {
	public User findUser(String loginName, String password) throws Exception;

	
}
