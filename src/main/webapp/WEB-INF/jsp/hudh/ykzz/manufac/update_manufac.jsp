<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	 String id = request.getParameter("id");
	   
	 if(id==null){
		 id="";
	 }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>供应商类别</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/web_info.css" />
</head>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    <form class="form-horizontal"  id="form1">
              <div class="box-body">
                	<input type="hidden" class="form-control" name="seqId" id="seqId" >
                	   <div class="kv ">
				            <label>编号</label>
				            <div class="kv-v">
				                <input type="text" name="manuCode" id="manuCode" readonly="readonly">
				            </div>
				        </div>
				        <div class="kv ">
				            <label>顺序号</label>
				            <div class="kv-v">
				                <input type="text" name="orderno" id="orderno" >
				            </div>
				        </div>
				         <div class="kv ">
				            <label>名称</label>
				            <div class="kv-v">
				                <input type="text" name="manuName" id="manuName" >
				            </div>
				        </div>
              </div>
              <div class="buttonBar" style="">
              	<a href="javascript:void(0);" class="kqdsSearchBtn" id="tijiao">保存</a>
			  </div>
            </form>
            </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript">
var apppath = apppath();
var id = '<%=id%>';
$(function (){
	if(!id){
		layer.alert("获取供应商信息失败");
		var frameindex = parent.layer.getFrameIndex(window.name);
        parent.layer.close(frameindex); //再执行关闭
	}else {
		$.ajax({
			url: apppath + "/YkzzManufacturersAct/findManufacturersById.act",
			type:"POST",
			dataType:"json",
			async:false,
			data:{"id":id},
			success:function(result){
				//alert(JSON.stringify(result));
				//console.log(result);
				$("#manuCode").val(result.manufacturersCode);
				$("#manuName").val(result.manufacturers_name);
				$("#orderno").val(result.orderno);
			}
		});
	}
	
	$("#tijiao").on("click",function (){
		var manuCode = $("#manuCode").val();
		var orderno = $("#orderno").val();
		var manuName = $("#manuName").val();
		if(!manuCode){layer.alert("请填写供应商编号！");return;}
		if(!orderno){layer.alert("请填写顺序号！");return;}
		if(!manuName){layer.alert("请填写供应商名称！");return;}
		$.ajax({
			url: apppath + "/YkzzManufacturersAct/updateManufacturersById.act",
			type:"POST",
			dataType:"json",
			async:false,
			data:{"id":id,
				  "orderno":orderno,
				  "manuName":manuName},
			success:function(result){
				layer.alert(result.retMsrg, {
		              end: function() {
		            	  window.parent.location.reload(); //刷新父页面 */
		                  var frameindex = parent.layer.getFrameIndex(window.name);
		                  parent.layer.close(frameindex); //再执行关闭
		              }
		        });
			}
		});
	});
});

</script>
</html>
