<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
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
	<title>角色管理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
	<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
	<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
	<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
	<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
	<style>
		.fixed-table-header{
			color: #fff;
			background: #00a6c0 ;
		}

	</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<section class="content">
	<div>
		<div id="toolbar">
			<button id="parentIframe" class="kqdsSearchBtn bigBtn" onclick="addPriv();">
				<i class="glyphicon glyphicon-plus" ></i> 新增
			</button>
			<span class="commonText">角色：</span>
			<input type="text" name="privname" id="privname" placeholder="角色名" />
			<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clearQuery()">清空</a>
			<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="searchPerson()">查询</a>
		</div>
		<div class="tableBox">
			<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
		</div>
	</div>
</section>
<script>
    var contextPath = "<%=contextPath %>";
    var organization = "<%=organization %>";
    var $table = $('#table');
    var pageurl = contextPath + "/YZPrivAct/selectPage.act";
    var static_deleteurl = 'YZPrivAct/delete.act?1=1';
    $(function() {
        getPageList();
        $(window).resize(function(){
            setHeight();
        });
    });
    // 清空
    function clearQuery(){
        $("#privname").val("");
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
            organization:organization,
            search:$("#privname").val(),
        };
        return temp;
    }
    function setHeight(){
        var tableHeight=0;
        tableHeight=$(window).height()-$(".clearfix").outerHeight()-30;
        $(".fixed-table-container").outerHeight(tableHeight);
    }
    function getTableHeight(){
        var tableHeight=0;
        tableHeight=$(window).height()-$(".clearfix").outerHeight()-10;
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
            striped: true,
            cache: false,
            sidePagination: "server",
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
                    title: '排序号',
                    field: 'privNo',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    editable: true,
                },
                {
                    title: '角色名称',
                    field: 'privName',
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
                        if (row.seqId != "1") {
                            menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
                            menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deletePriv(\'' + row.seqId + '\')"><span style="color:red">删除</span></a> ';
                        }
                        menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="setPriv(\'' + row.seqId + '\')">设置权限</a> ';
                        menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="copyPriv(\'' + row.seqId + '\')">拷贝</a> ';
                        return menubutton;
                    }
                }]
        });
    }
    function refresh() {
        $("#table").bootstrapTable('refresh', {
            'url': pageurl
        });
    }

    function edit(seqId) {
        window.location.href = contextPath + "/YZPrivAct/toEdit.act?seqId=" + seqId+"&organization=" + organization
    }

    function setPriv(seqId) {
        window.location.href = contextPath + "/YZPrivAct/toSetPriv.act?priv_seqId=" + seqId+"&organization="+organization;
    }

    function copyPriv(seqId) {
        layer.open({
            type: 2,
            title: '拷贝角色',
            maxmin: true,
            shadeClose: true,
            //点击遮罩关闭层
            area: ['500px', '300px'],
            content: contextPath + '/YZPrivAct/toCopy.act?seqId=' + seqId
        });
    }
    function addPriv() {
        layer.open({
            type: 2,
            title: '新建角色',
            maxmin: true,
            shadeClose: true,
            //点击遮罩关闭层
            area: ['500px', '450px'],
            content: contextPath + '/YZPrivAct/toNewAdd.act?organization=' + organization
        });
    }
    function deletePriv(id) {
        layer.confirm('确定删除？', {
                btn: ['删除', '放弃'] //按钮
            },
            function() {
                var reqUrl = static_deleteurl + "&seqId=" + id;
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
</script>
</body>
</html>
