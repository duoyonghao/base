<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	YZPerson person = SessionUtil.getLoginPerson(request);

	String currPersonId = person.getSeqId();
	String perPriv = person.getUserPriv();
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
.dzbl-context{
	margin-left:2%;
	width: 96%;
	text-align: center;
}
.bootstrap-table{
	margin-top:5px;
}
.topInfo-font{
	color:#FA6406;
}
.topInfo ul{
	width:100%;
	/* height: 30px; */
	display: flex;
    flex-direction: row;
}
.topInfo ul>li{
	width:24%;		
	margin-right:2%;
}
.topInfo ul>li select{
	/* width:60%; */
	float: left;
	max-width: 60%;
}
.topInfo ul>li font{
	float: left;
	line-height: 28px;
	margin-right:3%;
}
.kqdsSearchBtn{
	padding:0px;
	width:50%;
	text-align: center;
}
.layui-layer-setwin{
   display: none;
}
</style>
</head>
<body>
<div id="container">
	 <div class="dzbl-context">
	 	<div class="topInfo" style="text-align: left;">
        		<ul style="margin-top: 10px;margin-bottom: 0px;">
        			<li style="display: none;"><input id="blId"/></li>
        			<li style="display: none;"><input id="blStatus"/></li>
        			<li><font class="topInfo-font">病历分类</font>
        				<select class="dict" name="blfl" id="blfl">
        				<option value="">=请选择=</option>
        				<option value="牙齿种植">牙齿种植</option>
        				<option value="牙齿修复">牙齿修复</option>
        				<option value="综合科">综合科</option>
        				<option value="综合科（西院）">综合科（西院）</option>
        			</select>
        			</li>
        			<li><font class="topInfo-font">手术分类</font>
        				<select class="dict" name="sstype" id="sstype">
        				<option value="">请选择</option>
        				<!-- <option value="局部种植">局部种植</option>
        				<option value="全口/半口/即刻用">全口/半口/即刻用</option>
        				<option value="全口Locator">全口Locator</option> -->
        			</select>
        			</li>
        			<li><font class="topInfo-font">病&ensp;&ensp;&ensp;&ensp;程</font>
        				<select class="dict" name="bc" id="bc">
        				<option value="">请选择</option>
        			</select>
        			</li>
        			<li>
        				<div class="buttonBar" id="buttommenu" style="margin:0px;">
          					<a href="javascript:void(0);" onclick="buttonFun.searchTemple();" class="kqdsSearchBtn" id="searchTemple">确定</a>
          				</div>
        			</li>
        		</ul>
     	</div>
     	
     	<div id="selecttTemple">
     		<!-- <textarea id="templeDetail"></textarea> -->
     		<!-- <div id="myEditor" class="flag" style="text-align:left;width:800px;height:240px;border:1px;border-color: red;background-color: #f5f5f5" contenteditable="true"> </div>-->
     		<div style="width: 100%;height: 250px;">
     			<iframe frameborder="no"  style="width: 100%;height: 100%;" id="detail_iframe" class="detail_iframe" src=""></iframe>
     		</div>
     		<div class="buttonBar center-btn" id="dzblBtn">
     			<a href="javascript:void(0);" onclick="buttonFun.btnSave()" class="kqdsCommonBtn" id="save">暂存病历</a>
          		<a href="javascript:void(0);" onclick="buttonFun.btnSubmit()" class="kqdsCommonBtn" id="submit">提交病历</a>
          	</div>
     	</div>
     	
     	<div class="biliHistory">
            <div class="tableHd">历史病历【双击查看详情】</div>
            <table id="table" class="table-striped table-condensed table-bordered" data-height="350"></table>
        </div>
	 </div>
     
