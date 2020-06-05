package com.hudh.lclj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hudh.lclj.dao.NodeConfigDao;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
import com.kqds.util.sys.YZUtility;
import org.apache.commons.lang3.StringUtils;
/**
 * 临床路径操作Controller
 * @author XKY
 *
 */
@Controller
@RequestMapping("/HUDH_TestAct")
public class HUDH_TestAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_TestAct.class);
	/**
	 * 临床路径数据保存接口
	 */
	@Autowired
	private NodeConfigDao nodeConfigDao;
	
	@Autowired
	private KQDS_Ck_Goods_DetailLogic logic;
	private static FormulaEvaluator evaluator;
	@RequestMapping(value= "initFlowNodes.act")
	public String initFlowNodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel文件路径
        String excelPath = "D:\\数据（lclj）excel\\新增临床路径.xlsx";
        try {
        	// 打开指定位置的Excel文件
        	FileInputStream file = new FileInputStream(new File(excelPath));
        	Workbook workbook = new XSSFWorkbook(file);
        	// 打开Excel中的第一个Sheet
        	Sheet sheet = workbook.getSheetAt(0);
        	int row = sheet.getLastRowNum()+1;
        	System.out.println("有效行数"+row);
        	List<LcljNodeConfig> list = new ArrayList<LcljNodeConfig>();
        	LcljNodeConfig nodeConfig = null;
        	for(int i=1;i<row;i++) {
        		System.out.println(i);
        		nodeConfig = new LcljNodeConfig();
        		/*System.out.println(sheet.getRow(i).getCell(0).getStringCellValue());*/
        		nodeConfig.setId(YZUtility.getUUID());
        		/*nodeConfig.setNum(Integer.valueOf(sheet.getRow(i).getCell(0).getStringCellValue())); //编号*/
        		nodeConfig.setNum(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(0))));
        		if(null != sheet.getRow(i).getCell(1)) {
        			sheet.getRow(i).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setNodeId(sheet.getRow(i).getCell(1).getStringCellValue()); //节点id
        		}
        		if(null != sheet.getRow(i).getCell(2)) {
        			sheet.getRow(i).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setNodeName(sheet.getRow(i).getCell(2).getStringCellValue()); //节点名称
        		}
        		if(null != sheet.getRow(i).getCell(3)) {
        			sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setAuthorType(sheet.getRow(i).getCell(3).getStringCellValue()); //办理人类型
        		}
        		if(null != sheet.getRow(i).getCell(4)) {
        			sheet.getRow(i).getCell(4).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setAuthor(sheet.getRow(i).getCell(4).getStringCellValue()); //办理人
        		}
        		if(null != sheet.getRow(i).getCell(5)) {
        			sheet.getRow(i).getCell(5).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setViewUrl(sheet.getRow(i).getCell(5).getStringCellValue()); //操作界面
        		}
        		if(null != sheet.getRow(i).getCell(6)) {
        			sheet.getRow(i).getCell(6).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setOrganization(sheet.getRow(i).getCell(6).getStringCellValue()); //门诊
        		}
        		if(null != sheet.getRow(i).getCell(7)) {
        			sheet.getRow(i).getCell(7).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setCreatrtime(sheet.getRow(i).getCell(7).getStringCellValue()); //创建时间
        		}
        		if(null != sheet.getRow(i).getCell(8)) {
        			sheet.getRow(i).getCell(8).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setNodeLimit(sheet.getRow(i).getCell(8).getStringCellValue()); //节点时限
        		}
        		if(null != sheet.getRow(i).getCell(9)) {
        			sheet.getRow(i).getCell(9).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setLimitType(sheet.getRow(i).getCell(9).getStringCellValue()); //显示类型
        		}
        		if(null != sheet.getRow(i).getCell(10)) {
        			sheet.getRow(i).getCell(10).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setFlowType(sheet.getRow(i).getCell(10).getStringCellValue()); //显示流程分类
        		}
        		if(null != sheet.getRow(i).getCell(11)) {
        			sheet.getRow(i).getCell(11).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setFlowCode(sheet.getRow(i).getCell(11).getStringCellValue()); //所属流程
        		}
        		if(null != sheet.getRow(i).getCell(12)) {
        			sheet.getRow(i).getCell(12).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setLimitBench(sheet.getRow(i).getCell(12).getStringCellValue());
        		}
        		if(null != sheet.getRow(i).getCell(13)) {
        			sheet.getRow(i).getCell(13).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setArticleType(sheet.getRow(i).getCell(13).getStringCellValue());
        		}
        		if(null != sheet.getRow(i).getCell(14)) {
        			sheet.getRow(i).getCell(14).setCellType(Cell.CELL_TYPE_STRING);
        			nodeConfig.setDentalJaw(sheet.getRow(i).getCell(14).getStringCellValue());
        		}
        		 //超期基准节点
        		list.add(nodeConfig);
        	}
        	nodeConfigDao.batchSaveNodeConfig(list);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 //获取单元格各类型值，返回字符串类型
    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType=cell.getCellType();
        if(cellType==Cell.CELL_TYPE_FORMULA){ //表达式类型
            cellType=evaluator.evaluate(cell).getCellType();
        }
         
        switch (cellType) {
        case Cell.CELL_TYPE_STRING: //字符串类型
            cellValue= cell.getStringCellValue().trim();
            cellValue=StringUtils.isEmpty(cellValue) ? "" : cellValue; 
            break;
        case Cell.CELL_TYPE_BOOLEAN:  //布尔类型
            cellValue = String.valueOf(cell.getBooleanCellValue()); 
            break; 
        case Cell.CELL_TYPE_NUMERIC: //数值类型
             if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
             } else {  //否
                 cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue()); 
             } 
            break;
        default: //其它类型，取空串吧
            cellValue = "";
            break;
        }
        return cellValue;
    }
    
  /*@RequestMapping(value= "initCkGoods.act")
	public String initCkGoods(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//excel文件路径
        String excelPath = "D:\\补充仓库物品数据.xlsx";
        try {
        	// 打开指定位置的Excel文件
        	FileInputStream file = new FileInputStream(new File(excelPath));
        	Workbook workbook = new XSSFWorkbook(file);
        	// 打开Excel中的第一个Sheet
        	Sheet sheet = workbook.getSheetAt(0);
        	int row = sheet.getLastRowNum()+1;
        	System.out.println("有效行数"+row);
        	List<LcljNodeConfig> list = new ArrayList<LcljNodeConfig>();
        	KqdsCkGoods kqdsCkGoods = null;
        	for(int i=0;i<row;i++) {
        		kqdsCkGoods = new KqdsCkGoods();
        		kqdsCkGoods.setSeqId(YZUtility.getUUID());
        		kqdsCkGoods.setGoodsdetailid(sheet.getRow(i).getCell(0).getStringCellValue());
        		String sshouse = sheet.getRow(i).getCell(1).getStringCellValue();
        		if(sshouse.indexOf("G") != -1) {
        			kqdsCkGoods.setSshouse("8ef899f5-55c9-4940-a807-542bd6fe88c3");
        		}else {
        			kqdsCkGoods.setSshouse("cd7f4473-87c1-4ac2-b3f7-8095ff1fff4c");
        		}
        		kqdsCkGoods.setOrganization("HUDH");
        		logic.saveSingleUUID(TableNameUtil.KQDS_CK_GOODS, kqdsCkGoods);
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
}
