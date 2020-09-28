<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}

	String deptparentId = request.getParameter("deptparentId");
	if(deptparentId == null){
		deptparentId = "";
	}
	String deptId = request.getParameter("deptId");
	if(deptId == null){
		deptId = "";
	}
	String personId = request.getParameter("personId");
	if(personId == null){
		personId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>用户列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/person/list.css"/>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/person/hz_select/hz_select.js"></script>
</head>
<style>
input[type="text"], select {
    width: 95px;
    height: 26px;
    line-height: 26px;
    border: 1px solid #ddd;
    color: #333;
    padding: 0;
    outline: none;
    border-radius: 3px;
    font-size: 13px;
}
.commonText {
    display: inline-block;
    height: 26px;
    line-height: 26px;
    margin-left: 5px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	/* overflow: hidden; */
}
.pagination > .active > a, .pagination > .active > span, .pagination > .active > a:hover, .pagination > .active > span:hover, .pagination > .active > a:focus, .pagination > .active > span:focus{
	background-color: #00A6C0 ;
    border-color: #00A6C0 ;
}
.pagination > li > a, .pagination > li > span{
	color:#00A6C0;
}
</style>
<body>
	<div style="float: left; width: 100%; margin-right: 1%; padding:0 15px;">
		<div class="col-sm-12">
			<div id="toolbar">
				<span class="commonText">姓名：</span>
				<input type="text" name="username" id="username" placeholder="姓名" />
				<span class="commonText">手机号：</span>
				<input type="text" id="sjhm" name="sjhm" placeholder="手机号"/>
				<span class="commonText">建档时间：</span>
                <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
                <span class="commonText">到：</span>
                <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
                <span class="commonText">建档人：</span>
                <input type="hidden" name="createuser" id="createuser"  class="form-control"  value=""/>
				<input type="text"  id="createuserDesc" name="createuserDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"></input>	
				<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clearQuery()">清空</a>
				<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchPerson()">查询</a>
			</div>
		</div>
		<div class="tableBox">
			<table id="table" data-toolbar="#toolbar" data-height="550"></table>
		</div>
	</div>
<script>
var contextPath = "<%=contextPath %>";
var personId = "<%=personId %>";
var deptparentId = "<%=deptparentId %>";
var deptId = "<%=deptId %>";
var pageurl = "<%=contextPath%>/KQDS_UserDocumentAct/selectPage.act";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var static_single_select_userid = null;
var static_single_select_username = null;
var static_single_select_userObj = null;
var static_single_select_userDescObj = null;
// 取父页面的值
var static_select_userid_val = null;
var static_select_username_val = null;

var userRetNameArray = parent.parent.userRetNameArrayMulti; // 多选
if (userRetNameArray && userRetNameArray.length == 2) {
    static_single_select_userid = userRetNameArray[0];
    static_single_select_username = userRetNameArray[1];
    static_single_select_userObj = parent.parent.$("#" + static_single_select_userid);
    static_single_select_userDescObj = parent.parent.$("#" + static_single_select_username);
}
$(function() {
	$(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "buttom-right"
    });
    getPageList();
	$(window).resize(function(){
		setHeight();
	});
});
// 清空
function clearQuery(){
	$("#sjhm").val("");
	$("#username").val("");
	$("#starttime").val("");
	$("#endtime").val("");
	$("#createuserDesc").val("");
	$("#createuser").val("");
}

// 查询按钮
function searchPerson() {
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset,
        createuser:personId,
        starttime:$("#starttime").val(),
        endtime:$("#endtime").val(),
        deptparentId:deptparentId,
        deptId:deptId,
        username:$("#username").val(),
        sjhm: $("#sjhm").val()
    };
    if($("#createuser").val() != "" && $("#createuser").val() != null){
    	temp.createuser = $("#createuser").val();
    }
    return temp;
}
function setHeight(){
	var tableHeight=0;
	tableHeight=getTableHeight();
	$(".fixed-table-container").outerHeight(tableHeight);
}
function getTableHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".bs-bars").outerHeight()-5-$(".pagination-detail").outerHeight();
	return tableHeight;
}

function getPageList() {
	var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" data-toolbar="#toolbar" data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        toolbar: '#toolbar',
        cache: false,
        striped: true,
        sidePagination: "server",
        //服务端处理分页
        onLoadSuccess: function(data) { //加载成功时执行
            var tableList = $('#table').bootstrapTable('getData');
            for (var i = 0; i < tableList.length; i++) {
            }
        },
        onCheck: function (row) {
            //单行最前面的checkbox被选中
        	 setSelectVal(row);
        },
        onUncheck: function (row) {
            //单行最前面的checkbox被取消
        	setNoSelectVal(row);
        },
        onCheckAll: function (rows) {
            //最顶上的checkbox被选中
            setSelectAllVal(rows);
        },
        onUncheckAll: function (rows) {
            //最顶上的checkbox被取消
            setNoSelectAllVal(rows);
        },
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
           	return "<span class='name'>"+value+"</span>" 
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
           	return "<span>"+value+"</span>" 
            }
        },
        {
            title: '手机号码1',
            field: 'phonenumber1',
            align: 'center',
            sortale: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return "<span>"+value+"</span>" ;
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '手机号码2',
            field: 'phonenumber2',
            align: 'center',
            sortale: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return "<span>"+value+"</span>" ;
                } else {
                    return "<span></span>";
                }
            }
        },{
            title: '建档人',
            field: 'createusername',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
           		return "<span>"+value+"</span>" ;
            }
        },{
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function(value){
           		return "<span>"+value+"</span>" ;
            }
        }]
    });
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

    if (parent.leftFrame) {
        parent.leftFrame.refreshNode(static_deptid);
    }
}
</script>

</body>
</html>
