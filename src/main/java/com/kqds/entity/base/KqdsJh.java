package com.kqds.entity.base;

public class KqdsJh {
	private String id;//id
	private String usercode;//患者编号
	private String patientName;//患者姓名
	private String askperson;//咨询id
	private String createtime;//创建时间
	private String sex;//性别
	private String operator;//放射科操作人员
	private String createuser;//创建人员
	private String status;//状态1：排队 2：进行中
	private int del;//是否超时
	private String organization;//东西院
	private String regid;//挂号id
	private String remark;//超时备注
	private int age;//患者年龄
	private String items;//拍片类型
	private String surgeryStatus;//手术状态
	private String starttime;//拍片开始时间
	private String endtime;//拍片结束时间
	private String floor;//楼层
	private int number;//排队序号
	private String joint;//关节
	private String uplefttoothbit;//牙位
	private String uperrighttoothbit;//牙位
	private String leftlowertoothbit;//牙位
	private String lowrighttoothbit;//牙位
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getSurgeryStatus() {
		return surgeryStatus;
	}
	public void setSurgeryStatus(String surgeryStatus) {
		this.surgeryStatus = surgeryStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getAskperson() {
		return askperson;
	}
	public void setAskperson(String askperson) {
		this.askperson = askperson;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRegid() {
		return regid;
	}
	public void setRegid(String regid) {
		this.regid = regid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getJoint() {
		return joint;
	}
	public void setJoint(String joint) {
		this.joint = joint;
	}
	public String getUplefttoothbit() {
		return uplefttoothbit;
	}
	public void setUplefttoothbit(String uplefttoothbit) {
		this.uplefttoothbit = uplefttoothbit;
	}
	public String getUperrighttoothbit() {
		return uperrighttoothbit;
	}
	public void setUperrighttoothbit(String uperrighttoothbit) {
		this.uperrighttoothbit = uperrighttoothbit;
	}
	public String getLeftlowertoothbit() {
		return leftlowertoothbit;
	}
	public void setLeftlowertoothbit(String leftlowertoothbit) {
		this.leftlowertoothbit = leftlowertoothbit;
	}
	public String getLowrighttoothbit() {
		return lowrighttoothbit;
	}
	public void setLowrighttoothbit(String lowrighttoothbit) {
		this.lowrighttoothbit = lowrighttoothbit;
	}
}
