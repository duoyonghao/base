package com.kqds.entity.sys;

public class YZRegister
{
  private String seqId;
  private String dwmc;
  private String lxr;
  private String sjhm;
  private String createtime;
  private Integer status;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getDwmc()
  {
    return this.dwmc;
  }
  
  public void setDwmc(String dwmc)
  {
    this.dwmc = (dwmc == null ? null : dwmc.trim());
  }
  
  public String getLxr()
  {
    return this.lxr;
  }
  
  public void setLxr(String lxr)
  {
    this.lxr = (lxr == null ? null : lxr.trim());
  }
  
  public String getSjhm()
  {
    return this.sjhm;
  }
  
  public void setSjhm(String sjhm)
  {
    this.sjhm = (sjhm == null ? null : sjhm.trim());
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = (createtime == null ? null : createtime.trim());
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
}
