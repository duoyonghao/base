package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsActivityRecord
{
  private String seqId;
  private String createuser;
  private String createtime;
  private String address;
  private String activitydate;
  private String activityname;
  private String lxr1;
  private String lxr2;
  private String activitynum;
  private String activitycontacts;
  private String content;
  private String discounts;
  private String organization;
  private BigDecimal outmoney;
  private String attachmentid;
  private String attachmentname;
  private String kehu;
  private String kehudesc;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
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
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = (address == null ? null : address.trim());
  }
  
  public String getActivitydate()
  {
    return this.activitydate;
  }
  
  public void setActivitydate(String activitydate)
  {
    this.activitydate = (activitydate == null ? null : activitydate.trim());
  }
  
  public String getActivityname()
  {
    return this.activityname;
  }
  
  public void setActivityname(String activityname)
  {
    this.activityname = (activityname == null ? null : activityname.trim());
  }
  
  public String getLxr1()
  {
    return this.lxr1;
  }
  
  public void setLxr1(String lxr1)
  {
    this.lxr1 = (lxr1 == null ? null : lxr1.trim());
  }
  
  public String getLxr2()
  {
    return this.lxr2;
  }
  
  public void setLxr2(String lxr2)
  {
    this.lxr2 = (lxr2 == null ? null : lxr2.trim());
  }
  
  public String getActivitynum()
  {
    return this.activitynum;
  }
  
  public void setActivitynum(String activitynum)
  {
    this.activitynum = (activitynum == null ? null : activitynum.trim());
  }
  
  public String getActivitycontacts()
  {
    return this.activitycontacts;
  }
  
  public void setActivitycontacts(String activitycontacts)
  {
    this.activitycontacts = (activitycontacts == null ? null : activitycontacts.trim());
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = (content == null ? null : content.trim());
  }
  
  public String getDiscounts()
  {
    return this.discounts;
  }
  
  public void setDiscounts(String discounts)
  {
    this.discounts = (discounts == null ? null : discounts.trim());
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public BigDecimal getOutmoney()
  {
    return this.outmoney;
  }
  
  public void setOutmoney(BigDecimal outmoney)
  {
    this.outmoney = outmoney;
  }
  
  public String getAttachmentid()
  {
    return this.attachmentid;
  }
  
  public void setAttachmentid(String attachmentid)
  {
    this.attachmentid = (attachmentid == null ? null : attachmentid.trim());
  }
  
  public String getAttachmentname()
  {
    return this.attachmentname;
  }
  
  public void setAttachmentname(String attachmentname)
  {
    this.attachmentname = (attachmentname == null ? null : attachmentname.trim());
  }
  
  public String getKehu()
  {
    return this.kehu;
  }
  
  public void setKehu(String kehu)
  {
    this.kehu = (kehu == null ? null : kehu.trim());
  }
  
  public String getKehudesc()
  {
    return this.kehudesc;
  }
  
  public void setKehudesc(String kehudesc)
  {
    this.kehudesc = (kehudesc == null ? null : kehudesc.trim());
  }
}
