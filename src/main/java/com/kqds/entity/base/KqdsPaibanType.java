package com.kqds.entity.base;

public class KqdsPaibanType
{
  private String seqId;
  private String typename;
  private String startdate;
  private String enddate;
  private String createtime;
  private String createuser;
  private String organization;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getTypename()
  {
    return this.typename;
  }
  
  public void setTypename(String typename)
  {
    this.typename = (typename == null ? null : typename.trim());
  }
  
  public String getStartdate()
  {
    return this.startdate;
  }
  
  public void setStartdate(String startdate)
  {
    this.startdate = (startdate == null ? null : startdate.trim());
  }
  
  public String getEnddate()
  {
    return this.enddate;
  }
  
  public void setEnddate(String enddate)
  {
    this.enddate = (enddate == null ? null : enddate.trim());
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = (createtime == null ? null : createtime.trim());
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = (createuser == null ? null : createuser.trim());
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
