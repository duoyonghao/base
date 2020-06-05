/**  
  *
  * @Title:  HUDHacography.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2020年5月30日 下午2:35:03   
  * @version V1.0  
  */ 
package com.hudh.lclj.entity;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  HUDHacography   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2020年5月30日 下午2:35:03   
  *      
  */
public class HudhAcography implements Serializable{

	 private String seqId;
	 private String lcljId;
	 private String lcljNum;
	 private String userId;
	 private String doctorname;
	 private String writetime;
	 private String reviewrecord;
	 private String createuser;
	 private String createtime;
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
	  * @Title:  getLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljId() {
		return lcljId;
	}
	/**  
	  * @Title:  setLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljId(String lcljId) {
		this.lcljId = lcljId;
	}
	/**  
	  * @Title:  getLcljNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljNum() {
		return lcljNum;
	}
	/**  
	  * @Title:  setLcljNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljNum(String lcljNum) {
		this.lcljNum = lcljNum;
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
	  * @Title:  getDoctorname <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDoctorname() {
		return doctorname;
	}
	/**  
	  * @Title:  setDoctorname <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}
	/**  
	  * @Title:  getWritetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getWritetime() {
		return writetime;
	}
	/**  
	  * @Title:  setWritetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	/**  
	  * @Title:  getReviewrecord <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReviewrecord() {
		return reviewrecord;
	}
	/**  
	  * @Title:  setReviewrecord <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReviewrecord(String reviewrecord) {
		this.reviewrecord = reviewrecord;
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
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "HudhAcography [seqId=" + seqId + ", lcljId=" + lcljId + ", lcljNum=" + lcljNum + ", userId=" + userId
				+ ", doctorname=" + doctorname + ", writetime=" + writetime + ", reviewrecord=" + reviewrecord + "]";
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
		result = prime * result + ((doctorname == null) ? 0 : doctorname.hashCode());
		result = prime * result + ((lcljNum == null) ? 0 : lcljNum.hashCode());
		result = prime * result + ((lcljId == null) ? 0 : lcljId.hashCode());
		result = prime * result + ((reviewrecord == null) ? 0 : reviewrecord.hashCode());
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((writetime == null) ? 0 : writetime.hashCode());
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
		HudhAcography other = (HudhAcography) obj;
		if (doctorname == null) {
			if (other.doctorname != null)
				return false;
		} else if (!doctorname.equals(other.doctorname))
			return false;
		if (lcljNum == null) {
			if (other.lcljNum != null)
				return false;
		} else if (!lcljNum.equals(other.lcljNum))
			return false;
		if (lcljId == null) {
			if (other.lcljId != null)
				return false;
		} else if (!lcljId.equals(other.lcljId))
			return false;
		if (reviewrecord == null) {
			if (other.reviewrecord != null)
				return false;
		} else if (!reviewrecord.equals(other.reviewrecord))
			return false;
		if (seqId == null) {
			if (other.seqId != null)
				return false;
		} else if (!seqId.equals(other.seqId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (writetime == null) {
			if (other.writetime != null)
				return false;
		} else if (!writetime.equals(other.writetime))
			return false;
		return true;
	}
}
