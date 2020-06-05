package com.kqds.util.wx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import com.kqds.core.global.YZSysProps;
import com.kqds.util.sys.YZUtility;

/**
 * 下载
 * 
 * @author Administrator
 * 
 */
public class WXDownLoadUtil {

	/**
	 * 根据文件id下载文件
	 * 
	 * @param mediaId媒体id
	 * @throws Exception
	 */

	private static InputStream getInputStream(String mediaId, String accessToken) {
		InputStream is = null;
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + mediaId;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			// 获取文件转化为byte流
			is = http.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * 获取下载图片信息（jpg）
	 * 
	 * @param mediaId文件的id
	 * @throws Exception
	 */

	public static String saveImageToDisk(String mediaId, String msgid, String accessToken, HttpServletRequest request) throws Exception {
		InputStream inputStream = getInputStream(mediaId, accessToken);
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		String imageUrl = null;
		try {
			String hard = YZUtility.getHard4File();
			String fullPath = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + "wechat" + File.separator + hard + File.separator + msgid
					+ ".jpg";
			// 文件夹不存在则创建
			File folder = new File(request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + "wechat" + File.separator + hard);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			fileOutputStream = new FileOutputStream(fullPath);
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}

			imageUrl = YZSysProps.getString("WEBSITE_URL");
			imageUrl += request.getContextPath() + "/upload/wechat/" + hard + "/" + msgid + ".jpg";

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return imageUrl;
	}

	public static void main(String[] args) throws Exception {
		// saveImageToDisk("d6JQoc_2v98CXiLO9zH8plcNlV8QZ0NHb9o_lJKYLr46TCS6qwPrJAWsHT7ifI2r","mZhm5QUelEkWfG-RwXOkYu0GtSHnGr9jeFLPgjJQnDuq6-fHfD5TRLIOlKHmFbgJTrCRPHlOnlC5OABTN45W4DbP-SIDEHdaPSmx_o_3wj4sC2recHfsbvjQN74V9riGSHBdAAAVKE");
	}

}
