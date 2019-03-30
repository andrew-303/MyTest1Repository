package cn.itcast.jdbc.spring.JdbcTemplate;
/**
 * 使用支持命名参数的JdbcTempldate
 */
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.jdbc.domain.User;

public class NamedJdbcTemplate {

	static NamedParameterJdbcTemplate named = new NamedParameterJdbcTemplate(JdbcUtils.getDataSource()); 
	
	public static void main(String[] args) {
		User u = new User();
		u.setMoney(10f);
		u.setId(2);
		System.out.println(findUser(u));
		System.out.println(findUser2(u));
		addUser(u);
	}

	static void addUser(User user){
		String sql = "insert into user(name,birthday, money) values (:name,:birthday,:money)";
		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
		KeyHolder keyHolder = new GeneratedKeyHolder();//可用于保存主键
		named.update(sql, ps, keyHolder);
		int id = keyHolder.getKey().intValue();
		System.out.println("id="+id);
	}
	
	
	static User findUser2(User user) {
		// 需要执行的sql语句,:money和:id要对应User中的属性
		String sql = "select id, name, money, birthday  from user where money >:money and id <:id";
		// 传入sql需要执行的参数
		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
		// jdbcTemplate.queryForObject中的行映射new RowMapper()
		Object user1 = named.queryForObject(sql, ps, new BeanPropertyRowMapper<>(User.class));
		return (User) user1;
	}
	
	/**
	 * 可以用HashMap替换Jdbctemplate中的Object[]参数
	 * BeanPropertyRowMapper(User.class)，其中User.class使用到了反射机制
	 * @param name
	 * @return
	 */
	static User findUser(User user) {
		/*
		 * 
		 */
		// 需要执行的sql语句
		String sql = "select id, name, money, birthday  from user where money >:m and id <:id";
		// 传入sql需要执行的参数
		Map params = new HashMap<>();
		params.put("m", user.getMoney()); //map中存入的值为：key=sql中的:m value=对应user中的字段
		params.put("id", user.getId());
		Object user1 = named.queryForObject(sql, params, new BeanPropertyRowMapper<>(User.class));
		return (User) user1;
	}
}
