/**  
  *
  * @Title:  LcljOperationNodeRemark.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月31日 上午10:32:42   
  * @version V1.0  
  */
package com.hudh.lclj.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: LcljOperationNodeRemark
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 海德堡联合口腔
 * @date: 2019年8月31日 上午10:32:42
 * 
 */
public class LcljOperationNodeRemark implements Serializable {

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;

	private String seqId;
	
	private String LcljId;
	
	private String order_number;
	
	private String nodeName;
	
	private String nodeId;
	
	private String createuser;
	
	private String createtime;
	
	private String remark;
	
	private String organization;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getLcljId() {
		return LcljId;
	}

	public void setLcljId(String lcljId) {
		LcljId = lcljId;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}


}
