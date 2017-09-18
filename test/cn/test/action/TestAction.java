package cn.test.action;

import javax.annotation.Resource;

import cn.test.service.impl.TestService;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport {
	@Resource
	TestService testService;
	@Override
	public String execute() throws Exception {
		testService.say();
		return SUCCESS;
	}
}
