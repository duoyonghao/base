/**  
  *
  * @Title:  LcljOrderTrackDeleteRecord.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月3日 上午10:16:26   
  * @version V1.0  
  */
package com.hudh.lclj.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: LcljOrderTrackDeleteRecord
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 海德堡联合口腔
 * @date: 2019年9月3日 上午10:16:26
 * 
 */
public class LcljOrderTrackDeleteRecord implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 2228214573173010697L;

	private String seqId;
	private String createuser;
	private String createtime;
	private String lcljId;
	private String order_number;
	private String organization;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
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

	public String getLcljId() {
		return lcljId;
	}

	public void setLcljId(String lcljId) {
		this.lcljId = lcljId;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

}
