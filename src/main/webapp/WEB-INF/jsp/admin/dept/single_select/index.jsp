<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson loginUser = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门选择</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript">
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var static_single_select_deptid = null;
var static_single_select_deptname = null;
var static_single_select_deptObj = null;
var static_single_select_deptDescObj = null;

var deptRetNameArray = parent.deptRetNameArray;
if (deptRetNameArray && deptRetNameArray.length == 2) {
    static_single_select_deptid = deptRetNameArray[0];
    static_single_select_deptname = deptRetNameArray[1];
    static_single_select_deptObj = parent.$("#" + static_single_select_deptid);
    static_single_select_deptDescObj = parent.$("#" + static_single_select_deptname);
} else {
    static_single_select_deptObj = parent.$("#dept");
    static_single_select_deptDescObj = parent.$("#deptDesc");
}

// 赋值
function setSelectVal(selectId, selectName) {
    $(static_single_select_deptObj).val(selectId);
    $(static_single_select_deptDescObj).val(selectName);
    parent.layer.close(frameindex); //再执行关闭 */
}

$(function() {
    var select_deptid_val = $(static_single_select_deptObj).val();

    if (select_deptid_val) {
        var url = $('#contentFrame').attr('src');
        url += '?seqId=' + select_deptid_val;
        $('#contentFrame').attr('src', url);
    }
});
</script>
</head>
<frameset onload="" rows="40,*"  cols="*" frameborder="no" border="0" framespacing="0" id="frame1">
  <frame name="dept_title" scrolling="no" noresize src="<%=contextPath%>/YZDeptAct/toSelectTop.act" frameborder="NO">
  <frameset rows="*"  cols="210,*" frameborder="0" border="0" framespacing="0">	
    <frame name="leftFrame" id="leftFrame" src="<%=contextPath%>/YZDeptAct/toSelectLeft.act" frameborder="NO" noresize scrolling="auto"/>
    <frame name="contentFrame" id="contentFrame" src="<%=contextPath%>/YZDeptAct/toSelectList.act" frameborder="NO" scrolling="auto">	
  </frameset>
</frameset>
</html>