/**  
  *
  * @Title:  ILcljOrderTrackDeleteService.java   
  * @Package com.hudh.lclj.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月3日 上午11:05:38   
  * @version V1.0  
  */ 
package com.hudh.lclj.service;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;

/**  
  * 
  * @ClassName:  ILcljOrderTrackDeleteService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月3日 上午11:05:38   
  *      
  */
public interface ILcljOrderTrackDeleteService {
	
	void save(LcljOrderTrackDeleteRecord dp, HttpServletRequest request) throws Exception;

}
