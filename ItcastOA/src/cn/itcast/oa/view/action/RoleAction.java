package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

//@Controller 和 @Scope 注解是不能被子类继承的，所以要在自己的类中声明 
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

	private Long[] privilegeIds;
	
	

	/** 列表 */
	public String list() throws Exception{
		List<Role> roleList = roleService.findAll(); 
		ActionContext.getContext().put("roleList", roleList);
		return "list";
	}
	
	/** 删除*/
	public String delete() throws Exception{
		roleService.delete(model.getId());
		return "tolist";
	}
	
	/** 添加页面 */
	public String addUI() throws Exception{
		return "addUI";
	}
	
	/** 添加*/
	public String add() throws Exception{
		// 得到参数，封装成对象  当使用实体做为Model时，也可以直接使用model
		/*Role role = new Role();
		role.setName(name);
		role.setDescription(description);*/
		//保存到数据库中
		roleService.save(model);
		return "tolist";
	}
	
	/** 修改页面 */
	public String editUI() throws Exception{
		Role role = roleService.getById(model.getId());
		//在页面上表单上做数据回显
/*		this.name = role.getName();
		this.description = role.getDescription();*/
		
		//放到栈顶
		ActionContext.getContext().getValueStack().push(role);
	
		return "editUI";
	}
	
	/** 修改 */
	public String edit() throws Exception{
		// 从数据库中取出原对象
		Role role = roleService.getById(model.getId());
		
		//设置要修改的属性
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		
		//更新到数据库
		roleService.update(role);
		return "tolist";
	}
	
	// -----------------
	/** 修改权限页面 */
	public String setPrivilegeUI() throws Exception{
		//准备数据,意思是设置哪个岗位的权限
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().put("role", role);	//放到map中
				
		List<Privilege> topPrivilegeList = privilegeService.findTopList();
		ActionContext.getContext().put("topPrivilegeList", topPrivilegeList);
		
		//准备回显数据
		privilegeIds = new Long[role.getPrivileges().size()];
		int index = 0;
		for(Privilege privilege : role.getPrivileges()){
			privilegeIds[index++] = privilege.getId();
		}
		
		return "setPrivilegeUI";
	}
	
	/** 修改权限*/
	public String setPrivilege() throws Exception{
		// 从数据库中取出原对象
		Role role = roleService.getById(model.getId());
		
		//设置要修改的属性
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);//得到传过来的ID，然后把他们关联起来再存起来
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		
		//更新到数据库
		roleService.update(role);
		return "tolist";
	}

	//---------------------------------------
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
}
