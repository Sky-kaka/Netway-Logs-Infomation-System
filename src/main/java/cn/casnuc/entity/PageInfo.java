package cn.casnuc.entity;

import java.util.List;

public class PageInfo {
	
	private Integer allPage;	// ��ҳ��
	private Integer allSize;	// �ܼ�¼��
	private Integer perPageSize;	// ÿҳ��¼��
	private Integer currentPage; // ��ǰҳ
	private List<?> list;
	
	public Integer getAllPage() {
		return allPage;
	}
	public void setAllPage(Integer allPage) {
		this.allPage = allPage;
	}
	public Integer getAllSize() {
		return allSize;
	}
	public void setAllSize(Integer allSize) {
		this.allSize = allSize;
	}
	public Integer getPerPageSize() {
		return perPageSize;
	}
	public void setPerPageSize(Integer perPageSize) {
		this.perPageSize = perPageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	
}
