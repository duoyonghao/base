package com.kqds.entity.base;

public class KqdsCkHouse
{
  private String seqId;
  private String housename;
  private String housecode;
  private Integer sortno;
  private String organization;
  private String createuser;
  private String createtime;
  private String type;
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getHousename()
  {
    return this.housename;
  }
  
  public void setHousename(String housename)
  {
    this.housename = (housename == null ? null : housename.trim());
  }
  
  public String getHousecode()
  {
    return this.housecode;
  }
  
  public void setHousecode(String housecode)
  {
    this.housecode = (housecode == null ? null : housecode.trim());
  }
  
  public Integer getSortno()
  {
    return this.sortno;
  }
  
  public void setSortno(Integer sortno)
  {
    this.sortno = sortno;
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
}
