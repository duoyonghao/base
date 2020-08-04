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
	// 获取是否打开排班 和预约关联的 功能
	/* String IS_OPEN_PAIBAN_ORDER_FUNC = SysParaUtil.getSysValueByName(request, SysParaUtil.IS_OPEN_PAIBAN_ORDER_FUNC);
	if (IS_OPEN_PAIBAN_ORDER_FUNC == null) {
		IS_OPEN_PAIBAN_ORDER_FUNC = "0"; // 0 不打开  1打开
	} */
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reg/reg.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>

<style>
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
  	.askperson:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 130px;   
      }  
    .doctor:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 100px;   
      } 
    .repairDoc:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 130px;   
      }   
	.askperson>.btn { 
	    width: 130px; 
	 	height:26px; 
	 	border: solid 1px #e5e5e5; 
	}
	.doctor>.btn{
		width: 100px;
	    height:26px;
	    border: solid 1px #e5e5e5; 
	}
	.repairDoc>.btn{
		width: 130px;
	    height:26px;
	    border: solid 1px #e5e5e5; 
	}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
	    color: #999;
	    height: 26px;
	}
	.pull-left {
    float: left !important;
    color: #333;
	}
	.btn-group > .btn:first-child:hover {
	    background: white;
	}
input[type="text"].queryInp2 {
    width: 200px;
    /* border-top-left-radius: 0;
    border-bottom-left-radius: 0; */
/*     border-left-color: rgba(255,255,255,0); */
    float: left;
    color: #666;
}
.fixed-table-container {
    height: 210px !important;
    padding-bottom: 83px !important;
}
</style>
</head>
<body>
	<div class="content"><!--容器框 增加了些内边距 -->
		<header>
			<table class="tableLayout"><!--.tableLayout布局 宽度撑满父元素  -->
				<tbody>
					<tr>
						<td style="width: 85px;">
							<span>门诊</span>
		    				<select id="organization" name="organization" style="width: auto;"></select>
						</td>
						<td >
							<select style="display: none" id="searchField" class="querySel"><!-- .querySel查询中 的下拉框  -->
								<option value="username">姓名</option>
								<option value="PhoneNumber1">手机号码</option>
								<option value="idcardno">身份证号</option>
								<option value="usercode">患者编号</option>
							</select>
							<input class="queryInp2" type="text" id="searchValue" name="searchValue" placeholder="患者姓名/手机号/患者编号"/>	<!--.queryInp查询中的Input样式  -->
							<a id="searchHzda" href="javascript:void(0);" style="float:left;margin-top:1px;margin-left:10px;" class="kqdsSearchBtn" onclick="searchHzda()">查 询</a>	<!-- .btnCommon自定义按钮通用样式 .queryBtn查询按钮样式的调整 -->
						</td>					
					</tr>
				</tbody>
			</table>
		</header>
		
		<section>
			<!-- <span class="tableTitle">查询清单</span> --><!--.tableTitle查询清单字样的样式  -->
			<table id="table" class="table-striped table-condensed table-bordered" data-height="180"></table> <!--无自定义样式 此行样式为框架样式   -->
			<div class="operatedDiv">	<!-- 所有下拉框的父元素 用来布局 -->
			<form id="form">
				<table class="tableLayout">	<!--.operatedDiv下的 .tableLayout有自己特有的样式-->
					<tbody>
						<tr>
							<td>
								 <input type="hidden" id="usercode" name="usercode"/>
				            	 <input type="hidden" id="username" name="username"/>
				            	 <input type="hidden" id="netorderid" name="netorderid"/>
				            	 <input type="hidden" id="orderno" name="orderno"/>
				            		
				            	 <!--.commonText“就诊分类”文本的样式 提供布局  -->	<!--.colorRed 星号为红色及位置调整 -->
								 <span class="commonText"><i class="colorRed">*</i>就诊分类：</span>	
							</td>
							
							<td>	<!--.dict 本身无样式 与载入数据功能有关 -->
								<select class=" dict" tig="jzfl" name="recesort" id="recesort"  data-bv-notempty data-bv-notempty-message="就诊分类不能为空">
	               				 </select>
							</td>
							
							<td>	<!--.commonText“就诊分类”文本的样式 提供布局  -->
								 <span class="commonText"><i class="colorRed">*</i>挂号分类：</span>
							</td>
							
							<td>	<!--.dict 本身无样式 与载入数据功能有关 -->	
								<select class="dict" tig="ghfl" name="regsort" id="regsort" data-bv-notempty data-bv-notempty-message="挂号分类不能为空">
	                			</select>
							</td>
							
							<td> 
								 <span class="commonText"><i class="colorRed">*</i>挂号方式：</span>
							</td>
							
							<td>
								<select class="dict" tig="ghfs" name="regway" id="regway" data-bv-notempty data-bv-notempty-message="挂号方式不能为空">
								</select>
							</td>
							
							<td>
								 <span class="commonText"><i class="colorRed">*</i>挂号金额：</span>
							</td>
							
							<td>
								<input type="text" name="regmoney" id="regmoney" readonly data-bv-notempty data-bv-notempty-message="挂号金额不能为空"/>
							</td>
						</tr>
						
						<tr>
							<td>
								<span class="commonText"><i class="colorRed">*</i>咨询部门：</span>
							</td>
							<td>
								<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0 %>" name="askpersondept" id="askpersondept" data-bv-notempty data-bv-notempty-message="咨询部门不能为空">
								</select>
							</td>
							<td>
								<span class="commonText"><i class="colorRed">*</i>咨&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询：</span>
							</td>
							<td>
								 <select id="askperson" name="askperson" class="askperson"  data-live-search="true" title="请选择"></select>
