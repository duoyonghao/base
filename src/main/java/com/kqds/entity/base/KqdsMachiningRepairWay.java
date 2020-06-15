package com.kqds.entity.base;

import java.io.Serializable;

public class KqdsMachiningRepairWay
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String seqId;
  private String worksheetId;
  private String upleftTooth;
  private String leftLowerTooth;
  private String upperRightTooth;
  private String lowRightTooth;
  private String repairProjectArr;
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
  
  public String getWorksheetId()
  {
    return this.worksheetId;
  }
  
  public void setWorksheetId(String worksheetId)
  {
    this.worksheetId = worksheetId;
  }
  
  public String getUpleftTooth()
  {
    return this.upleftTooth;
  }
  
  public void setUpleftTooth(String upleftTooth)
  {
    this.upleftTooth = upleftTooth;
  }
  
  public String getLeftLowerTooth()
  {
    return this.leftLowerTooth;
  }
  
  public void setLeftLowerTooth(String leftLowerTooth)
  {
    this.leftLowerTooth = leftLowerTooth;
  }
  
  public String getUpperRightTooth()
  {
    return this.upperRightTooth;
  }
  
  public void setUpperRightTooth(String upperRightTooth)
  {
    this.upperRightTooth = upperRightTooth;
  }
  
  public String getLowRightTooth()
  {
    return this.lowRightTooth;
  }
  
  public void setLowRightTooth(String lowRightTooth)
  {
    this.lowRightTooth = lowRightTooth;
  }
  
  public String getRepairProjectArr()
  {
    return this.repairProjectArr;
  }
  
  public void setRepairProjectArr(String repairProjectArr)
  {
    this.repairProjectArr = repairProjectArr;
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
    result = 31 * result + (this.createtime == null ? 0 : this.createtime.hashCode());
    result = 31 * result + (this.createuser == null ? 0 : this.createuser.hashCode());
    result = 31 * result + (this.leftLowerTooth == null ? 0 : this.leftLowerTooth.hashCode());
    result = 31 * result + (this.lowRightTooth == null ? 0 : this.lowRightTooth.hashCode());
    result = 31 * result + (this.organization == null ? 0 : this.organization.hashCode());
    result = 31 * result + (this.remark == null ? 0 : this.remark.hashCode());
    result = 31 * result + (this.seqId == null ? 0 : this.seqId.hashCode());
    result = 31 * result + (this.upleftTooth == null ? 0 : this.upleftTooth.hashCode());
    result = 31 * result + (this.upperRightTooth == null ? 0 : this.upperRightTooth.hashCode());
    result = 31 * result + (this.worksheetId == null ? 0 : this.worksheetId.hashCode());
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
    KqdsMachiningRepairWay other = (KqdsMachiningRepairWay)obj;
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
    if (this.leftLowerTooth == null)
    {
      if (other.leftLowerTooth != null) {
        return false;
      }
    }
    else if (!this.leftLowerTooth.equals(other.leftLowerTooth)) {
      return false;
    }
    if (this.lowRightTooth == null)
    {
      if (other.lowRightTooth != null) {
        return false;
      }
    }
    else if (!this.lowRightTooth.equals(other.lowRightTooth)) {
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
    if (this.upleftTooth == null)
    {
      if (other.upleftTooth != null) {
        return false;
      }
    }
    else if (!this.upleftTooth.equals(other.upleftTooth)) {
      return false;
    }
    if (this.upperRightTooth == null)
    {
      if (other.upperRightTooth != null) {
        return false;
      }
    }
    else if (!this.upperRightTooth.equals(other.upperRightTooth)) {
      return false;
    }
    if (this.worksheetId == null)
    {
      if (other.worksheetId != null) {
        return false;
      }
    }
    else if (!this.worksheetId.equals(other.worksheetId)) {
      return false;
    }
    return true;
  }
  
  protected Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  
  public String toString()
  {
    return 
    

      "KqdsMachiningRepairWay [seqId=" + this.seqId + ", worksheetId=" + this.worksheetId + ", upleftTooth=" + this.upleftTooth + ", leftLowerTooth=" + this.leftLowerTooth + ", upperRightTooth=" + this.upperRightTooth + ", lowRightTooth=" + this.lowRightTooth + ", organization=" + this.organization + ", createuser=" + this.createuser + ", createtime=" + this.createtime + ", remark=" + this.remark + "]";
  }
  
  protected void finalize()
    throws Throwable
  {
    super.finalize();
  }
}
