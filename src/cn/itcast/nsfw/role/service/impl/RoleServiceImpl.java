package cn.itcast.nsfw.role.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.service.RoleService;
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	private RoleDao roleDao;
	@Resource
	public void setRoleDao(RoleDao roleDao) {
		super.setBasedao(roleDao);
		this.roleDao = roleDao;
	}

	public void update(Role role) {
		//更新之前删除原来的权限
		roleDao.deletePrivilegeByRoleId(role.getRoleId());
		roleDao.update(role);
	}

}
