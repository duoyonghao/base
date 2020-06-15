package com.kqds.entity.base;

public class KqdsCkGoodsIn {
	private String seqId;

	private String supplier;

	private String inhouse;

	private String incode;

	private String inremark;

	private String zhaiyao;

	private String auditor;

	private String shtime;

	private String rkr;

	private String rktime;

	private Integer instatus;

	private Integer intype;

	private String organization;

	private String createuser;

	private String createtime;

	private String stocktime;
	
	private int check_status;//验收状态  0:待验收   1:已验收
	
	private int status; //状态 0:正常 1:已删除 2：已修改
	
	private String modifiyId; //修改的原数据id，本表的其他数据
	
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getModifiyId() {
		return modifiyId;
	}

	public void setModifiyId(String modifiyId) {
		this.modifiyId = modifiyId;
	}

	public int getCheck_status() {
		return check_status;
	}

	public void setCheck_status(int check_status) {
		this.check_status = check_status;
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

	public String getInhouse() {
		return inhouse;
	}

	public void setInhouse(String inhouse) {
		this.inhouse = inhouse == null ? null : inhouse.trim();
	}

	public String getIncode() {
		return incode;
	}

	public void setIncode(String incode) {
		this.incode = incode == null ? null : incode.trim();
	}

	public String getInremark() {
		return inremark;
	}

	public void setInremark(String inremark) {
		this.inremark = inremark == null ? null : inremark.trim();
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

	public String getRkr() {
		return rkr;
	}

	public void setRkr(String rkr) {
		this.rkr = rkr == null ? null : rkr.trim();
	}

	public String getRktime() {
		return rktime;
	}

	public void setRktime(String rktime) {
		this.rktime = rktime == null ? null : rktime.trim();
	}

	public Integer getInstatus() {
		return instatus;
	}

	public void setInstatus(Integer instatus) {
		this.instatus = instatus;
	}

	public Integer getIntype() {
		return intype;
	}

	public void setIntype(Integer intype) {
		this.intype = intype;
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

	public String getStocktime() {
		return stocktime;
	}

	public void setStocktime(String stocktime) {
		this.stocktime = stocktime;
	}
	
}