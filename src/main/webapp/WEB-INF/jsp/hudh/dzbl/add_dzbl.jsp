<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>病历</title><!-- 右侧个人中心  中心 病历图标 点击进入的 主页面  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/datepicker/datepicker3.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/history_record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<style type="text/css">
html{
	height:100%;
}
body{
	height:100%;
}
#container{
	height:100%;
}
.dzbl-context{
	margin-left:2%;
	width: 96%;
	text-align: center;
	height:100%;
}
.topInfo{
	height: 50px;
	line-height: 50px;
}
.topInfo-font{
	color:#FA6406;
}
.templeInfo{
	height: 95%;
}
textarea{
	width:100%;
	height: 100%;
}
.center-btn{
	margin-top:5px;
	text-align: right;
}
.bootstrap-table{
	margin-top:5px;
}
.templeInfo{
	height: 70%;
}
.buttonBar{
	text-align: right;
	margin-top: 10px;
}
</style>
</head>
<body>
<div id="container">
	 <div class="dzbl-context">
	 	<div class="topInfo" style="text-align: left">
        	<table>
        		<tr>
        			</td>
        			<td><font class="topInfo-font">*标题</font>
        				<input type="text" id="title" name="title" value="" style="width:80px;">
        			</td>
        			<td><font class="topInfo-font">*病历分类</font>
        				<select class="dict" name="blfl" id="blfl" style="width:100px">
        				<option value="">=请选择=</option>
        				<option value="牙齿种植">牙齿种植</option>
        				<option value="牙齿修复">牙齿修复</option>
        				<option value="综合科">综合科</option>
        				<option value="综合科（西院）">综合科（西院）</option>
        			</select>
        			</td>
        			<td><font class="topInfo-font">手术分类</font>
        				<select class="dict" name="sstype" id="sstype" style="width:100px">
        				<option value="">=请选择=</option>
        				<!-- <option value="局部种植">局部种植</option>
        				<option value="全口All-on-6螺丝固位">全口All-on-6螺丝固位</option>
        				<option value="全口Locator">全口Locator</option> -->
        			</select>
        			</td>
        			<td><font class="topInfo-font">病程</font>
        				<select class="dict"  name="bc" id="bc" style="width:100px">
        				<option value="">=请选择=</option>
        				<!-- <option value="牙齿种植">术前取模</option>
        				<option value="牙齿种植">术前取模</option>
        				<option value="牙齿修复">种植后固定义齿</option>
        				<option value="牙齿种植">拆线</option>
        				<option value="牙齿种植">复诊清洗义齿</option>
        				<option value="牙齿种植">复诊取模（闭口式印模）</option>
        				<option value="牙齿种植">复诊取模（开口式印模）</option>
        				<option value="牙齿种植">试戴</option>
        				<option value="牙齿种植">戴牙</option> -->
        			</select>
        			</td>
        		</tr>
       		</table>
     	</div>
     	<div class="templeInfo"><textarea id="templeDetail"></textarea></div>
     	<div class="buttonBar" id="buttommenu" >
          	<a href="javascript:void(0);" onclick="submit();" class="kqdsSearchBtn" id="submit">保存</a>
        </div>
