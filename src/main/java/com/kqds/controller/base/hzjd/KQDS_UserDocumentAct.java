package com.kqds.controller.base.hzjd;

import com.hudh.util.HUDHUtil;
import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.entity.base.*;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderPriceList.KQDS_CostOrderPriceListLogic;
import com.kqds.service.base.hzLabel.KQDS_HzLabelAssociatedLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.code.UserCodeUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.sys.SysParaUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("KQDS_UserDocumentAct")
public class KQDS_UserDocumentAct {

    private Logger logger = LoggerFactory.getLogger(KQDS_UserDocumentAct.class);
    @Autowired
    private KQDS_UserDocumentLogic logic;
    @Autowired
    private KQDS_CostOrderPriceListLogic priceListLogic;
    @Autowired
    private KQDS_HzLabelAssociatedLogic hzLabelAssociatedLogic;
    @Autowired
    private YZPersonLogic personLogic;
    @Autowired
    private YZDictLogic dictLogic;
    @Autowired
    private KQDS_hz_labelLogic labelLogic;

    @RequestMapping(value = "/toCloudsTagsAdd.act")
    public ModelAndView toCloudsTagsAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/index/kfzx/user_manager_kfzx.jsp");
        return mv;
    }

    @RequestMapping(value = "/toUserManagerKfzx.act")
    public ModelAndView toUserManagerKfzx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/index/kfzx/user_manager_kfzx.jsp");
        return mv;
    }

    @RequestMapping(value = "/toUserManagerYxzx.act")
    public ModelAndView toUserManagerYxzx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/index/kfzx/user_manager_yxzx.jsp");
        return mv;
    }

    @RequestMapping(value = "/toUserManagerWdzx.act")
    public ModelAndView toUserManagerWdzx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/index/kfzx/user_manager_wdzx.jsp");
        return mv;
    }

    @RequestMapping(value = "/toXxbbCenter.act")
    public ModelAndView toXxbbCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/index/jdzx/xxbb_center.jsp");
        return mv;
    }

    @RequestMapping(value = "/toUserManagerJq.act")
    public ModelAndView toUserManagerJq(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/index/kfzx/user_manager_jq.jsp");
        return mv;
    }

    @RequestMapping(value = "/toFirst_Center.act")
    public ModelAndView toFirst_Center(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String menuId = request.getParameter("menuId");
        ModelAndView mv = new ModelAndView();
        mv.addObject("menuId", menuId);
        mv.setViewName("/kqdsFront/index/first_center.jsp");
        return mv;
    }

    @RequestMapping(value = "/toWdAddWin.act")
    public ModelAndView toWdAddWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/reg/wd/wdAddWin.jsp");
        return mv;
    }

    @RequestMapping(value = "/toWdWin.act")
    public ModelAndView toWdWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/reg/wd/wdWin.jsp");
        return mv;
    }

    @RequestMapping(value = "/toZzWinPage.act")
    public ModelAndView toZzWinPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/reg/zz/zzWin.jsp");
        return mv;
    }

    @RequestMapping(value = "/toZzAddWin.act")
    public ModelAndView toZzAddWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/reg/zz/zzAddWin.jsp");
        return mv;
    }

    @RequestMapping(value = "/toZzEditDoctor.act")
    public ModelAndView toZzEditDoctor(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/reg/zzDoctor/zz_edit_doctor.jsp");
        return mv;
    }

    @RequestMapping(value = "/toZzDoctorWin.act")
    public ModelAndView toZzDoctorWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/reg/zzDoctor/zzDoctorWin.jsp");
        return mv;
    }

    @RequestMapping(value = "/toGrxx.act")
    public ModelAndView toGrxx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/hzjd/grxx.jsp");
        return mv;
    }

    /**
     * @Title: toRegitGrxx
     * @Description: TODO(跳转右侧个人信息)
     * @param: @param
     * request
     * @param: @param
     * response
     * @param: @return
     * @param: @throws
     * Exception
     * @return: ModelAndView
     * @dateTime:2019年8月24日 下午6:56:20
     */
    @RequestMapping(value = "/toRightGrxx.act")
    public ModelAndView toRightGrxx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/hzjd/right_grxx.jsp");
        return mv;
    }

    /**
     * @Title: toOpenPatientTag
     * @Description: TODO(跳转新增标签)
     * @param: @param
     * request
     * @param: @param
     * response
     * @param: @return
     * @param: @throws
     * Exception
     * @return: ModelAndView
     * @dateTime:2019年8月24日 下午8:13:55
     */
    @RequestMapping(value = "/toOpenPatientTag.act")
    public ModelAndView toOpenPatientTag(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        String frameSelfindex = request.getParameter("frameSelfindex");
        mv.addObject("frameSelfindex", frameSelfindex);
        mv.setViewName("/kqdsFront/index/patientTags2.jsp");
        return mv;
    }

    /**
     * @Title: toOpenPatientTag
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param
     * request
     * @param: @param
     * response
     * @param: @return
     * @param: @throws
     * Exception
     * @return: ModelAndView
     * @dateTime:2019年8月24日 下午8:14:16
     */
    @RequestMapping(value = "/toOpenPatientTagAdd.act")
    public ModelAndView toOpenPatientTagAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/index/patientTags.jsp");
        return mv;
    }

    @RequestMapping(value = "/toUserCenter.act")
    public ModelAndView toUserCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/index/userCenter.jsp");
        return mv;
    }

    @RequestMapping(value = "/toUserList.act")
    public ModelAndView toUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String typechoose = request.getParameter("typechoose");
        ModelAndView mv = new ModelAndView();
        mv.addObject("typechoose", typechoose);
        mv.setViewName("/kqdsFront/hzjd/user_list.jsp");
        return mv;
    }

    @RequestMapping(value = "/toDahb.act")
    public ModelAndView toDahb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/hzjd/dahb.jsp");
        return mv;
    }

    @RequestMapping(value = "/toZzIndex.act")
    public ModelAndView toZzIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/reg/zz/zz_index.jsp");
        return mv;
    }

    @RequestMapping(value = "/toZzWin.act")
    public ModelAndView toZzWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/reg/zz/zzAddAllWin.jsp");
        return mv;
    }

    @RequestMapping(value = "/toHzjd_Xxbb.act")
    public ModelAndView toHzjd_Xxbb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/hzjd/hzjd_xxbb.jsp");
        return mv;
    }

    @RequestMapping(value = "/toHzjd_Edit.act")
    public ModelAndView toHzjd_Edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        String menuid = request.getParameter("menuid");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.addObject("menuid", menuid);
        mv.setViewName("/kqdsFront/hzjd/hzjd_edit.jsp");
        return mv;
    }

    @RequestMapping(value = "/toHzjd_Net_Edit.act")
    public ModelAndView toHzjd_Net_Edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/hzjd/hzjd_net_edit.jsp");
        return mv;
    }

    @RequestMapping(value = "/toHzjd.act")
    public ModelAndView toHzjd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/hzjd/hzjd.jsp");
        return mv;
    }

    @RequestMapping(value = "/toHzjd_zxzl.act")
    public ModelAndView toHzjd_zxzl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/hzjd/hzjd_zxzl.jsp");
        return mv;
    }

    @RequestMapping(value = "/toHzjd_Net.act")
    public ModelAndView toHzjd_Net(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        ModelAndView mv = new ModelAndView();
        mv.addObject("usercode", usercode);
        mv.setViewName("/kqdsFront/hzjd/hzjd_net.jsp");
        return mv;
    }

    @RequestMapping(value = "/toXxcxCenter.act")
    public ModelAndView toXxcxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String menuId = request.getParameter("menuId");
        ModelAndView mv = new ModelAndView();
        mv.addObject("menuId", menuId);
        mv.setViewName("/kqdsFront/index/jdzx/xxcx_center.jsp");
        return mv;
    }

    @RequestMapping(value = "/toWdIndex.act")
    public ModelAndView toWdIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/reg/wd/wd_index.jsp");
        return mv;
    }

    @RequestMapping(value = "/toVideo_Yxzl.act")
    public ModelAndView toVideo_Yxzl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");
        ModelAndView mv = new ModelAndView();
        mv.addObject("type", type);
        mv.setViewName("/kqdsFront/video/video_yxzl.jsp");
        return mv;
    }

    @RequestMapping(value = "/toHuizheng_Info.act")
    public ModelAndView toHuizheng_Info(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/huizhen/huizheng_info.jsp");
        return mv;
    }

    @RequestMapping(value = "/toMedicalrecord.act")
    public ModelAndView toMedicalrecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String blflag = request.getParameter("blflag");
        ModelAndView mv = new ModelAndView();
        mv.addObject("blflag", blflag);
        mv.setViewName("/kqdsFront/medicalRecord/medicalrecord.jsp");
        return mv;
    }

    @RequestMapping(value = "/toJg_List.act")
    public ModelAndView toJg_List(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/jiagong/jg_list.jsp");
        return mv;
    }

    @RequestMapping(value = "/toSms_Usercode.act")
    public ModelAndView toSms_Usercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/sms/sms/sms_usercode.jsp");
        return mv;
    }

    @RequestMapping(value = "/toCs.act")
    public ModelAndView toCs(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/lzjl/cs.jsp");
        return mv;
    }

    @RequestMapping(value = "/toYyzxMz.act")
    public ModelAndView toYyzxMz(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/yyzx/yyzxMz.jsp");
        return mv;
    }

    @RequestMapping(value = "/toReceive.act")
    public ModelAndView toReceive(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/zxjl/receive.jsp");
        return mv;
    }

    @RequestMapping(value = "/toTemplate.act")
    public ModelAndView toTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/zxjl/remarkTemplate.jsp");
        return mv;
    }

    @RequestMapping(value = "/toZengsong_List.act")
    public ModelAndView toZengsong_List(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/member/zengsong_list.jsp");
        return mv;
    }

    @RequestMapping(value = "/toGrxxList4dj.act")
    public ModelAndView toGrxxList4dj(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/soundRecord/grxxlist4dj.jsp");
        return mv;
    }

    /**
     *跳转介绍人页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toBbIntroducer.act")
    public ModelAndView toBbIntroducer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/index/bbzx/bb_introducer.jsp");
        return mv;
    }

    /**
     * 验证病历号是否已存在
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkBlcode.act")
    public String checkBlcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean result = true;
        String blcode = request.getParameter("blcode");
        String seqId = request.getParameter("seqId");
        try {
            int num = logic.checkBlcode(seqId, blcode, TableNameUtil.KQDS_USERDOCUMENT);
            if (num > 0) {
                result = false;
            }
            YZUtility.DEAL_SUCCESS_VALID(result, response);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    @RequestMapping(value = "/getSingUserByPhoneNumber.act")
    public String getSingUserByPhoneNumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String phonenumber = request.getParameter("phonenumber");

            if (YZUtility.isNullorEmpty(phonenumber)) {
                throw new Exception("phonenumber不能为空");
            }

            KqdsUserdocument doc = logic.getSingUserByPhoneNumber(phonenumber);

            if (doc == null) {
                throw new Exception("患者不存在，手机号码为：" + phonenumber);
            }

            YZUtility.DEAL_SUCCESS(JSONObject.fromObject(doc), null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 设定客服
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setKeFu.act")
    public String setKeFu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);

            // 保存的对象集合 json格式
            String listdata = request.getParameter("data");
            JSONArray jArray = JSONArray.fromObject(listdata);
            for (Object obj : jArray) {
                JSONObject job = (JSONObject) obj;
                String seqId = job.getString("seqId");
                String kefu = job.getString("kefu");// 客服人员
                String kefuremark = job.getString("kefuremark");// 客服备注
                KqdsUserdocument user = (KqdsUserdocument) logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT,
                        seqId);
                if (user != null) {
                    // 保存记录日志
                    KqdsChangeKefu wd = new KqdsChangeKefu();
                    wd.setSeqId(YZUtility.getUUID());
                    wd.setCreatetime(YZUtility.getCurDateTimeStr());
                    wd.setCreateuser(person.getSeqId());
                    if (!YZUtility.isNullorEmpty(user.getKefu())) {
                        wd.setOldper(user.getKefu());
                    }
                    wd.setToper(kefu);
                    wd.setRemark(kefuremark);
                    wd.setUsercode(user.getUsercode());
                    wd.setUsername(user.getUsername());
                    wd.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
                    user.setKefu(kefu);
                    logic.setKeFu(wd, user);
                    // 记录日志
                    BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_KEFU, wd, wd.getUsercode(),
                            TableNameUtil.KQDS_CHANGE_KEFU, request);
                }
            }
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, true, ex, response, logger);
        }
        return null;
    }

    /**
     * 单个设定客服
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setKF.act")
    public String setKF(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        String kefu = request.getParameter("kefu");
        String kefuremark = request.getParameter("kefuremark");

        try {
            YZPerson person = SessionUtil.getLoginPerson(request);
            JSONObject json = logic.findByUsercode(usercode);
            KqdsUserdocument user = new KqdsUserdocument();
            if (json != null) {
                // 保存记录日志
                KqdsChangeKefu wd = new KqdsChangeKefu();
                wd.setSeqId(YZUtility.getUUID());
                wd.setCreatetime(YZUtility.getCurDateTimeStr());
                wd.setCreateuser(person.getSeqId());
                if (!YZUtility.isNullorEmpty(json.getString("kefu"))) {
                    wd.setOldper(json.getString("kefu"));
                }
                wd.setToper(kefu);
                wd.setRemark(kefuremark);
                wd.setUsercode(json.getString("usercode"));
                wd.setUsername(json.getString("username"));
                wd.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
                user.setKefu(kefu);
                user.setUsercode(json.getString("usercode"));
                logic.setKF(wd, user);
                // 记录日志
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_KEFU, wd, wd.getUsercode(),
                        TableNameUtil.KQDS_CHANGE_KEFU, request);
            }

            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (
                Exception ex) {
            YZUtility.DEAL_ERROR(null, true, ex, response, logger);
        }
        return null;
    }

    /**
     * 设定建档人
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setInputtingPerson.act")
    public String setInputtingPerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);

            // 保存的对象集合 json格式
            String listdata = request.getParameter("data");
            JSONArray jArray = JSONArray.fromObject(listdata);
            for (Object obj : jArray) {
                JSONObject job = (JSONObject) obj;
                String seqId = job.getString("seqId");
                String createuser = job.getString("createuser");// 客服人员
                String jdRremark = job.getString("jdRremark");// 客服备注
                KqdsUserdocument user = (KqdsUserdocument) logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT,
                        seqId);
                if (user != null) {
                    // 保存记录日志
                    KqdsJdrchange wd = new KqdsJdrchange();
                    wd.setSeqId(YZUtility.getUUID());
                    wd.setCreatetime(YZUtility.getCurDateTimeStr());
                    wd.setCreateuser(person.getSeqId());
                    if (!YZUtility.isNullorEmpty(user.getCreateuser())) {
                        wd.setOldper(user.getCreateuser());
                    }
                    wd.setToper(createuser);
                    wd.setRemark(jdRremark);
                    wd.setUsercode(user.getUsercode());
                    wd.setUsername(user.getUsername());
                    wd.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
                    user.setCreateuser(createuser);
                    logic.setInputtingPerson(wd, user);
                    // 记录日志
                    BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_JDR, wd, wd.getUsercode(),
                            TableNameUtil.KQDS_CHANGE_JDR, request);
                }
            }
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, true, ex, response, logger);
        }
        return null;
    }

    /**
     * 新增或修改或者信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("null")
    @RequestMapping(value = "/insert.act")
    public String insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String exploit = request.getParameter("exploit");
            String exploit1 = request.getParameter("exploit1");
            String exploitId = request.getParameter("exploitId");
            String exploitId1 = request.getParameter("exploitId1");
            String labelAllArr = request.getParameter("labelAllArr");
            String organization = request.getParameter("organization");
            if (YZUtility.isNullorEmpty(organization)) {
                organization = ChainUtil.getCurrentOrganization(request);
            }
            String nation = request.getParameter("nation");
            String certOrg = request.getParameter("certOrg");
            String effDate = request.getParameter("effDate");
            String expDate = request.getParameter("expDate");
            String headPic = request.getParameter("headPic");
            String photoDisplay = request.getParameter("photoDisplay");
            String introduce = request.getParameter("introduce");
            List<kqdsHzLabellabeAndPatient> labelAllList = null;
            if (labelAllArr != null) {
                labelAllArr = java.net.URLDecoder.decode(labelAllArr, "UTF-8");
                labelAllList = HUDHUtil.parseJsonToObjectList(labelAllArr, kqdsHzLabellabeAndPatient.class);
            }

            YZPerson person = SessionUtil.getLoginPerson(request);
            KqdsUserdocument dp = new KqdsUserdocument();
            KqdsNetOrder netorder = new KqdsNetOrder();
            BeanUtils.populate(dp, request.getParameterMap());
            BeanUtils.populate(netorder, request.getParameterMap());
            String[] hobby = request.getParameterValues("hobby");
            if (hobby != null) {
                StringBuffer sbHobby = new StringBuffer();
                for (int i = 0; i < hobby.length / 2; i++) {// 拼接为字符串
                    sbHobby.append(hobby[i] + ";");
                }
                dp.setHobby(sbHobby.toString());
            }
            // 空值判断
            if (YZUtility.isNullorEmpty(dp.getPhonenumber1()) && YZUtility.isNullorEmpty(dp.getPhonenumber2())) {
                throw new Exception("建档时，手机号码不能都为空值！");
            }
            // 都有值时判断
            if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber1()) && YZUtility.isNotNullOrEmpty(dp.getPhonenumber2())) {
                if (dp.getPhonenumber1().equals(dp.getPhonenumber2())) {
                    throw new Exception("建档时，两个手机号码值不能一样！");
                }
            }
            String seqId = request.getParameter("seqId");
            String name = "";
            String createtime = "";
            if (!YZUtility.isNullorEmpty(seqId)) {
                if (YZUtility.isNotNullOrEmpty(dp.getBlcode())) {
                    int blcount = logic.checkBlcode(seqId, dp.getBlcode(), TableNameUtil.KQDS_USERDOCUMENT);
                    if (blcount > 0) {
                        throw new Exception("病历号重复，请重新填写");
                    }
                }

                KqdsUserdocument en = (KqdsUserdocument) logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT,
                        seqId);
                if (!YZUtility.isNullorEmpty(dp.getNexttype())) {
                    if (dp.getNexttype().equals("请选择")) {
                        dp.setNexttype("");
                    }
                }
                if (!en.getCreateuser().equals(dp.getCreateuser())) { // 如果修改了建档人，要加日志

                    YZPerson createPerson = (YZPerson) logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON,
                            dp.getCreateuser());
                    YZDept dept = (YZDept) logic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, createPerson.getDeptId());
                    if (ConstUtil.DEPT_TYPE_2.equals(dept.getDeptType())
                            || ConstUtil.DEPT_TYPE_3.equals(dept.getDeptType())) { // 网电或营销
                        dp.setType(1);// 网电建档
                    } else {
                        dp.setType(0); // 门诊建档
                    }

                    String logText = "系统用户" + person.getUserId() + "进行了建档人修改操作，原建档人" + en.getCreateuser() + "，现建档人："
                            + dp.getCreateuser() + "，患者编号：" + en.getUsercode() + "。";
                    BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY_JDR, BcjlUtil.KQDS_USERDOCUMENT, logText,
                            dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
                }

                dp.setDoorstatus(en.getDoorstatus());
                String pym = ChineseCharToEn.getAllFirstLetter(dp.getUsername());
                dp.setPym(pym);

                //身份证信息补充
                dp.setNation(nation);
                dp.setCertOrg(certOrg);
                dp.setEffDate(effDate);
                dp.setExpDate(expDate);
                dp.setHeadPic(headPic);
                dp.setPhotoDisplay(photoDisplay);
                dp.setIntroduce(introduce);
                logic.updateUserDoc(dp, en, netorder, person, request);

                if (labelAllList != null) {
                    logic.deleteLabel(dp.getUsercode());
                    kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
                    for (kqdsHzLabellabeAndPatient kPatien : labelAllList) {
                        String id = YZUtility.getUUID();
                        kPatient.setSeqId(id);
                        kPatient.setUserSeqId(dp.getSeqId());
                        kPatient.setUserId(dp.getUsercode());
                        kPatient.setUserName(dp.getUsername());
                        kPatient.setLabelOneId(kPatien.getLabelOneId());
                        kPatient.setLabelOneName(kPatien.getLabelOneName());
                        kPatient.setLabelTwoId(kPatien.getLabelTwoId());
                        kPatient.setLabelTwoName(kPatien.getLabelTwoName());
                        kPatient.setLabelThreeId(kPatien.getLabelThreeId());
                        if (kPatien.getLabelThreeId() == null) {
                            kPatien.setLabelThreeId("00165d45-650b-4394-9768-4482a0ca9b05");
                        }
                        kPatient.setOpinion(kPatien.getOpinion());
                        kPatient.setLabelThreeName(kPatien.getLabelThreeName());
                        kPatient.setCreateUser(person.getSeqId());
                        kPatient.setStatus(1);
                        kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                        kPatient.setOrganization(organization);
                        logic.saveKpatient(kPatient);
                        // if(!labelID.contains(kPatien.getLabelOneId())){
                        // labelID+=kPatien.getLabelOneId()+",";
                        // }
                        // if(!labelID.contains(kPatien.getLabelTwoId())){
                        // labelID+=kPatien.getLabelTwoId()+",";
                        // }
                        // if(!labelID.contains(kPatien.getLabelThreeId())){
                        // labelID+=kPatien.getLabelThreeId()+",";
                        // }
                        // if(labelName1.equals("")){
                        // labelName1=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                        // }else{
                        // if(labelName1.contains(kPatien.getLabelOneName())){
                        // if(labelName1.contains(kPatien.getLabelTwoName())){
                        // labelName1+=","+kPatien.getLabelThreeName();
                        // }else{
                        // labelName1+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                        // }
                        // }else{
                        // if(labelName2.equals("")){
                        // labelName2=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                        // }else{
                        // if(labelName2.contains(kPatien.getLabelOneName())){
                        // if(labelName2.contains(kPatien.getLabelTwoName())){
                        // labelName2+=","+kPatien.getLabelThreeName();
                        // }else{
                        // labelName2+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                        // }
                        // }else{
                        // if(labelName3.equals("")){
                        // labelName3=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                        // }else{
                        // if(labelName3.contains(kPatien.getLabelOneName())){
                        // if(labelName3.contains(kPatien.getLabelTwoName())){
                        // labelName3+=","+kPatien.getLabelThreeName();
                        // }else{
                        // labelName3+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                        // }
                        // }
                        // }
                        // }
                        // }
                        // }
                        // }
                    }
                    if (dp.getAge() != null && !dp.getAge().equals("")) {
                        int age = dp.getAge();
                        String threename = "";
                        if (age <= 30) {
                            threename = "30岁以下";
                        } else if (age >= 31 && age <= 40) {
                            threename = "31-40岁";
                        } else if (age >= 41 && age <= 50) {
                            threename = "41-50岁";
                        } else if (age >= 51 && age <= 60) {
                            threename = "51-60岁";
                        } else if (age >= 61 && age <= 70) {
                            threename = "61-70岁";
                        } else if (age >= 71 && age <= 80) {
                            threename = "71-80岁";
                        } else if (age >= 81) {
                            threename = "80岁以上";
                        }
                        // 根据三级名称查找三级id
                        String threeId = labelLogic.findKqdsHzLabelSeqIdByLeveLabel(threename);
                        String id = YZUtility.getUUID();
                        kPatient.setSeqId(id);
                        kPatient.setUserSeqId(dp.getSeqId());
                        kPatient.setUserId(dp.getUsercode());
                        kPatient.setUserName(dp.getUsername());
                        kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
                        kPatient.setLabelOneName("社会标签");
                        kPatient.setLabelTwoId("9f1cd4c9-2aea-4309-9556-7812b225e23f");
                        kPatient.setLabelTwoName("年龄");
                        kPatient.setLabelThreeId(threeId);
                        kPatient.setLabelThreeName(threename);
                        kPatient.setCreateUser(person.getSeqId());
                        kPatient.setStatus(1);
                        kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                        kPatient.setOrganization(organization);
                        logic.saveKpatient(kPatient);
                        // if(!labelID.contains("13543c4d-f81e-4251-87a1-f07984022e9f")){
                        // labelID+="13543c4d-f81e-4251-87a1-f07984022e9f,";
                        // }
                        // if(!labelID.contains("9f1cd4c9-2aea-4309-9556-7812b225e23f")){
                        // labelID+="9f1cd4c9-2aea-4309-9556-7812b225e23f,";
                        // }
                        // if(!labelID.contains(threeId)){
                        // labelID+=threeId+",";
                        // }
                        // if(labelName1.equals("")){
                        // labelName1="社会标签:年龄:"+threename;
                        // }else{
                        // if(labelName1.contains("社会标签")){
                        // if(labelName1.contains("年龄")){
                        // labelName1+=","+threename;
                        // }else{
                        // labelName1+=";年龄:"+threename;
                        // }
                        // }else{
                        // if(labelName2.equals("")){
                        // labelName2="社会标签:年龄:"+threename;
                        // }else{
                        // if(labelName2.contains("社会标签")){
                        // if(labelName2.contains("年龄")){
                        // labelName2+=","+threename;
                        // }else{
                        // labelName2+=";年龄:"+threename;
                        // }
                        // }else{
                        // if(labelName3.equals("")){
                        // labelName3="社会标签:年龄:"+threename;
                        // }else{
                        // if(labelName3.contains("社会标签")){
                        // if(labelName3.contains("年龄")){
                        // labelName3+=","+threename;
                        // }else{
                        // labelName3+=";年龄:"+threename;
                        // }
                        // }
                        // }
                        // }
                        // }
                        // }
                        // }
                    }

                    // 根据职业id在字典表查询职业名称
                    if (dp.getProfession() != null && !dp.getProfession().equals("")) {
                        String dictName = dictLogic.findDictNameBySeqId(dp.getProfession());
                        Map<String, String> map1 = new HashMap<String, String>();
                        if (dictName != null && !dictName.equals("")) {
                            map1.put("leveLabel", dictName);
                        }
                        map1.put("parentId", "62aa427d-b590-4077-8b7b-4195201ec758");
                        String seqid = labelLogic.selectKqdsHzLabelByLeveLabel(map1);
                        if (seqid == null || seqid.equals("")) {
                            // 新增标签
                            KqdsLabel kqdsHzLabel = new KqdsLabel();
                            seqid = YZUtility.getUUID();
                            kqdsHzLabel.setSeqId(seqid);
                            kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
                            kqdsHzLabel.setCreateUser(person.getSeqId());
                            kqdsHzLabel.setLeveLabel(dictName);
                            kqdsHzLabel.setParentId("62aa427d-b590-4077-8b7b-4195201ec758");
                            kqdsHzLabel.setParentName("职业");
                            kqdsHzLabel.setRemark("三级");
                            labelLogic.insertKqdsHzLabel(kqdsHzLabel);
                        }
                        String id = YZUtility.getUUID();
                        kPatient.setSeqId(id);
                        kPatient.setUserSeqId(dp.getSeqId());
                        kPatient.setUserId(dp.getUsercode());
                        kPatient.setUserName(dp.getUsername());
                        kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
                        kPatient.setLabelOneName("社会标签");
                        kPatient.setLabelTwoId("62aa427d-b590-4077-8b7b-4195201ec758");
                        kPatient.setLabelTwoName("职业");
                        kPatient.setLabelThreeId(seqid);
                        kPatient.setLabelThreeName(dictName);
                        kPatient.setCreateUser(person.getSeqId());
                        kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                        kPatient.setStatus(1);
                        kPatient.setOrganization(organization);
                        logic.saveKpatient(kPatient);
                    }
                }
                if (exploit != null && !exploit.equals("undefined") && exploitId != null) {
                    savePriceList(exploitId, exploit, dp.getUsername(), dp.getUsercode(), "1", person, response);
                }
                if (exploit1 != null && !exploit1.equals("undefined") && exploitId1 != null) {
                    savePriceList(exploitId1, exploit1, dp.getUsername(), dp.getUsercode(), "2", person, response);
                }
                // 记录日志
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(),
                        TableNameUtil.KQDS_USERDOCUMENT, request);
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, labelAllList,
                        dp.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
            } else {
                // ############### 【重要，后面的患者编号重复判断，有漏洞，在此修复】
                // 2018.3.11 改为如果开两个页面建档，编号重复，则直接获取新的usercode
                String usercode = dp.getUsercode();
                String jdOrganization = ChainUtil.getCurrentOrganization(request);
                if (1 == dp.getType()) {
                    jdOrganization = ChainUtil.getOrganizationFromUrl(request);
                }
                String realusercode = UserCodeUtil.getUserCode4Insert(jdOrganization, usercode);
                if (!usercode.equals(realusercode)) {
                    dp.setUsercode(realusercode);
                    logger.error("----页面患者编号：" + usercode + ",新编号：" + realusercode);
                }

                // 验证手机号码是否重复
                // if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber1())) {
                // Map<String, String> map = new HashMap<String, String>();
                // map.put("phonenumber1", dp.getPhonenumber1());
                // int count = logic.checkphonenumber(null, map,
                // TableNameUtil.KQDS_USERDOCUMENT);
                // if (count > 0) {
                // throw new Exception("手机号码1已存在：" + dp.getPhonenumber1());
                // }
                // }

                // if (YZUtility.isNotNullOrEmpty(dp.getPhonenumber2())) {
                // Map<String, String> map = new HashMap<String, String>();
                // map.put("phonenumber2", dp.getPhonenumber2());
                // int count = logic.checkphonenumber(null, map,
                // TableNameUtil.KQDS_USERDOCUMENT);
                // if (count > 0) {
                // throw new Exception("手机号码2已存在：" + dp.getPhonenumber2());
                // }
                // }
                // 验证手机号码是否重复 结束...
                // String labelID="";
                // String labelName1="";
                // String labelName2="";
                // String labelName3="";
                /**
                 * 根据家人、本人分情况验证手机号码是否重复 2019-7-24 syp start
                 */
                List<KqdsUserdocument> list = logic.selectUserdocumentByPhonenumber(dp.getPhonenumber1());
                if (list.size() == 0) {
                    String uuid = YZUtility.getUUID();
                    dp.setSeqId(uuid);
                    String pym = ChineseCharToEn.getAllFirstLetter(dp.getUsername());
                    dp.setPym(pym);
                    // dp.setUsercode(dp.getIdcardno());//患者编号 门诊编号+000001

                    // 先查询出门诊编号 再查询患者表中最后一条记录的患者编号数字是多少 本次新增
                    //身份证信息补充
                    dp.setNation(nation);
                    dp.setCertOrg(certOrg);
                    dp.setEffDate(effDate);
                    dp.setExpDate(expDate);
                    dp.setHeadPic(headPic);
                    dp.setPhotoDisplay(photoDisplay);
                    dp.setIsdelete(0);
                    dp.setCreatetime(YZUtility.getCurDateTimeStr());
                    dp.setCreateuser(person.getSeqId());
                    // 客服中心-新建档案时使用 start
                    name = dp.getUsername();
                    createtime = dp.getCreatetime();
                    // 客服中心-新建档案时使用 end
                    // 患者来源如果是网络或者电话 则保存用户为网电用户
                    // 验证患者编号是否存在 存在则重新获取并保存

                    /*************************************
                     * 生成条形码信息start
                     **************************************************/
                    // 生成条形码
                    // dp.setBarcode(BarcodeUtil.generateBarCode128(dp.getUsercode(),
                    // "0.5", "15"));
                    /*************************************
                     * 生成条形码信息end
                     **************************************************/
                    logic.insertUserDoc(dp, netorder, person, request);
                    if (labelAllList != null) {
                        kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
                        for (kqdsHzLabellabeAndPatient kPatien : labelAllList) {
                            String id = YZUtility.getUUID();
                            kPatient.setSeqId(id);
                            kPatient.setUserSeqId(dp.getSeqId());
                            kPatient.setUserId(dp.getUsercode());
                            kPatient.setUserName(dp.getUsername());
                            kPatient.setLabelOneId(kPatien.getLabelOneId());
                            kPatient.setLabelOneName(kPatien.getLabelOneName());
                            kPatient.setLabelTwoId(kPatien.getLabelTwoId());
                            kPatient.setLabelTwoName(kPatien.getLabelTwoName());
                            kPatient.setLabelThreeId(kPatien.getLabelThreeId());
                            kPatient.setLabelThreeName(kPatien.getLabelThreeName());
                            if (kPatien.getLabelThreeId() == null) {
                                kPatien.setLabelThreeId("00165d45-650b-4394-9768-4482a0ca9b05");
                            }
                            kPatient.setOpinion(kPatien.getOpinion());
                            kPatient.setCreateUser(person.getSeqId());
                            kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                            kPatient.setOrganization(organization);
                            logic.saveKpatient(kPatient);
                            // if(!labelID.contains(kPatien.getLabelOneId())){
                            // labelID+=kPatien.getLabelOneId()+",";
                            // }
                            // if(!labelID.contains(kPatien.getLabelTwoId())){
                            // labelID+=kPatien.getLabelTwoId()+",";
                            // }
                            // if(!labelID.contains(kPatien.getLabelThreeId())){
                            // labelID+=kPatien.getLabelThreeId()+",";
                            // }
                            // if(labelName1.equals("")){
                            // labelName1=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                            // }else{
                            // if(labelName1.contains(kPatien.getLabelOneName())){
                            // if(labelName1.contains(kPatien.getLabelTwoName())){
                            // labelName1+=","+kPatien.getLabelThreeName();
                            // }else{
                            // labelName1+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                            // }
                            // }else{
                            // if(labelName2.equals("")){
                            // labelName2=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                            // }else{
                            // if(labelName2.contains(kPatien.getLabelOneName())){
                            // if(labelName2.contains(kPatien.getLabelTwoName())){
                            // labelName2+=","+kPatien.getLabelThreeName();
                            // }else{
                            // labelName2+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                            // }
                            // }else{
                            // if(labelName3.equals("")){
                            // labelName3=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                            // }else{
                            // if(labelName3.contains(kPatien.getLabelOneName())){
                            // if(labelName3.contains(kPatien.getLabelTwoName())){
                            // labelName3+=","+kPatien.getLabelThreeName();
                            // }else{
                            // labelName3+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                            // }
                            // }
                            // }
                            // }
                            // }
                            // }
                            // }
                        }
                        if (dp.getAge() != null && !dp.getAge().equals("")) {
                            int age = dp.getAge();
                            String threename = "";
                            if (age <= 30) {
                                threename = "30岁以下";
                            } else if (age >= 31 && age <= 40) {
                                threename = "31-40岁";
                            } else if (age >= 41 && age <= 50) {
                                threename = "41-50岁";
                            } else if (age >= 51 && age <= 60) {
                                threename = "51-60岁";
                            } else if (age >= 61 && age <= 70) {
                                threename = "61-70岁";
                            } else if (age >= 71 && age <= 80) {
                                threename = "71-80岁";
                            } else if (age >= 81) {
                                threename = "80岁以上";
                            }
                            // 根据三级名称查找三级id
                            String threeId = labelLogic.findKqdsHzLabelSeqIdByLeveLabel(threename);
                            String id = YZUtility.getUUID();
                            kPatient.setSeqId(id);
                            kPatient.setUserSeqId(dp.getSeqId());
                            kPatient.setUserId(dp.getUsercode());
                            kPatient.setUserName(dp.getUsername());
                            kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
                            kPatient.setLabelOneName("社会标签");
                            kPatient.setLabelTwoId("9f1cd4c9-2aea-4309-9556-7812b225e23f");
                            kPatient.setLabelTwoName("年龄");
                            kPatient.setLabelThreeId(threeId);
                            kPatient.setLabelThreeName(threename);
                            kPatient.setCreateUser(person.getSeqId());
                            kPatient.setStatus(1);
                            kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                            kPatient.setOrganization(organization);
                            logic.saveKpatient(kPatient);
                            // if(!labelID.contains("13543c4d-f81e-4251-87a1-f07984022e9f")){
                            // labelID+="13543c4d-f81e-4251-87a1-f07984022e9f,";
                            // }
                            // if(!labelID.contains("9f1cd4c9-2aea-4309-9556-7812b225e23f")){
                            // labelID+="9f1cd4c9-2aea-4309-9556-7812b225e23f,";
                            // }
                            // if(!labelID.contains(threeId)){
                            // labelID+=threeId+",";
                            // }
                            // if(labelName1.equals("")){
                            // labelName1="社会标签:年龄:"+threename;
                            // }else{
                            // if(labelName1.contains("社会标签")){
                            // if(labelName1.contains("年龄")){
                            // labelName1+=","+threename;
                            // }else{
                            // labelName1+=";年龄:"+threename;
                            // }
                            // }else{
                            // if(labelName2.equals("")){
                            // labelName2="社会标签:年龄:"+threename;
                            // }else{
                            // if(labelName2.contains("社会标签")){
                            // if(labelName2.contains("年龄")){
                            // labelName2+=","+threename;
                            // }else{
                            // labelName2+=";年龄:"+threename;
                            // }
                            // }else{
                            // if(labelName3.equals("")){
                            // labelName3="社会标签:年龄:"+threename;
                            // }else{
                            // if(labelName3.contains("社会标签")){
                            // if(labelName3.contains("年龄")){
                            // labelName3+=","+threename;
                            // }else{
                            // labelName3+=";年龄:"+threename;
                            // }
                            // }
                            // }
                            // }
                            // }
                            // }
                            // }
                        }

                        // 根据职业id在字典表查询职业名称
                        if (dp.getProfession() != null && !dp.getProfession().equals("")) {
                            String dictName = dictLogic.findDictNameBySeqId(dp.getProfession());
                            Map<String, String> map1 = new HashMap<String, String>();
                            if (dictName != null && !dictName.equals("")) {
                                map1.put("leveLabel", dictName);
                            }
                            map1.put("parentId", "62aa427d-b590-4077-8b7b-4195201ec758");
                            String seqid = labelLogic.selectKqdsHzLabelByLeveLabel(map1);
                            if (seqid == null || seqid.equals("")) {
                                // 新增标签
                                KqdsLabel kqdsHzLabel = new KqdsLabel();
                                seqid = YZUtility.getUUID();
                                kqdsHzLabel.setSeqId(seqid);
                                kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
                                kqdsHzLabel.setCreateUser(person.getSeqId());
                                kqdsHzLabel.setLeveLabel(dictName);
                                kqdsHzLabel.setParentId("62aa427d-b590-4077-8b7b-4195201ec758");
                                kqdsHzLabel.setParentName("职业");
                                kqdsHzLabel.setRemark("三级");
                                labelLogic.insertKqdsHzLabel(kqdsHzLabel);
                            }
                            String id = YZUtility.getUUID();
                            kPatient.setSeqId(id);
                            kPatient.setUserSeqId(dp.getSeqId());
                            kPatient.setUserId(dp.getUsercode());
                            kPatient.setUserName(dp.getUsername());
                            kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
                            kPatient.setLabelOneName("社会标签");
                            kPatient.setLabelTwoId("62aa427d-b590-4077-8b7b-4195201ec758");
                            kPatient.setLabelTwoName("职业");
                            kPatient.setLabelThreeId(seqid);
                            kPatient.setLabelThreeName(dictName);
                            kPatient.setCreateUser(person.getSeqId());
                            kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                            kPatient.setStatus(1);
                            kPatient.setOrganization(organization);
                            logic.saveKpatient(kPatient);

                        }
                    }
                    if (exploit != null && !exploit.equals("undefined") && exploitId != null) {
                        savePriceList(exploitId, exploit, dp.getUsername(), dp.getUsercode(), "1", person, response);
                    }
                    if (exploit1 != null && !exploit1.equals("undefined") && exploitId1 != null) {
                        savePriceList(exploitId1, exploit1, dp.getUsername(), dp.getUsercode(), "2", person, response);
                    }

                } else if (list.size() > 0) {
                    int a = 0;
                    for (KqdsUserdocument kqdsUserdocument : list) {
                        if ("家人".equals(kqdsUserdocument.getFamilyship()) && (dp.getFamilyship()).equals("家人")) {
                            a = 1;
                        } else if ("本人".equals(kqdsUserdocument.getFamilyship()) && (dp.getFamilyship()).equals("家人")) {
                            a = 1;
                        } else if ("本人".equals(kqdsUserdocument.getFamilyship()) && (dp.getFamilyship()).equals("本人")) {
                            throw new Exception("患者本人重复建档手机号码不能重复！");
                        }
                    }
                    if (a > 0) {
                        String uuid = YZUtility.getUUID();
                        dp.setSeqId(uuid);
                        String pym = ChineseCharToEn.getAllFirstLetter(dp.getUsername());
                        dp.setPym(pym);
                        // dp.setUsercode(dp.getIdcardno());//患者编号 门诊编号+000001

                        // 先查询出门诊编号 再查询患者表中最后一条记录的患者编号数字是多少 本次新增
                        dp.setIsdelete(0);
                        dp.setCreatetime(YZUtility.getCurDateTimeStr());
                        dp.setCreateuser(person.getSeqId());
                        // 客服中心-新建档案时使用 start
                        name = dp.getUsername();
                        createtime = dp.getCreatetime();
                        // 客服中心-新建档案时使用 end
                        // 患者来源如果是网络或者电话 则保存用户为网电用户
                        // 验证患者编号是否存在 存在则重新获取并保存

                        /*************************************
                         * 生成条形码信息start
                         **************************************************/
                        // 生成条形码
                        // dp.setBarcode(BarcodeUtil.generateBarCode128(dp.getUsercode(),
                        // "0.5", "15"));
                        /*************************************
                         * 生成条形码信息end
                         **************************************************/
                        logic.insertUserDoc(dp, netorder, person, request);
                        if (labelAllList != null) {
                            kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
                            for (kqdsHzLabellabeAndPatient kPatien : labelAllList) {
                                String id = YZUtility.getUUID();
                                kPatient.setSeqId(id);
                                kPatient.setUserSeqId(dp.getSeqId());
                                kPatient.setUserId(dp.getUsercode());
                                kPatient.setUserName(dp.getUsername());
                                kPatient.setLabelOneId(kPatien.getLabelOneId());
                                kPatient.setLabelOneName(kPatien.getLabelOneName());
                                kPatient.setLabelTwoId(kPatien.getLabelTwoId());
                                kPatient.setLabelTwoName(kPatien.getLabelTwoName());
                                kPatient.setLabelThreeId(kPatien.getLabelThreeId());
                                kPatient.setLabelThreeName(kPatien.getLabelThreeName());
                                kPatient.setCreateUser(person.getSeqId());
                                kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                                kPatient.setOrganization(organization);
                                logic.saveKpatient(kPatient);
                                // if(!labelID.contains(kPatien.getLabelOneId())){
                                // labelID+=kPatien.getLabelOneId()+",";
                                // }
                                // if(!labelID.contains(kPatien.getLabelTwoId())){
                                // labelID+=kPatien.getLabelTwoId()+",";
                                // }
                                // if(!labelID.contains(kPatien.getLabelThreeId())){
                                // labelID+=kPatien.getLabelThreeId()+",";
                                // }
                                // if(labelName1.equals("")){
                                // labelName1=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                                // }else{
                                // if(labelName1.contains(kPatien.getLabelOneName())){
                                // if(labelName1.contains(kPatien.getLabelTwoName())){
                                // labelName1+=","+kPatien.getLabelThreeName();
                                // }else{
                                // labelName1+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                                // }
                                // }else{
                                // if(labelName2.equals("")){
                                // labelName2=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                                // }else{
                                // if(labelName2.contains(kPatien.getLabelOneName())){
                                // if(labelName2.contains(kPatien.getLabelTwoName())){
                                // labelName2+=","+kPatien.getLabelThreeName();
                                // }else{
                                // labelName2+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                                // }
                                // }else{
                                // if(labelName3.equals("")){
                                // labelName3=kPatien.getLabelOneName()+":"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                                // }else{
                                // if(labelName3.contains(kPatien.getLabelOneName())){
                                // if(labelName3.contains(kPatien.getLabelTwoName())){
                                // labelName3+=","+kPatien.getLabelThreeName();
                                // }else{
                                // labelName3+=";"+kPatien.getLabelTwoName()+":"+kPatien.getLabelThreeName();
                                // }
                                // }
                                // }
                                // }
                                // }
                                // }
                                // }
                            }
                            if (dp.getAge() != null && !dp.getAge().equals("")) {
                                int age = dp.getAge();
                                String threename = "";
                                if (age <= 30) {
                                    threename = "30岁以下";
                                } else if (age >= 31 && age <= 40) {
                                    threename = "31-40岁";
                                } else if (age >= 41 && age <= 50) {
                                    threename = "41-50岁";
                                } else if (age >= 51 && age <= 60) {
                                    threename = "51-60岁";
                                } else if (age >= 61 && age <= 70) {
                                    threename = "61-70岁";
                                } else if (age >= 71 && age <= 80) {
                                    threename = "71-80岁";
                                } else if (age >= 81) {
                                    threename = "80岁以上";
                                }
                                // 根据三级名称查找三级id
                                String threeId = labelLogic.findKqdsHzLabelSeqIdByLeveLabel(threename);
                                String id = YZUtility.getUUID();
                                kPatient.setSeqId(id);
                                kPatient.setUserSeqId(dp.getSeqId());
                                kPatient.setUserId(dp.getUsercode());
                                kPatient.setUserName(dp.getUsername());
                                kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
                                kPatient.setLabelOneName("社会标签");
                                kPatient.setLabelTwoId("9f1cd4c9-2aea-4309-9556-7812b225e23f");
                                kPatient.setLabelTwoName("年龄");
                                kPatient.setLabelThreeId(threeId);
                                kPatient.setLabelThreeName(threename);
                                kPatient.setCreateUser(person.getSeqId());
                                kPatient.setStatus(1);
                                kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                                kPatient.setOrganization(organization);
                                logic.saveKpatient(kPatient);
                                // if(!labelID.contains("13543c4d-f81e-4251-87a1-f07984022e9f")){
                                // labelID+="13543c4d-f81e-4251-87a1-f07984022e9f,";
                                // }
                                // if(!labelID.contains("9f1cd4c9-2aea-4309-9556-7812b225e23f")){
                                // labelID+="9f1cd4c9-2aea-4309-9556-7812b225e23f,";
                                // }
                                // if(!labelID.contains(threeId)){
                                // labelID+=threeId+",";
                                // }
                                // if(labelName1.equals("")){
                                // labelName1="社会标签:年龄:"+threename;
                                // }else{
                                // if(labelName1.contains("社会标签")){
                                // if(labelName1.contains("年龄")){
                                // labelName1+=","+threename;
                                // }else{
                                // labelName1+=";年龄:"+threename;
                                // }
                                // }else{
                                // if(labelName2.equals("")){
                                // labelName2="社会标签:年龄:"+threename;
                                // }else{
                                // if(labelName2.contains("社会标签")){
                                // if(labelName2.contains("年龄")){
                                // labelName2+=","+threename;
                                // }else{
                                // labelName2+=";年龄:"+threename;
                                // }
                                // }else{
                                // if(labelName3.equals("")){
                                // labelName3="社会标签:年龄:"+threename;
                                // }else{
                                // if(labelName3.contains("社会标签")){
                                // if(labelName3.contains("年龄")){
                                // labelName3+=","+threename;
                                // }else{
                                // labelName3+=";年龄:"+threename;
                                // }
                                // }
                                // }
                                // }
                                // }
                                // }
                                // }
                            }

                            // 根据职业id在字典表查询职业名称
                            if (dp.getProfession() != null && !dp.getProfession().equals("")) {
                                String dictName = dictLogic.findDictNameBySeqId(dp.getProfession());
                                Map<String, String> map1 = new HashMap<String, String>();
                                if (dictName != null && !dictName.equals("")) {
                                    map1.put("leveLabel", dictName);
                                }
                                map1.put("parentId", "62aa427d-b590-4077-8b7b-4195201ec758");
                                String seqid = labelLogic.selectKqdsHzLabelByLeveLabel(map1);
                                if (seqid == null || seqid.equals("")) {
                                    // 新增标签
                                    KqdsLabel kqdsHzLabel = new KqdsLabel();
                                    seqid = YZUtility.getUUID();
                                    kqdsHzLabel.setSeqId(seqid);
                                    kqdsHzLabel.setCreateTime(YZUtility.getCurDateTimeStr());
                                    kqdsHzLabel.setCreateUser(person.getSeqId());
                                    kqdsHzLabel.setLeveLabel(dictName);
                                    kqdsHzLabel.setParentId("62aa427d-b590-4077-8b7b-4195201ec758");
                                    kqdsHzLabel.setParentName("职业");
                                    kqdsHzLabel.setRemark("三级");
                                    labelLogic.insertKqdsHzLabel(kqdsHzLabel);
                                }
                                String id = YZUtility.getUUID();
                                kPatient.setSeqId(id);
                                kPatient.setUserSeqId(dp.getSeqId());
                                kPatient.setUserId(dp.getUsercode());
                                kPatient.setUserName(dp.getUsername());
                                kPatient.setLabelOneId("13543c4d-f81e-4251-87a1-f07984022e9f");
                                kPatient.setLabelOneName("社会标签");
                                kPatient.setLabelTwoId("62aa427d-b590-4077-8b7b-4195201ec758");
                                kPatient.setLabelTwoName("职业");
                                kPatient.setLabelThreeId(seqid);
                                kPatient.setLabelThreeName(dictName);
                                kPatient.setCreateUser(person.getSeqId());
                                kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                                kPatient.setStatus(1);
                                kPatient.setOrganization(organization);
                                logic.saveKpatient(kPatient);
                                // if(!labelID.contains("13543c4d-f81e-4251-87a1-f07984022e9f")){
                                // labelID+="13543c4d-f81e-4251-87a1-f07984022e9f,";
                                // }
                                // if(!labelID.contains("62aa427d-b590-4077-8b7b-4195201ec758")){
                                // labelID+="62aa427d-b590-4077-8b7b-4195201ec758,";
                                // }
                                // if(!labelID.contains(seqid)){
                                // labelID+=seqid+",";
                                // }
                                // if(labelName1.equals("")){
                                // labelName1="社会标签:职业:"+dictName;
                                // }else{
                                // if(labelName1.contains("社会标签")){
                                // if(labelName1.contains("职业")){
                                // labelName1+=","+dictName;
                                // }else{
                                // labelName1+=";职业:"+dictName;
                                // }
                                // }else{
                                // if(labelName2.equals("")){
                                // labelName2="社会标签:职业:"+dictName;
                                // }else{
                                // if(labelName2.contains("社会标签")){
                                // if(labelName2.contains("职业")){
                                // labelName2+=","+dictName;
                                // }else{
                                // labelName2+=";职业:"+dictName;
                                // }
                                // }else{
                                // if(labelName3.equals("")){
                                // labelName3="社会标签:职业:"+dictName;
                                // }else{
                                // if(labelName3.contains("社会标签")){
                                // if(labelName3.contains("职业")){
                                // labelName3+=","+dictName;
                                // }else{
                                // labelName3+=";职业:"+dictName;
                                // }
                                // }
                                // }
                                // }
                                // }
                                // }
                                // }
                            }
                        }
                        if (exploit != null && !exploit.equals("undefined") && exploitId != null) {
                            savePriceList(exploitId, exploit, dp.getUsername(), dp.getUsercode(), "1", person,
                                    response);
                        }
                        if (exploit1 != null && !exploit1.equals("undefined") && exploitId1 != null) {
                            savePriceList(exploitId1, exploit1, dp.getUsername(), dp.getUsercode(), "2", person,
                                    response);
                        }
                    }
                }
            }
            /**
             * end
             */

            // String uuid = YZUtility.getUUID();
            // dp.setSeqId(uuid);
            // String pym = ChineseCharToEn.getAllFirstLetter(dp.getUsername());
            // dp.setPym(pym);
            // // dp.setUsercode(dp.getIdcardno());//患者编号 门诊编号+000001
            //
            // // 先查询出门诊编号 再查询患者表中最后一条记录的患者编号数字是多少 本次新增
            // dp.setIsdelete(0);
            // dp.setCreatetime(YZUtility.getCurDateTimeStr());
            // dp.setCreateuser(person.getSeqId());
            // // 客服中心-新建档案时使用 start
            // name = dp.getUsername();
            // createtime = dp.getCreatetime();
            // // 客服中心-新建档案时使用 end
            // // 患者来源如果是网络或者电话 则保存用户为网电用户
            // // 验证患者编号是否存在 存在则重新获取并保存
            //
            // /*************************************
            // * 生成条形码信息start
            // **************************************************/
            // // 生成条形码
            // // dp.setBarcode(BarcodeUtil.generateBarCode128(dp.getUsercode(),
            // // "0.5", "15"));
            // /*************************************
            // * 生成条形码信息end
            // **************************************************/
            // logic.insertUserDoc(dp, netorder, person, request);

            JSONObject jobj = new JSONObject();
            // 客服中心-新建档案时使用 start
            jobj.put("name", name);
            jobj.put("createtime", createtime);
            jobj.put("usercode", dp.getUsercode());
            // 客服中心-新建档案时使用 end

            YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);

        }
        return null;
    }

    public void savePriceList(String seqid, String priveListDetails, String userName, String usercode, String status,
                              YZPerson person, HttpServletResponse response) throws Exception {
        try {
            // 添加关联表数据
            KqdsHzLabelAssociated kqdsHzLabelAssociated = new KqdsHzLabelAssociated();
            KqdsHzLabelAssociated kqdsHzLabelAssociated1 = new KqdsHzLabelAssociated();
            // 根据患者id查询关联表id 有则修改无则新增
            Map<String, String> map = new HashMap<String, String>();
            map.put("usercode", usercode);
            map.put("status", status);
            String hzLabelAssciatedSeqId = hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map);
            if (!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)) {
                // 批量更改关联表状态
                kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
                kqdsHzLabelAssociated1.setUpdateTime(YZUtility.getCurDateTimeStr());
                kqdsHzLabelAssociated1.setModifier(person.getSeqId());
                int j = hzLabelAssociatedLogic.updateKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
                // 根据parentid修改费用状态
                KqdsCostorderPriceList kqdsCostorderPriceList = new KqdsCostorderPriceList();
                kqdsCostorderPriceList.setModifier(person.getSeqId());
                kqdsCostorderPriceList.setUpdateTime(YZUtility.getCurDateTimeStr());
                kqdsCostorderPriceList.setParentId(hzLabelAssciatedSeqId);
                int x = priceListLogic.updatePriceList(kqdsCostorderPriceList);
            }
            hzLabelAssciatedSeqId = YZUtility.getUUID();
            kqdsHzLabelAssociated.setSeqId(hzLabelAssciatedSeqId);
            kqdsHzLabelAssociated.setLabeId(seqid);
            kqdsHzLabelAssociated.setUsercode(usercode);// 患者编号
            kqdsHzLabelAssociated.setUserName(userName);// 患者姓名
            kqdsHzLabelAssociated.setCreateTime(YZUtility.getCurDateTimeStr());
            kqdsHzLabelAssociated.setCreateUser(person.getSeqId());
            kqdsHzLabelAssociated.setRemark("");
            kqdsHzLabelAssociated.setStatus(Integer.valueOf(status));
            kqdsHzLabelAssociated.setIsdelete(0);
            int j = hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated);
            if (j > 0) {
                // 添加费用明细数据
                priveListDetails = java.net.URLDecoder.decode(priveListDetails, "UTF-8");
                priveListDetails = StringEscapeUtils.unescapeJava(priveListDetails).substring(1,
                        StringEscapeUtils.unescapeJava(priveListDetails).length() - 1);
                List<KqdsCostorderPriceList> list = HUDHUtil.parseJsonToObjectList(priveListDetails,
                        KqdsCostorderPriceList.class);
                for (KqdsCostorderPriceList kqdsCostorderPriceList : list) {
                    kqdsCostorderPriceList.setCreateuser(person.getSeqId());
                    kqdsCostorderPriceList.setCreatetime(YZUtility.getCurDateTimeStr());
                    kqdsCostorderPriceList.setSeqId(YZUtility.getUUID());
                    kqdsCostorderPriceList.setParentId(hzLabelAssciatedSeqId);
                    kqdsCostorderPriceList.setIsdelete(0);
                    kqdsCostorderPriceList.setUsercode(usercode);
                }
                priceListLogic.insertPriceList(list);
            }
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
        }
    }

    /**
     * 获取病人编号
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUserCode.act")
    public String getUserCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String organization = request.getParameter("organization");
            if (YZUtility.isNullorEmpty(organization)) {
                organization = ChainUtil.getCurrentOrganization(request);// 获取当前登录的门诊！！！
            }                                                                    // 修复连锁获取门诊编号的BUG！！
            String resultString = UserCodeUtil.getUserCode(organization);
            if (YZUtility.isNullorEmpty(resultString)) {
                throw new Exception("获取患者编号失败，编号值为空");
            }
            JSONObject jobj = new JSONObject();
            jobj.put(YZActionKeys.JSON_RET_DATA, resultString);
            YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 患者合并
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/hzhb.act")
    public String hzhb(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String usercode1 = request.getParameter("usercode1");
            String usercode2 = request.getParameter("usercode2");
            if (usercode1.equals(usercode2)) {
                throw new Exception("同一患者不能合并");
            }

            Map map = new HashMap<String, String>();
            map.put("usercode", usercode1);
            List<KqdsUserdocument> list1 = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT,
                    map);
            if (list1 == null || list1.isEmpty()) {
                throw new Exception("患者不存在");
            }
            map.put("usercode", usercode2);
            List<KqdsUserdocument> list2 = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT,
                    map);
            if (list2 == null || list2.isEmpty()) {
                throw new Exception("患者不存在");
            }
            KqdsUserdocument user1 = list1.get(0);
            KqdsUserdocument user2 = list2.get(0);
            // -----user1合并到user2---------合并start----------------------
            // 合并患者档案表
            logic.hzhb(user1, user2, request);
            // 记录日志
            BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MERGE, BcjlUtil.KQDS_USERDOCUMENT, user2, user2.getUsercode(),
                    TableNameUtil.KQDS_USERDOCUMENT, request);
            // --------------合并end----------------------
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, true, ex, response, logger);
        }

        return null;
    }

    /**
     * 根据usercode获取对象
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOneByUsercode.act")
    public String getOneByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String usercode = request.getParameter("usercode");

            Map map = new HashMap<String, String>();
            map.put("usercode", usercode); // usercode是全局唯一的
            // map.put("isdelete", "0");
            List<KqdsUserdocument> en = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

            if (en != null && en.size() > 1) { // 确保只能查出来一条记录
                throw new Exception("数据异常，一个编号对应多个患者");
            }

            Map<String, String> map2 = new HashMap<String, String>();
            map2.put("usercode", usercode);
            List<KqdsMember> mlist = (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map2);
            BigDecimal money = BigDecimal.ZERO;
            BigDecimal givemoney = BigDecimal.ZERO;
            for (KqdsMember mobj : mlist) {
                BigDecimal tmpMoney = mobj.getMoney();
                money = tmpMoney.add(money);
                BigDecimal tmpGiveMoney = mobj.getGivemoney();
                givemoney = tmpGiveMoney.add(givemoney);
            }

            JSONObject jobj = new JSONObject();
            jobj.put("data", en);
            /** 注意，这里返回的是一个List,js接收到的就是数组！！！ **/
            jobj.put("money", money); // 确保只能查出来一条记录
            jobj.put("givemoney", givemoney); // 确保只能查出来一条记录
            YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 根据usercode获取对象 【util.js中的方法调用】
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOneByUsercodes.act")
    public String getOneByUsercodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String usercodes = request.getParameter("usercodes");

            Map map = new HashMap<String, String>();
            List<KqdsUserdocument> list = new ArrayList<KqdsUserdocument>();
            if (!YZUtility.isNullorEmpty(usercodes)) {
                String[] usercodeArr = usercodes.split(",");
                for (String usercode : usercodeArr) {
                    map.put("usercode", usercode);
                    List<KqdsUserdocument> en = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT,
                            map);
                    if (en != null && en.size() > 0) {
                        list.add(en.get(en.size() - 1));
                    }
                }
            }
            JSONObject jobj = new JSONObject();
            jobj.put("data", list);
            YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    // 根据姓名、手机号查询usercode
    @RequestMapping(value = "/getUsercodeByPhoneAndName.act")
    public String getUsercodeByPhoneAndName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Map map = new HashMap<String, String>();

            String username = request.getParameter("username");
            String phonenumber1 = request.getParameter("phonenumber1");
            String phonenumber2 = request.getParameter("phonenumber2");

            map.put("username", username);
            if (!YZUtility.isNotNullOrEmpty(phonenumber1)) {
                map.put("phonenumber1", phonenumber1);
            }
            if (!YZUtility.isNotNullOrEmpty(phonenumber2)) {
                map.put("phonenumber2", phonenumber2);
            }
            List<KqdsUserdocument> en = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

            JSONObject jobj = new JSONObject();
            if (en.size() > 0) {
                // jobj.put("data", en.get(0).getUsercode());
                jobj.put("data", en.get(en.size() - 1).getUsercode());
            } else {
                jobj.put("data", "");
            }

            YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 跨院查询 【不做门诊条件过滤】
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectWithNopageGh.act")
    public String selectWithNopageGh(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            /* String type = request.getParameter("type"); */
            String searchField = request.getParameter("searchField");
            String searchValue = request.getParameter("searchValue");
            String usercode = request.getParameter("usercode");
            //String organization = request.getParameter("organization");
            String querydata = request.getParameter("querydata");
            // 封装参数到实体类
            List<JSONObject> list = new ArrayList<JSONObject>();
            Map<String, String> map = new HashMap<String, String>();
            /* map.put("type", type); */
            //map.put("organization", organization);
            map.put("isdelete", "0");
            if (!YZUtility.isNullorEmpty(querydata)) {
                map.put("querydata", querydata);
            }
            boolean search = null != searchField && null != searchValue && !"".equals(searchValue)
                    && !"".equals(searchField);
            if (search) {
                map.put(searchField, searchValue);
                list = logic.selectWithNopageGh(TableNameUtil.KQDS_USERDOCUMENT, map);
            } else {
                // 查询条件为空 则本次操作为建档、预约中心跳转 使用usercode查询
                if (YZUtility.isNotNullOrEmpty(usercode)) {
                    map.put("usercode", usercode);
                    list = logic.selectWithNopageGh(TableNameUtil.KQDS_USERDOCUMENT, map);
                }
            }
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 添加模糊查询 （挂号时）
     *
     * @Title: selectWithNopageGhLike
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param
     * request
     * @param: @param
     * response
     * @param: @return
     * @param: @throws
     * Exception
     * @return: String
     * @dateTime:2019年11月18日 下午5:03:46
     */
    @RequestMapping(value = "/selectWithNopageGhLike.act")
    public String selectWithNopageGhLike(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            /* String type = request.getParameter("type"); */
            String searchField = request.getParameter("searchField");
            String searchValue = request.getParameter("searchValue");
            String usercode = request.getParameter("usercode");
            String organization = request.getParameter("organization");
            String querydata = request.getParameter("querydata");
            // 封装参数到实体类
            JSONObject list = new JSONObject();
            Map<String, String> map = new HashMap<String, String>();
            /* map.put("type", type); */
            map.put("organization", organization);
            map.put("isdelete", "0");
            if (!YZUtility.isNullorEmpty(querydata)) {
                map.put("querydata", querydata);
            }
            boolean search = null != searchField && null != searchValue && !"".equals(searchValue)
                    && !"".equals(searchField);
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());
            if (search) {
                // map.put(searchField, searchValue);
                map.put("searchValue", searchValue);
                list = logic.selectWithNopageGhLike(TableNameUtil.KQDS_USERDOCUMENT, map, bp);
            } else {
                // 查询条件为空 则本次操作为建档、预约中心跳转 使用usercode查询
                if (YZUtility.isNotNullOrEmpty(usercode)) {
                    map.put("usercode", usercode);
                    list = logic.selectWithNopageGhLike(TableNameUtil.KQDS_USERDOCUMENT, map, bp);
                }
            }
            YZUtility.DEAL_SUCCESS(list, null, response, logger);
            //YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 咨询创建临床路径按权限查找患者 2019-7-30 syp
     *
     * @Title: selectWithNopageGhPermission
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param
     * request
     * @param: @param
     * response
     * @param: @return
     * @param: @throws
     * Exception
     * @return: String
     * @dateTime:2019年7月30日 上午10:24:18
     */
    @RequestMapping(value = "/selectWithNopageGhPermission.act")
    public String selectWithNopageGhPermission(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            String searchField = request.getParameter("searchField");
            String searchValue = request.getParameter("searchValue");
            String usercode = request.getParameter("usercode");
            String regsort = request.getParameter("regsort");// 挂号分类
            String organization = request.getParameter("organization");
            String querydata = request.getParameter("querydata");
            YZPerson person = SessionUtil.getLoginPerson(request);
            // 可见人员过滤
            String visualstaff = SessionUtil.getVisualstaff(request);
            // 封装参数到实体类
            List<JSONObject> list = new ArrayList<JSONObject>();
            Map<String, String> map = new HashMap<String, String>();
            if (YZUtility.isNotNullOrEmpty(visualstaff)) {
                map.put("querytype", visualstaff);
            }
            if (YZUtility.isNotNullOrEmpty(regsort)) {
                map.put("regsort", regsort);
            }
            map.put("organization", organization);
            map.put("isdelete", "0");
            if (!YZUtility.isNullorEmpty(querydata)) {
                map.put("querydata", querydata);
            }
            if(!YZUtility.isNullorEmpty(person.getSeqId())){
                map.put("kefu",person.getSeqId());
            }
            boolean search = null != searchField && null != searchValue && !"".equals(searchValue)
                    && !"".equals(searchField);
            if (search) {
                map.put(searchField, searchValue);
                list = logic.selectWithNopageGhPermission(TableNameUtil.KQDS_USERDOCUMENT, map);
            } else {
                // 查询条件为空 则本次操作为建档、预约中心跳转 使用usercode查询
                if (YZUtility.isNotNullOrEmpty(usercode)) {
                    map.put("usercode", usercode);
                    list = logic.selectWithNopageGhPermission(TableNameUtil.KQDS_USERDOCUMENT, map);
                }
            }
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 查询多选的关联人 【wd_index.jsp调用】 【不做门诊条件过滤】
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectByUserCodes.act")
    public String selectByUserCodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String usercodes = request.getParameter("usercodes");
            String usercodeStr = YZUtility.ConvertStringIds4Query(usercodes);
            // 封装参数到实体类
            List<JSONObject> list = logic.selectByUserCodes(TableNameUtil.KQDS_USERDOCUMENT, usercodeStr);
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 条件查询 查询患者 【支持跨院】
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectWithNopage.act")
    public String selectWithNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);

            String type = request.getParameter("type");// 1 网电建档 0导医建档
            String searchField = request.getParameter("searchField");
            String searchValue = request.getParameter("searchValue");
            String username = request.getParameter("username");
            String sjhm = request.getParameter("sjhm");
            String typechoose = request.getParameter("typechoose");
            String organization = request.getParameter("organization");
            // 试用版本(查询登录人的门诊编号)
            String IS_OPEN_CHAIN_SELECT = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_SELECT);
            if ("1".equals(IS_OPEN_CHAIN_SELECT)) {
                organization = ChainUtil.getCurrentOrganization(request);
            }
            Map<String, String> map = new HashMap<String, String>();
            if (!YZUtility.isNullorEmpty(organization)) {
                map.put("organization", organization);
            }
            if (!YZUtility.isNullorEmpty(type)) {
                map.put("type", type);
            }
            map.put("isdelete", "0");

            if (!YZUtility.isNullorEmpty(username)) {
                map.put("username", username);
            }
            if (!YZUtility.isNullorEmpty(sjhm)) {
                map.put("PhoneNumber1", sjhm);
            }
            boolean search = null != searchField && null != searchValue && !"".equals(searchValue)
                    && !"".equals(searchField);
            if (search) {
                map.put(searchField, searchValue);
            }
            String operFlag = request.getParameter("operFlag");// 用于判断是否是预约中心的选择患者页面，如果是，则后台的查询用
            // 预约可见人员，而不是可见人员
            // 可见人员过滤
            String visualstaff = "";
            if ("yyzx".equals(operFlag)) { // 预约中心
                visualstaff = request.getSession().getAttribute("visualstaffYyrl").toString();
            } else {
                visualstaff = SessionUtil.getVisualstaff(request);
            }
            List<JSONObject> list = logic.selectWithNopage(TableNameUtil.KQDS_USERDOCUMENT, map, typechoose,
                    visualstaff, person);
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 信息报备和信息查询页面调用
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectWithNopage2.act")
    public String selectWithNopage2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sortName = request.getParameter("sortName");
        String sortOrder = request.getParameter("sortOrder");
        try {
            String type = request.getParameter("type");
            String starttime = request.getParameter("cjstarttime");
            String endtime = request.getParameter("cjendtime");
            if (YZUtility.isNullorEmpty(starttime)) {
                starttime = request.getParameter("starttime");
            }
            if (YZUtility.isNullorEmpty(endtime)) {
                endtime = request.getParameter("endtime");
            }
            String dystarttime = request.getParameter("dystarttime");
            String dyendtime = request.getParameter("dyendtime");
            String username = request.getParameter("username");
            String askperson = request.getParameter("askperson");
            String doctor = request.getParameter("doctor");
            String devchannel = request.getParameter("devchannel");
            String nexttype = request.getParameter("nexttype");
            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String ywhf = request.getParameter("ywhf");
            String jdr = request.getParameter("jdr");
            String kfr = request.getParameter("kfr");
            // 患者编号集
            String usercodes = request.getParameter("usercodes");
            /** 是否绑定微信 **/
            String bindWX = request.getParameter("bindWX");
            // 导出参数
            String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
            String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
            String fieldnameArr = request.getParameter("fieldnameArr") == null ? ""
                    : request.getParameter("fieldnameArr");
            Map<String, String> map = new HashMap<String, String>();
            if (!YZUtility.isNullorEmpty(type)) {
                map.put("type", type);
            }
            map.put("isdelete", "0");

            if (!YZUtility.isNullorEmpty(starttime)) {
                map.put("starttime", starttime + ConstUtil.TIME_START);
            } else {
                if (dystarttime.equals("") && dyendtime.equals("") && username.equals("") && doctor.equals("")
                        && jdr.equals("") && devchannel.equals("") && nexttype.equals("") && ywhf.equals("")
                        && askperson.equals("")) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
                    map.put("starttime", df.format(new Date()) + ConstUtil.TIME_START);
                }
            }
            if (!YZUtility.isNullorEmpty(endtime)) {
                map.put("endtime", endtime + ConstUtil.TIME_END);
            } else {
                if (dystarttime.equals("") && dyendtime.equals("") && username.equals("") && doctor.equals("")
                        && jdr.equals("") && devchannel.equals("") && nexttype.equals("") && ywhf.equals("")
                        && askperson.equals("")) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
                    map.put("endtime", df.format(new Date()) + ConstUtil.TIME_END);
                }
            }
            if (!YZUtility.isNullorEmpty(dystarttime)) {
                map.put("dystarttime", dystarttime + ConstUtil.TIME_START);
            }
            if (!YZUtility.isNullorEmpty(dyendtime)) {
                map.put("dyendtime", dyendtime + ConstUtil.TIME_END);
            }
            if (!YZUtility.isNullorEmpty(username)) {
                map.put("username", username);
            }
            if (!YZUtility.isNullorEmpty(askperson)) {
                map.put("askperson", askperson);
            }
            if (!YZUtility.isNullorEmpty(doctor)) {
                map.put("doctor", doctor);
            }

            if (!YZUtility.isNullorEmpty(devchannel)) {
                map.put("devchannel", devchannel);
            }
            if (!YZUtility.isNullorEmpty(nexttype)) {
                map.put("nexttype", nexttype);
            }
            if (!YZUtility.isNullorEmpty(province)) {
                map.put("province", province);
            }
            if (!YZUtility.isNullorEmpty(city)) {
                map.put("city", city);
            }
            if (!YZUtility.isNullorEmpty(ywhf)) {
                map.put("ywhf", ywhf);
            }
            if (!YZUtility.isNullorEmpty(bindWX)) {
                /** 是否绑定微信 0否 1是 **/
                map.put("bindWX", bindWX);
            }
            if (!YZUtility.isNullorEmpty(jdr)) {
                map.put("createuser", jdr);
            }
            if (!YZUtility.isNullorEmpty(kfr)) {
                map.put("developer", kfr);
            }
            if (!YZUtility.isNullorEmpty(usercodes)) {
                map.put("usercodes", usercodes);
            }

            // 是否分页 1 分页 0 不分页
            /*
             * if (!YZUtility.isNullorEmpty(ispaging) &&
             * YZUtility.isNullorEmpty(flag)) { // 市场活动/媒体记录/异业记录的患者明细按钮，分页查询
             * map.put("ispaging", ispaging); } else { map.put("ispaging", "0");
             * }
             */

            JSONObject json = new JSONObject();

            // 初始化分页实体类
            @SuppressWarnings("rawtypes")
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());

            String visualstaff = SessionUtil.getVisualstaff(request);

            if (flag != null && flag.equals("exportTable")) {
                JSONObject resut1 = logic.selectWithNopage2(bp, TableNameUtil.KQDS_USERDOCUMENT, visualstaff, map,
                        ChainUtil.getOrganizationFromUrl(request), json, flag);// ###根据页面传值进行门诊条件过滤
                if (resut1 != null) {
                    ExportBean bean = ExportTable.initExcel4RsWrite("信息查询", fieldArr, fieldnameArr, response, request);
                    bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List<JSONObject>) resut1.get("rows"),
                            "userdoc_selectNoPage");
                    ExportTable.writeExcel4DownLoad("信息查询", bean.getWorkbook(), response);
                }
                return null;
            }
            if (sortName != null) {
                map.put("sortName", sortName);
                map.put("sortOrder", sortOrder);
                json = logic.selectWithNopage2(bp, TableNameUtil.KQDS_USERDOCUMENT, visualstaff, map,
                        ChainUtil.getOrganizationFromUrl(request), json, flag);// ###根据页面传值进行门诊条件过滤
            } else {
                json = logic.selectWithNopage2(bp, TableNameUtil.KQDS_USERDOCUMENT, visualstaff, map,
                        ChainUtil.getOrganizationFromUrl(request), json, flag);// ###根据页面传值进行门诊条件过滤
            }
            YZUtility.DEAL_SUCCESS(json, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 验证身份证号是否已存在
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkIdCardNo.act")
    public String checkIdCardNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Map map = new HashMap<String, String>();

            String idcardno = request.getParameter("idcardno");
            String seqId = request.getParameter("seqId");
            List<KqdsUserdocument> en = new ArrayList<KqdsUserdocument>();
            map.put("idcardno", idcardno);
            boolean result = true;
            if (YZUtility.isNotNullOrEmpty(seqId)) {
                map.put("seqId", seqId);
                int num = logic.checkIdcardnoBySeqIdAndIdcardno(map, TableNameUtil.KQDS_USERDOCUMENT);
                if (num > 0) {
                    result = false;
                }
            } else {
                if (!YZUtility.isNullorEmpty(idcardno)) {
                    en = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
                    result = true;
                    if (en != null && en.size() > 0) {
                        result = false;
                    }
                }
            }
            YZUtility.DEAL_SUCCESS_VALID(result, response);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }
        return null;
    }

    /**
     * 验证手机号是否已存在
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkphonenumber.act")
    public String checkphonenumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean result = true;
        try {
            String phonenumber1 = request.getParameter("phonenumber1");
            String phonenumber2 = request.getParameter("phonenumber2");
            Map<String, String> map = new HashMap<String, String>();
            if (!YZUtility.isNullorEmpty(phonenumber1)) {
                map.put("phonenumber1", phonenumber1);
            }
            if (!YZUtility.isNullorEmpty(phonenumber2)) {
                map.put("phonenumber2", phonenumber2);
            }
            String seqId = request.getParameter("seqId");
            int num = logic.checkphonenumber(seqId, map, TableNameUtil.KQDS_USERDOCUMENT);

            if (num > 0) {
                result = false;
            }
            YZUtility.DEAL_SUCCESS_VALID(result, response);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 保存患者资料 应用于费用添加页面等
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editSub.act")
    public String editSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            KqdsUserdocument dp = new KqdsUserdocument();
            BeanUtils.populate(dp, request.getParameterMap());
            Map map = new HashMap<String, String>();
            map.put("seq_id", dp.getSeqId());
            List<KqdsUserdocument> en = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);
            if (dp.getMedicalhistory() != null && !dp.getMedicalhistory().equals("")) {
                en.get(0).setMedicalhistory(dp.getMedicalhistory());
            }
            if (dp.getDrugllergy() != null && !dp.getDrugllergy().equals("")) {
                en.get(0).setDrugllergy(dp.getDrugllergy());
            }
            if (dp.getPhonenumber1() != null && !dp.getPhonenumber1().equals("")) {
                en.get(0).setPhonenumber1(dp.getPhonenumber1());
            }
            if (dp.getPhonenumber2() != null && !dp.getPhonenumber2().equals("")) {
                en.get(0).setPhonenumber2(dp.getPhonenumber2());
            }
            en.get(0).setSex(dp.getSex());
            if (dp.getBirthday() != null && !dp.getBirthday().equals("")) {
                en.get(0).setBirthday(dp.getBirthday());
            }
            if (dp.getAddress() != null && !dp.getAddress().equals("")) {
                en.get(0).setAddress(dp.getAddress());
            }
            if (dp.getIntroducer() != null && !dp.getIntroducer().equals("")) {
                en.get(0).setIntroducer(dp.getIntroducer());
            }
            if (dp.getDevchannel() != null && !dp.getDevchannel().equals("")) {
                en.get(0).setDevchannel(dp.getDevchannel());
            }
            if (dp.getNexttype() != null && !dp.getNexttype().equals("")) {
                en.get(0).setNexttype(dp.getNexttype());
            }
            if (dp.getPlatenumber() != null && !dp.getPlatenumber().equals("")) {
                en.get(0).setPlatenumber(dp.getPlatenumber());
            }
            if (dp.getFirstword() != null && !dp.getFirstword().equals("")) {
                en.get(0).setFirstword(dp.getFirstword());
            }
            logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, en.get(0));

            // 记录日志
            BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, en.get(0),
                    en.get(0).getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, true, ex, response, logger);
        }
        return null;
    }

    /**
     * 修改关联人 【wdAddWin.jsp调用】
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editGlr.act")
    public String editGlr(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);

            // 保存的对象集合 json格式
            String listdata = request.getParameter("data");
            JSONArray jArray = JSONArray.fromObject(listdata);
            Collection collection = JSONArray.toCollection(jArray, KqdsUserdocument.class);
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                KqdsUserdocument extension = (KqdsUserdocument) it.next();
                KqdsUserdocument en = (KqdsUserdocument) logic.loadObjSingleUUID(TableNameUtil.KQDS_USERDOCUMENT,
                        extension.getSeqId());
                en.setGlr(extension.getGlr());
                en.setGlrremark(extension.getGlrremark());
                en.setXgtime(YZUtility.getCurDateTimeStr());
                en.setXgr(person.getSeqId());
                // 保存记录日志
                KqdsChangeWd wd = new KqdsChangeWd();
                wd.setSeqId(YZUtility.getUUID());
                wd.setCreatetime(YZUtility.getCurDateTimeStr());
                wd.setCreateuser(person.getSeqId());
                wd.setOldper(en.getCreateuser());
                wd.setToper(extension.getGlr());
                wd.setRemark(extension.getGlrremark());
                wd.setUsercode(en.getUsercode());
                wd.setUsername(en.getUsername());
                wd.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
                logic.setWd(wd, en);
                // 记录日志
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_WD, wd, wd.getUsercode(),
                        TableNameUtil.KQDS_CHANGE_WD, request);
            }
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, true, ex, response, logger);
        }
        return null;
    }

    /**
     * 下载模板
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/downTemplet.act")
    public void downTemplet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        File f = new File(ConstUtil.ROOT_DIR + "\\model\\批量报备模板.xls");
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(("批量报备模板" + ".xls").getBytes(), "iso-8859-1"));// 下载文件的名称
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(f));
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }

    /**
     * 重新获取所有患者的实收金额
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSsje.act")
    public String getSsje(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // String organization = ChainUtil.getCurrentOrganization(request);
            String usercodeString = request.getParameter("usercode");
            if (YZUtility.isNullorEmpty(usercodeString)) {
                usercodeString = logic.getSfuser();
            }
            if (YZUtility.isNullorEmpty(usercodeString)) {
                throw new Exception("无收费的患者");
            }
            String[] usercodeArr = usercodeString.split(",");
            Map map = new HashMap<String, String>();
            for (String usercode : usercodeArr) {
                // 获取usercode的实收金额
                String ssje = logic.getSsje(usercode);
                map.put("usercode", usercode);
                List<KqdsUserdocument> list1 = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT,
                        map);
                if (list1 == null || list1.isEmpty()) {
                    throw new Exception("患者不存在");
                }
                KqdsUserdocument entity = list1.get(0);
                entity.setTotalpay(new BigDecimal(ssje));
                logic.updateSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, entity);
            }
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, true, ex, response, logger);
        }

        return null;
    }

    /**
     * 根据患者编号查询基础信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBaseUserInfoByUsercode.act")
    public String getBaseUserInfoByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String usercode = request.getParameter("usercode");
            JSONObject rtjson = logic.getBaseUserInfoByUsercode(usercode);
            YZUtility.DEAL_SUCCESS(rtjson, null, response, logger);

        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**********************************************************
     * 从kqds_net_order整合过来的方法
     *********************************************************************/

    /**
     * 网电预约 (客服中心-客户管理) 不分页 ### 精确查询
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/jingQueChaXun4Net.act")
    public String jingQueChaXun4Net(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String queryinput = request.getParameter("queryinput");
            String queryInputName = request.getParameter("queryInputName");
            if (!YZUtility.isNullorEmpty(queryinput) && queryinput.length() < 8) {
                throw new Exception("请输入精确查询条件，手机号码长度不能小于8");
            }
            if (!YZUtility.isNullorEmpty(queryInputName) && queryInputName.length() < 2) {
                throw new Exception("请输入精确查询条件，姓名长度不能小于2");
            }
            List<JSONObject> list = logic.jingQueChaXun4Net(queryinput, queryInputName);
            YZUtility.RETURN_LIST(list, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * 网电预约 -客户管理列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userManger4Wdzx.act")
    public String userManger4Wdzx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            String jdtime1 = request.getParameter("jdtime1");
            String jdtime2 = request.getParameter("jdtime2");
            String yewu = request.getParameter("yewu");
            String shouli = request.getParameter("shouli");
            String gongju = request.getParameter("gongju");
            String yytime1 = request.getParameter("yytime1");
            String yytime2 = request.getParameter("yytime2");
            String xiangmu = request.getParameter("xiangmu");
            String level = request.getParameter("level");
            String important = request.getParameter("important");
            String queryinput = request.getParameter("queryinput");
            String doorstatus = request.getParameter("doorstatus");
            String cjstatus = request.getParameter("cjstatus");
            String devchannel = request.getParameter("devchannel");
            String nexttype = request.getParameter("nexttype");
            String organization = request.getParameter("organization"); // 门诊编号

            // 导出参数
            String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
            String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
            String fieldnameArr = request.getParameter("fieldnameArr") == null ? ""
                    : request.getParameter("fieldnameArr");
            Map<String, String> map = new HashMap<String, String>();
            if (!YZUtility.isNullorEmpty(jdtime1)) {
                map.put("jdtime1", jdtime1 + ConstUtil.TIME_START);
            }
            if (!YZUtility.isNullorEmpty(jdtime2)) {
                map.put("jdtime2", jdtime2 + ConstUtil.TIME_END);
            }
            if (!YZUtility.isNullorEmpty(yewu)) {
                map.put("yewu", yewu);
            }
            if (!YZUtility.isNullorEmpty(shouli)) {
                map.put("shouli", shouli);
            }
            if (!YZUtility.isNullorEmpty(gongju)) {
                map.put("gongju", gongju);
            }
            if (!YZUtility.isNullorEmpty(devchannel)) {
                map.put("devchannel", devchannel);
            }
            if (!YZUtility.isNullorEmpty(nexttype)) {
                map.put("nexttype", nexttype);
            }
            if (!YZUtility.isNullorEmpty(yytime1)) {
                // 如果是精确到时分秒的，则去掉后面两位秒数值
                if (yytime1.length() == 19) {
                    yytime1 = yytime1.substring(0, yytime1.length() - 3);
                }

                // 如果是精确到天的，则加上 时分
                if (yytime1.length() == 10) {
                    yytime1 = yytime1 + ConstUtil.HOUR_START;
                }
                map.put("yytime1", yytime1);
            }
            if (!YZUtility.isNullorEmpty(yytime2)) {
                // 如果是精确到时分秒的，则去掉后面两位秒数值
                if (yytime2.length() == 19) {
                    yytime2 = yytime2.substring(0, yytime2.length() - 3);
                }

                // 如果是精确到天的，则加上 时分
                if (yytime2.length() == 10) {
                    yytime2 = yytime2 + ConstUtil.HOUR_END;
                }

                map.put("yytime2", yytime2);
            }
            if (!YZUtility.isNullorEmpty(xiangmu)) {
                map.put("xiangmu", xiangmu);
            }
            if (!YZUtility.isNullorEmpty(level)) {
                map.put("level", level);
            }
            if (!YZUtility.isNullorEmpty(important)) {
                map.put("important", important);
            }
            if (!YZUtility.isNullorEmpty(doorstatus)) {
                map.put("doorstatus", doorstatus);
            }
            if (!YZUtility.isNullorEmpty(cjstatus)) {
                map.put("cjstatus", cjstatus);
            }
            if (!YZUtility.isNullorEmpty(queryinput)) {
                map.put("queryinput", queryinput);
            }
            /**
             * 2019.07.18 lwg 网店咨询 客户管理点击
             */
            if (!YZUtility.isNullorEmpty(sortName)) {
                map.put("sortName", sortName);
            }
            if (!YZUtility.isNullorEmpty(sortOrder)) {
                map.put("sortOrder", sortOrder);
            }
            /**
             * end
             */
            // 可见人员过滤
            String visualstaff = SessionUtil.getVisualstaff(request);
            // 网电只查网电建档的 或者辅助角色为网电的
            String otherpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_WANGDIAN_SEQID);
            visualstaff = personLogic.filterVisualPersons(ConstUtil.DEPT_TYPE_2, visualstaff, otherpriv); // 部门类型
            if ("''".equals(visualstaff)) {
                // 如果网电1的所在部门没有设定为 网电类型，那么此处可见人员是空值'',而glr也存空值,就会查出来记录
                visualstaff = "'-1'";
                // 此时让查不到
            }
            // 2
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            String today = formatter2.format(new Date());
            /**
             * 2019.07.13 lwg 添加后端分页功能
             */
            // 初始化分页实体类
            @SuppressWarnings("rawtypes")
            JSONObject list = new JSONObject();
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());
            if (sortName == null && sortOrder != null && jdtime1.equals("") && jdtime2.equals("") && yewu.equals("")
                    && shouli.equals("") && gongju.equals("") && yytime1.equals("") && yytime2.equals("")
                    && xiangmu.equals("") && level == null && important.equals("") && queryinput.equals("")
                    && doorstatus.equals("") && cjstatus.equals("") && devchannel.equals("") && nexttype.equals("")) {// map为空
                // 即什么条件都没有
                // 查询
                // 建档日期或者预约日期为今天
                Map<String, String> map2 = new HashMap<String, String>();
                // 查询 建档日期为今天的记录
                map2.put("jdtime1", today + ConstUtil.TIME_START);
                map2.put("jdtime2", today + ConstUtil.TIME_END);
                map2.put("mryytime1", today + ConstUtil.TIME_START);
                list = logic.userManger4Wdzx(TableNameUtil.KQDS_NET_ORDER, person, map2, visualstaff, organization, bp);
            } else if (sortName != null && sortOrder != null && jdtime1.equals("") && jdtime2.equals("")
                    && yewu.equals("") && shouli.equals("") && gongju.equals("") && yytime1.equals("")
                    && yytime2.equals("") && xiangmu.equals("") && level == null && important.equals("")
                    && queryinput.equals("") && doorstatus.equals("") && cjstatus.equals("") && devchannel.equals("")
                    && nexttype.equals("")) {
                Map<String, String> map2 = new HashMap<String, String>();
                // 查询 建档日期为今天的记录
                map2.put("jdtime1", today + ConstUtil.TIME_START);
                map2.put("jdtime2", today + ConstUtil.TIME_END);
                map2.put("mryytime1", today + ConstUtil.TIME_START);
                map2.put("sortName", sortName);
                map2.put("sortOrder", sortOrder);
                list = logic.userManger4Wdzx(TableNameUtil.KQDS_NET_ORDER, person, map2, visualstaff, organization, bp);
            } else {
                list = logic.userManger4Wdzx(TableNameUtil.KQDS_NET_ORDER, person, map, visualstaff, organization, bp);
            }
            /*-------导出excel---------*/
            if (flag != null && flag.equals("exportTable")) {
                ExportTable.exportBootStrapTable2Excel("网电中心-客户列表", fieldArr, fieldnameArr, list.getJSONArray("rows"),
                        response, request);
                return null;
            }
            // YZUtility.RETURN_LIST(list, response, logger);
            YZUtility.DEAL_SUCCESS(list, null, response, logger);
            /**
             * end
             */

        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }
        return null;
    }

    /**
     * 客服中心 -客户管理列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userManger4Kfzx.act")
    public String userManger4Kfzx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            JSONObject list = new JSONObject();
            YZPerson person = SessionUtil.getLoginPerson(request);
            String jdtime1 = request.getParameter("jdtime1");
            String jdtime2 = request.getParameter("jdtime2");
            String yewu = request.getParameter("yewu");
            String devchannel = request.getParameter("devchannel");
            String nexttype = request.getParameter("nexttype");
            String shouli = request.getParameter("shouli");
            String gongju = request.getParameter("gongju");
            String yytime1 = request.getParameter("yytime1");
            String yytime2 = request.getParameter("yytime2");
            String xiangmu = request.getParameter("xiangmu");
            String level = request.getParameter("level");
            String important = request.getParameter("important");
            String queryinput = request.getParameter("queryinput");
            String doorstatus = request.getParameter("doorstatus");
            String cjstatus = request.getParameter("cjstatus");
            String ywhf = request.getParameter("ywhf");
            String customer = request.getParameter("customer");
            String consumer = request.getParameter("consumer");
            String organization = ChainUtil.getOrganizationFromUrlCanNull(request); // 门诊编号
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");

            // 增加患者标签高级查询条件
            // 第一咨询
            String askperson = request.getParameter("askperson");
            String needOne = request.getParameter("needOne");
            String societyOne = request.getParameter("societyOne");
            String expenseOne = request.getParameter("expenseOne");
            String needTwo = request.getParameter("needTwo");
            String societyTwo = request.getParameter("societyTwo");
            String expenseTwo = request.getParameter("expenseTwo");
            String needThree = request.getParameter("needThree");
            String societyThree = request.getParameter("societyThree");
            String expenseThree = request.getParameter("expenseThree");
            String jdtimelabel1 = request.getParameter("jdtimelabel1");
            String jdtimelabel2 = request.getParameter("jdtimelabel2");
            String starsLevelTwo = request.getParameter("starsLevelTwo");
            String unsatisfiedTwo = request.getParameter("unsatisfiedTwo");
            String starsLevelThree = request.getParameter("starsLevelThree");
            // end

            // 导出参数
            String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
            String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
            String fieldnameArr = request.getParameter("fieldnameArr") == null ? ""
                    : request.getParameter("fieldnameArr");
            Map<String, String> map = new HashMap<String, String>();
            JSONObject json = new JSONObject();// 用于根据选择患者指定标签查询
            String labelID = "";
            /**
             * 增加患者标签高级查询条件 start
             */
            if (!YZUtility.isNullorEmpty(needOne)) {
                // json.put("needOne", needOne);
                labelID += needOne + ",";
            }
            if (!YZUtility.isNullorEmpty(societyOne)) {
                // json.put("societyOne", societyOne);
                labelID += societyOne + ",";
            }
            if (!YZUtility.isNullorEmpty(expenseOne)) {
                // json.put("expenseOne", expenseOne);
                labelID += expenseOne + ",";
            }
            if (!YZUtility.isNullorEmpty(needTwo)) {
                // List<String> needTwolist =
                // YZUtility.ConvertString2List(needTwo);
                // json.put("needTwo", needTwo);
                labelID += needTwo + ",";
            }
            if (!YZUtility.isNullorEmpty(societyTwo)) {
                // List<String> societyTwoList =
                // YZUtility.ConvertString2List(societyTwo);
                // json.put("societyTwo", societyTwo);
                labelID += societyTwo + ",";
            }
            if (!YZUtility.isNullorEmpty(expenseTwo)) {
                // List<String> expenseTwoList =
                // YZUtility.ConvertString2List(expenseTwo);
                // json.put("expenseTwo", expenseTwo);
                labelID += expenseTwo + ",";
            }
            if (!YZUtility.isNullorEmpty(needThree)) {
                // List<String> needThreeList =
                // YZUtility.ConvertString2List(needThree);
                // json.put("needThree", needThree);
                labelID += needThree + ",";
            }
            if (!YZUtility.isNullorEmpty(societyThree)) {
                // List<String> societyThreeList =
                // YZUtility.ConvertString2List(societyThree);
                // json.put("societyThree", societyThree);
                labelID += societyThree + ",";
            }
            if (!YZUtility.isNullorEmpty(expenseThree)) {
                // List<String> expenseThreeList =
                // YZUtility.ConvertString2List(expenseThree);
                // json.put("expenseThree", expenseThree);
                labelID += expenseThree + ",";
            }
            if (!YZUtility.isNullorEmpty(askperson)) {
                // List<String> expenseThreeList =
                // YZUtility.ConvertString2List(expenseThree);
                // json.put("askperson", askperson);
                labelID += askperson + ",";
            }
            if (!YZUtility.isNullorEmpty(starsLevelThree)) {
                // List<String> expenseThreeList =
                // YZUtility.ConvertString2List(expenseThree);
                // json.put("starsLevelThree", starsLevelThree);
                labelID += starsLevelThree + ",";
            }
            if (!YZUtility.isNullorEmpty(starsLevelTwo)) {
                // List<String> expenseThreeList =
                // YZUtility.ConvertString2List(expenseThree);
                // json.put("starsLevelTwo", starsLevelTwo);
                labelID += starsLevelTwo + ",";
            }
            if (!YZUtility.isNullorEmpty(unsatisfiedTwo)) {
                // List<String> expenseThreeList =
                // YZUtility.ConvertString2List(expenseThree);
                // json.put("unsatisfiedTwo", unsatisfiedTwo);
                labelID += unsatisfiedTwo + ",";
            }
            if (!YZUtility.isNullorEmpty(jdtimelabel1)) {
                map.put("jdtime1", jdtimelabel1 + ConstUtil.TIME_START);
            }
            if (!YZUtility.isNullorEmpty(jdtimelabel2)) {
                map.put("jdtime2", jdtimelabel2 + ConstUtil.TIME_END);
            }
            /**
             * end
             */
            if (!YZUtility.isNullorEmpty(consumer)) {
                map.put("consumer", consumer);
            }
            if (!YZUtility.isNullorEmpty(customer)) {
                map.put("customer", customer);
            }
            if (!YZUtility.isNullorEmpty(jdtime1)) {
                map.put("jdtime1", jdtime1 + ConstUtil.TIME_START);
            }
            if (!YZUtility.isNullorEmpty(jdtime2)) {
                map.put("jdtime2", jdtime2 + ConstUtil.TIME_END);
            }
            if (!YZUtility.isNullorEmpty(yewu)) {
                map.put("yewu", yewu);
            }
            if (!YZUtility.isNullorEmpty(shouli)) {
                map.put("shouli", shouli);
            }
            if (!YZUtility.isNullorEmpty(gongju)) {
                map.put("gongju", gongju);
            }
            if (!YZUtility.isNullorEmpty(devchannel)) {
                map.put("devchannel", devchannel);
            }
            if (!YZUtility.isNullorEmpty(nexttype)) {
                map.put("nexttype", nexttype);
            }
            if (!YZUtility.isNullorEmpty(yytime1)) {
                // 如果是精确到天的，则加上 时分
                if (yytime1.length() == 10) {
                    yytime1 = yytime1 + ConstUtil.TIME_START;
                }
                map.put("yytime1", yytime1);
            }
            if (!YZUtility.isNullorEmpty(yytime2)) {
                // 如果是精确到天的，则加上 时分
                if (yytime2.length() == 10) {
                    yytime2 = yytime2 + ConstUtil.TIME_END;
                }

                map.put("yytime2", yytime2);
            }
            if (!YZUtility.isNullorEmpty(xiangmu)) {
                map.put("xiangmu", xiangmu);
            }
            if (!YZUtility.isNullorEmpty(level)) {
                map.put("level", level);
            }
            if (!YZUtility.isNullorEmpty(important)) {
                map.put("important", important);
            }
            if (!YZUtility.isNullorEmpty(doorstatus)) {
                map.put("doorstatus", doorstatus);
            }
            if (!YZUtility.isNullorEmpty(cjstatus)) {
                map.put("cjstatus", cjstatus);
            }
            if (!YZUtility.isNullorEmpty(queryinput)) {
                map.put("queryinput", queryinput);
            }
            if (!YZUtility.isNullorEmpty(sortName)) {
                map.put("sortName", sortName);
                map.put("sortOrder", sortOrder);
            }
            double time1 = 0;
            double time2 = 0;
            if (!YZUtility.isNullorEmpty(jdtimelabel1) && !YZUtility.isNullorEmpty(jdtimelabel2)) {
                // 根据时间查询所有的标签id
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = format.parse(map.get("jdtime1"));
                // 日期转时间戳（毫秒）
                time1 = date1.getTime();
                Date date2 = format.parse(map.get("jdtime2"));
                // 日期转时间戳（毫秒）
                time2 = date2.getTime();

            }
            if (!YZUtility.isNullorEmpty(jdtimelabel1) && YZUtility.isNullorEmpty(jdtimelabel2)) {
                // 根据时间查询所有的标签id
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = format.parse(map.get("jdtime1"));
                // 日期转时间戳（毫秒）
                time1 = date1.getTime();
                time2 = System.currentTimeMillis();
            }
            if (YZUtility.isNullorEmpty(jdtimelabel1) && !YZUtility.isNullorEmpty(jdtimelabel2)) {
                // 根据时间查询所有的标签id
                time1 = System.currentTimeMillis();
                // 根据时间查询所有的标签id
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date2 = format.parse(map.get("jdtime2"));
                // 日期转时间戳（毫秒）
                time2 = date2.getTime();
            }
            // if(time1==0&&time2==0&&labelID.equals("")){
            // //根据时间查询所有的标签id
            // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
            // HH:mm:ss");
            // String date = YZUtility.getDateStr(new Date());
            // date = date + ConstUtil.TIME_START;
            // Date date2 = format.parse(date);
            // time1=date2.getTime();
            // time2=System.currentTimeMillis();
            // }

            // 可见人员过滤
            String visualstaff = SessionUtil.getVisualstaff(request);
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            String today = formatter2.format(new Date());
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());
            if (map.isEmpty()) {// map为空 即什么条件都没有 查询 建档日期或者预约日期为今天
                Map<String, String> map2 = new HashMap<String, String>();
                // 查询 建档日期为今天的记录
                map2.put("jdtime1", today + ConstUtil.TIME_START);
                map2.put("jdtime2", today + ConstUtil.TIME_END);
                map2.put("mryytime1", today + ConstUtil.TIME_START);
                list = logic.userManger4Kfzx(TableNameUtil.KQDS_NET_ORDER, person, map2, ywhf, visualstaff,
                        organization, bp, json, time1, time2, labelID);
            } else if (sortName != null && sortOrder != null && jdtime1.equals("") && jdtime2.equals("")
                    && yewu.equals("") && shouli.equals("") && gongju.equals("") && yytime1.equals("")
                    && yytime2.equals("") && xiangmu.equals("") && level == null && important.equals("")
                    && queryinput.equals("") && doorstatus.equals("") && cjstatus.equals("") && devchannel.equals("")
                    && nexttype.equals("") && customer.equals("") && consumer == null) {
                Map<String, String> map2 = new HashMap<String, String>();
                // 查询 建档日期为今天的记录
                map2.put("jdtime1", today + ConstUtil.TIME_START);
                map2.put("jdtime2", today + ConstUtil.TIME_END);
                map2.put("mryytime1", today + ConstUtil.TIME_START);
                map2.put("sortName", sortName);
                map2.put("sortOrder", sortOrder);
                list = logic.userManger4Kfzx(TableNameUtil.KQDS_NET_ORDER, person, map2, ywhf, visualstaff,
                        organization, bp, json, time1, time2, labelID);
            } else {
                list = logic.userManger4Kfzx(TableNameUtil.KQDS_NET_ORDER, person, map, ywhf, visualstaff, organization,
                        bp, json, time1, time2, labelID);
            }
            /*-------导出excel---------*/
            if (flag != null && flag.equals("exportTable")) {
                List<JSONObject> da = list.getJSONArray("rows");
                if (da.size() > 0) {
                    for (JSONObject job : da) {
                        if ("1".equals(job.getString("cjstatus"))) {
                            job.put("cjstatus", "已成交");
                        } else {
                            job.put("cjstatus", "未成交");
                        }

                        if ("1".equals(job.getString("zdoorstatus"))) {
                            job.put("zdoorstatus", "已上门");
                        } else {
                            job.put("zdoorstatus", "未上门");
                        }
                        if ("1".equals(job.getString("doorstatus"))) {
                            job.put("doorstatus", "已上门");
                        } else {
                            job.put("doorstatus", "未上门");
                        }
                        if ("0".equals(job.getString("ywhf"))) {
                            job.put("ywhf", "无回访");
                        } else {
                            job.put("ywhf", "有回访");
                        }
                    }
                }

                ExportTable.exportBootStrapTable2Excel("客服中心-客户列表", fieldArr, fieldnameArr, da, response, request);
                return null;
            }
            // YZUtility.RETURN_LIST(list, response, logger);
            YZUtility.DEAL_SUCCESS(list, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }
        return null;
    }

    /**
     * 营销中心 -客户管理列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userManger4Yxzx.act")
    public String userManger4Yxzx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);
            String jdtime1 = request.getParameter("jdtime1");
            String jdtime2 = request.getParameter("jdtime2");
            String yewu = request.getParameter("yewu");
            String devchannel = request.getParameter("devchannel");
            String nexttype = request.getParameter("nexttype");
            String shouli = request.getParameter("shouli");
            String gongju = request.getParameter("gongju");
            String yytime1 = request.getParameter("yytime1");
            String yytime2 = request.getParameter("yytime2");
            String xiangmu = request.getParameter("xiangmu");
            String level = request.getParameter("level");
            String important = request.getParameter("important");
            String queryinput = request.getParameter("queryinput");
            String doorstatus = request.getParameter("doorstatus");
            String cjstatus = request.getParameter("cjstatus");
            String organization = ChainUtil.getOrganizationFromUrlCanNull(request); // 门诊编号
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            // 导出参数
            String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
            String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
            String fieldnameArr = request.getParameter("fieldnameArr") == null ? ""
                    : request.getParameter("fieldnameArr");
            Map<String, String> map = new HashMap<String, String>();
            if (!YZUtility.isNullorEmpty(jdtime1)) {
                map.put("jdtime1", jdtime1 + ConstUtil.TIME_START);
            }
            if (!YZUtility.isNullorEmpty(jdtime2)) {
                map.put("jdtime2", jdtime2 + ConstUtil.TIME_END);
            }
            if (!YZUtility.isNullorEmpty(yewu)) {
                map.put("yewu", yewu);
            }
            if (!YZUtility.isNullorEmpty(devchannel)) {
                map.put("devchannel", devchannel);
            }
            if (!YZUtility.isNullorEmpty(nexttype)) {
                map.put("nexttype", nexttype);
            }
            if (!YZUtility.isNullorEmpty(shouli)) {
                map.put("shouli", shouli);
            }
            if (!YZUtility.isNullorEmpty(gongju)) {
                map.put("gongju", gongju);
            }
            if (!YZUtility.isNullorEmpty(yytime1)) {
                // 如果是精确到时分秒的，则去掉后面两位秒数值
                if (yytime1.length() == 19) {
                    yytime1 = yytime1.substring(0, yytime1.length() - 3);
                }

                // 如果是精确到天的，则加上 时分
                if (yytime1.length() == 10) {
                    yytime1 = yytime1 + ConstUtil.HOUR_START;
                }
                map.put("yytime1", yytime1);
            }
            if (!YZUtility.isNullorEmpty(yytime2)) {
                // 如果是精确到时分秒的，则去掉后面两位秒数值
                if (yytime2.length() == 19) {
                    yytime2 = yytime2.substring(0, yytime2.length() - 3);
                }

                // 如果是精确到天的，则加上 时分
                if (yytime2.length() == 10) {
                    yytime2 = yytime2 + ConstUtil.HOUR_END;
                }

                map.put("yytime2", yytime2);
            }
            if (!YZUtility.isNullorEmpty(xiangmu)) {
                map.put("xiangmu", xiangmu);
            }
            if (!YZUtility.isNullorEmpty(level)) {
                map.put("level", level);
            }
            if (!YZUtility.isNullorEmpty(important)) {
                map.put("important", important);
            }
            if (!YZUtility.isNullorEmpty(doorstatus)) {
                map.put("doorstatus", doorstatus);
            }
            if (!YZUtility.isNullorEmpty(cjstatus)) {
                map.put("cjstatus", cjstatus);
            }
            if (!YZUtility.isNullorEmpty(queryinput)) {
                map.put("queryinput", queryinput);
            }
            if (!YZUtility.isNullorEmpty(sortName)) {
                map.put("sortName", sortName);
                map.put("sortOrder", sortOrder);
            }
            // 可见人员过滤
            String visualstaff = SessionUtil.getVisualstaff(request);
            // 营销只查网电建档的 或者辅助角色为网电的
            String otherpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHICHANG_SEQID);
            visualstaff = personLogic.filterVisualPersons(ConstUtil.DEPT_TYPE_3, visualstaff, otherpriv); // 部门类型
            if ("''".equals(visualstaff)) {
                /** 如果网电1的所在部门没有设定为 网电类型，那么此处可见人员是空值'',而glr也存空值,就会查出来记录 **/
                visualstaff = "'-1'";
                /** 此时让查不到 **/
            }
            JSONObject list = new JSONObject();
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            String today = formatter2.format(new Date());
            if (map.isEmpty()) {// map为空 即什么条件都没有 查询 建档日期或者预约日期为今天
                Map<String, String> map2 = new HashMap<String, String>();
                // 查询 建档日期为今天的记录
                map2.put("jdtime1", today + ConstUtil.TIME_START);
                map2.put("jdtime2", today + ConstUtil.TIME_END);
                map2.put("mryytime1", today + ConstUtil.TIME_START);
                list = logic.userManger4Yxzx(TableNameUtil.KQDS_NET_ORDER, person, map2, visualstaff, organization, bp);
            } else if (sortName != null && sortOrder != null && jdtime1.equals("") && jdtime2.equals("")
                    && yewu.equals("") && devchannel.equals("") && nexttype.equals("") && shouli.equals("")
                    && gongju.equals("") && yytime1.equals("") && yytime2.equals("") && xiangmu.equals("")
                    && level == null && important.equals("") && queryinput.equals("") && doorstatus.equals("")
                    && cjstatus.equals("")) {
                Map<String, String> map2 = new HashMap<String, String>();
                // 查询 建档日期为今天的记录
                map2.put("jdtime1", today + ConstUtil.TIME_START);
                map2.put("jdtime2", today + ConstUtil.TIME_END);
                map2.put("mryytime1", today + ConstUtil.TIME_START);
                map2.put("sortName", sortName);
                map2.put("sortOrder", sortOrder);
                list = logic.userManger4Yxzx(TableNameUtil.KQDS_NET_ORDER, person, map2, visualstaff, organization, bp);
            } else {
                list = logic.userManger4Yxzx(TableNameUtil.KQDS_NET_ORDER, person, map, visualstaff, organization, bp);
            }
            /*-------导出excel---------*/
            if (flag != null && flag.equals("exportTable")) {
                ExportTable.exportBootStrapTable2Excel("营销中心-客户列表", fieldArr, fieldnameArr, list.getJSONArray("rows"),
                        response, request);
                return null;
            }
            // YZUtility.RETURN_LIST(list, response, logger);
            YZUtility.DEAL_SUCCESS(list, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }
        return null;
    }

    /*
     * 分页查询
     */
    @RequestMapping(value = "/selectPage.act")
    public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            BootStrapPage bp = new BootStrapPage();
            BeanUtils.populate(bp, request.getParameterMap());
            String createuser = request.getParameter("createuser");
            String deptparentId = request.getParameter("deptparentId");
            String deptId = request.getParameter("deptId");
            String username = request.getParameter("username");
            String sjhm = request.getParameter("sjhm");
            String starttime = request.getParameter("starttime");
            String endtime = request.getParameter("endtime");
            String visualstaff = SessionUtil.getVisualstaff(request);
            Map<String, String> map = new HashMap<String, String>();
            if (!YZUtility.isNullorEmpty(createuser)) {
                map.put("createuser", createuser);
            }
            if (!YZUtility.isNullorEmpty(starttime)) {
                map.put("starttime", starttime + ConstUtil.TIME_START);
            }
            if (!YZUtility.isNullorEmpty(endtime)) {
                map.put("endtime", endtime + ConstUtil.TIME_END);
            }
            if (!YZUtility.isNullorEmpty(deptparentId)) {
                map.put("deptparentId", deptparentId);
            }
            if (!YZUtility.isNullorEmpty(deptId)) {
                map.put("deptId", deptId);
            }
            if (!YZUtility.isNullorEmpty(username)) {
                map.put("username", username);
            }
            if (!YZUtility.isNullorEmpty(sjhm)) {
                map.put("sjhm", sjhm);
            }
            if (!YZUtility.isNullorEmpty(visualstaff)) {
                map.put("visualstaff", visualstaff);
            }
            JSONObject data = logic.selectWithPage(bp, map);
            YZUtility.DEAL_SUCCESS(data, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }
        return null;
    }

    /*******************************************
     * 微信相关
     ************************************************/

    /**
     * 根据手机号码获取患者编号
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUsercodeByPhonenumber.act")
    public String getUsercodeByPhonenumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String phonenumber = request.getParameter("phonenumber");
            if (YZUtility.isNullorEmpty(phonenumber)) {
                throw new Exception("手机号码不能为空");
            }
            JSONObject jobj = logic.getusercodeBYsjhm(YZAuthenticator.encryKqdsPhonenumber(phonenumber));
            YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    /**
     * @Title: toWdzxGjd
     * @Description: TODO(跳转修改建档)
     * @param: @param
     * request
     * @param: @param
     * response
     * @param: @return
     * @return: String
     * @dateTime:2019年8月17日 下午7:00:10
     */
    @RequestMapping("/toWdzx_xgjdr.act")
    public ModelAndView toWdzxGjd(HttpServletRequest request, HttpServletResponse response) {
        String[] selectedrows = request.getParameterValues("selectedrows");
        ModelAndView mv = new ModelAndView();
        mv.addObject(selectedrows);
        mv.setViewName("/kqdsFront/index/kfzx/wdzx_jdr_set.jsp");
        return mv;
    }

    /**
     * @throws Exception
     * @Title: updateLabel
     * @Description: TODO(修改患者标签)
     * @param: @return
     * @return: String
     * @dateTime:2019年8月23日 下午5:50:49
     */
    @RequestMapping("/updateLabel.act")
    public String updateLabel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String labelAllArr = request.getParameter("labelAllArr");
        String exploit = request.getParameter("exploit");
        String exploit1 = request.getParameter("exploit1");
        String exploitId = request.getParameter("exploitId");
        String exploitId1 = request.getParameter("exploitId1");
        String organization = ChainUtil.getCurrentOrganization(request);
        try {
            YZPerson person = SessionUtil.getLoginPerson(request);
            List<kqdsHzLabellabeAndPatient> labelAllList = null;
            if (labelAllArr != null) {
                labelAllArr = java.net.URLDecoder.decode(labelAllArr, "UTF-8");
                labelAllList = HUDHUtil.parseJsonToObjectList(labelAllArr, kqdsHzLabellabeAndPatient.class);
            }
            if (labelAllList != null) {
                String labelID = "";
                String labelName1 = "";
                String labelName2 = "";
                String labelName3 = "";

                // if(logic.findLabel(userCode) != null){
                logic.deleteLabel(userCode);
                // }
                kqdsHzLabellabeAndPatient kPatient = new kqdsHzLabellabeAndPatient();
                for (kqdsHzLabellabeAndPatient kPatien : labelAllList) {
                    String id = YZUtility.getUUID();
                    kPatient.setSeqId(id);
                    kPatient.setUserId(userCode);
                    kPatient.setUserName(userName);
                    kPatient.setLabelOneId(kPatien.getLabelOneId());
                    kPatient.setLabelOneName(kPatien.getLabelOneName());
                    kPatient.setLabelTwoId(kPatien.getLabelTwoId());
                    kPatient.setLabelTwoName(kPatien.getLabelTwoName());
                    kPatient.setOpinion(kPatien.getOpinion());
                    if (kPatien.getLabelThreeId() == null) {
                        kPatien.setLabelThreeId("00165d45-650b-4394-9768-4482a0ca9b05");
                    }
                    kPatient.setLabelThreeId(kPatien.getLabelThreeId());
                    kPatient.setOpinion(kPatien.getOpinion());
                    kPatient.setLabelThreeName(kPatien.getLabelThreeName());
                    kPatient.setCreateUser(person.getSeqId());
                    kPatient.setCreateTime(YZUtility.getCurDateTimeStr());
                    kPatient.setOrganization(organization);
                    logic.saveKpatient(kPatient);
                    if (!labelID.contains(kPatien.getLabelOneId())) {
                        labelID += kPatien.getLabelOneId() + ",";
                    }
                    if (!labelID.contains(kPatien.getLabelTwoId())) {
                        labelID += kPatien.getLabelTwoId() + ",";
                    }
                    if (!labelID.contains(kPatien.getLabelThreeId())) {
                        labelID += kPatien.getLabelThreeId() + ",";
                    }
                    if (labelName1.equals("")) {
                        labelName1 = kPatien.getLabelOneName() + ":" + kPatien.getLabelTwoName() + ":"
                                + kPatien.getLabelThreeName();
                    } else {
                        if (labelName1.contains(kPatien.getLabelOneName())) {
                            if (labelName1.contains(kPatien.getLabelTwoName())) {
                                labelName1 += "," + kPatien.getLabelThreeName();
                            } else {
                                labelName1 += ";" + kPatien.getLabelTwoName() + ":" + kPatien.getLabelThreeName();
                            }
                        } else {
                            if (labelName2.equals("")) {
                                labelName2 = kPatien.getLabelOneName() + ":" + kPatien.getLabelTwoName() + ":"
                                        + kPatien.getLabelThreeName();
                            } else {
                                if (labelName2.contains(kPatien.getLabelOneName())) {
                                    if (labelName2.contains(kPatien.getLabelTwoName())) {
                                        labelName2 += "," + kPatien.getLabelThreeName();
                                    } else {
                                        labelName2 += ";" + kPatien.getLabelTwoName() + ":"
                                                + kPatien.getLabelThreeName();
                                    }
                                } else {
                                    if (labelName3.equals("")) {
                                        labelName3 = kPatien.getLabelOneName() + ":" + kPatien.getLabelTwoName() + ":"
                                                + kPatien.getLabelThreeName();
                                    } else {
                                        if (labelName3.contains(kPatien.getLabelOneName())) {
                                            if (labelName3.contains(kPatien.getLabelTwoName())) {
                                                labelName3 += "," + kPatien.getLabelThreeName();
                                            } else {
                                                labelName3 += ";" + kPatien.getLabelTwoName() + ":"
                                                        + kPatien.getLabelThreeName();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!labelID.equals("")) {
                    CacheUtil.openCache();
                    String labelName = "";
                    if (!labelName1.equals("")) {
                        labelName += labelName1 + "。";
                    }
                    if (!labelName2.equals("")) {
                        labelName += labelName2 + "。";
                    }
                    if (!labelName3.equals("")) {
                        labelName += labelName3 + "。";
                    }

                    Map<String, String> key = new HashMap<String, String>();
                    key.put(userCode + "," + labelID, userCode);
                    Map<String, String> key1 = new HashMap<String, String>();
                    key1.put(userCode, labelName);
                    Set<String> c = new HashSet<String>();
                    if (organization.equals("HUDH")) {
                        c = CacheUtil.keys("labelQuery:*" + userCode + "*");
                        if (c.size() > 0) {
                            for (String string : c) {
                                Double time = CacheUtil.getZSetScore("label:key", string.substring(11, string.length()));
                                CacheUtil.delMapKey("label:value", string.substring(11, string.length()));
                                CacheUtil.delMapKey("label:name", userCode);
                                CacheUtil.removeZSet("label:key", string.substring(11, string.length()));
                                CacheUtil.del(string);
                                CacheUtil.setMap("label:value", key);
                                CacheUtil.setMap("label:name", key1);
                                CacheUtil.addZSet("label:key", userCode + "," + labelID, time);
                                CacheUtil.set("labelQuery:" + userCode + "," + labelID, userCode);
                            }

                        } else {
                            CacheUtil.setMap("label:value", key);
                            CacheUtil.setMap("label:name", key1);
                            CacheUtil.addZSet("label:key", userCode + "," + labelID,
                                    Double.parseDouble(System.currentTimeMillis() + ""));
                            CacheUtil.set("labelQuery:" + userCode + "," + labelID, userCode);
                        }
                    } else {
                        c = CacheUtil.keys(organization + ":labelQuery:*" + userCode + "*");
                        if (c.size() > 0) {
                            for (String string : c) {
                                Double time = CacheUtil.getZSetScore(organization + ":label:key", string.substring(16, string.length()));
                                CacheUtil.delMapKey(organization + ":label:value", string.substring(16, string.length()));
                                CacheUtil.delMapKey(organization + ":label:name", userCode);
                                CacheUtil.removeZSet(organization + ":label:key", string.substring(16, string.length()));
                                CacheUtil.del(string);
                                CacheUtil.setMap(organization + ":label:value", key);
                                CacheUtil.setMap(organization + ":label:name", key1);
                                CacheUtil.addZSet(organization + ":label:key", userCode + "," + labelID, time);
                                CacheUtil.set(organization + ":labelQuery:" + userCode + "," + labelID, userCode);
                            }

                        } else {
                            CacheUtil.setMap(organization + ":label:value", key);
                            CacheUtil.setMap(organization + ":label:name", key1);
                            CacheUtil.addZSet(organization + ":label:key", userCode + "," + labelID,
                                    Double.parseDouble(System.currentTimeMillis() + ""));
                            CacheUtil.set(organization + ":labelQuery:" + userCode + "," + labelID, userCode);
                        }
                    }
                    CacheUtil.close();
                }
                if (exploit != null && !exploit.equals("undefined") && exploitId != null) {
                    savePriceList(exploitId, exploit, userName, userCode, "1", person, response);
                }
                if (exploit1 != null && !exploit1.equals("undefined") && exploitId1 != null) {
                    savePriceList(exploitId1, exploit1, userName, userCode, "2", person, response);
                }
                BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, labelAllArr, userCode,
                        TableNameUtil.KQDS_USERDOCUMENT, request);
                YZUtility.DEAL_SUCCESS(null, "保存成功!", response, logger);
            }
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR("保存失败!", false, e, response, logger);
        }
        return null;
    }

    /**
     * 修改患者病历号
     * <p>Title: updateBlcode</p>
     * <p>Description: </p>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author lwg
     * @date 2019年12月3日
     */
    @RequestMapping(value = "/updateBlcode.act")
    public String updateBlcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String usercode = request.getParameter("usercode");
            String blcode = request.getParameter("blcode");
            Map<String, String> map = new HashMap<String, String>();
            if (!YZUtility.isNullorEmpty(usercode)) {
                map.put("usercode", usercode);
            }
            if (!YZUtility.isNullorEmpty(blcode)) {
                map.put("blcode", blcode);
            }
            //查询病历号是否重复
            int num = logic.checkBlcode(null, blcode, TableNameUtil.KQDS_USERDOCUMENT);
            if (num > 0) {
                YZUtility.DEAL_SUCCESS(null, "病历号已存在", response, logger);
            } else {
                int j = logic.updateBlcode(map);
                if (j > 0) {
                    YZUtility.DEAL_SUCCESS(null, "修改成功", response, logger);
                } else {
                    YZUtility.DEAL_ERROR("修改失败", false, null, response, logger);
                }
            }
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }

        return null;
    }

    @RequestMapping(value = "/findByUsercode.act")
    public List<JSONObject> findByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String usercode = request.getParameter("usercode");
            JSONObject list = logic.findByUsercode(usercode);
            YZUtility.RETURN_OBJ(list, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }
        return null;
    }

    /**
     * @param response
     * @Title: updaterecord
     * @Description: [request, response](是否患有传染病)
     * @author admin
     * @param: * @param request
     * @return: java.lang.String
     * @dateTime: 2020/6/30 10:31
     */
    @RequestMapping("/updaterecord.act")
    public String updaterecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            KqdsUserdocument dp = new KqdsUserdocument();
            BeanUtils.populate(dp, request.getParameterMap());
            logic.updaterecord(dp);
            YZUtility.DEAL_SUCCESS(null, "操作成功!", response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR("", false, e, response, logger);
        }
        return null;
    }

    /**
     * @param response
     * @Title: getrecord
     * @Description: [request, response](这里用一句话描述这个方法的作用)
     * @author admin
     * @param: * @param request
     * @return: java.lang.String
     * @dateTime: 2020/6/30 16:52
     */
    @RequestMapping("/getrecord.act")
    public String getrecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usercode = request.getParameter("usercode");
        try {
            JSONObject json = logic.getrecord(usercode);
            YZUtility.DEAL_SUCCESS(json, "", response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR("", false, e, response, logger);
        }

        return null;
    }

    /**
     * 根据时间，模糊查，查询vip为1的患者
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/introducerStatistics.act")
    public String introducerStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String organization = request.getParameter("organization");
            String starttime = request.getParameter("starttime");
            String endtime = request.getParameter("endtime");
            String searchValue = request.getParameter("searchValue");
            Map<String, String> map = new HashMap<String, String>();
            if(!YZUtility.isNullorEmpty(organization)){
                map.put("organization", organization);
            }
            if(!YZUtility.isNullorEmpty(starttime)){
                map.put("starttime", starttime);
            }
            if(!YZUtility.isNullorEmpty(endtime)){
                map.put("endtime", endtime);
            }
            if(!YZUtility.isNullorEmpty(searchValue)){
                map.put("searchValue", searchValue);
            }
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());
            // 导出参数
            String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
            String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
            String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
            /*-------导出excel---------*/
            if (flag != null && flag.equals("exportTable")) {
                JSONObject object = logic.findIntroducer(flag,map, bp);
                if (object != null ) {
                    ExportBean bean = ExportTable.initExcel4RsWrite("费用趋势", fieldArr, fieldnameArr, response, request);
                    bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List<JSONObject>) object.get("rows"), "consumerTrends");
                    ExportTable.writeExcel4DownLoad("费用趋势", bean.getWorkbook(), response);
                }
                return null;
            }
            JSONObject object = logic.findIntroducer(null,map, bp);
            YZUtility.RETURN_OBJ(object, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
        }
        return null;
    }

    /**
     * 根据患者编号和上下级标识查询编号名下的患者
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/introducerStatisticsDetail.act")
    public String introducerStatisticsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String usercode = request.getParameter("usercode");
            String grade = request.getParameter("grade");
            Map<String, String> map = new HashMap<String, String>();
            if(!YZUtility.isNullorEmpty(usercode)){
                map.put("introducer", usercode);
            }
            if(!YZUtility.isNullorEmpty(grade)){
                map.put("grade", grade);
            }
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());
            JSONObject object = logic.findIntroducerDetail(map, bp);
            YZUtility.RETURN_OBJ(object, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
        }
        return null;
    }
    @RequestMapping("/update.act")
    public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
         KqdsUserdocument dp = new KqdsUserdocument();
        YZPerson person = SessionUtil.getLoginPerson(request);
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            BeanUtils.populate(dp,request.getParameterMap());
            logic.update(dp);
            BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_USERDOCUMENT, dp, dp.getUsercode(),
                    TableNameUtil.KQDS_USERDOCUMENT, request);
            YZUtility.DEAL_SUCCESS(null,"补录成功",response,logger);
        }catch (Exception e){
            YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
        }
        return null;
    }
}