<!-- 								<select  name="askperson" id="askperson" > -->
<!-- 								</select> -->
							</td>
							
							<td>
								<span class="commonText">就诊科室：</span>
							</td>
							<td><!-- dept 本身不含样式  有载入选项的功能 -->
								<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" data-bv-notempty data-bv-notempty-message="就诊科室不能为空">
								</select>
							</td>
							<td>
								<span class="commonText">医&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生：</span>
							</td>
							<td class="doctorText">	<!--.doctorText 医生 下的元素需要调整样式   -->
								 <select id="doctor" name="doctor" tig="" class="doctor"  data-live-search="true" title="请选择"></select>
<!-- 								<select  name="doctor" id="doctor"> -->
<!-- 								</select> -->
								<input type="hidden" name="checkperson" id="checkperson" placeholder="医生" title="医生"/> 
								<input type="hidden" onchange="changeAskPerson()" id="checkpersonDesc" name="checkpersonDesc" placeholder="医生" readonly ></input>	
								<a onClick="javascript:select()">&nbsp;高级</a>
							</td>
						</tr>
						<tr>
							<td>
								<span class="commonText ">挂号导医：</span>
							</td> 
							<td>
								<input type="hidden" name="receiveno" id="receiveno"/> 
								<input class="whiteInp" type="text" id="receivenoDesc" name="receivenoDesc" placeholder="挂号导医" readonly onClick="javascript:single_select_user(['receiveno', 'receivenoDesc'],'single');">
							</td>
							<!-- <td> 
							<span class="commonText">修复医生：</span>
							</td>
							<td>
								<select id="repairdoctor" name="repairdoctor" tig="" class="dict repairDoc"  data-live-search="true" title="请选择"></select>
<!-- 								<select class="dict" name=repairdoctor id="repairdoctor"></select> -->
<!-- 							</td>  -->
						</tr>
					</tbody>
				</table>
			</form>
				
			</div>
		</section>
		
		<footer>
			<!-- .clear2 本身无样式 -->
			<div class="clear2"></div>
			<!--.btnCommon自定义的按钮常规样式   -->
			<a class="kqdsSearchBtn bigBtn" onclick="getSelectedRow()">挂	号</a>
         	<a class="kqdsCommonBtn bigBtn" id="xfjl">消费记录</a>
         	<a class="kqdsCommonBtn bigBtn" id="zxjl">咨询记录</a>
         	<a class="kqdsCommonBtn bigBtn" id="lsbl">历史病历</a>
         	<a class="kqdsCommonBtn bigBtn" id="lzjl">流转记录</a>
         	<a class="kqdsCommonBtn bigBtn" id="ymjl">牙模记录</a>
         	<a class="kqdsCommonBtn bigBtn" id="qfjl">欠费记录</a>
         	<a class="kqdsCommonBtn bigBtn" id="xgzl">资料修改</a>
		</footer>
	</div>
		
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>

