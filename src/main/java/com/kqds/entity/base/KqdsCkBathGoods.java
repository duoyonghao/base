/**  
  *
  * @Title:  KqdsCkBathGoods.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2020年4月3日 下午1:50:40   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

import java.io.Serializable;
import java.math.BigDecimal;

/**  
  * 
  * @ClassName:  KqdsCkBathGoods   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2020年4月3日 下午1:50:40   
  *      
  */
public class KqdsCkBathGoods implements Serializable{
	
	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;

	private String seqId;

	private String sshouse;

	private BigDecimal goodsprice;

	private Integer nums;

	private Integer numsexport;

	private BigDecimal goodspriceexport;

	private String organization;

	private BigDecimal kcmoney;
	
	private String goodsdetailid;

	private String goodsname;

	private String goodscode;

	private String goodstype;

	private String goodsnorms;

	private String goodsunit;

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
	  * @Title:  getSshouse <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSshouse() {
		return sshouse;
	}

	/**  
	  * @Title:  setSshouse <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSshouse(String sshouse) {
		this.sshouse = sshouse;
	}

	/**  
	  * @Title:  getGoodsprice <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public BigDecimal getGoodsprice() {
		return goodsprice;
	}

	/**  
	  * @Title:  setGoodsprice <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}

	/**  
	  * @Title:  getNums <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getNums() {
		return nums;
	}

	/**  
	  * @Title:  setNums <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setNums(Integer nums) {
		this.nums = nums;
	}

	/**  
	  * @Title:  getNumsexport <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getNumsexport() {
		return numsexport;
	}

	/**  
	  * @Title:  setNumsexport <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setNumsexport(Integer numsexport) {
		this.numsexport = numsexport;
	}

	/**  
	  * @Title:  getGoodspriceexport <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public BigDecimal getGoodspriceexport() {
		return goodspriceexport;
	}

	/**  
	  * @Title:  setGoodspriceexport <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public void setGoodspriceexport(BigDecimal goodspriceexport) {
		this.goodspriceexport = goodspriceexport;
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
	  * @Title:  getKcmoney <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public BigDecimal getKcmoney() {
		return kcmoney;
	}

	/**  
	  * @Title:  setKcmoney <BR>  
	  * @Description: please write your description <BR>  
	  * @return: BigDecimal <BR>  
	  */
	public void setKcmoney(BigDecimal kcmoney) {
		this.kcmoney = kcmoney;
	}

	/**  
	  * @Title:  getGoodsdetailid <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGoodsdetailid() {
		return goodsdetailid;
	}

	/**  
	  * @Title:  setGoodsdetailid <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGoodsdetailid(String goodsdetailid) {
		this.goodsdetailid = goodsdetailid;
	}

	/**  
	  * @Title:  getGoodsname <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGoodsname() {
		return goodsname;
	}

	/**  
	  * @Title:  setGoodsname <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	/**  
	  * @Title:  getGoodscode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGoodscode() {
		return goodscode;
	}

	/**  
	  * @Title:  setGoodscode <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode;
	}

	/**  
	  * @Title:  getGoodstype <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGoodstype() {
		return goodstype;
	}

	/**  
	  * @Title:  setGoodstype <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}

	/**  
	  * @Title:  getGoodsnorms <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGoodsnorms() {
		return goodsnorms;
	}

	/**  
	  * @Title:  setGoodsnorms <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGoodsnorms(String goodsnorms) {
		this.goodsnorms = goodsnorms;
	}

	/**  
	  * @Title:  getGoodsunit <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGoodsunit() {
		return goodsunit;
	}

	/**  
	  * @Title:  setGoodsunit <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGoodsunit(String goodsunit) {
		this.goodsunit = goodsunit;
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
	  * @Title:  getSortno <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getSortno() {
		return sortno;
	}

	/**  
	  * @Title:  setSortno <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	/**  
	  * @Title:  getKuwei <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getKuwei() {
		return kuwei;
	}

	/**  
	  * @Title:  setKuwei <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setKuwei(String kuwei) {
		this.kuwei = kuwei;
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
	  * @Title:  getMinnums <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getMinnums() {
		return minnums;
	}

	/**  
	  * @Title:  setMinnums <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setMinnums(Integer minnums) {
		this.minnums = minnums;
	}

	/**  
	  * @Title:  getMingj <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getMingj() {
		return mingj;
	}

	/**  
	  * @Title:  setMingj <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setMingj(Integer mingj) {
		this.mingj = mingj;
	}

	/**  
	  * @Title:  getMaxnums <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getMaxnums() {
		return maxnums;
	}

	/**  
	  * @Title:  setMaxnums <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setMaxnums(Integer maxnums) {
		this.maxnums = maxnums;
	}

	/**  
	  * @Title:  getMaxgj <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public Integer getMaxgj() {
		return maxgj;
	}

	/**  
	  * @Title:  setMaxgj <BR>  
	  * @Description: please write your description <BR>  
	  * @return: Integer <BR>  
	  */
	public void setMaxgj(Integer maxgj) {
		this.maxgj = maxgj;
	}

