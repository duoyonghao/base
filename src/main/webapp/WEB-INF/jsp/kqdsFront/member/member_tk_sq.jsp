<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	String ispush = request.getParameter("ispush");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>退款申请</title><!-- 接待中心  下方退款按钮进入 退款列表， 点击 退款 进入申请页面  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/member/memberRefundRequest.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<style>
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
	
</style>
</head>
<body>
	<div class="refundRequestContanier"> <!-- refundRequestContanier 退款申请样式 -->
		<div class="headerDiv">	
			<span class="commonSpan">开卡时间</span>
			<!--.unitsDate本身无样式 时间验证有关系  -->
			<input class="unitsDate whiteInp"  type="text" placeholder="开始日期" id="starttime">
            <span class="commonSpan">到</span>
            <!--.unitsDate本身无样式 时间验证有关系  -->
            <input class="unitsDate whiteInp" type="text"  placeholder="结束日期" id="endtime">
            <!--commonSpan通用文本样式     .fuzzyQuerySpan提供间距的样式-->
			<span class="commonSpan fuzzyQuerySpan">模糊查询</span>
			<input class="fuzzyQuery" type="text" id="searchValue" name="searchValue" value>
			
			
			<a id="clean" onclick="clean()" href="javascript:void(0);" class="kqdsCommonBtn">清 空</a>
			<a id="searchHzda" href="javascript:void(0);" class="kqdsSearchBtn">查 询</a>
		</div>
		
		<div class="sectionDiv">
			<span class="titleText">查询清单</span>
			 <div class="tableBox"> 
			    <table id="table" class="table-striped table-condensed table-bordered" data-height="330"></table> 
			   </div> 
		</div>
	</div>	
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

<script type="text/javascript">

var ispush = "<%=ispush%>";
var contextPath = "<%=contextPath%>";
var personrole = "<%=person.getUserPriv()%>";
var pageurl = '<%=contextPath%>/KQDS_MemberAct/selectList.act';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var onclickrowOobj = "";
$(function(){
 	//当前日期
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	//时间选择
	$("input.unitsDate").datetimepicker({
		language:  'zh-CN',  
		minView:2,
	    autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "buttom-right"
	});
	//绑定两个时间选择框的chage时间
	$("#starttime,#endtime").change(function(){
		timeCompartAndFz("starttime","endtime");
    });
	getlist();
});
$('#clean').on('click',
function() {
	$("#starttime").val("");  //---默认只查当天的
    $("#endtime").val("");
    $("#searchValue").val("");
});
$('#searchHzda').on('click', function(){
	 $('#table').bootstrapTable('refresh',{'url':pageurl}); 
});
function queryParams(params) {
    var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		queryInput:$('#searchValue').val(),
    		starttime:$('#starttime').val(),
    		endtime:$('#endtime').val(),
    		pagenum:3
    };
    return temp;
}
function getlist(){
	$("#table").bootstrapTable({
			url: pageurl,
	        dataType: "json",
	        cache: false,
	        striped: true,
	        queryParams: queryParams,
	        pageSize: 25,
	        pageList : [10, 15, 20, 25],//可以选择每页大小
	        paginationShowPageGo: true,
	        sidePagination: "server",//后端分页 
	        onDblClickCell: function(field, value, row, td) {
	            //双击事件 调用提交回访结果方法 
	            onclickrowOobj = row;
	            goSq(row.seqId);
	        },
	        onLoadSuccess:function(data){
	        	//console.log("-=-=-"+JSON.stringify(data));
	        	setHeight();
	        },
	        columns: [
			{
			    title: '操作',
			    field: ' ',
			    align: 'center',
			    
			    sortable: true,
			    formatter: function(value, row, index) {
			        return '<span href="javascript:void(0);" style="color:#0E7CC9;cursor:pointer;" onclick="goSq(\''+row.seqId+'\')">申请退款</span>';
			    }
			},
	        {
	            title: '患者编号',
	            field: 'usercode',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	            title: '患者姓名',
	            field: 'username',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="name" title="' + value + '">' + value + '</span>';
	            }
	        },
			{title: '咨询',field: 'askpersonname',align: 'center',sortable: true,
				formatter:function(value,row,index){  
	                return '<span class="name">' + value + '</span>'; 
	        	}
			},
	        {
	            title: '会员卡号',
	            field: 'memberno',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	        	title: '会员卡类型',
	        	field: 'memberlevel',
	        	align: 'center',
	        	sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }},
	        {
	            title: '折扣(百分比)',
	            field: 'discount',
	            align: 'center',
	            
	            sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '会员卡状态',
	            field: 'memberstatus',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value == "未激活") {
	                    return '<span style="color:green;">未激活</span>';
	                } else if (value == "正常") {
	                    return '<span style="color:red;">正常</span>';
	                } else if (value == "已挂失") {
	                    return '<span style="color:red;">已挂失</span>';
	                } else if (value == "已补办") {
	                    return '<span style="color:red;">已补办</span>';
	                }
	            }
	        },
	        {
	            title: '充值余额',
	            field: 'money',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span class="money">'+value+'</span>';
	                } else {
	                    return "<span>0.0</span>";
	                }
	            }
	        },
	        {
	            title: '赠送余额',
	            field: 'givemoney',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value != "" && value != null) {
	                    return '<span class="money">'+value+'</span>';
	                } else {
	                    return "<span>0.0</span>";
	                }
	            }
	        },
	        {
	            title: '余额小计',
	            field: 'totalmoney',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="money" title="' + value + '">' + value.toFixed(2) + '</span>';
	            }
	        },
	        {title: '患者来源',field: 'devchannel',align: 'center',sortable: true,
				formatter:function(value,row,index){  
	                return '<span class="source">' + value + '</span>';
	        	}
			},
			{title: '来源子分类',field: 'nexttype',align: 'center',sortable: true,
				formatter:function(value,row,index){  
	                return '<span class="source">' + value + '</span>';
	        	}
			},
	        {
	            title: '办卡时间',
	            field: 'createtime',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                return '<span class="time" title="' + value + '">' + value + '</span>';
	            }
	        },
	        {
	            title: '备注',
	            field: 'remark',
	            align: 'center',
	            
	            sortable: true,
	            formatter: function(value, row, index) {
	                if (value) {
	                    return '<span class="remark" title="' + value + '">' + value + '</span>';
	                } else {
	                    return '';
	                }
	            }
	        }]
	    }).on('click-row.bs.table', function(e, row, element) {
	         $('.success').removeClass('success'); //去除之前选中的行的，选中样式
	         $(element).addClass('success'); //添加当前选中的 success样式用于区别
	         var index = $('#table').find('tr.success').data('index'); //获得选中的行
	         onclickrowOobj = $('#table').bootstrapTable('getData')[index];
	     }); 
}
function goSq(seqIdLocal){
	 layer.open({
         type: 2,
         title: '退费',
         shadeClose: false,
         shade: 0.6,
         area: ['80%', '350px'],
         content: contextPath + '/KQDS_MemberAct/toMemberTuifeiPage.act?seqId=' + seqIdLocal 
     });
}
function closePage(){
	parent.refresh();
	parent.layer.close(frameindex); //再执行关闭 
}
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-100
	});
}
</script>
</html>
