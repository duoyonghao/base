<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	// 获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	
	//返回后台异常信息
	String importMsg = "";  
	if(request.getSession().getAttribute("msg")!=null){  
	   importMsg = request.getSession().getAttribute("msg").toString();
	   //out.println(importMsg);
	}
	//request.getSession().setAttribute("msg", "你好！");  
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>文件上传</title>
<!-- 系统中只有一个页面 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/yxzx/active.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/cp.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

</head>
<body>
	<div style="margin-top: 37px;margin-left: 103px;">
	    <form id="uploadFileForm" method="post" enctype="multipart/form-data" style="margin-left: -19px;width: 200px;"> <!-- class="kqdsCommonBtn" -->
			<input type="file" name="files" id="fj" style="width: 200px;" class="kqdsCommonBtn">
			<p style="margin-left: 73px;">
			   <i>点击上传</i>
			</p>
			<font id="importMsg" color="red" hidden=""><%= importMsg %></font><!-- <input type="hidden"/> --> 
		</form>
	</div>
	
	<%-- 
	<div><font color="bule">批量导入客户</font></div>
     <form action="HUDH_YkzzAct/FileUploadAct" method="post" enctype="multipart/form-data" onsubmit="return check();">
         <div style="margin: 30px;"><input id="excel_file" type="file" name="filename" accept="xlsx" size="80"/>
         <input id="excel_button" type="submit" value="导入Excel"/></div>
         <font id="importMsg" color="red"><%=importMsg%></font><input type="hidden"/>
     </form> --%>
	
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/yxzx/trcc.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/yxzx/upload.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.easyui.min.js"></script>
<script type="text/javascript">
/* function check() {  
    var excel_file = $("#excel_file").val();  
    if (excel_file == "" || excel_file.length == 0) {  
        layer.alert("请选择文件路径！");  
        return false;  
    } else {  
       return true;  
    }  
}  */

/* $(document).ready(function () { 
	 alert(111111);
     var msg="";  
     if($("#importMsg").text()!=null){  
         msg=$("#importMsg").text();  
     }  
     if(msg!=""){  
         layer.alert(msg);  
     }  
});   */


//获取文件名
$("#fj").on("change",function(){
	if($("#fj").val()!=""){
		uploadfile();
	}
});


function uploadfile(){
	var fj = $("#fj")[0].files;
	var files = $("input[type='file'][name='files']")[0].files;
	if (fj == "" || fj == undefined) {
		layer.alert("请您选择需要上传的文件");
		//$.messager.alert('提示','请您选择需要上传的文件！', "info");
		return;
	}
	var url = contextPath+'/HUDH_InvoiceDetailAct/FileUploadAct'
	//var url = contextPath+'/HUDH_DrugsFileUploadAct/uploadFile.act?module=evidence&imgtype=drugs'
	//var url = contextPath+'/FileUploadAct/uploadFile.act'
	$("#uploadFileForm").form('submit', {
        url: url,
        data: $("#uploadFileForm").serialize(),
        onSubmit : function() {
        	var files = $("input[type='file'][name='files']")[0].files;
        	var suffixes = ['doc','docx','ppt','pptx','xls','xlsx','txt','pdf','bpm','gif','jpg','jpeg','png'];
        	for(var i=0;i < files.length;i++){
        		var filename = files[i].name;
        		var file_suffix = filename.substring(filename.lastIndexOf(".")+1);
        		var flag = 0; //标记是否是合法文件后缀
        		for (var j=0;j<suffixes.length;j++) {
        			if(file_suffix.toLocaleLowerCase() == suffixes[j]){
        				flag = 1;
        				break;
        			}
        		} 
        		if(flag == 0){
        			layer.alert('操作失败，上传的文件中含有不合法的文件格式！合法文件后缀：doc、docx、ppt、pptx、xls、xlsx、txt、pdf、bpm、gif、jpg、jpeg、png！');
        			//$.messager.alert('提示','操作失败，上传的文件中含有不合法的文件格式！合法文件后缀：doc、docx、ppt、pptx、xls、xlsx、txt、pdf、bpm、gif、jpg、jpeg、png！', "info");
        			return false;
        		}
        		if(files[i].size ==0){
        			layer.alert('上传的附件不允许为空文件！');
        			//$.messager.alert('提示','上传的附件不允许为空文件！', "info");
        			return false;
        		}
        		if(files[i].size > 20 * 1024 * 1024){
        			layer.alert('上传的单个文件大小不能超过20MB！');
        			//$.messager.alert('提示','上传的单个文件大小不能超过20MB！', "info");
        			return false;
        		}
        	}
        },
        success: function() {
        	/* var msg = "";  
            if($("#importMsg").text()!=null){  
             	msg = $("#importMsg").text();  
            } */
            var msg = $("#importMsg").text();
            //alert(msg);
        	layer.alert(msg, function() {
	        	window.parent.location.reload(); //刷新父页面
	            var frameindex = parent.layer.getFrameIndex(window.name);
	            parent.layer.close(frameindex); //再执行关闭
        	});
		}
	});
}

</script>
</html>
