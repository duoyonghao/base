package com.kqds.entity.wx;

public class WXNews
{
  private String seqId;
  private String newsname;
  private String newstype;
  private Integer sortno;
  private String createtime;
  private String createuser;
  private String organization;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getNewsname()
  {
    return this.newsname;
  }
  
  public void setNewsname(String newsname)
  {
    this.newsname = (newsname == null ? null : newsname.trim());
  }
  
  public String getNewstype()
  {
    return this.newstype;
  }
  
  public void setNewstype(String newstype)
  {
    this.newstype = (newstype == null ? null : newstype.trim());
  }
  
  public Integer getSortno()
  {
    return this.sortno;
  }
  
  public void setSortno(Integer sortno)
  {
    this.sortno = sortno;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = (createtime == null ? null : createtime.trim());
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = (createuser == null ? null : createuser.trim());
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
