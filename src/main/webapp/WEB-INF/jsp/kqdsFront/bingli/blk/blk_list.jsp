<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);

	String usercode = request.getParameter("usercode");
	if (usercode == null) {
		usercode = "";
	}

	String regno = request.getParameter("regno");
	if (regno == null) {
		regno = "";
	}

	String type = request.getParameter("type"); // 0 标准 1 自用
	if (type == null) {
		type = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>种植自用病历库</title>
<style type="text/css">
label {
	cursor: pointer;
}
</style>
</head>
<body>
<div id="toolbar">
	<button id="addBtn" class="btn btn-danger">
		<i class="glyphicon glyphicon-plus"></i> 新增
	</button>
	<input type="radio" name="mtype_header" value="2" id="mtype_2" checked="checked"><label for="mtype_2">种植一期</label>
	<input type="radio" name="mtype_header" value="3" id="mtype_3"><label for="mtype_3">术后拆线</label>
	<input type="radio" name="mtype_header" value="4" id="mtype_4"><label for="mtype_4">种植二期</label>
	<input type="radio" name="mtype_header" value="5" id="mtype_5"><label for="mtype_5">修复</label>
</div>
<table id="table" class="table-striped table-condensed table-bordered"  data-height="500"></table>
<script type="text/javascript">
var pageurl = '<%=contextPath%>/KQDS_BLKAct/selectPage4Front.act';
var $table = $('#table');
var static_type = '<%=type%>';
var static_personId = '<%=person.getSeqId()%>';
var static_regno = '<%=regno%>';
$(function() {
    initTableList();
});
$('input[name="mtype_header"]').change(function() {
	refresh();
});
function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		type: "<%=type%>",
    		mtype:$('input[name="mtype_header"]:checked ').val(),
    		organization: "<%=ChainUtil.getCurrentOrganization(request)%>"
    };
    return temp;
}
function setHeight(){
	var tableHeight=$(window).outerHeight()-$("#toolbar").outerHeight();
	$("#table").bootstrapTable("resetView",{
		height:tableHeight-5
	});
}
function initTableList() {
    $table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: false,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        //服务端处理分页
        columns: [{
            title: '病历库名称',
            field: 'blname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '病历库类别',
            field: 'mtype',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (value == "0") {
                    html = '<span class="label label-success">初诊</span>';
                } else if (value == "1") {
                    html = '<span class="label label-success">复诊</span>';
                } else if (value == "2") {
                    html = '<span class="label label-success">种植1期</span>';
                } else if (value == "3") {
                    html = '<span class="label label-success">术后拆线</span>';
                } else if (value == "4") {
                    html = '<span class="label label-success">种植二期</span>';
                } else if (value == "5") {
                    html = '<span class="label label-success">种植修复</span>';
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
            editable: true,
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
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
        },
        {
            title: '创建人',
            field: 'createusername',
            align: 'center',
            sortable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var html = "";
                if (static_type == '1') { // 自用病历库
                }
                if (static_personId == row.createuser && static_regno) {
                    html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\',\'' + row.mtype + '\')"><span style="color:red">编辑</span></a> ';
                    html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red">删除</span></a> ';
                }
                html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\',\'' + row.mtype + '\',\'detail\')">详情</a> ';
                return html;
            }
        }]
    });
}

/**
 * seqId 病历主表主键
 * mtype 病历类型
 * detailFlag 是否是详情页面
 */
function edit(seqId, mtype, detailFlag) {
    var windowTitle = "编辑病历模板";
    var titleStr = "";
    var requrl = '';

    if (mtype == '2') {
    	titleStr = "【种植一期】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhiYiQi_Edit.act';
    }
    
    if (mtype == '3') {
    	titleStr = "【种植复查】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhi_Suture_Removal_Edit.act';
    }

    if (mtype == '4') {
    	titleStr = "【种植二期】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhiErQi_Edit.act';
    }

    if (mtype == '5') {
    	titleStr = "【种植修复】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhi_XiuFu_Edit.act';
    }

    requrl += '?seqId=' + seqId + '&mtype=' + mtype + '&type=<%=type%>';

    layer.open({
        type: 2,
        title: windowTitle + titleStr,
        shade: 0.6,
        shadeClose: false,
        area: ['95%', '98%'],
        content: requrl
    });
}

/**
 * 病历库详情
 */
function detail(seqId, mtype, detailFlag) {
    var windowTitle = "病历模板详情";
    var titleStr = "";
    var requrl = '';

    if (mtype == '2') {
    	titleStr = "【种植一期】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhiYiQi_Detail.act';
    }
    
    if (mtype == '3') {
    	titleStr = "【种植复查】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhi_Suture_Removal_Detail.act';
    }

    if (mtype == '4') {
    	titleStr = "【种植二期】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhiErQi_Detail.act';
    }

    if (mtype == '5') {
    	titleStr = "【种植修复】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhi_XiuFu_Detail.act';
    }

    requrl += '?seqId=' + seqId + '&mtype=' + mtype + '&type=<%=type%>';

    layer.open({
        type: 2,
        title: windowTitle + titleStr,
        shade: 0.6,
        shadeClose: false,
        area: ['95%', '98%'],
        content: requrl
    });
}

//弹出一个iframe层
$('#addBtn').on('click',
function() {
	var titleStr = "【种植一期】";
	
    var mtype = $('input[name="mtype_header"]:checked ').val(); //0 初诊  1 复诊 2种植一期 3 术后拆线
    var requrl = contextPath+'/KQDS_BLKAct/toZhongZhiYiQi_Add.act?type=<%=type%>';

    if (mtype == '3') {
    	titleStr = "【种植复查】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhi_Suture_Removal_Add.act?type=<%=type%>';
    }

    if (mtype == '4') {
    	titleStr = "【种植二期】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhiErQi_Add.act?type=<%=type%>';
    }

    if (mtype == '5') {
    	titleStr = "【种植修复】";
        requrl = contextPath+'/KQDS_BLKAct/toZhongZhi_XiuFu_Add.act?type=<%=type%>';
    }

    layer.open({
        type: 2,
        title: '新建病历模板' + titleStr,
        maxmin: true,
        shadeClose: true,
        area: ['95%', '98%'],
        content: requrl
    });
});

function refresh() {
    $table.bootstrapTable('refresh', {
        'url': pageurl
    });
}

function del(id) {

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_BLKAct/deleteObj.act?seqId=' + id;
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
</script>
</body>
</html>
