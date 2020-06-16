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
<title>左侧树形目录</title>
<script type="text/Javascript">	
function doInit() {
    zTreeInit();
}

function zTreeInit() {
    //异步加载
    var url = '<%=contextPath%>/YZPersonAct/getPersonTree.act';
    var setting = {
        view: {
            showIcon: true // 去掉图标
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id", "name=n", "level=lv"],
            otherParam: {
                "otherParam": "zTreeAsyncTest"
            },
            dataFilter: ajaxDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onclick
    };
    $.fn.zTree.init($("#treeList"), setting);
}

//解析树 返回数据
function ajaxDataFilter(treeId, parentNode, responseData) {
    var tree;
    if (responseData.retState == "0") {
        tree = responseData.retData;
    }
    return tree;
};

function onclick(e, treeId, treeNode) {
    var id = treeNode.id;
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        zTree.expandNode(treeNode);
    } else {
        // 什么都不做
    }

    if (id == "orgId") {
        return;
    }
    if (id.indexOf('person') == -1) { // 部门
        var urls = "<%=contextPath%>/YZPersonAct/toList.act?deptId=" + id;
    } else { // 人员
        id = id.substring(6, id.length); // 6 是  person单词的长度
        var urls = "<%=contextPath%>/YZPersonAct/toEdit.act?personId=" + id;
    }
    parent.contentFrame.location = urls;
}

/**
 * 刷新指定节点，并展开
 */
function refreshNode(deptId) {
	var zTree = $.fn.zTree.getZTreeObj("treeList");
	if(!deptId){ // 如果deptId不存在，则重新加载树
		zTree.reAsyncChildNodes(null, "refresh");
		return false;
	}
    
    var deptNode = zTree.getNodeByParam("id", deptId);
	if(deptNode){
		if (deptNode.isParent) { // 
	        zTree.selectNode(deptNode); //指定选中ID的节点  
	        zTree.reAsyncChildNodes(deptNode, "refresh", false);
	        zTree.expandNode(deptNode, true, false); //指定选中ID节点展开  
	    } else {
	        var nodeParent = deptNode.getParentNode();
	        zTree.selectNode(deptNode); //指定选中ID的节点  
	        zTree.reAsyncChildNodes(nodeParent, "refresh", false);
	        zTree.expandNode(deptNode, true, false); //指定选中ID节点展开  
	    }
	}else{
		zTreeInit();
	}
    
}
</script>
</head>
<body onload="doInit()" style="overflow-x:hidden;">
<div id="treeList" class="ztree"></div>
</body>
</html>