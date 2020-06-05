package com.hudh.zzbl.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.dao.NotificationDao;
import com.hudh.zzbl.entity.Notification;
import com.hudh.zzbl.service.INotificationService;
@Service
public class NotificationServiceImpl implements INotificationService {
	@Autowired
	private NotificationDao notificationDao;
	@Override
	public String saveNotification(Notification notification) throws Exception {
		notificationDao.saveNotification(notification);
		return null;
	}
	@Override
	public Notification findNotificationByLcljId(Map<String, String> map) throws Exception {
		return notificationDao.findNotificationByLcljId(map);
	}
	@Override
	public int updateNotification(Notification notification) throws Exception {
		int i=notificationDao.updateNotification(notification);
		return i;
	}
	
}
