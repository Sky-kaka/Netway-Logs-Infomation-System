package cn.casnuc.service;

import cn.casnuc.dao.ProtectConfigDao;
import cn.casnuc.entity.ProtectReturn;

public class ProtectConfigService {

	private ProtectConfigDao protectConfigDao;

	public void setProtectConfigDao(ProtectConfigDao protectConfigDao) {
		this.protectConfigDao = protectConfigDao;
	}
	
	/**
	 * �鿴������������
	 * @return
	 */
	public ProtectReturn queryProtectConfig(){
		return protectConfigDao.queryProtectConfig();
	}
	
}
