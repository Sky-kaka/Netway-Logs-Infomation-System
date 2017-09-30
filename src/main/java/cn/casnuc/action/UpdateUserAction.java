package cn.casnuc.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.casnuc.entity.Permission;
import cn.casnuc.entity.SystemOperate;
import cn.casnuc.entity.User;
import cn.casnuc.service.SystemOperateService;
import cn.casnuc.service.UserService;

/**
 * ����ϵͳ�ϵ��û�����Ϣ
 * @author kaka
 *
 */
@SuppressWarnings("serial")
public class UpdateUserAction extends ActionSupport{
	
	private UserService userService;
	private JSONObject jsonResult;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private SystemOperateService systemOperateService;
	
	public void setSystemOperateService(SystemOperateService systemOperateService) {
		this.systemOperateService = systemOperateService;
	}
	public JSONObject getJsonResult() {
//		System.out.println(jsonResult);
		return jsonResult;
	}
	public void setJsonResult(JSONObject jsonResult) {
		this.jsonResult = jsonResult;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String updateInfo(){
		
		User user = (User) request.getSession().getAttribute("userinfo");	
		if(user == null){
			return "relogin";
		}
		
		// ��ȡҪ�޸ĵ���Ϣ����װ
		String name = request.getParameter("name");
		user.setName(name);
		
		// �ύ��ҵ��㴦��
		String rusult = userService.updateUser(user);
		
		// ��¼��־
		SystemOperate systemOperate = new SystemOperate();
		systemOperate.setAccount(user.getAccount());
		systemOperate.setEvent("�޸��˸�����Ϣ");
		systemOperate.setIp(request.getRemoteAddr());
		systemOperate.setName(user.getName());
		systemOperate.setRole(user.getRole());
		systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));			
		systemOperateService.insertSystemOperateLog(systemOperate);
		
		toResult(user, rusult, "user_modifyInfo");
		return SUCCESS;
	}
	
	/**
	 * �޸�����
	 * @return
	 */
	public String modifyPassword() {
		
		User user = (User) request.getSession().getAttribute("userinfo");
		if(user == null){
			return "relogin";
		}
		
		String oldPw = request.getParameter("oldpassword");
		String newPw = request.getParameter("newpassword");
		
		if(!oldPw.equals(user.getPassword())){
			toResult(user, "ԭ�������","user_modifyPassword");
		} else {
			
			user.setPassword(newPw);
			
			// ��¼��־
			SystemOperate systemOperate = new SystemOperate();
			systemOperate.setAccount(user.getAccount());
			systemOperate.setEvent("�޸�������");
			systemOperate.setIp(request.getRemoteAddr());
			systemOperate.setName(user.getName());
			systemOperate.setRole(user.getRole());
			systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));			
			systemOperateService.insertSystemOperateLog(systemOperate);
			
			toResult(user, userService.updateUser(user), "user_modifyPassword");
		}
		
		return SUCCESS;
	}

	private void toResult(User user, String result, String url) {
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print("<script language='JavaScript'>alert(\""+ result +"\");window.location.href='/ssh/"+url+"'</script>");
		out.flush();
		out.close();
	}
	
	/**
	 * �����û���Ϣ
	 * @return
	 */
	public String updateInfoJson(){
		
		User user = (User) request.getSession().getAttribute("userinfo");	
		if(user == null){
			// ��װJSON���󷵻ؽ��
			JSONObject json = new JSONObject();
			json.element("status", false);
			setJsonResult(json);
			
		}else{
			
			// ������Ҫ�޸ĵ�����
			String account = request.getParameter("account");
			String name = request.getParameter("name");
			
			boolean attackLog = Boolean.parseBoolean(request.getParameter("attackLog"));
			boolean gatewayConfig = Boolean.parseBoolean(request.getParameter("gatewayConfig"));
			boolean keywordConfig = Boolean.parseBoolean(request.getParameter("keywordConfig"));
			boolean protectConfig = Boolean.parseBoolean(request.getParameter("protectConfig"));
			boolean operateLog = Boolean.parseBoolean(request.getParameter("operateLog"));
			boolean protectLog = Boolean.parseBoolean(request.getParameter("protectLog"));
			boolean transferConfig = Boolean.parseBoolean(request.getParameter("transferConfig"));
			
			// ����Ҫ�޸���Ϣ���û�
			User updateUser = userService.findByAccount(account);
			
			// �޸���Ϣ
			Permission permission = updateUser.getPermission();
			permission.setAttackLog(attackLog);
			permission.setGatewayConfig(gatewayConfig);
			permission.setKeywordConfig(keywordConfig);
			permission.setOperateLog(operateLog);
			permission.setProtectConfig(protectConfig);
			permission.setProtectLog(protectLog);
			permission.setTransferConfig(transferConfig);			
			updateUser.setName(name);
			
			// �ύ������Ϣ
			userService.updateUser(updateUser);
			
			//��¼������־
			SystemOperate systemOperate = new SystemOperate();
			systemOperate.setAccount(user.getAccount());
			systemOperate.setEvent("�޸����û���Ϣ��������Ϊ"+updateUser.getName()+",Ȩ�޸�Ϊ"+
					updateUser.getPermission().getGatewayConfig()+","+
					updateUser.getPermission().getKeywordConfig()+","+
					updateUser.getPermission().getProtectConfig()+","+
					updateUser.getPermission().getTransferConfig()+","+
					updateUser.getPermission().getAttackLog()+","+
					updateUser.getPermission().getOperateLog()+","+
					updateUser.getPermission().getProtectLog());
			systemOperate.setIp(request.getRemoteAddr());
			systemOperate.setName(user.getName());
			systemOperate.setRole(user.getRole());
			systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			systemOperate.setRemarks("Ȩ������Ϊ�������á��ؼ������á��������á��������á�������־��������־�ͷ�����־");
			systemOperateService.insertSystemOperateLog(systemOperate);
			
			// ��װJSON���󷵻ؽ��
			JSONObject json = new JSONObject();
			json.element("status", true);
			setJsonResult(json);
		}
		
		return SUCCESS;
	}
	
	/**
	 * ���¹���Ա��Ϣ
	 * @return
	 */
	public String updateAdmin(){
		
		User user = (User) request.getSession().getAttribute("userinfo");	
		if(user == null){
			
			// ��װJSON���󷵻ؽ��
			JSONObject json = new JSONObject();
			json.element("status", false);
			setJsonResult(json);
			
		}else{
			
			// ������Ҫ�޸ĵ�����
			String account = request.getParameter("account");
			String name = request.getParameter("name");
						
			// ����Ҫ�޸���Ϣ���û�
			User updateUser = userService.findByAccount(account);
			
			// �޸���Ϣ
			updateUser.setName(name);
			
			// �ύ������Ϣ
			userService.updateUser(updateUser);
			
			// ��¼������־
			SystemOperate systemOperate = new SystemOperate();
			systemOperate.setAccount(user.getAccount());
			systemOperate.setEvent("�޸��˹���Ա��Ϣ��������Ϊ"+updateUser.getName());
			systemOperate.setIp(request.getRemoteAddr());
			systemOperate.setName(user.getName());
			systemOperate.setRole(user.getRole());
			systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			systemOperateService.insertSystemOperateLog(systemOperate);
			
			// ��װJSON���󷵻ؽ��
			JSONObject json = new JSONObject();
			json.element("status", true);
			setJsonResult(json);
		}
		
		return SUCCESS;
	}
	
}
