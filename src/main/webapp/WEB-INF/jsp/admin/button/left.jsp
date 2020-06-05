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
<title>左侧树形目录</title>
<script type="text/Javascript">
var static_get_menutree_url = '<%=contextPath%>/YZMenuAct/getAllMenu4TreeManage.act';
function doInit() {
    zTreeInit();
}

function zTreeInit() {
    var menutreeNodes = null;
    $.axse(static_get_menutree_url, null,
    function(data) {
        menutreeNodes = data.menuList;
    },
    function() {
        layer.alert('加载失败！' );
    });
    
    var setting = {
   		data: {
   			simpleData: {
   				enable:true,
   				idKey: "id",
   				pIdKey: "pId",
   				rootPId: "0"
   			}
   		},
    };

    setting['check'] = {
        enable: false
    };
    setting['callback'] = {
        onClick: onclick
    };

    $.fn.zTree.init($("#treeList"), setting, menutreeNodes);
}

function onclick(e, treeId, treeNode) {
    var id = treeNode.id;
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        zTree.expandNode(treeNode);
    } else {
        // 什么都不做
    }

    var url = "<%=contextPath%>/YZButtonAct/toList.act?menuId=" + id;
    parent.contentFrame.location = url;
}

function refresh4Menu() {
    zTreeInit();
}
</script>
</head>
<body onload="doInit()" style="overflow-x:hidden;">
<div id="treeList" class="ztree"></div>
</body>
</html>