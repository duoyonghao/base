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
	String username = request.getParameter("username");
	String createuser = person.getSeqId();
	String userId = request.getParameter("userId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> <!-- 清缓存 -->
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> <!-- 清缓存 -->
<meta http-equiv="Expires" content="0"> <!-- 清缓存 -->
<title>临床路径管理</title><!-- 医疗中心 临床路径 菜单  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />

<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>	 --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>


<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/indexTab.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/sys_picker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/kqds/kqds_system.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<style>
	/* .fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		border-top-left-radius: 6px;
		border-top-right-radius: 6px; 
		overflow: hidden;
	}  */
	.searchWrap{
	    margin-top: 20px;
   		overflow: hidden;
	}
	.tableLayout{/*  */
		margin:0 auto;
		width:98%;
	}
	.tableLayout tr{
		height:42px;
	}
	#searchvalue{/* 模糊查询搜索框 */
		width:157px;
	}
	.searchWrap .btnGroups{		/*按钮组右浮动  */
		float:right;
		overflow:hidden;
	}
	.searchWrap .btnGroups .aBtn{
		margin:0 3px;
		display:block;
		color:#0E7CC9;
		background:#fff;
		height:28px;
		padding:0 15px;
		line-height:28px;
		border-radius:16px;
		text-align:center;
		border:1px solid #0E7CC9;
		font-size:13px;
	}
	.searchWrap .btnGroups .aBtn:hover{
		cursor:pointer;
		color:#fff;
		background:#0E7CC9;
	}
	select{/*普通select inputsss */
		box-sizing:border-box;
		width:80px;
		color:#333;
		height:24px;
		border-radius:4px;
		transition:box-shadow linear 300ms;
	}
	input[type="text"]{/*普通select inputsss */
		box-sizing:border-box;
		width:86px;
		color:#333;
		height:24px;
		border-radius:4px;
		transition:box-shadow linear 300ms;
	}
	select{
		cursor:pointer;
	}
	/* input[type="text"]:focus,select:focus,textarea:focus{
	    box-shadow: 0 0 8px rgb(49, 165, 247);
	    border-color:transparent;
	} */
	.btnGroups{
		float:right;
	}
	.btnGroups a.aBtn{
		float:left;
	}
	.textContent{
		display:inline-block;
		text-align:right;
	}
	.orangeText{
		color:#FA6406;
	}
	.fixed-table-container thead th .sortable{/*覆盖样式 表头右边距导致无法居中的问题  */
		padding-right:8px;
	}
	.searchModule{
		margin-top:10px;
	}
	.hei{
		margin-top: 12px;
	}
	
		/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 100px;   
	       height:24px; 
	    }  
	.searchSelect>.btn { 
		    width: 100px; 
		 	height:24px; 
/* 		 	box-sizing:border-box; */
		 	padding: 0 !important;
		 	border: solid 1px #e5e5e5; 
		}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
	    color: #999;
	    height: 22px;
   		line-height: 20px;
	}
	.pull-left {
	    float: left !important;
	    color: #333;
	}
	.btn-group > .btn:first-child:hover {
	    background: white;
	}
	.bootstrap-select.btn-group .dropdown-toggle .filter-option {
/*     padding-left: 5px; */
/*     width: 100px; */
	}
	
  	.searchWrap .text {  
  		    position: static !important;   
  		    left: 5px;  
  		    top: 9px;  
  		    color:#333;;  
 		} 
 		
 	.bootstrap-select.btn-group .dropdown-toggle .filter-option {
    	 width: 75px; 
    	 margin-left: 10px;
    	 margin-top: 2px;
	}	 
		
	/* 	解決标签查询中下拉框悬浮 */
	.searchWrap{
		 overflow: visible;
		 border-top: 1px solid #00a6c0;
	}
	.formBox{
		/* overflow: visible; */
	}
	.searchWrap .formBox>section {
		 height: 100px;
	}
	.searchWrap .formBox>section>ul.conditions {
	    overflow: visible;
	    height: 100%;
	    position: relative;
	}
	.searchWrap .formBox>section{
		height: 130px
	}
	.searchWrap .cornerBox {
    	box-sizing: border-box;
    	background-color: #00a6c0;
	    color: white;
	    border: 1px solid #00a6c0;
	    font-weight: bold;
	    border-top-left-radius: 4px;
	    border-top-right-radius: 4px;
	}
	.kv>span{
		line-height: 28px;
		color: #666;
	}
	.searchWrap .kv input[type=text]{
		margin-top: 4px;
	}
	.searchBox{
		margin-top: 2px;
	}
	.image-icon {
	    display: block;
	    position: absolute;
	    top: 0px;
	    left: 0px;
	    width: 25px;
	    height: 25px;
	}
	.notRead {
	    width: 8px;
	    display: block;
	    position: absolute;
	    left: 17px;
	    top: -8px;
	    height: 16px;
	    padding: 0 6px;
	    border-radius: 10px;
	    line-height: 16px;
	    font-size: 12px;
	    background: #00a6c0;
	    color: #fff;
	    border: 0px;
	    outline: none;
	}
	.not{
		margin-left: 25px
	}	
	.formBox{overflow: visible;}
