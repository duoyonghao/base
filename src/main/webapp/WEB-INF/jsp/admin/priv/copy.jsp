<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);

	String seqId = request.getParameter("seqId");
	if (seqId == null) {
		seqId = "";
	}
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>拷贝角色</title>
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
							<span class="impText">排序号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="privNo" id="privNo" value="0">
							</div>
						</td>
					</tr>
					<tr>
							<td> 	<!--impText必填项样式  -->
								<span class="impText">所属门诊</span>
							</td>
							<td>
								<div class="form-group">
									<select id="organization" name="organization">
                					</select>
								</div>
							</td>
						</tr>
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">角色名称</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="privName" id="privName">
							</div>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="clean">清 空</a>
         	<a class="kqdsCommonBtn" id="submit">确 定</a>
		</div>
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
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var static_seqId = "<%=seqId%>";
var static_detailurl = 'YZPrivAct/selectDetail.act?seqId=' + static_seqId;
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	initHosSelectListNoCheckWithNull('organization');
    getDetail();
});

function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        loadData(serverData.retData);
        // 重新赋值
        $("#privName").val("拷贝_" + $("#privName").val());
    }
}

$('#clean').on('click',
function() {
    $("#defaultForm :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var privNo = $("#privNo").val();
    var privName = $("#privName").val();

    if (privNo == "") {
        layer.alert('排序号不能为空' );
        return false;
    }

    if (privName == "") {
        layer.alert('角色名称不能为空' );
        return false;
    }
    var param = $('#defaultForm').kqds_serialize();
    var url = 'YZPrivAct/copyPriv.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                parent.refresh();
                parent.layer.close(frameindex); //再执行关闭 */
            }
        });
    }
}
</script>
</html>
