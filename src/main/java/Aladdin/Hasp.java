package Aladdin;

Hasp

  []handle = 1
  featureid
  status
  HASP_UPDATEINFO = "<haspformat format=\"updateinfo\"/>"
  HASP_SESSIONINFO = "<haspformat format=\"sessioninfo\"/>"
  HASP_KEYINFO = "<haspformat format=\"keyinfo\"/>"
  HASP_FINGERPRINT = "<haspformat format=\"host_fingerprint\"/>"
  HASP_RECIPIENT = 
    "<haspformat root=\"location\">    <license_manager>    <attribute name=\"id\" />    <attribute name=\"time\" />    <element name=\"hostname\" />    <element name=\"version\" />    <element name=\"host_fingerprint\" />  </license_manager></haspformat> \n"
  HASP_FEATURETYPE_MASK = -65536L
  HASP_PROGNUM_FEATURETYPE = -65536L
  HASP_PROGNUM_MASK = 255L
  HASP_PROGNUM_OPT_MASK = 65280L
  HASP_PROGNUM_OPT_NO_LOCAL = 32768L
  HASP_PROGNUM_OPT_NO_REMOTE = 16384L
  HASP_PROGNUM_OPT_PROCESS = 8192L
  HASP_PROGNUM_OPT_CLASSIC = 4096L
  HASP_PROGNUM_OPT_TS = 2048L
  HASP_DEFAULT_FID = 0L
  HASP_PROGNUM_DEFAULT_FID = -65536L
  HASP_MIN_BLOCK_SIZE = 16
  HASP_MIN_BLOCK_SIZE_LEGACY = 8L
  HASP_FILEID_MAIN = 65520
  HASP_FILEID_LICENSE = 65522L
  HASP_FILEID_RW = 65524L
  HASP_FILEID_RO = 65525L
  
  getLastError
  
    status;
  }
  
  static
  {
    HaspStatus.Init();
  }
  
  public Hasp(long feature_id)
  {
    this.status = 0;
    this.featureid = feature_id;
    this.handle[0] = 0;
  }
  
  protected void finalize()
  {
    logout();
  }
  
  public boolean login(String vendor_code)
  {
    synchronized (this)
    {
      synchronized (this)
      {
        logout();
        this.status = Login(this.featureid, vendor_code, this.handle);
      }
    }
    return this.status == 0;
  }
  
  public boolean loginScope(String scope, String vendor_code)
  {
    if (vendor_code == null) {
      this.status = 22;
    } else if (scope == null) {
      this.status = 36;
    } else {
      synchronized (this)
      {
        logout();
        this.status = LoginScope(this.featureid, scope, vendor_code, this.handle);
      }
    }
    return this.status == 0;
  }
  
  public boolean logout()
  {
    if (this.handle[0] == 0)
    {
      this.status = 9;
      return true;
    }
    synchronized (this)
    {
      this.status = Logout(this.handle[0]);
      if (this.status == 0) {
        this.handle[0] = 0;
      }
    }
    return this.status == 0;
  }
  
  @Deprecated
  public boolean encrypt(byte[] buffer, int length)
  {
    if (buffer == null) {
      this.status = 501;
    } else if (length > buffer.length) {
      this.status = 501;
    } else {
      this.status = Encrypt(this.handle[0], buffer, length);
    }
    return this.status == 0;
  }
  
  public boolean encrypt(byte[] buffer)
  {
    if (buffer == null) {
      this.status = 501;
    } else {
      this.status = Encrypt(this.handle[0], buffer, buffer.length);
    }
    return this.status == 0;
  }
  
  @Deprecated
  public boolean decrypt(byte[] buffer, int length)
  {
    if (buffer == null) {
      this.status = 501;
    } else if (length > buffer.length) {
      this.status = 501;
    } else {
      this.status = Decrypt(this.handle[0], buffer, length);
    }
    return this.status == 0;
  }
  
  public boolean decrypt(byte[] buffer)
  {
    if (buffer == null) {
      this.status = 501;
    } else {
      this.status = Decrypt(this.handle[0], buffer, buffer.length);
    }
    return this.status == 0;
  }
  
  public String getInfo(String scope, String format, String vendor_code)
  {
    byte[] info = new byte[1];
    int[] status1 = new int[1];
    String s = null;
    
    this.status = 0;
    if (vendor_code == null) {
      this.status = 22;
    } else if (scope == null) {
      this.status = 36;
    } else if (format == null) {
      this.status = 15;
    }
    if (this.status != 0) {
      return null;
    }
    info = GetInfo(scope, format, vendor_code, status1);
    
    this.status = status1[0];
    if (this.status == 0) {
      s = new String(info);
    }
    return s;
  }
  
  public String getSessionInfo(String format)
  {
    byte[] info = new byte[1];
    int[] status1 = new int[1];
    String s = null;
    if (format == null)
    {
      this.status = 15;
      return null;
    }
    info = GetSessioninfo(this.handle[0], format, status1);
    
    this.status = status1[0];
    if (this.status == 0) {
      s = new String(info);
    }
    return s;
  }
  
  @Deprecated
  public boolean read(long fileid, int offset, int length, byte[] buffer)
  {
    if (buffer == null) {
      this.status = 501;
    } else if (offset < 0) {
      this.status = 501;
    } else if (length > buffer.length) {
      this.status = 501;
    } else {
      this.status = Read(this.handle[0], fileid, offset, length, buffer);
    }
    return this.status == 0;
  }
  
  public boolean read(long fileid, int offset, byte[] buffer)
  {
    if (buffer == null) {
      this.status = 501;
    } else if (offset < 0) {
      this.status = 501;
    } else {
      this.status = Read(this.handle[0], fileid, offset, buffer.length, buffer);
    }
    return this.status == 0;
  }
  
  @Deprecated
  public boolean write(long fileid, int offset, int length, byte[] buffer)
  {
    if (buffer == null) {
      this.status = 501;
    } else if (offset < 0) {
      this.status = 501;
    } else if (length > buffer.length) {
      this.status = 501;
    } else {
      this.status = Write(this.handle[0], fileid, offset, length, buffer);
    }
    return this.status == 0;
  }
  
  public boolean write(long fileid, int offset, byte[] buffer)
  {
    if (buffer == null) {
      this.status = 501;
    } else if (offset < 0) {
      this.status = 501;
    } else {
      this.status = Write(this.handle[0], fileid, offset, buffer.length, buffer);
    }
    return this.status == 0;
  }
  
  public int getSize(long fileid)
  {
    int[] size = new int[1];
    this.status = GetSize(this.handle[0], fileid, size);
    return size[0];
  }
  
  public String update(String update_data)
  {
    int[] dll_status = new int[1];
    String s = null;
    if (update_data == null)
    {
      this.status = 501;
      return null;
    }
    s = Update(update_data, dll_status);
    this.status = dll_status[0];
    
    return s;
  }
  
  public HaspTime getRealTimeClock()
  {
    long[] time = new long[1];
    
    this.status = GetRtc(this.handle[0], time);
    HaspTime rtcTime = new HaspTime(time[0]);
    if (this.status == 0) {
      this.status = rtcTime.getLastError();
    }
    return rtcTime;
  }
  
  public HaspApiVersion getVersion(String vendor_code)
  {
    HaspApiVersion version = new HaspApiVersion(vendor_code);
    this.status = version.getLastError();
    
    return version;
  }
  
  @Deprecated
  public boolean legacyencrypt(byte[] buffer, int length)
  {
    if (buffer == null) {
      this.status = 501;
    } else if (length > buffer.length) {
      this.status = 501;
    } else {
      this.status = LegacyEncrypt(this.handle[0], buffer, length);
    }
    return this.status == 0;
  }
  
  public boolean legacyencrypt(byte[] buffer)
  {
    if (buffer == null) {
      this.status = 501;
    } else {
      this.status = LegacyEncrypt(this.handle[0], buffer, buffer.length);
    }
    return this.status == 0;
  }
  
  @Deprecated
  public boolean legacydecrypt(byte[] buffer, int length)
  {
    if (buffer == null) {
      this.status = 501;
    } else if (length > buffer.length) {
      this.status = 501;
    } else {
      this.status = LegacyDecrypt(this.handle[0], buffer, length);
    }
    return this.status == 0;
  }
  
  public boolean legacydecrypt(byte[] buffer)
  {
    if (buffer == null) {
      this.status = 501;
    } else {
      this.status = LegacyDecrypt(this.handle[0], buffer, buffer.length);
    }
    return this.status == 0;
  }
  
  @Deprecated
  public boolean legacysetRtc(short idle_time)
  {
    this.status = LegacySetRtc(this.handle[0], idle_time);
    if (this.status == 0) {
      return true;
    }
    return false;
  }
  
  public boolean legacysetRtc(long idle_time)
  {
    this.status = LegacySetRtc(this.handle[0], idle_time);
    if (this.status == 0) {
      return true;
    }
    return false;
  }
  
  public boolean legacysetIdletime(short idle_time)
  {
    this.status = LegacySetIdletime(this.handle[0], idle_time);
    if (this.status == 0) {
      return true;
    }
    return false;
  }
  
  @Deprecated
  public String detach(String action, String scope, String vendor_code, String destination)
  {
    byte[] info = new byte[1];
    int[] status1 = new int[1];
    String s = null;
    if (action == null) {
      this.status = 501;
    } else if (scope == null) {
      this.status = 36;
    } else if (vendor_code == null) {
      this.status = 22;
    } else if (destination == null) {
      this.status = 501;
    }
    if (this.status != 0) {
      return null;
    }
    info = Detach(action, scope, vendor_code, destination, status1);
    this.status = status1[0];
    if (this.status == 0) {
      s = new String(info);
    }
    return s;
  }
  
  public String transfer(String action, String scope, String vendor_code, String destination)
  {
    byte[] info = new byte[1];
    int[] status1 = new int[1];
    String s = null;
    if (action == null) {
      this.status = 501;
    } else if (scope == null) {
      this.status = 36;
    } else if (vendor_code == null) {
      this.status = 22;
    } else if (destination == null) {
      this.status = 501;
    }
    if (this.status != 0) {
      return null;
    }
    info = Transfer(action, scope, vendor_code, destination, status1);
    
    this.status = status1[0];
    if (this.status == 0) {
      s = new String(info);
    }
    return s;
  }
  
  private static native int Login(long paramLong, String paramString, int[] paramArrayOfInt);
  
  private static native int LoginScope(long paramLong, String paramString1, String paramString2, int[] paramArrayOfInt);
  
  private static native int Logout(int paramInt);
  
  private static native int Encrypt(int paramInt1, byte[] paramArrayOfByte, int paramInt2);
  
  private static native int Decrypt(int paramInt1, byte[] paramArrayOfByte, int paramInt2);
  
  private static native int GetRtc(int paramInt, long[] paramArrayOfLong);
  
  private static native int LegacyEncrypt(int paramInt1, byte[] paramArrayOfByte, int paramInt2);
  
  private static native int LegacyDecrypt(int paramInt1, byte[] paramArrayOfByte, int paramInt2);
  
  private static native int LegacySetRtc(int paramInt, long paramLong);
  
  private static native int LegacySetIdletime(int paramInt, short paramShort);
  
  private static native byte[] GetSessioninfo(int paramInt, String paramString, int[] paramArrayOfInt);
  
  private static native byte[] GetInfo(String paramString1, String paramString2, String paramString3, int[] paramArrayOfInt);
  
  private static native void Free(long paramLong);
  
  private static native String Update(String paramString, int[] paramArrayOfInt);
  
  private static native byte[] Detach(String paramString1, String paramString2, String paramString3, String paramString4, int[] paramArrayOfInt);
  
  private static native byte[] Transfer(String paramString1, String paramString2, String paramString3, String paramString4, int[] paramArrayOfInt);
  
  private static native int Read(int paramInt1, long paramLong, int paramInt2, int paramInt3, byte[] paramArrayOfByte);
  
  private static native int Write(int paramInt1, long paramLong, int paramInt2, int paramInt3, byte[] paramArrayOfByte);
  
  private static native int GetSize(int paramInt, long paramLong, int[] paramArrayOfInt);
}
