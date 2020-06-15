package com.kqds.entity.base;

import java.io.Serializable;
import java.math.BigDecimal;

public class KqdsCkBathGoods
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String seqId;
  private String sshouse;
  private BigDecimal goodsprice;
  private Integer nums;
  private Integer numsexport;
  private BigDecimal goodspriceexport;
  private String organization;
  private BigDecimal kcmoney;
  private String goodsdetailid;
  private String goodsname;
  private String goodscode;
  private String goodstype;
  private String goodsnorms;
  private String goodsunit;
  private String createuser;
  private String createtime;
  private Integer sortno;
  private String kuwei;
  private String remark;
  private Integer minnums;
  private Integer mingj;
  private Integer maxnums;
  private Integer maxgj;
  private String pym;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = seqId;
  }
  
  public String getSshouse()
  {
    return this.sshouse;
  }
  
  public void setSshouse(String sshouse)
  {
    this.sshouse = sshouse;
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
    this.organization = organization;
  }
  
  public BigDecimal getKcmoney()
  {
    return this.kcmoney;
  }
  
  public void setKcmoney(BigDecimal kcmoney)
  {
    this.kcmoney = kcmoney;
  }
  
  public String getGoodsdetailid()
  {
    return this.goodsdetailid;
  }
  
  public void setGoodsdetailid(String goodsdetailid)
  {
    this.goodsdetailid = goodsdetailid;
  }
  
  public String getGoodsname()
  {
    return this.goodsname;
  }
  
  public void setGoodsname(String goodsname)
  {
    this.goodsname = goodsname;
  }
  
  public String getGoodscode()
  {
    return this.goodscode;
  }
  
  public void setGoodscode(String goodscode)
  {
    this.goodscode = goodscode;
  }
  
  public String getGoodstype()
  {
    return this.goodstype;
  }
  
  public void setGoodstype(String goodstype)
  {
    this.goodstype = goodstype;
  }
  
  public String getGoodsnorms()
  {
    return this.goodsnorms;
  }
  
  public void setGoodsnorms(String goodsnorms)
  {
    this.goodsnorms = goodsnorms;
  }
  
  public String getGoodsunit()
  {
    return this.goodsunit;
  }
  
  public void setGoodsunit(String goodsunit)
  {
    this.goodsunit = goodsunit;
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = createuser;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = createtime;
  }
  
  public Integer getSortno()
  {
    return this.sortno;
  }
  
  public void setSortno(Integer sortno)
  {
    this.sortno = sortno;
  }
  
  public String getKuwei()
  {
    return this.kuwei;
  }
  
  public void setKuwei(String kuwei)
  {
    this.kuwei = kuwei;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public Integer getMinnums()
  {
    return this.minnums;
  }
  
  public void setMinnums(Integer minnums)
  {
    this.minnums = minnums;
  }
  
  public Integer getMingj()
  {
    return this.mingj;
  }
  
  public void setMingj(Integer mingj)
  {
    this.mingj = mingj;
  }
  
  public Integer getMaxnums()
  {
    return this.maxnums;
  }
  
  public void setMaxnums(Integer maxnums)
  {
    this.maxnums = maxnums;
  }
  
  public Integer getMaxgj()
  {
    return this.maxgj;
  }
  
  public void setMaxgj(Integer maxgj)
  {
    this.maxgj = maxgj;
  }
  
  public String getPym()
  {
    return this.pym;
  }
  
  public void setPym(String pym)
  {
    this.pym = pym;
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.createtime == null ? 0 : this.createtime.hashCode());
    result = 31 * result + (this.createuser == null ? 0 : this.createuser.hashCode());
    result = 31 * result + (this.goodscode == null ? 0 : this.goodscode.hashCode());
    result = 31 * result + (this.goodsdetailid == null ? 0 : this.goodsdetailid.hashCode());
    result = 31 * result + (this.goodsname == null ? 0 : this.goodsname.hashCode());
    result = 31 * result + (this.goodsnorms == null ? 0 : this.goodsnorms.hashCode());
    result = 31 * result + (this.goodsprice == null ? 0 : this.goodsprice.hashCode());
    result = 31 * result + (this.goodspriceexport == null ? 0 : this.goodspriceexport.hashCode());
    result = 31 * result + (this.goodstype == null ? 0 : this.goodstype.hashCode());
    result = 31 * result + (this.goodsunit == null ? 0 : this.goodsunit.hashCode());
    result = 31 * result + (this.kcmoney == null ? 0 : this.kcmoney.hashCode());
    result = 31 * result + (this.kuwei == null ? 0 : this.kuwei.hashCode());
    result = 31 * result + (this.maxgj == null ? 0 : this.maxgj.hashCode());
    result = 31 * result + (this.maxnums == null ? 0 : this.maxnums.hashCode());
    result = 31 * result + (this.mingj == null ? 0 : this.mingj.hashCode());
    result = 31 * result + (this.minnums == null ? 0 : this.minnums.hashCode());
    result = 31 * result + (this.nums == null ? 0 : this.nums.hashCode());
    result = 31 * result + (this.numsexport == null ? 0 : this.numsexport.hashCode());
    result = 31 * result + (this.organization == null ? 0 : this.organization.hashCode());
    result = 31 * result + (this.pym == null ? 0 : this.pym.hashCode());
    result = 31 * result + (this.remark == null ? 0 : this.remark.hashCode());
    result = 31 * result + (this.seqId == null ? 0 : this.seqId.hashCode());
    result = 31 * result + (this.sortno == null ? 0 : this.sortno.hashCode());
    result = 31 * result + (this.sshouse == null ? 0 : this.sshouse.hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    KqdsCkBathGoods other = (KqdsCkBathGoods)obj;
    if (this.createtime == null)
    {
      if (other.createtime != null) {
        return false;
      }
    }
    else if (!this.createtime.equals(other.createtime)) {
      return false;
    }
    if (this.createuser == null)
    {
      if (other.createuser != null) {
        return false;
      }
    }
    else if (!this.createuser.equals(other.createuser)) {
      return false;
    }
    if (this.goodscode == null)
    {
      if (other.goodscode != null) {
        return false;
      }
    }
    else if (!this.goodscode.equals(other.goodscode)) {
      return false;
    }
    if (this.goodsdetailid == null)
    {
      if (other.goodsdetailid != null) {
        return false;
      }
    }
    else if (!this.goodsdetailid.equals(other.goodsdetailid)) {
      return false;
    }
    if (this.goodsname == null)
    {
      if (other.goodsname != null) {
        return false;
      }
    }
    else if (!this.goodsname.equals(other.goodsname)) {
      return false;
    }
    if (this.goodsnorms == null)
    {
      if (other.goodsnorms != null) {
        return false;
      }
    }
    else if (!this.goodsnorms.equals(other.goodsnorms)) {
      return false;
    }
    if (this.goodsprice == null)
    {
      if (other.goodsprice != null) {
        return false;
      }
    }
    else if (!this.goodsprice.equals(other.goodsprice)) {
      return false;
    }
    if (this.goodspriceexport == null)
    {
      if (other.goodspriceexport != null) {
        return false;
      }
    }
    else if (!this.goodspriceexport.equals(other.goodspriceexport)) {
      return false;
    }
    if (this.goodstype == null)
    {
      if (other.goodstype != null) {
        return false;
      }
    }
    else if (!this.goodstype.equals(other.goodstype)) {
      return false;
    }
    if (this.goodsunit == null)
    {
      if (other.goodsunit != null) {
        return false;
      }
    }
    else if (!this.goodsunit.equals(other.goodsunit)) {
      return false;
    }
    if (this.kcmoney == null)
    {
      if (other.kcmoney != null) {
        return false;
      }
    }
    else if (!this.kcmoney.equals(other.kcmoney)) {
      return false;
    }
    if (this.kuwei == null)
    {
      if (other.kuwei != null) {
        return false;
      }
    }
    else if (!this.kuwei.equals(other.kuwei)) {
      return false;
    }
    if (this.maxgj == null)
    {
      if (other.maxgj != null) {
        return false;
      }
    }
    else if (!this.maxgj.equals(other.maxgj)) {
      return false;
    }
    if (this.maxnums == null)
    {
      if (other.maxnums != null) {
        return false;
      }
    }
    else if (!this.maxnums.equals(other.maxnums)) {
      return false;
    }
    if (this.mingj == null)
    {
      if (other.mingj != null) {
        return false;
      }
    }
    else if (!this.mingj.equals(other.mingj)) {
      return false;
    }
    if (this.minnums == null)
    {
      if (other.minnums != null) {
        return false;
      }
    }
    else if (!this.minnums.equals(other.minnums)) {
      return false;
    }
    if (this.nums == null)
    {
      if (other.nums != null) {
        return false;
      }
    }
    else if (!this.nums.equals(other.nums)) {
      return false;
    }
    if (this.numsexport == null)
    {
      if (other.numsexport != null) {
        return false;
      }
    }
    else if (!this.numsexport.equals(other.numsexport)) {
      return false;
    }
    if (this.organization == null)
    {
      if (other.organization != null) {
        return false;
      }
    }
    else if (!this.organization.equals(other.organization)) {
      return false;
    }
    if (this.pym == null)
    {
      if (other.pym != null) {
        return false;
      }
    }
    else if (!this.pym.equals(other.pym)) {
      return false;
    }
    if (this.remark == null)
    {
      if (other.remark != null) {
        return false;
      }
    }
    else if (!this.remark.equals(other.remark)) {
      return false;
    }
    if (this.seqId == null)
    {
      if (other.seqId != null) {
        return false;
      }
    }
    else if (!this.seqId.equals(other.seqId)) {
      return false;
    }
    if (this.sortno == null)
    {
      if (other.sortno != null) {
        return false;
      }
    }
    else if (!this.sortno.equals(other.sortno)) {
      return false;
    }
    if (this.sshouse == null)
    {
      if (other.sshouse != null) {
        return false;
      }
    }
    else if (!this.sshouse.equals(other.sshouse)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return 
    




      "KqdsCkBathGoods [seqId=" + this.seqId + ", sshouse=" + this.sshouse + ", goodsprice=" + this.goodsprice + ", nums=" + this.nums + ", numsexport=" + this.numsexport + ", goodspriceexport=" + this.goodspriceexport + ", organization=" + this.organization + ", kcmoney=" + this.kcmoney + ", goodsdetailid=" + this.goodsdetailid + ", goodsname=" + this.goodsname + ", goodscode=" + this.goodscode + ", goodstype=" + this.goodstype + ", goodsnorms=" + this.goodsnorms + ", goodsunit=" + this.goodsunit + ", createuser=" + this.createuser + ", createtime=" + this.createtime + ", sortno=" + this.sortno + ", kuwei=" + this.kuwei + ", remark=" + this.remark + ", minnums=" + this.minnums + ", mingj=" + this.mingj + ", maxnums=" + this.maxnums + ", maxgj=" + this.maxgj + ", pym=" + this.pym + "]";
  }
  
  protected Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
