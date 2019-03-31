package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.service.ForumService;

@Service
public class ForumServiceImpl extends BaseDaoImpl<Forum> implements ForumService{

	@Override//重写findAll方法
	public List<Forum> findAll() {		
		return getSession().createQuery(
				"From Forum f order by  f.position asc")
				.list();
	}
	
	@Override//重写save方法
	public void save(Forum forum) {
		// 保存到DB，会生成Id的值
		getSession().save(forum);
		// 指定position的值为最大 
		forum.setPosition(forum.getId().intValue());

		// 因为是持久化状态，所以不需要调用update()方法。
		
	}

	public void moveUp(Long id) {
		// 获取要交换的两个Forum
		Forum forum = getById(id);	//当前操作的Forum
		Forum other = (Forum) getSession().createQuery(//当前行的上面的那个Forum
				"FROM Forum f WHERE f.position < ? ORDER BY  f.position DESC")
				.setParameter(0, forum.getPosition()) //上面?对应的参数
				.setFirstResult(0).setMaxResults(1)		//相当于MySQL中的limit 0,1的语法
				.uniqueResult();	
		
		// 最上面的不能上移,即如果上面的forum=空就直接返回，什么都不做。
		if(other == null){
			return;
		}

		// 交换position的值
		int temp = forum.getPosition();	//设定一个中间变量
		forum.setPosition(other.getPosition());
		other.setPosition(temp);

		// 更新到数据库中
		// 因为是持久化状态，所以不需要调用update()方法。
	}

	
	public void moveDown(Long id) {
		// 获取要交换的两个Forum
				Forum forum = getById(id);	//当前操作的Forum
				Forum other = (Forum) getSession().createQuery(//当前行的下面的那个Forum
						"FROM Forum f WHERE f.position > ? ORDER BY  f.position ASC")
						.setParameter(0, forum.getPosition()) //上面?对应的参数
						.setFirstResult(0).setMaxResults(1)		//相当于MySQL中的limit 0,1的语法
						.uniqueResult();	
				
				// 最上面的不能上移,即如果上面的forum=空就直接返回，什么都不做。
				if(other == null){
					return;
				}

				// 交换position的值
				int temp = forum.getPosition();	//设定一个中间变量
				forum.setPosition(other.getPosition());
				other.setPosition(temp);

				// 更新到数据库中
				// 因为是持久化状态，所以不需要调用update()方法。
		
	}
	

	
}
