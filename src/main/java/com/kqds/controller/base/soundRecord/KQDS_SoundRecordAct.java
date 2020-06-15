package com.kqds.controller.base.soundRecord;

import com.kqds.core.global.YZSysProps;
import com.kqds.service.base.soundRecord.KQDS_SoundRecordLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"KQDS_SoundRecordAct"})
public class KQDS_SoundRecordAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_SoundRecordAct.class);
  @Autowired
  private KQDS_SoundRecordLogic logic;
  
  @RequestMapping({"/toDetail.act"})
  public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/soundRecord/detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String phonenumber = request.getParameter("phonenumber");
      String startDate = request.getParameter("startDate");
      String endDate = request.getParameter("endDate");
      
      String record_file_dir = YZSysProps.getProp(SysParaUtil.RECORD_FILE_DIR);
      

      Map<String, String> map = new HashMap();
      if (!YZUtility.isNullorEmpty(phonenumber)) {
        map.put("phonenumber", phonenumber);
      }
      if ((!YZUtility.isNullorEmpty(startDate)) && (!YZUtility.isNullorEmpty(endDate)))
      {
        map.put("starttime", startDate + ConstUtil.TIME_START);
        map.put("endtime", endDate + ConstUtil.TIME_END);
        
        String[] dateArray = YZUtility.getBetweenDatesArray(startDate, endDate);
        for (String date : dateArray) {
          this.logic.updateRecordDataByDate(record_file_dir, date);
        }
      }
      else
      {
        if (!YZUtility.isNullorEmpty(startDate))
        {
          map.put("starttime", startDate + ConstUtil.TIME_START);
          this.logic.updateRecordDataByDate(record_file_dir, startDate);
        }
        if (!YZUtility.isNullorEmpty(endDate))
        {
          map.put("endtime", startDate + ConstUtil.TIME_END);
          this.logic.updateRecordDataByDate(record_file_dir, endDate);
        }
      }
      List<JSONObject> list = this.logic.selectList(TableNameUtil.KQDS_SOUND_RECORD, map);
      YZUtility.RETURN_LIST(list, response, logger);
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
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("seqId不能为空");
      }
      JSONObject en = this.logic.selectDetail(TableNameUtil.KQDS_SOUND_RECORD, seqId);
      if (en == null) {
        throw new Exception("数据不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", en);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
