package com.kqds.service.base.soundRecord;

import com.kqds.core.global.YZSysProps;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsSoundRecord;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_SoundRecordLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public List<JSONObject> selectList(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_SOUND_RECORD + ".selectList", map);
    

    String record_file_dir = YZSysProps.getProp(SysParaUtil.RECORD_WEBSITE_URL);
    if (YZUtility.isNullorEmpty(record_file_dir)) {
      throw new Exception("录音文件web访问路径没有配置或为空");
    }
    if (!record_file_dir.endsWith("/")) {
      record_file_dir = record_file_dir + "/";
    }
    for (JSONObject json : list)
    {
      String datedir = json.getString("datedir");
      String filename = json.getString("filename");
      String weburl = record_file_dir + datedir + "/" + filename;
      json.put("weburl", weburl);
    }
    return list;
  }
  
  public JSONObject selectDetail(String table, String seqId)
    throws Exception
  {
    JSONObject jobj = (JSONObject)this.dao.findForObject(TableNameUtil.KQDS_SOUND_RECORD + ".selectDetail", seqId);
    return jobj;
  }
  
  public int updateRecordDataByDate(String fileDir, String recordDate)
    throws Exception
  {
    int updateCount = 0;
    List<JSONObject> list = getRecordFileList(fileDir, recordDate);
    if (list == null) {
      return updateCount;
    }
    for (JSONObject record : list)
    {
      int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_SOUND_RECORD + ".selectRecordDataByDate", record)).intValue();
      if (count == 0)
      {
        KqdsSoundRecord bean = (KqdsSoundRecord)JSONObject.toBean(record, KqdsSoundRecord.class);
        bean.setCreatetime(YZUtility.getCurDateTimeStr());
        bean.setSeqId(YZUtility.getUUID());
        this.dao.saveSingleUUID(TableNameUtil.KQDS_SOUND_RECORD, bean);
        updateCount++;
      }
    }
    return updateCount;
  }
  
  public List<JSONObject> getRecordFileList(String fileDir, String recordDate)
    throws Exception
  {
    if (YZUtility.isNullorEmpty(fileDir)) {
      throw new Exception("录音文件存在路径未配置或为空");
    }
    File topDirObj = new File(fileDir);
    if (!topDirObj.exists()) {
      throw new Exception("录音文件存放目录不存在");
    }
    if (YZUtility.isNullorEmpty(recordDate)) {
      recordDate = YZUtility.getDateStr(new Date());
    }
    String[] dateArray = recordDate.split("-");
    int count = 0;
    for (String element : dateArray) {
      if (YZUtility.isNullorEmpty(element)) {
        count++;
      }
    }
    if (count > 0) {
      throw new Exception("日期格式非法");
    }
    String year = dateArray[0];
    String month = dateArray[1];
    String day = dateArray[2];
    
    String dateDir = year + "/" + month + "/" + day;
    String fullFileDir = null;
    if (fileDir.endsWith("/")) {
      fullFileDir = fileDir + dateDir;
    } else {
      fullFileDir = fileDir + "/" + dateDir;
    }
    File dirObj = new File(fullFileDir);
    if (!dirObj.exists()) {
      return null;
    }
    File[] files = dirObj.listFiles();
    if (files == null) {
      return null;
    }
    List<JSONObject> fileList = new ArrayList();
    for (File f : files)
    {
      String fname = f.getName();
      if ((f.isFile()) && (fname.endsWith("wav")))
      {
        JSONObject fileJson = new JSONObject();
        fileJson.put("datedir", dateDir);
        fileJson.put("filename", fname);
        
        getParamValsByFileName(fname, fileJson);
        
        fileList.add(fileJson);
      }
    }
    return fileList;
  }
  
  private static void getParamValsByFileName(String fileName, JSONObject paramJson)
    throws Exception
  {
    String[] fileNameArray = fileName.split("-");
    if (fileNameArray.length != 8) {
      throw new Exception("文件名称格式非法");
    }
    int checkCnt = 0;
    for (String element : fileNameArray) {
      if (YZUtility.isNullorEmpty(element)) {
        checkCnt++;
      }
    }
    if (checkCnt > 0) {
      throw new Exception("文件名称格式非法");
    }
    String linenum = fileNameArray[0];
    String phonenumber = fileNameArray[7].replace(".wav", "");
    String recordtime = fileNameArray[1] + "-" + fileNameArray[2] + "-" + fileNameArray[3] + " " + fileNameArray[4] + ":" + fileNameArray[5] + ":" + fileNameArray[6];
    
    paramJson.put("linenum", linenum);
    paramJson.put("phonenumber", phonenumber);
    paramJson.put("recordtime", recordtime);
  }
}
