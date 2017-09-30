package cn.casnuc.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class SystemInterceptor implements Interceptor{

	public void destroy() { }

	public void init() { }

	public String intercept(ActionInvocation invocation) throws Exception {
		
		// ��ȡrequest����
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// ��ȡaction�Ĵ������
		ActionProxy proxy = invocation.getProxy();
		 
		// ��ȡ��ǰִ�еķ�����
		String methodName = proxy.getMethod();
		String actionName = proxy.getActionName();
		
		if("exit".equals(actionName)){
			return invocation.invoke();
		}
		
		// ��action�ķ������ж�
		if (!"login".equals(methodName)) {
			// �Ȼ�ȡ��ǰ��½���û�
			Object obj = request.getSession().getAttribute("userinfo");
			if (obj == null) {
				// û�е�½
				return "relogin";
			} else {
				// ��ǰ�û��е�½
				return invocation.invoke();
			}
		} else {
			// ˵����ǰ�û����ڵ�½
			return invocation.invoke();
		}
	}

}
