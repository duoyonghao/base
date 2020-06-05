<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}

	String seqId = request.getParameter("seqId");
	if(seqId == null || "".equals(seqId)){
		seqId="0";
	}
	
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>添加标签</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/history_record.css" />
    
    <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>	
</head>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    <form class="form-horizontal"  id="form1">
              <div class="formBox">
			        <div class="kv singleKv">
			            <label >标签</label>
			            <div class="kv-v">
			                <select class="dict" tig="HZBQ" name="bq" id="bq" >
							</select>
							</br></br>
			            </div>
			        </div>
              </div>
              <div class="bottomBar" id="bottomBarDdiv" style="text-align: center;">
                <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="submitu()">保存</a>
              </div>
            </form>
            </div>
</div>
</body>
<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var seqId = '<%=seqId%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function(){
	initDictSelectByClass(); // 标签
	
});
//提交
function submitu(){
	var bq = $("#bq").val();
	if(bq == "" || bq == null){
		layer.alert('请选择标签', {  end:function(){
		}});
		return false;
	}
	 var url = '<%=contextPath%>/KQDS_UserDocumentAct/editSub.act';
	 var dataparam={
			 bq:bq,
			 seqId:seqId
	 };
	   $.axse(url,dataparam, 
              function(data){
				  if(data.retState=="0"){
					  window.parent.location.reload(); 
	        		  parent.layer.close(frameindex); //关闭当前弹层
	        	  }
              },
              function(){
            	  layer.alert('添加失败！' );
          	  }
        ); 
	
}


</script>
</html>