</style>
</head>
<body>
<!-- <div id="container"> -->
    <div class="main" style="margin-left:20px;">
        <div class="listWrap">
            <div class="listHd">
            	<span class="title">患者列表</span>
           	</div>
           	
            <div class="tableBox" style="overflow: hidden;border-radius:8px;border-top-left-radius: 6px;border-top-right-radius: 6px;border-left: 1px solid #ddd;border-right: 1px solid #ddd;border-bottom:1px solid #ddd;">
                <table id="table" class="table-striped table-condensed table-bordered" data-height="550"></table>
            </div>
        </div>
        
        <div class="searchWrap" style="padding-top:30px;">
                <div class="cornerBox">查询条件</div>
                <div class="formBox">
                    <div class="searchBox">
                        <div class="kv hide">
                            <label style="width:70px;">患者姓名</label>
                            <span style="width:0px;" id="userId" class="userId hide"></span>
                            <div class="kv-v">
                                  <input type="text" name="username" id="username" placeholder="患者姓名" style="width: 100px;"  autocomplete="off"/>
                            </div>
                        </div>
                        <div class="kv hide">
                            <label style="width:70px;">手机号</label>
                            <div class="kv-v">
                                  <input type="text" name="phonenumber" id="phonenumber" placeholder="手机号" style="width: 100px;"  autocomplete="off"/>
                            </div>
                        </div>
                        <div class="kv">
                            <label style="width:70px;">模糊查询</label>
                            <div class="kv-v">
                                  <input type="text" name="usercodeorname" id="usercodeorname" placeholder="姓名/手机号/病历号" style="width: 140px;" autocomplete="off"/>
                            </div>
                        </div>
                        <div class="kv">
                            <label style="width:70px;">节点</label>
                            <div class="kv-v">
                                  <select class="dict searchSelect" name="nodename" id="nodename" data-bv-notempty data-bv-notempty-message="节点" style="width: 100px;" data-live-search="true">
										<option value="">- 请选择 -</option>
								  </select>
                            </div>
                        </div>
                        <div class="kv">
                            <label style="width:70px;">跟踪方式</label>
                            <div class="kv-v">
                                  <select class="dict searchSelect" name="ssType" id="ssType" data-bv-notempty data-bv-notempty-message="跟踪方式" style="width: 100px;" data-live-search="true">
										<option value="">- 请选择 -</option>
					    				<option value="单颗多颗">单颗多颗</option>
					    				<option value="Locator">Locator</option>
					    				<option value="All-on-x">All-on-x</option>
								  </select>
                            </div>
                        </div>
                        <!-- <div class="kv">
                            <label style="width:70px;">是否植骨</label>
                            <div class="kv-v">
                                  <select class="dict" name="isBone" id="isBone" data-bv-notempty data-bv-notempty-message="是否植骨" style="width: 100px;">
										<option value="">- 请选择 -</option>
					    				<option value="是">是</option>
					    				<option value="否">否</option>
									</select>
                            </div>
                        </div> -->
                        <div class="kv">
                            <label style="width:70px;">助理</label>
                            <div class="kv-v">
                            	  <select id="counsellor" name="counsellor" class="dict searchSelect" data-bv-notempty data-bv-notempty-message="咨询师" style="width: 100px;" data-live-search="true" title="- 请选择  -"></select>
<!--                                   <select class="dict" name="counsellor" id="counsellor" data-bv-notempty data-bv-notempty-message="咨询师" style="width: 100px;"></select> -->
                            </div>
                        </div>
                        <div class="kv">
                            <label style="width:70px;">种植医师</label>
                            <div class="kv-v">
                             <select id="plant_physician" name="plant_physician" class="dict searchSelect" data-bv-notempty data-bv-notempty-message="种植医师" style="width: 100px;" data-live-search="true" title="- 请选择  -"></select>
<!--                                   <select class="dict" name="plant_physician" id="plant_physician" data-bv-notempty data-bv-notempty-message="种植医师" style="width: 100px;"></select> -->
                            </div> 
                        </div>
                        <div class="kv">
                            <label style="width:70px;">修复医师</label>
                            <div class="kv-v" style="margin-right: 10px;">
                              <select id="repair_physician" name="repair_physician" class="dict searchSelect" data-bv-notempty data-bv-notempty-message="修复医师" style="width: 100px;" data-live-search="true" title="- 请选择  -"></select>
