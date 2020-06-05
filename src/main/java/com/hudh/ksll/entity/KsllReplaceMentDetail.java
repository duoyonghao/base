package com.hudh.ksll.entity;

import java.math.BigDecimal;

/**
 * 科室退还子表数据
 * @author ASUS
 *
 */
public class KsllReplaceMentDetail {
	private String id;
	private String goodscode; //商品编号
	private String house; //仓库
	private String goodsname; //名称
	private String goodsnorms; //规格
	private String goodsunit; //单位
	private Integer nums; //退还数量
	private String organization; //门诊
	private String goodsremark; //附件说明
	private String parentid; //父id
	private BigDecimal goodsprice; //添加字段商品单价
	private String ph; //批号
	private String createtime;//创建时间
	private String recipientsdate;//领料时间
	private String goodsdetailId;//领料时间
	private String floor;//领料时间
	/**  
	  * @Title:  getFloor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getFloor() {
		return floor;
	}
	/**  
	  * @Title:  setFloor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setFloor(String floor) {
		this.floor = floor;
	}
	/**  
	  * @Title:  getGoodsdetailId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGoodsdetailId() {
		return goodsdetailId;
	}
	/**  
	  * @Title:  setGoodsdetailId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGoodsdetailId(String goodsdetailId) {
		this.goodsdetailId = goodsdetailId;
	}
	
	public BigDecimal getGoodsprice() {
		return goodsprice;
	}
	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodscode() {
		return goodscode;
	}
	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
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
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
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
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getRecipientsdate() {
		return recipientsdate;
	}
	public void setRecipientsdate(String recipientsdate) {
		this.recipientsdate = recipientsdate;
	}
	
}
