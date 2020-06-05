package com.hudh.ykzz.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hudh.lclj.StaticVar;
import com.hudh.ykzz.dao.IYkzzDrugsDao;
import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.util.CreateOrderNoUtil;
import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.entity.base.KqdsCkGoodsType;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.base.KqdsCkSupplier;
import com.kqds.entity.base.KqdsInformation;
import com.kqds.entity.base.KqdsOutprocessing;
import com.kqds.entity.base.KqdsOutprocessingType;
import com.kqds.entity.base.KqdsOutprocessingUnit;
import com.kqds.entity.base.KqdsPaiban;
import com.kqds.entity.base.KqdsPaibanType;
import com.kqds.entity.base.KqdsTreatitem;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.outProcessing.KQDS_OutProcessingLogic;
import com.kqds.service.base.outProcessing.KQDS_OutProcessing_TypeLogic;
import com.kqds.service.base.outProcessingUnit.KQDS_outProcessing_UnitLogic;
import com.kqds.service.base.treatItem.KQDS_TreatItemLogic;
import com.kqds.service.ck.KQDS_Ck_GoodstypeLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.util.base.code.UserCodeUtil;
import com.kqds.util.core.file.YZFileUploadForm;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.imports.ExcelTool;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.other.ChineseCharToEn;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@SuppressWarnings({ "unchecked", "restriction" })
@Service
public class HUDH_DrugsFileUploadLogic extends BaseLogic {
	
	private static Logger logger = LoggerFactory.getLogger(HUDH_DrugsFileUploadLogic.class);

	@Autowired
	private YZDeptLogic deptLogic;

	@Autowired
	private YZPrivLogic userPrivLogic;

	@Autowired
	private YZPersonLogic plogic;

	@Autowired
	private YZDictLogic dictLogic;

	@Autowired
	private KQDS_Ck_GoodstypeLogic goodstypeLogic;

	@Autowired
	private KQDS_UserDocumentLogic userlogic;

	@Autowired
	private KQDS_TreatItemLogic itemlogic;
	
	@Autowired
	private KQDS_outProcessing_UnitLogic jgclogic;
	
	@Autowired
	private KQDS_OutProcessing_TypeLogic typelogic;
	
	@Autowired
	private KQDS_OutProcessingLogic jgxmlogic;
	
	@Autowired
	private IYkzzDrugsDao IYkzzDrugsDao;
	
	@Autowired
	private DaoSupport dao;

	public static String getRandNum(int min, int max) {
		int randNum = min + (int) (Math.random() * ((max - min) + 1));
		return randNum + "";
	}

	/**
	 * 文件上传和导入
	 * 
	 * @param module
	 * @param fileForm
	 * @param request
	 * @param imgtype
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = { Exception.class })
	public JSONObject fileUploadAndImport(String module, YZFileUploadForm fileForm, HttpServletRequest request, String imgtype, String organization) throws Exception {
		JSONObject result = new JSONObject();
		try {
			String hard = YZUtility.getHard4File();
			Iterator<String> iKeys = fileForm.iterateFileFields();
			while (iKeys.hasNext()) {
				String fieldName = iKeys.next();
				String fileName = fileForm.getFileName(fieldName);
				if (YZUtility.isNullorEmpty(fileName)) {
					continue;
				}

				FileItem itemFile = fileForm.getFileItem(fieldName); // 文件实体
				if (itemFile == null) {
					continue;
				}

				String fileNameTmp = fileName; // 存一个副本，因为后面的程序会使fileName值变化

				String rand = YZUtility.getRondom();
				fileName = rand + "_" + fileName;

				while (YZUtility.getExist(YZSysProps.getAttachPath() + File.separator + hard, fileName)) {
					rand = YZUtility.getRondom();
					fileName = rand + "_" + fileNameTmp;
				}

				/**
				 * 商品导入、患者档案导入、消费项目导入、加工项目导入 && 排班数据导入【单独方法】
				 */
				if ("information".equals(imgtype) || "goods".equals(imgtype) || "documentuser".equals(imgtype) 
					|| "costorderitem".equals(imgtype) || "wjgitem".equals(imgtype) || "paiban".equals(imgtype) 
					|| "userdept".equals(imgtype) || "dictdata".equals(imgtype) || "drugs".equals(imgtype)) {
					FileItem fileitem = fileForm.getFileItem(fieldName);

					if ("goods".equals(imgtype) || "documentuser".equals(imgtype) || "paiban".equals(imgtype) || "drugs".equals(imgtype)) {
						organization = ChainUtil.getCurrentOrganization(request);
					}

					if (fileitem != null) {
						if ("paiban".equals(imgtype)) {
							String msg = importPaiBanData(request, fileitem, imgtype, organization); // 单独方法
							result.put("data", msg);
						} else {
							String msg = exceltodSavedata(request, fileitem, imgtype, organization); // 共用方法
							result.put("data", msg);
						}
					}
				} else {
					result.put(hard + "_" + rand, fileNameTmp);
					if (YZUtility.isNullorEmpty(module)) {
						module = YZUtility.DEFAULT_UPLOAD_MODULE_NAME;
					}
				}

				if ("wechat".equals(module) || "wechat_news".equals(module)) {
					/** 微信聊天，发送图片的场景，换成新的文件名，避免中文产生图片无法发送的问题 **/
					int index = fileName.lastIndexOf(".");
					String imgSort = fileName.substring(index, fileName.length());
					String newFileName = System.currentTimeMillis() + imgSort;
					// 用心的文件名替换之前的文件名
					fileName = fileName.replace(fileNameTmp, newFileName);
					// 替换返回结果
					result.put(hard + "_" + rand, newFileName);
				}
				// 保存上传的文件
				fileForm.saveFile(fieldName, request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + module + File.separator + hard + File.separator + fileName);
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 导入数据
	 * 
	 * @param request
	 * @param fileitem
	 * @param mtype
	 * @return
	 * @throws Exception
	 */
	private String exceltodSavedata(HttpServletRequest request, FileItem fileitem, String mtype, String organization) throws Exception {

		YZPerson person = SessionUtil.getLoginPerson(request);

		List<List<String>> list = null;
		try {
			list = ExcelTool.read(fileitem.getInputStream(), true);
		} catch (Exception e) {
			throw new Exception("导入数据文件格式有误，请以下载模板的格式重新导入（.xls的excel格式）！");
		}

		for (int i = 1; i < list.size(); i++) {
			// 行数 根据抬头算 否则容易漏值为空的列
			int rowslength = list.get(0).size();
			// 遍历行
			List<String> rows = list.get(i);
			String value = "";
			for (int j = 0; j < rowslength; j++) {
				// 遍历列
				String li = rows.get(j);
				// 防止漏值为空的列
				if (YZUtility.isNullorEmpty(li)) {
					value += li + " |||";
				} else {
					value += li + "|||";
				}
			}
			String[] val = value.split("\\|\\|\\|");
			try {
				if (mtype.equals("goods")) { // 商品导入
					importCKGoods(val, person, request, organization);
				} else if (mtype.equals("documentuser")) {
					importUserDocument(val, person, request, organization);
				} else if (mtype.equals("costorderitem")) {
					importCostItems(val, person, request, organization);
				} else if (mtype.equals("wjgitem")) {
					importOutProcessingItem(val, person, request, organization);
				} else if (mtype.equals("information")) {
					importInformation(val, person, request, organization);
				} else if (mtype.equals("userdept")) { // 部门用户导入
					importDeptUser(val, person, request);
				} else if (mtype.equals("dictdata")) { // 字典数据导入
					importDictData(val, person, request, organization);
				} else if (mtype.equals("drugs")) { // 药品导入
					importDrugs(val, person, request, organization);
				}
			} catch (Exception e) {
				throw new Exception("导入数据：第" + (i + 1) + "行数据格式有误：" + e.getMessage() + "，请重新导入！");
			}
		}
		return "成功导入数据" + (list.size() - 1) + "条！";
	}
	
