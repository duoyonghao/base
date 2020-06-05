/**  
  *
  * @Title:  KqdsCkGoodsDetail.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2020年5月18日 上午9:44:10   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

public class KqdsCkGoodsDetail {
	private String seqId;

	private String goodsname;

	private String goodscode;

	private String goodstype;

	private String goodsnorms;

	private String goodsunit;

	private String organization;

	private String createuser;

	private String createtime;

	private Integer sortno;

	private String kuwei;

	private String remark;

	private Integer minnums;

	private Integer mingj;

	private Integer maxnums;

	private Integer maxgj;

	private String pym;
	
	private String status;
	
	private String type;
	
	/**  
	  * @Title:  getType <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getType() {
		return type;
	}

	/**  
	  * @Title:  setType <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setType(String type) {
		this.type = type;
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

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname == null ? null : goodsname.trim();
	}

	public String getGoodscode() {
		return goodscode;
	}

	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode == null ? null : goodscode.trim();
	}

	public String getGoodstype() {
		return goodstype;
	}

	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype == null ? null : goodstype.trim();
	}

	public String getGoodsnorms() {
		return goodsnorms;
	}

	public void setGoodsnorms(String goodsnorms) {
		this.goodsnorms = goodsnorms == null ? null : goodsnorms.trim();
	}

	public String getGoodsunit() {
		return goodsunit;
	}

	public void setGoodsunit(String goodsunit) {
		this.goodsunit = goodsunit == null ? null : goodsunit.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
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

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public String getKuwei() {
		return kuwei;
	}

	public void setKuwei(String kuwei) {
		this.kuwei = kuwei == null ? null : kuwei.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getMinnums() {
		return minnums;
	}

	public void setMinnums(Integer minnums) {
		this.minnums = minnums;
	}

	public Integer getMingj() {
		return mingj;
	}

	public void setMingj(Integer mingj) {
		this.mingj = mingj;
	}

	public Integer getMaxnums() {
		return maxnums;
	}

	public void setMaxnums(Integer maxnums) {
		this.maxnums = maxnums;
	}

	public Integer getMaxgj() {
		return maxgj;
	}

	public void setMaxgj(Integer maxgj) {
		this.maxgj = maxgj;
	}

	public String getPym() {
		return pym;
	}

	public void setPym(String pym) {
		this.pym = pym == null ? null : pym.trim();
	}
}
