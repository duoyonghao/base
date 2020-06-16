<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String createuser = request.getParameter("createuser");
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>查看每个流程具体操作项的备注信息</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <style type="text/css">
    	.assessorBtn{
	    	width:30px;
	    	height:30px;
    		font-size: 30px;
    		border-radius:50%;
    		 padding: 0px!important;
    	}
    	textarea{
    		min-height: 0px;
    	}
    	.table-bordered > tbody > tr > td{
    	    vertical-align: middle;
    	    border:1px solid transparent; 
    	}
    	.table-bordered > thead > tr > th{
		    border: aliceblue;
		}
    </style>
</head>
<body>
	<div>
		<div class="tableBox" style="overflow: hidden;border-radius:8px;border-top-left-radius: 6px;border-top-right-radius: 6px;border-left: 1px solid #ddd;border-right: 1px solid #ddd;border-bottom:1px solid #ddd;">
	          <table id="assessor_table" class="table-striped table-condensed table-bordered"></table>
	   	</div> 
	</div>
   
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var createuser = '<%=createuser%>';
// console.log(createuser+"----------------------createuser");
var pageurl = '<%=contextPath%>/HUDH_LcljCheckRecordAct/getCheckRecord.act';
$(function() {		
	inittable();
});

	function queryParams(params) {
	    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	    		createuser:createuser,
	    };
	    return temp;
	}

	function inittable(){
	     $("#assessor_table").attr("data-height", $(window).height() - 55);
		 $("#assessor_table").bootstrapTable({
		        url: pageurl,
		        dataType: "json",
		        contentType : "application/x-www-form-urlencoded",   //必须有
		        pagination: true,//是否显示分页（*）
		        sidePagination : 'client',//server:服务器端分页|client：前端分页
		        queryParams: queryParams,
		        pageSize: 10,
		        pageList : [10, 15],
		        paginationShowPageGo: true,
		        onLoadSuccess: function(data) { //加载成功时执行\
//  		        	console.log(JSON.stringify(data)+"-------data");
		        },
		      columns: [		        
		        {
		        	title : '序号',
		        	align: "center",
		        	width: 40,
		        	formatter: function (value, row, index) {
// 		        		return index + 1; 
		        		var pageSize = $('#assessor_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
		            	var pageNumber = $('#assessor_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
		            	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		        	}
		        },
		        {
		            title: '患者编号',
		            field: 'blcode',
		            align: 'center',
		            sortable: true,
		            width: 80,
		            formatter: function(value, row, index) {
		                if (value) {
		                	return '<span class="">' + value + '</span>';
		                }
		            }
		        },
		        {
		            title: '患者名称',
		            field: 'username',
		            align: 'center',
		            width: 80,
		            formatter: function(value, row, index) {
		                if (value) {
		                	return '<span class="">' + value + '</span>';
		                }
		            }
		        },
		        {
		            title: '节点名称',
		            field: 'name',
		            align: 'center',
		            width: 80,
		            formatter: function(value, row, index) {
		                if (value) {
		                	return '<span class="">' + value + '</span>';
		                }
		            }
		        },
		        {
		            title: '手术类型',
		            field: 'flowname',
		            align: 'center',
		            width: 100,
		            formatter: function(value, row, index) {
		                if (value) {
		                	return '<span class="">' + value + '</span>';
		                }
		            }
		        },
		        {
		            title: '节点时间',
		            field: 'nodetime',
		            align: 'center',
		            width: 100,
		            sortable: true,
		            formatter: function(value, row, index) {
		                if (value) {
		                	return '<span class="">' + value + '</span>';
		                }
		            }
		        },
		        {
		            title: '挂号时间',
		            field: 'regtime',
		            align: 'center',
		            width: 100,
		            sortable: true,
		            editable: true,
		            formatter: function(value, row, index) {
		            	return '<span class="">' + value + '</span>';
		            }
		        },
		        {
		            title: '预约下一次时间',
		            field: 'nexttime',
		            align: 'center',
		            width: 60,
		            sortable: true,
		            formatter: function(value, row, index) {
		            	 return '<span class="">' + value + '</span>';
		            }
		        },
		        {
		            title: '就诊分类',
		            field: 'recesortname',
		            align: 'center',
		            width: 100,
		            formatter: function(value, row, index) {
		            	 return '<span class="">' + value + '</span>';
		            }
		        },
		        {
		            title: '挂号分类',
		            field: 'regsortname',
		            align: 'center',
		            width: 100,
		            formatter: function(value, row, index) {
		            	 return '<span class="">' + value + '</span>';
		            }
		        },
		        {
		            title: '挂号医生',
		            field: 'regdoctorname',
		            align: 'center',
		            width: 100,
		            formatter: function(value, row, index) {
		            	  return '<span class="">' + value + '</span>';
		            }
		        },
		        {
		            title: '提交人',
		            field: 'createuser',
		            align: 'center',
		            width: 100,
		            formatter: function(value, row, index) {
		            	  return '<span class="">' + value + '</span>';
		            }
		        },
		        {
		            title: '提交时间',
		            field: 'updatetime',
		            align: 'center',
		            width: 100,
		            formatter: function(value, row, index) {
		            	  return '<span class="">' + value + '</span>';
		            }
		        },
		        {
		            title: '备注',
		            field: 'remark',
		            align: 'center',
		            width: 200,
		            clickEdit: true,
		            formatter: function(value, row, index) {
// 		            	if(value==""){
// 		            		 return '<textarea name="remark" rows="1" cols="20">' + value + '</textarea>';
// 		            	}else{
		            		 return '<div style="width:200px;">' + value + '</div>';
// 		            	}		            			            	
		            }
		        },
		        {
		            title: '状态',
		            field: 'status',
		            align: 'center',
		            width: 90,
		            sortable: true,
		            formatter: function(value, row, index) {
// 		            	console.log(JSON.stringify(row.lcljid)+"--------row");
		            	if (row.status == "0") {
		            		return '<span style="color:#00a6c0;">未审核<span>'
		            	}else if(row.status == "1"){
		            		return '<span style="color:green;">审核通过<span>'
		            	}else if(row.status == "2"){
		            		return '<span style="color:red;">未通过<span>'
		            	}else if(row.status == "3") {
		            		return '<span style="color:Goldenrod;">退回操作审核记录<span>'
						}
		            	
		            }
		        }]
		    }).on('click-row.bs.table',
		    function(e, row, element) {
		        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
		        $(element).addClass('success'); //添加当前选中的 success样式用于区别
		        var index = $('#assessor_table').find('tr.success').data('index'); //获得选中的行
		        onclickrowOobj = $('#assessor_table').bootstrapTable('getData')[index];
		    }).on('dbl-click-row.bs.table', function (e, row, ele,field) {});
		    
	}	
		
</script>
</html>
