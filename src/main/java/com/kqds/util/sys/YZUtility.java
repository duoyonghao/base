package com.kqds.util.sys;

import com.kqds.core.util.YZGuid;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.chain.ChainUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YZUtility {
  private static Logger log = LoggerFactory.getLogger(YZUtility.class);
  
  public static String DEFAULT_UPLOAD_MODULE_NAME = "notify";
  
  public static final int WITHGROUP = 0;
  
  public static final int WITHOUTGROUP = 1;
  
  public static int SHEET_NO_LENGTH = 6;
  
  private static DecimalFormat numFormat = new DecimalFormat("#0.00");
  
  private static DecimalFormat numFormatG = new DecimalFormat("#,##0.00");
  
  private static FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
  
  private static FastDateFormat dateFormatDay = FastDateFormat.getInstance("yyyy-MM-dd");
  
  private static FastDateFormat dateFormatDayYmd = FastDateFormat.getInstance("yyyyMMdd");
  
  private static FastDateFormat dateFormatS = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss:SSS");
  
  public static String DATE_FORMAT_NOSPLIT = "yyyyMMddHHmmssSSS";
  
  public static String DATE_FORMAT_NOSPLIT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
  
  private static FastDateFormat dateFormatCn = FastDateFormat.getInstance("yyyy年MM月dd日", Locale.CHINESE);
  
  public static void deleteKeyOfMap(Map<String, String> paramsMap, String keyid) {
    Iterator<String> iter = paramsMap.keySet().iterator();
    while (iter.hasNext()) {
      String key = iter.next();
      if (keyid.equals(key))
        iter.remove(); 
    } 
  }
  
  public static String convertLong2Ymdhms(long time) {
    Date date = new Date(time);
    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sd.format(date);
  }
  
  public static String getThirtyMinutesLater() {
    long currentTime = System.currentTimeMillis() + 1800000L;
    Date date = new Date(currentTime);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = "";
    nowTime = df.format(date);
    return nowTime;
  }
  
  public static Date strToDateLong(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
  
  public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
    if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime())
      return true; 
    Calendar date = Calendar.getInstance();
    date.setTime(nowTime);
    Calendar begin = Calendar.getInstance();
    begin.setTime(startTime);
    Calendar end = Calendar.getInstance();
    end.setTime(endTime);
    if (date.after(begin) && date.before(end))
      return true; 
    return false;
  }
  
  public static String getCurrLoginPersonSeqId(HttpServletRequest request) {
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (person != null)
      return person.getSeqId(); 
    return null;
  }
  
  public static String getUrlParamsByMap(Map<String, Object> map) {
    if (map == null)
      return ""; 
    StringBuffer sb = new StringBuffer();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      sb.append(String.valueOf(entry.getKey()) + "=" + entry.getValue());
      sb.append("&");
    } 
    String s = sb.toString();
    if (s.endsWith("&"))
      s = StringUtils.substringBeforeLast(s, "&"); 
    return s;
  }
  
  public static String getImageUrlRelative(String module, String attrId, String attrName) {
    attrId = attrId.replace(",", "");
    attrName = attrName.replace("*", "");
    String[] attrIdArray = attrId.split("_");
    String imageUrl = "/upload/" + module + "/";
    imageUrl = String.valueOf(imageUrl) + attrIdArray[0] + "/" + attrIdArray[1] + "_" + attrName;
    return imageUrl;
  }
  
  public static String getHard4File() {
    Calendar cld = Calendar.getInstance();
    int year = cld.get(1) % 100;
    int month = cld.get(2) + 1;
    String mon = (month >= 10) ? (new StringBuilder(String.valueOf(month))).toString() : ("0" + month);
    String hard = String.valueOf(year) + mon;
    return hard;
  }
  
  public static String getImgUrl() {
    String hard = getHard4File();
    String imgurl = "\\imgUrl" + File.separator + hard + File.separator;
    return imgurl;
  }
  
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("Proxy-Client-IP"); 
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("WL-Proxy-Client-IP"); 
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getRemoteAddr(); 
    return ip;
  }
  
  public static String[] getDateArray(String startStr, String endStr) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<String> result = new ArrayList<>();
    try {
      Date start = sdf.parse(startStr);
      Date end = sdf.parse(endStr);
      Calendar tempStart = Calendar.getInstance();
      tempStart.setTime(start);
      Calendar tempEnd = Calendar.getInstance();
      tempEnd.setTime(end);
      tempEnd.add(6, 1);
      while (tempStart.before(tempEnd)) {
        result.add(sdf.format(tempStart.getTime()));
        tempStart.add(6, 1);
      } 
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    String[] array = new String[result.size()];
    String[] s = result.<String>toArray(array);
    return s;
  }
  
  public static String getRondom() throws NoSuchAlgorithmException {
    String result = "";
    result = YZGuid.getRawGuid();
    return result;
  }
  
  public static String ConvertStringIds4Query(String stringIds) {
    if (isNullorEmpty(stringIds))
      return null; 
    if (stringIds.contains("'"))
      stringIds = stringIds.replace("'", ""); 
    if (stringIds.endsWith(","))
      stringIds = stringIds.substring(0, stringIds.length() - 1); 
    StringBuffer bf = new StringBuffer("");
    String[] idArrayTmp = stringIds.split(",");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = idArrayTmp).length, b = 0; b < i; ) {
      String idTmp = arrayOfString1[b];
      if (!isNullorEmpty(idTmp))
        bf.append("'").append(idTmp).append("',"); 
      b++;
    } 
    if (bf.toString().endsWith(","))
      bf.deleteCharAt(bf.length() - 1); 
    return bf.toString();
  }
  
  public static String ConvertListIds4Query(List<String> idList) {
    String idstrs = ConvertList2String(idList);
    return ConvertStringIds4Query(idstrs);
  }
  
  public static String ConvertList2String(List<String> idList) {
    StringBuffer idbf = new StringBuffer();
    if (idList == null || idList.size() == 0)
      return ""; 
    for (String id : idList) {
      if (isNullorEmpty(id))
        continue; 
      idbf.append(id).append(",");
    } 
    return idbf.toString();
  }
  
  public static List<String> ConvertString2List(String idsStr) {
    if (isNotNullOrEmpty(idsStr))
      idsStr = idsStr.replace("'", ""); 
    List<String> list = new ArrayList<>();
    String[] idArrayTmp = idsStr.split(",");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = idArrayTmp).length, b = 0; b < i; ) {
      String idTmp = arrayOfString1[b];
      if (!isNullorEmpty(idTmp))
        list.add(idTmp); 
      b++;
    } 
    return list;
  }
  
  public static String getUUID() {
    return UUID.randomUUID().toString();
  }
  
  public static String getSystemID() {
    String str = (new SimpleDateFormat("yyyyMMddHHmmssSSS"))
      .format(new Date());
    return "JGD" + String.valueOf(str);
  }
  
  public static List convertJsonList2ClassList(List<JSONObject> jsonlist, Class cls) throws InstantiationException, IllegalAccessException {
    List<Object> newList = new ArrayList();
    for (JSONObject jsonObject : jsonlist) {
      Object newObj = JSONObject.toBean(jsonObject, cls);
      newList.add(newObj);
    } 
    return newList;
  }
  
  public static List<String> getAllDate(String start, String end) throws ParseException {
    List<String> datelist = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dBegin = sdf.parse(start);
    Date dEnd = sdf.parse(end);
    List<Date> lDate = findDates(dBegin, dEnd);
    for (Date date : lDate)
      datelist.add(sdf.format(date)); 
    return datelist;
  }
  
  private static List<Date> findDates(Date dBegin, Date dEnd) {
    List<Date> lDate = new ArrayList<>();
    lDate.add(dBegin);
    Calendar calBegin = Calendar.getInstance();
    calBegin.setTime(dBegin);
    Calendar calEnd = Calendar.getInstance();
    calEnd.setTime(dEnd);
    while (dEnd.after(calBegin.getTime())) {
      calBegin.add(5, 1);
      lDate.add(calBegin.getTime());
    } 
    return lDate;
  }
  
  public static String[] getBetweenDatesArray(String startStr, String endStr) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<String> result = new ArrayList<>();
    try {
      Date start = sdf.parse(startStr);
      Date end = sdf.parse(endStr);
      Calendar tempStart = Calendar.getInstance();
      tempStart.setTime(start);
      Calendar tempEnd = Calendar.getInstance();
      tempEnd.setTime(end);
      tempEnd.add(6, 1);
      while (tempStart.before(tempEnd)) {
        result.add(sdf.format(tempStart.getTime()));
        tempStart.add(6, 1);
      } 
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    String[] array = new String[result.size()];
    String[] s = result.<String>toArray(array);
    return s;
  }
  
  public static boolean isDateYYYYmmdd(String date) {
    if (date.length() != 10)
      return false; 
    String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
    Pattern pat = Pattern.compile(rexp);
    Matcher mat = pat.matcher(date);
    boolean dateType = mat.matches();
    return dateType;
  }
  
  public static void RETURN_OBJ(Object object, HttpServletResponse response, Logger logger) throws IOException {
    response.setContentType("text/json;charset=UTF-8");
    JSONObject job = JSONObject.fromObject(object);
    PrintWriter pw = response.getWriter();
    pw.println(job.toString());
    pw.flush();
  }
  
  public static void RETURN_LIST(List list, HttpServletResponse response, Logger logger) throws IOException {
    response.setContentType("text/json;charset=UTF-8");
    JSONArray jsonArray = JSONArray.fromObject(list);
    PrintWriter pw = response.getWriter();
    pw.println(jsonArray.toString());
    pw.flush();
  }
  
  public static void DEAL_SUCCESS(JSONObject retrunObj, String succesMsg, HttpServletResponse response, Logger logger) throws IOException {
    response.setContentType("text/json;charset=UTF-8");
    if (retrunObj == null)
      retrunObj = new JSONObject(); 
    if (isNullorEmpty(succesMsg))
      succesMsg = "操作成功"; 
    retrunObj.put("retState", "0");
    retrunObj.put("retMsrg", succesMsg);
    PrintWriter pw = response.getWriter();
    pw.println(retrunObj.toString());
    pw.flush();
  }
  
  public static void DEAL_SUCCESS_VALID(boolean result, HttpServletResponse response) throws IOException {
    String resultString = "{\"valid\":" + result + "}";
    PrintWriter pw = response.getWriter();
    pw.println(resultString);
    pw.flush();
  }
  
  public static void DEAL_ERROR(String errorMsg, boolean isThrow, Exception ex, HttpServletResponse response, Logger logger) throws Exception {
    response.setContentType("text/json;charset=UTF-8");
    if (isNullorEmpty(errorMsg))
      errorMsg = "操作失败"; 
    if (ex != null) {
      ex.printStackTrace();
      if (logger != null) {
        logger.error(String.valueOf(errorMsg) + "##" + ex.getMessage());
      } else {
        log.error(String.valueOf(errorMsg) + "##" + ex.getMessage());
      } 
    } 
    if (response != null) {
      JSONObject jobj = new JSONObject();
      jobj.put("retState", "1");
      jobj.put("retMsrg", errorMsg);
      PrintWriter pw = response.getWriter();
      pw.println(jobj.toString());
      pw.flush();
    } 
  }
  
  public static boolean isStrInArray(String userid, String arrayStr) {
    boolean flag = false;
    if (isNullorEmpty(arrayStr))
      return flag; 
    String[] array = arrayStr.split(",");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = array).length, b = 0; b < i; ) {
      String id = arrayOfString1[b];
      if (userid.equals(id)) {
        flag = true;
        break;
      } 
      b++;
    } 
    return flag;
  }
  
  public static boolean isStrInArrayEach(String arrayStr1, String arrayStr2) {
    boolean flag = false;
    if (isNullorEmpty(arrayStr1))
      return flag; 
    if (isNullorEmpty(arrayStr2))
      return flag; 
    String[] array1 = arrayStr1.split(",");
    String[] array2 = arrayStr2.split(",");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = array1).length, b = 0; b < i; ) {
      String id = arrayOfString1[b];
      byte b1;
      int j;
      String[] arrayOfString;
      for (j = (arrayOfString = array2).length, b1 = 0; b1 < j; ) {
        String id2 = arrayOfString[b1];
        if (id2.equals(id)) {
          flag = true;
          break;
        } 
        b1++;
      } 
      b++;
    } 
    return flag;
  }
  
  public static boolean getExist(String savePath, String fileExtName) throws IOException {
    String filePath = String.valueOf(savePath) + File.separator + fileExtName;
    if ((new File(filePath)).exists())
      return true; 
    return false;
  }
  
  public void zip(ZipOutputStream out, File file) throws Exception {
    try {
      byte[] buf = new byte[1024];
      if (file.isDirectory()) {
        zip(out, file.listFiles());
      } else {
        FileInputStream in = new FileInputStream(file);
        out.putNextEntry(new ZipEntry(file.getName()));
        int len;
        while ((len = in.read(buf)) > 0)
          out.write(buf, 0, len); 
        out.closeEntry();
        in.close();
      } 
      out.flush();
    } catch (FileNotFoundException fileNotFoundException) {
    
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public void zip(ZipOutputStream out, List<String> filePath) throws Exception {
    for (String s : filePath) {
      File file = new File(s);
      zip(out, file);
    } 
  }
  
  public void zip(ZipOutputStream out, File[] files) throws Exception {
    byte b;
    int i;
    File[] arrayOfFile;
    for (i = (arrayOfFile = files).length, b = 0; b < i; ) {
      File file = arrayOfFile[b];
      zip(out, file);
      b++;
    } 
  }
  
  public void zip(String zipFile, List<String> files) throws Exception {
    FileOutputStream fos = new FileOutputStream(zipFile);
    zip(fos, files);
  }
  
  public void zip(OutputStream fos, List<String> files) throws Exception {
    ZipOutputStream out = new ZipOutputStream(fos);
    out.setEncoding("GBK");
    zip(out, files);
    out.close();
  }
  
  public static String Message(String content, int type) {
    StringBuffer body = new StringBuffer();
    body.append("<div align=center><table width=\"300\" class=\"MessageBox\"><tbody><tr>");
    if (type == 0)
      body.append("<td class=\"msg info\" style='font-size:11pt'>"); 
    if (type == 1)
      body.append("<td class=\"msg error\" style='font-size:11pt'>"); 
    if (type == 2)
      body.append("<td class=\"msg warning\" style='font-size:11pt'>"); 
    if (type == 3)
      body.append("<td class=\"msg forbidden\">"); 
    if (type == 4)
      body.append("<td class=\"msg stop\">"); 
    if (type == 5)
      body.append("<td class=\"msg blank\">"); 
    body.append(content);
    body.append("</td></tr></tbody></table></div>");
    return body.toString();
  }
  
  public static boolean findId(String str, String id) {
    if (str == null || id == null || "".equals(str) || "".equals(id))
      return false; 
    String[] aStr = str.split(",");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = aStr).length, b = 0; b < i; ) {
      String tmp = arrayOfString1[b];
      if (tmp.equals(id))
        return true; 
      b++;
    } 
    return false;
  }
  
  public static String parseString(String s) {
    if (s == null)
      return ""; 
    return s.trim();
  }
  
  public static String parseString(String s, String defaultValue) {
    if (s == null || s.trim().equals(""))
      return defaultValue; 
    return s.trim();
  }
  
  public static boolean isNullorEmpty(String str) {
    if (str == null)
      return true; 
    str = str.trim();
    if (str.length() < 1)
      return true; 
    return false;
  }
  
  public static boolean isNotNullOrEmpty(String str) {
    return !isNullorEmpty(str);
  }
  
  public static String null2Empty(String str) {
    if (str == null)
      return ""; 
    return str;
  }
  
  public static String empty2Default(String str, String defaultStr) {
    if (isNullorEmpty(str))
      return defaultStr; 
    return str;
  }
  
  public static String getFormatedStr(double num, int pattern) {
    if (pattern == 1)
      return numFormat.format(num); 
    return numFormatG.format(num);
  }
  
  public static String getCurDateTimeStr() {
    return dateFormat.format(new Date());
  }
  
  public static int getDaysByYearMonth(int year, int month) {
    Calendar a = Calendar.getInstance();
    a.set(1, year);
    a.set(2, month - 1);
    a.set(5, 1);
    a.roll(5, -1);
    int maxDate = a.get(5);
    return maxDate;
  }
  
  public static String getCurDateTimeStr(String pattern) {
    if (pattern == null)
      return getCurDateTimeStr(); 
    FastDateFormat format = FastDateFormat.getInstance(pattern);
    return format.format(new Date());
  }
  
  public static String getCurDateTimeStr(FastDateFormat format) {
    if (format == null)
      return getCurDateTimeStr(); 
    return format.format(new Date());
  }
  
  public static String getDateTimeStr(Date date) {
    if (date == null)
      return getCurDateTimeStr(); 
    return dateFormat.format(date);
  }
  
  public static String getDateStr(Date date) {
    if (date == null)
      return getCurDateTimeStr(); 
    return dateFormatDay.format(date);
  }
  
  public static String getDateStrYmd(Date date) {
    if (date == null)
      return getCurDateTimeStr(); 
    return dateFormatDayYmd.format(date);
  }
  
  public static String getDateTimeStrCn(Date date) {
    if (date == null)
      return getCurDateTimeStr(dateFormatCn); 
    return dateFormatCn.format(date);
  }
  
  private static Calendar clearDate(Date date, int clearNum) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    if (clearNum > 0)
      cal.set(14, 0); 
    if (clearNum > 1)
      cal.set(13, 0); 
    if (clearNum > 2)
      cal.set(12, 0); 
    if (clearNum > 3)
      cal.set(11, 0); 
    if (clearNum > 4)
      cal.set(5, 0); 
    if (clearNum > 5)
      cal.set(2, 0); 
    return cal;
  }
  
  public static String[] getWeekLimitStr() throws Exception {
    return getWeekLimitStr(new Date());
  }
  
  public static String[] getMonthLimitStr() throws Exception {
    return getMonthLimitStr(new Date());
  }
  
  public static String[] getDateLimitStr(Date date) throws Exception {
    Date[] rtDateArray = getDateLimit(date);
    return new String[] { getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1]) };
  }
  
  public static String[] getWeekLimitStr(Date date) throws Exception {
    Date[] rtDateArray = getWeekLimit(date);
    return new String[] { getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1]) };
  }
  
  public static String[] getMonthLimitStr(Date date) throws Exception {
    Date[] rtDateArray = getMonthLimit(date);
    return new String[] { getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1]) };
  }
  
  public static String[] getYearLimitStr(Date date) throws Exception {
    Date[] rtDateArray = getYearLimit(date);
    return new String[] { getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1]) };
  }
  
  public static Date[] getDateLimit(Date date) throws Exception {
    Calendar cal = clearDate(date, 4);
    Date date1 = cal.getTime();
    cal.add(5, 1);
    cal.add(13, -1);
    Date date2 = cal.getTime();
    return new Date[] { date1, date2 };
  }
  
  public static Date getFirstDayOfWeek(Date date) {
    Calendar c = clearDate(date, 4);
    c.setFirstDayOfWeek(2);
    c.set(7, c.getFirstDayOfWeek());
    return c.getTime();
  }
  
  public static Date getLastDayOfWeek(Date date) {
    Calendar c = clearDate(date, 4);
    c.setFirstDayOfWeek(2);
    c.set(7, c.getFirstDayOfWeek() + 6);
    c.set(11, 23);
    c.set(12, 59);
    c.set(13, 59);
    c.set(14, 0);
    return c.getTime();
  }
  
  public static Date[] getWeekLimit(Date date) throws Exception {
    Date date1 = getFirstDayOfWeek(date);
    Date date2 = getLastDayOfWeek(date);
    return new Date[] { date1, date2 };
  }
  
  public static Date[] getMonthLimit(Date date) throws Exception {
    Calendar cal = clearDate(date, 5);
    cal.set(5, 1);
    Date date1 = cal.getTime();
    cal.add(2, 1);
    cal.add(13, -1);
    Date date2 = cal.getTime();
    return new Date[] { date1, date2 };
  }
  
  public static Date[] getYearLimit(Date date) throws Exception {
    Calendar cal = clearDate(date, 6);
    cal.set(2, 0);
    cal.set(5, 1);
    Date date1 = cal.getTime();
    cal.add(1, 1);
    cal.add(13, -1);
    Date date2 = cal.getTime();
    return new Date[] { date1, date2 };
  }
  
  public static Date getDayAfter(String dateStr, int dayCnt) throws Exception {
    return getDayAfter(parseDate(dateStr), dayCnt);
  }
  
  public static Date getDayAfter(Date date, int dayCnt) {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.add(5, dayCnt);
    return cal.getTime();
  }
  
  public static String getFixLengthNum(String str, int length) {
    if (str == null)
      str = ""; 
    String preFix = "";
    for (int i = 0; i < length - str.length(); i++)
      preFix = String.valueOf(preFix) + "0"; 
    return String.valueOf(preFix) + str;
  }
  
  public static String get36BaseStr(long num, int length) throws Exception {
    String numStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    long[] tmpArray = get10ArrayFromNum(num, 36, new long[0]);
    String tmpStr = "";
    for (int i = 0; i < tmpArray.length; i++)
      tmpStr = String.valueOf(tmpStr) + String.valueOf(numStr.charAt((int)tmpArray[i])); 
    String rtStr = getFixLengthNum(tmpStr, length);
    return rtStr;
  }
  
  public static long[] get10ArrayFromNum(long num, int baseNum, long[] num10) throws Exception {
    long[] tmpArray = new long[num10.length + 1];
    tmpArray[0] = num % baseNum;
    if (num10.length > 0)
      System.arraycopy(num10, 0, tmpArray, 1, num10.length); 
    if (num / baseNum == 0L)
      return tmpArray; 
    return get10ArrayFromNum(num / baseNum, baseNum, tmpArray);
  }
  
  public static boolean isNumber(String str) {
    if (str == null || str.equals(""))
      return false; 
    return Pattern.matches("-?\\d*\\.?\\d+(E\\d+)?", str);
  }
  
  public static boolean isInteger(String str) {
    if (str == null || str.equals(""))
      return false; 
    return Pattern.matches("(-?[1-9]\\d*)|0", str);
  }
  
  public static String parseDate2(String str) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new Date(Long.valueOf(str).longValue()));
  }
  
  public static Date parseDate(String dateStr) throws ParseException {
    if (dateStr == null || "".equals(dateStr))
      return null; 
    FastDateFormat format = null;
    if (Pattern.matches("\\d{4}-\\d{1,2}-\\d{1,2}", dateStr)) {
      format = FastDateFormat.getInstance("yyyy-MM-dd");
    } else if (Pattern.matches("\\d{4}\\d{2}\\d{2}", dateStr)) {
      format = FastDateFormat.getInstance("yyyyMMdd");
    } else if (Pattern.matches("\\d{4}年\\d{2}月\\d{2}日", dateStr)) {
      format = FastDateFormat.getInstance("yyyy年MM月dd日", Locale.CHINA);
    } else if (Pattern.matches("\\d{4}年\\d{1,2}月\\d{1,2}日", dateStr)) {
      format = FastDateFormat.getInstance("yyyy年M月d日", Locale.CHINA);
    } else if (Pattern.matches("\\d{1,2}\\w{3}\\d{4}", dateStr)) {
      format = FastDateFormat.getInstance("dMMMyyyy", Locale.ENGLISH);
    } else if (Pattern.matches("\\d{1,2}-\\w{3}-\\d{4}", dateStr)) {
      format = FastDateFormat.getInstance("d-MMM-yyyy", Locale.ENGLISH);
    } else if (dateStr.length() > 20) {
      format = dateFormatS;
    } else {
      format = dateFormat;
    } 
    return DateUtils.parseDate(dateStr, new String[] { format.getPattern() });
  }
  
  public static Timestamp parseTimeStamp() throws ParseException {
    return parseTimeStamp((String)null);
  }
  
  public static Timestamp parseTimeStamp(long longDate) throws ParseException {
    Date utilDate = new Date(longDate);
    Timestamp sqlDate = new Timestamp(utilDate.getTime());
    return sqlDate;
  }
  
  public static Timestamp parseTimeStamp(String dateStr) throws ParseException {
    Date utilDate = null;
    if (dateStr == null) {
      utilDate = new Date();
    } else {
      utilDate = parseDate(dateStr);
    } 
    Timestamp sqlDate = new Timestamp(utilDate.getTime());
    return sqlDate;
  }
  
  public static Date parseSqlDate() throws ParseException {
    return parseSqlDate(null);
  }
  
  public static Date parseSqlDate(String dateStr) throws ParseException {
    Date utilDate = null;
    if (dateStr == null) {
      utilDate = new Date();
    } else {
      utilDate = parseDate(dateStr);
    } 
    Date sqlDate = new Date(utilDate.getTime());
    return sqlDate;
  }
  
  public static String transferCode(String srcStr, String srcCode, String desCode) throws UnsupportedEncodingException {
    if (srcStr == null)
      return null; 
    return new String(srcStr.getBytes(srcCode), desCode);
  }
  
  public static String iso88591ToGbk(String srcStr) throws UnsupportedEncodingException {
    return transferCode(srcStr, "ISO-8859-1", "GBK");
  }
  
  public static String iso88591ToUTF8(String srcStr) throws UnsupportedEncodingException {
    return transferCode(srcStr, "ISO-8859-1", "UTF-8");
  }
  
  public static String getRandomName() {
    String currTimeStr = getCurDateTimeStr(DATE_FORMAT_NOSPLIT);
    String randomStr = String.valueOf((int)Math.random() * 10000);
    return String.valueOf(currTimeStr) + randomStr;
  }
  
  public static void str2Map(String propStr, Map<String, String> rtMap) {
    String[] propArray = propStr.split("\\\r*\\\n{1}");
    for (int i = 0; i < propArray.length; i++) {
      String tmpStr = propArray[i].trim();
      if (tmpStr.length() >= 1 && !tmpStr.startsWith("#")) {
        int tmpIndex = tmpStr.indexOf("=");
        if (tmpIndex >= 0) {
          String key = tmpStr.substring(0, tmpIndex).trim();
          String value = tmpStr.substring(tmpIndex + 1).trim();
          if (key.length() >= 1 && value.length() >= 1)
            rtMap.put(key, value); 
        } 
      } 
    } 
  }
  
  public static void copyMap(Map fromMap, Map<Object, Object> toMap) {
    Iterator iKeys = fromMap.keySet().iterator();
    while (iKeys.hasNext()) {
      Object key = iKeys.next();
      Object value = fromMap.get(key);
      toMap.put(key, value);
    } 
  }
  
  public static String encodeURL(String srcStr) throws Exception {
    if (srcStr == null)
      return ""; 
    return URLEncoder.encode(srcStr, "UTF-8");
  }
  
  public static String decodeURL(String srcStr) throws Exception {
    if (srcStr == null)
      return ""; 
    return URLDecoder.decode(srcStr, "UTF-8");
  }
  
  public static String encodeSpecial(String srcStr) {
    if (srcStr == null)
      return ""; 
    return srcStr.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'").replace("\r\n", "").replace("\n", "").replace("\r", "");
  }
  
  public static String encodeLike(String srcStr) {
    if (srcStr == null)
      return ""; 
    return srcStr.replace("\\", "\\\\").replace("'", "''").replace("%", "\\%").replace("_", "\\_");
  }
  
  public static Map startsWithMap(Map srcMap, String prefix) {
    Map<Object, Object> rtMap = new HashMap<>();
    if (isNullorEmpty(prefix))
      return rtMap; 
    Iterator<String> iKeys = srcMap.keySet().iterator();
    while (iKeys.hasNext()) {
      String key = iKeys.next();
      if (key.startsWith(prefix))
        rtMap.put(key, srcMap.get(key)); 
    } 
    return rtMap;
  }
  
  public static Map endsWithMap(Map srcMap, String postfix) {
    Map<Object, Object> rtMap = new HashMap<>();
    if (isNullorEmpty(postfix))
      return rtMap; 
    Iterator<String> iKeys = srcMap.keySet().iterator();
    while (iKeys.hasNext()) {
      String key = iKeys.next();
      if (key.endsWith(postfix))
        rtMap.put(key, srcMap.get(key)); 
    } 
    return rtMap;
  }
  
  public static void deleteMapNullKeyVal(Map map) {
    removeNullKey(map);
    removeNullValue(map);
  }
  
  private static void removeNullKey(Map map) {
    Set set = map.keySet();
    for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
      Object obj = iterator.next();
      remove(obj, iterator);
    } 
  }
  
  private static void removeNullValue(Map map) {
    Set set = map.keySet();
    for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
      Object obj = iterator.next();
      Object value = map.get(obj);
      remove(value, iterator);
    } 
  }
  
  private static void remove(Object obj, Iterator iterator) {
    if (obj instanceof String) {
      String str = (String)obj;
      if (isNullorEmpty(str))
        iterator.remove(); 
    } else if (obj instanceof Collection) {
      Collection col = (Collection)obj;
      if (col == null || col.isEmpty())
        iterator.remove(); 
    } else if (obj instanceof Map) {
      Map temp = (Map)obj;
      if (temp == null || temp.isEmpty())
        iterator.remove(); 
    } else if (obj instanceof Object[]) {
      Object[] array = (Object[])obj;
      if (array == null || array.length <= 0)
        iterator.remove(); 
    } else if (obj == null) {
      iterator.remove();
    } 
  }
  
  public static String FloatToFixed2(Float nums) {
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    String p = decimalFormat.format(nums);
    return p;
  }
  
  public static String FloatToFixed2(Double nums) {
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    String p = decimalFormat.format(nums);
    return p;
  }
  
  public static Object Obj1ToObj2(Object obj1, Object obj2) throws Exception {
    Field[] fields = obj1.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      String fieldName = field.getName();
      boolean flag = ChainUtil.isHasAttributeInClass(fieldName, obj2.getClass());
      if (flag) {
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Class<?> tCls = obj1.getClass();
        Method getMethod = tCls.getMethod(getMethodName, new Class[0]);
        Object obj1value = getMethod.invoke(obj1, new Object[0]);
        Class<?> tCls2 = obj2.getClass();
        Method setMethod2 = tCls2.getMethod(setMethodName, new Class[] { field.getType() });
        if (obj1value != null && !isNullorEmpty(obj1value.toString()))
          setMethod2.invoke(obj2, new Object[] { obj1value }); 
      } 
    } 
    return obj2;
  }
  
  public static String getPhone(String pbone) {
    String regEx = "[^0-9]";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(pbone);
    return m.replaceAll("").trim();
  }
  
  public static boolean validatemobile(String mobile) {
    if (mobile.length() == 0)
      return false; 
    if (mobile.length() != 11)
      return false; 
    String myreg = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
    Pattern p = Pattern.compile(myreg);
    Matcher m = p.matcher(mobile);
    return m.matches();
  }
  
  public static int compare_date(String sta, String end) throws ParseException {
    int b = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date dt1 = null;
    Date dt2 = null;
    try {
      dt1 = sdf.parse(sta);
      dt2 = sdf.parse(end);
      if (dt1.getTime() < dt2.getTime()) {
        b = 0;
      } else if (dt1.getTime() > dt2.getTime()) {
        b = 1;
      } else {
        b = 2;
      } 
    } catch (ParseException e) {
      log.error("compare_date Error：" + e.getMessage());
      throw e;
    } 
    return b;
  }
  
  public static String getMd5Str(String pwd) {
    try {
      MessageDigest digest = MessageDigest.getInstance("md5");
      byte[] bs = digest.digest(pwd.getBytes());
      String hexString = "";
      byte b;
      int i;
      byte[] arrayOfByte1;
      for (i = (arrayOfByte1 = bs).length, b = 0; b < i; ) {
        byte b1 = arrayOfByte1[b];
        int temp = b1 & 0xFF;
        if (temp < 16 && temp >= 0) {
          hexString = String.valueOf(hexString) + "0" + Integer.toHexString(temp);
        } else {
          hexString = String.valueOf(hexString) + Integer.toHexString(temp);
        } 
        b++;
      } 
      return hexString;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public static boolean valiDateTimeWithLongFormat(String timeStr) {
    String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
    Pattern pattern = Pattern.compile(format);
    Matcher matcher = pattern.matcher(timeStr);
    if (matcher.matches()) {
      pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
      matcher = pattern.matcher(timeStr);
      if (matcher.matches()) {
        int y = Integer.valueOf(matcher.group(1)).intValue();
        int m = Integer.valueOf(matcher.group(2)).intValue();
        int d = Integer.valueOf(matcher.group(3)).intValue();
        if (d > 28) {
          Calendar c = Calendar.getInstance();
          c.set(y, m - 1, 1);
          int lastDay = c.getActualMaximum(5);
          return (lastDay >= d);
        } 
      } 
      return true;
    } 
    return false;
  }
  
  public static String filterLineBreak(String desc) {
    if (desc != null && desc.indexOf("\n") != -1)
      if (desc.indexOf("\r\n") != -1) {
        desc = desc.replace("\r\n", ";");
      } else {
        desc = desc.replace("\n", ";");
      }  
    return desc;
  }
  
  public static void main(String[] args) {}
}
