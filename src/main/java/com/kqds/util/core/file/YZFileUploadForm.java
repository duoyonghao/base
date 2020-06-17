package com.kqds.util.core.file;

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

import com.kqds.core.global.YZConst;
import com.kqds.core.global.YZSysProps;
import com.kqds.util.sys.YZUtility;

@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class YZFileUploadForm {
	/**
	 * 普通表单参数
	 */
	private HashMap<String, String> paramMap = new HashMap();
	/**
	 * 文件列表
	 */
	private List fileList = new ArrayList();
	/**
	 * 文件哈希表
	 * 
	 * {文件表单名，FileItem}
	 */
	private HashMap fileMap = new HashMap();

	/**
	 * 取得表单参数哈希表
	 * 
	 * @return
	 */
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	/**
	 * 默认解析协议
	 * 
	 * @param request
	 *            客户端请求
	 * @throws Exception
	 */
	public void parseUploadRequest(HttpServletRequest request) throws Exception {
		File tmpFile = new File(YZSysProps.getUploadCatchPath());
		if (!tmpFile.exists()) {
			tmpFile.mkdirs();
		}
		parseUploadRequest(request, YZSysProps.getInt(YZSysProps.MAX_UPLOAD_FILE_SIZE), 10 * YZConst.K, YZSysProps.getString("fileUploadTempDir"), YZConst.DEFAULT_CODE);
	}

	/**
	 * 
	 * @param request
	 * @param maxSize
	 * @param buffSize
	 * @param tempPath
	 * @throws Exception
	 */
	public void parseUploadRequest(HttpServletRequest request, int maxSize, int buffSize, String tempPath, String charSet) throws Exception {

		DiskFileUpload fu = new DiskFileUpload();
		fu.setHeaderEncoding(charSet);
		// 设置允许用户上传文件大小,单位:字节
		fu.setSizeMax(maxSize * YZConst.M);
		// maximum size that will be stored in memory?
		// 设置最多只允许在内存中存储的数据,单位:字节
		fu.setSizeThreshold(buffSize);
		// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
		if (!YZUtility.isNullorEmpty(tempPath)) {
			fu.setRepositoryPath(tempPath);
		}
		// 开始读取上传信息

		List fieldList = fu.parseRequest(request);

		Iterator iter = fieldList.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			// 文件字段
			if (!item.isFormField()) {
				fileList.add(item);
				fileMap.put(item.getFieldName(), item);
				// 普通表单字段

			} else {
				if (charSet != null) {
					paramMap.put(item.getFieldName(), item.getString(charSet));
				} else {
					paramMap.put(item.getFieldName(), item.getString());
				}
			}
		}
	}

	/**
	 * 取得参数
	 * 
	 * @param paramName
	 * @return
	 */
	public String getParameter(String paramName) {
		return (String) paramMap.get(paramName);
	}

	/**
	 * 取得文件字段名称列表
	 */
	public Iterator iterateFileFields() {
		return fileMap.keySet().iterator();
	}

	/**
	 * 取得普通字段名称列表
	 */
	public Iterator iterateParamFiels() {
		return paramMap.keySet().iterator();
	}

	/**
	 * 用字段名称取得文件项目
	 */
	public FileItem getFileItem(String fieldName) {
		return (FileItem) fileMap.get(fieldName);
	}

	/**
	 * 取得文件的输入流
	 * 
	 * @param fieldName
	 * @return
	 */
	public InputStream getInputStream() throws IOException {
		if (fileList == null) {
			return null;
		}
		FileItem fileItem = (FileItem) fileList.get(0);
		return fileItem.getInputStream();
	}

	/**
	 * 取得文件的输入流
	 * 
	 * @param fieldName
	 * @return
	 */
	public InputStream getInputStream(String fieldName) throws IOException {
		FileItem item = getFileItem(fieldName);
		if (item != null) {
			return item.getInputStream();
		}
		return null;
	}

	/**
	 * 取得文件的名称
	 * 
	 * @return
	 * @throws IOExeption
	 */
	public String getFileName(FileItem fileItem) throws IOException {
		return YZFileUtility.getFileName(fileItem.getName());
	}

	/**
	 * 取得文件的名称
	 * 
	 * @param fieldName
	 * @return
	 */
	public String getFileName(String fieldName) throws IOException {
		return getFileName(getFileItem(fieldName));
	}

	/**
	 * 保存上传文件
	 * 
	 * @param fileItem
	 *            文件项目
	 * @param savePath
	 *            保存文件的全路径
	 * @throws IOException
	 */
	private void saveFile(FileItem fileItem, String savePath) throws IOException {
		if (fileItem == null) {
			return;
		}
		if (savePath == null) {
			return;
		}
		File outFile = new File(savePath);
		File outPath = outFile.getParentFile();
		if (!outPath.exists()) {
			outPath.mkdirs();
		}
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(fileItem.getInputStream());
			out = new BufferedOutputStream(new FileOutputStream(outFile));
			byte[] buff = new byte[YZConst.M];
			int length = 0;
			while ((length = in.read(buff)) > 0) {
				out.write(buff, 0, length);
				out.flush();
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception ex) {
			}
		}
		// 保存到数据库
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
				Map valueMap = new HashMap();
				valueMap.put("fileId", fileId);
				valueMap.put("filePath", savePath);
				valueMap.put("createTime", YZUtility.getCurDateTimeStr());
				// logic.saveSingle("SignFiles", valueMap);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new IOException(ex);
			}
		}
	}

	/**
	 * 保存指定文件
	 * 
	 * @param fieldName
	 *            表单字段名称
	 * @param savePath
	 *            保存文件的全路径
	 * @throws IOException
	 */
	public void saveFile(String fieldName, String savePath) throws IOException {
		saveFile(getFileItem(fieldName), savePath);
	}

}
