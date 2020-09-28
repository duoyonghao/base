package com.kqds.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.kqds.entity.sys.YZDict;
import com.kqds.service.base.label.KQDS_hz_LabellPatientLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.sys.DictUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Slf4j
@Component
@Controller
public class Job {
	private Logger log = LoggerFactory.getLogger(Job.class);
	@Autowired
	private KQDS_hz_labelLogic logic;
	@Autowired
	private KQDS_hz_LabellPatientLogic patientLogic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private KQDS_REGLogic reglogic;
	// @Scheduled(cron="*/10 * * * * *")
	// public void s10(){
	// org.jeecgframework.core.util.LogUtil.info("==== 十秒执行一次=======10s");
	// }
	//
	// @Scheduled(cron="0 */1 * * * *")
	// public void m1(){
	// org.jeecgframework.core.util.LogUtil.info("1m");
	// }

	/**
	 * 每天1点执行一次
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void oneOClockPerDay() {
		log.info("1h");
	}
	@Scheduled(cron = "0 40 23 * * ?")
	public void Scheduled() throws Exception{
//		CacheUtil.openCache();
//		CacheUtil.del("ck");
//		CacheUtil.del("ckQuery");
//		CacheUtil.del("cksupplier");
//		CacheUtil.close();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String time = df.format(new Date());
		Map<String,String> map = new HashMap<String,String>();
		if(time!=null&&!time.equals("")){
			//先删除数据再新增数据
			map.put("starttime", time + ConstUtil.TIME_START);
			map.put("endtime", time + ConstUtil.TIME_END);
			map.put("organization", "HUDH");
			List<JSONObject> list1=patientLogic.findLabelByCreatetime1(map);
			if(list1.size()>0){
				//删除包含usercode的数据
				StringBuffer str = new StringBuffer();
				CacheUtil.openCache();
				for (int i = 0; i < list1.size(); i++) {
					Set<String> s = CacheUtil.keys("labelQuery:*"+list1.get(i).getString("userid")+"*");
					if(s.size()>0){
						for (String string2 : s) {
							CacheUtil.del(string2);
							CacheUtil.removeZSet("label:key", string2.substring(11, string2.length()));
							CacheUtil.delMapKey("label:value", string2.substring(11, string2.length()));
						}
					}
					CacheUtil.delMapKey("label:name", list1.get(i).getString("userid"));
					if(i==list1.size()-1){
						str.append("\'"+list1.get(i).getString("userid")+"\'");
					}else{
						str.append("\'"+list1.get(i).getString("userid")+"\',");
					}
				}
				map.put("usercode", str.toString());
			}
			if(map.get("usercode")!=null&&!map.get("usercode").equals("")){
				map.put("starttime", "");
				map.put("endtime", "");
			}
			List<JSONObject> list = patientLogic.findLabelByCreatetime(map);
			if(list!=null&&list.size()>0){
				String usercodes="";
				String usercode="";
				for (JSONObject json : list) {
					if(!usercodes.contains(json.getString("userid"))){
						String labelID="";
						String labelName1="";
						String labelName2="";
						String labelName3="";
						long time1=0;
						if(usercodes.equals("")){
							usercodes=json.getString("userid");							
						}else{
							usercodes=usercodes+","+json.getString("userid");
						}
						usercode=json.getString("userid");	
						for (JSONObject json1 : list) {
							//患者编号相同 添加标签id到字段
							if(usercode.equals(json1.getString("userid"))){
								String createtime=json1.getString("time");
								SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = format.parse(createtime);  
								//日期转时间戳（毫秒）
								time1=date.getTime();
								if(labelID.equals("")){
									if(!YZUtility.isNullorEmpty(json1.getString("askperson"))){
										labelID=usercode+","+json1.getString("labeloneid")+","+json1.getString("labeltwoid")
										+","+json1.getString("labelthreeid")+","+json1.getString("askperson")+",";
									}else{
										labelID=usercode+","+json1.getString("labeloneid")+","+json1.getString("labeltwoid")
										+","+json1.getString("labelthreeid")+",";
									}
								}else{
									if(labelID.contains(json1.getString("labeloneid"))){
										if(labelID.contains(json1.getString("labeltwoid"))){
											if(!labelID.contains(json1.getString("labelthreeid"))){
												labelID=labelID+json1.getString("labelthreeid")+",";
											}
										}else{
											if(!labelID.contains(json1.getString("labelthreeid"))){
												labelID=labelID+json1.getString("labeltwoid")+","+json1.getString("labelthreeid")+",";
											}
										}
									}else{
											labelID=labelID+json1.getString("labeloneid")+","+json1.getString("labeltwoid")+","+json1.getString("labelthreeid")+",";
									}
								}
								
								if(labelName1.equals("")){
									labelName1=json1.getString("labelonename")+":"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
								}else{
									if(labelName1.contains(json1.getString("labelonename"))){
										if(labelName1.contains(json1.getString("labeltwoname"))){
											labelName1+=","+json1.getString("labelthreename");
										}else{
											labelName1+=";"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
										}
									}else{
										if(labelName2.equals("")){
											labelName2=json1.getString("labelonename")+":"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
										}else{
											if(labelName2.contains(json1.getString("labelonename"))){
												if(labelName2.contains(json1.getString("labeltwoname"))){
													labelName2+=","+json1.getString("labelthreename");
												}else{
													labelName2+=";"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
												}
											}else{
												if(labelName3.equals("")){
													labelName3=json1.getString("labelonename")+":"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
												}else{
													if(labelName3.contains(json1.getString("labelonename"))){
														if(labelName3.contains(json1.getString("labeltwoname"))){
															labelName3+=","+json1.getString("labelthreename");
														}else{
															labelName3+=";"+json1.getString("labeltwoname")+":"+json1.getString("labelthreename");
														}
													}
												}
											}
										}
									}
								}
							}
						}
						String labelName="";
						if(!labelName1.equals("")){
							labelName+=labelName1+"。";
						}
						if(!labelName2.equals("")){
							labelName+=labelName2+"。";
						}
						if(!labelName3.equals("")){
							labelName+=labelName3+"。";
						}
						//添加标签到缓存
						//CacheUtil.queryVisitArticleStatistics(1);
						Map<String,String> key=new HashMap<String,String>();
						key.put(labelID, usercode);
						Map<String,String> key1=new HashMap<String,String>();
						key1.put(usercode,labelName);
						CacheUtil.setMap("label:value", key);
						CacheUtil.setMap("label:name", key1);
						CacheUtil.addZSet("label:key",labelID,Double.parseDouble(time1+""));
						CacheUtil.set("labelQuery:"+labelID, usercode);
						CacheUtil.close(); 
					}
				}
			}
		}
    }
	
	
//	@Scheduled(cron = "0 40 23 * * ?")
//	public void Scheduled1() throws Exception{
//		CacheUtil.openCache();
//		CacheUtil.del("ck");
//		CacheUtil.del("ckQuery");
//		CacheUtil.del("cksupplier");
//		CacheUtil.close();
//	}
//	@Scheduled(cron = "0 50 14 * * ?")
//	public void Scheduled2() throws Exception{
//		CacheUtil.openCache();
//		CacheUtil.del("ck");
//		CacheUtil.del("ckQuery");
//		CacheUtil.del("cksupplier");
//		CacheUtil.close();
//	}
}