package com.kqds.entity.base;

public class KqdsTreatitemTcType
{
  private String seqId;
  private String name;
  private Integer isopen;
  private String organization;
  private String parentid;
  private String createtime;
  private String createuser;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = (name == null ? null : name.trim());
  }
  
  public Integer getIsopen()
  {
    return this.isopen;
  }
  
  public void setIsopen(Integer isopen)
  {
    this.isopen = isopen;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public String getParentid()
  {
    return this.parentid;
  }
  
  public void setParentid(String parentid)
  {
    this.parentid = (parentid == null ? null : parentid.trim());
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
}
