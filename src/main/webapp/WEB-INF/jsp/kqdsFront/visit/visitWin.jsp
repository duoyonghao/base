<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	  String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>回访记录</title>
<!-- 费用添加页面， 可进入  -->
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/static/css/kqdsFront/bingli.css" />
	<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
</style>
</head>
<body>
	<div id="centerWrap">
		<div class="tableBox">
			<table id="table"
				class="table-striped table-condensed table-bordered"
				data-height="318"></table>
		</div>
	</div>
</body>

<script type="text/javascript"
	src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var usercode= "<%=usercode %>";
var pageurl = '<%=contextPath%>/KQDS_VisitAct/selectListNotByPersonId.act?usercode=' + usercode;
var $table = $('#table');

var onclickrow = "";
$(function () {
	var tableheaderstr = "";
	if(usercode == null || usercode == ""){
		layer.alert('请选择患者', {
		});
	}else{
		inittable();
	}
});

//加载表格
function inittable(){
	$table.bootstrapTable({
		url:pageurl, 
		dataType: "json",
		cache: false, 
		striped: true,
	 	onLoadSuccess: function(data){  //加载成功时执行
	    },
		columns: [
					    {title: '回访分类',field: 'hfflname',align: 'center',valign: 'middle',sortable: true},
	              		{title: '回访要点',field: 'hfyd',align: 'center',valign: 'middle',sortable: true},
						{title: '回访状态',field: 'status',align: 'center',valign: 'middle',sortable: true,
			                  formatter:function(value,row,index){
			                	  var htmlstr = "";
			                	  if(row.status == "0"){
			                		  htmlstr = "<span style='color:red'>未回访</span>";
			                	  }else if(row.status == "1"){
			                		  htmlstr = "<span style='color:green'>已回访</span>";
			                	  }
			                	  return htmlstr;
		                	  }
	             		},
 						{title: '回访结果',field: 'hfresult',align: 'center', valign: 'middle',sortable: true},
						{title: '患者姓名',field: 'username',align: 'center',valign: 'middle',sortable: true},
						{title: '满意度',field: 'mydname',align: 'center',valign: 'middle',sortable: true},
		              	{title: '回访人员',field: 'visitorname',align: 'center',valign: 'middle',sortable: true,
			                   formatter:function(value,row,index){  
			                	  if(value){
 	 			                		  return "<span  class='name'>"+value+"</span>";
			                	  }
		                	  }
	              		}
		          ]
		    });
	
}
</script>
</html>
