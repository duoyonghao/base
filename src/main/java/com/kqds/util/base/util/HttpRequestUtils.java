package com.kqds.util.base.util;

import java.io.IOException;
import java.net.URLDecoder;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtils
{
  private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
  
  public static void main(String[] args)
  {
    StringBuffer queryBf = new StringBuffer();
    
    JSONObject jsonParam = new JSONObject();
    jsonParam.put("action", "QueryTalklog");
    jsonParam.put("rpccallkey", "VocLogRpcKey");
    jsonParam.put("startdate", "2017-11-21 00:30:10");
    jsonParam.put("enddate", "2017-11-21 18:30:10");
    jsonParam.put("localcode", "");
    jsonParam.put("channel", "");
    jsonParam.put("localname", "");
    jsonParam.put("localdepart", "");
    jsonParam.put("remotecode", "");
    jsonParam.put("direction", "");
    jsonParam.put("maxtalklong", "");
    jsonParam.put("mintalklong", "0");
    jsonParam.put("tags", "");
    jsonParam.put("pagestart", "0");
    jsonParam.put("pagelimit", "50");
    
    queryBf.append("action=" + jsonParam.getString("action"));
    queryBf.append("&rpccallkey=" + jsonParam.getString("rpccallkey"));
    queryBf.append("&startdate=" + jsonParam.getString("startdate"));
    queryBf.append("&enddate=" + jsonParam.getString("enddate"));
    queryBf.append("&localcode=" + jsonParam.getString("localcode"));
    queryBf.append("&channel=" + jsonParam.getString("channel"));
    queryBf.append("&localname=" + jsonParam.getString("localname"));
    queryBf.append("&localdepart=" + jsonParam.getString("localdepart"));
    queryBf.append("&remotecode=" + jsonParam.getString("remotecode"));
    queryBf.append("&direction=" + jsonParam.getString("direction"));
    queryBf.append("&maxtalklong=" + jsonParam.getString("maxtalklong"));
    queryBf.append("&mintalklong=" + jsonParam.getString("mintalklong"));
    queryBf.append("&tags=" + jsonParam.getString("tags"));
    queryBf.append("&pagestart=" + jsonParam.getString("pagestart"));
    queryBf.append("&pagelimit=" + jsonParam.getString("pagelimit"));
  }
  
  public static JSONObject httpPostJson(String url, String paramStr, boolean noNeedResponse)
  {
    DefaultHttpClient httpClient = new DefaultHttpClient();
    jsonResult = null;
    HttpPost method = new HttpPost(url);
    try
    {
      if (paramStr != null)
      {
        StringEntity entity = new StringEntity(paramStr, "utf-8");
        entity.setContentEncoding("UTF-8");
        
        entity.setContentType("application/x-www-form-urlencoded");
        
        method.setEntity(entity);
      }
      HttpResponse result = httpClient.execute(method);
      url = URLDecoder.decode(url, "UTF-8");
      if (result.getStatusLine().getStatusCode() == 200)
      {
        String str = "";
        try
        {
          str = EntityUtils.toString(result.getEntity());
          if (noNeedResponse) {
            return null;
          }
          jsonResult = JSONObject.fromObject(str);
        }
        catch (Exception e)
        {
          logger.error("post请求提交失败:" + url, e);
        }
      }
      return jsonResult;
    }
    catch (IOException e)
    {
      logger.error("post请求提交失败:" + url, e);
    }
  }
  
  public static String httpPost(String url, String param, boolean noNeedResponse)
    throws ClientProtocolException, IOException
  {
    url = URLDecoder.decode(url, "UTF-8");
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String result = null;
    HttpPost method = new HttpPost(url);
    
    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(300).build();
    method.setConfig(requestConfig);
    if (param != null)
    {
      StringEntity entity = new StringEntity(param, "utf-8");
      entity.setContentEncoding("UTF-8");
      entity.setContentType("application/x-www-form-urlencoded");
      method.setEntity(entity);
    }
    HttpResponse resultRep = httpClient.execute(method);
    if (resultRep.getStatusLine().getStatusCode() == 200)
    {
      String str = "";
      
      str = EntityUtils.toString(resultRep.getEntity());
      if (noNeedResponse) {
        return null;
      }
      result = str;
    }
    return result;
  }
  
  public static JSONObject httpGet(String url)
  {
    JSONObject jsonResult = null;
    try
    {
      DefaultHttpClient client = new DefaultHttpClient();
      
      HttpGet request = new HttpGet(url);
      HttpResponse response = client.execute(request);
      if (response.getStatusLine().getStatusCode() == 200)
      {
        String strResult = EntityUtils.toString(response.getEntity());
        
        jsonResult = JSONObject.fromObject(strResult);
        url = URLDecoder.decode(url, "UTF-8");
      }
      else
      {
        logger.error("get请求提交失败:" + url);
      }
    }
    catch (IOException e)
    {
      logger.error("get请求提交失败:" + url, e);
    }
    return jsonResult;
  }
}
