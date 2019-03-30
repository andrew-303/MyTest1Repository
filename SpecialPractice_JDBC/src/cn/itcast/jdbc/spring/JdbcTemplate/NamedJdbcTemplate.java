package cn.itcast.jdbc.spring.JdbcTemplate;
/**
 * ʹ��֧������������JdbcTempldate
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
		KeyHolder keyHolder = new GeneratedKeyHolder();//�����ڱ�������
		named.update(sql, ps, keyHolder);
		int id = keyHolder.getKey().intValue();
		System.out.println("id="+id);
	}
	
	
	static User findUser2(User user) {
		// ��Ҫִ�е�sql���,:money��:idҪ��ӦUser�е�����
		String sql = "select id, name, money, birthday  from user where money >:money and id <:id";
		// ����sql��Ҫִ�еĲ���
		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
		// jdbcTemplate.queryForObject�е���ӳ��new RowMapper()
		Object user1 = named.queryForObject(sql, ps, new BeanPropertyRowMapper<>(User.class));
		return (User) user1;
	}
	
	/**
	 * ������HashMap�滻Jdbctemplate�е�Object[]����
	 * BeanPropertyRowMapper(User.class)������User.classʹ�õ��˷������
	 * @param name
	 * @return
	 */
	static User findUser(User user) {
		/*
		 * 
		 */
		// ��Ҫִ�е�sql���
		String sql = "select id, name, money, birthday  from user where money >:m and id <:id";
		// ����sql��Ҫִ�еĲ���
		Map params = new HashMap<>();
		params.put("m", user.getMoney()); //map�д����ֵΪ��key=sql�е�:m value=��Ӧuser�е��ֶ�
		params.put("id", user.getId());
		Object user1 = named.queryForObject(sql, params, new BeanPropertyRowMapper<>(User.class));
		return (User) user1;
	}
}
