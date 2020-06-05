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
    <title>添加回访</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/layer.css">
</head>
<body>
<!--添加手术信息弹窗-->
<div class="containerAddEdit"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
    <input type="hidden" class="form-control" name="seqId" id="seqId" >
    <table id="table">
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
			
    </table>
    </br></br>
    <div align="center" class="layui-layer-btn ">
		 <button class="kqdsCommonBtn" onclick="lcljOrderTrack.save();">创建</button>&nbsp;&nbsp;&nbsp;&nbsp;
		 <button class="kqdsCommonBtn" onclick="lcljOrderTrack.close();">取消</button>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
/**
 * 生成临床跟踪路径
 */
var lcljOrderTrack = {
	save : function (){
		var tooth = $("#thisNum").val();
		var bone = $("#isBone").val();
		var type = $("#ssType").val();
		//验证必填
		if(!tooth) {
			layer.alert("请填写本次手术颗数");
			return;
		}else {
			//获取剩余牙齿数
			var uncomNum = '<%=request.getAttribute("uncomNum")%>';
			if(tooth <= 0){
				layer.alert("牙齿数需大于0");
				return;
			}
			if(parseInt(tooth) > parseInt(uncomNum)) {
				layer.alert("手术牙齿数大于该患者剩余未做牙齿颗数");
				return;
			}
		}
		if(!type) {layer.alert("请选择跟踪方式");return;}
		if(!bone) {layer.alert("请选择是否植骨");return;}
		
		//获取临床编号
		var orderNumber = '<%=request.getAttribute("orderNumber")%>';
		if(!orderNumber) {layer.alert("获取编号失败，请刷新当前页面重新操作");return;}
		
		var param = {tooth:tooth,orderNumber:orderNumber,bone:bone,type:type};
		var url = '<%=contextPath%>/HUDH_LCLJAct/saveLcljOrderTrack.act';
		$.axseSubmit(url, param, function() {}, function(r) {
			layer.alert(r.retMsrg, {
	              end: function() {
	            	  window.parent.location.reload(); //刷新父页面
	                  var frameindex = parent.layer.getFrameIndex(window.name);
	                  parent.layer.close(frameindex); //再执行关闭
	              }
	        });
		}, function(r) {
			layer.alert(r.retMsrg, {
	            end: function() {
	            	window.parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
	            }
	      });
		});
	},
	close : function (){
		var frameindex = parent.layer.getFrameIndex(window.name);
        parent.layer.close(frameindex); //再执行关闭
	}
}
</script>
</html>
