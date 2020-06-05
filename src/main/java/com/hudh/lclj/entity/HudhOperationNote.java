/**  
  *
  * @Title:  HUDH_operationNote.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2020年5月30日 上午9:22:03   
  * @version V1.0  
  */ 
package com.hudh.lclj.entity;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  HUDH_operationNote   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2020年5月30日 上午9:22:03   
  *      
  */
public class HudhOperationNote implements Serializable {

	private String seqId;
	private String userid;
	private String lcljId;
	private String lcljNum;
	private String plantdoctor;//手术医生 
	private String nurse;//配台护士
	private String operationdate;//手术日期
	private String localanesthesia;//麻醉药物
	private String operatingrecord;//操作记录
	private String implantbarcode;//种植体条码
	private String periostbarcode;//骨粉骨膜条码
	private String doctorname;//医生签字
	private String doctortime;//签字时间
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
	  * @Title:  getUserid <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUserid() {
		return userid;
	}
	/**  
	  * @Title:  setUserid <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUserid(String userid) {
		this.userid = userid;
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
	  * @Title:  getPlantdoctor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPlantdoctor() {
		return plantdoctor;
	}
	/**  
	  * @Title:  setPlantdoctor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPlantdoctor(String plantdoctor) {
		this.plantdoctor = plantdoctor;
	}
	/**  
	  * @Title:  getNurse <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getNurse() {
		return nurse;
	}
	/**  
	  * @Title:  setNurse <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setNurse(String nurse) {
		this.nurse = nurse;
	}
	/**  
	  * @Title:  getOperationdate <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOperationdate() {
		return operationdate;
	}
	/**  
	  * @Title:  setOperationdate <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOperationdate(String operationdate) {
		this.operationdate = operationdate;
	}
	/**  
	  * @Title:  getLocalanesthesia <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLocalanesthesia() {
		return localanesthesia;
	}
	/**  
	  * @Title:  setLocalanesthesia <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLocalanesthesia(String localanesthesia) {
		this.localanesthesia = localanesthesia;
	}
	/**  
	  * @Title:  getOperatingrecord <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOperatingrecord() {
		return operatingrecord;
	}
	/**  
	  * @Title:  setOperatingrecord <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOperatingrecord(String operatingrecord) {
		this.operatingrecord = operatingrecord;
	}
	/**  
	  * @Title:  getImplantbarcode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getImplantbarcode() {
		return implantbarcode;
	}
	/**  
	  * @Title:  setImplantbarcode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setImplantbarcode(String implantbarcode) {
		this.implantbarcode = implantbarcode;
	}
	/**  
	  * @Title:  getPeriostbarcode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPeriostbarcode() {
		return periostbarcode;
	}
	/**  
	  * @Title:  setPeriostbarcode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPeriostbarcode(String periostbarcode) {
		this.periostbarcode = periostbarcode;
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
	  * @Title:  getDoctortime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDoctortime() {
		return doctortime;
	}
	/**  
	  * @Title:  setDoctortime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDoctortime(String doctortime) {
		this.doctortime = doctortime;
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
		return "HUDH_operationNote [seqId=" + seqId + ", userid=" + userid + ", plantdoctor=" + plantdoctor + ", nurse="
				+ nurse + ", operationdate=" + operationdate + ", localanesthesia=" + localanesthesia
				+ ", operatingrecord=" + operatingrecord + ", implantbarcode=" + implantbarcode + ", periostbarcode="
				+ periostbarcode + ", doctorname=" + doctorname + ", doctortime=" + doctortime + "]";
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
		result = prime * result + ((doctortime == null) ? 0 : doctortime.hashCode());
		result = prime * result + ((implantbarcode == null) ? 0 : implantbarcode.hashCode());
		result = prime * result + ((localanesthesia == null) ? 0 : localanesthesia.hashCode());
		result = prime * result + ((nurse == null) ? 0 : nurse.hashCode());
		result = prime * result + ((operatingrecord == null) ? 0 : operatingrecord.hashCode());
		result = prime * result + ((operationdate == null) ? 0 : operationdate.hashCode());
		result = prime * result + ((periostbarcode == null) ? 0 : periostbarcode.hashCode());
		result = prime * result + ((plantdoctor == null) ? 0 : plantdoctor.hashCode());
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
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
		HudhOperationNote other = (HudhOperationNote) obj;
		if (doctorname == null) {
			if (other.doctorname != null)
				return false;
		} else if (!doctorname.equals(other.doctorname))
			return false;
		if (doctortime == null) {
			if (other.doctortime != null)
				return false;
		} else if (!doctortime.equals(other.doctortime))
			return false;
		if (implantbarcode == null) {
			if (other.implantbarcode != null)
				return false;
		} else if (!implantbarcode.equals(other.implantbarcode))
			return false;
		if (localanesthesia == null) {
			if (other.localanesthesia != null)
				return false;
		} else if (!localanesthesia.equals(other.localanesthesia))
			return false;
		if (nurse == null) {
			if (other.nurse != null)
				return false;
		} else if (!nurse.equals(other.nurse))
			return false;
		if (operatingrecord == null) {
			if (other.operatingrecord != null)
				return false;
		} else if (!operatingrecord.equals(other.operatingrecord))
			return false;
		if (operationdate == null) {
			if (other.operationdate != null)
				return false;
		} else if (!operationdate.equals(other.operationdate))
			return false;
		if (periostbarcode == null) {
			if (other.periostbarcode != null)
				return false;
		} else if (!periostbarcode.equals(other.periostbarcode))
			return false;
		if (plantdoctor == null) {
			if (other.plantdoctor != null)
				return false;
		} else if (!plantdoctor.equals(other.plantdoctor))
			return false;
		if (seqId == null) {
			if (other.seqId != null)
				return false;
		} else if (!seqId.equals(other.seqId))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
	
	
}
