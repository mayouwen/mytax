package cn.itcast.nsfw.user.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserService extends BaseService<User>  {
	//导出到excel
	void exportExcel(List<User> userList, ServletOutputStream outputStream);
	//导入excel
	void importExcel(File userExcel, String userExcelFileName);
	//账号唯一性检查
	List<User> findUserByAccountAndId(String id,String account );
	//保存用户和角色
	void saveUserAndRole(User user, String... roleIds);
	//更新用户和角色
	void updateUserAndRole(User user, String... roleIds);
	//根据用户id获取角色
	List<UserRole> getUserRoleByUserId(Serializable id);
	//根据账号和密码查询用户
	List<User> findUserByAccountAndPass(String account, String password);
}
