package cn.casnuc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cn.casnuc.entity.AttackLogInfo;
import cn.casnuc.entity.AttackQuery;

public class AttackLogInfoDao {
	
	// ע��SessionFactory����
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * ��ѯ��¼����
	 * @param attackQuery ������ѯ����
	 * @return ȫ������ļ���
	 */
	@SuppressWarnings("unchecked")
	public Integer queryAttackLog(AttackQuery attackQuery){
		
		Session sesssion = sessionFactory.getCurrentSession();
		Criteria c = sesssion.createCriteria(AttackLogInfo.class);
		
		if(attackQuery.getAttackTarget() == 1 || attackQuery.getAttackTarget() == 2){
			c.add(Restrictions.eq("attackTarget", attackQuery.getAttackTarget()));
		}
		if(attackQuery.getAttackType() != 999){
			c.add(Restrictions.eq("attackType", attackQuery.getAttackType()));
		}
		if(!attackQuery.getSourceMac().equals("ȫ��")){
			c.add(Restrictions.eq("sourceMac", attackQuery.getSourceMac()));
		}
		if(!attackQuery.getTargetIp().equals("ȫ��")){
			c.add(Restrictions.eq("targetIp", attackQuery.getTargetIp()));
		}
		if(attackQuery.getTargetPort() != -1){
			c.add(Restrictions.eq("targetPort", attackQuery.getTargetPort()));
		}
		c.add(Restrictions.ge("time", attackQuery.getStartDate()));
		c.add(Restrictions.le("time", attackQuery.getEndDate()));
		
		ScrollableResults scroll = c.scroll();
		scroll.last();
		
		return scroll.getRowNumber()+1;
	}
	
	/**
	 * ��ҳ��ѯ	
	 * @param attackQuery ������ѯ�Ķ���
 	 * @param start ��ѯ����ʼ��¼
	 * @param rows  ��ѯ�ļ�¼����
	 * @return  ��ҳ�Ĺ�����־���󼯺�
	 */
	@SuppressWarnings("unchecked")
	public List<AttackLogInfo> queryAttackLog(AttackQuery attackQuery, Integer start, Integer rows){
		
		Session sesssion = sessionFactory.getCurrentSession();
		Criteria c = sesssion.createCriteria(AttackLogInfo.class);
		
		if(attackQuery.getAttackTarget() == 1 || attackQuery.getAttackTarget() == 2){
			c.add(Restrictions.eq("attackTarget", attackQuery.getAttackTarget()));
		}
		if(attackQuery.getAttackType() != 999){
			c.add(Restrictions.eq("attackType", attackQuery.getAttackType()));
		}
		if(!attackQuery.getSourceMac().equals("ȫ��")){
			c.add(Restrictions.eq("sourceMac", attackQuery.getSourceMac()));
		}
		if(!attackQuery.getTargetIp().equals("ȫ��")){
			c.add(Restrictions.eq("targetIp", attackQuery.getTargetIp()));
		}
		if(attackQuery.getTargetPort() != -1){
			c.add(Restrictions.eq("targetPort", attackQuery.getTargetPort()));
		}
		c.add(Restrictions.ge("time", attackQuery.getStartDate()));
		c.add(Restrictions.le("time", attackQuery.getEndDate()));
		c.addOrder(Order.asc("time"));
		
		c.setFirstResult(start);
		c.setMaxResults(rows);
		
		return c.list();
	}
	
}
