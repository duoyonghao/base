package com.kqds.util.base.code;

import com.kqds.entity.base.KqdsOutprocessingSheet;
import com.kqds.service.base.outProcessingSheet.KQDS_OutProcessingSheetLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.chain.ChainUtil;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class JGCodeUtil
{
  @Autowired
  private KQDS_OutProcessingSheetLogic logic;
  private static JGCodeUtil jgCodeUtil;
  
  public void setUserDocLogic(KQDS_OutProcessingSheetLogic logic)
  {
    this.logic = logic;
  }
  
  @PostConstruct
  public void init()
  {
    jgCodeUtil = this;
    jgCodeUtil.logic = this.logic;
  }
  
  public static synchronized String getSheetNo(int cishu, HttpServletRequest request)
    throws Exception
  {
    String menzhen = ChainUtil.getCurrentOrganization(request);
    
    String uuid = menzhen + "JG";
    
    String generateSource = "0123456789";
    String rtnStr = "";
    for (int i = 0; i < cishu; i++)
    {
      String nowStr = String.valueOf(generateSource.charAt((int)Math.floor(Math.random() * generateSource.length())));
      rtnStr = rtnStr + nowStr;
      generateSource = generateSource.replaceAll(nowStr, "");
    }
    uuid = uuid + rtnStr;
    
    KqdsOutprocessingSheet sheet = (KqdsOutprocessingSheet)jgCodeUtil.logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, uuid);
    if (sheet == null) {
      return uuid;
    }
    return getSheetNo(cishu, request);
  }
}
