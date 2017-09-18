package cn.itcast.login.action;

import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	@Resource
	private UserService userService;
	private User user;
	private String loginResult;
	public String loginUI(){
		return "login";
	}
	//登录
	public String login(){
		if(user!=null){
			if(StringUtils.isNotBlank(user.getPassword())&&StringUtils.isNotBlank(user.getAccount())){
				//查询用户
				List<User> list=userService.findUserByAccountAndPass(user.getAccount(), user.getPassword());
				if(list!=null&&list.size()>0){
					User user=list.get(0);
					user.setUserRoles(userService.getUserRoleByUserId(user.getId()));
					//将user保存在session中
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					//记录的日志文件
					Log log = LogFactory.getLog(getClass());
					log.info("用户名称为："+user.getName()+"的用户登录了系统");
					//重定向到系统首页
					return "home";
				}else{
					loginResult="账号或密码不正确";
				}
			}else{
			loginResult="账号或密码不能为空";
			}
		}else{
			loginResult="请输入账号和密码";
		}
		return loginUI();
	}
	//退出
	public String logout(){
		//清除session中的数据
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
		return loginUI();
	}
	//跳转到没有权限登录页面
	public String NoPermissionUI(){
		return "NoPermissionUI";
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
}
