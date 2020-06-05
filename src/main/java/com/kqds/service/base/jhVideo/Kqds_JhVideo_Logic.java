package com.kqds.service.base.jhVideo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsJhVideo;
import com.kqds.util.sys.TableNameUtil;


@Service
public class Kqds_JhVideo_Logic {
	@Autowired
	private DaoSupport dao;
	
	public String insert(KqdsJhVideo jhVideo) throws Exception{
		dao.save(TableNameUtil.KQDS_JH_VIDEO+".insert", jhVideo);
		return null;
	}
	public String batchInsert(List<KqdsJhVideo> jhVideo) throws Exception{
		dao.batchSave(TableNameUtil.KQDS_JH_VIDEO+".insertList", jhVideo);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<KqdsJhVideo> selectUrl(String organization) throws Exception{
		List<KqdsJhVideo> list=(List<KqdsJhVideo>) dao.findForList(TableNameUtil.KQDS_JH_VIDEO+".selectUrl",organization);
		return list;
	}

	public String del(KqdsJhVideo url) throws Exception{
		dao.update(TableNameUtil.KQDS_JH_VIDEO+".del", url);
		return null;
	}
}
