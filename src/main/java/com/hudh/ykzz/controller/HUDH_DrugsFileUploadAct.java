package com.hudh.ykzz.controller;

import com.hudh.ykzz.service.impl.HUDH_DrugsFileUploadLogic;
import com.kqds.core.global.YZSysProps;
import com.kqds.util.core.file.YZFileUploadForm;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"HUDH_DrugsFileUploadAct"})
public class HUDH_DrugsFileUploadAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_DrugsFileUploadAct.class);
  @Autowired
  private HUDH_DrugsFileUploadLogic uploadLogic;
  
  @RequestMapping({"/uploadFile.act"})
  public String uploadFile(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    JSONObject result = new JSONObject();
    try
    {
      YZFileUploadForm fileForm = new YZFileUploadForm();
      fileForm.parseUploadRequest(request);
      String module = request.getParameter("module");
      String imgtype = request.getParameter("imgtype");
      

      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      String attrId = fileForm.getParameter("attachmentId") == null ? "" : fileForm.getParameter("attachmentId");
      String attrName = fileForm.getParameter("attachmentName") == null ? "" : fileForm.getParameter("attachmentName");
      result = this.uploadLogic.fileUploadAndImport(module, fileForm, request, imgtype, organization);
      
      Set<String> keys = result.keySet();
      for (String key : keys)
      {
        String value = (String)result.get(key);
        if ((attrId != null) && (!"".equals(attrId)))
        {
          if (!attrId.trim().endsWith(",")) {
            attrId = attrId + ",";
          }
          if (!attrName.trim().endsWith("*")) {
            attrName = attrName + "*";
          }
        }
        attrId = attrId + key + ",";
        attrName = attrName + value + "*";
      }
      if (!result.containsKey("data")) {
        result.put("data", "上传成功");
      }
      result.put("attrId", attrId);
      result.put("attrName", attrName);
      if ("wechat_news".equals(module))
      {
        JSONObject sub = new JSONObject();
        sub.put("fileKey", "");
        sub.put("name", "");
        sub.put("delurl", "");
        sub.put("viewhref", "");
        sub.put("url", YZUtility.getImageUrlRelative(module, attrId, attrName));
        
        result.put("attributes", sub);
        result.put("obj", null);
        result.put("jsonStr", "");
        result.put("success", Boolean.valueOf(true));
        result.put("msg", "文件添加成功");
      }
      YZUtility.DEAL_SUCCESS(result, null, response, this.logger);
    }
    catch (FileUploadBase.SizeLimitExceededException ex)
    {
      YZUtility.DEAL_ERROR("文件上传失败：文件需要小于" + YZSysProps.getInt("maxUploadFileSize") + "M", true, ex, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR("文件上传失败：" + ex.getMessage(), true, ex, response, this.logger);
    }
    return null;
  }
  
  public String picUploadBASE64(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    JSONObject result = new JSONObject();
    try
    {
      String imgStr = request.getParameter("image");
      if (imgStr != null) {
        imgStr = imgStr.substring(imgStr.indexOf(",") + 1);
      }
      String module = request.getParameter("module");
      result = this.uploadLogic.fileUploadLogicForbase64(module, imgStr, request);
      YZUtility.DEAL_SUCCESS(result, null, response, this.logger);
    }
    catch (FileUploadBase.SizeLimitExceededException ex)
    {
      YZUtility.DEAL_ERROR("文件上传失败：文件需要小于" + YZSysProps.getInt("maxUploadFileSize") + "M", true, ex, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR("文件上传失败：" + ex.getMessage(), true, ex, response, this.logger);
    }
    return null;
  }
  
  public void downLoad(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String module = request.getParameter("module");
    String attrId = request.getParameter("attachmentId");
    String attrName = request.getParameter("attachmentName");
    if (YZUtility.isNullorEmpty(attrId)) {
      throw new Exception("文件不存在！");
    }
    int ym = attrId.indexOf("_");
    String hard = attrId.substring(0, ym);
    String fileName = attrId.substring(ym + 1, attrId.length() - 1) + "_" + attrName.substring(0, attrName.length() - 1);
    String filePath = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + module + File.separator + hard + File.separator + fileName;
    File f = new File(filePath);
    
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try
    {
      response.setHeader("Content-Disposition", "attachment;filename=" + new String(attrName.substring(0, attrName.length() - 1).getBytes(), "iso-8859-1"));
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    ServletOutputStream out = response.getOutputStream();
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try
    {
      bis = new BufferedInputStream(new FileInputStream(f));
      bos = new BufferedOutputStream(out);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
      {
        int bytesRead;
        bos.write(buff, 0, bytesRead);
      }
    }
    catch (IOException e)
    {
      throw e;
    }
    finally
    {
      if (bis != null) {
        bis.close();
      }
      if (bos != null) {
        bos.close();
      }
    }
  }
  
  @RequestMapping({"/downLoadByURL.act"})
  public void downLoadByURL(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String url = request.getParameter("url");
    
    url = encodeURLChinese(url);
    if (YZUtility.isNullorEmpty(url)) {
      throw new Exception("文件下载网址不能为空！");
    }
    int offset = url.lastIndexOf("_");
    String fileName = url.substring(offset + 1, url.length());
    
    URL urlObj = new URL(url);
    InputStream in = urlObj.openStream();
    

    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try
    {
      response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.substring(0, fileName.length()).getBytes(), "iso-8859-1"));
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    ServletOutputStream out = response.getOutputStream();
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try
    {
      bis = new BufferedInputStream(in);
      bos = new BufferedOutputStream(out);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
      {
        int bytesRead;
        bos.write(buff, 0, bytesRead);
      }
    }
    catch (IOException e)
    {
      throw e;
    }
    finally
    {
      if (bis != null) {
        bis.close();
      }
      if (bos != null) {
        bos.close();
      }
      if (in != null) {
        in.close();
      }
    }
  }
  
  private String encodeURLChinese(String url)
  {
    try
    {
      if (!needEncoding(url)) {
        return url;
      }
      String allowChars = ".!*'();:@&=+_\\-$,/?#\\[\\]{}|\\^~`<>%\"";
      
      return encode(url, "UTF-8", allowChars, false);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  
  private boolean needEncoding(String url)
  {
    if (url.matches("^[0-9a-zA-Z.:/?=&%~`#()-+]+$")) {
      return false;
    }
    return true;
  }
  
  private final String encode(String s, String enc, String allowed, boolean lowerCase)
    throws UnsupportedEncodingException
  {
    byte[] bytes = s.getBytes(enc);
    int count = bytes.length;
    







    char[] buf = new char[3 * count];
    int j = 0;
    for (int i = 0; i < count; i++) {
      if (((bytes[i] >= 97) && (bytes[i] <= 122)) || 
        ((bytes[i] >= 65) && (bytes[i] <= 90)) || 
        ((bytes[i] >= 48) && (bytes[i] <= 57)) || 
        (allowed.indexOf(bytes[i]) >= 0))
      {
        buf[(j++)] = ((char)bytes[i]);
      }
      else
      {
        buf[(j++)] = '%';
        if (lowerCase)
        {
          buf[(j++)] = Character.forDigit(0xF & bytes[i] >>> 4, 16);
          buf[(j++)] = Character.forDigit(0xF & bytes[i], 16);
        }
        else
        {
          buf[(j++)] = lowerCaseToUpperCase(Character.forDigit(0xF & bytes[i] >>> 4, 16));
          buf[(j++)] = lowerCaseToUpperCase(Character.forDigit(0xF & bytes[i], 16));
        }
      }
    }
    return new String(buf, 0, j);
  }
  
  private char lowerCaseToUpperCase(char ch)
  {
    if ((ch >= 'a') && (ch <= 'z')) {
      ch = (char)(ch - ' ');
    }
    return ch;
  }
}
