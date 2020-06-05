/**  
  *
  * @Title:  KqdsBlct.java   
  * @Package com.kqds.entity.base   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年6月18日 下午4:33:20   
  * @version V1.0  
  */ 
package com.kqds.entity.base;

/**  
  * 
  * @ClassName:  KqdsBlct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年6月18日 下午4:33:20   
  *      
  */
public class KqdsBlct {
		private String seqId;

		private String ctname;

		private String cttype;

		private String ctnexttype;

		private String createuser;

		private String createtime;

		private Integer useflag;

		private Integer orderno;

		private String organization;

		public String getSeqId() {
			return seqId;
		}

		public void setSeqId(String seqId) {
			this.seqId = seqId == null ? null : seqId.trim();
		}

		public String getCtname() {
			return ctname;
		}

		public void setCtname(String ctname) {
			this.ctname = ctname == null ? null : ctname.trim();
		}

		public String getCttype() {
			return cttype;
		}

		public void setCttype(String cttype) {
			this.cttype = cttype == null ? null : cttype.trim();
		}

		public String getCtnexttype() {
			return ctnexttype;
		}

		public void setCtnexttype(String ctnexttype) {
			this.ctnexttype = ctnexttype == null ? null : ctnexttype.trim();
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

		public Integer getUseflag() {
			return useflag;
		}

		public void setUseflag(Integer useflag) {
			this.useflag = useflag;
		}

		public Integer getOrderno() {
			return orderno;
		}

		public void setOrderno(Integer orderno) {
			this.orderno = orderno;
		}

		public String getOrganization() {
			return organization;
		}

		public void setOrganization(String organization) {
			this.organization = organization == null ? null : organization.trim();
		}

}
