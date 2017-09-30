package cn.casnuc.entity;

public class AttackQuery {
	
	private String attackTarget;
	private String attackType;
	private String startDate;
	private String endDate;
	private String sourceMac;
	private String targetIp;
	private String targetPort;
	
	public int getAttackTarget() {		
		if(attackTarget.equals("ҵ���")){
			return 1;
		}else if(attackTarget.equals("�����")){
			return 2;
		}else {
			return 999;
		}
	}
	public void setAttackTarget(String attackTarget) {
		this.attackTarget = attackTarget;
	}
	public int getAttackType() {
		if(attackType.equals("�˿�ɨ��")){
			return 1;
		}else if(attackType.equals("ICMP����")){
			return 2;
		}else if(attackType.equals("SYN����")){
			return 3;
		}else if(attackType.equals("UDP����")){
			return 4;
		}else {
			return 999;
		}
	}
	public void setAttackType(String attackType) {
		this.attackType = attackType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSourceMac() {
		return sourceMac;
	}
	public void setSourceMac(String sourceMac) {
		this.sourceMac = sourceMac;
	}
	public String getTargetIp() {
		return targetIp;
	}
	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}
	public int getTargetPort() {
		if(targetPort.equals("ȫ��")){
			return -1;
		}else{
			return Integer.parseInt(targetPort);
		}
	}
	public void setTargetPort(String targetPort) {
		this.targetPort = targetPort;
	}

	public String toString(){
		return attackTarget+" : "+attackType+" : "+startDate+" : "+endDate+" : "+sourceMac+" : "+targetIp+" : "+targetPort;
	}
	
}
