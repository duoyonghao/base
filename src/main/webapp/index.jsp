<%@ include file="/inc/classImport.jsp"%>
<%
//判断是否使用加密狗 0 不使用 1 使用
String usekey = YZSysProps.getProp(SysParaUtil.IS_USE_USBKEY);
// 如果使用加密狗
if (!YZUtility.isNullorEmpty(usekey) && !("0").equals(usekey)) {
	// 未检测到加密狗
	if (!SentinelkeyUtil.login()) {
		// 跳转到提醒界面
		request.getRequestDispatcher("/inc/dogNotFinderror.jsp").forward(request, response);
	}else{
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}else{
	request.getRequestDispatcher("login.jsp").forward(request, response);
}
%>






