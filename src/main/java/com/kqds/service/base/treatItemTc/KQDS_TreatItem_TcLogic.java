package com.kqds.service.base.treatItemTc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsTreatitemTc;
import com.kqds.entity.base.KqdsTreatitemTcType;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes" })
@Service
public class KQDS_TreatItem_TcLogic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	@Autowired
	private KQDS_TreatItemLogic treatItemLogic;

	/**
	 * 添加收费套餐，前后台共用
	 * 
	 * @param dbConn
	 * @param tctype
	 * @param tcname
	 * @param map
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public void newAddTc(String tctype, String tcname, Map<String, String> map, HttpServletRequest request) throws Exception {
		KqdsTreatitemTcType dptype = new KqdsTreatitemTcType();
		KqdsTreatitemTcType dpname = new KqdsTreatitemTcType();

		String organization = map.get("organization");

		YZPerson person = SessionUtil.getLoginPerson(request);
		List<KqdsTreatitemTcType> list = (List<KqdsTreatitemTcType>) loadList(TableNameUtil.KQDS_TREATITEM_TC_TYPE, map);
		if (list != null && list.size() > 0) {
			// 存在套餐类型 则不新建
			dptype = list.get(0);
			// 新建套餐名称
			dpname.setSeqId(YZUtility.getUUID());
			dpname.setName(tcname);
			dpname.setIsopen(0);
			dpname.setParentid(dptype.getSeqId());
			dpname.setOrganization(organization);
			dpname.setCreatetime(YZUtility.getCurDateTimeStr());
			dpname.setCreateuser(person.getSeqId());
			saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dpname);
		} else {
			// 新建套餐类型
			dptype.setSeqId(YZUtility.getUUID());
			dptype.setName(tctype);
			dptype.setIsopen(0);
			dptype.setParentid("0");
			dptype.setOrganization(organization);
			dptype.setCreatetime(YZUtility.getCurDateTimeStr());
			dptype.setCreateuser(person.getSeqId());
			saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dptype);

			// 新建套餐名称
			dpname.setSeqId(YZUtility.getUUID());
			dpname.setName(tcname);
			dpname.setIsopen(0);
			dpname.setParentid(dptype.getSeqId());
			dpname.setOrganization(organization);
			dpname.setCreatetime(dptype.getCreatetime());
			dpname.setCreateuser(person.getSeqId());
			saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC_TYPE, dpname);
		}
		// 获取的对象集合 json格式
		String listdata = request.getParameter("params");
		JSONArray jArray = JSONArray.fromObject(listdata);
		Collection collection = JSONArray.toCollection(jArray, KqdsTreatitemTc.class);
		Iterator it = collection.iterator();
		// 保存套餐明细
		KqdsTreatitemTc detail = new KqdsTreatitemTc();
		while (it.hasNext()) {
			detail = (KqdsTreatitemTc) it.next();
			detail.setTcnameid(dpname.getSeqId());
			detail.setSeqId(YZUtility.getUUID());
			detail.setArrearmoney("0");
			detail.setVoidmoney("0");
			detail.setPaymoney(detail.getSubtotal());
			detail.setCreatetime(YZUtility.getCurDateTimeStr());
			detail.setCreateuser(person.getSeqId());
			detail.setOrganization(organization); // ### 【前端调用，以当前所在门诊为主】

			/** 这儿不能引用KQDS_TreatItemLogic类的getByTreatItemno方法，否则会内存溢出 **/
			KqdsTreatitem treatitem = treatItemLogic.getByTreatItemno(detail.getItemno());
			if (treatitem == null) {
				throw new Exception("收费编号对应的收费项目不存在");
			}

			if (1 == treatitem.getIsyjjitem()) { // 0 请选择 1预交金 2挂号 3治疗费 4 生成回访
				throw new Exception("预交金不能作为收费套餐项目");
			}

			saveSingleUUID(TableNameUtil.KQDS_TREATITEM_TC, detail);
		}
	}

	/**
	 * 根据消费项目编号统计
	 * 
	 * @param conn
	 * @param itemnos
	 * @return
	 * @throws Exception
	 */
	public int getCountByItemnos(String itemnos) throws Exception {
		int count = (int) dao.findForObject(TableNameUtil.KQDS_TREATITEM_TC + ".getCountByItemnos", YZUtility.ConvertStringIds4Query(itemnos));
		return count;
	}

	public int checkTc(String parentid, String tcname, String seqid) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("parentid", parentid);
		map1.put("name", tcname);
		map1.put("seqId", seqid);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_TREATITEM_TC + ".checkTc", map1);
		return count;
	}

	/**
	 * 查询套餐收费项目条数
	 * 
	 * @param tctype
	 * @param tcname
	 * @return
	 * @throws Exception
	 */
	public int selectTcxmCount(String table, String tcnameid) throws Exception {
		int num = (int) dao.findForObject(TableNameUtil.KQDS_TREATITEM_TC + ".selectTcxmCount", tcnameid);
		return num;
	}

	/**
	 * 门诊条件过滤
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectWithPage(String table, BootStrapPage bp, Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM_TC + ".selectWithPage", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @param tcnameid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectWithPageBytctypeAndname(String table, BootStrapPage bp, String tcnameid) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM_TC + ".selectWithPageBytctypeAndname", tcnameid);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param conn
	 * @param table
	 * @param bp
	 * @param tcnameid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectNoPageBytctypeAndname(String table, BootStrapPage bp, String tcnameid) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.KQDS_TREATITEM_TC + ".selectNoPageBytctypeAndname", tcnameid);
		return list;
	}

	/**
	 * 根据套餐类别，获取套餐名称列表
	 * 
	 * @param dbConn
	 * @param tctypeid
	 * @param createuser
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsTreatitemTcType> getTcNameListByTcType(String tctypeid, String createuser, String organization) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("createuser", createuser);
		map1.put("parentid", tctypeid);
		map1.put("organization", organization);

		List<KqdsTreatitemTcType> list = (List<KqdsTreatitemTcType>) dao.findForList(TableNameUtil.KQDS_TREATITEM_TC + ".getTcNameListByTcType", map1);
		return list;
	}

	/**
	 * 根据套餐类别，统计套餐名称数量
	 * 
	 * @param dbConn
	 * @param tctypeid
	 * @param createuser
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	public int countTcNameByTcType(String tctypeid, String createuser, String organization) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("createuser", createuser);
		map1.put("parentid", tctypeid);
		map1.put("organization", organization);
		int count = (int) dao.findForObject(TableNameUtil.KQDS_TREATITEM_TC + ".countTcNameByTcType", map1);
		return count;
	}

	/**
	 * 获取套餐类别列表
	 * 
	 * @param dbConn
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<KqdsTreatitemTcType> getTcTypeList(String organization) throws Exception {
		List<KqdsTreatitemTcType> list = (List<KqdsTreatitemTcType>) dao.findForList(TableNameUtil.KQDS_TREATITEM_TC + ".getTcTypeList", organization);
		return list;
	}

}