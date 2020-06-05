package com.hudh.zzbl.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.entity.Notification;
import com.kqds.dao.DaoSupport;
@Service
public class NotificationDao {
	@Autowired
	private DaoSupport dao;
	public void saveNotification(Notification notification)throws Exception{
		 dao.save("HUDH_Notification.saveNotification", notification);
	}
	
	public Notification findNotificationByLcljId(Map<String,String> map) throws Exception{
		Notification notification=(Notification) dao.findForObject("HUDH_Notification.findNotificationByLcljId", map);
		return notification;
	}
	
	public int updateNotification(Notification notification)throws Exception{
		 int i=(int) dao.update("HUDH_Notification.updateNotification", notification);
		return i;
	}
}
