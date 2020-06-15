package com.kqds.entity.base;

public class KqdsCkGoodsDetail
{
  private String seqId;
  private String goodsname;
  private String goodscode;
  private String goodstype;
  private String goodsnorms;
  private String goodsunit;
  private String organization;
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
  private String status;
  private String type;
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
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
  
  public String getGoodstype()
  {
    return this.goodstype;
  }
  
  public void setGoodstype(String goodstype)
  {
    this.goodstype = (goodstype == null ? null : goodstype.trim());
  }
  
  public String getGoodsnorms()
  {
    return this.goodsnorms;
  }
  
  public void setGoodsnorms(String goodsnorms)
  {
    this.goodsnorms = (goodsnorms == null ? null : goodsnorms.trim());
  }
  
  public String getGoodsunit()
  {
    return this.goodsunit;
  }
  
  public void setGoodsunit(String goodsunit)
  {
    this.goodsunit = (goodsunit == null ? null : goodsunit.trim());
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
    this.kuwei = (kuwei == null ? null : kuwei.trim());
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = (remark == null ? null : remark.trim());
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
    this.pym = (pym == null ? null : pym.trim());
  }
}
