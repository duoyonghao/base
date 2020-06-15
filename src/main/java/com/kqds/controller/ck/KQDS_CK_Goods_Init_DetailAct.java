package com.kqds.controller.ck;

import com.kqds.service.ck.KQDS_CK_Goods_Init_DetailLogic;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_CK_Goods_Init_Detail"})
public class KQDS_CK_Goods_Init_DetailAct {
  @Autowired
  private KQDS_CK_Goods_Init_DetailLogic logic;
  
  @RequestMapping({"/InitGoodsDetail.act"})
  public String InitGoodsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      FileInputStream file = new FileInputStream(new File("E:\\gz\\商品明细分库.xls"));
      POIFSFileSystem pSystem = new POIFSFileSystem(file);
      HSSFWorkbook hb = new HSSFWorkbook(pSystem);
      HSSFSheet sheet = hb.getSheetAt(0);
      int line = sheet.getLastRowNum() + 1;
      System.out.println("有效行数=" + line);
      int firstrow = sheet.getFirstRowNum();
      int lastrow = sheet.getLastRowNum();
      int n = 0;
      for (int i = firstrow; i < lastrow + 1; i++) {
        HSSFRow hSSFRow = sheet.getRow(i);
        Map<String, String> map = new HashMap<>();
        if (hSSFRow != null) {
          map.put("seqId", hSSFRow.getCell(0).getStringCellValue());
          map.put("goodscode", hSSFRow.getCell(1).getStringCellValue());
          map.put("goodstype", hSSFRow.getCell(2).getStringCellValue());
          this.logic.updateDetail(map);
          System.out.println("修改" + i + "数据");
          n++;
        } 
      } 
      System.out.println("修改了" + n + "数据");
      file.close();
    } catch (Exception e) {
      throw new Exception("出错了。。。。。");
    } 
    return null;
  }
  
  @RequestMapping({"/InitGoods.act"})
  public String InitGoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      FileInputStream file = new FileInputStream(new File("E:\\gz\\商品分库.xls"));
      POIFSFileSystem pSystem = new POIFSFileSystem(file);
      HSSFWorkbook hb = new HSSFWorkbook(pSystem);
      HSSFSheet sheet = hb.getSheetAt(0);
      int line = sheet.getLastRowNum() + 1;
      System.out.println("有效行数=" + line);
      int firstrow = sheet.getFirstRowNum();
      int lastrow = sheet.getLastRowNum();
      int n = 0;
      for (int i = firstrow; i < lastrow + 1; i++) {
        HSSFRow hSSFRow = sheet.getRow(i);
        Map<String, String> map = new HashMap<>();
        if (hSSFRow != null) {
          map.put("seqId", hSSFRow.getCell(2).getStringCellValue());
          map.put("house", hSSFRow.getCell(1).getStringCellValue());
          this.logic.updateGoods(map);
          System.out.println("修改" + i + "数据");
          n++;
        } 
      } 
      System.out.println("修改了" + n + "数据");
      file.close();
    } catch (Exception e) {
      throw new Exception("出错了。。。。。");
    } 
    return null;
  }
}
