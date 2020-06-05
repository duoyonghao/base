package com.kqds.controller.base.jh;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsJh;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.jh.Kqds_Jh_Logic;
import com.kqds.service.base.jhVideo.Kqds_JhVideo_Logic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/Kqds_JhAct")
public class Kqds_JhAct {
	private static Logger logger = LoggerFactory.getLogger(Kqds_JhAct.class);
	@Autowired
	private Kqds_Jh_Logic logic;
	@Autowired
	private KQDS_REGLogic regLogic;
	/**
	 * 保存叫号
	 * <p>Title: insert</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年1月11日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert.act")
	public String insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode=request.getParameter("usercode");
			String askperson=request.getParameter("askperson");
			String patient_name=request.getParameter("username");
			String sex=request.getParameter("sex");
			String regSeqId=request.getParameter("regid");
			String age=request.getParameter("age");
			String items=request.getParameter("items");
			String surgeryStatus=request.getParameter("surgery");
			String floor=request.getParameter("floor");
			String seqid=request.getParameter("seqid");
			String joint=request.getParameter("joint");
			String uplefttoothbit=request.getParameter("uplefttoothbit");
			String uperrighttoothbit=request.getParameter("uperrighttoothbit");
			String leftlowertoothbit=request.getParameter("leftlowertoothbit");
			String lowrighttoothbit=request.getParameter("lowrighttoothbit");
			YZPerson person = SessionUtil.getLoginPerson(request);
			JSONArray jsonArr = JSONArray.fromObject(floor);
			for (int i = 0; i < jsonArr.size(); i++) {
				if(jsonArr.get(i).equals("A")){
					floor="A";
				}
			}
			if(items.contains("小牙片")){
				floor="B";
			}
			if(surgeryStatus.equals("复查")){
				floor="B";
			}
			/*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date());
			if (!YZUtility.isNullorEmpty(time)) {
				map.put("starttime", time + ConstUtil.TIME_START);
				map.put("endtime", time + ConstUtil.TIME_END);
			}*/
			if(!YZUtility.isNullorEmpty(seqid)){
				KqdsJh jh1 = new KqdsJh();
				jh1.setId(seqid);
				jh1.setDel(2);
				logic.updateDel(jh1);
			}
			KqdsJh jh = new KqdsJh();
			jh.setId(YZUtility.getUUID());
			jh.setUsercode(usercode);
			jh.setCreateuser(person.getSeqId());
			jh.setCreatetime(YZUtility.getCurDateTimeStr());
			jh.setAskperson(askperson);
			jh.setPatientName(patient_name);
			jh.setStatus("1");//排队
			jh.setSex(sex);
			jh.setRegid(regSeqId);
			jh.setDel(0);
			jh.setOrganization(ChainUtil.getCurrentOrganization(request));
			jh.setAge(Integer.parseInt(age));
			jh.setItems(items);
			jh.setSurgeryStatus(surgeryStatus);
			jh.setFloor(floor);
			if(!YZUtility.isNullorEmpty(joint)){
				jh.setJoint(joint);
			}
			if(!YZUtility.isNullorEmpty(uplefttoothbit)){
				jh.setUplefttoothbit(uplefttoothbit);
			}
			if(!YZUtility.isNullorEmpty(uperrighttoothbit)){
				jh.setUperrighttoothbit(uperrighttoothbit);
			}
			if(!YZUtility.isNullorEmpty(leftlowertoothbit)){
				jh.setLeftlowertoothbit(leftlowertoothbit);
			}
			if(!YZUtility.isNullorEmpty(lowrighttoothbit)){
				jh.setLowrighttoothbit(lowrighttoothbit);
			}
			//根据楼层查询对应的序号
			logic.insert(jh);
			if(regSeqId!=null){
				Map<String,String> map=new HashMap<String,String>();
				map.put("seqid", regSeqId);
				map.put("jh", "2");
				regLogic.updateRegJhStatus(map);
			}
			
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 查询叫号 分页
	 * <p>Title: select</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年1月11日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectFy.act")
	public String selectFy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String status=request.getParameter("status");
			String floor=request.getParameter("floor");
			Map<String,String> map= new HashMap<String,String>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date());
			if (!YZUtility.isNullorEmpty(time)) {
				map.put("starttime", time + ConstUtil.TIME_START);
				map.put("endtime", time + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(status)) {
				map.put("status", status);
			}
			if (!YZUtility.isNullorEmpty(floor)) {
				map.put("floor", floor);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			// 初始化分页实体类
			@SuppressWarnings("rawtypes")
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject obj = logic.selectFy(map,bp);
			YZUtility.DEAL_SUCCESS(obj,null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 查询叫号 不分页
	 * <p>Title: select</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年1月11日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/select.act")
	public String select(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String status=request.getParameter("status");
			String floor=request.getParameter("floor");
			Map<String,String> map= new HashMap<String,String>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date());
			if (!YZUtility.isNullorEmpty(time)) {
				map.put("starttime", time + ConstUtil.TIME_START);
				map.put("endtime", time + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(status)) {
				map.put("status", status);
			}
			if (!YZUtility.isNullorEmpty(floor)) {
				map.put("floor", floor);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			
			List<JSONObject> list = logic.select(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	@RequestMapping(value = "/selectByRegId.act")
	public String selectById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String regid=request.getParameter("regid");
			Map<String,String> map= new HashMap<String,String>();
			if (!YZUtility.isNullorEmpty(regid)) {
				map.put("regid", regid);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			List<JSONObject> list = logic.selectByRegId(map);
			YZUtility.RETURN_OBJ(list.get(0), response, logger);
			//YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 分页
	 * <p>Title: selectdelFy</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月13日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectdelFy.act")
	public String selectdelFy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String floor=request.getParameter("floor");
			Map<String,String> map= new HashMap<String,String>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date());
			if (!YZUtility.isNullorEmpty(time)) {
				map.put("starttime", time + ConstUtil.TIME_START);
				map.put("endtime", time + ConstUtil.TIME_END);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			if(!YZUtility.isNullorEmpty(floor)){
				map.put("floor", floor);
			}
			// 初始化分页实体类
			@SuppressWarnings("rawtypes")
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject obj = logic.selectdelFy(map,bp);
			YZUtility.DEAL_SUCCESS(obj,null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 不分页
	 * <p>Title: selectdel</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月13日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectdel.act")
	public String selectdel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String floor=request.getParameter("floor");
			Map<String,String> map= new HashMap<String,String>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date());
			if (!YZUtility.isNullorEmpty(time)) {
				map.put("starttime", time + ConstUtil.TIME_START);
				map.put("endtime", time + ConstUtil.TIME_END);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			if(!YZUtility.isNullorEmpty(floor)){
				map.put("floor", floor);
			}
			List<JSONObject> list = logic.selectdel(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	@RequestMapping(value = "/selectTime.act")
	public String selectTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String organization =request.getParameter("organization");
			String startTime=request.getParameter("starttime");
			String endTime=request.getParameter("endtime");
			String floor=request.getParameter("floor");
			String items=request.getParameter("items");
			String surgery=request.getParameter("surgery");
			try {
				//初始页面加载查询所有员工
				Map<String,String> map =new  HashMap<String,String>();
				if(!YZUtility.isNullorEmpty(organization)){
					map.put("organization", organization);	
				}
				if(!YZUtility.isNullorEmpty(floor)){
					if(!floor.equals("all")){
						map.put("floor", floor);	
					}
				}
				if(!YZUtility.isNullorEmpty(items)){
					String[] item = items.split(",");
					if(item.length==1){
						map.put("items", "[\""+items+"\"]");
					}else if(item.length>1){
						String str="";
						for (int i = 0; i < item.length; i++) {
							if(i==0){
								str="[\""+item[i]+"\",";
							}else if(i==item.length-1){
								str+="\""+item[i]+"\"]";
							}else{
								str+="\""+item[i]+"\",";
							}
						}
						map.put("items", str);
					}
				}
				if(!YZUtility.isNullorEmpty(surgery)){
					if(!surgery.equals("all")){
						map.put("surgery", surgery);	
					}
				}
				if(!YZUtility.isNullorEmpty(startTime)){
					map.put("starttime", startTime+ConstUtil.TIME_START);	
				}
				if(!YZUtility.isNullorEmpty(endTime)){
					map.put("endtime", endTime+ ConstUtil.TIME_END);	
				}
				@SuppressWarnings("rawtypes")
				BootStrapPage bp = new BootStrapPage();
				// 封装参数到实体类
				BeanUtils.populate(bp, request.getParameterMap());
				JSONObject list=logic.selectByTime(map,bp);
				YZUtility.RETURN_OBJ(list, response, logger);
			} catch (Exception e) {
				YZUtility.DEAL_ERROR(null, true, e, response, logger);
			}
			
			return null;
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/selectTimeByDel.act")
	public String selectTimeByDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String organization =request.getParameter("organization");
			String startTime=request.getParameter("starttime");
			String endTime=request.getParameter("endtime");
			String floor=request.getParameter("floor");
			String items=request.getParameter("items");
			String surgery=request.getParameter("surgery");
			try {
				//初始页面加载查询所有员工
				Map<String,String> map =new  HashMap<String,String>();
				if(!YZUtility.isNullorEmpty(organization)){
					map.put("organization", organization);	
				}
				if(!YZUtility.isNullorEmpty(floor)){
					if(!floor.equals("all")){
						map.put("floor", floor);	
					}
				}
				if(!YZUtility.isNullorEmpty(items)){
					String[] item = items.split(",");
					if(item.length==1){
						map.put("items", "[\""+items+"\"]");
					}else if(item.length>1){
						String str="";
						for (int i = 0; i < item.length; i++) {
							if(i==0){
								str="[\""+item[i]+"\",";
							}else if(i==item.length-1){
								str+="\""+item[i]+"\"]";
							}else{
								str+="\""+item[i]+"\",";
							}
						}
						map.put("items", str);
					}
				}
				if(!YZUtility.isNullorEmpty(surgery)){
					if(!surgery.equals("all")){
						map.put("surgery", surgery);	
					}
				}
				if(!YZUtility.isNullorEmpty(startTime)){
					map.put("starttime", startTime+ConstUtil.TIME_START);	
				}
				if(!YZUtility.isNullorEmpty(endTime)){
					map.put("endtime", endTime+ ConstUtil.TIME_END);	
				}
				@SuppressWarnings("rawtypes")
				BootStrapPage bp = new BootStrapPage();
				// 封装参数到实体类
				BeanUtils.populate(bp, request.getParameterMap());
				JSONObject list=logic.selectByTimeByDel(map,bp);
				YZUtility.RETURN_OBJ(list, response, logger);
			} catch (Exception e) {
				YZUtility.DEAL_ERROR(null, true, e, response, logger);
			}
			
			return null;
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	
	/**
	 * 修改叫号
	 * <p>Title: update</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年1月11日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.act")
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id=request.getParameter("id");
			String regid=request.getParameter("regid");
			String status=request.getParameter("status");
			String floor=request.getParameter("floor");
			String organization=request.getParameter("organization");
			String number=request.getParameter("number");
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsJh jh = new KqdsJh();
			jh.setId(id);
			jh.setOperator(person.getSeqId());
			if(status.equals("0")){
				jh.setStatus("1");//2进行中 3完成 
				status="1";
				jh.setDel(0);
				jh.setFloor(floor);
				jh.setNumber(Integer.parseInt(number));
				jh.setOrganization(organization);
			}else{
				jh.setStatus(status);//2进行中 3完成 
			}
			if(status.equals("2")){
				jh.setStarttime(YZUtility.getCurDateTimeStr());
			}
			if(status.equals("3")){
				jh.setEndtime(YZUtility.getCurDateTimeStr());
			}
			logic.update(jh);
			if(regid!=null){
				Map<String,String> map=new HashMap<String,String>();
				map.put("seqid", regid);
				map.put("jh", (Integer.parseInt(status)+1)+"");
				regLogic.updateRegJhStatus(map);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 修改楼层
	 * <p>Title: updateFloor</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月17日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateFloor.act")
	public String updateFloor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String ids=request.getParameter("ids");
			String numbers=request.getParameter("numbers");
			String floor=request.getParameter("floor");
			YZPerson person = SessionUtil.getLoginPerson(request);
			JSONArray jsonArray1=JSONArray.fromObject(ids);
			JSONArray jsonArray2=JSONArray.fromObject(numbers);
			List<KqdsJh> jhList= new ArrayList<KqdsJh>();
			for (int i = 0; i < jsonArray1.size(); i++) {
				KqdsJh jh = new KqdsJh();
				jh.setId((String) jsonArray1.get(i));
				jh.setOperator(person.getSeqId());
				if(floor.equals("A")){
					jh.setFloor("B");
				}else{
					jh.setFloor("A");
				}
				jh.setOrganization(ChainUtil.getCurrentOrganization(request));
				jhList.add(jh);
			}
			if(jhList.size()>0){
				logic.updateFloor(jhList);
			}
			
			//修改患者的楼层编号
			//查找序号在转移患者序号最小值之后的患者
			int j=Integer.parseInt((String) Collections.min(jsonArray2));
			Map<String,String> map= new HashMap<String,String>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date());
			if (!YZUtility.isNullorEmpty(time)) {
				map.put("starttime", time + ConstUtil.TIME_START);
				map.put("endtime", time + ConstUtil.TIME_END);
			}
			map.put("number", j+"");
			map.put("floor",floor);
			map.put("organization",ChainUtil.getCurrentOrganization(request));
			List<KqdsJh> list1 = logic.selectByNumber(map);
			if(list1.size()>0){
				//修改患者的排队序号
				for (int i = 0; i < list1.size(); i++) {
					list1.get(i).setNumber(i+j);
				}
				logic.updateNumber(list1);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 优先操作
	 * <p>Title: precedence</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月17日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/precedence.act")
	public String precedence(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id=request.getParameter("id");
			String floor=request.getParameter("floor");
			YZPerson person = SessionUtil.getLoginPerson(request);

			Map<String,String> map= new HashMap<String,String>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String time = df.format(new Date());
			if (!YZUtility.isNullorEmpty(time)) {
				map.put("starttime", time + ConstUtil.TIME_START);
				map.put("endtime", time + ConstUtil.TIME_END);
			}
			map.put("floor",floor);
			map.put("organization",ChainUtil.getCurrentOrganization(request));
			List<KqdsJh> list1 = logic.selectByNumber(map);
			List<KqdsJh> list=new ArrayList<KqdsJh>();
			List<KqdsJh> list2=new ArrayList<KqdsJh>();
			KqdsJh jh = new KqdsJh();
			jh.setId(id);
			jh.setOperator(person.getSeqId());
			if(list1.size()>0){
				int j=list1.get(0).getNumber();
				jh.setNumber(j);
				list.add(jh);
				//修改患者的排队序号
				for (int i = 0; i < list1.size(); i++) {
					if(!list1.get(i).getId().equals(id)){
						list2.add(list1.get(i));
					}
				}
				for (int i = 0; i < list2.size(); i++) {
					list2.get(i).setNumber(i+j+1);
					list2.get(i).setOperator(person.getSeqId());
				}
				list.addAll(list2);
				logic.updateNumber(list);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * 超时
	 * <p>Title: delete</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月12日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.act")
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id=request.getParameter("id");
			String regSeqId=request.getParameter("regid");
			String remark=request.getParameter("remark");
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsJh jh = new KqdsJh();
			jh.setId(id);
			jh.setOperator(person.getSeqId());
			jh.setDel(1);
			jh.setRemark(remark);
			jh.setEndtime(YZUtility.getCurDateTimeStr());
			logic.updateDel(jh);
			if(regSeqId!=null){
				Map<String,String> map=new HashMap<String,String>();
				map.put("seqid", regSeqId);
				map.put("jh", "5");
				regLogic.updateRegJhStatus(map);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	/*@RequestMapping("/showVideo")
    public void showVideo(HttpServletResponse response, @RequestParam("fileName")String fileName) {
        show(response,fileName,"video");
    }
 */
    /**
     * 响应文件
     * @param response
     * @param fileName  文件全路径
     * @param type  响应流类型
     *//*
    public void  show(HttpServletResponse response, String fileName,String type){
	    	
	    	ServletOutputStream out=null;
    		try {
    			KqdsJhVideo jhVideo = jhVideoLogic.select(fileName);
    			if(!YZUtility.isNullorEmpty(jhVideo.getUrl())){
    				File file=new File(jhVideo.getUrl());
    				FileInputStream instream=new FileInputStream(file);
    				byte[] b=new byte[1024];
    				int length=0;
    				BufferedInputStream buf=new BufferedInputStream(instream);
    				out=response.getOutputStream();
    				BufferedOutputStream bot=new BufferedOutputStream(out);
    				while((length=buf.read(b))!=-1) {
    					bot.write(b,0, b.length);
    				}
    			}
    		} catch (Exception  e) {
    			e.printStackTrace();
    		}
    }*/
	
	
	/**
	 * 放射科展示页面
	 * <p>Title: toJhShow</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月12日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toJhShow.act")
	public ModelAndView toJhShow(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String url = "/kqdsFront/jh/jh_show.jsp"; 
    	return new ModelAndView(url);
	}
	/**
	 * 叫号显示页面
	 * <p>Title: toExhibition</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月12日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toExhibition.act")
	public ModelAndView toExhibition(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String url = "/kqdsFront/jh/exhibition.jsp"; 
    	return new ModelAndView(url);
	}
	
	/**
	 * 放射科人员填写超时页面
	 * <p>Title: toOvertime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月12日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toOvertime.act")
	public ModelAndView toOvertime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		String regSeqId=request.getParameter("regid");
		String usercode=request.getParameter("usercode");
		String username=request.getParameter("username");
		String sex=request.getParameter("sex");
		String askpersonname=request.getParameter("askpersonname");
		String age=request.getParameter("age");
		String phone=request.getParameter("phone");
		String floor=request.getParameter("floor");
		String number=request.getParameter("number");
    	String url = "/kqdsFront/jh/overtime.jsp"; 
    	ModelAndView model = new ModelAndView();
    	model.addObject("id", id);
    	model.addObject("regid", regSeqId);
    	model.addObject("usercode", usercode);
    	model.addObject("username", username);
    	model.addObject("sex", sex);
    	model.addObject("askpersonname", askpersonname);
    	model.addObject("age", age);
    	model.addObject("phone", phone);
    	model.addObject("floor", floor);
    	model.addObject("number", number);
    	model.setViewName(url);
    	return model;
	}
	/**
	 * 咨询填写患者预约单
	 * <p>Title: toAppointment</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年3月12日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAppointment.act")
	public ModelAndView toAppointment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode=request.getParameter("usercode");
		String regid=request.getParameter("regid");
		String username=request.getParameter("username");
		String sex=request.getParameter("sex");
		String askperson=request.getParameter("askperson");
		String age=request.getParameter("age");
		String index=request.getParameter("index");
    	String url = "/kqdsFront/jh/appointment.jsp"; 
    	ModelAndView model = new ModelAndView();
    	model.addObject("usercode", usercode);
    	model.addObject("regid", regid);
    	model.addObject("username", username);
    	model.addObject("sex", sex);
    	model.addObject("askperson", askperson);
    	model.addObject("age", age);
    	model.addObject("index", index);
    	model.setViewName(url);
    	return model;
	}
	
	@RequestMapping(value = "/toZxSickOvertime.act")
	public ModelAndView toZxSickOvertime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String regid=request.getParameter("regid");
		String askperson=request.getParameter("askperson");
    	String url = "/kqdsFront/jh/zx_sick_overtime.jsp"; 
    	ModelAndView model = new ModelAndView();
    	model.addObject("regid", regid);
    	model.addObject("askperson", askperson);
    	model.setViewName(url);
    	return model;
	}
	
	@RequestMapping(value = "/toRadiologyTime.act")
	public ModelAndView toRadiologyTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String url = "/kqdsFront/jh/time_statistics.jsp"; 
    	ModelAndView model = new ModelAndView();
    	model.setViewName(url);
    	return model;
	}
	
	
}