<!--                                   <select class="dict" name="repair_physician" id="repair_physician" data-bv-notempty data-bv-notempty-message="修复医师" style="width: 100px;"></select> -->
                            </div>
                        </div>
                        <div class="kv units hide" id="lclj_cjsj" style="height: 33px;">
                        		<span>创建时间</span>
	                        		<input type="text" placeholder="开始日期" id="starttime" readonly>
	                        	<span>到</span>
	                        		<input type="text"  placeholder="结束日期" id="endtime" readonly>
                        </div>
                    </div>
                    <div style="border-top:1px solid #ddd;clear:both;padding-top:10px;padding-bottom:5px;padding-left:15px;">
                    	<!-- <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="buttonFun.oprater(this);" id="oprate">创建临床路径</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="buttonFun.detail(this);" id="details">查看详情</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="buttonFun.electiveOperation(this);" id="electiveOperation">添加预约</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="buttonFun.visit(this);" id="visit">添加回访</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="buttonFun.prescribe(this);" id="prescribe">术前取模定咬合</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="buttonFun.deleteLclj(this);" id="lclj_sc">删除</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="buttonFun.editLclj(this);" id="lclj_edit">编辑</a>  -->
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.oprater(this);" id="lclj_cjlclj">患者信息录入</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.detail(this);" id="lclj_ckxq">路径创建与详情查看</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.electiveOperation(this);" id="lclj_tjyy">添加预约</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.visit(this);" id=lclj_tjhf>添加回访</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.prescribe(this);" id="lclj_sqcmdyh">术前取模定咬合</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.deleteLclj(this);" id="lclj_sc">删除</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.editLclj(this);" id="lclj_edit">编辑</a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.scbbLclj(this);" id="lclj_scbb">生成报表</a>
                    	
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.askPrevious(this);" id="lclj_zsbs"><font class="ask_Previous">主诉及病史</font></a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.examineDiagnose(this);" id="lclj_jc"><font class="examine_diagnose">检查与诊断</font></a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.diagnosisCase(this);" id="lclj_zl"><font class="diagnosis_case">诊疗方案</font></a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.plantKnowbook(this);" id="lclj_tys"><font class="plantKnowbook">同意书</font></a>
                    	<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.repairBill(this);" id="lclj_xf"><font class="xiufu_test">修复确认单</font></a>
                     	<a id="clean" href="javascript:void(0);"  class="kqdsCommonBtn">清空</a>
                    	<a id="searchHzlclj" href="javascript:void(0);"  class="kqdsSearchBtn" id="remindlclj" onclick = "query();">查询</a>
                    	<div style="display:inline-block;position: relative;" class="kqdsCommonBtn hide" onclick = "remindlclj();" id="lclj_shtx">
	                    	<img class="image-icon messageIcon" src="/base/static/image/kqdsFront/img/icon/messageIcon.png"/>
	                    	<input id="notNum" type="button" class="notRead" value="0"></input>
	                    	<p class="not">审核提醒</p>	                    	
                    	</div>
                    	<div style="display:inline-block;position: relative;" class="kqdsCommonBtn hide" onclick = "awaitRemindlclj();" id="lclj_ddsh">
	                    	<img class="image-icon messageIcon" src="/base/static/image/kqdsFront/img/icon/messageIcon.png"/>
	                    	<input id="awaitNum" type="button" class="notRead" value="0"></input>
	                    	<p class="not">待审核提醒</p>	                    	
                    	</div>   
                    </div>
                </div>
        </div>
	</div>  
