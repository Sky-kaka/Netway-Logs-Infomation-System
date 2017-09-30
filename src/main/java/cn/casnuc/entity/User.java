package cn.casnuc.entity;

import java.util.Date;

public class User {

	private int id; // �û����
	private String account; // �û��˺�
	private String name; // �û�����
	private String password; // �û�����
	private String role; // �û���ɫ
	private Permission permission; // �û�Ȩ��
	private Date time; // �û��˻�����ʱ��
	private Integer count; // �û���������������
	private boolean flag; // �û��˻�������־λ
	private Date lastErrorTime; //���һ����������ʱ��
	private String sessionId;  //�û���¼��Ψһ��ʶ
	
	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Date getLastErrorTime() {
		return lastErrorTime;
	}

	public void setLastErrorTime(Date lastErrorTime) {
		this.lastErrorTime = lastErrorTime;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String toString() {
		return account + " - " + name + " - " + role;
	}

}
