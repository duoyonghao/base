package com.kqds.entity.sys;

import java.util.List;

public class BootStrapPage<T> {
	private int limit = 0;

	private int offset = 0;

	private String order = "asc";
	private String sort;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	private List<T> rows;

	private String search;

	private Long total;

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

	public String getOrder() {
		return order;
	}

	public List<T> getRows() {
		return rows;
	}

	public String getSearch() {
		return search;
	}

	public Long getTotal() {
		return total;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