</body>
<script type="text/javascript">
var createuserid = "<%=createuser%>";
// console.log(createuserid+"---------------------------createuserid");
//存储表格中点击的某一个行的对象,在zhcx.js的 initTable 方法中赋值
var onclickrowOobj = "";
var testaa;
var contextPath = "<%=contextPath%>";
var menuid = "<%=menuid%>";
var userId = "<%=userId%>";
var listbutton;
var pageurl = '<%=contextPath%>/HUDH_FlowAct/findOrderTrackInforByOrderNumber.act';
var pageurl1 = '<%=contextPath%>/HUDH_FlowAct/findOrderTrackInforByConditionQuery.act';
var selectOrderNumber;
var nowday;
var id;
var seqId;
var nodesinfor;
var tableDataforSon;
var consultSelectPatient;//选中患者信息对象
var lcljId;
var func = ['exportTable'];
$(function() {
	vetoNum();
	awaitVerifieNum();
	//时间选择
    $(".units input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
	
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
	 
	reciveUsername();
	//获取当前页面所有按钮
	getButtonAllCurPage(menuid);
	//initDictSelectByClass();
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        queryParams: queryParams,
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        //pageNumber: 1,
        //在表格底部显示分页工具栏 
        //singleSelect: true,
        //striped: true,//是否显示行间隔色
        paginationShowPageGo: true,
        //showPaginationSwitch: true,//是否显示数据条数选择框（隐藏/显示分页）
        //showColumns: true,   //是否显示所有的列（选择显示的列）
        //showRefresh: true,   //是否显示刷新按钮
      	//显示导出插件（包括excel）
        //showExport: true, 
        //onlyInfoPagination: true,
        //smartDisplay: true, // 智能显示 pagination 和 cardview 等
        //clickToSelect: true,
        //smartDisplay: true,
        //toolbar: '#toolbar',//工具按钮用哪个容器
        onLoadSuccess: function(data) { //加载成功时执行\
         	//console.log(JSON.stringify(data)+"--------------data");
        	 tableDataforSon=data;
        	 var tableList = $('#table').bootstrapTable('getData');
             $("#size").html(tableList.length);
             setHeight();
           	 //时间选择
             $(".unitsDate").datetimepicker({
                language:  'zh-CN',  
            	minView:0,
                format: 'yyyy-mm-dd hh:ii:ss',
            	autoclose : true,//选中之后自动隐藏日期选择框   
            	pickerPosition: "bottom-right"
             });
        },
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        //服务端处理分页
        columns: [
 		/* {field: ' ',checkbox: true},  */
        {
        	title: 'id',
            field: 'id',
            visible: false,
        },
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
            title: '患者姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '病历号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return value;
                }
            }
        },
        {
            title: '电话号码',
            field: 'phonenumber',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '当前环节',
            field: 'nodename',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '路径类型',
            field: 'type',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	if(value == "SingleOrMulti") {
            		return "单颗多颗";
            	}else if(value == "Locator"){
            		return "Locator";
            	}else if(value == "Allonx"){
            		return "All-on-x";
            	}
            }
        },
        {
            title: '手术类型',
            field: 'flowname',
            align: 'center',
            sortable: true,
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
        },
        /* {
            title: '手术时间',
            field: 'sstime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return '<input type="text" style="width:60%; text-align:center;" readonly="readonly" class="unitsDate" autocomplete="off" onchange="changeSsTime(\''+row.id+'\',this)" value="'+value+'"/>';
            }
        }, */
        {
            title: '订单编号',
            field: 'order_number',
            align: 'center',
            sortable: true,
            visible: false,
        },
        {
            title: '助理',
            field: 'counsellor',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return value;
            }
        },
        {
            title: '种植医师',
            field: 'plant_physician',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return value;
            }
        },
        {
            title: '修复医师',
            field: 'repair_physician',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return value;
            }
        },
        {
            title: '诊室护士',
            field: 'clinic_nurse',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return value;
            }
        },
        {
            title: '状态',
            field: 'flowstatus',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	if (value == "1") {
                    return '<span style="color:red;">进行中</span>';
                }else if (value == "2") {
                    return '<span style="color:green;">已完成</span>';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        selectOrderNumber = $('#table').bootstrapTable('getData')[index].orderNumber;
        id = $('#table').bootstrapTable('getData')[index].id;
        seqId = $('#table').bootstrapTable('getData')[index].seqid;
        consultSelectlclj=row;//选中患者对象
        lcljId=consultSelectlclj.seqid;
        //console.log(id);
    }).on('dbl-click-row.bs.table', function (e, row, ele,field) {
    		consultSelectPatient=row;//选中患者对象
    		//判断是否已经创建临床路径
    		if(!row.flowname){
    			//根据患者编号查询是否存在临床路径
    			var url = contextPath + '/KQDS_REGAct/selectExistByUsercode.act';
				$.ajax({
			         type: "post",
			         url: url,
			         data: {usercode : row.usercode},
			         dataType: "json",
			         success: function(data){
// 			           	if(data.status=="未挂号"){
// 						    layer.alert("患者未挂号，请挂号后操作！");
// 						    return;
// 						}else{
							layer.open({
				    			type: 2,
				    			title: '创建手术信息',
				    			shadeClose: false,
				    			shade: 0.6,
				    			area: ['95%', '70%'],
				    			content: contextPath+'/HUDH_FlowViewAct/toConsultOperationPrepare.act?id=' + seqId
				    		});
// 						}
			         }
			    });
    		}else{
    			layer.alert("已经创建了临床路径，可直接查看详情！");
    		}
    		
    		$(".layui-layer-close").removeAttr("href");//禁止×拖拽，拖拽有文字显示，可以拖到输入框里
    		
     });
    
    //查询当前登录人员是否是临床路径管理员
    var url = contextPath + '/HUDH_FlowAct/findLcljAdmin.act';
    $.axseSubmit(url, null ,function() {},function(data) { 
    	//console.log(JSON.stringify(data));
    	var agencyUser_seq_id = data.agencyUser_seq_id;
    	var agencyUserId = data.agencyUserId;
    	var agencyUserName = "";
    	if(data.agencyUserName) {
    		agencyUserName = data.agencyUserName;
    	}
    	if(data.lcljAmdin == "YES") {
    		$('.btnBar').append(
    				'<div class="text-left" style="float: right;margin-right: 20px;">'+
    				'<span class="commonText ">代办人：</span>'+
    				'<input type="hidden" name="agencyUser_seq_id" id="agencyUser_seq_id" value="'+agencyUser_seq_id+'"> '+
    				'<input type="hidden" name="agencyUser" id="agencyUser" value="'+agencyUserId+'"> '+
		    		'<input class="whiteInp" type="text" id="agencyUserDesc" value="'+agencyUserName+'" name="agencyUserDesc" placeholder="代办人" readonly="" onclick="buttonFun.selectAgencyUser();">'+
		    		'<a href="javascript:void(0);" style="margin-left:10px;"  class="kqdsCommonBtn" onclick="buttonFun.cleanAgencyUser();">清空</a></div>');
    	}
 		},function(r){
	 	layer.alert('获取信息错误，请重新打开或联系管理员');
 	});
  	//查询节点名称
    var url = contextPath + '/HUDH_FlowAct/findNodeName.act';
    $.axseSubmit(url, null ,function() {},function(data) { 
    	if(data.length>0){
    		for (var i = 0; i < data.length; i++) {
				if(data[i].nodeName!="复查"&&data[i].nodeName!=""){
		    		$("#nodename").append("<option value="+data[i].nodeName+">"+data[i].nodeName+"</option>");
				}
			}
    	}
 	});
    
    //代办人员改变事件更新后台对应参数
    $('#agencyUserDesc').on('change',function (){
    	var params = {seq_id :$('#agencyUser_seq_id').val(), //参数表对应数据seq_id
    			paraValue : $('#agencyUser').val()}; //前端选择的人员id
    	updateAgency(params);
    });
    
    //初始化咨询师下拉框
    initSysUserByDeptId($("#counsellor"),"consultation","loginName");
    
    //初始化种植医生下拉框
    initSysUserByDeptId($("#plant_physician"),"plantDoctor","loginName");
    //初始化修复医生下拉框
    initSysUserByDeptId($("#repair_physician"),"repairdoctor","loginName");
    
    //计算主体的宽度
    $(window).resize(function() {
        setHeight();
    });
    $('.searchSelect').selectpicker("refresh");//咨询部门初始化刷新--2019-10-24 licc
});

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	//pagenum:1,
    	//pageSize: params.pageSize,  //页面大小
    	sortName:this.sortName,//排序名称
        sortOrder:this.sortOrder,//排序类型
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        //pageNumber: params.pageNumber, //页码
    	usercodeorname: $('#usercodeorname').val(),
    	username : $('#username').val(),
    	userId : $('#userId').val(),
    	starttime:$("#starttime").val(),
    	endtime:$("#endtime").val(),
    	phonenumber : $('#phonenumber').val(),
    	ssType : $('#ssType').val(),
    	isBone : $('#isBone').val(),
    	counsellor : $('#counsellor').val(),
    	plant_physician : $('#plant_physician').val(),
    	repair_physician : $('#repair_physician').val(),
    	nodename:$("#nodename").val()
    };
    return temp;
}

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	//pagenum:1,
    	//pageSize: params.pageSize,  //页面大小
    	//limit: params.limit,   //页面大小
        //offset: params.offset, //页码 
        //pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        //pageNumber: params.pageNumber, //页码
    	usercodeorname: $('#usercodeorname').val(),
    	username : $('#username').val(),
    	starttime : $("#starttime").val(),
    	endtime : $("#endtime").val(),
    	phonenumber : $('#phonenumber').val(),
    	ssType : $('#ssType').val(),
    	isBone : $('#isBone').val(),
    	counsellor : $('#counsellor').val(),
    	plant_physician : $('#plant_physician').val(),
    	repair_physician : $('#repair_physician').val(),
    	nodename:$("#nodename").val()
    };
    return temp;
}


