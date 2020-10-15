package com.kqds.util.test;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class exportExcelFileUtils {


    private XSSFCellStyle head_Style;

    private SXSSFWorkbook workbook;

// 当前sheet

    private SXSSFSheet sheet;

    private SXSSFRow row = null;// 创建一行

    private SXSSFCell cell = null;

    private String headers[][];

    private int currentRow = 0;

    private XSSFCellStyle date_Style;

    private XSSFCellStyle time_Style;

    private XSSFCellStyle string_style;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 构造函数初始化参数
     *
     * @param title
     * @param headers
     * @param sheeatName
     */

    public exportExcelFileUtils(String title, String[][] headers, String sheeatName) {

        this.headers = headers;

        try {

            workbook = new SXSSFWorkbook();

            this.head_Style = (XSSFCellStyle) this.workbook.createCellStyle();

            head_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

            head_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);

            head_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);

            head_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);

            head_Style.setFillForegroundColor(IndexedColors.AQUA.getIndex());

            head_Style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            head_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            XSSFFont head_font = (XSSFFont) workbook.createFont();

            head_font.setFontName("宋体");// 设置头部字体为宋体

            head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体

            head_font.setFontHeightInPoints((short) 11);

            this.head_Style.setFont(head_font);// 单元格样式使用字体


            XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();


            XSSFFont data_font = (XSSFFont) workbook.createFont();

            data_font.setFontName("宋体");// 设置头部字体为宋体

            data_font.setFontHeightInPoints((short) 10);


            this.date_Style = (XSSFCellStyle) this.workbook.createCellStyle();

            date_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

            date_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);

            date_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);

            date_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);

            date_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            date_Style.setFont(data_font);// 单元格样式使用字体

            date_Style.setDataFormat(format.getFormat("yyyy-m-d"));


            this.time_Style = (XSSFCellStyle) this.workbook.createCellStyle();

            time_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

            time_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);

            time_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);

            time_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);

            time_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            time_Style.setFont(data_font);// 单元格样式使用字体

            time_Style.setDataFormat(format.getFormat("yyyy-m-d h:mm:s"));


            this.string_style = (XSSFCellStyle) this.workbook.createCellStyle();

            string_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);

            string_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);

            string_style.setBorderRight(XSSFCellStyle.BORDER_THIN);

            string_style.setBorderTop(XSSFCellStyle.BORDER_THIN);

            string_style.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            string_style.setFont(data_font);// 单元格样式使用字体


            createSheet(sheeatName, headers);

        } catch (Exception exc) {

            exc.printStackTrace();

        }


    }

    /**
     * 创建表头
     *
     * @param sheetName
     * @param headers
     */

    private void createSheet(String sheetName, String headers[][]) {

        sheet = (SXSSFSheet) workbook.createSheet(sheetName);

        row = (SXSSFRow) sheet.createRow(currentRow);

        for (int i = 0; i < headers.length; i++) {

            cell = (SXSSFCell) row.createCell(i);

            cell.setCellType(XSSFCell.CELL_TYPE_STRING);

            cell.setCellValue(headers[i][0]);

            cell.setCellStyle(head_Style);

        }

        currentRow++;

    }

    /**
     * 导出excel
     *
     * @param listRows
     * @throws ParseException
     */

    public synchronized void PoiWriteExcel_To2007(List listRows, OutputStream out) throws ParseException {

        for (int i = 0; i < listRows.size(); i++) {

            row = (SXSSFRow) sheet.createRow(currentRow);

            ArrayList ListCells = (ArrayList) listRows.get(i);

            for (int j = 0; j < ListCells.size(); j++) {

                Object obj = ListCells.get(j);

                cell = (SXSSFCell) row.createCell(j);

                if (obj instanceof Integer) {

                    cell.setCellValue((String) obj);

                    cell.setCellStyle(string_style);

                } else if (obj instanceof Date) {

                    String type = headers[j][1];

                    if ("DATE".equals(type)) {

                        cell.setCellValue((Date) obj);

                        cell.setCellStyle(date_Style);

                    } else if ("TIME".equals(type)) {

                        cell.setCellValue((Date) obj);

                        cell.setCellStyle(time_Style);

                    } else {

                        if (!"".equals((String) obj))

                            cell.setCellValue(sdf.format((Date) obj));

                        cell.setCellValue("");

                        cell.setCellStyle(string_style);

                    }

                } else {

                    if (!"".equals((String) obj))

                        cell.setCellValue((String) obj);

                    cell.setCellValue("");

                    cell.setCellStyle(string_style);

                }

            }

            currentRow++;

        }

        try {

            workbook.write(out);

            out.flush();

            out.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
