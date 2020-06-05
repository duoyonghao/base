<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>流转记录</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/kqdsCss/kqdsCommon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/add_cost.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
.top-btn{
	width : 100%;
	height: 35px;
}
.btn-list{
	margin-top: 5px;
}
</style>
</head>
<body>
    <div class="tableBox" style="border-top:1px solid #0054B5">
    	<div class="top-btn">
    		<div class="costHd btn-list">
			<label id="add" onclick="manuBtn.add()" style="cursor:pointer">新增</label>
			<label id="edit" onclick="manuBtn.edit()" style="cursor:pointer">编辑</label>
			<label id="deletes" onclick="manuBtn.deletes()" style="cursor:pointer">删除</label>
		</div>
    	</div>
       <table id="table" class="table-striped table-condensed table-bordered" data-height="418"></table>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript">
var apppath = apppath();
var pageurl = apppath+'/HUDH_YkzzManuAct/findAllManu.act';
var $table = $('#table');
var onclickrow = ""; //选中行对象

$(function() {
	initTable();
	$table.bootstrapTable("resetView",{
		height:$(window).height()-50
	});
	
});
function initTable(){
	$table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        onDblClickCell: function(field, value, row, td) {
            typeBtn.selectChilds();
        },
        columns: [
        {
            title: '排序号',
            field: 'orderno',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },          
        {
            title: '供应商名称',
            field: 'manu_name',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: 'id',
            field: 'id',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '创建人',
            field: 'user_name',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            },
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
    });
}

var manuBtn = {
		add : function (){
			layer.open({
		        type: 2,
		        title: '添加供应商',
		        maxmin: true,
		        shadeClose: true,
		        //点击遮罩关闭层
		        area: ['550px', '250px'],
		        content: apppath + '/HUDH_YkzzViewAct/toSaveManu.act'
		    });
		},
		edit : function (){
			var id = onclickrow.id;
			if(!id) {
				layer.alert("请选择数据");
				return;
			}else {
				layer.open({
			        type: 2,
			        title: '修改供应商',
			        maxmin: true,
			        shadeClose: true,
			        //点击遮罩关闭层
			        area: ['550px', '200px'],
			        content: apppath + '/HUDH_YkzzViewAct/toUpdateManu.act?id=' + id
			    });
			}
		},
		deletes : function (){
			var id = onclickrow.id;
			if(!id) {
				layer.alert("请选择要删除的供应商");
				return;
			}else {
				layer.alert("确认删除？", {
					  closeBtn: 1    // 是否显示关闭按钮
					  ,anim: 1 //动画类型
					  ,icon : 5 //文字前方表情类型
					  ,btn: ['确认','关闭'] //按钮
					  ,yes:function(index){
						  deletes(id);
					  }
					  ,btn2:function(){}
				})
			}
		}
}

function deletes(id){
	$.ajax({
		url: apppath + "/HUDH_YkzzManuAct/deleteYkzzManuById.act",
		type:"POST",
		dataType:"json",
		async:false,
		data:{"id":id},
		success:function(result){
			layer.alert(result.retMsrg, {
	              end: function() {
	            	  window.location.reload(); //刷新页面 */
	              }
	        });
		}
	});
}
</script>
</html>
