/**  
  *
  * @Title:  HUDH_DataAnalysisAct.java   
  * @Package com.hudh.sjtj.controller   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月23日 下午2:30:51   
  * @version V1.0  
  */ 
package com.hudh.sjtj.controller;

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

import com.kqds.util.sys.ConstUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hudh.sjtj.service.IDataAnalysisService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

/**  
  * syp 2019-9-23
  * @ClassName:  HUDH_DataAnalysisAct   
  * @Description:TODO(主要用于数据统计分析)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月23日 下午2:30:51   
  *      
  */
@Controller
@RequestMapping("HUDH_DataAnalysisAct")
public class HUDH_DataAnalysisAct {
	
	private static Logger logger = LoggerFactory.getLogger(HUDH_DataAnalysisAct.class);
	
	@Autowired
	private IDataAnalysisService analysisService;
	/**
	 * 
	 * <p>Title: toBb_BaseDataDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toBb_BaseDataDay.act")
	public ModelAndView toBb_BaseDataDay(HttpServletRequest request, HttpServletResponse response) {
		String timeMonth = request.getParameter("timeMonth");
		String qxname=request.getParameter("qxname");
		String deptCategory=request.getParameter("deptCategory");
		ModelAndView mv = new ModelAndView();
		mv.addObject("timeMonth", timeMonth);
		mv.addObject("qxname", qxname);
		mv.addObject("deptCategory", deptCategory);
		mv.setViewName("/kqdsFront/index/zxtj/baseDataDay.jsp");
		return mv;
	}
	/**
	 * 
	 * <p>Title: findCJStatistics</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年9月25日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCJStatisticsByMonth.act")
	public String findCJStatisticsByMonth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String organization =request.getParameter("organization");
		//ChainUtil.getCurrentOrganization(request);//获取当前门诊
		String startTime=request.getParameter("starttime");
		String endTime=request.getParameter("endtime");
		String qxname=request.getParameter("qxname");
		String deptCategory=request.getParameter("deptCategory");
		try {
			//初始页面加载查询所有员工
			List<JSONObject> list=new ArrayList<JSONObject>();
			Map<String,String> map =new  HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
			if(!YZUtility.isNullorEmpty(startTime)){
				map.put("startTime", startTime);	
			}
			if(!YZUtility.isNullorEmpty(endTime)){
				map.put("endTime", endTime);	
			}
			//合计
			int hjczAllNums=0;
			int hjczCjNum=0;
			double hjczPaymoney=0;
			double hjczJcfmoney=0;
			double hjczYjjmoney=0;
			int hjfzAllNums=0;
			int hjfzCjNum=0;
			double hjfzPaymoney=0;
			double hjfzJcfmoney=0;
			double hjfzYjjmoney=0;
			int hjzxfAllNums=0;
			int hjzxfCjNum=0;
			double hjzxfPaymoney=0;
			double hjCmoney=0;
			double hjzxfJcfmoney=0;
			double hjzxfYjjmoney=0;
			double hjTcmoney=0;
			double hjDrugsmoney=0;
			//查询天 、 月 、 年
			//查询天
			if(startTime.length()==10||endTime.length()==10){
				//判断是否为当天
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date(System.currentTimeMillis());
				String time = formatter.format(date);
				if(YZUtility.isNullorEmpty(endTime)&&!YZUtility.isNullorEmpty(startTime)){
					endTime=time;
				}else if(!YZUtility.isNullorEmpty(endTime)&&YZUtility.isNullorEmpty(startTime)){
					Long time1 = formatter.parse(endTime).getTime();
					Long time2 = formatter.parse(time).getTime();
					if(time1>=time2){
						startTime=time;
					}else{
						throw new Exception("请选择查询起始时间！");
					}
				}
				//同一天
				if(startTime.equals(endTime)){
					String sss="ISNULL(sum(case when  datepart(day,createtime)="+Integer.parseInt(startTime.substring(8, 10))+" then 1 else 0 end),0) as '"+startTime+"'";
					map.put("year", startTime.substring(0,4));
					map.put("month", startTime.substring(5,7));
					map.put("code", sss);
					map.put("startday",startTime.substring(8,10));
					map.put("endday", startTime.substring(8,10));
					map.put("years", "(year(r.createtime)="+startTime.substring(0,4)+")");
					map.put("months", "(month(r.createtime)="+startTime.substring(5,7)+")");
					map.put("costyears", "(year(cost.sftime)="+startTime.substring(0,4)+")");
					map.put("costmonths", "(month(cost.sftime)="+startTime.substring(5,7)+")");
					list=analysisService.findCJStatisticsByDay(request, map);
					for (JSONObject json : list) {
						if(json.getString("name").equals("合计")){
							hjczAllNums+=Integer.parseInt(json.getString("czperson"));
							hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
							hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
							hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
							hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
							hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
							hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
							hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
							hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
							hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
							hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
							hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
							hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
							hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
							hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
							hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
							hjCmoney+=Double.parseDouble(json.getString("cmoney"));
							hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
						}
					}
				//不同天
				}else{
					//是不是同一年
					if(startTime.substring(0, 4).equals(endTime.substring(0, 4))){
						//同一年
						//判断是不是同一月
						if(startTime.substring(5, 7).equals(endTime.substring(5, 7))){
							StringBuffer sss= new StringBuffer();
							for (int j =Integer.parseInt(startTime.substring(8,10)); j <= Integer.parseInt(endTime.substring(8,10)); j++) {
								if(j==Integer.parseInt(endTime.substring(8,10))){
									sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日'");
								}else{					
									sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日',");
								}
							}
							map.put("year", startTime.substring(0,4));
							map.put("month", startTime.substring(5,7));
							map.put("code", sss.toString());
							map.put("startday",startTime.substring(8,10));
							map.put("endday", endTime.substring(8,10));
							map.put("years", "(year(r.createtime)="+startTime.substring(0,4)+")");
							map.put("months", "(month(r.createtime)="+startTime.substring(5,7)+")");
							map.put("costyears", "(year(cost.sftime)="+startTime.substring(0,4)+")");
							map.put("costmonths", "(month(cost.sftime)="+startTime.substring(5,7)+")");
							list=analysisService.findCJStatisticsByDay(request, map);
							for (JSONObject json : list) {
								if(json.getString("name").equals("合计")){
									hjczAllNums+=Integer.parseInt(json.getString("czperson"));
									hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
									hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
									hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
									hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
									hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
									hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
									hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
									hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
									hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
									hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
									hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
									hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
									hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
									hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
									hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
									hjCmoney+=Double.parseDouble(json.getString("cmoney"));
									hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
								}
							}
						}else{
							
							for (int i = Integer.parseInt(startTime.substring(5, 7)); i <= Integer.parseInt(endTime.substring(5, 7)); i++) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
								Calendar calendar = Calendar.getInstance();
								//一个月的天数
								int s=0;
								//起始天
								int k=0;
								if(i == Integer.parseInt(startTime.substring(5, 7))){
									calendar.setTime(sdf.parse(startTime.substring(0, 7)));
									s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
									k=Integer.parseInt(startTime.substring(8,10));
								}else if(i > Integer.parseInt(startTime.substring(5, 7))&&i < Integer.parseInt(endTime.substring(5, 7))){
									calendar.setTime(sdf.parse(startTime.substring(0, 4)+"-"+i));
									s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
									k=1;
								}else{
									s=Integer.parseInt(endTime.substring(8, 10));
									k=1;
								}
								//查询月天数
								
								StringBuffer sss= new StringBuffer();
								for (int j =k; j <= s; j++) {
									if(j==s){
										sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日'");
									}else{					
										sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日',");
									}
								}
								map.put("year", startTime.substring(0,4));
								map.put("month",i+"");
								map.put("code", sss.toString());
								map.put("startday",k+"");
								map.put("endday", s+"");
								map.put("years", "(year(r.createtime)="+startTime.substring(0,4)+")");
								map.put("months", "(month(r.createtime)="+i+")");
								map.put("costyears", "(year(cost.sftime)="+startTime.substring(0,4)+")");
								map.put("costmonths", "(month(cost.sftime)="+i+")");
								List<JSONObject> list1=analysisService.findCJStatisticsByDay(request, map);
								list.addAll(list1);
								for (JSONObject json : list1) {
									if(json.getString("name").equals("合计")){
										hjczAllNums+=Integer.parseInt(json.getString("czperson"));
										hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
										hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
										hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
										hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
										hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
										hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
										hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
										hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
										hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
										hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
										hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
										hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
										hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
										hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
										hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
										hjCmoney+=Double.parseDouble(json.getString("cmoney"));
										hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
									}
								}
							}
						}
					}else{
						//不是同一年
						for (int k =Integer.parseInt(startTime.substring(0,4)); k <= Integer.parseInt(endTime.substring(0,4)); k++) {
								//第一年
							if(k ==Integer.parseInt(startTime.substring(0,4))){
								for (int i = Integer.parseInt(startTime.substring(5, 7)); i <= 12; i++) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
									Calendar calendar = Calendar.getInstance();
									int s=0;
									int l=0;
									if(i == Integer.parseInt(startTime.substring(5, 7))){
										calendar.setTime(sdf.parse(startTime.substring(0, 7)));
										s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
										l=Integer.parseInt(startTime.substring(8,10));
									}else if(i > Integer.parseInt(startTime.substring(5, 7))&&i <=12){
										calendar.setTime(sdf.parse(startTime.substring(0, 4)+"-"+i));
										s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
										l=1;
									}
									//查询月天数
									
									StringBuffer sss= new StringBuffer();
									for (int j =l; j <= s; j++) {
										if(j==s){
											sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日'");
										}else{					
											sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日',");
										}
									}
									map.put("year", startTime.substring(0,4));
									map.put("month", i+"");
									map.put("code", sss.toString());
									map.put("startday",l+"");
									map.put("endday", s+"");
									map.put("years", "(year(r.createtime)="+startTime.substring(0,4)+")");
									map.put("months", "(month(r.createtime)="+i+")");
									map.put("costyears", "(year(cost.sftime)="+startTime.substring(0,4)+")");
									map.put("costmonths", "(month(cost.sftime)="+i+")");
									List<JSONObject> list1=analysisService.findCJStatisticsByDay(request, map);
									list.addAll(list1);
									for (JSONObject json : list1) {
										if(json.getString("name").equals("合计")){
											hjczAllNums+=Integer.parseInt(json.getString("czperson"));
											hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
											hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
											hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
											hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
											hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
											hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
											hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
											hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
											hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
											hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
											hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
											hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
											hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
											hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
											hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
											hjCmoney+=Double.parseDouble(json.getString("cmoney"));
											hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
										}
									}
								}
								//中间年
							}else if(k>Integer.parseInt(startTime.substring(0,4))&&k <Integer.parseInt(endTime.substring(0,4))){
								for (int i = 1; i <= 12; i++) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(sdf.parse((Integer.parseInt(startTime.substring(0, 4))+k)+"-"+i));
									int	s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
									int	l=1;
									//查询月天数
									StringBuffer sss= new StringBuffer();
									for (int j =l; j <= s; j++) {
										if(j==s){
											sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日'");
										}else{					
											sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日',");
										}
									}
									map.put("year", (Integer.parseInt(startTime.substring(0, 4))+k)+"");
									map.put("month", i+"");
									map.put("code", sss.toString());
									map.put("startday",l+"");
									map.put("endday", s+"");
									map.put("years", "(year(r.createtime)="+(Integer.parseInt(startTime.substring(0, 4))+k)+")");
									map.put("months", "(month(r.createtime)="+i+")");
									map.put("costyears", "(year(cost.sftime)="+(Integer.parseInt(startTime.substring(0, 4))+k)+")");
									map.put("costmonths", "(month(cost.sftime)="+i+")");
									List<JSONObject> list1=analysisService.findCJStatisticsByDay(request, map);
									list.addAll(list1);
									for (JSONObject json : list1) {
										if(json.getString("name").equals("合计")){
											hjczAllNums+=Integer.parseInt(json.getString("czperson"));
											hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
											hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
											hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
											hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
											hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
											hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
											hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
											hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
											hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
											hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
											hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
											hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
											hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
											hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
											hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
											hjCmoney+=Double.parseDouble(json.getString("cmoney"));
											hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
										}
									}
								}
								//最后一年
							}else{
								for (int i = 1; i <= Integer.parseInt(endTime.substring(5, 7)); i++) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(sdf.parse(endTime.substring(0, 4)+"-"+i));
									int	s=0;
									if(i == Integer.parseInt(endTime.substring(5, 7))){
										s=Integer.parseInt(endTime.substring(8,10));
									}else{
										s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
									}
									int	l=1;
									//查询月天数
									StringBuffer sss= new StringBuffer();
									for (int j =l; j <= s; j++) {
										if(j==s){
											sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日'");
										}else{					
											sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日',");
										}
									}
									map.put("year", endTime.substring(0, 4));
									map.put("month", i+"");
									map.put("code", sss.toString());
									map.put("startday",l+"");
									map.put("endday", s+"");
									map.put("years", "(year(r.createtime)="+endTime.substring(0, 4)+")");
									map.put("months", "(month(r.createtime)="+i+")");
									map.put("costyears", "(year(cost.sftime)="+endTime.substring(0, 4)+")");
									map.put("costmonths", "(month(cost.sftime)="+i+")");
									List<JSONObject> list1=analysisService.findCJStatisticsByDay(request, map);
									list.addAll(list1);
									for (JSONObject json : list1) {
										if(json.getString("name").equals("合计")){
											hjczAllNums+=Integer.parseInt(json.getString("czperson"));
											hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
											hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
											hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
											hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
											hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
											hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
											hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
											hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
											hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
											hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
											hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
											hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
											hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
											hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
											hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
											hjCmoney+=Double.parseDouble(json.getString("cmoney"));
											hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
										}
									}
								}
							}
						}
					}
				}
				//月
			}else if(startTime.length()==7||endTime.length()==7){
				//判断是否为当天
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM");
				Date date = new Date(System.currentTimeMillis());
				String time = formatter.format(date);
				if(YZUtility.isNullorEmpty(endTime)&&!YZUtility.isNullorEmpty(startTime)){
					endTime=time;
				}else if(!YZUtility.isNullorEmpty(endTime)&&YZUtility.isNullorEmpty(startTime)){
					Long time1 = formatter.parse(endTime).getTime();
					Long time2 = formatter.parse(time).getTime();
					if(time1>=time2){
						startTime=time;
					}else{
						throw new Exception("请选择查询起始时间！");
					}
				}
				//不是同一年
				if(Integer.parseInt(startTime.substring(0, 4))<Integer.parseInt(endTime.substring(0, 4))){
					//截取每年的每个月的数据
					//间隔几年查询每一年月份数据
					for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); i++) {
						if(i==Integer.parseInt(startTime.substring(0, 4))){
							//查询第一年数据
							StringBuffer sss= new StringBuffer();
							for (int j = Integer.parseInt(startTime.substring(5, 7)); j < 13; j++) {
								if(j==12){
									sss.append("ISNULL(sum(case when  datepart(month,createtime)=12 then 1 else 0 end),0) as '12月'");
								}else{					
									sss.append("ISNULL(sum(case when  datepart(month,createtime)="+j+" then 1 else 0 end),0) as '"+j+"月',");
								}
							}
							map.put("year", i+"");
							//查询年的数据
							map.put("code", sss.toString());
							map.put("startmonth", Integer.parseInt(startTime.substring(5, 7))+"");
							map.put("endmonth", 12+"");
							map.put("years", "(year(r.createtime)="+i+")");
							map.put("costyears", "(year(cost.sftime)="+i+")");
							List<JSONObject> list1=analysisService.findCJStatisticsByMonth(request, map);
							list.addAll(list1);
							for (JSONObject json : list1) {
								if(json.getString("name").equals("合计")){
									hjczAllNums+=Integer.parseInt(json.getString("czperson"));
									hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
									hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
									hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
									hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
									hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
									hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
									hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
									hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
									hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
									hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
									hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
									hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
									hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
									hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
									hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
									hjCmoney+=Double.parseDouble(json.getString("cmoney"));
									hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
								}
							}
							
						}else if(i>Integer.parseInt(startTime.substring(0, 4))&&i<Integer.parseInt(endTime.substring(0, 4))){
							//查询间隔年限数据
							StringBuffer sss= new StringBuffer();
							for (int j = 1; j <= 12; j++) {
								if(j==12){
									sss.append("ISNULL(sum(case when  datepart(month,createtime)=12 then 1 else 0 end),0) as '12月'");
								}else{
									sss.append("ISNULL(sum(case when  datepart(month,createtime)="+j+" then 1 else 0 end),0) as '"+j+"月',");
								}
							}
							map.put("year", i+"");
							//查询年的数据
							map.put("code", sss.toString());
							map.put("startmonth", 1+"");
							map.put("endmonth", 12+"");
							map.put("years", "(year(r.createtime)="+i+")");
							map.put("costyears", "(year(cost.sftime)="+i+")");
							List<JSONObject> list2=analysisService.findCJStatisticsByMonth(request, map);
							list.addAll(list2);
							for (JSONObject json : list2) {
								if(json.getString("name").equals("合计")){
									hjczAllNums+=Integer.parseInt(json.getString("czperson"));
									hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
									hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
									hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
									hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
									hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
									hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
									hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
									hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
									hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
									hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
									hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
									hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
									hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
									hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
									hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
									hjCmoney+=Double.parseDouble(json.getString("cmoney"));
									hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
								}
							}
						}else if(i==Integer.parseInt(endTime.substring(0, 4))){
							//查询最后一年数据
							StringBuffer sss= new StringBuffer();
							for (int j = 1; j <= Integer.parseInt(endTime.substring(5, 7)); j++) {
								if(j==Integer.parseInt(endTime.substring(5, 7))){
									sss.append("ISNULL(sum(case when  datepart(month,createtime)="+j+" then 1 else 0 end),0) as '"+j+"月'");
								}else{
									sss.append("ISNULL(sum(case when  datepart(month,createtime)="+j+" then 1 else 0 end),0) as '"+j+"月',");
								}
							}
							map.put("year", i+"");
							//查询年的数据
							map.put("code", sss.toString());
							map.put("startmonth", 1+"");
							map.put("endmonth", Integer.parseInt(endTime.substring(5, 7))+"");
							map.put("years", "(year(r.createtime)="+i+")");
							map.put("costyears", "(year(cost.sftime)="+i+")");
							List<JSONObject> list3=analysisService.findCJStatisticsByMonth(request, map);
							list.addAll(list3);
							for (JSONObject json : list3) {
								if(json.getString("name").equals("合计")){
									hjczAllNums+=Integer.parseInt(json.getString("czperson"));
									hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
									hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
									hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
									hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
									hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
									hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
									hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
									hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
									hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
									hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
									hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
									hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
									hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
									hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
									hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
									hjCmoney+=Double.parseDouble(json.getString("cmoney"));
									hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
								}
							}
						}
					}
					//同年不是同一月
				}else if(Integer.parseInt(startTime.substring(5, 7))<Integer.parseInt(endTime.substring(5, 7))){
					StringBuffer sss= new StringBuffer();
					int aa=0;
					for (int j = Integer.parseInt(startTime.substring(5, 7)); j <= Integer.parseInt(endTime.substring(5, 7)); j++) {
						if(j==Integer.parseInt(endTime.substring(5, 7))){
							sss.append("ISNULL(sum(case when  datepart(month,createtime)="+Integer.parseInt(endTime.substring(5, 7))+" then 1 else 0 end),0) as '"+Integer.parseInt(endTime.substring(5, 7))+"月'");
						}else{					
							sss.append("ISNULL(sum(case when  datepart(month,createtime)="+((Integer.parseInt(startTime.substring(5, 7))+aa)+"")+" then 1 else 0 end),0) as '"+((Integer.parseInt(startTime.substring(5, 7))+aa)+"")+"月',");
						}
						aa+=1;
					}
					map.put("year", endTime.substring(0, 4));
					map.put("code", sss.toString());
					map.put("startmonth", Integer.parseInt(startTime.substring(5, 7))+"");
					map.put("endmonth", Integer.parseInt(endTime.substring(5, 7))+"");
					map.put("years", "(year(r.createtime)="+endTime.substring(0, 4)+")");
					map.put("costyears", "(year(cost.sftime)="+endTime.substring(0, 4)+")");
					list=analysisService.findCJStatisticsByMonth(request, map);
					for (JSONObject json : list) {
						if(json.getString("name").equals("合计")){
							hjczAllNums+=Integer.parseInt(json.getString("czperson"));
							hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
							hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
							hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
							hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
							hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
							hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
							hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
							hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
							hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
							hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
							hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
							hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
							hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
							hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
							hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
							hjCmoney+=Double.parseDouble(json.getString("cmoney"));
							hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
						}
					}
				//同年同月
				}else{
					map.put("year", endTime.substring(0, 4)+"");
					map.put("code", "ISNULL(sum(case when  datepart(month,createtime)="+Integer.parseInt(endTime.substring(5, 7))+" then 1 else 0 end),0) as '"+Integer.parseInt(endTime.substring(5, 7))+"月'");
					map.put("startmonth", Integer.parseInt(startTime.substring(5, 7))+"");
					map.put("endmonth", Integer.parseInt(endTime.substring(5, 7))+"");
					map.put("years", "(year(r.createtime)="+endTime.substring(0, 4)+")");
					map.put("costyears", "(year(cost.sftime)="+endTime.substring(0, 4)+")");
					list=analysisService.findCJStatisticsByMonth(request, map);
					for (JSONObject json : list) {
						if(json.getString("name").equals("合计")){
							hjczAllNums+=Integer.parseInt(json.getString("czperson"));
							hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
							hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
							hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
							hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
							hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
							hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
							hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
							hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
							hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
							hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
							hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
							hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
							hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
							hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
							hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
							hjCmoney+=Double.parseDouble(json.getString("cmoney"));
							hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
						}
					}
				}	
				//年
			}else if(startTime.length()==4||endTime.length()==4){
				//判断是否为当天
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
				Date date = new Date(System.currentTimeMillis());
				String time = formatter.format(date);
				if(YZUtility.isNullorEmpty(endTime)&&!YZUtility.isNullorEmpty(startTime)){
					endTime=time;
				}else if(!YZUtility.isNullorEmpty(endTime)&&YZUtility.isNullorEmpty(startTime)){
					Long time1 = formatter.parse(endTime).getTime();
					Long time2 = formatter.parse(time).getTime();
					if(time1>=time2){
						startTime=time;
					}else{
						throw new Exception("请选择查询起始时间！");
					}
				}
				//是不是同一年
				if(startTime.equals(endTime)){
					map.put("code", "ISNULL(sum(case when  datepart(year,createtime)="+endTime+" then 1 else 0 end),0) as '"+endTime+"年'");
					map.put("startyear", startTime);
					map.put("endyear", startTime);
					list=analysisService.findCJStatisticsByYear(request, map);
					for (JSONObject json : list) {
						hjczAllNums+=Integer.parseInt(json.getString("czperson"));
						hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
						hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
						hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
						hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
						hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
						hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
						hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
						hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
						hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
						hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
						hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
						hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
						hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
						hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
						hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
						hjCmoney+=Double.parseDouble(json.getString("cmoney"));
						hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
					}
				}else{
					StringBuffer sss= new StringBuffer();
					for (int i = Integer.parseInt(startTime); i <= Integer.parseInt(endTime); i++) {
						if(i==Integer.parseInt(endTime)){
							sss.append("ISNULL(sum(case when  datepart(year,createtime)="+endTime+" then 1 else 0 end),0) as '"+endTime+"年'");
						}else{
							sss.append("ISNULL(sum(case when  datepart(year,createtime)="+i+" then 1 else 0 end),0) as '"+i+"年',");
						}
					}
					map.put("code", sss.toString());
					map.put("startyear", startTime);
					map.put("endyear", endTime);
					list=analysisService.findCJStatisticsByYear(request, map);
					for (JSONObject json : list) {
						hjczAllNums+=Integer.parseInt(json.getString("czperson"));
						hjczCjNum+=Integer.parseInt(json.getString("czcjperson"));
						hjczPaymoney+=Double.parseDouble(json.getString("czpaymoney"));
						hjczJcfmoney+=Double.parseDouble(json.getString("czjcfmoney"));
						hjczYjjmoney+=Double.parseDouble(json.getString("czyjjmoney"));
						hjfzAllNums+=Integer.parseInt(json.getString("fzperson"));
						hjfzCjNum+=Integer.parseInt(json.getString("fzcjperson"));
						hjfzPaymoney+=Double.parseDouble(json.getString("fzpaymoney"));
						hjfzJcfmoney+=Double.parseDouble(json.getString("fzjcfmoney"));
						hjfzYjjmoney+=Double.parseDouble(json.getString("fzyjjmoney"));
						hjzxfAllNums+=Integer.parseInt(json.getString("zxfperson"));
						hjzxfCjNum+=Integer.parseInt(json.getString("zxfcjperson"));
						hjzxfPaymoney+=Double.parseDouble(json.getString("zxfpaymoney"));
						hjzxfJcfmoney+=Double.parseDouble(json.getString("zxfjcfmoney"));
						hjzxfYjjmoney+=Double.parseDouble(json.getString("zxfyjjmoney"));
						hjDrugsmoney+=Double.parseDouble(json.getString("drugsmoney"));
						hjCmoney+=Double.parseDouble(json.getString("cmoney"));
						hjTcmoney+=Double.parseDouble(json.getString("tcmoney"));
					}
				}
				
			}
			JSONObject jsonobject=new JSONObject();
			jsonobject.put("month","-");
			jsonobject.put("name", "合计");
			jsonobject.put("czperson", hjczAllNums+"");
			jsonobject.put("czcjperson", hjczCjNum+"");
			NumberFormat nf1 = NumberFormat.getInstance();
			nf1.setGroupingUsed(false);
			if(hjczPaymoney>0){
				jsonobject.put("czpaymoney",nf1.format(hjczPaymoney));
			}else{
				jsonobject.put("czpaymoney", "0.00");
			}
			if(hjczJcfmoney>0){
				jsonobject.put("czjcfmoney",nf1.format(hjczJcfmoney));
			}else{
				jsonobject.put("czjcfmoney", "0.00");
			}
			if(hjczYjjmoney>0){
				jsonobject.put("czyjjmoney",nf1.format(hjczYjjmoney));
			}else{
				jsonobject.put("czyjjmoney", "0.00");
			}
			jsonobject.put("fzperson", hjfzAllNums+"");
			jsonobject.put("fzcjperson", hjfzCjNum+"");
			if(hjfzPaymoney>0){
				jsonobject.put("fzpaymoney", nf1.format(hjfzPaymoney));
			}else{
				jsonobject.put("fzpaymoney", "0.00");
			}
			if(hjfzJcfmoney>0){
				jsonobject.put("fzjcfmoney", nf1.format(hjfzJcfmoney));
			}else{
				jsonobject.put("fzjcfmoney", "0.00");
			}
			if(hjfzYjjmoney>0){
				jsonobject.put("fzyjjmoney", nf1.format(hjfzYjjmoney));
			}else{
				jsonobject.put("fzyjjmoney", "0.00");
			}
			jsonobject.put("zxfperson", hjzxfAllNums+"");
			jsonobject.put("zxfcjperson", hjzxfCjNum+"");
			if(hjzxfPaymoney>0){
				jsonobject.put("zxfpaymoney", nf1.format(hjzxfPaymoney));
				
			}else{
				jsonobject.put("zxfpaymoney", "0.00");
			}
			if(hjzxfJcfmoney>0){
				jsonobject.put("zxfjcfmoney", nf1.format(hjzxfJcfmoney));
				
			}else{
				jsonobject.put("zxfjcfmoney", "0.00");
			}
			if(hjzxfYjjmoney>0){
				jsonobject.put("zxfyjjmoney", nf1.format(hjzxfYjjmoney));
				
			}else{
				jsonobject.put("zxfyjjmoney", "0.00");
			}
			if(hjCmoney>0){
				jsonobject.put("cmoney", nf1.format(hjCmoney));
				
			}else{
				jsonobject.put("cmoney", "0.00");
			}
			if(hjTcmoney<0){
				jsonobject.put("tcmoney", nf1.format(hjTcmoney));
				
			}else{
				jsonobject.put("tcmoney", "0.00");
			}
			if(hjDrugsmoney>0){
				jsonobject.put("drugsmoney", nf1.format(hjDrugsmoney));
				
			}else{
				jsonobject.put("drugsmoney", "0.00");
			}
			if(hjczAllNums>0){
				jsonobject.put("czcjratio", (double)hjczCjNum/hjczAllNums+"");
			}else{
				jsonobject.put("czcjratio", "0");
			}
			if(hjfzAllNums>0){
				jsonobject.put("fzcjratio", (double)hjfzCjNum/hjfzAllNums+"");
			}else{
				jsonobject.put("fzcjratio", "0");
			}
			if(hjzxfAllNums>0){
				jsonobject.put("zxfcjratio", (double)hjzxfCjNum/hjzxfAllNums+"");
			}else{
				jsonobject.put("zxfcjratio", "0");
			}
			jsonobject.put("xmpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))));
			//jsonobject.put("zjpaymoney", ""+(Double.parseDouble(jsonobject.getString("czpaymoney"))+Double.parseDouble(jsonobject.getString("fzpaymoney"))+Double.parseDouble(jsonobject.getString("zxfpaymoney"))+Double.parseDouble(jsonobject.getString("cmoney"))));
			list.add(jsonobject);
			
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, true, e, response, logger);
		}
		
		return null;
	}
	@RequestMapping(value = "/findCJStatisticsByDay.act")
	public String findCJStatisticsByDay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String organization = request.getParameter("organization");
		String timeMonth=request.getParameter("timeMonth");
		String qxname=request.getParameter("qxname");
		String deptCategory=request.getParameter("deptCategory");
		try {
			Map<String,String> map =new  HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}	
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
			if(!YZUtility.isNullorEmpty(timeMonth)){
				map.put("startTime", timeMonth);	
			}
			List<JSONObject> list=new ArrayList<JSONObject>();
			if(timeMonth.length()==7||timeMonth.length()==8){
				//判断是否为当月
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date(System.currentTimeMillis());
				String time = formatter.format(date);
				//当月数据
				if(timeMonth.substring(0,4).equals(time.substring(0,4))&&timeMonth.substring(5,6).equals(Integer.parseInt(time.substring(5,7))+"")||timeMonth.substring(0,4).equals(time.substring(0,4))&&timeMonth.substring(5,7).equals(Integer.parseInt(time.substring(5,7))+"")){
					StringBuffer sss= new StringBuffer();
					for (int j =1 ; j <= Integer.parseInt(time.substring(8, 10)); j++) {
						if(j==Integer.parseInt(time.substring(8, 10))){
							sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日'");
						}else{					
							sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日',");
						}
					}
					map.put("year", time.substring(0,4));
					map.put("month", time.substring(5,7));
					map.put("code", sss.toString());
					map.put("startday",1+"");
					map.put("endday", time.substring(8,10));
					map.put("years", "(year(r.createtime)="+time.substring(0,4)+")");
					map.put("months", "(month(r.createtime)="+time.substring(5,7)+")");
					map.put("costyears", "(year(cost.sftime)="+time.substring(0,4)+")");
					map.put("costmonths", "(month(cost.sftime)="+time.substring(5,7)+")");
					list=analysisService.findCJStatisticsByDay(request, map);
					//不是当月数据
				}else{
					//获取月份
					int i=0;
					String month="";
					if(timeMonth.substring(5,7).contains("月")){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(sdf.parse(timeMonth.substring(0,4)+"-"+timeMonth.substring(5, 6)));
						i=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
						month=timeMonth.substring(5, 6);
					}else{
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(sdf.parse(timeMonth.substring(0,4)+"-"+timeMonth.substring(5, 6)));
						i=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
						month=timeMonth.substring(5, 7);
					}
					StringBuffer sss= new StringBuffer();
					for (int j =1 ; j <= i; j++) {
						if(j==i){
							sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日'");
						}else{					
							sss.append("ISNULL(sum(case when  datepart(day,createtime)="+j+" then 1 else 0 end),0) as '"+j+"日',");
						}
					}
					map.put("year", timeMonth.substring(0,4));
					map.put("month", month);
					map.put("code", sss.toString());
					map.put("startday",1+"");
					map.put("endday",i+"");
					map.put("years", "(year(r.createtime)="+timeMonth.substring(0,4)+")");
					map.put("months", "(month(r.createtime)="+month+")");
					map.put("costyears", "(year(cost.sftime)="+timeMonth.substring(0,4)+")");
					map.put("costmonths", "(month(cost.sftime)="+month+")");
					list=analysisService.findCJStatisticsByDay(request, map);
				}
				//一年查询月份
			}else if(timeMonth.length()==5){
				//判断是否为当年
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
				Date date = new Date(System.currentTimeMillis());
				String time = formatter.format(date);
				//当前年
				if(time.equals(timeMonth.substring(0, 4))){
					SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM");
					Date date1 = new Date(System.currentTimeMillis());
					String time1 = formatter1.format(date1);
					StringBuffer sss= new StringBuffer();
					for (int j =1 ; j <= Integer.parseInt(time1.substring(5, 7)); j++) {
						if(j==Integer.parseInt(time1.substring(5, 7))){
							sss.append("ISNULL(sum(case when  datepart(month,createtime)="+j+" then 1 else 0 end),0) as '"+j+"月'");
						}else{					
							sss.append("ISNULL(sum(case when  datepart(month,createtime)="+j+" then 1 else 0 end),0) as '"+j+"月',");
						}
					}
					map.put("year", timeMonth.substring(0,4));
					map.put("code", sss.toString());
					map.put("startmonth",1+"");
					map.put("endmonth", Integer.parseInt(time1.substring(5, 7))+"");
					map.put("years", "(year(r.createtime)="+timeMonth.substring(0,4)+")");
					map.put("costyears", "(year(cost.sftime)="+timeMonth.substring(0,4)+")");
					list=analysisService.findCJQuantityByAskpersonAndMonthInYear(request, map);
				}else{
					StringBuffer sss= new StringBuffer();
					for (int j =1 ; j <= 12; j++) {
						if(j==12){
							sss.append("ISNULL(sum(case when  datepart(month,createtime)="+j+" then 1 else 0 end),0) as '"+j+"月'");
						}else{					
							sss.append("ISNULL(sum(case when  datepart(month,createtime)="+j+" then 1 else 0 end),0) as '"+j+"月',");
						}
					}
					map.put("year", timeMonth.substring(0,4));
					map.put("code", sss.toString());
					map.put("startmonth",1+"");
					map.put("endmonth", 12+"");
					map.put("years", "(year(r.createtime)="+timeMonth.substring(0,4)+")");
					map.put("costyears", "(year(cost.sftime)="+timeMonth.substring(0,4)+")");
					list=analysisService.findCJQuantityByAskpersonAndMonthInYear(request, map);
				}
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, true, e, response, logger);
		}
		
		return null;
	}
	
	/**
	  * @Title: volumeTransaction   
	  * @Description: TODO(咨询成交)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年9月26日 下午4:00:43
	 */
	@RequestMapping("/volumeTransaction.act")
	public String volumeTransaction(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = request.getParameter("organization");
			String qxname=request.getParameter("qxname");
			String deptCategory=request.getParameter("deptCategory");
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}	
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
				if(endtime != null && !endtime.equals("")){
					map.put("year", Integer.parseInt(endtime.substring(0, 4))+"");
				}
				
