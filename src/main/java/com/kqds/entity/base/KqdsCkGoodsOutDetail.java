package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCkGoodsOutDetail {
	private String seqId;

	private String goodsuuid;

	private BigDecimal outprice;

	private Integer outnum;

	private String sqremark;

	private String outcode;

	private String organization;

	private String createuser;

	private String createtime;

	private BigDecimal ckmoney;
	
	private String goodscode;
	
	private String addtime;
	
	private String addnumber;
	
	private String ph;//增加字段，商品出库批号
	
	private Integer phnum;//增加字段、商品出库批号数量
	
	private String type;
	
	/**  
	  * @Title:  getAddtime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAddtime() {
		return addtime;
	}

	/**  
	  * @Title:  setAddtime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

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
	private String yxdate;

	/**  
	  * @Title:  getAddnumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAddnumber() {
		return addnumber;
	}

	/**  
	  * @Title:  setAddnumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAddnumber(String addnumber) {
		this.addnumber = addnumber;
	}

	/**  
	  * @Title:  getYxdate <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getYxdate() {
		return yxdate;
	}

	/**  
	  * @Title:  setYxdate <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setYxdate(String yxdate) {
		this.yxdate = yxdate;
	}

	public String getPh() {
		return ph;
	}

	public Integer getPhnum() {
		return phnum;
	}

	public void setPhnum(Integer phnum) {
		this.phnum = phnum;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}
	
	public String getAddTime() {
		return addtime;
	}

	public void setAddTime(String addtime) {
		this.addtime = addtime == null ? null : addtime.trim();
	}

	public String getAddNumber() {
		return addnumber;
	}

	public void setAddNumber(String addnumber) {
		this.addnumber = addnumber == null ? null : addnumber.trim();
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

	public BigDecimal getOutprice() {
		return outprice;
	}

	public void setOutprice(BigDecimal outprice) {
		this.outprice = outprice;
	}

	public Integer getOutnum() {
		return outnum;
	}

	public void setOutnum(Integer outnum) {
		this.outnum = outnum;
	}

	public String getSqremark() {
		return sqremark;
	}

	public void setSqremark(String sqremark) {
		this.sqremark = sqremark == null ? null : sqremark.trim();
	}

	public String getOutcode() {
		return outcode;
	}

	public void setOutcode(String outcode) {
		this.outcode = outcode == null ? null : outcode.trim();
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

	public BigDecimal getCkmoney() {
		return ckmoney;
	}

	public void setCkmoney(BigDecimal ckmoney) {
		this.ckmoney = ckmoney;
	}

	public String getGoodscode() {
		return goodscode;
	}

	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode;
	}
	
}