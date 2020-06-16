<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
   String perId = request.getParameter("perId");
   String perName = request.getParameter("perName");
   
   if(perId==null){
	   perId="";
   }
   if(perName==null){
	   perName="";
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>商品类别</title>
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
				            <label>上级类别</label>
				            <div class="kv-v">
				                <input type="hidden" name="perid" id="perid" >
				                <input type="text" name="pername" id="pername" readonly="readonly">
				            </div>
				        </div>
				        <div class="kv ">
				            <label>分类名称</label>
				            <div class="kv-v">
				                <input type="text" name="goodstype" id="goodstype" >
				            </div>
				        </div>
				        <div class="kv ">
				            <label>排序号</label>
				            <div class="kv-v">
				                <input type="text" name="sortno" id="sortno" >
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
var perId = '<%=perId%>';
var perName = '<%=perName%>';
$(function (){
	$("#perid").val(perId);
	$("#pername").val(perName);
	
	$("#tijiao").on("click",function (){
		var orderno = $("#sortno").val();
		var typeName = $("#goodstype").val();
		if(!$("#pername").val()||!$("#perid").val()){layer.alert("获取父类型失败，请重新添加！");return;}
		if(!typeName){layer.alert("请填写分类名称！");return;}
		if(!orderno){layer.alert("请填写编号！");return;}
		$.ajax({
			url: apppath + "/HUDH_YkzzTypeAct/insertYkzzType.act",
			type:"POST",
			dataType:"json",
			async:false,
			data:{"typeName":typeName,
				  "orderno":orderno,
				  "parentid":perId},
			success:function(result){
				layer.alert(result.retMsrg, {
		              end: function() {
		            	  parent.refresh(apppath + "/HUDH_YkzzTypeAct/findChildTypesByParentId.act?id="+perId);
		            	  /* window.parent.location.reload(); //刷新父页面 */
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
