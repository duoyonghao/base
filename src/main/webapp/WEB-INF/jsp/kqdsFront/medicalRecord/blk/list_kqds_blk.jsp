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
<title>病历库管理</title>
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
				       <i class="glyphicon glyphicon-plus"></i> 新增
				</button>
				<span class="commonText">病历库类别：</span><select class="dict" tig="BLKFL" name="blkfl" id="blkfl"></select>
				<span class="commonText">病历库种类：</span>
					<select id="mtype" name="mtype">
						<option value="">请选择</option>
						<option value="0">初诊</option>
						<option value="1">复诊</option>
					</select>
				<span class="commonText">病历库名称：</span><input type="text" name="blname" id="blname" >
				<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="margin:0 0 0 5px;">
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
var pageurl = '<%=contextPath%>/KQDS_BLKAct/selectPage.act';
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
        columns: [{
            title: '病历库名称',
            field: 'blname',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '病历库类别',
            field: 'blkfl',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return value;
                }
            }
        },
        {
            title: '病历种类',
            field: 'mtype',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (value == "1") {
                    html = '<span class="label label-success">复诊</span>';
                } else {
                    html = '<span class="label label-success">初诊</span>';
                }
                return html;
            }
        },
        {
            title: '是否自用',
            field: 'type',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (value == "1") {
                    html = "自用";
                } else {
                    html = "公用";
                }
                return html;
            }
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	if (value) {
                    return value;
                }
            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var html = "";
                if ("<%=person.getSeqId()%>" == 1) {
                	html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="bzblkEdit(\'' + row.seqId + '\',\'' + row.mtype + '\')"><span style="color:#00A6C0;">编辑</span></a> ';
                	html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="copyBLK(\'' + row.seqId + '\')">拷贝</a> ';
                	html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red;">删除</span></a> ';
                }
                html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                return html;
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
        organization: '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        blkfl: $('#blkfl').val(),
        mtype: $('#mtype').val(),
        blname: $('#blname').val()
    };
    return temp;
}
function refresh() {

    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

}

function copyBLK(seqId) {
    layer.open({
        type: 2,
        title: '拷贝病历库',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['500px', '400px'],
        content: contextPath + '/KQDS_BLKAct/KQDS_BLKAct?seqId=' + seqId
    });
}

function zTreeInit() {
    //异步加载
    var url = contextPath + '/YZDictAct/getLITree.act?parentCode=BLKFL&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';

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
    	$("#blkfl").val(treeNode.id);
        
    }

    refresh(); // 执行查询

}

function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getCurrentOrganizationName(request)%>：病历库详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '480px'],
        content:  contextPath + '/KQDS_BLKAct/toBlkDetail.act?seqId=' + id
    });
}
function del(id) {

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = contextPath +'/KQDS_BLKAct/deleteObj.act?seqId=' + id;
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
        title: '<%=ChainUtil.getCurrentOrganizationName(request)%>：新增病历模板',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        /*   offset: 'ct', */
        area: ['80%', '80%'],
        content: '<%=contextPath%>/KQDS_BLKAct/toMedicalrecord4blk.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});

//编辑病历
function bzblkEdit(seqId, mtype) {
 parent.layer.open({
     type: 2,
     title: '编辑病历',
     maxmin: true,
     shadeClose: true,
     //点击遮罩关闭层
     zIndex: 211111,
     area: ['80%', '90%'],
     content: contextPath + '/KQDS_BLKAct/toMedicalrecord_Edit4blk.act?seqId=' + seqId + '&mtype=' + mtype
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
