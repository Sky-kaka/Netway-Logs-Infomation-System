package cn.casnuc.action;

import java.text.SimpleDateFormat;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.casnuc.entity.Permission;
import cn.casnuc.entity.SystemOperate;
import cn.casnuc.entity.User;
import cn.casnuc.service.SystemOperateService;
import cn.casnuc.service.UserService;

/**
 * �����û���������Ա����ͨ�û���
 * @author kaka
 *
 */
@SuppressWarnings("serial")
public class AddUserAction extends ActionSupport{
	
	private User user;
	private JSONObject result;
	private UserService userService;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private SystemOperateService systemOperateService;
	
	public void setSystemOperateService(SystemOperateService systemOperateService) {
		this.systemOperateService = systemOperateService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public JSONObject getResult() {
//		System.out.println("get�������ص�data:"+result.toString());
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
	
	@Override
	public String execute(){
		
		User loginUser = (User) request.getSession().getAttribute("userinfo");
		if (loginUser == null || !loginUser.getSessionId().equals( request.getSession().getId())) {
			
			return "relogin";
		
		} else {
			
			String result = null;
			if (loginUser.getRole().equals("ϵͳ����Ա")) {
				result = addSysAdmin(user, loginUser);
			} else if (loginUser.getRole().equals("��ȫ����Ա")) {
				result = addSafeAdmin(user, loginUser);
			} else if (loginUser.getRole().equals("��ƹ���Ա")) {
				result = addAuditAdmin(user, loginUser);
			}
			
			toResult(loginUser.getRole(), result);

			return SUCCESS;
		}
	}
	
	private String addSysAdmin(User user, User loginUser){
		
		if (user.getRole().equals("ϵͳ����Ա")) {
			
			//��װ����
			Permission permission = new Permission();
  			permission.setKeywordConfig(false);
			permission.setOperateLog(false);
			permission.setProtectConfig(false);
			permission.setTransferConfig(false);
			permission.setAttackLog(false);
			permission.setGatewayConfig(false);
			permission.setProtectLog(false);			
			user.setPermission(permission);
			
			// ��¼��־
			SystemOperate systemOperate = new SystemOperate();
			systemOperate.setAccount(loginUser.getAccount());
			systemOperate.setEvent("������ϵͳ����Ա"+ user.getAccount()+ "  " +user.getName());
			systemOperate.setIp(request.getRemoteAddr());
			systemOperate.setName(loginUser.getName());
			systemOperate.setRole(loginUser.getRole());
			systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			systemOperateService.insertSystemOperateLog(systemOperate);
					
			return addUser(user);
			
		} else {
			
			//��װ�û�
			user.setRole("�û�");
			Permission permission = new Permission();
			permission.setKeywordConfig(conversionData(request.getParameter("keywordConfig")));
			permission.setOperateLog(conversionData(request.getParameter("operateLog")));
			permission.setProtectConfig(conversionData(request.getParameter("protectConfig")));
			permission.setTransferConfig(conversionData(request.getParameter("transferConfig")));
			permission.setAttackLog(conversionData(request.getParameter("attackLog")));
			permission.setGatewayConfig(conversionData(request.getParameter("gatewayConfig")));
			permission.setProtectLog(conversionData(request.getParameter("protectLog")));
			user.setPermission(permission);
			
			// ��¼��־
			SystemOperate systemOperate = new SystemOperate();
			systemOperate.setAccount(loginUser.getAccount());
			systemOperate.setEvent("�������û�"+user.getAccount()+"  "+user.getName());
			systemOperate.setIp(request.getRemoteAddr());
			systemOperate.setName(loginUser.getName());
			systemOperate.setRole(loginUser.getRole());
			systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			systemOperateService.insertSystemOperateLog(systemOperate);
			
			return addUser(user);
		}	
	}
	
	private String addAuditAdmin(User user, User loginUser) {

		//��װ����
		user.setRole("��ƹ���Ա");
		Permission permission = new Permission();
		permission.setKeywordConfig(false);
		permission.setOperateLog(false);
		permission.setProtectConfig(false);
		permission.setTransferConfig(false);
		permission.setAttackLog(false);
		permission.setGatewayConfig(false);
		permission.setProtectLog(false);		
		user.setPermission(permission);
		
		// ��¼��־
		SystemOperate systemOperate = new SystemOperate();
		systemOperate.setAccount(loginUser.getAccount());
		systemOperate.setEvent("��������ƹ���Ա"+user.getAccount()+"  "+user.getName());
		systemOperate.setIp(request.getRemoteAddr());
		systemOperate.setName(loginUser.getName());
		systemOperate.setRole(loginUser.getRole());
		systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		systemOperateService.insertSystemOperateLog(systemOperate);
		
		return addUser(user);
	}
	
	private String addSafeAdmin(User user, User loginUser) {
		
		// ��װ����
		user.setRole("��ȫ����Ա");
		Permission permission = new Permission();
		permission.setKeywordConfig(false);
		permission.setOperateLog(false);
		permission.setProtectConfig(false);
		permission.setTransferConfig(false);
		permission.setAttackLog(false);
		permission.setGatewayConfig(false);
		permission.setProtectLog(false);	
		user.setPermission(permission);
		
		// ��¼������־��
		SystemOperate systemOperate = new SystemOperate();
		systemOperate.setAccount(loginUser.getAccount());
		systemOperate.setEvent("�����˰�ȫ����Ա"+user.getAccount()+"  "+user.getName());
		systemOperate.setIp(request.getRemoteAddr());
		systemOperate.setName(loginUser.getName());
		systemOperate.setRole(loginUser.getRole());
		systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		systemOperateService.insertSystemOperateLog(systemOperate);
		
		return addUser(user);
	}
	
	/**
	 * ����ҵ��������û�
	 * @param user	Ҫ��ӵ��û�
	 * @return ���ַ������ؽ����Ϣ
	 */
	private String addUser(User user) {
		return userService.addUser(user);		
	}
	
	/**
	 * ��String���͵�Ȩ��ת��Ϊboolean����
	 * @param string Ҫת�����ַ�ת
	 * @return ����boolean����
	 */
	private boolean conversionData(String string){
		if(string != null && string.equals("true")){
			return true;
		}else{
			return false;
		}
	}
	
	private void toResult(String role, String result){
		
		String url = null;
		
		if(role.equals("ϵͳ����Ա")){
			url = "user_add";
		}else if(role.equals("��ƹ���Ա")){
			url = "user_add_audit";
		}else if(role.equals("��ȫ����Ա")){
			url = "user_add_safe";
		}
		
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
	 * ����û����Ƿ����
	 * @return
	 */
	public String queryUserAccount(){
		
		String account = request.getParameter("account");		// ��ȡ����

		User queryUser = new User();
		queryUser.setAccount(account);
		List<User> list = userService.queryUser(queryUser);
		Map<String, Object> map = new HashMap<String, Object>();
		if(list.size() > 0){
			map.put("result", false);
		}else{
			map.put("result", true);
		}
		
		JSONObject json = JSONObject.fromObject(map);// ��map����ת����json��������
		
		setResult(json);
		
		return SUCCESS;
	}
}
