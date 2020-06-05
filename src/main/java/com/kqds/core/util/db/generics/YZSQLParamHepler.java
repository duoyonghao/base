package com.kqds.core.util.db.generics;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;

public class YZSQLParamHepler {

	public static String clobToString(Clob cl) throws Exception {
		String res = "";
		Reader is = null;
		if (cl == null) {
			return "";
		}
		try {
			is = cl.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(s);
				s = br.readLine();
				if (s != null) {
					sb.append("\r\n");
				}
			}
			res = sb.toString();
			// System.out.println(res);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			is.close();
		}
	}

}
