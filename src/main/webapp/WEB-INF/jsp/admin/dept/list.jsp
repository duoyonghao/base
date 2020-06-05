<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}

	String deptId = request.getParameter("deptId");
	if(deptId == null){
		deptId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>部门列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
</head>
<body>
	<div style="float: left; width: 100%; margin-right: 1%;">
		<div id="toolbar">
			<button id="newAdd" class="btn btn-danger">
				<i class="glyphicon glyphicon-plus"></i> 新增
			</button>
			<button id="cleanPassword" class="btn btn-danger"  onclick="clearPassword();">
				<i class="glyphicon glyphicon-plus"></i> 清空密码
			</button>
			<button id="deletePerson" class="btn btn-danger" onclick="deletePerson();">
				<i class="glyphicon glyphicon-plus"></i> 删除
			</button>
		</div>
		<input type="hidden" id="deptId" value=""></input>
		<table id="table" data-toolbar="#toolbar" data-height="500"></table>
	</div>
<script>
var contextPath = "<%=contextPath %>";
var static_deptid = "<%=deptId %>";
var $table = $('#table');
var pageurl = "<%=contextPath%>/YZDeptAct/selectPage.act";
var static_deleteurl = 'YZDeptAct/delete.act?1=1';
var static_clearPasswordUrl = 'YZDeptAct/clearPassword.act?1=1';

$(function() {
    getPageList();

    // 当前门诊主键
    $("#deptId").val(static_deptid);
});

//获取选中行的usercode
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//复选框
function stateFormatter(value, row, index) {
    if (row.userId === 'admin') {
        return {
            disabled: true,
            checked: false
        };
    }
    if (row.id === 0) {
        return {
            disabled: true,
            checked: true
        }
    }
    return value;
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset,
        deptId: static_deptid
    };
    return temp;
}
function getPageList() {
    $table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        toolbar: '#toolbar',
        striped: true,
        cache: false,
        sidePagination: "server",
        //服务端处理分页
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
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
            title: '部门名称',
            field: 'userId',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '真实姓名',
            field: 'userName',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '部门',
            field: 'deptidname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '角色',
            field: 'userprivname',
            align: 'center',
            sortable: true,
            editable: true,
        },
        {
            title: '是否离职',
            field: 'isLeave',
            align: 'center',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "否";
                }
                if (value == "1") {
                    return "是";
                }
            }
        }]
    });
}
function refresh() {
    $table.bootstrapTable('refresh', {
        'url': pageurl
    });

    if (parent.leftFrame) {
        parent.leftFrame.refreshNode(static_deptid);
    }
}

function clearPassword() {
    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选要清空密码的部门' );
        return false;
    }

    // 获取要删除的部门id，以逗号分隔
    var seqIds = "";
    for (var i = 0; i < selectList.length; i++) {
        seqIds += selectList[i].seqId + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);

    //询问框
    layer.confirm('确定清空密码？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
        var reqUrl = static_clearPasswordUrl + "&seqId=" + seqIds;
        var serverData = getDataFromServer(reqUrl);
        if (serverData) {
            layer.alert('清空成功', {
                  
                end: function() {
                    refresh();
                }
            });

        }
    });
}

function deletePerson(id) {
    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选要删除的部门' );
        return false;
    }

    // 获取要删除的部门id，以逗号分隔
    var seqIds = "";
    for (var i = 0; i < selectList.length; i++) {
        seqIds += selectList[i].seqId + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var reqUrl = static_deleteurl + "&seqId=" + seqIds;
        var serverData = getDataFromServer(reqUrl);
        if (serverData) {
            layer.alert('删除成功', {
                  
                end: function() {
                    refresh();
                }
            });
        }
    });
}

//弹出一个iframe层
$('#newAdd').on('click',
function() {
    layer.open({
        type: 2,
        title: '新建部门',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: contextPath +'/YZDeptAct/toNewAdd.act?deptId=' + static_deptid
    });
});
</script>
</body>
</html>
