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
    <title>网电记录</title><!-- 费用添加页面查看 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
	<!-- table数据表 -->
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
	<style>
		.fixed-table-container thead th .sortable{
			padding-right:8px;
		}
	</style>
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
var pageurl = '<%=contextPath%>/KQDS_Net_OrderAct/selectNoPage.act?usercode=' + usercode;

$(function(){
	initTable();
});

//加载表格
function initTable(){
	$("#table").bootstrapTable({
		url:pageurl, 
		dataType: "json",
		singleSelect: false,
		striped: true,
		onLoadSuccess: function(data){  //加载成功时执行
	    },
		columns: [
		          {title: '患者编号',field: 'usercode',align: 'center',valign: 'middle',visible:false,switchable:false},
		          {
		        	  title: '受理日期',
		        	  field: 'acceptdate',
		        	  align: 'center',
		        	  valign: 'middle',
		        	  sortable: true,
		        	  formatter:function(value){
		        		  return '<span>'+value+'</span>';
		        	  }
		        	  
		          },
		          {title: '受理类型',field: 'accepttypename',align: 'center',valign: 'middle',sortable: true},
   			  	  {title: '受理工具',field: 'accepttoolname',align: 'center',valign: 'middle',sortable: true},
   			  	  {title: '咨询项目',field: 'askitemname',align: 'center',valign: 'middle',sortable: true},
				  {title: '咨询人员',field: 'askpersonname',align: 'center',valign: 'middle',sortable: true,
		                  formatter:function(value,row,index){  
		                	  if(value!=""  && value!=null){
		                		  return "<span  class='name'>"+value+"</span>";
		                	  }
		         	      } 
   			 	 },
   			 	 {title: '咨询内容',field: 'askcontent',align: 'center',valign: 'middle',sortable: true,
		   			 	 formatter:function(value,row,index){  
			               	  if(value!=""  && value!=null){
			               		  return "<span  class='time'>"+value+"</span>";
			               	  }
		        	      } 
   			 	 },
   			 	 {title: '预约时间',field: 'ordertime', align: 'center',valign: 'middle',sortable: true,
	                  
	                  formatter:function(value,row,index){  
	                	  if(row.ordertime!="" && row.ordertime!=null){
	                		  var html = row.ordertime.substring(5);
		                    return "<span>"+html+"</span>";  
	                	  }
             	      } 
       		 	 },
       		 	{title: '到诊时间',field: 'guidetime',align: 'center', valign: 'middle',sortable: true,
					    formatter:function(value,row,index){  
					  	  if(value!="" && value!=null){
					            return '<span class="time" title="'+value+'">' + value + '</span>';
					  	  }else{
					  		  return '';
					  	  }
						  }
				},
				{title: '本次上门状态',field: 'doorstatus',align: 'center',valign: 'middle',sortable: true,width:'10%',
				    formatter:function(value,row,index){  
				  	  if(value==1){
							   return '<span style="color:green">已上门</span>';
						  }else{
							   return '<span style="color:red">未上门</span>';
						  }
				     } 
				},
				{title: '本次成交状态',field: 'cjstatus',align: 'center',valign: 'middle',sortable: true,width:'10%',
				    formatter:function(value,row,index){  
				  		 if(value==1){
								 return '<span style="color:green">已成交</span>';
							 }else{
								 return '<span style="color:red">未成交</span>';
							 }
				     } 
				},
       		 	 {title: '预约医生/咨询',field: 'orderpersonname',align: 'center',valign: 'middle',
	                  formatter:function(value,row,index){  
	                	  if(value!=""  && value!=null){
	                		  return "<span class='name'>"+value+"</span>";
	                	  }
	         	      } 
   			  	},
   			  	{title: 'seqId',field: 'seqId',align: 'center',valign: 'middle',visible:false,switchable:false}
		  ]
		  });
}
</script>

</html>
