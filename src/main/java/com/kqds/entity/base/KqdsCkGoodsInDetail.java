package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCkGoodsInDetail {
	private String seqId;

	private String goodsuuid;

	private BigDecimal inprice;

	private Integer innum;

	private String yxdate;

	private String sqremark;

	private String incode;

	private String organization;

	private String createuser;

	private String createtime;

	private BigDecimal rkmoney;

	private String ph;

	private String zczh;

	private String cd;
	
	private String goodsInSeqId;//添加字段，在明细表中保存商品入库表id
	
	private int auditStatus;//验收状态  0:待验收   1:已验收
	
	private int phnum;//添加字段 批号数量
	
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

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getBeforeph() {
		return beforeph;
	}

	public void setBeforeph(String beforeph) {
		this.beforeph = beforeph;
	}

	private String goodsId;//添加字段  商品的主键ID
	
	private String updateuser;
	
	private String updatetime;
	
	private String beforeph;
	
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public int getPhnum() {
		return phnum;
	}

	public void setPhnum(int phnum) {
		this.phnum = phnum;
	}

	public String getGoodsInSeqId() {
		return goodsInSeqId;
	}

	public void setGoodsInSeqId(String goodsInSeqId) {
		this.goodsInSeqId = goodsInSeqId;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getGoodsuuid() {
		return goodsuuid;
	}

	public void setGoodsuuid(String goodsuuid) {
		this.goodsuuid = goodsuuid == null ? null : goodsuuid.trim();
	}

	public BigDecimal getInprice() {
		return inprice;
	}

	public void setInprice(BigDecimal inprice) {
		this.inprice = inprice;
	}

	public Integer getInnum() {
		return innum;
	}

	public void setInnum(Integer innum) {
		this.innum = innum;
	}

	public String getYxdate() {
		return yxdate;
	}

	public void setYxdate(String yxdate) {
		this.yxdate = yxdate == null ? null : yxdate.trim();
	}

	public String getSqremark() {
		return sqremark;
	}

	public void setSqremark(String sqremark) {
		this.sqremark = sqremark == null ? null : sqremark.trim();
	}

	public String getIncode() {
		return incode;
	}

	public void setIncode(String incode) {
		this.incode = incode == null ? null : incode.trim();
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

	public BigDecimal getRkmoney() {
		return rkmoney;
	}

	public void setRkmoney(BigDecimal rkmoney) {
		this.rkmoney = rkmoney;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph == null ? null : ph.trim();
	}

	public String getZczh() {
		return zczh;
	}

	public void setZczh(String zczh) {
		this.zczh = zczh == null ? null : zczh.trim();
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd == null ? null : cd.trim();
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "KqdsCkGoodsInDetail [seqId=" + seqId + ", goodsuuid=" + goodsuuid + ", inprice=" + inprice + ", innum="
				+ innum + ", yxdate=" + yxdate + ", sqremark=" + sqremark + ", incode=" + incode + ", organization="
				+ organization + ", createuser=" + createuser + ", createtime=" + createtime + ", rkmoney=" + rkmoney
				+ ", ph=" + ph + ", zczh=" + zczh + ", cd=" + cd + ", goodsInSeqId=" + goodsInSeqId + ", auditStatus="
				+ auditStatus + ", phnum=" + phnum + ", goodsId=" + goodsId + ", updateuser=" + updateuser
				+ ", updatetime=" + updatetime + ", beforeph=" + beforeph + "]";
	}
	
}