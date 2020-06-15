package com.kqds.timer.task;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.sys.YZDept;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class GoodsAlarmTask implements Job {
  @Autowired
  private KQDS_Ck_Goods_DetailLogic logic;
  
  @Autowired
  private YZParaLogic paraLogic;
  
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    try {
      doTask();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void doTask() throws Exception {
    if (ChainUtil.isOpenChain() || ChainUtil.isOpenTry()) {
      List<YZDept> list = ChainUtil.getHosList(null);
      for (YZDept yzDept : list) {
        if (YZUtility.isNullorEmpty(yzDept.getDeptCode()))
          continue; 
        goodsAlarm(yzDept.getDeptCode());
      } 
    } else {
      String organization = YZSysProps.getProp(SysParaUtil.ORGANIZATION);
      goodsAlarm(organization);
    } 
  }
  
  private void goodsAlarm(String organization) throws Exception {
    JSONObject sysPara = this.paraLogic.getSysPara(organization);
    String ckpriv = sysPara.getString(SysParaUtil.PRIV_CK_SEQID);
    Map<String, String> map3 = new HashMap<>();
    if (!YZUtility.isNullorEmpty(organization))
      map3.put("organization", organization); 
    List<JSONObject> list = this.logic.selectGoodsGjNoHousseList(map3);
    this.logic.selectGoodsGjTx(list, ckpriv, organization);
  }
}
