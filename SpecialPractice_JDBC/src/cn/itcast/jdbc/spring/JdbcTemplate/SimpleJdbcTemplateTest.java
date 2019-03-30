package cn.itcast.jdbc.spring.JdbcTemplate;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.jdbc.domain.User;

/**
 * SimpleJdbcTemplate有2个特性：
 * 1.泛型，可以直接返回想要的类型
 * 2.可变参数  Object...args
 * @author Administrator
 *
 */
public class SimpleJdbcTemplateTest {

	static SimpleJdbcTemplate simple = new SimpleJdbcTemplate(JdbcUtils.getDataSource());
	
	public static void main(String[] args) {
		User u = new User();
		//u.setName("zhangsan");
		System.out.println(find("zhangsan"));
		System.out.println(find("lisi"));
	}

	static User find(String name){
		String sql = "select id,name,money,birthday from user where name=? and money>?";
		User user = simple.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class), name,10.0f);
		return user;
	}
}
