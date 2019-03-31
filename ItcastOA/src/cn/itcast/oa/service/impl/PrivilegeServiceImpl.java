package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;

@Service
public class PrivilegeServiceImpl extends BaseDaoImpl<Privilege> implements PrivilegeService{


	@SuppressWarnings("unchecked")
	public List<Privilege> findTopList() {
		return getSession().createQuery(
				"FROM Privilege p WHERE p.parent is null"
				).list();
	}

	@SuppressWarnings("unchecked")
	public List<Privilege> getAllPrivilegeUrls() {
		return getSession().createQuery(
				"SELECT DISTINCT p.url FROM  Privilege p WHERE p.url IS NOT NULL"
				).list();
	}

}
