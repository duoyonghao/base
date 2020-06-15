package com.hudh.doctorpick.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsDoctorPickInDetail implements Serializable {
	
    private String seqId;

    private String goodsname;

    private String goodscode;

    private String housename;//所属仓库

    private String deptpartname;//科室
    
	public String getDeptpartname() {
		return deptpartname;
	}

	public void setDeptpartname(String deptpartname) {
		this.deptpartname = deptpartname;
	}

	private String incode;
    private String goodsuuid;//商品的id

	public String getGoodsuuid() {
		return goodsuuid;
	}

	public void setGoodsuuid(String goodsuuid) {
		this.goodsuuid = goodsuuid;
	}

	public String getIncode() {
		return incode;
	}

	public void setIncode(String incode) {
		this.incode = incode;
	}

	public String getHousename() {
		return housename;
	}

	public void setHousename(String housename) {
		this.housename = housename;
	}

	private String userdocument;
	
	public String getUserdocument() {
		return userdocument;
	}

	public void setUserdocument(String userdocument) {
		this.userdocument = userdocument;
	}

	private String goodsunit;

    private String nums;

    private String validity;

    private String goodsnorms;

    private String organization;

    private String createuser;

    private String createtime;
    
    private String company; //单位
	private BigDecimal nuitPrice; //单价  nuitprice
	private String quantity; //数量
	private BigDecimal amount; //金额
	private String remark; //备注
	private String createdate; //生产效期
	private String batchnum; //批号
	private String regisnum; //注册证号
	private String productionPlace; //产地
	private String phids; 
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public BigDecimal getNuitPrice() {
		return nuitPrice;
	}

	public void setNuitPrice(BigDecimal nuitPrice) {
		this.nuitPrice = nuitPrice;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}

	public String getRegisnum() {
		return regisnum;
	}

	public void setRegisnum(String regisnum) {
		this.regisnum = regisnum;
	}

	public String getProductionPlace() {
		return productionPlace;
	}

	public void setProductionPlace(String productionPlace) {
		this.productionPlace = productionPlace;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


    private static final long serialVersionUID = 1L;

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode == null ? null : goodscode.trim();
    }

    public String getGoodsunit() {
        return goodsunit;
    }

    public void setGoodsunit(String goodsunit) {
        this.goodsunit = goodsunit == null ? null : goodsunit.trim();
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums == null ? null : nums.trim();
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity == null ? null : validity.trim();
    }

    public String getGoodsnorms() {
        return goodsnorms;
    }

    public void setGoodsnorms(String goodsnorms) {
        this.goodsnorms = goodsnorms == null ? null : goodsnorms.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

	public String getPhids() {
		return phids;
	}

	public void setPhids(String phids) {
		this.phids = phids;
	}

}