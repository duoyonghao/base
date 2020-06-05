package com.hudh.ykzz.entity;

import java.io.Serializable;

public class YkzzManufacturers implements Serializable {
	
    private String id;

    private String orderno;

    private String manufacturersName;

    private String createtime;

    private String creator;

    private String manufacturersCode;
    
    private String organization;//添加字段

    private static final long serialVersionUID = 1L;
    
    public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getManufacturersName() {
        return manufacturersName;
    }

    public void setManufacturersName(String manufacturersName) {
        this.manufacturersName = manufacturersName == null ? null : manufacturersName.trim();
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

    public String getManufacturersCode() {
        return manufacturersCode;
    }

    public void setManufacturersCode(String manufacturersCode) {
        this.manufacturersCode = manufacturersCode == null ? null : manufacturersCode.trim();
    }
}