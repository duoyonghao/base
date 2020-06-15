package com.hudh.sjtj.controller;

import com.hudh.sjtj.service.IDataAnalysisService;
import com.kqds.util.sys.YZUtility;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@RequestMapping({"HUDH_DataAnalysisAct"})
public class HUDH_DataAnalysisAct {
  private static Logger logger = LoggerFactory.getLogger(HUDH_DataAnalysisAct.class);
  
  @Autowired
  private IDataAnalysisService analysisService;
  
  @RequestMapping({"/toBb_BaseDataDay.act"})
  public ModelAndView toBb_BaseDataDay(HttpServletRequest request, HttpServletResponse response) {
    String timeMonth = request.getParameter("timeMonth");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    ModelAndView mv = new ModelAndView();
    mv.addObject("timeMonth", timeMonth);
    mv.addObject("qxname", qxname);
    mv.addObject("deptCategory", deptCategory);
    mv.setViewName("/kqdsFront/index/zxtj/baseDataDay.jsp");
    return mv;
  }
  
  @RequestMapping({"/findCJStatisticsByMonth.act"})
  public String findCJStatisticsByMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    String startTime = request.getParameter("starttime");
    String endTime = request.getParameter("endtime");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    try {
      List<JSONObject> list = new ArrayList<>();
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      if (!YZUtility.isNullorEmpty(startTime))
        map.put("startTime", startTime); 
      if (!YZUtility.isNullorEmpty(endTime))
        map.put("endTime", endTime); 
      int hjczAllNums = 0;
      int hjczCjNum = 0;
      double hjczPaymoney = 0.0D;
      double hjczJcfmoney = 0.0D;
      double hjczYjjmoney = 0.0D;
      int hjfzAllNums = 0;
      int hjfzCjNum = 0;
      double hjfzPaymoney = 0.0D;
      double hjfzJcfmoney = 0.0D;
      double hjfzYjjmoney = 0.0D;
      int hjzxfAllNums = 0;
      int hjzxfCjNum = 0;
      double hjzxfPaymoney = 0.0D;
      double hjCmoney = 0.0D;
      double hjzxfJcfmoney = 0.0D;
      double hjzxfYjjmoney = 0.0D;
      double hjTcmoney = 0.0D;
      double hjDrugsmoney = 0.0D;
      if (startTime.length() == 10 || endTime.length() == 10) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        if (YZUtility.isNullorEmpty(endTime) && !YZUtility.isNullorEmpty(startTime)) {
          endTime = time;
        } else if (!YZUtility.isNullorEmpty(endTime) && YZUtility.isNullorEmpty(startTime)) {
          Long time1 = Long.valueOf(formatter.parse(endTime).getTime());
          Long time2 = Long.valueOf(formatter.parse(time).getTime());
          if (time1.longValue() >= time2.longValue()) {
            startTime = time;
          } else {
            throw new Exception("请选择查询起始时间！");
          } 
        } 
        if (startTime.equals(endTime)) {
          String sss = "ISNULL(sum(case when  datepart(day,createtime)=" + Integer.parseInt(startTime.substring(8, 10)) + " then 1 else 0 end),0) as '" + startTime + "'";
          map.put("year", startTime.substring(0, 4));
          map.put("month", startTime.substring(5, 7));
          map.put("code", sss);
          map.put("startday", startTime.substring(8, 10));
          map.put("endday", startTime.substring(8, 10));
          map.put("years", "(year(r.createtime)=" + startTime.substring(0, 4) + ")");
          map.put("months", "(month(r.createtime)=" + startTime.substring(5, 7) + ")");
          map.put("costyears", "(year(cost.sftime)=" + startTime.substring(0, 4) + ")");
          map.put("costmonths", "(month(cost.sftime)=" + startTime.substring(5, 7) + ")");
          list = this.analysisService.findCJStatisticsByDay(request, map);
          for (JSONObject json : list) {
            if (json.getString("name").equals("合计")) {
              hjczAllNums += Integer.parseInt(json.getString("czperson"));
              hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
              hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
              hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
              hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
              hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
              hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
              hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
              hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
              hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
              hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
              hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
              hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
              hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
              hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
              hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
              hjCmoney += Double.parseDouble(json.getString("cmoney"));
              hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
            } 
          } 
        } else if (startTime.substring(0, 4).equals(endTime.substring(0, 4))) {
          if (startTime.substring(5, 7).equals(endTime.substring(5, 7))) {
            StringBuffer sss = new StringBuffer();
            for (int j = Integer.parseInt(startTime.substring(8, 10)); j <= Integer.parseInt(endTime.substring(8, 10)); j++) {
              if (j == Integer.parseInt(endTime.substring(8, 10))) {
                sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日'");
              } else {
                sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日',");
              } 
            } 
            map.put("year", startTime.substring(0, 4));
            map.put("month", startTime.substring(5, 7));
            map.put("code", sss.toString());
            map.put("startday", startTime.substring(8, 10));
            map.put("endday", endTime.substring(8, 10));
            map.put("years", "(year(r.createtime)=" + startTime.substring(0, 4) + ")");
            map.put("months", "(month(r.createtime)=" + startTime.substring(5, 7) + ")");
            map.put("costyears", "(year(cost.sftime)=" + startTime.substring(0, 4) + ")");
            map.put("costmonths", "(month(cost.sftime)=" + startTime.substring(5, 7) + ")");
            list = this.analysisService.findCJStatisticsByDay(request, map);
            for (JSONObject json : list) {
              if (json.getString("name").equals("合计")) {
                hjczAllNums += Integer.parseInt(json.getString("czperson"));
                hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
                hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
                hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
                hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
                hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
                hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
                hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
                hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
                hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
                hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
                hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
                hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
                hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
                hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
                hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
                hjCmoney += Double.parseDouble(json.getString("cmoney"));
                hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
              } 
            } 
          } else {
            for (int i = Integer.parseInt(startTime.substring(5, 7)); i <= Integer.parseInt(endTime.substring(5, 7)); i++) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
              Calendar calendar = Calendar.getInstance();
              int s = 0;
              int k = 0;
              if (i == Integer.parseInt(startTime.substring(5, 7))) {
                calendar.setTime(sdf.parse(startTime.substring(0, 7)));
                s = calendar.getActualMaximum(5);
                k = Integer.parseInt(startTime.substring(8, 10));
              } else if (i > Integer.parseInt(startTime.substring(5, 7)) && i < Integer.parseInt(endTime.substring(5, 7))) {
                calendar.setTime(sdf.parse(String.valueOf(startTime.substring(0, 4)) + "-" + i));
                s = calendar.getActualMaximum(5);
                k = 1;
              } else {
                s = Integer.parseInt(endTime.substring(8, 10));
                k = 1;
              } 
              StringBuffer sss = new StringBuffer();
              for (int j = k; j <= s; j++) {
                if (j == s) {
                  sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日'");
                } else {
                  sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日',");
                } 
              } 
              map.put("year", startTime.substring(0, 4));
              map.put("month", (new StringBuilder(String.valueOf(i))).toString());
              map.put("code", sss.toString());
              map.put("startday", (new StringBuilder(String.valueOf(k))).toString());
              map.put("endday", (new StringBuilder(String.valueOf(s))).toString());
              map.put("years", "(year(r.createtime)=" + startTime.substring(0, 4) + ")");
              map.put("months", "(month(r.createtime)=" + i + ")");
              map.put("costyears", "(year(cost.sftime)=" + startTime.substring(0, 4) + ")");
              map.put("costmonths", "(month(cost.sftime)=" + i + ")");
              List<JSONObject> list1 = this.analysisService.findCJStatisticsByDay(request, map);
              list.addAll(list1);
              for (JSONObject json : list1) {
                if (json.getString("name").equals("合计")) {
                  hjczAllNums += Integer.parseInt(json.getString("czperson"));
                  hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
                  hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
                  hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
                  hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
                  hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
                  hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
                  hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
                  hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
                  hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
                  hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
                  hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
                  hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
                  hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
                  hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
                  hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
                  hjCmoney += Double.parseDouble(json.getString("cmoney"));
                  hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
                } 
              } 
            } 
          } 
        } else {
          for (int k = Integer.parseInt(startTime.substring(0, 4)); k <= Integer.parseInt(endTime.substring(0, 4)); k++) {
            if (k == Integer.parseInt(startTime.substring(0, 4))) {
              for (int i = Integer.parseInt(startTime.substring(5, 7)); i <= 12; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar calendar = Calendar.getInstance();
                int s = 0;
                int l = 0;
                if (i == Integer.parseInt(startTime.substring(5, 7))) {
                  calendar.setTime(sdf.parse(startTime.substring(0, 7)));
                  s = calendar.getActualMaximum(5);
                  l = Integer.parseInt(startTime.substring(8, 10));
                } else if (i > Integer.parseInt(startTime.substring(5, 7)) && i <= 12) {
                  calendar.setTime(sdf.parse(String.valueOf(startTime.substring(0, 4)) + "-" + i));
                  s = calendar.getActualMaximum(5);
                  l = 1;
                } 
                StringBuffer sss = new StringBuffer();
                for (int j = l; j <= s; j++) {
                  if (j == s) {
                    sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日'");
                  } else {
                    sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日',");
                  } 
                } 
                map.put("year", startTime.substring(0, 4));
                map.put("month", (new StringBuilder(String.valueOf(i))).toString());
                map.put("code", sss.toString());
                map.put("startday", (new StringBuilder(String.valueOf(l))).toString());
                map.put("endday", (new StringBuilder(String.valueOf(s))).toString());
                map.put("years", "(year(r.createtime)=" + startTime.substring(0, 4) + ")");
                map.put("months", "(month(r.createtime)=" + i + ")");
                map.put("costyears", "(year(cost.sftime)=" + startTime.substring(0, 4) + ")");
                map.put("costmonths", "(month(cost.sftime)=" + i + ")");
                List<JSONObject> list1 = this.analysisService.findCJStatisticsByDay(request, map);
                list.addAll(list1);
                for (JSONObject json : list1) {
                  if (json.getString("name").equals("合计")) {
                    hjczAllNums += Integer.parseInt(json.getString("czperson"));
                    hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
                    hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
                    hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
                    hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
                    hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
                    hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
                    hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
                    hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
                    hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
                    hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
                    hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
                    hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
                    hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
                    hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
                    hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
                    hjCmoney += Double.parseDouble(json.getString("cmoney"));
                    hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
                  } 
                } 
              } 
            } else if (k > Integer.parseInt(startTime.substring(0, 4)) && k < Integer.parseInt(endTime.substring(0, 4))) {
              for (int i = 1; i <= 12; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(String.valueOf(Integer.parseInt(startTime.substring(0, 4)) + k) + "-" + i));
                int s = calendar.getActualMaximum(5);
                int l = 1;
                StringBuffer sss = new StringBuffer();
                for (int j = l; j <= s; j++) {
                  if (j == s) {
                    sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日'");
                  } else {
                    sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日',");
                  } 
                } 
                map.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(startTime.substring(0, 4)) + k))).toString());
                map.put("month", (new StringBuilder(String.valueOf(i))).toString());
                map.put("code", sss.toString());
                map.put("startday", (new StringBuilder(String.valueOf(l))).toString());
                map.put("endday", (new StringBuilder(String.valueOf(s))).toString());
                map.put("years", "(year(r.createtime)=" + (Integer.parseInt(startTime.substring(0, 4)) + k) + ")");
                map.put("months", "(month(r.createtime)=" + i + ")");
                map.put("costyears", "(year(cost.sftime)=" + (Integer.parseInt(startTime.substring(0, 4)) + k) + ")");
                map.put("costmonths", "(month(cost.sftime)=" + i + ")");
                List<JSONObject> list1 = this.analysisService.findCJStatisticsByDay(request, map);
                list.addAll(list1);
                for (JSONObject json : list1) {
                  if (json.getString("name").equals("合计")) {
                    hjczAllNums += Integer.parseInt(json.getString("czperson"));
                    hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
                    hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
                    hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
                    hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
                    hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
                    hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
                    hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
                    hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
                    hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
                    hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
                    hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
                    hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
                    hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
                    hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
                    hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
                    hjCmoney += Double.parseDouble(json.getString("cmoney"));
                    hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
                  } 
                } 
              } 
            } else {
              for (int i = 1; i <= Integer.parseInt(endTime.substring(5, 7)); i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(String.valueOf(endTime.substring(0, 4)) + "-" + i));
                int s = 0;
                if (i == Integer.parseInt(endTime.substring(5, 7))) {
                  s = Integer.parseInt(endTime.substring(8, 10));
                } else {
                  s = calendar.getActualMaximum(5);
                } 
                int l = 1;
                StringBuffer sss = new StringBuffer();
                for (int j = l; j <= s; j++) {
                  if (j == s) {
                    sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日'");
                  } else {
                    sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日',");
                  } 
                } 
                map.put("year", endTime.substring(0, 4));
                map.put("month", (new StringBuilder(String.valueOf(i))).toString());
                map.put("code", sss.toString());
                map.put("startday", (new StringBuilder(String.valueOf(l))).toString());
                map.put("endday", (new StringBuilder(String.valueOf(s))).toString());
                map.put("years", "(year(r.createtime)=" + endTime.substring(0, 4) + ")");
                map.put("months", "(month(r.createtime)=" + i + ")");
                map.put("costyears", "(year(cost.sftime)=" + endTime.substring(0, 4) + ")");
                map.put("costmonths", "(month(cost.sftime)=" + i + ")");
                List<JSONObject> list1 = this.analysisService.findCJStatisticsByDay(request, map);
                list.addAll(list1);
                for (JSONObject json : list1) {
                  if (json.getString("name").equals("合计")) {
                    hjczAllNums += Integer.parseInt(json.getString("czperson"));
                    hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
                    hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
                    hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
                    hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
                    hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
                    hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
                    hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
                    hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
                    hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
                    hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
                    hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
                    hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
                    hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
                    hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
                    hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
                    hjCmoney += Double.parseDouble(json.getString("cmoney"));
                    hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
                  } 
                } 
              } 
            } 
          } 
        } 
      } else if (startTime.length() == 7 || endTime.length() == 7) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        if (YZUtility.isNullorEmpty(endTime) && !YZUtility.isNullorEmpty(startTime)) {
          endTime = time;
        } else if (!YZUtility.isNullorEmpty(endTime) && YZUtility.isNullorEmpty(startTime)) {
          Long time1 = Long.valueOf(formatter.parse(endTime).getTime());
          Long time2 = Long.valueOf(formatter.parse(time).getTime());
          if (time1.longValue() >= time2.longValue()) {
            startTime = time;
          } else {
            throw new Exception("请选择查询起始时间！");
          } 
        } 
        if (Integer.parseInt(startTime.substring(0, 4)) < Integer.parseInt(endTime.substring(0, 4))) {
          for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); i++) {
            if (i == Integer.parseInt(startTime.substring(0, 4))) {
              StringBuffer sss = new StringBuffer();
              for (int j = Integer.parseInt(startTime.substring(5, 7)); j < 13; j++) {
                if (j == 12) {
                  sss.append("ISNULL(sum(case when  datepart(month,createtime)=12 then 1 else 0 end),0) as '12月'");
                } else {
                  sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "月',");
                } 
              } 
              map.put("year", (new StringBuilder(String.valueOf(i))).toString());
              map.put("code", sss.toString());
              map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(startTime.substring(5, 7))))).toString());
              map.put("endmonth", "12");
              map.put("years", "(year(r.createtime)=" + i + ")");
              map.put("costyears", "(year(cost.sftime)=" + i + ")");
              List<JSONObject> list1 = this.analysisService.findCJStatisticsByMonth(request, map);
              list.addAll(list1);
              for (JSONObject json : list1) {
                if (json.getString("name").equals("合计")) {
                  hjczAllNums += Integer.parseInt(json.getString("czperson"));
                  hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
                  hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
                  hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
                  hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
                  hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
                  hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
                  hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
                  hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
                  hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
                  hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
                  hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
                  hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
                  hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
                  hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
                  hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
                  hjCmoney += Double.parseDouble(json.getString("cmoney"));
                  hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
                } 
              } 
            } else if (i > Integer.parseInt(startTime.substring(0, 4)) && i < Integer.parseInt(endTime.substring(0, 4))) {
              StringBuffer sss = new StringBuffer();
              for (int j = 1; j <= 12; j++) {
                if (j == 12) {
                  sss.append("ISNULL(sum(case when  datepart(month,createtime)=12 then 1 else 0 end),0) as '12月'");
                } else {
                  sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "月',");
                } 
              } 
              map.put("year", (new StringBuilder(String.valueOf(i))).toString());
              map.put("code", sss.toString());
              map.put("startmonth", "1");
              map.put("endmonth", "12");
              map.put("years", "(year(r.createtime)=" + i + ")");
              map.put("costyears", "(year(cost.sftime)=" + i + ")");
              List<JSONObject> list2 = this.analysisService.findCJStatisticsByMonth(request, map);
              list.addAll(list2);
              for (JSONObject json : list2) {
                if (json.getString("name").equals("合计")) {
                  hjczAllNums += Integer.parseInt(json.getString("czperson"));
                  hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
                  hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
                  hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
                  hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
                  hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
                  hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
                  hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
                  hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
                  hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
                  hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
                  hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
                  hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
                  hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
                  hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
                  hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
                  hjCmoney += Double.parseDouble(json.getString("cmoney"));
                  hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
                } 
              } 
            } else if (i == Integer.parseInt(endTime.substring(0, 4))) {
              StringBuffer sss = new StringBuffer();
              for (int j = 1; j <= Integer.parseInt(endTime.substring(5, 7)); j++) {
                if (j == Integer.parseInt(endTime.substring(5, 7))) {
                  sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "月'");
                } else {
                  sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "月',");
                } 
              } 
              map.put("year", (new StringBuilder(String.valueOf(i))).toString());
              map.put("code", sss.toString());
              map.put("startmonth", "1");
              map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endTime.substring(5, 7))))).toString());
              map.put("years", "(year(r.createtime)=" + i + ")");
              map.put("costyears", "(year(cost.sftime)=" + i + ")");
              List<JSONObject> list3 = this.analysisService.findCJStatisticsByMonth(request, map);
              list.addAll(list3);
              for (JSONObject json : list3) {
                if (json.getString("name").equals("合计")) {
                  hjczAllNums += Integer.parseInt(json.getString("czperson"));
                  hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
                  hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
                  hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
                  hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
                  hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
                  hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
                  hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
                  hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
                  hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
                  hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
                  hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
                  hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
                  hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
                  hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
                  hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
                  hjCmoney += Double.parseDouble(json.getString("cmoney"));
                  hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
                } 
              } 
            } 
          } 
        } else if (Integer.parseInt(startTime.substring(5, 7)) < Integer.parseInt(endTime.substring(5, 7))) {
          StringBuffer sss = new StringBuffer();
          int aa = 0;
          for (int j = Integer.parseInt(startTime.substring(5, 7)); j <= Integer.parseInt(endTime.substring(5, 7)); j++) {
            if (j == Integer.parseInt(endTime.substring(5, 7))) {
              sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + Integer.parseInt(endTime.substring(5, 7)) + " then 1 else 0 end),0) as '" + Integer.parseInt(endTime.substring(5, 7)) + "月'");
            } else {
              sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + (Integer.parseInt(startTime.substring(5, 7)) + aa) + " then 1 else 0 end),0) as '" + (Integer.parseInt(startTime.substring(5, 7)) + aa) + "月',");
            } 
            aa++;
          } 
          map.put("year", endTime.substring(0, 4));
          map.put("code", sss.toString());
          map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(startTime.substring(5, 7))))).toString());
          map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endTime.substring(5, 7))))).toString());
          map.put("years", "(year(r.createtime)=" + endTime.substring(0, 4) + ")");
          map.put("costyears", "(year(cost.sftime)=" + endTime.substring(0, 4) + ")");
          list = this.analysisService.findCJStatisticsByMonth(request, map);
          for (JSONObject json : list) {
            if (json.getString("name").equals("合计")) {
              hjczAllNums += Integer.parseInt(json.getString("czperson"));
              hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
              hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
              hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
              hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
              hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
              hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
              hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
              hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
              hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
              hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
              hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
              hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
              hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
              hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
              hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
              hjCmoney += Double.parseDouble(json.getString("cmoney"));
              hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
            } 
          } 
        } else {
          map.put("year", (new StringBuilder(String.valueOf(endTime.substring(0, 4)))).toString());
          map.put("code", "ISNULL(sum(case when  datepart(month,createtime)=" + Integer.parseInt(endTime.substring(5, 7)) + " then 1 else 0 end),0) as '" + Integer.parseInt(endTime.substring(5, 7)) + "月'");
          map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(startTime.substring(5, 7))))).toString());
          map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endTime.substring(5, 7))))).toString());
          map.put("years", "(year(r.createtime)=" + endTime.substring(0, 4) + ")");
          map.put("costyears", "(year(cost.sftime)=" + endTime.substring(0, 4) + ")");
          list = this.analysisService.findCJStatisticsByMonth(request, map);
          for (JSONObject json : list) {
            if (json.getString("name").equals("合计")) {
              hjczAllNums += Integer.parseInt(json.getString("czperson"));
              hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
              hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
              hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
              hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
              hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
              hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
              hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
              hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
              hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
              hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
              hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
              hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
              hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
              hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
              hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
              hjCmoney += Double.parseDouble(json.getString("cmoney"));
              hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
            } 
          } 
        } 
      } else if (startTime.length() == 4 || endTime.length() == 4) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        if (YZUtility.isNullorEmpty(endTime) && !YZUtility.isNullorEmpty(startTime)) {
          endTime = time;
        } else if (!YZUtility.isNullorEmpty(endTime) && YZUtility.isNullorEmpty(startTime)) {
          Long time1 = Long.valueOf(formatter.parse(endTime).getTime());
          Long time2 = Long.valueOf(formatter.parse(time).getTime());
          if (time1.longValue() >= time2.longValue()) {
            startTime = time;
          } else {
            throw new Exception("请选择查询起始时间！");
          } 
        } 
        if (startTime.equals(endTime)) {
          map.put("code", "ISNULL(sum(case when  datepart(year,createtime)=" + endTime + " then 1 else 0 end),0) as '" + endTime + "年'");
          map.put("startyear", startTime);
          map.put("endyear", startTime);
          list = this.analysisService.findCJStatisticsByYear(request, map);
          for (JSONObject json : list) {
            hjczAllNums += Integer.parseInt(json.getString("czperson"));
            hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
            hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
            hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
            hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
            hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
            hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
            hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
            hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
            hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
            hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
            hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
            hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
            hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
            hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
            hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
            hjCmoney += Double.parseDouble(json.getString("cmoney"));
            hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
          } 
        } else {
          StringBuffer sss = new StringBuffer();
          for (int i = Integer.parseInt(startTime); i <= Integer.parseInt(endTime); i++) {
            if (i == Integer.parseInt(endTime)) {
              sss.append("ISNULL(sum(case when  datepart(year,createtime)=" + endTime + " then 1 else 0 end),0) as '" + endTime + "年'");
            } else {
              sss.append("ISNULL(sum(case when  datepart(year,createtime)=" + i + " then 1 else 0 end),0) as '" + i + "年',");
            } 
          } 
          map.put("code", sss.toString());
          map.put("startyear", startTime);
          map.put("endyear", endTime);
          list = this.analysisService.findCJStatisticsByYear(request, map);
          for (JSONObject json : list) {
            hjczAllNums += Integer.parseInt(json.getString("czperson"));
            hjczCjNum += Integer.parseInt(json.getString("czcjperson"));
            hjczPaymoney += Double.parseDouble(json.getString("czpaymoney"));
            hjczJcfmoney += Double.parseDouble(json.getString("czjcfmoney"));
            hjczYjjmoney += Double.parseDouble(json.getString("czyjjmoney"));
            hjfzAllNums += Integer.parseInt(json.getString("fzperson"));
            hjfzCjNum += Integer.parseInt(json.getString("fzcjperson"));
            hjfzPaymoney += Double.parseDouble(json.getString("fzpaymoney"));
            hjfzJcfmoney += Double.parseDouble(json.getString("fzjcfmoney"));
            hjfzYjjmoney += Double.parseDouble(json.getString("fzyjjmoney"));
            hjzxfAllNums += Integer.parseInt(json.getString("zxfperson"));
            hjzxfCjNum += Integer.parseInt(json.getString("zxfcjperson"));
            hjzxfPaymoney += Double.parseDouble(json.getString("zxfpaymoney"));
            hjzxfJcfmoney += Double.parseDouble(json.getString("zxfjcfmoney"));
            hjzxfYjjmoney += Double.parseDouble(json.getString("zxfyjjmoney"));
            hjDrugsmoney += Double.parseDouble(json.getString("drugsmoney"));
            hjCmoney += Double.parseDouble(json.getString("cmoney"));
            hjTcmoney += Double.parseDouble(json.getString("tcmoney"));
          } 
        } 
      } 
      JSONObject jsonobject = new JSONObject();
      jsonobject.put("month", "-");
      jsonobject.put("name", "合计");
      jsonobject.put("czperson", (new StringBuilder(String.valueOf(hjczAllNums))).toString());
      jsonobject.put("czcjperson", (new StringBuilder(String.valueOf(hjczCjNum))).toString());
      NumberFormat nf1 = NumberFormat.getInstance();
      nf1.setGroupingUsed(false);
      if (hjczPaymoney > 0.0D) {
        jsonobject.put("czpaymoney", nf1.format(hjczPaymoney));
      } else {
        jsonobject.put("czpaymoney", "0.00");
      } 
      if (hjczJcfmoney > 0.0D) {
        jsonobject.put("czjcfmoney", nf1.format(hjczJcfmoney));
      } else {
        jsonobject.put("czjcfmoney", "0.00");
      } 
      if (hjczYjjmoney > 0.0D) {
        jsonobject.put("czyjjmoney", nf1.format(hjczYjjmoney));
      } else {
        jsonobject.put("czyjjmoney", "0.00");
      } 
      jsonobject.put("fzperson", (new StringBuilder(String.valueOf(hjfzAllNums))).toString());
      jsonobject.put("fzcjperson", (new StringBuilder(String.valueOf(hjfzCjNum))).toString());
      if (hjfzPaymoney > 0.0D) {
        jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
      } else {
        jsonobject.put("fzpaymoney", "0.00");
      } 
      if (hjfzJcfmoney > 0.0D) {
        jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
      } else {
        jsonobject.put("fzjcfmoney", "0.00");
      } 
      if (hjfzYjjmoney > 0.0D) {
        jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
      } else {
        jsonobject.put("fzyjjmoney", "0.00");
      } 
      jsonobject.put("zxfperson", (new StringBuilder(String.valueOf(hjzxfAllNums))).toString());
      jsonobject.put("zxfcjperson", (new StringBuilder(String.valueOf(hjzxfCjNum))).toString());
      if (hjzxfPaymoney > 0.0D) {
        jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
      } else {
        jsonobject.put("zxfpaymoney", "0.00");
      } 
      if (hjzxfJcfmoney > 0.0D) {
        jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
      } else {
        jsonobject.put("zxfjcfmoney", "0.00");
      } 
      if (hjzxfYjjmoney > 0.0D) {
        jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
      } else {
        jsonobject.put("zxfyjjmoney", "0.00");
      } 
      if (hjCmoney > 0.0D) {
        jsonobject.put("cmoney", nf1.format(hjCmoney));
      } else {
        jsonobject.put("cmoney", "0.00");
      } 
      if (hjTcmoney < 0.0D) {
        jsonobject.put("tcmoney", nf1.format(hjTcmoney));
      } else {
        jsonobject.put("tcmoney", "0.00");
      } 
      if (hjDrugsmoney > 0.0D) {
        jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
      } else {
        jsonobject.put("drugsmoney", "0.00");
      } 
      if (hjczAllNums > 0) {
        jsonobject.put("czcjratio", (new StringBuilder(String.valueOf(hjczCjNum / hjczAllNums))).toString());
      } else {
        jsonobject.put("czcjratio", "0");
      } 
      if (hjfzAllNums > 0) {
        jsonobject.put("fzcjratio", (new StringBuilder(String.valueOf(hjfzCjNum / hjfzAllNums))).toString());
      } else {
        jsonobject.put("fzcjratio", "0");
      } 
      if (hjzxfAllNums > 0) {
        jsonobject.put("zxfcjratio", (new StringBuilder(String.valueOf(hjzxfCjNum / hjzxfAllNums))).toString());
      } else {
        jsonobject.put("zxfcjratio", "0");
      } 
      jsonobject.put("xmpaymoney", Double.parseDouble(jsonobject.getString("czpaymoney")) + Double.parseDouble(jsonobject.getString("fzpaymoney")) + Double.parseDouble(jsonobject.getString("zxfpaymoney")));
      list.add(jsonobject);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, true, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findCJStatisticsByDay.act"})
  public String findCJStatisticsByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    String timeMonth = request.getParameter("timeMonth");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      if (!YZUtility.isNullorEmpty(timeMonth))
        map.put("startTime", timeMonth); 
      List<JSONObject> list = new ArrayList<>();
      if (timeMonth.length() == 7 || timeMonth.length() == 8) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        if ((timeMonth.substring(0, 4).equals(time.substring(0, 4)) && timeMonth.substring(5, 6).equals((new StringBuilder(String.valueOf(Integer.parseInt(time.substring(5, 7))))).toString())) || (timeMonth.substring(0, 4).equals(time.substring(0, 4)) && timeMonth.substring(5, 7).equals((new StringBuilder(String.valueOf(Integer.parseInt(time.substring(5, 7))))).toString()))) {
          StringBuffer sss = new StringBuffer();
          for (int j = 1; j <= Integer.parseInt(time.substring(8, 10)); j++) {
            if (j == Integer.parseInt(time.substring(8, 10))) {
              sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日'");
            } else {
              sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日',");
            } 
          } 
          map.put("year", time.substring(0, 4));
          map.put("month", time.substring(5, 7));
          map.put("code", sss.toString());
          map.put("startday", "1");
          map.put("endday", time.substring(8, 10));
          map.put("years", "(year(r.createtime)=" + time.substring(0, 4) + ")");
          map.put("months", "(month(r.createtime)=" + time.substring(5, 7) + ")");
          map.put("costyears", "(year(cost.sftime)=" + time.substring(0, 4) + ")");
          map.put("costmonths", "(month(cost.sftime)=" + time.substring(5, 7) + ")");
          list = this.analysisService.findCJStatisticsByDay(request, map);
        } else {
          int i = 0;
          String month = "";
          if (timeMonth.substring(5, 7).contains("月")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(String.valueOf(timeMonth.substring(0, 4)) + "-" + timeMonth.substring(5, 6)));
            i = calendar.getActualMaximum(5);
            month = timeMonth.substring(5, 6);
          } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(String.valueOf(timeMonth.substring(0, 4)) + "-" + timeMonth.substring(5, 6)));
            i = calendar.getActualMaximum(5);
            month = timeMonth.substring(5, 7);
          } 
          StringBuffer sss = new StringBuffer();
          for (int j = 1; j <= i; j++) {
            if (j == i) {
              sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日'");
            } else {
              sss.append("ISNULL(sum(case when  datepart(day,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "日',");
            } 
          } 
          map.put("year", timeMonth.substring(0, 4));
          map.put("month", month);
          map.put("code", sss.toString());
          map.put("startday", "1");
          map.put("endday", (new StringBuilder(String.valueOf(i))).toString());
          map.put("years", "(year(r.createtime)=" + timeMonth.substring(0, 4) + ")");
          map.put("months", "(month(r.createtime)=" + month + ")");
          map.put("costyears", "(year(cost.sftime)=" + timeMonth.substring(0, 4) + ")");
          map.put("costmonths", "(month(cost.sftime)=" + month + ")");
          list = this.analysisService.findCJStatisticsByDay(request, map);
        } 
      } else if (timeMonth.length() == 5) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        if (time.equals(timeMonth.substring(0, 4))) {
          SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM");
          Date date1 = new Date(System.currentTimeMillis());
          String time1 = formatter1.format(date1);
          StringBuffer sss = new StringBuffer();
          for (int j = 1; j <= Integer.parseInt(time1.substring(5, 7)); j++) {
            if (j == Integer.parseInt(time1.substring(5, 7))) {
              sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "月'");
            } else {
              sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "月',");
            } 
          } 
          map.put("year", timeMonth.substring(0, 4));
          map.put("code", sss.toString());
          map.put("startmonth", "1");
          map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(time1.substring(5, 7))))).toString());
          map.put("years", "(year(r.createtime)=" + timeMonth.substring(0, 4) + ")");
          map.put("costyears", "(year(cost.sftime)=" + timeMonth.substring(0, 4) + ")");
          list = this.analysisService.findCJQuantityByAskpersonAndMonthInYear(request, map);
        } else {
          StringBuffer sss = new StringBuffer();
          for (int j = 1; j <= 12; j++) {
            if (j == 12) {
              sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "月'");
            } else {
              sss.append("ISNULL(sum(case when  datepart(month,createtime)=" + j + " then 1 else 0 end),0) as '" + j + "月',");
            } 
          } 
          map.put("year", timeMonth.substring(0, 4));
          map.put("code", sss.toString());
          map.put("startmonth", "1");
          map.put("endmonth", "12");
          map.put("years", "(year(r.createtime)=" + timeMonth.substring(0, 4) + ")");
          map.put("costyears", "(year(cost.sftime)=" + timeMonth.substring(0, 4) + ")");
          list = this.analysisService.findCJQuantityByAskpersonAndMonthInYear(request, map);
        } 
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, true, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/volumeTransaction.act"})
  public String volumeTransaction(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String organization = request.getParameter("organization");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      if (endtime != null && !endtime.equals(""))
        map.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(0, 4))))).toString()); 
      map.put("code", "ISNULL(sum(case when  datepart(month,createtime)=" + Integer.parseInt(endtime.substring(5, 7)) + " then 1 else 0 end),0) as '" + Integer.parseInt(endtime.substring(5, 7)) + "月'");
      map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(starttime.substring(5, 7))))).toString());
      map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(5, 7))))).toString());
      map.put("years", "(year(r.createtime)=" + Integer.parseInt(endtime.substring(0, 4)) + ")");
      List<JSONObject> json = this.analysisService.findCjsCale(request, map);
      YZUtility.RETURN_LIST(json, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findAllCJStatisticsByMonth.act"})
  public String findAllCJStatisticsByMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    String startTime = request.getParameter("starttime");
    String endTime = request.getParameter("endtime");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    String personId = request.getParameter("recesort");
    try {
      List<JSONObject> list = new ArrayList<>();
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      if (!YZUtility.isNullorEmpty(personId))
        map.put("personId", personId); 
      if (!YZUtility.isNullorEmpty(startTime))
        map.put("startTime", startTime); 
      if (!YZUtility.isNullorEmpty(endTime))
        map.put("endTime", endTime); 
      if (startTime.length() == 10 || endTime.length() == 10) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        if (YZUtility.isNullorEmpty(endTime) && !YZUtility.isNullorEmpty(startTime)) {
          endTime = time;
        } else if (!YZUtility.isNullorEmpty(endTime) && YZUtility.isNullorEmpty(startTime)) {
          Long time1 = Long.valueOf(formatter.parse(endTime).getTime());
          Long time2 = Long.valueOf(formatter.parse(time).getTime());
          if (time1.longValue() >= time2.longValue()) {
            startTime = time;
          } else {
            throw new Exception("请选择查询起始时间！");
          } 
        } 
        if (startTime.equals(endTime)) {
          map.put("year", startTime.substring(0, 4));
          map.put("month", startTime.substring(5, 7));
          map.put("startday", startTime.substring(8, 10));
          map.put("endday", startTime.substring(8, 10));
          map.put("years", "(year(r.createtime)=" + startTime.substring(0, 4) + ")");
          map.put("months", "(month(r.createtime)=" + startTime.substring(5, 7) + ")");
          map.put("costyears", "(year(cost.sftime)=" + startTime.substring(0, 4) + ")");
          map.put("costmonths", "(month(cost.sftime)=" + startTime.substring(5, 7) + ")");
          list = this.analysisService.findAllCJStatisticsByDay(request, map);
        } else if (startTime.substring(0, 4).equals(endTime.substring(0, 4))) {
          if (startTime.substring(5, 7).equals(endTime.substring(5, 7))) {
            map.put("year", startTime.substring(0, 4));
            map.put("month", startTime.substring(5, 7));
            map.put("startday", startTime.substring(8, 10));
            map.put("endday", endTime.substring(8, 10));
            map.put("years", "(year(r.createtime)=" + startTime.substring(0, 4) + ")");
            map.put("months", "(month(r.createtime)=" + startTime.substring(5, 7) + ")");
            map.put("costyears", "(year(cost.sftime)=" + startTime.substring(0, 4) + ")");
            map.put("costmonths", "(month(cost.sftime)=" + startTime.substring(5, 7) + ")");
            list = this.analysisService.findAllCJStatisticsByDay(request, map);
          } else {
            for (int i = Integer.parseInt(startTime.substring(5, 7)); i <= Integer.parseInt(endTime.substring(5, 7)); i++) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
              Calendar calendar = Calendar.getInstance();
              int s = 0;
              int k = 0;
              if (i == Integer.parseInt(startTime.substring(5, 7))) {
                calendar.setTime(sdf.parse(startTime.substring(0, 7)));
                s = calendar.getActualMaximum(5);
                k = Integer.parseInt(startTime.substring(8, 10));
              } else if (i > Integer.parseInt(startTime.substring(5, 7)) && i < Integer.parseInt(endTime.substring(5, 7))) {
                calendar.setTime(sdf.parse(String.valueOf(startTime.substring(0, 4)) + "-" + i));
                s = calendar.getActualMaximum(5);
                k = 1;
              } else {
                s = Integer.parseInt(endTime.substring(8, 10));
                k = 1;
              } 
              map.put("year", startTime.substring(0, 4));
              map.put("month", (new StringBuilder(String.valueOf(i))).toString());
              map.put("startday", (new StringBuilder(String.valueOf(k))).toString());
              map.put("endday", (new StringBuilder(String.valueOf(s))).toString());
              map.put("years", "(year(r.createtime)=" + startTime.substring(0, 4) + ")");
              map.put("months", "(month(r.createtime)=" + i + ")");
              map.put("costyears", "(year(cost.sftime)=" + startTime.substring(0, 4) + ")");
              map.put("costmonths", "(month(cost.sftime)=" + i + ")");
              List<JSONObject> list1 = this.analysisService.findAllCJStatisticsByDay(request, map);
              if (list.size() == 0) {
                list.addAll(list1);
              } else {
                for (int j = 0; j < list.size(); j++) {
                  ((JSONObject)list.get(j)).put("month", "-");
                  ((JSONObject)list.get(j)).put("name", ((JSONObject)list.get(j)).getString("name"));
                  ((JSONObject)list.get(j)).put("czperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czperson") + ((JSONObject)list1.get(j)).getInt("czperson")))).toString());
                  ((JSONObject)list.get(j)).put("czcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czcjperson") + ((JSONObject)list1.get(j)).getInt("czcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("czperson")) > 0) {
                    ((JSONObject)list.get(j)).put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("czcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("czperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("czcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("czpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czpaymoney") + ((JSONObject)list1.get(j)).getDouble("czpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("czjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czjcfmoney") + ((JSONObject)list1.get(j)).getDouble("czjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("czyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czyjjmoney") + ((JSONObject)list1.get(j)).getDouble("czyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzperson") + ((JSONObject)list1.get(j)).getInt("fzperson")))).toString());
                  ((JSONObject)list.get(j)).put("fzcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzcjperson") + ((JSONObject)list1.get(j)).getInt("fzcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("fzperson")) > 0) {
                    ((JSONObject)list.get(j)).put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("fzcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("fzperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("fzcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("fzpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzpaymoney") + ((JSONObject)list1.get(j)).getDouble("fzpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzjcfmoney") + ((JSONObject)list1.get(j)).getDouble("fzjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzyjjmoney") + ((JSONObject)list1.get(j)).getDouble("fzyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfperson") + ((JSONObject)list1.get(j)).getInt("zxfperson")))).toString());
                  ((JSONObject)list.get(j)).put("zxfcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfcjperson") + ((JSONObject)list1.get(j)).getInt("zxfcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("zxfperson")) > 0) {
                    ((JSONObject)list.get(j)).put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("zxfcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("zxfperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("zxfcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("cmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("cmoney") + ((JSONObject)list1.get(j)).getDouble("cmoney")))).toString());
                  ((JSONObject)list.get(j)).put("tcmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("tcmoney") + ((JSONObject)list1.get(j)).getDouble("tcmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfpaymoney") + ((JSONObject)list1.get(j)).getDouble("zxfpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfjcfmoney") + ((JSONObject)list1.get(j)).getDouble("zxfjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfyjjmoney") + ((JSONObject)list1.get(j)).getDouble("zxfyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("drugsmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("drugsmoney") + ((JSONObject)list1.get(j)).getDouble("drugsmoney")))).toString());
                  ((JSONObject)list.get(j)).put("xmpaymoney", Double.parseDouble(((JSONObject)list.get(j)).getString("czpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("fzpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("zxfpaymoney")));
                } 
              } 
            } 
          } 
        } else {
          for (int k = Integer.parseInt(startTime.substring(0, 4)); k <= Integer.parseInt(endTime.substring(0, 4)); k++) {
            if (k == Integer.parseInt(startTime.substring(0, 4))) {
              for (int i = Integer.parseInt(startTime.substring(5, 7)); i <= 12; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar calendar = Calendar.getInstance();
                int s = 0;
                int l = 0;
                if (i == Integer.parseInt(startTime.substring(5, 7))) {
                  calendar.setTime(sdf.parse(startTime.substring(0, 7)));
                  s = calendar.getActualMaximum(5);
                  l = Integer.parseInt(startTime.substring(8, 10));
                } else if (i > Integer.parseInt(startTime.substring(5, 7)) && i <= 12) {
                  calendar.setTime(sdf.parse(String.valueOf(startTime.substring(0, 4)) + "-" + i));
                  s = calendar.getActualMaximum(5);
                  l = 1;
                } 
                map.put("year", startTime.substring(0, 4));
                map.put("month", (new StringBuilder(String.valueOf(i))).toString());
                map.put("startday", (new StringBuilder(String.valueOf(l))).toString());
                map.put("endday", (new StringBuilder(String.valueOf(s))).toString());
                map.put("years", "(year(r.createtime)=" + startTime.substring(0, 4) + ")");
                map.put("months", "(month(r.createtime)=" + i + ")");
                map.put("costyears", "(year(cost.sftime)=" + startTime.substring(0, 4) + ")");
                map.put("costmonths", "(month(cost.sftime)=" + i + ")");
                List<JSONObject> list1 = this.analysisService.findAllCJStatisticsByDay(request, map);
                if (list.size() == 0) {
                  list.addAll(list1);
                } else {
                  for (int j = 0; j < list.size(); j++) {
                    ((JSONObject)list.get(j)).put("month", "-");
                    ((JSONObject)list.get(j)).put("name", ((JSONObject)list.get(j)).getString("name"));
                    ((JSONObject)list.get(j)).put("czperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czperson") + ((JSONObject)list1.get(j)).getInt("czperson")))).toString());
                    ((JSONObject)list.get(j)).put("czcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czcjperson") + ((JSONObject)list1.get(j)).getInt("czcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("czperson")) > 0) {
                      ((JSONObject)list.get(j)).put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("czcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("czperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("czcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("czpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czpaymoney") + ((JSONObject)list1.get(j)).getDouble("czpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("czjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czjcfmoney") + ((JSONObject)list1.get(j)).getDouble("czjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("czyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czyjjmoney") + ((JSONObject)list1.get(j)).getDouble("czyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzperson") + ((JSONObject)list1.get(j)).getInt("fzperson")))).toString());
                    ((JSONObject)list.get(j)).put("fzcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzcjperson") + ((JSONObject)list1.get(j)).getInt("fzcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("fzperson")) > 0) {
                      ((JSONObject)list.get(j)).put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("fzcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("fzperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("fzcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("fzpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzpaymoney") + ((JSONObject)list1.get(j)).getDouble("fzpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzjcfmoney") + ((JSONObject)list1.get(j)).getDouble("fzjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzyjjmoney") + ((JSONObject)list1.get(j)).getDouble("fzyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfperson") + ((JSONObject)list1.get(j)).getInt("zxfperson")))).toString());
                    ((JSONObject)list.get(j)).put("zxfcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfcjperson") + ((JSONObject)list1.get(j)).getInt("zxfcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("zxfperson")) > 0) {
                      ((JSONObject)list.get(j)).put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("zxfcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("zxfperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("zxfcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("cmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("cmoney") + ((JSONObject)list1.get(j)).getDouble("cmoney")))).toString());
                    ((JSONObject)list.get(j)).put("tcmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("tcmoney") + ((JSONObject)list1.get(j)).getDouble("tcmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfpaymoney") + ((JSONObject)list1.get(j)).getDouble("zxfpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfjcfmoney") + ((JSONObject)list1.get(j)).getDouble("zxfjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfyjjmoney") + ((JSONObject)list1.get(j)).getDouble("zxfyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("drugsmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("drugsmoney") + ((JSONObject)list1.get(j)).getDouble("drugsmoney")))).toString());
                    ((JSONObject)list.get(j)).put("xmpaymoney", Double.parseDouble(((JSONObject)list.get(j)).getString("czpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("fzpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("zxfpaymoney")));
                  } 
                } 
              } 
            } else if (k > Integer.parseInt(startTime.substring(0, 4)) && k < Integer.parseInt(endTime.substring(0, 4))) {
              for (int i = 1; i <= 12; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(String.valueOf(Integer.parseInt(startTime.substring(0, 4)) + k) + "-" + i));
                int s = calendar.getActualMaximum(5);
                int l = 1;
                map.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(startTime.substring(0, 4)) + k))).toString());
                map.put("month", (new StringBuilder(String.valueOf(i))).toString());
                map.put("startday", (new StringBuilder(String.valueOf(l))).toString());
                map.put("endday", (new StringBuilder(String.valueOf(s))).toString());
                map.put("years", "(year(r.createtime)=" + (Integer.parseInt(startTime.substring(0, 4)) + k) + ")");
                map.put("months", "(month(r.createtime)=" + i + ")");
                map.put("costyears", "(year(cost.sftime)=" + (Integer.parseInt(startTime.substring(0, 4)) + k) + ")");
                map.put("costmonths", "(month(cost.sftime)=" + i + ")");
                List<JSONObject> list1 = this.analysisService.findAllCJStatisticsByDay(request, map);
                if (list.size() == 0) {
                  list.addAll(list1);
                } else {
                  for (int j = 0; j < list.size(); j++) {
                    ((JSONObject)list.get(j)).put("month", "-");
                    ((JSONObject)list.get(j)).put("name", ((JSONObject)list.get(j)).getString("name"));
                    ((JSONObject)list.get(j)).put("czperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czperson") + ((JSONObject)list1.get(j)).getInt("czperson")))).toString());
                    ((JSONObject)list.get(j)).put("czcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czcjperson") + ((JSONObject)list1.get(j)).getInt("czcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("czperson")) > 0) {
                      ((JSONObject)list.get(j)).put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("czcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("czperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("czcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("czpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czpaymoney") + ((JSONObject)list1.get(j)).getDouble("czpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("czjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czjcfmoney") + ((JSONObject)list1.get(j)).getDouble("czjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("czyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czyjjmoney") + ((JSONObject)list1.get(j)).getDouble("czyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzperson") + ((JSONObject)list1.get(j)).getInt("fzperson")))).toString());
                    ((JSONObject)list.get(j)).put("fzcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzcjperson") + ((JSONObject)list1.get(j)).getInt("fzcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("fzperson")) > 0) {
                      ((JSONObject)list.get(j)).put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("fzcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("fzperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("fzcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("fzpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzpaymoney") + ((JSONObject)list1.get(j)).getDouble("fzpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzjcfmoney") + ((JSONObject)list1.get(j)).getDouble("fzjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzyjjmoney") + ((JSONObject)list1.get(j)).getDouble("fzyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfperson") + ((JSONObject)list1.get(j)).getInt("zxfperson")))).toString());
                    ((JSONObject)list.get(j)).put("zxfcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfcjperson") + ((JSONObject)list1.get(j)).getInt("zxfcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("zxfperson")) > 0) {
                      ((JSONObject)list.get(j)).put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("zxfcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("zxfperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("zxfcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("cmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("cmoney") + ((JSONObject)list1.get(j)).getDouble("cmoney")))).toString());
                    ((JSONObject)list.get(j)).put("tcmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("tcmoney") + ((JSONObject)list1.get(j)).getDouble("tcmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfpaymoney") + ((JSONObject)list1.get(j)).getDouble("zxfpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfjcfmoney") + ((JSONObject)list1.get(j)).getDouble("zxfjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfyjjmoney") + ((JSONObject)list1.get(j)).getDouble("zxfyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("drugsmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("drugsmoney") + ((JSONObject)list1.get(j)).getDouble("drugsmoney")))).toString());
                    ((JSONObject)list.get(j)).put("xmpaymoney", Double.parseDouble(((JSONObject)list.get(j)).getString("czpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("fzpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("zxfpaymoney")));
                  } 
                } 
              } 
            } else {
              for (int i = 1; i <= Integer.parseInt(endTime.substring(5, 7)); i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(String.valueOf(endTime.substring(0, 4)) + "-" + i));
                int s = 0;
                if (i == Integer.parseInt(endTime.substring(5, 7))) {
                  s = Integer.parseInt(endTime.substring(8, 10));
                } else {
                  s = calendar.getActualMaximum(5);
                } 
                int l = 1;
                map.put("year", endTime.substring(0, 4));
                map.put("month", (new StringBuilder(String.valueOf(i))).toString());
                map.put("startday", (new StringBuilder(String.valueOf(l))).toString());
                map.put("endday", (new StringBuilder(String.valueOf(s))).toString());
                map.put("years", "(year(r.createtime)=" + endTime.substring(0, 4) + ")");
                map.put("months", "(month(r.createtime)=" + i + ")");
                map.put("costyears", "(year(cost.sftime)=" + endTime.substring(0, 4) + ")");
                map.put("costmonths", "(month(cost.sftime)=" + i + ")");
                List<JSONObject> list1 = this.analysisService.findAllCJStatisticsByDay(request, map);
                if (list.size() == 0) {
                  list.addAll(list1);
                } else {
                  for (int j = 0; j < list.size(); j++) {
                    ((JSONObject)list.get(j)).put("month", "-");
                    ((JSONObject)list.get(j)).put("name", ((JSONObject)list.get(j)).getString("name"));
                    ((JSONObject)list.get(j)).put("czperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czperson") + ((JSONObject)list1.get(j)).getInt("czperson")))).toString());
                    ((JSONObject)list.get(j)).put("czcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czcjperson") + ((JSONObject)list1.get(j)).getInt("czcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("czperson")) > 0) {
                      ((JSONObject)list.get(j)).put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("czcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("czperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("czcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("czpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czpaymoney") + ((JSONObject)list1.get(j)).getDouble("czpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("czjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czjcfmoney") + ((JSONObject)list1.get(j)).getDouble("czjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("czyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czyjjmoney") + ((JSONObject)list1.get(j)).getDouble("czyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzperson") + ((JSONObject)list1.get(j)).getInt("fzperson")))).toString());
                    ((JSONObject)list.get(j)).put("fzcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzcjperson") + ((JSONObject)list1.get(j)).getInt("fzcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("fzperson")) > 0) {
                      ((JSONObject)list.get(j)).put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("fzcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("fzperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("fzcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("fzpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzpaymoney") + ((JSONObject)list1.get(j)).getDouble("fzpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzjcfmoney") + ((JSONObject)list1.get(j)).getDouble("fzjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("fzyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzyjjmoney") + ((JSONObject)list1.get(j)).getDouble("fzyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfperson") + ((JSONObject)list1.get(j)).getInt("zxfperson")))).toString());
                    ((JSONObject)list.get(j)).put("zxfcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfcjperson") + ((JSONObject)list1.get(j)).getInt("zxfcjperson")))).toString());
                    if (Integer.parseInt(((JSONObject)list.get(j)).getString("zxfperson")) > 0) {
                      ((JSONObject)list.get(j)).put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("zxfcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("zxfperson"))))).toString());
                    } else {
                      ((JSONObject)list.get(j)).put("zxfcjratio", "0");
                    } 
                    ((JSONObject)list.get(j)).put("cmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("cmoney") + ((JSONObject)list1.get(j)).getDouble("cmoney")))).toString());
                    ((JSONObject)list.get(j)).put("tcmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("tcmoney") + ((JSONObject)list1.get(j)).getDouble("tcmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfpaymoney") + ((JSONObject)list1.get(j)).getDouble("zxfpaymoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfjcfmoney") + ((JSONObject)list1.get(j)).getDouble("zxfjcfmoney")))).toString());
                    ((JSONObject)list.get(j)).put("zxfyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfyjjmoney") + ((JSONObject)list1.get(j)).getDouble("zxfyjjmoney")))).toString());
                    ((JSONObject)list.get(j)).put("drugsmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("drugsmoney") + ((JSONObject)list1.get(j)).getDouble("drugsmoney")))).toString());
                    ((JSONObject)list.get(j)).put("xmpaymoney", Double.parseDouble(((JSONObject)list.get(j)).getString("czpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("fzpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("zxfpaymoney")));
                  } 
                } 
              } 
            } 
          } 
        } 
      } else if (startTime.length() == 7 || endTime.length() == 7) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        if (YZUtility.isNullorEmpty(endTime) && !YZUtility.isNullorEmpty(startTime)) {
          endTime = time;
        } else if (!YZUtility.isNullorEmpty(endTime) && YZUtility.isNullorEmpty(startTime)) {
          Long time1 = Long.valueOf(formatter.parse(endTime).getTime());
          Long time2 = Long.valueOf(formatter.parse(time).getTime());
          if (time1.longValue() >= time2.longValue()) {
            startTime = time;
          } else {
            throw new Exception("请选择查询起始时间！");
          } 
        } 
        if (Integer.parseInt(startTime.substring(0, 4)) < Integer.parseInt(endTime.substring(0, 4))) {
          for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); i++) {
            if (i == Integer.parseInt(startTime.substring(0, 4))) {
              map.put("year", (new StringBuilder(String.valueOf(i))).toString());
              map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(startTime.substring(5, 7))))).toString());
              map.put("endmonth", "12");
              map.put("years", "(year(r.createtime)=" + i + ")");
              map.put("costyears", "(year(cost.sftime)=" + i + ")");
              List<JSONObject> list1 = this.analysisService.findAllCJStatisticsByMonth(request, map);
              if (list.size() == 0) {
                list.addAll(list1);
              } else {
                for (int j = 0; j < list.size(); j++) {
                  ((JSONObject)list.get(j)).put("month", "-");
                  ((JSONObject)list.get(j)).put("name", ((JSONObject)list.get(j)).getString("name"));
                  ((JSONObject)list.get(j)).put("czperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czperson") + ((JSONObject)list1.get(j)).getInt("czperson")))).toString());
                  ((JSONObject)list.get(j)).put("czcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czcjperson") + ((JSONObject)list1.get(j)).getInt("czcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("czperson")) > 0) {
                    ((JSONObject)list.get(j)).put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("czcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("czperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("czcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("czpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czpaymoney") + ((JSONObject)list1.get(j)).getDouble("czpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("czjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czjcfmoney") + ((JSONObject)list1.get(j)).getDouble("czjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("czyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czyjjmoney") + ((JSONObject)list1.get(j)).getDouble("czyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzperson") + ((JSONObject)list1.get(j)).getInt("fzperson")))).toString());
                  ((JSONObject)list.get(j)).put("fzcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzcjperson") + ((JSONObject)list1.get(j)).getInt("fzcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("fzperson")) > 0) {
                    ((JSONObject)list.get(j)).put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("fzcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("fzperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("fzcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("fzpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzpaymoney") + ((JSONObject)list1.get(j)).getDouble("fzpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzjcfmoney") + ((JSONObject)list1.get(j)).getDouble("fzjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzyjjmoney") + ((JSONObject)list1.get(j)).getDouble("fzyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfperson") + ((JSONObject)list1.get(j)).getInt("zxfperson")))).toString());
                  ((JSONObject)list.get(j)).put("zxfcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfcjperson") + ((JSONObject)list1.get(j)).getInt("zxfcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("zxfperson")) > 0) {
                    ((JSONObject)list.get(j)).put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("zxfcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("zxfperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("zxfcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("cmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("cmoney") + ((JSONObject)list1.get(j)).getDouble("cmoney")))).toString());
                  ((JSONObject)list.get(j)).put("tcmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("tcmoney") + ((JSONObject)list1.get(j)).getDouble("tcmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfpaymoney") + ((JSONObject)list1.get(j)).getDouble("zxfpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfjcfmoney") + ((JSONObject)list1.get(j)).getDouble("zxfjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfyjjmoney") + ((JSONObject)list1.get(j)).getDouble("zxfyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("drugsmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("drugsmoney") + ((JSONObject)list1.get(j)).getDouble("drugsmoney")))).toString());
                  ((JSONObject)list.get(j)).put("xmpaymoney", Double.parseDouble(((JSONObject)list.get(j)).getString("czpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("fzpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("zxfpaymoney")));
                } 
              } 
            } else if (i > Integer.parseInt(startTime.substring(0, 4)) && i < Integer.parseInt(endTime.substring(0, 4))) {
              map.put("year", (new StringBuilder(String.valueOf(i))).toString());
              map.put("startmonth", "1");
              map.put("endmonth", "12");
              map.put("years", "(year(r.createtime)=" + i + ")");
              map.put("costyears", "(year(cost.sftime)=" + i + ")");
              List<JSONObject> list1 = this.analysisService.findAllCJStatisticsByMonth(request, map);
              if (list.size() == 0) {
                list.addAll(list1);
              } else {
                for (int j = 0; j < list.size(); j++) {
                  ((JSONObject)list.get(j)).put("month", "-");
                  ((JSONObject)list.get(j)).put("name", ((JSONObject)list.get(j)).getString("name"));
                  ((JSONObject)list.get(j)).put("czperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czperson") + ((JSONObject)list1.get(j)).getInt("czperson")))).toString());
                  ((JSONObject)list.get(j)).put("czcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czcjperson") + ((JSONObject)list1.get(j)).getInt("czcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("czperson")) > 0) {
                    ((JSONObject)list.get(j)).put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("czcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("czperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("czcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("czpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czpaymoney") + ((JSONObject)list1.get(j)).getDouble("czpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("czjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czjcfmoney") + ((JSONObject)list1.get(j)).getDouble("czjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("czyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czyjjmoney") + ((JSONObject)list1.get(j)).getDouble("czyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzperson") + ((JSONObject)list1.get(j)).getInt("fzperson")))).toString());
                  ((JSONObject)list.get(j)).put("fzcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzcjperson") + ((JSONObject)list1.get(j)).getInt("fzcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("fzperson")) > 0) {
                    ((JSONObject)list.get(j)).put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("fzcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("fzperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("fzcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("fzpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzpaymoney") + ((JSONObject)list1.get(j)).getDouble("fzpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzjcfmoney") + ((JSONObject)list1.get(j)).getDouble("fzjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzyjjmoney") + ((JSONObject)list1.get(j)).getDouble("fzyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfperson") + ((JSONObject)list1.get(j)).getInt("zxfperson")))).toString());
                  ((JSONObject)list.get(j)).put("zxfcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfcjperson") + ((JSONObject)list1.get(j)).getInt("zxfcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("zxfperson")) > 0) {
                    ((JSONObject)list.get(j)).put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("zxfcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("zxfperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("zxfcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("cmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("cmoney") + ((JSONObject)list1.get(j)).getDouble("cmoney")))).toString());
                  ((JSONObject)list.get(j)).put("tcmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("tcmoney") + ((JSONObject)list1.get(j)).getDouble("tcmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfpaymoney") + ((JSONObject)list1.get(j)).getDouble("zxfpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfjcfmoney") + ((JSONObject)list1.get(j)).getDouble("zxfjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfyjjmoney") + ((JSONObject)list1.get(j)).getDouble("zxfyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("drugsmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("drugsmoney") + ((JSONObject)list1.get(j)).getDouble("drugsmoney")))).toString());
                  ((JSONObject)list.get(j)).put("xmpaymoney", Double.parseDouble(((JSONObject)list.get(j)).getString("czpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("fzpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("zxfpaymoney")));
                } 
              } 
            } else if (i == Integer.parseInt(endTime.substring(0, 4))) {
              map.put("year", (new StringBuilder(String.valueOf(i))).toString());
              map.put("startmonth", "1");
              map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endTime.substring(5, 7))))).toString());
              map.put("years", "(year(r.createtime)=" + i + ")");
              map.put("costyears", "(year(cost.sftime)=" + i + ")");
              List<JSONObject> list1 = this.analysisService.findAllCJStatisticsByMonth(request, map);
              if (list.size() == 0) {
                list.addAll(list1);
              } else {
                for (int j = 0; j < list.size(); j++) {
                  ((JSONObject)list.get(j)).put("month", "-");
                  ((JSONObject)list.get(j)).put("name", ((JSONObject)list.get(j)).getString("name"));
                  ((JSONObject)list.get(j)).put("czperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czperson") + ((JSONObject)list1.get(j)).getInt("czperson")))).toString());
                  ((JSONObject)list.get(j)).put("czcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("czcjperson") + ((JSONObject)list1.get(j)).getInt("czcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("czperson")) > 0) {
                    ((JSONObject)list.get(j)).put("czcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("czcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("czperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("czcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("czpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czpaymoney") + ((JSONObject)list1.get(j)).getDouble("czpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("czjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czjcfmoney") + ((JSONObject)list1.get(j)).getDouble("czjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("czyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("czyjjmoney") + ((JSONObject)list1.get(j)).getDouble("czyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzperson") + ((JSONObject)list1.get(j)).getInt("fzperson")))).toString());
                  ((JSONObject)list.get(j)).put("fzcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("fzcjperson") + ((JSONObject)list1.get(j)).getInt("fzcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("fzperson")) > 0) {
                    ((JSONObject)list.get(j)).put("fzcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("fzcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("fzperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("fzcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("fzpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzpaymoney") + ((JSONObject)list1.get(j)).getDouble("fzpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzjcfmoney") + ((JSONObject)list1.get(j)).getDouble("fzjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("fzyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("fzyjjmoney") + ((JSONObject)list1.get(j)).getDouble("fzyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfperson") + ((JSONObject)list1.get(j)).getInt("zxfperson")))).toString());
                  ((JSONObject)list.get(j)).put("zxfcjperson", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getInt("zxfcjperson") + ((JSONObject)list1.get(j)).getInt("zxfcjperson")))).toString());
                  if (Integer.parseInt(((JSONObject)list.get(j)).getString("zxfperson")) > 0) {
                    ((JSONObject)list.get(j)).put("zxfcjratio", (new StringBuilder(String.valueOf(Double.parseDouble(((JSONObject)list.get(j)).getString("zxfcjperson")) / Double.parseDouble(((JSONObject)list.get(j)).getString("zxfperson"))))).toString());
                  } else {
                    ((JSONObject)list.get(j)).put("zxfcjratio", "0");
                  } 
                  ((JSONObject)list.get(j)).put("cmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("cmoney") + ((JSONObject)list1.get(j)).getDouble("cmoney")))).toString());
                  ((JSONObject)list.get(j)).put("tcmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("tcmoney") + ((JSONObject)list1.get(j)).getDouble("tcmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfpaymoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfpaymoney") + ((JSONObject)list1.get(j)).getDouble("zxfpaymoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfjcfmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfjcfmoney") + ((JSONObject)list1.get(j)).getDouble("zxfjcfmoney")))).toString());
                  ((JSONObject)list.get(j)).put("zxfyjjmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("zxfyjjmoney") + ((JSONObject)list1.get(j)).getDouble("zxfyjjmoney")))).toString());
                  ((JSONObject)list.get(j)).put("drugsmoney", (new StringBuilder(String.valueOf(((JSONObject)list.get(j)).getDouble("drugsmoney") + ((JSONObject)list1.get(j)).getDouble("drugsmoney")))).toString());
                  ((JSONObject)list.get(j)).put("xmpaymoney", Double.parseDouble(((JSONObject)list.get(j)).getString("czpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("fzpaymoney")) + Double.parseDouble(((JSONObject)list.get(j)).getString("zxfpaymoney")));
                } 
              } 
            } 
          } 
        } else {
          map.put("year", (new StringBuilder(String.valueOf(endTime.substring(0, 4)))).toString());
          map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(startTime.substring(5, 7))))).toString());
          map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endTime.substring(5, 7))))).toString());
          map.put("years", "(year(r.createtime)=" + endTime.substring(0, 4) + ")");
          map.put("costyears", "(year(cost.sftime)=" + endTime.substring(0, 4) + ")");
          list = this.analysisService.findAllCJStatisticsByMonth(request, map);
        } 
      } else if (startTime.length() == 4 || endTime.length() == 4) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        if (YZUtility.isNullorEmpty(endTime) && !YZUtility.isNullorEmpty(startTime)) {
          endTime = time;
        } else if (!YZUtility.isNullorEmpty(endTime) && YZUtility.isNullorEmpty(startTime)) {
          Long time1 = Long.valueOf(formatter.parse(endTime).getTime());
          Long time2 = Long.valueOf(formatter.parse(time).getTime());
          if (time1.longValue() >= time2.longValue()) {
            startTime = time;
          } else {
            throw new Exception("请选择查询起始时间！");
          } 
        } 
        map.put("startyear", startTime);
        map.put("endyear", endTime);
        list = this.analysisService.findAllCJStatisticsByYear(request, map);
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, true, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findTotalMoneyByMonth.act"})
  public String findTotalMoneyByMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    String startTime = request.getParameter("starttime");
    String endTime = request.getParameter("endtime");
    String selectLabel = request.getParameter("selectLabel");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    Map<String, String> dataMap = new HashMap<>();
    try {
      if (!YZUtility.isNullorEmpty(organization))
        dataMap.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(startTime))
        dataMap.put("startTime", startTime); 
      if (!YZUtility.isNullorEmpty(endTime))
        dataMap.put("endTime", endTime); 
      if (!YZUtility.isNullorEmpty(selectLabel))
        dataMap.put("selectLabel", selectLabel); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          dataMap.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          dataMap.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        dataMap.put("deptCategory", deptCategory); 
      List<JSONObject> list = new ArrayList<>();
      list = this.analysisService.findTotalMoneyByMonth(dataMap, request);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/DepartmentPerformance.act"})
  public String DepartmentPerformance(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String organization = request.getParameter("organization");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      map.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(0, 4))))).toString());
      map.put("code", "ISNULL(sum(case when  datepart(month,createtime)=" + Integer.parseInt(endtime.substring(5, 7)) + " then 1 else 0 end),0) as '" + Integer.parseInt(endtime.substring(5, 7)) + "月'");
      map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(starttime.substring(5, 7))))).toString());
      map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(5, 7))))).toString());
      map.put("years", "(year(r.createtime)=" + Integer.parseInt(endtime.substring(0, 4)) + ")");
      List<JSONObject> json = this.analysisService.findDepartment(request, map);
      YZUtility.RETURN_LIST(json, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/implantStatistics.act"})
  public String implantStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String organization = request.getParameter("organization");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      map.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(0, 4))))).toString());
      map.put("code", "ISNULL(sum(case when  datepart(month,createtime)=" + Integer.parseInt(endtime.substring(5, 7)) + " then 1 else 0 end),0) as '" + Integer.parseInt(endtime.substring(5, 7)) + "月'");
      map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(starttime.substring(5, 7))))).toString());
      map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(5, 7))))).toString());
      map.put("years", "(year(r.createtime)=" + Integer.parseInt(endtime.substring(0, 4)) + ")");
      List<JSONObject> json = this.analysisService.findImplant(request, map);
      YZUtility.RETURN_LIST(json, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/doctorStatistics.act"})
  public String doctorStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String organization = request.getParameter("organization");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      map.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(0, 4))))).toString());
      map.put("code", "ISNULL(sum(case when  datepart(month,createtime)=" + Integer.parseInt(endtime.substring(5, 7)) + " then 1 else 0 end),0) as '" + Integer.parseInt(endtime.substring(5, 7)) + "月'");
      map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(starttime.substring(5, 7))))).toString());
      map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(5, 7))))).toString());
      map.put("years", "(year(r.createtime)=" + Integer.parseInt(endtime.substring(0, 4)) + ")");
      List<JSONObject> json = this.analysisService.finddoctor(request, map);
      YZUtility.RETURN_LIST(json, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/Devchannel.act"})
  public String Devchannel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String organization = request.getParameter("organization");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      map.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(0, 4))))).toString());
      map.put("code", "ISNULL(sum(case when  datepart(month,createtime)=" + Integer.parseInt(endtime.substring(5, 7)) + " then 1 else 0 end),0) as '" + Integer.parseInt(endtime.substring(5, 7)) + "月'");
      map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(starttime.substring(5, 7))))).toString());
      map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(5, 7))))).toString());
      map.put("years", "(year(r.createtime)=" + Integer.parseInt(endtime.substring(0, 4)) + ")");
      List<JSONObject> json = this.analysisService.Devchannel(request, map);
      YZUtility.RETURN_LIST(json, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/consumptionInterval.act"})
  public String consumptionInterval(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String organization = request.getParameter("organization");
    String qxname = request.getParameter("qxname");
    String deptCategory = request.getParameter("deptCategory");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(qxname)) {
        String[] qxnamelist = qxname.split(",");
        if (qxnamelist.length == 1) {
          map.put("buttonName", "'" + qxname + "'");
        } else {
          StringBuilder str = new StringBuilder();
          for (int i = 0; i < qxnamelist.length; i++) {
            if (i == qxnamelist.length - 1) {
              str.append("'" + qxnamelist[i] + "'");
            } else {
              str.append("'" + qxnamelist[i] + "',");
            } 
          } 
          map.put("buttonName", str.toString());
        } 
      } 
      if (!YZUtility.isNullorEmpty(deptCategory))
        map.put("deptCategory", deptCategory); 
      map.put("year", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(0, 4))))).toString());
      map.put("code", "ISNULL(sum(case when  datepart(month,createtime)=" + Integer.parseInt(endtime.substring(5, 7)) + " then 1 else 0 end),0) as '" + Integer.parseInt(endtime.substring(5, 7)) + "月'");
      map.put("startmonth", (new StringBuilder(String.valueOf(Integer.parseInt(starttime.substring(5, 7))))).toString());
      map.put("endmonth", (new StringBuilder(String.valueOf(Integer.parseInt(endtime.substring(5, 7))))).toString());
      map.put("years", "(year(r.createtime)=" + Integer.parseInt(endtime.substring(0, 4)) + ")");
      List<JSONObject> json = this.analysisService.consumptionInterval(request, map);
      YZUtility.RETURN_LIST(json, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
    } 
    return null;
  }
}
