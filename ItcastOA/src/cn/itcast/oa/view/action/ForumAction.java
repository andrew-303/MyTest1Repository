package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.utils.HqlHelper;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum>{


	/**
	 * 0 表示全部主题 <br>
	 * 1 表示只看精华帖
	 */
	private int viewType = 0;

	/**
	 * 0 代表默认排序(所有置顶帖在前面，并按最后更新时间降序排列)<br>
	 * 1 代表只按最后更新时间排序<br>
	 * 2 代表只按主题发表时间排序<br>
	 * 3 代表只按回复数量排序
	 */
	private int orderBy = 0;

	/**
	 * true 表示升序<br>
	 * false 表示降序
	 */
	private boolean asc = false;

	
	/**
	 * 论坛板块列表
	 * @return
	 */
	public String list(){
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	/**
	 * 显示单个板块(主题列表)
	 * @return
	 */
	public String show(){
		//准备数据：Forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		
		/*//准备数据:topicList
		List<Topic> topicList = topicService.findByForum(forum);
		ActionContext.getContext().put("topicList", topicList);*/
		
		//准备数据：主题列表的分页信息
		/*PageBean pageBean = topicService.getPageBean(pageNum,forum);
		ActionContext.getContext().getValueStack().push(pageBean); //把pageBean放到值栈中
		*/
		// 最终版：
		// 构建查询条件
		new HqlHelper(Topic.class,"t")
		.addCondition("t.forum=? ", forum)
		.addCondition(viewType==1, "t.type=? ",Topic.TYPE_BEST)//1代表只看精华帖
		.addOrder(orderBy == 1, "t.lastUpdateTime",asc) //1代表只按最后更新时间排序
		.addOrder(orderBy == 2, "t.postTime", asc) // 2 代表只按主题发表时间排序
		.addOrder(orderBy == 3, "t.replyCount", asc) // 3 代表只按回复数量排序
		.addOrder(orderBy == 0, "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)	// 0 代表默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
		.addOrder(orderBy == 0, "t.lastUpdateTime", false)
		.buildPageBeanForStruts2(pageNum, replyService);
		return "show";
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
	
}
