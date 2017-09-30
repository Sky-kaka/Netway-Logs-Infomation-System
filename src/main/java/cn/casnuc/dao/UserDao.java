package cn.casnuc.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

import cn.casnuc.entity.Permission;
import cn.casnuc.entity.User;

public class UserDao {
	
	// ע��SessionFactory����
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
		
	/**
	 * �����û�������������û�
 	 * @param account �û���
	 * @param password ����
	 * @return ���ز��ҵ����û�
	 */
	public User findByAccountAndPass(String account, String password ) {
		Session sesssion = sessionFactory.getCurrentSession();
		Query query = sesssion.createQuery("from User where account = ? and password = ?");
		query.setParameter(0, account);
		query.setParameter(1, password);
		List<User> list = query.list();
		
		if(list.size() == 0) {
			return null;	//δ�ҵ�����û�
		}else{
			return (User)list.get(0);
		}	
	}
	
	/**
	 * �����û��������û�
	 * @param account �û���
	 * @return �����ҵ����û���û�ҵ�����null
	 */
	public User findUserByAccount(String account){
		Session sesssion = sessionFactory.getCurrentSession();
		Query query = sesssion.createQuery("from User where account = ?"); 
		query.setParameter(0, account);
		List<User> list = query.list();
		
		if(list.size() == 0) {
			return null;	//δ�ҵ�����û�
		}else{
			return (User)list.get(0);
		}
	}
	
	/**
	 * ����û�
	 */
	public String addUser(User user){		
		Session sesssion = sessionFactory.getCurrentSession();
		Query query = sesssion.createQuery("from User where account = ?");
		query.setParameter(0, user.getAccount());
		List<User> list = query.list();
		if(list.size() == 0){
			sesssion.save(user);
			return "��ӳɹ�";
		}else{
			return "�û����Ѿ�����";
		}		
	}
	
	/**
	 * ɾ��ָ���û�
	 * @param user Ҫɾ�����û�
	 * @return true ɾ���ɹ�  false û�и��û�
	 */
	public void deleteUser(User user){
		
		Session sesssion = sessionFactory.getCurrentSession();
		if(user != null){
			sesssion.delete(user);		
		}
	}
	
	/**
	 * �����û���Ϣ
	 * @param user
	 * @return
	 */
	public String updateUser(User user) {
		
		Session sesssion = sessionFactory.getCurrentSession();
		sesssion.update(user);
		
		return "���³ɹ�";		
	}
	
	/**
	 * ��ҳ��ѯ
	 * @param user ��ѯ����������
	 * @param start ��ʼ��ѯ��λ��
	 * @param rows ��ѯ����Ϣ����
	 * @return �û��������
	 */
	public List<User> queryUser(User user, Integer start, Integer rows){
		
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(User.class);
		if(!user.getName().isEmpty() && !user.getName().equals("ȫ��")){
			c.add(Restrictions.eq("name", user.getName()));
		}
		if(!user.getAccount().isEmpty() && !user.getAccount().equals("ȫ��")){
			c.add(Restrictions.eq("account", user.getAccount()));
		}
		if(user.getRole() != null){
			c.add(Restrictions.eq("role", user.getRole()));
		}
		c.setFirstResult(start);
		c.setMaxResults(rows);
		c.add(Restrictions.ne("account", "sysadmin"));
		c.add(Restrictions.ne("account", "safeadmin"));
		c.add(Restrictions.ne("account", "auditadmin"));
		
		return c.list();
	}
	
	/**
	 * ��ѯ���������ļ�¼����
	 * @param user ������ѯ����
	 * @return ���������ļ�¼����
	 */
	public List<User> queryUser(User user){
		
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(User.class);
		if(user.getName() != null && !user.getName().equals("ȫ��")){
			c.add(Restrictions.eq("name", user.getName()));
		}
		if(user.getAccount() != null && !user.getAccount().equals("ȫ��")){
			c.add(Restrictions.eq("account", user.getAccount()));
		}
		if(user.getRole() != null){
			c.add(Restrictions.eq("role", user.getRole()));
		}

		return c.list();
	}

	/**
	 * �����û����ж��˻��Ƿ�����
	 * @param account �û���
	 * @return true �������� false û�б�����
	 */
	public boolean isLock(String account) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(User.class);
		c.add(Restrictions.eq("account", account));
		User user = (User) c.list().get(0);
		
		return user.getFlag();
	}
	
	/**
	 * �����û�����������ʱ��
	 * @param account �û���
	 * @return true �������� false û�б�����
	 */
	public Date lockTime(String account) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria c = session.createCriteria(User.class);
		c.add(Restrictions.eq("account", account));
		User user = (User) c.list().get(0);
		
		return user.getTime();
	}
}