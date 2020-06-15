package com.kqds.controller.base.blk;

import com.kqds.entity.base.KqdsBlk;
import com.kqds.entity.base.KqdsBlkRestoration;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordRestoration;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.blk.KQDS_BLKLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_BLK_XiuFuAct"})
public class KQDS_BLK_XiuFuAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_BLK_XiuFuAct.class);
  @Autowired
  private KQDS_BLKLogic logic;
  
  @RequestMapping({"/toZhongZhi_XiuFu_Huifu.act"})
  public ModelAndView toZhongZhi_XiuFu_Huifu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String mtype = request.getParameter("mtype");
    String type = request.getParameter("type");
    String editFlag = request.getParameter("editFlag");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("mtype", mtype);
    mv.addObject("type", type);
    mv.addObject("editFlag", editFlag);
    mv.setViewName("/kqdsFront/bingli/xiufu/zhongzhi_xiufu_huifu.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZhongZhi_XiuFu_Detail.act"})
  public ModelAndView toZhongZhi_XiuFu_Detail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String mtype = request.getParameter("mtype");
    String type = request.getParameter("type");
    String detailFlag = request.getParameter("detailFlag");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("mtype", mtype);
    mv.addObject("type", type);
    mv.addObject("detailFlag", detailFlag);
    mv.setViewName("/kqdsFront/bingli/xiufu/zhongzhi_xiufu_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZhongZhi_XiuFu_Add.act"})
  public ModelAndView toZhongZhi_XiuFu_Add(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/bingli/xiufu/zhongzhi_xiufu_add.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      KqdsBlk dp = new KqdsBlk();
      KqdsBlkRestoration xiufu = new KqdsBlkRestoration();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(xiufu, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String subSeqId = request.getParameter("subSeqId");
      if (!YZUtility.isNullorEmpty(seqId))
      {
        if (YZUtility.isNullorEmpty(subSeqId)) {
          throw new Exception("病历内容表主键不能为空");
        }
        KqdsBlk m = (KqdsBlk)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK, seqId);
        if (m == null) {
          throw new Exception("病历不存在");
        }
        dp.setMtype(m.getMtype());
        
        KqdsBlkRestoration subM = (KqdsBlkRestoration)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK_RESTORATION, subSeqId);
        if (subM == null) {
          throw new Exception("病历内容不存在");
        }
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_BLK, dp);
        
        xiufu.setSeqId(subSeqId);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_BLK_RESTORATION, xiufu);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_BLK_RESTORATION, xiufu, TableNameUtil.KQDS_BLK_RESTORATION, request);
      }
      else
      {
        String type = request.getParameter("type");
        String meid = request.getParameter("meid");
        String blname = request.getParameter("blname");
        if (YZUtility.isNullorEmpty(meid))
        {
          dp.setMtype(BLUtil.MTYPE_5);
          dp.setSeqId(YZUtility.getUUID());
          dp.setCreatetime(YZUtility.getCurDateTimeStr());
          dp.setCreateuser(person.getSeqId());
          dp.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);
          
          xiufu.setSeqId(YZUtility.getUUID());
          xiufu.setMeid(dp.getSeqId());
          xiufu.setOrganization(ChainUtil.getCurrentOrganization(request));
          
          xiufu.setCreatetime(YZUtility.getCurDateTimeStr());
          xiufu.setCreateuser(person.getSeqId());
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK_RESTORATION, xiufu);
        }
        else
        {
          KqdsMedicalrecord m = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, meid);
          if (m == null) {
            throw new Exception("病历不存在");
          }
          Map<String, String> map = new HashMap();
          map.put("meid", meid);
          List<KqdsMedicalrecordRestoration> list = (List)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_RESTORATION, map);
          if ((list == null) || (list.isEmpty())) {
            throw new Exception("病历内容不存在");
          }
          KqdsMedicalrecordRestoration zhongzhiM = (KqdsMedicalrecordRestoration)list.get(0);
          String mseqId = YZUtility.getUUID();
          dp.setMtype(m.getMtype());
          dp.setBlname(blname);
          dp.setSeqId(mseqId);
          dp.setType(type);
          dp.setCreatetime(YZUtility.getCurDateTimeStr());
          dp.setCreateuser(person.getSeqId());
          dp.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);
          

          xiufu = (KqdsBlkRestoration)YZUtility.Obj1ToObj2(zhongzhiM, xiufu);
          xiufu.setSeqId(YZUtility.getUUID());
          xiufu.setMeid(dp.getSeqId());
          xiufu.setOrganization(ChainUtil.getCurrentOrganization(request));
          
          xiufu.setCreatetime(YZUtility.getCurDateTimeStr());
          xiufu.setCreateuser(person.getSeqId());
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK_RESTORATION, xiufu);
        }
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLK_RESTORATION, xiufu, TableNameUtil.KQDS_BLK_RESTORATION, request);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "0");
      jobj.put("id", dp.getSeqId());
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, logger);
    }
    return null;
  }
}
