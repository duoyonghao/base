/**  
  *
  * @Title:  SysPersonUtil.java   
  * @Package com.hudh.lclj.util   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年7月15日 下午1:54:19   
  * @version V1.0  
  */ 
package com.hudh.lclj.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hudh.lclj.dao.SysPersonDao;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;

/**  
  * 
  * @ClassName:  SysPersonUtil   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年7月15日 下午1:54:19   
  *      
  */
public class SysPersonUtil {
	private SysPersonDao sysPersonDao;
	private SysPersonUtil(){};
	private volatile static SysPersonUtil instance;
	
	public static SysPersonUtil getInstance(){
		if(instance == null) {
			synchronized (SysPersonUtil.class) {
				if(instance == null) {
					instance = new SysPersonUtil();
				}
			}
		}
		return instance;
	}
	
	@SuppressWarnings("unused")
	public Map<String,YZPerson> getSysPersonList() throws Exception{
		if(sysPersonDao == null) {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			sysPersonDao = wac.getBean(SysPersonDao.class);
		}
		List<YZPerson> list = sysPersonDao.findSysPersonList();
		
		//转成map集合返回
		Map<String,YZPerson> personMap = new HashMap<String,YZPerson>();
		if(null != list && list.size() > 0) { 
			for(YZPerson yzPerson : list) {
				personMap.put(yzPerson.getSeqId(), yzPerson);
			}
		}
		return personMap;
	}
}
