package com.hudh.doctorpick.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsDoctorPickInDetail
  implements Serializable
{
  private String seqId;
  private String goodsname;
  private String goodscode;
  private String housename;
  private String deptpartname;
  private String incode;
  private String goodsuuid;
  private String userdocument;
  private String goodsunit;
  private String nums;
  private String validity;
  private String goodsnorms;
  private String organization;
  private String createuser;
  private String createtime;
  private String company;
  private BigDecimal nuitPrice;
  private String quantity;
  private BigDecimal amount;
  private String remark;
  private String createdate;
  private String batchnum;
  private String regisnum;
  private String productionPlace;
  private String phids;
  private static final long serialVersionUID = 1L;
  
  public String getDeptpartname()
  {
    return this.deptpartname;
  }
  
  public void setDeptpartname(String deptpartname)
  {
    this.deptpartname = deptpartname;
  }
  
  public String getGoodsuuid()
  {
    return this.goodsuuid;
  }
  
  public void setGoodsuuid(String goodsuuid)
  {
    this.goodsuuid = goodsuuid;
  }
  
  public String getIncode()
  {
    return this.incode;
  }
  
  public void setIncode(String incode)
  {
    this.incode = incode;
  }
  
  public String getHousename()
  {
    return this.housename;
  }
  
  public void setHousename(String housename)
  {
    this.housename = housename;
  }
  
  public String getUserdocument()
  {
    return this.userdocument;
  }
  
  public void setUserdocument(String userdocument)
  {
    this.userdocument = userdocument;
  }
  
  public String getCompany()
  {
    return this.company;
  }
  
  public void setCompany(String company)
  {
    this.company = company;
  }
  
  public BigDecimal getNuitPrice()
  {
    return this.nuitPrice;
  }
  
  public void setNuitPrice(BigDecimal nuitPrice)
  {
    this.nuitPrice = nuitPrice;
  }
  
  public String getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(String quantity)
  {
    this.quantity = quantity;
  }
  
  public BigDecimal getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(BigDecimal amount)
  {
    this.amount = amount;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getCreatedate()
  {
    return this.createdate;
  }
  
  public void setCreatedate(String createdate)
  {
    this.createdate = createdate;
  }
  
  public String getBatchnum()
  {
    return this.batchnum;
  }
  
  public void setBatchnum(String batchnum)
  {
    this.batchnum = batchnum;
  }
  
  public String getRegisnum()
  {
    return this.regisnum;
  }
  
  public void setRegisnum(String regisnum)
  {
    this.regisnum = regisnum;
  }
  
  public String getProductionPlace()
  {
    return this.productionPlace;
  }
  
  public void setProductionPlace(String productionPlace)
  {
    this.productionPlace = productionPlace;
  }
  
  public static long getSerialversionuid()
  {
    return 1L;
  }
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getGoodsname()
  {
    return this.goodsname;
  }
  
  public void setGoodsname(String goodsname)
  {
    this.goodsname = (goodsname == null ? null : goodsname.trim());
  }
  
  public String getGoodscode()
  {
    return this.goodscode;
  }
  
  public void setGoodscode(String goodscode)
  {
    this.goodscode = (goodscode == null ? null : goodscode.trim());
  }
  
  public String getGoodsunit()
  {
    return this.goodsunit;
  }
  
  public void setGoodsunit(String goodsunit)
  {
    this.goodsunit = (goodsunit == null ? null : goodsunit.trim());
  }
  
  public String getNums()
  {
    return this.nums;
  }
  
  public void setNums(String nums)
  {
    this.nums = (nums == null ? null : nums.trim());
  }
  
  public String getValidity()
  {
    return this.validity;
  }
  
  public void setValidity(String validity)
  {
    this.validity = (validity == null ? null : validity.trim());
  }
  
  public String getGoodsnorms()
  {
    return this.goodsnorms;
  }
  
  public void setGoodsnorms(String goodsnorms)
  {
    this.goodsnorms = (goodsnorms == null ? null : goodsnorms.trim());
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
  
  public String getPhids()
  {
    return this.phids;
  }
  
  public void setPhids(String phids)
  {
    this.phids = phids;
  }
}
