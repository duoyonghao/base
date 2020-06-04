<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}

	String menuid = request.getParameter("menuId");
	if(menuid == null){
		menuid = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/button/buttonList.css"/>
<title>按钮管理</title>
</head>
<body>
	<div class="content">
		<div style="float: left; width: 99%; margin-right: 1%;">
			<div id="toolbar" style="display: none;">
				<button id="newAdd" class="kqdsSearchBtn bigBtn">
					<i class="glyphicon glyphicon-plus"></i> 新增
				</button>
			</div>
			<input type="hidden" id="menuid"></input>
			<div class="tableBox">
				<table id="table" data-toolbar="#toolbar" data-height="400"></table>
			</div>
		</div>
	</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script>
var contextPath = "<%=contextPath %>";
var static_menuid = "<%=menuid %>";
var $table = $('#table');
var pageurl = "<%=contextPath%>/YZButtonAct/selectList.act";
$(function() {
    if (static_menuid) {
        $("#toolbar").css("display", "block");
    }
    $("#menuid").val(static_menuid);
    getButton();
    $(window).resize(function(){
    	setHeight();
    });
});

function queryParams(params) {
    var temp = {
        menuid: $('#menuid').val()
    };
    return temp;
}
function setHeight(){/* 重置页面时设置table数据表的高度 */
	var tableHeight=getTableHeight()-20;
	$(".fixed-table-container").outerHeight(tableHeight);
}
/*计算table高度  */
function getTableHeight(){
	var tableHeight=$(window).height()-$(".bs-bars").outerHeight()-10;
	return tableHeight;
}
function getButton() {
	/* 初始化时计算table需要的高度 */
	var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" data-toolbar="#toolbar" data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        singleSelect: false,
        toolbar: '#toolbar',
        striped: true,
        cache: false,
        //服务端处理分页
        columns: [{
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '名称',
            field: 'name',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '权限标识',
            field: 'qxName',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '备注',
            field: 'bz',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '关联表门诊',
            field: 'deptname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true
        },
        {
            title: '关联部门',
            field: 'relatedDept ',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                var e = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="add(\'' + row.qxName + '\')">添加</a> ';
                var d = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="updatedept(\''+row.deptprivid + '\')">修改</a> ';
                return e + d;
            }
        },
        {
            title: '部门名称',
            field: 'dept_name',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '关联表id',
            field: 'deptprivid',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            visible: false
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                var e = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">修改</a> ';
                var d = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId+','+row.deptprivid + '\')">删除</a> ';
                return e + d;
            }
        }]
    });
}
function refresh() {
	$("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}
function add(name) {
    var index = layer.open({
        type: 2,
        title: '关联部门',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath+'/YZButtonAct/toDept.act?qxname=' + name
    });
}
function updatedept(id) {
    var index = layer.open({
        type: 2,
        title: '关联部门',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath+'/YZButtonAct/toDeptId.act?deptprivid=' + id
    });
}

function edit(id) {
    var index = layer.open({
        type: 2,
        title: '修改按钮',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath+'/YZButtonAct/toEdit.act?seqId=' + id
    });
}

function del(id) {
	var ids=id.split(",");
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = "<%=contextPath%>/YZButtonAct/delete.act?seqId=" + ids[0]+"&deptprivid="+ids[1];
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.ajax({
            type: 'POST',
            url: url,
            dataType: "json",
            success: function(data) {
                if (data.retState == "0") {
                    layer.alert('删除成功'  );
                    refresh();
                }
            }
        });
    });
}

//弹出一个iframe层
$('#newAdd').on('click',
function() {
    layer.open({
        type: 2,
        title: '新增按钮',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath+'/YZButtonAct/toNewAdd.act?menuId=' + static_menuid 
    });
});
</script>

</body>
</html>
