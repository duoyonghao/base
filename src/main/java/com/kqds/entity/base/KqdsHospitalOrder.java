package com.kqds.entity.base;

public class KqdsHospitalOrder {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String orderno;

	private String ordertype;

	private String organization;

	private String askperson;

	private String ordertime;

	private String orderitemtype;

	private String orderitemdetail;

	private String starttime;

	private String endtime;

	private String orderremark;

	private Integer orderstatus;

	private String remark;

	private String regno;

	private String username;

	private String delperson;

	private String delreason;

	private String regdept;

	private Integer cjstatus;

	private Integer isdelete;
	
	private String order_number;//临床路径编号
	
	private String nodeId;//灵床路径具体流程节点id

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno == null ? null : orderno.trim();
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype == null ? null : ordertype.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getAskperson() {
		return askperson;
	}

	public void setAskperson(String askperson) {
		this.askperson = askperson == null ? null : askperson.trim();
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime == null ? null : ordertime.trim();
	}

	public String getOrderitemtype() {
		return orderitemtype;
	}

	public void setOrderitemtype(String orderitemtype) {
		this.orderitemtype = orderitemtype == null ? null : orderitemtype.trim();
	}

	public String getOrderitemdetail() {
		return orderitemdetail;
	}

	public void setOrderitemdetail(String orderitemdetail) {
		this.orderitemdetail = orderitemdetail == null ? null : orderitemdetail.trim();
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime == null ? null : starttime.trim();
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime == null ? null : endtime.trim();
	}

	public String getOrderremark() {
		return orderremark;
	}

	public void setOrderremark(String orderremark) {
		this.orderremark = orderremark == null ? null : orderremark.trim();
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno == null ? null : regno.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getDelperson() {
		return delperson;
	}

	public void setDelperson(String delperson) {
		this.delperson = delperson == null ? null : delperson.trim();
	}

	public String getDelreason() {
		return delreason;
	}

	public void setDelreason(String delreason) {
		this.delreason = delreason == null ? null : delreason.trim();
	}

	public String getRegdept() {
		return regdept;
	}

	public void setRegdept(String regdept) {
		this.regdept = regdept == null ? null : regdept.trim();
	}

	public Integer getCjstatus() {
		return cjstatus;
	}

	public void setCjstatus(Integer cjstatus) {
		this.cjstatus = cjstatus;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
}