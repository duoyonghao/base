package com.hudh.lclj.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hudh.lclj.service.IFlowOperateService;
import com.hudh.lclj.service.IFlowService;
import com.hudh.util.HUDHUtil;
import com.hudh.zzbl.entity.LocatorFamiliar;
import com.hudh.zzbl.service.DzblService;
import com.hudh.zzbl.service.IRepairSchemeConfirmService;
import com.hudh.zzbl.service.IZzblCheckService;
import com.hudh.zzbl.service.IZzblService;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.hudh.lclj.StaticVar;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.entity.LcljOptRecode;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.LcljWorklist;
import com.hudh.lclj.entity.OperatingRecord;
/**
 * 临床路径操作Controller
 * @author XKY
 *
 */



@Controller
@RequestMapping("/HUDH_FlowAct")
public class HUDH_FlowAct {

    private Logger logger = LoggerFactory.getLogger(HUDH_FlowAct.class);
    /**
     * 临床路径数据保存接口
     */
    @Autowired
    private IFlowService flowService;

    @Autowired
    private IZzblService zzblService;

    @Autowired
    private IZzblCheckService zzblCheckService;

    @Autowired
    private IRepairSchemeConfirmService rscService;

    @Autowired
    private DzblService dzblService;

    /**
     * 临床路径数据保存接口
     */
    @Autowired
    private IFlowOperateService flowOperateService;

    @Autowired
    private YZParaLogic logic;

