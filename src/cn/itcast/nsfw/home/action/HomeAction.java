package cn.itcast.nsfw.home.action;

import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {
	//跳转到服务系统首页
	public String frame(){
		return "frame";
	}
	//跳转到纳税服务系统顶部
	public String top(){
		return "top";
	}
	//跳转到纳税服务系统左侧
	public String left(){
		return "left";
	}
}
