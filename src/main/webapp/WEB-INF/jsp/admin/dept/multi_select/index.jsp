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
<!--与multi_select/left.jsp使用相同样式表  -->
<link type="text/css" rel="stylesheet"  href="<%=contextPath%>/static/css/admin/person/multi_select/multi_select_left.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<!--与multi_select/left.jsp使用相同js  -->
<script type="text/Javascript" src="<%=contextPath%>/static/js/admin/person/multi_select/multi_select_left.js"></script>
<title>部门多选</title>
<script type="text/Javascript">	
var frameindex = null //先得到当前iframe层的索引
var static_single_select_deptid = null;
var static_single_select_deptname = null;
var static_single_select_deptObj = null;
var static_single_select_deptDescObj = null;
// 取父页面的值
var static_select_deptid_val = null;
var static_select_deptname_val = null;

var deptRetNameArray = parent.deptRetNameArrayMulti; // 多选
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
function setSelectVal() {
    var zTree = $.fn.zTree.getZTreeObj("treeList");
    var selectList = zTree.getCheckedNodes(true);
    // 获取要删除的用户id，以逗号分隔
    var seqIds = "";
    var names = "";
    for (var i = 0; i < selectList.length; i++) {
        var tmpId = selectList[i].id; // 替换掉后台赋值的person
        seqIds += tmpId + ",";
        names += selectList[i].name + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);
    names = names.substring(0, names.length - 1);

    $(static_single_select_deptObj).val(seqIds);
    $(static_single_select_deptDescObj).val(names);

    // 重新取值，赋值
    static_select_deptid_val = $(static_single_select_deptObj).val(); // id
    static_select_deptname_val = $(static_single_select_deptDescObj).val(); // name
}

function submit() {
    setSelectVal();
    parent.layer.close(frameindex); //再执行关闭 */
}

$(function() {
    if (parent && parent.layer) {
        frameindex = parent.layer.getFrameIndex(window.name);
    }
    // 取父页面的值
    static_select_deptid_val = $(static_single_select_deptObj).val(); // id
    static_select_deptname_val = $(static_single_select_deptDescObj).val(); // name
    // 初始化树
    zTreeInit4All();

    // 初始化Ul li
    initUL();
});

function initUL() {
    var idArray = static_select_deptid_val.split(',');
    var nameArray = static_select_deptname_val.split(',');
    $("#pul").empty();
    for (var i = 0; i < idArray.length; i++) {
        if (idArray[i] != null && idArray[i] != '') {
            $("#pul").append("<li onclick=\"expandNode('" + idArray[i] + "')\">" + nameArray[i] + "</li>");
        }
    }
}

function expandNode(id) {
    var nodeId = id;
    var zTree = $.fn.zTree.getZTreeObj("treeList");
    var node = zTree.getNodeByParam("id", nodeId);

    if (node.isParent) { // 
        zTree.selectNode(node); //指定选中ID的节点  
        zTree.reAsyncChildNodes(node, "refresh", false);
        zTree.expandNode(node, true, false); //指定选中ID节点展开  
    } else {
        var nodeParent = node.getParentNode();
        zTree.selectNode(nodeParent); //指定选中ID的节点  
        zTree.reAsyncChildNodes(nodeParent, "refresh", false);
        zTree.expandNode(nodeParent, true, false); //指定选中ID节点展开  
    }

}

/**
 * 一次加载所有部门、人员树
 */
function zTreeInit4All() {
    var url = '<%=contextPath%>/YZDeptAct/getDeptTree4All.act?deptIds=' + static_select_deptid_val;
    var treeNodes = null;
    $.axse(url, null,
    function(data) {
        treeNodes = data.retData;
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

    setting['callback'] = {
        onClick: onclick,
        onCheck: zTreeOnCheck
    };

    setting['check'] = {
        enable: true
    };

    $.fn.zTree.init($("#treeList"), setting, treeNodes);
    
    var zTreeTmp = $.fn.zTree.getZTreeObj("treeList"); /** 默认展开第一级和第二级  **/
    var allNodes = zTreeTmp.getNodes();
    for(var i=0; i < allNodes.length; i ++){
		var node = allNodes[i];
		if(node.pId == 'orgParentId' || node.pId == '0' ){
			var findNode = zTreeTmp.getNodeByParam("id",node.id,null);
            if(findNode && findNode.isParent ==  true){ // 没有子节点不要执行下面的代码，否则会导致程序终止
            	zTreeTmp.setting.callback.onClick(null, zTreeTmp.setting.treeId, findNode);//触发函数
            }
		}
	}
}

// 选中，取消选中
function zTreeOnCheck(event, treeId, treeNode) {
    if (treeNode.checked) {
        // 选中
    } else {
        // 取消选中
    }
    setSelectVal();

    // 初始化Ul li
    initUL();
}

function onclick(e, treeId, treeNode) {
    var id = treeNode.id;
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeList");
        zTree.expandNode(treeNode);
    } else {
        // 什么都不做
    }
}
</script>
</head>

<!-- <body style="overflow-x:hidden;">
<div style="float: left;">
<div id="treeList" class="ztree" ></div>

</div>

<div id="plist" style="float: left;">
已选部门：
	<ul id="pul">
		
	</ul>
	<div style="width:100%;text-align: center;"><a class="kqdsCommonBtn" id="menuSetSave" href="javascript:void(0);" onclick="submit()">确定</a></div>
</div>
</body> -->

<body>
<div id="roleChoice" style="float: left;">
<p class="roleChoiceText"></p>
<ul id="treeList" class="ztree"></ul>

</div>

<div id="plist" style="float: left;">
<p class="roleChoiceText">已选部门：</p>
	<ul id="pul">
		
	</ul>
	<div class="btnGroup">
		<a class="kqdsCommonBtn" id="menuSetSave" href="javascript:void(0);" onclick="submit()">确 定</a>
	</div>	
</div>

</body>
</html>