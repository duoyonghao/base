package com.kqds.controller.ck;

import com.kqds.entity.base.KqdsCkGoodsDeleter;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.HUDH_Ck_Goods_In_DeleterLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"HUDH_Ck_Goods_In_DeleterAct"})
public class HUDH_Ck_Goods_In_DeleterAct {
  private static Logger logger = LoggerFactory.getLogger(HUDH_Ck_Goods_In_DeleterAct.class);
  
  @Autowired
  private HUDH_Ck_Goods_In_DeleterLogic logic;
  
  @RequestMapping({"/saveDeletePersonInfor.act"})
  public String saveDeletePersonInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String menzhen = ChainUtil.getCurrentOrganization(request);
    String inseqId = request.getParameter("inseqId");
    String indetailseqId = request.getParameter("indetailseqId");
    String goodsseqId = request.getParameter("goodsseqId");
    String remark = request.getParameter("remark");
    String goodsname = request.getParameter("goodsname");
    String goodscode = request.getParameter("goodscode");
    String goodsnorms = request.getParameter("goodsnorms");
    KqdsCkGoodsDeleter dp = new KqdsCkGoodsDeleter();
    dp.setSeqId(YZUtility.getUUID());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    dp.setGoodsseqId(goodsseqId);
    dp.setIndetailseqId(indetailseqId);
    dp.setInseqId(inseqId);
    dp.setCreator(person.getSeqId());
    dp.setOrganization(menzhen);
    dp.setRemark(remark);
    dp.setGoodsname(goodsname);
    dp.setGoodsnorms(goodsnorms);
    dp.setGoodscode(goodscode);
    try {
      this.logic.saveDeletePersonInfor(dp);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
}
