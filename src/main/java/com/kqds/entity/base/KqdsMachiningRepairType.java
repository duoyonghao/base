/**  
  *
  * @Title:  KqdsMachiningRepairType.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月18日 下午2:49:13   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  KqdsMachiningRepairType   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月18日 下午2:49:13   
  *      
  */
public class KqdsMachiningRepairType implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;

	private String seqId;
	/**
	 * 牙位Id
	 */
	private String toothId;
	/**
	 * 一级类型Id
	 */
	private  String typeOne;
	/**
	 * 一级类型名称
	 */
	private String typeOneName;
	/**
	 * 三级类型Id
	 */
	private String typeSecond;
	/**
	 * 三级类型名称
	 */
	private String typeSecondName;
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
	  * @Title:  getToothId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getToothId() {
		return toothId;
	}
	/**  
	  * @Title:  setToothId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setToothId(String toothId) {
		this.toothId = toothId;
	}
	/**  
	  * @Title:  getTypeOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getTypeOne() {
		return typeOne;
	}
	/**  
	  * @Title:  setTypeOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setTypeOne(String typeOne) {
		this.typeOne = typeOne;
	}
	/**  
	  * @Title:  getTypeOneName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getTypeOneName() {
		return typeOneName;
	}
	/**  
	  * @Title:  setTypeOneName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setTypeOneName(String typeOneName) {
		this.typeOneName = typeOneName;
	}
	/**  
	  * @Title:  getTypeSecond <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getTypeSecond() {
		return typeSecond;
	}
	/**  
	  * @Title:  setTypeSecond <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setTypeSecond(String typeSecond) {
		this.typeSecond = typeSecond;
	}
	/**  
	  * @Title:  getTypeSecondName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getTypeSecondName() {
		return typeSecondName;
	}
	/**  
	  * @Title:  setTypeSecondName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setTypeSecondName(String typeSecondName) {
		this.typeSecondName = typeSecondName;
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
	  * @see Object#hashCode()
	  */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((createuser == null) ? 0 : createuser.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		result = prime * result + ((toothId == null) ? 0 : toothId.hashCode());
		result = prime * result + ((typeOne == null) ? 0 : typeOne.hashCode());
		result = prime * result + ((typeOneName == null) ? 0 : typeOneName.hashCode());
		result = prime * result + ((typeSecond == null) ? 0 : typeSecond.hashCode());
		result = prime * result + ((typeSecondName == null) ? 0 : typeSecondName.hashCode());
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
		KqdsMachiningRepairType other = (KqdsMachiningRepairType) obj;
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
		if (toothId == null) {
			if (other.toothId != null)
				return false;
		} else if (!toothId.equals(other.toothId))
			return false;
		if (typeOne == null) {
			if (other.typeOne != null)
				return false;
		} else if (!typeOne.equals(other.typeOne))
			return false;
		if (typeOneName == null) {
			if (other.typeOneName != null)
				return false;
		} else if (!typeOneName.equals(other.typeOneName))
			return false;
		if (typeSecond == null) {
			if (other.typeSecond != null)
				return false;
		} else if (!typeSecond.equals(other.typeSecond))
			return false;
		if (typeSecondName == null) {
			if (other.typeSecondName != null)
				return false;
		} else if (!typeSecondName.equals(other.typeSecondName))
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
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#toString()
	  */  
	@Override
	public String toString() {
		return "KqdsMachiningRepairType [seqId=" + seqId + ", toothId=" + toothId + ", typeOne=" + typeOne
				+ ", typeOneName=" + typeOneName + ", typeSecond=" + typeSecond + ", typeSecondName=" + typeSecondName
				+ ", organization=" + organization + ", createuser=" + createuser + ", createtime=" + createtime
				+ ", remark=" + remark + "]";
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
}
