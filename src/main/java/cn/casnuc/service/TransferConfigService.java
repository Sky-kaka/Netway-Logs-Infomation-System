package cn.casnuc.service;

import cn.casnuc.dao.TransferConfigDao;
import cn.casnuc.entity.TransferConfig;

public class TransferConfigService {
	
	private TransferConfigDao transferConfigDao;

	public void setTransferConfigDao(TransferConfigDao transferConfigDao) {
		this.transferConfigDao = transferConfigDao;
	}
	
	/**
	 * �鿴������������
	 * @return 
	 */
	public TransferConfig queryTransferConfig(){
		return transferConfigDao.queryTransferConfig();
	}
	
}
