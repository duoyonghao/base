<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<% 
	String seqId=request.getParameter( "seqId"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>模板详情</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>

<body>
<form class="container" class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
	<table class="tableLayout">
		<tr>
			<td>
				<span class="commonText">短信名称<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="smsname" id="smsname" placeholder="短信名称">
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<span class="commonText">短信分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select class="dict" tig="dxfl" name="smstype" id="smstype" ></select>
				</div>
			</td>
			<td>
				<span class="commonText">二级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="smsnexttype" id="smsnexttype" >
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<span class="commonText">自动发送模板</span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="isdsmodel" id="isdsmodel" >
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</td>
			<td>
				<span class="commonText">排序号</span>
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="sortno" id="sortno" placeholder="请输入数字">
				</div>
			</td>
		</tr>
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">内容</span>
				
			</td>
			<td colspan="3">
				<div class="form-group">
					<textarea rows="7" class="longTextarea" name="smscontent" id="smscontent"></textarea>
				</div>
			</td>
		</tr>
	</table>
</form>

<script>
var seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
	 initDictSelectByClass();
	 var url = 'KQDS_Sms_ModelAct/selectDetail.act';
     var pageParam = {
     		seqId: seqId
         };
     var returnData = getDataFromServer(url, pageParam);
     if (returnData.retState == 0) {
    	 var detaildata = returnData.data;
    	 $("#smsname").val(detaildata.smsname);
    	 $("#smstype").val(detaildata.smstype).trigger("change");
    	 $("#smsnexttype").val(detaildata.smsnexttype);
    	 $("#smscontent").val(detaildata.smscontent);
     } else {
         layer.alert('查询失败！', {
               
         });
     }
	 readonly();
});
$('#smstype').change(function() {
	getSubDictSelectByParentCode(this.value,'smsnexttype');
});
</script>
</body>
</html>
