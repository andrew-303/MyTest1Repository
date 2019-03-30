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

	//JdbcTemplate()����Ҫ��������Դ�Ĳ���
	static JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
	public static void main(String[] args) {
		User user1 = findUser1("zhangsan");
		System.out.println("findUser1�õ���user="+user1);
		
		User user2 = findUser2("lisi");
		System.out.println("findUser2�õ���user="+user2);
		
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
	 * ����
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
	 * �޸�
	 */
	static int updateName(){
		String sql = "update user set name='updatedname' where id=8";
		return jdbcTemplate.update(sql);
	}
	
	/**
	 * JdbcTemplate ����map����
	 */
	static Map getData(int id){
		String sql = "select id,name,money,birthday from user where id = "+id;
		return jdbcTemplate.queryForMap(sql);
	}
	/**
	 * JdbcTemplate ����String���� 
	 */
	static String getUserName(int id){
		String sql = "select name from user where id="+id;
		String name = jdbcTemplate.queryForObject(sql, String.class);
		return name;		
	}
	
	
	/**
	 *JdbcTemplate����int���� 
	 */
	static int getCount(){
		String sql = "select count(*) from user";
		return jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * Jdbctempldate����List����
	 */
	@SuppressWarnings("unchecked")
	static List findUsers(int id){
		String sql = "select id,name,money,birthday from user where id <?";
		Object[] args = new Object[]{id};		
		List users = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(User.class));
		return users;
	}

	/**
	 * ��ѯ�û��ķ���
	 * @param name
	 * @return User
	 */
	static User findUser1(String name) {
		/*��ȡ�������ڳ�Ա������   JdbcTemplate()����Ҫ��������Դ�Ĳ���
		JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());*/
		//��Ҫִ�е�sql���
		String sql = "select id, name, money, birthday  from user where name=?";
		//����sql��Ҫִ�еĲ���
		Object[] args = new Object[] { name };
		//jdbcTemplate.queryForObject�е���ӳ��new RowMapper() ��Ҫʵ����ӿڵ�mapRow()������Ҳ����һ�ֻص��ķ�ʽ
		@SuppressWarnings("unchecked")
		Object user = jdbcTemplate.queryForObject(sql, args, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				//��Ҫ�Լ�ʵ��������Ȼ����ResultSet������еĽ��������ֵ
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
	 * ��findUser1�е���ӳ���ڵĴ���
	 * ��RowMapper�е�ʵ�ַ����滻Ϊnew BeanPropertyRowMapper(User.class)������User.classʹ�õ��˷������
	 * @param name
	 * @return
	 */
	static User findUser2(String name) {
		/*//JdbcTemplate()����Ҫ��������Դ�Ĳ���
		JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());*/
		//��Ҫִ�е�sql���
		String sql = "select id, name, money, birthday  from user where name=?";
		//����sql��Ҫִ�еĲ���
		Object[] args = new Object[] { name };
		//jdbcTemplate.queryForObject�е���ӳ��new RowMapper() ��Ҫʵ����ӿڵ�mapRow()������Ҳ����һ�ֻص��ķ�ʽ
		@SuppressWarnings("unchecked")
		Object user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper(User.class));
		return (User) user;
	}
}
