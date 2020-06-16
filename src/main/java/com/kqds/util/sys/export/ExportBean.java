package com.kqds.util.sys.export;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExportBean {
	private SXSSFWorkbook workbook;
	private Sheet sheet;

	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	private String names;
	private String values;

	private Map<String, String> namevals;

	public Map<String, String> getNamevals() {
		return namevals;
	}

	public void setNamevals(Map<String, String> namevals) {
		this.namevals = namevals;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	private HttpServletResponse response;
	private HttpServletRequest request;

	public SXSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(SXSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

}
