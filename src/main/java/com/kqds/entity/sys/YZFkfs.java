package com.kqds.entity.sys;

public class YZFkfs
{
  private String seqId;
  private String payname;
  private Integer ismoney;
  private String costfield;
  private String createuser;
  private String createtime;
  private Integer orderno;
  private Integer useflag;
  private String memberfield;
  private Integer noedit;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getPayname()
  {
    return this.payname;
  }
  
  public void setPayname(String payname)
  {
    this.payname = (payname == null ? null : payname.trim());
  }
  
  public Integer getIsmoney()
  {
    return this.ismoney;
  }
  
  public void setIsmoney(Integer ismoney)
  {
    this.ismoney = ismoney;
  }
  
  public String getCostfield()
  {
    return this.costfield;
  }
  
  public void setCostfield(String costfield)
  {
    this.costfield = (costfield == null ? null : costfield.trim());
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
  
  public Integer getOrderno()
  {
    return this.orderno;
  }
  
  public void setOrderno(Integer orderno)
  {
    this.orderno = orderno;
  }
  
  public Integer getUseflag()
  {
    return this.useflag;
  }
  
  public void setUseflag(Integer useflag)
  {
    this.useflag = useflag;
  }
  
  public String getMemberfield()
  {
    return this.memberfield;
  }
  
  public void setMemberfield(String memberfield)
  {
    this.memberfield = (memberfield == null ? null : memberfield.trim());
  }
  
  public Integer getNoedit()
  {
    return this.noedit;
  }
  
  public void setNoedit(Integer noedit)
  {
    this.noedit = noedit;
  }
}
