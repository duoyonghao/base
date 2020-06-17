<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
	if(usercode == null ){
	 usercode = "";  // 配合js 的非空判断，这里不这样写，则js 需进行  usercode != "null"的判断
	}
	//网电预约Id 挂号时选择的时网电预约记录，挂号后把该预约设为已上门
	String netorderid = request.getParameter("netorderid");
	if(netorderid == null ){
	 netorderid = "";  
	}
	String orderno = request.getParameter("orderno");
	if(orderno == null ){
		orderno = "";  
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta charset="utf-8" />
<title>就诊挂号</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reg/reg.css" /> --%>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/huizheng_info.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/admin.css">
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style>
	.tableLayout{
		width:100%
	}
	.colorRed {
	    color: #ed4308;
	    position: absolute;
	    top: -1px;
	    left: -5px;
	    font-size: 20px;
	}
	.operatedDiv .commonText {
	    display: block;
	    margin-left: 15px;
	    text-align: right;
	    position: relative;
	    padding-left: 5px;
	}
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
	.tooth-location{
		height:300px
	}
	.textear{
		border-radius: 0.5em;
	}
	
	/* 辅助角色 下拉框样式调整*/
	.muli_select>div{
		height:28px;
		width:400px !important;
	
	}
	.muli_select button{/* 辅助橘色 下拉按钮 框架实现 需要特别调整 */
		padding:4px 10px 3px;;
		color:#333;
		border:1px solid #ddd;
		height:28px;
	}
	.preparation-ul li{
		float:left;
		list-style: none;
	}
	.preparation-ul li label input{
		margin-left: 10px;
	}
	/* #operationBefore_form{
		border:1px solid red;
	} */
	.preparation-ul label{
		margin-bottom: 10px;
		margin-right: 5px;
	}
	input{
		vertical-align: text-top;
	}
	
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 130px;   
	      }  
	.searchSelect>.btn { 
		    width: 130px; 
		 	height:28px; 
		 	border: solid 1px #e5e5e5; 
		}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
		    color: #999;
		    height: 28px;
		}
	.pull-left {
	    float: left !important;
	    color: #333;
		}
	.btn-group > .btn:first-child:hover {
	    background: white;
	}
	.searchWrap .text {
		    position: static !important; 
		    left: 0px;
		    top: 9px;
		    color: rgb(31, 32, 33);
		}
		.content{
	padding:20px 20px 0 20px;
}
a{
	text-decoration:none;
	color:#333;
}
a:hover{
	text-decoration:none;
}

.tableLayout{/*table布局 */
	margin:0 auto;
	border-collapse:separate;
	font-size:13px;
	width:100%;
}
.tableLayout tr{/*行高 用来调整行与行之间的距离 */
	
}
header{
	padding:0 20px 10px 0;
	margin-bottom:5px;
	border-bottom:1px solid #ddd;
}
select,input[type="text"]{/*普通select inputsss */
	box-sizing:border-box;
	width:100px;
	color:#333;
	height:28px;
	border-radius:4px;
	transition:box-shadow linear 300ms;
}
select{
	cursor:pointer;
}
input[type="text"]:focus,select:focus,textarea:focus{
    box-shadow: 0 0 8px rgb(49, 165, 247);
    border-color:transparent;
}
input[readonly]:focus{
    box-shadow: none;
    border-color:#ddd;
}
input[type="text"].greyInp{/* 灰色输入框 */
	background:#eee;
	cursor:pointer;
}
input[type="text"].whiteInp{/*白色输入框 */
	cursor:pointer;
}
input[readonly]{
	cursor: not-allowed;
}

select[disabled]{
	cursor: not-allowed;
}
/* 本页面 所有按钮的基础样式 */
.btnCommon{
	display:inline-block;
	color:#0E7CC9;
	background:#fff;
	height:28px;
	width:90px;
	line-height:28px;
	border-radius:16px;
	text-align:center;
	padding:0 5px;
	border:1px solid #0E7CC9;
	margin:0 10px;
	font-size:13px;
}
.btnCommon:hover{
	cursor:pointer;
	color:#fff;
	background:#0E7CC9;
}
						/*header部分  */
/*首行 查询按钮样式 */
a.queryBtn,button.queryBtn{
	float:left;
	height:26px;
	line-height:26px;
	margin-left:20px;
	margin-top:1px;
}
/*首行  查询下拉选项 */
select.querySel{
	width:90px;
	border-top-right-radius:0;
	border-bottom-right-radius:0;
	float:left;
	color:#666;
	padding-left:5px;
}
/*首行  查询内容 */
input[type="text"].queryInp{
	width:200px;
	border-top-left-radius:0;
	border-bottom-left-radius:0;
	border-left-color:rgba(255,255,255,0);
	float:left;
	color:#666;
}

/* 查询清单 及 用户选择区*/
section{
	padding-bottom:5px;
}

.table-striped > tbody > tr:nth-child(odd) {/* 清单中信息 奇数行为蓝色底   */
    background: #eef9fe;
}

.operatedDiv table.tableLayout tbody tr{/* 操作区域选项中的table的  行高 */
	height:35px;
}


