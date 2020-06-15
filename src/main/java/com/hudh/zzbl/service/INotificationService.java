package com.hudh.zzbl.service;

import com.hudh.zzbl.entity.Notification;
import java.util.Map;

public interface INotificationService {
  String saveNotification(Notification paramNotification) throws Exception;
  
  Notification findNotificationByLcljId(Map<String, String> paramMap) throws Exception;
}
