package com.kqds.controller.base.blk;

import com.kqds.entity.base.KqdsBlk;
import com.kqds.entity.base.KqdsBlkZhongzhi2;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi2;
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
@RequestMapping({"KQDS_BLK_Zhongzhi2Act"})
public class KQDS_BLK_Zhongzhi2Act
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_BLK_Zhongzhi2Act.class);
  @Autowired
  private KQDS_BLKLogic logic;
  
  @RequestMapping({"/toZhongZhiErQi_Huifu.act"})
  public ModelAndView toZhongZhiErQi_Huifu(HttpServletRequest request, HttpServletResponse response)
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
    mv.setViewName("/kqdsFront/bingli/zhongzhi2/zhongzhierqi_huifu.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZhongZhiErQi_Detail.act"})
  public ModelAndView toZhongZhiErQi_Detail(HttpServletRequest request, HttpServletResponse response)
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
    mv.setViewName("/kqdsFront/bingli/zhongzhi2/zhongzhierqi_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toZhongZhiErQi_Add.act"})
  public ModelAndView toZhongZhiErQi_Add(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String usercode = request.getParameter("usercode");
    String regno = request.getParameter("regno");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.addObject("regno", regno);
    mv.setViewName("/kqdsFront/bingli/zhongzhi2/zhongzhierqi_add.jsp");
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
      KqdsBlkZhongzhi2 zhongzhi2 = new KqdsBlkZhongzhi2();
      BeanUtils.populate(dp, request.getParameterMap());
      BeanUtils.populate(zhongzhi2, request.getParameterMap());
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
        
        KqdsBlkZhongzhi2 subM = (KqdsBlkZhongzhi2)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_BLK_ZHONGZHI2, subSeqId);
        if (subM == null) {
          throw new Exception("病历内容不存在");
        }
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(ChainUtil.getCurrentOrganization(request));
        this.logic.updateSingleUUID(TableNameUtil.KQDS_BLK, dp);
        
        zhongzhi2.setSeqId(subSeqId);
        this.logic.updateSingleUUID(TableNameUtil.KQDS_BLK_ZHONGZHI2, zhongzhi2);
        
        BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_BLK_ZHONGZHI2, zhongzhi2, TableNameUtil.KQDS_BLK_ZHONGZHI2, request);
      }
      else
      {
        String type = request.getParameter("type");
        String meid = request.getParameter("meid");
        String blname = request.getParameter("blname");
        if (YZUtility.isNullorEmpty(meid))
        {
          dp.setMtype(BLUtil.MTYPE_4);
          dp.setSeqId(YZUtility.getUUID());
          dp.setCreatetime(YZUtility.getCurDateTimeStr());
          dp.setCreateuser(person.getSeqId());
          dp.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);
          
          zhongzhi2.setSeqId(YZUtility.getUUID());
          zhongzhi2.setMeid(dp.getSeqId());
          zhongzhi2.setOrganization(ChainUtil.getCurrentOrganization(request));
          
          zhongzhi2.setCreatetime(YZUtility.getCurDateTimeStr());
          zhongzhi2.setCreateuser(person.getSeqId());
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK_ZHONGZHI2, zhongzhi2);
        }
        else
        {
          KqdsMedicalrecord m = (KqdsMedicalrecord)this.logic.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, meid);
          if (m == null) {
            throw new Exception("病历不存在");
          }
          Map<String, String> map = new HashMap();
          map.put("meid", meid);
          List<KqdsMedicalrecordZhongzhi2> list = (List)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI2, map);
          if ((list == null) || (list.isEmpty())) {
            throw new Exception("病历内容不存在");
          }
          KqdsMedicalrecordZhongzhi2 zhongzhiM = (KqdsMedicalrecordZhongzhi2)list.get(0);
          String mseqId = YZUtility.getUUID();
          dp.setMtype(m.getMtype());
          dp.setBlname(blname);
          dp.setSeqId(mseqId);
          dp.setType(type);
          dp.setCreatetime(YZUtility.getCurDateTimeStr());
          dp.setCreateuser(person.getSeqId());
          dp.setOrganization(ChainUtil.getCurrentOrganization(request));
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK, dp);
          

          zhongzhi2 = (KqdsBlkZhongzhi2)YZUtility.Obj1ToObj2(zhongzhiM, zhongzhi2);
          zhongzhi2.setSeqId(YZUtility.getUUID());
          zhongzhi2.setMeid(dp.getSeqId());
          zhongzhi2.setOrganization(ChainUtil.getCurrentOrganization(request));
          
          zhongzhi2.setCreatetime(YZUtility.getCurDateTimeStr());
          zhongzhi2.setCreateuser(person.getSeqId());
          this.logic.saveSingleUUID(TableNameUtil.KQDS_BLK_ZHONGZHI2, zhongzhi2);
        }
        BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_BLK_ZHONGZHI2, zhongzhi2, TableNameUtil.KQDS_BLK_ZHONGZHI2, request);
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
