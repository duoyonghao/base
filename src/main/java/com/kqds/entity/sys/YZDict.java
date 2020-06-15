package com.kqds.entity.sys;

public class YZDict
{
  private String seqId;
  private String createuser;
  private String createtime;
  private String dictCode;
  private String dictName;
  private String organization;
  private Integer useflag;
  private String parentCode;
  private Integer orderno;
  private String remark;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
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
  
  public String getDictCode()
  {
    return this.dictCode;
  }
  
  public void setDictCode(String dictCode)
  {
    this.dictCode = (dictCode == null ? null : dictCode.trim());
  }
  
  public String getDictName()
  {
    return this.dictName;
  }
  
  public void setDictName(String dictName)
  {
    this.dictName = (dictName == null ? null : dictName.trim());
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public Integer getUseflag()
  {
    return this.useflag;
  }
  
  public void setUseflag(Integer useflag)
  {
    this.useflag = useflag;
  }
  
  public String getParentCode()
  {
    return this.parentCode;
  }
  
  public void setParentCode(String parentCode)
  {
    this.parentCode = (parentCode == null ? null : parentCode.trim());
  }
  
  public Integer getOrderno()
  {
    return this.orderno;
  }
  
  public void setOrderno(Integer orderno)
  {
    this.orderno = orderno;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = (remark == null ? null : remark.trim());
  }
}
