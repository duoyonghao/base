<%@ page language="java" pageEncoding="UTF-8" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String id=request.getParameter("id");
	String regid=request.getParameter("regid");
	String usercode=request.getParameter("usercode");
	String username=request.getParameter("username");
	String sex=request.getParameter("sex");
	String askpersonname=request.getParameter("askpersonname");
	String age=request.getParameter("age");
	String phone=request.getParameter("phone");
	String floor=request.getParameter("floor");
	String number=request.getParameter("number");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>叫号</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 150px;   
	      }  
	.searchSelect>.btn { 
		    width: 150px; 
		 	height:28px; 
		 	border: solid 1px #e5e5e5; 
		}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
		    color: #999;
		    height: 28px;
		}
	.pull-left {
	    float: left !important;
	    color: #333;
		}
	.btn-group > .btn:first-child:hover {
	    background: white;
	}
	.searchWrap .text {
		    position: static !important; 
		    left: 0px;
		    top: 9px;
		    color: rgb(31, 32, 33);
		}
	.checkbox1 {
		width:20px !important;
	}
	.textareaTr1 {
		height: 80px !important;
	}
</style>
</head>
<body>
<div class="containerAddEdit"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
    <table id="table">
		<tbody>
			<tr>
				<td>
					<span class="information">序号</span>
				</td>
				<td>
					<input type="text" id="number" name="number" readonly>
				</td>
				<td>
					<span class="information">患者姓名</span>
				</td>
				<td>
					<input type="text" id="username" name="username" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">病人编号</span>
				</td>
				<td>
					<input type="text" id="usercode" name="usercode" readonly>
				</td>
				<td>
					<span class="information">患者手机</span>
				</td>
				<td>
					<input type="text" id="phone" name="phone" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">患者性别</span>
				</td>
				<td>
					<input type="text" id="sex" name="sex" readonly>
				</td>
				<td>
					<span class="information">患者年龄</span>
				</td>
				<td>
					<input type="text" id="age" name="age" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">患者助理</span>
				</td>
				<td>
					<input type="text" id="askpersonname" name="askpersonname" readonly="readonly">
				</td>
				
			</tr>
			<tr class="textareaTr">
				<td>
					<span class="information">超时原因</span>
				</td>
				<td colspan="5">
					<textarea class="form-control" rows="3" name="remark" id="remark" placeholder="" ></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="text-align:center;">
					<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()" id="tijiao">提交</a>
				</td>
			</tr>
			
		</tbody>
    </table>
    
    
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var id = '<%=id%>';
var regid = '<%=regid%>';
var usercode = '<%=usercode%>';
var username = '<%=username%>';
var sex = '<%=sex%>';
var askpersonname = '<%=askpersonname%>';
var age = '<%=age%>';
var phone = '<%=phone%>';
var floor = '<%=floor%>';
var number = '<%=number%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	$("#usercode").val(usercode);
	$("#username").val(username);
	$("#sex").val(sex);
	$("#age").val(age);
	$("#askpersonname").val(askpersonname);
	$("#phone").val(phone);
	if(Number(number)<=9){
		$("#number").val(floor+"0"+number);
	}else{
		$("#number").val(floor+number);
	}
	parent.s=1;
});
//提交
function submitu() {
	var remark = $("#remark").val();
	layer.confirm("是否确定操作？",{
		   btn: ['确认', '取消'],
		   closeBtn:0
	}, function (index) {
		parent.updateParameter();
		var url = contextPath + '/Kqds_JhAct/delete.act';
		$.ajax({
	        type: "POST",
	        url: url,
	        data: {id:id,remark:remark,regid:regid},
	        dataType: "json",
	        success: function(r){
	        	if(r.retState == "0"){
	        		parent.s=0;
	        		layer.alert('操作成功!');
	        		parent.refresh(); // 调用indexTab的点击事件，使内容刷新
	        		parent.layer.close(frameindex); //再执行关闭
	        	}else{
	        		parent.s=0;
	        		layer.alert('操作失败!');
	        		parent.layer.close(frameindex); //再执行关闭
	        	}
	        }
		});
	});
}
</script>
</html>