	/**
	 * 药品导入
	 * @param val
	 * @param person
	 * @param request
	 * @param organization
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	private void importDrugs(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		
		// 药品名称不能为空
		if (YZUtility.isNullorEmpty(val[0])) {
			throw new Exception("药品"
					+ "不能为空");
		}
		
		// 药品零售价不能为空
		if (YZUtility.isNullorEmpty(val[1])) {
			throw new Exception("药品零售价不能为空");
		}
		
		// 折扣不能为空
		if (YZUtility.isNullorEmpty(val[2])) {
			throw new Exception("折扣不能为空");
		}
		
		// 药品化学名不能为空
		if (YZUtility.isNullorEmpty(val[3])) {
			throw new Exception("药品化学名不能为空");
		}
		
		// 药品规格不能为空
		if (YZUtility.isNullorEmpty(val[4])) {
			throw new Exception("药品规格不能为空");
		}
		
		// 药品化学名不能为空
		if (YZUtility.isNullorEmpty(val[5])) {
			throw new Exception("药品化学名不能为空");
		}
		
		// 一级分类不能为空
		if (YZUtility.isNullorEmpty(val[6])) {
			throw new Exception("一级分类不能为空");
		}
		
		// 二级分类不能为空
		if (YZUtility.isNullorEmpty(val[7])) {
			throw new Exception("二级分类不能为空");
		}
		
		// 包装数量不能为空
		if (YZUtility.isNullorEmpty(val[8])) {
			throw new Exception("包装数量不能为空");
		}
		
		// 最小单位不能为空
		if (YZUtility.isNullorEmpty(val[9])) {
			throw new Exception("最小单位不能为空");
		}
		
		// 含量系数不能为空
		if (YZUtility.isNullorEmpty(val[10])) {
			throw new Exception("含量系数不能为空");
		}
		
		// 含量单位不能为空
		if (YZUtility.isNullorEmpty(val[11])) {
			throw new Exception("含量单位不能为空");
		}
		
		// 统计大项目不能为空
		if (YZUtility.isNullorEmpty(val[12])) {
			throw new Exception("统计大项目不能为空");
		}
		
		// 国家编码不能为空
		if (YZUtility.isNullorEmpty(val[13])) {
			throw new Exception("国家编码不能为空");
		}
		
		// 药理分类不能为空
		if (YZUtility.isNullorEmpty(val[14])) {
			throw new Exception("药理分类不能为空");
		}
		
		// 药品剂型不能为空
		if (YZUtility.isNullorEmpty(val[15])) {
			throw new Exception("药品剂型不能为空");
		}
		
		// 抗生素级别不能为空
		if (YZUtility.isNullorEmpty(val[16])) {
			throw new Exception("抗生素级别不能为空");
		}
		
		// 精神药品不能为空
		if (YZUtility.isNullorEmpty(val[17])) {
			throw new Exception("精神药品不能为空");
		}
		
		// 下限值不能为空
		if (YZUtility.isNullorEmpty(val[18])) {
			throw new Exception("下限值不能为空");
		}
		
		// 下限报警提醒不能为空
		if (YZUtility.isNullorEmpty(val[19])) {
			throw new Exception("下限报警提醒不能为空");
		}
		
		// 上限值不能为空
		if (YZUtility.isNullorEmpty(val[20])) {
			throw new Exception("上限值不能为空");
		}
		
		// 上限报警提醒不能为空
		if (YZUtility.isNullorEmpty(val[21])) {
			throw new Exception("上限报警提醒不能为空");
		}
		
		// 药品标识不能为空
		if (YZUtility.isNullorEmpty(val[22])) {
			throw new Exception("药品标识不能为空");
		}
		
		// 药品介绍不能为空
		if (YZUtility.isNullorEmpty(val[23])) {
			throw new Exception("药品介绍不能为空");
		}
		
		// 厂家不能为空
		if (YZUtility.isNullorEmpty(val[24])) {
			throw new Exception("厂家不能为空");
		}
		
		// 包装单位不能为空
		if (YZUtility.isNullorEmpty(val[25])) {
			throw new Exception("包装单位不能为空");
		}
		
		// 将数据插入到药品基础表
		YkzzDrugs ykzzDrugs = new YkzzDrugs();
		ykzzDrugs.setCreatetime(YZUtility.getCurDateTimeStr());
		ykzzDrugs.setId(YZUtility.getUUID());
		ykzzDrugs.setCreator(person.getUserName());
		ykzzDrugs.setOrganization(organization);
		// 获取下一个编号
		String orderNumber = CreateOrderNoUtil.getInstance().getNextOrderNumber();// 获取下一个编号
		ykzzDrugs.setOrder_no(orderNumber);
		ykzzDrugs.setGood_name(val[0]);//药品名称
		BigDecimal bd = new BigDecimal(val[1]);
		bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
		ykzzDrugs.setDrug_retail_price(bd); //药品零售价
		ykzzDrugs.setDiscount(val[2]); //折扣
		ykzzDrugs.setChemistry_name(val[3]); // 药品化学名
		ykzzDrugs.setPharm_spec(val[4]); //药品规格
		ykzzDrugs.setCompany(val[5]); // 单位
		ykzzDrugs.setDrugs_typeone(val[6]); //一级分类
		ykzzDrugs.setDrugs_typetwo(val[7]); //二级分类
		ykzzDrugs.setPacking_num(val[8]); //包装数量
		ykzzDrugs.setCompany_min(val[9]); //最小单位
		ykzzDrugs.setContent_coe(val[10]); //含量系数
		ykzzDrugs.setContent_unit(val[11]); //含量单位
		ykzzDrugs.setSta_item(val[12]); //统计大项目
		ykzzDrugs.setContry_code(val[13]); //国家编码
		ykzzDrugs.setPharm_class(val[14]); //药理分类
		ykzzDrugs.setPharm_dos(val[15]); //药品剂型
		ykzzDrugs.setAnt_gra(val[16]); //抗生素级别
		ykzzDrugs.setPsy_drugs(val[17]); //精神药品
		ykzzDrugs.setMinnums(val[18]); //下限值
		ykzzDrugs.setMingj(val[19]); //下限报警提醒
		ykzzDrugs.setMaxnums(val[20]); //上限值
		ykzzDrugs.setMingj(val[21]); //上限报警提醒
		ykzzDrugs.setDrug_identify(val[22]); //药品标识
		ykzzDrugs.setComments(val[23]); //药品介绍
		ykzzDrugs.setManu_id(val[24]); //厂家
		ykzzDrugs.setPacking_unit(val[25]); //包装单位
		IYkzzDrugsDao.insertDrugsInfro(ykzzDrugs);
		KqdsTreatitem kqdsTreatitem = new KqdsTreatitem();
		kqdsTreatitem.setDiscount(ykzzDrugs.getDiscount());
		kqdsTreatitem.setCreateuser(ykzzDrugs.getCreator());
		kqdsTreatitem.setSeqId(YZUtility.getUUID());
		kqdsTreatitem.setTreatitemno(ykzzDrugs.getOrder_no());
		kqdsTreatitem.setTreatitemname(ykzzDrugs.getGood_name());
		BigDecimal retailPrice = ykzzDrugs.getDrug_retail_price();
		kqdsTreatitem.setUnitprice(retailPrice.toString());
		kqdsTreatitem.setUnit(ykzzDrugs.getCompany());
		kqdsTreatitem.setXmjs(ykzzDrugs.getComments());
		kqdsTreatitem.setUseflag(0);
		kqdsTreatitem.setCreatetime(YZUtility.getCurDateTimeStr());
		kqdsTreatitem.setBasetype(StaticVar.HUDH_BASE_TYPE);//一级分类
		kqdsTreatitem.setNexttype(StaticVar.HUDH_NEXT_TYPE);//二级分类
		kqdsTreatitem.setStatus(StaticVar.HUDH_STATUS);//1表示为药品
		kqdsTreatitem.setOrganization(ykzzDrugs.getOrganization());
		kqdsTreatitem.setIstsxm(0);
		kqdsTreatitem.setIsyjjitem(0);
		kqdsTreatitem.setIssplit(0);
		IYkzzDrugsDao.addTreatItemBack(kqdsTreatitem);
	}

	/**
	 * 处理上传附件，返回附件id，base64
	 * 
	 * @param imgStr
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param
	 * @return Map<String, String> ==> {id = 文件名}
	 * @throws Exception
	 */
	public JSONObject fileUploadLogicForbase64(String module, String imgStr, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		try {
			String hard = YZUtility.getHard4File();
			String fileName = YZUtility.getCurDateTimeStr(YZUtility.DATE_FORMAT_NOSPLIT_yyyyMMddHHmmss) + ".png";
			String rand = YZUtility.getRondom();
			// 返回json
			result.put("attrId", hard + "_" + rand + ",");
			result.put("attrName", fileName + "*");
			// 完整文件路径
			fileName = rand + "_" + fileName;
			while (YZUtility.getExist(YZSysProps.getAttachPath() + File.separator + hard, fileName)) {
				rand = YZUtility.getRondom();
				fileName = rand + "_" + fileName;
			}

			if (YZUtility.isNullorEmpty(module)) {
				module = YZUtility.DEFAULT_UPLOAD_MODULE_NAME;
			}
			generateImage(imgStr, request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + module + File.separator + hard + File.separator,
					fileName);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 功能描述：base64字符串转换成图片
	 * 
	 * @since 2016/5/24
	 */
	private boolean generateImage(String imgStr, String filePath, String fileName) {
		try {
			if (imgStr == null) {
				return false;
			}
			BASE64Decoder decoder = new BASE64Decoder();
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			// 如果目录不存在，则创建
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 生成图片
			OutputStream out = new FileOutputStream(filePath + fileName);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {

			// log.error("生成图片异常：{}", e.getMessage());
			return false;
		}
	}

	/**
	 * 排班数据导入
	 * 
	 * @param request
	 * @param fileitem
	 * @param mtype
	 * @return
	 * @throws Exception
	 */
	public String importPaiBanData(HttpServletRequest request, FileItem fileitem, String mtype, String organization) throws Exception {

		YZPerson person = SessionUtil.getLoginPerson(request);

		List<List<String>> list = null;
		try {
			list = ExcelTool.read(fileitem.getInputStream(), true);
		} catch (Exception e) {
			throw new Exception("导入数据文件格式有误，请以下载模板的格式重新导入（.xls的excel格式）！");
		}

		if (list == null || list.size() == 0) {
			throw new Exception("导入的文件无数据，请重新导入！");
		}

		// 获取第一行，日期
		List<String> dateRowList = list.get(0); // 第一行的第一个cell值为空

		int cloNum = 0;
		for (String date : dateRowList) {
			cloNum++;

			if (cloNum == 1) { // 不对第一列进行校验
								// #####################################【重要！！！
								// 第一列不能填空值，填空值程序会少读取一列，具体查看ImportExecl.java
								// 239行】 ys add 05-26
				continue;
			}

			if (YZUtility.isNullorEmpty(date)) { // 第一行的第一个cell值为空
				throw new Exception("第" + cloNum + "列日期格式不对，必须为yyyy-mm-dd，请重新导入！");
			}
			if (!YZUtility.isDateYYYYmmdd(date)) { // 如果日期格式不对，抛异常
				throw new Exception("第" + cloNum + "列日期格式不对，必须为yyyy-mm-dd，请重新导入！");
			}
		}
		List<String> userIdList = new ArrayList<String>();
		// 第一列为人员
		for (int i = 1; i < list.size(); i++) { // 从第二行开始取，因为第一行是日期
			// 遍历行
			List<String> rowDataList = list.get(i);
			if (YZUtility.isNullorEmpty(rowDataList.get(0))) {
				break;
			}
			String userId = rowDataList.get(0);

			if (YZUtility.isNullorEmpty(userId)) {
				throw new Exception("第" + (i + 1) + "行用户账号不能为空，请重新导入！");
			}

			userIdList.add(userId);
		}

		Map<String, String> userId2SeqIdMap = new HashMap<String, String>();
		Map<String, String> userId2DeptIdMap = new HashMap<String, String>();
		// 判断用户是否存在
		for (int i = 0; i < userIdList.size(); i++) {

			String userId = userIdList.get(i);
			Map<String, String> filtermap = new HashMap<String, String>();
			filtermap.put("user_id", userId);
			List<YZPerson> perlist = (List<YZPerson>) dao.loadList(TableNameUtil.SYS_PERSON, filtermap);

			if (perlist == null || perlist.size() == 0) {
				throw new Exception("第" + (i + 2) + "行用户账号在系统中不存在，请重新导入！");
			}

			userId2SeqIdMap.put(userId, perlist.get(0).getSeqId()); // 方便后面取值，节省查询数据库次数
			userId2DeptIdMap.put(userId, perlist.get(0).getDeptId());
		}

		// 读取排班类型表
		List<KqdsPaibanType> plist = (List<KqdsPaibanType>) dao.loadList(TableNameUtil.KQDS_PAIBAN_TYPE, null);
		Map<String, KqdsPaibanType> pbTypeMap = new HashMap<String, KqdsPaibanType>();
		for (KqdsPaibanType kqds_Paiban_Type : plist) {
			pbTypeMap.put(kqds_Paiban_Type.getTypename(), kqds_Paiban_Type);
		}

		// 读取数据，准备入库
		for (int i = 1; i < list.size(); i++) { // 从第二行开始取，因为第一行是日期
			// 遍历行
			List<String> rowDataList = list.get(i);
			if (YZUtility.isNullorEmpty(rowDataList.get(0))) {
				break;
			}
			String userId = rowDataList.get(0);

			for (int j = 1; j < dateRowList.size(); j++) { // 这里不以rowDataList为准，而以dateRowList为准，因为具体排几天，以第一行日期为准比较合适
				String zbValue = rowDataList.get(j);
				if (YZUtility.isNullorEmpty(zbValue)) { // ## 如果没填，默认值班
					String errmsg = "第" + (i + 1) + "行，第" + (j + 1) + " 列单元格内容不能为空";
					throw new Exception(errmsg);
				}

				if (!pbTypeMap.containsKey(zbValue)) {
					String errmsg = "第" + (i + 1) + "行，第" + (j + 1) + " 列排班类型在系统中不存在";
					throw new Exception(errmsg);
				}

				KqdsPaibanType pbTypeObj = pbTypeMap.get(zbValue);

				// 这里要判断是新增还是更新
				Map<String, String> pbfilter = new HashMap<String, String>();
				pbfilter.put("personid", userId2SeqIdMap.get(userId) + "");
				pbfilter.put("startdate", dateRowList.get(j));
				pbfilter.put("organization", organization);
				String SeqId = checkPaiban4DaoRu(pbfilter, organization);
				if (SeqId != null) {
					KqdsPaiban entity = (KqdsPaiban) dao.loadObjSingleUUID(TableNameUtil.KQDS_PAIBAN, SeqId);
					entity.setPersonid(userId2SeqIdMap.get(userId) + "");
					entity.setDeptid(userId2DeptIdMap.get(userId) + "");
					if (!YZUtility.isNullorEmpty(pbTypeObj.getStartdate())) {
						entity.setStartdate(dateRowList.get(j) + " " + pbTypeObj.getStartdate() + ":00");
						entity.setEnddate(dateRowList.get(j) + " " + pbTypeObj.getEnddate() + ":00");
					} else {
						entity.setStartdate(dateRowList.get(j) + ConstUtil.TIME_START);
						entity.setEnddate(dateRowList.get(j) + ConstUtil.TIME_START);
					}
					entity.setOrderstatus(zbValue);
					entity.setCreatetime(YZUtility.getCurDateTimeStr());
					entity.setCreateuser(person.getSeqId());

					dao.updateSingleUUID(TableNameUtil.KQDS_PAIBAN, entity);

					// 记录日志
					BcjlUtil.LogBcjl(BcjlUtil.UPDATE, BcjlUtil.KQDS_PAIBAN, entity, TableNameUtil.KQDS_PAIBAN, request);
				} else {
					// 将数据插入到数据库中
					KqdsPaiban entity = new KqdsPaiban();
					entity.setSeqId(YZUtility.getUUID());
					entity.setPersonid(userId2SeqIdMap.get(userId) + "");
					entity.setDeptid(userId2DeptIdMap.get(userId) + "");

					if (!YZUtility.isNullorEmpty(pbTypeObj.getStartdate())) {
						entity.setStartdate(dateRowList.get(j) + " " + pbTypeObj.getStartdate() + ":00");
						entity.setEnddate(dateRowList.get(j) + " " + pbTypeObj.getEnddate() + ":00");
					} else {
						entity.setStartdate(dateRowList.get(j) + ConstUtil.TIME_START);
						entity.setEnddate(dateRowList.get(j) + ConstUtil.TIME_START);
					}
					entity.setOrderstatus(zbValue);
					entity.setCreatetime(YZUtility.getCurDateTimeStr());
					entity.setCreateuser(person.getSeqId());
					entity.setOrganization(organization); // ###
															// 【前端页面调用，以所在门诊为准】
					dao.saveSingleUUID(TableNameUtil.KQDS_PAIBAN, entity);

					// 记录日志
					BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_PAIBAN, entity, TableNameUtil.KQDS_PAIBAN, request);

				}

			}
		}

		return "成功导入数据" + (list.size() - 1) + "条！";
	}

	/**
	 * 判断当前时间是否排过班 排过班的 不可新建 只能修改
	 * 
	 * @param conn
	 * @param map
	 * @param organization
	 * @return
	 * @throws Exception
	 */
	private String checkPaiban4DaoRu(Map<String, String> map, String organization) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.loadList(TableNameUtil.KQDS_PAIBAN, map);
		if (list != null && list.size() > 0) {
			return list.get(0).getString("seq_id");
		} else {
			return null;
		}
	}

	/**
	 * 导入部门、用户数据
	 * 
	 * @param dbConn
	 * @param orm
	 * @param val
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void importDeptUser(String[] val, YZPerson person, HttpServletRequest request) throws Exception {
		String userId = null;
		String userName = null;
		String userPriv = null; // 模板中的角色名称
		String userPrivId = null;// 角色名称对应数据库中的主键值
		String userPrivOther = null;
		StringBuffer userPrivOtherId = new StringBuffer();
		String userDept = null;
		String userDeptId = null;
		String pwd = null;
		String sortno = null;
		if (val.length != 9) { // 标准模板的列数
			throw new Exception("请确认该Excel文件的列数和标准模板一致，标准模板列数为9");
		}
		/** 用户ID校验 **/
		if (YZUtility.isNullorEmpty(val[0])) {
			return; // 第一列字段为空的，直接跳过
		}

		/** 用户姓名 **/
		if (YZUtility.isNullorEmpty(val[1])) {
			throw new Exception("真实姓名不能为空");
		}
		userName = val[1].trim();

		/** 主角色校验 **/
		if (YZUtility.isNullorEmpty(val[2])) {
			throw new Exception("主角色不能为空");
		}

		if (YZUtility.isNullorEmpty(val[4])) {
			throw new Exception("部门不能为空");
		}
		userDept = val[4].trim();

		if (YZUtility.isNullorEmpty(val[5])) {
			throw new Exception("密码不能为空");
		}
		pwd = val[5].trim();

		// 排序号可以为空
		if (YZUtility.isNullorEmpty(val[6])) {
			val[6] = "0"; // 为空，则默认赋值为0
		}
		sortno = val[6].trim();

		try {
			Integer.parseInt(sortno);
		} catch (Exception e) {
			logger.error("用户排序号【" + sortno + "】只能为数字类型：" + e.getMessage());
			throw new Exception("用户排序号【" + sortno + "】只能为数字类型：" + e.getMessage());
		}

		// 门诊编号不可以为空
		if (YZUtility.isNullorEmpty(val[7])) {
			throw new Exception("门诊编号不能为空");
		}
		String organization = val[7].trim();
		String depttypeStr = val[8].trim();
		String depttype = "";
		if (depttypeStr.contains(",")) {
			String[] depttypeArr = depttypeStr.split(",");
			for (String depttypes : depttypeArr) {
				if (depttypes.equals("咨询")) {
					depttype += "0,";
				} else if (depttypes.equals("医生")) {
					depttype += "1,";
				} else if (depttypes.equals("网电")) {
					depttype += "2,";
				} else if (depttypes.equals("营销")) {
					depttype += "3,";
				} else if (depttypes.equals("护士")) {
					depttype += "4,";
				} else if (depttypes.equals("业绩科室")) {
					depttype += "5,";
				}
			}
			if (depttype.endsWith(",")) {
				depttype = depttype.substring(0, depttype.length() - 1);
			}
		} else {
			if (depttypeStr.equals("咨询")) {
				depttype = ConstUtil.DEPT_TYPE_0;
			} else if (depttypeStr.equals("医生")) {
				depttype = ConstUtil.DEPT_TYPE_1;
			} else if (depttypeStr.equals("网电")) {
				depttype = ConstUtil.DEPT_TYPE_2;
			} else if (depttypeStr.equals("营销")) {
				depttype = ConstUtil.DEPT_TYPE_3;
			} else if (depttypeStr.equals("护士")) {
				depttype = ConstUtil.DEPT_TYPE_4;
			} else if (depttypeStr.equals("业绩科室")) {
				depttype = ConstUtil.DEPT_TYPE_5;
			}
		}
		/*************************** 以下为逻辑性判断 ***********************************/
		userId = val[0].trim();
		Map<String, String> countMap = new HashMap<String, String>();
		countMap.put("user_id", userId);
		int count1 = dao.selectCount(TableNameUtil.SYS_PERSON, countMap);
		if (count1 > 0) {
			throw new Exception("用户名已存在：" + val[0]);
		}

		userPriv = val[2].trim();
		JSONObject jsonMainPriv = userPrivLogic.getOneByPrivNameNoOrg(userPriv, organization);
		if (jsonMainPriv == null) {
			throw new Exception("角色不存在：" + userPriv);
		}
		userPrivId = jsonMainPriv.getString("seqId");// 用于入库

		// 辅助角色可以为空
		if (!YZUtility.isNullorEmpty(val[3])) {
			// 不为空时进行判断
			userPrivOther = val[3].trim();
			userPrivOther = userPrivOther.replace("，", ","); // 容错处理，中文逗号替换成英文
			String[] uOtherArr = userPrivOther.split(",");
			for (String othpriv : uOtherArr) {
				othpriv = othpriv.trim();
				if (YZUtility.isNullorEmpty(othpriv)) {
					continue;
				}
				JSONObject jsonP = userPrivLogic.getOneByPrivNameNoOrg(othpriv, organization);
				if (jsonP == null) {
					throw new Exception("辅助角色不存在：" + othpriv);
				}
				userPrivOtherId.append(jsonP.getString("seqId")).append(","); // 用于入库
			}
		}

		JSONObject jsonDept = deptLogic.getOneByNameAndCode(userDept, organization);
		if (jsonDept == null) {
			YZDept topDept = deptLogic.getTopDeptByCode(organization);
			if (topDept == null) {
				throw new Exception("所属门诊不存在，门诊编号：" + organization);
			}
			String maxDeptNo = deptLogic.getMaxDeptNO(organization);
			// 不存在，就创建
			YZDept newDept = new YZDept();
			newDept.setSeqId(YZUtility.getUUID());
			newDept.setCreatetime(YZUtility.getCurDateTimeStr());
			newDept.setCreateuser(SessionUtil.getLoginPerson(request).getSeqId());
			newDept.setDeptCode(organization);
			newDept.setDeptParent(topDept.getSeqId());
			newDept.setDeptName(userDept);
			newDept.setDeptType(depttype);
			try {
				int currNO = Integer.parseInt(maxDeptNo) + 1;
				newDept.setDeptNo(String.valueOf(currNO));
			} catch (Exception e) {
				logger.error("新建部门，排序号【" + maxDeptNo + "】转换异常：" + e.getMessage());
				newDept.setDeptNo("0");
			}
			dao.saveSingleUUID(TableNameUtil.SYS_DEPT, newDept);
			userDeptId = newDept.getSeqId(); // 用于入库
		} else {
			userDeptId = jsonDept.getString("seqId"); // 用于入库
		}

		String auPwd = YZPassEncrypt.encryptPass(pwd);// 加密密码

		YZPerson newp = new YZPerson();
		newp.setSeqId(YZUtility.getUUID());
		newp.setCreatetime(YZUtility.getCurDateTimeStr());
		newp.setCreateuser(SessionUtil.getLoginPerson(request).getSeqId());
		newp.setDeptId(userDeptId);
		// newp.setDeptIdOther();
		newp.setIsLeave(0); // 默认为未离职
		newp.setNotLogin("0"); // 默认可以登录
		// newp.setLastVisitTime(lastVisitTime)
		// newp.setMyStatus(myStatus)
		newp.setPassword(auPwd);
		// newp.setRecordaccount(recordaccount)
		// newp.setRecordpwd(recordpwd)
		// newp.setSex(sex)
		newp.setUserId(userId);
		newp.setUserName(userName);
		newp.setUserNo(Integer.parseInt(sortno));
		newp.setUserPriv(userPrivId);
		newp.setUserPrivOther(userPrivOtherId.toString());

		dao.saveSingleUUID(TableNameUtil.SYS_PERSON, newp);
	}

