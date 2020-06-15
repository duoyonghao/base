package com.hudh.ksll.entity;

/**
 * 科室库存表
 * @author ASUS
 *
 */
public class KsllGoods {
	private String id; 
	private String deptpart; //科室 
	private String goodscode; //商品编号
	private String goodsname; //商品名称
	private String house; //所属仓库
	private String goodsnorms; //规格
	private String goodsunit; //单位
	private Integer nums; //库存数量
	private String validity; //有效期
	private String inremark; //附件说明
	private String organization; //门诊编号
	private String goodsdetailid;
	private String goodsprice;//添加字段  商品单价
	private String createtime;//添加字段  创建时间
	private String createuser;//添加字段  创建人
	
	
	/**  
	  * @Title:  getGoodsdetailid <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGoodsdetailid() {
		return goodsdetailid;
	}
	/**  
	  * @Title:  setGoodsdetailid <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGoodsdetailid(String goodsdetailid) {
		this.goodsdetailid = goodsdetailid;
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
	public String getGoodsprice() {
		return goodsprice;
	}
	public void setGoodsprice(String goodsprice) {
		this.goodsprice = goodsprice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptpart() {
		return deptpart;
	}
	public void setDeptpart(String deptpart) {
		this.deptpart = deptpart;
	}
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
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
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
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getInremark() {
		return inremark;
	}
	public void setInremark(String inremark) {
		this.inremark = inremark;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
}
