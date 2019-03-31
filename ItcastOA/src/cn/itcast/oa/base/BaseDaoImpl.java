package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.cfg.Configuration;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.utils.HqlHelper;

//@Transactional注解可以被子类继承，即对子类也有效
@Transactional
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements BaseDao<T> {
	//ע��SessionFactory
	@Resource
	private SessionFactory sessionFactory;
	protected Class<T> clazz;//����һ�����⣬��û�н����
	
	//���캯��
	public BaseDaoImpl(){
		// ͨ����õ�T����ʵ����
		ParameterizedType pt =  (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
		
		System.out.println("clazz = " + clazz.getName());
	}
	
	public void save(T entity) {
		getSession().save(entity);
	}

	public void update(T entity) {
		getSession().update(entity);		
	}

	public void delete(Long id) {
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
	}
	
	public T getById(Long id) {
		if(id == null){
			return  null;
		}
		return (T) getSession().get(clazz, id);
	}


	public List<T> getByIds(Long[] ids) {
		if(ids == null || ids.length== 0){
			return Collections.EMPTY_LIST;
		}
		
		return getSession().createQuery(
				"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")
				.setParameterList("ids", ids)
				.list();
	}

	public List<T> findAll() {
		return getSession().createQuery(
				"FROM " + clazz.getSimpleName()).list();
	}
	
	/**
	 * ��ȡ��ǰ���õ�Session
	 * 
	 * @return
	 */
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	// 最终版
	public PageBean getPageBean(int pageNum, HqlHelper hqlHelper) {
		System.out.println("--------------> BaseDaoImpl.getPageBean( int pageNum, HqlHelper hqlHelper )");
		int pageSize = Configuration.getPageSize();
		List<Object> parameters = hqlHelper.getParameters();
		
		// 查询本页的数据列表
		System.out.println(hqlHelper.getQueryListHql());
		Query listQuery = getSession().createQuery(hqlHelper.getQueryListHql());
		if(parameters !=null && parameters.size()>0){ //设置参数
			for(int i=0 ; i< parameters.size() ;i++){
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum-1)*pageSize );
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list();//执行查询
		
		// 查询总记录数
		Query countQuery = getSession().createQuery(hqlHelper.getQueryCountHql());
		if(parameters != null && parameters.size()>0){
			for(int i=0;i<parameters.size();i++){
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		
		Long count = (Long)countQuery.uniqueResult();//执行查询
		
		return new PageBean(pageNum, pageSize, list, count.intValue());
	}
}
