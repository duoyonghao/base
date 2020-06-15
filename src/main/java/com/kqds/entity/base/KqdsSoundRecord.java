package com.kqds.entity.base;

public class KqdsSoundRecord
{
  private String seqId;
  private String linenum;
  private String phonenumber;
  private String filename;
  private String datedir;
  private String recordtime;
  private String createtime;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getLinenum()
  {
    return this.linenum;
  }
  
  public void setLinenum(String linenum)
  {
    this.linenum = (linenum == null ? null : linenum.trim());
  }
  
  public String getPhonenumber()
  {
    return this.phonenumber;
  }
  
  public void setPhonenumber(String phonenumber)
  {
    this.phonenumber = (phonenumber == null ? null : phonenumber.trim());
  }
  
  public String getFilename()
  {
    return this.filename;
  }
  
  public void setFilename(String filename)
  {
    this.filename = (filename == null ? null : filename.trim());
  }
  
  public String getDatedir()
  {
    return this.datedir;
  }
  
  public void setDatedir(String datedir)
  {
    this.datedir = (datedir == null ? null : datedir.trim());
  }
  
  public String getRecordtime()
  {
    return this.recordtime;
  }
  
  public void setRecordtime(String recordtime)
  {
    this.recordtime = (recordtime == null ? null : recordtime.trim());
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = (createtime == null ? null : createtime.trim());
  }
}
