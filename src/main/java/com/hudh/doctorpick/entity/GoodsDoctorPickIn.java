package com.hudh.doctorpick.entity;

import java.io.Serializable;

public class GoodsDoctorPickIn implements Serializable {
	
    private String seqId;

    private String supplier;

    private String incode;

    private String inremark;

    private String summary;

    private String pickPerson;

    private String userdocument;

    private String organization;

    private String createtime;

    private String creator;
    
    private String rktime;
    
    private String stocktime;

    public String getRktime() {
		return rktime;
	}

	public void setRktime(String rktime) {
		this.rktime = rktime;
	}

	public String getStocktime() {
		return stocktime;
	}

	public void setStocktime(String stocktime) {
		this.stocktime = stocktime;
	}

	private static final long serialVersionUID = 1L;

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getIncode() {
        return incode;
    }

    public void setIncode(String incode) {
        this.incode = incode == null ? null : incode.trim();
    }

    public String getInremark() {
        return inremark;
    }

    public void setInremark(String inremark) {
        this.inremark = inremark == null ? null : inremark.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getPickPerson() {
        return pickPerson;
    }

    public void setPickPerson(String pickPerson) {
        this.pickPerson = pickPerson == null ? null : pickPerson.trim();
    }

    public String getUserdocument() {
        return userdocument;
    }

    public void setUserdocument(String userdocument) {
        this.userdocument = userdocument == null ? null : userdocument.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
}