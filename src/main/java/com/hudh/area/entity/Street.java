/**  
  *
  * @Title:  Street.java   
  * @Package com.hudh.area.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月15日 上午11:11:41   
  * @version V1.0  
  */
package com.hudh.area.entity;

/**
 * 
 * @ClassName: Street
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 海德堡联合口腔
 * @date: 2019年8月15日 上午11:11:41
 * 
 */
public class Street {

	private Integer streetId;
	private String streetCode;
	private String areaCode;
	private String streetName;
	private String shortName;
	private String lng;
	private String lat;
	private Integer sort;
	private String gmyCreate;
	private String gmtModified;
	private String memo;
	private Integer dataState;
	private String tenantCode;

	public Integer getStreetId() {
		return streetId;
	}

	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
	}

	public String getStreetCode() {
		return streetCode;
	}

	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getGmyCreate() {
		return gmyCreate;
	}

	public void setGmyCreate(String gmyCreate) {
		this.gmyCreate = gmyCreate;
	}

	public String getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getDataState() {
		return dataState;
	}

	public void setDataState(Integer dataState) {
		this.dataState = dataState;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

}
