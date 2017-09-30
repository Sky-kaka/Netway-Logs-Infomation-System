package cn.casnuc.entity;

import java.util.ArrayList;
import java.util.List;

public class PageProtectLog {
	
	private Integer currPageNO;		//��ǰҳ��OK
	private Integer perPageSize = 9;	//ÿҳ��ʾ��¼����Ĭ��Ϊ3����¼OK
	private Integer allRecordNO;	//�ܼ�¼��OK
	private Integer allPageNO;	//��ҳ��OK
	private List<ProtectLogInfo> pLogList = new ArrayList<ProtectLogInfo>();//�ñ�ҳ��ʾ������OK
	
	public Integer getCurrPageNO() {
		return currPageNO;
	}
	public void setCurrPageNO(Integer currPageNO) {
		this.currPageNO = currPageNO;
	}
	public Integer getPerPageSize() {
		return perPageSize;
	}
	public void setPerPageSize(Integer perPageSize) {
		this.perPageSize = perPageSize;
	}
	public Integer getAllRecordNO() {
		return allRecordNO;
	}
	public void setAllRecordNO(Integer allRecordNO) {
		this.allRecordNO = allRecordNO;
	}
	public Integer getAllPageNO() {
		return allPageNO;
	}
	public void setAllPageNO(Integer allPageNO) {
		this.allPageNO = allPageNO;
	}
	public List<ProtectLogInfo> getpLogList() {
		return pLogList;
	}
	public void setpLogList(List<ProtectLogInfo> pLogList) {
		this.pLogList = pLogList;
	}
	
	
	
}
