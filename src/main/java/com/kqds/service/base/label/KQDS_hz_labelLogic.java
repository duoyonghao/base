package com.kqds.service.base.label;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsLabel;
import com.kqds.util.sys.TableNameUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_hz_labelLogic
{
  @Autowired
  private DaoSupport dao;
  
  @Transactional(rollbackFor={Exception.class})
  public Integer saveLabel(KqdsLabel label)
    throws Exception
  {
    return (Integer)this.dao.save(TableNameUtil.KQDS_HZ_label + ".saveLabel", label);
  }
  
  public List<KqdsLabel> findLabel(String parentId)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("parentId", parentId);
    return (List)this.dao.findForList(TableNameUtil.KQDS_HZ_label + ".findLabel", map);
  }
  
  public List<KqdsLabel> findLabelCondition(String parentId)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    map.put("parentId", parentId);
    return (List)this.dao.findForList(TableNameUtil.KQDS_HZ_label + ".findLabelCondition", map);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public Integer deLabel(String[] seqId)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    Integer status = null;
    for (int i = 0; i < seqId.length; i++)
    {
      map.put("seqId", seqId[i]);
      status = (Integer)this.dao.delete(TableNameUtil.KQDS_HZ_label + ".deLabel", map);
    }
    return status;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public Integer updateLabel(KqdsLabel kLabel)
    throws Exception
  {
    return (Integer)this.dao.delete(TableNameUtil.KQDS_HZ_label + ".updateLabel", kLabel);
  }
  
  public List<JSONObject> findLabelAll()
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_HZ_label + ".findLabelAll", null);
  }
  
  public List<JSONObject> fingLabelByCondition(Map<String, String> map)
    throws Exception
  {
    return (List)this.dao.findForList(TableNameUtil.KQDS_HZ_label + ".fingLabelByCondition", map);
  }
  
  @Transactional(rollbackFor={Exception.class})
  public int insertKqdsHzLabel(KqdsLabel kqdsHzLabel)
    throws Exception
  {
    int i = ((Integer)this.dao.save("KQDS_HZ_label.insertKqdsHzLabel", kqdsHzLabel)).intValue();
    return i;
  }
  
  public String selectKqdsHzLabelByLeveLabel(Map<String, String> map)
    throws Exception
  {
    String seqid = (String)this.dao.findForObject("KQDS_HZ_label.selectKqdsHzLabelByLeveLabel", map);
    return seqid;
  }
  
  public KqdsLabel selectKqdsHzLabelByUsercode(Map<String, String> map)
    throws Exception
  {
    KqdsLabel kqdsHzLabel = (KqdsLabel)this.dao.findForObject("KQDS_HZ_label.selectKqdsHzLabelByUsercode", map);
    return kqdsHzLabel;
  }
  
  public KqdsLabel findKqdsHzLabelParentIdByParentName(String parentName)
    throws Exception
  {
    KqdsLabel parentId = (KqdsLabel)this.dao.findForObject("KQDS_HZ_label.findKqdsHzLabelParentIdByParentName", parentName);
    return parentId;
  }
  
  public String findKqdsHzLabelSeqIdByLeveLabel(String leveLabel)
    throws Exception
  {
    String seqId = (String)this.dao.findForObject("KQDS_HZ_label.findKqdsHzLabelSeqIdByLeveLabel", leveLabel);
    return seqId;
  }
  
  public JSONObject findParentId(String paString)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("parentId", paString);
    JSONObject json = new JSONObject();
    List<JSONObject> list = (List)this.dao.findForList("KQDS_HZ_label.findParentId", dataMap);
    json.put("list", list);
    return json;
  }
}
