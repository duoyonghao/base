package com.kqds.entity.base;

public class KqdsTreatitem {
	private String seqId;

	private String createuser;

	private String createtime;

	private String treatitemno;

	private String treatitemname;

	private String unit;

	private String unitprice;

	private String discount;

	private String basetype;

	private String nexttype;

	private String xmjs;

	private String yhxx;

	private Integer istsxm;

	private Integer issplit;

	private Integer useflag;

	private String organization;

	private Integer isyjjitem;
	
	private String status;//添加字段

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
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

	public String getTreatitemno() {
		return treatitemno;
	}

	public void setTreatitemno(String treatitemno) {
		this.treatitemno = treatitemno == null ? null : treatitemno.trim();
	}

	public String getTreatitemname() {
		return treatitemname;
	}

	public void setTreatitemname(String treatitemname) {
		this.treatitemname = treatitemname == null ? null : treatitemname.trim();
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public String getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice == null ? null : unitprice.trim();
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount == null ? null : discount.trim();
	}

	public String getBasetype() {
		return basetype;
	}

	public void setBasetype(String basetype) {
		this.basetype = basetype == null ? null : basetype.trim();
	}

	public String getNexttype() {
		return nexttype;
	}

	public void setNexttype(String nexttype) {
		this.nexttype = nexttype == null ? null : nexttype.trim();
	}

	public String getXmjs() {
		return xmjs;
	}

	public void setXmjs(String xmjs) {
		this.xmjs = xmjs == null ? null : xmjs.trim();
	}

	public String getYhxx() {
		return yhxx;
	}

	public void setYhxx(String yhxx) {
		this.yhxx = yhxx == null ? null : yhxx.trim();
	}

	public Integer getIstsxm() {
		return istsxm;
	}

	public void setIstsxm(Integer istsxm) {
		this.istsxm = istsxm;
	}

	public Integer getIssplit() {
		return issplit;
	}

	public void setIssplit(Integer issplit) {
		this.issplit = issplit;
	}

	public Integer getUseflag() {
		return useflag;
	}

	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public Integer getIsyjjitem() {
		return isyjjitem;
	}

	public void setIsyjjitem(Integer isyjjitem) {
		this.isyjjitem = isyjjitem;
	}
}