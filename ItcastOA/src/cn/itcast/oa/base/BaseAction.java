package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.ReplyService;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.service.TopicService;
import cn.itcast.oa.service.UserService;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	//	将原来在Action中注入的Service内容抽取出来,注入所有的Service
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;	
	@Resource
	protected UserService userService;
	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected ForumService forumService;
	@Resource
	protected TopicService topicService;
	@Resource
	protected ReplyService replyService;
	
	
	//将原来在Action中的模型驱动抽取出来，但是这里就不能直接通过new进行初始化了
	protected T model ; 
	
	public BaseAction(){		
		try {
			// 得到model的类型信息
			ParameterizedType pt =(ParameterizedType) this.getClass().getGenericSuperclass();
			Class clazz = (Class)pt.getActualTypeArguments()[0];
			// 通过反射生成model的实例
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	//模型驱动
	public T getModel() {				
		return model;
	}
	
	/**
	 * 获取当前登录的用户
	 */
	public User getCurrentUser(){
		return (User) ActionContext.getContext().getSession().get("user");
	}
	
	//页码默认为第一页
	protected int pageNum =1;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