function updateAgency(params){
	$.axseSubmit(contextPath + '/HUDH_FlowAct/updateAgencyUser.act', params ,function() {},function(data) { 
 		return;	
	},function(r){
	 	layer.alert('获取信息错误，请重新打开或联系管理员');
 	});
}

var row = $.map($('#table').bootstrapTable('getSelections'),function (row) {
	return row;
});

var buttonFun = {
		oprater : function (thi){
			layer.open({
				type: 2,
				title: '创建手术信息',
				shadeClose: false,
				shade: 0.6,
				area: ['95%', '70%'],
				content: contextPath+'/ClinicPathControllerAct/toLcljOpreation.act?id=' + id
				/* content :contextPath + "/KQDS_REGAct/toAddReg.act?orderno=11" */
			});
		},
		detail : function (thi){
			//console.log("路径创建与详情查看！！");
			if(!selectOrderNumber) {
				layer.alert('请先选择数据');
				return;
			}
			var url = contextPath + '/HUDH_FlowAct/findLcljOrderTrsackById.act';
			var param = { id : seqId };
		    $.axse(url, param,
		    function(r) {
		    	nodesinfor = r.nodes;
		    	//console.log(nodesinfor);
		    },
		    function() {
		        layer.alert('查询失败', {
		              
		        });
		    });
			if (nodesinfor == "") {
				var url = contextPath + '/KQDS_REGAct/selectExistByUsercode.act';
				$.ajax({
			         type: "post",
			         url: url,
			         data: {usercode : onclickrowOobj.usercode},
			         dataType: "json",
			         success: function(data){
// 			           	if(data.status=="未挂号"){
// 						    layer.alert("患者未挂号，请挂号后操作！");
// 						    return;
// 						}else{
							layer.open({
								type: 2,
								title: '创建路径信息',
								shadeClose: false,
								shade: 0.6,
								area: ['80%', '70%'],
								content: contextPath +'/static/css/hudh/lclj/flowdetail/hintPopup.html?id='+seqId
							});
// 						}
			         }
			    });
			} else {
				layer.open({
					type: 2,
					title: '手术详情',
					shadeClose: false,
					shade: 0.6,
					area: ['99%', '98%'],
					content: contextPath +'/static/css/hudh/lclj/flowdetail/index.jsp?orderNumber='+selectOrderNumber + '&id=' + seqId+ '&lcljId=' + lcljId,
					cancel:function(index, layero){
						var childrenDom =layer.getChildFrame('.layui-layer-content', index);						 
						 if (childrenDom.val() == '') {
							 layer.alert("请先关闭当前页面!");							 
							 return false;
						 }else{
							
						 }
					}
				});
			} 
		},
		electiveOperation : function (thi) {
			/* if(!onclickrowOobj.usercode) {
				layer.alert('请先选择患者');
				return;
			} */
			layer.open({
		         type: 2,
		         title: '预约',
		         shadeClose: false,
		         shade: 0.6,
		         area: ['95%', '95%'],
		         content: contextPath+'/KQDS_Net_OrderAct/toYyzx2.act?menuId=601301'
		    });
		},
		visit : function (thi) {
			if(!onclickrowOobj.usercode) {
				layer.alert('请先选择患者');
				return;
			}
			//alert('添加回访'); 
		    layer.open({
		         type: 2,
		         title: '添加回访',
		         shadeClose: false,
		         shade: 0.6,
		         area: ['550px', '480px'],
		         content: contextPath+'/KQDS_VisitAct/toVisitAdd.act?lytype=usermanager&type=khgl&usercode=' + onclickrowOobj.usercode 
		    });
		},
		prescribe : function (thi){
			if(!onclickrowOobj.usercode) {
				layer.alert('请先选择患者');
				return;
			}
			layer.open({
				type: 2,
				title: '术前取模定咬合',
				shadeClose: false,
				shade: 0.6,
				area: ['85%', '50%'],
				content: contextPath+'/ClinicPathControllerAct/toSaveOperativeTreatmentQmdyh.act?orderNumber='+selectOrderNumber
			});
		},
		selectAgencyUser : function() {
			var depts=new Array("adea811e-a70c-46b0-a950-2f5727bd2fe8");
			single_select_user(['agencyUser', 'agencyUserDesc'],'single');
			//deptid_select_user(['agencyUser', 'agencyUserDesc'],depts);
		},
		cleanAgencyUser : function (){
			$('#agencyUser').val("");
			$('#agencyUserDesc').val("");
			var params = {seq_id :$('#agencyUser_seq_id').val(), //参数表对应数据seq_id
	    			paraValue : ""}; //前端选择的人员id
	    	updateAgency(params);
		},
		deleteLclj : function (){//删除临床路径 
			if(!onclickrowOobj) {
				layer.alert("请选择数据");return;
			}
			layer.confirm("确认删除患者"+onclickrowOobj.username+"(编号："+onclickrowOobj.order_number+")的临床路径？", {
		        btn: ['确定', '取消'] //按钮
		    },
		    function() {
		        var url = contextPath + '/HUDH_FlowAct/updateLcljOrderTrackIsobsolete.act?order_number=' + onclickrowOobj.order_number + "&lcljId=" + onclickrowOobj.seqid;
		    	$.axseSubmit(url, {id:seqId}, function() {}, function(r) {
		    		layer.alert(r.retMsrg, {
		                  end: function() {
		                	  window.location.reload(); //刷新父页面
		                      var frameindex = parent.layer.getFrameIndex(window.name);
		                      layer.close(frameindex); //再执行关闭
		                  }
		            }); 
		    	}, function(r) {
		    		var frameindex = parent.layer.getFrameIndex(window.name);
                    layer.close(frameindex); //再执行关闭
		    	});
		    },
		    function() {
		        layer.closeAll('dialog');
		    });
		},
		editLclj : function (){//修改临床路径 
			if(!onclickrowOobj) {
				layer.alert("请选择数据");return;
			}
			layer.open({
				type: 2,
				title: '修改临床路径',
				shadeClose: false,
				shade: 0.6,
				area: ['95%', '60%'],
				content: contextPath+'/ClinicPathControllerAct/toEditLcljOpreation.act?id=' + seqId
			});
		},
		askPrevious:function(){
			if(!onclickrowOobj) {
				layer.alert("请选择数据");return;
			}
			layer.open({
				title:"主诉及既往病史",
				type:2,
				closeBtn:1,
				content:contextPath + "/ZzblViewAct/toZzllAnammesis.act",
				area:['80%','80%'],
				cancel: function(){
					for(var key in onclickrowOobj){
					}
				}
			}); 
		},
		examineDiagnose:function(){
			if(!onclickrowOobj) {
				layer.alert("请选择数据");return;
			}
			layer.open({
				title:"检查及诊断",
				type:2,
				closeBtn:1,
				content:contextPath + "/ZzblViewAct/toZzllExamineDiagnose.act",
				area:['80%','80%'],
				cancel: function(){
					/* console.log("点击了右上角关闭按钮！"); */    
				}
			});
		},
		diagnosisCase:function(){
			if(!onclickrowOobj) {
				layer.alert("请选择数据");return;
			}
			layer.open({
				title:"诊疗方案",
				type:2,
				closeBtn:1,
				content:contextPath + "/ZzblViewAct/toZzllDiagnosisProject.act",
				area:['80%','80%'],
				cancel: function(){
					/* console.log("点击了右上角关闭按钮！"); */     
				}
			}); 
		},
		plantKnowbook:function(){
			if(!onclickrowOobj) {
				layer.alert("请选择数据");return;
			}
			layer.open({
				title:"签署知情同意书",
				type:2,
				closeBtn:1,
				content:contextPath + "/ZzblViewAct/toZzllPlantKnowbook.act",
				area:['80%','80%'],
				cancel: function(){ 
					/* console.log("点击了右上角关闭按钮！"); */     
				}
			});
		},
		repairBill:function(){
			if(!onclickrowOobj) {
				layer.alert("请选择数据");return;
			}
			layer.open({
				title:"修复方案确认单",
				type:2,
				closeBtn:1,
				content:contextPath + "/ZzblViewAct/toZzllRepairProject.act",
				area:['80%','80%'],
				cancel: function(){ 
					/* console.log("点击了右上角关闭按钮！"); */     
				}
			}); 
		},
		scbbLclj : function (){//生成报表
			exportTable();
			//导出
			function exportTable() {
				loadedData = [];
				nowpage = 0;
			    var fieldArr = [];
			    var fieldnameArr = [];
			    $('#table thead tr th').each(function() {
			        var field = $(this).attr("data-field");
			        if (field != "") {
			            fieldArr.push(field); //获取字段
			            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
			        }
			    });
			    var param = JsontoUrldata(queryParamsB());
			    //console.log("param==-=-"+param);
			    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
			}
		}	
}
//##################计算左侧表格高度保证一屏展示###################
function setHeight() {
	var userAgent = navigator.userAgent; //使用设备
	var height_pc=$(window).outerHeight()-$(".searchWrap").outerHeight()-$(".listWrap .listHd").outerHeight()-65;
	var height_ipad=height_pc-65;
	if(userAgent.indexOf("iPad") > -1){//ipad
		if(window.orientation == 90 || window.orientation == -90){//横屏
			 $("#table").bootstrapTable("resetView",{
				   height:height_pc
			   });
		}else if(window.orientation == 0 || window.orientation == 180){//竖屏
			 $("#table").bootstrapTable("resetView",{
				   height:height_ipad
			   });
		}		
	}else{//pc
	   $("#table").bootstrapTable("resetView",{
		   height:height_pc
	   });
	}
}

