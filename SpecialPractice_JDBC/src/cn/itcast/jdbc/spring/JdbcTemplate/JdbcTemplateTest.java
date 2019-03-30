package cn.itcast.jdbc.spring.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mysql.jdbc.Statement;

import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.jdbc.domain.User;

public class JdbcTemplateTest {

	//JdbcTemplate()中需要传入数据源的参数
	static JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
	public static void main(String[] args) {
		User user1 = findUser1("zhangsan");
		System.out.println("findUser1得到的user="+user1);
		
		User user2 = findUser2("lisi");
		System.out.println("findUser2得到的user="+user2);
		
		System.out.println("users="+findUsers(3));
		System.out.println("user count="+getCount());
		System.out.println("user name="+getUserName(1));
		System.out.println("user data="+getData(3));
		System.out.println("updateName="+updateName());
		
		User u = new User();
		u.setName("1216test");
		u.setBirthday(new Date());
		u.setMoney((float) 1000.0);
		addUser(u);
	}
	
	/**
	 * 插入
	 */
	@SuppressWarnings("unchecked")
	static int addUser(final User user){
		jdbcTemplate.execute(new ConnectionCallback() {

			@Override
			public Object doInConnection(Connection con) throws SQLException, DataAccessException {
				String sql = "insert into user(name,birthday, money) values (?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);				
				ps.setString(1, user.getName());
				ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
				ps.setFloat(3, user.getMoney());
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()){
					user.setId(rs.getInt(1));
				}
				return null;
			}
		});
		return 0;
	}
	/**
	 * 修改
	 */
	static int updateName(){
		String sql = "update user set name='updatedname' where id=8";
		return jdbcTemplate.update(sql);
	}
	
	/**
	 * JdbcTemplate 返回map类型
	 */
	static Map getData(int id){
		String sql = "select id,name,money,birthday from user where id = "+id;
		return jdbcTemplate.queryForMap(sql);
	}
	/**
	 * JdbcTemplate 返回String类型 
	 */
	static String getUserName(int id){
		String sql = "select name from user where id="+id;
		String name = jdbcTemplate.queryForObject(sql, String.class);
		return name;		
	}
	
	
	/**
	 *JdbcTemplate返回int类型 
	 */
	static int getCount(){
		String sql = "select count(*) from user";
		return jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * Jdbctempldate返回List类型
	 */
	@SuppressWarnings("unchecked")
	static List findUsers(int id){
		String sql = "select id,name,money,birthday from user where id <?";
		Object[] args = new Object[]{id};		
		List users = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(User.class));
		return users;
	}

	/**
	 * 查询用户的方法
	 * @param name
	 * @return User
	 */
	static User findUser1(String name) {
		/*抽取出来放在成员变量中   JdbcTemplate()中需要传入数据源的参数
		JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());*/
		//需要执行的sql语句
		String sql = "select id, name, money, birthday  from user where name=?";
		//传入sql需要执行的参数
		Object[] args = new Object[] { name };
		//jdbcTemplate.queryForObject中的行映射new RowMapper() 需要实现其接口的mapRow()方法；也属于一种回调的方式
		@SuppressWarnings("unchecked")
		Object user = jdbcTemplate.queryForObject(sql, args, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				//需要自己实例化对象，然后用ResultSet结果集中的结果给对象赋值
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setMoney(rs.getFloat("money"));
				user.setBirthday(rs.getDate("birthday"));
				return user;
			}});
		return (User) user;
	}
	/**
	 * 简化findUser1中的行映射内的处理
	 * 将RowMapper中的实现方法替换为new BeanPropertyRowMapper(User.class)，其中User.class使用到了反射机制
	 * @param name
	 * @return
	 */
	static User findUser2(String name) {
		/*//JdbcTemplate()中需要传入数据源的参数
		JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());*/
		//需要执行的sql语句
		String sql = "select id, name, money, birthday  from user where name=?";
		//传入sql需要执行的参数
		Object[] args = new Object[] { name };
		//jdbcTemplate.queryForObject中的行映射new RowMapper() 需要实现其接口的mapRow()方法；也属于一种回调的方式
		@SuppressWarnings("unchecked")
		Object user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper(User.class));
		return (User) user;
	}
}
