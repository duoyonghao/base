/**  
  *
  * @Title:  KqdsUserdocumentMergeRecord.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年7月29日 下午2:58:39   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

/**  
  * 
  * @ClassName:  KqdsUserdocumentMergeRecord   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年7月29日 下午2:58:39   
  *      
  */
public class KqdsUserdocumentMergeRecord {
	
	private String seqId;
	private String cratetime;
	private String crateuser;
	private String username_one;
	private String username_two;
	private String usercode_one;
	private String usercode_two;
	private String organization;
	private String userdocument_one_crateuser;
	private String userdocument_two_crateuser;
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getCratetime() {
		return cratetime;
	}
	public void setCratetime(String cratetime) {
		this.cratetime = cratetime;
	}
	public String getCrateuser() {
		return crateuser;
	}
	public void setCrateuser(String crateuser) {
		this.crateuser = crateuser;
	}
	public String getUsername_one() {
		return username_one;
	}
	public void setUsername_one(String username_one) {
		this.username_one = username_one;
	}
	public String getUsername_two() {
		return username_two;
	}
	public void setUsername_two(String username_two) {
		this.username_two = username_two;
	}
	public String getUsercode_one() {
		return usercode_one;
	}
	public void setUsercode_one(String usercode_one) {
		this.usercode_one = usercode_one;
	}
	public String getUsercode_two() {
		return usercode_two;
	}
	public void setUsercode_two(String usercode_two) {
		this.usercode_two = usercode_two;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getUserdocument_one_crateuser() {
		return userdocument_one_crateuser;
	}
	public void setUserdocument_one_crateuser(String userdocument_one_crateuser) {
		this.userdocument_one_crateuser = userdocument_one_crateuser;
	}
	public String getUserdocument_two_crateuser() {
		return userdocument_two_crateuser;
	}
	public void setUserdocument_two_crateuser(String userdocument_two_crateuser) {
		this.userdocument_two_crateuser = userdocument_two_crateuser;
	}

}
