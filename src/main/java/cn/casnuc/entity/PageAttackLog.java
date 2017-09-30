package cn.casnuc.entity;

import java.util.List;

public class PageAttackLog {
	
	private Integer allPageSize;	// ��ҳ��
	private Integer allRecord;		// �ܼ�¼��
	private Integer perPageSize;	// ÿҳ��ʾ�ļ�¼��
	private Integer currentPage; 	// ��ǰҳ��
	private List<AttackLogInfo> list; // ��ǰҳ�Ľ������
	
	public Integer getAllPageSize() {
		return allPageSize;
	}
	public void setAllPageSize(Integer allPageSize) {
		this.allPageSize = allPageSize;
	}
	public Integer getAllRecord() {
		return allRecord;
	}
	public void setAllRecord(Integer allRecord) {
		this.allRecord = allRecord;
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
	public List<AttackLogInfo> getList() {
		return list;
	}
	public void setList(List<AttackLogInfo> list) {
		this.list = list;
	}
	
	
}
