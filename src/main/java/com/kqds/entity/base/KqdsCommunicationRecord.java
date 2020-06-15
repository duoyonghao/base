package com.kqds.entity.base;

import java.io.Serializable;

public class KqdsCommunicationRecord
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String seqId;
  private String content;
  private String status;
  private String organization;
  private String createuser;
  private String createtime;
  private String remark;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = seqId;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
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
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.content == null ? 0 : this.content.hashCode());
    result = 31 * result + (this.createtime == null ? 0 : this.createtime.hashCode());
    result = 31 * result + (this.createuser == null ? 0 : this.createuser.hashCode());
    result = 31 * result + (this.organization == null ? 0 : this.organization.hashCode());
    result = 31 * result + (this.remark == null ? 0 : this.remark.hashCode());
    result = 31 * result + (this.seqId == null ? 0 : this.seqId.hashCode());
    result = 31 * result + (this.status == null ? 0 : this.status.hashCode());
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
    KqdsCommunicationRecord other = (KqdsCommunicationRecord)obj;
    if (this.content == null)
    {
      if (other.content != null) {
        return false;
      }
    }
    else if (!this.content.equals(other.content)) {
      return false;
    }
    if (this.createtime == null)
    {
      if (other.createtime != null) {
        return false;
      }
    }
    else if (!this.createtime.equals(other.createtime)) {
      return false;
    }
    if (this.createuser == null)
    {
      if (other.createuser != null) {
        return false;
      }
    }
    else if (!this.createuser.equals(other.createuser)) {
      return false;
    }
    if (this.organization == null)
    {
      if (other.organization != null) {
        return false;
      }
    }
    else if (!this.organization.equals(other.organization)) {
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
    if (this.seqId == null)
    {
      if (other.seqId != null) {
        return false;
      }
    }
    else if (!this.seqId.equals(other.seqId)) {
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
    return true;
  }
  
  public String toString()
  {
    return 
    
      "CommunicationRecord [seqId=" + this.seqId + ", content=" + this.content + ", status=" + this.status + ", organization=" + this.organization + ", createuser=" + this.createuser + ", createtime=" + this.createtime + ", remark=" + this.remark + "]";
  }
}
