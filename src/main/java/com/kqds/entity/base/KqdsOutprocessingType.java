package com.kqds.entity.base;

public class KqdsOutprocessingType
{
  private String seqId;
  private String processingfactory;
  private String typename;
  private String typeno;
  private String createuser;
  private String createtime;
  private Integer useflag;
  private String organization;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getProcessingfactory()
  {
    return this.processingfactory;
  }
  
  public void setProcessingfactory(String processingfactory)
  {
    this.processingfactory = (processingfactory == null ? null : processingfactory.trim());
  }
  
  public String getTypename()
  {
    return this.typename;
  }
  
  public void setTypename(String typename)
  {
    this.typename = (typename == null ? null : typename.trim());
  }
  
  public String getTypeno()
  {
    return this.typeno;
  }
  
  public void setTypeno(String typeno)
  {
    this.typeno = (typeno == null ? null : typeno.trim());
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
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
}