<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var onclickrowOobj = window.parent.onclickrowOobj; //父页面传值
<%-- var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopageGh.act'; --%>//原查询接口
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopageGhLike.act';
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
//是否初诊
var ischuzhen = true;
//咨询
var advisory = null;
//是否初诊(是否加载过)
var ischeckchuzhen = false;
// var lastDoctor=JSON.stringify(onclickrowOobj.doctorname);
//var lastDoctor=JSON.stringify(baseinfo.doctorname);
$(function() {
  initDictSelectByClass(); // 就诊分类、挂号分类、挂号方式
  
  initSysUserByDeptId($("#repairdoctor"),"repairdoctor");
  $('.repairDoc').selectpicker("refresh");//修复医生搜索初始化刷新--2019-10-18 licc
  initHosSelectListNoCheck('organization','<%=ChainUtil.getCurrentOrganization(request)%>'); // 加载门诊列表
  
  // 就诊科室下拉框，目前是通过 dept_type = '1'  查询获取的
  initDeptSelectByTypesAndClass('<%=ChainUtil.getCurrentOrganization(request)%>');
  // 科室变动时，人员列表随之变动
  //selectChangeTwo("regdept", "doctor");
  selectChangeTwoSearch("regdept", "doctor");

<%--    initPersonSelectByDeptType("doctor","<%=ConstUtil.DEPT_TYPE_1 %>"); --%>
  		initPersonSelectByDept("doctor","<%=ConstUtil.DEPT_TYPE_1 %>");
  
  // 咨询部门下拉框，目前是通过 dept_type = '0'  查询获取的  syp 2019-8-24
<%--    initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>"); --%>
		initPersonSelectByDept("askperson","<%=ConstUtil.DEPT_TYPE_0 %>"); 		 
	   
  // 咨询部门变动时，人员列表随之变动  syp 2019-8-24
//   selectChangeTwo("askpersondept", "askperson");
   selectChangeTwoSearch("askpersondept", "askperson");

  //咨询 下拉列表
//   initSysUserByDeptId($("#askperson"),"consultation");
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
  $('.doctor').selectpicker("refresh");//就诊医生初始化刷新--2019-10-18 licc
  $('.askperson').selectpicker("refresh");//咨询部门初始化刷新--2019-10-18 licc
  
});

function initSysUserByDeptIdSearch($obj,deptId,valueType){
	$.ajax({
		url: apppath() + "/YZPersonAct/getUserListByDeptId.act",
		type:"POST",
		dataType:"json",
		data : {"deptId":deptId},
		async : false,
		success:function(result){
// 			console.log(JSON.stringify(result)+"------初始化返回结果当前单个部门result");
			$($obj).append('<option value="">请选择</option>');
			if(result) {
				if(valueType == "loginName") {
					for(var i in result) {
						$($obj).append('<option value="'+result[i].userId+'">'+result[i].userName+'</option>');
					}
				}else {
					for(var i in result) {
						$($obj).append('<option value="'+result[i].seqId+'">'+result[i].userName+'</option>');
					}
				}
				$($obj).selectpicker("refresh");
			}
		}
	});
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
              if($("#regdept").val()==null){
            	  $("#regdept").val(data.xy_dept);
              }
              //手动触发 下拉选change事件
              $("#regdept").trigger("change");
              //人员下拉选赋值
              //$("#doctor").val(checkerson);
              $('#doctor').selectpicker('val', checkerson);
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
  $("#askpersondept").val(""); // 清空咨询部门
  $("#askperson").selectpicker("val",""); // 避免选中上个患者的咨询--（改搜索框）
//   $("#askperson").val(""); // 避免选中上个患者的咨询
  $("#regdept").val("").trigger("click");
  $("#doctor").selectpicker("val","");//--（改搜索框）
//   $("#doctor").val("");
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
  $("#searchHzda").attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
  $("#searchHzda").text("查询中");

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
   		  limit: params.limit,   //页面大小
       	  offset: params.offset, //页码 
       	  pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)	
          usercode: usercode,
          organization: $("#organization").val()
      };
  } else if (onclickrowOobj.usercode) {
      temp = {
   		  limit: params.limit,   //页面大小
       	  offset: params.offset, //页码 
       	  pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)	
          usercode: onclickrowOobj.usercode,
          organization: $("#organization").val()
      };
  } else {
      temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	  limit: params.limit,   //页面大小
    	  offset: params.offset, //页码 
    	  pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)	
          searchField: $('#searchField').val(),
          searchValue: $('#searchValue').val(),
          organization: $("#organization").val()
      };
  }
