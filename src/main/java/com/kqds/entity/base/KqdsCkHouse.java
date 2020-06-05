package com.kqds.entity.base;

public class KqdsCkHouse {
	private String seqId;

	private String housename;

	private String housecode;

	private Integer sortno;

	private String organization;

	private String createuser;

	private String createtime;
	
	private String type;

	/**  
	  * @Title:  getType <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getType() {
		return type;
	}

	/**  
	  * @Title:  setType <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setType(String type) {
		this.type = type;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getHousename() {
		return housename;
	}

	public void setHousename(String housename) {
		this.housename = housename == null ? null : housename.trim();
	}

	public String getHousecode() {
		return housecode;
	}

	public void setHousecode(String housecode) {
		this.housecode = housecode == null ? null : housecode.trim();
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}
}