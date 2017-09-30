package cn.casnuc.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import cn.casnuc.entity.PageInfo;
import cn.casnuc.entity.SystemOperate;
import cn.casnuc.entity.User;
import cn.casnuc.service.SystemOperateService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ��ϵͳ���û��Ĳ�����Ϊ���м�¼
 * @author kaka
 *
 */
@SuppressWarnings("serial")
public class SystemOperateAction extends ActionSupport{
	
	private SystemOperateService systemOperateService;
	private JSONObject jsonResult;
	private HttpServletRequest request = ServletActionContext.getRequest();
	
	public JSONObject getJsonResult() {
//		System.out.println(jsonResult.toString());
		return jsonResult;
	}
	public void setJsonResult(JSONObject jsonResult) {
		this.jsonResult = jsonResult;
	}

	public void setSystemOperateService(SystemOperateService systemOperateService) {
		this.systemOperateService = systemOperateService;
	}
	
	/**
	 * ��ѯ��־
	 * @return
	 */
	public String queryOperate(){
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		
		if(user == null){
			// �û�ʧЧ
			JSONObject jsonObject = new JSONObject();
			jsonObject.element("status", false);
			setJsonResult(jsonObject);
			
			return SUCCESS;			
		}
		
		// ��������
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String role = request.getParameter("role");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageStr = request.getParameter("page");
		String rowStr = request.getParameter("rows");		
		if(pageStr == null || pageStr.trim().length() == 0){
			pageStr = "1";
		}
		Integer page = Integer.parseInt(pageStr);
		Integer rows = Integer.parseInt(rowStr);
		
		// ��װ����
		SystemOperate operate = new SystemOperate();
		operate.setAccount(account);	
		operate.setName(name);
		if(user.getRole().equals("��ȫ����Ա")){
			operate.setRole("��ƹ���Ա");
		}else if(user.getRole().equals("ϵͳ����Ա")){
			operate.setRole("�û�");
		}else{
			operate.setRole(role);
		}
		
		// �ύ��ҵ����ѯ
		PageInfo pageInfo = systemOperateService.queryOperateLog(operate, startTime, endTime, page, rows);
		
/*		// ��¼��־
		SystemOperate systemOperate = new SystemOperate();
		systemOperate.setAccount(user.getAccount());
		systemOperate.setEvent("�鿴�˲�����־�������������û���="+account+",����="+name+",��ɫ="+ operate.getRole() + ",��ʼʱ��="+startTime+",��ֹʱ��="+endTime);
		systemOperate.setIp(request.getRemoteAddr());
		systemOperate.setName(user.getName());
		systemOperate.setRole(user.getRole());
		systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		systemOperateService.insertSystemOperateLog(systemOperate);
*/		
		// ��װJson���
		JSONObject json = new JSONObject();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("total", pageInfo.getAllSize());
		map.put("rows", pageInfo.getList());
		JSONObject jsonChild = JSONObject.fromObject(map);
		json.element("status", true);
		json.element("result", jsonChild);		
		setJsonResult(json);
		
		return SUCCESS;
	}
	
	public String writeLog(){
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");	
		
		if(user == null){
			
			// �û���¼��ϢʧЧ
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("status", false);
			JSONObject resultJson = JSONObject.fromObject(map);
			setJsonResult(resultJson);

			return SUCCESS;		
		}
		
		// ��¼��־
		SystemOperate systemOperate = new SystemOperate();
		systemOperate.setAccount(user.getAccount());
		systemOperate.setEvent(request.getParameter("event"));
		systemOperate.setIp(request.getRemoteAddr());
		systemOperate.setName(user.getName());
		systemOperate.setRole(user.getRole());
		systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		systemOperateService.insertSystemOperateLog(systemOperate);
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("status", true);
		JSONObject resultJson = JSONObject.fromObject(map);
		setJsonResult(resultJson);
		
		return SUCCESS;
	}
	
}
