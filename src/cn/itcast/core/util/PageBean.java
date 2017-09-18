package cn.itcast.core.util;

import java.util.List;

/**
 * ��װ��ҳ�Ĳ���
 * @author mayouwen
 *
 */
public class PageBean<T> {
	private int currentPage=1;//��ǰҳ
	private int pageCount=2;//��ѯ���ص�������ÿҳ������
	private long totalCount;//�ܼ�¼��
	private int totalPage;//��ҳ��
	private List<T> pageData;//��ҳ��ѯ��������
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
	//��ȡ��ҳ��
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
