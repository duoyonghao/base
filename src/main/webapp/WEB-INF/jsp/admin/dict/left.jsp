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
var contextPath = "<%=contextPath %>";
function doInit() {
    zTreeInit();
}

function zTreeInit() {
    //异步加载
    var url = '<%=contextPath%>/YZDictAct/getDictTree.act';
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
    var url = "";
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        zTree.expandNode(treeNode);
    } else {

	}
    
    if("COSTITEM_SORT" == treeNode.id){ // 收费项目分类
    	url = contextPath+"/YZDictAct/toTreatItemType.act";
    } else if("HZLY" == treeNode.id){ // 患者来源
    	url = contextPath+"/YZDictAct/toHzly.act";
    } else if("BLFL" == treeNode.id){ // 病历分类
    	url = contextPath+"/YZDictAct/toBlfl.act";
    } else if("JZMD" == treeNode.id){ // 就诊目的
    	url = contextPath+"/YZDictAct/toJzmd.act";
    } else if("DXFL" == treeNode.id){ // 短信分类
    	url = contextPath+"/YZDictAct/toDxfl.act";
    } else if("blct124" == treeNode.id){ // 病历词条
    	url = contextPath+"/YZDictAct/toBlct.act";
    } else {
    	url = contextPath+"/YZDictAct/toList.act?parentCode=" + treeNode.id;
    }
    parent.contentFrame.location = url;

}

/**
 * 刷新指定节点，并展开
 */
function refreshNode(deptId) {

    if ("0" == deptId) {
        zTreeInit();
        return;
    }

    var zTree = $.fn.zTree.getZTreeObj("treeList");
    var deptNode = zTree.getNodeByParam("id", deptId);
	if(deptNode){
		if (deptNode.isParent) {
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

/**
 * 删除时调用
 */
function refreshNode4Delte(deptId) {
    if ("0" == deptId) {
        zTreeInit();
        return;
    }

    var zTree = $.fn.zTree.getZTreeObj("treeList");
    var deptNode = zTree.getNodeByParam("id", deptId);
    // 获取父节点
    var nodeParent = deptNode.getParentNode();
    zTree.selectNode(nodeParent); //指定选中ID的节点  
    zTree.reAsyncChildNodes(nodeParent, "refresh", false);
    zTree.expandNode(nodeParent, true, false); //指定选中ID节点展开  
}
</script>
</head>
<body onload="doInit()" style="overflow-x:hidden;">
<div id="treeList" class="ztree"></div>
</body>
</html>