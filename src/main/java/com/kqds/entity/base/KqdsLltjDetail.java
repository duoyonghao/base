package com.kqds.entity.base;

public class KqdsLltjDetail {
	private String seqId;

	private String lltjid;

	private String usercode;

	private String username;

	private String itemno;

	private String unit;

	private Integer nums;

	private String goods;

	private String goodsunit;

	private Integer goodsnums;

	private String createuser;

	private String createtime;

	private Integer iszl;

	private String organization;

	private String sshouse;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getLltjid() {
		return lltjid;
	}

	public void setLltjid(String lltjid) {
		this.lltjid = lltjid == null ? null : lltjid.trim();
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno == null ? null : itemno.trim();
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods == null ? null : goods.trim();
	}

	public String getGoodsunit() {
		return goodsunit;
	}

	public void setGoodsunit(String goodsunit) {
		this.goodsunit = goodsunit == null ? null : goodsunit.trim();
	}

	public Integer getGoodsnums() {
		return goodsnums;
	}

	public void setGoodsnums(Integer goodsnums) {
		this.goodsnums = goodsnums;
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

	public Integer getIszl() {
		return iszl;
	}

	public void setIszl(Integer iszl) {
		this.iszl = iszl;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getSshouse() {
		return sshouse;
	}

	public void setSshouse(String sshouse) {
		this.sshouse = sshouse == null ? null : sshouse.trim();
	}
}