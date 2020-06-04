<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	Object isyx = request.getAttribute("isyx");
	if(isyx == null){
		isyx = "0";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>今日患者</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yiliao/bingli_search.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style>
	/*查询条件中的样式  */
.searchWrap .formBox>section>ul.conditions>li{
	padding: 3px 0;
}
.searchWrap .formBox>section>ul.conditions>li>span{
	width:62px;
	text-align:right;
}
.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
.searchWrap .formBox>section>ul.conditions>li>select{
	width:94px;
}
@media screen and (max-width:1390px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:55px;
		text-align:right;
		font-size:11px;
		height:24px;
		line-height:24px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:82px;
		font-size:11px;
		padding:0 3px 0 5px;
		height:24px;
		line-height:24px;
	}
}
@media screen and (max-width:1100px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:51px;
		text-align:right;
		font-size:10px;
		height:22px;
		line-height:22px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:73px;
		font-size:10px;
		padding:0 3px 0 5px;
		height:22px;
		line-height:22px;
	}
}
	.pagination li{
		margin-left: 0px!important;
		height:auto!important;
	}
	.tableBox{
		border-bottom: 1px solid #ddd;
	}
	.dropdown-menu{
		min-width: auto!important;
	    padding: 0px 0!important;
	}
	.dropdown-menu li{
		margin-left: 0px!important;
	}
	.centerWrap .columnWrap .columnBd ul li.current {
    color: #00a6c0 !important;;
    border-bottom: 0px solid #00a6c0 !important;
}
/* 搜索框select */
.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 94px;   
      }  
.searchSelect>.btn { 
	width: 94px; 
	height:26px; 
	border: solid 1px #e5e5e5; 
}

.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
    color: #999;
    height: 26px;
}
.pull-left {
   float: left !important;
   color: #666;
}
.bootstrap-select.btn-group .dropdown-toggle .filter-option {
    margin-top: -3px;
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
	
/* 	解決标签查询中下拉框悬浮 */
/* .searchModule>section>ul.conditions { */
/*    	overflow: visible;  */
/*  	margin: 0; */
/* } */
.searchWrap{
	 overflow: visible;
}
.formBox{
	overflow: visible;
}
.searchWrap .formBox>section>ul.conditions {
	height: 128px;
    overflow: visible;
    margin: 0;
}
</style>

</head>
<body>
<div id="container">
       <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
				<div class="columnHd">
				    <span class="title">今日患者</span>
				</div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height="430"></table>
                    </div>
                    <div id="gongzuol">
			             <div class="columnBd">
			             	<ul class="dataCountUl" id="dataCount">
			             		<li>总记录数:<span id="zong">0</span></li>
			             		<li>成交数:<span id="cj">0</span></li>
			             		<li>未成交数:<span id="wcj">0</span></li>
			             	</ul>
			             </div>
			        </div>
                </div>
            </div>
            
            <div class="searchWrap">
	               <!--  <div class="cornerBox">查询条件</div> -->
	                <div class="searchToggleBtnGroup">
	                	<span class="ptcx checked">
	                		通用查询
	                	</span>
	                	<span class="gjcx">
	                		高级查询
	                	</span>
	                </div>
	                <div class="formBox">
	                	<section>
				    		<ul class="conditions">
				    			<li>
				    				<span>到院门诊</span>
									<select id="organization" name="organization"></select>
								</li>
								<li>
				    				<span>挂号时间</span>
		    						<input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
			                    </li>
				    			<li>
				    				<span>到</span>
	                              	<input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
			                    </li>
								<li>
				    				<span>模糊查询</span>
				    				<input type="text" id="searchValue" class="searchInput" placeholder="姓名/手机号" > 
				    			</li>
<%-- 				    		<% --%>
<!-- 							if(SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) == -1) { -->
<!-- 							%> -->
							
<%-- 							<%}else{ %> --%>
				    			<li class="toggleTr" id="tool">
				    				<span>患者来源</span>
<!-- 				    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');"> -->
<!-- 									</select> -->
									<select class="patients-source select2 dict searchSelect" resource="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelectSearch('devchannel','nexttype');" data-live-search="true" title="请选择">
									</select>
				    			</li>
				    			<li class="toggleTr" id="toolSon">
				    				<span>子分类</span>
<!-- 				    				<select class="select2 dict" name="nexttype" id="nexttype"> -->
<!-- 										<option value="">请选择</option> -->
<!-- 									</select> -->
									<select class="select2 dict searchSelect" name="nexttype" id="nexttype" data-live-search="true" title="请选择">
									</select>
				    			</li>
<%-- 				    		<%} %>   --%>
				    			<li class="toggleTr">
				    				<span>挂号分类</span>
			    					<select class="dict" tig="ghfl" name="regsort" id="regsort"></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>就诊分类</span>
				    				<select class="dict" tig="jzfl" name="recesort" id="recesort" ></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>受理类型</span>
			    					<select class="dict" tig="SLLX" id="shouli" ></select>
				    			</li>
				    		<%
							if(SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) == -1) {
							%>
							
							<%}else{ %>
				    			<li class="toggleTr">
				    				<span>受理工具</span>
<!-- 			    					<select class="dict" tig="SLGJ" id="gongju" ></select> -->
			    					<select class="dict searchSelect" tig="SLGJ" id="gongju" data-live-search="true" title="请选择" ></select>
				    			</li>
				    		<%} %> 
				    			<li class="toggleTr">
				    				<span>成交状态</span>
				    				<select  name="cjstatus" id="cjstatus" >
				                          <option value="">请选择</option>
				                          <option value="0">未成交</option>
				                          <option value="1">已成交</option>
									</select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>有无回访</span>
			                        <select id="ywhf">
		                        		<option value="">- 请选择 -</option>
		                        		<option value="0">无回访</option>
		                        		<option value="1">有回访</option>
		                        	</select>
				                </li>
				    		    <li class="toggleTr">
				    				<span>咨询</span>