/* footer */
footer{/* 底部 按钮组 */
	padding-top:15px;
	/* min-height:30px; */
	text-align:left;
	border-top:1px solid #00A6C0;
}
footer a.btnCommon{/* 底部按钮在基础按钮样式btn.Common的基础上 调整了左右距离 */
	margin:0 3px;
}
.bootstrap-select .dropdown-toggle:focus {
	outline: 0px dotted #333333 !important;
	outline: 0px auto -webkit-focus-ring-color !important;
	outline-offset: -2px;
}
</style>
</head>
<body>
		<header style="margin-top:10px;">
			<table class="tableLayout"><!--.tableLayout布局 宽度撑满父元素  -->
				<tbody>
					<tr>
						<td colspan="8">
							<select id="searchField" class="querySel"><!-- .querySel查询中 的下拉框  -->
								<option value="username">姓名</option>
								<option value="PhoneNumber1">手机号码</option>
								<option value="idcardno">身份证号</option>
								<option value="usercode">患者编号</option>
							</select>
							<input class="queryInp" type="text" id="searchValue" name="searchValue"/>	<!--.queryInp查询中的Input样式  -->
							<a id="searchHzda" href="javascript:void(0);" style="float:left;margin-top:1px;margin-left:10px;" class="kqdsSearchBtn" onclick="searchHzda()">查 询</a>	<!-- .btnCommon自定义按钮通用样式 .queryBtn查询按钮样式的调整 -->
						</td>
					</tr>
				</tbody>
			</table>
		</header>
		
		<section>
			<table id="table" class="table-striped table-condensed table-bordered" data-height="180"></table> <!--无自定义样式 此行样式为框架样式   -->
			
			<div class="operatedDiv">	<!-- 所有下拉框的父元素 用来布局 -->
				<form id="form">
					<table class="tableLayout">	<!--.operatedDiv下的 .tableLayout有自己特有的样式-->
						<tbody>
							<tr class="condition">
								<td>
					            	 <!--.commonText“就诊分类”文本的样式 提供布局  -->	<!--.colorRed 星号为红色及位置调整 -->
									 <!-- <span class="commonText"><i class="colorRed">*</i>咨&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;师：</span>	 -->
									 <span class="commonText"><i class="colorRed">*</i>助理：</span>	
								</td>
								<td>	<!--.dict 本身无样式 与载入数据功能有关 -->
									<select id="counsellor" name="counsellor" class="dict searchSelect"  data-live-search="true" title="请选择" data-bv-notempty data-bv-notempty-message="咨询师"></select>									
									<!-- <input class="whiteInp" type="text" id="counsellor" name="counsellor" placeholder="咨询师" readonly onClick="javascript:single_select_user(['receiveno', 'counsellor'],'single');" />-->
<!-- 									 <select class="dict" name="counsellor" id="counsellor" data-bv-notempty data-bv-notempty-message="咨询师"></select> -->
								</td>
							
								<td>
					            	 <!--.commonText“就诊分类”文本的样式 提供布局  -->	<!--.colorRed 星号为红色及位置调整 -->
									 <span class="commonText"><!-- <i class="colorRed">*</i> -->种植医师：</span>	
								</td>
								<td>	<!--.dict 本身无样式 与载入数据功能有关 -->
									<!-- <input class="whiteInp" type="text" id="plant_physician" name="plant_physician" placeholder="种植医师" onClick="javascript:single_select_user(['receiveno', 'plant_physician'],'single');" oninput="value=value.replace(/[^\d]/g,'')"/> -->
									<select id="plant_physician" name="plant_physician" tig="" class="dict searchSelect" data-bv-notempty data-bv-notempty-message="种植医师"  data-live-search="true" title="请选择"></select>
<!-- 									<select class="dict" name="plant_physician" id="plant_physician" data-bv-notempty data-bv-notempty-message="种植医师"></select> -->
								</td>
								
								<td>
					            	 <!--.commonText“就诊分类”文本的样式 提供布局  -->	<!--.colorRed 星号为红色及位置调整 -->
									 <span class="commonText"><!-- <i class="colorRed">*</i> -->修复医师：</span>	
								</td>
								<td>	<!--.dict 本身无样式 与载入数据功能有关 -->
									<!-- <input class="whiteInp" type="text" id="repair_physician" name="repair_physician" placeholder="修复医师" onClick="javascript:single_select_user(['receiveno', 'repair_physician'],'single');" oninput="value=value.replace(/[^\d]/g,'')"/> -->
									<select id="repair_physician" name="repair_physician" tig="" class="dict searchSelect" data-bv-notempty data-bv-notempty-message="修复医师" data-live-search="true" title="请选择"></select>
<!-- 									<select class="dict" name="repair_physician" id="repair_physician" data-bv-notempty data-bv-notempty-message="修复医师"></select> -->
								</td>
								<td>
					            	 <!--.commonText“就诊分类”文本的样式 提供布局  -->	<!--.colorRed 星号为红色及位置调整 -->
									 <span class="commonText"><!-- <i class="colorRed">*</i> -->诊室护士：</span>	
								</td>
								<td>	<!--.dict 本身无样式 与载入数据功能有关 -->
									<!-- <input class="whiteInp" type="text" id="clinic_nurse" name="clinic_nurse" placeholder="诊室护士" onClick="javascript:single_select_user(['receiveno', 'clinic_nurse'],'single');" oninput="value=value.replace(/[^\d]/g,'')"/> -->
									<select id="clinic_nurse" name="clinic_nurse" tig="" class="dict searchSelect" data-bv-notempty data-bv-notempty-message="诊室护士"  data-live-search="true" title="请选择"></select>
