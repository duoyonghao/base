package com.hudh.lclj.util;

import java.text.ParseException;

public class Test {
  public static void main(String[] args) throws ParseException {
    String left_up = "1,2,3,";
    left_up = left_up.substring(0, left_up.length() - 1);
    System.out.println(left_up);
  }
}
