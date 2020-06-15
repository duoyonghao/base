package com.kqds.entity.base;

public class KqdsCkGoodsType
{
  private String seqId;
  private String goodstype;
  private String typeno;
  private String perid;
  private String createuser;
  private String createtime;
  private String organization;
  private Integer sortno;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getGoodstype()
  {
    return this.goodstype;
  }
  
  public void setGoodstype(String goodstype)
  {
    this.goodstype = (goodstype == null ? null : goodstype.trim());
  }
  
  public String getTypeno()
  {
    return this.typeno;
  }
  
  public void setTypeno(String typeno)
  {
    this.typeno = (typeno == null ? null : typeno.trim());
  }
  
  public String getPerid()
  {
    return this.perid;
  }
  
  public void setPerid(String perid)
  {
    this.perid = (perid == null ? null : perid.trim());
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
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public Integer getSortno()
  {
    return this.sortno;
  }
  
  public void setSortno(Integer sortno)
  {
    this.sortno = sortno;
  }
}
