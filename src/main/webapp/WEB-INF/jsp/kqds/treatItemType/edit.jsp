<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String seqId = request.getParameter("seqId");
	if (seqId == null) {
		seqId = "";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>编辑收费项目分类</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
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
							<span class="impText">所属门诊</span>
						</td>
						<td>
							 <select id="organization" name="organization"></select>
						</td>
					</tr>
					<tr style="display: none;">
						<td> 	<!--impText必填项样式  -->
							<span class="impText">上级编号</span>
						</td>
						<td>
							<input type="text" name="parentCode" id="parentCode" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">分类名称</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="dictName" id="dictName" ></div>
						</td>
					</tr>
					<tr style="display: none;">
						<td>
							<span class="impText">分类编号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="dictCode" id="dictCode" ></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="impText">排序号</span>
						</td>
						<td>
							<div class="form-group">
								<input type="text" name="orderno" id="orderno" value="0"></div>
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText" id="remarkTd">备注</span>
						</td>
						<td>
							<div class="form-group" style="width: 60%;">
								<input type="text" name="remark" id="remark" value="" ></div> <span id="remarkSpan"></span>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<!-- <a class="kqdsCommonBtn" id="clean">清 空</a> -->
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
var static_seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var static_detailurl = 'YZDictAct/selectDetail.act?seqId=' + static_seqId;
var static_parentCode = null;
$(function() {
	initHosSelectListNoCheckWithCommon('organization');// 连锁门诊下拉框	
    getDetail();
});

function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        loadData(serverData.retData);

        $("#seqId").val(serverData.retData.seqId);
        
        static_parentCode = serverData.retData.parentCode;

        $("#organization option").each(function(){
    		if($(this).val() != serverData.retData.organization){
    			$(this).remove();
    		}
    	});
    }
}

$('#clean').on('click',
function() {
    $("#defaultForm :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
});

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
	var parentCode = $("#parentCode").val();
    var dictName = $("#dictName").val();
    var dictCode = $("#dictCode").val();
    var orderno = $("#orderno").val();
    var organization = $("#organization").val();
/*  if (organization == "") {
        layer.alert('所属门诊不能为空', {
        });
        return false;
    } */
    if (parentCode == "") {
        layer.alert('上级编号不能为空', {
        });
        return false;
    }

    if (dictName == "") {
        layer.alert('分类名称不能为空', {
        });
        return false;
    }

    if (dictCode == "") {
        layer.alert('分类编号不能为空', {
        });
        return false;
    }

    if (orderno == "") {
        layer.alert('排序号不能为空', {
        });
        return false;
    }
    
    var remark = $("#remark").val();

    // 赋值
    var param = $('#defaultForm').kqds_serialize();

    var url = 'YZDictAct/insertOrUpdate.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        layer.alert('操作成功', {
            end: function() {
                if (parent) {
                    parent.refresh();
                    parent.layer.close(frameindex);
                }
            }
        });
    }
}
</script>
</html>
