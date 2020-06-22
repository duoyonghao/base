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
	
	String seqId = request.getParameter("seqId");
	if(seqId == null){
		seqId = "";
	}
	//0 或空 不显示离职人员  1显示离职人员
	String showleave = request.getParameter("showleave");
	if (showleave == null) {
		showleave = "0";
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>用户列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/person/single_select.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<style>
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
</style>
</head>
<body>
	<div style="float: left; width: 100%; margin-right: 1%;">
		<input type="hidden" id="deptId" value=""></input>
		<table id="table" data-toolbar="#toolbar" data-height="390"></table>
	</div>
<script>
var contextPath = "<%=contextPath %>";
var static_deptid = "<%=deptId %>";
var static_seqId = "<%=seqId %>";
var showleave = "<%=showleave %>";
var $table = $('#table');
var pageurl = "<%=contextPath%>/YZPersonAct/selectNoPage.act";
$(function() {
    getPageList();
    // 当前门诊主键
    $("#deptId").val(static_deptid);
});

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        deptId: static_deptid,
        seqId: static_seqId,
        showleave:showleave
    };
    return temp;
}
function getPageList() {
    $table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
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
            title: '用户名',
            field: 'userId',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        /* {
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
        }
        ,{
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
        } */
        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        // 调用父页面赋值        
        parent.setSelectVal(row.seqId, row.userId);
    });
}
</script>

</body>
</html>