//   console.log(JSON.stringify(temp)+"------------temp");
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
      /* striped: true,
      cache: false, */
      dataType: "json",
      pagination: true,//是否显示分页（*）
      pageSize: 10,
      pageList : [10, 15, 20, 25],//可以选择每页大小
      singleSelect: true,
      sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
      paginationShowPageGo: true,
      onLoadSuccess: function(data) {
		  //解除查询按钮禁用 lutian
		  if(data){
			  $("#searchHzda").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
			  $("#searchHzda").text("查询");
		  }
//      	  console.log(JSON.stringify(data)+"-----------paaagurl");
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
	  /* {
	    title: '门诊',
	    field: 'organizationname',
	    align: 'center',
	    valign: 'middle',
	    visible: true,
	    switchable: false
	  }, */
	  {
		   title: '门诊',
		   field: 'organizationname',
		   align: 'center',
		   valign: 'middle',
		   visible: true,
		   formatter: function(value, row, index) {
		      if (row.organizationid == "a514e620-8cc9-4cba-87bd-84c73c6f99c3") {
		               return '<span  style="">'+value+'</span>';
		          } else {
		               return '<span  style="background:#00a6c0;color:white;">'+value+'</span>';
		          }            
		      }
		   },
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
      /* {
          title: '咨询',
          field: 'askpersonname',
          align: 'center',
          valign: 'middle',
          formatter:function(value){
        	  return '<span>'+value+'</span>';
          }
      }, */
      {
          title: '亲属关系',
          field: 'familyship',
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
          title: '客服',
          field: 'kefuname',
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
//       console.log(JSON.stringify(onclickrowOobj)+"-------onclickrowOobj");
   	  ischeckchuzhen = false;//重新加载患者是否是初诊
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
				askpersonname = getPerUserNameBySeqIdTB(receive_askperson);
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
// 	    		$("#askperson").val(static_askperson);
	    		$('#askperson').selectpicker('val', static_askperson);
	    	}else{
	    		if(receive_askperson){
// 	        		$("#askperson").val(receive_askperson);
	        		$('#askperson').selectpicker('val', receive_askperson);
	        	}
	    	}
	    	
	        layer.closeAll('dialog');
	    });
	}
});
// 挂号页面，自动设置
function AuotSetFunc(){
	 $("#askpersondept").val(""); // 清空咨询部门
	 $("#askperson").selectpicker("val",""); // 避免选中上个患者的咨询--（改搜索框）
	//   $("#askperson").val(""); // 避免选中上个患者的咨询
	 $("#regdept").val("").trigger("click");
	 $("#doctor").selectpicker("val","");//--（改搜索框）
	//   $("#doctor").val("");
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
	if(!lastcostInfo){ // 这样写的目的是，多次点击同一条患者记录，避免重复请求
		lastcostInfo = getLastestCostOrderInfo(onclickrowOobj.usercode);
	}else{
	// 获取最后一次挂号修复医生给当前挂号赋值   add by xky 2019-1-1 修改挂号带出上次选择的修复医生
			//console.log("修复医生===="+lastregInfo.repairdoctor);
		if(lastregInfo){
			$("#repairdoctor").val(lastregInfo.repairdoctor);
		}
	// 查询最近的开单医生
		if(lastcostInfo.usercode != onclickrowOobj.usercode){
			lastcostInfo = getLastestCostOrderInfo(onclickrowOobj.usercode);
		}
	}
	$("#askperson").val("");
	//if (onclickrowOobj.askperson) { // 如果该患者的第一咨询已经设定，则不允许修改### 此需求待确定，因为一旦这样做之后，如果当前咨询不在班，则无法进行咨询了！！！
		//static_askperson = onclickrowOobj.askperson;
	   	//$("#askperson").val(onclickrowOobj.askperson);
	   	//advisory = static_askperson;
	//}else{//如果该患者没有第一咨询，但存在接诊咨询，取最新挂号的接诊咨询 赋值（可修改）
    if(onclickrowOobj.kefu){
        receive_askperson = onclickrowOobj.kefu;
        $('#askperson').selectpicker('val',onclickrowOobj.kefu);
        if(lastregInfo){
            $("#regdept").val(lastregInfo.regdept);
            $('#doctor').selectpicker('val', lastregInfo.doctor);
        }
    }else{
        //查询是否存在接诊咨询
        if(lastregInfo){
            if(lastregInfo.askperson){
                receive_askperson = lastregInfo.askperson;
                $("#regdept").val(lastregInfo.regdept);
                $('#askperson').selectpicker('val', lastregInfo.askperson);
                $('#doctor').selectpicker('val', lastregInfo.doctor);
            }
            advisory = lastregInfo.askperson;
        }
    }
	//}
    $("#askpersondept").val("");
    if(onclickrowOobj.kefudeptid){
        $("#askpersondept").val(onclickrowOobj.kefudeptid);
    }else{
        if($("#askperson").val() != '' && $("#askperson").val() != null){
            $.ajax({
                url:contextPath+'/YZPersonAct/selectDetail.act',
                type:'POST', //GET
                data:{
                    seqId:advisory
                },
                dataType:'json',
                success:function(data){
                    $("#askpersondept").val(data.retData.deptId);
                }
            })
        }
    }
	/* if (onclickrowOobj.doctor) {
	    $("#checkperson").val(onclickrowOobj.doctor);
	    $('#checkpersonDesc').trigger("change"); // 触发change事件，调用changeAskPerson()方法，对医生字段进行赋值
	} else {
	    $("#checkperson").val("");
	    $('#checkpersonDesc').trigger("change");
	} */

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
	
	/* var tmpdoctor = $("#doctor").val();
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
	} */
	
	// 根据患者是否是初次挂号，动态调整挂号分类
	$('#recesort').trigger("change");
	// --------------------方法块，不要拆分 end..-------------------/
}


