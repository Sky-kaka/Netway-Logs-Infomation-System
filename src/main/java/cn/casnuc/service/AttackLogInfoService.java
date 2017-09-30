package cn.casnuc.service;

import java.util.List;

import cn.casnuc.dao.AttackLogInfoDao;
import cn.casnuc.entity.AttackLogInfo;
import cn.casnuc.entity.AttackQuery;
import cn.casnuc.entity.PageAttackLog;

public class AttackLogInfoService {

	private AttackLogInfoDao attackLogInfoDao;

	public void setAttackLogInfoDao(AttackLogInfoDao attackLogInfoDao) {
		this.attackLogInfoDao = attackLogInfoDao;
	}
	
	/**
	 * ��ҳ��ѯ
	 * @param query ������ѯ����
	 * @param page  ��ѯ��ҳ��
	 * @param rows	ÿҳ��ʾ�ļ�¼��
	 * @return	��ѯ�������
	 */
	public PageAttackLog queryAttackLog(AttackQuery query, Integer page, Integer rows){
		
		PageAttackLog pageAttackLog = new PageAttackLog();
		
		// �����ѯ����ʼλ��
		Integer start = (page - 1) * rows;
		
		// ����DAO���ѯ
		Integer allSize = attackLogInfoDao.queryAttackLog(query);
		List<AttackLogInfo> pageList = attackLogInfoDao.queryAttackLog(query, start, rows);
		
		// ��װ�ܼ�¼��
		pageAttackLog.setAllRecord(allSize);

		// ��װÿҳ��ʾ�ļ�¼��
		pageAttackLog.setPerPageSize(rows);
		
		// ��װ��ҳ��
		Integer allPage = null;
		if(allSize % rows == 0){
			allPage = allSize / rows;
		}else{
			allPage = allSize / rows + 1;
		}
		pageAttackLog.setAllPageSize(allPage);
		
		// ��װ��ѯ���
		pageAttackLog.setList(pageList);
		
		return pageAttackLog;
	}
};