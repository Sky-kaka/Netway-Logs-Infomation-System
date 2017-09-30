package cn.casnuc.service;

import java.util.List;

import cn.casnuc.dao.ProtectLogInfoDao;
import cn.casnuc.entity.DataQuery;
import cn.casnuc.entity.PageProtectLog;
import cn.casnuc.entity.ProtectLogInfo;

public class ProtectLogInfoService {

	private ProtectLogInfoDao protectLogInfoDao;

	public void setProtectLogInfoDao(ProtectLogInfoDao protectLogInfoDao) {
		this.protectLogInfoDao = protectLogInfoDao;
	}

	public List<ProtectLogInfo> findByDate(String start, String end) {
		return protectLogInfoDao.findByDate(start, end);
	}

	public List<ProtectLogInfo> queryProtectLog(DataQuery object) {
		return protectLogInfoDao.queryProtectLog(object);
	}
	
	/**
	 * ҵ��� 
	 * @param object ��ѯ����
	 * @param page	��ѯҳ��
	 * @param rows	ҳ������
	 * @return ��װ�õķ�ҳ�������
	 */
	public PageProtectLog queryProtectLog(DataQuery object, Integer page, Integer rows) {
		
		PageProtectLog pageProtectLog = new PageProtectLog();
		
		// ������ʼ��ַ
		Integer start = (page - 1) * rows + 1;
		
		// ��װÿҳ��ʾ�ļ�¼��
		pageProtectLog.setPerPageSize(rows);
		
		// ��װ��ҳ���
		List<ProtectLogInfo> list = protectLogInfoDao.queryProtectLog(object, start, rows);
		pageProtectLog.setpLogList(list);
		
		// ��װ�ܼ�¼��
		Integer allRecord = protectLogInfoDao.queryProtectLog(object).size();
		pageProtectLog.setAllRecordNO(allRecord);
		
		// ��װ��ҳ��
		Integer allPage = null;
		if(allRecord % pageProtectLog.getPerPageSize() == 0){
			allPage = allRecord / pageProtectLog.getPerPageSize();
		}else{
			allPage = allRecord / pageProtectLog.getPerPageSize() + 1;
		}		
		pageProtectLog.setAllPageNO(allPage);
		
		return pageProtectLog;
	}
};