<!-- 									<select class="dict" name="clinic_nurse" id="clinic_nurse" data-bv-notempty data-bv-notempty-message="诊室护士"></select> -->
								</td>
								
								<td>
					            	 <!--.commonText“就诊分类”文本的样式 提供布局  -->	<!--.colorRed 星号为红色及位置调整 -->
									 <span class="commonText"><i class="colorRed"></i>客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服：</span>	
								</td>
								<td>	<!--.dict 本身无样式 与载入数据功能有关 -->
									<!-- <input class="whiteInp" type="text" id="customer_service" name="customer_service" placeholder="客服" onClick="javascript:single_select_user(['receiveno', 'customer_service'],'single');" oninput="value=value.replace(/[^\d]/g,'')"/> -->
									<select id="customer_service" name="customer_service" tig="" class="dict searchSelect" data-bv-notempty data-bv-notempty-message="客服"  data-live-search="true" title="请选择"></select>
<!-- 									<select class="dict" name="customer_service" id="customer_service" data-bv-notempty data-bv-notempty-message="客服"></select> -->
								</td>
							</tr>							
							<tr></tr>							
						</tbody>
					</table>
				</form>
			</div>
		</section>
		
		<footer style="padding-bottom: 10px">
			<!-- .clear2 本身无样式 -->
			<div class="clear2"></div>
			<!--.btnCommon自定义的按钮常规样式   -->
			<a class="kqdsSearchBtn bigBtn" style="margin-left:20px;" onclick="createSSInfo()">创建</a>
		</footer>
		
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>