</div></body>
<script type="text/javascript">
var apppath = apppath();
$(function() {
	/* $("#blfl").on("change",function (){
		var blfl = $(this).val();
		$("#sstype").find("option").remove(); 
		$("#sstype").append('<option value="">=请选择=</option>');
		if(blfl=="牙齿修复"){
			$("#sstype").append('<option value="局部种植">局部种植</option>');
			$("#sstype").append('<option value="全口/半口/即刻用">全口/半口/即刻用</option>');
			$("#sstype").append('<option value="全口Locator">全口Locator</option>)');
		}
	}); */
	
	//病历分类改变后获取对应的病程列表
	   $("#blfl").on("change",function (){
		   /* initBcList(); */
		   cleanSelect($("#sstype"));
		   cleanSelect($("#bc"));
		   if($(this).val() == "牙齿种植" || $(this).val() == "牙齿修复") {
			   $("#sstype").append('<option value="局部种植">局部种植</option>');
			   $("#sstype").append('<option value="全口/半口/即刻用">全口/半口/即刻用</option>');
			   $("#sstype").append('<option value="全口Locator">全口Locator</option>');
		   }else if($(this).val() == "综合科") {
			   $("#sstype").append('<option value="补牙">补牙</option>');
			   $("#sstype").append('<option value="急性根尖周炎">急性根尖周炎</option>');
			   $("#sstype").append('<option value="急性牙髓炎">急性牙髓炎</option>');
			   $("#sstype").append('<option value="慢性根尖周炎">慢性根尖周炎</option>');
			   $("#sstype").append('<option value="慢性牙髓炎Ⅰ">慢性牙髓炎Ⅰ</option>');
			   $("#sstype").append('<option value="慢性牙髓炎Ⅱ">慢性牙髓炎Ⅱ</option>');
			   $("#sstype").append('<option value="慢性牙周炎">慢性牙周炎</option>');
			   $("#sstype").append('<option value="慢性龈缘炎">慢性龈缘炎</option>');
			   $("#sstype").append('<option value="浅龋">浅龋</option>');
			   $("#sstype").append('<option value="中龋">中龋</option>');
			   $("#sstype").append('<option value="深龋">深龋</option>');
			   $("#sstype").append('<option value="牙列缺失">牙列缺失</option>');
			   $("#sstype").append('<option value="牙周—牙髓联合病变">牙周—牙髓联合病变</option>');
			   $("#sstype").append('<option value="要求拔牙">要求拔牙</option>');
			   $("#sstype").append('<option value="智齿冠周炎">智齿冠周炎</option>');
		   }else if($(this).val() == "综合科（西院）") {
			   $("#sstype").append('<option value="补牙">补牙11111</option>');
			   $("#sstype").append('<option value="急性根尖周炎">急性根尖周炎</option>');
			   $("#sstype").append('<option value="急性牙髓炎">急性牙髓炎</option>');
			   $("#sstype").append('<option value="慢性根尖周炎">慢性根尖周炎</option>');
			   $("#sstype").append('<option value="慢性牙髓炎Ⅰ">慢性牙髓炎Ⅰ</option>');
			   $("#sstype").append('<option value="慢性牙髓炎Ⅱ">慢性牙髓炎Ⅱ</option>');
			   $("#sstype").append('<option value="慢性牙周炎">慢性牙周炎</option>');
			   $("#sstype").append('<option value="慢性龈缘炎">慢性龈缘炎</option>');
			   $("#sstype").append('<option value="浅龋">浅龋</option>');
			   $("#sstype").append('<option value="中龋">中龋</option>');
			   $("#sstype").append('<option value="深龋">深龋</option>');
			   $("#sstype").append('<option value="牙列缺失">牙列缺失</option>');
			   $("#sstype").append('<option value="牙周—牙髓联合病变">牙周—牙髓联合病变</option>');
			   $("#sstype").append('<option value="要求拔牙">要求拔牙</option>');
			   $("#sstype").append('<option value="智齿冠周炎">智齿冠周炎</option>');
		   }
	   });
	
	$("#sstype").on("change",function (){
		var sstype = $(this).val();
		$("#bc").find("option").remove(); 
		$("#bc").append('<option value="">=请选择=</option>');
		if(sstype == "局部种植") {
			$("#bc").append('<option value="术前取模">术前取模</option>');
			$("#bc").append('<option value="术后戴牙">术后戴牙</option>');
			$("#bc").append('<option value="拆线">拆线</option>');
			$("#bc").append('<option value="二期">二期</option>');
			$("#bc").append('<option value="取模">取模</option>');
			$("#bc").append('<option value="戴牙">戴牙</option>');
			$("#bc").append('<option value="病程">病程</option>');
		}else if(sstype == "全口/半口/即刻用"){
			$("#bc").append('<option value="术前取模">术前取模</option>');
			$("#bc").append('<option value="种植术后固定义齿">种植术后固定义齿</option>');
			$("#bc").append('<option value="拆线">拆线</option>');
			$("#bc").append('<option value="复诊清洗义齿">复诊清洗义齿</option>');
			$("#bc").append('<option value="复诊取模（闭口式印模）">复诊取模（闭口式印模）</option>');
			$("#bc").append('<option value="复诊取模（开口式印模）">复诊取模（开口式印模）</option>');
			$("#bc").append('<option value="试戴">试戴</option>');
			$("#bc").append('<option value="戴牙">戴牙</option>');
			$("#bc").append('<option value="病程">病程</option>');
		}else if(sstype == "全口Locator") {
			$("#bc").append('<option value="种植术前取模">种植术前取模</option>');
			$("#bc").append('<option value="种植术后固定义齿">种植术后固定义齿</option>');
			$("#bc").append('<option value="拆线">拆线</option>');
			$("#bc").append('<option value="复诊清洗义齿">复诊清洗义齿</option>');
			$("#bc").append('<option value="最终牙取模定咬合">最终牙取模定咬合</option>');
			$("#bc").append('<option value="试戴">试戴</option>');
			$("#bc").append('<option value="戴牙">戴牙</option>');
			$("#bc").append('<option value="病程">病程</option>');
		}else if(sstype == "急性根尖周炎") {
			$("#bc").append('<option value="急性根尖周炎1">急性根尖周炎1</option>');
			$("#bc").append('<option value="急性根尖周炎2">急性根尖周炎2</option>');
			$("#bc").append('<option value="急性根尖周炎3">急性根尖周炎3</option>');
		}else if(sstype == "急性牙髓炎") {
			$("#bc").append('<option value="急性牙髓炎1">急性牙髓炎1</option>');
			$("#bc").append('<option value="急性牙髓炎2">急性牙髓炎2</option>');
		}else if(sstype == "慢性牙髓炎Ⅰ") {
			$("#bc").append('<option value="慢性牙髓炎1">慢性牙髓炎1</option>');
			$("#bc").append('<option value="慢性牙髓炎2">慢性牙髓炎2</option>');
		}else if(sstype == "牙列缺失") {
			$("#bc").append('<option value="牙列缺失1">牙列缺失1</option>');
			$("#bc").append('<option value="牙列缺失2">牙列缺失2</option>');
			$("#bc").append('<option value="牙列缺失3">牙列缺失3</option>');
		}else if(sstype == "牙周—牙髓联合病变") {
			$("#bc").append('<option value="牙周—牙髓联合病变1">牙周—牙髓联合病变1</option>');
			$("#bc").append('<option value="牙周—牙髓联合病变2">牙周—牙髓联合病变2</option>');
		}else { //处理只有一种分类和sstype一致
			$("#bc").append('<option value="'+sstype+'">'+sstype+'</option>');
		}
	});
});

