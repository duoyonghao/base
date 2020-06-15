package com.hudh.ksll.entity;

/**
 * 科室领用明细表
 * @author ASUS
 *
 */
public class KsllCollorDetail {
	private String id;
	private String basetype; //一级类别
	private String nexttype; //二级类别
	private String goodscode; //商品编号
	private String house; //所属仓库
	private String goodsname; //商品名称
	private String goodsnorms; //规格
	private String goodsunit; //单位
	private Integer nums; //数量
	private String validity; //有效期
	private String organization; //门诊编号
	private String goodsremark; //备注
	private String parentid; //父id
	private Integer status; //状态  记录库房对科室申请项的操作  1：删除  2：修改  0：没有变动
	private String ckchange;//仓库操作记录
	private String ckchangeuser;//仓库操作记录人员
	private String goodPrice;//添加字段
	private String ph;//批号
	private int phnum;//批号数量
	private String goodsdetailId;
	private String creator;
	private String creattime;
	public String getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBasetype() {
		return basetype;
	}
	public void setBasetype(String basetype) {
		this.basetype = basetype;
	}
	public String getNexttype() {
		return nexttype;
	}
	public void setNexttype(String nexttype) {
		this.nexttype = nexttype;
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
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCkchange() {
		return ckchange;
	}
	public void setCkchange(String ckchange) {
		this.ckchange = ckchange;
	}
	public String getCkchangeuser() {
		return ckchangeuser;
	}
	public void setCkchangeuser(String ckchangeuser) {
		this.ckchangeuser = ckchangeuser;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public int getPhnum() {
		return phnum;
	}
	public void setPhnum(int phnum) {
		this.phnum = phnum;
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
	/**  
	  * @Title:  getCreator <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreator() {
		return creator;
	}
	/**  
	  * @Title:  setCreator <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**  
	  * @Title:  getCreattime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreattime() {
		return creattime;
	}
	/**  
	  * @Title:  setCreattime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#toString()
	  */  
	@Override
	public String toString() {
		return "KsllCollorDetail [id=" + id + ", basetype=" + basetype + ", nexttype=" + nexttype + ", goodscode="
				+ goodscode + ", house=" + house + ", goodsname=" + goodsname + ", goodsnorms=" + goodsnorms
				+ ", goodsunit=" + goodsunit + ", nums=" + nums + ", validity=" + validity + ", organization="
				+ organization + ", goodsremark=" + goodsremark + ", parentid=" + parentid + ", status=" + status
				+ ", ckchange=" + ckchange + ", ckchangeuser=" + ckchangeuser + ", goodPrice=" + goodPrice + ", ph="
				+ ph + ", phnum=" + phnum + ", goodsdetailId=" + goodsdetailId + ", creator=" + creator + ", creattime="
				+ creattime + "]";
	}
	/**   
	  * <p>Title: equals</p>   
	  * <p>Description: </p>   
	  * @param obj
	  * @return   
	  * @see Object#equals(Object)
	  */  
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	/**   
	  * <p>Title: finalize</p>   
	  * <p>Description: </p>   
	  * @throws Throwable   
	  * @see Object#finalize()
	  */  
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
}
