package com.kqds.service.base.hzLabel;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.service.sys.base.BaseLogic;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KQDS_HzLabelAssociatedLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  @Transactional(rollbackFor={Exception.class})
  public int insertKqdsHzLabelAssociated(KqdsHzLabelAssociated kqdsHzLabelAssociated)
    throws Exception
  {
    int i = ((Integer)this.dao.save("KQDS_HZ_labelAssociated.insertKqdsHzLabelAssociated", kqdsHzLabelAssociated)).intValue();
    return i;
  }
  
  public List<JSONObject> selectPriceListByLabeId(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("KQDS_HZ_labelAssociated.selectPriceListByLabeId", map);
    return list;
  }
  
  public String selectKqdsHzLabelAssociatedByUserId(Map<String, String> map)
    throws Exception
  {
    String seqid = (String)this.dao.findForObject("KQDS_HZ_labelAssociated.selectKqdsHzLabelAssociatedByUserId", map);
    return seqid;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public int updateKqdsHzLabelAssociated(KqdsHzLabelAssociated kqdsHzLabelAssociated)
    throws Exception
  {
    int a = ((Integer)this.dao.update("KQDS_HZ_labelAssociated.updateKqdsHzLabelAssociated", kqdsHzLabelAssociated)).intValue();
    return a;
  }
  
  public String selectKqdsHzLabelBySeqId(String hzLabelAssciatedSeqId)
    throws Exception
  {
    String seqid = (String)this.dao.findForObject("KQDS_HZ_labelAssociated.selectKqdsHzLabelBySeqId", hzLabelAssciatedSeqId);
    return seqid;
  }
  
  @Transactional(rollbackFor={Exception.class})
  public int updateKqdsHzLabelAssociatedByStatus(KqdsHzLabelAssociated kqdsHzLabelAssociated)
    throws Exception
  {
    int a = ((Integer)this.dao.update("KQDS_HZ_labelAssociated.updateKqdsHzLabelAssociatedByStatus", kqdsHzLabelAssociated)).intValue();
    return a;
  }
}
