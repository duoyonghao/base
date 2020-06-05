package com.kqds.timer.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.sys.YZDept;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

/**
 * 仓库告警检测
 * 
 * @author Administrator
 * 
 */
@Component
@Controller
public class GoodsAlarmTask implements Job {
	@Autowired
	private KQDS_Ck_Goods_DetailLogic logic;
	@Autowired
	private YZParaLogic paraLogic;

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

		if (ChainUtil.isOpenChain() || ChainUtil.isOpenTry()) {
			List<YZDept> list = ChainUtil.getHosList(null);
			for (YZDept yzDept : list) {
				if (YZUtility.isNullorEmpty(yzDept.getDeptCode())) {
					continue;
				}
				goodsAlarm(yzDept.getDeptCode());
			}
		} else {
			String organization = YZSysProps.getProp(SysParaUtil.ORGANIZATION);
			goodsAlarm(organization);
		}
	}

	private void goodsAlarm(String organization) throws Exception {
		JSONObject sysPara = paraLogic.getSysPara(organization);
		String ckpriv = sysPara.getString(SysParaUtil.PRIV_CK_SEQID);

		// 查询产生报警的商品
		Map<String, String> map3 = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(organization)) {
			map3.put("organization", organization);
		}
		List<JSONObject> list = logic.selectGoodsGjNoHousseList(map3);
		// 报警提醒
		logic.selectGoodsGjTx(list, ckpriv, organization);

	}

}