	/**
	 * 商品导入
	 * 
	 * @param dbConn
	 * @param val
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void importCKGoods(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		if (YZUtility.isNullorEmpty(val[0])) {
			throw new Exception("一级类别不能为空");
		}

		if (YZUtility.isNullorEmpty(val[1])) {
			throw new Exception("二级类别不能为空");
		}

		// 商品编号不能为空
		if (YZUtility.isNullorEmpty(val[2])) {
			throw new Exception("商品编号不能为空");
		}

		// 商品名称不能为空
		if (YZUtility.isNullorEmpty(val[3])) {
			throw new Exception("商品名称不能为空");
		}
		
		/*****************注释  XKY 2018-12-4 解决导入商品信息无须金额单价数量问题*******************/
		// 数量格式合法性校验
		/*if (YZUtility.isNullorEmpty(val[6])) {
			throw new Exception("数量不能为空");
		}
		try {
			new BigDecimal(val[6]);
		} catch (Exception e) {
			throw new Exception("数量必须为数字类型，不能为：" + val[6]);
		}

		// 单价
		if (YZUtility.isNullorEmpty(val[7])) {
			throw new Exception("单价不能为空");
		}
		try {
			new BigDecimal(val[7]);
		} catch (Exception e) {
			throw new Exception("单价必须为数字类型，不能为：" + val[7]);
		}

		// 金额
		if (YZUtility.isNullorEmpty(val[8])) {
			throw new Exception("金额不能为空");
		}
		try {
			new BigDecimal(val[8]);
		} catch (Exception e) {
			throw new Exception("金额必须为数字类型，不能为：" + val[8]);
		}*/
		/*****************注释  XKY 2018-12-4 解决导入商品信息无须金额单价数量问题*******************/
		
		
		// 仓库不能为空
		if (YZUtility.isNullorEmpty(val[9])) {
			throw new Exception("仓库不能为空");
		}
		
