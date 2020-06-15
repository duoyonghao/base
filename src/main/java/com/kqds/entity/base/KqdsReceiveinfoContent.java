package com.kqds.entity.base;

public class KqdsReceiveinfoContent
{
  private String seqId;
  private String createuser;
  private String createtime;
  private String receiveid;
  private String zs;
  private String check;
  private String jy;
  private String member;
  private String fy;
  private String kaifa;
  private String organization;
  
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
  
  public String getReceiveid()
  {
    return this.receiveid;
  }
  
  public void setReceiveid(String receiveid)
  {
    this.receiveid = (receiveid == null ? null : receiveid.trim());
  }
  
  public String getZs()
  {
    return this.zs;
  }
  
  public void setZs(String zs)
  {
    this.zs = (zs == null ? null : zs.trim());
  }
  
  public String getCheck()
  {
    return this.check;
  }
  
  public void setCheck(String check)
  {
    this.check = (check == null ? null : check.trim());
  }
  
  public String getJy()
  {
    return this.jy;
  }
  
  public void setJy(String jy)
  {
    this.jy = (jy == null ? null : jy.trim());
  }
  
  public String getMember()
  {
    return this.member;
  }
  
  public void setMember(String member)
  {
    this.member = (member == null ? null : member.trim());
  }
  
  public String getFy()
  {
    return this.fy;
  }
  
  public void setFy(String fy)
  {
    this.fy = (fy == null ? null : fy.trim());
  }
  
  public String getKaifa()
  {
    return this.kaifa;
  }
  
  public void setKaifa(String kaifa)
  {
    this.kaifa = (kaifa == null ? null : kaifa.trim());
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
}
