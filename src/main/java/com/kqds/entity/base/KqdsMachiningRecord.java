/**  
  *
  * @Title:  KqdsMachiningRecord.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月18日 上午11:25:03   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  KqdsMachiningRecord   
  * @Description:TODO(流转记录)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月18日 上午11:25:03   
  *      
  */
public class KqdsMachiningRecord implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;
	
	private String seqId;
	/**
	 * 加工单Id
	 */
	private String worksheetId;
	/**
	 * 加工单号
	 */
	private String orderNumber;
	/**
	 * 系统单号
	 */
	private String systemNumber;
	/**
	 * 患者档案号
	 */
	private String userCode;
	/**
	 * 患者姓名
	 */
	private String userName;
	/**
	 * 流程状态
	 */
	private String status;
	/**
	 * 操作内容
	 */
	private String operationalContext;
	/**
	 * 操作人
	 */
	private String createuser;
	/**
	 * 操作时间
	 */
	private String createtime;
	/**
	 * 物料预留
	 */
	private String material;
	/**
	 * 门诊
	 */
	private String organization;
	/**
	 * 备注
	 */
	private String remark;
	/**  
	  * @Title:  getSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSeqId() {
		return seqId;
	}
	/**  
	  * @Title:  setSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	/**  
	  * @Title:  getWorksheetId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getWorksheetId() {
		return worksheetId;
	}
	/**  
	  * @Title:  setWorksheetId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setWorksheetId(String worksheetId) {
		this.worksheetId = worksheetId;
	}
	/**  
	  * @Title:  getOrderNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**  
	  * @Title:  setOrderNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**  
	  * @Title:  getSystemNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSystemNumber() {
		return systemNumber;
	}
	/**  
	  * @Title:  setSystemNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSystemNumber(String systemNumber) {
		this.systemNumber = systemNumber;
	}
	/**  
	  * @Title:  getUserCode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUserCode() {
		return userCode;
	}
	/**  
	  * @Title:  setUserCode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**  
	  * @Title:  getUserName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUserName() {
		return userName;
	}
	/**  
	  * @Title:  setUserName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUserName(String userName) {
		this.userName = userName;
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
	  * @Title:  getOperationalContext <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOperationalContext() {
		return operationalContext;
	}
	/**  
	  * @Title:  setOperationalContext <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOperationalContext(String operationalContext) {
		this.operationalContext = operationalContext;
	}
	/**  
	  * @Title:  getCreateuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreateuser() {
		return createuser;
	}
	/**  
	  * @Title:  setCreateuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	/**  
	  * @Title:  getCreatetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreatetime() {
		return createtime;
	}
	/**  
	  * @Title:  setCreatetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**  
	  * @Title:  getMaterial <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getMaterial() {
		return material;
	}
	/**  
	  * @Title:  setMaterial <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setMaterial(String material) {
		this.material = material;
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
	  * <p>Title: hashCode</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#hashCode()
	  */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((createuser == null) ? 0 : createuser.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
		result = prime * result + ((operationalContext == null) ? 0 : operationalContext.hashCode());
		result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((systemNumber == null) ? 0 : systemNumber.hashCode());
		result = prime * result + ((userCode == null) ? 0 : userCode.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((worksheetId == null) ? 0 : worksheetId.hashCode());
		return result;
	}
	/**   
	  * <p>Title: equals</p>   
	  * <p>Description: </p>   
	  * @param obj
	  * @return   
	  * @see Object#equals(Object)
	  */  
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KqdsMachiningRecord other = (KqdsMachiningRecord) obj;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (createuser == null) {
			if (other.createuser != null)
				return false;
		} else if (!createuser.equals(other.createuser))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (operationalContext == null) {
			if (other.operationalContext != null)
				return false;
		} else if (!operationalContext.equals(other.operationalContext))
			return false;
		if (orderNumber == null) {
			if (other.orderNumber != null)
				return false;
		} else if (!orderNumber.equals(other.orderNumber))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (seqId == null) {
			if (other.seqId != null)
				return false;
		} else if (!seqId.equals(other.seqId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (systemNumber == null) {
			if (other.systemNumber != null)
				return false;
		} else if (!systemNumber.equals(other.systemNumber))
			return false;
		if (userCode == null) {
			if (other.userCode != null)
				return false;
		} else if (!userCode.equals(other.userCode))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (worksheetId == null) {
			if (other.worksheetId != null)
				return false;
		} else if (!worksheetId.equals(other.worksheetId))
			return false;
		return true;
	}
	/**   
	  * <p>Title: clone</p>   
	  * <p>Description: </p>   
	  * @return
	  * @throws CloneNotSupportedException   
	  * @see Object#clone()
	  */  
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	/**   
	  * <p>Title: finalize</p>   
	  * <p>Description: </p>   
	  * @throws Throwable   
	  * @see Object#finalize()
	  */  
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	
	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#toString()
	  */  
	@Override
	public String toString() {
		return "KqdsMachiningRecord [seqId=" + seqId + ", worksheetId=" + worksheetId + ", orderNumber=" + orderNumber
				+ ", systemNumber=" + systemNumber + ", userCode=" + userCode + ", userName=" + userName + ", status="
				+ status + ", operationalContext=" + operationalContext + ", createuser=" + createuser + ", createtime="
				+ createtime + ", material=" + material + ", remark=" + remark + "]";
	}
	

}