		/*****************注释  XKY 2018-12-4 解决导入商品信息无须金额单价数量问题*******************/
		/*int nums = 0;
		try {
			// nums = Integer.parseInt(val[6].trim());
			nums = (int) Float.parseFloat(val[6].trim()); // 这种写法更兼容 excel的数据格式
		} catch (Exception e) {
			throw new Exception("数量必须为数字类型，不能为：" + val[6]);
		}*/
		/*****************注释  XKY 2018-12-4 解决导入商品信息无须金额单价数量问题*******************/
		
		// 上限告警数
		int maxnums = 0;
		if (!YZUtility.isNullorEmpty(val[14])) {
			try {
			 // maxnums = Integer.parseInt(val[14].trim());
				maxnums = (int) Float.parseFloat(val[14].trim());   // 这种写法更兼容
																	// excel的数据格式
			} catch (Exception e) {
				throw new Exception("上限告警值必须为数字类型，不能为：" + val[14]);
			}
		}
		// 上限告警 开启/关闭
		int maxgj = 0;
		if ("是".equals(val[15])) {
			maxgj = 1;
		}
		// 下限告警数
		int minnums = 0;
		if (!YZUtility.isNullorEmpty(val[16])) {
			try {
			 // minnums = Integer.parseInt(val[16].trim());
				minnums = (int) Float.parseFloat(val[16].trim());   // 这种写法更兼容
																	// excel的数据格式
			} catch (Exception e) {
				throw new Exception("下限告警值必须为数字类型，不能为：" + val[16]);
			}
		}
		// 下限告警 开启/关闭
		int mingj = 0;
		if ("是".equals(val[17])) {
			mingj = 1;
		}

