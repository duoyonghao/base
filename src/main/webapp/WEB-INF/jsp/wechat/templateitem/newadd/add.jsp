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
<title>新增页面</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/dict/dictNewAdd.css"/>
<style>
	.container{
 		padding-top:10px;
 	}
	.tableContent span.impText{
		color:#fff;
		font-size:16px;
	}	
	.tableContent{
		width:516px;
		font-size:14px;
		padding:0;
		border-top:1px solid #ddd;
		border-left:1px solid #ddd;
	}
	.tableContent td{
		padding:10px;
		border-right:1px solid #ddd;
		border-bottom:1px solid #ddd;
	}
	.tableContent tr td:first-child{
		width:20%;
	}
	.abc{
	    display: inline-block;
	    width: 65px;
	    text-align: right;
	    font-size: 13px;
	    line-height: 23px;
	}
	.def{
		display:inline-block;
		margin: 2px 0;
	}
	#content input:nth-child(1){
		width:345px;
	}
	.def input{
		border:1px solid #ddd;
		padding:0 3px;
		color:#333;
		height:23px;
		width:280px;
		font-size:13px;
	}
	.def input:focus{
		border:1px solid transparent;
		box-shadow: 0 0 8px rgb(49, 165, 247);
   		border-color: transparent;
	}
	#content{
		margin:0 auto;
		padding-left: 18px;
	}
	#example{
		margin:0 auto;
		padding-left: 18px;	
		font-size:13px;
	}
	.fixedBottomDiv{
		margin-top:5px;
	}
</style>
</head>
<body>
<div class="container"><!--提供内边距 让内容不顶头显示-->
	<form id="form1"><!-- 本身无样式  defaultForm表单验证功能要用 -->
		<table class="tableContent"><!--tableContent的样式 -->
			<tbody>
				<tr>
					<td style="background:#0E7CC9; color:#fff;"> 	<!--impText必填项样式  -->
						<span class="impText">内 容</span>
					</td>
					<td>
						<div id="content">
						
						</div>
					</td>
				</tr>
				<tr>
					<td style="background:#0E7CC9; color:#fff;"> 	<!--impText必填项样式  -->
						<span class="impText">示 例</span>
					</td>
					<td>
						<div id="example">
						
						</div>
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
var templateSeqid = $.getUrlVar('templateSeqid');
var templateId = $.getUrlVar('templateId');
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    if (templateSeqid && templateId) {
        intInfo();
    }
});

function intInfo() {
    var detailurl = "WXTemplateMsgAct/selectDetail.act?seqId=" + templateSeqid;
    var rtData = getDataFromServer(detailurl);
    if (rtData) {
        var template = rtData.data;
        var content_tmp = template.content;
        var example_tmp = template.example;
        var patt = /{{.*?\.DATA}}/g; // 正则表达式
        var paramArray = content_tmp.match(patt);
        for (var i = 0; i < paramArray.length; i++) {
            var param = paramArray[i];
            var id = param.replace("{{", "").replace(".DATA}}", "");
            
            var inputHtml = "<input id='" + id + "' name='" + id + "' />";
            if ('first' == id) {
                // inputHtml = "标题：" + inputHtml;
            }

            if ('remark' == id) {
                // inputHtml = "备注：" + inputHtml;
            }
           
            content_tmp = content_tmp.replace(param, inputHtml);
            
        }
        var contentHtml =  "标题：" + template.title + "</br>" + content_tmp;
        // 给文字加标签
        var contentArray2 = contentHtml.split("</br>");
        for (var i = 1; i < contentArray2.length; i++) {
        	var linestr = contentArray2[i];
        	if(linestr != ''){
        		var linestr4replace = "<span class='def'>" + linestr + "</span>"
            	contentHtml = contentHtml.replace(linestr,linestr4replace);
        	}
        	
        	var lineArray = linestr.split("<input ");
        	if(lineArray[0] != '' && (lineArray[0].indexOf("input") == -1)){
        		// alert(lineArray[0]);
        		var tmp4replace = "<span class='abc'>" + lineArray[0] + "</span>"
        		contentHtml = contentHtml.replace(lineArray[0],tmp4replace);
        	}
        }
        
        
        $("#content").html(contentHtml);
        
        var exampleHtml = "标题：" + template.title + "</br>" + example_tmp;
        $("#example").html(exampleHtml);
    }
}

$('#submit').on('click',
function() {
    submitForm();
});

function submitForm() {
    var first = $("#first").val();
    if (first == "") {
        layer.alert('标题不能为空' );
        return false;
    }

    var param = $('#form1').serialize();
    var url = "WXTemplateItemAct/insertOrUpdate.act?";
    url += param;
    url += "&templateId=" + templateId + "&templateSeqid=" + templateSeqid;
    var rtData = getDataFromServer(url, null);
    if (rtData.retState == 0) {
        layer.alert('新增成功！', {
              
            end: function() {
                parent.colseWindow();
            }
        });
    }
}
</script>
</html>
