package cn.itcast.nsfw.role.action;

import java.util.HashSet;
import java.util.List;
import javax.annotation.Resource;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivilege;
import cn.itcast.nsfw.role.entity.RolePrivilegeId;
import cn.itcast.nsfw.role.service.RoleService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RoleAction extends ActionSupport {
	@Resource
	private RoleService roleService;
	private List<Role> roleList;
	private Role role;
	private String[] privilegeIds;
	private String[] selectedRow;
	
	//列表页面
	public String listUI(){
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		roleList=roleService.findAll();
		return "listUI";
	}
	//跳转新增页面
	public String addUI(){
		//加载权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			if(role!=null){
				//处理权限
				if(privilegeIds!=null){
					HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
					for(int i=0;i<privilegeIds.length;i++){
						set.add(new RolePrivilege(new RolePrivilegeId(role,privilegeIds[i])));
				}
					role.setRolePrivileges(set);
				}
				roleService.save(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//跳转编辑页面
	public String editUI(){
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		if(role!=null&&role.getRoleId()!=null){
			role=roleService.findById(role.getRoleId());
			//处理权限回显
			if(role.getRolePrivileges()!=null){
				privilegeIds=new String[role.getRolePrivileges().size()];
				int i=0;
				for(RolePrivilege rp:role.getRolePrivileges()){
					privilegeIds[i++]=rp.getId().getCode();
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(role!=null){
				//处理权限
				if(privilegeIds!=null){
					HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
					for(int i=0;i<privilegeIds.length;i++){
						set.add(new RolePrivilege(new RolePrivilegeId(role,privilegeIds[i])));
				}
					role.setRolePrivileges(set);
				}
				roleService.update(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		if(role!=null&&role.getRoleId()!=null){
			roleService.delete(role.getRoleId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow!=null){
			for(String id:selectedRow){
				roleService.delete(id);
			}
		}
		return "list";
	}
	public String[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	
}
