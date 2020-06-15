package com.hudh.zzbl.service.impl;

import com.hudh.zzbl.dao.NotificationDao;
import com.hudh.zzbl.entity.Notification;
import com.hudh.zzbl.service.INotificationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements INotificationService {
  @Autowired
  private NotificationDao notificationDao;
  
  public String saveNotification(Notification notification) throws Exception {
    this.notificationDao.saveNotification(notification);
    return null;
  }
  
  public Notification findNotificationByLcljId(Map<String, String> map) throws Exception {
    return this.notificationDao.findNotificationByLcljId(map);
  }
}
