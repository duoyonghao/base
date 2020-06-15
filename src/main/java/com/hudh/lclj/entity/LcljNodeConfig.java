package com.hudh.lclj.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class LcljNodeConfig
  implements Comparable
{
  private String id;
  private Integer num;
  private String nodeId;
  private String nodeName;
  private String authorType;
  private String author;
  private String viewUrl;
  private String organization;
  private String creatrtime;
  private String nodeLimit;
  private String limitType;
  private String flowCode;
  private String stus;
  private String limitBench;
  private String flowType;
  private String dentalJaw;
  private String articleType;
  
  public String getFlowType()
  {
    return this.flowType;
  }
  
  public void setFlowType(String flowType)
  {
    this.flowType = flowType;
  }
  
  public String getDentalJaw()
  {
    return this.dentalJaw;
  }
  
  public void setDentalJaw(String dentalJaw)
  {
    this.dentalJaw = dentalJaw;
  }
  
  public String getArticleType()
  {
    return this.articleType;
  }
  
  public void setArticleType(String articleType)
  {
    this.articleType = articleType;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public Integer getNum()
  {
    return this.num;
  }
  
  public void setNum(Integer num)
  {
    this.num = num;
  }
  
  public String getNodeId()
  {
    return this.nodeId;
  }
  
  public void setNodeId(String nodeId)
  {
    this.nodeId = nodeId;
  }
  
  public String getNodeName()
  {
    return this.nodeName;
  }
  
  public void setNodeName(String nodeName)
  {
    this.nodeName = nodeName;
  }
  
  public String getAuthorType()
  {
    return this.authorType;
  }
  
  public void setAuthorType(String authorType)
  {
    this.authorType = authorType;
  }
  
  public String getAuthor()
  {
    return this.author;
  }
  
  public void setAuthor(String author)
  {
    this.author = author;
  }
  
  public String getViewUrl()
  {
    return this.viewUrl;
  }
  
  public void setViewUrl(String viewUrl)
  {
    this.viewUrl = viewUrl;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
  public String getCreatrtime()
  {
    return this.creatrtime;
  }
  
  public void setCreatrtime(String creatrtime)
  {
    this.creatrtime = creatrtime;
  }
  
  public String getNodeLimit()
  {
    return this.nodeLimit;
  }
  
  public void setNodeLimit(String nodeLimit)
  {
    this.nodeLimit = nodeLimit;
  }
  
  public String getLimitType()
  {
    return this.limitType;
  }
  
  public void setLimitType(String limitType)
  {
    this.limitType = limitType;
  }
  
  public String getFlowCode()
  {
    return this.flowCode;
  }
  
  public void setFlowCode(String flowCode)
  {
    this.flowCode = flowCode;
  }
  
  public String getStus()
  {
    return this.stus;
  }
  
  public void setStus(String stus)
  {
    this.stus = stus;
  }
  
  public String getLimitBench()
  {
    return this.limitBench;
  }
  
  public void setLimitBench(String limitBench)
  {
    this.limitBench = limitBench;
  }
  
  public int compareTo(Object o)
  {
    if ((o instanceof LcljNodeConfig))
    {
      LcljNodeConfig emp = (LcljNodeConfig)o;
      return this.num.intValue() - emp.getNum().intValue();
    }
    throw new ClassCastException("不能转换为Emp类型的对象...");
  }
}
