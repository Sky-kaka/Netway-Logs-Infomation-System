package cn.casnuc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import cn.casnuc.entity.SystemOperate;

public class SystemOperateDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * ���������־
	 * @param systemOperate  ��Ҫ�������־����
	 */
	public void insertOperateLog(SystemOperate systemOperate){
		
		Session sesssion = sessionFactory.getCurrentSession();
		sesssion.save(systemOperate);
		
	}
	
	/**
	 * ɾ��������־
	 * @param systemOperate ��Ҫɾ������־����
	 */
	public void deleteOperateLog(SystemOperate systemOperate){
		
		Session sesssion = sessionFactory.getCurrentSession();
		sesssion.delete(systemOperate);
	
	}
	
	/**
	 *  ��ҳ��ѯ
	 * @param operate Ҫ��ѯ����Ϣ
	 * @param start ��ѯ����ʼλ��
	 * @param rows ÿҳ��ʾ������
	 * @return ���ز�ѯ���Ľ����Ϣ
	 */
	@SuppressWarnings("unchecked")
	public List<SystemOperate> queryOperateLog(SystemOperate operate, Integer start, Integer rows, String startTime, String endTime) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(SystemOperate.class);
		
		if(!operate.getAccount().isEmpty() && !operate.getAccount().equals("ȫ��") ){
			c.add(Restrictions.eq("account", operate.getAccount()));
		}
		if(!operate.getName().isEmpty() && !operate.getName().equals("ȫ��")){
			c.add(Restrictions.eq("name", operate.getName()));
		}
		if(operate.getRole().equals("��ƹ���Ա")){
			c.add(Restrictions.ne("role", "ϵͳ����Ա"));
			c.add(Restrictions.ne("role", "�û�"));
			c.add(Restrictions.ne("role", "��ȫ����Ա"));
		} else if(operate.getRole().equals("ȫ��")){
			c.add(Restrictions.or(
					Restrictions.eq("role", "��ȫ����Ա"),
					Restrictions.eq("role", "ϵͳ����Ա")
					));
		} else {
			c.add(Restrictions.eq("role", operate.getRole()));
		}
	
		c.add(Restrictions.ge("time", startTime));
		c.add(Restrictions.le("time", endTime));
		
		c.setFirstResult(start);
		c.setMaxResults(rows);
		
		return c.list();
	}
	
	/**
	 * ��ѯ����
	 * @param operate Ҫ��ѯ����Ϣ
	 * @return ���ز�ѯ���Ľ����Ϣ
	 */
	public Integer queryOperateAll(SystemOperate operate, String startTime, String endTime) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(SystemOperate.class);
		if(!operate.getAccount().isEmpty() && !operate.getAccount().equals("ȫ��") ){
			c.add(Restrictions.eq("account", operate.getAccount()));
		}
		if(!operate.getName().isEmpty() && !operate.getName().equals("ȫ��")){
			c.add(Restrictions.eq("name", operate.getName()));
		}
		
		if(operate.getRole().equals("��ƹ���Ա")){
			c.add(Restrictions.ne("role", "ϵͳ����Ա"));
			c.add(Restrictions.ne("role", "�û�"));
			c.add(Restrictions.ne("role", "��ȫ����Ա"));
		} else if(operate.getRole().equals("ȫ��")){
			c.add(Restrictions.or(
					Restrictions.eq("role", "��ȫ����Ա"),
					Restrictions.eq("role", "ϵͳ����Ա")
					));
		}else{
			c.add(Restrictions.eq("role", operate.getRole()));
		}
		
		c.add(Restrictions.ge("time", startTime));
		c.add(Restrictions.le("time", endTime));
		
		return c.list().size();
	}
	
	
}
