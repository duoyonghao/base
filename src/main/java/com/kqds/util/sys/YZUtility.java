package com.kqds.util.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.core.util.YZGuid;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class YZUtility {
	private static Logger log = LoggerFactory.getLogger(YZUtility.class);
	public static String DEFAULT_UPLOAD_MODULE_NAME = "notify";

	// 三个一分组，保留两位小数

	public static final int WITHGROUP = 0;
	// 不分组，保留两位小数
	public static final int WITHOUTGROUP = 1;

	// 加工单号随机数长度
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

	/**
	 * 根据key删除Map中对应的元素
	 * 
	 * @param paramsMap
	 * @param keyid
	 */
	public static void deleteKeyOfMap(Map<String, String> paramsMap, String keyid) {
		Iterator<String> iter = paramsMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			if (keyid.equals(key)) {
				iter.remove();
			}
		}
	}

	/**
	 * 日期格式转换
	 * 
	 * @param time
	 * @return
	 */
	public static String convertLong2Ymdhms(long time) {
		Date date = new Date(time);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(date);
	}

	/**
	 * 获取当前时间三十分钟后的时间  syp
	 * 
	 * 
	 * @return
	 */
	public static String getThirtyMinutesLater() {
		long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
		Date date = new Date(currentTime);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = "";
		nowTime= df.format(date);
		return nowTime;
	}
	
	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * syp
	 * @param strDate
	 * @return
	 */
	 public static Date strToDateLong(String strDate) {
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     ParsePosition pos = new ParsePosition(0);
	     Date strtodate = formatter.parse(strDate, pos);
	     return strtodate;
	 }
	 
	 /**
	     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	     * 
	     * @param nowTime 当前时间
	     * @param startTime 开始时间
	     * @param endTime 结束时间
	     * @return
	     * @author syp
	     */
	 public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
	        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
	            return true;
	        }

	        Calendar date = Calendar.getInstance();
	        date.setTime(nowTime);

	        Calendar begin = Calendar.getInstance();
	        begin.setTime(startTime);

	        Calendar end = Calendar.getInstance();
	        end.setTime(endTime);

	        if (date.after(begin) && date.before(end)) {
	            return true;
	        } else {
	            return false;
	        }
	 }
	
	/**
	 * 获取当前登录用户的主键、
	 * 
	 * @param request
	 * @return
	 */
	public static String getCurrLoginPersonSeqId(HttpServletRequest request) {
		YZPerson person = SessionUtil.getLoginPerson(request);
		if (person != null) {
			return person.getSeqId();
		}
		return null;
	}

	/**
	 * 将map转换成url
	 * 
	 * @param map
	 * @return
	 */
	public static String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}

	/**
	 * 获取图片网络地址
	 * 
	 * @param module
	 * @param attrId
	 * @param attrName
	 * @param request
	 * @return
	 */
	public static String getImageUrlRelative(String module, String attrId, String attrName) {
		attrId = attrId.replace(",", "");
		attrName = attrName.replace("*", "");
		String[] attrIdArray = attrId.split("_");
		String imageUrl = "/upload/" + module + "/";
		imageUrl += attrIdArray[0] + "/" + attrIdArray[1] + "_" + attrName;
		return imageUrl;
	}

	/**
	 * 用于文件上传下载时的文件夹命名
	 * 
	 * @return
	 */
	public static String getHard4File() {
		Calendar cld = Calendar.getInstance();
		int year = cld.get(Calendar.YEAR) % 100;
		int month = cld.get(Calendar.MONTH) + 1;
		String mon = month >= 10 ? month + "" : "0" + month;
		String hard = year + mon;
		return hard;
	}

	public static String getImgUrl() {
		String hard = getHard4File();
		String imgurl = "\\imgUrl" + File.separator + hard + File.separator;
		return imgurl;
	}

	/**
	 * 取得IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取两个日期之间的日期
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 日期集合
	 */
	public static String[] getDateArray(String startStr, String endStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> result = new ArrayList<String>();
		try {
			Date start = sdf.parse(startStr);
			Date end = sdf.parse(endStr);
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DAY_OF_YEAR, 1);
			while (tempStart.before(tempEnd)) {
				result.add(sdf.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] array = new String[result.size()];
		String[] s = result.toArray(array);
		return s;
	}

	/**
	 * 用于上传文件时，生成文件路径
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getRondom() throws NoSuchAlgorithmException {
		String result = "";
		result = YZGuid.getRawGuid();
		return result;
	}

	/**
	 * 目前 系统管理相关表的 主键改为 nvarchar类型 这时，可见人员，就需要将 原先的 1，2，3 转换成 '1','2','3'格式
	 * 
	 * @return
	 */
	public static String ConvertStringIds4Query(String stringIds) {

		if (YZUtility.isNullorEmpty(stringIds)) {
			/*return "''";*/
			return null;
		}

		if (stringIds.contains("'")) {
			stringIds = stringIds.replace("'", "");// ### 避免出现多个 '' 导致查询出错
		}

		if (stringIds.endsWith(",")) {
			stringIds = stringIds.substring(0, stringIds.length() - 1);
		}

		// ### seq_id 由int类型 改为了 varchar类型，
		// ### 规定传入的ids样式，单个id为 2e9978ea-0f3b-475f-83f6-49d9e21d0ef1，
		// ### 多个id为
		// 2e9978ea-0f3b-475f-83f6-49d9e21d0ef1,2e9978ea-0f3b-475f-83f6-49d9e21d0ef1
		StringBuffer bf = new StringBuffer("");

		String[] idArrayTmp = stringIds.split(",");
		for (String idTmp : idArrayTmp) {
			if (YZUtility.isNullorEmpty(idTmp)) {
				continue;
			}
			bf.append("'").append(idTmp).append("',");
		}
		if (bf.toString().endsWith(",")) {
			bf.deleteCharAt(bf.length() - 1);
		}

		return bf.toString();
	}

	public static String ConvertListIds4Query(List<String> idList) {
		String idstrs = ConvertList2String(idList);
		return ConvertStringIds4Query(idstrs);
	}

	public static String ConvertList2String(List<String> idList) {
		StringBuffer idbf = new StringBuffer();
		if (idList == null || idList.size() == 0) {
			return "";
		}

		for (String id : idList) {
			if (YZUtility.isNullorEmpty(id)) {
				continue;
			}
			idbf.append(id).append(",");
		}
		return idbf.toString();
	}

	public static List<String> ConvertString2List(String idsStr) {
		if (YZUtility.isNotNullOrEmpty(idsStr)) {
			idsStr = idsStr.replace("'", ""); // 替换掉'号，为了Mybatis的 In查询
		}

		List<String> list = new ArrayList<String>();
		String[] idArrayTmp = idsStr.split(",");
		for (String idTmp : idArrayTmp) {
			if (YZUtility.isNullorEmpty(idTmp)) {
				continue;
			}
			list.add(idTmp);
		}
		return list;
	}

	/**
	 * 获取UUID，用作表的主键
	 * 
	 * @return
	 */
	public static String getUUID() {

		return UUID.randomUUID().toString();
	}
	
	/**
	 * 自动系统单号,用作订单号
	 * 
	 * @return
	 */
	public static String getSystemID() {
		String str = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		return "JGD"+String.valueOf(str);
	}
	
	/***
	 * 将jsonlist 转换成 实体类List
	 * 
	 * @param jsonlist
	 * @param cls
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List convertJsonList2ClassList(List<JSONObject> jsonlist, Class cls) throws InstantiationException, IllegalAccessException {
		List newList = new ArrayList();
		for (JSONObject jsonObject : jsonlist) {
			Object newObj = JSONObject.toBean(jsonObject, cls);
			newList.add(newObj);
		}
		return newList;
	}

	/**
	 * 获取某个时间段内的日期
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getAllDate(String start, String end) throws ParseException {
		List<String> datelist = new ArrayList<String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dBegin = sdf.parse(start);
		Date dEnd = sdf.parse(end);
		List<Date> lDate = findDates(dBegin, dEnd);
		for (Date date : lDate) {
			datelist.add(sdf.format(date));
		}
		return datelist;
	}

	private static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	/**
	 * 获取两个日期之间的日期
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 日期集合
	 */
	public static String[] getBetweenDatesArray(String startStr, String endStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> result = new ArrayList<String>();
		try {
			Date start = sdf.parse(startStr);
			Date end = sdf.parse(endStr);
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);
			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DAY_OF_YEAR, 1);
			while (tempStart.before(tempEnd)) {
				result.add(sdf.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] array = new String[result.size()];
		String[] s = result.toArray(array);
		return s;
	}

	/**
	 * 判断字符串是否是合法的日期 yyyy-mm-dd 不可以是 yyyy/mm/dd ，也不可以是2017-5-25，应该是 217-05-25
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDateYYYYmmdd(String date) {
		if (date.length() != 10) {
			return false;
		}

		/**
		 * 判断日期格式和范围
		 */
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\s]?((((0?[13578])|(1[02]))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(date);

		boolean dateType = mat.matches();

		return dateType;
	}

	public static void RETURN_OBJ(Object object, HttpServletResponse response, Logger logger) throws IOException {
		// 设置编码格式，否则直接通过url打开的浏览器，返回中文乱码
		response.setContentType("text/json;charset=UTF-8");
		JSONObject job = JSONObject.fromObject(object);
		PrintWriter pw = response.getWriter();
		pw.println(job.toString());
		pw.flush();
	}

	/**
	 * 以json格式返回List
	 * 
	 * @param list
	 * @param response
	 * @param logger
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void RETURN_LIST(List list, HttpServletResponse response, Logger logger) throws IOException {
		// 设置编码格式，否则直接通过url打开的浏览器，返回中文乱码
		response.setContentType("text/json;charset=UTF-8");
		JSONArray jsonArray = JSONArray.fromObject(list);
		PrintWriter pw = response.getWriter();
		pw.println(jsonArray.toString());
		pw.flush();
	}

	/**
	 * Action类处理成功的返回代码
	 * 
	 * @param retrunObj
	 * @param succesMsg
	 * @param response
	 * @param logger
	 * @throws IOException
	 */
	public static void DEAL_SUCCESS(JSONObject retrunObj, String succesMsg, HttpServletResponse response, Logger logger) throws IOException {
		// 设置编码格式，否则直接通过url打开的浏览器，返回中文乱码
		response.setContentType("text/json;charset=UTF-8");
		if (retrunObj == null) {
			retrunObj = new JSONObject();
		}

		if (YZUtility.isNullorEmpty(succesMsg)) {
			succesMsg = "操作成功";
		}

		retrunObj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
		retrunObj.put(YZActionKeys.JSON_RET_MSRGS, succesMsg);

		PrintWriter pw = response.getWriter();
		pw.println(retrunObj.toString());
		pw.flush();

	}

	/**
	 * Action类处理成功的返回代码(表单验证)
	 * 
	 * @param retrunObj
	 * @param succesMsg
	 * @param response
	 * @param logger
	 * @throws IOException
	 */
	public static void DEAL_SUCCESS_VALID(boolean result, HttpServletResponse response) throws IOException {
		String resultString = "{\"valid\":" + result + "}";
		PrintWriter pw = response.getWriter();
		pw.println(resultString);
		pw.flush();
	}

	/**
	 * 通用异常处理模块
	 * 
	 * @param ex
	 * @param response
	 * @throws Exception
	 */
	public static void DEAL_ERROR(String errorMsg, boolean isThrow, Exception ex, HttpServletResponse response, Logger logger) throws Exception {
		// 设置编码格式，否则直接通过url打开的浏览器，返回中文乱码
		response.setContentType("text/json;charset=UTF-8");
		if (YZUtility.isNullorEmpty(errorMsg)) {
			errorMsg = "操作失败";
		}

		if (ex != null) {
			ex.printStackTrace();

			if (logger != null) {
				logger.error(errorMsg + "##" + ex.getMessage());
			} else {
				log.error(errorMsg + "##" + ex.getMessage());
			}
		}

		if (response != null) {
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_ERROR);
			jobj.put(YZActionKeys.JSON_RET_MSRGS, errorMsg);
			PrintWriter pw = response.getWriter();
			pw.println(jobj.toString());
			pw.flush();
		}

		/** spring mvc 中不可以继续抛异常，否则错误信息返回不到前台 **/
		// if (isThrow) { // 是否是事务，是的话抛异常
		// if (ex != null) {
		// throw ex;
		// }
		// }
	}

	/**
	 * 判断选择的人是否在可见人员范围内 比如 网电-客户管理，选择建档人员进行查询，如果选择的建档人不在当前操作人的可见范围内，则不能查询出结果
	 * 前台点击查询时也要做出判断####
	 * 
	 * @param userid
	 * @param visualstaff
	 * @return
	 */
	public static boolean isStrInArray(String userid, String arrayStr) {
		boolean flag = false;

		if (YZUtility.isNullorEmpty(arrayStr)) {
			return flag;
		}

		String[] array = arrayStr.split(",");

		for (String id : array) {

			if (userid.equals(id)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 判断选择的人是否在可见人员范围内 比如 网电-客户管理，选择建档人员进行查询，如果选择的建档人不在当前操作人的可见范围内，则不能查询出结果
	 * 前台点击查询时也要做出判断####
	 * 
	 * @param userid
	 * @param visualstaff
	 * @return
	 */
	public static boolean isStrInArrayEach(String arrayStr1, String arrayStr2) {
		boolean flag = false;

		if (YZUtility.isNullorEmpty(arrayStr1)) {
			return flag;
		}

		if (YZUtility.isNullorEmpty(arrayStr2)) {
			return flag;
		}

		String[] array1 = arrayStr1.split(",");

		String[] array2 = arrayStr2.split(",");

		for (String id : array1) {
			for (String id2 : array2) {
				if (id2.equals(id)) {
					flag = true;
					break;
				}

			}
		}
		return flag;
	}

	/**
	 * 判断 文件是否存在
	 * 
	 * @param savePath
	 * @param fileExtName
	 * @return
	 * @throws IOException
	 */
	public static boolean getExist(String savePath, String fileExtName) throws IOException {
		String filePath = savePath + File.separator + fileExtName;
		if (new File(filePath).exists()) {
			return true;
		}
		return false;
	}

	/** 清理YZPortAct.java類 START ***********************************/
	public void zip(ZipOutputStream out, File file) throws Exception {
		try {
			byte[] buf = new byte[1024];
			if (file.isDirectory()) {
				zip(out, file.listFiles());
			} else {
				FileInputStream in = new FileInputStream(file);
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(file.getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.flush();
		} catch (FileNotFoundException e) {

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
		for (File file : files) {
			zip(out, file);
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

	/*
	 * 删除workflow模块后，由于程序报错引入的方法 start
	 * ##############################################
	 */

	/**
	 * 
	 * @param content
	 *            内容
	 * @param type
	 *            0-提示信息,1-错误信息,2-警告信息,3-禁止信息,4-停止信息,5-空白信息
	 * @return
	 */
	public static String Message(String content, int type) {
		StringBuffer body = new StringBuffer();
		body.append("<div align=center><table width=\"300\" class=\"MessageBox\"><tbody><tr>");
		if (type == 0) {
			body.append("<td class=\"msg info\" style='font-size:11pt'>");
		}
		if (type == 1) {
			body.append("<td class=\"msg error\" style='font-size:11pt'>");
		}
		if (type == 2) {
			body.append("<td class=\"msg warning\" style='font-size:11pt'>");
		}
		if (type == 3) {
			body.append("<td class=\"msg forbidden\">");
		}
		if (type == 4) {
			body.append("<td class=\"msg stop\">");
		}
		if (type == 5) {
			body.append("<td class=\"msg blank\">");
		}
		body.append(content);
		body.append("</td></tr></tbody></table></div>");
		return body.toString();
	}

	/**
	 * 判段id是不是在str里面
	 * 
	 * @param str
	 * @param id
	 * @return
	 */
	public static boolean findId(String str, String id) {
		if (str == null || id == null || "".equals(str) || "".equals(id)) {
			return false;
		}
		String[] aStr = str.split(",");
		for (String tmp : aStr) {
			if (tmp.equals(id)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 删除workflow模块后，由于程序报错引入的方法 end ##############################################
	 */

	public static String parseString(String s) {
		if (s == null) {
			return "";
		} else {
			return s.trim();
		}
	}

	public static String parseString(String s, String defaultValue) {
		if (s == null || s.trim().equals("")) {
			return defaultValue;
		} else {
			return s.trim();
		}
	}

	/**
	 * 判断是否为空串
	 * 
	 * @param str
	 * @return true: null/"" false: 其它
	 */
	public static boolean isNullorEmpty(String str) {
		if (str == null) {
			return true;
		}
		str = str.trim();
		if (str.length() < 1) {
			return true;
		}
		return false;
	}

	public static boolean isNotNullOrEmpty(String str) {
		return !isNullorEmpty(str);
	}

	/**
	 * 处理空值的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String null2Empty(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	/**
	 * 处理空值的字符串
	 * 
	 * @param str
	 *            源字符串
	 * @param defaultStr
	 *            默认字符串
	 * @return
	 */
	public static String empty2Default(String str, String defaultStr) {
		if (isNullorEmpty(str)) {
			return defaultStr;
		}
		return str;
	}

	/**
	 * 取得格式化后的字符串
	 * 
	 * @param num
	 * @return
	 */
	public static String getFormatedStr(double num, int pattern) {
		if (pattern == WITHOUTGROUP) {
			return numFormat.format(num);
		}
		return numFormatG.format(num);
	}

	/**
	 * 取得当前的时间，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurDateTimeStr() {
		return dateFormat.format(new Date());
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 取得当前的时间，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurDateTimeStr(String pattern) {
		if (pattern == null) {
			return getCurDateTimeStr();
		}
		FastDateFormat format = FastDateFormat.getInstance(pattern);
		return format.format(new Date());
	}

	/**
	 * 取得当前的时间，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurDateTimeStr(FastDateFormat format) {
		if (format == null) {
			return getCurDateTimeStr();
		}
		return format.format(new Date());
	}

	/**
	 * 取得的时间串，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateTimeStr(Date date) {
		if (date == null) {
			return getCurDateTimeStr();
		}
		return dateFormat.format(date);
	}

	/**
	 * 取得的时间串，格式为 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDateStr(Date date) {
		if (date == null) {
			return getCurDateTimeStr();
		}
		return dateFormatDay.format(date);
	}

	/**
	 * 取得的时间串，格式为 yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDateStrYmd(Date date) {
		if (date == null) {
			return getCurDateTimeStr();
		}
		return dateFormatDayYmd.format(date);
	}

	/**
	 * 取得的中文习惯的时间串，格式为 yyyy年MM月dd日
	 * 
	 * @return
	 */
	public static String getDateTimeStrCn(Date date) {
		if (date == null) {
			return getCurDateTimeStr(dateFormatCn);
		}
		return dateFormatCn.format(date);
	}

	/**
	 * Date清零
	 * 
	 * @param date
	 * @param clearNum
	 *            1=毫秒, 2=秒, 3=分钟, 4=小时, 5=天, 6=月份
	 * @return
	 */
	private static Calendar clearDate(Date date, int clearNum) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		// 毫秒
		if (clearNum > 0) {
			cal.set(Calendar.MILLISECOND, 0);
		}
		// 秒

		if (clearNum > 1) {
			cal.set(Calendar.SECOND, 0);
		}
		// 分钟
		if (clearNum > 2) {
			cal.set(Calendar.MINUTE, 0);
		}
		// 小时
		if (clearNum > 3) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
		}
		// 天

		if (clearNum > 4) {
			cal.set(Calendar.DATE, 0);
		}
		// 月份
		if (clearNum > 5) {
			cal.set(Calendar.MONTH, 0);
		}
		return cal;
	}

	/**
	 * 取得指定日期当周的起始时间串
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getWeekLimitStr() throws Exception {
		return getWeekLimitStr(new Date());
	}

	/**
	 * 取得指定日期当月的起始时间串
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getMonthLimitStr() throws Exception {
		return getMonthLimitStr(new Date());
	}

	/**
	 * 取得指定日期的起始时间串
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getDateLimitStr(Date date) throws Exception {
		Date[] rtDateArray = getDateLimit(date);
		return new String[] { getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1]) };
	}

	/**
	 * 取得指定日期当周的起始时间串
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getWeekLimitStr(Date date) throws Exception {
		Date[] rtDateArray = getWeekLimit(date);
		return new String[] { getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1]) };
	}

	/**
	 * 取得指定日期当月的起始时间串
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getMonthLimitStr(Date date) throws Exception {
		Date[] rtDateArray = getMonthLimit(date);
		return new String[] { getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1]) };
	}

	/**
	 * 取得指定日期当年的起始时间串
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getYearLimitStr(Date date) throws Exception {
		Date[] rtDateArray = getYearLimit(date);
		return new String[] { getDateTimeStr(rtDateArray[0]), getDateTimeStr(rtDateArray[1]) };
	}

	/**
	 * 取得指定日期的起始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date[] getDateLimit(Date date) throws Exception {
		Calendar cal = clearDate(date, 4);
		Date date1 = cal.getTime();

		cal.add(Calendar.DATE, 1);
		cal.add(Calendar.SECOND, -1);
		Date date2 = cal.getTime();

		return new Date[] { date1, date2 };
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = clearDate(date, 4);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = clearDate(date, 4);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 000);
		return c.getTime();
	}

	/**
	 * 取得指定日期的当周的起始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date[] getWeekLimit(Date date) throws Exception {
		Date date1 = getFirstDayOfWeek(date);
		Date date2 = getLastDayOfWeek(date);
		return new Date[] { date1, date2 };
	}

	/**
	 * 取得指定日期的当月起始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date[] getMonthLimit(Date date) throws Exception {
		Calendar cal = clearDate(date, 5);
		cal.set(Calendar.DATE, 1);
		Date date1 = cal.getTime();

		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.SECOND, -1);
		Date date2 = cal.getTime();

		return new Date[] { date1, date2 };
	}

	/**
	 * 取得指定日期的当年起始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date[] getYearLimit(Date date) throws Exception {
		Calendar cal = clearDate(date, 6);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		Date date1 = cal.getTime();

		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.SECOND, -1);
		Date date2 = cal.getTime();

		return new Date[] { date1, date2 };
	}

	/**
	 * 取得后一天的时间字
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDayAfter(String dateStr, int dayCnt) throws Exception {
		return getDayAfter(parseDate(dateStr), dayCnt);
	}

	/**
	 * 取得后一天的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayAfter(Date date, int dayCnt) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayCnt);
		return cal.getTime();
	}

	/**
	 * 取得指定长度的数字
	 * 
	 * @param str
	 *            原始的数串
	 * 
	 * @param length
	 *            长度
	 * @return length <= str.length str length > str.length 字符串前面补零到指定长度的字符串
	 */
	public static String getFixLengthNum(String str, int length) {
		if (str == null) {
			str = "";
		}
		String preFix = "";
		for (int i = 0; i < length - str.length(); i++) {
			preFix += "0";
		}
		return preFix + str;
	}

	/**
	 * 10进制转换为36进制字符串
	 * 
	 * @param num
	 *            10进制数字
	 * @param length
	 *            返回字符串长度
	 * 
	 * @return 36进制字符串
	 * 
	 * @throws Exception
	 */
	public static String get36BaseStr(long num, int length) throws Exception {
		String numStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		long[] tmpArray = get10ArrayFromNum(num, 36, new long[] {});
		String tmpStr = "";
		for (int i = 0; i < tmpArray.length; i++) {
			tmpStr += String.valueOf(numStr.charAt((int) tmpArray[i]));
		}
		String rtStr = getFixLengthNum(tmpStr, length);
		return rtStr;
	}

	/**
	 * 把任意进制的数转换为十进制数的数组
	 * 
	 * @param num
	 *            十进制表示的任意进制的数
	 * @param baseNum
	 *            基数
	 * @param num10
	 * @return
	 * @throws Exception
	 */
	public static long[] get10ArrayFromNum(long num, int baseNum, long[] num10) throws Exception {
		long[] tmpArray = new long[num10.length + 1];
		tmpArray[0] = num % baseNum;
		if (num10.length > 0) {
			System.arraycopy(num10, 0, tmpArray, 1, num10.length);
		}
		if (num / baseNum == 0) {
			return tmpArray;
		} else {
			return get10ArrayFromNum(num / baseNum, baseNum, tmpArray);
		}
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		return Pattern.matches("-?\\d*\\.?\\d+(E\\d+)?", str);
	}

	/**
	 * 判断是否是数字
	 * 
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		return Pattern.matches("(-?[1-9]\\d*)|0", str);
	}

	public static String parseDate2(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(Long.valueOf(str)));
	}

	/**
	 * 把字符串转化为Date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}

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

		return DateUtils.parseDate(dateStr, format.getPattern());
	}

	/**
	 * 当前日期转化成java.sql.Date对象
	 * 
	 * @param dateStr
	 * @return
	 */
	public static java.sql.Timestamp parseTimeStamp() throws ParseException {
		return parseTimeStamp(null);
	}

	/**
	 * 把字符串转化为java.sql.Date对象
	 * 
	 * @param longDate
	 * @return
	 */
	public static java.sql.Timestamp parseTimeStamp(long longDate) throws ParseException {
		Date utilDate = new Date(longDate);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
		return sqlDate;
	}

	/**
	 * 把字符串转化为java.sql.Date对象
	 * 
	 * @param dateStr
	 * @return
	 */
	public static java.sql.Timestamp parseTimeStamp(String dateStr) throws ParseException {
		Date utilDate = null;
		if (dateStr == null) {
			utilDate = new Date();
		} else {
			utilDate = YZUtility.parseDate(dateStr);
		}
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
		return sqlDate;
	}

	/**
	 * 当前日期转化成java.sql.Date对象
	 * 
	 * @param dateStr
	 * @return
	 */
	public static java.sql.Date parseSqlDate() throws ParseException {
		return parseSqlDate(null);
	}

	/**
	 * 把字符串转化为java.sql.Date对象
	 * 
	 * @param dateStr
	 * @return
	 */
	public static java.sql.Date parseSqlDate(String dateStr) throws ParseException {
		Date utilDate = null;
		if (dateStr == null) {
			utilDate = new Date();
		} else {
			utilDate = YZUtility.parseDate(dateStr);
		}
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

	/**
	 * 转换编码
	 * 
	 * @return
	 */
	public static String transferCode(String srcStr, String srcCode, String desCode) throws UnsupportedEncodingException {
		if (srcStr == null) {
			return null;
		}
		return new String(srcStr.getBytes(srcCode), desCode);
	}

	/**
	 * 把编码为ISO-8859-1的转换为GBK
	 * 
	 * @param srcStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String iso88591ToGbk(String srcStr) throws UnsupportedEncodingException {
		return transferCode(srcStr, "ISO-8859-1", "GBK");
	}

	/**
	 * 把编码为ISO-8859-1的转换为UTF-8
	 * 
	 * @param srcStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String iso88591ToUTF8(String srcStr) throws UnsupportedEncodingException {
		return transferCode(srcStr, "ISO-8859-1", "UTF-8");
	}

	/**
	 * 用系统时间和随机数产生系统唯一的文件名
	 * 
	 * @return
	 */
	public static String getRandomName() {
		String currTimeStr = YZUtility.getCurDateTimeStr(YZUtility.DATE_FORMAT_NOSPLIT);
		String randomStr = String.valueOf((int) Math.random() * 10000);
		return currTimeStr + randomStr;
	}

	/**
	 * 把字符串转换为属性哈希表
	 * 
	 * @param propStr
	 * @param rtMap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void str2Map(String propStr, Map rtMap) {
		String[] propArray = propStr.split("\\\r*\\\n{1}");
		for (int i = 0; i < propArray.length; i++) {
			String tmpStr = propArray[i].trim();
			if (tmpStr.length() < 1 || tmpStr.startsWith("#")) {
				continue;
			}
			int tmpIndex = tmpStr.indexOf("=");
			if (tmpIndex < 0) {
				continue;
			}
			String key = tmpStr.substring(0, tmpIndex).trim();
			String value = tmpStr.substring(tmpIndex + 1).trim();
			if (key.length() < 1 || value.length() < 1) {
				continue;
			}
			rtMap.put(key, value);
		}
	}

	/**
	 * Copy 哈希表
	 * 
	 * @param fromMap
	 * @param toMap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copyMap(Map fromMap, Map toMap) {
		Iterator iKeys = fromMap.keySet().iterator();
		while (iKeys.hasNext()) {
			Object key = iKeys.next();
			Object value = fromMap.get(key);
			toMap.put(key, value);
		}
	}

	/**
	 * URL编码
	 */
	public static String encodeURL(String srcStr) throws Exception {
		if (srcStr == null) {
			return "";
		}
		return URLEncoder.encode(srcStr, YZConst.DEFAULT_CODE);
	}

	/**
	 * URL编码
	 */
	public static String decodeURL(String srcStr) throws Exception {
		if (srcStr == null) {
			return "";
		}
		return URLDecoder.decode(srcStr, YZConst.DEFAULT_CODE);
	}

	/**
	 * 处理\ " '
	 * 
	 * @param srcStr
	 * @return
	 * @throws Exception
	 */
	public static String encodeSpecial(String srcStr) {
		if (srcStr == null) {
			return "";
		}
		return srcStr.replace("\\", "\\\\").replace("\"", "\\\"").replace("\'", "\\\'").replace("\r\n", "").replace("\n", "").replace("\r", "");
	}

	/**
	 * 处理' % _
	 * 
	 * @param srcStr
	 * @return
	 * @throws Exception
	 */
	public static String encodeLike(String srcStr) {
		if (srcStr == null) {
			return "";
		}
		return srcStr.replace("\\", "\\\\").replace("'", "''").replace("%", "\\%").replace("_", "\\_");
	}

	/**
	 * 获取Key前面部分相同的子哈希表
	 * 
	 * @param srcMap
	 * @param prefix
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map startsWithMap(Map srcMap, String prefix) {
		Map rtMap = new HashMap<String, Object>();

		if (isNullorEmpty(prefix)) {
			return rtMap;
		}
		Iterator<String> iKeys = srcMap.keySet().iterator();
		while (iKeys.hasNext()) {
			String key = iKeys.next();
			if (key.startsWith(prefix)) {
				rtMap.put(key, srcMap.get(key));
			}
		}
		return rtMap;
	}

	/**
	 * 获取Key后面部分相同的子哈希表
	 * 
	 * @param srcMap
	 * @param prefix
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map endsWithMap(Map srcMap, String postfix) {
		Map rtMap = new HashMap<String, Object>();

		if (isNullorEmpty(postfix)) {
			return rtMap;
		}
		Iterator<String> iKeys = srcMap.keySet().iterator();
		while (iKeys.hasNext()) {
			String key = iKeys.next();
			if (key.endsWith(postfix)) {
				rtMap.put(key, srcMap.get(key));
			}
		}
		return rtMap;
	}

	/**
	 * 移除map中空key或者value空值
	 * 
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	public static void deleteMapNullKeyVal(Map map) {
		removeNullKey(map);
		removeNullValue(map);
	}

	/**
	 * 移除map的空key
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static void removeNullKey(Map map) {
		Set set = map.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			remove(obj, iterator);
		}
	}

	/**
	 * 移除map中的value空值
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static void removeNullValue(Map map) {
		Set set = map.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			Object value = (Object) map.get(obj);
			remove(value, iterator);
		}
	}

	/**
	 * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。 Iterator
	 * 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，
	 * 所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出
	 * java.util.ConcurrentModificationException 异常。 所以 Iterator
	 * 在工作的时候是不允许被迭代的对象被改变的。 但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove()
	 * 方法会在删除当前迭代对象的同时维护索引的一致性。
	 * 
	 * @param obj
	 * @param iterator
	 */
	@SuppressWarnings("rawtypes")
	private static void remove(Object obj, Iterator iterator) {
		if (obj instanceof String) {
			String str = (String) obj;
			if (YZUtility.isNullorEmpty(str)) {
				iterator.remove();
			}
		} else if (obj instanceof Collection) {
			Collection col = (Collection) obj;
			if (col == null || col.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Map) {
			Map temp = (Map) obj;
			if (temp == null || temp.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			if (array == null || array.length <= 0) {
				iterator.remove();
			}
		} else {
			if (obj == null) {
				iterator.remove();
			}
		}
	}

	/**
	 * 保留2位小数
	 * 
	 * @param map
	 * @return
	 */
	public static String FloatToFixed2(Float nums) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(nums);// format 返回的是字符串
		return p;
	}

	public static String FloatToFixed2(Double nums) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(nums);// format 返回的是字符串
		return p;
	}

	/**
	 * obj1 赋值 obj2(字段要完全一致)
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object Obj1ToObj2(Object obj1, Object obj2) throws Exception {
		Field[] fields = obj1.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			boolean flag = ChainUtil.isHasAttributeInClass(fieldName, obj2.getClass());
			if (flag) {
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				// obj1的值
				Class tCls = obj1.getClass();
				Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
				Object obj1value = getMethod.invoke(obj1, new Object[] {});
				// obj2的值
				Class tCls2 = obj2.getClass();
				// obj1赋值到obj2
				Method setMethod2 = tCls2.getMethod(setMethodName, new Class[] { field.getType() });
				if (obj1value != null && !YZUtility.isNullorEmpty(obj1value.toString())) {
					setMethod2.invoke(obj2, obj1value);
				}
			}
		}
		return obj2;
	}

	/**
	 * 获取手机号码
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static String getPhone(String pbone) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(pbone);
		return m.replaceAll("").trim();
	}

	/**
	 * 验证手机号码
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean validatemobile(String mobile) {
		if (mobile.length() == 0) {
			return false;
		}
		if (mobile.length() != 11) {
			return false;
		}

		String myreg = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(myreg);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 比较两个日期大小
	 * 
	 * @param sta
	 * @param end
	 * @return 0 表示 sta < end 1 表示 sta > end 2 表示 sta = end
	 * @throws ParseException
	 */
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

	/**
	 * 获取MD5加密
	 * 
	 * @param pwd
	 *            需要加密的字符串
	 * @return String字符串 加密后的字符串
	 */
	public static String getMd5Str(String pwd) {
		try {
			// 创建加密对象
			MessageDigest digest = MessageDigest.getInstance("md5");

			// 调用加密对象的方法，加密的动作已经完成
			byte[] bs = digest.digest(pwd.getBytes());
			// 接下来，我们要对加密后的结果，进行优化，按照mysql的优化思路走
			// mysql的优化思路：
			// 第一步，将数据全部转换成正数：
			String hexString = "";
			for (byte b : bs) {
				// 第一步，将数据全部转换成正数：
				// 解释：为什么采用b&255
				/*
				 * b:它本来是一个byte类型的数据(1个字节) 255：是一个int类型的数据(4个字节)
				 * byte类型的数据与int类型的数据进行运算，会自动类型提升为int类型 eg: b: 1001 1100(原始数据) 运算时： b: 0000 0000
				 * 0000 0000 0000 0000 1001 1100 255: 0000 0000 0000 0000 0000 0000 1111 1111
				 * 结果：0000 0000 0000 0000 0000 0000 1001 1100 此时的temp是一个int类型的整数
				 */
				int temp = b & 255;
				// 第二步，将所有的数据转换成16进制的形式
				// 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
				// 因此，需要对temp进行判断
				if (temp < 16 && temp >= 0) {
					// 手动补上一个“0”
					hexString = hexString + "0" + Integer.toHexString(temp);
				} else {
					hexString = hexString + Integer.toHexString(temp);
				}
			}
			return hexString;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 验证时间字符串格式输入是否正确
	 * 
	 * @param timeStr
	 * @return true 合法 false不合法
	 */
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		// 验证每个月的最大天数如下
		// Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) " + "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
			matcher = pattern.matcher(timeStr);
			if (matcher.matches()) {
				int y = Integer.valueOf(matcher.group(1));
				int m = Integer.valueOf(matcher.group(2));
				int d = Integer.valueOf(matcher.group(3));
				if (d > 28) {
					Calendar c = Calendar.getInstance();
					c.set(y, m - 1, 1);
					int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					return (lastDay >= d);
				}
			}
			return true;
		}
		return false;
	}

	/**
	  * @Title: filterLineBreak   
	  * @Description: TODO(回车进行转义)   
	  * @param: @param desc
	  * @param: @return      
	  * @return: String
	  * @dateTime:2019年9月19日 上午9:01:16
	 */
public static String filterLineBreak(String desc){

		if(desc!=null&&desc.indexOf("\n")!=-1){
			if(desc.indexOf("\r\n")!=-1){
				desc = desc.replace("\r\n",";");
			}else{
				desc = desc.replace("\n",";");
			}
		}
	return desc;
}
	
	public static void main(String[] args) {
		// System.out.println(compare_date("2017-11-11 00:00:00",
		// "2017-11-11 00:00:00"));
	}
}