package com.kqds.util.sys.connection;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSources extends AbstractRoutingDataSource {
  protected Object determineCurrentLookupKey() {
    return DataSourceSwitch.getDataSourceType();
  }
}
