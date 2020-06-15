package com.kqds.timer;

import com.kqds.service.base.label.KQDS_hz_LabellPatientLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.CacheUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

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
  
  @Scheduled(cron = "0 0 1 * * ?")
  public void oneOClockPerDay() {
    this.log.info("1h");
  }
  
  @Scheduled(cron = "0 40 23 * * ?")
  public void Scheduled() throws Exception {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String time = df.format(new Date());
    Map<String, String> map = new HashMap<>();
    if (time != null && !time.equals("")) {
      map.put("starttime", String.valueOf(time) + ConstUtil.TIME_START);
      map.put("endtime", String.valueOf(time) + ConstUtil.TIME_END);
      map.put("organization", "HUDH");
      List<JSONObject> list1 = this.patientLogic.findLabelByCreatetime1(map);
      if (list1.size() > 0) {
        StringBuffer str = new StringBuffer();
        CacheUtil.openCache();
        for (int i = 0; i < list1.size(); i++) {
          Set<String> s = CacheUtil.keys("labelQuery:*" + ((JSONObject)list1.get(i)).getString("userid") + "*");
          if (s.size() > 0)
            for (String string2 : s) {
              CacheUtil.del(new String[] { string2 });
              CacheUtil.removeZSet("label:key", string2.substring(11, string2.length()));
              CacheUtil.delMapKey("label:value", string2.substring(11, string2.length()));
            }  
          CacheUtil.delMapKey("label:name", ((JSONObject)list1.get(i)).getString("userid"));
          if (i == list1.size() - 1) {
            str.append("'" + ((JSONObject)list1.get(i)).getString("userid") + "'");
          } else {
            str.append("'" + ((JSONObject)list1.get(i)).getString("userid") + "',");
          } 
        } 
        map.put("usercode", str.toString());
      } 
      if (map.get("usercode") != null && !((String)map.get("usercode")).equals("")) {
        map.put("starttime", "");
        map.put("endtime", "");
      } 
      List<JSONObject> list = this.patientLogic.findLabelByCreatetime(map);
      if (list != null && list.size() > 0) {
        String usercodes = "";
        String usercode = "";
        for (JSONObject json : list) {
          if (!usercodes.contains(json.getString("userid"))) {
            String labelID = "";
            String labelName1 = "";
            String labelName2 = "";
            String labelName3 = "";
            long time1 = 0L;
            if (usercodes.equals("")) {
              usercodes = json.getString("userid");
            } else {
              usercodes = String.valueOf(usercodes) + "," + json.getString("userid");
            } 
            usercode = json.getString("userid");
            for (JSONObject json1 : list) {
              if (usercode.equals(json1.getString("userid"))) {
                String createtime = json1.getString("time");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(createtime);
                time1 = date.getTime();
                if (labelID.equals("")) {
                  if (!YZUtility.isNullorEmpty(json1.getString("askperson"))) {
                    labelID = String.valueOf(usercode) + "," + json1.getString("labeloneid") + "," + json1.getString("labeltwoid") + 
                      "," + json1.getString("labelthreeid") + "," + json1.getString("askperson") + ",";
                  } else {
                    labelID = String.valueOf(usercode) + "," + json1.getString("labeloneid") + "," + json1.getString("labeltwoid") + 
                      "," + json1.getString("labelthreeid") + ",";
                  } 
                } else if (labelID.contains(json1.getString("labeloneid"))) {
                  if (labelID.contains(json1.getString("labeltwoid"))) {
                    if (!labelID.contains(json1.getString("labelthreeid")))
                      labelID = String.valueOf(labelID) + json1.getString("labelthreeid") + ","; 
                  } else if (!labelID.contains(json1.getString("labelthreeid"))) {
                    labelID = String.valueOf(labelID) + json1.getString("labeltwoid") + "," + json1.getString("labelthreeid") + ",";
                  } 
                } else {
                  labelID = String.valueOf(labelID) + json1.getString("labeloneid") + "," + json1.getString("labeltwoid") + "," + json1.getString("labelthreeid") + ",";
                } 
                if (labelName1.equals("")) {
                  labelName1 = String.valueOf(json1.getString("labelonename")) + ":" + json1.getString("labeltwoname") + ":" + json1.getString("labelthreename");
                  continue;
                } 
                if (labelName1.contains(json1.getString("labelonename"))) {
                  if (labelName1.contains(json1.getString("labeltwoname"))) {
                    labelName1 = String.valueOf(labelName1) + "," + json1.getString("labelthreename");
                    continue;
                  } 
                  labelName1 = String.valueOf(labelName1) + ";" + json1.getString("labeltwoname") + ":" + json1.getString("labelthreename");
                  continue;
                } 
                if (labelName2.equals("")) {
                  labelName2 = String.valueOf(json1.getString("labelonename")) + ":" + json1.getString("labeltwoname") + ":" + json1.getString("labelthreename");
                  continue;
                } 
                if (labelName2.contains(json1.getString("labelonename"))) {
                  if (labelName2.contains(json1.getString("labeltwoname"))) {
                    labelName2 = String.valueOf(labelName2) + "," + json1.getString("labelthreename");
                    continue;
                  } 
                  labelName2 = String.valueOf(labelName2) + ";" + json1.getString("labeltwoname") + ":" + json1.getString("labelthreename");
                  continue;
                } 
                if (labelName3.equals("")) {
                  labelName3 = String.valueOf(json1.getString("labelonename")) + ":" + json1.getString("labeltwoname") + ":" + json1.getString("labelthreename");
                  continue;
                } 
                if (labelName3.contains(json1.getString("labelonename"))) {
                  if (labelName3.contains(json1.getString("labeltwoname"))) {
                    labelName3 = String.valueOf(labelName3) + "," + json1.getString("labelthreename");
                    continue;
                  } 
                  labelName3 = String.valueOf(labelName3) + ";" + json1.getString("labeltwoname") + ":" + json1.getString("labelthreename");
                } 
              } 
            } 
            String labelName = "";
            if (!labelName1.equals(""))
              labelName = String.valueOf(labelName) + labelName1 + "。"; 
            if (!labelName2.equals(""))
              labelName = String.valueOf(labelName) + labelName2 + "。"; 
            if (!labelName3.equals(""))
              labelName = String.valueOf(labelName) + labelName3 + "。"; 
            Map<String, String> key = new HashMap<>();
            key.put(labelID, usercode);
            Map<String, String> key1 = new HashMap<>();
            key1.put(usercode, labelName);
            CacheUtil.setMap("label:value", key);
            CacheUtil.setMap("label:name", key1);
            CacheUtil.addZSet("label:key", labelID, Double.parseDouble((new StringBuilder(String.valueOf(time1))).toString()));
            CacheUtil.set("labelQuery:" + labelID, usercode);
            CacheUtil.close();
          } 
        } 
      } 
    } 
  }
}
