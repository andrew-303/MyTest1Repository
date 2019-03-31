package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

public interface ReplyService extends BaseDao<Reply>{

	/**
	 * 查询指定主题中所有的回复列表，排序按照发表时间正序排列
	 * @param topic
	 * @return
	 */
	@Deprecated
	List<Reply> findByTopic(Topic topic);

	/**
	 * 获取分页信息
	 * @param pageNum
	 * @param topic
	 * @return
	 */
	PageBean getPageBean(int pageNum, Topic topic);

}
