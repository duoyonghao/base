/**  
  *
  * @Title:  KQDS_CK_Goods_Init_Detail.java   
  * @Package com.kqds.controller.ck   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2020年3月27日 下午5:12:09   
  * @version V1.0  
  */ 
package com.kqds.controller.ck;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.service.ck.KQDS_CK_Goods_Init_DetailLogic;

/**  
  * 
  * @ClassName:  KQDS_CK_Goods_Init_Detail   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2020年3月27日 下午5:12:09   
  *      
  */
@Controller
@RequestMapping("KQDS_CK_Goods_Init_Detail")
public class KQDS_CK_Goods_Init_DetailAct {

	@Autowired
	private KQDS_CK_Goods_Init_DetailLogic logic;
	
	@RequestMapping("/InitGoodsDetail.act")
	public String InitGoodsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
        				// 打开指定位置的Excel文件
        				FileInputStream file = new FileInputStream(new File("E:\\gz\\商品明细分库.xls"));
        				//解析excel
        			    POIFSFileSystem pSystem=new POIFSFileSystem(file);
        			    //获取整个excel
        			    HSSFWorkbook hb=new HSSFWorkbook(pSystem);
        			    //获取第一个表单sheet
        			    HSSFSheet sheet=hb.getSheetAt(0);
        			    //输出excel表格数据行数
        			    int line = sheet.getLastRowNum() + 1;
        				System.out.println("有效行数=" + line);
        			    //获取第一行  从0开始
        				int firstrow=sheet.getFirstRowNum();
        				//获取最后一行
        				int lastrow=sheet.getLastRowNum();
        				//定义某一变量用于计数
        				int n = 0 ;
        				//循环读取每一行数据修改对应数据
        				for (int i = firstrow; i < lastrow+1; i++) {//循环行数
        			        //获取哪一行i
        			        Row row=sheet.getRow(i);
        			        //创建一个集合，将每一行的每列数据存入集合中的对应键值
        			        Map<String, String> map = new HashMap<String, String>();
        			        if (row!=null) {
        			        map.put("seqId", row.getCell(0).getStringCellValue());
        			        map.put("goodscode", row.getCell(1).getStringCellValue());
        			        map.put("goodstype", row.getCell(2).getStringCellValue());
        			        logic.updateDetail(map);
        			        System.out.println("修改" + i + "数据");
        			        n++;
        			        }
        			    }
        				System.out.println("修改了" + n + "数据");
        				file.close();
        				
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("出错了。。。。。");
		}
		return null;
	}
	
	@RequestMapping("/InitGoods.act")
	public String InitGoods(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
        				// 打开指定位置的Excel文件
        				FileInputStream file = new FileInputStream(new File("E:\\gz\\商品分库.xls"));
        				//解析excel
        			    POIFSFileSystem pSystem=new POIFSFileSystem(file);
        			    //获取整个excel
        			    HSSFWorkbook hb=new HSSFWorkbook(pSystem);
        			    //获取第一个表单sheet
        			    HSSFSheet sheet=hb.getSheetAt(0);
        			    //输出excel表格数据行数
        			    int line = sheet.getLastRowNum() + 1;
        				System.out.println("有效行数=" + line);
        			    //获取第一行  从0开始
        				int firstrow=sheet.getFirstRowNum();
        				//获取最后一行
        				int lastrow=sheet.getLastRowNum();
        				//定义某一变量用于计数
        				int n = 0 ;
        				//循环读取每一行数据修改对应数据
        				for (int i = firstrow; i < lastrow+1; i++) {//循环行数
        			        //获取哪一行i
        			        Row row=sheet.getRow(i);
        			        //创建一个集合，将每一行的每列数据存入集合中的对应键值
        			        Map<String, String> map = new HashMap<String, String>();
        			        if (row!=null) {
        			        map.put("seqId", row.getCell(2).getStringCellValue());
        			        map.put("house", row.getCell(1).getStringCellValue());
        			        logic.updateGoods(map);
        			        System.out.println("修改" + i + "数据");
        			        n++;
        			        }
        			    }
        				System.out.println("修改了" + n + "数据");
        				file.close();
        				
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("出错了。。。。。");
		}
		return null;
	}
}
