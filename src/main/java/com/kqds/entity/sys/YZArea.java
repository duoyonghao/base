package com.kqds.entity.sys;

public class YZArea
{
  private String seqId;
  private String areaName;
  private String parentId;
  private Integer sortno;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getAreaName()
  {
    return this.areaName;
  }
  
  public void setAreaName(String areaName)
  {
    this.areaName = (areaName == null ? null : areaName.trim());
  }
  
  public String getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(String parentId)
  {
    this.parentId = (parentId == null ? null : parentId.trim());
  }
  
  public Integer getSortno()
  {
    return this.sortno;
  }
  
  public void setSortno(Integer sortno)
  {
    this.sortno = sortno;
  }
}
