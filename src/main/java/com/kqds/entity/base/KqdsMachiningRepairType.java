package com.kqds.entity.base;

import java.io.Serializable;

public class KqdsMachiningRepairType implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String seqId;
  
  private String toothId;
  
  private String typeOne;
  
  private String typeOneName;
  
  private String typeSecond;
  
  private String typeSecondName;
  
  private String status;
  
  private String organization;
  
  private String createuser;
  
  private String createtime;
  
  private String remark;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }
  
  public String getToothId() {
    return this.toothId;
  }
  
  public void setToothId(String toothId) {
    this.toothId = toothId;
  }
  
  public String getTypeOne() {
    return this.typeOne;
  }
  
  public void setTypeOne(String typeOne) {
    this.typeOne = typeOne;
  }
  
  public String getTypeOneName() {
    return this.typeOneName;
  }
  
  public void setTypeOneName(String typeOneName) {
    this.typeOneName = typeOneName;
  }
  
  public String getTypeSecond() {
    return this.typeSecond;
  }
  
  public void setTypeSecond(String typeSecond) {
    this.typeSecond = typeSecond;
  }
  
  public String getTypeSecondName() {
    return this.typeSecondName;
  }
  
  public void setTypeSecondName(String typeSecondName) {
    this.typeSecondName = typeSecondName;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
  }
  
  public String getCreateuser() {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser) {
    this.createuser = createuser;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = 31 * result + ((this.createtime == null) ? 0 : this.createtime.hashCode());
    result = 31 * result + ((this.createuser == null) ? 0 : this.createuser.hashCode());
    result = 31 * result + ((this.organization == null) ? 0 : this.organization.hashCode());
    result = 31 * result + ((this.remark == null) ? 0 : this.remark.hashCode());
    result = 31 * result + ((this.seqId == null) ? 0 : this.seqId.hashCode());
    result = 31 * result + ((this.toothId == null) ? 0 : this.toothId.hashCode());
    result = 31 * result + ((this.typeOne == null) ? 0 : this.typeOne.hashCode());
    result = 31 * result + ((this.typeOneName == null) ? 0 : this.typeOneName.hashCode());
    result = 31 * result + ((this.typeSecond == null) ? 0 : this.typeSecond.hashCode());
    result = 31 * result + ((this.typeSecondName == null) ? 0 : this.typeSecondName.hashCode());
    return result;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj == null)
      return false; 
    if (getClass() != obj.getClass())
      return false; 
    KqdsMachiningRepairType other = (KqdsMachiningRepairType)obj;
    if (this.createtime == null) {
      if (other.createtime != null)
        return false; 
    } else if (!this.createtime.equals(other.createtime)) {
      return false;
    } 
    if (this.createuser == null) {
      if (other.createuser != null)
        return false; 
    } else if (!this.createuser.equals(other.createuser)) {
      return false;
    } 
    if (this.organization == null) {
      if (other.organization != null)
        return false; 
    } else if (!this.organization.equals(other.organization)) {
      return false;
    } 
    if (this.remark == null) {
      if (other.remark != null)
        return false; 
    } else if (!this.remark.equals(other.remark)) {
      return false;
    } 
    if (this.seqId == null) {
      if (other.seqId != null)
        return false; 
    } else if (!this.seqId.equals(other.seqId)) {
      return false;
    } 
    if (this.toothId == null) {
      if (other.toothId != null)
        return false; 
    } else if (!this.toothId.equals(other.toothId)) {
      return false;
    } 
    if (this.typeOne == null) {
      if (other.typeOne != null)
        return false; 
    } else if (!this.typeOne.equals(other.typeOne)) {
      return false;
    } 
    if (this.typeOneName == null) {
      if (other.typeOneName != null)
        return false; 
    } else if (!this.typeOneName.equals(other.typeOneName)) {
      return false;
    } 
    if (this.typeSecond == null) {
      if (other.typeSecond != null)
        return false; 
    } else if (!this.typeSecond.equals(other.typeSecond)) {
      return false;
    } 
    if (this.typeSecondName == null) {
      if (other.typeSecondName != null)
        return false; 
    } else if (!this.typeSecondName.equals(other.typeSecondName)) {
      return false;
    } 
    return true;
  }
  
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
  
  public String toString() {
    return "KqdsMachiningRepairType [seqId=" + this.seqId + ", toothId=" + this.toothId + ", typeOne=" + this.typeOne + 
      ", typeOneName=" + this.typeOneName + ", typeSecond=" + this.typeSecond + ", typeSecondName=" + this.typeSecondName + 
      ", organization=" + this.organization + ", createuser=" + this.createuser + ", createtime=" + this.createtime + 
      ", remark=" + this.remark + "]";
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
  }
}
