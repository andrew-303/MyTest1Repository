package cn.itcast.oa.utils;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.oa.base.BaseDao;
import cn.itcast.oa.domain.PageBean;

/**
 * 用于辅助拼接生成HQL语句的工具类
 * @author Administrator
 *
 */
public class HqlHelper {

	private String fromClause; // From子句，必须
	private String whereClause = ""; // Where子句，可选
	private String orderByClause = ""; // OrderBy子句，可选
	
	private List<Object> parameters = new ArrayList<Object>();  //参数列表 
	/**
	 * 生成From子句，默认的别名为'o'
	 */
	public HqlHelper(Class clazz){
		this.fromClause = "From " + clazz.getSimpleName() + " o ";
	}
	/**
	 * 生成From子句，使用指定的别名为
	 */
	public HqlHelper(Class clazz ,String alias){
		this.fromClause = "From " + clazz.getSimpleName() + " " + alias;
	}
	
	/**
	 * 拼接Where子句
	 */
	public HqlHelper addCondition(String condition,Object... params){
		
		//拼接
		if(whereClause.length() == 0){ //如果where子句长度为0，说明之前没有where语句
			whereClause = " WHERE " + condition;			
		}else{						 //如果where子句长度不为0，说明之前有where语句，后面需要加and语句
			whereClause += " AND " + condition;
		}
		//保存参数
		if(params != null && params.length >0){
			for(Object obj : params){
				parameters.add(obj);
			}
		}
		return this;
	}
	
	/**
	 * 如果第1个参数为true，则拼接Where子句
	 * 
	 * @param append
	 * @param condition
	 * @param params
	 */
	public HqlHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}
	
	/**
	 * 拼接OrderBy子句
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(String propertyName,boolean isASC){
		if(orderByClause.length() == 0){
			orderByClause = " ORDER BY " + propertyName + (isASC ? " ASC" : " DESC");
		}else{
			orderByClause += ", " + propertyName + (isASC ? " ASC" : " DESC");
		}
		return this;
	}
	
	/**
	 * 如果第1个参数为true，则拼接OrderBy子句
	 * 
	 * @param append
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(boolean append, String propertyName, boolean isAsc) {
		if (append) {
			addOrder(propertyName, isAsc);
		}
		return this;
	}
	
	/**
	 * 获取生成的查询数据列表的HQL语句
	 * @return
	 */
	public String getQueryListHql(){
		return fromClause + whereClause + orderByClause;
	}
	
	/**
	 * 获取生成的查询总记录数的HQL语句（没有OrderBy子句）
	 * 
	 * @return
	 */
	public String getQueryCountHql(){
		return "select count(*) " + fromClause + whereClause;
	}
	/**
	 * 获取参数列表，与HQL过滤条件中的'?'一一对应
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}
	
	/**
	 * 查询并准备分页信息（放到栈顶）
	 * 
	 * @param pageNum
	 * @param service
	 * @return
	 */
	public HqlHelper buildPageBeanForStruts2(int pageNum, BaseDao<?> service) {
		System.out.println("===> HqlHelper.buildPageBeanForStruts2()");
		
		PageBean pageBean = service.getPageBean(pageNum, this);
		ActionContext.getContext().getValueStack().push(pageBean);
		return this;
	}
}