		// 验证商品编号
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("goodscode", val[2]);
		map2.put("organization", organization); // 当前所在门诊
		List<KqdsCkGoodsDetail> listDetail = (List<KqdsCkGoodsDetail>) dao.loadList(TableNameUtil.KQDS_CK_GOODS_DETAIL, map2);
		if (listDetail != null && listDetail.size() > 0) {
			throw new Exception("该商品编号已存在，无法重复导入，编号为：" + val[2] + "！");
		}

		// 查询商品一级分类
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("goodstype", val[0]);
		map1.put("perid", "0");
		map1.put("ORGANIZATION", organization);
		List<KqdsCkGoodsType> lelve1List = (List<KqdsCkGoodsType>) dao.loadList(TableNameUtil.KQDS_CK_GOODS_TYPE, map1);
		KqdsCkGoodsType goodstype = null;
		KqdsCkGoodsType nextgoodstype = null;
		if (lelve1List == null || lelve1List.size() == 0) {
			// 一级类别不存在，创建该一级分类
			goodstype = saveGoodsType(val[0], "0", person, request, organization);
		} else {
			// 存在一级分类
			goodstype = lelve1List.get(0);
		}

		// 判断是否存在二级分类
		map1.put("goodstype", val[1]);
		map1.put("perid", goodstype.getSeqId());
		List<KqdsCkGoodsType> lelve2List = (List<KqdsCkGoodsType>) dao.loadList(TableNameUtil.KQDS_CK_GOODS_TYPE, map1);
		if (lelve2List == null || lelve2List.size() == 0) {// 不存在二级分类
			// 创建二级分类
			nextgoodstype = saveGoodsType(val[1], goodstype.getSeqId(), person, request, organization);
		} else {
			nextgoodstype = lelve2List.get(0);
		}

		// 验证仓库是否存在
		KqdsCkHouse house = null;
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("housename", val[9]);
		map3.put("organization", organization); // 当前所在门诊
		List<KqdsCkHouse> houselist = (List<KqdsCkHouse>) dao.loadList(TableNameUtil.KQDS_CK_HOUSE, map3);
		if (houselist == null || houselist.size() == 0) {
			// 所属仓库不存在
			house = saveHouse(val[9], person, request, organization);
		} else {
			house = houselist.get(0);
		}
		if (!YZUtility.isNullorEmpty(val[10])) {
			if (YZUtility.isNullorEmpty(val[11])) {
				throw new Exception("该供应商对应的编号不能为空，供应商为：" + val[10] + "！");
			}
			// 验证供应商是否存在
			Map<String, String> map4 = new HashMap<String, String>();
			map4.put("suppliercode", val[11]);
			map4.put("organization", organization); // 当前所在门诊
			List<KqdsCkSupplier> supplierlist = (List<KqdsCkSupplier>) dao.loadList(TableNameUtil.KQDS_CK_SUPPLIER, map4);
			if (supplierlist == null || supplierlist.size() == 0) {
				// 供应商不存在
				saveSupplier(val[10], val[11], person, request, organization);
			} else {
				// 验证 供应商编号 是否对应该供应商
				if (!supplierlist.get(0).getSuppliername().equals(val[10])) {
					throw new Exception("该供应商对应的编号在系统中已存在，供应商为：" + val[10] + "！");
				}
			}
		}
		/*****************注释  XKY 2018-12-4 解决导入商品信息无须金额单价数量问题*******************/
		/*BigDecimal kcmoney = new BigDecimal(val[8].trim()).setScale(2, BigDecimal.ROUND_HALF_DOWN); // 金额
		BigDecimal goodsprice = new BigDecimal(Float.parseFloat(val[7].trim())).setScale(2, BigDecimal.ROUND_HALF_DOWN); // 单价
		BigDecimal goodspricereal = BigDecimal.ZERO;*/
		/*****************注释  XKY 2018-12-4 解决导入商品信息无须金额单价数量问题*******************/
		
		// 校验  库存*单价 = 金额
		/*if (nums != 0) {
			*//**
			 * RoundingMode.CEILING：取右边最近的整数
			 * RoundingMode.DOWN：去掉小数部分取整，也就是正数取左边，负数取右边，相当于向原点靠近的方向取整
			 * RoundingMode.FLOOR：取左边最近的正数 RoundingMode.HALF_DOWN:五舍六入，负数先取绝对值再五舍六入再负数
			 * RoundingMode.HALF_UP:四舍五入，负数原理同上
			 * RoundingMode.HALF_EVEN:这个比较绕，整数位若是奇数则四舍五入，若是偶数则五舍六入
			 *//*
			goodspricereal = new BigDecimal(val[8].trim()).divide(new BigDecimal(nums), 2, RoundingMode.HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			if (KqdsBigDecimal.compareTo(goodsprice, goodspricereal) != 0) {
				throw new Exception("单价有误：金额除以数量不等于单价，项目编号为：" + val[2]);
			}
		} else {
			// 数量是0 ，则单价、金额都为0
			goodsprice = BigDecimal.ZERO;
			kcmoney = BigDecimal.ZERO;

		}*/
		String kuwei = "", remark = "";
		
		// 库位
		if (!YZUtility.isNullorEmpty(val[12])) {
			kuwei = val[12];
		}
		
		// 备注
		if (!YZUtility.isNullorEmpty(val[13])) {
			remark = val[13];
		}
		
