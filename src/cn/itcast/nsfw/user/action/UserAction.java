package cn.itcast.nsfw.user.action;
import java.io.File;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;
	private List<User> userList;
	private User user;
	private String[] selectedRow;
	private File headImg;
	private String headImgFileName;
	private String headImgContentType;
	
	private File userExcel;
	private String userExcelContentType;
	private String userExcelFileName;
	private String[] roleIds; 
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	//列表页面
	public String listUI(){
		userList=userService.findAll();
		return "listUI";
	}
	//跳转新增页面
	public String addUI(){
		//添加角色列表
		ActionContext.getContext().getContextMap().put("rolelist", roleService.findAll());
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			if(user != null){
				//处理头像
				if(headImg!=null){
					//保存到upload
					//获取保存文件的绝对地址
					String filepath=ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName=UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf('.'));
					FileUtils.copyFile(headImg, new File(filepath, fileName));
					//设置用户头像路径
					user.setHeadImg("user/"+fileName);
				}
				userService.saveUserAndRole(user,roleIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//跳转编辑页面
	public String editUI(){
		ActionContext.getContext().getContextMap().put("rolelist", roleService.findAll());
		if(user!=null&&user.getId()!=null){
			user=userService.findById(user.getId());
			//处理角色
			List<UserRole> list=userService.getUserRoleByUserId(user.getId());
			if(list!=null&&list.size()>0){
				roleIds=new String[list.size()];
				for(int i=0;i<list.size();i++){
					roleIds[i]=list.get(i).getId().getRole().getRoleId();
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(user!=null){
				//处理头像
				if(headImg!=null){
					//保存到upload
					//获取保存文件的绝对地址
					String filepath=ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName=UUID.randomUUID().toString()+headImgFileName.substring(headImgFileName.lastIndexOf('.'));
					FileUtils.copyFile(headImg, new File(filepath, fileName));
					//设置用户头像路径
					user.setHeadImg("user/"+fileName);
				}
				userService.updateUserAndRole(user,roleIds);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		userService.delete(user.getId());
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow!=null){
			for(String id:selectedRow){
				userService.delete(id);
			}
		}
		return "list";
	}
	//导出到excel
	public void exportExcel(){
		try {
			//查找用户列表
			userList=userService.findAll();
			//导出
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachment;filename="+new String("用户列表.xls".getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			userService.exportExcel(userList,outputStream);
			if(outputStream!=null){
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//导入excel
	public String importExcel(){
		if(userExcel!=null){
			//判断是否是excel
			if(userExcelFileName.matches("^.+\\.(?i)((xls)||(xlsx))$")){
				userService.importExcel(userExcel,userExcelFileName);
			}
		}
		return "list";
	}
	//账户唯一性检查
	public void verifyAccount(){
		try {
			//获取账户
			if(user!=null&&StringUtils.isNotBlank(user.getAccount())){
				List<User> list=userService.findUserByAccountAndId(user.getId(),user.getAccount());
				String result="true";
				if(list!=null&&list.size()>0){
					//说明账户已经存在
					result="false";
				}
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(result.getBytes());
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	public File getUserExcel() {
		return userExcel;
	}
	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}
	public String getUserExcelContentType() {
		return userExcelContentType;
	}
	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}
	public String getUserExcelFileName() {
		return userExcelFileName;
	}
	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
