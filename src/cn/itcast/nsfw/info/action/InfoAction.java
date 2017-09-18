package cn.itcast.nsfw.info.action;



import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;


import cn.itcast.core.util.PageBean;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class InfoAction extends ActionSupport {
	@Resource
	private InfoService infoService;
	private List<Info> infoList;
	private Info info;
	private String[] selectedRow;
	private String strTitle;
	private PageBean<Info> pageBean;
	private int currentPage;
	private int pageCount;
	//列表页面
	public String listUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap",info.INFO_TYPE_MAP);
		QueryHelper queryHelper = new QueryHelper(Info.class, "i");
		String hql=queryHelper.getQueryListHql();
		try {
			if(info!=null){
				if(StringUtils.isNotBlank(info.getTitle())){
					info.setTitle(URLDecoder.decode(info.getTitle(), "utf-8"));
					queryHelper.addCondition("i.title like ?", "%"+info.getTitle()+"%");
				}
			}
					queryHelper.addOrderBy("i.createTime", queryHelper.ORDER_BY_DESC);
					pageBean=infoService.findAll(queryHelper,getCurrentPage(),getPageCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listUI";
	}
	//跳转新增页面
	public String addUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap",info.INFO_TYPE_MAP);
		info=new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			if(info != null){
				infoService.save(info);
			}
			info=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//跳转编辑页面
	public String editUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap",info.INFO_TYPE_MAP);
		if(info!=null&&info.getInfoId()!=null){
			//解决查询条件覆盖的问题
			strTitle = info.getTitle();
			info=infoService.findById(info.getInfoId());
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(info!=null){
				infoService.update(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		strTitle = info.getTitle();
		infoService.delete(info.getInfoId());
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow!=null){
			for(String id:selectedRow){
				infoService.delete(id);
			}
		}
		return "list";
	}
	//异步发布信息
	public void publicInfo(){
		try {
			if(info!=null){
				//更新信息
				Info temp=infoService.findById(info.getInfoId());
				temp.setState(info.getState());
				infoService.update(temp);
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write("更新状态成功".getBytes("utf-8"));
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public InfoService getInfoService() {
		return infoService;
	}
	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}
	public List<Info> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public PageBean<Info> getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean<Info> pageBean) {
		this.pageBean = pageBean;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
