package com.kqds.service.base.jh;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsJh;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.util.sys.TableNameUtil;

import net.sf.json.JSONObject;

@Service
public class Kqds_Jh_Logic {
	@Autowired
	private DaoSupport dao;
	
	public String insert(KqdsJh jh) throws Exception{
		//添加完数据之后刷新显示排队页面
		dao.save(TableNameUtil.KQDS_JH+".insert", jh);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject selectFy(Map<String,String> map,BootStrapPage bp) throws Exception{
		//添加完数据之后刷新显示排队页面
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_JH+".select", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();		
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> select(Map<String,String> map) throws Exception{
		//添加完数据之后刷新显示排队页面
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_JH+".select", map);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectByRegId(Map<String,String> map) throws Exception{
		//添加完数据之后刷新显示排队页面
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_JH+".selectByRegid", map);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject selectdelFy(Map<String,String> map,BootStrapPage bp) throws Exception{
		//添加完数据之后刷新显示排队页面
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<KqdsJh> list=(List<KqdsJh>) dao.findForList(TableNameUtil.KQDS_JH+".selectdel", map);
		PageInfo<KqdsJh> pageInfo = new PageInfo<KqdsJh>(list);
		JSONObject jobj = new JSONObject();		
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectdel(Map<String,String> map) throws Exception{
		//添加完数据之后刷新显示排队页面
		List<JSONObject> list=(List<JSONObject>) dao.findForList(TableNameUtil.KQDS_JH+".selectdel", map);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<KqdsJh> selectByNumber(Map<String,String> map) throws Exception{
		//添加完数据之后刷新显示排队页面
		List<KqdsJh> list=(List<KqdsJh>) dao.findForList(TableNameUtil.KQDS_JH+".selectByNumber", map);
		return list;
	}

	@SuppressWarnings("unchecked")
	public JSONObject selectByTime(Map<String,String> map,BootStrapPage bp) throws Exception{
		//添加完数据之后刷新显示排队页面
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<KqdsJh> list=(List<KqdsJh>) dao.findForList(TableNameUtil.KQDS_JH+".selectByTime", map);
		PageInfo<KqdsJh> pageInfo = new PageInfo<KqdsJh>(list);
		List<KqdsJh> alist=new ArrayList<KqdsJh>();
		List<KqdsJh> blist=new ArrayList<KqdsJh>();
		List<KqdsJh> clist=new ArrayList<KqdsJh>();
		if(list.size()>0){
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (KqdsJh kqdsJh : list) {
				if(kqdsJh.getFloor().equals("A")){
					Long time1 = formatter.parse(kqdsJh.getCreatetime()).getTime();
					Long time2 = formatter.parse(kqdsJh.getStarttime()).getTime();
					Long time3 = formatter.parse(kqdsJh.getEndtime()).getTime();
					kqdsJh.setStarttime((time2-time1)+"");
					kqdsJh.setEndtime((time3-time2)+"");
					alist.add(kqdsJh);
				}else if(kqdsJh.getFloor().equals("B")){
					Long time1 = formatter.parse(kqdsJh.getCreatetime()).getTime();
					Long time2 = formatter.parse(kqdsJh.getStarttime()).getTime();
					Long time3 = formatter.parse(kqdsJh.getEndtime()).getTime();
					kqdsJh.setStarttime((time2-time1)+"");
					kqdsJh.setEndtime((time3-time2)+"");
					blist.add(kqdsJh);
				}
			}
		}
		if(alist.size()>0){
			clist.addAll(alist);
		}
		if(blist.size()>0){
			clist.addAll(blist);
		}
		JSONObject jobj = new JSONObject();		
		jobj.put("rows", clist);
		jobj.put("total", pageInfo.getTotal());
		return jobj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject selectByTimeByDel(Map<String,String> map,BootStrapPage bp) throws Exception{
		//添加完数据之后刷新显示排队页面
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<KqdsJh> list1=(List<KqdsJh>) dao.findForList(TableNameUtil.KQDS_JH+".selectByDelStatistics", map);
		PageInfo<KqdsJh> pageInfo = new PageInfo<KqdsJh>(list1);
		List<KqdsJh> alist=new ArrayList<KqdsJh>();
		List<KqdsJh> blist=new ArrayList<KqdsJh>();
		List<KqdsJh> clist=new ArrayList<KqdsJh>();
		if(list1.size()>0){
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (KqdsJh kqdsJh : list1) {
				if(kqdsJh.getFloor().equals("A")){
					Long time1 = formatter.parse( kqdsJh.getCreatetime()).getTime();
					Long time2 = formatter.parse(kqdsJh.getStarttime()).getTime();
					Long time3 = formatter.parse(kqdsJh.getEndtime()).getTime();
					kqdsJh.setStarttime((time2-time1)+"");
					kqdsJh.setEndtime((time3-time2)+"");
					alist.add(kqdsJh);
				}else if(kqdsJh.getFloor().equals("B")){
					Long time1 = formatter.parse(kqdsJh.getCreatetime()).getTime();
					Long time2 = formatter.parse(kqdsJh.getStarttime()).getTime();
					Long time3 = formatter.parse(kqdsJh.getEndtime()).getTime();
					kqdsJh.setStarttime((time2-time1)+"");
					kqdsJh.setEndtime((time3-time2)+"");
					blist.add(kqdsJh);
				}
			}
		}
		if(alist.size()>0){
			clist.addAll(alist);
		}
		if(blist.size()>0){
			clist.addAll(blist);
		}
		JSONObject jobj = new JSONObject();		
		jobj.put("rows", clist);
		jobj.put("total", pageInfo.getTotal());
		return jobj;
	}
	
	
	@SuppressWarnings("unchecked")
	public String update(KqdsJh jh) throws Exception{
		//添加完数据之后刷新显示排队页面
		dao.update(TableNameUtil.KQDS_JH+".update", jh);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String updateDel(KqdsJh jh) throws Exception{
		//添加完数据之后刷新显示排队页面
		dao.update(TableNameUtil.KQDS_JH+".updateDel", jh);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String updateNumber(List<KqdsJh> jh) throws Exception{
		//添加完数据之后刷新显示排队页面
		dao.update(TableNameUtil.KQDS_JH+".updateNumber", jh);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String updateFloor(List<KqdsJh> jh) throws Exception{
		//添加完数据之后刷新显示排队页面
		dao.update(TableNameUtil.KQDS_JH+".updateFloor", jh);
		return null;
	}
}
