package com.hudh.zzbl.dao;

import com.hudh.zzbl.entity.Notification;
import com.kqds.dao.DaoSupport;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationDao
{
  @Autowired
  private DaoSupport dao;
  
  public void saveNotification(Notification notification)
    throws Exception
  {
    this.dao.save("HUDH_Notification.saveNotification", notification);
  }
  
  public Notification findNotificationByLcljId(Map<String, String> map)
    throws Exception
  {
    Notification notification = (Notification)this.dao.findForObject("HUDH_Notification.findNotificationByLcljId", map);
    return notification;
  }
}
