package cn.casnuc.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import cn.casnuc.entity.DataQuery;
import cn.casnuc.entity.PageProtectLog;
import cn.casnuc.entity.ProtectLogInfo;
import cn.casnuc.entity.User;
import cn.casnuc.service.ProtectLogInfoService;
import cn.casnuc.service.SystemOperateService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �鿴ҵ�������־��Ϣ
 * @author kaka
 *
 */
@SuppressWarnings("serial")
public class ProtectLogInfoAction extends ActionSupport{
	
	private ProtectLogInfoService protectLogInfoService;
	private DataQuery dataQuery;
	private JSONObject jsonstring;
	private SystemOperateService systemOperateService;

	public void setSystemOperateService(SystemOperateService systemOperateService) {
		this.systemOperateService = systemOperateService;
	}
	public JSONObject getJsonstring() {
		return jsonstring;
	}
	public void setJsonstring(JSONObject jsonstring) {
		this.jsonstring = jsonstring;
	}
	public void setProtectLogInfoService(ProtectLogInfoService protectLogInfoService) {
		this.protectLogInfoService = protectLogInfoService;
	}
	public DataQuery getDataQuery() {
		return dataQuery;
	}
	public void setDataQuery(DataQuery dataQuery) {
		this.dataQuery = dataQuery;
	}
	public String toResult(){
		return "nodata";
	}
	
	public String findByDate(){
		if (dataQuery.getStart() != null && dataQuery.getEnd() != null) {
			List<ProtectLogInfo> list = protectLogInfoService.findByDate(dataQuery.getStart(), dataQuery.getEnd());
			if (list.size() > 0) {
				
				ActionContext ac = ActionContext.getContext();
				Map<String, Object> requestReturn = ac.getContextMap();
				requestReturn.put("protectlog", list);
	
				return SUCCESS;
			} else {
				return toResult();
			}
		} else {
			return toResult();
		}	
	}
	
	public String queryProtectLog(DataQuery object) {
		
		List<ProtectLogInfo> list = protectLogInfoService.queryProtectLog(object);		
		if (list.size() > 0) {
			
			ActionContext ac = ActionContext.getContext();
			Map<String, Object> requestReturn = ac.getContextMap();
			requestReturn.put("protectlog", list);

			return SUCCESS;
		} else {
			
			return toResult();
			
		}
	}
	
	@Override
	public String execute(){
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");	
		if(user == null){		
			return "relogin";
		}else{			
			
			System.out.println(user.getName() + "��ѯ�˷�����־��Ϣ");
			
			return queryProtectLog(dataQuery);	
		}
	}
	
	public String jsonInfo() {
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");	
		
		if(user == null){
			
			// �û���¼��ϢʧЧ
			Map<String, Object> map = new LinkedHashMap<String,Object>();
			map.put("status", false);			
			JSONObject resultJson = JSONObject.fromObject(map);
			
			setJsonstring(resultJson);
				
			return SUCCESS;
			
		}else{			
			
			DataQuery myDataQuery = new DataQuery();
			
			// ����Ҫ��ʾ��ҳ�������
			HttpServletRequest request = ServletActionContext.getRequest();
			String pageStr = request.getParameter("page");
			String rowsStr = request.getParameter("rows");
			
			// ���ղ�ѯ����
			myDataQuery.setFileName(request.getParameter("fileName"));
			myDataQuery.setTransferDrection(request.getParameter("transferDrection"));
			myDataQuery.setTransferResult(request.getParameter("transferResult"));
			myDataQuery.setStart(request.getParameter("start"));
			myDataQuery.setEnd(request.getParameter("end"));
			myDataQuery.setSourceIp(request.getParameter("sourceIp"));
			myDataQuery.setSourceMac(request.getParameter("sourceMac"));
			myDataQuery.setDestinationIp(request.getParameter("destinationIp"));
			myDataQuery.setDestinationMac(request.getParameter("destinationMac"));
			
			// ��������ת��
			if(pageStr == null || pageStr.trim().length() == 0){
				pageStr = "1";
			}
			Integer page = Integer.parseInt(pageStr);
			Integer rows = Integer.parseInt(rowsStr);
/*			
			// ��¼��־
			SystemOperate systemOperate = new SystemOperate();
			systemOperate.setAccount(user.getAccount());
			systemOperate.setEvent("�鿴�˷�����־");
			systemOperate.setIp(request.getRemoteAddr());
			systemOperate.setName(user.getName());
			systemOperate.setRole(user.getRole());
			systemOperate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			systemOperateService.insertSystemOperateLog(systemOperate);
*/			
			return queryProtectLog(myDataQuery, page, rows);	
		}
		
	}
	
	private String queryProtectLog(DataQuery dataQuery, Integer page,  Integer rows) {
		
		//���ղ�ѯ���
		PageProtectLog result = protectLogInfoService.queryProtectLog(dataQuery, page, rows);		
		
		//��װ��json����
		Map<String, Object> map = new LinkedHashMap<String,Object>();		
		map.put("total", result.getAllRecordNO());		
		map.put("rows", result.getpLogList());
		JSONObject jsonObject = JSONObject.fromObject(map);		
		JSONObject resultJson = new JSONObject();
		resultJson.element("status", true);
		resultJson.element("result", jsonObject);
		
		setJsonstring(resultJson);
			
		return SUCCESS;
					
	}
}
