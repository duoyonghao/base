package com.hudh.lclj.entity;

import java.io.Serializable;

public class LcljApprovedMemo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String SeqId;
  private String LcljId;
  private String LcljName;
  private String nodeId;
  private String nodeName;
  private String nodetime;
  private String regtime;
  private String nexttime;
  private String recesort;
  private String regsort;
  private String regdoctor;
  private String remark;
  private String updatetime;
  private String createuser;
  private String createtime;
  private String updateuser;
  private String organization;
  private String status;
  private String nodeLimit;
  private String isRejectStatus;
  private String orderNumber;
  
  public String getSeqId()
  {
    return this.SeqId;
  }
  
  public String getOrderNumber()
  {
    return this.orderNumber;
  }
  
  public void setOrderNumber(String orderNumber)
  {
    this.orderNumber = orderNumber;
  }
  
  public String getIsRejectStatus()
  {
    return this.isRejectStatus;
  }
  
  public void setIsRejectStatus(String isRejectStatus)
  {
    this.isRejectStatus = isRejectStatus;
  }
  
  public String getNodeLimit()
  {
    return this.nodeLimit;
  }
  
  public void setNodeLimit(String nodeLimit)
  {
    this.nodeLimit = nodeLimit;
  }
  
  public void setSeqId(String seqId)
  {
    this.SeqId = seqId;
  }
  
  public String getLcljId()
  {
    return this.LcljId;
  }
  
  public void setLcljId(String lcljId)
  {
    this.LcljId = lcljId;
  }
  
  public String getNodeId()
  {
    return this.nodeId;
  }
  
  public void setNodeId(String nodeId)
  {
    this.nodeId = nodeId;
  }
  
  public String getNodeName()
  {
    return this.nodeName;
  }
  
  public void setNodeName(String nodeName)
  {
    this.nodeName = nodeName;
  }
  
  public String getNodetime()
  {
    return this.nodetime;
  }
  
  public void setNodetime(String nodetime)
  {
    this.nodetime = nodetime;
  }
  
  public String getRegtime()
  {
    return this.regtime;
  }
  
  public void setRegtime(String regtime)
  {
    this.regtime = regtime;
  }
  
  public String getNexttime()
  {
    return this.nexttime;
  }
  
  public void setNexttime(String nexttime)
  {
    this.nexttime = nexttime;
  }
  
  public String getRecesort()
  {
    return this.recesort;
  }
  
  public void setRecesort(String recesort)
  {
    this.recesort = recesort;
  }
  
  public String getRegsort()
  {
    return this.regsort;
  }
  
  public void setRegsort(String regsort)
  {
    this.regsort = regsort;
  }
  
  public String getRegdoctor()
  {
    return this.regdoctor;
  }
  
  public void setRegdoctor(String regdoctor)
  {
    this.regdoctor = regdoctor;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getUpdatetime()
  {
    return this.updatetime;
  }
  
  public void setUpdatetime(String updatetime)
  {
    this.updatetime = updatetime;
  }
  
  public String getUpdateuser()
  {
    return this.updateuser;
  }
  
  public void setUpdateuser(String updateuser)
  {
    this.updateuser = updateuser;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = createuser;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
  public String getLcljName()
  {
    return this.LcljName;
  }
  
  public void setLcljName(String lcljName)
  {
    this.LcljName = lcljName;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = createtime;
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.LcljId == null ? 0 : this.LcljId.hashCode());
    result = 31 * result + (this.SeqId == null ? 0 : this.SeqId.hashCode());
    result = 31 * result + (this.nexttime == null ? 0 : this.nexttime.hashCode());
    result = 31 * result + (this.nodeId == null ? 0 : this.nodeId.hashCode());
    result = 31 * result + (this.nodeName == null ? 0 : this.nodeName.hashCode());
    result = 31 * result + (this.nodetime == null ? 0 : this.nodetime.hashCode());
    result = 31 * result + (this.recesort == null ? 0 : this.recesort.hashCode());
    result = 31 * result + (this.regdoctor == null ? 0 : this.regdoctor.hashCode());
    result = 31 * result + (this.regsort == null ? 0 : this.regsort.hashCode());
    result = 31 * result + (this.regtime == null ? 0 : this.regtime.hashCode());
    result = 31 * result + (this.remark == null ? 0 : this.remark.hashCode());
    result = 31 * result + (this.status == null ? 0 : this.status.hashCode());
    result = 31 * result + (this.updatetime == null ? 0 : this.updatetime.hashCode());
    result = 31 * result + (this.updateuser == null ? 0 : this.updateuser.hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LcljApprovedMemo other = (LcljApprovedMemo)obj;
    if (this.LcljId == null)
    {
      if (other.LcljId != null) {
        return false;
      }
    }
    else if (!this.LcljId.equals(other.LcljId)) {
      return false;
    }
    if (this.SeqId == null)
    {
      if (other.SeqId != null) {
        return false;
      }
    }
    else if (!this.SeqId.equals(other.SeqId)) {
      return false;
    }
    if (this.nexttime == null)
    {
      if (other.nexttime != null) {
        return false;
      }
    }
    else if (!this.nexttime.equals(other.nexttime)) {
      return false;
    }
    if (this.nodeId == null)
    {
      if (other.nodeId != null) {
        return false;
      }
    }
    else if (!this.nodeId.equals(other.nodeId)) {
      return false;
    }
    if (this.nodeName == null)
    {
      if (other.nodeName != null) {
        return false;
      }
    }
    else if (!this.nodeName.equals(other.nodeName)) {
      return false;
    }
    if (this.nodetime == null)
    {
      if (other.nodetime != null) {
        return false;
      }
    }
    else if (!this.nodetime.equals(other.nodetime)) {
      return false;
    }
    if (this.recesort == null)
    {
      if (other.recesort != null) {
        return false;
      }
    }
    else if (!this.recesort.equals(other.recesort)) {
      return false;
    }
    if (this.regdoctor == null)
    {
      if (other.regdoctor != null) {
        return false;
      }
    }
    else if (!this.regdoctor.equals(other.regdoctor)) {
      return false;
    }
    if (this.regsort == null)
    {
      if (other.regsort != null) {
        return false;
      }
    }
    else if (!this.regsort.equals(other.regsort)) {
      return false;
    }
    if (this.regtime == null)
    {
      if (other.regtime != null) {
        return false;
      }
    }
    else if (!this.regtime.equals(other.regtime)) {
      return false;
    }
    if (this.remark == null)
    {
      if (other.remark != null) {
        return false;
      }
    }
    else if (!this.remark.equals(other.remark)) {
      return false;
    }
    if (this.status == null)
    {
      if (other.status != null) {
        return false;
      }
    }
    else if (!this.status.equals(other.status)) {
      return false;
    }
    if (this.updatetime == null)
    {
      if (other.updatetime != null) {
        return false;
      }
    }
    else if (!this.updatetime.equals(other.updatetime)) {
      return false;
    }
    if (this.updateuser == null)
    {
      if (other.updateuser != null) {
        return false;
      }
    }
    else if (!this.updateuser.equals(other.updateuser)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return 
    

      "LcljApprovedMemo [SeqId=" + this.SeqId + ", LcljId=" + this.LcljId + ", nodeId=" + this.nodeId + ", nodeName=" + this.nodeName + ", nodetime=" + this.nodetime + ", regtime=" + this.regtime + ", nexttime=" + this.nexttime + ", recesort=" + this.recesort + ", regsort=" + this.regsort + ", regdoctor=" + this.regdoctor + ", remark=" + this.remark + ", updatetime=" + this.updatetime + ", updateuser=" + this.updateuser + ", status=" + this.status + "]";
  }
}
