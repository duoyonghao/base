<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>计划内容</title><!-- 客服中心  推广计划 ， 点击 计划内容 按钮进入  -->
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
                  <div class="kv singleKv" style="text-align: center;">
			            <div class="kv-v">
			                <textarea type="text" id="hfresult" name="hfresult" row="3" ></textarea>
			            </div>
			            <div class="kv-v">
			            </br></br>
			                <input type="button" class="kqdsSearchBtn" onclick="submitu()" value="提交" />
			            </div>
		          </div>
              </div>
            </form>
            </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var row = window.parent.onclickrowOobj;
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引

//提交
function submitu(){
	
	var hfresult = $("#hfresult").val();
	if(hfresult == "" || hfresult == null ){
		layer.alert('请输入计划内容' );
		return false;
	}
	
	//保存
	var param = {
		seqId : row.seqId,
		status : 1,
		hfresult : hfresult
	};
	
	var url = '<%=contextPath%>/KQDS_ExtensionAct/insertOrUpdate.act';
   	$.axseSubmit(url,param, 
  		function(){
			 layer.msg('加载中', {icon: 16});
	  	},
        function(r){
   	        if(r.retState=="0"){
   	        	layer.alert('操作成功', {  end:function(){
   	        		parent.refresh();
   	        		var index = parent.layer.index; //获取当前弹层的索引号
   	        		parent.layer.close(index);
   	        	}});
   	        }else{
   	        	layer.alert('操作失败' );
   	        }     
        },
           function(){
         	    layer.alert('操作失败' );
		}
    ); 	
}

</script>
</html>