</div></body>
<script type="text/javascript">
var onclickrowOobj = "";
var perPriv = '<%=perPriv%>';//获取当前用户角色Id
var apppath = apppath();
var lcljAdminOrAgency = "";
var currPersonId = '<%=currPersonId%>';
$(function() {
    onclickrowOobj = window.parent.onclickrowOobj;
   /*  alert(onclickrowOobj.usercode); */
   //判断当前登录人是否有操作电子病历的权限
   checkIsOptAuth();
   //获取是否临床路径管理员
   findLcljAdminOrAgency();
   //加载历史病历列表
   gethisbl();
   //病历分类改变后获取对应的病程列表
   $("#blfl").on("change",function (){
	   /* initBcList(); */
	   cleanSelect($("#sstype"));
	   cleanSelect($("#bc"));
	   if($(this).val() == "牙齿种植" || $(this).val() == "牙齿修复") {
		   $("#sstype").append('<option value="局部种植">局部种植</option>');
		   $("#sstype").append('<option value="全口/半口/即刻用">全口/半口/即刻用</option>');
		   $("#sstype").append('<option value="全口Locator">全口Locator</option>');
		   $("#sstype").append('<option value="(新)局部种植">(新)局部种植</option>');
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
   
   $("#detail_iframe").attr("src",apppath + "/static/js/hudh/dzbl/dzbl_detail.html");
   
   
   $("#sstype").on("change",function (){
	   initBcList();
   });
  
   if(isStrInArrayStringEach(perPriv, "de063632-8dc5-45f4-a616-b27ada252406")){ //只有等于HUDH_系统管理员角色时
  	   $("#dzblBtn").append('<a href="javascript:void(0);" onclick="buttonFun.btnAdd()" class="kqdsCommonBtn" id="insert">新建模板</a>');
   }
});

//按钮方法
var buttonFun = {
		//根据选择获取病例模板
		searchTemple : function (){
			$.ajax({
				url: apppath + '/HUDH_DzblAct/findTemplateByBlflAndBc.act',
				type:"POST",
				dataType:"json",
				data : {"blfl":$("#blfl").val(),"bc":$("#bc").val(),"sstype":$("#sstype").val()},
				success:function(result){
					if(result.id) {
						$("#detail_iframe").attr("src",apppath + "/static/js/hudh/dzbl/dzbl_detail.html?blId=" + result.id);
					}else {
						$("#detail_iframe").contents().find("#detail").html("");
						layer.alert("该病程下暂无模板内容");
					}
					
				}
			});
			// 2019-9-25 lutian 解除暂存病历，提交病历按钮的禁用
			$("#save").removeAttr("disabled").css("color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer");
			$("#submit").removeAttr("disabled").css("color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer");
		},
		
		//暂存病历
		btnSave : function (){
			//必填项检验
			var blfl = $("#blfl").val(); //病历分类
			var sstype = $("#sstype").val(); //手术分类
			var bc = $("#bc").val(); //病程分类
			var detail = $("#detail_iframe").contents().find("#detail").html(); //病历内容
			if(!blfl){layer.alert("病历分类不能为空");return;}
			if(!sstype){layer.alert("手术分类不能为空");return;}
			if(!bc){layer.alert("病程不能为空");return;}
			if(!detail){layer.alert("病历内容不能为空");return;}
			
			//判断是否是生效的病例
			var blStatus = $("#blStatus").val();
			if(blStatus == "1") {
				layer.alert("修改已提交的病历请直接提交");
				return;
			}
			//获取子页面的病例内容
			var detail =$("#detail_iframe").contents().find("#detail").html();
			layer.alert('确认暂存此病例？', {
				  closeBtn: 1    // 是否显示关闭按钮
				  ,anim: 1 //动画类型
				  ,btn: ['确认','关闭'] //按钮
				  ,yes:function(index){
					  	/* 请求一次并禁用此提交按钮 */
						$("#detail_iframe").contents().find("#detail").html("");
						$("#save").attr("disabled","disabled").css("color","#818080").css("border","1px solid #818080").css("cursor","no-drop");
						
					  $.ajax({
							url: apppath + '/HUDH_DzblAct/insertDzblDetail.act',
							type:"POST",
							dataType:"json",
							data : { "id" : $("#blId").val(),
									 "sstype" : $("#sstype").val(),
								     "blcode":onclickrowOobj.usercode,
									 "blfl":$("#blfl").val(),
								     "bc":$("#bc").val(),
								     "title":$("#title").val(),
								     "templeDetail":detail,
								     "type":"0"
								   },
							success:function(result){
								layer.alert('暂存成功', function(index) {
									//获取初始化备注列表数据
									//emptyInfo();
									// window.location.reload(); //刷新父页面
									$("#blfl").val("");
									$("#sstype").val("");
									$("#bc").val(""); 
									searchLsbl();
									layer.close(index);
								})
								setTimeout(function(){
									$("#save").removeAttr("disabled").css("color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer");
								}, 60000);
							}
						});
				  }
				  ,btn2:function(){}
			})
		},
		//提交病历
		btnSubmit : function (){
			//必填项检验
			var blfl = $("#blfl").val(); //病历分类
			var sstype = $("#sstype").val(); //手术分类
			var bc = $("#bc").val(); //病程分类
			var detail = $("#detail_iframe").contents().find("#detail").html(); //病历内容
			if(!blfl){layer.alert("病历分类不能为空");return;}
			if(!sstype){layer.alert("手术分类不能为空");return;}
			if(!bc){layer.alert("病程不能为空");return;}
			if(!detail){layer.alert("病历内容不能为空");return;}
			
			//获取子页面的病例内容
			var detail =$("#detail_iframe").contents().find("#detail").html();
			layer.alert('确认提交？', {
				  closeBtn: 1    // 是否显示关闭按钮
				  ,anim: 1 //动画类型
				  ,btn: ['确认','关闭'] //按钮
				  ,yes:function(index){
					  	/* 请求一次并禁用此提交按钮 */
						$("#detail_iframe").contents().find("#detail").html("");
						$("#submit").attr("disabled","disabled").css("color","#818080").css("border","1px solid #818080").css("cursor","no-drop");
					  
					  $.ajax({
							url: apppath + '/HUDH_DzblAct/insertDzblDetail.act',
							type:"POST",
							dataType:"json",
							data : { "id" : $("#blId").val(),
								     "sstype" : $("#sstype").val(),
									 "blcode":onclickrowOobj.usercode,
								     "blfl":$("#blfl").val(),
								     "bc":$("#bc").val(),
								     "title":$("#title").val(),
								     "templeDetail":detail,
								     "type":"1"
								   },
							success:function(result){
								layer.alert('提交成功', function(index) {
									//获取初始化备注列表数据
									//emptyInfo();
									// window.location.reload(); //刷新父页面
									$("#blfl").val("");
									$("#sstype").val("");
									$("#bc").val("");
									searchLsbl();
									layer.close(index);
								})
								setTimeout(function(){
									$("#submit").removeAttr("disabled").css("color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer");
								}, 60000);
							}
						});
				  }
				  ,btn2:function(){}
			})
			
		},
		//新建病历
		btnAdd : function (){
			layer.open({
		        type: 2,
		        title: '新建病历模板',
		        shadeClose: true,
		        shade: 0.6,
		        area: ['90%', '50%'],
		        content: apppath+'/HUDH_DzblViewAct/toAddDzbl.act'
		    });
		},
		//暂存病历在编辑
		editBl : function (id,blfl,bc,detail,sstype,status){
			$("#blId").val(id);
			$("#blStatus").val(status);
			$("#blfl").val(blfl);
			$("#blfl").trigger("change");
			$("#sstype").val(sstype);
			$("#sstype").trigger("change");
			initBcList();
			$("#bc").val(bc);
			$("#detail_iframe").contents().find("#detail").html(detail);
			// 2019-9-25 lutian 解除暂存病历，提交病历按钮的禁用
			$("#save").removeAttr("disabled").css("color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer");
			$("#submit").removeAttr("disabled").css("color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer");
		}
	}

//查询按钮
function searchLsbl() {
	var pageurlhis = apppath+'/HUDH_DzblAct/findDzblByBlcode.act?blCode=' + onclickrowOobj.usercode;
	$('#table').bootstrapTable('refresh', {
        'url': pageurlhis
    });
}

function gethisbl() {
    var pageurlhis = apppath+'/HUDH_DzblAct/findDzblByBlcode.act?blCode=' + onclickrowOobj.usercode;
    $("#table").bootstrapTable({
        url: pageurlhis,
        dataType: "json",
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        },
        onDblClickCell: function(field, value, row, td) {
            detail(row.id,onclickrowOobj.usercode);
        },
        columns: [{
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '病历分类',
            field: 'blfl',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '病程',
            field: 'bc',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: 'id',
            field: 'id',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>"
            }
        },
        {
            title: '创建人',
            field: 'username',
            align: 'center',
            sortable: true,
            editable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
            	 //只有临床路径管理员及代办人或者自己的暂存的病例可以修改
            	if(lcljAdminOrAgency == "YES" || (lcljAdminOrAgency != "YES" && row.status == "0"  && row.creator == currPersonId)) {
            		return '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="buttonFun.editBl(\'' + row.id + '\',\'' + row.blfl + '\',\'' + row.bc + '\',\'' + row.detail + '\',\'' + row.sstype + '\',\'' + row.status + '\')">&nbsp;编辑</a>'
            	}
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
    });
}

/**
 * 清空输入的信息
 */
function emptyInfo(){
	$("#blfl").val("");
	$("#bc").val("");
	$("#templeDetail").val("");
}

/**
 * 初始化病程下拉列表
 */
function initBcList (){
	var blfl = $("#blfl").val();
	var sstype = $("#sstype").val();
	$.ajax({
			url: apppath + '/HUDH_DzblAct/findBcByBlfl.act',
			type:"POST",
			dataType:"json",
			data : {"blfl":blfl,"sstype":sstype},
			async: false,
			success:function(result){
				$("#bc").find("option").remove(); 
				var $bc = '<option value="">=请选择=</option>';
				var list = result.list;
				if(list) {
					for(var i in list){
						$bc += '<option value="'+list[i].bc+'">'+list[i].bc+'</option>';
					}
				}
				$("#bc").append($bc);
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

function detail(blId,blCode){
	parent.layer.open({
        type: 2,
        title: '详情',
        shadeClose: true,
        shade: 0.6,
        area: ['70%', '55%'],
        content: apppath+'/HUDH_DzblViewAct/toBlDetail.act?blId='+blId+'&blCode='+blCode
    });
}

/**
 * 查找当前人员是否当前管理员
 */
function findLcljAdminOrAgency(){
	$.ajax({
		url: apppath + '/HUDH_FlowAct/findLcljAdminOrAgency.act',
		type:"POST",
		dataType:"json",
		async:false, 
		data : {},
		success:function(result){
			lcljAdminOrAgency = result.hasPass;
		}
	});
}

/**
 * 判断当前登录人员是否有电子病历修改的权限
 */
function checkIsOptAuth(){
	$.ajax({
		url: apppath + '/HUDH_DzblAct/selectHasDzblOptAuth.act',
		type:"POST",
		dataType:"json",
		async:false, 
		data : {},
		success:function(result){
			if(result.isOk == "false") {
				$("#save").remove();
				$("#submit").remove();
			}
		}
	});
}
</script>
</html>