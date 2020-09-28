<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	YZPerson loginUser = SessionUtil.getLoginPerson(request);

	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String priv_seqId = request.getParameter("priv_seqId");
	if (priv_seqId == null) {
		priv_seqId = "";
	}
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
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
var priv_seqId = "<%=priv_seqId%>";
var static_get_menutree_url = '<%=contextPath%>/YZMenuAct/getAllMenu4TreeData.act?privSeqId=' + priv_seqId;
var static_privset_url = 'YZPrivAct/menuSetSave.act?1=1';
var organization = '<%=organization %>';
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
   		}
    };
    
    setting['check'] = {
        enable: true
    };

    setting['callback'] = {
        onClick: onclick,
        onCheck: menuSetSave
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

    var url = "<%=contextPath%>/YZPrivAct/toSetPrivList.act?menuId=" + id + "&priv_seqId=" + priv_seqId+"&organization="+organization;
    parent.contentFrame.location = url;
}

/**
 * 选中、取消选中，实时提交设置到服务器
 */
function menuSetSave(event, treeId, treeNode) {
    if (priv_seqId == "") {
        layer.alert('角色ID为空，请联系系统管理员' );
        return false;
    }

    var zTree = $.fn.zTree.getZTreeObj("treeList");
    var selectList = zTree.getCheckedNodes(true);
    // 获取要删除的用户id，以逗号分隔
    var seqIds = "";
    for (var i = 0; i < selectList.length; i++) {
        seqIds += selectList[i].id + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);

    var reqUrl = static_privset_url + "&seqId=" + priv_seqId + "&funcIdStr=" + seqIds;
    var serverData = getDataFromServer(reqUrl);
    if (serverData) {
    	if(parent.parent.layer){
    		parent.parent.alert('设置成功');
    	}
    }
}
</script>
</head>
<body onload="doInit()" style="overflow-x:hidden;">
<div id="treeList" class="ztree"></div>
</body>
</html>