<!-- 			    					<select  name="askpersonSearch" id="askpersonSearch" ></select>	 -->
			    					<select class="searchSelect" name="askpersonSearch" id="askpersonSearch" data-live-search="true" title="请选择" ></select>	
				    			</li>
				    			<li class="toggleTr">
				    				<span>医生</span>
<!-- 			    					<select class="searchSelect" name="doctorSearch" id="doctorSearch" ></select>	 -->
			    					<select class="searchSelect" name="doctorSearch" id="doctorSearch" data-live-search="true" title="请选择" ></select>	
				    			</li>
				    			<li class="toggleTr">
				    				<span>建档人</span>
			    					<input type="hidden" name="createuserSearch" id="createuserSearch"  class="form-control"  value=""/>
								    <input  type="text"  id="createuserSearchDesc" name="createuserSearchDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuserSearch', 'createuserSearchDesc'],'single',1);"></input>	
				    			</li>
				    			<li>
				    				<span>客服</span>  <!-- 添加客服部条件查询 -->
<!-- 				    				<select class="dict" name="customer" id="customer" data-bv-notempty data-bv-notempty-message="客服" ></select>  -->
				    				<select class="dict searchSelect" name="customer" id="customer" data-bv-notempty data-bv-notempty-message="客服" data-live-search="true" title="请选择"></select> 
			    				</li>
			    			</ul>
			    		</section>
	                
	                    <!-- <table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">到院门诊：</td>
				    			<td style="text-align:left;">
										<select id="organization" name="organization"></select>
								</td>
								<td style="text-align:right;">挂号时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="unitsDate">
				    						<input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
				                        </span>
				                </td>
				    			
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="unitsDate">
				                              <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
				                        </span>
								</td>
								<td style="text-align:right;">模糊查询：</td>
				    			<td style="text-align:left;">
				    				<input type="text" id="searchValue" class="searchInput" placeholder="姓名/手机号" > 
				    			</td>
				    		</tr>
				    		
				    		<tr class="toggleTr">
				    			<td style="text-align:right;">患者来源：</td>
				    			<td style="text-align:left;">
				    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');">
									</select>
				    			</td>
				    			
				    			<td style="text-align:right;">子分类：</td>
				    			<td style="text-align:left;">
				    				<select class="select2 dict" name="nexttype" id="nexttype">
										<option value="">请选择</option>
									</select>
				    			</td>
				    			<td style="text-align:right;">挂号分类：</td>
				    			<td style="text-align:left;">
				    					<select class="dict" tig="ghfl" name="regsort" id="regsort"></select>
				    			</td>
				    			<td style="text-align:right;">就诊分类：</td>
				    			<td style="text-align:left;">
				    				<select class="dict" tig="jzfl" name="recesort" id="recesort" ></select>
				    			</td>
				    		</tr>
				    		<tr class="toggleTr">	
				    			<td style="text-align:right;">受理类型：</td>
				    			<td style="text-align:left;">
			    					 <select class="dict" tig="SLLX" id="shouli" ></select>
				    			</td>
				    			<td style="text-align:right;">受理工具：</td>
				    			<td style="text-align:left;">
			    					<select class="dict" tig="SLGJ" id="gongju" ></select>
				    			</td>
				    			
				    			<td style="text-align:right;">成交状态：</td>
				    			<td style="text-align:left;">
				    				<select  name="cjstatus" id="cjstatus" >
					                          <option value="">请选择</option>
					                          <option value="0">未成交</option>
					                          <option value="1">已成交</option>
										</select>
				    			</td>
				    			<td style="text-align:right;">有无回访：</td>
				    			<td style="text-align:left;"> 
			                         <select id="ywhf">
		                        		<option value="">- 请选择 -</option>
		                        		<option value="0">无回访</option>
		                        		<option value="1">有回访</option>
		                        	</select>
				                </td>
				    		    
				    		</tr>
				    		<tr class="toggleTr">
					    		
								<td style="text-align:right;">咨询：</td>
				    			<td style="text-align:left;">
				    					<select  name="askpersonSearch" id="askpersonSearch" ></select>	
				    			</td>
				    			<td style="text-align:right;">医生：</td>
				    			<td style="text-align:left;">
				    					<select  name="doctorSearch" id="doctorSearch" ></select>	
				    			</td>
				    			<td style="text-align:right;">建档人：</td>
				    			<td style="text-align:left;">
				    					<input type="hidden" name="createuserSearch" id="createuserSearch"  class="form-control"  value=""/>
									    <input  type="text"  id="createuserSearchDesc" name="createuserSearchDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuserSearch', 'createuserSearchDesc'],'single',1);"></input>	
				    			</td>
				    		</tr>
				    	</table> -->
	                    <div class="btnBar" id="bottomBarDdiv" style="text-align: left;">
	                     	 <a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>
	                     	 <a href="javascript:void(0);" class="kqdsCommonBtn hide" id="kf_scbb" onclick="exportTable()">生成报表</a>
	               		     <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="searchHzda()">查询</a>
	               		     <a href="javascript:void(0);" class="kqdsSearchBtn" id="xxcx" onclick="xxcx()">信息查询</a>
			            </div>
	                </div>
	            </div>
        </div>
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var deptId = '<%=person.getDeptId()%>';
var onclickrowOobj = "";
var pageurl = contextPath + '/KQDS_REGAct/selectToday.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
var menuid = "<%=menuid%>";
var isyx = "<%=isyx%>";
var qxnameArr = ['rskcx_wd','xfmx_all_wd','wd_jrhz_scbb'];
var func = ['rskcx','xfmx','exportTable'];

