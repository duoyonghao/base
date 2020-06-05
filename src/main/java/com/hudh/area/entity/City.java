/**  
  *
  * @Title:  City.java   
  * @Package com.hudh.area.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月14日 下午7:07:28   
  * @version V1.0  
  */
package com.hudh.area.entity;

/**
 * 
 * @ClassName: City
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 海德堡联合口腔
 * @date: 2019年8月14日 下午7:07:28
 * 
 */
public class City {

	private Integer cityId;
	private String cityCode;
	private String cityName;
	private String shortName;
	private String proviceCode;
	private String lng;
	private String lat;
	private Integer sort;
	private String gmyCreate;
	private String gmtModified;
	private String memo;
	private Integer dataState;
	private String tenantCode;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getProviceCode() {
		return proviceCode;
	}

	public void setProviceCode(String proviceCode) {
		this.proviceCode = proviceCode;
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
