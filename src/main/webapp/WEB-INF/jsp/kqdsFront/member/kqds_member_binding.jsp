<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String seqId = request.getParameter("seqId");
	if(seqId == null || seqId == ""){
		seqId = "";
	}
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>绑定IC卡</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/hzjd.css">
</head>

<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId" value="<%=seqId%>">
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					
					<tr class="addVisitingDialog-content">	<!--addVisitingDialog 本身无样式 绑定了星星的点击事件  -->
						<td>	
							<span class="impText">IC卡号*</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="icno" id="icno">
							</div>
						</td>
					</tr>
					<tr>
						<td> 	
							<span class="impText">密码*</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="password" id="password">
							</div>
						</td>
					</tr>
					<tr>	
						<td>
							<span class="impText">确认密码*</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="password2" id="password2">
							</div>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv">
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="submit">确 定</a>
		</div>
	</div>
</body>
<script type="text/javascript">
// ### 这个变量声明在下面，hzjd.js调用不到
var contextPath = '<%=contextPath%>';
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/kqds_member_binding.js"></script>
<script type="text/javascript">

var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function(){
	$("#icno").focus(); 
    //验证表单
    Yztable();
});
$('#submit').on('click', function(){
	 var flag = submit(); //submit()校验方法
	 if (!flag) {
         return false;
     }
	 var param = $('#defaultForm').serialize();
     var url = contextPath + '/KQDS_MemberAct/bindingAdd.act?' + param;
     $.axse(url, null,
     function(r) {
         if (r.retState == "0") {
            layer.alert('绑定成功', {
                   
                 end: function() {
                	 parent.refresh();
                	 parent.layer.close(frameindex); //执行关闭
                 }
             });
             return true;
         }else{
             layer.alert(r.retMsrg, {
                   
             });
             return false;
         }
     },
     function() {
         layer.alert(r.retMsrg, {
               
         });
         return false;
     });
});
</script>


</html>