// 挂号操作
function getSelectedRow() {
  if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
      layer.alert('请选择患者', {
            
      });
      return false;
  }
  var jzfl = $("#recesort").val();
  if (jzfl == "") {
      layer.alert('请选择就诊分类', {
            
      });
      return false;
  }
  var regsort = $("#regsort").val();
  if (regsort == "") {
      layer.alert('请选择挂号分类', {
            
      });
      return false;
  }
  var regway = $("#regway").val();
  if (regway == "") {
      layer.alert('请选择挂号方式', {
            
      });
      return false;
  }
  var regmoney = $("#regmoney").val();
  if (regmoney == "") {
      layer.alert('挂号金额不能为空', {
            
      });
      return false;
  }
  var askperson = $("#askperson").val();
  var doctor = $("#doctor").val();
  if (askperson == "" || askperson == null) {
      layer.alert('请选择咨询', {
            
      });
      return false;
  }
  
  if(lastregInfo){
	  var doctorname = $("#doctor option:selected").text();
	  var lastDoc=getPerUserNameBySeqIdTB(lastregInfo.doctor);
	  var msg ="该患者存在接诊医生为："+lastDoc+"，确认选择：“"+doctorname+"” 为接诊医生？";
	  if((doctor != null && doctor != "") && (lastregInfo.doctor != null && lastregInfo.doctor != "")
  			&& doctor != lastregInfo.doctor){
		  layer.confirm(msg,{btn: ['确定', '取消'] },function() {
			  // 提交挂号数据到服务器
	    	  submitReg();
	      });
	  }else{
		  submitReg();
	  }
  }
  else{
	  // 提交挂号数据到服务器
	  submitReg();
  }

}