function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}

/**
 * 清空
 */
$('#clean').on('click',
	function() {
		 $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
		 $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
	     $(".searchSelect li.selected").empty();//清空
	     $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.28--licc
});

//查询
function query() {
    /* var usercodeorname = $("#usercodeorname").val();
    if (usercodeorname == "") {
        layer.alert("请选择查询条件" );
        return false;
    }  */
    refresh();
}
//  审核提醒
function remindlclj(){
	var createuser = createuserid;
	layer.open({
		type: 2,
		title: '审核提醒',
		shadeClose: false,
		shade: 0.6,
		area: ['80%', '70%'],
		content: contextPath + '/HUDH_LcljCheckRecordAct/toRemindAssessor.act?createuser='+createuser
	});
}
//  等待审核提醒
function awaitRemindlclj(){
	var createuser = createuserid;
	layer.open({
		type: 2,
		title: '等待审核提醒',
		shadeClose: false,
		shade: 0.6,
		area: ['80%', '70%'],
		content: contextPath + '/HUDH_LcljCheckRecordAct/toAwaitRemindAssessor.act?createuser='+createuser
	});
}
//审核未通过数量
function vetoNum(){
		var createuser = createuserid;
		var url = contextPath + "/HUDH_LcljCheckRecordAct/getCheckRecordNum.act";
		var params = {
			createuser:createuser,
				status:2
	    };
	   $.axse(url, params,
	         function(data) {
// 		   console.log(JSON.stringify(data)+'-----------data');
				   if(data.retState=="0"){
			   		  $("#notNum").val(data.count);
			   		  if(data.count!=0){
			   			  $(".notRead").css("color","red");
			   		  }
				   }else{
					   layer.alert('查询失败！', {
	 		               });
				   }
	           },
	           function() {
	               layer.alert('查询失败！', {
	               });
	       });
}
// 等待审核数量
function awaitVerifieNum(){
		var createuser = createuserid;
		var url = contextPath + "/HUDH_LcljCheckRecordAct/getAwaitVerifieNum.act";
		var params = {
			createuser:createuser,
				status:2
	    };
	   $.axse(url, params,
	         function(data) {
// 		   console.log(JSON.stringify(data)+'-----------data');
				   if(data.retState=="0"){
			   		  $("#awaitNum").val(data.count);
			   		  if(data.count!=0){
			   			  $(".notRead").css("color","red");
			   		  }
				   }else{
					   layer.alert('查询失败！', {
	 		               });
				   }
	           },
	           function() {
	               layer.alert('查询失败！', {
	               });
	       });
}

