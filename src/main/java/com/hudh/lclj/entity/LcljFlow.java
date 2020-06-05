package com.hudh.lclj.entity;

/**
 * 临床路径流程实体
 * @author ASUS
 *
 */
public class LcljFlow {
	private String id;
	private String flowName; //流程名称
	private String flowCode; //流程编号
	private String remark; //备注
	private String createtime; //创建时间
	private Integer num; //顺序号
	private String flowType; //流程分类
	private String dentalJaw; //上下颌  1：上颌  2：下颌
	private String articleType; //项目分类  1：种植  2：正畸
	private String organization; //门诊 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getDentalJaw() {
		return dentalJaw;
	}
	public void setDentalJaw(String dentalJaw) {
		this.dentalJaw = dentalJaw;
	}
	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
}
