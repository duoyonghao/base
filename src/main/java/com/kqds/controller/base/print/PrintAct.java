package com.kqds.controller.base.print;

import com.kqds.service.base.print.PrintFyqrdLogic;
import com.kqds.service.base.print.PrintLogic;
import com.kqds.service.base.print.PrintZhongZhiLogic;
import com.kqds.util.sys.YZUtility;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"PrintAct"})
public class PrintAct {
  @Autowired
  private PrintLogic printlogic;
  
  @Autowired
  private PrintFyqrdLogic feiyongLogic;
  
  @Autowired
  private PrintZhongZhiLogic zhongzhiLogic;
  
  @RequestMapping({"/zhongZhiBingliPdf4Print.act"})
  public String zhongZhiBingliPdf4Print(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("application/pdf");
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("病历号不能为空"); 
      String filePath = this.zhongzhiLogic.print(seqId, request);
      File f = new File(filePath);
      FileInputStream in = new FileInputStream(f);
      ServletOutputStream servletOutputStream = response.getOutputStream();
      byte[] b = new byte[8192];
      while (in.read(b) != -1)
        servletOutputStream.write(b); 
      servletOutputStream.flush();
      in.close();
      servletOutputStream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  @RequestMapping({"/bingLiPdf4Print.act"})
  public String bingLiPdf4Print(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("application/pdf");
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("病历号不能为空"); 
      String filePath = this.printlogic.bingLiPdf4Print(seqId, request);
      File f = new File(filePath);
      FileInputStream in = new FileInputStream(f);
      ServletOutputStream servletOutputStream = response.getOutputStream();
      byte[] b = new byte[8192];
      while (in.read(b) != -1)
        servletOutputStream.write(b); 
      servletOutputStream.flush();
      in.close();
      servletOutputStream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  @RequestMapping({"/feiYongPdf4Print.act"})
  public String feiYongPdf4Print(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("application/pdf");
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("费用单号不能为空"); 
      String filePath = this.feiyongLogic.feiYongPdf4Print(seqId, request);
      File f = new File(filePath);
      FileInputStream in = new FileInputStream(f);
      ServletOutputStream servletOutputStream = response.getOutputStream();
      byte[] b = new byte[8192];
      while (in.read(b) != -1)
        servletOutputStream.write(b); 
      servletOutputStream.flush();
      in.close();
      servletOutputStream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
}