//登录权限licc--2020-1-8
var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(","); //允许权限

<%-- var allowedpersonBtn='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCKBtn)%>'; --%>
// var allowedBtn=allowedpersonBtn.split(","); //允许权限查询按钮
// console.log(allowedBtn+"-----allowedBtn");
var allowedBtn=["afa8bf41-af02-4504-99b9-b8b69de40aca","34379b48-ac6f-4b61-9a99-1b427183ef25","eea5c430-1d44-40bb-ba16-0ffad8b09697","190e9256-2aeb-453a-9e8a-e35c0c7a48dc","794d8599-9b96-4461-b2c1-05b5c373d486","5d80d52d-9ca4-4389-be27-ae4a50d7e8c6","46721f65-18ec-463f-b18c-e165fa589fbe","388fcdf9-4969-46c6-b767-a7c3b7905f2f","005e6673-15ab-46d7-b0f4-bb1ed99556a4","713a8067-6890-4157-b7ee-b7d981401c8a","6ff236ec-da8f-40d8-ba0b-e9105bd89f5d","c17de419-b6d4-4eea-b2f6-4f7422b94914","2589c8f7-f4ec-4247-8866-067550cc5ddb","4b32630c-13ac-444e-8dcc-0f3844a2d2bb","b586afb5-6b6e-442a-9387-246c5dfa2db9","a0c50df9-3ba1-47ac-a739-6b2d17e3bc91","de063632-8dc5-45f4-a616-b27ada252406","5bcd3e00-a179-4265-8a9d-7fc966541016","3ee5815c-f2b1-40c7-844a-489d0ff3f256","fc47af55-26cc-4617-863d-6ac5be7b4d34","0dfa1271-72b7-4593-b5d7-328b2d9567d5","1f7fd635-241d-4969-93ae-4ffeb3b5b78f","45a11365-4add-4a2e-81c4-ad12349a6592","0d82d20e-404a-43d0-a977-99c208b1690a"];
// console.log(allowedBtn+"-----allowedBtn");
//判断当前人员权限是否有查看患者来源和子分类等着资源
var total=load.concat(allowed);
function isExist(total) {
    var exist = {};
    for(var i in total) {
        if(exist[total[i]]) {
            return true;
        }
        exist[total[i]] = true;
    }
    return false;
}
//判断当前人员权限是否有查看患者来源和子分类等着资源的按钮
var totalbtn=load.concat(allowedBtn);
function isExistBtn(totalbtn) {
    var exist = {};
    for(var i in totalbtn) {
        if(exist[totalbtn[i]]) {
            return true;
        }
        exist[totalbtn[i]] = true;
    }
    return false;
}
var existornotBtn=isExistBtn(totalbtn);//资源隐藏按钮
if(!existornotBtn){
		$('#tool').attr('class','toole').attr('style','display:none');
		$('#toolSon').attr('class','toolSone').attr('style','display:none');
}else{}
// console.log(existornotBtn+"------existornotBtn");

