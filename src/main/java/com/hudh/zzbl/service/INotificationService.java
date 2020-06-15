package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.Notification;
import java.util.Map;

public abstract interface INotificationService
{
  public abstract String saveNotification(Notification paramNotification)
    throws Exception;
  
  public abstract Notification findNotificationByLcljId(Map<String, String> paramMap)
    throws Exception;
}
