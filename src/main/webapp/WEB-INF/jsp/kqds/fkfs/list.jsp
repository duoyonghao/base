<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>付款方式管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/dict/dict.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/person/list.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
</head>
<body>
<div class="content">
	<div style="float: left; width: 100%; margin-right: 1%;">
		<div id="toolbar">
			<button id="jinyong" class="kqdsSearchBtn" onclick="updateFlagBySeqIds(1);">
				<i class="glyphicon glyphicon-remove"></i> 禁用
			</button>
			<button id="qiyong" class="kqdsSearchBtn" onclick="updateFlagBySeqIds(0);">
				<i class="glyphicon glyphicon-ok"></i> 启用
			</button>
		</div>
		<input type="hidden" id="parentCode" value=""></input>
		<div class="tableBox">
			<table id="table" data-toolbar="#toolbar" data-height="500"></table>
		</div>
	</div>
</div>
<script>
var contextPath = "<%=contextPath %>";
var pageurl = contextPath+"/YZFkfsAct/selectNoPage.act";
var static_updateurl = 'YZFkfsAct/updateFlagBySeqIds.act?1=1';
$(function() {
    getPageList();
    $(window).resize(function(){
		setHeight();
	});
});
//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
  	    function(row) {
  	        return row;
	});
}
//复选框
function stateFormatter(value, row, index) {
    return {
        disabled: false,
        checked: false
    }
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset
    };
    return temp;
}
function setHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".bs-bars").outerHeight()-50-$(".pagination-detail").outerHeight();
	$(".fixed-table-container").outerHeight(tableHeight);
}
function getTableHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".bs-bars").outerHeight()-10-$(".pagination-detail").outerHeight();
	return tableHeight;
}
function getPageList() {
	var tableHeight = getTableHeight();
	$(".tableBox").html('<table id="table" data-toolbar="#toolbar" data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
      //  pagination: true,
        singleSelect: false,
        toolbar: '#toolbar',
        striped: true,
        cache: false,
       // sidePagination: "server",
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
        },
        {
            title: '排序号',
            field: 'orderno',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },{
            title: '付款方式',
            field: 'payname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '付款类型',
            field: 'ismoney',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "不计实收";
                }
                if (value == "1") {
                    return "计入实收";
                }
                if (value == "2") {
                    return "预交金";
                }
            }
        },
        {
            title: '状态',
            field: 'useflag',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "<span style='color:#00A6C0;'>启用</span>";
                }
                if (value == "1") {
                    return "<span style='color:red;'>禁用</span>";
                }
            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
            	var e = '';
            	if(row.noedit != '1'){
                    e = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
            	}
                return e; 
            }
        }
       ]
    });
}

function refreshTable() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}

/**
 *@param flag 0 启用  1禁用
 */
function updateFlagBySeqIds(flag) {
    var opername = null;
    if ("0" == flag) {
        opername = "启用"
    }
    if ("1" == flag) {
        opername = "禁用"
    }

    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选要' + opername + '的记录', {
        });
        return false;
    }

    // 获取要删除的用户id，以逗号分隔
    var seqIds = "";
    for (var i = 0; i < selectList.length; i++) {
        seqIds += selectList[i].seqId + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);

    //询问框
    layer.confirm('确定' + opername + '？', {
        btn: [opername, '取消'] //按钮
    },
    function() {
        var reqUrl = static_updateurl + "&seqId=" + seqIds + "&flag=" + flag;
        var serverData = getDataFromServer(reqUrl);
        if (serverData) {
            layer.alert(opername + '成功', {
                end: function() {
                    refreshTable();
                }
            });
        }
    });
}

function edit(id) {
    var index = layer.open({
        type: 2,
        title: '修改付款方式',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: ['700px', '380px'],
        content: contextPath+'/YZFkfsAct/toEdit.act?seqId=' + id
    });
}
</script>
</body>
</html>
