package cn.itcast.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.service.RoleService;

@Service
public class RoleServiceImpl extends BaseDaoImpl<Role> implements RoleService{
	
}
