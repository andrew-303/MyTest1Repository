package cn.itcast.oa.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.domain.Department;

public class DepartmentUtils {

	/**
	 * 遍历部门数，得到所有的部门列表，并修改名称以表示层次
	 * @param topList
	 * @return
	 */
	public static List<Department> getAllDepartments(List<Department> topList) {
		List<Department> list = new ArrayList<Department>();	
		String prefix = "--";
		walkDepartmentTrees(topList,prefix,list);
		return list;	//把递归遍历后的集合返回给调用者
	}

	/**
	 * 递归遍历部门数，把遍历出来的部门都放在指定的list集合中
	 * @param list 
	 * 
	 */
	private static void walkDepartmentTrees(Collection<Department> topList,String prefix, List<Department> list){
		for(Department top : topList){
			//顶点
			Department copy = new Department();//原对象是在session中的对象，是持久化的不能直接使用，所以需要使用副本
			copy.setId(top.getId()); 
			copy.setName(prefix + top.getName());
			
			list.add(copy);
			
			//子树
			walkDepartmentTrees(top.getChildren(),"　　" + prefix , list);//全角的空格
		}
	
	}
}
