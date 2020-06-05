<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String goodsinid = request.getParameter("goodsinid");
	String incode = request.getParameter("incode");
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>添加审核</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <style type="text/css">
    	.text{
    		width: 260px;
    		height: 140px;
    		border-radius: 1em;
    	}
    	#table{
    		margin: 0;
    	}
    	.containerAddEdit{
    		    padding: 10px 10px;
    	}
    </style>
</head>
<body>
<!--添加路径操作备注录入信息弹窗-->
 <div class="containerAddEdit"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
 	<div align="left">
 	<input id="drugsInId" class="hide"/>
	 	<table id="table">
	 		<tbody align="left">
				<tr>
					<td>
						<span class="information">外包装有无破损异常：</span>
					</td>
					<td>
						<select class="dict select2" id="packing">
					    	<option value="0">异常</option>
					    	<option value="1" selected = "selected" >通过</option>
			    		</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<span class="information">合格证：</span>
					</td>
					<td>
						<select class="dict select2" id="certificate">
					    	<option value="0">异常</option>
					    	<option value="1" selected = "selected" >通过</option>
			    		</select>
					</td>
					<td>
						<span class="information">检验报告：</span>
					</td>
					<td>
						<select class="dict select2" id="report">
					    	<option value="0">异常</option>
					    	<option value="1" selected = "selected" >通过</option>
			    		</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<span class="information">验收人：</span>
					</td>
					<td>
						<input type="text" id="checkUserId" name="checkUserId" class="hide">
						<input type="text" id="checkUserName" name="checkUserName">
					</td>
					<td>
						<span class="information">验收日期：</span>
					</td>
					<td>
						<input type="text" id="checkDate" name="checkDate">
					</td>
				</tr>
				<tr>
					<td align="right">
						<span class="information">备注：</span>
					</td>
					<td  colspan="3">
						<textarea id="remark" ></textarea>
					</td>
				</tr>
	 		</tbody>
	    </table>
 	</div>    
    <div align="center" style="margin-top:10px;">
    	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="submit(1)" id="tijiao">加入库存</a>
    	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="submit(0)" style="margin-left: 30px;" id="tijiao">搁置处理</a>
    </div><br>
</div> 

</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var goodsinid = '<%=goodsinid%>';
var incode = '<%=incode%>';
var currentUserId = '<%=person.getUserId()%>';
var currentUserUser = '<%=person.getUserName()%>';
var menuid = parent.menuid;
$(function (){
	$("#drugsInId").val(drugsInId);
	$("#checkUserId").val(currentUserId);
	$("#checkUserName").val(currentUserUser);
	nowday = getNowFormatDate();
    $("#checkDate").val(nowday);
});

function submit(flag){
	var drugsInId = $("#drugsInId").val();
	var packing = $("#packing").val();
	var certificate = $("#certificate").val();
	var report = $("#report").val();
	var checkDate = $("#checkDate").val();
	var remark = $("#remark").val();
	
	if(flag == "1") {
		if(packing == "0" || certificate == "0" || report == "0"){
			layer.alert("存在异常数据禁止入库");
			return;
		}
	}else if(flag == "0") {
		if(packing == "1" && certificate == "1" && report == "1"){
			layer.alert("全部通过请点击加入库存");
			return;
		}
	}

	var url = '<%=contextPath%>/HUDH_Ck_Goods_In_CheckAct/saveGoodsInCheck.act';
	var param = { 
			goodsinid : goodsinid,
			incode : incode,
			packing : packing,
			certificate : certificate,
			report : report,
			checkDate : checkDate,
			remark : remark
	};
	$.axseSubmit(url, param, function() {}, function(r) {
		layer.alert(r.retMsrg, {
            end: function() {
            	parent.window.location.reload(); //刷新父页面
                var frameindex = parent.layer.getFrameIndex(window.name);
                parent.layer.close(frameindex); //再执行关闭
            }
      });
	}, function() {
	}); 
}
</script>
</html>
