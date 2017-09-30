package cn.casnuc.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.struts2.ServletActionContext;

import cn.casnuc.entity.SystemOperate;
import cn.casnuc.entity.TransferConfig;
import cn.casnuc.entity.TransferResult;
import cn.casnuc.entity.User;
import cn.casnuc.service.SystemOperateService;
import cn.casnuc.service.TransferConfigService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * �鿴�����ϵĴ������������Ϣ
 * @author kaka
 *
 */
@SuppressWarnings("serial")
public class TransferConfigAction extends ActionSupport{
	
	private TransferResult transferResult;
	private TransferConfigService transferConfigService;
	private SystemOperateService systemOperateService;
		
	public TransferResult getTransferResult() {
		return transferResult;
	}
	public void setTransferResult(TransferConfig transferConfig) {
		
		TransferResult transferResult = new TransferResult();
		transferResult.dataConver(transferConfig);
		
		this.transferResult = transferResult;
	}
	public void setTransferConfigService(TransferConfigService transferConfigService) {
		this.transferConfigService = transferConfigService;
	}
	public void setSystemOperateService(SystemOperateService systemOperateService) {
		this.systemOperateService = systemOperateService;
	}
	
	public String queryTransferConfig(){
		
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		
		if(user == null){
			return "relogin";
		}
		
		// �鿴��������
		TransferConfig transferConfig = transferConfigService.queryTransferConfig();
		
		//��װ������
		setTransferResult(transferConfig);
		
		// ��¼��־
		SystemOperate systemOperate = new SystemOperate();
		systemOperate.setAccount(user.getAccount());
		systemOperate.setEvent("�鿴�˴������������Ϣ");
		systemOperate.setIp(ServletActionContext.getRequest().getRemoteAddr());
		systemOperate.setName(user.getName());
		systemOperate.setRole(user.getRole());
		systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
		systemOperateService.insertSystemOperateLog(systemOperate);
		
		return SUCCESS;
	}
	
}
