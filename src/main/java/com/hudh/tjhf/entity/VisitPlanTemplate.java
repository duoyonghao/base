/**  
  *
  * @Title:  VisitPlanTemplate.java   
  * @Package com.hudh.tjhf.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月2日 上午11:10:57   
  * @version V1.0  
  */ 
package com.hudh.tjhf.entity;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  VisitPlanTemplate   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月2日 上午11:10:57   
  *      
  */
public class VisitPlanTemplate implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String SEQ_ID;
	/**
	 * 
	 */
	private String managarId;
	/**
	 * 
	 */
	private String plandays;
	/**
	 * 
	 */
	private String visittype;
	/**
	 * 
	 */
	private String ismandatory;
	
	/**
	 * 
	 */
	private String isautomatic;
	/**
	 * 
	 */
	private String visitstaff;
	/**
	 * 
	 */
	private String hfdept;
	/**
	 * 
	 */
	private String accepttype;
	/**
	 * 
	 */
	private String organization;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private String createuser;
	/**
	 * 
	 */
	private String createtime;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private String spare1;
	/**
	 * 
	 */
	private String spare2;
	
	/**   
	  * <p>Title: hashCode</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#hashCode()
	  */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SEQ_ID == null) ? 0 : SEQ_ID.hashCode());
		result = prime * result + ((accepttype == null) ? 0 : accepttype.hashCode());
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((createuser == null) ? 0 : createuser.hashCode());
		result = prime * result + ((isautomatic == null) ? 0 : isautomatic.hashCode());
		result = prime * result + ((ismandatory == null) ? 0 : ismandatory.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((plandays == null) ? 0 : plandays.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((spare1 == null) ? 0 : spare1.hashCode());
		result = prime * result + ((spare2 == null) ? 0 : spare2.hashCode());
		result = prime * result + ((visitstaff == null) ? 0 : visitstaff.hashCode());
		result = prime * result + ((visittype == null) ? 0 : visittype.hashCode());
		return result;
	}


	

	/**   
	 * @Title:  VisitPlanTemplate   
	 * @Description:    TODO(这里用一句话描述这个方法的作用)   
	 * @param:    
	 * @throws   
	 */  
	public VisitPlanTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}




	/**  
	  * @Title:  getSEQ_ID <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSEQ_ID() {
		return SEQ_ID;
	}
	/**  
	  * @Title:  setSEQ_ID <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSEQ_ID(String sEQ_ID) {
		SEQ_ID = sEQ_ID;
	}
	
	/**  
	  * @Title:  getManagarId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getManagarId() {
		return managarId;
	}


	/**  
	  * @Title:  setManagarId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setManagarId(String managarId) {
		this.managarId = managarId;
	}


	/**  
	  * @Title:  getPlanDays <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPlandays() {
		return plandays;
	}
	/**  
	  * @Title:  setPlanDays <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPlandays(String plandays) {
		this.plandays = plandays;
	}
	/**  
	  * @Title:  getVisitType <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getVisittype() {
		return visittype;
	}
	/**  
	  * @Title:  setVisitType <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}
	
	/**  
	  * @Title:  getIsmandatory <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsmandatory() {
		return ismandatory;
	}


	/**  
	  * @Title:  setIsmandatory <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsmandatory(String ismandatory) {
		this.ismandatory = ismandatory;
	}


	/**  
	  * @Title:  getIsautomatic <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsautomatic() {
		return isautomatic;
	}


	/**  
	  * @Title:  setIsautomatic <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsautomatic(String isautomatic) {
		this.isautomatic = isautomatic;
	}
	

	/**  
	  * @Title:  getHfdept <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHfdept() {
		return hfdept;
	}


	/**  
	  * @Title:  setHfdept <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHfdept(String hfdept) {
		this.hfdept = hfdept;
	}


	/**  
	  * @Title:  getVisitStaff <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getVisitstaff() {
		return visitstaff;
	}
	/**  
	  * @Title:  setVisitStaff <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setVisitstaff(String visitstaff) {
		this.visitstaff = visitstaff;
	}
	/**  
	  * @Title:  getAcceptType <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAcceptsype() {
		return accepttype;
	}
	/**  
	  * @Title:  setAcceptType <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAccepttype(String accepttype) {
		this.accepttype = accepttype;
	}
	/**  
	  * @Title:  getOrganization <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOrganization() {
		return organization;
	}
	/**  
	  * @Title:  setOrganization <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	/**  
	  * @Title:  getRemark <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRemark() {
		return remark;
	}
	/**  
	  * @Title:  setRemark <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**  
	  * @Title:  getCreartuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreateuser() {
		return createuser;
	}
	/**  
	  * @Title:  setCreartuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreatetuser(String createuser) {
		this.createuser = createuser;
	}
	/**  
	  * @Title:  getCrearttime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreatetime() {
		return createtime;
	}
	
	/**  
	  * @Title:  getStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getStatus() {
		return status;
	}


	/**  
	  * @Title:  setStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setStatus(String status) {
		this.status = status;
	}


	/**  
	  * @Title:  setCrearttime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**  
	  * @Title:  getSpare1 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSpare1() {
		return spare1;
	}
	/**  
	  * @Title:  setSpare1 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}
	/**  
	  * @Title:  getSpare2 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSpare2() {
		return spare2;
	}
	/**  
	  * @Title:  setSpare2 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}


	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#toString()
	  */  
	@Override
	public String toString() {
		return "VisitPlanTemplate [SEQ_ID=" + SEQ_ID + ", managarId=" + managarId + ", plandays=" + plandays
				+ ", visittype=" + visittype + ", ismandatory=" + ismandatory + ", isautomatic=" + isautomatic
				+ ", visitstaff=" + visitstaff + ", accepttype=" + accepttype + ", organization=" + organization
				+ ", remark=" + remark + ", createuser=" + createuser + ", createtime=" + createtime + ", spare1="
				+ spare1 + ", spare2=" + spare2 + "]";
	}

}
