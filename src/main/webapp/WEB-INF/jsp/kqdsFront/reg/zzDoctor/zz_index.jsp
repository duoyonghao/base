<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
      contextPath = "/kqds";
  }
  String usercode = request.getParameter("usercode");
 
  if(usercode == null ){
	  usercode = "";  // 配合js 的非空判断，这里不这样写，则js 需进行  usercode != "null"的判断
  }
  String costno = request.getParameter("costno");
  if(costno == null ){
	  costno = "";  // 配合js 的非空判断，这里不这样写，则js 需进行  usercode != "null"的判断
  }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=7">
    <meta charset="utf-8" />
    <title>就诊挂号</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
	<style>
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
	<div class="jiuzhen_register-content" style="width: 98%;">
		<div class="filterWrap">
			
			<div class="kv searchKv">
				<div class="kv-v">
					<select id="searchField">
						<option value="username">姓名</option>
						<option value="PhoneNumber1">手机号码</option>
						<option value="idcardno">身份证号</option>
					</select>
					<input type="text" id="searchValue" name="searchValue">
					<a id="searchHzda" href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查 询</a>
				</div>
			</div>
		</div>
		 <div class="tableBox" style="margin-bottom: 20px;">
		 	<p	class="table-title">查询清单</p>
            <table id="table" class="table-striped table-condensed table-bordered" data-height="395"></table>
         </div>
         <div class="position-bottom" >
         	<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="zz">转诊</a>
         	<a class="kqdsCommonBtn" id="zzjl">转诊记录</a>
         </div>
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
var onclickrowOobj;
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopageGh.act';
//建档、预约中心跳转传值
var usercode = "<%=usercode %>";
var costno = "<%=costno %>";
$(function(){
	onclickrowOobj = getCostOrderObjBySeqId(costno);
      //档案列表
    $('#table').bootstrapTable({
    	url:pageurl, 
		dataType: "json",
		queryParams:queryParams,
		striped: true,
		cache: false, 
		onLoadSuccess : function(data) {
			if(usercode){
// 				$('#table').DataTable().rows().nodes()[0].trigger("click");
				//根据usercode 查询患者姓名 并给查询条件框赋值
				$("#searchValue").val(getNameByusercodesTB(usercode)[0].username);
			}else if(onclickrowOobj.usercode){
// 				$('#table').DataTable().rows().nodes()[0].trigger("click");
				//根据usercode 查询患者姓名 并给查询条件框赋值
				$("#searchValue").val(getNameByusercodesTB(onclickrowOobj.usercode)[0].username);
			}
			setHeight();
		},
		columns: [
		          		{title: 'seqId',field: 'seqId',align: 'center',valign: 'middle',visible:false,switchable:false}, 
			        	{title: '病人编号',field: 'usercode',align: 'center',valign: 'middle',visible:false,switchable:false},
		                {title: '姓名',field: 'username',align: 'center',valign: 'middle'}, 
		                {title: '性别',field: 'sex',align: 'center',valign: 'middle'}, 
		                {title: '出生日期',field: 'birthday',align: 'center',valign: 'middle'}, 
		                {title: '身份证号',field: 'idcardno',align: 'center',valign: 'middle'}, 
		                {title: '手机号码1',field: 'phonenumber1',align: 'center'},
		                {title: '手机号码2',field: 'phonenumber2',align: 'center'}
		            ]
		  });
});
function setHeight(){
	var tableHeight=$(window).outerHeight()-$(".table-title").outerHeight()-$(".filterWrap").outerHeight()-$(".position-bottom").outerHeight();
	$("#table").bootstrapTable("resetView",{
		height:tableHeight-45
	});
}
function searchHzda(){
	 onclickrowOobj="";
	 var searchValue = $("#searchValue").val();
	 if($.trim(searchValue)==""){
		 layer.alert('请输入查询条件' );
 		return false;
	 }
	 $('#table').bootstrapTable('refresh',{'url':pageurl});
	 $("#searchValue").val(searchValue);
}
function queryParams(params) {
	var temp ;
	if(usercode){
		temp = {
    		usercode:usercode
	    };
	}else if(onclickrowOobj.usercode){
		temp = {
	    	usercode:onclickrowOobj.usercode
		};
	}else{
		temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		limit: params.limit,
    		offset:params.offset ,
    		searchField:$('#searchField').val(),
    		searchValue:$('#searchValue').val()
	    };
	}
    return temp;
}
	$('#zz').on('click', function(){
		if(onclickrowOobj==null || onclickrowOobj.costno==null || onclickrowOobj.costno=="undefined"){
			layer.alert('请选择转诊医生的费用单！', {});
			return false;
		}
		layer.open({
			type: 2,
			title: '转诊',
			maxmin: true,
			shadeClose: true, //点击遮罩关闭层
			area : ['80%' , '90%'],
			content: contextPath+'/KQDS_UserDocumentAct/toZzEditDoctor.act?usercode=' + onclickrowOobj.usercode
		});
	});
$('#zzjl').on('click', function(){
	var usercode = "";
	if(onclickrowOobj != null && onclickrowOobj != ""){
		usercode = onclickrowOobj.usercode;
	}
	layer.open({
		type: 2,
		title: '转诊记录',
		maxmin: true,
		shadeClose: true, //点击遮罩关闭层
		area : ['90%' , '90%'],
		content: contextPath+'/KQDS_UserDocumentAct/toZzDoctorWin.act?usercode=' + usercode
	});
});
</script>

</html>
