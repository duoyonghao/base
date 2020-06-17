package com.kqds.util.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kqds.core.global.YZConst;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class YZFileUtility {

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static void loadLine2Array(String fileName, int startLine, int endLine, List rtList, String charSet) throws Exception {

		InputStream ins = null;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return;
			}
			ins = new FileInputStream(file);
			loadLine2Array(ins, startLine, endLine, rtList, charSet);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 把文件中的内容读入数组，每行作为一个元素
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static void loadLine2Array(String fileName, List rtList) throws Exception {
		loadLine2Array(fileName, 0, Integer.MAX_VALUE, rtList, YZConst.DEFAULT_CODE);
	}

	/**
	 * 把流中的内容读入到数组之中，每行作为一个元素
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	private static void loadLine2Array(InputStream in, int startLine, int endLine, List rtList, String charSet) throws Exception {

		LineNumberReader reader = new LineNumberReader(new InputStreamReader(in, charSet));
		String str = null;
		for (int i = 0; (str = reader.readLine()) != null; i++) {
			if (i < startLine) {
				continue;
			}
			if (i > endLine) {
				break;
			}
			rtList.add(str);
		}
	}

	/**
	 * 加载属性文件到Map中
	 * 
	 * @param fileName
	 *            属性文件路径
	 * 
	 * @param rtMap
	 *            返回哈希表
	 * @throws Exception
	 */
	public static void load2Map(String fileName, Map rtMap) throws Exception {
		ArrayList propList = new ArrayList();

		loadLine2Array(fileName, propList);
		for (int i = 0; i < propList.size(); i++) {
			String line = propList.get(i).toString().trim();
			if (line.length() < 1 || line.startsWith("#")) {
				continue;
			}
			int tmpIndex = line.indexOf("=");
			if (tmpIndex < 0) {
				continue;
			}
			String name = line.substring(0, tmpIndex).trim();
			String value = line.substring(tmpIndex + 1).trim();
			rtMap.put(name, value);
		}
	}

	/**
	 * 取得文件名，带扩展名部分
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (filePath == null) {
			return null;
		}
		int startIndex = 0;
		if (filePath.lastIndexOf(YZFileConst.PATH_SPLIT_FILE_WIN) >= 0) {
			startIndex = filePath.lastIndexOf(YZFileConst.PATH_SPLIT_FILE_WIN) + 1;
		} else if (filePath.lastIndexOf(YZFileConst.PATH_SPLIT_URL) >= 0) {
			startIndex = filePath.lastIndexOf(YZFileConst.PATH_SPLIT_URL) + 1;
		}

		return filePath.substring(startIndex, filePath.length());
	}

}
