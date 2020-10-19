package com.hudh.zzbl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.dao.LcljTrackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.dao.RepairSchemeConfirmDao;
import com.hudh.zzbl.entity.RepairSchemeConfirm;
import com.hudh.zzbl.service.IRepairSchemeConfirmService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class RepairSchemeConfirmServiceImpl implements IRepairSchemeConfirmService {
	
	@Autowired
	private RepairSchemeConfirmDao repairSchemeConfirmDao;
	@Autowired
	private LcljTrackDao lcljTrackDao;

	@Override
	public void saveRepairSchemeConfirmInfro(RepairSchemeConfirm dp, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setSeqId(YZUtility.getUUID());
	    String orderNumber = request.getParameter("order_number");
	    String id = request.getParameter("id");
	    String tprgleftup = request.getParameter("tprgLeftUp");
	    String tprgleftdown = request.getParameter("tprgLeftDown");
	    String tprgrightup = request.getParameter("tprgRightUp");
	    String tprgrightdown = request.getParameter("tprgRightDown");
	    String plantthistime = request.getParameter("plantthistime");
	    String dentiumleftup = request.getParameter("dentiumLeftUp");
	    String dentiumleftdown = request.getParameter("dentiumLeftDown");
	    String dentiumrightup = request.getParameter("dentiumRightUp");
	    String dentiumrightdown = request.getParameter("dentiumRightDown");
	    String icxleftup = request.getParameter("icxLeftUp");
	    String icxleftdown = request.getParameter("icxLeftDown");
	    String icxrightup = request.getParameter("icxRightUp");
	    String icxrightdown = request.getParameter("icxRightDown");
		String templantleftup = request.getParameter("templantLeftUp");
		String templantleftdown = request.getParameter("templantLeftDown");
		String templantrightup = request.getParameter("templantRightUp");
		String templantrightdown = request.getParameter("templantRightDown");
		String nobelactiveleftup = request.getParameter("nobelActiveLeftUp");
	    String nobelactiveleftdown = request.getParameter("nobelActiveLeftDown");
	    String nobelactiverightup = request.getParameter("nobelActiveRightUp");
	    String nobelactiverightdown = request.getParameter("nobelActiveRightDown");
	    String camlogleftup = request.getParameter("camlogLeftUp");
	    String camlogleftdown = request.getParameter("camlogLeftDown");
	    String camlogrightup = request.getParameter("camlogRightUp");
	    String camlogrightdown = request.getParameter("camlogRightDown");
	    String hiossenleftup = request.getParameter("hiossenLeftUp");
	    String hiossenleftdown = request.getParameter("hiossenLeftDown");
	    String hiossenrightup = request.getParameter("hiossenRightUp");
	    String hiossenrightdown = request.getParameter("hiossenRightDown");
	    String rbrdleftup = request.getParameter("rbrdLeftUp");
	    String rbrdleftdown = request.getParameter("rbrdLeftDown");
	    String rbrdrigthup = request.getParameter("rbrdRigthUp");
	    String rbrdrigthdown = request.getParameter("rbrdRigthDown");
	    String invidenleftup = request.getParameter("inviDenLeftUp");
	    String invidenleftdown = request.getParameter("inviDenLeftDown");
	    String invidenrightup = request.getParameter("inviDenRightUp");
	    String invidenrightdown = request.getParameter("inviDenRightDown");
	    String locatorleftup = request.getParameter("locatorLeftUp");
	    String locatorleftdown = request.getParameter("locatorLeftDown");
	    String locatorrightup = request.getParameter("locatorRightUp");
	    String locatorrightdown = request.getParameter("locatorRightDown");
	    String srrleftup = request.getParameter("srrLeftUp");
	    String srrleftdown = request.getParameter("srrLeftDown");
	    String srrrightup = request.getParameter("srrRightUp");
	    String srrrightdown = request.getParameter("srrRightDown");
	    String retainerleftup = request.getParameter("retainerLeftUp");
	    String retainerleftdown = request.getParameter("retainerLeftDown");
	    String retainerrightup = request.getParameter("retainerRightUp");
	    String retainerrightdown = request.getParameter("retainerRightDown");
	    String singlecompleftup = request.getParameter("singlecompleftup");
	    String singlecompleftdown = request.getParameter("singlecompleftdown");
	    String singlecomprightup = request.getParameter("singlecomprightup");
	    String singlecomprightdown = request.getParameter("singlecomprightdown");
	    String singlecompselect = request.getParameter("singlecompselect");
	    String sfsincomleftup = request.getParameter("sfsincomleftup");
	    String sfsincomleftdown = request.getParameter("sfsincomleftdown");
	    String sfsincomrightup = request.getParameter("sfsincomrightup");
	    String sfsincomrightdown = request.getParameter("sfsincomrightdown");
	    String sfsincomselect = request.getParameter("sfSinComSelect");
	    String localplantleftup = request.getParameter("localplantleftup");
	    String localplantleftdown = request.getParameter("localplantleftdown");
	    String localplantrightup = request.getParameter("localplantrightup");
	    String localplantrightdown = request.getParameter("localplantrightdown");
	    String localplantselect = request.getParameter("localPlantSelect");
	    String requirerestor = request.getParameter("requireRestor");
	    
	    String operationDoctorTime = request.getParameter("operationDoctorTime");
	    String serviceTime = request.getParameter("serviceTime");
	    String servicesignature = request.getParameter("servicesignature");
	    String operationDoctorsignature = request.getParameter("operationDoctorsignature");
	    String repairDoctorsignature = request.getParameter("repairDoctorsignature");
	    String patientsignature = request.getParameter("patientsignature");
	    String patientTime = request.getParameter("patientTime");
	    String doctorTime = request.getParameter("doctorTime");
	    
	    String nobelpmcleftup = request.getParameter("nobelpmcleftup");//新增牙位图
	    String nobelpmcleftdown = request.getParameter("nobelpmcleftdown");
	    String nobelpmcrightup = request.getParameter("nobelpmcrightup");
	    String nobelpmcrightdown = request.getParameter("nobelpmcrightdown");
	    String zimmerleftup = request.getParameter("zimmerleftup");
	    String zimmerleftdown = request.getParameter("zimmerleftdown");
	    String zimmerrightup = request.getParameter("zimmerrightup");
	    String zimmerrightdown = request.getParameter("zimmerrightdown");
		String etleftup = request.getParameter("etleftup");
		String etleftdown = request.getParameter("etleftdown");
		String etrightup = request.getParameter("etrightup");
		String etrightdown = request.getParameter("etrightdown");
		String bbleftup = request.getParameter("bbleftup");
		String bbleftdown = request.getParameter("bbleftdown");
		String bbrightup = request.getParameter("bbrightup");
		String bbrightdown = request.getParameter("bbrightdown");
	    dp.setNobelpmcleftdown(nobelpmcleftdown);
	    dp.setNobelpmcleftup(nobelpmcleftup);
	    dp.setNobelpmcrightdown(nobelpmcrightdown);
	    dp.setNobelpmcrightup(nobelpmcrightup);
	    dp.setZimmerleftdown(zimmerleftdown);
	    dp.setZimmerleftup(zimmerleftup);
	    dp.setZimmerrightdown(zimmerrightdown);
	    dp.setZimmerrightup(zimmerrightup);
		dp.setEtleftup(etleftup);
		dp.setEtleftdown(etleftdown);
		dp.setEtrightup(etrightup);
		dp.setEtrightdown(etrightdown);
		dp.setBbleftup(bbleftup);
		dp.setBbleftdown(bbleftdown);
		dp.setBbrightup(bbrightup);
		dp.setBbrightdown(bbrightdown);
	    dp.setPatientTime(patientTime);
	    dp.setDoctorTime(doctorTime);
	    String username = request.getParameter("username");
	    String sex = request.getParameter("sex");
	    String age = request.getParameter("age");
	    dp.setAge(age);
	    dp.setUsername(username);
	    dp.setSex(sex);
	    dp.setOperationDoctorTime(operationDoctorTime);
	    dp.setServiceTime(serviceTime);
	    dp.setServicesignature(servicesignature);
	    dp.setOperationDoctorsignature(operationDoctorsignature);
	    dp.setRepairDoctorsignature(repairDoctorsignature);
	    dp.setPatientsignature(patientsignature);
	    dp.setOrder_number(orderNumber);
	    dp.setId(id);
	    dp.setTprgleftup(tprgleftup);
	    dp.setTprgleftdown(tprgleftdown);
	    dp.setTprgrightup(tprgrightup);
	    dp.setTprgrightdown(tprgrightdown);
	    dp.setPlantthistime(plantthistime);
	    dp.setDentiumleftup(dentiumleftup);
	    dp.setDentiumleftdown(dentiumleftdown);
	    dp.setDentiumrightup(dentiumrightup);
	    dp.setDentiumrightdown(dentiumrightdown);
	    dp.setIcxleftup(icxleftup);
	    dp.setIcxleftdown(icxleftdown);
	    dp.setIcxrightup(icxrightup);
	    dp.setIcxrightdown(icxrightdown);
		dp.setTemplantleftup(templantleftup);
		dp.setTemplantleftdown(templantleftdown);
		dp.setTemplantrightup(templantrightup);
		dp.setTemplantrightdown(templantrightdown);
	    dp.setNobelactiveleftup(nobelactiveleftup);
	    dp.setNobelactiveleftdown(nobelactiveleftdown);
	    dp.setNobelactiverightup(nobelactiverightup);
	    dp.setNobelactiverightdown(nobelactiverightdown);
	    dp.setCamlogleftup(camlogleftup);
	    dp.setCamlogleftdown(camlogleftdown);
	    dp.setCamlogrightup(camlogrightup);
	    dp.setCamlogrightdown(camlogrightdown);
	    dp.setHiossenleftup(hiossenleftup);
	    dp.setHiossenleftdown(hiossenleftdown);
	    dp.setHiossenrightup(hiossenrightup);
	    dp.setHiossenrightdown(hiossenrightdown);
	    dp.setRbrdleftup(rbrdleftup);
	    dp.setRbrdleftdown(rbrdleftdown);
	    dp.setRbrdrigthup(rbrdrigthup);
	    dp.setRbrdrigthdown(rbrdrigthdown);
	    dp.setInvidenleftup(invidenleftup);
	    dp.setInvidenleftdown(invidenleftdown);
	    dp.setInvidenrightup(invidenrightup);
	    dp.setInvidenrightdown(invidenrightdown);
	    dp.setLocalplantleftup(localplantleftup);
	    dp.setLocalplantleftdown(localplantleftdown);
	    dp.setLocalplantrightup(localplantrightup);
	    dp.setLocalplantrightdown(localplantrightdown);
	    dp.setLocatorleftup(locatorleftup);
	    dp.setLocatorleftdown(locatorleftdown);
	    dp.setLocatorrightup(locatorrightup);
	    dp.setLocatorrightdown(locatorrightdown);
	    dp.setSrrleftup(srrleftup);
	    dp.setSrrleftdown(srrleftdown);
	    dp.setSrrrightup(srrrightup);
	    dp.setSrrrightdown(srrrightdown);
	    dp.setRetainerleftup(retainerleftup);
	    dp.setRetainerleftdown(retainerleftdown);
	    dp.setRetainerrightup(retainerrightup);
	    dp.setRetainerrightdown(retainerrightdown);
	    dp.setSinglecompleftup(singlecompleftup);
	    dp.setSinglecompleftdown(singlecompleftdown);
	    dp.setSinglecomprightup(singlecomprightup);
	    dp.setSinglecomprightdown(singlecomprightdown);
	    dp.setSinglecompselect(singlecompselect);
	    dp.setSfsincomleftup(sfsincomleftup);
	    dp.setSfsincomleftdown(sfsincomleftdown);
	    dp.setSfsincomrightup(sfsincomrightup);
	    dp.setSfsincomrightdown(sfsincomrightdown);
	    dp.setSfsincomselect(sfsincomselect);
	    dp.setLocalplantselect(localplantselect);
	    dp.setRequirerestor(requirerestor);
		repairSchemeConfirmDao.saveRepairSchemeConfirmInfro(dp);
		Map<String,String> map=new HashMap<String,String>();
		map.put("form","repair");
		map.put("status","1");
		map.put("id",id);
		lcljTrackDao.updateFormStatus(map);
	}

	@Override
	public void deleteRepairInforById(String id) throws Exception {
		// TODO Auto-generated method stub
		repairSchemeConfirmDao.deleteRepairInforById(id);
	}

	@Override
	public void updateRepairInforById(RepairSchemeConfirm dp, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
//		String orderNumber = request.getParameter("order_number");
//	    String id = request.getParameter("id");
	    String tprgleftup = request.getParameter("tprgLeftUp");
	    String tprgleftdown = request.getParameter("tprgLeftDown");
	    String tprgrightup = request.getParameter("tprgRightUp");
	    String tprgrightdown = request.getParameter("tprgRightDown");
	    String plantthistime = request.getParameter("plantthistime");
	    String dentiumleftup = request.getParameter("dentiumLeftUp");
	    String dentiumleftdown = request.getParameter("dentiumLeftDown");
	    String dentiumrightup = request.getParameter("dentiumRightUp");
	    String dentiumrightdown = request.getParameter("dentiumRightDown");
	    String icxleftup = request.getParameter("icxLeftUp");
	    String icxleftdown = request.getParameter("icxLeftDown");
	    String icxrightup = request.getParameter("icxRightUp");
	    String icxrightdown = request.getParameter("icxRightDown");
		String templantleftup = request.getParameter("templantLeftUp");
		String templantleftdown = request.getParameter("templantLeftDown");
		String templantrightup = request.getParameter("templantRightUp");
		String templantrightdown = request.getParameter("templantRightDown");
		String nobelactiveleftup = request.getParameter("nobelActiveLeftUp");
	    String nobelactiveleftdown = request.getParameter("nobelActiveLeftDown");
	    String nobelactiverightup = request.getParameter("nobelActiveRightUp");
	    String nobelactiverightdown = request.getParameter("nobelActiveRightDown");
	    String camlogleftup = request.getParameter("camlogLeftUp");
	    String camlogleftdown = request.getParameter("camlogLeftDown");
	    String camlogrightup = request.getParameter("camlogRightUp");
	    String camlogrightdown = request.getParameter("camlogRightDown");
	    String hiossenleftup = request.getParameter("hiossenLeftUp");
	    String hiossenleftdown = request.getParameter("hiossenLeftDown");
	    String hiossenrightup = request.getParameter("hiossenRightUp");
	    String hiossenrightdown = request.getParameter("hiossenRightDown");
	    String rbrdleftup = request.getParameter("rbrdLeftUp");
	    String rbrdleftdown = request.getParameter("rbrdLeftDown");
	    String rbrdrigthup = request.getParameter("rbrdRigthUp");
	    String rbrdrigthdown = request.getParameter("rbrdRigthDown");
	    String invidenleftup = request.getParameter("inviDenLeftUp");
	    String invidenleftdown = request.getParameter("inviDenLeftDown");
	    String invidenrightup = request.getParameter("inviDenRightUp");
	    String invidenrightdown = request.getParameter("inviDenRightDown");
	    String locatorleftup = request.getParameter("locatorLeftUp");
	    String locatorleftdown = request.getParameter("locatorLeftDown");
	    String locatorrightup = request.getParameter("locatorRightUp");
	    String locatorrightdown = request.getParameter("locatorRightDown");
	    String srrleftup = request.getParameter("srrLeftUp");
	    String srrleftdown = request.getParameter("srrLeftDown");
	    String srrrightup = request.getParameter("srrRightUp");
	    String srrrightdown = request.getParameter("srrRightDown");
	    String retainerleftup = request.getParameter("retainerLeftUp");
	    String retainerleftdown = request.getParameter("retainerLeftDown");
	    String retainerrightup = request.getParameter("retainerRightUp");
	    String retainerrightdown = request.getParameter("retainerRightDown");
	    String singlecompleftup = request.getParameter("singlecompleftup");
	    String singlecompleftdown = request.getParameter("singlecompleftdown");
	    String singlecomprightup = request.getParameter("singlecomprightup");
	    String singlecomprightdown = request.getParameter("singlecomprightdown");
	    String singlecompselect = request.getParameter("singlecompselect");
	    String sfsincomleftup = request.getParameter("sfsincomleftup");
	    String sfsincomleftdown = request.getParameter("sfsincomleftdown");
	    String sfsincomrightup = request.getParameter("sfsincomrightup");
	    String sfsincomrightdown = request.getParameter("sfsincomrightdown");
	    String sfsincomselect = request.getParameter("sfSinComSelect");
	    String localplantleftup = request.getParameter("localplantleftup");
	    String localplantleftdown = request.getParameter("localplantleftdown");
	    String localplantrightup = request.getParameter("localplantrightup");
	    String localplantrightdown = request.getParameter("localplantrightdown");
	    String localplantselect = request.getParameter("localPlantSelect");
	    String requirerestor = request.getParameter("requireRestor");
	    
	    String operationDoctorTime = request.getParameter("operationDoctorTime");
	    String serviceTime = request.getParameter("serviceTime");
	    String servicesignature = request.getParameter("servicesignature");
	    String operationDoctorsignature = request.getParameter("operationDoctorsignature");
	    String repairDoctorsignature = request.getParameter("repairDoctorsignature");
	    String patientsignature = request.getParameter("patientsignature");
	    String patientTime = request.getParameter("patientTime");
	    String doctorTime = request.getParameter("doctorTime");
	    
	    String nobelpmcleftup = request.getParameter("nobelpmcleftup");//新增牙位图
	    String nobelpmcleftdown = request.getParameter("nobelpmcleftdown");
	    String nobelpmcrightup = request.getParameter("nobelpmcrightup");
	    String nobelpmcrightdown = request.getParameter("nobelpmcrightdown");
	    String zimmerleftup = request.getParameter("zimmerleftup");
	    String zimmerleftdown = request.getParameter("zimmerleftdown");
	    String zimmerrightup = request.getParameter("zimmerrightup");
	    String zimmerrightdown = request.getParameter("zimmerrightdown");
		String etleftup = request.getParameter("etleftup");
		String etleftdown = request.getParameter("etleftdown");
		String etrightup = request.getParameter("etrightup");
		String etrightdown = request.getParameter("etrightdown");
		String bbleftup = request.getParameter("bbleftup");
		String bbleftdown = request.getParameter("bbleftdown");
		String bbrightup = request.getParameter("bbrightup");
		String bbrightdown = request.getParameter("bbrightdown");

	    dp.setNobelpmcleftdown(nobelpmcleftdown);
	    dp.setNobelpmcleftup(nobelpmcleftup);
	    dp.setNobelpmcrightdown(nobelpmcrightdown);
	    dp.setNobelpmcrightup(nobelpmcrightup);
	    dp.setZimmerleftdown(zimmerleftdown);
	    dp.setZimmerleftup(zimmerleftup);
	    dp.setZimmerrightdown(zimmerrightdown);
	    dp.setZimmerrightup(zimmerrightup);
		dp.setEtleftup(etleftup);
		dp.setEtleftdown(etleftdown);
		dp.setEtrightup(etrightup);
		dp.setEtrightdown(etrightdown);
		dp.setBbleftup(bbleftup);
		dp.setBbleftdown(bbleftdown);
		dp.setBbrightup(bbrightup);
		dp.setBbrightdown(bbrightdown);
	    dp.setPatientTime(patientTime);
	    dp.setDoctorTime(doctorTime);
	    String username = request.getParameter("username");
	    String sex = request.getParameter("sex");
	    String age = request.getParameter("age");
	    dp.setAge(age);
	    dp.setUsername(username);
	    dp.setSex(sex);
	    dp.setOperationDoctorTime(operationDoctorTime);
	    dp.setServiceTime(serviceTime);
	    dp.setServicesignature(servicesignature);
	    dp.setOperationDoctorsignature(operationDoctorsignature);
	    dp.setRepairDoctorsignature(repairDoctorsignature);
	    dp.setPatientsignature(patientsignature);
//	    dp.setOrder_number(orderNumber);
//	    dp.setId(id);
	    dp.setTprgleftup(tprgleftup);
	    dp.setTprgleftdown(tprgleftdown);
	    dp.setTprgrightup(tprgrightup);
	    dp.setTprgrightdown(tprgrightdown);
	    dp.setPlantthistime(plantthistime);
	    dp.setDentiumleftup(dentiumleftup);
	    dp.setDentiumleftdown(dentiumleftdown);
	    dp.setDentiumrightup(dentiumrightup);
	    dp.setDentiumrightdown(dentiumrightdown);
	    dp.setIcxleftup(icxleftup);
	    dp.setIcxleftdown(icxleftdown);
	    dp.setIcxrightup(icxrightup);
	    dp.setIcxrightdown(icxrightdown);
		dp.setTemplantleftup(templantleftup);
		dp.setTemplantleftdown(templantleftdown);
		dp.setTemplantrightup(templantrightup);
		dp.setTemplantrightdown(templantrightdown);
	    dp.setNobelactiveleftup(nobelactiveleftup);
	    dp.setNobelactiveleftdown(nobelactiveleftdown);
	    dp.setNobelactiverightup(nobelactiverightup);
	    dp.setNobelactiverightdown(nobelactiverightdown);
	    dp.setCamlogleftup(camlogleftup);
	    dp.setCamlogleftdown(camlogleftdown);
	    dp.setCamlogrightup(camlogrightup);
	    dp.setCamlogrightdown(camlogrightdown);
	    dp.setHiossenleftup(hiossenleftup);
	    dp.setHiossenleftdown(hiossenleftdown);
	    dp.setHiossenrightup(hiossenrightup);
	    dp.setHiossenrightdown(hiossenrightdown);
	    dp.setRbrdleftup(rbrdleftup);
	    dp.setRbrdleftdown(rbrdleftdown);
	    dp.setRbrdrigthup(rbrdrigthup);
	    dp.setRbrdrigthdown(rbrdrigthdown);
	    dp.setInvidenleftup(invidenleftup);
	    dp.setInvidenleftdown(invidenleftdown);
	    dp.setInvidenrightup(invidenrightup);
	    dp.setInvidenrightdown(invidenrightdown);
	    dp.setLocalplantleftup(localplantleftup);
	    dp.setLocalplantleftdown(localplantleftdown);
	    dp.setLocalplantrightup(localplantrightup);
	    dp.setLocalplantrightdown(localplantrightdown);
	    dp.setLocatorleftup(locatorleftup);
	    dp.setLocatorleftdown(locatorleftdown);
	    dp.setLocatorrightup(locatorrightup);
	    dp.setLocatorrightdown(locatorrightdown);
	    dp.setSrrleftup(srrleftup);
	    dp.setSrrleftdown(srrleftdown);
	    dp.setSrrrightup(srrrightup);
	    dp.setSrrrightdown(srrrightdown);
	    dp.setRetainerleftup(retainerleftup);
	    dp.setRetainerleftdown(retainerleftdown);
	    dp.setRetainerrightup(retainerrightup);
	    dp.setRetainerrightdown(retainerrightdown);
	    dp.setSinglecompleftup(singlecompleftup);
	    dp.setSinglecompleftdown(singlecompleftdown);
	    dp.setSinglecomprightup(singlecomprightup);
	    dp.setSinglecomprightdown(singlecomprightdown);
	    dp.setSinglecompselect(singlecompselect);
	    dp.setSfsincomleftup(sfsincomleftup);
	    dp.setSfsincomleftdown(sfsincomleftdown);
	    dp.setSfsincomrightup(sfsincomrightup);
	    dp.setSfsincomrightdown(sfsincomrightdown);
	    dp.setSfsincomselect(sfsincomselect);
	    dp.setLocalplantselect(localplantselect);
	    dp.setRequirerestor(requirerestor);
		repairSchemeConfirmDao.updateRepairInforById(dp);
	}

	@Override
	public List<JSONObject> findRepairInforById(String id) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> json = repairSchemeConfirmDao.findRepairInforById(id);
		return json;
	}

	@Override
	public List<JSONObject> findReapirInforAll() throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = repairSchemeConfirmDao.findReapirInforAll();
		return list;
	}

	@Override
	public RepairSchemeConfirm selectRepairSchemeConfirmById(String id) throws Exception {
		// TODO Auto-generated method stub
		return repairSchemeConfirmDao.selectRepairSchemeConfirmById(id);
	}

	/**   
	  * <p>Title: selectRepairInforById</p>   
	  * <p>Description: </p>   
	  * @param id
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.zzbl.service.IRepairSchemeConfirmService#selectRepairInforById(java.lang.String)   
	  */  
	@Override
	public JSONObject selectRepairInforById(String id) throws Exception {
		// TODO Auto-generated method stub
		return repairSchemeConfirmDao.selectRepairInforById(id);
	}

}
