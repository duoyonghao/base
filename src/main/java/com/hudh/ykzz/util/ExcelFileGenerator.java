package com.hudh.ykzz.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelFileGenerator {

	private final int SPLIT_COUNT = 1500; // Excel每个工作簿的行数

	private String[] fieldName = null; // excel标题数据集

	private List<?> fieldData = null; // excel数据内容

	private HSSFWorkbook workBook = null;

	/**
	 * 构造器
	 * 
	 * @param fieldName
	 *            结果集的字段名
	 * @param data
	 */
	public ExcelFileGenerator(String fieldName, List<?> fieldData) {
		this.fieldName = fieldName.split("/");
		this.fieldData = fieldData;
	}

	/**
	 * 创建HSSFWorkbook对象
	 * 
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook createWorkbook() {
		workBook = new HSSFWorkbook();
		int rows = fieldData.size();
		int sheetNum = 0;
		int lastSheetRows = 0;
		if (rows % SPLIT_COUNT == 0) {
			sheetNum = rows / SPLIT_COUNT;
			lastSheetRows = rows;
		} else {
			sheetNum = rows / SPLIT_COUNT + 1;
			lastSheetRows = rows % SPLIT_COUNT;
		}
		if(sheetNum==0){
			sheetNum=1;
		}
		for (int i = 1; i <= sheetNum; i++) {
			HSSFSheet sheet = workBook.createSheet("Page " + i);
			HSSFRow headRow = sheet.createRow((short) 0);
			// 设置标题行
			for (int j = 0; j < fieldName.length; j++) {
				HSSFCell cell = headRow.createCell(j);
				// 添加样式
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 添加样式,设置所有单元格的宽度
				sheet.setColumnWidth(j, 6000);
				// 创建样式(使用工作本的对象创建)
				HSSFCellStyle cellStyle = workBook.createCellStyle();
				// 创建字体的对象
				HSSFFont font = workBook.createFont();
				// 将字体加粗
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 设置字体的颜色
				short color = HSSFColor.RED.index;
				font.setColor(color);
				// 将新设置的字体属性放置到样式中
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(fieldName[j]);
			}
			// 将数据内容放入excel单元格
			for (int k = 0; k < (i == sheetNum ? lastSheetRows : SPLIT_COUNT); k++) {
				HSSFRow row = sheet.createRow((short) (k + 1));
				Object[] model = (Object[]) fieldData.get((i - 1) * SPLIT_COUNT + k);
				for (int n = 0; n < model.length; n++) {
					setValue(model[n], row, n);
				}
			}
		}
		return workBook;
	}

	private void setValue(Object value, HSSFRow row, int n) {
		HSSFCell cell = row.createCell(n);
		if (value != null) {
			if (value instanceof String) {
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue((String) value);
			} else if (value instanceof Date) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				cell.setCellValue(df.format(value));
			} else if (value instanceof Boolean) {
				cell.setCellValue((Boolean) value ? "1" : "0");
			} else if (value instanceof Double) {
				cell.setCellValue((Double) value);
			} else {
				cell.setCellValue(String.valueOf(value));
			}
		} else {
			cell.setCellValue(" ");
		}
	}
}