<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var onclickrowOobj = window.parent.onclickrowOobj; //父页面传值
<%-- var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopageGh.act'; --%>
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopageGhPermission.act';
//建档、预约中心跳转传值
var usercode = "<%=usercode %>";
var netorderid = "<%=netorderid %>";
var orderno ="<%=orderno%>";
var static_askperson = null;//第一咨询
var receive_askperson = null;//接诊咨询
//获取最近一次挂号，并赋上一次挂号分类的值，避免重复点同一条患者记录
var lastregInfo = null;
// 查询最近的开单医生
var lastcostInfo = null;
//判断是否挂号的状态
var registrationStatus=0;
//是否初诊
var ischuzhen = true;
//是否初诊(是否加载过)
var ischeckchuzhen = false;
$(function() {
	
	var userAgent = navigator.userAgent; 
	if (userAgent.indexOf("iPad") > -1){
		$(".condition").css({'display':'flex','flex-wrap':'wrap'})

	}
	 $("#operation_time").datetimepicker({
	        language:  'zh-CN',  
	   		minView:0,
	        format: 'yyyy-mm-dd hh:ii',
	   		autoclose : true,//选中之后自动隐藏日期选择框   
	   		pickerPosition: "bottom-right"
	  });
	
  initDictSelectByClass(); // 就诊分类、挂号分类、挂号方式
  // 就诊科室下拉框，目前是通过 dept_type = '1'  查询获取的
  initDeptSelectByTypesAndClass('<%=ChainUtil.getCurrentOrganization(request)%>');
  //初始化咨询师下拉框
  initSysUserByDeptId($("#counsellor"),"consultation","loginName");
  //初始化种植医生下拉框
  initSysUserByDeptId($("#plant_physician"),"plantDoctor","loginName");
  //初始化修复医生下拉框
  initSysUserByDeptId($("#repair_physician"),"repairdoctor","loginName");
  //初始化护士下拉框
  initSysUserByDeptId($("#clinic_nurse"),"nurse","loginName");
  //初始化客服下拉框
  initSysUserByDeptId($("#customer_service"),"customer","loginName");
  // 科室变动时，人员列表随之变动
  selectChangeTwo("regdept", "doctor");
  //咨询 下拉列表
  initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");
  //跟踪方式下拉框
  initssType();
  $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-26 licc	
  //初始化多选下拉框
  $('#plant_system').selectpicker({
	  title: "请选择",
  });
  $('#crown_material').selectpicker({
	  title: "请选择",
  });
  $("#ssType").on("change",function (){
	  if($("#ssType").val() != "1") {
		  $(".mouset").removeClass("hide");
	  }else {
		  $(".mouset").addClass("hide");
	  }
  });
  
  
  
  //默认选中免费挂号  触发change事件 给挂号金额赋值
  $("#regway option:eq(0)").remove(); // 移除第一个 请选择选项
  $("#regway option:eq(0)").prop("selected", 'selected'); /** 对于HTML元素本身就带有的固有属性，在处理时，使用prop方法，对于HTML元素我们自己自定义的DOM属性，在处理时，使用attr方法。 **/ 
  $('#regway').change(); // 此代码的作用是去掉[0]的左右括号，给regmoney赋值
  
  //网电预约Id 挂号时选择的时网电预约记录，挂号后把该预约设为已上门
  if (netorderid != "") {
      $("#netorderid").val(netorderid);
  }
  //门诊预约Id 挂号时选择的时门诊预约记录，挂号后把该预约设为已上门
  if (orderno != "") {
      $("#orderno").val(orderno);
  }
  //加载 bootstrap table
  initTableList();
  
 
  /*****************种植牙位图操作*******************/
  //点击牙齿
  $('#zhongzhi').on('click', 'li',
  function() {
      $(this).toggleClass('current');
      getAllToothNum();
  });
  
//成人
  //自动选择--左上
  $('#lefttop').on('click',
  function() {
  	if ($('#lefttop').is(':checked')) {
  		 $("span[name=zzadultupYa1]").each(function(index) {
 	        	$(this).parent().removeClass('current');
 	            $(this).parent().toggleClass('current');
  	     });
      }else{
      	 $("span[name=zzadultupYa1]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
      }
  });
  //自动选择--右上
  $('#righttop').on('click',
  function() {
  	if ($('#righttop').is(':checked')) {
	  		 $("span[name=zzadultupYa2]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
      }else{
     	 $("span[name=zzadultupYa2]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
      }
  });
  //自动选择--左下
  $('#leftdown').on('click',
  function() {
  	if ($('#leftdown').is(':checked')) {
	  		 $("span[name=zzadultdownYa1]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
      }else{
    		 $("span[name=zzadultdownYa1]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
      }
  });
  //自动选择--右下
  $('#rightdown').on('click',
  function() {
  	if ($('#rightdown').is(':checked')) {
	  		 $("span[name=zzadultdownYa2]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
      }else{
   		 $("span[name=zzadultdownYa2]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
      }
  });
  /*****************牙位图操作 END*******************/
  
  
  
  
  /*****************修复牙位图操作*******************/
  //点击牙齿
  $('#xiufu').on('click', 'li',
  function() {
      $(this).toggleClass('current');
      getAllToothNumb();
  }); 
  
//成人
  //自动选择--左上
  $('#repairlefttop').on('click',
  function() {
  	if ($('#repairlefttop').is(':checked')) {
  		 $("span[name=repairadultupYa1]").each(function(index) {
 	        	$(this).parent().removeClass('current');
 	            $(this).parent().toggleClass('current');
  	     });
      }else{
      	 $("span[name=repairadultupYa1]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
      }
  });
  //自动选择--右上
  $('#repairrighttop').on('click',
  function() {
  	if ($('#repairrighttop').is(':checked')) {
	  		 $("span[name=repairadultupYa2]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
      }else{
     	 $("span[name=repairadultupYa2]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
      }
  });
  //自动选择--左下
  $('#repairleftdown').on('click',
  function() {
  	if ($('#repairleftdown').is(':checked')) {
	  		 $("span[name=repairadultdownYa1]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
      }else{
    		 $("span[name=repairadultdownYa1]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
      }
  });
  //自动选择--右下
  $('#repairrightdown').on('click',
  function() {
  	if ($('#repairrightdown').is(':checked')) {
	  		 $("span[name=repairadultdownYa2]").each(function(index) {
	 	        	$(this).parent().removeClass('current');
	 	            $(this).parent().toggleClass('current');
	  	     });
      }else{
   		 $("span[name=repairadultdownYa2]").each(function(index) {
	            $(this).parent().removeClass('current');
	    	 });
      }
  }); 
  /*****************牙位图操作 END*******************/
  initInfo();
});

/**
 * 初始化跟踪方式
 */
function initssType() {
	var url = contextPath + "/HUDH_FlowConfigAct/findAllLcljFlow.act";
	var param = { id : null };
	$.axseSubmit(url, param, function() {}, function(r) {
	  //alert(JSON.stringify(r));
	  //console.log(r);
	  for (var i = 0; i < r.length; i++) {
			$("#ssType").append(
				'<option value='+ r[i].id + '>' + r[i].name + '</option>' 
			);
	  }
	}, function() {
	  layer.alert("查询失败！");
	});
} 


//初始化已经有的手术信息
function initInfo(){
	if(onclickrowOobj) {
		  $("#counsellor").val(onclickrowOobj.askpersonname);
		  $("#plant_physician").val(onclickrowOobj.doctorname);
		  $("#repair_physician").val(onclickrowOobj.repairdoctorname);
	  }
}
/**
 * ######################################### 有以下几种方式进入该系统 #############################################
 * 1、接待中心，点击挂号按钮， 此时，Url没有传值
 * 2、预约挂号：
 *   1) 点击门诊预约记录，进入当前页面，此时，传值usercode
 *   2) 点击网电预约记录，进入当前页面，此时，传值usercode 和 网电预约记录的主键
 * 3、患者建档完成后，进入当前页面，此时，传值usercode
 */


/**
 * 就诊分类
 * 如果是初诊，则就诊分类下拉框只显示 初诊选项
 * 如果不是初诊，则不显示初诊选项
 */
$('#recesort').on('change',
	function() {
	
	if(!onclickrowOobj.usercode){
		layer.alert('请选择患者', {
			  
		});
		return false;
	}
	if(!ischeckchuzhen){ // 这样写的作用是，重复点击一个初诊患者，减少数据查询次数
		ischuzhen = checkIschuzhen();
	}
    if(ischuzhen){ // 如果是初诊，但是挂号没选初诊
   		$("#recesort option").each(function(){
   			if($(this).html() == '初诊'){
   				$(this).attr("selected",true);
   			}
		});
    }else{
    	$("#recesort option").each(function(){
   			if($(this).html() == '初诊'){
   				$(this).remove();
   			}
		});
    }
    
    return false;
    
});

function checkIschuzhen(){
	var ischuzhen = false;
	// 判断当前挂号是否是初诊挂号
    var url = '<%=contextPath%>/KQDS_REGAct/isFirstReg.act?usercode=' + onclickrowOobj.usercode;
    $.axse(url, null,
    function(data) {
		ischuzhen = data.data;
		ischeckchuzhen = true;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return ischuzhen;
}

/**
 * 添加需选择的每个角色
 */
 function noEdit(){
	$("#plant_physician").attr("disabled","disabled");
	$("#repair_physician").attr("disabled","disabled");
	$("#clinic_nurse").attr("disabled","disabled");
	$("#customer_service").attr("disabled","disabled");
	$("#counsellor").attr("disabled","disabled");
}
 function qxDisable(){
	$("#plant_physician").removeAttr("disabled");
	$("#repair_physician").removeAttr("disabled");
	$("#clinic_nurse").removeAttr("disabled");
	$("#customer_service").removeAttr("disabled");
	$("#counsellor").removeAttr("disabled");
}

//医生 高级选择 连接
function select() {
  //获取部门的所有id
  var jzks = $("#regdept option").map(function() {  // map() 把每个元素通过函数传递到当前匹配集合中，生成包含返回值的新的 jQuery 对象。
      return $(this).val();
  }).get().join(","); // 由于返回值是 jQuery 封装的数组，使用 get() 来处理返回的对象以得到基础的数组。 // join() 方法用于把数组中的所有元素放入一个字符串。
  jzks = jzks.replace(/^,+|,+$/g, '');
  deptid_select_user(['checkperson', 'checkpersonDesc'], jzks);
}

/**
 * 通过点击 医生后面的 高级 超链接，选择医生后
 * 1、就诊科室根据选择的医生联动
 * 2、给医生字段赋值
 */
function changeAskPerson() {
  var checkerson = $("#checkperson").val();
  if (checkerson) {
      //获取用户部门id
      var url = contextPath + "/YZPersonAct/getDeptByUserSeqId.act?seqId=" + checkerson;
      $.axse(url, null,
      function(data) {
          if (data.retState == "0") {
              var val = data.retData;
              //就诊科室赋值
              $("#regdept").val(val);
              //手动触发 下拉选change事件
              $("#regdept").trigger("change");
              //人员下拉选赋值
              $("#doctor").val(checkerson);
          }
      },
      function() {
          layer.alert('查询出错！', {
                
          });
      });
  }else{
	  $("#regdept").val("");
	  $("#doctor").empty();
  }
}

// 点击某个患者，跳转到预约查询界面
function yySearch(usercode, username) {
  parent.parent.frames[0].location.href = contextPath + "/KQDS_Net_OrderAct/toNetorderSearch.act?usercode=" + usercode + "&username=" + username;
}

// 查询患者按钮
function searchHzda() {
  $("#askperson").val(""); // 避免选中上个患者的咨询
  $("#regdept").val("").trigger("click");
  $("#doctor").val("");
  $("#regsort").val("");
	  
  lastcostInfo = "";
  lastregInfo = "";
  usercode = "";	
  onclickrowOobj = ""; // ### 重置该变量的值，否则影响bootstrap table onloadsuccess方法的执行
                       // ### 这里没有清空该页面的全局变量usercode，所以从netOrderSearch.jsp页面进入到挂号页面，不能更换患者，即查询无效
  var searchValue = $("#searchValue").val();
  if ($.trim(searchValue) == "") {
      layer.alert('请输入查询条件', {
            
      });
      return false;
  }
  $('#table').bootstrapTable('refresh', {
      'url': pageurl
  });
  $("#searchValue").val(searchValue);
}


// bootstrap table的查询条件
function queryParams(params) {
  var temp;
  if (usercode) {
      temp = {
          usercode: usercode
      };
  } else if (onclickrowOobj.usercode) {
      temp = {
          usercode: onclickrowOobj.usercode
      };
  } else {
      temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
          limit: params.limit,
          offset: params.offset,
          searchField: $('#searchField').val(),
          searchValue: $('#searchValue').val()
      };
  }
  return temp;
}


/**
 * 进入页面后，加载bootstrap table
 * 如果不传任何查询条件，则后台返回空 
 */
function initTableList(){
  // 档案列表
  $('#table').bootstrapTable({
      url: pageurl,
      dataType: "json",
      queryParams: queryParams,
      striped: true,
      cache: false,
      onLoadSuccess: function(data) {
          if (usercode) { /** 建档后，提示是否挂号，点击是，则将usercocde参数传到此挂号页面  **/
              $("#searchValue").val(getNameByusercodesTB(usercode)[0].username);
              //表格加载成功 默认选中第一条;
              $("#table tr td:first").each(function() {  // ## 这两种情况，只会有一个患者出现，所以 直接 td 不会出错
                  $(this).trigger("click");
              });
          } else if (onclickrowOobj.usercode) { 
              $("#searchValue").val(getNameByusercodesTB(onclickrowOobj.usercode)[0].username);
              //表格加载成功 默认选中第一条;
              $("#table tr td:first").each(function() { // ## 这两种情况，只会有一个患者出现，所以 直接 td 不会出错
                  $(this).trigger("click");
              });
          }
          setHeight();
      },
      onDblClickCell: function(field, value, row, td) {
          //双击事件 调用提交回访结果方法 
          goToUserCenterPage(row.usercode);
      },
      columns: [
      {
          title: '病人编号',
          field: 'usercode',
          align: 'center',
          valign: 'middle',
          visible: true,
          switchable: false
      },
      {
          title: '姓名',
          field: 'username',
          align: 'center',
          valign: 'middle',
          formatter: function(value, row, index) {
              return "<span class='name'>"+value+"</span>";
          }
      },
      {
          title: '性别',
          field: 'sex',
          align: 'center',
          valign: 'middle',
          formatter:function(value){
        	  return '<span>'+value+'</span>';
          }
      },
      {
          title: '出生日期',
          field: 'birthday',
          align: 'center',
          valign: 'middle',
          formatter:function(value){
        	  return '<span>'+value+'</span>';
          }
      },
      {
          title: '身份证号',
          field: 'idcardno',
          align: 'center',
          valign: 'middle',
          formatter:function(value){
        	  return '<span>'+value+'</span>';
          }
      },
      {
          title: '手机号码1',
          field: 'phonenumber1',
          align: 'center',
          formatter:function(value){
        	  return '<span>'+value+'</span>';
          }
      },
      {
          title: '手机号码2',
          field: 'phonenumber2',
          align: 'center',
          formatter:function(value){
        	  return '<span>'+value+'</span>';
          }
      },
      {
          title: '助理',
          field: 'firstaskpersonname',
          align: 'center',
          formatter:function(value){
        	  return '<span>'+value+'</span>';
          }
      },
      {
          title: '挂号时间',
          field: 'createtime',
          align: 'center',
          formatter:function(value){
        	  return '<span>'+value+'</span>';
          }
      },
      {
          title: '建档人',
          field: 'createusername',
          align: 'center',
          formatter: function(value, row, index) {
             if(value){
            	 return '<span class="name">'+value+'</span>';
             }
          }
      },
      {
          title: '操作',
          field: ' ',
          align: 'center',
          formatter: function(value, row, index) {
              if (row.type == 1) {
                  return '<a href="javascript:void(0);" style="color:#00A6C0;" onclick="yySearch(\'' + row.usercode + '\',\'' + row.username + '\')">预约详情</a> ';
              } else {
                  return "";
              }
          }
      }]
  }).on('click-row.bs.table',
  function(e, row, element) {
      $('.success').removeClass('success'); //去除之前选中的行的，选中样式
      $(element).addClass('success'); //添加当前选中的 success样式用于区别
      var index = $('#table').find('tr.success').data('index'); //获得选中的行
      onclickrowOobj = $('#table').bootstrapTable('getData')[index];
   	  ischeckchuzhen = false;//重新加载患者是否是初诊
//    	  estimateRegistration(onclickrowOobj.usercode);//注释改行代码、取消未挂号校验（咨询操作）
         /**
          * ######################################### 有以下几种方式进入该系统 #############################################
          * 1、接待中心，点击挂号按钮， 此时，Url没有传值
          * 2、预约挂号：
          *   1) 点击门诊预约记录，进入当前页面，此时，传值usercode
          *   2) 点击网电预约记录，进入当前页面，此时，传值usercode 和 网电预约记录的主键
          * 3、患者建档完成后，进入当前页面，此时，传值usercode
          * ########################################## 不管是哪种方式进入当前页面，进行挂号前，都会执行到此处 ！！！
          */
          
       	// ###挂号页面，自动设置
       AuotSetFunc();
  });	
}
function estimateRegistration(usercode){
	var url = contextPath + '/KQDS_REGAct/selectExistByUsercode.act';
	$.ajax({
         type: "post",
         url: url,
         data: {usercode : usercode},
         dataType: "json",
         success: function(data){
        	if(data.status=="未挂号"){
		    	layer.alert("患者未挂号，请挂号后操作！");
		    	registrationStatus=1;
		    	return;
		    }
         }
     });
}
//【咨询修改值跟默认值不一致，跳出提醒】
$("#askperson").change(function(){
	var msg = "";
	//存在第一咨询
	if(static_askperson){
		if(static_askperson != $(this).val()){
			var askpersonname = $("#askperson option[value='" + static_askperson + "']").text();
			var askpersonnamenow = $("#askperson option[value='" + $(this).val() + "']").text();
			if(askpersonname == ""){
				askpersonname = getPerUserNameBySeqIdTB(static_askperson);
			}
			msg = "该患者第一咨询为：‘"+askpersonname+"’，确认选择：‘"+askpersonnamenow+"’ 为接诊咨询？"
		}
	}
	//不存在第一咨询，存在接诊咨询
	if(receive_askperson){
		if(receive_askperson != $(this).val()){
			var askpersonname = $("#askperson option[value='" + receive_askperson + "']").text();
			var askpersonnamenow = $("#askperson option[value='" + $(this).val() + "']").text();
			if(askpersonname == ""){
				askpersonname = getPerUserNameBySeqIdTB(static_askperson);
			}
			msg = "该患者存在接诊咨询为：‘"+askpersonname+"’，确认选择：‘"+askpersonnamenow+"’ 为接诊咨询？"
		}
	}
	if(msg !=""){
		layer.confirm(msg, {
	        btn: ['确定', '取消'] //按钮
	    },
	    function() {
	        layer.closeAll('dialog');
	    },
	    function() {
	    	if(static_askperson){
	    		$("#askperson").val(static_askperson);
	    	}else{
	    		if(receive_askperson){
	        		$("#askperson").val(receive_askperson);
	        	}
	    	}
	    	
	        layer.closeAll('dialog');
	    });
	}
});
// 挂号页面，自动设置
function AuotSetFunc(){
	$("#regsort").val("");
	static_askperson = null;
	receive_askperson = null;
	// ###########################获取最近一次挂号，并赋上一次挂号分类的值
	// 这样写的目的是，多次点击同一条患者记录，避免重复请求
	if(!lastregInfo){
		lastregInfo = getLastestRegInfo(onclickrowOobj.usercode);
	}else{
		if(lastregInfo.usercode != onclickrowOobj.usercode){
			lastregInfo = getLastestRegInfo(onclickrowOobj.usercode);
		}
	}
	// 查询最近的开单医生
	if(!lastcostInfo){ // 这样写的目的是，多次点击同一条患者记录，避免重复请求
		lastcostInfo = getLastestCostOrderInfo(onclickrowOobj.usercode);
	}else{
		if(lastcostInfo.usercode != onclickrowOobj.usercode){
			lastcostInfo = getLastestCostOrderInfo(onclickrowOobj.usercode);
		}
	}
	
	$("#askperson").val("");
	if (onclickrowOobj.askperson) { // 如果该患者的第一咨询已经设定，则不允许修改### 此需求待确定，因为一旦这样做之后，如果当前咨询不在班，则无法进行咨询了！！！
		static_askperson = onclickrowOobj.askperson;
	    $("#askperson").val(onclickrowOobj.askperson);
	    //$("#askperson").attr("disabled","disabled");
	}else{//如果该患者没有第一咨询，但存在接诊咨询，取最新挂号的接诊咨询 赋值（可修改）
		//查询是否存在接诊咨询
		if(lastregInfo){
			if(lastregInfo.askperson){
				receive_askperson = lastregInfo.askperson;
				$("#askperson").val(lastregInfo.askperson);
			}
		}
	}
	
	if (onclickrowOobj.doctor) {
	    $("#checkperson").val(onclickrowOobj.doctor);
	    $('#checkpersonDesc').trigger("change"); // 触发change事件，调用changeAskPerson()方法，对医生字段进行赋值
	} else {
	    $("#checkperson").val("");
	    $('#checkpersonDesc').trigger("change");
	}

	// --------------------方法块，不要拆分 -------------------/
	initDictSelect("recesort", "jzfl"); // 就诊分类
	if(lastregInfo){
		$('#regsort').val(lastregInfo.regsort);
	}
	// 当天门诊预约的，默认赋值医生为预约的医生
	var currDateHosInfo = null;
	if(orderno){
		currDateHosInfo = getHosOrderInfoBySeqId(orderno);
	}else{
		currDateHosInfo = getCurrDatetHosOrderInfo(onclickrowOobj.usercode);
	}
	if(currDateHosInfo && (currDateHosInfo.usercode == onclickrowOobj.usercode)){
		$('#checkperson').val(currDateHosInfo.askperson);
		$('#checkpersonDesc').trigger("change");
	}
	
	var tmpdoctor = $("#doctor").val();
	if(!tmpdoctor){ // 如果没有预约医生，或者预约的不是医生，比如是admin，那么此时取的doctor是没有值的，这个时候，再根据上一次挂号医生来赋值
		if(lastcostInfo){
			if(lastcostInfo.doctor){
				$('#checkperson').val(lastcostInfo.doctor);
				$('#checkpersonDesc').trigger("change");
			}else{

			}
		}else{
			if(lastregInfo){
				if(lastregInfo.doctor){
					$('#checkperson').val(lastregInfo.doctor);
					$('#checkpersonDesc').trigger("change");
				}
			}
		}
	}
	
	// 根据患者是否是初次挂号，动态调整挂号分类
	$('#recesort').trigger("change");
	// --------------------方法块，不要拆分 end..-------------------/
}

function createSSInfo(){
	if(registrationStatus==1){
		layer.alert("患者未挂号，无法创建，请挂号后操作！");
    	return;
	}else{
		var seqId = onclickrowOobj.seqId;
		var id = '<%=request.getAttribute("id")%>';
		var tooth_total = $('#tooth_total').val();
		var ssType = $('#ssType').val();
		var isBone = $('#isBone').val();
		
		var counsellor = $('#counsellor').val();
		var plant_physician = $('#plant_physician').val();
		var repair_physician = $('#repair_physician').val();
		var clinic_nurse = $('#clinic_nurse').val();
		var customer_service = $('#customer_service').val();
		
		var plant_system = $('#plant_system').val();
		var crown_material = $('#crown_material').val();
		
		var modeoperation = $('#modeoperation').val();
		var remark = $('#remark').val();
		var before_Modulo_bite=$('#before_Modulo_bite').val();
		var zz_tooth_total = $('#zz_tooth_total').val();
		var blcode = $('#table').find('.success').find('td:first').text(); //获取选中的病例号
		if(!blcode) {
			layer.alert('请选择患者');
			return;
		}
		if(!counsellor) {layer.alert('请选择助理');return;}
		/* if(!plant_physician) {layer.alert('请选择种植医师');return;}
		if(!repair_physician) {layer.alert('请选择修复医师');return;} */
		/* if(!clinic_nurse) {layer.alert('请选择诊室护士');return;} */
		var imageological_examination = showImagelogic();
		var consultation = showConsultation();
		var advisory = showAdvisory();
		var abutment_station = abutmentStation(); //基台放置
		var tooth_texture = toothTexture(); //牙冠材质
		//if(!consultation) {layer.alert('请勾选术前准备选项');return;}
		//创建手术
		var param = {
				seqId : seqId,//传入档案表主键
				abutment_station : abutment_station,
				tooth_texture : tooth_texture,
				before_Modulo_bite : before_Modulo_bite,
				advisory : advisory,
				blcode : blcode,
				tooth_total : tooth_total,
				ssType : ssType,
				isBone : isBone,
				counsellor : counsellor,
				plant_physician : plant_physician,
				repair_physician : repair_physician,
				clinic_nurse : clinic_nurse,
				customer_service : customer_service,
				imageological_examination : imageological_examination,
				consultation : consultation,
				zz_tooth_total : zz_tooth_total,
				modeoperation : modeoperation,
				remark : remark,
				id : id
		};
		var url = '<%=contextPath%>/HUDH_FlowAct/saveLcljOrderTrack.act';
		$.axseSubmit(url, param, function() {}, function(r) {
			layer.alert(r.retMsrg, {
	            end: function() {
	          	  window.parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
	            }
	      });
		}, function(r) {
			layer.alert(r.retMsrg, {
	            end: function() {
	            	//console.log("保存失败");
	            	window.parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
	            }
	      });
		});
	}
}

//获取影像学检查
function showImagelogic(){
    var obj = document.getElementsByName("imagelogic");
    var imageological_examination = "";
    for ( k in obj ) {
        if(obj[k].checked)
        //alert(obj[k].value);
        imageological_examination = imageological_examination + obj[k].value + ';';//拼接字符串
    }
    return imageological_examination;
} 
//获取专家会诊
function showConsultation(){
    var obj = document.getElementsByName("Consultation");
    var consultation = "";
    for ( k in obj ) {
        if(obj[k].checked)
        	//consultation.push(obj[k].value);
        consultation = consultation + obj[k].value + ';';
    }
    return consultation;
}
//获取咨询
function showAdvisory(){
    var obj = document.getElementsByName("Advisory");
    var advisory = "";
    for ( k in obj ) {
        if(obj[k].checked)
        	//advisory.push(obj[k].value);
        	advisory = advisory + obj[k].value + ';';
    }
    return advisory;
}

//获取基台放置信息
function abutmentStation(){
    var obj = document.getElementsByName("abutment_station");
    var abutment_station = "";
    for ( k in obj ) {
        if(obj[k].checked)
        //alert(obj[k].value);
        	abutment_station = abutment_station + obj[k].value + ';';//拼接字符串
    }
    return abutment_station;
}

//获取牙冠材质
function toothTexture(){
    var obj = document.getElementsByName("tooth_texture");
    var tooth_texture = "";
    for ( k in obj ) {
        if(obj[k].checked)
        //alert(obj[k].value);
        	tooth_texture = tooth_texture + obj[k].value + ';';//拼接字符串
    }
    return tooth_texture;
}


//获取对象中的选中值
function getSelectToothNum(obj){
	var tooth = "";
	$(obj).each(function (){
		var $li = $(this).parent('li');
		var $span = $li.find('span');
		if($li.hasClass('current') && $span.hasClass('num')){
			tooth = tooth+ ($(this).text()) + ",";
		}
	});
	return tooth;
}

//获取修复对象中的选中值
function getSelectToothNumb(obje){
	var toothNum = "";
	$(obje).each(function (){
		var $li = $(this).parent('li');
		var $span = $li.find('span');
		if($li.hasClass('current') && $span.hasClass('num')){
			toothNum = toothNum+ ($(this).text()) + ",";
		}
	});
	return toothNum;
}


//计算当前选中牙齿总颗数为牙齿总数赋值
function getAllToothNum (){
	var at = 0;
 	$('#zhongzhi').find('li').each(function (){
 		var $span = $(this).find('span');
		if($(this).hasClass('current') && $span.hasClass('num')) {
			at ++;
		}
	});
 	
	$('#tooth_total').val(at);
	
 }
 
//计算种植牙位图当前选中牙齿总颗数为牙齿总数赋值
function getAllToothNumb (){
	var ato = 0;
 	$('#xiufu').find('li').each(function (){
 		var $span = $(this).find('span');
		if($(this).hasClass('current') && $span.hasClass('num')) {
			ato ++;
		}
	});
 	
	$('#zz_tooth_total').val(ato);
	
 }

function setHeight(){
	$("#table").bootstrapTable("resetView",{
		/* height:$(window).outerHeight()-$(".operatedDiv").outerHeight()-$("footer").outerHeight()-$("header").outerHeight()-400 */
		height: '30%'
	});
}
</script>
</html>
