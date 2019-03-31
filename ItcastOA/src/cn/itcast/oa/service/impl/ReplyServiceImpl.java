package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.cfg.Configuration;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.ReplyService;

@Service
@SuppressWarnings("unchecked")
public class ReplyServiceImpl extends BaseDaoImpl<Reply> implements ReplyService{
	
	public List<Reply> findByTopic(Topic topic) {
		return getSession().createQuery(
				// 排序：最前面的是最早发表的回帖
				"FROM Reply r WHERE r.topic = ? ORDER BY r.postTime ASC")
				.setParameter(0, topic)
				.list();
	}

	@Override
	public void save(Reply reply) {
		//保存到DB
		getSession().save(reply);
		
		//维护相关信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();
		
		forum.setArticleCount(forum.getArticleCount()+1);//板块的文章数
		topic.setReplyCount(topic.getReplyCount()+1);//主题的回复数
		topic.setLastReply(reply);//主题最后的发表回复
		topic.setLastUpdateTime(reply.getPostTime());//主题最后的更新时间
		
		getSession().update(topic);
		getSession().update(forum);
		
	}

	
	@Override
	public PageBean getPageBean(int pageNum, Topic topic) {
		int pageSize = Configuration.getPageSize();
		
		// 查询本页的数据列表
		List list = getSession().createQuery(
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")
				.setParameter(0, topic)
				.setFirstResult((pageNum -1) * pageSize)
				.setMaxResults(pageSize)
				.list(); 
		
		// 查询总记录数
		Long count = (Long)getSession().createQuery(
				"SELECT COUNT(*) FROM Reply r WHERE r.topic=?")
				.setParameter(0, topic)
				.uniqueResult();
		
		
		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	
}
