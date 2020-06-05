package com.kqds.entity.sys;

public class YZPriv {
	private String seqId;

	private String privName;

	private Integer privNo;

	private String createtime;

	private String createuser;

	private String organization;

	private String funcIdStr;

	private String funcbutton;

	private String privIdStr;

	private String visualPerson;

	private String visualDept;
	
	private String visitDept;

	private String orderVisualPerson;

	private String orderVisualDept;
	
	private String authority;
	
	private String firstFloor;
	
	private String upstair;
	
	/**  
	  * @Title:  getFirstFloor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getFirstFloor() {
		return firstFloor;
	}

	/**  
	  * @Title:  setFirstFloor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setFirstFloor(String firstFloor) {
		this.firstFloor = firstFloor;
	}

	/**  
	  * @Title:  getUpstair <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUpstair() {
		return upstair;
	}

	/**  
	  * @Title:  setUpstair <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUpstair(String upstair) {
		this.upstair = upstair;
	}

	/**  
	  * @Title:  getAuthority <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAuthority() {
		return authority;
	}

	/**  
	  * @Title:  setAuthority <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getPrivName() {
		return privName;
	}

	public void setPrivName(String privName) {
		this.privName = privName == null ? null : privName.trim();
	}

	public Integer getPrivNo() {
		return privNo;
	}

	public void setPrivNo(Integer privNo) {
		this.privNo = privNo;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getFuncIdStr() {
		return funcIdStr;
	}

	public void setFuncIdStr(String funcIdStr) {
		this.funcIdStr = funcIdStr == null ? null : funcIdStr.trim();
	}

	public String getFuncbutton() {
		return funcbutton;
	}

	public void setFuncbutton(String funcbutton) {
		this.funcbutton = funcbutton == null ? null : funcbutton.trim();
	}

	public String getPrivIdStr() {
		return privIdStr;
	}

	public void setPrivIdStr(String privIdStr) {
		this.privIdStr = privIdStr == null ? null : privIdStr.trim();
	}

	public String getVisualPerson() {
		return visualPerson;
	}

	public void setVisualPerson(String visualPerson) {
		this.visualPerson = visualPerson == null ? null : visualPerson.trim();
	}

	public String getVisualDept() {
		return visualDept;
	}

	public void setVisualDept(String visualDept) {
		this.visualDept = visualDept == null ? null : visualDept.trim();
	}
	
	
	
	/**  
	  * @Title:  getVisitDept <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getVisitDept() {
		return visitDept;
	}

	/**  
	  * @Title:  setVisitDept <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setVisitDept(String visitDept) {
		this.visitDept = visitDept;
	}

	public String getOrderVisualPerson() {
		return orderVisualPerson;
	}

	public void setOrderVisualPerson(String orderVisualPerson) {
		this.orderVisualPerson = orderVisualPerson == null ? null : orderVisualPerson.trim();
	}

	public String getOrderVisualDept() {
		return orderVisualDept;
	}

	public void setOrderVisualDept(String orderVisualDept) {
		this.orderVisualDept = orderVisualDept == null ? null : orderVisualDept.trim();
	}
}