    /**
     * 保存临床路径跟踪信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveLcljOrderTrack.act")
    public String saveLcljOrderTrack(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        String blcode = request.getParameter("blcode"); //病历号
        String tooth_total = request.getParameter("tooth_total"); //本次手术牙齿颗数
        String ssType = request.getParameter("ssType"); //跟踪方式
        String isBone = request.getParameter("isBone"); //是否植骨
        String left_up = request.getParameter("left_up"); //左上牙齿颗数
        String left_down = request.getParameter("left_down"); //左下
        String right_up = request.getParameter("right_up"); //右上
        String right_down = request.getParameter("right_down"); //右下

        String counsellor = request.getParameter("counsellor");//咨询师
        String plant_physician = request.getParameter("plant_physician");//种植医师
        String repair_physician = request.getParameter("repair_physician");//修复医师
        String clinic_nurse = request.getParameter("clinic_nurse");//诊室护士
        String customer_service = request.getParameter("customer_service");//客服
        String plant_system = request.getParameter("plant_system");//种植系统
        String crown_material = request.getParameter("crown_material");//牙冠材料
        String tooth_texture = request.getParameter("tooth_texture");//牙冠材料
        String imageological_examination = request.getParameter("imageological_examination");//影像学检查
        String consultation = request.getParameter("consultation");//专家会诊
        String advisory = request.getParameter("advisory");//咨询
        String repair_left_up = request.getParameter("repair_left_up");//修复左上牙齿颗数
        String repair_left_down = request.getParameter("repair_left_down");//修复左下牙齿颗数
        String repair_right_up = request.getParameter("repair_right_up");//修复右上牙齿颗数
        String repair_right_down = request.getParameter("repair_right_down");//修复右下牙齿颗数
        String zz_tooth_total = request.getParameter("zz_tooth_total");//修复牙齿颗数总数
        String modeoperation = request.getParameter("modeoperation");//术前/术后取模
        String remark = request.getParameter("remark");//备注
        String abutment_station = request.getParameter("abutment_station");
        String id = request.getParameter("id");//修复牙齿颗数总数
        String userdocument_id = request.getParameter("seqId");////患者档案表ID

        LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
        lcljOrderTrack.setBlcode(blcode);
        lcljOrderTrack.setTooth(tooth_total);
        lcljOrderTrack.setType(ssType);
        lcljOrderTrack.setBone(isBone);
        YZPerson person = SessionUtil.getLoginPerson(request);
        lcljOrderTrack.setCreatorid(person.getSeqId());
        lcljOrderTrack.setCreatorname(person.getUserName());

        lcljOrderTrack.setCounsellor(counsellor);
        lcljOrderTrack.setPlantPhysician(plant_physician);
        lcljOrderTrack.setClinicNurse(clinic_nurse);
        lcljOrderTrack.setRepairPphysician(repair_physician);
        lcljOrderTrack.setCustomerService(customer_service);
        lcljOrderTrack.setPlantSystem(plant_system);
        lcljOrderTrack.setCrownMaterial(crown_material);
        lcljOrderTrack.setTooth_texture(tooth_texture);//牙质材料
        lcljOrderTrack.setImageologicalExamination(imageological_examination);
        lcljOrderTrack.setConsultation(consultation);
        lcljOrderTrack.setAdvisory(advisory);
        lcljOrderTrack.setRepairLeftUp(repair_left_up);
        lcljOrderTrack.setRepairLeftDown(repair_left_down);
        lcljOrderTrack.setRepairRightUp(repair_right_up);
        lcljOrderTrack.setRepairRightDown(repair_right_down);
        lcljOrderTrack.setRepairToothTotal(zz_tooth_total);
        lcljOrderTrack.setModeoperation(modeoperation);
        lcljOrderTrack.setRemark(remark);
        lcljOrderTrack.setAbutment_station(abutment_station);
        lcljOrderTrack.setUserdocument_id(userdocument_id);
        if (YZUtility.isNotNullOrEmpty(left_up)) {
            left_up = left_up.substring(0, left_up.length() - 1); //去掉字符串尾部的逗号
            lcljOrderTrack.setLeftUp(left_up);
        }
        if (YZUtility.isNotNullOrEmpty(left_down)) {
            left_down = left_down.substring(0, left_down.length() - 1);
            lcljOrderTrack.setLeftDown(left_down);
        }
        if (YZUtility.isNotNullOrEmpty(right_up)) {
            right_up = right_up.substring(0, right_up.length() - 1);
            lcljOrderTrack.setRightUp(right_up);
        }
        if (YZUtility.isNotNullOrEmpty(right_down)) {
            right_down = right_down.substring(0, right_down.length() - 1);
            lcljOrderTrack.setRightDown(right_down);
        }

        if (YZUtility.isNotNullOrEmpty(repair_left_up)) {
            repair_left_up = repair_left_up.substring(0, repair_left_up.length() - 1);
            lcljOrderTrack.setRepairLeftUp(repair_left_up);
        }
        if (YZUtility.isNotNullOrEmpty(repair_left_down)) {
            repair_left_down = repair_left_down.substring(0, repair_left_down.length() - 1);
            lcljOrderTrack.setRepairLeftDown(repair_left_down);
        }
        if (YZUtility.isNotNullOrEmpty(repair_right_up)) {
            repair_right_up = repair_right_up.substring(0, repair_right_up.length() - 1);
            lcljOrderTrack.setRepairRightUp(repair_right_up);
        }
        if (YZUtility.isNotNullOrEmpty(repair_right_down)) {
            repair_right_down = repair_right_down.substring(0, repair_right_down.length() - 1);
            lcljOrderTrack.setRepairRightDown(repair_right_down);
        }

        try {
            //保存临床跟踪数据
            flowService.saveLcljOrderTrackInfo(lcljOrderTrack, id, request);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 点击下一步提交给下一个节点
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/gotoNextNode.act")
    public String gotoNextNode(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        String orderNumber = request.getParameter("orderNumber"); //编号
        String isShouShu = request.getParameter("isShouShu"); //判断是否是手术节点，如果是则更新牙位信息
        try {
            //保存临床跟踪数据
            flowOperateService.submitFlow(orderNumber, request);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
            if (isShouShu.equals("SHOUSHU")) { //如果是手术治疗节点则更新牙位信息
                Map<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("orderNumber", orderNumber);
                String leftUp = request.getParameter("leftUp");
                String leftDown = request.getParameter("leftDown");
                String rightUp = request.getParameter("rightUp");
                String rightDown = request.getParameter("rightDown");
                String repairLeftUp = request.getParameter("repairLeftUp");
                String repairLeftDown = request.getParameter("repairLeftDown");
                String repairRightUp = request.getParameter("repairRightUp");
                String repairRightDown = request.getParameter("repairRightDown");
                String tooth = request.getParameter("toothTotal");
                String repairToothTotal = request.getParameter("repairToothTotal");
                if (YZUtility.isNotNullOrEmpty(leftUp)) {
                    dataMap.put("leftUp", leftUp);
                }
                if (YZUtility.isNotNullOrEmpty(leftDown)) {
                    dataMap.put("leftDown", leftDown);
                }
                if (YZUtility.isNotNullOrEmpty(rightUp)) {
                    dataMap.put("rightUp", rightUp);
                }
                if (YZUtility.isNotNullOrEmpty(rightDown)) {
                    dataMap.put("rightDown", rightDown);
                }
                if (YZUtility.isNotNullOrEmpty(repairLeftUp)) {
                    dataMap.put("repairLeftUp", repairLeftUp);
                }
                if (YZUtility.isNotNullOrEmpty(repairLeftDown)) {
                    dataMap.put("repairLeftDown", repairLeftDown);
                }
                if (YZUtility.isNotNullOrEmpty(repairRightUp)) {
                    dataMap.put("repairRightUp", repairRightUp);
                }
                if (YZUtility.isNotNullOrEmpty(repairRightDown)) {
                    dataMap.put("repairRightDown", repairRightDown);
                }
                if (YZUtility.isNotNullOrEmpty(tooth)) {
                    dataMap.put("tooth", tooth);
                }
                if (YZUtility.isNotNullOrEmpty(repairToothTotal)) {
                    dataMap.put("repairToothTotal", repairToothTotal);
                }
                flowService.updateOrderTrack(dataMap);
            }
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
        }
        return null;
    }

    /**
     * 点击退回上一节点
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/reject.act")
    public String reject(HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        String orderNumber = request.getParameter("orderNumber"); //编号
        try {
            //保存临床跟踪数据
            /*flowService.reject(orderNumber);*/
            flowOperateService.rejectFlow(orderNumber, request);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 保存备注信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveOptRecode.act")
    public String saveOptRecode(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        YZPerson person = SessionUtil.getLoginPerson(request);
        String orderNumber = request.getParameter("orderNumber"); //编号
        String remarks = request.getParameter("remarks"); //备注信息
        String variation = request.getParameter("variation"); //备注信息

        LcljOptRecode lcljOptRecode = new LcljOptRecode();
        lcljOptRecode.setOrderNumber(orderNumber);
        lcljOptRecode.setRemarks(remarks);
        lcljOptRecode.setVariation(variation);
//		lcljOptRecode.setCreator(person.getUserId());
        lcljOptRecode.setCreator(person.getUserName());
        try {

            flowService.saveOptRecode(lcljOptRecode);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据orderNumber获取备注列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/findOptRecodeList.act")
    public String findOptRecodeList(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String orderNumber = request.getParameter("orderNumber"); //编号
        String searchFlowink = request.getParameter("searchFlowink"); //流程步骤

        if (YZUtility.isNullorEmpty(searchFlowink)) {
            searchFlowink = null;
        }
        try {
            if (YZUtility.isNotNullOrEmpty(orderNumber)) {
                List<JSONObject> data = flowService.findOptRecodeList(orderNumber, searchFlowink);
                JSONObject jo = new JSONObject();
                jo.put("remarks", JSON.toJSON(data));

                //获取当前人员是否有权限操作
                YZPerson person = SessionUtil.getLoginPerson(request);
                List<JSONObject> list = flowService.findLcljAdminOrAgency(request);
                jo.put("hasPass", StaticVar.LCLJ_HASPASS_NO);
                if (null != list) {
                    for (JSONObject object : list) {
                        if (person.getUserId().equals(object.get("para_value")) || person.getSeqId().equals(object.get("para_value"))) {
                            jo.put("hasPass", StaticVar.LCLJ_HASPASS_YES);
                            break;
                        }
                    }
                }
                YZUtility.DEAL_SUCCESS(jo, null, response, logger);
            }
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }


    /**
     * 根据orderNumber获取患者挂号信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/findRegListByBlcode.act")
    public String findRegListByBlcode(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String orderNumber = request.getParameter("orderNumber"); //编号
        try {
            if (YZUtility.isNotNullOrEmpty(orderNumber)) {
                List<KqdsReg> data = flowService.findRegListByBlcode(orderNumber);
                JSONObject jo = new JSONObject();
                jo.put("regs", JSON.toJSON(data));
                YZUtility.DEAL_SUCCESS(jo, null, response, logger);
            }
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据orderNumber获取手术及患者信息
     * 并判断当前节点是否超期，如果超期修改对应节点状态
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/findOrderTrackInfo.act")
    public String findOrderTrackInfo(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        String orderNumber = request.getParameter("orderNumber"); //编号
        try {
            if (YZUtility.isNotNullOrEmpty(orderNumber)) {
                JSONObject data = flowService.findOrderTrackInfo(orderNumber);
                String nodes = data.getString("flowinfo");
                List<LcljNodeConfig> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNodeConfig.class);
                //获取该路径下已经办理过的节点
                List<LcljWorklist> hadhList = flowOperateService.findHadWorkList(orderNumber, request);
                //将节点转成map
                Map<String, LcljNodeConfig> nodeListMap = new HashMap<String, LcljNodeConfig>();
                for (LcljNodeConfig lcljNodeConfig : nodeList) {
                    lcljNodeConfig.setStus("0");
                    nodeListMap.put(lcljNodeConfig.getNodeId(), lcljNodeConfig);
                }
                for (LcljWorklist lcljWorklist : hadhList) {
                    String stusTemp = nodeListMap.get(lcljWorklist.getNodeId()).getStus();
                    if (null != stusTemp && !stusTemp.equals("1")) {
                        nodeListMap.get(lcljWorklist.getNodeId()).setStus(lcljWorklist.getNodeStatus() + "");
                    }
                }

                data.put("nodes", JSON.toJSONString(nodeList));
//				YZUtility.DEAL_SUCCESS(data,null, response, logger);
                return JSON.toJSONString(data);
            }
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据订单编号查询患者的信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/findOrderTrackInforByOrderNumber.act")
    public String findOrderTrackInforByOrderNumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
        YZPerson person = SessionUtil.getLoginPerson(request);
        String regdept = request.getParameter("regdept");
        String doctor = request.getParameter("doctor");
        String fzsj = request.getParameter("cjsj");
        String fzsj2 = request.getParameter("cjsj2");
        String organization = request.getParameter("organization");
        String usercodeorname = request.getParameter("usercodeorname");
        String username = request.getParameter("username");
        String userId = request.getParameter("userId");
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        String phonenumber = request.getParameter("phonenumber");
        String ssType = request.getParameter("ssType");
        String isBone = request.getParameter("isBone");
        String counsellor = request.getParameter("counsellor");
        String plant_physician = request.getParameter("plant_physician");
        String repair_physician = request.getParameter("repair_physician");
        String nodename = request.getParameter("nodename");
        String sortName = request.getParameter("sortName");
        String sortOrder = request.getParameter("sortOrder");
        // 可见人员过滤
        String visualstaff = SessionUtil.getVisualstaff(request);
        /*医务部权限 2020/06/18 start*/
        if (organization == null || organization.equals("")) {
            organization = ChainUtil.getCurrentOrganization(request);
        }
        String paraValue = null;
        try {
            JSONObject json = logic.getParaValueByOrganization(SessionUtil.SYS_MEDICAL, organization);
            paraValue = json.getString("paraValue");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        JSONObject json = new JSONObject();
        if (paraValue != null && !paraValue.equals("") && paraValue.contains(person.getUserPriv())) {
            map.put("paraValue", "1");
        } else {
            map.put("paraValue", "");
        }
        /*医务部权限 2020/06/18 end*/
        if (YZUtility.isNotNullOrEmpty(nodename)) {
            map.put("nodename", nodename);
        }
        if (YZUtility.isNotNullOrEmpty(visualstaff)) {
            map.put("querytype", visualstaff);
        }
        if (!YZUtility.isNullorEmpty(usercodeorname)) {
            map.put("usercodeorname", usercodeorname);
        }
        if (!YZUtility.isNullorEmpty(regdept)) {
            map.put("regdept", regdept);
        }
        if (!YZUtility.isNullorEmpty(doctor)) {
            map.put("doctor", doctor);
        }
        if (!YZUtility.isNullorEmpty(fzsj)) {
            map.put("fzsj", fzsj);
        }
        if (!YZUtility.isNullorEmpty(fzsj2)) {
            map.put("fzsj2", fzsj2);
        }
        if (!YZUtility.isNullorEmpty(username)) {
            map.put("username", username);
        }
        if (!YZUtility.isNullorEmpty(userId)) {
            map.put("userId", userId);
        }
        if (!YZUtility.isNullorEmpty(starttime)) {
            map.put("starttime", starttime);
        }
        if (!YZUtility.isNullorEmpty(endtime)) {
            map.put("endtime", endtime);
        }
        if (!YZUtility.isNullorEmpty(phonenumber)) {
            map.put("phonenumber", phonenumber);
        }
        if (!YZUtility.isNullorEmpty(ssType)) {
            map.put("ssType", ssType);
        }
        if (!YZUtility.isNullorEmpty(isBone)) {
            map.put("isBone", isBone);
        }
        if (!YZUtility.isNullorEmpty(counsellor)) {
            map.put("counsellor", counsellor);
        }
        if (!YZUtility.isNullorEmpty(plant_physician)) {
            map.put("plant_physician", plant_physician);
        }
        if (!YZUtility.isNullorEmpty(repair_physician)) {
            map.put("repair_physician", repair_physician);
        }
        if (!YZUtility.isNullorEmpty(sortName)) {
            map.put("sortName", sortName);
            map.put("sortOrder", sortOrder);
        }
        // 导出参数
        String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
        String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
        String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
        try {
            // 初始化分页实体类
            @SuppressWarnings("rawtypes")
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());
            JSONObject jsonO = flowService.findOrderTrackInforByOrderNumber(person, bp, map, organization, json);

            /*-------导出excel---------*/
            if (flag != null && flag.equals("exportTable")) {
                JSONObject data = flowService.findOrderTrackInforByOrderNumber(person, bp, map, organization, json);
                if (data != null) {
                    ExportBean bean = ExportTable.initExcel4RsWrite("临床路径", fieldArr, fieldnameArr, response, request);
                    bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List<JSONObject>) data.get("rows"), "lclj_findOrderTrackInforByOrderNumber");
                    ExportTable.writeExcel4DownLoad("临床路径", bean.getWorkbook(), response);
                }
                return null;
            }
            YZUtility.DEAL_SUCCESS(jsonO, null, response, logger);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据配置的para_name找临床路径管理员
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findLcljAdmin.act")
    public String findLcljAdmin(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        try {
            JSONObject jo = flowService.findLcljAdmin(request);
            YZUtility.DEAL_SUCCESS(jo, null, response, logger);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据配置的para_name找临床路径管理员及配置的代办人
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findLcljAdminOrAgency.act")
    public String findLcljAdminOrAgency(HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        YZPerson person = SessionUtil.getLoginPerson(request);
        try {
            String currentUserId = person.getUserId();
            String currentUserSeqId = person.getSeqId();
            List<JSONObject> list = flowService.findLcljAdminOrAgency(request);
            JSONObject jo = new JSONObject();
            jo.put("hasPass", StaticVar.LCLJ_HASPASS_NO);
            if (null != list) {
                for (JSONObject object : list) {
                    if (currentUserId.equals(object.get("para_value")) || currentUserSeqId.equals(object.get("para_value"))) {
                        jo.put("hasPass", StaticVar.LCLJ_HASPASS_YES);
                        break;
                    }
                }
            }
            YZUtility.DEAL_SUCCESS(jo, null, response, logger);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 更新代办人
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateAgencyUser.act")
    public String updateAgencyUser(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        String seqId = request.getParameter("seq_id");
        String paraValue = request.getParameter("paraValue");
        YZPara yzPara = new YZPara();
        yzPara.setSeqId(seqId);
        yzPara.setParaValue(paraValue);

        try {
            flowService.updateAgencyUser(yzPara);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 跟新备注信息状态
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateRemarkStus.act")
    public String updateRemarkStus(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        try {
            flowService.updateRemarkStus(id, status);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据条件精确查询临床路径患者信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/findOrderTrackInforByConditionQuery.act")
    public String findOrderTrackInforByConditionQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String regdept = request.getParameter("regdept");
        String doctor = request.getParameter("doctor");
        String usercodeorname = request.getParameter("usercodeorname");
        String fzsj = request.getParameter("fzsj");
        String fzsj2 = request.getParameter("fzsj2");
        String organization = request.getParameter("organization");
        YZPerson person = SessionUtil.getLoginPerson(request);
        Map<String, String> map = new HashMap<>();
        if (!YZUtility.isNullorEmpty(regdept)) {
            map.put("regdept", regdept);
        }
        if (!YZUtility.isNullorEmpty(doctor)) {
            map.put("doctor", doctor);
        }
        if (!YZUtility.isNullorEmpty(usercodeorname)) {
            map.put("usercodeorname", usercodeorname);
        }
        if (!YZUtility.isNullorEmpty(fzsj)) {
            map.put("fzsj", fzsj);
        }
        if (!YZUtility.isNullorEmpty(fzsj2)) {
            map.put("fzsj2", fzsj2);
        }
        try {
            JSONObject json = flowService.findOrderTrackInforByConditionQuery(person, map, organization);
            YZUtility.DEAL_SUCCESS(json, null, response, logger);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据id更新手术数据信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateOrderTrackById.act")
    public String updateOrderTrackById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String sstime = request.getParameter("sstime");
        Map<String, String> map = new HashMap<>();
        if (!YZUtility.isNullorEmpty(id)) {
            map.put("id", id);
        }
        if (!YZUtility.isNullorEmpty(sstime)) {
            map.put("sstime", sstime);
        }
        try {
            flowService.updateOrderTrackById(map);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据id删除手术信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteOrderTrackInforById.act")
    public String deleteOrderTrackInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        try {
            flowService.deleteOrderTrackInforById(id);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 改变临床路径流程植骨状态
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changeLcljOrderTrackBoneStatus.act")
    public String changeLcljOrderTrackBoneStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String blcode = request.getParameter("blcode"); //病历号
        String tooth_total = request.getParameter("tooth_total"); //本次手术牙齿颗数
        String ssType = request.getParameter("ssType"); //跟踪方式
        String isBone = request.getParameter("isBone"); //是否植骨
        String left_up = request.getParameter("left_up"); //左上牙齿颗数
        String left_down = request.getParameter("left_down"); //左下
        String right_up = request.getParameter("right_up"); //右上
        String right_down = request.getParameter("right_down"); //右下

        String counsellor = request.getParameter("counsellor");//咨询师
        String plant_physician = request.getParameter("plant_physician");//种植医师
        String repair_physician = request.getParameter("repair_physician");//修复医师
        String clinic_nurse = request.getParameter("clinic_nurse");//诊室护士
        String customer_service = request.getParameter("customer_service");//客服
        String plant_system = request.getParameter("plant_system");//种植系统
        String crown_material = request.getParameter("crown_material");//牙冠材料
        String imageological_examination = request.getParameter("imageological_examination");//影像学检查
        String consultation = request.getParameter("consultation");//专家会诊
        String advisory = request.getParameter("advisory");//咨询
        String repair_left_up = request.getParameter("repair_left_up");//修复左上牙齿颗数
        String repair_left_down = request.getParameter("repair_left_down");//修复左下牙齿颗数
        String repair_right_up = request.getParameter("repair_right_up");//修复右上牙齿颗数
        String repair_right_down = request.getParameter("repair_right_down");//修复右下牙齿颗数
        String zz_tooth_total = request.getParameter("zz_tooth_total");//修复牙齿颗数总数

        String id = request.getParameter("id");

        LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
        lcljOrderTrack.setBlcode(blcode);
        lcljOrderTrack.setTooth(tooth_total);
        lcljOrderTrack.setType(ssType);
        lcljOrderTrack.setBone(isBone);
        YZPerson person = SessionUtil.getLoginPerson(request);
        lcljOrderTrack.setCreatorid(person.getSeqId());
        lcljOrderTrack.setCreatorname(person.getUserName());

        lcljOrderTrack.setCounsellor(counsellor);
        lcljOrderTrack.setPlantPhysician(plant_physician);
        lcljOrderTrack.setClinicNurse(clinic_nurse);
        lcljOrderTrack.setRepairPphysician(repair_physician);
        lcljOrderTrack.setCustomerService(customer_service);
        lcljOrderTrack.setPlantSystem(plant_system);
        lcljOrderTrack.setCrownMaterial(crown_material);
        lcljOrderTrack.setImageologicalExamination(imageological_examination);
        lcljOrderTrack.setConsultation(consultation);
        lcljOrderTrack.setAdvisory(advisory);
        lcljOrderTrack.setRepairLeftUp(repair_left_up);
        lcljOrderTrack.setRepairLeftDown(repair_left_down);
        lcljOrderTrack.setRepairRightUp(repair_right_up);
        lcljOrderTrack.setRepairRightDown(repair_right_down);
        lcljOrderTrack.setRepairToothTotal(zz_tooth_total);

        if (YZUtility.isNotNullOrEmpty(left_up)) {
            left_up = left_up.substring(0, left_up.length() - 1); //去掉字符串尾部的逗号
            lcljOrderTrack.setLeftUp(left_up);
        }
        if (YZUtility.isNotNullOrEmpty(left_down)) {
            left_down = left_down.substring(0, left_down.length() - 1);
            lcljOrderTrack.setLeftDown(left_down);
        }
        if (YZUtility.isNotNullOrEmpty(right_up)) {
            right_up = right_up.substring(0, right_up.length() - 1);
            lcljOrderTrack.setRightUp(right_up);
        }
        if (YZUtility.isNotNullOrEmpty(right_down)) {
            right_down = right_down.substring(0, right_down.length() - 1);
            lcljOrderTrack.setRightDown(right_down);
        }

        if (YZUtility.isNotNullOrEmpty(repair_left_up)) {
            repair_left_up = repair_left_up.substring(0, repair_left_up.length() - 1);
            lcljOrderTrack.setRepairLeftUp(repair_left_up);
        }
        if (YZUtility.isNotNullOrEmpty(repair_left_down)) {
            repair_left_down = repair_left_down.substring(0, repair_left_down.length() - 1);
            lcljOrderTrack.setRepairLeftDown(repair_left_down);
        }
        if (YZUtility.isNotNullOrEmpty(repair_right_up)) {
            repair_right_up = repair_right_up.substring(0, repair_right_up.length() - 1);
            lcljOrderTrack.setRepairRightUp(repair_right_up);
        }
        if (YZUtility.isNotNullOrEmpty(repair_right_down)) {
            repair_right_down = repair_right_down.substring(0, repair_right_down.length() - 1);
            lcljOrderTrack.setRepairRightDown(repair_right_down);
        }

        try {
            //保存临床跟踪数据
            flowService.changeLcljOrderTrackBoneStatus(lcljOrderTrack, id);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据id查询临床路径信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findLcljOrderTrsackById.act")
    public String findLcljOrderTrsackById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        try {
            JSONObject json = flowService.findLcljOrderTrsackById(id);
            YZUtility.DEAL_SUCCESS(json, null, response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * 根据路径单号和节点id查询
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findHadWorkByOrderNumberAndNodeId.act")
    public String findHadWorkByOrderNumberAndNodeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ordNumber = request.getParameter("ordNumber");
        String nodeId = request.getParameter("nodeId");
        Map<String, String> dataMap = new HashMap<String, String>();
        if (YZUtility.isNotNullOrEmpty(ordNumber)) {
            dataMap.put("ordNumber", ordNumber);
        }
        if (YZUtility.isNotNullOrEmpty(nodeId)) {
            dataMap.put("nodeId", nodeId);
        }
        try {
            LcljWorklist work = flowOperateService.findHadWorkByOrderNumberAndNodeId(dataMap);
            if (work == null) {
                throw new Exception(" ");
            }
            JSONObject jo = new JSONObject();
            jo.put("selNodeWork", work);
            YZUtility.DEAL_SUCCESS(jo, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
        }
        return null;
    }

    /**
     * 更新临床路径节点信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateOrderTrackNodes.act")
    public String updateOrderTrackNodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] plant_system = request.getParameterValues("plant_system");//种植系统
        StringBuffer sbplantSystem = new StringBuffer();
        for (int i = 0; i < plant_system.length; i++) {
            sbplantSystem.append(plant_system[i] + ";");
        }
        //牙冠材料
		/*String[] crown_material = request.getParameterValues("crown_material");
		StringBuffer sbcrownMaterial = new StringBuffer();
		for (int i = 0; i < crown_material.length; i++) {
			sbcrownMaterial.append(crown_material[i] + ";");
		}*/
		String flowCode = request.getParameter("flowcode");
		String type = request.getParameter("type");
		String dentalJaw = request.getParameter("dentalJaw");		
		String articleType = request.getParameter("articleType");		
		String flowType = request.getParameter("flowType");		
		String id = request.getParameter("id");	//临床路径信息表id	
		LcljOrderTrack lcljOrderTrack = flowService.findLcljOrderTrsackByseqId(id);
		lcljOrderTrack.setPlantSystem(sbplantSystem.toString());
		/*lcljOrderTrack.setCrownMaterial(sbcrownMaterial.toString());*/
		lcljOrderTrack.setFlowcode(flowCode);
		lcljOrderTrack.setDentalJaw(dentalJaw);
		lcljOrderTrack.setArticleType(articleType);
		lcljOrderTrack.setType(flowType);
		Map<String,String> dataMap = new HashMap<String,String>();
		try {
			//保存临床跟踪数据
//			if(isShouShu.equals("SHOUSHU")) { //如果是手术治疗节点则更新牙位信息
//				dataMap.put("orderNumber",orderNumber);
			/*if (dzblService.findCaseHistoryById(id) == null) {
				throw new Exception("主诉及既往病史还没有填写，请填写完再进行提交！");
			} else if (zzblCheckService.findZzblOprationById(id) == null) {
				throw new Exception("检查及诊断还没有填写，请填写完再进行提交！");
			} else if (zzblService.findZzblOprationById(id) == null) {
				throw new Exception("诊疗方案还没有填写，请填写完再进行提交！");
			} else if (dzblService.findFamiliarBook(id) == null) {
				throw new Exception("知情同意书还没有填写，请填写完再进行提交！");
			} else if (rscService.findRepairInforById(id) == null) {
				throw new Exception("修复方案确认单还没有填写，请填写完再进行提交！");
			} else {
			}*/
			if(dzblService.findCaseHistoryById(id).size()>0){
				if(zzblCheckService.findZzblOprationById(id).size()>0){
					if(zzblService.findZzblOprationById(id).size()>0){
						if(rscService.findRepairInforById(id).size()>0){
							if(dzblService.findFamiliarBook(id) != null || dzblService.findLocatorFamiliares(id).size()>0){
								flowService.updateOrderTrackNodes(dataMap,flowCode,type,dentalJaw,lcljOrderTrack,request);
								YZUtility.DEAL_SUCCESS(null,null, response, logger);
							}else{
								throw new Exception("知情同意书还没有填写，请填写完再进行提交！");
							}
						}else{
							throw new Exception("修复方案确认单还没有填写，请填写完再进行提交！");
						}
					}else{
						throw new Exception("诊疗方案还没有填写，请填写完再进行提交！");
					}
				}else{
					throw new Exception("检查及诊断还没有填写，请填写完再进行提交！");
				}
			}else{
				throw new Exception("主诉及既往病史还没有填写，请填写完再进行提交！");
			}
//				flowService.updateOrderTrackNodes(dataMap,flowCode,type,dentalJaw,lcljOrderTrack,request);
//			}
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * 更改临床路径isobsolete状态，判断是否作废该条数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/updateLcljOrderTrackIsobsolete.act")
	public String updateLcljOrderTrackIsobsolete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if(YZUtility.isNullorEmpty(id)) {
			throw new Exception("获取临床路径数据失败");
		}
 		try {
			flowService.updateLcljOrderTrackIsobsolete(id, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id更新临床路径信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateLcljOrderTrackById.act")
	public String updateLcljOrderTrackById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String counsellor = request.getParameter("counsellor");
		String plant_physician = request.getParameter("plant_physician");
		String repair_physician = request.getParameter("repair_physician");
		String clinic_nurse = request.getParameter("clinic_nurse");
		String customer_service = request.getParameter("customer_service");
		String imageological_examination = request.getParameter("imageological_examination");
		String consultation = request.getParameter("consultation");
		String tooth_texture = request.getParameter("tooth_texture");
		String abutment_station = request.getParameter("abutment_station");
		String remark = request.getParameter("remark");
		String advisory = request.getParameter("advisory");
		LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
		lcljOrderTrack.setId(id);
		lcljOrderTrack.setCounsellor(counsellor);
		lcljOrderTrack.setPlantPhysician(plant_physician);
		lcljOrderTrack.setRepairPphysician(repair_physician);
		lcljOrderTrack.setClinicNurse(clinic_nurse);
		lcljOrderTrack.setCustomerService(customer_service);
		lcljOrderTrack.setImageologicalExamination(imageological_examination);
		lcljOrderTrack.setConsultation(consultation);
		lcljOrderTrack.setRemark(remark);
		lcljOrderTrack.setAbutment_station(abutment_station);
		lcljOrderTrack.setTooth_texture(tooth_texture);
		lcljOrderTrack.setAdvisory(advisory);
		try {
			/*if (dzblService.findCaseHistoryById(id) == null) {
				throw new Exception("主诉及既往病史还没有填写，请填写完再进行提交！");
			} else if (zzblCheckService.findZzblOprationById(id) == null) {
				throw new Exception("检查及诊断还没有填写，请填写完再进行提交！");
			} else if (zzblService.findZzblOprationById(id) == null) {
				throw new Exception("诊疗方案还没有填写，请填写完再进行提交！");
			} else if (dzblService.findFamiliarBook(id) != null ) {
				throw new Exception("知情同意书还没有填写，请填写完再进行提交！");
			} else if (rscService.findRepairInforById(id) == null) {
				throw new Exception("修复方案确认单还没有填写，请填写完再进行提交！");
			} else {
				flowService.updateLcljOrderTrackById(lcljOrderTrack);
			}*/
			if(dzblService.findCaseHistoryById(id).size()>0){
				if(zzblCheckService.findZzblOprationById(id).size()>0){
					if(zzblService.findZzblOprationById(id).size()>0){
						if(rscService.findRepairInforById(id).size()>0){
							if(dzblService.findFamiliarBook(id) != null || dzblService.findLocatorFamiliares(id).size()>0){
								flowService.updateLcljOrderTrackById(lcljOrderTrack);
								YZUtility.DEAL_SUCCESS(null, null, response, logger);
							}else{
								throw new Exception("知情同意书还没有填写，请填写完再进行提交！");
							}
						}else{
							throw new Exception("修复方案确认单还没有填写，请填写完再进行提交！");
						}
					}else{
						throw new Exception("诊疗方案还没有填写，请填写完再进行提交！");
					}
				}else{
					throw new Exception("检查及诊断还没有填写，请填写完再进行提交！");
				}
			}else{
				throw new Exception("主诉及既往病史还没有填写，请填写完再进行提交！");
			}
//			flowService.updateLcljOrderTrackById(lcljOrderTrack);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	@RequestMapping(value = "/updateLcljOrderTrackCounsellorById.act")
	public String updateLcljOrderTrackCounsellorById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String counsellor = request.getParameter("counsellor");
		String plant_physician = request.getParameter("plant_physician");
		String repair_physician = request.getParameter("repair_physician");
		String clinic_nurse = request.getParameter("clinic_nurse");
		String customer_service = request.getParameter("customer_service");
		String imageological_examination = request.getParameter("imageological_examination");
		String tooth_texture = request.getParameter("tooth_texture");
		/*String consultation = request.getParameter("consultation");
		
		String abutment_station = request.getParameter("abutment_station");*/
		String remark = request.getParameter("remark");
		//String advisory = request.getParameter("advisory");
		LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
		lcljOrderTrack.setId(id);
		lcljOrderTrack.setCounsellor(counsellor);
		lcljOrderTrack.setPlantPhysician(plant_physician);
		lcljOrderTrack.setRepairPphysician(repair_physician);
		lcljOrderTrack.setClinicNurse(clinic_nurse);
		lcljOrderTrack.setCustomerService(customer_service);
		lcljOrderTrack.setRemark(remark);
		if(imageological_examination!=null && !imageological_examination.equals("")){
			lcljOrderTrack.setImageologicalExamination(imageological_examination);
		}
		if(tooth_texture!=null && !tooth_texture.equals("")){
			lcljOrderTrack.setTooth_texture(tooth_texture);
		}
		try {
			flowService.updateLcljOrderTrackById(lcljOrderTrack);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}

	/**
	  * @Title: editToothBit   
	  * @Description: TODO(临床路径编辑牙位图)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年7月25日 下午2:44:05
	 */
	@RequestMapping("/editToothBit.act")
	public String editToothBit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String lcljId = request.getParameter("lcljId");
		String left_up = request.getParameter("left_up");
		String left_down = request.getParameter("left_down");
		String right_up = request.getParameter("right_up");
		String right_down = request.getParameter("right_down");
		String repair_left_up = request.getParameter("repair_left_up");
		String repair_left_down = request.getParameter("repair_left_down");
		String repair_right_up = request.getParameter("repair_right_up");
		String repair_right_down = request.getParameter("repair_right_down");
		
		LcljOrderTrack lTrack = new LcljOrderTrack();
		if(lcljId !=null && !lcljId.equals("")){
			lTrack.setId(lcljId);
		}
		if(left_up != null && !left_up.equals("")){
				lTrack.setLeftUp(left_up);
		}
		if(left_down!=null && !left_down.equals("")){
			lTrack.setLeftDown(left_down);
		}
		if(right_up != null && !right_up.equals("")){
			lTrack.setRightUp(right_up);
		}
		if(right_down != null && !right_down.equals("")){
			lTrack.setRightDown(right_down);
		}
		if(repair_left_up != null && !repair_left_up.equals("")){
			lTrack.setRepairLeftUp(repair_left_up);
		}
		if(repair_left_down!=null && !repair_left_down.equals("")){
			lTrack.setRepairLeftDown(repair_left_down);
		}
		if(repair_right_up != null && !repair_right_up.equals("")){
			lTrack.setRepairRightUp(repair_right_up);
		}
		if(repair_right_down != null && !repair_right_down.equals("")){
			lTrack.setRepairRightDown(repair_right_down);
		}
		try {
			Integer data = flowService.editToothBit(lTrack);
			if(data>0){	
				String id = YZUtility.getUUID();
				Date date = new Date();
				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
				OperatingRecord oRecord = new OperatingRecord();
				oRecord.setId(id);
				oRecord.setLcljId(lcljId);
				oRecord.setName(request.getParameter("person"));
				oRecord.setCreateTime(dateFormat.format(date));
				flowService.saveOperatingRecord(oRecord);
				YZUtility.DEAL_SUCCESS(null, "临床路径牙位图修改成功!", response, logger);
			}else{
				YZUtility.DEAL_SUCCESS(null, "临床路径牙位图修改失败!", response, logger);
			}
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping("/findPatientInformation.act")
	public String findPatientInformation(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		try {
			String usercode=request.getParameter("usercode");
			String status=request.getParameter("status");
			String id=request.getParameter("id");
			String order_number=request.getParameter("order_number");
		    JSONObject jo = flowService.findPatientInformation(usercode,status,id,order_number);
			YZUtility.DEAL_SUCCESS(jo,null, response, logger);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	@RequestMapping("/findNodeName.act")
	public String findNodeName(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		try {
		    List<LcljNodeConfig> list = flowService.findNodeName();
			YZUtility.RETURN_LIST(list, response, logger);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
