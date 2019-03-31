package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{

	//给页面传递部门ID时使用
	private Long departmentId;
	private Long[] roleIds;
	
	/*
	 * 列表
	 */
	public String list(){
		List<User> userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		return "list";
	}
	/*
	 * 删除
	 */
	public String delete(){
		userService.delete(model.getId());
		return "tolist";
	}
	/*
	 * 添加页面
	 */
	public String addUI(){
		//准备数据:departmentList
		//TODO 应该是部门结构树，先用所有部门列表代替
		List<Department> departmentList = departmentService.findAll();
		ActionContext.getContext().put("departmentList", departmentList);
		
		//准备数据:roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		
		return "saveUI";
	}
	/*
	 * 添加
	 */
	public String add(){
		// 1，新建对象并设置属性（也可以使用model）
		//由于Department和Role没有被model封装，所以需要在这里赋值
		Department department = departmentService.getById(departmentId);
		model.setDepartment(department);
		
		List<Role> roleList = roleService.getByIds(roleIds);
		Set<Role> roles = new HashSet<Role>(roleList);
		model.setRoles(roles);
		// 2，保存到数据库
		userService.save(model);		
		return "tolist";
	}
	
	/*
	 * 修改页面
	 */
	
	/*
	 * 修改
	 */
	
	/*
	 * 登录页面
	 */
	public String loginUI(){
		
		return "loginUI";
	}
	
	/*
	 * 登录
	 */
	public String login(){
		//验证用户名和密码对不对，根据传入的用户名和密码进行校验
		User  user = userService.findByLoginNameAndPassword(model.getLoginName(),model.getPassword());
		
		if(user == null){
			//用户名或密码错误
			addFieldError("login", "用户名或密码错误");
			return "loginUI";
		}else{
			//用户名密码正确，则进行登录
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
		
	}
	
	/*
	 * 退出
	 */
	public String logout(){
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}
	//------------------------
	
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
