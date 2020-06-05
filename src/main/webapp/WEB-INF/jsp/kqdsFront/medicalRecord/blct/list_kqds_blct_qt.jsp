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
<title>病历词条管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<!-- 后台维护页面 -->
<style type="text/css">
	.leftDiv{
		width:150px;
		float:left;
		height:100%;
		/* background:beige; */
		border:1px solid #ddd;
		overflow-y:auto;
	}
	.rightDiv{
		margin-left:160px;
		/* background:deepskyblue; */
		height:100%;
		padding-right:10px;
		overflow-y:auto;
	}
	.kqdsSearchBtn{
		padding:0 10px;
	}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">

<section class="content">
	<div>
		<div class="leftDiv">
			<div id="treeDemo" class="ztree"></div>
		</div>
	 	<div class="rightDiv">
 			<div id="toolbar" style="overflow:hidden;">
				<button id="parentIframe" class="kqdsSearchBtn bigBtn" >
				       <i class="glyphicon glyphicon-plus"></i> 使用
				</button>
				<span class="commonText">词条类别：</span><select class="dict" tig="blct124" name="cttype" id="cttype"></select>
				<span class="commonText">二级分类：</span> <select class="select2"  name="ctnexttype" id="ctnexttype"><option value="">请选择</option></select>
				<span class="commonText">词条名称：</span><input type="text" name="ctname" id="ctname" >
			</div>
			<div class="tableBox">
				<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
			</div>
		</div>
	</div>
</section>
<script>
var pageurl = '<%=contextPath%>/YZBlctAct/selectPage.act';
var static_updateurl = 'YZBlctAct/updateFlagBySeqIds.act?1=1';
var onclickrowOobj2 = "";
$(function() {
	initDictSelectByClass();
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        toolbar: '#toolbar',
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        sidePagination: "server",
        //服务端处理分页
        columns: [
        {
            title: '词条内容',
            field: 'ctname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true
        }]
    }).on('click-row.bs.table',
  	    function(e, row, element) {
  	        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
  	        $(element).addClass('success'); //添加当前选中的 success样式用于区别
  	        var index = $('#table').find('tr.success').data('index'); //获得选中的行
  	        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
  	    });
    
    zTreeInit();
    
    /* 页面重置，重新设置table的高度 */
    $(window).resize(function(){
		setHeight(); 
	});
});
function queryParams(params) {
    var temp = { 
   		offset : params.offset,
       	limit :	 params.limit,
        organization: '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        ctname: $('#ctname').val(),
        cttype: $('#cttype').val(),
        ctnexttype: $('#ctnexttype').val()
    };
    return temp;
}
$('#cttype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
		initCostSortSelect2Level('ctnexttype',this.value);
	}
});

function zTreeInit() {
    //异步加载
    var url = contextPath + '/YZDictAct/getDictTree4Blct.act';

    var setting = {
            view: {
                showIcon: true // 去掉图标
            },
            async: {
                enable: true,
                url: url,
                autoParam: ["id", "name=n", "level=lv"],
                otherParam: {
                    "otherParam": "zTreeAsyncTest",
                    "organization": "<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>"
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
    if (treeNode.level == 0) { // 一级分类
    	$("#cttype").val(treeNode.id);
    	$("#cttype").trigger("change");
    	$("#ctnexttype").val("");
        
    } else { // 二级分类
    	$("#cttype").val(treeNode.pId);
    	$("#cttype").trigger("change");
    	$("#ctnexttype").val(treeNode.id);
	}
    refresh(); // 执行查询
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}
$('#parentIframe').on('click',
function() {
   if(onclickrowOobj2==""){
	   layer.alert('请选择词条！'  );
   }else{
	   parent.$("#tabIframe")[0].contentWindow.setct(onclickrowOobj2.ctname);
	   var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	   parent.layer.close(frameindex); //再执行关闭 */
   }
});
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight();
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight-40
	})
	$(".leftDiv").outerHeight(tableHeight);
}
</script>



</body>
</html>
