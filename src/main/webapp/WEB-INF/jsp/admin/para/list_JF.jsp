<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = (YZPerson) request.getSession().getAttribute(ConstUtil.LOGIN_USER);
	
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>积分参数设置</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/para/para.css">
</head>
<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId">
			<table id="table" class="tableContent" style="width:50%;"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td style="text-align: center;">参数值</td>
						<td style="text-align: center;"><input type="text" name="jfvalue"  id="jfvalue" value=''></td>
					</tr>
					<tr id="paraList">
						<td style="text-align: center;">参数说明</td>
						<td style="text-align: center;"><input type="text" name="jfremark"  id="jfremark" value=''></td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="BottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="aBtn" id="refresh" href="javascript:void(0);" onclick="refresh();">刷新</a>
         	<a class="aBtn" id="refresh" href="javascript:void(0);" onclick="singleSave();">保存</a>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds_person_select.js"></script>
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var static_listurl = 'YZParaAct/selectList.act?1=1';
var static_deleteurl = 'YZParaAct/delete.act?1=1';
var organization = "<%=organization %>";
$(function() {
	if(organization == ""){
		layer.alert('参数错误：organization不能为空！');
		return false;
	}
	static_listurl = static_listurl + '&organization=' + organization;
    getList();
});

function getList() {
    var serverData = getDataFromServer(static_listurl);
    if (serverData) {
        var paralist = serverData.retData;
        var htmlstr = "";
        for (var i = 0; i < paralist.length; i++) {
			if("COST_INTEGRAL"==paralist[i].paraName){
				$("#jfvalue").val(paralist[i].paraValue);
				$("#jfremark").val(paralist[i].remark);
				$("#seqId").val(paralist[i].seqId);
			}
        }
    }
}

function refresh() {
    window.location.reload();
}

function singleSave() {
	var seqId = $("#seqId").val();
	var paraObj = new Object();
	paraObj['organization'] = organization;
	paraObj['paraName'] = 'COST_INTEGRAL';
	paraObj['paraValue'] = $("#jfvalue").val();
	paraObj['remark'] = $("#jfremark").val();
    if (seqId) {
        paraObj['seqId'] = seqId;
    } else {
        paraObj['seqId'] = '';
    }

    var url = 'YZParaAct/insertOrUpdate.act';
    var serverData = getDataFromServer(url, paraObj);
    if (serverData) {
        layer.alert('操作成功', {
            icon: 6,
            end: function() {
                refresh();
            }
        });
    }
}
</script>
</html>
