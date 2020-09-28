package com.kqds.controller.ck;

import com.hudh.util.HUDHUtil;
import com.kqds.entity.base.KqdsCkBathGoods;
import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_GoodsLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("KQDS_Ck_GoodsAct")
public class KQDS_Ck_GoodsAct {
    private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_GoodsAct.class);
    @Autowired
    private KQDS_Ck_GoodsLogic logic;

    @RequestMapping(value = "/toCkIndex.act")
    public ModelAndView toCkIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/ck_index.jsp");
        return mv;
    }

    /* 物资管理-->基础档案 2020/03/24 lutian */
    @RequestMapping(value = "/toCkBaseInfo.act")
    public ModelAndView toCkBaseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/ck_baseInfo.jsp");
        return mv;
    }

    /* 物资管理-->基础档案-->商品 	2020/03/25 lutian */
    @RequestMapping(value = "/toCkBaseInfoGoods.act")
    public ModelAndView toCkBaseInfoGoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/baseInfo/goods.jsp");
        return mv;
    }

    @RequestMapping(value = "/toChildHouseInfo.act")
    public ModelAndView toChildHouseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/childhouse/goods.jsp");
        return mv;
    }

    /* 物资管理-->基础档案-->商品资料展示 	2020/03/25 lutian */
    @RequestMapping(value = "/toCkBaseInfoGoodShow.act")
    public ModelAndView toCkBaseInfoGoodShow(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/baseInfo/goodShow.jsp");
        return mv;
    }

    /* 物资管理-->基础档案-->商品资料批量添加 	2020/03/28 lutian */
    @RequestMapping(value = "/toCkAddBatchGood.act")
    public ModelAndView toCkAddBatchGood(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/baseInfo/addBatchGood.jsp");
        return mv;
    }

    /* 物资管理-->入库管理 2020/03/24 lutian */
    @RequestMapping(value = "/toCkRkManage.act")
    public ModelAndView toCkRkManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/ck_rkManage.jsp");
        return mv;
    }

    /* 物资管理-->出库管理 2020/03/24 lutian */
    @RequestMapping(value = "/toCkCkManage.act")
    public ModelAndView toCkCkManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/ck_ckManage.jsp");
        return mv;
    }

    /* 物资管理-->库存管理 2020/03/24 lutian */
    @RequestMapping(value = "/toCkKcManage.act")
    public ModelAndView toCkKcManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/ck_kcManage.jsp");
        return mv;
    }

    /* 物资管理-->报表查询 2020/03/24 lutian */
    @RequestMapping(value = "/toCkBbSelect.act")
    public ModelAndView toCkBbSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/ck_bbSelect.jsp");
        return mv;
    }

    @RequestMapping("/toCkOutGoods.act")
    public ModelAndView toCkOutGoods(HttpServletRequest request, HttpServletResponse response) {
        String menuid = request.getParameter("menuid");
        ModelAndView mv = new ModelAndView();
        mv.addObject("menuid", menuid);
        mv.setViewName("/kqdsFront/ck/childhouse/outGoods/out_goods.jsp");
        return mv;
    }

    @RequestMapping(value = "/toSave.act")
    public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String seqId = request.getParameter("seqId");
        String sshouse = request.getParameter("sshouse");
        ModelAndView mv = new ModelAndView();
        mv.addObject("seqId", seqId);
        mv.addObject("sshouse", sshouse);
        mv.setViewName("/kqdsFront/ck/goods/save_goods.jsp");
        return mv;
    }

    /*修改商品*/
    @RequestMapping(value = "/toEdit.act")
    public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String seqId = request.getParameter("seqId");
        String sshouse = request.getParameter("sshouse");
        ModelAndView mv = new ModelAndView();
        mv.addObject("seqId", seqId);
        mv.addObject("sshouse", sshouse);
        mv.setViewName("/kqdsFront/ck/goods/edit_goods.jsp");
        return mv;
    }

    @RequestMapping(value = "/toCkSfcSearch.act")
    public ModelAndView toCkSfcSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/search/sfc_search.jsp");
        return mv;
    }

    @RequestMapping(value = "/toCkGoodsSearch.act")
    public ModelAndView toCkGoodsSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/search/goods_search.jsp");
        return mv;
    }

    @RequestMapping(value = "/toCkGjSearch.act")
    public ModelAndView toCkGjSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/search/ck_gj_index.jsp");
        return mv;
    }

    @RequestMapping(value = "/toCkGjMinSearch.act")
    public ModelAndView toCkGjMinSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/search/min_gj_search.jsp");
        return mv;
    }

    @RequestMapping(value = "/toCkGjMaxSearch.act")
    public ModelAndView toCkGjMaxSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/search/max_gj_search.jsp");
        return mv;
    }

    /**
     * @Title: toChildHouse
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @return: ModelAndView
     * @dateTime:2020年4月16日 下午4:12:14
     */
    @RequestMapping("/toChildHouse.act")
    public ModelAndView toChildHouse(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/kqdsFront/ck/childhouse/index.jsp");
        return mv;
    }

    @RequestMapping(value = "/getNumber.act")
    public String getNumber(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id = request.getParameter("id");

        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            JSONObject json = logic.getNumber(map);
            String goodscode = null;
            if (json != null) {
                String code = json.getString("goodscode");
                if (code != null && !code.equals("") && code != "") {
                    int num = Integer.parseInt(code.substring(2, code.length()));
                    num++;
                    if (num < 10) {
                        goodscode = code.substring(0, 2) + "000" + num;
                    } else if (num >= 10 && num < 100) {
                        goodscode = code.substring(0, 2) + "00" + num;
                    } else if (num >= 100 && num < 1000) {
                        goodscode = code.substring(0, 2) + "0" + num;
                    } else {
                        goodscode = code.substring(0, 2) + num;
                    }
                } else {
                    String name = logic.getTypeName(map);
                    int num = 0;
                    num++;
                    goodscode = name + "000" + num;
                }
            }

            json.put("goodscode", goodscode);
            YZUtility.DEAL_SUCCESS(json, null, response, logger);
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }

        return null;
    }

    /*
     * 不分页查询-收发存 （去除审批不同意）
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/selectListSfc.act")
    public String selectListSfc(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Map<String, String> map = new HashMap<String, String>();
            String starttime = request.getParameter("starttime");
            String endtime = request.getParameter("endtime");
            String house = request.getParameter("house");
            String goodsname = request.getParameter("goodsname");
            String goodsuuid = request.getParameter("goodsuuid");// 商品Id

            // 导出参数
            String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
            String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
            String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
            if (!YZUtility.isNullorEmpty(starttime)) {
                if (starttime.length() <= 11) {// 修改入库明细的时候需要查询
                    map.put("starttime", starttime + ConstUtil.TIME_START);
                } else {
                    map.put("starttime", starttime);
                }
            }
            if (!YZUtility.isNullorEmpty(endtime)) {
                map.put("endtime", endtime + ConstUtil.TIME_END);
            }
            if (!YZUtility.isNullorEmpty(house)) {
                map.put("sshouse", house);
            }
            if (!YZUtility.isNullorEmpty(goodsname)) {
                map.put("goodsname", goodsname);
            }
            if (!YZUtility.isNullorEmpty(goodsuuid)) {
                map.put("goodsuuid", goodsuuid);
            }
            map.put("organization", ChainUtil.getCurrentOrganization(request));
//			List<JSONObject> list = new ArrayList<JSONObject>();
            JSONObject json = new JSONObject();
            // 初始化分页实体类
            @SuppressWarnings("rawtypes")
            BootStrapPage bp = new BootStrapPage();
            // 封装参数到实体类
            BeanUtils.populate(bp, request.getParameterMap());
            if (!YZUtility.isNullorEmpty(house)) {
                json = logic.selectListSfc(TableNameUtil.KQDS_CK_GOODS, map, bp, json, ChainUtil.getCurrentOrganization(request));
            } else {
                json = logic.selectNoHouseListSfc(TableNameUtil.KQDS_CK_GOODS, map, bp, json, ChainUtil.getCurrentOrganization(request));
            }
            /*-------导出excel---------*/
            List<JSONObject> list = json.getJSONArray("rows");//添加分页
            if (flag != null && flag.equals("exportTable")) {
                ExportTable.exportBootStrapTable2Excel("收发存查询", fieldArr, fieldnameArr, list, response, request);
                return null;
            }