	/**  
	  * @Title:  getPym <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPym() {
		return pym;
	}

	/**  
	  * @Title:  setPym <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPym(String pym) {
		this.pym = pym;
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
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((createuser == null) ? 0 : createuser.hashCode());
		result = prime * result + ((goodscode == null) ? 0 : goodscode.hashCode());
		result = prime * result + ((goodsdetailid == null) ? 0 : goodsdetailid.hashCode());
		result = prime * result + ((goodsname == null) ? 0 : goodsname.hashCode());
		result = prime * result + ((goodsnorms == null) ? 0 : goodsnorms.hashCode());
		result = prime * result + ((goodsprice == null) ? 0 : goodsprice.hashCode());
		result = prime * result + ((goodspriceexport == null) ? 0 : goodspriceexport.hashCode());
		result = prime * result + ((goodstype == null) ? 0 : goodstype.hashCode());
		result = prime * result + ((goodsunit == null) ? 0 : goodsunit.hashCode());
		result = prime * result + ((kcmoney == null) ? 0 : kcmoney.hashCode());
		result = prime * result + ((kuwei == null) ? 0 : kuwei.hashCode());
		result = prime * result + ((maxgj == null) ? 0 : maxgj.hashCode());
		result = prime * result + ((maxnums == null) ? 0 : maxnums.hashCode());
		result = prime * result + ((mingj == null) ? 0 : mingj.hashCode());
		result = prime * result + ((minnums == null) ? 0 : minnums.hashCode());
		result = prime * result + ((nums == null) ? 0 : nums.hashCode());
		result = prime * result + ((numsexport == null) ? 0 : numsexport.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((pym == null) ? 0 : pym.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((seqId == null) ? 0 : seqId.hashCode());
		result = prime * result + ((sortno == null) ? 0 : sortno.hashCode());
		result = prime * result + ((sshouse == null) ? 0 : sshouse.hashCode());
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
		KqdsCkBathGoods other = (KqdsCkBathGoods) obj;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (createuser == null) {
			if (other.createuser != null)
				return false;
		} else if (!createuser.equals(other.createuser))
			return false;
		if (goodscode == null) {
			if (other.goodscode != null)
				return false;
		} else if (!goodscode.equals(other.goodscode))
			return false;
		if (goodsdetailid == null) {
			if (other.goodsdetailid != null)
				return false;
		} else if (!goodsdetailid.equals(other.goodsdetailid))
			return false;
		if (goodsname == null) {
			if (other.goodsname != null)
				return false;
		} else if (!goodsname.equals(other.goodsname))
			return false;
		if (goodsnorms == null) {
			if (other.goodsnorms != null)
				return false;
		} else if (!goodsnorms.equals(other.goodsnorms))
			return false;
		if (goodsprice == null) {
			if (other.goodsprice != null)
				return false;
		} else if (!goodsprice.equals(other.goodsprice))
			return false;
		if (goodspriceexport == null) {
			if (other.goodspriceexport != null)
				return false;
		} else if (!goodspriceexport.equals(other.goodspriceexport))
			return false;
		if (goodstype == null) {
			if (other.goodstype != null)
				return false;
		} else if (!goodstype.equals(other.goodstype))
			return false;
		if (goodsunit == null) {
			if (other.goodsunit != null)
				return false;
		} else if (!goodsunit.equals(other.goodsunit))
			return false;
		if (kcmoney == null) {
			if (other.kcmoney != null)
				return false;
		} else if (!kcmoney.equals(other.kcmoney))
			return false;
		if (kuwei == null) {
			if (other.kuwei != null)
				return false;
		} else if (!kuwei.equals(other.kuwei))
			return false;
		if (maxgj == null) {
			if (other.maxgj != null)
				return false;
		} else if (!maxgj.equals(other.maxgj))
			return false;
		if (maxnums == null) {
			if (other.maxnums != null)
				return false;
		} else if (!maxnums.equals(other.maxnums))
			return false;
		if (mingj == null) {
			if (other.mingj != null)
				return false;
		} else if (!mingj.equals(other.mingj))
			return false;
		if (minnums == null) {
			if (other.minnums != null)
				return false;
		} else if (!minnums.equals(other.minnums))
			return false;
		if (nums == null) {
			if (other.nums != null)
				return false;
		} else if (!nums.equals(other.nums))
			return false;
		if (numsexport == null) {
			if (other.numsexport != null)
				return false;
		} else if (!numsexport.equals(other.numsexport))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (pym == null) {
			if (other.pym != null)
				return false;
		} else if (!pym.equals(other.pym))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (seqId == null) {
			if (other.seqId != null)
				return false;
		} else if (!seqId.equals(other.seqId))
			return false;
		if (sortno == null) {
			if (other.sortno != null)
				return false;
		} else if (!sortno.equals(other.sortno))
			return false;
		if (sshouse == null) {
			if (other.sshouse != null)
				return false;
		} else if (!sshouse.equals(other.sshouse))
			return false;
		return true;
	}

	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "KqdsCkBathGoods [seqId=" + seqId + ", sshouse=" + sshouse + ", goodsprice=" + goodsprice + ", nums="
				+ nums + ", numsexport=" + numsexport + ", goodspriceexport=" + goodspriceexport + ", organization="
				+ organization + ", kcmoney=" + kcmoney + ", goodsdetailid=" + goodsdetailid + ", goodsname="
				+ goodsname + ", goodscode=" + goodscode + ", goodstype=" + goodstype + ", goodsnorms=" + goodsnorms
				+ ", goodsunit=" + goodsunit + ", createuser=" + createuser + ", createtime=" + createtime + ", sortno="
				+ sortno + ", kuwei=" + kuwei + ", remark=" + remark + ", minnums=" + minnums + ", mingj=" + mingj
				+ ", maxnums=" + maxnums + ", maxgj=" + maxgj + ", pym=" + pym + "]";
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
	
}
