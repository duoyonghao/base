package com.kqds.controller.base.soundRecord;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.util.base.util.HttpRequestUtils;
import com.kqds.util.sys.YZUtility;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
@RequestMapping({"KQDS_SoundRecordAct4DJ"})
public class KQDS_SoundRecordAct4DJ
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_SoundRecordAct4DJ.class);
  @Autowired
  private KQDS_UserDocumentLogic userlogic;
  
  @RequestMapping({"/toDetail4dj.act"})
  public ModelAndView toDetail4dj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/soundRecord/detail4dj.jsp");
    return mv;
  }
  
  @RequestMapping({"/toListchannel4dj.act"})
  public ModelAndView toListchannel4dj(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/soundRecord/listchannel4dj.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectList.act"})
  public String selectList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      String startdate = request.getParameter("startdate");
      String enddate = request.getParameter("enddate");
      String localcode = request.getParameter("localcode");
      String channel = request.getParameter("channel");
      String remotecode = request.getParameter("remotecode");
      String direction = request.getParameter("direction");
      String maxtalklong = request.getParameter("maxtalklong");
      String mintalklong = request.getParameter("mintalklong");
      String pagestart = request.getParameter("pagestart");
      int pagelimit = bp.getLimit();
      
      int offset = bp.getOffset();
      pagestart = String.valueOf(offset / pagelimit);
      if (YZUtility.isNullorEmpty(startdate)) {
        throw new Exception("开始日期不能为空");
      }
      if (YZUtility.isNullorEmpty(enddate)) {
        throw new Exception("结束日期不能为空");
      }
      if (YZUtility.isNullorEmpty(pagestart)) {
        pagestart = "0";
      }
      JSONObject jsonParam = new JSONObject();
      jsonParam.put("action", "QueryTalklog");
      jsonParam.put("rpccallkey", "VocLogRpcKey");
      jsonParam.put("startdate", startdate);
      jsonParam.put("enddate", enddate);
      jsonParam.put("localcode", localcode);
      jsonParam.put("channel", channel);
      jsonParam.put("localname", "");
      jsonParam.put("localdepart", "");
      jsonParam.put("remotecode", remotecode);
      jsonParam.put("direction", direction);
      jsonParam.put("maxtalklong", maxtalklong);
      jsonParam.put("mintalklong", mintalklong);
      jsonParam.put("tags", "");
      jsonParam.put("pagestart", pagestart);
      jsonParam.put("pagelimit", Integer.valueOf(pagelimit));
      
      String queryUrl = getQueryUrl(jsonParam);
      
      JSONObject httpResult = HttpRequestUtils.httpPostJson("http://192.168.0.200/friextra/unite_call.php", queryUrl, false);
      if ((httpResult.has("result")) && ("ok".equals(httpResult.getString("result"))))
      {
        List<JSONObject> list = (List)httpResult.get("datas");
        
        getNameAndCode4List(list, request);
        JSONObject jobj = new JSONObject();
        jobj.put("total", httpResult.getString("totalcount"));
        jobj.put("rows", list);
        YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
      }
      else
      {
        throw new Exception("请求录音数据失败，请确保网络/设备正常！");
      }
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectList4Grxx.act"})
  public String selectList4Grxx(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      String startdate = request.getParameter("startdate");
      String enddate = request.getParameter("enddate");
      String usercode = request.getParameter("usercode");
      String pagestart = request.getParameter("pagestart");
      int pagelimit = bp.getLimit();
      int offset = bp.getOffset();
      pagestart = String.valueOf(offset / pagelimit);
      if (YZUtility.isNullorEmpty(startdate)) {
        throw new Exception("开始日期不能为空");
      }
      if (YZUtility.isNullorEmpty(enddate)) {
        throw new Exception("结束日期不能为空");
      }
      if (YZUtility.isNullorEmpty(pagestart)) {
        pagestart = "0";
      }
      JSONObject userinfo = this.userlogic.getUserInfoByUserCode(usercode);
      if (userinfo == null) {
        throw new Exception("患者不存在");
      }
      String phonenumber1 = userinfo.getString("phonenumber1");
      String phonenumber2 = userinfo.getString("phonenumber2");
      
      JSONObject jsonParam = new JSONObject();
      jsonParam.put("action", "QueryTalklog");
      jsonParam.put("rpccallkey", "VocLogRpcKey");
      jsonParam.put("startdate", startdate);
      jsonParam.put("enddate", enddate);
      jsonParam.put("localcode", "");
      jsonParam.put("channel", "");
      jsonParam.put("localname", "");
      jsonParam.put("localdepart", "");
      jsonParam.put("direction", "");
      jsonParam.put("maxtalklong", "");
      jsonParam.put("mintalklong", "");
      jsonParam.put("tags", "");
      jsonParam.put("pagestart", pagestart);
      jsonParam.put("pagelimit", Integer.valueOf(pagelimit));
      
      int total = 0;
      List<JSONObject> totalList = new ArrayList();
      if (YZUtility.isNotNullOrEmpty(phonenumber1))
      {
        jsonParam.put("remotecode", phonenumber1);
        String queryUrl = getQueryUrl(jsonParam);
        JSONObject httpResult = HttpRequestUtils.httpPostJson("http://192.168.0.200/friextra/unite_call.php", queryUrl, false);
        if ((httpResult.has("result")) && ("ok".equals(httpResult.getString("result"))))
        {
          List<JSONObject> list = (List)httpResult.get("datas");
          
          getNameAndCode4List(list, request);
          totalList.addAll(list);
          total += httpResult.getInt("totalcount");
        }
        else
        {
          throw new Exception("请求录音数据失败，请确保网络/设备正常！");
        }
      }
      if (YZUtility.isNotNullOrEmpty(phonenumber2))
      {
        jsonParam.put("remotecode", phonenumber2);
        String queryUrl = getQueryUrl(jsonParam);
        JSONObject httpResult = HttpRequestUtils.httpPostJson("http://192.168.0.200/friextra/unite_call.php", queryUrl, false);
        if ((httpResult.has("result")) && ("ok".equals(httpResult.getString("result"))))
        {
          List<JSONObject> list = (List)httpResult.get("datas");
          
          getNameAndCode4List(list, request);
          totalList.addAll(list);
          total += httpResult.getInt("totalcount");
        }
        else
        {
          throw new Exception("请求录音数据失败，请确保网络/设备正常！");
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("total", Integer.valueOf(total));
      jobj.put("rows", totalList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  private String getQueryUrl(JSONObject jsonParam)
  {
    StringBuffer queryBf = new StringBuffer();
    
    queryBf.append("action=" + jsonParam.getString("action"));
    queryBf.append("&rpccallkey=" + jsonParam.getString("rpccallkey"));
    queryBf.append("&startdate=" + jsonParam.getString("startdate"));
    queryBf.append("&enddate=" + jsonParam.getString("enddate"));
    queryBf.append("&localcode=" + jsonParam.getString("localcode"));
    queryBf.append("&channel=" + jsonParam.getString("channel"));
    queryBf.append("&localname=" + jsonParam.getString("localname"));
    queryBf.append("&localdepart=" + jsonParam.getString("localdepart"));
    queryBf.append("&remotecode=" + jsonParam.getString("remotecode"));
    queryBf.append("&direction=" + jsonParam.getString("direction"));
    queryBf.append("&maxtalklong=" + jsonParam.getString("maxtalklong"));
    queryBf.append("&mintalklong=" + jsonParam.getString("mintalklong"));
    queryBf.append("&tags=" + jsonParam.getString("tags"));
    queryBf.append("&pagestart=" + jsonParam.getString("pagestart"));
    queryBf.append("&pagelimit=" + jsonParam.getString("pagelimit"));
    
    return queryBf.toString();
  }
  
  private void getNameAndCode4List(List<JSONObject> list, HttpServletRequest request)
    throws SQLException, Exception
  {
    Set<String> numSet = new HashSet();
    String phonenumber;
    for (JSONObject rs : list)
    {
      phonenumber = rs.getString("remotecode");
      if (YZUtility.isNotNullOrEmpty(phonenumber)) {
        numSet.add(phonenumber);
      }
    }
    Map<String, JSONObject> numberInfoMap = new HashMap();
    for (String phonenumber : numSet)
    {
      JSONObject json = this.userlogic.getSingUserNameAndCodeByPhoneNumber(phonenumber);
      if ((json != null) && (json.containsKey("usercode")) && (YZUtility.isNotNullOrEmpty(json.getString("usercode")))) {
        numberInfoMap.put(phonenumber, json);
      }
    }
    for (JSONObject info : list)
    {
      String phonenumber = info.getString("remotecode");
      if (!YZUtility.isNullorEmpty(phonenumber))
      {
        JSONObject userjson = (JSONObject)numberInfoMap.get(phonenumber);
        if (userjson != null)
        {
          info.put("usercode", userjson.getString("usercode"));
          info.put("username", userjson.getString("username"));
        }
      }
    }
  }
  
  @RequestMapping({"/selectChannelList.act"})
  public String selectChannelList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      StringBuffer queryBf = new StringBuffer();
      JSONObject jsonParam = new JSONObject();
      jsonParam.put("action", "QueryChannel");
      jsonParam.put("rpccallkey", "VocLogRpcKey");
      jsonParam.put("localcode", "");
      jsonParam.put("channel", "");
      jsonParam.put("localname", "");
      jsonParam.put("localdepart", "");
      
      queryBf.append("action=" + jsonParam.getString("action"));
      queryBf.append("&rpccallkey=" + jsonParam.getString("rpccallkey"));
      queryBf.append("&localcode=" + jsonParam.getString("localcode"));
      queryBf.append("&channel=" + jsonParam.getString("channel"));
      queryBf.append("&localname=" + jsonParam.getString("localname"));
      queryBf.append("&localdepart=" + jsonParam.getString("localdepart"));
      
      JSONObject httpResult = HttpRequestUtils.httpPostJson("http://192.168.0.200/friextra/unite_call.php", queryBf.toString(), false);
      if ((httpResult.has("result")) && ("ok".equals(httpResult.getString("result")))) {
        YZUtility.RETURN_LIST((List)httpResult.get("datas"), response, logger);
      } else {
        throw new Exception("查询录音通道数据失败，请确保网络/设备正常！");
      }
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deviceDetail.act"})
  public String deviceDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      StringBuffer queryBf = new StringBuffer();
      JSONObject jsonParam = new JSONObject();
      jsonParam.put("action", "QueryDevice");
      jsonParam.put("rpccallkey", "VocLogRpcKey");
      
      queryBf.append("action=" + jsonParam.getString("action"));
      queryBf.append("&rpccallkey=" + jsonParam.getString("rpccallkey"));
      
      JSONObject httpResult = HttpRequestUtils.httpPostJson("http://192.168.0.200/friextra/unite_call.php", queryBf.toString(), false);
      if ((!httpResult.has("result")) || (!"ok".equals(httpResult.getString("result")))) {
        throw new Exception("查询录音通道数据失败，请确保网络/设备正常！");
      }
      YZUtility.DEAL_SUCCESS(httpResult, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
