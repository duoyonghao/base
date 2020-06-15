package com.kqds.controller.sys.pdf;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kqds.controller.sys.pdf.com.dhtmlx.scheduler.PDFWriter;

public class PDFGenerator {
	public void ExportPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String xml;
		xml = request.getParameter("mycoolxmlbody");
		xml = URLDecoder.decode(xml, "UTF-8");
		PDFWriter pdf = new PDFWriter();
		pdf.generate(xml, response);
	}
}
