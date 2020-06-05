package com.kqds.entity.base;

public class KqdsCostorderPriceList {
	private String seqId;//id
	private String exploit;//开发
	private String istsxm;//特殊项目
	private String treatitemname;//治疗项目
	private String unit;//单位
	private String unitprice;//单价
	private String num;//数量
	private String discount;//折扣
	private String subtotal;//小计
	private String arrearmoney;//欠费
	private String paymoney;//缴费
	private String voidmoney;//免除
	private String parentId;//关联表id
	private String createuser;//创建人id
	private String createtime;//创建时间
	private String usercode;//患者编号
	private String itemname;//套餐治疗项目
	private String nodename;//节点名称
	private String itemid;//项目id
	private String modifier;//修改人
	private String updateTime;//修改时间
	private int isdelete;//是否删除 1删除
	private String tcnameid;
	private String spare;//预留字段
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getExploit() {
		return exploit;
	}
	public void setExploit(String exploit) {
		this.exploit = exploit;
	}
	public String getIstsxm() {
		return istsxm;
	}
	public void setIstsxm(String istsxm) {
		this.istsxm = istsxm;
	}
	public String getTreatitemname() {
		return treatitemname;
	}
	public void setTreatitemname(String treatitemname) {
		this.treatitemname = treatitemname;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public String getArrearmoney() {
		return arrearmoney;
	}
	public void setArrearmoney(String arrearmoney) {
		this.arrearmoney = arrearmoney;
	}
	public String getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	public String getVoidmoney() {
		return voidmoney;
	}
	public void setVoidmoney(String voidmoney) {
		this.voidmoney = voidmoney;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public int getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	
	public String getTcnameid() {
		return tcnameid;
	}
	public void setTcnameid(String tcnameid) {
		this.tcnameid = tcnameid;
	}
	public String getSpare() {
		return spare;
	}
	public void setSpare(String spare) {
		this.spare = spare;
	}
}
