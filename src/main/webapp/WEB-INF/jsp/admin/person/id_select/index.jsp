<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson loginUser = SessionUtil.getLoginPerson(request);

	String deptIds = request.getParameter("deptIds");
	if (deptIds == null) {
		deptIds = "";
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

var userRetNameArray = parent.userRetNameArrayDeptId;
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
    $(static_single_select_userObj).val(selectId); // 这样赋值，无法触发change事件，需要手工触发
    $(static_single_select_userDescObj).val(selectName);
    $(static_single_select_userDescObj).trigger('change');
    
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
  <frame name="user_title" scrolling="no" noresize src="<%=contextPath%>/YZPersonAct/toIdTop.act" frameborder="NO">
  <frameset rows="*"  cols="210,*" frameborder="0" border="0" framespacing="0">	
    <frame name="leftFrame" id="leftFrame" src="<%=contextPath%>/YZPersonAct/toIdLeft.act?deptIds=<%=deptIds%>" frameborder="NO" noresize scrolling="auto"/>
    <frame name="contentFrame" id="contentFrame" src="<%=contextPath%>/YZPersonAct/toIdList.act" frameborder="NO" scrolling="auto">	
  </frameset>
</frameset>
</html>