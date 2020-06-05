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
    <title>流转记录</title><!-- 弹框 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
	<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
	
</head>
<body>
    <div class="tableBox">
       <table id="table" class="table-striped table-condensed table-bordered" data-height="318"></table>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var usercode= "<%=usercode %>";
var pageurl = '<%=contextPath%>/KQDS_BCJLAct/selectPageLz.act';
$(function(){
	getlzjl();
});
function getlzjl(){
	//流转记录
	var url = pageurl+'?usercode='+usercode; 
	    $('#table').bootstrapTable({
	    	url:url, 
			dataType: "json",
			cache: false, 
			striped: true,
			onLoadSuccess: function(data){  //加载成功时执行
			    },
			columns: [
			          {title: 'seqId',field: 'seqId',align: 'center',valign: 'middle',visible:false,switchable:false}, 
			          {title: '时间',field: 'createtime',align: 'center',valign: 'middle'}, 
			          {title: '病人编号',field: 'usercode',align: 'center',valign: 'middle',visible:false,switchable:false},
			          {title: '姓名',field: 'username',align: 'center',valign: 'middle'}, 
			          {title: '状态',field: 'bcmc',align: 'center',valign: 'middle'}, 
			          {title: '就诊分类',field: 'recesort',align: 'center',valign: 'middle',
			        	  formatter:function(value,row,index){
		                	  if(row.bcmc=="挂号"){
			                	  return value;
		                	  }
	                	  } 
			          }, 
			          {title: '咨询',field: 'askperson',align: 'center',valign: 'middle'}, 
			          {title: '医生',field: 'doctor',align: 'center',valign: 'middle'}, 
			          {title: '操作人',field: 'createusername',align: 'center',valign: 'middle'}
			            ]
			  });
}
</script>

</html>
