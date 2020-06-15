package com.kqds.util.sys.test;

import com.kqds.core.util.auth.YZAuthenticator;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {
  public static void main(String[] args) throws Exception {
    System.out.println(YZAuthenticator.ciphDecryptStr("zCwr2BjLzoY="));
    System.out.print("请输入要加紧的密码:");
    BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
    String c = strin.readLine();
    System.out.println(YZAuthenticator.ciphEncryptStr(c));
  }
}
