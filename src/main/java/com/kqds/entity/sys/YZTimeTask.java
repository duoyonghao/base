package com.kqds.entity.sys;

import java.util.Date;

/**
 * @Title: Entity
 * @Description: 定时任务管理
 * @author JueYue
 * @date 2013-09-21 20:47:43
 * @version V1.0
 * 
 */
public class YZTimeTask implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** id */
	private String seqId;
	/** 任务ID */
	private String taskId;
	/** 任务描述 */
	private String taskDescribe;
	/** cron表达式 */
	private String cronExpression;
	/** 是否生效了0未生效,1生效了 */
	private String isEffect;
	/** 是否运行0停止,1运行 */
	private String isStart;
	/** 创建时间 */
	private java.util.Date createDate;
	/** 创建人ID */
	private String createBy;
	/** 创建人名称 */
	private String createName;
	/** 修改时间 */
	private java.util.Date updateDate;
	/** 修改人ID */
	private String updateBy;
	/** 修改人名称 */
	private String updateName;

	/** 任务类名 **/
	private String className;
	/** 运行任务的服务器IP **/
	private String runServerIp;
	/** 远程主机（域名/IP+项目路径） **/
	private String runServer;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskDescribe() {
		return taskDescribe;
	}

	public void setTaskDescribe(String taskDescribe) {
		this.taskDescribe = taskDescribe;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(String isEffect) {
		this.isEffect = isEffect;
	}

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRunServerIp() {
		return runServerIp;
	}

	public void setRunServerIp(String runServerIp) {
		this.runServerIp = runServerIp;
	}

	public String getRunServer() {
		return runServer;
	}

	public void setRunServer(String runServer) {
		this.runServer = runServer;
	}
}
