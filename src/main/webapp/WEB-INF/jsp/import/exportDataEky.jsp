<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<title>数据导入</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <section class="content">
            <div class="row" >
            <div class="col-sm-12">
            <div id="toolbar">
		        <!--dom结构部分-->
				<div class="child-inline-block-middle" id="uploader-demo">
					<input type="hidden"  id="imgtype"> 
				    <input type="checkbox"  name="model" value="documentuser"> 患者基本信息
				    <input type="checkbox"  name="model" value="costorderdetail"> 费用明细
				    <br>
				    <br>
					<a id="filePicker"  class="btn btn-success" style="color: #fff;"><i class="glyphicon glyphicon-plus"></i> 导入</a>
					<br>
					<br>
				</div>
   			 </div>
             </div>
          </div>
        </section>
<script>
var $table = $('#table');
$(function() {
	/** 轻松牙医数据导入 **/
    uploadfile(contextPath + "/kqds/act/thirdImport/qsyy/EkyDataImportAct/dataImport.act?module=evidence");
});

//禁止页面选择，防止数据拷贝导出
$(function() {
    document.onselectstart = function() {
        return true;
    };
});
$("input[name='model']").click(function(){
	$(this).attr('checked','checked').siblings().removeAttr('checked');
	$("input[name='model']").each(function(){
	    if ($(this).is(':checked')) { 
	    	$("#imgtype").val($(this).val());
    	}
	});
});
</script>
</body>
</html>
