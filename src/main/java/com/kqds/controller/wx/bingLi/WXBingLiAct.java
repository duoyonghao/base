package com.kqds.controller.wx.bingLi;

import com.kqds.entity.base.KqdsMedicalrecordCz;
import com.kqds.entity.base.KqdsMedicalrecordFz;
import com.kqds.entity.base.KqdsMedicalrecordRestoration;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi2;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.wx.bingLi.WXBingLiLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.bus.BLUtil;
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

@Controller
@RequestMapping({"WXBingLiAct"})
public class WXBingLiAct
{
  private static Logger logger = LoggerFactory.getLogger(WXBingLiAct.class);
  @Autowired
  private WXBingLiLogic logic = new WXBingLiLogic();
  
  @RequestMapping({"/selectPage4ZhongZhi.act"})
  public String selectPage4ZhongZhi(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      String usercode = request.getParameter("usercode");
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      JSONObject data = this.logic.selectPage4ZhongZhi(TableNameUtil.KQDS_MEDICALRECORD, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      String usercode = request.getParameter("usercode");
      
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(usercode)) {
        map.put("usercode", usercode);
      }
      JSONObject data = this.logic.selectPage(TableNameUtil.KQDS_MEDICALRECORD, bp, map);
      YZUtility.DEAL_SUCCESS(data, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String meid = request.getParameter("meid");
      String mtype = request.getParameter("mtype");
      
      Map<String, String> map = new HashMap();
      map.put("meid", meid);
      JSONObject jobj = new JSONObject();
      if ((YZUtility.isNullorEmpty(mtype)) || (YZUtility.isNullorEmpty(meid))) {
        throw new Exception("参数传递错误，参数值不能为空");
      }
      if (mtype.equals(BLUtil.MTYPE_0))
      {
        List<KqdsMedicalrecordCz> cz = (List)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_CZ, map);
        if (cz.size() == 0) {
          throw new Exception("初诊病历内容不存在");
        }
        jobj.put("data", cz.get(0));
      }
      else if (mtype.equals(BLUtil.MTYPE_1))
      {
        List<KqdsMedicalrecordFz> fz = (List)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_FZ, map);
        if (fz.size() == 0) {
          throw new Exception("复诊病历内容不存在");
        }
        jobj.put("data", fz.get(0));
      }
      else if (mtype.equals(BLUtil.MTYPE_2))
      {
        List<KqdsMedicalrecordZhongzhi> zhongzhi = (List)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, map);
        if (zhongzhi.size() == 0) {
          throw new Exception("种植1期病历病历内容不存在");
        }
        jobj.put("data", zhongzhi.get(0));
      }
      else if (mtype.equals(BLUtil.MTYPE_3))
      {
        List<KqdsMedicalrecordReview> chaixian = (List)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, map);
        if (chaixian.size() == 0) {
          throw new Exception("术后拆线病历病历内容不存在");
        }
        jobj.put("data", chaixian.get(0));
      }
      else if (mtype.equals(BLUtil.MTYPE_4))
      {
        List<KqdsMedicalrecordZhongzhi2> erqi = (List)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI2, map);
        if (erqi.size() == 0) {
          throw new Exception("种植2期病历病历内容不存在");
        }
        jobj.put("data", erqi.get(0));
      }
      else if (mtype.equals(BLUtil.MTYPE_5))
      {
        List<KqdsMedicalrecordRestoration> xiufu = (List)this.logic.loadList(TableNameUtil.KQDS_MEDICALRECORD_RESTORATION, map);
        if (xiufu.size() == 0) {
          throw new Exception("种植修复病历病历内容不存在");
        }
        jobj.put("data", xiufu.get(0));
      }
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
