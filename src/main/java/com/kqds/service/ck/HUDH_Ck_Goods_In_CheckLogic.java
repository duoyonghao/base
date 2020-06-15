package com.kqds.service.ck;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsInCheck;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HUDH_Ck_Goods_In_CheckLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  @Autowired
  private KQDS_Ck_Goods_InLogic logic;
  
  @Transactional(rollbackFor = {Exception.class})
  public void saveGoodsInCheck(KqdsCkGoodsInCheck dp, String incode) throws Exception {
    if (dp.getPacking().equals("1") && dp.getReport().equals("1") && dp.getCertificate().equals("1")) {
      dp.setResult("1");
      this.logic.updateCheckStatus(dp.getGoodsinid());
      this.logic.updateAuditStatus(incode);
      this.logic.goodsAddInStock(dp.getGoodsinid(), dp.getOrganization());
    } else {
      dp.setResult("0");
    } 
    this.dao.save(String.valueOf(TableNameUtil.KQDS_CK_GOODS_IN_CHECK) + ".saveGoodsInCheck", dp);
  }
  
  public List<JSONObject> findGoodsInExamineByInId(String goodsInId) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_CK_GOODS_IN_CHECK) + ".findGoodsInExamineByInId", goodsInId);
    return list;
  }
}
