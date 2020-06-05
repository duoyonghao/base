/**  
  *
  * @Title:  LcljApprovedMemo.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月23日 下午6:11:28   
  * @version V1.0  
  */ 
package com.hudh.lclj.entity;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  LcljApprovedMemo   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月23日 下午6:11:28   
  *      
  */
public class LcljApprovedMemo implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;

	private String SeqId;
	/**
	 * 临床Id
	 */
	private String LcljId;
	/**
	 * 临床名称
	 */
	private String LcljName;
	/**
	 * 节点Id
	 */
	private String nodeId;
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 节点时间
	 */
	private String nodetime;
	/**
	 * 挂号时间
	 */
	private String regtime;
	/**
	 * 下次预约时间
	 */
	private String nexttime;
	/**
	 * 就诊分类
	 */
	private String recesort;
	/**
	 * 挂号分类
	 */
	private String regsort;
	/**
	 * 挂号医生
	 */
	private String regdoctor;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 审核时间
	 */
	private String updatetime;
	/**
	 * 提交人
	 */
	private String createuser;
	/**
	 * 提交时间
	 */
	private String createtime;
	/**
	 * 审核人
	 */
	private String updateuser;
	/**
	 * 
	 */
	private String organization;
	/**
	 * 审核状态
	 */
	private String status;
	
	private String nodeLimit;//新增字段、用于记录节点勇士范围
	private String isRejectStatus;//新增字段、用于标识是否是退回后点击提交所新增的记录（1：是、0：不是）
	private String orderNumber;//新增字段、记录临床路径编号
	/**  
	  * @Title:  getSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSeqId() {
		return SeqId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getIsRejectStatus() {
		return isRejectStatus;
	}
	public void setIsRejectStatus(String isRejectStatus) {
		this.isRejectStatus = isRejectStatus;
	}
	public String getNodeLimit() {
		return nodeLimit;
	}
	public void setNodeLimit(String nodeLimit) {
		this.nodeLimit = nodeLimit;
	}
	/**  
	  * @Title:  setSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSeqId(String seqId) {
		SeqId = seqId;
	}
	/**  
	  * @Title:  getLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljId() {
		return LcljId;
	}
	/**  
	  * @Title:  setLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljId(String lcljId) {
		LcljId = lcljId;
	}
	/**  
	  * @Title:  getNodeId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getNodeId() {
		return nodeId;
	}
	/**  
	  * @Title:  setNodeId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**  
	  * @Title:  getNodeName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getNodeName() {
		return nodeName;
	}
	/**  
	  * @Title:  setNodeName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**  
	  * @Title:  getNodetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getNodetime() {
		return nodetime;
	}
	/**  
	  * @Title:  setNodetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setNodetime(String nodetime) {
		this.nodetime = nodetime;
	}
	/**  
	  * @Title:  getRegtime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRegtime() {
		return regtime;
	}
	/**  
	  * @Title:  setRegtime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	/**  
	  * @Title:  getNexttime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getNexttime() {
		return nexttime;
	}
	/**  
	  * @Title:  setNexttime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setNexttime(String nexttime) {
		this.nexttime = nexttime;
	}
	/**  
	  * @Title:  getRecesort <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRecesort() {
		return recesort;
	}
	/**  
	  * @Title:  setRecesort <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRecesort(String recesort) {
		this.recesort = recesort;
	}
	/**  
	  * @Title:  getRegsort <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRegsort() {
		return regsort;
	}
	/**  
	  * @Title:  setRegsort <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRegsort(String regsort) {
		this.regsort = regsort;
	}
	/**  
	  * @Title:  getRegdoctor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRegdoctor() {
		return regdoctor;
	}
	/**  
	  * @Title:  setRegdoctor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRegdoctor(String regdoctor) {
		this.regdoctor = regdoctor;
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
	  * @Title:  getUpdatetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUpdatetime() {
		return updatetime;
	}
	/**  
	  * @Title:  setUpdatetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	/**  
	  * @Title:  getUpdateuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUpdateuser() {
		return updateuser;
	}
	/**  
	  * @Title:  setUpdateuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
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
	  * @Title:  getLcljName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljName() {
		return LcljName;
	}
	/**  
	  * @Title:  setLcljName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljName(String lcljName) {
		LcljName = lcljName;
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
	  * <p>Title: hashCode</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#hashCode()   
	  */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LcljId == null) ? 0 : LcljId.hashCode());
		result = prime * result + ((SeqId == null) ? 0 : SeqId.hashCode());
		result = prime * result + ((nexttime == null) ? 0 : nexttime.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result + ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result + ((nodetime == null) ? 0 : nodetime.hashCode());
		result = prime * result + ((recesort == null) ? 0 : recesort.hashCode());
		result = prime * result + ((regdoctor == null) ? 0 : regdoctor.hashCode());
		result = prime * result + ((regsort == null) ? 0 : regsort.hashCode());
		result = prime * result + ((regtime == null) ? 0 : regtime.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updatetime == null) ? 0 : updatetime.hashCode());
		result = prime * result + ((updateuser == null) ? 0 : updateuser.hashCode());
		return result;
	}
	/**   
	  * <p>Title: equals</p>   
	  * <p>Description: </p>   
	  * @param obj
	  * @return   
	  * @see java.lang.Object#equals(java.lang.Object)   
	  */  
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LcljApprovedMemo other = (LcljApprovedMemo) obj;
		if (LcljId == null) {
			if (other.LcljId != null)
				return false;
		} else if (!LcljId.equals(other.LcljId))
			return false;
		if (SeqId == null) {
			if (other.SeqId != null)
				return false;
		} else if (!SeqId.equals(other.SeqId))
			return false;
		if (nexttime == null) {
			if (other.nexttime != null)
				return false;
		} else if (!nexttime.equals(other.nexttime))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		if (nodetime == null) {
			if (other.nodetime != null)
				return false;
		} else if (!nodetime.equals(other.nodetime))
			return false;
		if (recesort == null) {
			if (other.recesort != null)
				return false;
		} else if (!recesort.equals(other.recesort))
			return false;
		if (regdoctor == null) {
			if (other.regdoctor != null)
				return false;
		} else if (!regdoctor.equals(other.regdoctor))
			return false;
		if (regsort == null) {
			if (other.regsort != null)
				return false;
		} else if (!regsort.equals(other.regsort))
			return false;
		if (regtime == null) {
			if (other.regtime != null)
				return false;
		} else if (!regtime.equals(other.regtime))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updatetime == null) {
			if (other.updatetime != null)
				return false;
		} else if (!updatetime.equals(other.updatetime))
			return false;
		if (updateuser == null) {
			if (other.updateuser != null)
				return false;
		} else if (!updateuser.equals(other.updateuser))
			return false;
		return true;
	}
	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "LcljApprovedMemo [SeqId=" + SeqId + ", LcljId=" + LcljId + ", nodeId=" + nodeId + ", nodeName="
				+ nodeName + ", nodetime=" + nodetime + ", regtime=" + regtime + ", nexttime=" + nexttime
				+ ", recesort=" + recesort + ", regsort=" + regsort + ", regdoctor=" + regdoctor + ", remark=" + remark
				+ ", updatetime=" + updatetime + ", updateuser=" + updateuser + ", status=" + status + "]";
	}
	
}
