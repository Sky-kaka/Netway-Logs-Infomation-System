package cn.casnuc.entity;

public class Permission {
	
	private int id;
	private boolean protectLog; // ������־
	private boolean attackLog;	// ������־
	private boolean operateLog;	// ������־
	private boolean transferConfig; // ��������
	private boolean protectConfig; // ��������
	private boolean keywordConfig; // �ؼ�������
	private boolean gatewayConfig; // ��������
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getProtectLog() {
		return protectLog;
	}
	public void setProtectLog(boolean protectLog) {
		this.protectLog = protectLog;
	}
	public boolean getAttackLog() {
		return attackLog;
	}
	public void setAttackLog(boolean attackLog) {
		this.attackLog = attackLog;
	}
	public boolean getOperateLog() {
		return operateLog;
	}
	public void setOperateLog(boolean operateLog) {
		this.operateLog = operateLog;
	}
	public boolean getTransferConfig() {
		return transferConfig;
	}
	public void setTransferConfig(boolean transferConfig) {
		this.transferConfig = transferConfig;
	}
	public boolean getProtectConfig() {
		return protectConfig;
	}
	public void setProtectConfig(boolean protectConfig) {
		this.protectConfig = protectConfig;
	}
	public boolean getKeywordConfig() {
		return keywordConfig;
	}
	public void setKeywordConfig(boolean keywordConfig) {
		this.keywordConfig = keywordConfig;
	}
	public boolean getGatewayConfig() {
		return gatewayConfig;
	}
	public void setGatewayConfig(boolean gatewayConfig) {
		this.gatewayConfig = gatewayConfig;
	}
	public String  toString(){
		return id+"";
	}
}
