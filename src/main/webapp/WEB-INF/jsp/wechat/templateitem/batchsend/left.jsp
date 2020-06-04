<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	YZPerson loginUser = SessionUtil.getLoginPerson(request);

	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<title>模板消息列表</title>
<style>
	html{
		height:100%;
		overflow-y:auto;
	}
 	body{
 		margin:0px;
 		padding:0px;
 	}
 	
	ul{
		padding:0;
		margin:0;
	}
	#ul{
		
	}
	#ul li{
		list-style:none;
		padding:2px 10px;
		font-size:14px;
		color:#0E7CC9;
	}
	#ul li.now{
		color:#fff;
		background:#0E7CC9;
	}
</style>
<script type="text/Javascript">
function doInit() {
    getTemplateMsgList();
}

function getTemplateMsgList() {
    var pageUrl = "WXTemplateItemAct/selectList.act?1=1";
    var rtData = getDataFromServer(pageUrl);
    if (rtData) {
        var liHtml = "";
        $.each(rtData.list,
        function(index) {
            var liObj = rtData.list[index];
            var li = "<li onclick='toNewPage(\"" + liObj.seqId + "\",\"" + liObj.templateSeqid + "\",\"" + liObj.templateId + "\")' style='cursor:pointer;' >" + liObj.first + "</li>";
            liHtml += li;
        });
        $("#ul").html(liHtml);
    }
}

function toNewPage(seqId, templateSeqid, templateId) {
    var url = contextPath + '/WXTemplateItemAct/toSend.act?seqId=' + seqId + '&templateSeqid=' + templateSeqid + '&templateId=' + templateId;
    parent.contentFrame.location = url;
}
</script>
</head>
<body onload="doInit()" style="overflow-x:hidden;">
<div>
	<ul id="ul">
	</ul>
</div>
<script type="text/Javascript">
	$("#ul").on("click","li",function(){
		$(this).addClass("now").siblings("li").removeClass("now");
	})
</script>
</body>
</html>