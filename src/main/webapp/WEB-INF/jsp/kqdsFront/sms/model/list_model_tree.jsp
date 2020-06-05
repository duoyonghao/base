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
<title>短信模板</title>
<!-- 点击右侧个人中心  中间病历 图标 页面， 进入后点击  常用病历库按钮 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/fhbuttonTree.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<style>
	.kuBtn{
		margin: 0 3% 0 0;
	}
</style>
</head>
<body>
	<div style="float: left;width:29%;margin-left:1%;">
		<ul id="treeDemo" class="ztree" style="width:98%;margin-top:0;"></ul>
	</div>
	<div style="float: left;width:69%;margin-right:1%;">
		<div id="toolbar" style="padding:5px 0; margin-top:0;">
		<!-- wl 修改了下面按钮 class名 重写样式 -->
			<button id="parentIframe" class="confirmBtn">
				<i class="glyphicon glyphicon-plus"></i> 选择
			</button>
		</div>
		<div class="box-body" id="blcontent" style="overflow:auto;"></div>
	</div>
<script>
var perseqId = "<%=person.getSeqId()%>";
var content = "",smstype="",smsnexttype="";
$(function() {
    zTreeInit();
    
    /*树自适应高度  */
    selfAdaptive();
});
/*树自适应高度  */
function selfAdaptive(){
	var treeHeight=$(window).height()-$(".btnFather").outerHeight()-10;
	$("#treeDemo").outerHeight(treeHeight-$(".searchWrap").outerHeight());
	$("#blcontent").outerHeight(treeHeight);
}
function zTreeInit() {
    var zNodes;
    var url = '<%=contextPath%>/KQDS_Sms_ModelAct/getSelectModelTree.act';
    $.axse(url, null,
    function(data) {
        zNodes = eval(data.tree);
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
    setting['callback'] = {
        onClick: onCheck
    };
    setting['check'] = {
        enable: false
    };
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
}
function getFont(treeId, node) {
	return node.font ? node.font : {};
}
function onCheck(e, treeId, treeNode) {
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    } else {
        treeNode.check_Focus = true;
        treeNode.checked = true;
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.updateNode(treeNode);
        var detailurl = '<%=contextPath%>/KQDS_Sms_ModelAct/selectDetail.act?seqId=' + treeNode.id;
        $.axse(detailurl, null,
        function(data) {
        	$("#blcontent").html(data.data.smscontent);
        	content = data.data.smscontent;
        	smstype = data.data.smstype;
        	smsnexttype = data.data.smsnexttype;
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}
// 弹出一个iframe层
$('#parentIframe').on('click',
function() {
    if (content == "") {
        layer.alert('请选择病历模板！'  );
        return false;
    }
    parent.setsmscontent(content,smstype,smsnexttype);
    var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(frameindex); //再执行关闭 */
});
</script>
</body>
</html>
