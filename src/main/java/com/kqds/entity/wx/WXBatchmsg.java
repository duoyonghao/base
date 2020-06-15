package com.kqds.entity.wx;

public class WXBatchmsg
{
  private String seqId;
  private String createtime;
  private String createuser;
  private String msgtype;
  private Integer okcount;
  private Integer errcount;
  private String msgcontent;
  private String userids;
  private String okids;
  private String errids;
  private String usernames;
  private String oknames;
  private String errnames;
  private String openids;
  private String okopenids;
  private String erropenids;
  private String usercodes;
  private String okusercodes;
  private String errusercodes;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
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
  
  public String getMsgtype()
  {
    return this.msgtype;
  }
  
  public void setMsgtype(String msgtype)
  {
    this.msgtype = (msgtype == null ? null : msgtype.trim());
  }
  
  public Integer getOkcount()
  {
    return this.okcount;
  }
  
  public void setOkcount(Integer okcount)
  {
    this.okcount = okcount;
  }
  
  public Integer getErrcount()
  {
    return this.errcount;
  }
  
  public void setErrcount(Integer errcount)
  {
    this.errcount = errcount;
  }
  
  public String getMsgcontent()
  {
    return this.msgcontent;
  }
  
  public void setMsgcontent(String msgcontent)
  {
    this.msgcontent = (msgcontent == null ? null : msgcontent.trim());
  }
  
  public String getUserids()
  {
    return this.userids;
  }
  
  public void setUserids(String userids)
  {
    this.userids = (userids == null ? null : userids.trim());
  }
  
  public String getOkids()
  {
    return this.okids;
  }
  
  public void setOkids(String okids)
  {
    this.okids = (okids == null ? null : okids.trim());
  }
  
  public String getErrids()
  {
    return this.errids;
  }
  
  public void setErrids(String errids)
  {
    this.errids = (errids == null ? null : errids.trim());
  }
  
  public String getUsernames()
  {
    return this.usernames;
  }
  
  public void setUsernames(String usernames)
  {
    this.usernames = (usernames == null ? null : usernames.trim());
  }
  
  public String getOknames()
  {
    return this.oknames;
  }
  
  public void setOknames(String oknames)
  {
    this.oknames = (oknames == null ? null : oknames.trim());
  }
  
  public String getErrnames()
  {
    return this.errnames;
  }
  
  public void setErrnames(String errnames)
  {
    this.errnames = (errnames == null ? null : errnames.trim());
  }
  
  public String getOpenids()
  {
    return this.openids;
  }
  
  public void setOpenids(String openids)
  {
    this.openids = (openids == null ? null : openids.trim());
  }
  
  public String getOkopenids()
  {
    return this.okopenids;
  }
  
  public void setOkopenids(String okopenids)
  {
    this.okopenids = (okopenids == null ? null : okopenids.trim());
  }
  
  public String getErropenids()
  {
    return this.erropenids;
  }
  
  public void setErropenids(String erropenids)
  {
    this.erropenids = (erropenids == null ? null : erropenids.trim());
  }
  
  public String getUsercodes()
  {
    return this.usercodes;
  }
  
  public void setUsercodes(String usercodes)
  {
    this.usercodes = (usercodes == null ? null : usercodes.trim());
  }
  
  public String getOkusercodes()
  {
    return this.okusercodes;
  }
  
  public void setOkusercodes(String okusercodes)
  {
    this.okusercodes = (okusercodes == null ? null : okusercodes.trim());
  }
  
  public String getErrusercodes()
  {
    return this.errusercodes;
  }
  
  public void setErrusercodes(String errusercodes)
  {
    this.errusercodes = (errusercodes == null ? null : errusercodes.trim());
  }
}
