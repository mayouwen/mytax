package cn.itcast.core.util;

import java.util.List;

/**
 * 封装分页的参数
 * @author mayouwen
 *
 */
public class PageBean<T> {
	private int currentPage=1;//当前页
	private int pageCount=2;//查询返回的行数，每页的行数
	private long totalCount;//总记录数
	private int totalPage;//总页数
	private List<T> pageData;//分页查询到的数据
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
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	//获取总页数
	public int getTotalPage() {
		if(totalCount%pageCount==0){
			totalPage=(int) (totalCount/pageCount);
		}else{
			totalPage=(int) (totalCount/pageCount+1);
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getPageData() {
		return pageData;
	}
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	public PageBean(int currentPage, int pageCount, long totalCount,
			List<T> pageData) {
		super();
		this.currentPage = currentPage;
		this.pageCount = pageCount;
		this.pageData = pageData;
		this.totalCount=totalCount;
	}
	
}
