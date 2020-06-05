package com.hudh.lclj.entity;

import java.util.List;
/**
 * 临床路径操作记录
 * @author XKY
 *
 */
public class LcljOperate {
	private String name;
	private String isComplate;
	private List<LcljOperateDetail> detail;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsComplate() {
		return isComplate;
	}
	public void setIsComplate(String isComplate) {
		this.isComplate = isComplate;
	}
	public List<LcljOperateDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<LcljOperateDetail> detail) {
		this.detail = detail;
	}
	
	
}