/**
 * 提交挂号数据到服务器
 */
function submitReg(){
	<%-- if('<%=IS_OPEN_PAIBAN_ORDER_FUNC%>' == '1'){ 
    	 getRestPersonListByDate();
    }  --%>
	  //移除只读属性 不然后台取不到值
	  //$("#askperson").removeAttr("disabled");
	  $("#form input[name=usercode]").val(onclickrowOobj.usercode);
	  $("#form input[name=username]").val(onclickrowOobj.username);
	  var param = $('#form').serialize();
	  var url = '<%=contextPath%>/KQDS_REGAct/insertOrUpdate.act?' + param;
	  var msg;
	  $.axseSubmit(url, null,
	  function() {
	      msg = layer.msg('加载中', {
	          icon: 16
	      });
	  },
	  function(r) {
	      layer.close(msg);
	      if (r.retState == "0") {
	          onclickrowOobj = ""; // 清空选中记录
	          layer.alert('挂号成功', {
	              end: function() {
	                  var frameindex = parent.layer.getFrameIndex(window.name);
	                  try {
	                      parent.initclick(); // 调用indexTab的点击事件，使内容刷新
	                  } catch(e) {
	                      if (netorderid != "") {
	                          parent.searchwangdian();
	                      } else {
	                          parent.searchmenzhen();
	                      }
	                  }
	                  if(r.list){
	                	  var data='该患者当天还有其他医生预约 !';
	                	  for (var i = 0; i < r.list.length; i++) {
	                		  data+='<br>预约时间:'+r.list[i].starttime.substring(11,19)+'~'+r.list[i].endtime.substring(11,19)+'&nbsp;&nbsp;医生:'+r.list[i].doctorname;
						}
	                	  layer.open({
	                		  title: '信息',
	                		  content: data,
	                		  end:function(){
	                			  parent.layer.close(frameindex);
	                		  }
	                		});  	 
	                  }else{
		                  parent.layer.close(frameindex);	                	  
	                  }
	              }
	          });
	      } else if(r.retState == "100"){ // ##### 重要，修复同时打开两个界面，同时进行挂初诊，都能挂上的BUG
	    	  // ###挂号页面，自动设置
	    	  AuotSetFunc(); 
        	  layer.alert('该患者已挂过初诊，请重新选择就诊分类。', {
                   
              });
	      }else{
	          layer.alert('挂号失败', {
	                
	          });
	      }
	  },
	  function() {
	      layer.alert('挂号失败', {
	            
	      });
	  });
}

/* #########################################################################以下弹框多为本页面专用，不需要进行抽取 START  杨森  17-3-24 */
$('#regway').on('change',
function() {
  var value = $("#regway").find("option:selected").text();
  if (value.indexOf("[") < 0) {
      $('#regmoney').val(0);
  } else {
      value = value.substring(value.indexOf("[") + 1, value.indexOf("]"));
      $('#regmoney').val(value);
  }
});

$('#ymjl').on('click',
function() {
  layer.open({
      type: 2,
      title: '牙模记录',
      maxmin: true,
      shadeClose: true,
      //点击遮罩关闭层
      area: ['520px', '364px'],
      content: contextPath + '/KQDS_Tooth_DocAct/toYmjlWin.act?usercode=' + onclickrowOobj.usercode
  });
});

$('#lzjl').on('click',
function() {
  /* 这个弹框在add_cost.jsp页面中也有使用 */
  layer.open({
      type: 2,
      title: '流转记录',
      maxmin: true,
      shadeClose: true,
      //点击遮罩关闭层
      area: ['620px', '364px'],
      content: contextPath + '/KQDS_BCJLAct/toLzjlWin.act?usercode=' + onclickrowOobj.usercode
  });
});

