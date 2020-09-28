package com.kqds.controller.base.jhVideo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsJhVideo;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.jhVideo.Kqds_JhVideo_Logic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

@Controller
@RequestMapping("/Kqds_Jh_VideoAct")
public class Kqds_Jh_VideoAct {
	private static Logger logger = LoggerFactory.getLogger(Kqds_Jh_VideoAct.class);
	@Autowired
	private Kqds_JhVideo_Logic logic;
	
	@RequestMapping(value = "/FileUploadAct", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String FileUploadAct(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value = "files") MultipartFile[] files) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			if (null == files || files.length <= 0) {
				map.put("result", "failure");
				map.put("reason", "文件不存在");
			}
			List<KqdsJhVideo> list= new ArrayList<KqdsJhVideo>();
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 查询已上传的文件
			for (MultipartFile file : files) {
		        String fileName = file.getOriginalFilename();
		        String suffix = fileName.substring(fileName.lastIndexOf('.'));
		        String newFileName = new Date().getTime() + suffix;
		        String path = "D:/radiologyVideo/"+ChainUtil.getCurrentOrganization(request)+"/";
		        File newFile = new File(path + newFileName);
		        file.transferTo(newFile);
		        //输出文件时长
		        Encoder encoder = new Encoder();
		        MultimediaInfo m = encoder.getInfo(newFile);
		        long ls = m.getDuration();	        
		        KqdsJhVideo jhVideo= new KqdsJhVideo();
		        jhVideo.setCreatetime(YZUtility.getCurDateTimeStr());
		        jhVideo.setId(YZUtility.getUUID());
		        jhVideo.setFilename(fileName);
		        jhVideo.setUrl(newFileName);
		        jhVideo.setCreateuser(person.getSeqId());
		        jhVideo.setDel(0);
		        jhVideo.setOrganization(ChainUtil.getCurrentOrganization(request));
		        if(ls/1000/60<=9){
		        	if(ls/1000%60<=9){
		        		jhVideo.setBurningTime("0"+ls/1000/60+":0"+ls/1000%60);
		        	}else{
		        		jhVideo.setBurningTime("0"+ls/1000/60+":"+ls/1000%60);
		        	}
		        }else{
		        	jhVideo.setBurningTime(ls/1000/60+":0"+ls/1000%60);
		        }
		        list.add(jhVideo);
			}
			if(list.size()==1){
				logic.insert(list.get(0));
			}else if(list.size()>1){
				logic.batchInsert(list);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
  	}
	
	@RequestMapping(value = "/select.act")
	public String select(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<KqdsJhVideo> list = logic.selectUrl(ChainUtil.getCurrentOrganization(request));
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/del.act")
	public String del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String url=request.getParameter("url");
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsJhVideo jhVideo= new KqdsJhVideo();
			jhVideo.setDel(1);
			jhVideo.setOperator(person.getSeqId());
			jhVideo.setUrl(url);
			jhVideo.setOrganization(ChainUtil.getCurrentOrganization(request));
			logic.del(jhVideo);
			String path = "D:/radiologyVideo/"+ChainUtil.getCurrentOrganization(request)+"/";
			File temp = new File(path + url);
			temp.delete();
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/toJhVideo.act")
	public ModelAndView toJhVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String url = "/kqdsFront/jhVideo/jh_video.jsp"; 
    	ModelAndView model = new ModelAndView();
    	model.setViewName(url);
    	return model;
	}
}
