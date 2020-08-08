/**
 * @Title: MedicalRecordsAct.java
 * @Package com.hudh.lclj.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 海德堡联合空腔
 * @date: 2020年5月27日 下午3:59:18
 * @version V1.0
 */
package com.hudh.lclj.controller;

import com.hudh.lclj.entity.*;
import com.hudh.lclj.service.impl.MedicalRecordsLogic;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MedicalRecordsAct
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 海德堡联合口腔
 * @date: 2020年5月27日 下午3:59:18
 */
@Controller
@RequestMapping("HUDH_MedicalRecordsAct")
public class HUDH_MedicalRecordsAct {

    private Logger logger = LoggerFactory.getLogger(HUDH_MedicalRecordsAct.class);

    @Autowired
    private MedicalRecordsLogic mlogic;

    /**
     * @Title: installData
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: String
     * @dateTime:2020年5月30日 下午3:08:59
     */
    @RequestMapping("/installData.act")
    public String installData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);
            String organization = ChainUtil.getCurrentOrganization(request);
            HudhSpecialitycheck dp = new HudhSpecialitycheck();
            BeanUtils.populate(dp, request.getParameterMap());
            if (dp.getSeqId() != null && !"".equals(dp.getSeqId())) {
                mlogic.updateDate(dp);
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.HUDH_SpecialityCheck, dp, dp.getUserId(),
                        TableNameUtil.HUDH_SpecialityCheck, request);
            } else {
                dp.setSeqId(YZUtility.getUUID());
                dp.setCreateuser(person.getSeqId());
                dp.setCreatetime(YZUtility.getCurDateTimeStr());
                dp.setOrganization(organization);
                dp.setStatus("0");
                mlogic.installData(dp);
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_SpecialityCheck, dp, dp.getUserId(),
                        TableNameUtil.HUDH_SpecialityCheck, request);
            }
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * @Title: selectdata
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: String
     * @dateTime:2020年5月30日 下午3:08:50
     */
    @RequestMapping("/selectdata.act")
    public String selectdata(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String lcljId = request.getParameter("lcljId");
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("lcljId", lcljId);
            List<JSONObject> list = mlogic.selectdata(map);
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * @Title: insertRecord
     * @Description: TODO(存入手术记录数据)
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: String
     * @dateTime:2020年5月30日 下午3:08:45
     */
    @RequestMapping("/insertRecord.act")
    public String insertRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);
            String organization = ChainUtil.getCurrentOrganization(request);
            HudhOperationNote dp = new HudhOperationNote();
            BeanUtils.populate(dp, request.getParameterMap());
            if (dp.getSeqId() != null && dp.getSeqId() != "") {
                mlogic.updateRecord(dp);
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.HUDH_operationNote, dp, dp.getUserid(),
                        TableNameUtil.HUDH_operationNote, request);
            } else {
                dp.setSeqId(YZUtility.getUUID());
                dp.setCreateuser(person.getSeqId());
                dp.setCreatetime(YZUtility.getCurDateTimeStr());
                dp.setOrganization(organization);
                mlogic.insertRecord(dp);
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_operationNote, dp, dp.getUserid(),
                        TableNameUtil.HUDH_operationNote, request);
            }
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * @Title: selectRecord
     * @Description: TODO(查询手术记录)
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @param: @throws Exception
     * @return: String
     * @dateTime:2020年5月30日 下午3:08:39
     */
    @RequestMapping("/selectRecord.act")
    public String selectRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String lcljId = request.getParameter("lcljId");
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("lcljId", lcljId);
            List<JSONObject> list = mlogic.selectRecord(map);
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * @throws Exception
     * @Title: installOrUpdate
     * @Description: TODO(录入或更改治疗记录)
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @param: @throws IllegalAccessException
     * @param: @throws InvocationTargetException
     * @return: String
     * @dateTime:2020年5月30日 下午3:08:34
     */
    @RequestMapping("/installOrUpdate.act")
    public String installOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        YZPerson person = SessionUtil.getLoginPerson(request);
        String organization = ChainUtil.getCurrentOrganization(request);
        try {
            HudhAcography dp = new HudhAcography();
            BeanUtils.populate(dp, request.getParameterMap());
            if (dp.getSeqId() != null && !"".equals(dp.getSeqId())) {
                mlogic.update(dp);
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE, BcjlUtil.HUDH_Acography, dp, dp.getUserId(),
                        TableNameUtil.HUDH_Acography, request);
            } else {
                dp.setSeqId(YZUtility.getUUID());
                dp.setCreateuser(person.getSeqId());
                dp.setCreatetime(YZUtility.getCurDateTimeStr());
                dp.setOrganization(organization);
                mlogic.insert(dp);
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_Acography, dp, dp.getUserId(),
                        TableNameUtil.HUDH_Acography, request);
            }
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * @throws Exception
     * @Title: selectAcography
     * @Description: TODO(列出治疗记录)
     * @param: @return
     * @return: String
     * @dateTime:2020年5月30日 下午3:08:23
     */
    @RequestMapping("/selectAcography.act")
    public String selectAcography(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String lcljId = request.getParameter("lcljId");
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("lcljId", lcljId);
            List<JSONObject> list = mlogic.selectAcography(map);
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * @param request
     * @param response
     * @return
     * @Description: TODO(填写 / 修改手术核查单)
     * @dateTime:2020年6月30日 下午14:12:23
     */
    @RequestMapping("/SaveVerification.act")
    public String saveVerification(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);
            String organization = ChainUtil.getCurrentOrganization(request);
            LcljVerification dp = new LcljVerification();
            BeanUtils.populate(dp, request.getParameterMap());
            if (dp.getSeqId() != null && !"".equals(dp.getSeqId())) {
                mlogic.updateVerification(dp);
                YZUtility.DEAL_SUCCESS(null, "", response, logger);
            } else {
                dp.setSeqId(YZUtility.getUUID());
                dp.setCreateuser(person.getSeqId());
                dp.setCreatetime(YZUtility.getCurDateTimeStr());
                dp.setOrganization(organization);
                mlogic.SaveVerification(dp);
                YZUtility.DEAL_SUCCESS(null, "", response, logger);
            }
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR("", false, e, response, logger);
        }
        return null;
    }

    /**
     * @param response
     * @Title: findVerification
     * @Description: [request, response](查询患者术前核查单)
     * @author admin
     * @param: * @param request
     * @return: java.lang.String
     * @dateTime: 2020/6/12 17:24
     */
    @RequestMapping("/findVerification.act")
    public String findVerification(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        try {
            List<JSONObject> list = mlogic.findVerification(id);
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR("", false, e, response, logger);
        }
        return null;
    }


    /**
     * @param response
     * @Title: insertFamiliar
     * @Description: [request, response](种植术后知情书签字确认单)
     * @author admin
     * @param: * @param request
     * @return: java.lang.String
     * @dateTime: 2020/6/15 15:20
     */
    @RequestMapping("/insertFamiliar.act")
    public String insertFamiliar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);
            String organization = ChainUtil.getCurrentOrganization(request);
            LcljFamiliar dp = new LcljFamiliar();
            BeanUtils.populate(dp, request.getParameterMap());
            dp.setSeqId(YZUtility.getUUID());
            dp.setCreateUser(person.getSeqId());
            dp.setCreateTime(YZUtility.getCurDateTimeStr());
            dp.setOrganization(organization);
            mlogic.insertFamiliar(dp);
            YZUtility.DEAL_SUCCESS(null,"",response,logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR("", false, e, response, logger);
        }
        return null;
    }

    /**
     * @param response
     * @Title: findFamiliar
     * @Description: [request, response](种植牙术后知情书签字展示)
     * @author admin
     * @param: * @param request
     * @return: java.lang.String
     * @dateTime: 2020/6/15 15:47
     */
    @RequestMapping("/findFamiliar.act")
    public String findFamiliar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String id = request.getParameter("id");
            JSONObject list = mlogic.findFamiliar(id);
            YZUtility.DEAL_SUCCESS(list, "", response, logger);
        } catch (IOException e) {
            YZUtility.DEAL_ERROR("", false, e, response, logger);
        }
        return null;
    }
}
