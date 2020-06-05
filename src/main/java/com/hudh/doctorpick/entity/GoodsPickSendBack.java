package com.hudh.doctorpick.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsPickSendBack implements Serializable {
	
    private String seqId;

    private String goodsname;

    private String goodscode;

    private String deptpartname;

    private String housename;

    private String goodsunit;

    private String nums;

    private String goodsnorms;

    private String createuser;

    private String createtime;
    
    private String organization;
    
    private String goodsuuid;//商品id
    
    private BigDecimal nuitPrice; //单价
    
    private BigDecimal amount; //金额
    
    private String remark;
    
    private String supplier;
    
    private String detailId;
    
    private String batchnum;

    public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getNuitPrice() {
		return nuitPrice;
	}

	public void setNuitPrice(BigDecimal nuitPrice) {
		this.nuitPrice = nuitPrice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getGoodsuuid() {
		return goodsuuid;
	}

	public void setGoodsuuid(String goodsuuid) {
		this.goodsuuid = goodsuuid;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
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
        this.goodsname= goodsname== null ? null : goodsname.trim();
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode == null ? null : goodscode.trim();
    }

    public String getDeptpartname() {
        return deptpartname;
    }

    public void setDeptpartname(String deptpartname) {
        this.deptpartname = deptpartname == null ? null : deptpartname.trim();
    }

    public String getHousename() {
        return housename;
    }

    public void setHousename(String housename) {
        this.housename = housename == null ? null : housename.trim();
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

    public String getGoodsnorms() {
        return goodsnorms;
    }

    public void setGoodsnorms(String goodsnorms) {
        this.goodsnorms = goodsnorms == null ? null : goodsnorms.trim();
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

	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}
}