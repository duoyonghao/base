/**  
  *
  * @Title:  KqdsMachiningRepairWay.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月18日 上午11:13:58   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

import java.io.Serializable;
import java.util.List;

/**  
  * 
  * @ClassName:  KqdsMachiningRepairWay   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月18日 上午11:13:58   
  *      
  */
public class KqdsMachiningRepairWay implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;
	
	private  String seqId;
	/**
	 * 加工单Id
	 */
	private String worksheetId;
	/**
	 * 左上牙位 1-8
	 */
	private String upleftTooth;
	/**
	 * 左下牙位 1-8
	 */
	private String leftLowerTooth;
	/**
	 * 右上牙位 1-8
	 */
	private String upperRightTooth;
	/**
	 * 右下牙位 1-8
	 */
	private String lowRightTooth;
	/**
	 * 修复类型集合
	 */
	private String repairProjectArr;
	/**
	 * 加工单状态
	 */
	private String status;
	/**
	 * 门诊
	 */
	private String organization;
	/**
	 * 创建人
	 */
	private String createuser;
	/**
	 * 创建时间
	 */
	private String createtime;
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
	  * @Title:  getUpleftTooth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUpleftTooth() {
		return upleftTooth;
	}
	/**  
	  * @Title:  setUpleftTooth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUpleftTooth(String upleftTooth) {
		this.upleftTooth = upleftTooth;
	}
	/**  
	  * @Title:  getLeftLowerTooth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLeftLowerTooth() {
		return leftLowerTooth;
	}
	/**  
	  * @Title:  setLeftLowerTooth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLeftLowerTooth(String leftLowerTooth) {
		this.leftLowerTooth = leftLowerTooth;
	}
	/**  
	  * @Title:  getUpperRightTooth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUpperRightTooth() {
		return upperRightTooth;
	}
	/**  
	  * @Title:  setUpperRightTooth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUpperRightTooth(String upperRightTooth) {
		this.upperRightTooth = upperRightTooth;
	}
	/**  
	  * @Title:  getLowRightTooth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLowRightTooth() {
		return lowRightTooth;
	}
	/**  
	  * @Title:  setLowRightTooth <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLowRightTooth(String lowRightTooth) {
		this.lowRightTooth = lowRightTooth;
	}
	/**  
	  * @Title:  getRepairProjectArr <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRepairProjectArr() {
		return repairProjectArr;
	}
	/**  
	  * @Title:  setRepairProjectArr <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRepairProjectArr(String repairProjectArr) {
		this.repairProjectArr = repairProjectArr;
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
	  * @see java.lang.Object#hashCode()   
	  */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((createuser == null) ? 0 : createuser.hashCode());
		result = prime * result + ((leftLowerTooth == null) ? 0 : leftLowerTooth.hashCode());
		result = prime * result + ((lowRightTooth == null) ? 0 : lowRightTooth.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		result = prime * result + ((upleftTooth == null) ? 0 : upleftTooth.hashCode());
		result = prime * result + ((upperRightTooth == null) ? 0 : upperRightTooth.hashCode());
		result = prime * result + ((worksheetId == null) ? 0 : worksheetId.hashCode());
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
		KqdsMachiningRepairWay other = (KqdsMachiningRepairWay) obj;
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
		if (leftLowerTooth == null) {
			if (other.leftLowerTooth != null)
				return false;
		} else if (!leftLowerTooth.equals(other.leftLowerTooth))
			return false;
		if (lowRightTooth == null) {
			if (other.lowRightTooth != null)
				return false;
		} else if (!lowRightTooth.equals(other.lowRightTooth))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
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
		if (upleftTooth == null) {
			if (other.upleftTooth != null)
				return false;
		} else if (!upleftTooth.equals(other.upleftTooth))
			return false;
		if (upperRightTooth == null) {
			if (other.upperRightTooth != null)
				return false;
		} else if (!upperRightTooth.equals(other.upperRightTooth))
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
	  * @see java.lang.Object#clone()   
	  */  
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "KqdsMachiningRepairWay [seqId=" + seqId + ", worksheetId=" + worksheetId + ", upleftTooth="
				+ upleftTooth + ", leftLowerTooth=" + leftLowerTooth + ", upperRightTooth=" + upperRightTooth
				+ ", lowRightTooth=" + lowRightTooth + ", organization=" + organization + ", createuser=" + createuser
				+ ", createtime=" + createtime + ", remark=" + remark + "]";
	}
	/**   
	  * <p>Title: finalize</p>   
	  * <p>Description: </p>   
	  * @throws Throwable   
	  * @see java.lang.Object#finalize()   
	  */  
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
}
