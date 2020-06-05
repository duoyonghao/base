package com.hudh.zzbl.service;

import java.util.Map;

import com.hudh.zzbl.entity.Notification;

public interface INotificationService {
	String saveNotification(Notification notification) throws Exception;
	
	Notification findNotificationByLcljId(Map<String,String> map) throws Exception;
	
	int updateNotification(Notification notification) throws Exception;
}
