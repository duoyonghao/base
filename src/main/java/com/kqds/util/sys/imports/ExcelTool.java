package com.kqds.util.sys.imports;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelTool {

	private static Logger logger = LoggerFactory.getLogger(ExcelTool.class);

	/** 总行数 */
	private static int totalRows = 0;

	/** 总列数 */
	private static int totalCells = 0;

	/** 错误信息 */
	private static String errorInfo;

	/** 构造方法 */
	public ExcelTool() {

	}

	/**
	 * @描述：得到总列数
	 */
	private static int getTotalCells() {
		return totalCells;
	}

	/**
	 * 
	 * @描述：是否是2003的excel，返回true是2003
	 * @返回值：boolean
	 */

	public static boolean isExcel2003(String filePath) {

		return filePath.matches("^.+\\.(?i)(xls)$");

	}

	/**
	 * 
	 * @描述：是否是2007的excel，返回true是2007
	 * @返回值：boolean
	 */

	public static boolean isExcel2007(String filePath) {

		return filePath.matches("^.+\\.(?i)(xlsx)$");

	}

	/**
	 * @描述：验证excel文件
	 * @返回值：boolean
	 */

	private boolean validateExcel(String filePath) {
		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			errorInfo = "文件名不是excel格式";
			return false;
		}

		/** 检查文件是否存在 */
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			errorInfo = "文件不存在";
			return false;
		}
		return true;
	}

	/**
	 * @throws Exception
	 * @描述：根据文件名读取excel文件
	 * @返回值：List
	 */
	public List<List<String>> read(String filePath) throws Exception {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		InputStream is = null;
		try {
			/** 验证文件是否合法 */
			if (!validateExcel(filePath)) {
				throw new Exception(errorInfo);
			}

			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (isExcel2007(filePath)) {
				isExcel2003 = false;
			}

			/** 调用本类提供的根据流读取的方法 */
			File file = new File(filePath);
			is = new FileInputStream(file);
			dataLst = read(is, isExcel2003);
			is.close();
		} catch (Exception ex) {
			logger.error("ExcelTool read Error：" + ex.getMessage());
			throw ex;
		} finally {
			if (is != null) {
				is.close();
			}
		}

		/** 返回最后读取的结果 */
		return dataLst;
	}

	/**
	 * 
	 * @throws IOException
	 * @描述：根据流读取Excel文件
	 * @返回值：List
	 */

	public static List<List<String>> read(InputStream inputStream, boolean isExcel2003) throws IOException {
		List<List<String>> dataLst = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			if (isExcel2003) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
			dataLst = read(wb);
		} catch (IOException e) {
			logger.error("ExcelTool read Error ：" + e.getMessage());
			throw e;
		}
		return dataLst;
	}

	/**
	 * 
	 * @描述：读取数据
	 * @返回值：List<List<String>>
	 */

	private static List<List<String>> read(Workbook wb) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		/** 得到第一个shell */
		Sheet sheet = wb.getSheetAt(0);
		/** 得到Excel的行数 */
		totalRows = sheet.getPhysicalNumberOfRows();
		/** 得到Excel的列数 */
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		/** 循环Excel的行 */
		for (int r = 0; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			List<String> rowLst = new ArrayList<String>();
			/** 循环Excel的列 */
			for (int c = 0; c < getTotalCells(); c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						cellValue = cell.getNumericCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;
					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;
					default:
						cellValue = "未知类型";
						break;
					}
				}
				rowLst.add(cellValue);
			}
			/** 保存第r行的第c列 */
			dataLst.add(rowLst);
		}
		return dataLst;
	}

	/**
	 * 
	 * @描述：main测试方法
	 * @返回值：void
	 */

	public static void main(String[] args) throws Exception {
		ExcelTool poi = new ExcelTool();
		List<List<String>> list = poi.read("c:/模板.xls");
		// List<List<String>> list = poi.read("c:/商品导入模板.xlsx");
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				// System.out.print("第" + (i) + "行");
				List<String> cellList = list.get(i);
				for (int j = 0; j < cellList.size(); j++) {
					// System.out.print(" 第" + (j + 1) + "列值：");
					// System.out.print(" " + cellList.get(j));
				}
				// System.out.println();
			}
		}
	}
}
