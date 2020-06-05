/**  
  *
  * @Title:  KqdsMachining.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月13日 上午9:02:27   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

import java.io.Serializable;
import java.util.List;

/**  
  * 
  * @ClassName:  KqdsMachining   
  * @Description:TODO(加工单)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月13日 上午9:02:27   
  *      
  */
public class KqdsMachining implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;

	private String seqId;
	/**
	 * 系统单号
	 */
	private String systemNumber;
	/**
	 * 加工单号
	 */
	private String orderNumber;
	/**
	 * 加工单位
	 */
	private String processUnit;
	/**
	 * 种植医师
	 */
	private String doctor;
	/**
	 * 患者档案号
	 */
	private String userCode;
	/**
	 * 患者姓名
	 */
	private String userName;
	/**
	 * 患者性别
	 */
	private String sex;
	/**
	 * 患者年龄
	 */
	private String age;
	/**
	 * 患者手机号
	 */
	private String phoneNumber;
	/**
	 * 取模时间
	 */
	private String deliverytime;
	/**
	 * 收取模型人
	 */
	private String chargeModelPerson;
	/**
	 * 收取模型时间
	 */
	private String chargeModeltime;
	/**
	 * 完工发出时间
	 */
	private String sendOuttime;
	/**
	 * 上颌模型
	 */
	private String maxillaryModel;
	/**
	 * 咬合记录
	 */
	private String occlusioRecord;
	/**
	 * 替代体
	 */
	private String replaceBody;
	/**
	 * 愈合基台
	 */
	private String gingivalFormer;
	/**
	 * 下颌模型
	 */
	private String mandibleModel;
	/**
	 * 旧义齿模型(参考用)
	 */
	private String oldDentureModel;
	/**
	 * 基台
	 */
	private String drillBase;
	/**
	 * 定位器
	 */
	private String locator;
	/**
	 * 上颌托盘
	 */
	private String maxillarySalver;
	/**
	 * 参考蜡型
	 */
	private String waxPattern;
	/**
	 * 螺丝
	 */
	private String screw;
	/**
	 * 颌架
	 */
	private String jawFrame;
	/**
	 * 下颌托盘
	 */
	private String mandibleSalver;
	/**
	 * 转移杆
	 */
	private String shiftLever;
	/**
	 * 人工牙龈
	 */
	private String humanGingival;
	/**
	 * 阿曼颌架底座
	 */
	private String omanJawBase;
	/**
	 * 其它
	 */
	private String other;
	/**
	 * 修复牙位集合
	 */
	private String toothMapArr;
	/**
	 * 内冠  1:紧,2:正常,3:松   
	 */
	private String innerCrown;
	/**
	 * 咬颌 1:紧,2:0.2mm,3:0.3mm
	 */
	private String biteJaw;
	/**
	 * 邻接关系 1：点面接触,2：点接触,3：面接触
	 */
	private String syntopy;
	/**
	 * 桥体设计 1：嵌入式,2：鞍式,3：船底式,4：悬空式
	 */
	private String bridgeDesign;
	/**
	 * 颈缘设计 1：瓷肩台,2：全包瓷,3：舌面金属边,4：全金属颈缝
	 */
	private String neckFlangeDesign;
	/**
	 * 种植固定方式 
	 * 1：粘接固定冠不开孔（配定位器）, 2：粘接固定冠开排溢孔（配定位器）
	 * 3：粘接固定冠开螺丝孔（配定位器）,4：螺丝固定粘接出货
	 */
	private String plantFixed;
	/**
	 * 流程状态
	 */
	private String flowStatus;
	/**
	 * 加工单状态
	 */
	private String status;
	/**
	 * 门诊
	 */
	private String organization;
	/**
	 * 创建人
	 */
	private String createuser;
	/**
	 * 创建时间
	 */
	private String createtime; 
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否内部加工
	 */
	private String isInterior;
	
	private String tryInTime;//新增字段、记录试戴的时间
	private String takeAwayTime;//新增字段、记录戴走的时间
	
	public String getTryInTime() {
		return tryInTime;
	}
	public void setTryInTime(String tryInTime) {
		this.tryInTime = tryInTime;
	}
	public String getTakeAwayTime() {
		return takeAwayTime;
	}
	public void setTakeAwayTime(String takeAwayTime) {
		this.takeAwayTime = takeAwayTime;
	}
	/**  
	  * @Title:  getSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSeqId() {
		return seqId;
	}
	/**  
	  * @Title:  setSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	/**  
	  * @Title:  getSystemNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSystemNumber() {
		return systemNumber;
	}
	/**  
	  * @Title:  setSystemNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSystemNumber(String systemNumber) {
		this.systemNumber = systemNumber;
	}
	/**  
	  * @Title:  getOrderNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**  
	  * @Title:  setOrderNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**  
	  * @Title:  getProcessUnit <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getProcessUnit() {
		return processUnit;
	}
	/**  
	  * @Title:  setProcessUnit <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setProcessUnit(String processUnit) {
		this.processUnit = processUnit;
	}
	/**  
	  * @Title:  getDoctor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDoctor() {
		return doctor;
	}
	/**  
	  * @Title:  setDoctor <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	/**  
	  * @Title:  getUserCode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUserCode() {
		return userCode;
	}
	/**  
	  * @Title:  setUserCode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**  
	  * @Title:  getUserName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUserName() {
		return userName;
	}
	/**  
	  * @Title:  setUserName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**  
	  * @Title:  getSex <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSex() {
		return sex;
	}
	/**  
	  * @Title:  setSex <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**  
	  * @Title:  getAge <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAge() {
		return age;
	}
	/**  
	  * @Title:  setAge <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAge(String age) {
		this.age = age;
	}
	/**  
	  * @Title:  getPhoneNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**  
	  * @Title:  setPhoneNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**  
	  * @Title:  getDeliverytime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDeliverytime() {
		return deliverytime;
	}
	/**  
	  * @Title:  setDeliverytime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}
	/**  
	  * @Title:  getChargeModelPerson <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getChargeModelPerson() {
		return chargeModelPerson;
	}
	/**  
	  * @Title:  setChargeModelPerson <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setChargeModelPerson(String chargeModelPerson) {
		this.chargeModelPerson = chargeModelPerson;
	}
	/**  
	  * @Title:  getChargeModeltime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getChargeModeltime() {
		return chargeModeltime;
	}
	/**  
	  * @Title:  setChargeModeltime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setChargeModeltime(String chargeModeltime) {
		this.chargeModeltime = chargeModeltime;
	}
	/**  
	  * @Title:  getSendOuttime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSendOuttime() {
		return sendOuttime;
	}
	/**  
	  * @Title:  setSendOuttime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSendOuttime(String sendOuttime) {
		this.sendOuttime = sendOuttime;
	}
	/**  
	  * @Title:  getMaxillaryModel <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getMaxillaryModel() {
		return maxillaryModel;
	}
	/**  
	  * @Title:  setMaxillaryModel <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setMaxillaryModel(String maxillaryModel) {
		this.maxillaryModel = maxillaryModel;
	}
	/**  
	  * @Title:  getOcclusioRecord <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOcclusioRecord() {
		return occlusioRecord;
	}
	/**  
	  * @Title:  setOcclusioRecord <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOcclusioRecord(String occlusioRecord) {
		this.occlusioRecord = occlusioRecord;
	}
	/**  
	  * @Title:  getReplaceBody <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReplaceBody() {
		return replaceBody;
	}
	/**  
	  * @Title:  setReplaceBody <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReplaceBody(String replaceBody) {
		this.replaceBody = replaceBody;
	}
	/**  
	  * @Title:  getGingivalFormer <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGingivalFormer() {
		return gingivalFormer;
	}
	/**  
	  * @Title:  setGingivalFormer <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGingivalFormer(String gingivalFormer) {
		this.gingivalFormer = gingivalFormer;
	}
	/**  
	  * @Title:  getMandibleModel <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getMandibleModel() {
		return mandibleModel;
	}
	/**  
	  * @Title:  setMandibleModel <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setMandibleModel(String mandibleModel) {
		this.mandibleModel = mandibleModel;
	}
	/**  
	  * @Title:  getOldDentureModel <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOldDentureModel() {
		return oldDentureModel;
	}
	/**  
	  * @Title:  setOldDentureModel <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOldDentureModel(String oldDentureModel) {
		this.oldDentureModel = oldDentureModel;
	}
	/**  
	  * @Title:  getDrillBase <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDrillBase() {
		return drillBase;
	}
	/**  
	  * @Title:  setDrillBase <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDrillBase(String drillBase) {
		this.drillBase = drillBase;
	}
	/**  
	  * @Title:  getLocator <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLocator() {
		return locator;
	}
	/**  
	  * @Title:  setLocator <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLocator(String locator) {
		this.locator = locator;
	}
	/**  
	  * @Title:  getMaxillarySalver <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getMaxillarySalver() {
		return maxillarySalver;
	}
	/**  
	  * @Title:  setMaxillarySalver <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setMaxillarySalver(String maxillarySalver) {
		this.maxillarySalver = maxillarySalver;
	}
	/**  
	  * @Title:  getWaxPattern <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getWaxPattern() {
		return waxPattern;
	}
	/**  
	  * @Title:  setWaxPattern <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setWaxPattern(String waxPattern) {
		this.waxPattern = waxPattern;
	}
	/**  
	  * @Title:  getScrew <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getScrew() {
		return screw;
	}
	/**  
	  * @Title:  setScrew <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setScrew(String screw) {
		this.screw = screw;
	}
	/**  
	  * @Title:  getJawFrame <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getJawFrame() {
		return jawFrame;
	}
	/**  
	  * @Title:  setJawFrame <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setJawFrame(String jawFrame) {
		this.jawFrame = jawFrame;
	}
	/**  
	  * @Title:  getMandibleSalver <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getMandibleSalver() {
		return mandibleSalver;
	}
	/**  
	  * @Title:  setMandibleSalver <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setMandibleSalver(String mandibleSalver) {
		this.mandibleSalver = mandibleSalver;
	}
	/**  
	  * @Title:  getShiftLever <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getShiftLever() {
		return shiftLever;
	}
	/**  
	  * @Title:  setShiftLever <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setShiftLever(String shiftLever) {
		this.shiftLever = shiftLever;
	}
	/**  
	  * @Title:  getHumanGingival <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHumanGingival() {
		return humanGingival;
	}
	/**  
	  * @Title:  setHumanGingival <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHumanGingival(String humanGingival) {
		this.humanGingival = humanGingival;
	}
	/**  
	  * @Title:  getOmanJawBase <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOmanJawBase() {
		return omanJawBase;
	}
	/**  
	  * @Title:  setOmanJawBase <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOmanJawBase(String omanJawBase) {
		this.omanJawBase = omanJawBase;
	}
	/**  
	  * @Title:  getOther <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOther() {
		return other;
	}
	/**  
	  * @Title:  setOther <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOther(String other) {
		this.other = other;
	}
	/**  
	  * @Title:  getToothMapArr <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getToothMapArr() {
		return toothMapArr;
	}
	/**  
	  * @Title:  setToothMapArr <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setToothMapArr(String toothMapArr) {
		this.toothMapArr = toothMapArr;
	}
	/**  
	  * @Title:  getInnerCrown <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getInnerCrown() {
		return innerCrown;
	}
	/**  
	  * @Title:  setInnerCrown <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setInnerCrown(String innerCrown) {
		this.innerCrown = innerCrown;
	}
	/**  
	  * @Title:  getBiteJaw <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getBiteJaw() {
		return biteJaw;
	}
	/**  
	  * @Title:  setBiteJaw <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setBiteJaw(String biteJaw) {
		this.biteJaw = biteJaw;
	}
	/**  
	  * @Title:  getSyntopy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSyntopy() {
		return syntopy;
	}
	/**  
	  * @Title:  setSyntopy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSyntopy(String syntopy) {
		this.syntopy = syntopy;
	}
	/**  
	  * @Title:  getBridgeDesign <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getBridgeDesign() {
		return bridgeDesign;
	}
	/**  
	  * @Title:  setBridgeDesign <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setBridgeDesign(String bridgeDesign) {
		this.bridgeDesign = bridgeDesign;
	}
	/**  
	  * @Title:  getNeckFlangeDesign <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getNeckFlangeDesign() {
		return neckFlangeDesign;
	}
	/**  
	  * @Title:  setNeckFlangeDesign <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setNeckFlangeDesign(String neckFlangeDesign) {
		this.neckFlangeDesign = neckFlangeDesign;
	}
	/**  
	  * @Title:  getPlantFixed <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPlantFixed() {
		return plantFixed;
	}
	/**  
	  * @Title:  setPlantFixed <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPlantFixed(String plantFixed) {
		this.plantFixed = plantFixed;
	}
	
	/**  
	  * @Title:  getFlowStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getFlowStatus() {
		return flowStatus;
	}
	/**  
	  * @Title:  setFlowStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	/**  
	  * @Title:  getStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getStatus() {
		return status;
	}
	/**  
	  * @Title:  setStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setStatus(String status) {
		this.status = status;
	}
	/**  
	  * @Title:  getOrganization <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOrganization() {
		return organization;
	}
	/**  
	  * @Title:  setOrganization <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	/**  
	  * @Title:  getCreateuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreateuser() {
		return createuser;
	}
	/**  
	  * @Title:  setCreateuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	/**  
	  * @Title:  getCreatetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreatetime() {
		return createtime;
	}
	/**  
	  * @Title:  setCreatetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**  
	  * @Title:  getRemark <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRemark() {
		return remark;
	}
	/**  
	  * @Title:  setRemark <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**  
	  * @Title:  getIsInterior <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsInterior() {
		return isInterior;
	}
	/**  
	  * @Title:  setIsInterior <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsInterior(String isInterior) {
		this.isInterior = isInterior;
	}
	/**   
	  * <p>Title: hashCode</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#hashCode()   
	  */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((biteJaw == null) ? 0 : biteJaw.hashCode());
		result = prime * result + ((bridgeDesign == null) ? 0 : bridgeDesign.hashCode());
		result = prime * result + ((chargeModelPerson == null) ? 0 : chargeModelPerson.hashCode());
		result = prime * result + ((chargeModeltime == null) ? 0 : chargeModeltime.hashCode());
		result = prime * result + ((deliverytime == null) ? 0 : deliverytime.hashCode());
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		result = prime * result + ((drillBase == null) ? 0 : drillBase.hashCode());
		result = prime * result + ((gingivalFormer == null) ? 0 : gingivalFormer.hashCode());
		result = prime * result + ((humanGingival == null) ? 0 : humanGingival.hashCode());
		result = prime * result + ((innerCrown == null) ? 0 : innerCrown.hashCode());
		result = prime * result + ((jawFrame == null) ? 0 : jawFrame.hashCode());
		result = prime * result + ((locator == null) ? 0 : locator.hashCode());
		result = prime * result + ((mandibleModel == null) ? 0 : mandibleModel.hashCode());
		result = prime * result + ((mandibleSalver == null) ? 0 : mandibleSalver.hashCode());
		result = prime * result + ((maxillaryModel == null) ? 0 : maxillaryModel.hashCode());
		result = prime * result + ((maxillarySalver == null) ? 0 : maxillarySalver.hashCode());
		result = prime * result + ((neckFlangeDesign == null) ? 0 : neckFlangeDesign.hashCode());
		result = prime * result + ((occlusioRecord == null) ? 0 : occlusioRecord.hashCode());
		result = prime * result + ((oldDentureModel == null) ? 0 : oldDentureModel.hashCode());
		result = prime * result + ((omanJawBase == null) ? 0 : omanJawBase.hashCode());
		result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
		result = prime * result + ((other == null) ? 0 : other.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((plantFixed == null) ? 0 : plantFixed.hashCode());
		result = prime * result + ((processUnit == null) ? 0 : processUnit.hashCode());
		result = prime * result + ((replaceBody == null) ? 0 : replaceBody.hashCode());
		result = prime * result + ((screw == null) ? 0 : screw.hashCode());
		result = prime * result + ((sendOuttime == null) ? 0 : sendOuttime.hashCode());
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((shiftLever == null) ? 0 : shiftLever.hashCode());
		result = prime * result + ((syntopy == null) ? 0 : syntopy.hashCode());
		result = prime * result + ((systemNumber == null) ? 0 : systemNumber.hashCode());
		result = prime * result + ((userCode == null) ? 0 : userCode.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((waxPattern == null) ? 0 : waxPattern.hashCode());
		return result;
	}
	/**   
	  * <p>Title: equals</p>   
	  * <p>Description: </p>   
	  * @param obj
	  * @return   
	  * @see java.lang.Object#equals(java.lang.Object)   
	  */  
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KqdsMachining other = (KqdsMachining) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (biteJaw == null) {
			if (other.biteJaw != null)
				return false;
		} else if (!biteJaw.equals(other.biteJaw))
			return false;
		if (bridgeDesign == null) {
			if (other.bridgeDesign != null)
				return false;
		} else if (!bridgeDesign.equals(other.bridgeDesign))
			return false;
		if (chargeModelPerson == null) {
			if (other.chargeModelPerson != null)
				return false;
		} else if (!chargeModelPerson.equals(other.chargeModelPerson))
			return false;
		if (chargeModeltime == null) {
			if (other.chargeModeltime != null)
				return false;
		} else if (!chargeModeltime.equals(other.chargeModeltime))
			return false;
		if (deliverytime == null) {
			if (other.deliverytime != null)
				return false;
		} else if (!deliverytime.equals(other.deliverytime))
			return false;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (drillBase == null) {
			if (other.drillBase != null)
				return false;
		} else if (!drillBase.equals(other.drillBase))
			return false;
		if (gingivalFormer == null) {
			if (other.gingivalFormer != null)
				return false;
		} else if (!gingivalFormer.equals(other.gingivalFormer))
			return false;
		if (humanGingival == null) {
			if (other.humanGingival != null)
				return false;
		} else if (!humanGingival.equals(other.humanGingival))
			return false;
		if (innerCrown == null) {
			if (other.innerCrown != null)
				return false;
		} else if (!innerCrown.equals(other.innerCrown))
			return false;
		if (jawFrame == null) {
			if (other.jawFrame != null)
				return false;
		} else if (!jawFrame.equals(other.jawFrame))
			return false;
		if (locator == null) {
			if (other.locator != null)
				return false;
		} else if (!locator.equals(other.locator))
			return false;
		if (mandibleModel == null) {
			if (other.mandibleModel != null)
				return false;
		} else if (!mandibleModel.equals(other.mandibleModel))
			return false;
		if (mandibleSalver == null) {
			if (other.mandibleSalver != null)
				return false;
		} else if (!mandibleSalver.equals(other.mandibleSalver))
			return false;
		if (maxillaryModel == null) {
			if (other.maxillaryModel != null)
				return false;
		} else if (!maxillaryModel.equals(other.maxillaryModel))
			return false;
		if (maxillarySalver == null) {
			if (other.maxillarySalver != null)
				return false;
		} else if (!maxillarySalver.equals(other.maxillarySalver))
			return false;
		if (neckFlangeDesign == null) {
			if (other.neckFlangeDesign != null)
				return false;
		} else if (!neckFlangeDesign.equals(other.neckFlangeDesign))
			return false;
		if (occlusioRecord == null) {
			if (other.occlusioRecord != null)
				return false;
		} else if (!occlusioRecord.equals(other.occlusioRecord))
			return false;
		if (oldDentureModel == null) {
			if (other.oldDentureModel != null)
				return false;
		} else if (!oldDentureModel.equals(other.oldDentureModel))
			return false;
		if (omanJawBase == null) {
			if (other.omanJawBase != null)
				return false;
		} else if (!omanJawBase.equals(other.omanJawBase))
			return false;
		if (orderNumber == null) {
			if (other.orderNumber != null)
				return false;
		} else if (!orderNumber.equals(other.orderNumber))
			return false;
		if (this.other == null) {
			if (other.other != null)
				return false;
		} else if (!this.other.equals(other.other))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (plantFixed == null) {
			if (other.plantFixed != null)
				return false;
		} else if (!plantFixed.equals(other.plantFixed))
			return false;
		if (processUnit == null) {
			if (other.processUnit != null)
				return false;
		} else if (!processUnit.equals(other.processUnit))
			return false;
		if (replaceBody == null) {
			if (other.replaceBody != null)
				return false;
		} else if (!replaceBody.equals(other.replaceBody))
			return false;
		if (screw == null) {
			if (other.screw != null)
				return false;
		} else if (!screw.equals(other.screw))
			return false;
		if (sendOuttime == null) {
			if (other.sendOuttime != null)
				return false;
		} else if (!sendOuttime.equals(other.sendOuttime))
			return false;
		if (seqId == null) {
			if (other.seqId != null)
				return false;
		} else if (!seqId.equals(other.seqId))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (shiftLever == null) {
			if (other.shiftLever != null)
				return false;
		} else if (!shiftLever.equals(other.shiftLever))
			return false;
		if (syntopy == null) {
			if (other.syntopy != null)
				return false;
		} else if (!syntopy.equals(other.syntopy))
			return false;
		if (systemNumber == null) {
			if (other.systemNumber != null)
				return false;
		} else if (!systemNumber.equals(other.systemNumber))
			return false;
		if (userCode == null) {
			if (other.userCode != null)
				return false;
		} else if (!userCode.equals(other.userCode))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (waxPattern == null) {
			if (other.waxPattern != null)
				return false;
		} else if (!waxPattern.equals(other.waxPattern))
			return false;
		return true;
	}
	/**   
	  * <p>Title: clone</p>   
	  * <p>Description: </p>   
	  * @return
	  * @throws CloneNotSupportedException   
	  * @see java.lang.Object#clone()   
	  */  
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "KqdsMachining [seqId=" + seqId + ", systemNumber=" + systemNumber + ", orderNumber=" + orderNumber
				+ ", processUnit=" + processUnit + ", doctor=" + doctor + ", userCode=" + userCode + ", userName="
				+ userName + ", sex=" + sex + ", age=" + age + ", phoneNumber=" + phoneNumber + ", deliverytime="
				+ deliverytime + ", chargeModelPerson=" + chargeModelPerson + ", chargeModeltime=" + chargeModeltime
				+ ", sendOuttime=" + sendOuttime + ", maxillaryModel=" + maxillaryModel + ", occlusioRecord="
				+ occlusioRecord + ", replaceBody=" + replaceBody + ", gingivalFormer=" + gingivalFormer
				+ ", mandibleModel=" + mandibleModel + ", oldDentureModel=" + oldDentureModel + ", drillBase="
				+ drillBase + ", locator=" + locator + ", maxillarySalver=" + maxillarySalver + ", waxPattern="
				+ waxPattern + ", screw=" + screw + ", jawFrame=" + jawFrame + ", mandibleSalver=" + mandibleSalver
				+ ", shiftLever=" + shiftLever + ", humanGingival=" + humanGingival + ", omanJawBase=" + omanJawBase
				+ ", other=" + other + ", innerCrown=" + innerCrown + ", biteJaw=" + biteJaw + ", syntopy=" + syntopy
				+ ", bridgeDesign=" + bridgeDesign + ", neckFlangeDesign=" + neckFlangeDesign + ", plantFixed="
				+ plantFixed + "]";
	}
	/**   
	  * <p>Title: finalize</p>   
	  * <p>Description: </p>   
	  * @throws Throwable   
	  * @see java.lang.Object#finalize()   
	  */  
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
}
