package com.hudh.lclj.entity;

/**
 * 临床路径跟踪表
 * @author XKY
 *
 */
public class LcljOrderTrack {
	private String id;
	private String tooth;
	private String createtime;
	private String orderNumber;
	private String ssTime;
	private String flowLink;
	private String ssStatus;
	private String type;
	private String bone;
	private String ssStu;
	private String shgcStu;
	private String dyStu;
	
	private String blcode;
	private String leftUp;
	private String leftDown;
	private String rightUp;
	private String rightDown;
	private String nodes;
	private String lastTime;
	private String creatorid;
	private String creatorname;
	
	
	private String counsellor;
	private String plantPhysician;
	private String repairPphysician;
	private String clinicNurse;
	private String customerService;
	private String plantSystem;
	private String crownMaterial;
	private String imageologicalExamination;
	private String consultation;
	private String advisory;
	private String repairLeftUp;
	private String repairLeftDown;
	private String repairRightUp;
	private String repairRightDown;
	private String repairToothTotal;
	private String tooth_texture;//牙质材料
	private String userdocument_id;//患者档案表ID
	public String getUserdocument_id() {
		return userdocument_id;
	}
	public void setUserdocument_id(String userdocument_id) {
		this.userdocument_id = userdocument_id;
	}
	public String getTooth_texture() {
		return tooth_texture;
	}
	public void setTooth_texture(String tooth_texture) {
		this.tooth_texture = tooth_texture;
	}
	private String isobsolete;//判断记录是否作废
	private String modeoperation;//术前/术后取模   0：术前  1：术后
	private String remark;//备注
	private String flowcode;//流程编号
	private String dentalJaw;//上下颌  1：上颌  2：下颌
	private String articleType;//项目分类  1：种植  2：正畸
	private String abutment_station;//愈合基台
	public String getAbutment_station() {
		return abutment_station;
	}
	public void setAbutment_station(String abutment_station) {
		this.abutment_station = abutment_station;
	}
	public String getFlowcode() {
		return flowcode;
	}
	public void setFlowcode(String flowcode) {
		this.flowcode = flowcode;
	}
	public String getDentalJaw() {
		return dentalJaw;
	}
	public void setDentalJaw(String dentalJaw) {
		this.dentalJaw = dentalJaw;
	}
	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	public String getIsobsolete() {
		return isobsolete;
	}
	public void setIsobsolete(String isobsolete) {
		this.isobsolete = isobsolete;
	}
	public String getRepairToothTotal() {
		return repairToothTotal;
	}
	public void setRepairToothTotal(String repairToothTotal) {
		this.repairToothTotal = repairToothTotal;
	}
	public String getCounsellor() {
		return counsellor;
	}
	public void setCounsellor(String counsellor) {
		this.counsellor = counsellor;
	}
	public String getPlantPhysician() {
		return plantPhysician;
	}
	public void setPlantPhysician(String plantPhysician) {
		this.plantPhysician = plantPhysician;
	}
	public String getRepairPphysician() {
		return repairPphysician;
	}
	public void setRepairPphysician(String repairPphysician) {
		this.repairPphysician = repairPphysician;
	}
	public String getClinicNurse() {
		return clinicNurse;
	}
	public void setClinicNurse(String clinicNurse) {
		this.clinicNurse = clinicNurse;
	}
	public String getCustomerService() {
		return customerService;
	}
	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	public String getPlantSystem() {
		return plantSystem;
	}
	public void setPlantSystem(String plantSystem) {
		this.plantSystem = plantSystem;
	}
	public String getCrownMaterial() {
		return crownMaterial;
	}
	public void setCrownMaterial(String crownMaterial) {
		this.crownMaterial = crownMaterial;
	}
	public String getImageologicalExamination() {
		return imageologicalExamination;
	}
	public void setImageologicalExamination(String imageologicalExamination) {
		this.imageologicalExamination = imageologicalExamination;
	}
	public String getConsultation() {
		return consultation;
	}
	public void setConsultation(String consultation) {
		this.consultation = consultation;
	}
	public String getAdvisory() {
		return advisory;
	}
	public void setAdvisory(String advisory) {
		this.advisory = advisory;
	}
	public String getRepairLeftUp() {
		return repairLeftUp;
	}
	public void setRepairLeftUp(String repairLeftUp) {
		this.repairLeftUp = repairLeftUp;
	}
	public String getRepairLeftDown() {
		return repairLeftDown;
	}
	public void setRepairLeftDown(String repairLeftDown) {
		this.repairLeftDown = repairLeftDown;
	}
	public String getRepairRightUp() {
		return repairRightUp;
	}
	public void setRepairRightUp(String repairRightUp) {
		this.repairRightUp = repairRightUp;
	}
	public String getRepairRightDown() {
		return repairRightDown;
	}
	public void setRepairRightDown(String repairRightDown) {
		this.repairRightDown = repairRightDown;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTooth() {
		return tooth;
	}
	public void setTooth(String tooth) {
		this.tooth = tooth;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getSsTime() {
		return ssTime;
	}
	public void setSsTime(String ssTime) {
		this.ssTime = ssTime;
	}
	public String getFlowLink() {
		return flowLink;
	}
	public void setFlowLink(String flowLink) {
		this.flowLink = flowLink;
	}
	public String getSsStatus() {
		return ssStatus;
	}
	public void setSsStatus(String ssStatus) {
		this.ssStatus = ssStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBone() {
		return bone;
	}
	public void setBone(String bone) {
		this.bone = bone;
	}
	public String getSsStu() {
		return ssStu;
	}
	public void setSsStu(String ssStu) {
		this.ssStu = ssStu;
	}
	public String getShgcStu() {
		return shgcStu;
	}
	public void setShgcStu(String shgcStu) {
		this.shgcStu = shgcStu;
	}
	public String getDyStu() {
		return dyStu;
	}
	public void setDyStu(String dyStu) {
		this.dyStu = dyStu;
	}
	public String getBlcode() {
		return blcode;
	}
	public void setBlcode(String blcode) {
		this.blcode = blcode;
	}
	public String getLeftUp() {
		return leftUp;
	}
	public void setLeftUp(String leftUp) {
		this.leftUp = leftUp;
	}
	public String getLeftDown() {
		return leftDown;
	}
	public void setLeftDown(String leftDown) {
		this.leftDown = leftDown;
	}
	public String getRightUp() {
		return rightUp;
	}
	public void setRightUp(String rightUp) {
		this.rightUp = rightUp;
	}
	public String getRightDown() {
		return rightDown;
	}
	public void setRightDown(String rightDown) {
		this.rightDown = rightDown;
	}
	public String getNodes() {
		return nodes;
	}
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}
	public String getCreatorname() {
		return creatorname;
	}
	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}
	public String getModeoperation() {
		return modeoperation;
	}
	public void setModeoperation(String modeoperation) {
		this.modeoperation = modeoperation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
