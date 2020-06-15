package com.hudh.lclj.entity;

import java.io.Serializable;

public class LcljOrderTrackDeleteRecord
  implements Serializable
{
  private static final long serialVersionUID = 2228214573173010697L;
  private String seqId;
  private String createuser;
  private String createtime;
  private String lcljId;
  private String order_number;
  private String organization;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = seqId;
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = createuser;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = createtime;
  }
  
  public String getLcljId()
  {
    return this.lcljId;
  }
  
  public void setLcljId(String lcljId)
  {
    this.lcljId = lcljId;
  }
  
  public String getOrder_number()
  {
    return this.order_number;
  }
  
  public void setOrder_number(String order_number)
  {
    this.order_number = order_number;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
}
