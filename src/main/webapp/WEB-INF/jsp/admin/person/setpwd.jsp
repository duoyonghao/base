<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>设置密码</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/admin.css">
</head>
<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId">
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">原始密码</span>
						</td>
						<td>
							<div class="form-group">
								<input type="password" name="oldpwd" id="oldpwd" >
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">新密码</span>
						</td>
						<td>
							<div class="form-group">
								<input type="password" name="newpwd1" id="newpwd1" ></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">确认新密码</span>
						</td>
						<td>
							<div class="form-group">
								<input type="password" name="newpwd2" id="newpwd2" ></div>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="submit">确 定</a>
		</div>
		<div id="managerTree" class="nohc-scroll-webkit ztree" style="width:200px;height:300px; display:none;position: absolute;overflow-y: auto;"/>
		
		
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds_person_select.js"></script>
<script type="text/Javascript">
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var contextPath = "<%=contextPath%>";
$(function() {

});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
	var oldpwd  = $("oldpwd").val();
    var newpwd1 = $("#newpwd1").val();
    var newpwd2 = $("#newpwd2").val();
   	
    
    if(newpwd1 == ""){
    	layer.alert('新密码不能为空！' );
        return false;
    }
    if(newpwd1 == oldpwd){
    	layer.alert('新密码不能与旧密码一致！' );
        return false;
    }
    if(newpwd1 == "123456"){
    	layer.alert('新密码不能与默认密码相同！' );
        return false;
    }
    if(newpwd2 == ""){
    	layer.alert('确认密码不能为空!' );
        return false;
    }
    if (newpwd1 != newpwd2) {
        layer.alert('两次输入的新密码不一致' );
        return false;
    }

    // 赋值
    var param = $('#defaultForm').kqds_serialize();
    var url = 'YZPersonAct/setPassword.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('密码设设置成功', {
              
            end: function() {
                if (parent.layer) {
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            }
        });
    }
}
</script>


</html>
