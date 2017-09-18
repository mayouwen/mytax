package cn.itcast.nsfw.user.dao;

import java.io.Serializable;
import java.util.List;


import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserDao extends BaseDao<User> {
	//用户唯一性检查
	public List<User> findUserByAccountAndId(String id,String account);
	//保存用户和角色
	public void saveUserAndRole(UserRole userRole);
	//更新用户和角色
	public void deleteUserRoleByUserId(Serializable id);
	//根据用户id获取角色
	public List<UserRole> getUserRoleByUserId(Serializable id);
	//根据账号和密码查询用户
	public List<User> findUserByAccountAndPass(String account, String password);

}
