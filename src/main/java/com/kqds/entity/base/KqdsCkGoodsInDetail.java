package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCkGoodsInDetail
{
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
  private String goodsInSeqId;
  private int auditStatus;
  private int phnum;
  private String type;
  private String goodsId;
  private String updateuser;
  private String updatetime;
  private String beforeph;
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getUpdateuser()
  {
    return this.updateuser;
  }
  
  public void setUpdateuser(String updateuser)
  {
    this.updateuser = updateuser;
  }
  
  public String getUpdatetime()
  {
    return this.updatetime;
  }
  
  public void setUpdatetime(String updatetime)
  {
    this.updatetime = updatetime;
  }
  
  public String getBeforeph()
  {
    return this.beforeph;
  }
  
  public void setBeforeph(String beforeph)
  {
    this.beforeph = beforeph;
  }
  
  public String getGoodsId()
  {
    return this.goodsId;
  }
  
  public void setGoodsId(String goodsId)
  {
    this.goodsId = goodsId;
  }
  
  public int getPhnum()
  {
    return this.phnum;
  }
  
  public void setPhnum(int phnum)
  {
    this.phnum = phnum;
  }
  
  public String getGoodsInSeqId()
  {
    return this.goodsInSeqId;
  }
  
  public void setGoodsInSeqId(String goodsInSeqId)
  {
    this.goodsInSeqId = goodsInSeqId;
  }
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getGoodsuuid()
  {
    return this.goodsuuid;
  }
  
  public void setGoodsuuid(String goodsuuid)
  {
    this.goodsuuid = (goodsuuid == null ? null : goodsuuid.trim());
  }
  
  public BigDecimal getInprice()
  {
    return this.inprice;
  }
  
  public void setInprice(BigDecimal inprice)
  {
    this.inprice = inprice;
  }
  
  public Integer getInnum()
  {
    return this.innum;
  }
  
  public void setInnum(Integer innum)
  {
    this.innum = innum;
  }
  
  public String getYxdate()
  {
    return this.yxdate;
  }
  
  public void setYxdate(String yxdate)
  {
    this.yxdate = (yxdate == null ? null : yxdate.trim());
  }
  
  public String getSqremark()
  {
    return this.sqremark;
  }
  
  public void setSqremark(String sqremark)
  {
    this.sqremark = (sqremark == null ? null : sqremark.trim());
  }
  
  public String getIncode()
  {
    return this.incode;
  }
  
  public void setIncode(String incode)
  {
    this.incode = (incode == null ? null : incode.trim());
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = (createuser == null ? null : createuser.trim());
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = (createtime == null ? null : createtime.trim());
  }
  
  public BigDecimal getRkmoney()
  {
    return this.rkmoney;
  }
  
  public void setRkmoney(BigDecimal rkmoney)
  {
    this.rkmoney = rkmoney;
  }
  
  public String getPh()
  {
    return this.ph;
  }
  
  public void setPh(String ph)
  {
    this.ph = (ph == null ? null : ph.trim());
  }
  
  public String getZczh()
  {
    return this.zczh;
  }
  
  public void setZczh(String zczh)
  {
    this.zczh = (zczh == null ? null : zczh.trim());
  }
  
  public String getCd()
  {
    return this.cd;
  }
  
  public void setCd(String cd)
  {
    this.cd = (cd == null ? null : cd.trim());
  }
  
  public int getAuditStatus()
  {
    return this.auditStatus;
  }
  
  public void setAuditStatus(int auditStatus)
  {
    this.auditStatus = auditStatus;
  }
  
  public String toString()
  {
    return 
    



      "KqdsCkGoodsInDetail [seqId=" + this.seqId + ", goodsuuid=" + this.goodsuuid + ", inprice=" + this.inprice + ", innum=" + this.innum + ", yxdate=" + this.yxdate + ", sqremark=" + this.sqremark + ", incode=" + this.incode + ", organization=" + this.organization + ", createuser=" + this.createuser + ", createtime=" + this.createtime + ", rkmoney=" + this.rkmoney + ", ph=" + this.ph + ", zczh=" + this.zczh + ", cd=" + this.cd + ", goodsInSeqId=" + this.goodsInSeqId + ", auditStatus=" + this.auditStatus + ", phnum=" + this.phnum + ", goodsId=" + this.goodsId + ", updateuser=" + this.updateuser + ", updatetime=" + this.updatetime + ", beforeph=" + this.beforeph + "]";
  }
}