function submit (){
	var title = $("#title").val();
	var blfl = $("#blfl").val();
	var bc = $("#bc").val();
	var sstype = $("#sstype").val();
	var templeDetail = getFormatCode($("#templeDetail").val());
	
	if(!title){layer.alert("请填写打印标题");return;}
	if(!blfl){layer.alert("请填写病历分类");return;}
	if(!templeDetail){layer.alert("模板内容为空");return;}
	
	$.ajax({
		url: apppath + "/HUDH_DzblAct/findTemplateByBlflAndBc.act",
		type:"POST",
		dataType:"json",
		data : {"blfl":blfl,"bc":bc,"sstype":sstype},
		success:function(result){
			if(result.id) {
				layer.alert('当前病程下已存在模板，点击确定将覆盖原有模板!', {
					  closeBtn: 1    // 是否显示关闭按钮
					  ,anim: 1 //动画类型
					  ,btn: ['确认','关闭'] //按钮
					  ,yes:function(index){
						  $.ajax({
								url: apppath + "/HUDH_DzblAct/saveBlTemple.act",
								type:"POST",
								dataType:"json",
								data : {"title":title,"blfl":blfl,"bc":bc,"templeDetail":templeDetail,"sstype":sstype},
								success:function(result){
									layer.alert(result.retMsrg, {
							              end: function() {
							            	  //window.parent.location.reload(); //刷新父页面
							                  var frameindex = parent.layer.getFrameIndex(window.name);
							                  parent.layer.close(frameindex); //再执行关闭
							              }
							        });
								}
							});
					  }
					  ,btn2:function(){}
				})
			} else {
				$.ajax({
					url: apppath + "/HUDH_DzblAct/saveBlTemple.act",
					type:"POST",
					dataType:"json",
					data : {"title":title,"blfl":blfl,"bc":bc,"templeDetail":templeDetail,"sstype":sstype},
					success:function(result){
						layer.alert(result.retMsrg, {
				              end: function() {
				            	  //window.parent.location.reload(); //刷新父页面
				                  var frameindex = parent.layer.getFrameIndex(window.name);
				                  parent.layer.close(frameindex); //再执行关闭
				              }
				        });
					}
				});
			}
		}
	});
}

/**
 * 清空指定下拉框的选项
 */
function cleanSelect(obj){
	obj.empty();
	obj.append("<option value=''>请选择</option>");
}
</script>
</html>