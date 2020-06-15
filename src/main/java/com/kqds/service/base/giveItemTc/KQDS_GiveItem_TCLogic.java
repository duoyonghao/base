package com.kqds.service.base.giveItemTc;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsGiveitemTc;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_GiveItem_TCLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  public int getCountByItemnos(String costitemSeqId) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.KQDS_GIVEITEM_TC) + ".getCountByItemnos", costitemSeqId)).intValue();
    return count;
  }
  
  public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_GIVEITEM_TC) + ".selectWithPage", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
  
  public List getSelectTc(String organization) throws Exception {
    List<KqdsGiveitemTc> list = new ArrayList<>();
    List<JSONObject> listJson = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.KQDS_GIVEITEM_TC) + ".getSelectTc", organization);
    for (JSONObject typeRs : listJson) {
      KqdsGiveitemTc dict = new KqdsGiveitemTc();
      dict.setSeqId(typeRs.getString("seq_id"));
      dict.setName(typeRs.getString("name"));
      dict.setNum(typeRs.getString("num"));
      list.add(dict);
    } 
    return list;
  }
}
