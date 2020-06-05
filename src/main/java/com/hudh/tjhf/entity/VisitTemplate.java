/**  
  *
  * @Title:  VisitTemplate.java   
  * @Package com.hudh.tjhf.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月2日 上午10:55:41   
  * @version V1.0  
  */ 
package com.hudh.tjhf.entity;

import java.io.Serializable;

/**  
  * dyh
  * @ClassName:  VisitTemplate   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月2日 上午10:55:41   
  *      
  */
public class VisitTemplate implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String seqId;
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	/**
	 * 
	 */
	private String planName;
	
	/**
	 * 
	 */
	private String hfdept;
	/**
	 * 
	 */
	private String explanation;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private String creartuser;
	/**
	 * 
	 */
	private String crearttime;
	/**
	 * 
	 */
	private String organization;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private String spare2;
	/**  
	  * @Title:  getSEQ_ID <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	/**  
	  * @Title:  getPlanName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPlanName() {
		return planName;
	}
	
	/**  
	  * @Title:  getHfdpet <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHfdept() {
		return hfdept;
	}
	/**  
	  * @Title:  setHfdpet <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHfdept(String hfdept) {
		this.hfdept = hfdept;
	}
	/**  
	  * @Title:  setPlanName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	/**  
	  * @Title:  getExplanation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExplanation() {
		return explanation;
	}
	/**  
	  * @Title:  setExplanation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCreartuser() {
		return creartuser;
	}
	/**  
	  * @Title:  setCreartuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreartuser(String creartuser) {
		this.creartuser = creartuser;
	}
	/**  
	  * @Title:  getCreartTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCrearttTime() {
		return crearttime;
	}
	/**  
	  * @Title:  setCreartTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCrearttime(String crearttime) {
		this.crearttime = crearttime;
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
	  * @Title:  getSerialversionuid <BR>  
	  * @Description: please write your description <BR>  
	  * @return: long <BR>  
	  */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
