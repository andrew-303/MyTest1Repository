package cn.itcast.oa.view.action;

import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.ReplyService;

@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply>{

	private Long topicId;
	
	/** 发表新回复页面 */
	public String addUI(){
		//准备数据topic
		Topic topic = topicService.getById(topicId); 
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}
	
	/** 发表新回复 */
	public String add(){
		//1.封装(已经封装了title content faceIcon)
		model.setTopic(topicService.getById(topicId));
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		
		//2.保存
		replyService.save(model);
		return "toTopicShow";	//转到新回复所属主题的显示页面
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
		
}
