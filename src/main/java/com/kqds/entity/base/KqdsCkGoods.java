package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCkGoods {
	private String seqId;

	private String goodsdetailid;

	private String sshouse;

	private BigDecimal goodsprice;
	
	private BigDecimal goodsprices;

	private Integer nums;
	
	private Integer num;

	private Integer numsexport;

	private BigDecimal goodspriceexport;

	private String organization;

	private BigDecimal kcmoney;
	
	private BigDecimal kcmoneys;

	/**  
	  * @Title:  getNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getNum() {
		return num;
	}

	/**  
	  * @Title:  setNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setNum(Integer num) {
		this.num = num;
	}

	/**  
	  * @Title:  getKcmoneys <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public BigDecimal getKcmoneys() {
		return kcmoneys;
	}

	/**  
	  * @Title:  setKcmoneys <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public void setKcmoneys(BigDecimal kcmoneys) {
		this.kcmoneys = kcmoneys;
	}
	
	/**  
	  * @Title:  getGoodsprices <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public BigDecimal getGoodsprices() {
		return goodsprices;
	}

	/**  
	  * @Title:  setGoodsprices <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public void setGoodsprices(BigDecimal goodsprices) {
		this.goodsprices = goodsprices;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getGoodsdetailid() {
		return goodsdetailid;
	}

	public void setGoodsdetailid(String goodsdetailid) {
		this.goodsdetailid = goodsdetailid == null ? null : goodsdetailid.trim();
	}

	public String getSshouse() {
		return sshouse;
	}

	public void setSshouse(String sshouse) {
		this.sshouse = sshouse == null ? null : sshouse.trim();
	}

	public BigDecimal getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public Integer getNumsexport() {
		return numsexport;
	}

	public void setNumsexport(Integer numsexport) {
		this.numsexport = numsexport;
	}

	public BigDecimal getGoodspriceexport() {
		return goodspriceexport;
	}

	public void setGoodspriceexport(BigDecimal goodspriceexport) {
		this.goodspriceexport = goodspriceexport;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public BigDecimal getKcmoney() {
		return kcmoney;
	}

	public void setKcmoney(BigDecimal kcmoney) {
		this.kcmoney = kcmoney;
	}

	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "KqdsCkGoods [seqId=" + seqId + ", goodsdetailid=" + goodsdetailid + ", sshouse=" + sshouse
				+ ", goodsprice=" + goodsprice + ", nums=" + nums + ", numsexport=" + numsexport + ", goodspriceexport="
				+ goodspriceexport + ", organization=" + organization + ", kcmoney=" + kcmoney +  "]";
	}
	
}