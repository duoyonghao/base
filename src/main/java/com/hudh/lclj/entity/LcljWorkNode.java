/**  
  *
  * @Title:  LcljWorkNode.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月2日 下午4:01:15   
  * @version V1.0  
  */ 
package com.hudh.lclj.entity;

/**  
  * 
  * @ClassName:  LcljWorkNode   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月2日 下午4:01:15   
  *      
  */
public class LcljWorkNode {
	
	private String num; //流程顺序编号
	private String nodeName; //节点名称
	private String nodeLimit; //当前节点时限
	private String nodeStatus; //流程节点状态办理状态 0：未完成  1：进行中   2： 已完成   3：超期
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeLimit() {
		return nodeLimit;
	}
	public void setNodeLimit(String nodeLimit) {
		this.nodeLimit = nodeLimit;
	}
	public String getNodeStatus() {
		return nodeStatus;
	}
	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

}
