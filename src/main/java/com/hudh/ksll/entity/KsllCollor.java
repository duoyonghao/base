package com.hudh.ksll.entity;

/**
 * 科室领料主表
 * @author ASUS
 */
public class KsllCollor {
	private String id;
	private String deptpart; //领用科室
	private Integer type; //出库方式     1:领用出库  3：换货出库  5：退货出库  7：其他出库
	private String remark; //附件说明
	private String creator; //领用人
	private Integer status; //领用状态  0:备货中  1：已备货  3：已领取
	private String createtime; //领用时间
	private String organization; //门诊编号
	private String sshouse; //领用仓库
	private String floor;//楼层
	/**  
	  * @Title:  getFloor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getFloor() {
		return floor;
	}
	/**  
	  * @Title:  setFloor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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

	
	
}
