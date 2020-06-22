<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson loginUser = SessionUtil.getLoginPerson(request);

	String isSingle = request.getParameter("isSingle");
	if (isSingle == null) {
		isSingle = "";
	}
	//0 或空 不显示离职人员  1显示离职人员
	String showleave = request.getParameter("showleave");
	if (showleave == null) {
		showleave = "0";
	}
	
	// 部门类型，支持多个，逗号分隔
	String depttype = request.getParameter("depttype");
	if (depttype == null) {
		depttype = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户选择</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript">
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var static_single_select_userid = null;
var static_single_select_username = null;
var static_single_select_userObj = null;
var static_single_select_userDescObj = null;

var userRetNameArray = parent.userRetNameArray;
if (userRetNameArray && userRetNameArray.length == 2) {
    static_single_select_userid = userRetNameArray[0];
    static_single_select_username = userRetNameArray[1];
    static_single_select_userObj = parent.$("#" + static_single_select_userid);
    static_single_select_userDescObj = parent.$("#" + static_single_select_username);
} else {
    static_single_select_userObj = parent.$("#user");
    static_single_select_userDescObj = parent.$("#userDesc");
}

// 赋值
function setSelectVal(selectId, selectName) {
    $(static_single_select_userObj).val(selectId);
    $(static_single_select_userDescObj).val(selectName);
    // 触发change事件，否则营销建档，选建档人，验证不执行 Hzjd.js的 $('#developerDesc').on('change',方法
    parent.$(static_single_select_userDescObj).trigger("change");
    parent.layer.close(frameindex); //再执行关闭 */
}

$(function() {
    var select_userid_val = $(static_single_select_userObj).val();

    if (select_userid_val) {
        var url = $('#contentFrame').attr('src');
        url += '?seqId=' + select_userid_val;
        $('#contentFrame').attr('src', url);
    }
});
</script>
</head>
<frameset onload="" rows="0,*"  cols="*" frameborder="no" border="0" framespacing="0" id="frame1">
  <frame name="user_title" scrolling="no" noresize src="<%=contextPath%>/YZPersonAct/toSingleTop.act" frameborder="NO">
  <frameset rows="*"  cols="210,*" frameborder="0" border="0" framespacing="0">	
    <frame name="leftFrame" id="leftFrame" src="<%=contextPath%>/YZPersonAct/toSingleLeft.act?isSingle=<%=isSingle%>&showleave=<%=showleave%>&depttype=<%=depttype%>" frameborder="NO" noresize scrolling="auto"/>
    <frame name="contentFrame" id="contentFrame" src="<%=contextPath%>/YZPersonAct/toSingleList.act" frameborder="NO" scrolling="auto">	
  </frameset>
</frameset>
</html>