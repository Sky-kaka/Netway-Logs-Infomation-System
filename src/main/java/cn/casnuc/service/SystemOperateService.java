package cn.casnuc.service;

import java.util.List;

import cn.casnuc.dao.SystemOperateDao;
import cn.casnuc.entity.PageInfo;
import cn.casnuc.entity.SystemOperate;

public class SystemOperateService {
	
	private SystemOperateDao systemOperateDao;

	public void setSystemOperateDao(SystemOperateDao systemOperateDao) {
		this.systemOperateDao = systemOperateDao;
	}
	
	/**
	 * ���������־����
	 * @param systemOperate ��Ҫ���ӵ�����
	 */
	public void insertSystemOperateLog(SystemOperate systemOperate){
		
		systemOperateDao.insertOperateLog(systemOperate);
		
	}
	
	/**
	 * ɾ��������־����
	 * @param systemOperate ��Ҫɾ����Ŀ�����
	 */
	public void deleteSystemOperateLog(SystemOperate systemOperate){
		
		systemOperateDao.deleteOperateLog(systemOperate);
	}
	
	/**
	 * ��ҳ��ѯ������־
	 * @param operate Ҫ��ѯ����Ϣ
	 * @param page	�ڼ�ҳ
	 * @param rows	ÿҳ������
	 */
	public PageInfo queryOperateLog(SystemOperate operate, String startTime, String endTime, Integer page, Integer rows) {
		
		PageInfo pageInfo = new PageInfo();
		
		// �����ѯ����ʼλ��
		Integer start = (page - 1) * rows;
		
		// ����Dao���ѯ
		List list = systemOperateDao.queryOperateLog(operate, start, rows, startTime, endTime);
		Integer allSize = systemOperateDao.queryOperateAll(operate, startTime, endTime);
		
		// ������ҳ��
		Integer allPage = null;
		if(allSize % rows == 0){
			allPage = allSize / rows;
		}else{
			allPage = allSize / rows + 1;
		}
		
		// ��װ���
		pageInfo.setAllPage(allPage);
		pageInfo.setAllSize(allSize);
		pageInfo.setCurrentPage(page);
		pageInfo.setList(list);
		pageInfo.setPerPageSize(rows);
		
		return pageInfo;
	}
}
