package com.kqds.entity.base;

public class KqdsCkGoodsIn
{
  private String seqId;
  private String supplier;
  private String inhouse;
  private String incode;
  private String inremark;
  private String zhaiyao;
  private String auditor;
  private String shtime;
  private String rkr;
  private String rktime;
  private Integer instatus;
  private Integer intype;
  private String organization;
  private String createuser;
  private String createtime;
  private String stocktime;
  private int check_status;
  private int status;
  private String modifiyId;
  private String type;
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public String getModifiyId()
  {
    return this.modifiyId;
  }
  
  public void setModifiyId(String modifiyId)
  {
    this.modifiyId = modifiyId;
  }
  
  public int getCheck_status()
  {
    return this.check_status;
  }
  
  public void setCheck_status(int check_status)
  {
    this.check_status = check_status;
  }
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getSupplier()
  {
    return this.supplier;
  }
  
  public void setSupplier(String supplier)
  {
    this.supplier = (supplier == null ? null : supplier.trim());
  }
  
  public String getInhouse()
  {
    return this.inhouse;
  }
  
  public void setInhouse(String inhouse)
  {
    this.inhouse = (inhouse == null ? null : inhouse.trim());
  }
  
  public String getIncode()
  {
    return this.incode;
  }
  
  public void setIncode(String incode)
  {
    this.incode = (incode == null ? null : incode.trim());
  }
  
  public String getInremark()
  {
    return this.inremark;
  }
  
  public void setInremark(String inremark)
  {
    this.inremark = (inremark == null ? null : inremark.trim());
  }
  
  public String getZhaiyao()
  {
    return this.zhaiyao;
  }
  
  public void setZhaiyao(String zhaiyao)
  {
    this.zhaiyao = (zhaiyao == null ? null : zhaiyao.trim());
  }
  
  public String getAuditor()
  {
    return this.auditor;
  }
  
  public void setAuditor(String auditor)
  {
    this.auditor = (auditor == null ? null : auditor.trim());
  }
  
  public String getShtime()
  {
    return this.shtime;
  }
  
  public void setShtime(String shtime)
  {
    this.shtime = (shtime == null ? null : shtime.trim());
  }
  
  public String getRkr()
  {
    return this.rkr;
  }
  
  public void setRkr(String rkr)
  {
    this.rkr = (rkr == null ? null : rkr.trim());
  }
  
  public String getRktime()
  {
    return this.rktime;
  }
  
  public void setRktime(String rktime)
  {
    this.rktime = (rktime == null ? null : rktime.trim());
  }
  
  public Integer getInstatus()
  {
    return this.instatus;
  }
  
  public void setInstatus(Integer instatus)
  {
    this.instatus = instatus;
  }
  
  public Integer getIntype()
  {
    return this.intype;
  }
  
  public void setIntype(Integer intype)
  {
    this.intype = intype;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
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
  
  public String getStocktime()
  {
    return this.stocktime;
  }
  
  public void setStocktime(String stocktime)
  {
    this.stocktime = stocktime;
  }
}
