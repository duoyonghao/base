package com.hudh.lclj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws ParseException {
//		String jsonStr = "[{'isComplate':'未完成','name':'操作1'},{'isComplate':'未完成','name':'操作2'},{'isComplate':'未完成','name':'操作3'}]";
//		JSONArray jsonobject = JSONArray.fromObject(jsonStr);
//		List<LcljOperate> users= (List<LcljOperate>)JSONArray.toCollection(jsonobject, LcljOperate.class);
		
		
		 String left_up = "1,2,3,";
		 left_up = left_up.substring(0, left_up.length()-1);
		 System.out.println(left_up);
	}

}
