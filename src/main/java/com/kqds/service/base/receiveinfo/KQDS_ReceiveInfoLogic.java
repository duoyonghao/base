package com.kqds.service.base.receiveinfo;

import com.kqds.dao.DaoSupport;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.TableNameUtil;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KQDS_ReceiveInfoLogic
  extends BaseLogic
{
  @Autowired
  private DaoSupport dao;
  
  public int countReceiveByRegNo(String regseqId)
    throws Exception
  {
    int count = ((Integer)this.dao.findForObject(TableNameUtil.KQDS_RECEIVEINFO + ".countReceiveByRegNo", regseqId)).intValue();
    return count;
  }
  
  public List<JSONObject> noSelectWithPage(String table, Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_RECEIVEINFO + ".noSelectWithPage", map);
    return list;
  }
  
  public List<JSONObject> getNoUsercode(String usercode)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.KQDS_RECEIVEINFO + ".getNoUsercode", usercode);
    return list;
  }
  
  public void deleteByrecno(String outprocessingsheetno)
    throws Exception
  {
    this.dao.delete(TableNameUtil.KQDS_RECEIVEINFO + ".deleteByrecno", outprocessingsheetno);
  }
}
