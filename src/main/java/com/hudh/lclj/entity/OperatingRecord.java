/**  
  *
  * @Title:  OperatingRecord.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年7月26日 下午6:46:43   
  * @version V1.0  
  */ 
package com.hudh.lclj.entity;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  OperatingRecord   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年7月26日 下午6:46:43   
  *      
  */
public class OperatingRecord implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String lcljId;
	
	private String name;
	
	private String createTime;

	/**  
	  * @Title:  getId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getId() {
		return id;
	}

	/**  
	  * @Title:  setId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setId(String id) {
		this.id = id;
	}

	/**  
	  * @Title:  getLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljId() {
		return lcljId;
	}

	/**  
	  * @Title:  setLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljId(String lcljId) {
		this.lcljId = lcljId;
	}

	/**  
	  * @Title:  getName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getName() {
		return name;
	}

	/**  
	  * @Title:  setName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	  * @Title:  getCreateTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreateTime() {
		return createTime;
	}

	/**  
	  * @Title:  setCreateTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#toString()
	  */  
	@Override
	public String toString() {
		return "OperatingRecord [id=" + id + ", lcljId=" + lcljId + ", name=" + name + ", createTime=" + createTime
				+ "]";
	}

}
