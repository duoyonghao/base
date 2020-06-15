package com.kqds.util.core.file;

import com.kqds.core.global.YZSysProps;
import com.kqds.util.sys.YZUtility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class YZFileUploadForm {
  private HashMap<String, String> paramMap = new HashMap<>();
  
  private List fileList = new ArrayList();
  
  private HashMap fileMap = new HashMap<>();
  
  public Map<String, String> getParamMap() {
    return this.paramMap;
  }
  
  public void parseUploadRequest(HttpServletRequest request) throws Exception {
    File tmpFile = new File(YZSysProps.getUploadCatchPath());
    if (!tmpFile.exists())
      tmpFile.mkdirs(); 
    parseUploadRequest(request, YZSysProps.getInt("maxUploadFileSize"), 10240, YZSysProps.getString("fileUploadTempDir"), "UTF-8");
  }
  
  public void parseUploadRequest(HttpServletRequest request, int maxSize, int buffSize, String tempPath, String charSet) throws Exception {
    DiskFileUpload fu = new DiskFileUpload();
    fu.setHeaderEncoding(charSet);
    fu.setSizeMax((maxSize * 1048576));
    fu.setSizeThreshold(buffSize);
    if (!YZUtility.isNullorEmpty(tempPath))
      fu.setRepositoryPath(tempPath); 
    List fieldList = fu.parseRequest(request);
    Iterator<FileItem> iter = fieldList.iterator();
    while (iter.hasNext()) {
      FileItem item = iter.next();
      if (!item.isFormField()) {
        this.fileList.add(item);
        this.fileMap.put(item.getFieldName(), item);
        continue;
      } 
      if (charSet != null) {
        this.paramMap.put(item.getFieldName(), item.getString(charSet));
        continue;
      } 
      this.paramMap.put(item.getFieldName(), item.getString());
    } 
  }
  
  public String getParameter(String paramName) {
    return this.paramMap.get(paramName);
  }
  
  public Iterator iterateFileFields() {
    return this.fileMap.keySet().iterator();
  }
  
  public Iterator iterateParamFiels() {
    return this.paramMap.keySet().iterator();
  }
  
  public FileItem getFileItem(String fieldName) {
    return (FileItem)this.fileMap.get(fieldName);
  }
  
  public InputStream getInputStream() throws IOException {
    if (this.fileList == null)
      return null; 
    FileItem fileItem = this.fileList.get(0);
    return fileItem.getInputStream();
  }
  
  public InputStream getInputStream(String fieldName) throws IOException {
    FileItem item = getFileItem(fieldName);
    if (item != null)
      return item.getInputStream(); 
    return null;
  }
  
  public String getFileName(FileItem fileItem) throws IOException {
    return YZFileUtility.getFileName(fileItem.getName());
  }
  
  public String getFileName(String fieldName) throws IOException {
    return getFileName(getFileItem(fieldName));
  }
  
  private void saveFile(FileItem fileItem, String savePath) throws IOException {
    if (fileItem == null)
      return; 
    if (savePath == null)
      return; 
    File outFile = new File(savePath);
    File outPath = outFile.getParentFile();
    if (!outPath.exists())
      outPath.mkdirs(); 
    if (!outFile.exists())
      outFile.createNewFile(); 
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new BufferedInputStream(fileItem.getInputStream());
      out = new BufferedOutputStream(new FileOutputStream(outFile));
      byte[] buff = new byte[1048576];
      int length = 0;
      while ((length = in.read(buff)) > 0) {
        out.write(buff, 0, length);
        out.flush();
      } 
    } catch (IOException ex) {
      throw ex;
    } finally {
      try {
        if (in != null)
          in.close(); 
      } catch (Exception exception) {}
      try {
        if (out != null)
          out.close(); 
      } catch (Exception exception) {}
    } 
    String tmpStr = null;
    int tmpIndex = savePath.lastIndexOf("\\");
    if (tmpIndex >= 4) {
      tmpStr = savePath.substring(tmpIndex - 4);
      tmpStr = tmpStr.replace("\\", "_");
    } 
    if (tmpStr != null && Pattern.matches("^\\d{4}_[0-9a-z]{32}_.*$", tmpStr)) {
      tmpIndex = tmpStr.indexOf("_", 5);
      String fileId = tmpStr.substring(0, tmpIndex);
      try {
        Map<Object, Object> valueMap = new HashMap<>();
        valueMap.put("fileId", fileId);
        valueMap.put("filePath", savePath);
        valueMap.put("createTime", YZUtility.getCurDateTimeStr());
      } catch (Exception ex) {
        ex.printStackTrace();
        throw new IOException(ex);
      } 
    } 
  }
  
  public void saveFile(String fieldName, String savePath) throws IOException {
    saveFile(getFileItem(fieldName), savePath);
  }
}
