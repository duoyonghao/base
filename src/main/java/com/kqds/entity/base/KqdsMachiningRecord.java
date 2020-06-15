package com.kqds.entity.base;

import java.io.Serializable;

public class KqdsMachiningRecord implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String seqId;
  
  private String worksheetId;
  
  private String orderNumber;
  
  private String systemNumber;
  
  private String userCode;
  
  private String userName;
  
  private String status;
  
  private String operationalContext;
  
  private String createuser;
  
  private String createtime;
  
  private String material;
  
  private String organization;
  
  private String remark;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }
  
  public String getWorksheetId() {
    return this.worksheetId;
  }
  
  public void setWorksheetId(String worksheetId) {
    this.worksheetId = worksheetId;
  }
  
  public String getOrderNumber() {
    return this.orderNumber;
  }
  
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }
  
  public String getSystemNumber() {
    return this.systemNumber;
  }
  
  public void setSystemNumber(String systemNumber) {
    this.systemNumber = systemNumber;
  }
  
  public String getUserCode() {
    return this.userCode;
  }
  
  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getOperationalContext() {
    return this.operationalContext;
  }
  
  public void setOperationalContext(String operationalContext) {
    this.operationalContext = operationalContext;
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
  
  public String getMaterial() {
    return this.material;
  }
  
  public void setMaterial(String material) {
    this.material = material;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
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
    result = 31 * result + ((this.material == null) ? 0 : this.material.hashCode());
    result = 31 * result + ((this.operationalContext == null) ? 0 : this.operationalContext.hashCode());
    result = 31 * result + ((this.orderNumber == null) ? 0 : this.orderNumber.hashCode());
    result = 31 * result + ((this.remark == null) ? 0 : this.remark.hashCode());
    result = 31 * result + ((this.seqId == null) ? 0 : this.seqId.hashCode());
    result = 31 * result + ((this.status == null) ? 0 : this.status.hashCode());
    result = 31 * result + ((this.systemNumber == null) ? 0 : this.systemNumber.hashCode());
    result = 31 * result + ((this.userCode == null) ? 0 : this.userCode.hashCode());
    result = 31 * result + ((this.userName == null) ? 0 : this.userName.hashCode());
    result = 31 * result + ((this.worksheetId == null) ? 0 : this.worksheetId.hashCode());
    return result;
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj == null)
      return false; 
    if (getClass() != obj.getClass())
      return false; 
    KqdsMachiningRecord other = (KqdsMachiningRecord)obj;
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
    if (this.material == null) {
      if (other.material != null)
        return false; 
    } else if (!this.material.equals(other.material)) {
      return false;
    } 
    if (this.operationalContext == null) {
      if (other.operationalContext != null)
        return false; 
    } else if (!this.operationalContext.equals(other.operationalContext)) {
      return false;
    } 
    if (this.orderNumber == null) {
      if (other.orderNumber != null)
        return false; 
    } else if (!this.orderNumber.equals(other.orderNumber)) {
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
    if (this.status == null) {
      if (other.status != null)
        return false; 
    } else if (!this.status.equals(other.status)) {
      return false;
    } 
    if (this.systemNumber == null) {
      if (other.systemNumber != null)
        return false; 
    } else if (!this.systemNumber.equals(other.systemNumber)) {
      return false;
    } 
    if (this.userCode == null) {
      if (other.userCode != null)
        return false; 
    } else if (!this.userCode.equals(other.userCode)) {
      return false;
    } 
    if (this.userName == null) {
      if (other.userName != null)
        return false; 
    } else if (!this.userName.equals(other.userName)) {
      return false;
    } 
    if (this.worksheetId == null) {
      if (other.worksheetId != null)
        return false; 
    } else if (!this.worksheetId.equals(other.worksheetId)) {
      return false;
    } 
    return true;
  }
  
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
  }
  
  public String toString() {
    return "KqdsMachiningRecord [seqId=" + this.seqId + ", worksheetId=" + this.worksheetId + ", orderNumber=" + this.orderNumber + 
      ", systemNumber=" + this.systemNumber + ", userCode=" + this.userCode + ", userName=" + this.userName + ", status=" + 
      this.status + ", operationalContext=" + this.operationalContext + ", createuser=" + this.createuser + ", createtime=" + 
      this.createtime + ", material=" + this.material + ", remark=" + this.remark + "]";
  }
}
