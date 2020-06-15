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
<title>修改密码</title>
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
							<span>患者姓名：</span>
						</td>
						<td>
							<span id="username"></span>
						</td>
						<td>	
							<span>患者编号：</span>
						</td>
						<td>
							<span id="usercode"></span>
						</td>
					</tr>
					<tr>
						<td>	
							<span>会员卡号：</span>
						</td>
						<td>
							<span  id="memberno"></span>
						</td>
						<td>	
							<span>IC卡号：</span>
						</td>
						<td>
							<span  id="icno"></span>
						</td>
					</tr>
					<tr>	
						<td>	
							<span>充值余额：</span>
						</td>
						<td>
							<span  id="money"></span>
						</td>
						<td>	
							<span>赠送余额：</span>
						</td>
						<td>
							<span  id="givemoney"></span>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv">
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="editpass">修改密码</a>
         	<a class="kqdsCommonBtn" id="resetpass">重置密码</a>
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
var seqId = '<%=seqId%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function(){
	var data = getDetail(seqId).data;
	$("#username").html(data.username);
	$("#usercode").html(data.usercode);
	$("#memberno").html(data.memberno);
	$("#icno").html(data.icno);
	var allmoney = Number(data.money)+Number(data.givemoney);
	$("#money").html(Number(data.money));
	$("#givemoney").html(Number(data.givemoney));
});
$('#editpass').on('click', function(){
    layer.open({
        type: 2,
        title: $("#username").html()+'-修改密码',
        shadeClose: false,
        shade: 0.6,
        maxmin: true,
        area: ['320px', '280px'],
        content: contextPath + '/KQDS_MemberAct/toMemberEditpass.act?seqId='+seqId
    });
});
$('#resetpass').on('click', function(){
	layer.prompt({
        title: '输入密码，并确认',
        formType: 0
    },
    function(pass, index) {
        layer.close(index);
        var param = {
       		password: pass,
            seqId: seqId
        };
        var url = contextPath + '/KQDS_MemberAct/editPass.act';
        $.axse(url, param,
        function(r) {
        	if (r.retState == "0") {
                layer.alert('修改成功', {
                       
                     end: function() {
                    	 parent.layer.close(frameindex); //执行关闭
                     }
                 });
                 return true;
             }
        },
        function() {
            layer.alert(r.retMsrg  );
        });
    });
});
</script>


</html>
