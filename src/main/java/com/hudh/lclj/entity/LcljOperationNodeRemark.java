package com.hudh.lclj.entity;

import java.io.Serializable;

public class LcljOperationNodeRemark
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String seqId;
  private String LcljId;
  private String order_number;
  private String nodeName;
  private String nodeId;
  private String createuser;
  private String createtime;
  private String remark;
  private String organization;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = seqId;
  }
  
  public String getLcljId()
  {
    return this.LcljId;
  }
  
  public void setLcljId(String lcljId)
  {
    this.LcljId = lcljId;
  }
  
  public String getOrder_number()
  {
    return this.order_number;
  }
  
  public void setOrder_number(String order_number)
  {
    this.order_number = order_number;
  }
  
  public String getNodeName()
  {
    return this.nodeName;
  }
  
  public void setNodeName(String nodeName)
  {
    this.nodeName = nodeName;
  }
  
  public String getNodeId()
  {
    return this.nodeId;
  }
  
  public void setNodeId(String nodeId)
  {
    this.nodeId = nodeId;
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
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
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
