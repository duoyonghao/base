package com.hudh.lclj.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.hudh.lclj.dao.SysDictDao;
import com.kqds.entity.sys.YZDict;
/**
 * 获取系统所有的字典项Map集合
 * @author ASUS
 *
 */
public class SysDictUtil {
	private SysDictDao sysDictDao;
	private SysDictUtil(){};
	private volatile static SysDictUtil instance;
	
	public static SysDictUtil getInstance(){
		if(instance == null) {
			synchronized (SysDictUtil.class) {
				if(instance == null) {
					instance = new SysDictUtil();
				}
			}
		}
		return instance;
	}
	
	@SuppressWarnings("unused")
	public Map<String,YZDict> getSysDictList() throws Exception{
		if(sysDictDao == null) {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			sysDictDao = wac.getBean(SysDictDao.class);
		}
		List<YZDict> list = sysDictDao.findSysDictList();
		
		//转成map集合返回
		Map<String,YZDict> dictMap = new HashMap<String,YZDict>();
		if(null != list && list.size() > 0) { 
			for(YZDict yzDict : list) {
				dictMap.put(yzDict.getSeqId(), yzDict);
			}
		}
		return dictMap;
	}
}
