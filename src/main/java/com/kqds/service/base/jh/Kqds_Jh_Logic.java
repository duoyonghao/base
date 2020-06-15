package com.kqds.service.base.jh;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsJh;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.util.sys.TableNameUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Kqds_Jh_Logic
{
  @Autowired
  private DaoSupport dao;
  
  public String insert(KqdsJh jh)
    throws Exception
  {
    this.dao.save(TableNameUtil.KQDS_JH + ".insert", jh);
    return null;
  }
  
  public JSONObject selectFy(Map<String, String> map, BootStrapPage bp)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_JH + ".select", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> select(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_JH + ".select", map);
    return list;
  }
  
  public List<JSONObject> selectByRegId(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_JH + ".selectByRegid", map);
    return list;
  }
  
  public JSONObject selectdelFy(Map<String, String> map, BootStrapPage bp)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<KqdsJh> list = (List)this.dao.findForList(TableNameUtil.KQDS_JH + ".selectdel", map);
    PageInfo<KqdsJh> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List<JSONObject> selectdel(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_JH + ".selectdel", map);
    return list;
  }
  
  public List<KqdsJh> selectByNumber(Map<String, String> map)
    throws Exception
  {
    List<KqdsJh> list = (List)this.dao.findForList(TableNameUtil.KQDS_JH + ".selectByNumber", map);
    return list;
  }
  
  public JSONObject selectByTime(Map<String, String> map, BootStrapPage bp)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<KqdsJh> list = (List)this.dao.findForList(TableNameUtil.KQDS_JH + ".selectByTime", map);
    PageInfo<KqdsJh> pageInfo = new PageInfo(list);
    List<KqdsJh> alist = new ArrayList();
    List<KqdsJh> blist = new ArrayList();
    List<KqdsJh> clist = new ArrayList();
    if (list.size() > 0)
    {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      for (KqdsJh kqdsJh : list) {
        if (kqdsJh.getFloor().equals("A"))
        {
          Long time1 = Long.valueOf(formatter.parse(kqdsJh.getCreatetime()).getTime());
          Long time2 = Long.valueOf(formatter.parse(kqdsJh.getStarttime()).getTime());
          Long time3 = Long.valueOf(formatter.parse(kqdsJh.getEndtime()).getTime());
          kqdsJh.setStarttime(time2.longValue() - time1.longValue());
          kqdsJh.setEndtime(time3.longValue() - time2.longValue());
          alist.add(kqdsJh);
        }
        else if (kqdsJh.getFloor().equals("B"))
        {
          Long time1 = Long.valueOf(formatter.parse(kqdsJh.getCreatetime()).getTime());
          Long time2 = Long.valueOf(formatter.parse(kqdsJh.getStarttime()).getTime());
          Long time3 = Long.valueOf(formatter.parse(kqdsJh.getEndtime()).getTime());
          kqdsJh.setStarttime(time2.longValue() - time1.longValue());
          kqdsJh.setEndtime(time3.longValue() - time2.longValue());
          blist.add(kqdsJh);
        }
      }
    }
    if (alist.size() > 0) {
      clist.addAll(alist);
    }
    if (blist.size() > 0) {
      clist.addAll(blist);
    }
    JSONObject jobj = new JSONObject();
    jobj.put("rows", clist);
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    return jobj;
  }
  
  public JSONObject selectByTimeByDel(Map<String, String> map, BootStrapPage bp)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<KqdsJh> list1 = (List)this.dao.findForList(TableNameUtil.KQDS_JH + ".selectByDelStatistics", map);
    PageInfo<KqdsJh> pageInfo = new PageInfo(list1);
    List<KqdsJh> alist = new ArrayList();
    List<KqdsJh> blist = new ArrayList();
    List<KqdsJh> clist = new ArrayList();
    if (list1.size() > 0)
    {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      for (KqdsJh kqdsJh : list1) {
        if (kqdsJh.getFloor().equals("A"))
        {
          Long time1 = Long.valueOf(formatter.parse(kqdsJh.getCreatetime()).getTime());
          Long time2 = Long.valueOf(formatter.parse(kqdsJh.getStarttime()).getTime());
          Long time3 = Long.valueOf(formatter.parse(kqdsJh.getEndtime()).getTime());
          kqdsJh.setStarttime(time2.longValue() - time1.longValue());
          kqdsJh.setEndtime(time3.longValue() - time2.longValue());
          alist.add(kqdsJh);
        }
        else if (kqdsJh.getFloor().equals("B"))
        {
          Long time1 = Long.valueOf(formatter.parse(kqdsJh.getCreatetime()).getTime());
          Long time2 = Long.valueOf(formatter.parse(kqdsJh.getStarttime()).getTime());
          Long time3 = Long.valueOf(formatter.parse(kqdsJh.getEndtime()).getTime());
          kqdsJh.setStarttime(time2.longValue() - time1.longValue());
          kqdsJh.setEndtime(time3.longValue() - time2.longValue());
          blist.add(kqdsJh);
        }
      }
    }
    if (alist.size() > 0) {
      clist.addAll(alist);
    }
    if (blist.size() > 0) {
      clist.addAll(blist);
    }
    JSONObject jobj = new JSONObject();
    jobj.put("rows", clist);
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    return jobj;
  }
  
  public String update(KqdsJh jh)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_JH + ".update", jh);
    return null;
  }
  
  public String updateDel(KqdsJh jh)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_JH + ".updateDel", jh);
    return null;
  }
  
  public String updateNumber(List<KqdsJh> jh)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_JH + ".updateNumber", jh);
    return null;
  }
  
  public String updateFloor(List<KqdsJh> jh)
    throws Exception
  {
    this.dao.update(TableNameUtil.KQDS_JH + ".updateFloor", jh);
    return null;
  }
}
