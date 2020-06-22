<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>批量发送图文</title>
<!-- 由费用添加页面  新增套餐， 后台提供编辑 查询 功能 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/fhbuttonTree.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItemTc/treatitemTc.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
</head>
<body>
	<div class="containerDiv">
			<ul id="treeDemo" class="ztree" style="height: 100%;"></ul>
	</div>
<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var openid = $.getUrlVar('openid');
$(function() {
    zTreeInit();
    /*wl--ztreeInit初始化的 宽度为228px 这里设置为100% */
    $("#treeDemo").css("width", "100%");

    /*wl--结束 */
});
function zTreeInit() {
    //异步加载
    var url = '<%=contextPath%>/WXNewsAct/select4chat.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
    var setting = {
        view: {
            showIcon: false // 去掉图标
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
    $.fn.zTree.init($("#treeDemo"), setting);
}
function onclick(e, treeId, treeNode) {
    if (treeNode.level == 1) {
        //询问框
        layer.confirm('确定发送此图文吗？[' + treeNode.name + ']', {
            btn: ['确定', '取消'] //按钮
        },
        function() {
            // 发送图文
            parent.batchSendNewsMsg(treeNode.id);
        });
    }
}


</script>
</body>
</html>
