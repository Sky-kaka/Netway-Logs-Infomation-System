package cn.casnuc.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import cn.casnuc.entity.SystemOperate;
import cn.casnuc.entity.User;
import cn.casnuc.service.SystemOperateService;
import cn.casnuc.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * �û���¼��Action
 * @author kaka
 *
 */
@SuppressWarnings("serial")
public class UserAction extends ActionSupport{
	
	private UserService userService;
	private User loginUser;
	private String loginResult;
	private SystemOperateService systemOperateService;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private JSONObject jsonResult;
	
	
	public JSONObject getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(JSONObject jsonResult) {
		this.jsonResult = jsonResult;
	}
	public void setSystemOperateService(SystemOperateService systemOperateService) {
		this.systemOperateService = systemOperateService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String toLoginUI(){
		return "login";
	}
	public User getLoginUser() {
		return loginUser;
	}	
	public void setLoginUser(User user) {
		this.loginUser = user;
	}		
	public String getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
	/**
	 * �û���¼
	 */
	public String login(){		
		
		try{
			if(this.loginUser != null && (this.loginUser.getAccount() != null || this.loginUser.getPassword() != null)){			
				
				//�ж��˻��Ƿ����
				User account = userService.findByAccount(this.loginUser.getAccount());
				if(account == null){				
					loginResult = "�û��������ڣ�";
					return toLoginUI();
				}
				
				//�ж��˻��Ƿ�����
				if(account.getFlag()){
					
					//�ж�����ʱ���Ƿ���Ч
					if(isLimit(account.getTime())){
						loginResult = "���ʺű����������Ժ����ԣ�";					
						return toLoginUI();
					}
					
					//�������
					account.setFlag(false);				//�����Ʊ�־ֵΪfalse
					account.setCount(0);				//��������������0
					userService.updateUser(account);
				}
				
				// �˻�û������
				User user = userService.findByAccountAndPass(this.loginUser.getAccount(), this.loginUser.getPassword());									
				
				// �ж������Ƿ���ȷ
				if(user != null){			
					
					//����sessionId
					user.setSessionId(request.getSession().getId());
					// �����������0
					user.setCount(0);
					userService.updateUser(user);
					
					// ��½�ɹ�
					request.getSession().setAttribute("userinfo", user);				
					
					// ��¼��־
					SystemOperate systemOperate = new SystemOperate();
					systemOperate.setAccount(user.getAccount());
					systemOperate.setName(user.getName());
					systemOperate.setRole(user.getRole());
					systemOperate.setEvent("��¼��ϵͳ");
					systemOperate.setIp(request.getRemoteAddr());	// ����ip
					systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  //�������� 
					
					systemOperateService.insertSystemOperateLog(systemOperate);
					
					return SUCCESS;
					
				}

				// �����������
				// �жϾ����ϴ������������ʱ���Ƿ����5����
				if(isGtTimes(account.getLastErrorTime())){					
					account.setCount(0);	// ������������������0
				}
				
				// �ж������������Ƿ񵽴�5��
				if(account.getCount() >= 4) {
					
					account.setFlag(true); 			// ��������־λ��Ϊtrue
					account.setTime(new Date());	// ��������ʱ��
					account.setLastErrorTime(new Date());  // ���������������ʱ��
					userService.updateUser(account);
					
					// ��¼�˻������¼�
					SystemOperate systemOperate = new SystemOperate();
					systemOperate.setAccount("");
					systemOperate.setName("");
					systemOperate.setRole("");
					systemOperate.setEvent("�˻� " + account.getAccount() + " ������");
					systemOperate.setRemarks("�˻�������");
					systemOperate.setIp(request.getRemoteAddr());	// ����ip
					systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  //�������� 
					systemOperateService.insertSystemOperateLog(systemOperate);
					
					loginResult = "�������5�Σ��˻�������";
					return toLoginUI();
				}
				
				// ������С��5��
				Integer errorCount = account.getCount() + 1;
				account.setCount(errorCount);	// ��������������
				account.setLastErrorTime(new Date());  // ���������������ʱ��
				userService.updateUser(account);
				
				loginResult = "���벻��ȷ���������" + errorCount + "�Σ�";
				return toLoginUI();
				
			}else{
				
				loginResult = "�û��������벻��Ϊ�գ�";
				
				return toLoginUI();
			}
			
		}catch(Exception e){
			
			System.out.println("��¼�����쳣"+e);
			
			return toLoginUI();
		}
	}
	
	/**
	 * �жϵ�ǰʱ�������ʱ����Ƿ�С��1Сʱ
	 * @param time
	 * @return С��1Сʱ����true
	 * @throws ParseException
	 */
	private boolean isLimit(Date date) throws ParseException {
		
		if(date == null){
    		return false;
    	}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date now = sdf.parse(sdf.format(new Date()));
        if( now.getTime()-date.getTime() < 60*60*1000 ){
            return true;
        }
        else{
            return false;
        }
	}
	
	/**
     * �жϵ�ǰʱ�������ʱ����Ƿ����5���� 
     * @param date
     * @return ����5���ӷ���true
     * @throws Exception
     */
    public boolean isGtTimes(Date date) throws Exception{
        
    	if(date == null){
    		return true;
    	}
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date now=sdf.parse(sdf.format(new Date()));
        if( now.getTime()-date.getTime() > 5*60*1000 ){
            return true;
        } else {
            return false;
        }
    }
	
    /**
     * �ж��û��ĵ�¼״��
     */
    public String userState(){
    	
    	User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");	
    	
    	Map<String, Object> map = new LinkedHashMap<String, Object>();
    	
    	if(user == null){
    		map.put("status", "null");
    	}else if(user.getSessionId().equals(userService.findByAccount(user.getAccount()).getSessionId())){
    		map.put("status", "ok");
    	}else{
    		map.put("status", "relogin");
    	}
    	
    	JSONObject resultJson = JSONObject.fromObject(map);
		setJsonResult(resultJson);
    	
    	return SUCCESS;
    }
    
    /**
     * 
     * @return
     */
	public String publicMethod(){
		return SUCCESS;
	}

}