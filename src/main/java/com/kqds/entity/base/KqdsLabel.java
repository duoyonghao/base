/**  
  *
  * @Title:  KqdsLabel.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 上午11:11:35   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  KqdsLabel   
  * @Description:TODO(患者印象标签库)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月14日 上午11:11:35   
  *      
  */
public class KqdsLabel implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;
	
	/**
	 * SEQ_ID
	 */
	private String seqId;
	/**
	 * 标签名称
	 */
	private String leveLabel;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 修改时间
	 */
	private String updateTime;
	/**
	 *父级Id
	 */
	private String parentId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 预留字段1
	 */
	private String parentName;
	/**
	 * 预留字段2
	 */
	private String status;
	/**
	 * 预留字段3
	 */
	private String reservedt;
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
	  * @Title:  getLeveLabel <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLeveLabel() {
		return leveLabel;
	}
	/**  
	  * @Title:  setLeveLabel <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLeveLabel(String leveLabel) {
		this.leveLabel = leveLabel;
	}
	/**  
	  * @Title:  getCreateTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreateTime() {
		return createTime;
	}
	/**  
	  * @Title:  setCreateTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**  
	  * @Title:  getCreateUser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreateUser() {
		return createUser;
	}
	/**  
	  * @Title:  setCreateUser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**  
	  * @Title:  getUpdateTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUpdateTime() {
		return updateTime;
	}
	/**  
	  * @Title:  setUpdateTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**  
	  * @Title:  getParentId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getParentId() {
		return parentId;
	}
	/**  
	  * @Title:  setParentId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	  * @Title:  getReservedo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getParentName() {
		return parentName;
	}
	/**  
	  * @Title:  setReservedo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	/**  
	  * @Title:  getReservedt <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReservedt() {
		return reservedt;
	}
	/**  
	  * @Title:  setReservedt <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReservedt(String reservedt) {
		this.reservedt = reservedt;
	}
	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#toString()
	  */  
	@Override
	public String toString() {
		return "KqdsLabel [seqId=" + seqId + ", leveLabel=" + leveLabel + ", createTime=" + createTime + ", createUser="
				+ createUser + ", updateTime=" + updateTime + ", parentId=" + parentId + ", remark=" + remark
				+ ", parentName=" + parentName + ", status=" + status + ", reservedt=" + reservedt + "]";
	}

}
