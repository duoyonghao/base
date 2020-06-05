<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>就诊目的</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
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
	   			<div id="toolbar">
	   				<input id="organization" name="organization" type="hidden" />
					<span class="commonText">就诊目的：</span><select class="dict" tig="JZMD" name="jzmd" id="jzmd"></select>
					<span class="commonText">子分类名称：</span><select  name="jzmdchildname" id="jzmdchildname"></select>
					<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="float: right;">
					 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
					</button>
				</div>
				<div class="tableBox">
					<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
				</div>
			</div>
		</div>
	</section>
<script>
var organization = '<%=organization%>';
var pageurl = '<%=contextPath%>/KQDS_Jzmd_TypeBackAct/selectPage.act?organization=' + organization;
var $table = $('#table');
$(function() {
	initDictSelectByClassOrg(organization);
	/*初始化计算table的高度 */
    $("#organization").val(organization);
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        sidePagination: "server",
        //服务端处理分页
        columns: [{
            title: '就诊目的',
            field: 'fname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '子分类名称',
            field: 'cname',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '提醒就诊',
            field: 'txname',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '有效天数',
            field: 'yxday',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
            	var menubutton = "";
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="addTx(\'' + row.fid + '\',\'' + row.cid + '\',\'' + row.jzmdid + '\')"> 设置提醒</a> ';
                return menubutton;
            }
        }]
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
        jzmd: $('#jzmd').val(),
        jzmdchildname: $('#jzmdchildname').val()
    };
    return temp;
}
$('#jzmd').change(function() {
	getSubDictSelectByParentCode(this.value,'jzmdchildname');
});

function refresh() {

    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

}

function zTreeInit() {
    //异步加载
    var url = contextPath + '/YZDictAct/getLITree.act?parentCode=JZMD&organization='+organization;

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
    var url = "";
    if (treeNode.level == 0) { // 一级分类
    	$("#jzmd").val(treeNode.id);
    	$("#jzmd").trigger("change");
    }

    refresh(); // 执行查询

}


function addTx(fid,cid,id) {
    var index = layer.open({
        type: 2,
        title: '设置就诊提醒',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '320px'],
        content: contextPath+'/KQDS_Jzmd_TypeAct/toNewAdd.act?fid='+fid+'&cid='+cid+'&seqId=' + id + '&organization=' + organization
    });
}
  
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
