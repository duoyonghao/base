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
<title>单位管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/unit/unit.css">
</head>
<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<h2 class="titleText">单位管理</h2>
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<div class="tableDiv">
				<input type="hidden" name="seqId" id="seqId">
				<table class="tableContent"><!--tableContent的样式 -->
					<tbody>
						<tr>
							<td style="width:30%;"> 	<!--impText必填项样式  -->
								<span class="impText">单位名称</span>
							</td>
							<td style="width:70%;">
								<div class="form-group">
									<input type="text" name="unitName" id="unitName" >
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<span class="comText">单位电话</span>
							</td>
							<td>
								<div class="form-group">
									<input type="text" name="telephone" id="telephone" ></div>
							</td>
						</tr>
						<tr>
							<td>
								<span class="comText">邮政编码</span>
							</td>
							<td>
								<div class="form-group">
									<input type="text" name="postcode" id="postcode" ></div>
							</td>
						</tr>
						<tr>
							<td>
								<span class="comText">单位地址</span>
							</td>
							<td>
								<div class="form-group">
									<input type="text" name="address" id="address" ></div>
							</td>
						</tr>
						<tr>
							<td>
								<span class="comText">单位网址</span>
							</td>
							<td>
								<div class="form-group">
									<input type="text" name="website" id="website" ></div>
							</td>
						</tr>
						<tr>
							<td>
								<span class="comText">单位邮箱</span>
							</td>
							<td>
								<div class="form-group">
									<input type="text" name="email" id="email" ></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>	
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
var contextPath = "<%=contextPath%>";
var static_detailurl = 'YZUnitAct/selectDetail.act';
$(function() {
    getDetail();
});

function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        loadData(serverData.retData);
        $("#seqId").val(serverData.retData.seqId);

    }
}

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var unitName = $("#unitName").val();

    if (unitName == "") {
        layer.alert('单位名称不能为空' );
        return false;
    }

    // 赋值
    var param = $('#defaultForm').kqds_serialize();

    var url = 'YZUnitAct/update.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                window.location.reload();
            }
        });
    }
}
</script>


</html>
