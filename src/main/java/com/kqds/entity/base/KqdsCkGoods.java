package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCkGoods
{
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
  
  public Integer getNum()
  {
    return this.num;
  }
  
  public void setNum(Integer num)
  {
    this.num = num;
  }
  
  public BigDecimal getKcmoneys()
  {
    return this.kcmoneys;
  }
  
  public void setKcmoneys(BigDecimal kcmoneys)
  {
    this.kcmoneys = kcmoneys;
  }
  
  public BigDecimal getGoodsprices()
  {
    return this.goodsprices;
  }
  
  public void setGoodsprices(BigDecimal goodsprices)
  {
    this.goodsprices = goodsprices;
  }
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getGoodsdetailid()
  {
    return this.goodsdetailid;
  }
  
  public void setGoodsdetailid(String goodsdetailid)
  {
    this.goodsdetailid = (goodsdetailid == null ? null : goodsdetailid.trim());
  }
  
  public String getSshouse()
  {
    return this.sshouse;
  }
  
  public void setSshouse(String sshouse)
  {
    this.sshouse = (sshouse == null ? null : sshouse.trim());
  }
  
  public BigDecimal getGoodsprice()
  {
    return this.goodsprice;
  }
  
  public void setGoodsprice(BigDecimal goodsprice)
  {
    this.goodsprice = goodsprice;
  }
  
  public Integer getNums()
  {
    return this.nums;
  }
  
  public void setNums(Integer nums)
  {
    this.nums = nums;
  }
  
  public Integer getNumsexport()
  {
    return this.numsexport;
  }
  
  public void setNumsexport(Integer numsexport)
  {
    this.numsexport = numsexport;
  }
  
  public BigDecimal getGoodspriceexport()
  {
    return this.goodspriceexport;
  }
  
  public void setGoodspriceexport(BigDecimal goodspriceexport)
  {
    this.goodspriceexport = goodspriceexport;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public BigDecimal getKcmoney()
  {
    return this.kcmoney;
  }
  
  public void setKcmoney(BigDecimal kcmoney)
  {
    this.kcmoney = kcmoney;
  }
  
  public String toString()
  {
    return 
    
      "KqdsCkGoods [seqId=" + this.seqId + ", goodsdetailid=" + this.goodsdetailid + ", sshouse=" + this.sshouse + ", goodsprice=" + this.goodsprice + ", nums=" + this.nums + ", numsexport=" + this.numsexport + ", goodspriceexport=" + this.goodspriceexport + ", organization=" + this.organization + ", kcmoney=" + this.kcmoney + "]";
  }
}
