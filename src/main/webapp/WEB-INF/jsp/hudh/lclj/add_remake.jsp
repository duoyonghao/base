<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
	String regno = request.getParameter("regno");
	String lytype = request.getParameter("lytype");
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>添加临床路径操作备注录入信息</title>
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
    </style>
</head>
<body>
<!--添加路径操作备注录入信息弹窗-->
 <div class="containerAddEdit"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
 	<div align="left">
	 	<table id="table">
	 		<tbody align="left">
				<tr>
					<td>
						<span class="information">手术颗数：</span>
					</td>
					<td>
						<input type="text" id="thisNum" name="thisNum" oninput="value=value.replace(/[^\d]/g,'')"/>
					</td>
				</tr>
				<tr>
					<td>
						<span class="information">跟踪方式：</span>
					</td>
					<td>
						<select class="dict select2" id="ssType">
					    	<option value="">- 请选择  -</option>
					    	<option value="单颗多颗">单颗多颗</option>
					    	<option value="Locator">Locator</option>
					    	<option value="All-on-x">All-on-x</option>
			    		</select>
					</td>
				</tr>
				<tr>
					<td>
						<span class="information">是否植骨：</span>
					</td>
					<td>
						<select class="dict select2" id="isBone">
					    	<option value="">- 请选择 -</option>
					    	<option value="是">是</option>
					    	<option value="否">否</option>
			    		</select>
					</td>
				</tr>
	 		</tbody>
	    </table>
 	</div>
    <div align="left">
    	<textarea autofocus="autofocus" class="text" id="remake">
    		
    	</textarea>
    </div><br>       
    <div align="center">
    	<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()" id="tijiao">提交</a>
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
	function submitu(){
		var orderTrackId = '<%=request.getAttribute("orderTrackId")%>';
		var flowLink = '<%=request.getAttribute("flowLink")%>';
		var operateName = '<%=request.getAttribute("operateName")%>';
		var orderNumber = '<%=request.getAttribute("orderNumber")%>';
	    //alert(orderNumber);
		var remake = $("#remake").val();
		var url = '<%=contextPath%>/HUDH_LCLJAct/updateOperateNoteInfo.act';
		var param = {orderTrackId:orderTrackId, flowLink:flowLink, operateName:operateName, remake:remake};
		$.axseSubmit(url, param, function() {}, function(r) {
			layer.alert(r.retMsrg, {
	            end: function() {
	            	window.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
	            }
	      });
		}, function() {
			
		});
	}
</script>
</html>
