package com.kqds.entity.sys;

import java.util.List;

public class BootStrapPage<T> {
  private int limit = 0;
  
  private int offset = 0;
  
  private String order = "asc";
  
  private String sort;
  
  private List<T> rows;
  
  private String search;
  
  private Long total;
  
  public String getSort() {
    return this.sort;
  }
  
  public void setSort(String sort) {
    this.sort = sort;
  }
  
  public int getLimit() {
    return this.limit;
  }
  
  public int getOffset() {
    return this.offset;
  }
  
  public String getOrder() {
    return this.order;
  }
  
  public List<T> getRows() {
    return this.rows;
  }
  
  public String getSearch() {
    return this.search;
  }
  
  public Long getTotal() {
    return this.total;
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
