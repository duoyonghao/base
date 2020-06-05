package com.kqds.entity.base;

public class KqdsOutprocessingSheet {
	/**
	 * 
	 */
	private String seqId;
	/**
	 * 患者编号
	 */
	private String usercode;
	/**
	 * 费用单号
	 */
	private String cosno;
	/**
	 *  系统单号
	 */
	private String outprocessingsheetno;
	/**
	 * 加工订单号
	 */
	private String orderNumber; 
	/**
	 * 加工单状态
	 */
	private String status;
	/**
	 * 门诊/单位
	 */
	private String organization;
	/**
	 * 开单医生
	 */
	private String doctorno;
	/**
	 * 提交外加工时间
	 */
	private String submittime;
	/**
	 * 要求取件时间
	 */
	private String hopebacktime;
	/**
	 * 加工单位
	 */
	private String processingfactory;
	/**
	 * 戴走人
	 */
	private String pickupperson;
	/**
	 * 戴走时间
	 */
	private String pickuptime;
	/**
	 * 预计回件时间
	 */
	private String estimatebacktime;
	/**
	 * 创建人
	 */
	private String createuser;
	/**
	 * 创建时间
	 */
	private String createtime;
	/**
	 * 加工单类型
	 */
	private String type;
	/**
	 * 设计要求
	 */
	private String yaoqiu;
	/**
	 * 发件人
	 */
	private String fajianperson;
	/**
	 * 发件时间
	 */
	private String fajiantime;
	/**
	 * 回件人
	 */
	private String huijianperson;
	/**
	 * 回件时间
	 */
	private String huijiantime;
	/**
	 * 返工人
	 */
	private String fangongperson;
	/**
	 * 返工时间
	 */
	private String fangongtime;
	/**
	 * 作废人
	 */
	private String zuofeiperson;
	/**
	 * 作废时间
	 */
	private String zuofeitime;
	
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public String getCosno() {
		return cosno;
	}

	public void setCosno(String cosno) {
		this.cosno = cosno == null ? null : cosno.trim();
	}

	public String getOutprocessingsheetno() {
		return outprocessingsheetno;
	}

	public void setOutprocessingsheetno(String outprocessingsheetno) {
		this.outprocessingsheetno = outprocessingsheetno == null ? null : outprocessingsheetno.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getDoctorno() {
		return doctorno;
	}

	public void setDoctorno(String doctorno) {
		this.doctorno = doctorno == null ? null : doctorno.trim();
	}

	public String getSubmittime() {
		return submittime;
	}

	public void setSubmittime(String submittime) {
		this.submittime = submittime == null ? null : submittime.trim();
	}

	public String getHopebacktime() {
		return hopebacktime;
	}

	public void setHopebacktime(String hopebacktime) {
		this.hopebacktime = hopebacktime == null ? null : hopebacktime.trim();
	}

	public String getProcessingfactory() {
		return processingfactory;
	}

	public void setProcessingfactory(String processingfactory) {
		this.processingfactory = processingfactory == null ? null : processingfactory.trim();
	}

	public String getPickupperson() {
		return pickupperson;
	}

	public void setPickupperson(String pickupperson) {
		this.pickupperson = pickupperson == null ? null : pickupperson.trim();
	}

	public String getPickuptime() {
		return pickuptime;
	}

	public void setPickuptime(String pickuptime) {
		this.pickuptime = pickuptime == null ? null : pickuptime.trim();
	}

	public String getEstimatebacktime() {
		return estimatebacktime;
	}

	public void setEstimatebacktime(String estimatebacktime) {
		this.estimatebacktime = estimatebacktime == null ? null : estimatebacktime.trim();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getYaoqiu() {
		return yaoqiu;
	}

	public void setYaoqiu(String yaoqiu) {
		this.yaoqiu = yaoqiu == null ? null : yaoqiu.trim();
	}

	public String getFajianperson() {
		return fajianperson;
	}

	public void setFajianperson(String fajianperson) {
		this.fajianperson = fajianperson == null ? null : fajianperson.trim();
	}

	public String getFajiantime() {
		return fajiantime;
	}

	public void setFajiantime(String fajiantime) {
		this.fajiantime = fajiantime == null ? null : fajiantime.trim();
	}

	public String getHuijianperson() {
		return huijianperson;
	}

	public void setHuijianperson(String huijianperson) {
		this.huijianperson = huijianperson == null ? null : huijianperson.trim();
	}

	public String getHuijiantime() {
		return huijiantime;
	}

	public void setHuijiantime(String huijiantime) {
		this.huijiantime = huijiantime == null ? null : huijiantime.trim();
	}

	public String getFangongperson() {
		return fangongperson;
	}

	public void setFangongperson(String fangongperson) {
		this.fangongperson = fangongperson == null ? null : fangongperson.trim();
	}

	public String getFangongtime() {
		return fangongtime;
	}

	public void setFangongtime(String fangongtime) {
		this.fangongtime = fangongtime == null ? null : fangongtime.trim();
	}

	public String getZuofeiperson() {
		return zuofeiperson;
	}

	public void setZuofeiperson(String zuofeiperson) {
		this.zuofeiperson = zuofeiperson == null ? null : zuofeiperson.trim();
	}

	public String getZuofeitime() {
		return zuofeitime;
	}

	public void setZuofeitime(String zuofeitime) {
		this.zuofeitime = zuofeitime == null ? null : zuofeitime.trim();
	}
}