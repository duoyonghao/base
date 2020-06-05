package com.hudh.lclj.entity;
/**
 * 流程节点实体类
 * @author XKY
 *
 */
public class LcljNode {
	private String num; //流程顺序编号
	private String name; //节点名称
	private String limit; //当前节点时限
	private String stus; //流程节点状态办理状态 0：未完成  1：进行中   2： 已完成   3：超期
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getStus() {
		return stus;
	}
	public void setStus(String stus) {
		this.stus = stus;
	}
	
}