$('#qfjl').on('click',
function() {
  layer.open({
      type: 2,
      title: '欠费记录',
      maxmin: true,
      shadeClose: true,
      //点击遮罩关闭层
      area: ['520px', '364px'],
      content: contextPath + '/KQDS_CostOrder_DetailAct/toQfqkWin.act?usercode=' + onclickrowOobj.usercode
  });
});
$('#zxjl').on('click',
function() {
  layer.open({
      type: 2,
      title: '咨询信息',
      maxmin: true,
      shadeClose: true,
      //点击遮罩关闭层
      area: ['900px', '364px'],
      content: contextPath + '/KQDS_ReceiveInfoAct/toReceiveWin.act?usercode=' + onclickrowOobj.usercode
  });
});
$('#lsbl').on('click',
function() {
  /* 这个弹框在add_cost.jsp页面中也有使用 */
  layer.open({
      type: 2,
      title: '历史病历',
      maxmin: true,
      shadeClose: true,
      //点击遮罩关闭层
      area: ['520px', '364px'],
      content: contextPath + '/KQDS_MedicalRecordAct/toLsblWin.act?usercode=' + onclickrowOobj.usercode + '&status=2'
  });
});
/* 消费记录 按钮的事件 */
$('#xfjl').on('click',
function() {
  layer.open({
      type: 2,
      title: '消费记录',
      maxmin: true,
      shadeClose: true,
      //点击遮罩关闭层
      area: ['900px', '364px'],
      content: contextPath + '/Kqds_PayCostAct/toXfjlWin.act?usercode=' + onclickrowOobj.usercode
  });
});
$('#xgzl').on('click',
function() {
  if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
      layer.alert('请选择患者', {
            
      });
      return false;
  }
  //修改资料
  parent.layer.open({
      type: 2,
      title: '修改患者资料',
      shadeClose: false,
      shade: 0.6,
      area: ['750px', '850px'],
      content: contextPath + '/KQDS_UserDocumentAct/toHzjd_Edit.act?usercode=' + onclickrowOobj.usercode 
  });
});
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".operatedDiv").outerHeight()-$("footer").outerHeight()-$("header").outerHeight()-50
	});
}
//根据日期获取当天休息的人员列表
/* function getRestPersonListByDate(){
	
	var mode = scheduler.getState().mode;
	if(mode != 'unit'){
		return;
	}
	
	
	var perIds = "";
	
	if(static_doctor_list == null){
		return;
	}
		
	for(var i=0;i<static_doctor_list.length;i++){
		var id = static_doctor_list[i].key + '';
		if(id.indexOf('empty') >= 0 ){
			continue;
		}
		perIds += id + ',';
	}
	
	if(perIds == ""){
		return false;
	}
	
	var data = {
		perIds: perIds,
		organization: static_organization,
        starttime: scheduler._min_date.getTime(),
        endtime: scheduler._max_date.getTime()
    };
	
    var requrl = contextPath + '/KQDS_PaibanAct/getRestPersonListByDate.act';
    $.axse(requrl, data,  // 同步请求
    function(data) {
        if (data.retState == '0') {
            // scheduler.blockTime(new Date(scheduler._min_date.getTime()), "fullday", { unit: data.perIndexList });
            static_perSeqIdList = "";
            for(var i=0;i<data.perIndexList.length; i ++){
            	var pindex = data.perIndexList[i];
            	
            	static_perSeqIdList = data.perSeqIdList;// 给页面全局变量赋值【休息】
            	var pName = $(".dhx_scale_bar:eq("+pindex+")").html();
            	if(pName.indexOf("【")<0){
            		var orderstatus = data.orderStatusList[i];
            		if(orderstatus=="" || orderstatus=="休息"){
            			pName += "<span style='color:red;font-size: 10px;'>【休息】</span>";
            		}else{
            			pName += "<span style='color:blue;font-size: 10px;'>【"+orderstatus+"】</span>";
            		}
            	}
            	$(".dhx_scale_bar:eq("+pindex+")").html(pName);
            	
            }
            
            
        	
        }
    },
    function() {

	});
} */
/* #########################################################################以下弹框多为本页面专用，不需要进行抽取  END  杨森  17-3-24 */
</script>

</html>