		// 将数据插入到商品基础表
		KqdsCkGoodsDetail detail = new KqdsCkGoodsDetail();
		detail.setSeqId(YZUtility.getUUID());
		detail.setGoodstype(nextgoodstype.getSeqId());
		detail.setOrganization(organization);
		detail.setGoodscode(val[2]);
		detail.setGoodsname(val[3]);
		detail.setGoodsnorms(val[4] == null ? "" : val[4]);
		detail.setGoodsunit(val[5] == null ? "" : val[5]);
		detail.setKuwei(kuwei);
		detail.setRemark(remark);
		detail.setCreatetime(YZUtility.getCurDateTimeStr());
		detail.setCreateuser(person.getSeqId());
		detail.setSortno(1);
		detail.setMinnums(minnums);
		detail.setMaxnums(maxnums);
		detail.setMingj(mingj);
		detail.setMaxgj(maxgj);
		detail.setOrganization(organization);   // ###
												// 【前端页面调用，当前所在门诊为准】
		// 拼音码
		String pym = ChineseCharToEn.getAllFirstLetter(detail.getGoodsname());
		detail.setPym(pym);
		dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_DETAIL, detail);

		// 记录日志
		BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_DETAIL, detail, TableNameUtil.KQDS_CK_GOODS_DETAIL, request);

		
		/*****************注释  XKY 2018-12-4 解决导入商品信息无须金额单价数量问题*******************/
		// 将数据插入到商品库存表
		/*KqdsCkGoods entity = new KqdsCkGoods();
		entity.setSeqId(YZUtility.getUUID());
		entity.setGoodsdetailid(detail.getSeqId());
		entity.setSshouse(house.getSeqId());
		entity.setGoodsprice(goodsprice);
		entity.setKcmoney(kcmoney);
		entity.setNums(nums);
		entity.setNumsexport(nums);// 导入数量
		entity.setGoodspriceexport(kcmoney);// 导入金额
		entity.setOrganization(organization);
		dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, entity);*/
		/*****************注释  XKY 2018-12-4 解决导入商品信息无须金额单价数量问题*******************/
		
	}

	/**
	 * 添加商品类别
	 * 
	 * @param dbConn
	 * @param goodstypename
	 * @param perid
	 * @param person
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private KqdsCkGoodsType saveGoodsType(String goodstypename, String perid, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		// 一级类别不存在，创建该一级分类
		KqdsCkGoodsType goodstype = new KqdsCkGoodsType();
		goodstype.setSeqId(YZUtility.getUUID());
		// 根据中文 对应拼音首字母
		String typeno = goodstypeLogic.getUniTypenoByName(goodstypename, perid);
		goodstype.setTypeno(typeno);
		goodstype.setGoodstype(goodstypename);
		goodstype.setSortno(1);
		goodstype.setPerid(perid);
		goodstype.setCreatetime(YZUtility.getCurDateTimeStr());
		goodstype.setCreateuser(person.getSeqId());
		goodstype.setOrganization(organization);
		dao.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS_TYPE, goodstype);

		// 记录日志
		BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_GOODS_TYPE, goodstype, TableNameUtil.KQDS_CK_GOODS_TYPE, request);
		return goodstype;
	}

	/**
	 * 添加仓库
	 * 
	 * @param dbConn
	 * @param housename
	 * @param person
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private KqdsCkHouse saveHouse(String housename, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		KqdsCkHouse house = new KqdsCkHouse();
		house.setSeqId(YZUtility.getUUID());
		house.setHousename(housename);
		house.setSortno(1);
		house.setCreatetime(YZUtility.getCurDateTimeStr());
		house.setCreateuser(person.getSeqId());
		house.setOrganization(organization); // ###
												// 【前端页面调用，当前所在门诊为准】
		dao.saveSingleUUID(TableNameUtil.KQDS_CK_HOUSE, house);

		// 记录日志
		BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_HOUSE, house, TableNameUtil.KQDS_CK_HOUSE, request);
		return house;
	}

	/**
	 * 添加供应商
	 * 
	 * @param dbConn
	 * @param suppliername
	 * @param suppliercode
	 * @param person
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private KqdsCkSupplier saveSupplier(String suppliername, String suppliercode, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		KqdsCkSupplier supplier = new KqdsCkSupplier();
		supplier.setSeqId(YZUtility.getUUID());
		supplier.setSuppliername(suppliername);
		supplier.setSuppliercode(suppliercode);
		supplier.setSortno(1);
		supplier.setCreatetime(YZUtility.getCurDateTimeStr());
		supplier.setCreateuser(person.getSeqId());
		supplier.setOrganization(organization);
		dao.saveSingleUUID(TableNameUtil.KQDS_CK_SUPPLIER, supplier);
		// 记录日志
		BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_CK_SUPPLIER, supplier, TableNameUtil.KQDS_CK_SUPPLIER, request);
		return supplier;
	}

	/**
	 * 患者档案导入
	 * 
	 * @param dbConn
	 * @param val
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void importUserDocument(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		if (YZUtility.isNullorEmpty(val[0])) {
			throw new Exception("姓名不能为空");
		}

		if (YZUtility.isNullorEmpty(val[1])) {
			throw new Exception("性别不能为空");
		}

		if (YZUtility.isNullorEmpty(val[2])) {
			throw new Exception("电话不能为空");
		}

		if (YZUtility.isNullorEmpty(val[3])) {
			throw new Exception("患者来源不能为空");
		}

		if (YZUtility.isNullorEmpty(val[4])) {
			throw new Exception("患者来源子分类不能为空");
		}

		// 手机号码验证
		String phonenumber = val[2];
		Map<String, String> mapsj = new HashMap<String, String>();
		mapsj.put("phonenumber1", phonenumber);
		int num = userlogic.checkphonenumber(null, mapsj, TableNameUtil.KQDS_USERDOCUMENT);
		if (num > 0) {
			throw new Exception("电话号码已存在，号码为：" + phonenumber);
		}

		// 患者来源
		YZDict dict = null;
		try {
			dict = dictLogic.getDetailByNameAndParentCode(val[3], DictUtil.HZLY);
		} catch (Exception e) {
			throw new Exception("患者来源数据异常，错误信息为：" + e.getMessage());
		}

		// 患者来源子分类
		YZDict subDict = null;
		try {
			subDict = dictLogic.getDetailByNameAndParentCode(val[4], dict.getDictCode());
		} catch (Exception e) {
			throw new Exception("患者来源子分类数据异常，错误信息为：" + e.getMessage());
		}

		// 将数据插入到数据库中
		KqdsUserdocument entity = new KqdsUserdocument();
		// 查询开发人编号
		YZPerson per = plogic.getPersonByUserId(val[6]);
		if (per == null) {
			throw new Exception("开发人不存在");
		}
		if (YZUtility.isNullorEmpty(organization)) {
			throw new Exception("门诊编号获取不到，无法生成患者编号信息");
		}

		String uuid = YZUtility.getUUID();
		entity.setSeqId(uuid);
		entity.setIsdelete(0);
		entity.setOrganization(organization);
		entity.setCreatetime(YZUtility.getCurDateTimeStr());
		entity.setCreateuser(person.getSeqId());
		entity.setUsername(val[0] == null ? "" : val[0]);
		entity.setSex(val[1] == null ? "" : val[1]);
		entity.setPhonenumber1(val[2] == null ? "" : val[2]);
		entity.setRemark(val[5] == null ? "" : val[5]);
		entity.setDeveloper(per.getSeqId() + "");
		entity.setDevchannel(dict.getSeqId());
		entity.setNexttype(subDict.getSeqId());
		String uniqUserCode = UserCodeUtil.getUserCode(organization);
		entity.setUsercode(uniqUserCode);
		entity.setType(2); // 批量导入的用户

		// 以下几个字段，模板中没有，这里手工设置为''，否则界面表格展示会出现 null字符 ##
		// !!!!! 注意，模板中如果增加字段，且在下面的代码中出现，要把下面的对应代码删除，否则会被赋空值
		entity.setBirthday("");
		entity.setProfession("");
		entity.setIdcardno("");
		entity.setGuider("");
		entity.setIntroducer("");
		entity.setAddress("");
		entity.setRemark("");
		entity.setExperience("");
		entity.setHabit("");
		entity.setXueli("");
		entity.setQq("");
		entity.setImportant("");
		entity.setPhonenumber2("");
		entity.setOrganization(organization); // ###
												// 【前端页面调用，当前所在门诊为准】
		// 拼音码
		String pym = ChineseCharToEn.getAllFirstLetter(entity.getUsername());
		entity.setPym(pym);
		dao.saveSingleUUID(TableNameUtil.KQDS_USERDOCUMENT, entity);

		// 记录日志
		BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_USERDOCUMENT, entity, entity.getUsercode(), TableNameUtil.KQDS_USERDOCUMENT, request);

	}
	
	/**s
	 * 导入收费项目
	 * 
	 * @param dbConn
	 * @param orm
	 * @param val
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void importCostItems(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {

		if (val.length != 12) { // 标准模板的列数
			throw new Exception("请确认该Excel文件的列数和标准模板一致，标准模板列数为12");
		}

		if (YZUtility.isNullorEmpty(val[0])) {
			throw new Exception("项目编号不能为空");
		}

		int count = itemlogic.countByTreatItemno(val[0]);
		if (count > 0) {
			throw new Exception("项目编号在系统中已存在，不允许重复导入，项目编号为：" + val[0]);
		}

		if (YZUtility.isNullorEmpty(val[1])) {
			throw new Exception("项目名称不能为空");
		}

		if (YZUtility.isNullorEmpty(val[3])) {
			throw new Exception("单价不能为空");
		}

		val[3] = val[3].trim(); // 去掉左右空格

		BigDecimal unitPriceTmp = BigDecimal.ZERO;
		try {
			unitPriceTmp = new BigDecimal(val[3]);
		} catch (Exception e) {
			throw new Exception("单价必须为数字类型，不能为：" + val[3]);
		}

		if (KqdsBigDecimal.compareTo(unitPriceTmp, BigDecimal.ZERO) == -1) {
			throw new Exception("单价必须大于等于0");
		}

		BigDecimal zhekou = BigDecimal.ZERO;
		try {
			zhekou = new BigDecimal(val[4]);

		} catch (Exception e) {
			throw new Exception("折扣必须为数字类型，不能为：" + val[4]);
		}

		if (KqdsBigDecimal.compareTo(zhekou, BigDecimal.ZERO) == -1) {
			throw new Exception("折扣不能小于0，不能为：" + val[4]);
		}

		if (KqdsBigDecimal.compareTo(zhekou, new BigDecimal(100)) == 1) {
			throw new Exception("折扣不能大于100，不能为：" + val[4]);
		}

		if (YZUtility.isNullorEmpty(val[8])) {
			throw new Exception("消费项目一级分类不能为空");
		}

		if (YZUtility.isNullorEmpty(val[9])) {
			throw new Exception("消费项目二级分类不能为空");
		}

		// 特殊项目
		String istsxm = val[5];
		if (istsxm != null && istsxm.equals("是")) {
			istsxm = "1";
		} else {
			istsxm = "0";
		}
		// 领料拆分
		String llcf = val[6];
		if (llcf != null && llcf.equals("是")) {
			llcf = "1";
		} else {
			llcf = "0";
		}
		// 禁用
		String useflag = val[7];
		if (useflag != null && useflag.equals("是")) {
			useflag = "1";
		} else {
			useflag = "0";
		}
		// 基础分类，这里是中文名称
		String basetype = val[8];
		// 根据中文 对应拼音首字母
		String classNo = null;
		YZDict baseDict = dictLogic.getByNameAndParntCodeOrg(basetype, DictUtil.COSTITEM_SORT, organization);
		if (baseDict == null) {// 分类不存在，则 添加基础分类
			YZDict tcode = new YZDict();
			classNo = dictLogic.getUniDictCodeByName(basetype); // 获取唯一的编号
																// // ,
																// DictUtil.COSTITEM_SORT
			tcode.setSeqId(YZUtility.getUUID());
			tcode.setDictCode(classNo);
			tcode.setDictName(basetype);
			tcode.setCreatetime(YZUtility.getCurDateTimeStr());
			tcode.setCreateuser(person.getSeqId());
			tcode.setUseflag(0);
			tcode.setOrderno(0);
			tcode.setParentCode(DictUtil.COSTITEM_SORT);
			tcode.setOrganization(organization);
			dao.saveSingleUUID(TableNameUtil.SYS_DICT, tcode);

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.NEW, SysLogUtil.SYS_DICT, tcode, TableNameUtil.SYS_DICT, request);
		} else {
			classNo = baseDict.getDictCode();
		}

		// 上级分类
		String nexttype = val[9];
		String nexttypeid = null;
		// 根据中文 对应拼音首字母
		YZDict nextDict = dictLogic.getByNameAndParntCodeOrg(nexttype, classNo, organization);
		if (nextDict == null) {// 分类已存在
			// 添加二级分类
			YZDict ci = new YZDict();
			String subClassNo = dictLogic.getUniDictCodeByName(nexttype); // 获取唯一的编号
																			// //
																			// ,
																			// classNo
			ci.setSeqId(YZUtility.getUUID());
			ci.setDictCode(subClassNo);
			ci.setDictName(nexttype);
			ci.setCreatetime(YZUtility.getCurDateTimeStr());
			ci.setCreateuser(person.getSeqId());
			ci.setUseflag(0);
			ci.setOrderno(0);
			ci.setParentCode(classNo);
			ci.setOrganization(organization);
			dao.saveSingleUUID(TableNameUtil.SYS_DICT, ci);
			nexttypeid = ci.getSeqId();
		} else {
			nexttypeid = nextDict.getSeqId();
		}
		// 将数据插入到数据库中
		KqdsTreatitem entity = new KqdsTreatitem();
		// 查询开发人编号
		String uuid = YZUtility.getUUID();
		entity.setSeqId(uuid);
		entity.setCreatetime(YZUtility.getCurDateTimeStr());
		entity.setCreateuser(person.getSeqId());
		entity.setTreatitemno(val[0]);
		entity.setTreatitemname(val[1]);
		entity.setUnit(val[2]);
		entity.setUnitprice(val[3]);
		entity.setDiscount(val[4]);
		entity.setIstsxm(Integer.parseInt(istsxm));
		entity.setIssplit(Integer.parseInt(llcf));
		entity.setUseflag(Integer.parseInt(useflag));
		entity.setBasetype(classNo);
		entity.setNexttype(nexttypeid + "");
		entity.setXmjs(val[10] == null ? "" : val[10]);
		entity.setYhxx(val[11] == null ? "" : val[11]);
		entity.setOrganization(organization);// ###
		// 【后台基础数据维护，页面传入organization为准】
		dao.saveSingleUUID(TableNameUtil.KQDS_TREATITEM, entity);
	}

	/**
	 * 导入加工项目
	 * 
	 * @param dbConn
	 * @param val
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	private void importOutProcessingItem(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		if (YZUtility.isNullorEmpty(val[0])) {
			throw new Exception("项目编号不能为空");
		}

		int count = jgxmlogic.countByCode(val[0]);
		if (count > 0) {
			throw new Exception("项目编号在系统中已存在，不允许重复导入，项目编号为：" + val[0]);
		}

		if (YZUtility.isNullorEmpty(val[1])) {
			throw new Exception("项目名称不能为空");
		}

		if (YZUtility.isNotNullOrEmpty(val[3])) { // 存在单价的情况
			try {
				new BigDecimal(val[3]);
			} catch (Exception e) {
				throw new Exception("单价必须为数字类型，不能为：" + val[3]);
			}
		}

		if (YZUtility.isNullorEmpty(val[7])) {
			throw new Exception("加工厂不能为空");
		}

		if (YZUtility.isNullorEmpty(val[8])) {
			throw new Exception("项目分类不能为空");
		}

		// 收费分类
		String basetype = null;
		String nexttype = null;
		String dysfxm = null;

		if (!YZUtility.isNullorEmpty(val[6])) { // 存在收费项目时，判断一二级分类是否为空
			if (YZUtility.isNullorEmpty(val[4])) {
				throw new Exception("导入收费项目时，收费分类不能为空，收费项目为：" + val[6]);
			}
			YZDict dict = dictLogic.getDetailByNameAndParentCode(val[4], DictUtil.COSTITEM_SORT);
			if (dict == null) {
				throw new Exception("收费项目对应的收费分类不存在，收费项目为：" + val[6]);
			}
			basetype = dict.getSeqId();

			if (YZUtility.isNullorEmpty(val[5])) {
				throw new Exception("导入收费项目时，收费分类子类不能为空，收费项目为：" + val[6]);
			}
			YZDict dict2 = dictLogic.getDetailByNameAndParentCode(val[5], dict.getDictCode());
			if (dict2 == null) {
				throw new Exception("收费项目对应的收费分类子类不存在，收费项目为：" + val[6]);
			}
			nexttype = dict2.getSeqId();

			// 收费项目
			KqdsTreatitem treatitem = itemlogic.getTreatItem(basetype, nexttype, val[6]);
			if (treatitem == null) {
				throw new Exception("导入的收费项目不存在，收费项目为：" + val[6]);
			}
			dysfxm = treatitem.getSeqId();
		}

		// 加工厂
		String mrjgc = val[7];
		String code = null;
		KqdsOutprocessingUnit jgcDB = jgclogic.getJgcByName(mrjgc, organization);
		if (jgcDB == null) {// 加工厂不存在
			code = jgclogic.getUniCodeByName(mrjgc); // 获取唯一编号
			// 添加加工厂
			KqdsOutprocessingUnit jgc = new KqdsOutprocessingUnit();

			jgc.setSeqId(YZUtility.getUUID());
			jgc.setCreatetime(YZUtility.getCurDateTimeStr());
			jgc.setCreateuser(person.getSeqId());
			jgc.setCode(code);
			jgc.setName(mrjgc);
			jgc.setOrganization(organization); // ###【基础数据导入，以页面传入的organization为准】
			dao.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_UNIT, jgc);
			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING_UNIT, jgc, TableNameUtil.KQDS_OUTPROCESSING_UNIT, request);
		} else {
			code = jgcDB.getCode();
		}
		// 项目分类
		String xmfl = val[8];
		String typeno = typelogic.checkJgcType(xmfl, code, organization);
		if (YZUtility.isNullorEmpty(typeno)) {// 分类不存在
			// 添加分类
			KqdsOutprocessingType jgctype = new KqdsOutprocessingType();
			jgctype.setSeqId(YZUtility.getUUID());
			jgctype.setProcessingfactory(code);
			jgctype.setTypename(xmfl);
			typeno = typelogic.getUniCodeByName(xmfl, code); // 获取唯一编号
			jgctype.setTypeno(typeno);
			jgctype.setUseflag(0);
			jgctype.setCreatetime(YZUtility.getCurDateTimeStr());
			jgctype.setCreateuser(person.getSeqId());
			jgctype.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));// ###【基础数据导入，以页面传入的organization为准】
			dao.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_TYPE, jgctype);

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING_TYPE, jgctype, TableNameUtil.KQDS_OUTPROCESSING_TYPE, request);

		}
		// 将数据插入到数据库中
		KqdsOutprocessing entity = new KqdsOutprocessing();
		// 查询开发人编号
		String uuid = YZUtility.getUUID();
		entity.setSeqId(uuid);
		entity.setCreatetime(YZUtility.getCurDateTimeStr());
		entity.setCreateuser(person.getSeqId());
		entity.setWjgxmbh(val[0]);
		entity.setWjgmc(val[1]);
		entity.setDw(val[2]);
		entity.setDj(val[3]);
		entity.setBasetype(basetype);
		entity.setNexttype(nexttype);
		entity.setDysfxm(dysfxm);
		entity.setMrjgc(code);
		entity.setWjgfl(typeno);
		entity.setRemark(val[9]);
		entity.setOrganization(ChainUtil.getOrganizationFromUrlCanNull(request));// ###【基础数据导入，以页面传入的organization为准】
		dao.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING, entity);
	}

	/**
	 * 导入加工项目
	 * 
	 * @param dbConn
	 * @param val
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	private void importInformation(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		String xxtype = val[0];
		String xxtitle = val[1];
		String xxcontent = "";
		if (YZUtility.isNullorEmpty(val[0])) {
			throw new Exception("信息分类不能为空");
		}
		if (YZUtility.isNullorEmpty(val[1])) {
			throw new Exception("信息标题不能为空");
		}
		if (YZUtility.isNullorEmpty(val[2])) {
			throw new Exception("信息内容不能为空");
		}
		YZDict dict = dictLogic.getDetailByNameAndParentCode(xxtype, DictUtil.XXKFL);
		if (dict == null) {
			dict = new YZDict();
			dict.setSeqId(YZUtility.getUUID());
			dict.setDictCode("XXKFL_" + YZUtility.getUUID());
			dict.setDictName(val[0]);
			dict.setCreatetime(YZUtility.getCurDateTimeStr());
			dict.setCreateuser(person.getSeqId());
			dict.setUseflag(0);
			dict.setOrderno(0);
			dict.setParentCode(DictUtil.XXKFL);
			dict.setOrganization(organization);
			dao.saveSingleUUID(TableNameUtil.SYS_DICT, dict);
		}
		xxtype = dict.getSeqId();
		String start_p = "<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑, Tahoma, Arial, sans-serif; white-space: normal; background-color: rgb(255, 255, 255);\">";
		String end_p = "</p>";
		String xxcontentArr[] = val[2].split("\\s+");
		for (int i = 0; i < xxcontentArr.length; i++) {
			String content = xxcontentArr[i];
			if (!content.equals("")) {
				xxcontent += start_p + content + end_p;
			}
		}

		KqdsInformation infomation = new KqdsInformation();
		String uuid = YZUtility.getUUID();
		infomation.setSeqId(uuid);
		infomation.setCreatetime(YZUtility.getCurDateTimeStr());
		infomation.setCreateuser(person.getSeqId());
		infomation.setOrganization(organization);
		infomation.setType(xxtype);
		infomation.setTitle(xxtitle);
		infomation.setContent(xxcontent);
		dao.saveSingleUUID(TableNameUtil.KQDS_INFORMATION, infomation);
	}

	/**
	 * 导入部门、用户数据
	 * 
	 * @param dbConn
	 * @param orm
	 * @param val
	 * @param person
	 * @param request
	 * @throws Exception
	 */
	public void importDictData(String[] val, YZPerson person, HttpServletRequest request, String organization) throws Exception {
		int useflag2 = 0;
		int useflag3 = 0;
		if (val.length != 5) { // 标准模板的列数
			throw new Exception("请确认该Excel文件的列数和标准模板一致，标准模板列数为5");
		}
		/** 一级分类 **/
		if (YZUtility.isNullorEmpty(val[0])) {
			throw new Exception("一级分类不能为空");
		}
		/** 二级分类 **/
		if (YZUtility.isNullorEmpty(val[1])) {
			throw new Exception("二级分类不能为空");
		}

		if (!YZUtility.isNullorEmpty(val[2]) && val[2].equals("是")) {
			useflag2 = 1;
		}
		if (!YZUtility.isNullorEmpty(val[4]) && val[4].equals("是")) {
			useflag3 = 1;
		}
		// 一级分类
		YZDict level1Dict = dictLogic.getByNameAndParntCodeOrg(val[0], "0", organization);
		if (level1Dict == null) {
			level1Dict = new YZDict();
			String pym = ChineseCharToEn.getAllFirstLetter_RandNum(val[0]);
			level1Dict.setSeqId(YZUtility.getUUID());
			level1Dict.setDictCode(pym);
			level1Dict.setDictName(val[0]);
			level1Dict.setCreatetime(YZUtility.getCurDateTimeStr());
			level1Dict.setCreateuser(person.getSeqId());
			level1Dict.setUseflag(0);
			level1Dict.setOrderno(0);
			level1Dict.setParentCode("0");
			level1Dict.setOrganization(organization);
			dao.saveSingleUUID(TableNameUtil.SYS_DICT, level1Dict);
		}
		// 二级分类
		YZDict level2Dict = dictLogic.getByNameAndParntCodeOrg(val[1], level1Dict.getDictCode(), organization);
		if (level2Dict == null) {
			level2Dict = new YZDict();
			String pym = ChineseCharToEn.getAllFirstLetter_RandNum(val[1]);
			level2Dict.setSeqId(YZUtility.getUUID());
			level2Dict.setDictCode(level1Dict.getDictCode() + "_" + pym);
			level2Dict.setDictName(val[1]);
			level2Dict.setCreatetime(YZUtility.getCurDateTimeStr());
			level2Dict.setCreateuser(person.getSeqId());
			level2Dict.setUseflag(useflag2);
			level2Dict.setOrderno(0);
			level2Dict.setParentCode(level1Dict.getDictCode());
			level2Dict.setOrganization(organization);
			dao.saveSingleUUID(TableNameUtil.SYS_DICT, level2Dict);
		}
		// 三级分类
		if (!YZUtility.isNullorEmpty(val[3])) {
			YZDict level3Dict = dictLogic.getByNameAndParntCodeOrg(val[3], level2Dict.getDictCode(), organization);
			if (level3Dict == null) {
				level3Dict = new YZDict();
				String pym = ChineseCharToEn.getAllFirstLetter_RandNum(val[3]);
				level3Dict.setSeqId(YZUtility.getUUID());
				level3Dict.setDictCode(level2Dict.getDictCode() + "_" + pym);
				level3Dict.setDictName(val[3]);
				level3Dict.setCreatetime(YZUtility.getCurDateTimeStr());
				level3Dict.setCreateuser(person.getSeqId());
				level3Dict.setUseflag(useflag3);
				level3Dict.setOrderno(0);
				level3Dict.setParentCode(level2Dict.getDictCode());
				level3Dict.setOrganization(organization);
				dao.saveSingleUUID(TableNameUtil.SYS_DICT, level3Dict);
			}
		}
	}
}
