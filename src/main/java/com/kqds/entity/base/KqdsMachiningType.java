/**  
  *
  * @Title:  KqdsMachiningType.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月13日 上午9:02:17   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  KqdsMachiningType   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月13日 上午9:02:17   
  *      
  */
public class KqdsMachiningType implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;
	
	private String seqId;

	private String typename;
	
	private String parentId;

	private String isCategory;

	private String createuser;

	private String createtime;

	private Integer useflag;

	private String organization;
	
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
	  * @Title:  getTypename <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getTypename() {
		return typename;
	}

	/**  
	  * @Title:  setTypename <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setTypename(String typename) {
		this.typename = typename;
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
	  * @Title:  getTypeno <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsCategory() {
		return isCategory;
	}

	/**  
	  * @Title:  setTypeno <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsCategory(String isCategory) {
		this.isCategory = isCategory;
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
	  * @Title:  getUseflag <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getUseflag() {
		return useflag;
	}

	/**  
	  * @Title:  setUseflag <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
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
	  * @see java.lang.Object#hashCode()   
	  */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((createuser == null) ? 0 : createuser.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		result = prime * result + ((typename == null) ? 0 : typename.hashCode());
		result = prime * result + ((isCategory == null) ? 0 : isCategory.hashCode());
		result = prime * result + ((useflag == null) ? 0 : useflag.hashCode());
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
		KqdsMachiningType other = (KqdsMachiningType) obj;
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
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
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
		if (typename == null) {
			if (other.typename != null)
				return false;
		} else if (!typename.equals(other.typename))
			return false;
		if (isCategory == null) {
			if (other.isCategory != null)
				return false;
		} else if (!isCategory.equals(other.isCategory))
			return false;
		if (useflag == null) {
			if (other.useflag != null)
				return false;
		} else if (!useflag.equals(other.useflag))
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
		return "KqdsMachiningType [seqId=" + seqId + ", typename=" + typename + ", parentId=" + parentId + ", isCategory="
				+ isCategory + ", createuser=" + createuser + ", createtime=" + createtime + ", useflag=" + useflag
				+ ", organization=" + organization + ", remark=" + remark + "]";
	}

}
