/**  
  *
  * @Title:  VisitPlant.java   
  * @Package com.hudh.tjhf.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月2日 上午9:25:17   
  * @version V1.0  
  */
package com.hudh.tjhf.entity;

/**
 * 
 * @ClassName: VisitPlant
 * @Description:TODO(添加回访计划实体类)
 * @author: 海德堡联合口腔
 * @date: 2019年11月2日 上午9:25:17
 * 
 */
public class VisitPlant {

	private String seqId;
	private String createtime;
	private String createuser;
	private String organization;
	private String usercode;
	private String username;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

}
