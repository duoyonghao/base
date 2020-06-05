package com.kqds.controller.base.print;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.service.base.print.PrintFyqrdLogic;
import com.kqds.service.base.print.PrintLogic;
import com.kqds.service.base.print.PrintZhongZhiLogic;
import com.kqds.util.sys.YZUtility;

@Controller
@RequestMapping("PrintAct")
public class PrintAct {
	@Autowired
	private PrintLogic printlogic;
	@Autowired
	private PrintFyqrdLogic feiyongLogic;
	@Autowired
	private PrintZhongZhiLogic zhongzhiLogic;

	/**
	 * 打印种植病历
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/zhongZhiBingliPdf4Print.act")
	public String zhongZhiBingliPdf4Print(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		try {
			String seqId = request.getParameter("seqId"); // 病历主键
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("病历号不能为空");
			}
			String filePath = zhongzhiLogic.print(seqId, request);

			File f = new File(filePath);

			FileInputStream in = new FileInputStream(f);
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024 * 8];
			while ((in.read(b)) != -1) {
				out.write(b);
			}
			out.flush();
			in.close();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 打印病历
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bingLiPdf4Print.act")
	public String bingLiPdf4Print(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		try {
			String seqId = request.getParameter("seqId"); // 病历主键
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("病历号不能为空");
			}
			String filePath = printlogic.bingLiPdf4Print(seqId, request);

			File f = new File(filePath);

			FileInputStream in = new FileInputStream(f);
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024 * 8];
			while ((in.read(b)) != -1) {
				out.write(b);
			}
			out.flush();
			in.close();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 打印费用单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/feiYongPdf4Print.act")
	public String feiYongPdf4Print(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		try {
			String seqId = request.getParameter("seqId"); // 病历主键
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("费用单号不能为空");
			}
			String filePath = feiyongLogic.feiYongPdf4Print(seqId, request);

			File f = new File(filePath);

			FileInputStream in = new FileInputStream(f);
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024 * 8];
			while ((in.read(b)) != -1) {
				out.write(b);
			}
			out.flush();
			in.close();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
