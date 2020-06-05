package com.kqds.entity.base;

public class KqdsCkGoodsOut {
	private String seqId;

	private String supplier;

	private String outhouse;

	private String outcode;

	private String outremark;

	private String zhaiyao;

	private String auditor;

	private String shtime;

	private String ckr;

	private String cktime;

	private Integer outstatus;

	private Integer outtype;

	private String llr;

	private String sqdeptid;

	private String sqdoctor;

	private String organization;

	private String createuser;

	private String createtime;
	
	private int isksll;
	
	private String ksllCollorId;//添加字段，用于校验同一领料单重复出库
	
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

	public String getKsllCollorId() {
		return ksllCollorId;
	}

	public void setKsllCollorId(String ksllCollorId) {
		this.ksllCollorId = ksllCollorId;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier == null ? null : supplier.trim();
	}

	public String getOuthouse() {
		return outhouse;
	}

	public void setOuthouse(String outhouse) {
		this.outhouse = outhouse == null ? null : outhouse.trim();
	}

	public String getOutcode() {
		return outcode;
	}

	public void setOutcode(String outcode) {
		this.outcode = outcode == null ? null : outcode.trim();
	}

	public String getOutremark() {
		return outremark;
	}

	public void setOutremark(String outremark) {
		this.outremark = outremark == null ? null : outremark.trim();
	}

	public String getZhaiyao() {
		return zhaiyao;
	}

	public void setZhaiyao(String zhaiyao) {
		this.zhaiyao = zhaiyao == null ? null : zhaiyao.trim();
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor == null ? null : auditor.trim();
	}

	public String getShtime() {
		return shtime;
	}

	public void setShtime(String shtime) {
		this.shtime = shtime == null ? null : shtime.trim();
	}

	public String getCkr() {
		return ckr;
	}

	public void setCkr(String ckr) {
		this.ckr = ckr == null ? null : ckr.trim();
	}

	public String getCktime() {
		return cktime;
	}

	public void setCktime(String cktime) {
		this.cktime = cktime == null ? null : cktime.trim();
	}

	public Integer getOutstatus() {
		return outstatus;
	}

	public void setOutstatus(Integer outstatus) {
		this.outstatus = outstatus;
	}

	public Integer getOuttype() {
		return outtype;
	}

	public void setOuttype(Integer outtype) {
		this.outtype = outtype;
	}

	public String getLlr() {
		return llr;
	}

	public void setLlr(String llr) {
		this.llr = llr == null ? null : llr.trim();
	}

	public String getSqdeptid() {
		return sqdeptid;
	}

	public void setSqdeptid(String sqdeptid) {
		this.sqdeptid = sqdeptid == null ? null : sqdeptid.trim();
	}

	public String getSqdoctor() {
		return sqdoctor;
	}

	public void setSqdoctor(String sqdoctor) {
		this.sqdoctor = sqdoctor == null ? null : sqdoctor.trim();
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

	public int getIsksll() {
		return isksll;
	}

	public void setIsksll(int isksll) {
		this.isksll = isksll;
	}
	
}