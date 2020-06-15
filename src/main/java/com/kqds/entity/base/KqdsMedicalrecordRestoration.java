package com.kqds.entity.base;

public class KqdsMedicalrecordRestoration
{
  private String seqId;
  private String organization;
  private String meid;
  private String usercode;
  private String retention;
  private String remark;
  private String restorationNumber;
  private String appointmentRecord;
  private String createuser;
  private String createtime;
  private String restorationType;
  private String bridge;
  private String overdenture;
  private String cemented;
  private String screwed;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public String getMeid()
  {
    return this.meid;
  }
  
  public void setMeid(String meid)
  {
    this.meid = (meid == null ? null : meid.trim());
  }
  
  public String getUsercode()
  {
    return this.usercode;
  }
  
  public void setUsercode(String usercode)
  {
    this.usercode = (usercode == null ? null : usercode.trim());
  }
  
  public String getRetention()
  {
    return this.retention;
  }
  
  public void setRetention(String retention)
  {
    this.retention = (retention == null ? null : retention.trim());
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = (remark == null ? null : remark.trim());
  }
  
  public String getRestorationNumber()
  {
    return this.restorationNumber;
  }
  
  public void setRestorationNumber(String restorationNumber)
  {
    this.restorationNumber = (restorationNumber == null ? null : restorationNumber.trim());
  }
  
  public String getAppointmentRecord()
  {
    return this.appointmentRecord;
  }
  
  public void setAppointmentRecord(String appointmentRecord)
  {
    this.appointmentRecord = (appointmentRecord == null ? null : appointmentRecord.trim());
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
  
  public String getRestorationType()
  {
    return this.restorationType;
  }
  
  public void setRestorationType(String restorationType)
  {
    this.restorationType = (restorationType == null ? null : restorationType.trim());
  }
  
  public String getBridge()
  {
    return this.bridge;
  }
  
  public void setBridge(String bridge)
  {
    this.bridge = (bridge == null ? null : bridge.trim());
  }
  
  public String getOverdenture()
  {
    return this.overdenture;
  }
  
  public void setOverdenture(String overdenture)
  {
    this.overdenture = (overdenture == null ? null : overdenture.trim());
  }
  
  public String getCemented()
  {
    return this.cemented;
  }
  
  public void setCemented(String cemented)
  {
    this.cemented = (cemented == null ? null : cemented.trim());
  }
  
  public String getScrewed()
  {
    return this.screwed;
  }
  
  public void setScrewed(String screwed)
  {
    this.screwed = (screwed == null ? null : screwed.trim());
  }
}
