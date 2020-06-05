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
<title>审核</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/dict/dictNewAdd.css"/>
</head>
<body>
<div class="container"><!--提供内边距 让内容不顶头显示-->
	<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
		<input type="hidden" name="seqId" id="seqId">
		<table class="tableContent"><!--tableContent的样式 -->
			<tbody>
				<tr>
					<td> 	<!--impText必填项样式  -->
						<span class="impText">预约时间</span>
					</td>
					<td>
						<input type="text" name="ordertime" id="ordertime" >
					</td>
				</tr>
				<tr>
					<td> 	<!--impText必填项样式  -->
						<span class="impText">审核结果</span>
					</td>
					<td>
						<textarea rows="" cols="" name="auditorremark" id="auditorremark" style="width:90%;"></textarea>
					</td>
				</tr>
			</tbody>
		</table>	
	</form>
	<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
		<div class="clear2"></div>
        	<a class="kqdsCommonBtn" id="submit">确 定</a>
	</div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds_person_select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/kqds_wechat_order.js"></script>
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {

});

$("#ordertime").datetimepicker({
    language: 'zh-CN',
    minView: 0,
    format: 'yyyy-mm-dd hh:ii',
    autoclose: true,
    //选中之后自动隐藏日期选择框   
    pickerPosition: "bottom-right"
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var auditorremark = $("#auditorremark").val();
    if (auditorremark == "") {
        layer.alert('审核结果不能为空' );
        return false;
    }

    var ordertime = $("#ordertime").val();
    if (auditorremark == "") {
        layer.alert('预约时间不能为空' );
        return false;
    }

    var param = {
        seqId: $.getUrlVar('seqId'),
        orderstatus: $.getUrlVar('orderstatus'),
        ordertime: ordertime,
        auditorremark: auditorremark
    };
    var rtData = getDataFromServer(wxOrderObj.auditor, param);
    if (rtData.retState == 0) {
        layer.alert('审核成功！', {
              
            end: function() {
                parent.refresh();
                parent.layer.close(frameindex);
            }
        });
    }
}
</script>
</html>
