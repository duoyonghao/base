package com.kqds.entity.wx;

public class WXNewsitem
{
  private String seqId;
  private String author;
  private String description;
  private String imagepath;
  private Integer sortno;
  private String title;
  private String newsid;
  private String url;
  private String createdate;
  private String createtime;
  private String createuser;
  private String organization;
  private String itemtype;
  private String content;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getAuthor()
  {
    return this.author;
  }
  
  public void setAuthor(String author)
  {
    this.author = (author == null ? null : author.trim());
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = (description == null ? null : description.trim());
  }
  
  public String getImagepath()
  {
    return this.imagepath;
  }
  
  public void setImagepath(String imagepath)
  {
    this.imagepath = (imagepath == null ? null : imagepath.trim());
  }
  
  public Integer getSortno()
  {
    return this.sortno;
  }
  
  public void setSortno(Integer sortno)
  {
    this.sortno = sortno;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = (title == null ? null : title.trim());
  }
  
  public String getNewsid()
  {
    return this.newsid;
  }
  
  public void setNewsid(String newsid)
  {
    this.newsid = (newsid == null ? null : newsid.trim());
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = (url == null ? null : url.trim());
  }
  
  public String getCreatedate()
  {
    return this.createdate;
  }
  
  public void setCreatedate(String createdate)
  {
    this.createdate = (createdate == null ? null : createdate.trim());
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
  
  public String getItemtype()
  {
    return this.itemtype;
  }
  
  public void setItemtype(String itemtype)
  {
    this.itemtype = (itemtype == null ? null : itemtype.trim());
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = (content == null ? null : content.trim());
  }
}
