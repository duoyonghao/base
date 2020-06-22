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
			<label id="add" onclick="typeBtn.add()" style="cursor:pointer">新增</label>
			<label id="edit" onclick="typeBtn.edit()" style="cursor:pointer">编辑</label>
			<label id="deletes" onclick="typeBtn.deletes()" style="cursor:pointer">删除</label>
			<label id="selectChilds" onclick="typeBtn.reBack()" style="cursor:pointer">返回上一级</label>
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
var pageurl = apppath+'/HUDH_YkzzTypeAct/findChildTypesByParentId.act?id=root';
var $table = $('#table');
var onclickrow = ""; //选中行对象

var perId = "root"; //记录当前页面展示父类型
var perName = "根节点";
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
        	if(perId != "root") { //当前列表父分类不是root说明当前分类为二级分类，当前支持到二级分类
        		layer.alert("已是末级分类");
        		return;
        	}
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
            title: '分类名称',
            field: 'type_name',
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
function refresh(url) {
	onclickrow = "";
    $table.bootstrapTable('refresh', {
        'url': url
    });

}

var typeBtn = {
		add : function (){
			layer.open({
		        type: 2,
		        title: '添加类别',
		        maxmin: true,
		        shadeClose: true,
		        //点击遮罩关闭层
		        area: ['550px', '250px'],
		        content: apppath + '/HUDH_YkzzViewAct/toSaveType.act?perId=' + perId+'&perName=' + perName
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
			        title: '修改类别',
			        maxmin: true,
			        shadeClose: true,
			        //点击遮罩关闭层
			        area: ['550px', '200px'],
			        content: apppath + '/HUDH_YkzzViewAct/toUpdateType.act?id=' + id +"&perId=" + perId+'&perName=' + perName
			    });
			}
		},
		deletes : function (){
			var id = onclickrow.id;
			if(!id) {
				layer.alert("请选择要删除的类别");
				return;
			}else {
				//判断当前节点下是否存在子节点
				$.ajax({
					url: apppath + "/HUDH_YkzzTypeAct/findChildTypesByParentId.act",
					type:"POST",
					dataType:"json",
					async:false,
					data:{"id":id},
					success:function(result){
						if(result.length > 0) {
							layer.alert("该分类下存在子分类");
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
				});
			}
		},
		selectChilds : function (){
			var id = onclickrow.id;
			if(!id) {
				layer.alert("请选择类别");
				return;
			}
			perId = onclickrow.id;
			perName = onclickrow.type_name;
			parentid = onclickrow.id == "0"?"-1":onclickrow.parentid;
			refresh(apppath + "/HUDH_YkzzTypeAct/findChildTypesByParentId.act?id="+id);
		},
		reBack : function (){
			//返回上一级回去当前页面父节点的ID及父节点
			if(perId == "root") {
				layer.alert("当前已处于根节点");
				return;
			}
			$.ajax({
				url: apppath + "/HUDH_YkzzTypeAct/reBack.act",
				type:"POST",
				dataType:"json",
				async:false,
				data:{"id":perId},
				success:function(result){
					perId = result.perId;
					perName = result.perName;
					refresh(apppath + "/HUDH_YkzzTypeAct/findChildTypesByParentId.act?id="+perId);
				}
			});
		}
}

function deletes(id){
	$.ajax({
		url: apppath + "/HUDH_YkzzTypeAct/deleteYkzzTypeById.act",
		type:"POST",
		dataType:"json",
		async:false,
		data:{"id":id},
		success:function(result){
			layer.alert(result.retMsrg, {
	              end: function() {
	            	  refresh(apppath + "/HUDH_YkzzTypeAct/findChildTypesByParentId.act?id="+perId);
	              }
	        });
		}
	});
}

function findChildType(id){
	$.ajax({
		url: apppath + "/HUDH_YkzzTypeAct/findChildTypesByParentId.act",
		type:"POST",
		dataType:"json",
		async:false,
		data:{"id":id},
		success:function(result){
			console.log(JSON.stringify(result));
			return result;
		}
	});
}
</script>
</html>
