package com.hudh.zzbl.service.impl;

import com.hudh.zzbl.dao.ZzblPlantTeethOperationDao;
import com.hudh.zzbl.entity.ZzblPlantTeethOperation;
import com.hudh.zzbl.service.IZzblPlantTeethOperationService;
import com.kqds.util.sys.YZUtility;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZzblPlantTeethOperationServiceImpl implements IZzblPlantTeethOperationService {
  @Autowired
  private ZzblPlantTeethOperationDao opertaionDao;
  
  public void insertZzblPlantTeethOperation(ZzblPlantTeethOperation dp, HttpServletRequest request) throws Exception {
    dp.setSEQ_ID(YZUtility.getUUID());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    String id = request.getParameter("id");
    String order_number = request.getParameter("order_number");
    String remove = request.getParameter("remove");
    String thicknessGum = request.getParameter("thicknessGum");
    String alveolarRidgeThickness = request.getParameter("alveolarRidgeThickness");
    String locator = request.getParameter("locator");
    String kindBone = request.getParameter("kindBone");
    String plantSystem = request.getParameter("plantSystem");
    String modelNumber = request.getParameter("modelNumber");
    String twistingForce = request.getParameter("twistingForce");
    String boneMeal = request.getParameter("boneMeal");
    String coveringPeriosteum = request.getParameter("coveringPeriosteum");
    String doctorSignature = request.getParameter("doctorSignature");
    String signatureTime = request.getParameter("signatureTime");
    String blm_milliliter = request.getParameter("blm_milliliter");
    String plant_bonemeal = request.getParameter("plant_bonemeal");
    String operation_date = request.getParameter("operation_date");
    String username = request.getParameter("username");
    String sex = request.getParameter("sex");
    String age = request.getParameter("age");
    String put_down = request.getParameter("put_down");
    String operation_alltext = request.getParameter("operation_alltext");
    dp.setPut_down(put_down);
    dp.setOperation_alltext(operation_alltext);
    dp.setAge(age);
    dp.setUsername(username);
    dp.setSex(sex);
    dp.setBlm_milliliter(blm_milliliter);
    dp.setPlant_bonemeal(plant_bonemeal);
    dp.setOperation_date(operation_date);
    dp.setRemove(remove);
    dp.setId(id);
    dp.setOrder_number(order_number);
    dp.setThicknessGum(thicknessGum);
    dp.setAlveolarRidgeThickness(alveolarRidgeThickness);
    dp.setLocator(locator);
    dp.setKindBone(kindBone);
    dp.setPlantSystem(plantSystem);
    dp.setModelNumber(modelNumber);
    dp.setTwistingForce(twistingForce);
    dp.setCoveringPeriosteum(coveringPeriosteum);
    dp.setBoneMeal(boneMeal);
    dp.setDoctorSignature(doctorSignature);
    dp.setSignatureTime(signatureTime);
    this.opertaionDao.insertZzblPlantTeethOperation(dp);
  }
  
  public JSONObject findZzblPlantTeethOperationById(String id) throws Exception {
    return this.opertaionDao.findZzblPlantTeethOperationById(id);
  }
}
