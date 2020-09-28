package com.hudh.lclj.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 临床路径节点实体类
 * @author ASUS
 *
 */
@JsonAutoDetect
public class LcljNodeConfig implements Comparable{
	private String id;
	private Integer num; //编号 节点顺序
	private String nodeId; //节点id唯一标识
	private String nodeName; //节点名称
	private String authorType; //办理人类型 部门或者为创建时的一个字段的值
	private String author; //办理人
	private String viewUrl; //操作界面的url
	private String organization; //门诊
	private String creatrtime; //创建时间
	private String nodeLimit; //节点时限
	private String limitType; //节点时限显示的类型 以天或月为单位显示  1:天 2:周  3:月
	private String flowCode; //所属流程
	private String stus; //流程节点状态办理状态 0：未完成  1：进行中   2： 已完成   3：超期
	private String limitBench; //超期基准节点
	private String flowType; //流程分类
	private String dentalJaw; //上下颌  1：上颌  2：下颌
	private String articleType; //项目分类  1：种植  2：正畸
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	public String getAuthorType() {
		return authorType;
	}
	public void setAuthorType(String authorType) {
		this.authorType = authorType;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getCreatrtime() {
		return creatrtime;
	}
	public void setCreatrtime(String creatrtime) {
		this.creatrtime = creatrtime;
	}
	public String getNodeLimit() {
		return nodeLimit;
	}
	public void setNodeLimit(String nodeLimit) {
		this.nodeLimit = nodeLimit;
	}
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getStus() {
		return stus;
	}
	public void setStus(String stus) {
		this.stus = stus;
	}
	public String getLimitBench() {
		return limitBench;
	}
	public void setLimitBench(String limitBench) {
		this.limitBench = limitBench;
	}
	@Override
	public int compareTo(Object o) {
		if(o instanceof LcljNodeConfig){
			LcljNodeConfig emp = (LcljNodeConfig) o;
			return this.num-emp.getNum();//按照年龄升序排序
        }
        throw new ClassCastException("不能转换为Emp类型的对象...");
	}
	
}
