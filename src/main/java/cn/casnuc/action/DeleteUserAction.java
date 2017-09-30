package cn.casnuc.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import cn.casnuc.entity.SystemOperate;
import cn.casnuc.entity.User;
import cn.casnuc.service.SystemOperateService;
import cn.casnuc.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ɾ���û�����������Ա���û���
 * @author kaka
 *
 */
@SuppressWarnings("serial")
public class DeleteUserAction extends ActionSupport{

	private UserService userService;
	private JSONObject jsonResult;
	private SystemOperateService systemOperateService;
	
	public void setSystemOperateService(SystemOperateService systemOperateService) {
		this.systemOperateService = systemOperateService;
	}
	public JSONObject getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(JSONObject jsonResult) {
		this.jsonResult = jsonResult;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public String execute() throws Exception {
		// �ж��û���Ϣ�Ƿ���Ч
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		if(user == null){			
			
			// ��װJSON���󷵻ؽ��
			JSONObject json = new JSONObject();
			json.element("status", false);
			setJsonResult(json);			
			return SUCCESS;
		
		} else {
			
			HttpServletRequest request = ServletActionContext.getRequest();
			String account = request.getParameter("account");
			
			// ��װҪɾ�����û�
			User removeUser = new User();
			removeUser.setAccount(account);			
			
			// ����ҵ���
			removeUser = userService.deleteUser(removeUser);
			
			//��¼������־
			SystemOperate systemOperate = new SystemOperate();
			systemOperate.setAccount(user.getAccount());
			systemOperate.setEvent("ɾ�����û����û���Ϊ"+removeUser.getAccount() + "������Ϊ"+removeUser.getName());
			systemOperate.setIp(request.getRemoteAddr());
			systemOperate.setName(user.getName());
			systemOperate.setRole(user.getRole());
			systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			systemOperate.setRemarks("��ɾ����ɫΪ" + removeUser.getRole());
			systemOperateService.insertSystemOperateLog(systemOperate);
			
			// ����json����
			JSONObject json = new JSONObject();
			json.element("status", true); 
			setJsonResult(json);			
			
			return SUCCESS;
		}				
	}
}
