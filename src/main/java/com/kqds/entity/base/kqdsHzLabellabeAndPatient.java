/**  
  *
  * @Title:  KQDSHzLabellabeAndPatient.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月22日 下午3:19:48   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  KQDSHzLabellabeAndPatient   
  * @Description:TODO(患者标签关联)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月22日 下午3:19:48   
  *      
  */
public class kqdsHzLabellabeAndPatient implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;
	
	/*
	 * Id
	 */
	private String seqId;
	/*
	 * 一级
	 */
	private String labelOneId;
	/*
	 * 一级
	 */
	private String labelOneName;
	/*
	 * 一级
	 */
	private String labelTwoId;
	/*
	 * 二级
	 */
	private String labelTwoName;
	/*
	 * 一级
	 */
	private String labelThreeId;
	/*
	 * 三级
	 */
	private String labelThreeName;
	/*
	 * 患者Id
	 */
	private String userSeqId;
	/*
	 * 患者档案Id
	 */
	private String userId;
	/*
	 * 患者姓名
	 */
	private String userName;
	/*
	 * 创建人
	 */
	private String createUser;
	/*
	 * 创建时间
	 */
	private String createTime;
	/*
	 * 备注
	 */
	private String remark;
	/*
	 * 备用
	 */
	private int status;
	/*
	 * 备用
	 */
	private String opinion;
	/*
	 * 备用
	 */
	private String reservedth;
	/*
	 * 备用
	 */
	private String reservedf;
	
	private String organization;
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
	  * @Title:  getLabelOneName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLabelOneName() {
		return labelOneName;
	}
	/**  
	  * @Title:  setLabelOneName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLabelOneName(String labelOneName) {
		this.labelOneName = labelOneName;
	}
	/**  
	  * @Title:  getLabelTwoName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLabelTwoName() {
		return labelTwoName;
	}
	/**  
	  * @Title:  setLabelTwoName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLabelTwoName(String labelTwoName) {
		this.labelTwoName = labelTwoName;
	}
	/**  
	  * @Title:  getLabelThreeName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLabelThreeName() {
		return labelThreeName;
	}
	/**  
	  * @Title:  setLabelThreeName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLabelThreeName(String labelThreeName) {
		this.labelThreeName = labelThreeName;
	}
	/**  
	  * @Title:  getUserSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUserSeqId() {
		return userSeqId;
	}
	/**  
	  * @Title:  setUserSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUserSeqId(String userSeqId) {
		this.userSeqId = userSeqId;
	}
	/**  
	  * @Title:  getUserId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUserId() {
		return userId;
	}
	/**  
	  * @Title:  setUserId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUserId(String userId) {
		this.userId = userId;
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
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	/**  
	  * @Title:  getLabelOneId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLabelOneId() {
		return labelOneId;
	}
	/**  
	  * @Title:  setLabelOneId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLabelOneId(String labelOneId) {
		this.labelOneId = labelOneId;
	}
	/**  
	  * @Title:  getLabelTwoId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLabelTwoId() {
		return labelTwoId;
	}
	/**  
	  * @Title:  setLabelTwoId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLabelTwoId(String labelTwoId) {
		this.labelTwoId = labelTwoId;
	}
	/**  
	  * @Title:  getLabelThreeId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLabelThreeId() {
		return labelThreeId;
	}
	/**  
	  * @Title:  setLabelThreeId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLabelThreeId(String labelThreeId) {
		this.labelThreeId = labelThreeId;
	}
	/**  
	  * @Title:  getReservedo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOpinion() {
		return opinion;
	}
	/**  
	  * @Title:  setReservedo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	/**  
	  * @Title:  getReservedth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReservedth() {
		return reservedth;
	}
	/**  
	  * @Title:  setReservedth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReservedth(String reservedth) {
		this.reservedth = reservedth;
	}
	/**  
	  * @Title:  getReservedf <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReservedf() {
		return reservedf;
	}
	/**  
	  * @Title:  setReservedf <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReservedf(String reservedf) {
		this.reservedf = reservedf;
	}

	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	@Override
	public String toString() {
		return "kqdsHzLabellabeAndPatient [seqId=" + seqId + ", labelOneId=" + labelOneId + ", labelOneName="
				+ labelOneName + ", labelTwoId=" + labelTwoId + ", labelTwoName=" + labelTwoName + ", labelThreeId="
				+ labelThreeId + ", labelThreeName=" + labelThreeName + ", userSeqId=" + userSeqId + ", userId="
				+ userId + ", userName=" + userName + ", createUser=" + createUser + ", createTime=" + createTime
				+ ", remark=" + remark + ", status=" + status + ", opinion=" + opinion + ", reservedth=" + reservedth
				+ ", reservedf=" + reservedf + ", organization=" + organization + "]";
	}
	
}
