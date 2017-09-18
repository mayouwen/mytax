package cn.itcast.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.permission.PermissionCheck;
import cn.itcast.nsfw.user.entity.User;

public class LoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		String uri=request.getRequestURI();
		if(!uri.contains("sys/login_")){
			if(request.getSession().getAttribute(Constant.USER)!=null){
				//说明已经登录
				//判断是否访问纳税服务系统
				if(uri.contains("nsfw/")){
					//说明访问纳税服务系统
					User user=(User) request.getSession().getAttribute(Constant.USER);
					//获取spring容器
					WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					PermissionCheck pc=(PermissionCheck)applicationContext.getBean("permissionCheck");
					if(pc.isAccessible(user,"nsfw")){
						//说明权限放行
						chain.doFilter(request, response);
					}else{
						//没有权限，跳到没有权限页面
						response.sendRedirect(request.getContextPath()+"/sys/login_NoPermissionUI.action");
					}
				}else{
					chain.doFilter(request, response);
				}
			}else{
				response.sendRedirect(request.getContextPath()+"/sys/login_loginUI.action");
			}
		}else{
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
