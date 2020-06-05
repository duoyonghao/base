package com.kqds.timer;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.kqds.entity.sys.YZTimeTask;
import com.kqds.service.sys.timer.TimeTaskLogic;
import com.kqds.util.sys.TableNameUtil;

/**
 * 在原有功能的基础上面增加数据库的读取
 * 
 * @author JueYue
 * @date 2013-9-22
 * @version 1.0
 */
public class DataBaseCronTriggerBean extends CronTriggerBean {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TimeTaskLogic timeTaskService;

	/**
	 * 读取数据库更新文件
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		YZTimeTask task = null;
		Map map = new HashMap();
		map.put("TASK_ID", this.getName());
		try {
			task = (YZTimeTask) timeTaskService.loadList4One(TableNameUtil.SYS_TIMETASK, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (task != null && task.getIsEffect().equals("1") && !task.getCronExpression().equals(this.getCronExpression())) {

			try {
				this.setCronExpression(task.getCronExpression());
			} catch (ParseException e) {
				// TODO 异常必须被处理
				e.printStackTrace();
			}

			// DynamicTask.updateSpringMvcTaskXML(this,task.getCronExpression());
		}
	}

}