				map.put("code", "ISNULL(sum(case when  datepart(month,createtime)="+(Integer.parseInt(endtime.substring(5, 7))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(endtime.substring(5, 7))+"")+"月'");
				map.put("startmonth", Integer.parseInt(starttime.substring(5, 7))+"");
				map.put("endmonth", Integer.parseInt(endtime.substring(5, 7))+"");
				map.put("years", "(year(r.createtime)="+(Integer.parseInt(endtime.substring(0, 4))+"")+")");
			List<JSONObject> json = analysisService.findCjsCale(request,map);
			YZUtility.RETURN_LIST(json, response, logger);
			
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
		}
		return null;
	}
	@RequestMapping(value = "/findAllCJStatisticsByMonth.act")
	public String findAllCJStatisticsByMonth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//门诊
		String organization = request.getParameter("organization");
		//开始时间
		String startTime=request.getParameter("starttime");
		//结束时间
		String endTime=request.getParameter("endtime");
		//按钮标识
		String qxname=request.getParameter("qxname");
		//部门id
		String deptCategory=request.getParameter("deptCategory");
		//人员id
		String personId=request.getParameter("recesort");
		try {
			List<JSONObject> list=new ArrayList<JSONObject>();
			Map<String,String> map =new  HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
			if(!YZUtility.isNullorEmpty(personId)){
				map.put("personId", personId);	
			}
			if(!YZUtility.isNullorEmpty(startTime)){
				map.put("startTime", startTime);	
			}
			if(!YZUtility.isNullorEmpty(endTime)){
				map.put("endTime", endTime);	
			}
			//查询天 、 月 、 年
			//查询天
			if(startTime.length()==10||endTime.length()==10){
				//判断是否为当天
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date(System.currentTimeMillis());
				String time = formatter.format(date);
				if(YZUtility.isNullorEmpty(endTime)&&!YZUtility.isNullorEmpty(startTime)){
					endTime=time;
				}else if(!YZUtility.isNullorEmpty(endTime)&&YZUtility.isNullorEmpty(startTime)){
					Long time1 = formatter.parse(endTime).getTime();
					Long time2 = formatter.parse(time).getTime();
					if(time1>=time2){
						startTime=time;
					}else{
						throw new Exception("请选择查询起始时间！");
					}
				}
				//同一天
				if(startTime.equals(endTime)){
					map.put("year", startTime.substring(0,4));
					map.put("month", startTime.substring(5,7));
					map.put("startday",startTime.substring(8,10));
					map.put("endday", startTime.substring(8,10));
					map.put("years", "(year(r.createtime)="+startTime.substring(0,4)+")");
					map.put("months", "(month(r.createtime)="+startTime.substring(5,7)+")");
					map.put("costyears", "(year(cost.sftime)="+startTime.substring(0,4)+")");
					map.put("costmonths", "(month(cost.sftime)="+startTime.substring(5,7)+")");
					list=analysisService.findAllCJStatisticsByDay(request, map);
				//不同天
				}else{
					//是不是同一年
					if(startTime.substring(0, 4).equals(endTime.substring(0, 4))){
						//同一年
						//同年同月
						if(startTime.substring(5, 7).equals(endTime.substring(5, 7))){
							map.put("year", startTime.substring(0,4));
							map.put("month", startTime.substring(5,7));
							map.put("startday",startTime.substring(8,10));
							map.put("endday", endTime.substring(8,10));
							map.put("years", "(year(r.createtime)="+startTime.substring(0,4)+")");
							map.put("months", "(month(r.createtime)="+startTime.substring(5,7)+")");
							map.put("costyears", "(year(cost.sftime)="+startTime.substring(0,4)+")");
							map.put("costmonths", "(month(cost.sftime)="+startTime.substring(5,7)+")");
							list=analysisService.findAllCJStatisticsByDay(request, map);
							
						}else{
							for (int i = Integer.parseInt(startTime.substring(5, 7)); i <= Integer.parseInt(endTime.substring(5, 7)); i++) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
								Calendar calendar = Calendar.getInstance();
								//一个月的天数
								int s=0;
								//起始天
								int k=0;
								if(i == Integer.parseInt(startTime.substring(5, 7))){
									calendar.setTime(sdf.parse(startTime.substring(0, 7)));
									s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
									k=Integer.parseInt(startTime.substring(8,10));
								}else if(i > Integer.parseInt(startTime.substring(5, 7))&&i < Integer.parseInt(endTime.substring(5, 7))){
									calendar.setTime(sdf.parse(startTime.substring(0, 4)+"-"+i));
									s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
									k=1;
								}else{
									s=Integer.parseInt(endTime.substring(8, 10));
									k=1;
								}
								map.put("year", startTime.substring(0,4));
								map.put("month",i+"");
								map.put("startday",k+"");
								map.put("endday", s+"");
								map.put("years", "(year(r.createtime)="+startTime.substring(0,4)+")");
								map.put("months", "(month(r.createtime)="+i+")");
								map.put("costyears", "(year(cost.sftime)="+startTime.substring(0,4)+")");
								map.put("costmonths", "(month(cost.sftime)="+i+")");
								List<JSONObject> list1=analysisService.findAllCJStatisticsByDay(request, map);
								if(list.size()==0){
									list.addAll(list1);	
								}else{
									for (int j = 0; j < list.size(); j++) {
										list.get(j).put("month", "-");
										list.get(j).put("name", list.get(j).getString("name"));
										list.get(j).put("czperson", list.get(j).getInt("czperson")+list1.get(j).getInt("czperson")+"");
										list.get(j).put("czcjperson", list.get(j).getInt("czcjperson")+list1.get(j).getInt("czcjperson")+"");
										if(Integer.parseInt(list.get(j).getString("czperson"))>0){
											list.get(j).put("czcjratio", Double.parseDouble(list.get(j).getString("czcjperson"))/Double.parseDouble(list.get(j).getString("czperson"))+"");
										}else{
											list.get(j).put("czcjratio", "0");
										}
										list.get(j).put("czpaymoney", list.get(j).getDouble("czpaymoney")+list1.get(j).getDouble("czpaymoney")+"");
										list.get(j).put("czjcfmoney", list.get(j).getDouble("czjcfmoney")+list1.get(j).getDouble("czjcfmoney")+"");
										list.get(j).put("czyjjmoney", list.get(j).getDouble("czyjjmoney")+list1.get(j).getDouble("czyjjmoney")+"");
										list.get(j).put("fzperson", list.get(j).getInt("fzperson")+list1.get(j).getInt("fzperson")+"");
										list.get(j).put("fzcjperson", list.get(j).getInt("fzcjperson")+list1.get(j).getInt("fzcjperson")+"");
										if(Integer.parseInt(list.get(j).getString("fzperson"))>0){
											list.get(j).put("fzcjratio", Double.parseDouble(list.get(j).getString("fzcjperson"))/Double.parseDouble(list.get(j).getString("fzperson"))+"");
										}else{
											list.get(j).put("fzcjratio", "0");
										}
										list.get(j).put("fzpaymoney", list.get(j).getDouble("fzpaymoney")+list1.get(j).getDouble("fzpaymoney")+"");
										list.get(j).put("fzjcfmoney", list.get(j).getDouble("fzjcfmoney")+list1.get(j).getDouble("fzjcfmoney")+"");
										list.get(j).put("fzyjjmoney", list.get(j).getDouble("fzyjjmoney")+list1.get(j).getDouble("fzyjjmoney")+"");
										list.get(j).put("zxfperson", list.get(j).getInt("zxfperson")+list1.get(j).getInt("zxfperson")+"");
										list.get(j).put("zxfcjperson", list.get(j).getInt("zxfcjperson")+list1.get(j).getInt("zxfcjperson")+"");
										if(Integer.parseInt(list.get(j).getString("zxfperson"))>0){
											list.get(j).put("zxfcjratio", Double.parseDouble(list.get(j).getString("zxfcjperson"))/Double.parseDouble(list.get(j).getString("zxfperson"))+"");
										}else{
											list.get(j).put("zxfcjratio", "0");
										}
										list.get(j).put("cmoney", list.get(j).getDouble("cmoney")+list1.get(j).getDouble("cmoney")+"");
										list.get(j).put("tcmoney", list.get(j).getDouble("tcmoney")+list1.get(j).getDouble("tcmoney")+"");
										list.get(j).put("zxfpaymoney", list.get(j).getDouble("zxfpaymoney")+list1.get(j).getDouble("zxfpaymoney")+"");
										list.get(j).put("zxfjcfmoney", list.get(j).getDouble("zxfjcfmoney")+list1.get(j).getDouble("zxfjcfmoney")+"");
										list.get(j).put("zxfyjjmoney", list.get(j).getDouble("zxfyjjmoney")+list1.get(j).getDouble("zxfyjjmoney")+"");
										list.get(j).put("drugsmoney", list.get(j).getDouble("drugsmoney")+list1.get(j).getDouble("drugsmoney")+"");
										list.get(j).put("xmpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))));
										//list.get(j).put("zjpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))+Double.parseDouble(list.get(j).getString("cmoney"))));
									}
								}
								
							}
						}
					}else{
						//不是同一年
						for (int k =Integer.parseInt(startTime.substring(0,4)); k <= Integer.parseInt(endTime.substring(0,4)); k++) {
								//第一年
							if(k ==Integer.parseInt(startTime.substring(0,4))){
								for (int i = Integer.parseInt(startTime.substring(5, 7)); i <= 12; i++) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
									Calendar calendar = Calendar.getInstance();
									int s=0;
									int l=0;
									if(i == Integer.parseInt(startTime.substring(5, 7))){
										calendar.setTime(sdf.parse(startTime.substring(0, 7)));
										s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
										l=Integer.parseInt(startTime.substring(8,10));
									}else if(i > Integer.parseInt(startTime.substring(5, 7))&&i <=12){
										calendar.setTime(sdf.parse(startTime.substring(0, 4)+"-"+i));
										s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
										l=1;
									}
									//查询月天数
									map.put("year", startTime.substring(0,4));
									map.put("month", i+"");
									map.put("startday",l+"");
									map.put("endday", s+"");
									map.put("years", "(year(r.createtime)="+startTime.substring(0,4)+")");
									map.put("months", "(month(r.createtime)="+i+")");
									map.put("costyears", "(year(cost.sftime)="+startTime.substring(0,4)+")");
									map.put("costmonths", "(month(cost.sftime)="+i+")");
									List<JSONObject> list1=analysisService.findAllCJStatisticsByDay(request, map);
									if(list.size()==0){
										list.addAll(list1);	
									}else{
										for (int j = 0; j < list.size(); j++) {
											list.get(j).put("month", "-");
											list.get(j).put("name", list.get(j).getString("name"));
											list.get(j).put("czperson", list.get(j).getInt("czperson")+list1.get(j).getInt("czperson")+"");
											list.get(j).put("czcjperson", list.get(j).getInt("czcjperson")+list1.get(j).getInt("czcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("czperson"))>0){
												list.get(j).put("czcjratio", Double.parseDouble(list.get(j).getString("czcjperson"))/Double.parseDouble(list.get(j).getString("czperson"))+"");
											}else{
												list.get(j).put("czcjratio", "0");
											}
											list.get(j).put("czpaymoney", list.get(j).getDouble("czpaymoney")+list1.get(j).getDouble("czpaymoney")+"");
											list.get(j).put("czjcfmoney", list.get(j).getDouble("czjcfmoney")+list1.get(j).getDouble("czjcfmoney")+"");
											list.get(j).put("czyjjmoney", list.get(j).getDouble("czyjjmoney")+list1.get(j).getDouble("czyjjmoney")+"");
											list.get(j).put("fzperson", list.get(j).getInt("fzperson")+list1.get(j).getInt("fzperson")+"");
											list.get(j).put("fzcjperson", list.get(j).getInt("fzcjperson")+list1.get(j).getInt("fzcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("fzperson"))>0){
												list.get(j).put("fzcjratio", Double.parseDouble(list.get(j).getString("fzcjperson"))/Double.parseDouble(list.get(j).getString("fzperson"))+"");
											}else{
												list.get(j).put("fzcjratio", "0");
											}
											list.get(j).put("fzpaymoney", list.get(j).getDouble("fzpaymoney")+list1.get(j).getDouble("fzpaymoney")+"");
											list.get(j).put("fzjcfmoney", list.get(j).getDouble("fzjcfmoney")+list1.get(j).getDouble("fzjcfmoney")+"");
											list.get(j).put("fzyjjmoney", list.get(j).getDouble("fzyjjmoney")+list1.get(j).getDouble("fzyjjmoney")+"");
											list.get(j).put("zxfperson", list.get(j).getInt("zxfperson")+list1.get(j).getInt("zxfperson")+"");
											list.get(j).put("zxfcjperson", list.get(j).getInt("zxfcjperson")+list1.get(j).getInt("zxfcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("zxfperson"))>0){
												list.get(j).put("zxfcjratio", Double.parseDouble(list.get(j).getString("zxfcjperson"))/Double.parseDouble(list.get(j).getString("zxfperson"))+"");
											}else{
												list.get(j).put("zxfcjratio", "0");
											}
											list.get(j).put("cmoney", list.get(j).getDouble("cmoney")+list1.get(j).getDouble("cmoney")+"");
											list.get(j).put("tcmoney", list.get(j).getDouble("tcmoney")+list1.get(j).getDouble("tcmoney")+"");
											list.get(j).put("zxfpaymoney", list.get(j).getDouble("zxfpaymoney")+list1.get(j).getDouble("zxfpaymoney")+"");
											list.get(j).put("zxfjcfmoney", list.get(j).getDouble("zxfjcfmoney")+list1.get(j).getDouble("zxfjcfmoney")+"");
											list.get(j).put("zxfyjjmoney", list.get(j).getDouble("zxfyjjmoney")+list1.get(j).getDouble("zxfyjjmoney")+"");
											list.get(j).put("drugsmoney", list.get(j).getDouble("drugsmoney")+list1.get(j).getDouble("drugsmoney")+"");
											list.get(j).put("xmpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))));
											//list.get(j).put("zjpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))+Double.parseDouble(list.get(j).getString("cmoney"))));
										}
									}
									
								}
								//中间年
							}else if(k>Integer.parseInt(startTime.substring(0,4))&&k <Integer.parseInt(endTime.substring(0,4))){
								for (int i = 1; i <= 12; i++) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(sdf.parse((Integer.parseInt(startTime.substring(0, 4))+k)+"-"+i));
									int	s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
									int	l=1;
									//查询月天数
									map.put("year", (Integer.parseInt(startTime.substring(0, 4))+k)+"");
									map.put("month", i+"");
									map.put("startday",l+"");
									map.put("endday", s+"");
									map.put("years", "(year(r.createtime)="+(Integer.parseInt(startTime.substring(0, 4))+k)+")");
									map.put("months", "(month(r.createtime)="+i+")");
									map.put("costyears", "(year(cost.sftime)="+(Integer.parseInt(startTime.substring(0, 4))+k)+")");
									map.put("costmonths", "(month(cost.sftime)="+i+")");
									List<JSONObject> list1=analysisService.findAllCJStatisticsByDay(request, map);
									if(list.size()==0){
										list.addAll(list1);	
									}else{
										for (int j = 0; j < list.size(); j++) {
											list.get(j).put("month", "-");
											list.get(j).put("name", list.get(j).getString("name"));
											list.get(j).put("czperson", list.get(j).getInt("czperson")+list1.get(j).getInt("czperson")+"");
											list.get(j).put("czcjperson", list.get(j).getInt("czcjperson")+list1.get(j).getInt("czcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("czperson"))>0){
												list.get(j).put("czcjratio", Double.parseDouble(list.get(j).getString("czcjperson"))/Double.parseDouble(list.get(j).getString("czperson"))+"");
											}else{
												list.get(j).put("czcjratio", "0");
											}
											list.get(j).put("czpaymoney", list.get(j).getDouble("czpaymoney")+list1.get(j).getDouble("czpaymoney")+"");
											list.get(j).put("czjcfmoney", list.get(j).getDouble("czjcfmoney")+list1.get(j).getDouble("czjcfmoney")+"");
											list.get(j).put("czyjjmoney", list.get(j).getDouble("czyjjmoney")+list1.get(j).getDouble("czyjjmoney")+"");
											list.get(j).put("fzperson", list.get(j).getInt("fzperson")+list1.get(j).getInt("fzperson")+"");
											list.get(j).put("fzcjperson", list.get(j).getInt("fzcjperson")+list1.get(j).getInt("fzcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("fzperson"))>0){
												list.get(j).put("fzcjratio", Double.parseDouble(list.get(j).getString("fzcjperson"))/Double.parseDouble(list.get(j).getString("fzperson"))+"");
											}else{
												list.get(j).put("fzcjratio", "0");
											}
											list.get(j).put("fzpaymoney", list.get(j).getDouble("fzpaymoney")+list1.get(j).getDouble("fzpaymoney")+"");
											list.get(j).put("fzjcfmoney", list.get(j).getDouble("fzjcfmoney")+list1.get(j).getDouble("fzjcfmoney")+"");
											list.get(j).put("fzyjjmoney", list.get(j).getDouble("fzyjjmoney")+list1.get(j).getDouble("fzyjjmoney")+"");
											list.get(j).put("zxfperson", list.get(j).getInt("zxfperson")+list1.get(j).getInt("zxfperson")+"");
											list.get(j).put("zxfcjperson", list.get(j).getInt("zxfcjperson")+list1.get(j).getInt("zxfcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("zxfperson"))>0){
												list.get(j).put("zxfcjratio", Double.parseDouble(list.get(j).getString("zxfcjperson"))/Double.parseDouble(list.get(j).getString("zxfperson"))+"");
											}else{
												list.get(j).put("zxfcjratio", "0");
											}
											list.get(j).put("cmoney", list.get(j).getDouble("cmoney")+list1.get(j).getDouble("cmoney")+"");
											list.get(j).put("tcmoney", list.get(j).getDouble("tcmoney")+list1.get(j).getDouble("tcmoney")+"");
											list.get(j).put("zxfpaymoney", list.get(j).getDouble("zxfpaymoney")+list1.get(j).getDouble("zxfpaymoney")+"");
											list.get(j).put("zxfjcfmoney", list.get(j).getDouble("zxfjcfmoney")+list1.get(j).getDouble("zxfjcfmoney")+"");
											list.get(j).put("zxfyjjmoney", list.get(j).getDouble("zxfyjjmoney")+list1.get(j).getDouble("zxfyjjmoney")+"");
											list.get(j).put("drugsmoney", list.get(j).getDouble("drugsmoney")+list1.get(j).getDouble("drugsmoney")+"");
											list.get(j).put("xmpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))));
											//list.get(j).put("zjpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))+Double.parseDouble(list.get(j).getString("cmoney"))));
										}
									}
									
									
								}
								//最后一年
							}else{
								for (int i = 1; i <= Integer.parseInt(endTime.substring(5, 7)); i++) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
									Calendar calendar = Calendar.getInstance();
									calendar.setTime(sdf.parse(endTime.substring(0, 4)+"-"+i));
									int	s=0;
									if(i == Integer.parseInt(endTime.substring(5, 7))){
										s=Integer.parseInt(endTime.substring(8,10));
									}else{
										s=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
									}
									int	l=1;
									//查询月天数
									map.put("year", endTime.substring(0, 4));
									map.put("month", i+"");
									map.put("startday",l+"");
									map.put("endday", s+"");
									map.put("years", "(year(r.createtime)="+endTime.substring(0, 4)+")");
									map.put("months", "(month(r.createtime)="+i+")");
									map.put("costyears", "(year(cost.sftime)="+endTime.substring(0, 4)+")");
									map.put("costmonths", "(month(cost.sftime)="+i+")");
									List<JSONObject> list1=analysisService.findAllCJStatisticsByDay(request, map);
									if(list.size()==0){
										list.addAll(list1);	
									}else{
										for (int j = 0; j < list.size(); j++) {
											list.get(j).put("month", "-");
											list.get(j).put("name", list.get(j).getString("name"));
											list.get(j).put("czperson", list.get(j).getInt("czperson")+list1.get(j).getInt("czperson")+"");
											list.get(j).put("czcjperson", list.get(j).getInt("czcjperson")+list1.get(j).getInt("czcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("czperson"))>0){
												list.get(j).put("czcjratio", Double.parseDouble(list.get(j).getString("czcjperson"))/Double.parseDouble(list.get(j).getString("czperson"))+"");
											}else{
												list.get(j).put("czcjratio", "0");
											}
											list.get(j).put("czpaymoney", list.get(j).getDouble("czpaymoney")+list1.get(j).getDouble("czpaymoney")+"");
											list.get(j).put("czjcfmoney", list.get(j).getDouble("czjcfmoney")+list1.get(j).getDouble("czjcfmoney")+"");
											list.get(j).put("czyjjmoney", list.get(j).getDouble("czyjjmoney")+list1.get(j).getDouble("czyjjmoney")+"");
											list.get(j).put("fzperson", list.get(j).getInt("fzperson")+list1.get(j).getInt("fzperson")+"");
											list.get(j).put("fzcjperson", list.get(j).getInt("fzcjperson")+list1.get(j).getInt("fzcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("fzperson"))>0){
												list.get(j).put("fzcjratio", Double.parseDouble(list.get(j).getString("fzcjperson"))/Double.parseDouble(list.get(j).getString("fzperson"))+"");
											}else{
												list.get(j).put("fzcjratio", "0");
											}
											list.get(j).put("fzpaymoney", list.get(j).getDouble("fzpaymoney")+list1.get(j).getDouble("fzpaymoney")+"");
											list.get(j).put("fzjcfmoney", list.get(j).getDouble("fzjcfmoney")+list1.get(j).getDouble("fzjcfmoney")+"");
											list.get(j).put("fzyjjmoney", list.get(j).getDouble("fzyjjmoney")+list1.get(j).getDouble("fzyjjmoney")+"");
											list.get(j).put("zxfperson", list.get(j).getInt("zxfperson")+list1.get(j).getInt("zxfperson")+"");
											list.get(j).put("zxfcjperson", list.get(j).getInt("zxfcjperson")+list1.get(j).getInt("zxfcjperson")+"");
											if(Integer.parseInt(list.get(j).getString("zxfperson"))>0){
												list.get(j).put("zxfcjratio", Double.parseDouble(list.get(j).getString("zxfcjperson"))/Double.parseDouble(list.get(j).getString("zxfperson"))+"");
											}else{
												list.get(j).put("zxfcjratio", "0");
											}
											list.get(j).put("cmoney", list.get(j).getDouble("cmoney")+list1.get(j).getDouble("cmoney")+"");
											list.get(j).put("tcmoney", list.get(j).getDouble("tcmoney")+list1.get(j).getDouble("tcmoney")+"");
											list.get(j).put("zxfpaymoney", list.get(j).getDouble("zxfpaymoney")+list1.get(j).getDouble("zxfpaymoney")+"");
											list.get(j).put("zxfjcfmoney", list.get(j).getDouble("zxfjcfmoney")+list1.get(j).getDouble("zxfjcfmoney")+"");
											list.get(j).put("zxfyjjmoney", list.get(j).getDouble("zxfyjjmoney")+list1.get(j).getDouble("zxfyjjmoney")+"");
											list.get(j).put("drugsmoney", list.get(j).getDouble("drugsmoney")+list1.get(j).getDouble("drugsmoney")+"");
											list.get(j).put("xmpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))));
											//list.get(j).put("zjpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))+Double.parseDouble(list.get(j).getString("cmoney"))));
										}
									}
									
								}
							}
						}
					}
				}
				//月
			}else if(startTime.length()==7||endTime.length()==7){
				//判断是否为当天
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM");
				Date date = new Date(System.currentTimeMillis());
				String time = formatter.format(date);
				if(YZUtility.isNullorEmpty(endTime)&&!YZUtility.isNullorEmpty(startTime)){
					endTime=time;
				}else if(!YZUtility.isNullorEmpty(endTime)&&YZUtility.isNullorEmpty(startTime)){
					Long time1 = formatter.parse(endTime).getTime();
					Long time2 = formatter.parse(time).getTime();
					if(time1>=time2){
						startTime=time;
					}else{
						throw new Exception("请选择查询起始时间！");
					}
				}
				//不是同一年
				if(Integer.parseInt(startTime.substring(0, 4))<Integer.parseInt(endTime.substring(0, 4))){
					//截取每年的每个月的数据
					//间隔几年查询每一年月份数据
					for (int i = Integer.parseInt(startTime.substring(0, 4)); i <= Integer.parseInt(endTime.substring(0, 4)); i++) {
						if(i==Integer.parseInt(startTime.substring(0, 4))){
							map.put("year", i+"");
							//查询年的数据
							map.put("startmonth", Integer.parseInt(startTime.substring(5, 7))+"");
							map.put("endmonth", 12+"");
							map.put("years", "(year(r.createtime)="+i+")");
							map.put("costyears", "(year(cost.sftime)="+i+")");
							List<JSONObject> list1=analysisService.findAllCJStatisticsByMonth(request, map);
							if(list.size()==0){
								list.addAll(list1);	
							}else{
								for (int j = 0; j < list.size(); j++) {
									list.get(j).put("month", "-");
									list.get(j).put("name", list.get(j).getString("name"));
									list.get(j).put("czperson", list.get(j).getInt("czperson")+list1.get(j).getInt("czperson")+"");
									list.get(j).put("czcjperson", list.get(j).getInt("czcjperson")+list1.get(j).getInt("czcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("czperson"))>0){
										list.get(j).put("czcjratio", Double.parseDouble(list.get(j).getString("czcjperson"))/Double.parseDouble(list.get(j).getString("czperson"))+"");
									}else{
										list.get(j).put("czcjratio", "0");
									}
									list.get(j).put("czpaymoney", list.get(j).getDouble("czpaymoney")+list1.get(j).getDouble("czpaymoney")+"");
									list.get(j).put("czjcfmoney", list.get(j).getDouble("czjcfmoney")+list1.get(j).getDouble("czjcfmoney")+"");
									list.get(j).put("czyjjmoney", list.get(j).getDouble("czyjjmoney")+list1.get(j).getDouble("czyjjmoney")+"");
									list.get(j).put("fzperson", list.get(j).getInt("fzperson")+list1.get(j).getInt("fzperson")+"");
									list.get(j).put("fzcjperson", list.get(j).getInt("fzcjperson")+list1.get(j).getInt("fzcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("fzperson"))>0){
										list.get(j).put("fzcjratio", Double.parseDouble(list.get(j).getString("fzcjperson"))/Double.parseDouble(list.get(j).getString("fzperson"))+"");
									}else{
										list.get(j).put("fzcjratio", "0");
									}
									list.get(j).put("fzpaymoney", list.get(j).getDouble("fzpaymoney")+list1.get(j).getDouble("fzpaymoney")+"");
									list.get(j).put("fzjcfmoney", list.get(j).getDouble("fzjcfmoney")+list1.get(j).getDouble("fzjcfmoney")+"");
									list.get(j).put("fzyjjmoney", list.get(j).getDouble("fzyjjmoney")+list1.get(j).getDouble("fzyjjmoney")+"");
									list.get(j).put("zxfperson", list.get(j).getInt("zxfperson")+list1.get(j).getInt("zxfperson")+"");
									list.get(j).put("zxfcjperson", list.get(j).getInt("zxfcjperson")+list1.get(j).getInt("zxfcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("zxfperson"))>0){
										list.get(j).put("zxfcjratio", Double.parseDouble(list.get(j).getString("zxfcjperson"))/Double.parseDouble(list.get(j).getString("zxfperson"))+"");
									}else{
										list.get(j).put("zxfcjratio", "0");
									}
									list.get(j).put("cmoney", list.get(j).getDouble("cmoney")+list1.get(j).getDouble("cmoney")+"");
									list.get(j).put("tcmoney", list.get(j).getDouble("tcmoney")+list1.get(j).getDouble("tcmoney")+"");
									list.get(j).put("zxfpaymoney", list.get(j).getDouble("zxfpaymoney")+list1.get(j).getDouble("zxfpaymoney")+"");
									list.get(j).put("zxfjcfmoney", list.get(j).getDouble("zxfjcfmoney")+list1.get(j).getDouble("zxfjcfmoney")+"");
									list.get(j).put("zxfyjjmoney", list.get(j).getDouble("zxfyjjmoney")+list1.get(j).getDouble("zxfyjjmoney")+"");
									list.get(j).put("drugsmoney", list.get(j).getDouble("drugsmoney")+list1.get(j).getDouble("drugsmoney")+"");
									list.get(j).put("xmpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))));
									//list.get(j).put("zjpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))+Double.parseDouble(list.get(j).getString("cmoney"))));
								}
							}
							
							
						}else if(i>Integer.parseInt(startTime.substring(0, 4))&&i<Integer.parseInt(endTime.substring(0, 4))){
							map.put("year", i+"");
							//查询年的数据
							map.put("startmonth", 1+"");
							map.put("endmonth", 12+"");
							map.put("years", "(year(r.createtime)="+i+")");
							map.put("costyears", "(year(cost.sftime)="+i+")");
							List<JSONObject> list1=analysisService.findAllCJStatisticsByMonth(request, map);
							if(list.size()==0){
								list.addAll(list1);	
							}else{
								for (int j = 0; j < list.size(); j++) {
									list.get(j).put("month", "-");
									list.get(j).put("name", list.get(j).getString("name"));
									list.get(j).put("czperson", list.get(j).getInt("czperson")+list1.get(j).getInt("czperson")+"");
									list.get(j).put("czcjperson", list.get(j).getInt("czcjperson")+list1.get(j).getInt("czcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("czperson"))>0){
										list.get(j).put("czcjratio", Double.parseDouble(list.get(j).getString("czcjperson"))/Double.parseDouble(list.get(j).getString("czperson"))+"");
									}else{
										list.get(j).put("czcjratio", "0");
									}
									list.get(j).put("czpaymoney", list.get(j).getDouble("czpaymoney")+list1.get(j).getDouble("czpaymoney")+"");
									list.get(j).put("czjcfmoney", list.get(j).getDouble("czjcfmoney")+list1.get(j).getDouble("czjcfmoney")+"");
									list.get(j).put("czyjjmoney", list.get(j).getDouble("czyjjmoney")+list1.get(j).getDouble("czyjjmoney")+"");
									list.get(j).put("fzperson", list.get(j).getInt("fzperson")+list1.get(j).getInt("fzperson")+"");
									list.get(j).put("fzcjperson", list.get(j).getInt("fzcjperson")+list1.get(j).getInt("fzcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("fzperson"))>0){
										list.get(j).put("fzcjratio", Double.parseDouble(list.get(j).getString("fzcjperson"))/Double.parseDouble(list.get(j).getString("fzperson"))+"");
									}else{
										list.get(j).put("fzcjratio", "0");
									}
									list.get(j).put("fzpaymoney", list.get(j).getDouble("fzpaymoney")+list1.get(j).getDouble("fzpaymoney")+"");
									list.get(j).put("fzjcfmoney", list.get(j).getDouble("fzjcfmoney")+list1.get(j).getDouble("fzjcfmoney")+"");
									list.get(j).put("fzyjjmoney", list.get(j).getDouble("fzyjjmoney")+list1.get(j).getDouble("fzyjjmoney")+"");
									list.get(j).put("zxfperson", list.get(j).getInt("zxfperson")+list1.get(j).getInt("zxfperson")+"");
									list.get(j).put("zxfcjperson", list.get(j).getInt("zxfcjperson")+list1.get(j).getInt("zxfcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("zxfperson"))>0){
										list.get(j).put("zxfcjratio", Double.parseDouble(list.get(j).getString("zxfcjperson"))/Double.parseDouble(list.get(j).getString("zxfperson"))+"");
									}else{
										list.get(j).put("zxfcjratio", "0");
									}
									list.get(j).put("cmoney", list.get(j).getDouble("cmoney")+list1.get(j).getDouble("cmoney")+"");
									list.get(j).put("tcmoney", list.get(j).getDouble("tcmoney")+list1.get(j).getDouble("tcmoney")+"");
									list.get(j).put("zxfpaymoney", list.get(j).getDouble("zxfpaymoney")+list1.get(j).getDouble("zxfpaymoney")+"");
									list.get(j).put("zxfjcfmoney", list.get(j).getDouble("zxfjcfmoney")+list1.get(j).getDouble("zxfjcfmoney")+"");
									list.get(j).put("zxfyjjmoney", list.get(j).getDouble("zxfyjjmoney")+list1.get(j).getDouble("zxfyjjmoney")+"");
									list.get(j).put("drugsmoney", list.get(j).getDouble("drugsmoney")+list1.get(j).getDouble("drugsmoney")+"");
									list.get(j).put("xmpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))));
									//list.get(j).put("zjpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))+Double.parseDouble(list.get(j).getString("cmoney"))));
								}
							}
							
						}else if(i==Integer.parseInt(endTime.substring(0, 4))){
							map.put("year", i+"");
							map.put("startmonth", 1+"");
							map.put("endmonth", Integer.parseInt(endTime.substring(5, 7))+"");
							map.put("years", "(year(r.createtime)="+i+")");
							map.put("costyears", "(year(cost.sftime)="+i+")");
							List<JSONObject> list1=analysisService.findAllCJStatisticsByMonth(request, map);
							if(list.size()==0){
								list.addAll(list1);	
							}else{
								for (int j = 0; j < list.size(); j++) {
									list.get(j).put("month", "-");
									list.get(j).put("name", list.get(j).getString("name"));
									list.get(j).put("czperson", list.get(j).getInt("czperson")+list1.get(j).getInt("czperson")+"");
									list.get(j).put("czcjperson", list.get(j).getInt("czcjperson")+list1.get(j).getInt("czcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("czperson"))>0){
										list.get(j).put("czcjratio", Double.parseDouble(list.get(j).getString("czcjperson"))/Double.parseDouble(list.get(j).getString("czperson"))+"");
									}else{
										list.get(j).put("czcjratio", "0");
									}
									list.get(j).put("czpaymoney", list.get(j).getDouble("czpaymoney")+list1.get(j).getDouble("czpaymoney")+"");
									list.get(j).put("czjcfmoney", list.get(j).getDouble("czjcfmoney")+list1.get(j).getDouble("czjcfmoney")+"");
									list.get(j).put("czyjjmoney", list.get(j).getDouble("czyjjmoney")+list1.get(j).getDouble("czyjjmoney")+"");
									list.get(j).put("fzperson", list.get(j).getInt("fzperson")+list1.get(j).getInt("fzperson")+"");
									list.get(j).put("fzcjperson", list.get(j).getInt("fzcjperson")+list1.get(j).getInt("fzcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("fzperson"))>0){
										list.get(j).put("fzcjratio", Double.parseDouble(list.get(j).getString("fzcjperson"))/Double.parseDouble(list.get(j).getString("fzperson"))+"");
									}else{
										list.get(j).put("fzcjratio", "0");
									}
									list.get(j).put("fzpaymoney", list.get(j).getDouble("fzpaymoney")+list1.get(j).getDouble("fzpaymoney")+"");
									list.get(j).put("fzjcfmoney", list.get(j).getDouble("fzjcfmoney")+list1.get(j).getDouble("fzjcfmoney")+"");
									list.get(j).put("fzyjjmoney", list.get(j).getDouble("fzyjjmoney")+list1.get(j).getDouble("fzyjjmoney")+"");
									list.get(j).put("zxfperson", list.get(j).getInt("zxfperson")+list1.get(j).getInt("zxfperson")+"");
									list.get(j).put("zxfcjperson", list.get(j).getInt("zxfcjperson")+list1.get(j).getInt("zxfcjperson")+"");
									if(Integer.parseInt(list.get(j).getString("zxfperson"))>0){
										list.get(j).put("zxfcjratio", Double.parseDouble(list.get(j).getString("zxfcjperson"))/Double.parseDouble(list.get(j).getString("zxfperson"))+"");
									}else{
										list.get(j).put("zxfcjratio", "0");
									}
									list.get(j).put("cmoney", list.get(j).getDouble("cmoney")+list1.get(j).getDouble("cmoney")+"");
									list.get(j).put("tcmoney", list.get(j).getDouble("tcmoney")+list1.get(j).getDouble("tcmoney")+"");
									list.get(j).put("zxfpaymoney", list.get(j).getDouble("zxfpaymoney")+list1.get(j).getDouble("zxfpaymoney")+"");
									list.get(j).put("zxfjcfmoney", list.get(j).getDouble("zxfjcfmoney")+list1.get(j).getDouble("zxfjcfmoney")+"");
									list.get(j).put("zxfyjjmoney", list.get(j).getDouble("zxfyjjmoney")+list1.get(j).getDouble("zxfyjjmoney")+"");
									list.get(j).put("drugsmoney", list.get(j).getDouble("drugsmoney")+list1.get(j).getDouble("drugsmoney")+"");
									list.get(j).put("xmpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))));
									//list.get(j).put("zjpaymoney", ""+(Double.parseDouble(list.get(j).getString("czpaymoney"))+Double.parseDouble(list.get(j).getString("fzpaymoney"))+Double.parseDouble(list.get(j).getString("zxfpaymoney"))+Double.parseDouble(list.get(j).getString("cmoney"))));
								}
							}	
						}
					}
				}else{
					map.put("year", endTime.substring(0, 4)+"");
					map.put("startmonth", Integer.parseInt(startTime.substring(5, 7))+"");
					map.put("endmonth", Integer.parseInt(endTime.substring(5, 7))+"");
					map.put("years", "(year(r.createtime)="+endTime.substring(0, 4)+")");
					map.put("costyears", "(year(cost.sftime)="+endTime.substring(0, 4)+")");
					list=analysisService.findAllCJStatisticsByMonth(request, map);
				}	
				//年
			}else if(startTime.length()==4||endTime.length()==4){
				//判断是否为当天
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
				Date date = new Date(System.currentTimeMillis());
				String time = formatter.format(date);
				if(YZUtility.isNullorEmpty(endTime)&&!YZUtility.isNullorEmpty(startTime)){
					endTime=time;
				}else if(!YZUtility.isNullorEmpty(endTime)&&YZUtility.isNullorEmpty(startTime)){
					Long time1 = formatter.parse(endTime).getTime();
					Long time2 = formatter.parse(time).getTime();
					if(time1>=time2){
						startTime=time;
					}else{
						throw new Exception("请选择查询起始时间！");
					}
				}
				
				map.put("startyear", startTime);
				map.put("endyear", endTime);
				list=analysisService.findAllCJStatisticsByYear(request, map);
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, true, e, response, logger);
		}
		
		return null;
	}
	
	/**
	 * 总数据查询 syp
	  * @Title: findTotalMoneyByMonth   
	  * @Description: TODO(总数据查询)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年10月6日 下午2:16:35
	 */
	@RequestMapping(value = "/findTotalMoneyByMonth.act")
	public String findTotalMoneyByMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String organization = request.getParameter("organization");
		String startTime = request.getParameter("starttime");
		String endTime = request.getParameter("endtime");
		String selectLabel = request.getParameter("selectLabel");
		String qxname=request.getParameter("qxname");
		String deptCategory=request.getParameter("deptCategory");
		Map<String, String> dataMap = new HashMap<>();
		try {
			if(!YZUtility.isNullorEmpty(organization)){
				dataMap.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(startTime)){
				dataMap.put("startTime", startTime);	
			}
			if(!YZUtility.isNullorEmpty(endTime)){
				dataMap.put("endTime", endTime);	
			}
			if(!YZUtility.isNullorEmpty(selectLabel)){
				dataMap.put("selectLabel", selectLabel);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					dataMap.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					dataMap.put("buttonName", str.toString());
				}
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				dataMap.put("deptCategory", deptCategory);	
			}
			List<JSONObject> list = new ArrayList<JSONObject>();
			list = analysisService.findTotalMoneyByMonth(dataMap, request);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: DepartmentPerformance   
	  * @Description: TODO(成交科室)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月28日 下午3:22:52
	 */
	@RequestMapping("/DepartmentPerformance.act")
	public String DepartmentPerformance(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = request.getParameter("organization");
			String qxname=request.getParameter("qxname");
			String deptCategory=request.getParameter("deptCategory");
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
				map.put("year", Integer.parseInt(endtime.substring(0, 4))+"");
				map.put("code", "ISNULL(sum(case when  datepart(month,createtime)="+(Integer.parseInt(endtime.substring(5, 7))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(endtime.substring(5, 7))+"")+"月'");
				map.put("startmonth", Integer.parseInt(starttime.substring(5, 7))+"");
				map.put("endmonth", Integer.parseInt(endtime.substring(5, 7))+"");
				map.put("years", "(year(r.createtime)="+(Integer.parseInt(endtime.substring(0, 4))+"")+")");
			List<JSONObject> json = analysisService.findDepartment(request,map);
			YZUtility.RETURN_LIST(json, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: DepartmentPerformance   
	  * @Description: TODO(咨询对应种植体)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月28日 下午3:22:52
	 */
	@RequestMapping("/implantStatistics.act")
	public String implantStatistics(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = request.getParameter("organization");
			String qxname=request.getParameter("qxname");
			String deptCategory=request.getParameter("deptCategory");
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
				map.put("year", Integer.parseInt(endtime.substring(0, 4))+"");
				map.put("code", "ISNULL(sum(case when  datepart(month,createtime)="+(Integer.parseInt(endtime.substring(5, 7))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(endtime.substring(5, 7))+"")+"月'");
				map.put("startmonth", Integer.parseInt(starttime.substring(5, 7))+"");
				map.put("endmonth", Integer.parseInt(endtime.substring(5, 7))+"");
				map.put("years", "(year(r.createtime)="+(Integer.parseInt(endtime.substring(0, 4))+"")+")");
			List<JSONObject> json = analysisService.findImplant(request,map);
			YZUtility.RETURN_LIST(json, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: DepartmentPerformance   
	  * @Description: TODO(咨询对应医生)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月28日 下午3:22:52
	 */
	@RequestMapping("/doctorStatistics.act")
	public String doctorStatistics(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = request.getParameter("organization");
			String qxname=request.getParameter("qxname");
			String deptCategory=request.getParameter("deptCategory");
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
				map.put("year", Integer.parseInt(endtime.substring(0, 4))+"");
				map.put("code", "ISNULL(sum(case when  datepart(month,createtime)="+(Integer.parseInt(endtime.substring(5, 7))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(endtime.substring(5, 7))+"")+"月'");
				map.put("startmonth", Integer.parseInt(starttime.substring(5, 7))+"");
				map.put("endmonth", Integer.parseInt(endtime.substring(5, 7))+"");
				map.put("years", "(year(r.createtime)="+(Integer.parseInt(endtime.substring(0, 4))+"")+")");
			List<JSONObject> json = analysisService.finddoctor(request,map);
			YZUtility.RETURN_LIST(json, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: DepartmentPerformance   
	  * @Description: TODO(咨询对应渠道)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月28日 下午3:22:52
	 */
	@RequestMapping("/Devchannel.act")
	public String Devchannel(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = request.getParameter("organization");
			String qxname=request.getParameter("qxname");
			String deptCategory=request.getParameter("deptCategory");
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
				map.put("year", Integer.parseInt(endtime.substring(0, 4))+"");
				map.put("code", "ISNULL(sum(case when  datepart(month,createtime)="+(Integer.parseInt(endtime.substring(5, 7))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(endtime.substring(5, 7))+"")+"月'");
				map.put("startmonth", Integer.parseInt(starttime.substring(5, 7))+"");
				map.put("endmonth", Integer.parseInt(endtime.substring(5, 7))+"");
				map.put("years", "(year(r.createtime)="+(Integer.parseInt(endtime.substring(0, 4))+"")+")");
				List<JSONObject> json = analysisService.Devchannel(request,map);
			YZUtility.RETURN_LIST(json, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
		}
		return null;
	}
	
	/**
	  * @Title: DepartmentPerformance   
	  * @Description: TODO(咨询对应消费区间)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月28日 下午3:22:52
	 */
	@RequestMapping("/consumptionInterval.act")
	public String consumptionInterval(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String organization = request.getParameter("organization");
			String qxname=request.getParameter("qxname");
			String deptCategory=request.getParameter("deptCategory");
		try {
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);	
			}
			if(!YZUtility.isNullorEmpty(qxname)){
				String[] qxnamelist = qxname.split(",");
				if(qxnamelist.length==1){
					map.put("buttonName", "\'"+qxname+"\'");						
				}else{
					StringBuilder str= new StringBuilder();
					for (int i = 0; i < qxnamelist.length; i++) {
						if(i==qxnamelist.length-1){
							str.append("\'"+qxnamelist[i]+"\'");
						}else{
							str.append("\'"+qxnamelist[i]+"\',");
						}
					}
					map.put("buttonName", str.toString());
				}	
			}
			if(!YZUtility.isNullorEmpty(deptCategory)){
				map.put("deptCategory", deptCategory);	
			}
				map.put("year", Integer.parseInt(endtime.substring(0, 4))+"");
				map.put("code", "ISNULL(sum(case when  datepart(month,createtime)="+(Integer.parseInt(endtime.substring(5, 7))+"")+" then 1 else 0 end),0) as '"+(Integer.parseInt(endtime.substring(5, 7))+"")+"月'");
				map.put("startmonth", Integer.parseInt(starttime.substring(5, 7))+"");
				map.put("endmonth", Integer.parseInt(endtime.substring(5, 7))+"");
				map.put("years", "(year(r.createtime)="+(Integer.parseInt(endtime.substring(0, 4))+"")+")");
				List<JSONObject> json = analysisService.consumptionInterval(request,map);
			YZUtility.RETURN_LIST(json, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/toBargainPerformance.act")
	public ModelAndView toBargainPerformance(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/zxtj/bargainPerformance.jsp");
		return mv;
	}
	@RequestMapping("/bargainPerformance.act")
	public String bargainPerformance(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String organization = request.getParameter("organization");
		try {
			Map<String,String> map = new HashMap<String,String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
                starttime=df.format(new Date()) + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
                endtime=df.format(new Date()) + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);
			}

			String[] time=starttime.split("-");
            String[] time1=endtime.split("-");
            //同年不同月
            if(time[0].equals(time1[0])&&!time[1].equals(time1[1])){
                map.put("czstarttime",starttime);
                map.put("czendtime",endtime);
                //同年同月
            }else if(time[0].equals(time1[0])&&time[1].equals(time1[1])){
                map.put("year",time[0]);
                map.put("month",time[1]);
                //不同年
            }else{
                map.put("czstarttime",starttime);
                map.put("czendtime",endtime);
            }

			//时间，门诊为必选
			JSONObject json = analysisService.bargainPerformance(map);
			List<JSONObject> list=new ArrayList<JSONObject>();
			list.add(json);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR("查询失败！", false, e, response, logger);
		}
		return null;
	}
}
