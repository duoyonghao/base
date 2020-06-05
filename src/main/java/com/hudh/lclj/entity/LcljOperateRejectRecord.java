/**  
  *
  * @Title:  LcljOperateRecord.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年6月27日 下午2:09:10   
  * @version V1.0  
  */
package com.hudh.lclj.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: LcljOperateRecord
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 海德堡联合口腔
 * @date: 2019年6月27日 下午2:09:10
 * 
 */
public class LcljOperateRejectRecord implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private String SEQ_ID;
	private String createuser;
	private String createtime;
	private String nodeName;
	private String nodeId;
	private String organization;
	private String orderNumber;

	public String getSEQ_ID() {
		return SEQ_ID;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setSEQ_ID(String sEQ_ID) {
		SEQ_ID = sEQ_ID;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
}
