package cn.itcast.oa.view.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.utils.DepartmentUtils;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{
		

	private Long parentId;
	

	/** 列表 */
	public String list() throws Exception{
		List<Department> departmentList = null;
		if(parentId == null){ //如果上级ID为空，查询所有顶级部门的列表
			departmentList = departmentService.findTopList();
		}else{	//查询子部门列表
			departmentList = departmentService.findChildren(parentId);
			Department parent = departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		 
		//将查询到的结果存入map
		ActionContext.getContext().put("departmentList", departmentList);		
		return "list";
	}
	
	/** 删除*/
	public String delete() throws Exception{
		departmentService.delete(model.getId());
		return "tolist";
	}
	
	/** 添加页面 */
	public String addUI() throws Exception{
		//准备数据departmentList，显示为部门结构树，
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "addUI";
	}
	
	/** 添加*/
	public String add() throws Exception{
		// 得到参数，封装成对象  当使用实体做为Model时，也可以直接使用model
		model.setParent(departmentService.getById(parentId));//括号里面表示获得父节点的Department对象
		//保存到数据库中
		departmentService.save(model);
		return "tolist";
	}
	
	/** 修改页面 */
	public String editUI() throws Exception{
		//TODO 应该是部门结构树，先用所有部门列表代替
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		//在页面上表单上做数据回显
		Department department = departmentService.getById(model.getId());
		//放到栈顶
		ActionContext.getContext().getValueStack().push(department);
		//设置上级部门的回显：
		if(department.getParent() != null){
			parentId = department.getParent().getId();
		}
		return "editUI";
	}
	
	/** 修改 */
	public String edit() throws Exception{
		// 从数据库中取出原对象
		Department department = departmentService.getById(model.getId());
		
		//设置要修改的属性
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(departmentService.getById(parentId));//括号里面表示获得父节点的Department对象
		//更新到数据库
		departmentService.update(department);
		return "tolist";
	}
	
	
	//----------------------
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
