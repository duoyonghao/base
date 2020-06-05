package com.kqds.timer.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.service.sys.register.YZRegisterLogic;

@Component
@Controller
public class RegisterYzTask implements Job {
	@Autowired
	private YZRegisterLogic logic;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			doTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doTask() throws Exception {

		// System.out.println("----------Test----");

		/*
		 * Map<String, String> map = new HashMap<String, String>(); map.put("status",
		 * "0"); try { List<YZRegister> gerlist = (List<YZRegister>)
		 * logic.loadList(TableNameUtil.SYS_REGISTER,map); if (gerlist != null &&
		 * gerlist.size() > 0) { for (YZRegister gster : gerlist) { String organization
		 * = YZRegisterLogic.getDeptcodeNumstr(); gster.setStatus(1);
		 * gster.setOrganization(organization);
		 * logic.updateSingleUUID(TableNameUtil.SYS_REGISTER, gster); // 初始化门诊信息 YZDept
		 * dept = new YZDept(); dept.setSeqId(YZUtility.getUUID());
		 * dept.setDeptName(gster.getDwmc()); dept.setDeptParent("0");
		 * dept.setDeptCode(organization); dept.setDeptByname(gster.getDwmc());
		 * dept.setPrintName(gster.getDwmc()); dept.setOrgflag(1);
		 * dept.setCreatetime(gster.getCreatetime());
		 * logic.saveSingleUUID(TableNameUtil.SYS_DEPT, dept); // 设置仓库初始化 logic.setck(
		 * null, dept.getDeptCode()); // 初始化下级部门人员 logic.registerNextInit( null, dept,
		 * gster.getSjhm()); } } } catch (Exception e) {
		 * YZUtility.DEAL_ERROR(e.getMessage(), true, e, null, null); }
		 */

	}
}