//			YZUtility.RETURN_LIST(list, response, logger);
            YZUtility.DEAL_SUCCESS(json, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }
        return null;
    }

    /*
     *
     * 根据hoser 查询
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/selectByhouse.act")
    public String selectByhouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String houseid = request.getParameter("houseid");

            Map<String, String> map = new HashMap<String, String>();
            map.put("sshouse", houseid);
            List<KqdsCkGoods> en = (ArrayList<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
            JSONObject jobj = new JSONObject();
            jobj.put("data", en);
            YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
        } catch (Exception ex) {
            YZUtility.DEAL_ERROR(null, false, ex, response, logger);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = {Exception.class})
    @RequestMapping("/bathCommodities.act")
    public String bathCommodities(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String goods = request.getParameter("goods");
        List<KqdsCkBathGoods> goodsList = new ArrayList<KqdsCkBathGoods>();
        try {
            if (goods != null) {
                goods = java.net.URLDecoder.decode(goods, "UTF-8");
                goodsList = HUDHUtil.parseJsonToObjectList(goods, KqdsCkBathGoods.class);
            }
            YZPerson person = SessionUtil.getLoginPerson(request);
            KqdsCkGoodsDetail dp = new KqdsCkGoodsDetail();
            for (KqdsCkBathGoods kqdsCkBathGoods : goodsList) {
                String uuid = YZUtility.getUUID();
                dp.setSeqId(uuid);
                String pym = ChineseCharToEn.getAllFirstLetter(kqdsCkBathGoods.getGoodsname());
                dp.setPym(pym);
                dp.setGoodscode(kqdsCkBathGoods.getGoodscode());
                dp.setGoodsname(kqdsCkBathGoods.getGoodsname());
                dp.setGoodsunit(kqdsCkBathGoods.getGoodsunit());
                dp.setGoodsnorms(kqdsCkBathGoods.getGoodsnorms());
                dp.setGoodstype(kqdsCkBathGoods.getGoodstype());
                dp.setCreatetime(YZUtility.getCurDateTimeStr());
                dp.setCreateuser(person.getSeqId());
                dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
                logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, dp);
                //保存基础数据的同时向goods表同时插入数据
                KqdsCkGoods kqdsckGood = new KqdsCkGoods();
                kqdsckGood.setSeqId(YZUtility.getUUID());
                kqdsckGood.setGoodsdetailid(uuid);
                kqdsckGood.setSshouse(kqdsCkBathGoods.getSshouse());
                kqdsckGood.setOrganization(ChainUtil.getCurrentOrganization(request));
                logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, kqdsckGood);
            }
            // 记录日志
            BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_DETAIL, dp, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);
            YZUtility.DEAL_SUCCESS(null, null, response, logger);
        } catch (Exception e) {
            YZUtility.DEAL_ERROR(null, false, e, response, logger);
        }
        return null;
    }

    /**
     * @throws Exception
     * @Title: unallowable
     * @Description: TODO(启停)
     * @param: @param request
     * @param: @param response
     * @param: @return
     * @return: String
     * @dateTime:2020年4月30日 上午9:07:21
     */
    @RequestMapping("/unallowable.act")
    public String unallowable(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String seqId = request.getParameter("seqId");
        String status = request.getParameter("status");
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("seqId", seqId);
            map.put("status", status);
            JSONObject json = logic.findGoodsById(seqId);
            if (Integer.parseInt(json.getString("nums")) > 0) {
                throw new Exception("不可禁用，该商品存在库存……");
            } else {
                logic.unallowable(map);
                YZUtility.DEAL_SUCCESS(null, null, response, logger);
            }
        } catch (Exception e) {
            // TODO: handle exception
            YZUtility.DEAL_ERROR("不可禁用，该商品存在库存……", false, e, response, logger);
        }
        return null;
    }
}