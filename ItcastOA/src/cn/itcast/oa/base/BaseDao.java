package cn.itcast.oa.base;

import java.util.List;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.utils.HqlHelper;


public interface BaseDao<T> {

	/**
	 * ����ʵ��
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * ɾ��ʵ��
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * ����ʵ��
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * ��ѯʵ��
	 *  
	 * @param id  如果ID==null, 则不返回对象
	 * @return
	 */
	T getById(Long id);

	/**
	 * ��ѯʵ��
	 * 
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);

	/**
	 * ��ѯ����
	 * 
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param pageNum
	 * @param hqlHelper
	 * 查询条件（HQL语句与参数列表）
	 * @return
	 */
	PageBean getPageBean(int pageNum, HqlHelper hqlHelper);
}
