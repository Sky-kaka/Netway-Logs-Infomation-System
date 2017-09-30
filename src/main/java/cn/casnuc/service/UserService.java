package cn.casnuc.service;

import java.util.Date;
import java.util.List;

import cn.casnuc.dao.UserDao;
import cn.casnuc.entity.PageInfo;
import cn.casnuc.entity.User;

public class UserService {
	
	// IOCע��
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * ��¼��ѯ
	 * @param emp
	 */
	public User findByAccountAndPass(String account, String password) {
		User user = userDao.findByAccountAndPass(account, password);
		return user;
	}
	
	/**
	 * �����û�
	 */
	public String addUser(User user){
		return userDao.addUser(user);
	}
	
	/**
	 * �����û���Ϣ
	 * @param user
	 * @return
	 */
	public String updateUser(User user) {
		
		return userDao.updateUser(user);
	}

	/**
	 * ������ѯ
	 * @param user	��ѯ��������
	 */
	public List<User> queryUser(User user) {
		return userDao.queryUser(user);
	}
	
	/**
	 * ��ҳ��ѯ�û�
	 * @param user	��ѯ��������
	 * @return	��ǰҳ�������
	 */
	public PageInfo queryUser(User user, Integer page, Integer rows) {
		
		PageInfo pageInfo = new PageInfo();
		
		// �����ѯ����ʼ�ڵ�
		Integer start = (page - 1) * rows;
				
		// ��ѯ���
		List<?> list = userDao.queryUser(user, start, rows);
		Integer allSize = userDao.queryUser(user).size();
		
		// ������ҳ��
		Integer allPage = null;
		if(allSize % rows == 0){
			allPage = allSize / rows;
		}else{
			allPage = allSize / rows + 1;
		}
				
		// ��װ��ѯ���
		pageInfo.setPerPageSize(rows);
		pageInfo.setAllPage(allPage);
		pageInfo.setAllSize(allSize);
		pageInfo.setCurrentPage(page);
		pageInfo.setList(list);
		
		return pageInfo;
	}
	
	/**
	 * ɾ��ָ���û�
	 * @param removeUser ��Ҫɾ�����û�
	 * @return true ɾ���ɹ��� false �޴��û�
	 */
	public User deleteUser(User user) {
		// �����û�
		user = userDao.findUserByAccount(user.getAccount());
		// �ҵ��û���ɾ��
		if(user != null){
			userDao.deleteUser(user);
		}
		
		return user;
	}
	
	/**
	 * �����û������ҳ�ָ���û�
	 * @param account �û���
	 * @return ��Ҫ���ҵ��û�����
	 */
	public User findByAccount(String account) {
		return userDao.findUserByAccount(account);
	}
	
	/**
	 * �����û����ж��˻��Ƿ�����
	 * @param account �û���
	 * @return true �������� false û�б�����
	 */
	public boolean isLock(String account){
		return userDao.isLock(account);
	}
	
	/**
	 * �����û�����������ʱ��
	 * @param account �û���
	 * @return true �������� false û�б�����
	 */
	public Date lockTime(String account) {
		return userDao.lockTime(account);
	}
}