function refresh(){
	 $('#table').bootstrapTable('refresh', {
	        'url': pageurl,
	 });
}

//修改手术时间
function changeSsTime(id,thi){
	$.axseSubmit(contextPath + '/HUDH_FlowAct/updateOrderTrackById.act', {id : id,sstime : $(thi).val()} ,function() {},function(data) {
	},function(r){
	 	layer.alert('操作失败，请联系管理员');
 	});
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
		btnList	+= '{"qx":"lclj_tjhf","name":"添加回访"},';
		btnList	+= '{"qx":"lclj_ckxq","name":"路径创建与详情查看"},';
		btnList	+= '{"qx":"lclj_cjlclj","name":"患者信息录入"},';
		btnList	+= '{"qx":"lclj_sc","name":"删除"},';
		btnList	+= '{"qx":"lclj_tjyy","name":"添加预约"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"lclj_sqcmdyh","name":"术前取模定咬合"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"lclj_edit","name":"编辑"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"lclj_cjsj","name":"创建时间"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"lclj_scbb","name":"生成报表"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"lclj_shtx","name":"审核提醒"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"lclj_ddsh","name":"等待审核提醒"}'; // 最后一个不要逗号
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

function reciveUsername(){
	var username = "<%=username%>";
	//console.log(username);
	var userId = '<%=userId%>';
	//console.log(userId);
	if(username!="null"&&username != "" && username != null){
		$("#username").val(username)
	}
	if(userId!="null"&&userId != "" && userId != null){
		$("#userId").val(userId)
	}
	
	//console.log("cc="+userId+username);
	
}
</script>
</html>
