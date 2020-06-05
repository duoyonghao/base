package com.kqds.controller.ck;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsCkGoods;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.sys.SysParaUtil;

@Controller
@RequestMapping("KQDS_Ck_Goods_DetailAct")
public class KQDS_Ck_Goods_DetailAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_Ck_Goods_DetailAct.class);
	@Autowired
	private KQDS_Ck_Goods_DetailLogic logic;

	@RequestMapping(value = "/toGoodsgJ.act")
	public ModelAndView toGoodsgJ(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/ck/search/ck_gj_index.jsp");
		return mv;
	}

	@RequestMapping(value = "/toGoodsDetailSearch.act")
	public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// String seqId = request.getParameter("seqId");
		ModelAndView mv = new ModelAndView();
		// mv.addObject("seqId", seqId);
		mv.setViewName("/kqdsFront/ck/search/goods_detail_search.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String sshouse = request.getParameter("sshouse");
			KqdsCkGoodsDetail dp = new KqdsCkGoodsDetail();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				String pym = ChineseCharToEn.getAllFirstLetter(dp.getGoodsname());
				dp.setPym(pym);
				logic.updateSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, dp);
				//保存基础数据的同时向goods表同时插入数据
				Map<String, String> map = new HashMap<String, String>();
				map.put("seqId", dp.getSeqId());
				map.put("house", sshouse);
				logic.updateGoods( map);
				// 记录日志
 				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_CK_GOODS_DETAIL, dp, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				String pym = ChineseCharToEn.getAllFirstLetter(dp.getGoodsname());
				dp.setPym(pym);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, dp);
				//保存基础数据的同时向goods表同时插入数据
				KqdsCkGoods kqdsckGood = new KqdsCkGoods();
				kqdsckGood.setSeqId(YZUtility.getUUID());
				kqdsckGood.setGoodsdetailid(dp.getSeqId());
				kqdsckGood.setSshouse(sshouse);
				kqdsckGood.setOrganization(ChainUtil.getCurrentOrganization(request));
				logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, kqdsckGood);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_DETAIL, dp, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 新增或修改商品信息时，校验该商品编号在当前门诊下是否重复
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/YzCode.act")
	public String YzCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			String goodscode = request.getParameter("goodscode");
			// 构建查询对象
			KqdsCkGoodsDetail dp4check = new KqdsCkGoodsDetail();
			dp4check.setGoodscode(goodscode);
			dp4check.setSeqId(seqId);
			dp4check.setOrganization(ChainUtil.getCurrentOrganization(request));
			int count = logic.countByGoodsCode(dp4check);
			boolean flag = count > 0 ? false : true; // false 表示存在
			JSONObject jobj = new JSONObject();
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除商品类别时，验证该类别下是否存在商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delleteGoodsYz.act")
	public String delleteGoodsYz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String goodstype = request.getParameter("goodstype"); //
			Map<String, String> map = new HashMap<String, String>();
			map.put("goodstype", goodstype);
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			int size = logic.selectSizeByTypeno(map);
			boolean flag = size > 0 ? false : true; // false 表示存在
			JSONObject jobj = new JSONObject();
			jobj.put("data", flag);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			int incount = logic.getCountInByItemnos(seqId);
			if (incount > 0) {
				throw new Exception("该商品存在入库记录，不能删除！");
			}

			int outcount = logic.getCountOutByItemnos(seqId);
			if (outcount > 0) {
				throw new Exception("该商品存在出库记录，不能删除！");
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("goodsdetailid", seqId);
			List<KqdsCkGoods> list = (ArrayList<KqdsCkGoods>) logic.loadList(TableNameUtil.KQDS_CK_GOODS, map);
			for (KqdsCkGoods goods : list) {
				if (goods.getNums() > 0) {
					throw new Exception("该商品存在库存，不能删除！");
				}
				logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS, goods.getSeqId());
			}

			KqdsCkGoodsDetail en = (KqdsCkGoodsDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, seqId);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_CK_GOODS_DETAIL, en, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			KqdsCkGoodsDetail en = (KqdsCkGoodsDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetailAll.act")
	public String selectDetailAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqIds = request.getParameter("seqIds");
			if (YZUtility.isNullorEmpty(seqIds)) {
				throw new Exception("主键参数值为空");
			}
			String goodsname = "";
			String[] arr = seqIds.split(",");
			for (String seqId : arr) {
				if (YZUtility.isNullorEmpty(seqId)) {
					continue;
				}
				KqdsCkGoodsDetail en = (KqdsCkGoodsDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, seqId);
				goodsname += en.getGoodsname() + ",";
			}
			if (goodsname.endsWith(",")) {
				goodsname = goodsname.substring(0, goodsname.length() - 1);
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", goodsname);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 商品列表(仓库首页面调用，当前库存页面,选择商品 调用)
	 * 
	 * @param request
	 * @param response
	 * @param bp 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String type = request.getParameter("type");
			String queryInput = request.getParameter("queryInput");
			String sshouse = request.getParameter("sshouse");
			String qstime = request.getParameter("qstime");
			String jztime = request.getParameter("jztime");
			String goodstype = request.getParameter("goodstype");
			String goodsname = request.getParameter("goodsname");
			String goodsuuid = request.getParameter("goodsuuid");
			String basetype = request.getParameter("basetype");
			String nexttype = request.getParameter("nexttype");
			String supplier = request.getParameter("supplier");
			/**
			 * 2019.07.11 lwg
			 */
			String current=request.getParameter("current");
			JSONObject json = new JSONObject();
			
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			
			
			
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("queryInput", queryInput);
			}
			if (!YZUtility.isNullorEmpty(sshouse)) {
				map.put("sshouse", sshouse);
			}
			if (!YZUtility.isNullorEmpty(goodstype)) {
				map.put("goodstype", goodstype);
			}
			if (!YZUtility.isNullorEmpty(goodsname)) {
				map.put("goodsname", goodsname);
			}
			if (!YZUtility.isNullorEmpty(goodsuuid)) {
				map.put("goodsuuid", goodsuuid);
			}
			if (!YZUtility.isNullorEmpty(basetype)) {
				map.put("basetype", basetype);
			}
			if (!YZUtility.isNullorEmpty(nexttype) && nexttype != "") {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(qstime)) {
				map.put("qstime", qstime);
			}
			if (!YZUtility.isNullorEmpty(jztime)) {
				map.put("jztime", jztime);
			}
			if(!YZUtility.isNullorEmpty(supplier)){
				map.put("supplier", supplier);
			}
			if(!YZUtility.isNullorEmpty(flag)){
				map.put("flag", flag);
			}
			if(!YZUtility.isNullorEmpty(type)){
				map.put("type", type);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			JSONObject list = new JSONObject();
			// 初始化分页实体类
			@SuppressWarnings("rawtypes")
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			// 截止时间
			if (!YZUtility.isNullorEmpty(jztime)||!YZUtility.isNullorEmpty(qstime)) {
				if (!YZUtility.isNullorEmpty(sshouse)) {
					// 按时间查 初始化数量金额 +入库。。。-出库。。。
					list = logic.selectListOrdertime(map,bp,json);
				} else {
					list = logic.selectNoHouseListOrdertime(map,bp,json);
				}
			} else {
				if (!YZUtility.isNullorEmpty(sshouse)) {
					list = logic.selectList(map,bp,json);
				} else {
					list = logic.selectNoHouseList(map,bp,json);
				}
			}
			List<JSONObject> list1 = list.getJSONArray("rows");
			
			if(supplier==null||supplier.equals("")){
				/**
				 * 2019-3-10  前台搜索需要根据厂家进行检索 前面查询数据分类太多在查询出结果后在进行添加
				 */
				if(current!=null&&!current.equals("")&&current.equals("1")){
					StringBuilder str = new StringBuilder();
					for (int i = 0; i < list1.size(); i++) {
						if(i==list1.size()-1){
							str.append("\'"+list1.get(i).getString("seq_id")+"\'");
						}else{
							str.append("\'"+list1.get(i).getString("seq_id")+"\',");
						}
					}
					String stri = str.toString();
					List<JSONObject> list0 = null;
					if(!stri.equals("")) {
						list0 = logic.selectGoodSupplierByInGoods(str.toString());
					}
					Map<String,JSONObject> goodSupplierMap = new HashMap<String,JSONObject>();
					/**
					 * 2019.07.10 lwg
					 * 从redis中查询厂家数据
					 */
					if(list0 != null && list0.size() > 0) {
						for(JSONObject obj : list0) {
							if(obj.getString("supplier")!=null&&!obj.getString("supplier").equals("")){
								goodSupplierMap.put(obj.getString("goodsuuid"), obj);
							}
						}
					}
					
					for(JSONObject good : list1) {
						JSONObject obj = goodSupplierMap.get(good.get("seq_id"));
						String supplierId = "";
						String suppliername = "";
						if(null != obj) {
							supplierId = (String) obj.get("supplier");
							suppliername = (String) obj.get("suppliername");
						}
						if(supplierId!=null&&!supplierId.equals("")&&suppliername!=null&&!suppliername.equals("")){
							good.put("supplier", supplierId);
							good.put("suppliername", suppliername);
						}
					}
					//根据条件进行过滤
					if(YZUtility.isNotNullOrEmpty(supplier)) {
						Iterator<JSONObject> it = list1.iterator();
						while (it.hasNext()) {
							JSONObject o = it.next();
							if(o.get("supplier")!=null&&!o.get("supplier").equals(supplier)) {
								it.remove();
							}
						}
					}
				}
				/**
				 * End
				 */
				
			}
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("入库明细", fieldArr, fieldnameArr, list1, response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(list,null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 商品基本资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectGoodsDetailList.act")
	public String selectGoodsDetailList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String queryInput = request.getParameter("queryInput");
			String goodstype = request.getParameter("goodstype");
			String goodsname = request.getParameter("goodsname");
			String goodsuuid = request.getParameter("goodsuuid");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("queryInput", queryInput);
			}
			if (!YZUtility.isNullorEmpty(goodstype)) {
				map.put("goodstype", goodstype);
			}
			if (!YZUtility.isNullorEmpty(goodsname)) {
				map.put("goodsname", goodsname);
			}
			if (!YZUtility.isNullorEmpty(goodsuuid)) {
				map.put("goodsuuid", goodsuuid);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request));
//			List<JSONObject> list = logic.selectGoodsDetailList(map);
			
			JSONObject jsonOb = new JSONObject();
			// 初始化分页实体类
			@SuppressWarnings("rawtypes")
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			jsonOb = logic.selectGoodsDetailList(map,bp,jsonOb);
			/*-------导出excel---------*/
			List<JSONObject> list = jsonOb.getJSONArray("rows");//添加分页
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("商品资料", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
//			YZUtility.RETURN_LIST(list, response, logger);
			YZUtility.DEAL_SUCCESS(jsonOb,null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 库存报警查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectGoodsGjList.act")
	public String selectGoodsGjList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String queryInput = request.getParameter("queryInput");
			String sshouse = request.getParameter("sshouse");
			String goodstype = request.getParameter("goodstype");
			String goodsname = request.getParameter("goodsname");
			String goodsuuid = request.getParameter("goodsuuid");
			String gjtype = request.getParameter("gjtype");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("queryInput", queryInput);
			}
			if (!YZUtility.isNullorEmpty(sshouse)) {
				map.put("sshouse", sshouse);
			}
			if (!YZUtility.isNullorEmpty(goodstype)) {
				map.put("goodstype", goodstype);
			}
			if (!YZUtility.isNullorEmpty(goodsname)) {
				map.put("goodsname", goodsname);
			}
			if (!YZUtility.isNullorEmpty(goodsuuid)) {
				map.put("goodsuuid", goodsuuid);
			}
			if (!YZUtility.isNullorEmpty(gjtype)) {
				map.put("gjtype", gjtype);
			}
			map.put("organization", ChainUtil.getCurrentOrganization(request));
			List<JSONObject> list = new ArrayList<JSONObject>();
			if (!YZUtility.isNullorEmpty(sshouse)) {
				list = logic.selectGoodsGjList(map);
			} else {
				list = logic.selectGoodsGjNoHousseList(map);
			}

			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("库存告警", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 报警提醒
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveGoodsGjTx.act")
	public String saveGoodsGjTx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String organization = ChainUtil.getCurrentOrganization(request);
			Map<String, String> map = new HashMap<String, String>();
			map.put("organization", organization);
			List<JSONObject> list = logic.selectGoodsGjNoHousseList(map);
			// 报警提醒
			String ck = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_CK_SEQID);
			logic.selectGoodsGjTx(list, ck, organization);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	@RequestMapping(value = "/excelStandardTemplateOut.act")
	public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File f = new File(ConstUtil.ROOT_DIR + "\\model\\商品导入模板.xls");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("商品导入模板" + ".xls").getBytes(), "iso-8859-1"));// 下载文件的名称
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

}