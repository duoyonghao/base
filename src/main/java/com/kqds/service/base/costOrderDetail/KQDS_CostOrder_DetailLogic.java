package com.kqds.service.base.costOrderDetail;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.SysParaUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Service
public class KQDS_CostOrder_DetailLogic extends BaseLogic {
    @Autowired
    private DaoSupport dao;
    @Autowired
    private YZDictLogic dictLogic;
    @Autowired
    private YZPersonLogic personLogic;

    /**
     * 根据消费项目编号统计
     *
     * @param conn
     * @param itemnos
     * @return
     * @throws Exception
     */
    public int getCountByItemnos(String itemnos) throws Exception {
        itemnos = YZUtility.ConvertStringIds4Query(itemnos);
        int count = (int) dao.findForObject(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getCountByItemnos", itemnos);
        return count;
    }

    /**
     * 根据当前消费项目的创建时间和欠费编号，判断该费用单是否过期
     *
     * @param createtime
     * @param qfbh
     * @return 0 没过期 1过期了
     * @throws SQLException
     */
    public int judgeIFExpire(String createtime, String qfbh) throws Exception {
        if (YZUtility.isNullorEmpty(qfbh)) {
            return 0;
        }
        /** 只会有一条数据，或者不存在（欠款还清了） **/
        JSONObject lastestQfObj = (JSONObject) dao.findForObject(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getQfItemnos", qfbh);
        if (lastestQfObj == null) {
            return 1;
        }
        /**
         * 每次结账时，会更改最新的真实项目的
         */
        String sftime = lastestQfObj.getString("sftime");
        if (YZUtility.isNullorEmpty(sftime)) {
            return 0; // 没结账的，肯定不是过期项目
        }

        // 如果createtime早于（小于）qfCreatetime，则说明该费用单过期了
        int flag = YZUtility.compare_date(createtime, sftime);
        if (flag == 0) {
            return 1; // 表示该项目过期了
        }
        return 0;
    }

    /**
     * 根据治疗项目表的basetype和nexttype字段，获取对应的描述
     *
     * @param conn
     * @param itemNO
     * @param itemName
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getClassName(String itemNO, String itemName, String organization) throws Exception {

        Map<String, String> cls = new HashMap<String, String>();
        cls.put("basetypeName", "");
        cls.put("nexttypeName", "");

        Map<String, String> filters = new HashMap<String, String>();
        filters.put("treatitemno", itemNO);
        filters.put("treatitemname", itemName);
        filters.put("t.organization", organization);

        List<KqdsTreatitem> itemList = (List<KqdsTreatitem>) dao.loadList(TableNameUtil.KQDS_TREATITEM, filters);

        if (itemList == null || itemList.size() == 0) {

            return cls;
        }

        KqdsTreatitem tItem = itemList.get(0);

        String basetype = tItem.getBasetype();
        String nexttype = tItem.getNexttype();

        String basetypeName = dictLogic.getDictNameBySeqId(basetype);
        String nexttypeName = dictLogic.getDictNameBySeqId(nexttype);

        cls.put("basetypeName", basetypeName);
        cls.put("nexttypeName", nexttypeName);

        return cls;
    }

    // 已结账费用明细
    @SuppressWarnings("unchecked")
    public JSONObject selectNoPage(BootStrapPage bp, String table, YZPerson person, Map<String, String> map, String visualstaff, String organization) throws Exception {
        int firstIndex = bp.getOffset();
        if (map.containsKey("queryinput")) {
            map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
            map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
        }

        map.put("visualstaff", visualstaff);
        map.put("organization", organization);
        if (map.get("sortName") != null) {

            if (map.get("sortName").equals("detailremark")) {
                map.put("sortName", "d.remark");
            }
            if (map.get("sortName").equals("remark")) {
                map.put("sortName", "c.remark");
            }
            if (map.get("sortName").equals("sfuser")) {
                map.put("sortName", "per2.USER_NAME");
            }
            if (map.get("sortName").equals("jdtime")) {
                map.put("sortName", "u.CreateTime");
            }
            if (map.get("sortName").equals("jddy")) {
                map.put("sortName", "per7.USER_NAME");
            }
            if (map.get("sortName").equals("jduser")) {
                map.put("sortName", "per3.USER_NAME");
            }
            if (map.get("sortName").equals("developer")) {
                map.put("sortName", "per6.USER_NAME");
            }
            if (map.get("sortName").equals("introducer")) {
                map.put("sortName", "u2.username");
            }
            if (map.get("sortName").equals("kdtime")) {
                map.put("sortName", "c.createtime");
            }
            if (map.get("sortName").equals("kduser")) {
                map.put("sortName", "per1.USER_NAME");
            }
            if (map.get("sortName").equals("nexttype")) {
                map.put("sortName", "hzly.dict_name");
            }
            if (map.get("sortName").equals("devchannel")) {
                map.put("sortName", "kcit3.dict_name");
            }
            if (map.get("sortName").equals("techperson")) {
                map.put("sortName", "per9.USER_NAME");
            }
            if (map.get("sortName").equals("nurse")) {
                map.put("sortName", "per8.USER_NAME");
            }
            if (map.get("sortName").equals("doctor")) {
                map.put("sortName", "per5.USER_NAME");
            }
            if (map.get("sortName").equals("regdept")) {
                map.put("sortName", "dept1.DEPT_NAME");
            }
            if (map.get("sortName").equals("askperson")) {
                map.put("sortName", "per4.USER_NAME");
            }
            if (map.get("sortName").equals("faskperson")) {
                map.put("sortName", "per10.USER_NAME");
            }
            if (map.get("sortName").equals("cjstatus")) {
                map.put("sortName", "c.cjstatus");
            }
            if (map.get("sortName").equals("regsort")) {
                map.put("sortName", "kcit2.dict_name");
            }
            if (map.get("sortName").equals("recesort")) {
                map.put("sortName", "kcit1.dict_name");
            }
            if (map.get("sortName").equals("payyjj")) {
                map.put("sortName", "d.payyjj");
            }
            if (map.get("sortName").equals("paybank")) {
                map.put("sortName", "d.paybank");
            }
            if (map.get("sortName").equals("payyb")) {
                map.put("sortName", "d.payyb");
            }
            if (map.get("sortName").equals("paywx")) {
                map.put("sortName", "d.paywx");
            }
            if (map.get("sortName").equals("payzfb")) {
                map.put("sortName", "d.payzfb");
            }
            if (map.get("sortName").equals("paymmd")) {
                map.put("sortName", "d.paymmd");
            }
            if (map.get("sortName").equals("paybdfq")) {
                map.put("sortName", "d.paybdfq");
            }
            if (map.get("sortName").equals("payother1")) {
                map.put("sortName", "d.payother1");
            }
            if (map.get("sortName").equals("istsxm")) {
                map.put("sortName", "d.istsxm");
            }
            if (map.get("sortName").equals("y2")) {
                map.put("sortName", "d.y2");
            }
            if (map.get("sortName").equals("payother2")) {
                map.put("sortName", "d.payother2");
            }
            if (map.get("sortName").equals("paydjq")) {
                map.put("sortName", "d.paydjq");
            }
            if (map.get("sortName").equals("payintegral")) {
                map.put("sortName", "d.payintegral");
            }
            if (map.get("sortName").equals("paymoney")) {
                map.put("sortName", "d.paymoney");
            }
            if (map.get("sortName").equals("payxj")) {
                map.put("sortName", "d.payxj");
            }
            if (map.get("sortName").equals("organization")) {
                map.put("sortName", "dept.DEPT_NAME");
            }
            if (map.get("sortName").equals("sftime")) {
                map.put("sortName", "c.sftime");
            }
            if (map.get("sortName").equals("kaifa")) {
                map.put("sortName", "d.kaifa");
            }
            if (map.get("sortName").equals("zltime")) {
                map.put("sortName", "d.zltime");
            }
            if (map.get("sortName").equals("usercode")) {
                map.put("sortName", "d.usercode");
            }
            if (map.get("sortName").equals("blcode")) {
                map.put("sortName", "u.blcode");
            }
            if (map.get("sortName").equals("username")) {
                map.put("sortName", "c.username");
            }
            if (map.get("sortName").equals("phonenumber1")) {
                map.put("sortName", "u.PhoneNumber1");
            }
            if (map.get("sortName").equals("classname")) {
                map.put("sortName", "tcode.dict_name");
            }
            if (map.get("sortName").equals("nextname")) {
                map.put("sortName", "cit.dict_name");
            }
            if (map.get("sortName").equals("itemname")) {
                map.put("sortName", "d.itemname");
            }
            if (map.get("sortName").equals("unit")) {
                map.put("sortName", "d.unit");
            }
            if (map.get("sortName").equals("num")) {
                map.put("sortName", "d.num");
            }
            if (map.get("sortName").equals("discount")) {
                map.put("sortName", "d.discount");
            }
            if (map.get("sortName").equals("subtotal")) {
                map.put("sortName", "d.subtotal");
            }
            if (map.get("sortName").equals("voidmoney")) {
                map.put("sortName", "d.voidmoney");
            }
        }

        PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
        List<JSONObject> list = (List<JSONObject>) dao.findForList(table + ".selectNoPage", map);
        for (JSONObject obj : list) {
            obj.put("classname", obj.getString("dict_name"));
            obj.put("nextname", obj.getString("dict_name_2"));
            obj.put("cjstatus", obj.getString("cjstatus").equals("0") ? "未成交" : "已成交");
            obj.put("type", obj.getString("type").equals("0") ? "否" : "是");
            obj.put("istsxm", obj.getString("istsxm").equals("0") ? "否" : "是");
            // 应收
            boolean flag = true;
            if (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("y2")), new BigDecimal(0)) <= 0 && !YZUtility.isNullorEmpty(obj.getString("qfbh"))
                    && KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("subtotal")), BigDecimal.ZERO) == 0
                    && KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("voidmoney")), BigDecimal.ZERO) == 0) {
                flag = false;
            }
            String ys = "0.00";
            String paymoney = YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney");
            String arrearmoney = YZUtility.isNullorEmpty(obj.getString("arrearmoney")) ? "0.00" : obj.getString("arrearmoney");
            if (flag || obj.getString("istk").equals("1")) {
                ys = new BigDecimal(paymoney).add(new BigDecimal(arrearmoney)).toString();
            }
            obj.put("ys", ys);
            String payother2 = YZUtility.isNullorEmpty(obj.getString("payother2")) ? "0.00" : obj.getString("payother2");
            String paydjq = YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq");
            String payintegral = YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral");
            obj.put("paymoney", new BigDecimal(paymoney).subtract(new BigDecimal(payother2)).subtract(new BigDecimal(paydjq)).subtract(new BigDecimal(payintegral)).toString());
        }
        PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
        JSONObject jobj = new JSONObject();
        // 第一次加载数量，分页查询时不加载
        if (firstIndex == 0) {
            if (list.size() > 0) {
                // 统计 费用表中 小计 应收 免除 欠款 缴费 等
                Map<String, String> wheremap = new HashMap<>();
                wheremap.putAll(map);
                wheremap.put("d.subtotal", "subtotal");
                wheremap.put("d.voidmoney", "voidmoney");
                wheremap.put("d.arrearmoney", "arrearmoney");
                wheremap.put("d.paymoney", "paymoney");
                wheremap.put("d.payother2", "payother2");
                wheremap.put("d.paydjq", "paydjq");
                wheremap.put("d.payintegral", "payintegral");
                wheremap.put("d.y2", "y2");
                JSONObject obj = (JSONObject) dao.findForObject(table + ".getTotalNumFields", wheremap);
                BigDecimal subtotal = new BigDecimal(obj.getString("subtotal"));
                BigDecimal voidmoney = new BigDecimal(obj.getString("voidmoney"));
                jobj.put("subtotal", obj.getString("subtotal"));
                jobj.put("voidmoney", obj.getString("voidmoney"));
                jobj.put("ys", subtotal.subtract(voidmoney).toString());
                String payother2str = "0";
                if (!YZUtility.isNullorEmpty(obj.getString("payother2"))) {
                    payother2str = obj.getString("payother2");
                }
                BigDecimal paymoney = new BigDecimal(obj.getString("paymoney"));
                BigDecimal payother2 = new BigDecimal(payother2str);
                BigDecimal paydjq = new BigDecimal(obj.getString("paydjq"));
                BigDecimal payintegral = new BigDecimal(obj.getString("payintegral"));
                BigDecimal payyjj = new BigDecimal(obj.getString("payyjj"));
                jobj.put("paymoney", paymoney.subtract(payother2).subtract(paydjq).subtract(payintegral).subtract(payyjj).toString());
                jobj.put("payother2", obj.getString("payother2"));
                jobj.put("y2", obj.getString("y2"));
                jobj.put("paydjq", obj.getString("paydjq"));
                jobj.put("payintegral", obj.getString("payintegral"));
            }
        }
        jobj.put("total", pageInfo.getTotal());
        jobj.put("rows", list);
        return jobj;
    }

    // 消费明细 含预收
    @SuppressWarnings("unchecked")
    public JSONObject selectNoPageOrder(BootStrapPage bp, String table, YZPerson person, Map<String, String> map, String visualstaff, String visualstaffwd) throws Exception {
        if (map.containsKey("queryinput")) {
            map.put("p1", YZAuthenticator.phonenumberLike("u.PhoneNumber1", map.get("queryinput")));
            map.put("p2", YZAuthenticator.phonenumberLike("u.PhoneNumber2", map.get("queryinput")));
        }

        map.put("visualstaff", visualstaff);
        map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
        if (map.containsKey("usercodes")) {
            map.put("usercodes", YZUtility.ConvertStringIds4Query(map.get("usercodes")));
        }
        int firstIndex = bp.getOffset();
        if (map.get("sortName") != null) {
            int a = 0;
            if (map.get("sortName").equals("sftime")) {
                map.put("sortName", "c.sftime");
                a = 1;
            }
            if (map.get("sortName").equals("kaifa")) {
                map.put("sortName", "d.kaifa");
                a = 1;
            }
            if (map.get("sortName").equals("zltime")) {
                map.put("sortName", "d.zltime");
                a = 1;
            }
            if (map.get("sortName").equals("usercode")) {
                map.put("sortName", "d.usercode");
                a = 1;
            }
            if (map.get("sortName").equals("blcode")) {
                map.put("sortName", "u.blcode");
                a = 1;
            }
            if (map.get("sortName").equals("username")) {
                map.put("sortName", "c.username");
                a = 1;
            }
            if (map.get("sortName").equals("phonenumber1")) {
                map.put("sortName", "u.PhoneNumber1");
                a = 1;
            }
            if (map.get("sortName").equals("classname")) {
                map.put("sortName", "tcode.dict_name");
                a = 1;
            }
            if (map.get("sortName").equals("nextname")) {
                map.put("sortName", "cit.dict_name");
                a = 1;
            }
            if (map.get("sortName").equals("itemname")) {
                map.put("sortName", "d.itemname");
                a = 1;
            }
            if (map.get("sortName").equals("realmoney")) {
                map.put("sortName", "d.paymoney-d.payyjj");
                a = 1;
            }
            if (map.get("sortName").equals("unit")) {
                map.put("sortName", "d.unit");
                a = 1;
            }
            if (map.get("sortName").equals("num")) {
                map.put("sortName", "d.num");
                a = 1;
            }
            if (map.get("sortName").equals("discount")) {
                map.put("sortName", "d.discount");
                a = 1;
            }
            if (map.get("sortName").equals("subtotal")) {
                map.put("sortName", "d.subtotal");
                a = 1;
            }
            if (map.get("sortName").equals("voidmoney")) {
                map.put("sortName", "d.voidmoney");
                a = 1;
            }
            if (map.get("sortName").equals("ys")) {
                map.put("sortName", "d.arrearmoney+d.paymoney");
                a = 1;
            }
            if (map.get("sortName").equals("y2")) {
                map.put("sortName", "d.y2");
                a = 1;
            }
            if (map.get("sortName").equals("payother2")) {
                map.put("sortName", "d.payother2");
                a = 1;
            }
            if (map.get("sortName").equals("paydjq")) {
                map.put("sortName", "d.paydjq");
                a = 1;
            }
            if (map.get("sortName").equals("payintegral")) {
                map.put("sortName", "d.payintegral");
                a = 1;
            }
            if (map.get("sortName").equals("paymoney")) {
                map.put("sortName", "d.paymoney");
                a = 1;
            }
            if (map.get("sortName").equals("payxj")) {
                map.put("sortName", "d.payxj");
                a = 1;
            }
            if (map.get("sortName").equals("payyjj")) {
                map.put("sortName", "d.payyjj");
                a = 1;
            }
            if (map.get("sortName").equals("paybank")) {
                map.put("sortName", "d.paybank");
                a = 1;
            }
            if (map.get("sortName").equals("payyb")) {
                map.put("sortName", "d.payyb");
                a = 1;
            }
            if (map.get("sortName").equals("paywx")) {
                map.put("sortName", "d.paywx");
                a = 1;
            }
            if (map.get("sortName").equals("payzfb")) {
                map.put("sortName", "d.payzfb");
                a = 1;
            }
            if (map.get("sortName").equals("paymmd")) {
                map.put("sortName", "d.paymmd");
                a = 1;
            }
            if (map.get("sortName").equals("paybdfq")) {
                map.put("sortName", "d.paybdfq");
                a = 1;
            }
            if (map.get("sortName").equals("payother1")) {
                map.put("sortName", "d.payother1");
                a = 1;
            }
            if (map.get("sortName").equals("istsxm")) {
                map.put("sortName", "d.istsxm");
                a = 1;
            }
            if (map.get("sortName").equals("recesort")) {
                map.put("sortName", "kcit1.dict_name");
                a = 1;
            }
            if (map.get("sortName").equals("regsort")) {
                map.put("sortName", "kcit2.dict_name");
                a = 1;
            }
            if (map.get("sortName").equals("cjstatus")) {
                map.put("sortName", "c.cjstatus");
                a = 1;
            }
            if (map.get("sortName").equals("faskperson")) {
                map.put("sortName", "per10.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("askperson")) {
                map.put("sortName", "per4.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("regdept")) {
                map.put("sortName", "dept1.DEPT_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("doctor")) {
                map.put("sortName", "per5.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("nurse")) {
                map.put("sortName", "per8.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("techperson")) {
                map.put("sortName", "per9.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("devchannel")) {
                map.put("sortName", "kcit3.dict_name");
                a = 1;
            }
            if (map.get("sortName").equals("nexttype")) {
                map.put("sortName", "hzly.dict_name");
                a = 1;
            }
            if (map.get("sortName").equals("accepttype")) {
                map.put("sortName", "kcit5.dict_name");
                a = 1;
            }
            if (map.get("sortName").equals("accepttool")) {
                map.put("sortName", "kcit6.dict_name");
                a = 1;
            }
            if (map.get("sortName").equals("kduser")) {
                map.put("sortName", "per1.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("kdtime")) {
                map.put("sortName", "c.createtime");
                a = 1;
            }
            if (map.get("sortName").equals("introducer")) {
                map.put("sortName", "u2.username");
                a = 1;
            }
            if (map.get("sortName").equals("jduser")) {
                map.put("sortName", "per3.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("jddy")) {
                map.put("sortName", "per7.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("jdtime")) {
                map.put("sortName", "u.CreateTime");
                a = 1;
            }
            if (map.get("sortName").equals("sfuser")) {
                map.put("sortName", "per2.USER_NAME");
                a = 1;
            }
            if (map.get("sortName").equals("remark")) {
                map.put("sortName", "c.remark");
                a = 1;
            }
            if (map.get("sortName").equals("detailremark")) {
                map.put("sortName", "d.remark");
                a = 1;
            }
            if (a == 0) {
                map.put("sortName", "");
            }
        }
//		if (map.containsKey("ispaging")) {
//			if (map.get("ispaging").equals("1")) {
//				// 分页插件
//			}
//		}
        PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectNoPageOrder", map);
        for (JSONObject obj : list) {
            // 治疗项目一、二级分类
            obj.put("classname", obj.getString("dict_name"));
            obj.put("nextname", obj.getString("dict_name_2"));
            obj.put("cjstatus", obj.getString("cjstatus").equals("0") ? "未成交" : "已成交");
            obj.put("type", obj.getString("type").equals("0") ? "否" : "是");
            obj.put("istsxm", obj.getString("istsxm").equals("0") ? "否" : "是");
            // 应收
            boolean flag = true;
            if (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("y2")), new BigDecimal(0)) <= 0 && !YZUtility.isNullorEmpty(obj.getString("qfbh"))
                    && KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("subtotal")), BigDecimal.ZERO) == 0
                    && KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("voidmoney")), BigDecimal.ZERO) == 0) {
                flag = false;
            }
            BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney"));
            BigDecimal arrearmoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("arrearmoney")) ? "0.00" : obj.getString("arrearmoney"));

            BigDecimal ys = BigDecimal.ZERO;
            if (flag || obj.getString("istk").equals("1")) {
                ys = paymoney.add(arrearmoney);
            }
            obj.put("ys", ys.toString());

            BigDecimal payother2 = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payother2")) ? "0.00" : obj.getString("payother2"));
            BigDecimal paydjq = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq"));
            BigDecimal payintegral = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral"));
            obj.put("paymoney", paymoney.subtract(payother2).subtract(paydjq).subtract(payintegral));
            String payyjj = YZUtility.isNullorEmpty(obj.getString("payyjj")) ? "0.00" : obj.getString("payyjj");
            obj.put("realmoney", new BigDecimal(obj.getString("paymoney")).subtract(new BigDecimal(payyjj)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            obj.put("cmoney", "0.00");
            obj.put("cgivemoney", "0.00");
            obj.put("ctotal", "0.00");

        }
        PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
        JSONObject jobj = new JSONObject();
        if (firstIndex == 0) {
//			int count = (int) dao.findForObject(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectCountOrdermxcx", map);
//			jobj.put("total", count);
            if (pageInfo.getTotal() > 0) {
                // 统计 费用表中 小计 应收 免除 欠款 缴费 等
                Map<String, String> wheremap = new HashMap<>();
                wheremap.putAll(map);
                wheremap.put("d.paymoney", "paymoney");
                wheremap.put("d.payxj", "payxj");
                wheremap.put("d.paybank", "paybank");
                wheremap.put("d.payyb", "payyb");
                wheremap.put("d.paywx", "paywx");
                wheremap.put("d.payzfb", "payzfb");
                wheremap.put("d.paymmd", "paymmd");
                wheremap.put("d.paybdfq", "paybdfq");
                wheremap.put("d.payother1", "payother1");
                wheremap.put("d.payyjj", "payyjj");
                wheremap.put("d.payother2", "payother2");
                wheremap.put("d.paydjq", "paydjq");
                wheremap.put("d.payintegral", "payintegral");
                //List<JSONObject> listmoney = (List<JSONObject>) dao.findForList(table + ".getTotalNumFields2", wheremap);
                JSONObject obj = (JSONObject) dao.findForObject(table + ".getTotalNumFields2", wheremap);
                BigDecimal paymoney = new BigDecimal(obj.getString("paymoney"));
                BigDecimal payyjj = new BigDecimal(obj.getString("payyjj"));
                BigDecimal paydjq = new BigDecimal(obj.getString("paydjq"));
                BigDecimal payintegral = new BigDecimal(obj.getString("payintegral"));
                BigDecimal payother2 = new BigDecimal(obj.getString("payother2"));
                jobj.put("realmoney", paymoney.subtract(payyjj).subtract(payother2).subtract(paydjq).subtract(payintegral).toString());
                jobj.put("payxj", obj.getString("payxj").toString());
                jobj.put("paybank", obj.getString("paybank").toString());
                jobj.put("payyb", obj.getString("payyb").toString());
                jobj.put("paywx", obj.getString("paywx").toString());
                jobj.put("payzfb", obj.getString("payzfb").toString());
                jobj.put("paymmd", obj.getString("paymmd").toString());
                jobj.put("paybdfq", obj.getString("paybdfq").toString());
                jobj.put("payother1", obj.getString("payother1").toString());

            }
        }
        jobj.put("total", pageInfo.getTotal());
        jobj.put("rows", list);
        return jobj;
    }

    // 日收款统计
    public List selectRsktj(String table, YZPerson person, Map<String, String> map, String visualstaff, String organization, HttpServletRequest request) throws Exception {

        List<Object> list = new ArrayList<Object>();
        JSONObject sfxm = null;
        JSONObject yjj = null;
        JSONObject tk = null;
        JSONObject yjjtk = null;
        if (map.containsKey("askperson")) {// 咨询
            sfxm = selectRsktjSfxm(table, person, map, visualstaff, "askperson", organization);
            tk = selectRsktjTk(table, person, map, visualstaff, "askperson", organization);
            yjj = selectRsktjYjj(table, person, map, visualstaff, "askperson", organization);
            yjjtk = selectRsktjYjjTk(table, person, map, visualstaff, "askperson", organization);
        } else if (map.containsKey("wdperson")) {// 网电
            sfxm = selectRsktjSfxm(table, person, map, visualstaff, "wdperson", organization);
            tk = selectRsktjTk(table, person, map, visualstaff, "wdperson", organization);
            yjj = selectRsktjYjj(table, person, map, visualstaff, "wdperson", organization);
            yjjtk = selectRsktjYjjTk(table, person, map, visualstaff, "wdperson", organization);
        } else if (map.containsKey("yxperson")) {// 营销
            sfxm = selectRsktjSfxm(table, person, map, visualstaff, "yxperson", organization);
            tk = selectRsktjTk(table, person, map, visualstaff, "yxperson", organization);
            yjj = selectRsktjYjj(table, person, map, visualstaff, "yxperson", organization);
            yjjtk = selectRsktjYjjTk(table, person, map, visualstaff, "yxperson", organization);
        } else { // 接待中心
            sfxm = selectRsktjSfxm(table, person, map, visualstaff, "skr", organization);
            tk = selectRsktjTk(table, person, map, visualstaff, "skr", organization);
            yjj = selectRsktjYjj(table, person, map, visualstaff, "skr", organization);
            yjjtk = selectRsktjYjjTk(table, person, map, visualstaff, "skr", organization);
        }
        list = getRsktjList(sfxm, yjj, tk, yjjtk, request);
        return list;
    }

    private List<Object> getRsktjList(JSONObject sfxm, JSONObject yjj, JSONObject tk, JSONObject yjjtk, HttpServletRequest request) throws Exception {
        List<Object> list = new ArrayList<Object>();
        for (int i = 1; i < 22; i++) {
            JSONObject obj = new JSONObject();
            if (i == 1) {
                obj.put("xh", "1");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payxj"));// 项目
                obj.put("sfje", sfxm.get("xj").toString());// 退费前实收金额
                obj.put("tkje", tk.get("xj").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(sfxm.get("xj").toString()).add(new BigDecimal(tk.get("xj").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 2) {// 银行卡支付
                obj.put("xh", "2");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paybank"));// 项目
                obj.put("sfje", sfxm.get("bank").toString());// 退费前实收金额
                obj.put("tkje", tk.get("bank").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(sfxm.get("bank").toString()).add(new BigDecimal(tk.get("bank").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 3) {// 医保
                obj.put("xh", "3");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payyb"));// 项目
                obj.put("sfje", sfxm.get("yb").toString());// 退费前实收金额
                obj.put("tkje", tk.get("yb").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(sfxm.get("yb").toString()).add(new BigDecimal(tk.get("yb").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 4) {// 微信
                obj.put("xh", "4");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paywx"));// 项目
                obj.put("sfje", sfxm.get("wx").toString());// 退费前实收金额
                obj.put("tkje", tk.get("wx").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(sfxm.get("wx").toString()).add(new BigDecimal(tk.get("wx").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 5) {// 支付宝
                obj.put("xh", "5");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payzfb"));// 项目
                obj.put("sfje", sfxm.get("zfb").toString());// 退费前实收金额
                obj.put("tkje", tk.get("zfb").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(sfxm.get("zfb").toString()).add(new BigDecimal(tk.get("zfb").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 6) {// 么么贷
                obj.put("xh", "6");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paymmd"));// 项目
                obj.put("sfje", sfxm.get("mmd").toString());// 退费前实收金额
                obj.put("tkje", tk.get("mmd").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(sfxm.get("mmd").toString()).add(new BigDecimal(tk.get("mmd").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 7) {// 百度分期
                obj.put("xh", "7");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paybdfq"));// 项目
                obj.put("sfje", sfxm.get("bdfq").toString());// 退费前实收金额
                obj.put("tkje", tk.get("bdfq").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(sfxm.get("bdfq").toString()).add(new BigDecimal(tk.get("bdfq").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 8) {// 其他收款方式
                obj.put("xh", "8");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payother1"));// 项目
                obj.put("sfje", sfxm.get("other1").toString());// 退费前实收金额
                obj.put("tkje", tk.get("other1").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(sfxm.get("other1").toString()).add(new BigDecimal(tk.get("other1").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 9) {// 代金券
                obj.put("xh", "9");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "paydjq"));// 项目
                obj.put("sfje", "0.00");// 退费前实收金额
                obj.put("tkje", "0.00");// 退费金额
                obj.put("ssje", "0.00");// 退费后实收金额
                BigDecimal jfxj = new BigDecimal(sfxm.get("djq").toString()).add(new BigDecimal(tk.get("djq").toString()));
                obj.put("zsje", jfxj.toString());// 赠送金额
                obj.put("jfxj", jfxj.toString());// 缴费小计
            } else if (i == 10) {// 积分
                obj.put("xh", "10");// 序号
                obj.put("xm", SysParaUtil.getFkfsNameByCostField(request, "payintegral"));// 项目
                obj.put("sfje", "0.00");// 退费前实收金额
                obj.put("tkje", "0.00");// 退费金额
                obj.put("ssje", "0.00");// 退费后实收金额
                BigDecimal jfxj = new BigDecimal(sfxm.get("integral").toString()).add(new BigDecimal(tk.get("integral").toString()));
                obj.put("zsje", jfxj.toString());// 赠送金额
                obj.put("jfxj", jfxj.toString());// 缴费小计
            } else if (i == 11) {// 预交金总
                obj.put("xh", "11");// 序号
                obj.put("xm", ConstUtil.FKFS_YJJ2 + "充值");// 项目
                obj.put("sfje", yjj.get("czze").toString());// 退费前实收金额
                obj.put("tkje", yjjtk.get("czze").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(yjj.get("czze").toString()).add(new BigDecimal(yjjtk.get("czze").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                BigDecimal zsje = new BigDecimal(yjj.get("zs").toString()).add(new BigDecimal(yjjtk.get("zs").toString()));
                obj.put("zsje", zsje.toString());// 赠送金额
                BigDecimal jfxj = KqdsBigDecimal.add(ssje, zsje);
                obj.put("jfxj", jfxj.toString());// 缴费小计
            } else if (i == 12) {// 预交金
                obj.put("xh", "");// 序号
                obj.put("xm", "└-├" + SysParaUtil.getFkfsNameByMemberField(request, "xjmoney"));// 项目
                obj.put("sfje", yjj.get("xjcz").toString());// 退费前实收金额
                obj.put("tkje", yjjtk.get("xjcz").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(yjj.get("xjcz").toString()).add(new BigDecimal(yjjtk.get("xjcz").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 13) {// 预交金银行卡
                obj.put("xh", "");// 序号
                obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "yhkmoney"));// 项目
                obj.put("sfje", yjj.get("bankcz").toString());// 退费前实收金额
                obj.put("tkje", yjjtk.get("bankcz").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(yjj.get("bankcz").toString()).add(new BigDecimal(yjjtk.get("bankcz").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 14) {// 预交金微信
                obj.put("xh", "");// 序号
                obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "wxmoney"));// 项目
                obj.put("sfje", yjj.get("wxcz").toString());// 退费前实收金额
                obj.put("tkje", yjjtk.get("wxcz").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(yjj.get("wxcz").toString()).add(new BigDecimal(yjjtk.get("wxcz").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 15) {// 预交金支付宝
                obj.put("xh", "");// 序号
                obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "zfbmoney"));// 项目
                obj.put("sfje", yjj.get("zfbcz").toString());// 退费前实收金额
                obj.put("tkje", yjjtk.get("zfbcz").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(yjj.get("zfbcz").toString()).add(new BigDecimal(yjjtk.get("zfbcz").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 16) {// 预交金么么贷
                obj.put("xh", "");// 序号
                obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "mmdmoney"));// 项目
                obj.put("sfje", yjj.get("mmdcz").toString());// 退费前实收金额
                obj.put("tkje", yjjtk.get("mmdcz").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(yjj.get("mmdcz").toString()).add(new BigDecimal(yjjtk.get("mmdcz").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 17) {// 预交金百度分期
                obj.put("xh", "");// 序号
                obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "bdfqmoney"));// 项目
                obj.put("sfje", yjj.get("bdfqcz").toString());// 退费前实收金额
                obj.put("tkje", yjjtk.get("bdfqcz").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(yjj.get("bdfqcz").toString()).add(new BigDecimal(yjjtk.get("bdfqcz").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 18) {// 预交金其他
                obj.put("xh", "");// 序号
                obj.put("xm", "&nbsp;&nbsp;&nbsp;&nbsp;├" + SysParaUtil.getFkfsNameByMemberField(request, "qtmoney"));// 项目
                obj.put("sfje", yjj.get("qtcz").toString());// 退费前实收金额
                obj.put("tkje", yjjtk.get("qtcz").toString());// 退费金额
                BigDecimal ssje = new BigDecimal(yjj.get("qtcz").toString()).add(new BigDecimal(yjjtk.get("qtcz").toString()));
                obj.put("ssje", ssje.toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", ssje.toString());// 缴费小计
            } else if (i == 19) {// 当日收款金额
                obj.put("xh", "12");// 序号
                obj.put("xm", "当日收款金额");// 项目
                BigDecimal sfss = new BigDecimal(sfxm.get("skze").toString().equals("") ? "0.00" : sfxm.get("skze").toString());// 收费实收
                BigDecimal czss = new BigDecimal(yjj.get("czze").toString().equals("") ? "0.00" : yjj.get("czze").toString());// 充值实收
                obj.put("sfje", KqdsBigDecimal.round(sfss.add(czss), 2).toString());// 退款前实收金额
                BigDecimal tkczss = new BigDecimal(yjjtk.get("czze").toString().equals("") ? "0.00" : yjjtk.get("czze").toString());// 充值退款
                BigDecimal tkss = new BigDecimal(tk.get("skze").toString().equals("") ? "0.00" : tk.get("skze").toString());// 退款
                obj.put("tkje", KqdsBigDecimal.round(tkss.add(tkczss), 2).toString());// 退款金额
                BigDecimal ssje = sfss.add(czss).add(tkss).add(tkczss);
                obj.put("ssje", KqdsBigDecimal.round(ssje, 2).toString());// 退款后实收金额
                BigDecimal yjjzs = new BigDecimal(yjj.get("zs").toString().equals("") ? "0.00" : yjj.get("zs").toString());// 预交金收款赠送
                BigDecimal yjjtkzs = new BigDecimal(yjjtk.get("zs").toString().equals("") ? "0.00" : yjjtk.get("zs").toString());// 预交金退款赠送
                BigDecimal tkdjq = new BigDecimal(tk.get("djq").toString().equals("") ? "0.00" : tk.get("djq").toString());// 退款代金券
                BigDecimal tkintegral = new BigDecimal(tk.get("integral").toString().equals("") ? "0.00" : tk.get("integral").toString());// 退款积分
                BigDecimal sfdjq = new BigDecimal(sfxm.get("djq").toString().equals("") ? "0.00" : sfxm.get("djq").toString());// 收费代金券
                BigDecimal sfintegral = new BigDecimal(sfxm.get("integral").toString().equals("") ? "0.00" : sfxm.get("integral").toString());// 收费积分
                BigDecimal zsje = yjjzs.add(yjjtkzs).add(tkdjq).add(sfdjq).add(tkintegral).add(sfintegral);
                obj.put("zsje", KqdsBigDecimal.round(zsje, 2).toString());// 赠送金额
                obj.put("jfxj", KqdsBigDecimal.round(ssje.add(zsje), 2).toString());// 缴费小计
            } else if (i == 20) {// 划扣金额
                obj.put("xh", "13");// 序号
                obj.put("xm", "预交金使用金额");// 项目
                BigDecimal hkje = new BigDecimal(sfxm.get("yjj").toString()).subtract(new BigDecimal(tk.get("yjj").toString()));
                obj.put("sfje", KqdsBigDecimal.round(hkje, 2).toString());// 退费前实收金额
                obj.put("tkje", "0.00");// 退费金额
                obj.put("ssje", KqdsBigDecimal.round(hkje, 2).toString());// 退费后实收金额
                BigDecimal sfxmzs = new BigDecimal(sfxm.get("zs").toString().equals("") ? "0.00" : sfxm.get("zs").toString());// 预交金收款赠送
                BigDecimal tkzs = new BigDecimal(tk.get("zs").toString().equals("") ? "0.00" : tk.get("zs").toString());// 预交金退款赠送
                BigDecimal zsje = sfxmzs.add(tkzs);
                obj.put("zsje", KqdsBigDecimal.round(zsje, 2).toString());// 赠送金额
                obj.put("jfxj", KqdsBigDecimal.round(hkje.add(zsje), 2).toString());// 缴费小计
            } else if (i == 21) {// 营业收入
                obj.put("xh", "14");// 序号
                obj.put("xm", "营业收入（实收+欠费）");// 项目
                obj.put("sfje", KqdsBigDecimal.round(new BigDecimal(sfxm.getString("realmoney")), 2).toString());// 退费前实收金额
                obj.put("tkje", KqdsBigDecimal.round(new BigDecimal(tk.getString("realmoney")), 2).toString());// 退费金额
                BigDecimal yesr = new BigDecimal(sfxm.get("realmoney").toString()).add(new BigDecimal(tk.get("realmoney").toString()));
                obj.put("ssje", KqdsBigDecimal.round(yesr, 2).toString());// 退费后实收金额
                obj.put("zsje", "0.00");// 赠送金额
                obj.put("jfxj", KqdsBigDecimal.round(yesr, 2).toString());// 缴费小计
            }
            list.add(obj);
        }
        return list;
    }

    // 日收款统计---收費項目
    @SuppressWarnings("unchecked")
    private JSONObject selectRsktjSfxm(String table, YZPerson person, Map<String, String> map, String visualstaff, String userpriv, String organization) throws Exception {
        if (!YZUtility.isNullorEmpty(organization)) {
            map.put("organization", organization);
        }
        map.put("userpriv", userpriv);
        map.put("visualstaff", visualstaff);
        JSONObject obj = new JSONObject();
        if (userpriv.equals("wdperson")) {// 网电
            if (map.containsKey("wdperson")) {
                // 再加上网电部门的过滤
                String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg("2");
                map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
            }
        } else if (userpriv.equals("yxperson")) {// 营销
            if (map.containsKey("yxperson")) {
                // 再加上网电部门的过滤
                String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg("3");
                map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
            }
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectRsktjSfxm", map);
        if (list != null && list.size() > 0) {
            JSONObject rs = list.get(0);
            obj.put("xj", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("xj")) ? "0.00" : rs.getString("xj")), 2));
            obj.put("bank", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bank")) ? "0.00" : rs.getString("bank")), 2));
            obj.put("yb", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yb")) ? "0.00" : rs.getString("yb")), 2));
            obj.put("wx", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("wx")) ? "0.00" : rs.getString("wx")), 2));
            obj.put("zfb", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zfb")) ? "0.00" : rs.getString("zfb")), 2));
            obj.put("mmd", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("mmd")) ? "0.00" : rs.getString("mmd")), 2));
            obj.put("bdfq", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bdfq")) ? "0.00" : rs.getString("bdfq")), 2));
            obj.put("djq", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq")), 2));
            obj.put("integral", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral")), 2));
            obj.put("other1", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("other1")) ? "0.00" : rs.getString("other1")), 2));
            BigDecimal payyjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0.00" : rs.getString("yjj"));
            BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
            BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0.00" : rs.getString("skze"));
            BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
            BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
            skze = skze.subtract(zs).subtract(payyjj).subtract(djq).subtract(integral);
            obj.put("zs", zs.toString());
            obj.put("skze", skze.toString());
            // 欠费金额
            BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
            // 营业收入（项目收费） = 实收金额 + 欠费金额
            obj.put("realmoney", skze.add(qf).add(payyjj));
            // 划扣金额
            obj.put("yjj", payyjj.toString());
        } else {
            obj.put("xj", "0.00");
            obj.put("bank", "0.00");
            obj.put("yb", "0.00");
            obj.put("other1", "0.00");
            obj.put("skze", "0.00");
            obj.put("zs", "0.00");
            obj.put("wx", "0.00");
            obj.put("zfb", "0.00");
            obj.put("mmd", "0.00");
            obj.put("bdfq", "0.00");
            obj.put("djq", "0.00");
            obj.put("integral", "0.00");
            obj.put("realmoney", "0.00");
            obj.put("yjj", "0.00");
        }

        return obj;
    }

    // 日收款统计---收費項目
    @SuppressWarnings("unchecked")
    private JSONObject selectRsktjTk(String table, YZPerson person, Map<String, String> map, String visualstaff, String userpriv, String organization) throws Exception {
        if (!YZUtility.isNullorEmpty(organization)) {
            map.put("organization", organization);
        }
        map.put("userpriv", userpriv);
        map.put("visualstaff", visualstaff);
        JSONObject obj = new JSONObject();
        if (userpriv.equals("wdperson")) {// 网电
            if (map.containsKey("wdperson")) {
                // 再加上网电部门的过滤
                String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg("2");
                map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
            }
        } else if (userpriv.equals("yxperson")) {// 营销
            if (map.containsKey("yxperson")) {
                // 再加上网电部门的过滤
                String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg("3");
                map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
            }
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectRsktjTk", map);
        if (list != null && list.size() > 0) {
            JSONObject rs = list.get(0);
            obj.put("xj", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("xj")) ? "0.00" : rs.getString("xj")), 2));
            obj.put("bank", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bank")) ? "0.00" : rs.getString("bank")), 2));
            obj.put("yb", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yb")) ? "0.00" : rs.getString("yb")), 2));
            obj.put("wx", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("wx")) ? "0.00" : rs.getString("wx")), 2));
            obj.put("zfb", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zfb")) ? "0.00" : rs.getString("zfb")), 2));
            obj.put("mmd", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("mmd")) ? "0.00" : rs.getString("mmd")), 2));
            obj.put("bdfq", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bdfq")) ? "0.00" : rs.getString("bdfq")), 2));
            obj.put("djq", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq")), 2));
            obj.put("integral", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral")), 2));
            obj.put("other1", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("other1")) ? "0.00" : rs.getString("other1")), 2));
            BigDecimal payyjj = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("yjj")) ? "0.00" : rs.getString("yjj"));
            BigDecimal zs = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
            BigDecimal skze = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("skze")) ? "0.00" : rs.getString("skze"));
            BigDecimal djq = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("djq")) ? "0.00" : rs.getString("djq"));
            BigDecimal integral = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("integral")) ? "0.00" : rs.getString("integral"));
            skze = skze.subtract(zs).subtract(payyjj).subtract(djq).subtract(integral);
            obj.put("skze", skze.toString());
            obj.put("zs", zs.toString());
            // 欠费金额
            BigDecimal qf = new BigDecimal(YZUtility.isNullorEmpty(rs.getString("y2")) ? "0.00" : rs.getString("y2"));
            // 营业收入（项目收费） = 实收金额 + 欠费金额
            obj.put("realmoney", skze.add(qf).add(payyjj).toString());
            // 划扣金额
            obj.put("yjj", payyjj.toString());
        } else {
            obj.put("xj", "0.00");
            obj.put("zs", "0.00");
            obj.put("bank", "0.00");
            obj.put("yb", "0.00");
            obj.put("other1", "0.00");
            obj.put("skze", "0.00");
            obj.put("wx", "0.00");
            obj.put("zfb", "0.00");
            obj.put("mmd", "0.00");
            obj.put("bdfq", "0.00");
            obj.put("djq", "0.00");
            obj.put("integral", "0.00");
            obj.put("realmoney", "0.00");
            obj.put("yjj", "0.00");
        }
        return obj;
    }

    // 日收款统计---預交金
    @SuppressWarnings("unchecked")
    private JSONObject selectRsktjYjj(String table, YZPerson person, Map<String, String> map, String visualstaff, String userpriv, String organization) throws Exception {
        JSONObject obj = new JSONObject();
        if (!YZUtility.isNullorEmpty(organization)) {
            map.put("organization", organization);
        }
        map.put("userpriv", userpriv);
        map.put("visualstaff", visualstaff);

        if (userpriv.equals("wdperson")) {// 网电
            if (map.containsKey("wdperson")) {
                // 再加上网电部门的过滤
                String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg("2");
                map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
            }
        } else if (userpriv.equals("yxperson")) {
            if (map.containsKey("yxperson")) {
                // 再加上网电部门的过滤
                String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg("3");
                map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
            }
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectRsktjYjj", map);
        if (list != null && list.size() > 0) {
            JSONObject rs = list.get(0);
            obj.put("bankcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bankcz")) ? "0.00" : rs.getString("bankcz")), 2));
            obj.put("xjcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("xjcz")) ? "0.00" : rs.getString("xjcz")), 2));
            obj.put("qtcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("qtcz")) ? "0.00" : rs.getString("qtcz")), 2));
            obj.put("zs", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs")), 2));
            obj.put("zfbcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("zfbcz")) ? "0.00" : rs.getString("zfbcz")), 2));
            obj.put("mmdcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("mmdcz")) ? "0.00" : rs.getString("mmdcz")), 2));
            obj.put("bdfqcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("bdfqcz")) ? "0.00" : rs.getString("bdfqcz")), 2));
            obj.put("wxcz", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("wxcz")) ? "0.00" : rs.getString("wxcz")), 2));
            obj.put("czze", KqdsBigDecimal.round(new BigDecimal(YZUtility.isNullorEmpty(rs.getString("czze")) ? "0.00" : rs.getString("czze")), 2));
        } else {
            obj.put("bankcz", "0.00");
            obj.put("xjcz", "0.00");
            obj.put("qtcz", "0.00");
            obj.put("zs", "0.00");
            obj.put("zfbcz", "0.00");
            obj.put("wxcz", "0.00");
            obj.put("mmdcz", "0.00");
            obj.put("bdfqcz", "0.00");
            obj.put("czze", "0.00");
        }

        return obj;
    }

    // 日收款统计---預交金
    @SuppressWarnings("unchecked")
    private JSONObject selectRsktjYjjTk(String table, YZPerson person, Map<String, String> map, String visualstaff, String userpriv, String organization) throws Exception {
        JSONObject obj = new JSONObject();
        if (!YZUtility.isNullorEmpty(organization)) {
            map.put("organization", organization);
        }
        map.put("userpriv", userpriv);
        map.put("visualstaff", visualstaff);

        if (userpriv.equals("wdperson")) {// 网电
            if (map.containsKey("wdperson")) {
                // 再加上网电部门的过滤
                String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg("2");
                map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
            }
        } else if (userpriv.equals("yxperson")) {
            if (map.containsKey("yxperson")) {
                // 再加上网电部门的过滤
                String visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg("3");
                map.put("visualstaffwd", YZUtility.ConvertStringIds4Query(visualstaffwd));
            }
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectRsktjYjjTk", map);
        if (list != null && list.size() > 0) {
            JSONObject rs = list.get(0);
            obj.put("bankcz", YZUtility.isNullorEmpty(rs.getString("bankcz")) ? "0.00" : rs.getString("bankcz"));
            obj.put("xjcz", YZUtility.isNullorEmpty(rs.getString("xjcz")) ? "0.00" : rs.getString("xjcz"));
            obj.put("qtcz", YZUtility.isNullorEmpty(rs.getString("qtcz")) ? "0.00" : rs.getString("qtcz"));
            obj.put("zs", YZUtility.isNullorEmpty(rs.getString("zs")) ? "0.00" : rs.getString("zs"));
            obj.put("zfbcz", YZUtility.isNullorEmpty(rs.getString("zfbcz")) ? "0.00" : rs.getString("zfbcz"));
            obj.put("mmdcz", YZUtility.isNullorEmpty(rs.getString("mmdcz")) ? "0.00" : rs.getString("mmdcz"));
            obj.put("bdfqcz", YZUtility.isNullorEmpty(rs.getString("bdfqcz")) ? "0.00" : rs.getString("bdfqcz"));
            obj.put("wxcz", YZUtility.isNullorEmpty(rs.getString("wxcz")) ? "0.00" : rs.getString("wxcz"));
            obj.put("czze", YZUtility.isNullorEmpty(rs.getString("czze")) ? "0.00" : rs.getString("czze"));
        } else {
            obj.put("bankcz", "0.00");
            obj.put("xjcz", "0.00");
            obj.put("qtcz", "0.00");
            obj.put("zs", "0.00");
            obj.put("zfbcz", "0.00");
            obj.put("wxcz", "0.00");
            obj.put("mmdcz", "0.00");
            obj.put("bdfqcz", "0.00");
            obj.put("czze", "0.00");
        }

        return obj;
    }

    /**
     * 查询条件不需要再增加 Organization
     *
     * @param conn
     * @param table
     * @param map
     * @param visualstaff
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<JSONObject> selectWithPage(String table, Map<String, String> map, String visualstaff) throws Exception {
        map.put("visualstaff", visualstaff);
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectWithPage", map);
        return list;
    }

    /**
     * 【根据costno 进行查询，查询条件不需要再增加 Organization】
     *
     * @param conn
     * @param table
     * @param map
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<JSONObject> selectWithPageLzjl(String table, Map<String, String> map) throws Exception {
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectWithPageLzjl", map);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<JSONObject> selectWithPageLzjlForLabel(String table, Map<String, String> map) throws Exception {
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectWithPageLzjlForLabel", map);
        return list;
    }

    /**
     * 查询欠费项目 【不做门诊条件过滤】
     *
     * @param conn
     * @param table
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<JSONObject> selectWithPageLzjl2(String table, Map<String, String> map) throws Exception {
        // 只查已结账的
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectWithPageLzjl2", map);
        return list;
    }

    /**
     * 根据欠费编号查询 该项目实际欠费情况 不做门诊条件过滤
     *
     * @param conn
     * @param table
     * @param qfbh
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String selectRealQf(String table, String qfbh) throws Exception {
        BigDecimal qf = BigDecimal.ZERO;
        // 过滤确认划价 但还未缴费的
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectrealqf", qfbh);
        for (JSONObject rs : list) {
            if (rs.getString("y2") != null && !rs.getString("y2").equals("")) {
                qf = qf.add(new BigDecimal(Double.parseDouble(rs.getString("y2"))));
            }
        }
        return qf.toString();
    }

    /**
     * 结账后费用单号为空删除 不做门诊条件过滤
     *
     * @param dbConn
     * @throws Exception
     */
    public void deleteDetail(String usercode, HttpServletRequest request) throws Exception {
        dao.delete(TableNameUtil.KQDS_COSTORDER_DETAIL + ".deleteDetail", usercode);
    }

    /**
     * 科室权责业绩图表展示
     *
     * @param conn
     * @param table
     * @param map
     * @param organization
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List getCountTj(String table, Map<String, String> map, String organization) throws Exception {
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getCountTj", map);
        for (JSONObject obj : list) {
            String ys = YZUtility.isNullorEmpty(obj.getString("ys")) ? "0.00" : obj.getString("ys");
            String xflx = obj.getString("xflx");

            obj.put("ys", KqdsBigDecimal.round(new BigDecimal(ys), 2));
            obj.put("xflx", xflx);
        }
        return list;
    }

    /**
     * 统计全院业绩情况 ---应收
     *
     * @param conn
     * @param table
     * @param year
     * @param month
     * @param days
     * @param organization
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List getCountQylrAll(String table, int year, int month, int days, String organization) throws Exception {
        List<BigDecimal[]> list = new ArrayList<BigDecimal[]>();
        BigDecimal[] num = new BigDecimal[days + 1];// 解决折线图初始位置从1开始
        Map<String, String> map = new HashMap<String, String>();
        map.put("year", year + "");
        map.put("month", month + "");
        map.put("organization", organization);
        List<JSONObject> jsonList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getCountQylrAll", map);

        for (int i = 0; i <= days; i++) {
            num[i] = BigDecimal.ZERO;
        }

        for (JSONObject rs : jsonList) {
            for (int i = 0; i <= days; i++) {
                if (rs.getInt("d") == i) {
                    num[i] = KqdsBigDecimal.round(new BigDecimal(rs.getString("ys")), 2);
                }
            }
        }
        list.add(num);
        return list;
    }

    /**
     * 统计全院业绩情况 ---实收
     *
     * @param conn
     * @param table
     * @param year
     * @param month
     * @param days
     * @param organization
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List getCountQylrNew(String table, int year, int month, int days, String organization) throws Exception {
        List<BigDecimal[]> list = new ArrayList<BigDecimal[]>();
        BigDecimal[] num = new BigDecimal[days + 1];// 解决折线图初始位置从1开始

        Map<String, String> map = new HashMap<String, String>();
        map.put("year", year + "");
        map.put("month", month + "");
        map.put("organization", organization);
        List<JSONObject> jsonList = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getCountQylrNew", map);

        for (int i = 0; i <= days; i++) {
            num[i] = BigDecimal.ZERO;
        }

        for (JSONObject rs : jsonList) {
            for (int i = 0; i <= days; i++) {
                if (rs.getInt("d") == i) {
                    num[i] = KqdsBigDecimal.round(new BigDecimal(rs.getString("ss")), 2);
                }
            }
        }
        list.add(num);
        return list;
    }

    /**
     * 统计全院业绩情况 ---应收
     *
     * @param conn
     * @param table
     * @param year
     * @param month
     * @param organization
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public BigDecimal getCountQylrAll(String table, int year, int month, String organization) throws Exception {
        BigDecimal nums = BigDecimal.ZERO;
        Map<String, String> map = new HashMap<String, String>();
        map.put("year", year + "");
        map.put("month", month + "");
        map.put("organization", organization);
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getCountQylrAll2", map);
        if (list != null && list.size() > 0) {
            if (YZUtility.isNotNullOrEmpty(list.get(0).getString("ys"))) {
                nums = new BigDecimal(list.get(0).getString("ys"));
            }
        }
        return KqdsBigDecimal.round(nums, 2);
    }

    /**
     * 统计全院业绩情况 ---实收
     *
     * @param conn
     * @param table
     * @param year
     * @param month
     * @param organization
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public BigDecimal getCountQylrNew(String table, int year, int month, String organization) throws Exception {
        BigDecimal nums = BigDecimal.ZERO;
        Map<String, String> map = new HashMap<String, String>();
        map.put("year", year + "");
        map.put("month", month + "");
        map.put("organization", organization);
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".getCountQylrNew2", map);
        if (list != null && list.size() > 0) {
            if (YZUtility.isNotNullOrEmpty(list.get(0).getString("ss"))) {
                nums = new BigDecimal(list.get(0).getString("ss"));
            }
        }

        return KqdsBigDecimal.round(nums, 2);
    }

    /**
     * 退费金额验证 【不做门诊条件过滤】
     *
     * @param conn
     * @param table
     * @param usercode
     * @param itemno
     * @param qfbh
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public JSONObject checkTf(String usercode, String itemno, String qfbh, String detailId) throws Exception {
        JSONObject obj = new JSONObject();
        Map<String, String> map = new HashMap<String, String>();
        if (!YZUtility.isNullorEmpty(usercode)) {
            map.put("usercode", usercode);
        }
        if (!YZUtility.isNullorEmpty(itemno)) {
            map.put("itemno", itemno);
        }
        if (!YZUtility.isNullorEmpty(qfbh)) {
            map.put("qfbh", qfbh);
        } else {
            map.put("noqfbh", "");
            if (!YZUtility.isNullorEmpty(detailId)) {
                map.put("detailId", detailId);
            }
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".checkTf", map);
        if (list != null && list.size() > 0) {
            JSONObject rs = list.get(0);
            String paymoney = YZUtility.isNullorEmpty(rs.getString("paymoney")) ? "0.00" : rs.getString("paymoney");
            obj.put("paymoney", paymoney);
        }

        return obj;
    }

    /**
     * 退费金额验证 【不做门诊条件过滤】
     *
     * @param conn
     * @param table
     * @param usercode
     * @param itemno
     * @param qfbh
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public JSONObject checkTfRefund(String usercode, String itemno, String qfbh, String orderdetailno) throws Exception {
        JSONObject obj = new JSONObject();
        Map<String, String> map = new HashMap<String, String>();
        // --这里不需要包含同意退款并且已退款的，因为已退款后生成的费用明细已经计算过了
        if (!YZUtility.isNullorEmpty(usercode)) {
            map.put("usercode", usercode);
        }
        if (!YZUtility.isNullorEmpty(itemno)) {
            map.put("itemno", itemno);
        }
        if (!YZUtility.isNullorEmpty(qfbh)) {
            map.put("qfbh", qfbh);
        } else {
            map.put("noqfbh", "");
            if (!YZUtility.isNullorEmpty(orderdetailno)) {
                map.put("orderdetailno", orderdetailno);
            }
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".checkTfRefund", map);
        if (list != null && list.size() > 0) {
            JSONObject rs = list.get(0);
            String tkmoney = YZUtility.isNullorEmpty(rs.getString("tkmoney")) ? "0.00" : rs.getString("tkmoney");
            obj.put("tkmoney", tkmoney);
        }
        return obj;
    }

    /**
     * 退费单赠送金额 【不做门诊条件过滤】
     *
     * @param conn
     * @param table
     * @param costno
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public BigDecimal searchOrderZs(String table, String costno) throws Exception {
        BigDecimal zs = BigDecimal.ZERO;
        Map<String, String> map = new HashMap<String, String>();
        // --这里不需要包含同意退款并且已退款的，因为已退款后生成的费用明细已经计算过了
        if (!YZUtility.isNullorEmpty(costno)) {
            map.put("costno", costno);
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".searchOrderZs", map);
        if (list != null && list.size() > 0) {
            JSONObject rs = list.get(0);
            zs = YZUtility.isNullorEmpty(rs.getString("zs")) ? BigDecimal.ZERO : new BigDecimal(rs.getString("zs"));
        }
        return zs;
    }

    /**
     * 打印页面 【不做门诊条件过滤】
     *
     * @param conn
     * @param table
     * @param costno
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public JSONObject printSfxm(String table, String costno, HttpServletRequest request) throws Exception {
        JSONObject obj = new JSONObject();
        Map<String, String> map = new HashMap<String, String>();
        // --这里不需要包含同意退款并且已退款的，因为已退款后生成的费用明细已经计算过了
        if (!YZUtility.isNullorEmpty(costno)) {
            map.put("costno", costno);
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".printSfxm", map);

        for (JSONObject rs : list) {
            if (!YZUtility.isNullorEmpty(rs.getString("xj"))) {
                if (rs.getDouble("xj") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "payxj"), rs.getString("xj"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("bank"))) {
                if (rs.getDouble("bank") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "paybank"), rs.getString("bank"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("yb"))) {
                if (rs.getDouble("yb") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "payyb"), rs.getString("yb"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("wx"))) {
                if (rs.getDouble("wx") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "paywx"), rs.getString("wx"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("zfb"))) {
                if (rs.getDouble("zfb") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "payzfb"), rs.getString("zfb"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("mmd"))) {
                if (rs.getDouble("mmd") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "paymmd"), rs.getString("mmd"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("bdfq"))) {
                if (rs.getDouble("bdfq") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "paybdfq"), rs.getString("bdfq"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("other1"))) {
                if (rs.getDouble("other1") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "payother1"), rs.getString("other1"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("djq"))) {
                if (rs.getDouble("djq") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "paydjq"), rs.getString("djq"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("integral"))) {
                if (rs.getDouble("integral") != 0) {
                    obj.put(SysParaUtil.getFkfsNameByCostField(request, "payintegral"), rs.getString("integral"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("yjj"))) {
                if (rs.getDouble("yjj") != 0) {
                    obj.put(ConstUtil.FKFS_YJJ, rs.getString("yjj"));
                }
            }
            if (!YZUtility.isNullorEmpty(rs.getString("zs"))) {
                if ((rs.getDouble("zs")) != 0) {
                    obj.put(ConstUtil.FKFS_ZS, rs.getString("zs"));
                }
            }
        }

        return obj;
    }

    // 根据usercode查询总欠费
    @SuppressWarnings("unchecked")
    public BigDecimal selectQfAll(String usercode, String sftime) throws Exception {
        BigDecimal zqf = BigDecimal.ZERO;
        Map<String, String> map = new HashMap<String, String>();
        // --这里不需要包含同意退款并且已退款的，因为已退款后生成的费用明细已经计算过了
        if (!YZUtility.isNullorEmpty(usercode)) {
            map.put("usercode", usercode);
        }
        if (!YZUtility.isNullorEmpty(sftime)) {
            map.put("sftime", sftime);
        }
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectQfAll", map);

        if (list != null && list.size() > 0) {
            String y2 = list.get(0).getString("y2");
            if (!YZUtility.isNullorEmpty(y2)) {
                zqf = KqdsBigDecimal.round(new BigDecimal(y2), 2);
            }
        }
        return zqf;
    }

    public Object selectOneByQfbh(String qfbh, String seqId) throws Exception {
        Map<String, String> filter = new HashMap<String, String>();
        filter.put("qfbh", qfbh);
        filter.put("seqId", seqId);
        return dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectOneByQfbh", filter);
    }

    public Object selectOneByQfbh2(String qfbh) throws Exception {
        return dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectOneByQfbh2", qfbh);
    }

    @SuppressWarnings("unchecked")
    public List<JSONObject> selectListByQfbh(String qfbh) throws Exception {
        return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectListByQfbh", qfbh);
    }

    public List<JSONObject> findCostOrderDetailByUsercodes(String usercodes) throws Exception {
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".findCostOrderDetailByUsercodes", usercodes);
        return list;
    }

    public List<JSONObject> findCostOrderDetailTuidanByUsercodes(String usercodes) throws Exception {
        List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL_TUIDAN + ".findCostOrderDetailTuidanByUsercodes", usercodes);
        return list;
    }

    /**
     * 根据费用单号查询收费明细数据  2019-9-6  syp
     *
     * @param costno
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<KqdsCostorderDetail> selectCostorderDetail(String costno) throws Exception {
        List<KqdsCostorderDetail> list = (List<KqdsCostorderDetail>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".selectCostorderDetail", costno);
        return list;
    }

    /**
     * 根据id更新收费明细数据  2019-9-6  syp
     *
     * @param seqId
     * @throws Exception
     */
    public void updateCostorderDetailBySeqId(KqdsCostorderDetail dp) throws Exception {
        dao.update(TableNameUtil.KQDS_COSTORDER_DETAIL + ".updateCostorderDetailBySeqId", dp);
    }

    /**
     * @throws Exception
     * @Title: findZperformance
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param map
     * @return: void
     * @dateTime:2019年9月28日 下午1:42:19
     */
    @SuppressWarnings("unchecked")
    public List<JSONObject> findZperformance(Map<String, String> map) throws Exception {
        // TODO Auto-generated method stub
        return (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_COSTORDER_DETAIL + ".findZperformance", map);
    }

}