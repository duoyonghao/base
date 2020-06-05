package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCkReplacementDetail {
	private String seqId;
	private String goodscode; //商品编号
	private String goodsname; //名称
	private String goodsnorms; //规格
	private String goodsunit; //单位
	private Integer nums; //退还数量
	private Integer phnum; //领料数量
	private Integer returnnums; //退货数量
	private String goodsremark; //附加说明
	private String parentid; //父id
	private BigDecimal price; //添加字段商品单价
	private String createtime;//创建时间
	private String createuser;//创建人
	private String ph;//批号
	private Integer residue_num; //批号剩余数量
	private String addnumber;
	public String getGoodscode() {
		return goodscode;
	}
	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode;
	}

	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getGoodsnorms() {
		return goodsnorms;
	}
	public void setGoodsnorms(String goodsnorms) {
		this.goodsnorms = goodsnorms;
	}
	public String getGoodsunit() {
		return goodsunit;
	}
	public void setGoodsunit(String goodsunit) {
		this.goodsunit = goodsunit;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public Integer getPhnum() {
		return phnum;
	}
	public void setPhnum(Integer phnum) {
		this.phnum = phnum;
	}
	public Integer getReturnnums() {
		return returnnums;
	}
	public void setReturnnums(Integer returnnums) {
		this.returnnums = returnnums;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getGoodsremark() {
		return goodsremark;
	}
	public void setGoodsremark(String goodsremark) {
		this.goodsremark = goodsremark;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public Integer getResidue_num() {
		return residue_num;
	}
	public void setResidue_num(Integer residue_num) {
		this.residue_num = residue_num;
	}
	public String getAddnumber() {
		return addnumber;
	}
	public void setAddnumber(String addnumber) {
		this.addnumber = addnumber;
	}
	
}
