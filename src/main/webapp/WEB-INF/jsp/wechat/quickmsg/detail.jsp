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
<title>快捷回复详情</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />

<style>
	.tableLayout{
		margin:0;
		width:460px;
	}
	.tableLayout tr.textareaTr {
	    height: 150px;
	}
</style>	
</head>

<body>
<form class="container" class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
	<table class="tableLayout">
		<tr>
			<td>
				<span class="commonText">回复分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select class="dict" tig="WECHAT_KEYWORD" name="msgtype" id="msgtype" ></select>
				</div>
			</td>
		</tr>
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">内容</span>
				
			</td>
			<td>
				<div class="form-group">
					<textarea rows="7" class="longTextarea" name="msgcontent" id="msgcontent"></textarea>
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
	 var url = 'WXQuickMsgAct/selectDetail.act';
     var pageParam = {
     		seqId: seqId
         };
     var returnData = getDataFromServer(url, pageParam);
     if (returnData.retState == 0) {
    	 var detaildata = returnData.data;
    	 $("#msgtype").val(detaildata.msgtype).trigger("change");
    	 $("#msgcontent").val(detaildata.msgcontent);
     } else {
         layer.alert('查询失败！', {
               
         });
     }
	 readonly();
});
</script>
</body>
</html>