$(function() {
	//initHosSelectListNoCheck('organization'); // 连锁门诊下拉框		
    initHosSelectListNoCheck('organization','<%=ChainUtil.getCurrentOrganization(request)%>'); // 加载门诊列表
	//咨询 下拉列表
	initPersonSelectByDeptType("askpersonSearch","<%=ConstUtil.DEPT_TYPE_0 %>");
	//医生 下拉列表
	initPersonSelectByDeptType("doctorSearch","<%=ConstUtil.DEPT_TYPE_1 %>");
	//初始化客服下拉框
    initSysUserByDeptId($("#customer"),"customer","loginName");
    getButtonPowerByPriv(qxnameArr,func,menuid);
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	
	initDictSelectByClassIsBtnshow(existornotBtn,'triggerChange');//患者来源根据部门
    initDictSelectByClass(); // 就诊分类、挂号分类
    //获取当前页面所有按钮
    getButtonAll("<%=menuid%>");
	getButtonAllCurPage(menuid);//获取当前页面所有按钮
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    togglemodel.initial('jrhz',pageurl);/*wl 初始化 开关模块 */
    //4、表格初始化
    initTable();
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
    $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
});

 /**
  * 初始化患者来源 （专属客户部人员）licc 2020.1.15
  */
 function initDictSelectByClassIsBtnshow(existornotBtn,operFlag) {
     if ($(".dict").length > 0) {
         for (var i = 0; i < $(".dict").length; i++) {
             var dict = $(".dict").eq(i);
             // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
             var type = dict.attr("resource");
             var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
             $.axse(url, null,
             function(data) {
                 var list = data.list;
                 if (list != null && list.length > 0) {
                     dict.empty();
                     dict.append("<option value=''>请选择</option>");
                     for (var j = 0; j < list.length; j++) {
                        	 var optionStr = list[j];
 	                     if(loadperson=="c17de419-b6d4-4eea-b2f6-4f7422b94914"||loadperson=="afa8bf41-af02-4504-99b9-b8b69de40aca"){
                     		 //根据登录人员是前台
 	                        	if(optionStr.seqId=="sc569"||optionStr.seqId=="zrdz510"||optionStr.seqId=="lpyjs106"||optionStr.seqId=="qdzz13"||optionStr.seqId=="ptlz562"||optionStr.seqId=="pyjs900"||optionStr.seqId=="nbygkf734"||optionStr.seqId=="yyyg526"||optionStr.seqId=="wzqdjs66"){
 	                        		dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
 	                        	}else{
 	                        		dict.append("<option value='" + optionStr.seqId + "' style='display:none;'>" + optionStr.dictName + "</option>");
 	                        	}
 	                   	 }else if(loadperson=="005e6673-15ab-46d7-b0f4-bb1ed99556a4"||loadperson=="388fcdf9-4969-46c6-b767-a7c3b7905f2f"){
 	                   		 //根据登录人员是客户部
 	                         	if(optionStr.seqId=="lpyjs106"){
 	                         		dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
 	                         	}else{
 	                         		dict.append("<option value='" + optionStr.seqId + "' style='display:none;'>" + optionStr.dictName + "</option>");
 	                         	}
 	                   	 }else{
 	                       	dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
 	                   	 }
                     }
                 }
                 if ('triggerChange' == operFlag) { // 触发Onchange事件 #### add by yangsen 
                     $(dict).trigger('change'); // 和 连锁代码片段的 $("#organization").change 配合使用
                 }
             },
             function() {
                 layer.alert('查询出错！'  );
             });
         }
     }
 }
 
