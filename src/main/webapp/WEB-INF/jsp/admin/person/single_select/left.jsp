<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	YZPerson loginUser = SessionUtil.getLoginPerson(request);

	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

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
<link type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/person/single_select_left.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<title>左侧树形目录</title>

<script type="text/Javascript">
var showleave = "<%=showleave%>";
function doInit() {
    zTreeInit();
}

function zTreeInit() {
    //异步加载
    var url = '<%=contextPath%>/YZDeptAct/getDeptTreeOnlyOrg.act';
    var setting = {
        view: {
            showIcon: true // 去掉图标
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id", "name=n", "level=lv"],
            otherParam: {
                "isSingle": "<%=isSingle%>",
                "depttype": "<%=depttype%>"
            },
            dataFilter: ajaxDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onclick,
        onAsyncSuccess: function (event, treeId, treeNode, msg){
        	var zTreeTmp = $.fn.zTree.getZTreeObj("treeList"); /** 默认展开第一级和第二级  **/
        	var rtObj = eval('(' + msg + ')');
        	for(var i=0; i < rtObj.retData.length; i ++){
        		var node = (rtObj.retData)[i];
        		if(node.pId == 'orgParentId' || node.pId == '0' ){
        			var findNode = zTreeTmp.getNodeByParam("id",node.id,null);
                    if(findNode && findNode.isParent ==  true){ // 没有子节点不要执行下面的代码，否则会导致程序终止
                    	zTreeTmp.setting.callback.onClick(null, zTreeTmp.setting.treeId, findNode);//触发函数
                    }
        		}
        	}
        }
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
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        zTree.expandNode(treeNode);
    }
    if (treeNode.id == "orgId") { // 顶级部门
        return;
    } else {
        var url = "<%=contextPath%>/YZPersonAct/toSingleList.act?deptId=" + treeNode.id+"&showleave="+showleave;
        parent.contentFrame.location = url;
    }
}

/**
 * 刷新指定节点，并展开
 */
function refreshNode(deptId) {
    if (deptId == "0") {
        deptId = "orgId";
    }

    var zTree = $.fn.zTree.getZTreeObj("treeList");
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
<body onload="doInit()">
<p class="chooseDept">选择部门</p>
<ul id="treeList" class="ztree"></ul>
</body>
</html>