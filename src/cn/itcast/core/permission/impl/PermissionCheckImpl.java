package cn.itcast.core.permission.impl;

import java.util.List;

import javax.annotation.Resource;
import cn.itcast.core.permission.PermissionCheck;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

public class PermissionCheckImpl implements PermissionCheck {
	@Resource
	private UserService userService;
	public boolean isAccessible(User user, String code) {
		//获取用户角色
		List<UserRole> list = user.getUserRoles();
		if(list==null){
			list = userService.getUserRoleByUserId(user.getId());
		}
		//根据每个角色的权限进行比较
		if(list!=null&&list.size()>0){
			for(UserRole ur:list){
				Role role=ur.getId().getRole();
				for(RolePrivilege rp:role.getRolePrivileges()){
					if(code.equals(rp.getId().getCode())){
						return true;
					}
				}
			}
		}
		return false;
	}

}