function initTable(status, type) {
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    $('#table').bootstrapTable({
        url: pageurl,
        queryParams: queryParams1,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        pageNumber : 1,
        //clickToSelect: false,
        singleSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
//         	隐藏患者来源子分类
			var existornot=isExist(total);//资源隐藏判断条件ZY_LYCK
        	if(!existornot){
//         		console.log("------------此用户不可查看患者来源子分类受理工具");
        		$('#table').bootstrapTable('hideColumn', 'devchannel');
        		$('#table').bootstrapTable('hideColumn', 'nexttype');
        		$('#table').bootstrapTable('hideColumn', 'accepttool');        		
        	}else{
//         		console.log("------------此用户可查看");
        	}  
            var tableList = data.nums[0];
            $("#zong").html(data.total);
            /* var cjs = 0,
            wcjs = 0;
            for (var i = 0; i < tableList.length; i++) {
                if (tableList[i].cjstatus == "1") {
                    cjs = cjs + 1;
                } else {
                    wcjs = wcjs + 1;
                }
            } */
            $("#cj").html(tableList.cjstatus);
            $("#wcj").html(Number(data.total)-Number(tableList.cjstatus));
          	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (Number(row.del) > 0) {
                strclass = 'active'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
				{
				    title : '序号',
				    align: "center",
				    width: 40,
				    formatter: function (value, row, index) {
				     /* return index + 1; */
				     var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
				        var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
				        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
				    }
				   },
				{
				    title: '到院门诊',
				    field: 'organizationname',
				    align: 'center',
				    visible: true,
				    switchable: false,
				    formatter: function(value, row, index) {
				        if (value != "" && value != null) {
				            return '<span title="' + value + '">' + value + '</span>';
				        }else{
				        	return '<span></span>';
				        }
				    }
				},
		        
		        {title: '挂号时间',field: 'createtime',align: 'center',sortable: true,
		            formatter: function(value, row, index) {
		                return '<span>' + value + '</span>';
		            }
		        },
		        {title: '患者编号',field: 'usercode',align: 'center',sortable: true,
		            formatter: function(value, row, index) {
		                return '<span title="' + value + '">' + value + '</span>';
		            }
		        },
		        {
		        	title: '姓名',
		        	field: 'username',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="name" title="' + value + '">' + value + '</span>';
		            }
		        },
		        {
		        	title: '手机号码1',
		        	field: 'phonenumber1',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		                if (value != null || value != "") {
		                    if (canlookphone == 0) {
		                        return '<span title="' + value + '">' + value + '</span>';
		                    } else {
		                        return '<span>-</span>';
		                    }
		                } else {
		                    return '<span>-</span>';
		                }
		            }
		        },
		        {
		        	title: '性别',
		        	field: 'sex',
		        	align: 'center',
		        	sortable: true,
		        	formatter:function(value,row,index){
		        		return '<span>'+value+'</span>';
		        	}
		        	
		        },
		        {title: '年龄',field: 'age',align: 'center',sortable: true,
		            formatter: function(value, row, index) {
		                if (value == "0") {
		                    return '<span>-</span>';
		                } else {
		                    return '<span>'+value+'<span>';
		                }
		            }
		        },
		        {
		        	title: '客户等级',
		        	field: 'important',
		        	align: 'center',
		        	
		        	sortable: true,
		            formatter: function(value, row, index) {
		                if (value == "1") {
		                    return '<span>'+'一级'+'</span>';
		                } else if (value == "2") {
		                    return '<span>'+'二级'+'</span>';
		                } else if (value == "3") {
		                    return '<span>'+'三级'+'</span>';
		                } else if (value == "4") {
		                    return '<span>'+'四级'+'</span>';
		                } else if (value == "5") {
		                    return '<span>'+'五级'+'</span>';
		                } else if (value == "2") {
		                    return '<span>'+''+'</span>';
		                }else{
		                	return "<span>-</span>";
		                }
		            }
		        },
		        {
		        	title: '建档人',
		        	field: 'jdr',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
			                return '<span class="name">' + value + '</span>';
			         }
		        },
		        {
		        	title: '建档时间',
		        	field: 'jdsj',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="time">' + value+ '</span>';
		            }
		        },
		        {
		        	title: '成交状态',
		        	field: 'cjstatus',
		        	align: 'center',
		        	sortable: true,
		            formatter: function(value, row, index) {
		                var html = "";
		                if (value == "0") {
		                    html = '<span class="label label-danger">未成交</span>';
		                } else if (value == "1") {
		                    html = '<span class="label label-success">已成交</span>';
		                }
		                return html;
		            }
		        },
		        {
		        	title: '就诊分类',
		        	field: 'recesort',
		        	align: 'center',
		        	sortable: true,
		        	formatter: function(value, row, index) {
			            return '<span class="source">' + value + '</span>';
			        }
		        },
		        {
		        	title: '挂号分类',
		        	field: 'regsort',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
			                return '<span class="source">' + value + '</span>';
			         }
		        }, 
		        {
		        	title: '就诊科室',
		        	field: 'regdept',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
		        		 	if(value == null){
		        		 		value = '<span class="name"></span>';
		        		 	}
			                return '<span class="name">' + value + '</span>';
			         }
		        },
		        {
		        	title: '咨询',
		        	field: 'askperson',
		        	align: 'center',
		        	sortable: true,
		        	 formatter: function(value, row, index) {
		        		 	if(value == null){
		        		 		value = '<span class="name"></span>';
		        		 	}
			                return '<span class="name" title="'+value+'">' + value + '</span>';
			         }
		        },
		        {
		        	title: '医生',
		        	field: 'doctor',
		        	align: 'center',
		        	sortable: true, 
		        	 formatter: function(value, row, index) {
		        		 	if(value == null){
		        		 		value = '<span class="name"></span>';
		        		 	}
			                return '<span class="name">' + value + '</span>';
			         }
		        },
		        {	
		        	title: '受理类型',
		            field: 'accepttype',
		            align: 'center',
		            
		            sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="source">' + value + '</span>';
		         	}
		        },
		        {
		            title: '受理工具',
		            field: 'accepttool',
		            align: 'center',
		            
		            sortable: true,
		            formatter: function(value, row, index) {
		                return '<span class="source">' + value + '</span>';
		         	}
		        },
		        {title: '患者来源',field: 'devchannel',align: 'center',sortable: true,
		        	 formatter: function(value, row, index) {
			                return '<span class="source">' + value + '</span>';
			         }
		        },
		        {title: '来源子分类',field: 'nexttype',align: 'center',sortable: true,
		        	 formatter: function(value, row, index) {
			                return '<span class="source">' + value + '</span>';
			         }
		        },
		        {
		            title: '客服',
		            field: 'kefuname',
		            align: 'center',
		            
		            sortable: true,
		            formatter: function(value, row, index) {
		                if (value != "" && value != null) {
		                    return '<span class="name" title="' + value + '">' + value + '</span>';
		                } else {
		                    return "<span class='name'>-</span>";
		                }
		            }
		        },
		        {title: '知情同意书',field: 'cisprint',align: 'center',/* sortable: true, */
		            formatter: function(value, row, index) {
		                if (row.cisprint == "1" || row.misprint == "1") {
		                    return '<span>'+"已打印"+'</span>';
		                } else {
		                    return '<span>'+"未打印"+'</span>';
		                }
		            }
		        },
		        {title: '病历',field: 'ifmedrecord',align: 'center',
		            formatter: function(value, row, index) {
		                var html = "";
		                if (row.ifmedrecord == "1") {
		                    html = '<span class="label label-success">已填写</span>';
		                } else {
		                    html = '<span class="label label-danger">未填写</span>';
		                }
		                return html;
		            }
		        },
		        {title: '建档导医',field: 'jddy',align: 'center',sortable: true,
		        	 formatter: function(value, row, index) {
		        		 if(value){
		        			  return '<span class="name">' + value + '</span>';
		        		 }else{
		        			 return '<span class="name"></span>'
		        		 }
			         }
		        },
		        {title: '挂号人',field: 'createuser',align: 'center',sortable: true,
		        	 formatter: function(value, row, index) {
			                return '<span class="name">' + value + '</span>';
			         }
		        },
		        {title: '挂号导医',field: 'dy',align: 'center',sortable: true,
		        	 formatter: function(value, row, index) {
		        		 if(value){
		        			  return '<span class="name">' + value + '</span>';
		        		 }else{
		        			 return '<span class="name"></span>'
		        		 }
			         }
		        },
		        {
		            title: '有无回访',
		            field: 'ywhf',
		            align: 'center',
		            
		            sortable: true,
		            formatter: function(value, row, index) {
		                if (value == "无回访") {
		                  	return "<span class='label label-danger'>无回访</span>";
		                }else{
		                	return '<span class="label label-success">'+"有回访"+'</span>';
		                }
		            }
		        },
		        {
		        	title: 'seq_id',
		        	field: 'seq_id',
		        	align: 'center',
		        	valign: 'middle',
		        	visible: false,
		        	switchable: false
		        	
		        }
        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        //showpersoninfo(onclickrowOobj);//展示右侧个人信息
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}
function queryParams(params) {
	var depttype = '';
	if(isyx == 1){ // 1 是营销
		depttype = 3;
	}
	if(isyx == 2){ // 2 是网电
		depttype = 2;
	}
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        //可以查询挂号表医生 咨询不是自己，收费项目种有自己的
        depttype:depttype,//2 网电 3 营销  空 是客服
        ywhf: $('#ywhf').val(),
        organization: $('#organization').val(),
        doctorSearch: $('#doctorSearch').val(),
        askpersonSearch: $('#askpersonSearch').val(),
        regsort: $('#regsort').val(),
        cjstatus: $('#cjstatus').val(),
        recesort: $('#recesort').val(),
        searchValue: $('#searchValue').val(),
        createuserSearch: $('#createuserSearch').val(),
        starttime: $('#starttime').val(),
        shouli: $('#shouli').val(),
        gongju: $('#gongju').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        customer: $('#customer').val(),
        endtime: $('#endtime').val(),
        sortName:this.sortName,
        sortOrder:this.sortOrder,
    };
    return temp;
}
function queryParams1(params) {
	var depttype = '';
	if(isyx == 1){ // 1 是营销
		depttype = 3;
	}
	if(isyx == 2){ // 2 是网电
		depttype = 2;
	}
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        //可以查询挂号表医生 咨询不是自己，收费项目种有自己的
        depttype:depttype,//2 网电 3 营销  空 是客服
        ywhf: $('#ywhf').val(),
        organization: $('#organization').val(),
        doctorSearch: $('#doctorSearch').val(),
        askpersonSearch: $('#askpersonSearch').val(),
        regsort: $('#regsort').val(),
        cjstatus: $('#cjstatus').val(),
        recesort: $('#recesort').val(),
        searchValue: $('#searchValue').val(),
        createuserSearch: $('#createuserSearch').val(),
        starttime: $('#starttime').val(),
        shouli: $('#shouli').val(),
        gongju: $('#gongju').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        customer: $('#customer').val(),
        endtime: $('#endtime').val(),
        limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        sortName:this.sortName,
        sortOrder:this.sortOrder,
        pageIndex : params.offset/params.limit + 1
    };
    return temp;
}
function searchHzda() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function clean() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#regsort").val("").trigger("change");
    $("#cjstatus").val("").trigger("change");
    $("#devchannelSearch").val("").trigger("change");
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
    $(".searchSelect li.selected").empty();//清空
    $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc

}
//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

