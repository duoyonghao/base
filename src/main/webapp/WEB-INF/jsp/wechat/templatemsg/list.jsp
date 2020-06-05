<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>消息模板管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/dict/dict.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/weixinModule.css" />
<style>
	
	.fixed-table-toolbar{
		overflow:hidden;
	}
	.bs-bars {
		width:100%;
	}
	
	.fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
		margin:5px 0;
	}
	.fixed-table-container thead th .sortable{
		padding-right:8px;	
	}
	#table td span.newstitle{display:inline-block;    width: fit-content;white-space: nowrap;overflow: hidden;text-overflow:ellipsis; }
	.fixed-table-header{
		border-top-left-radius:6px;
		border-top-right-radius:6px;
	}
	.fixed-table-body{
		border-bottom-left-radius:6px;
		border-bottom-right-radius:6px;
		border-bottom:1px solid #ddd;
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
	}
</style>

</head>
<body>
<div class="main">
	<div class="content listWrap">
		<div class="listHd ">
			<span class="title">微信模板</span>
		</div>
		<div >
			<div id="toolbar">
				<button id="btn_export" onclick="getTemplateMsgList()" type="button" class="kqdsSearchBtn" style="float: left;">
				 	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>从微信同步消息模板
				</button>
			</div>
			<div class="tableBox">
				<table id="table" data-toolbar="#toolbar" data-height="500"></table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script>
var pageUrl = "WXTemplateMsgAct/selectPage4Manage.act?1=1";
$(function() {
	//获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    getPageList();
    $(window).resize(function() {
        setHeight();
    });
});

/**
 * 从微信同步消息模板
 */
function getTemplateMsgList() {
    var pageUrl = "WXTemplateMsgAct/get_all_private_template.act?1=1";
    var rtData = getDataFromServer(pageUrl);
    if (rtData) {
        layer.alert('同步更新成功', {
              
            end: function() {
                refresh();
            }
        });
    }
}

function del(seqId, templateId) {
    //询问框
    layer.confirm('确定要删除该消息模板吗？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
        var param = {
            seqId: seqId,
            templateId: templateId
        };
        var url = "WXTemplateMsgAct/del_private_template.act?1=1";
        var rtData = getDataFromServer(url, param);
        if (rtData) {
            layer.alert('删除成功', {
                  
                end: function() {
                    refresh();
                }
            });
        } else {
            layer.alert('删除失败！'  );
        }
    });
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset
    };
    return temp;
}
function setHeight() {
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-50
	});
}
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var params = "";
    location.href = contextPath + "/" + pageUrl + "&flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + params;
}

function getPageList() {
   
    $("#table").bootstrapTable({
        url: contextPath + "/" + pageUrl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        toolbar: '#toolbar',
        striped: true,
        cache: false,
        sidePagination: "server",
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [
        //服务端处理分页
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '主行业',
            field: 'primaryIndustry',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '副行业',
            field: 'deputyIndustry',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '标题',
            field: 'title',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span >'+value+'</span>';
            }
        },
        {
            title: '内容',
            field: 'content',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
            	var showVal = value;
            	if(value.length > 30){
            		showVal = value.substring(0, 30) + "...";
            		showVal = showVal.replace(new RegExp("</br>",'gm'),'');
            		showVal = showVal.replace(new RegExp("<br>",'gm'),'');
            	}
            	return '<span style="width:350px;text-align:left;float:left;position:relative;top:-1px;" class="newstitle" title="'+value+'" >' + showVal + '</a> ';
            }
        },
        {
            title: '示例',
            field: 'example',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
            	var showVal = value;
            	if(value.length > 30){
            		showVal = value.substring(0, 30) + "...";
            		showVal = showVal.replace(new RegExp("</br>",'gm'),'');
            		showVal = showVal.replace(new RegExp("<br>",'gm'),'');
            	}
            	return '<span style="width:350px;text-align:left;float:left;position:relative;top:-1px;" class="newstitle" title="'+value+'" >' + showVal + '</a> ';
            }
        },
        {
            title: '操作',
            field: ' ',
            align: 'center',
            
            formatter: function(value, row, index) {
                var menubutton = "";
                menubutton += '<span><a href="javascript:void(0);" style="color:red;curror:point;" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\',\'' + row.templateId + '\')">删除</a></span> ';
                return menubutton;
            }
        }]
    });
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': contextPath + "/" + pageUrl
    });
}

function updateBaseinfo(openid) {
    var param = {
        openid: openid
    };
    var json = getDataFromServer("WXFansAct/updateBaseInfoByOpenid.act", param);
    if (json && json.retState == 0) {
        layer.alert('信息更新成功', {
              
            end: function() {
                refresh();
            }
        });
    } else {
        layer.alert('信息更新失败' );
    }
}

/**
 * 聊天记录
 */
function talkhislist(openid) {
    layer.open({
        type: 2,
        title: '微信聊天记录',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['80%', '80%'],
        content: contextPath + '/wechat/chat/talkhislist.html?openid=' + openid
    });
}
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "wxmb_scbb") {
        	menubutton1 += '<button id="btn_export" onclick="exportTable()" type="button" class="kqdsCommonBtn" style="float: left;margin-left:5px">生成报表</button>';
        }
    }
    $("#toolbar").append(menubutton1);
    
}

</script>
</body>
</html>
