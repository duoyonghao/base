package com.hudh.lclj.entity;

/**
 * 临床路径流程记录实体类
 * @author ASUS
 *
 */
public class LcljWorklist {
	private String id;
	private String nodeId; //节点id
	private String nodeName; //节点名称
	private Integer nodeStatus; //节点状态   1：进行中   2： 已完成   3：超期
	private Integer flowStatus; //流程状态   1：进行中   2：已完结
	private String dataId; //对应每个环节的操作记录数据id
	private String orderNumber; //对应创建时的临床路径编号
	private String flowInfo; //对应当前节点的所有流程节点信息
	private String operate; //执行的操作
	private Integer examine; //审核状态
	private String createtime; //操作时间
	private String creator; //操作人
	private String organization;
	private String viewUrl; //当前节点的操作路径
	private String flowCode; //流程code
	private Integer overdue; //是否超期 0：否  1：是

	private String dateDue; //到期提醒时间
	private String repairPhysician; //修复医生 根据医生和到期时间进行节点的提醒

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Integer getNodeStatus() {
		return nodeStatus;
	}
	public void setNodeStatus(Integer nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	public Integer getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(Integer flowStatus) {
		this.flowStatus = flowStatus;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getFlowInfo() {
		return flowInfo;
	}
	public void setFlowInfo(String flowInfo) {
		this.flowInfo = flowInfo;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public Integer getExamine() {
		return examine;
	}
	public void setExamine(Integer examine) {
		this.examine = examine;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public Integer getOverdue() {
		return overdue;
	}
	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	public String getDateDue() {
		return dateDue;
	}

	public void setDateDue(String dateDue) {
		this.dateDue = dateDue;
	}

	public String getRepairPhysician() {
		return repairPhysician;
	}

	public void setRepairPhysician(String repairPhysician) {
		this.repairPhysician = repairPhysician;
	}
}
