package Aladdin;

public class HaspTime
{
  private long[] time = new long[1];
  private int[] day = new int[1];
  private int[] month = new int[1];
  private int[] year = new int[1];
  private int[] hour = new int[1];
  private int[] minute = new int[1];
  private int[] second = new int[1];
  private int status;
  
  static {}
  
  private static native int DatetimeToHasptime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long[] paramArrayOfLong);
  
  private static native int HasptimeToDatetime(long paramLong, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int[] paramArrayOfInt6);
  
  public HaspTime(int year, int month, int day, int hour, int minute, int second)
  {
    this.status = DatetimeToHasptime(day, month, year, hour, minute, second, this.time);
  }
  
  public HaspTime(long hasptime)
  {
    this.time[0] = hasptime;
    this.status = HasptimeToDatetime(hasptime, this.day, this.month, this.year, this.hour, this.minute, this.second);
  }
  
  public int getLastError()
  {
    return this.status;
  }
  
  public long getHaspTime()
  {
    return this.time[0];
  }
  
  public int getMonth()
  {
    return this.month[0];
  }
  
  public int getYear()
  {
    return this.year[0];
  }
  
  public int getDay()
  {
    return this.day[0];
  }
  
  public int getHour()
  {
    return this.hour[0];
  }
  
  public int getMinute()
  {
    return this.minute[0];
  }
  
  public int getSecond()
  {
    return this.second[0];
  }
}
