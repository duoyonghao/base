package com.hudh.ksll.entity;

import java.math.BigDecimal;

/**
 * 科室退还主表数据
 * @author ASUS
 *
 */
public class KsllReplaceMent {
	private String id;
	private String deptpart; //退还科室
	private String remark; //附加信息
	private String creator; //退还人
	private Integer status; //退还状态 0:退还未审批  1：同意
	private String createtime; //退还时间
	private String organization; //门诊
	private String sshouse; //退还仓库
	private BigDecimal goodprice; //退还时单价
	private Integer type; //标识退回还是内部消耗  1：退回  2：内部消耗
	private String floor; //标识退回还是内部消耗  1：退回  2：内部消耗
	/**  
	  * @Title:  getFloor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public String getFloor() {
		return floor;
	}
	/**  
	  * @Title:  setFloor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptpart() {
		return deptpart;
	}
	public void setDeptpart(String deptpart) {
		this.deptpart = deptpart;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getSshouse() {
		return sshouse;
	}
	public void setSshouse(String sshouse) {
		this.sshouse = sshouse;
	}
	public BigDecimal getGoodprice() {
		return goodprice;
	}
	public void setGoodprice(BigDecimal goodprice) {
		this.goodprice = goodprice;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
