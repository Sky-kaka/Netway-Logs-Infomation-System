package cn.casnuc.entity;

public class TransferResult {
	
	private String transferDirection;
	private String serviceType;
	private String transferCount;
	private String inNetIp;
	private String inNetMac;
	private String outNetIp;
	private String outNetMac;
	private String remarks;	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTransferDirection() {
		return transferDirection;
	}
	public void setTransferDirection(String transferDirection) {
		this.transferDirection = transferDirection;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getTransferCount() {
		return transferCount;
	}
	public void setTransferCount(String transferCount) {
		this.transferCount = transferCount;
	}
	public String getInNetIp() {
		return inNetIp;
	}
	public void setInNetIp(String inNetIp) {
		this.inNetIp = inNetIp;
	}
	public String getInNetMac() {
		return inNetMac;
	}
	public void setInNetMac(String inNetMac) {
		this.inNetMac = inNetMac;
	}
	public String getOutNetIp() {
		return outNetIp;
	}
	public void setOutNetIp(String outNetIp) {
		this.outNetIp = outNetIp;
	}
	public String getOutNetMac() {
		return outNetMac;
	}
	public void setOutNetMac(String outNetMac) {
		this.outNetMac = outNetMac;
	}
	
	public void dataConver(TransferConfig transferConfig){
		
		inNetIp = inet_ntoa(transferConfig.getInNetIp());
		inNetMac = toMac(transferConfig.getInNetMac());
		
		outNetIp = inet_ntoa(transferConfig.getOutNetIp());
		outNetMac = toMac(transferConfig.getOutNetMac());
		
		if(transferConfig.getTransferDirection() == 1){
			transferDirection = "������";
		}else{
			transferDirection = "���򵼳�";
		}
		
		if(transferConfig.getServiceType() == 1){
			serviceType = "MDC����";
		}else{
			serviceType = "DNC����";
		}

		transferCount= ""+transferConfig.getTransferCount();
		
		remarks = transferConfig.getRemarks();
	}
	
	/**
	 * ������ת��ΪIP��ַ
	 * @param num
	 * @return
	 */
	public String inet_ntoa(Integer num) {  
		
		//ת��Ϊ�޷�����
		long addr = num & 0x0FFFFFFFFl;
		
		return ((addr & 0xff000000) >> 24) + "." + ((addr & 0xff0000) >> 16)  
		            + "." + ((addr & 0xff00) >> 8) + "." + ((addr & 0xff));  
	}
	
	/**
	 * ������ת��ΪMAC��ַ
	 * @param num ����Դ
	 * @return
	 */
	public String toMac(Long num){
		
		String macString = Long.toHexString(num);
		
		String regex = "(.{2})";
		
		String string = macString.replaceAll (regex, "$1 : ");
		
		return string.substring(0, string.length()-3);
		
	}
}
