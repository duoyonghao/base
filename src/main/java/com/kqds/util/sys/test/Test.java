/**
 * 
 */
package com.kqds.util.sys.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.kqds.core.util.auth.YZAuthenticator;

/**
 * @author ljs
 * 
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// String str=YZGuid.getRawGuid();
		// System.out.println(str);
		// System.out.println(System.getProperty("java.library.path"));
		// System.out.println(YZAuthenticator.ciphEncryptStr("dlhy@1234"));
		System.out.println(YZAuthenticator.ciphDecryptStr("zCwr2BjLzoY="));
		String c;
		System.out.print("请输入要加紧的密码:");
		BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
		c = strin.readLine();
		System.out.println(YZAuthenticator.ciphEncryptStr(c));
	}

}
