package com.kqds.util.sys.connection;

public class DataSourceSwitch {
	private static final ThreadLocal contextHolder = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	@SuppressWarnings("unchecked")
	public static void reset() {
		contextHolder.set(DataSourceInstances.KQDS);
	}

	@SuppressWarnings("unchecked")
	public static String getDataSourceType() {
		if (contextHolder.get() == null) {
			contextHolder.set(DataSourceInstances.KQDS);
		}
		return (String) contextHolder.get();
	}

	public static void clearDataSourceType() {
		contextHolder.remove();
	}
}
