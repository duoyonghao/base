package com.kqds.entity.base;

public class KqdsBlct
{
  private String seqId;
  private String ctname;
  private String cttype;
  private String ctnexttype;
  private String createuser;
  private String createtime;
  private Integer useflag;
  private Integer orderno;
  private String organization;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getCtname()
  {
    return this.ctname;
  }
  
  public void setCtname(String ctname)
  {
    this.ctname = (ctname == null ? null : ctname.trim());
  }
  
  public String getCttype()
  {
    return this.cttype;
  }
  
  public void setCttype(String cttype)
  {
    this.cttype = (cttype == null ? null : cttype.trim());
  }
  
  public String getCtnexttype()
  {
    return this.ctnexttype;
  }
  
  public void setCtnexttype(String ctnexttype)
  {
    this.ctnexttype = (ctnexttype == null ? null : ctnexttype.trim());
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
  
  public Integer getUseflag()
  {
    return this.useflag;
  }
  
  public void setUseflag(Integer useflag)
  {
    this.useflag = useflag;
  }
  
  public Integer getOrderno()
  {
    return this.orderno;
  }
  
  public void setOrderno(Integer orderno)
  {
    this.orderno = orderno;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
}
