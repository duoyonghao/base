package com.kqds.core.util.auth;

public class YZMD5Context
{
  int[] state = new int[4];
  int[] count = new int[2];
  byte[] buffer = new byte[64];
  
  public void set0()
  {
    for (int i = 0; i < 4; i++) {
      this.state[i] = 0;
    }
    for (int i = 0; i < 2; i++) {
      this.count[i] = 0;
    }
    for (int i = 0; i < 64; i++) {
      this.buffer[i] = 0;
    }
  }
}
