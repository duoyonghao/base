package com.kqds.entity.base;

import java.io.Serializable;

public class KqdsMachiningType
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String seqId;
  private String typename;
  private String parentId;
  private String isCategory;
  private String createuser;
  private String createtime;
  private Integer useflag;
  private String organization;
  private String remark;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = seqId;
  }
  
  public String getTypename()
  {
    return this.typename;
  }
  
  public void setTypename(String typename)
  {
    this.typename = typename;
  }
  
  public String getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(String parentId)
  {
    this.parentId = parentId;
  }
  
  public String getIsCategory()
  {
    return this.isCategory;
  }
  
  public void setIsCategory(String isCategory)
  {
    this.isCategory = isCategory;
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
    this.organization = organization;
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
    result = 31 * result + (this.createtime == null ? 0 : this.createtime.hashCode());
    result = 31 * result + (this.createuser == null ? 0 : this.createuser.hashCode());
    result = 31 * result + (this.organization == null ? 0 : this.organization.hashCode());
    result = 31 * result + (this.parentId == null ? 0 : this.parentId.hashCode());
    result = 31 * result + (this.remark == null ? 0 : this.remark.hashCode());
    result = 31 * result + (this.seqId == null ? 0 : this.seqId.hashCode());
    result = 31 * result + (this.typename == null ? 0 : this.typename.hashCode());
    result = 31 * result + (this.isCategory == null ? 0 : this.isCategory.hashCode());
    result = 31 * result + (this.useflag == null ? 0 : this.useflag.hashCode());
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
    KqdsMachiningType other = (KqdsMachiningType)obj;
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
    if (this.parentId == null)
    {
      if (other.parentId != null) {
        return false;
      }
    }
    else if (!this.parentId.equals(other.parentId)) {
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
    if (this.typename == null)
    {
      if (other.typename != null) {
        return false;
      }
    }
    else if (!this.typename.equals(other.typename)) {
      return false;
    }
    if (this.isCategory == null)
    {
      if (other.isCategory != null) {
        return false;
      }
    }
    else if (!this.isCategory.equals(other.isCategory)) {
      return false;
    }
    if (this.useflag == null)
    {
      if (other.useflag != null) {
        return false;
      }
    }
    else if (!this.useflag.equals(other.useflag)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return 
    
      "KqdsMachiningType [seqId=" + this.seqId + ", typename=" + this.typename + ", parentId=" + this.parentId + ", isCategory=" + this.isCategory + ", createuser=" + this.createuser + ", createtime=" + this.createtime + ", useflag=" + this.useflag + ", organization=" + this.organization + ", remark=" + this.remark + "]";
  }
}