function rskcx(){
	 layer.open({
         type:
         2,
         title: '日收款查询',
         shadeClose: false,
         shade: 0.6,
         area: ['80%', '85%'],
         content: contextPath + '/KQDS_ScbbAct/toRsktj_search_wdperson.act?menuId=' + menuid
     });
}
function xfmx(){
	 layer.open({
       type:
       2,
       title: '日收款明细',
       shadeClose: false,
       shade: 0.6,
       area: ['98%', '95%'],
       content: contextPath + '/KQDS_ScbbAct/toBb_Sfmx_all_Wdperson.act?menuId=' + menuid
   });
}

function xxcx(){
	// 信息查询
    window.location.href = contextPath + '/KQDS_UserDocumentAct/toXxcxCenter.act?menuId=' + menuid;
}

/**
 *  设置按钮权限操作 
 */
function getButtonPower(){
	var menubutton1 = "";
	// 创建一个数组，存放listbutton的qxName 
	var listbuttonArray = new Array();
	for ( var i = 0; i < listbutton.length; i++) {
		listbuttonArray[i] = listbutton[i].qxName;
	}
	/* 按钮 */
	var btnList =  '[';
		btnList	+= '{"qx":"kf_scbb","name":"生成报表"},';
		btnList	+= '{"qx":"scbb","name":"生成报表"}';
	    btnList	+= ']';
	    var jsonbtnList = jQuery.parseJSON( btnList );
		for( var i = 0; i < jsonbtnList.length; i++){
			// 判断当前用户具备的按钮权限
			var index = jQuery.inArray(jsonbtnList[i].qx, listbuttonArray);
			// index = -1 时，表示当前用户没有此按钮的操作权限
			if( index == -1 ){//无权限的展示
			} else {//有权限的展示
				$("#"+jsonbtnList[i].qx).removeClass("hide");
			}
		}
}
</script>
</html>
