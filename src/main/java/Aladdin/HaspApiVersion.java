package Aladdin;

import java.io.UnsupportedEncodingException;

public class HaspApiVersion
{
  private int[] major_version = new int[1];
  private int[] minor_version = new int[1];
  private int[] build_server = new int[1];
  private int[] build_number = new int[1];
  private int status;
  
  static {}
  
  private static native int GetVersion(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, byte[] paramArrayOfByte);
  
  public HaspApiVersion(String vendor_code)
  {
    try
    {
      int vc_bytes_count = vendor_code.length();
      byte[] tmp_vendor_code = new byte[vc_bytes_count + 1];
      
      System.arraycopy(vendor_code.getBytes("UTF-8"), 0, tmp_vendor_code, 0, vc_bytes_count);
      tmp_vendor_code[vc_bytes_count] = 0;
      
      this.status = GetVersion(this.major_version, this.minor_version, this.build_server, this.build_number, tmp_vendor_code);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
  }
  
  public int getLastError()
  {
    return this.status;
  }
  
  public int majorVersion()
  {
    return this.major_version[0];
  }
  
  public int minorVersion()
  {
    return this.minor_version[0];
  }
  
  public int buildNumber()
  {
    return this.build_number[0];
  }
}
