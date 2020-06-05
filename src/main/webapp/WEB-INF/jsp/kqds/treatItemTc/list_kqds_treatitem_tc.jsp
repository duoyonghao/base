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
<title>收费套餐管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<!-- 由费用添加页面  新增套餐， 后台提供编辑 查询 功能 -->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div id="toolbar">
				 <!--dom结构部分-->
				<div class="child-inline-block-middle" id="uploader-demo">
					<button id="parentIframe" class="kqdsSearchBtn bigBtn" >
					       <i class="glyphicon glyphicon-plus"></i> 新增
					</button>
					<span class="commonText">套餐类型：</span><input type="text" name="tctype" id="tctype" >
					<span class="commonText">套餐名称：</span><input type="text" name="tcname" id="tcname" >
					<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="float: right;">
					 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
					</button>
				</div>
			</div>
			<div class="tableBox">
				<table id="table" class="table-striped table-condensed table-bordered"  data-height="460"></table>
			</div>
			
		</div>
	</div>
</section>
<script type="text/javascript">
var perseqId = "<%=person.getSeqId()%>";

var pageurl = '<%=contextPath%>/KQDS_TreatItem_TcBackAct/selectPage.act';
var $table = $('#table');
$(function() {
	
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        sidePagination: "server",
        onLoadSuccess:function(){
        	setHeight();
        },
        //服务端处理分页
        columns: [{
            title: '套餐类型',
            field: 'tctype',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '套餐名称',
            field: 'tcname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '是否公用',
            field: 'isopen',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
            	var html="";
            	if(value=="1"){
            		html = '<span style="color:green;">公用</span>';
            	}else{
            		html = '<span style="color:red;">自用</span>';
            	}
                return html;
            }
        },
        {
            title: '创建人',
            field: 'createusername',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var menubutton = "";
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\',\'' + row.tctype + '\',\'' + row.tcname + '\')">修改</a> ';
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red">删除</span></a>';
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\',\'' + row.tctype + '\',\'' + row.tcname + '\')">详情</a>  ';
                if(row.isopen == 1){
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="openOrcloseTc(\'' + row.seqId + '\',0)">自用</a>  ';
                }else{
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="openOrcloseTc(\'' + row.seqId + '\',1)">公用</a>  ';
                }
                return menubutton;
            }
        }]
    });
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
        tctype: $('#tctype').val(),
        tcname: $('#tcname').val()
    };
    return temp;
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}
//公开套擦
function openOrcloseTc(tcnameid, isopen) {
    var opera = "公用";
    if (isopen == 0) {
        opera = "自用";
    }
    //询问框
    layer.confirm('确定改为' + opera + '套餐？', {
        btn: [opera, '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_TreatItem_TcBackAct/openOrcloseTc.act?tcnameid=' + tcnameid + '&isopen=' + isopen;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('已' + opera, {
                      
                });
                refresh();
            }
        },
        function() {
            layer.alert('操作失败！'  );
        });
    });
}

function edit(tcnameid,tctype,tcname) {
    var index = layer.open({
        type: 2,
        offset: ['10px'],
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改收费套餐',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['90%', '500px'],
        content: contextPath+'/KQDS_TreatItem_TcBackAct/toEdit.act?tcnameid=' + tcnameid + '&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>&tctype='+tctype+'&tcname='+tcname,
    });
}
function detail(tcnameid,tctype,tcname) {
    var index = layer.open({
        type: 2,
        offset: ['10px'],
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：收费套餐详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['90%', '500px'],
        content: contextPath+'/KQDS_TreatItem_TcBackAct/toDetail.act?tcnameid=' + tcnameid + '&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>&tctype='+tctype+'&tcname='+tcname,
    });
}
function del(tcnameid) {
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_TreatItem_TcAct/deleteObjBytctype.act?tcnameid=' + tcnameid;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                });
                refresh();
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });
}

//弹出一个iframe层
$('#parentIframe').on('click',
function() {
    layer.open({
        type: 2,
        offset: ['10px'],
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增收费套餐',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['90%', '500px'],
        content:  contextPath+'/KQDS_TreatItem_TcBackAct/toNewAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});
  
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight()-20;
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight
	})
	$(".leftDiv").outerHeight(tableHeight);
} 
</script>



</body>
</html>
