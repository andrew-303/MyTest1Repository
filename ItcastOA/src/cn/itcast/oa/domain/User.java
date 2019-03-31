package cn.itcast.oa.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * ʵ�壬�û�
 * @author Administrator
 *
 */
public class User {
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();
	
	
	private String loginName;	//��¼��		
	private String name;		//��ʵ����
	private String password;	//����
	private String gender;		//�Ա�
	private String phoneNumber;		//�绰����
	private String email;		//�����ʼ�
	private String description;	//˵��
	
	/*
	 * 判断本用户是否有指定名称的权限
	 */
	public boolean hasPrivilegeByName(String privilegeName){
		//超级管理员的权限
		if(isAdmin()){
			return true;
		}
		
		//其他用户要是有权限才返回true
		for(Role role : roles){	//先循环判断角色，因为权限是在角色里面
			for(Privilege privilege : role.getPrivileges()){  //再判断权限
				if(privilege.getName().equals(privilegeName)){
					return true;
				}				
			}
		}return false; //全部循环完成，没有找到，则返回false
	}
	
	/*
	 * 判断本用户是否有指定Url的权限
	 */
	public boolean hasPrivilegeByUrl(String privilegeUrl){
		//超级管理员的权限
		if(isAdmin()){
			return true;
		}
		//如果以UI后缀结尾，需要去掉UI后缀，以得到对应的权限（例如：addUI和add是同一个权限）
		if(privilegeUrl.endsWith("UI")){
			privilegeUrl = privilegeUrl.substring(0, privilegeUrl.length() - 2);
		}
		
		//其他用户要是有权限才返回true
		List<String> allPrivilegeUrls = (List<String>) ActionContext.getContext().getApplication().get("allPrivilegeUrls");
		//从最大的作用域中取出我们要查询的权限，最大的作用域在监听器中
		
		
		if(!allPrivilegeUrls.contains(privilegeUrl)){//如果当前url不在所有的权限url中的时候，说明不需要权限控制
			//如果是不需要控制的功能，则所有用户都可以使用
			return true;
		}else{
			//试过是需要控制的功能，则有权限的用户才可以使用
			for(Role role : roles){	//先循环判断角色，因为权限是在角色里面
				for(Privilege privilege : role.getPrivileges()){  //再判断权限
					if(privilegeUrl.equals(privilege.getUrl())){
						return true;
					}				
				}
			}return false; //全部循环完成，没有找到，则返回false		
		}
	}
	
	/*
	 * 判断是否是管理员
	 */
	private boolean isAdmin() {
		return "admin".equals(loginName);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
