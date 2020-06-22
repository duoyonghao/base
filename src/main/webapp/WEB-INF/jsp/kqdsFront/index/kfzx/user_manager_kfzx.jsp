<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();

	// 客服中心—客户管理页面右侧的网电信息Url和其他页面不一样，
	// 一个是kqdsFront/jdzx/dialogFrame/netorder.jsp
	// 一个是kqdsFront/jdzx/frame/kfzx/netorder_insertOrUpdate.jsp
	// 为了能统一包含一个公用JSP文件，所以在这里设置个标识，供rightPartInfo.jsp界面判断
	//staticIsUserMgrPage: value为1 网电预约  2 报备预约
	request.setAttribute("staticIsUserMgrPage", "1");
	YZPerson person = SessionUtil.getLoginPerson(request);
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	String isyx = request.getParameter("isyx");//营销中心进入
	if (isyx == null) {
		isyx = "0";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>客服中心—客户管理</title>

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
<!-- 标签查询 -->
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-select.min.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- 标签查询 -->
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-select.min.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
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

/* 标签筛选样式start */
	/* 	搜索框样式修改 */
	.bootstrap-select>.btn { 
		    width: 94px; 
		 	height:26px; 
	}
 	@media screen and (max-width: 1390px) {
		 .bootstrap-select>.btn { 
		    width: 83px; 
		 	height:24px; 
		}	
 	}
 	@media screen and (max-width:1100px){
 		.bootstrap-select>.btn { 
			width: 73px; 
		 	height:22px; 
		}	
 	}
	 .bootstrap-select:not([class*="span"]):not([class*="col-"]):not([class*="form-control"]) {
	    width: 100px;
	}
	.bootstrap-select.btn-group.show-tick .dropdown-menu li a span.text {
    margin-right: 34px;
    position: static;
    color: black;
	}
	
	.bootstrap-select.btn-group.show-tick .dropdown-menu li.selected a i.check-mark {
	    display: inline-block;
	    position: absolute;
	    right: 15px;
	    margin-top: 2.5px;
	}
	
	.label ul{
	  float:left;
	}
	
		input[type=text], .kv .kv-v input[type=text] {
	    padding: 0 10px;
	    width: 100%;
	    height: 28px;
	    line-height: 28px;
	    border: solid 1px #e5e5e5;
	    border-radius: 3px;
	    -webkit-transition: all .3s;
	    transition: all .3s;
	}
	.searchWrap .formBox>section>ul.conditions>li.label .labelUl{
		 height: 32px;
/* 		 overflow:visible; */
/* 		  padding: 3px 3px; */
	}
	.searchWrap .formBox>section>ul.conditions>li.label .labelUl .labelLi{
	    padding: 3px 0;
	}

	.searchWrap .formBox>section>ul.conditions>li.label .labelUl .labelLi>span.title{
			width:55px;
			text-align:right;
			font-size:13px;
			height:30px;
			line-height:30px;
	/* 		border: 1px solid red; */
			color:#313840;
		}
	.searchWrap .formBox>section>ul.conditions>li.label .labelUl .labelLi>span.labeltitle{
		width:55px;
		text-align:right;
		font-size:11px;
		height:24px;
		line-height:24px;
/* 		border: 1px solid red; */
		color:#313840;
	}
	
	.searchWrap .formBox>section>ul.conditions>li.label .labelUl .labelLi>input{
		width:94px;
		text-align:center;
		font-size:11px;
		height:26px;
		line-height:26px;
 		border: 1px solid #ccc; 
		color:#313840;
	}
	
	.searchWrap .formBox>section>ul.conditions>li.label .labelUl .labelLi{
    padding-right: 15px;
    float: left
    }

	.searchWrap .formBox>section>ul.conditions>li.label .labelUl .labelLi select{
		width:94px;
		color: black;
		font-size:11px;
		padding:0 3px 0 5px;
		height:26px;
		line-height:26px;
		color:#333;
		font-weight: normal;
	}
	
	.searchWrap .formBox>section>ul.conditions>li.label .labelUl .labelLi .labelDetails{
		display: inline-block;
	}
	

	@media screen and (max-width:1390px){
		.searchWrap .formBox>section>ul.conditions>li.label>ul li>span.labeltitle{
			width:55px;
			text-align:right;
			font-size:11px;
			height:24px;
			line-height:24px;
	/* 		border: 1px solid red; */
			color:#313840;
		}
/*  	.bootstrap-select>.btn {  */
/*  	    width: 100%;  */
/*  	}  */
/*  	.bootstrap-select:not([class*="span"]):not([class*="col-"]):not([class*="form-control"]) {  */
/*      width: 100px;  */
/*  	}  */
	.searchWrap .formBox>section>ul.conditions>li.label .labelUl .labelLi{
    padding-right: 15px;
    float: left
    }

	.searchWrap .formBox>section>ul.conditions>li.label .labelUl select{
		width:82px;
		font-size:11px;
		padding:0 3px 0 5px;
		height:24px;
		line-height:24px;
	}
 	input[type=text], .kv .kv-v input[type=text] {	    
 	    width: 100%;	   
 		} 
	}

	/*标签筛选 end */

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

	.centerWrap .columnWrap .columnBd ul li.current {
    color: #00a6c0;
    border-bottom: 0px solid #00a6c0;
	}
/* 	解決标签查询中下拉框悬浮 */
	.searchWrap{
		 overflow: visible;
	}
	.formBox{
		overflow: visible;
	}
	.searchWrap .formBox>section {
		 height: 100px;
	}
	.searchWrap .formBox>section>ul.conditions {
	    overflow: visible;
	    height: 100%;
	    position: relative;
	}
	.label{
		position: absolute;
	    top: 0px;
	    left: 0px;
	}
	
	.searchWrap .text {
		    position: static !important; 
		    left: 0px;
		    top: 9px;
		    color: rgb(31, 32, 33);
		}
	.bootstrap-select.btn-group .btn .filter-option {
    position: absolute;
	}
	
	.searchWrap .formBox>section>ul.conditions>li.toggleTr{
		height: 30px;
	}
	
	/* 搜索框select */
	.bootstrap-select > .dropdown-toggle {
     		z-index: 0; 
	}
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
	.bootstrap-select.btn-group .btn .filter-option {
    position: static;
	}
</style>
</head>

<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd"><span class="title">客户接待列表</span></div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height="430"></table>
                    </div>
                    <div id="gongzuol">
		                <div class="columnBd">
		           			<ul class="dataCountUl">
		                		<li>总记录数:<span id="zong">0</span></li>
		                		<li>上门数:<span id="sm">0</span></li>
		                		<li>成交数:<span id="cj">0</span></li>
		                		<li>未上门数:<span id="wsm">0</span></li>
		                		<li>未成交数:<span id="wcj">0</span></li>
		                	</ul>
		                </div>
		            </div>
                </div>
            </div>
             
            <div class="searchWrap">
                 <!-- <div class="cornerBox">查询条件</div> -->
                <div class="searchToggleBtnGroup">
                	<span class="ptcx checked">
                		通用查询
                	</span>
                	<span class="gjcx">
                		高级查询
                	</span>
                	<span class="bqcx">
                		标签筛选
                	</span>
                </div>
                <div class="formBox">
                	<section>
			    		<ul class="conditions">
		    				<li class="commonUse">
			    				<span>建档门诊</span>
		    					<select id="organization" name="organization"></select>
		    				</li>
		    				<li class="unitsDate commonUse">
			    				<span>建档日期</span>
	    						<input type="text" placeholder="开始日期" id="jdtime1" readonly>
	                        </li>
			    			<li class="unitsDate commonUse">
			    				<span>到</span>
	                            <input type="text"  placeholder="结束日期" id="jdtime2" readonly>
		                    </li>
			    			<li class="commonUse">
			    				<span>模糊查询</span>
			    				<input type="text" id="queryInput" class="searchInput" placeholder="姓名/手机号" > 
			    			</li>
			    			<li class="toggleTr">
			    				<span>客服</span>  <!-- 添加客服部条件查询 -->
<!-- 			    				<select class="dict " name="customer" id="customer" data-bv-notempty data-bv-notempty-message="客服" ></select>  -->
			    				<select class="dict searchSelect" name="customer" id="customer" data-bv-notempty data-bv-notempty-message="客服" data-live-search="true" title="请选择"></select> 
			    			</li>
			    			<!-- <li>
			    				<span>客户</span>  添加客户部条件查询
			    				<select class="dict" name="consumer" id="consumer" data-bv-notempty data-bv-notempty-message="客户" ></select> 
			    			</li> -->
				    		
			    			<li class="toggleTr">
			    				<span>咨询项目</span>
		    					<select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" ></select>
			    			</li>
			    			<li class="toggleTr unitsDate">
			    				<span>挂号日期</span>
	    						<input type="text" placeholder="开始日期" id="yytime1" readonly>
		                  	</li>
			    			<li class="toggleTr unitsDate">
			    				<span>到</span>
	                            <input type="text"  placeholder="结束日期" id="yytime2" readonly>
		                    </li>
							<li class="toggleTr">
			    				<span>客户星级</span>
			    				<select id="important">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="1">一级</option>
	                        		<option value="2">二级</option>
	                        		<option value="3">三级</option>
	                        		<option value="4">四级</option>
	                        		<option value="5">五级</option>
	                        	</select>
			    			</li>
<%-- 			    			<% --%>
<!-- 							if(SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) == -1) { -->
<!-- 							%> -->
							
<%-- 							<%}else{ %> --%>
			    			<li class="toggleTr" id="tool">
			    				<span>患者来源</span>
<!-- 			    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');"> -->
<!-- 								</select> -->
								<select class="patients-source select2 dict searchSelect" resource="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelectSearch('devchannel','nexttype');" data-live-search="true" title="请选择">
								</select>
			    			</li>
			    			<li class="toggleTr" id="toolSon">
			    				<span>子分类</span>
				    				<select class="select2 dict searchSelect" name="nexttype" id="nexttype"  data-live-search="true" title="请选择">
									</select>
<!-- 			    				<select class="select2 dict" name="nexttype" id="nexttype"> -->
<!-- 									<option value="">请选择</option> -->
<!-- 								</select> -->
			    			</li>
<%-- 			    			<%} %>    --%>
			    			<li class="toggleTr">
			    				<span>上门</span>
		                        <select id="doorstatus">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">未上门</option>
	                        		<option value="1">已上门</option>
	                        	</select>
			                </li>
			                <li class="toggleTr">
			    				<span>成交状态</span>
		                         <select id="cjstatus">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">未成交</option>
	                        		<option value="1">已成交</option>
	                        	</select>
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
<!-- 		    					<select class="dict" tig="SLGJ" id="gongju" ></select> -->
		    					<select class="dict searchSelect" tig="SLGJ" id="gongju"  data-live-search="true" title="请选择"></select>
			    			</li>
			    			<%} %>   
			    			<li class="toggleTr">
			    				<span>有无回访</span>
		                        <select id="ywhf">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">无回访</option>
	                        		<option value="1">有回访</option>
	                        	</select>
			                </li>
			    			<li class="toggleTr">
			    				<span>建档人</span>
		    					<input type="hidden" name="yewu" id="yewu" value="" title="建档人" class="form-control"/>
								<input type="text" id="yewuDesc" name="yewuDesc" value="" onClick="javascript:single_select_user(['yewu', 'yewuDesc'],'',1);"  readonly></input>
			    			</li>
			    			<li class="label">

			    				<ul class="labelUl">
				    				<li class="labelLi">
						    			<span class="title" id="needOneId">需求标签</span>  
<!-- 					    				<select class="dict" name="" id=""  data-bv-notempty data-bv-notempty-message="一级标签"></select>  -->
				    				</li>
				    				<li class="labelLi">
						    			<span class="labeltitle">二级标签:</span>  
					    				<div class="form-group labelDetails needTwoIdDetail">
									        <select id="needTwoId" name="needTwo" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="needTwoSelectOnchang();"></select>
									    </div>
				    				</li>
				    				<li class="labelLi">
						    			<span class="labeltitle">三级标签:</span>  
					    				<div class="form-group labelDetails needThreeIdDetail">
									        <select id="needThreeId" name="needThree" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="needThreeSelectOnchang();"></select>
									    </div> 
				    				</li>				    				
			    				</ul>
			    				<ul class="labelUl">
 				    				<li class="labelLi">
						    			<span class="title" id="societyOneId">社会标签</span>  
<!-- 					    				<select class="dict" name="" id="" data-bv-notempty data-bv-notempty-message="客服" ></select>  -->
				    				</li>
				    				<li class="labelLi">
						    			<span class="labeltitle">二级标签:</span>  
					    				<div class="form-group labelDetails societyTwoIdDetail">
									        <select id="societyTwoId" name="societyTwo" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="societyTwoSelectOnchang();"></select>
									    </div>
				    				</li>
				    				<li class="labelLi">
						    			<span class="labeltitle">三级标签:</span>  
					    				<div class="form-group labelDetails societyThreeIdDetail">
									        <select id="societyThreeId" name="societyThree" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="societyThreeSelectOnchang();"></select>
									    </div> 
				    				</li>				    								    				
			    				</ul>
			    				<ul class="labelUl">
				    				<li class="labelLi">
						    			<span class="title" id="expenseOneId">消费标签</span>  
<!-- 					    				<select class="dict" name="" id="" data-bv-notempty data-bv-notempty-message="客服" ></select>  -->
				    				</li>
				    				<li  class="labelLi">
						    			<span class="labeltitle">二级标签:</span>  
					    				<div class="form-group labelDetails expenseTwoIdDetail">
									        <select id="expenseTwoId" name="expenseTwo" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="expenseTwoSelectOnchang();"></select>
									    </div>
				    				</li>
				    				<li  class="labelLi">
						    			<span class="labeltitle">三级标签:</span>  
					    				<div class="form-group labelDetails expenseThreeIdDetail">
									        <select id="expenseThreeId" name="expenseThree" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="expenseThreeSelectOnchang();"></select>
									    </div> 
				    				</li>					    				
			    				</ul>
			    				<ul class="labelUl">
				    				<li class="labelLi">
						    			<span class="title" id="starsLevelOneId">星级标签</span>  
<!-- 					    				<select class="dict" name="" id="" data-bv-notempty data-bv-notempty-message="客服" ></select>  -->
				    				</li>
				    				<li  class="labelLi">
						    			<span class="labeltitle">二级标签:</span>  
					    				<div class="form-group labelDetails starsLevelTwoIdDetail">
									        <select id="starsLevelTwoId" name="starsLevelTwoId" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="starsLevelTwoSelectOnchang();"></select>
									    </div>
				    				</li>
				    				<li  class="labelLi">
						    			<span class="labeltitle">三级标签:</span>  
					    				<div class="form-group labelDetails starsLevelThreeIdDetail">
									        <select id="starsLevelThreeId" name="starsLevelThree" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="starsLevelThreeSelectOnchang();"></select>
									    </div> 
				    				</li>					    				
			    				</ul>
			    				<ul class="labelUl">
				    				<li class="labelLi">
						    			<span class="title" id="unsatisfiedOneId">不满意标签</span>  
<!-- 					    				<select class="dict" name="" id="" data-bv-notempty data-bv-notempty-message="客服" ></select>  -->
				    				</li>
				    				<li  class="labelLi">
						    			<span class="labeltitle">二级标签:</span>  
					    				<div class="form-group labelDetails unsatisfiedTwoIdDetail">
									        <select id="unsatisfiedTwoId" name="unsatisfiedTwoId" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" onchange="unsatisfiedTwoSelectOnchang();"></select>
									    </div>
				    				</li>
<!-- 				    				<li  class="labelLi"> -->
<!-- 						    			<span class="labeltitle">三级标签:</span>   -->
<!-- 					    				<div class="form-group labelDetails unsatisfiedThreeIdDetail"> -->
<!-- 									        <select id="unsatisfiedThreeId" name="unsatisfiedThree" tig="" class="dict selectpicker" multiple data-live-search="true" title="请选择" ></select> -->
<!-- 									    </div>  -->
<!-- 				    				</li>					    				 -->
			    				</ul>
			    				<ul class="labelUl">
				    				<li class="labelLi">
					    				<span class="labeltitle">建档日期</span>
			    						<input type="text" placeholder="开始日期" id="jdtimelabel1" readonly>
			                        </li>
					    			<li class="labelLi">
					    				<span class="labeltitle">到</span>
			                            <input type="text"  placeholder="结束日期" id="jdtimelabel2" readonly>
				                    </li>		
			    				</ul>
			    				<ul class="labelUl">
				    				<li class="labelLi">
					    				<span class="labeltitle">咨询</span>
					    				<div class="form-group labelDetails askpersonSearch">
				    						<select   name="askpersonSearch" id="askpersonSearch" class="selectpicker" data-live-search="true" title="请选择" ></select>	
				    					</div> 
					    			</li>	
				    			</ul>		    					    				
 		    				</li> 		    				
		    			</ul>
		    		</section>
                    <!-- <table class="kqds_table">
			    		<tr>
			    			<td style="text-align:right;">建档门诊：</td>
			    			<td style="text-align:left;">
			    				<select id="organization" name="organization"></select>
			    			</td>
			    			<td style="text-align:right;">建档日期：</td>
			    			<td style="text-align:left;"> 
		    					<span class="unitsDate">
		    						<input type="text" placeholder="开始日期" id="jdtime1" readonly>
		                        </span>
			                </td>
			    			
			    			<td style="text-align:right;">到：</td>
			    			<td style="text-align:left;">
								<span class="unitsDate">
		                             <input type="text"  placeholder="结束日期" id="jdtime2" readonly>
		                        </span>
							</td>
			    			<td style="text-align:right;">模糊查询：</td>
			    			<td style="text-align:left;" colspan="3">
			    				<input type="text" id="queryInput" class="searchInput" placeholder="姓名/手机号" > 
			    			</td>
			    			
			    		</tr>
				    		
			    		<tr class="toggleTr">
			    			<td style="text-align:right;">咨询项目：</td>
			    			<td style="text-align:left;">
		    					 <select class="dict" tig="ZXXM" name="xiangmu" id="xiangmu" ></select>
			    			</td>
			    			<td style="text-align:right;">挂号日期：</td>
			    			<td style="text-align:left;"> 
		    					<span class="unitsDate">
		    						<input type="text" placeholder="开始日期" id="yytime1" readonly>
		                        </span>
			                </td>
			    			<td style="text-align:right;">到：</td>
			    			<td style="text-align:left;">
								<span class="unitsDate">
		                             <input type="text"  placeholder="结束日期" id="yytime2" readonly>
		                        </span>
							</td>
							<td style="text-align:right;">客户星级：</td>
			    			<td style="text-align:left;">
			    				<select id="important">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="1">一级</option>
	                        		<option value="2">二级</option>
	                        		<option value="3">三级</option>
	                        		<option value="4">四级</option>
	                        		<option value="5">五级</option>
	                        	</select>
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
			    			<td style="text-align:right;">上门：</td>
			    			<td style="text-align:left;"> 
		                        <select id="doorstatus">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">未上门</option>
	                        		<option value="1">已上门</option>
	                        	</select>
			                </td>
			                <td style="text-align:right;">成交状态：</td>
			    			<td style="text-align:left;"> 
		                         <select id="cjstatus">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">未成交</option>
	                        		<option value="1">已成交</option>
	                        	</select>
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
			    			<td style="text-align:right;">有无回访：</td>
			    			<td style="text-align:left;"> 
		                         <select id="ywhf">
	                        		<option value="">- 请选择 -</option>
	                        		<option value="0">无回访</option>
	                        		<option value="1">有回访</option>
	                        	</select>
			                </td>
			    			<td style="text-align:right;">建档人：</td>
			    			<td style="text-align:left;">
		    					 <input type="hidden" name="yewu" id="yewu" value="" title="建档人" class="form-control"/>
								 <input type="text" id="yewuDesc" name="yewuDesc" value="" onClick="javascript:single_select_user(['yewu', 'yewuDesc'],'',1);"  readonly></input>
			    			</td>
			    		</tr>
			    	</table> -->
                </div>
                <div class="btnBar" style="border-top:none;" id ="recommendedBarDiv" >
                 	<a href="javascript:void(0);" class="kqdsCommonBtn hide" id="kf_hzjd" onclick="hzjd()">新建档案</a> 
                 	<a href="javascript:void(0);" class="kqdsCommonBtn hide" id="kf_xgda" onclick="hzjdedit()">修改档案</a> 
	                <a href="javascript:void(0);" class="kqdsCommonBtn hide" id="kf_tjtg" onclick="goAddRenwu()">添加推广</a>
	                <a href="javascript:void(0);" class="kqdsCommonBtn hide" id="kf_tjhf" onclick="goAddVisit()">添加回访</a>
	                <a href="javascript:void(0);" class="kqdsCommonBtn hide" id="xgglr" onclick="goeditGlr()">修改关联人</a>
					<a href="javascript:void(0);" class="kqdsCommonBtn hide" id="scbb" onclick="exportTable();">生成报表</a>
					<a href="javascript:void(0);" class="kqdsCommonBtn hide" id="zhiding_kefu" onclick="setKeFu();">指定客服</a>
					<a href="javascript:void(0);" class="kqdsCommonBtn hide" id="add_label" onclick="addLabel();">存入标签</a>
                    <a href="javascript:void(0);" class="kqdsCommonBtn clean" id="clear">清空</a>
                    <a href="javascript:void(0);" class="kqdsSearchBtn search" onclick="querySC();">查询</a>
                </div>
                
            </div>
        </div>
       
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var deptId = '<%=person.getDeptId()%>'; 
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/userManger4Kfzx.act';
var listbutton;
var onclickrowOobj = "";
var nowday;
var selectedrows = "";
var personrole = "";
var $table = $("#table");
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
var isyx = "<%=isyx%>";
var loc = new Location();

// 登录权限licc--2020-1-8
var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(","); //允许权限
var allowedBtn=["afa8bf41-af02-4504-99b9-b8b69de40aca","34379b48-ac6f-4b61-9a99-1b427183ef25","eea5c430-1d44-40bb-ba16-0ffad8b09697","190e9256-2aeb-453a-9e8a-e35c0c7a48dc","794d8599-9b96-4461-b2c1-05b5c373d486","5d80d52d-9ca4-4389-be27-ae4a50d7e8c6","46721f65-18ec-463f-b18c-e165fa589fbe","388fcdf9-4969-46c6-b767-a7c3b7905f2f","005e6673-15ab-46d7-b0f4-bb1ed99556a4","713a8067-6890-4157-b7ee-b7d981401c8a","6ff236ec-da8f-40d8-ba0b-e9105bd89f5d","c17de419-b6d4-4eea-b2f6-4f7422b94914","2589c8f7-f4ec-4247-8866-067550cc5ddb","4b32630c-13ac-444e-8dcc-0f3844a2d2bb","b586afb5-6b6e-442a-9387-246c5dfa2db9","a0c50df9-3ba1-47ac-a739-6b2d17e3bc91","de063632-8dc5-45f4-a616-b27ada252406","5bcd3e00-a179-4265-8a9d-7fc966541016","3ee5815c-f2b1-40c7-844a-489d0ff3f256","fc47af55-26cc-4617-863d-6ac5be7b4d34","0dfa1271-72b7-4593-b5d7-328b2d9567d5","1f7fd635-241d-4969-93ae-4ffeb3b5b78f","45a11365-4add-4a2e-81c4-ad12349a6592","0d82d20e-404a-43d0-a977-99c208b1690a"]
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
$(function() {
	
	$(window).on('load', function () {
	    //初始化搜索下拉框
	   $('.selectpicker').selectpicker({});
	});
	//咨询 下拉列表
	initPersonSelectByDeptType("askpersonSearch","<%=ConstUtil.DEPT_TYPE_0 %>");
    //进入页面默认查询今日网电预约列表
    nowday = getNowFormatDate();
    //绑定两个时间选择框的chage时间
    $("#jdtimelabel1,#jdtimelabel2").change(function() {
        timeCompartAndFz("jdtimelabel1", "jdtimelabel2");
    });
    
    $("#jdtime1,#jdtime2").change(function() {
        timeCompartAndFz("jdtime1", "jdtime2");
    });
    
    $("#yytime1,#yytime2").change(function() {
        timeCompartAndFz("yytime1", "yytime2");
    });
    
    //initLabel();
    /** ########################################################### 连锁相关  **/
	//initHosSelectList4Front('organization');// 连锁门诊下拉框
	initHosSelectListNoCheck('organization','<%=ChainUtil.getCurrentOrganization(request)%>'); // 加载门诊列表
    
	initDictSelectByClassIsBtnshow(existornotBtn,'triggerChange');//患者来源根据部门
    initDictSelectByClass('triggerChange'); // 咨询项目、受理类型、受理工具
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
    
	togglemodel.initial('kfzx',pageurl);/*wl 初始化 开关模块 */
	
	//初始化客服下拉框
    initSysUserByDeptId($("#customer"),"customer","loginName");
	//初始化客户下拉框
    initSysUserByDeptId($("#consumer"),"consumer","loginName");
	//初始化标签

    //3、加载表格
    initTable(1);
	
	$('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
    
    ///KQDS_LabelAct/findLabelCondition.act
    //清空
    $('#clear').on('click',
    function() {
        $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
        $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change"); 
        $(".selectpicker li.selected").empty();//清空
        $('.selectpicker').selectpicker("refresh");//初始化刷新--2019.10.28--licc
        $(".searchSelect li.selected").empty();//清空
        $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.28--licc
        
    });

    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    
    $(".labelUl input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });

    //获取当前页面所有按钮##现在没用到此方法 ## yangsen 17-5-2
    getButtonAllCurPage("<%=menuid%>");

    //计算主体的宽度
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
    initLabel();
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

function initLabel() {		
			var parentId = null;
            var param = { parentId : "1" };
            var url = contextPath + "/KQDS_LabelAct/findParentId.act";
            $.axse(url, param,
            function(data) {
            //	console.log(JSON.stringify(data)+'----');
                var needList = data.list0.list;
                var expenseList = data.list1.list;
                var societyList = data.list2.list;
                var starsLevelList = data.list3.list;
                var unsatisfiedList = data.list4.list;
                if (needList != null && needList.length > 0) {
                    for (var j = 0; j < needList.length; j++) {                   
                        var optionStr = needList[j];                   
                        $("#needTwoId").append("<option value='" + optionStr.seqId + "'>" + optionStr.levelabel + "</option>");
                    }
                }
                
                if (expenseList != null && expenseList.length > 0) {
                 for (var j = 0; j < expenseList.length; j++) {                                    
	                     var optionStr = expenseList[j];              
	                     $("#expenseTwoId").append("<option value='" + optionStr.seqId + "'>" + optionStr.levelabel + "</option>");
                 	}
            	 }
                
                if (societyList != null && societyList.length > 0) {
                    for (var j = 0; j < societyList.length; j++) {                                    
                        var optionStr = societyList[j];              
                        $("#societyTwoId").append("<option value='" + optionStr.seqId + "'>" + optionStr.levelabel + "</option>");
                    }
                }
                
                if (starsLevelList != null && starsLevelList.length > 0) {
                    for (var j = 0; j < starsLevelList.length; j++) {                                    
                        var optionStr = starsLevelList[j];              
                        $("#starsLevelTwoId").append("<option value='" + optionStr.seqId + "'>" + optionStr.levelabel + "</option>");
                    }
                }
                
                if (unsatisfiedList != null && unsatisfiedList.length > 0) {
                    for (var j = 0; j < unsatisfiedList.length; j++) {                                    
                        var optionStr = unsatisfiedList[j];              
                        $("#unsatisfiedTwoId").append("<option value='" + optionStr.seqId + "'>" + optionStr.levelabel + "</option>");
                    }
                }
                
            },
            function() {
                layer.alert('查询出错！');
            });
            $("#needTwoId").change(function(){
            	var code = $("#needTwoId").val();
	            var detailurl = contextPath + "/KQDS_LabelAct/findLabelCondition.act?code=" + code;
            	$.ajax({        	         
        	          url:detailurl,      	       
        	          success:function (res) {	
        	        	 	 var needThird=res.jsonList;   
        	        		 var str="";
        		             for (var i = 0; i < needThird.length; i++) {
        		            	 var optionStr = needThird[i];         		            	
        		                 str+="<option value='" + optionStr.seqId + "'>" + optionStr.leveLabel + "</option>";
	          		           
        		            } 
        		            
        		            $("#needThreeId").html(str);
        		            $('#needThreeId').selectpicker('refresh');        		                    	      
         	          }
        	    });
                        	
            })
                        
            
             $("#societyTwoId").change(function(){
            	var code = $("#societyTwoId").val();
	            var detailurl = contextPath + "/KQDS_LabelAct/findLabelCondition.act?code=" + code;
            	$.ajax({        	         
        	          url:detailurl,      	       
        	          success:function (res) {        	        	    	        	        	        	          	        	  
          	        	 	 var societyThird=res.jsonList;  
          	        		 var str="";
          		             for (var i = 0; i < societyThird.length; i++) {
          		            	 var optionStr = societyThird[i]; 
          		                 str+="<option value='" + optionStr.seqId + "'>" + optionStr.leveLabel + "</option>";
  	          		           
          		            } 
          		            $("#societyThreeId").html(str);
          		            $('#societyThreeId').selectpicker('refresh');             	        	  
         	          }
        	    });            			     
            })
            
             $("#expenseTwoId").change(function(){
            	var code = $("#expenseTwoId").val();
	            var detailurl = contextPath + "/KQDS_LabelAct/findLabelCondition.act?code=" + code;
            	$.ajax({        	         
        	          url:detailurl,      	       
        	          success:function (res) {	        	        	        	          	        	  
     	        	 	 var expenseThird=res.jsonList;  
     	        		 var str="";
     		             for (var i = 0; i < expenseThird.length; i++) {
     		            	 var optionStr = expenseThird[i]; 
     		                 str+="<option value='" + optionStr.seqId + "'>" + optionStr.leveLabel + "</option>";	          		           
     		            } 
     		            $("#expenseThreeId").html(str);
     		            $('#expenseThreeId').selectpicker('refresh');       
         	          }
        	    });            	
            	
            })
            
            $("#starsLevelTwoId").change(function(){
            	var code = $("#starsLevelTwoId").val();
	            var detailurl = contextPath + "/KQDS_LabelAct/findLabelCondition.act?code=" + code;
            	$.ajax({        	         
        	          url:detailurl,      	       
        	          success:function (res) {	    
        	        	// console.log(JSON.stringify(res)+'----');
     	        	 	 var starsLevelThird=res.jsonList;  
     	        		 var str="";
     		             for (var i = 0; i < starsLevelThird.length; i++) {
     		            	 var optionStr = starsLevelThird[i]; 
     		                 str+="<option value='" + optionStr.seqId + "'>" + optionStr.leveLabel + "</option>";	          		           
     		            } 
     		            $("#starsLevelThreeId").html(str);
     		            $('#starsLevelThreeId').selectpicker('refresh');       
         	          }
        	    });            	
            	
            })
            
             $("#unsatisfiedTwoId").change(function(){
            	var code = $("#unsatisfiedTwoId").val();
	            var detailurl = contextPath + "/KQDS_LabelAct/findLabelCondition.act?code=" + code;
            	$.ajax({        	         
        	          url:detailurl,      	       
        	          success:function (res) {	    
        	        	 console.log(JSON.stringify(res)+'----');
     	        	 	 var unsatisfiedThird=res.jsonList;  
     	        		 var str="";
     		             for (var i = 0; i < unsatisfiedThird.length; i++) {
     		            	 var optionStr = unsatisfiedThird[i]; 
     		                 str+="<option value='" + optionStr.seqId + "'>" + optionStr.leveLabel + "</option>";	          		           
     		            } 
     		            $("#unsatisfiedThreeId").html(str);
     		            $('#unsatisfiedThreeId').selectpicker('refresh');       
         	          }
        	    });            	
            	
            })
            
	}	    


function needTwoSelectOnchang() {

	var val = "", staffs = [];            	
    //循环的取出插件选择的元素(通过是否添加了selected类名判断)
    for (var i = 0; i < $(".needTwoIdDetail li.selected").length; i++) {                   
            val=$("#needTwoId option:selected").eq(i).attr("value"); 
        if (val != '') {
            staffs.push(val);
        }
    }
    var need2IdStr=staffs.join();
    //console.log(need2IdStr);
	return need2IdStr;
}

function expenseTwoSelectOnchang() {

    var val = "", staffs = [];
 for (var i = 0; i < $(".expenseTwoIdDetail li.selected").length; i++) {    
 	
        val=$("#expenseTwoId option:selected").eq(i).attr("value"); 
     if (val != '') {
         staffs.push(val);
     }
 }
 var expense2IdStr=staffs.join();
 //console.log(expense2IdStr);
	return expense2IdStr;
}

function societyTwoSelectOnchang() {

	var val = "", staffs = [];            	
 for (var i = 0; i < $(".societyTwoIdDetail li.selected").length; i++) {                   
         val=$("#societyTwoId option:selected").eq(i).attr("value"); 
     if (val != '') {
         staffs.push(val);
     }
 }
 var society2IdStr=staffs.join();
 //console.log(society2IdStr);
	return society2IdStr;
}

function starsLevelTwoSelectOnchang() {

	var val = "", staffs = [];            	
 for (var i = 0; i < $(".starsLevelTwoIdDetail li.selected").length; i++) {                   
         val=$("#starsLevelTwoId option:selected").eq(i).attr("value"); 
     if (val != '') {
         staffs.push(val);
     }
 }
 var starsLevel2IdStr=staffs.join();
 //console.log(society2IdStr);
	return starsLevel2IdStr;
}

function unsatisfiedTwoSelectOnchang() {

	var val = "", staffs = [];            	
 for (var i = 0; i < $(".unsatisfiedTwoIdDetail li.selected").length; i++) {                   
         val=$("#unsatisfiedTwoId option:selected").eq(i).attr("value"); 
     if (val != '') {
         staffs.push(val);
     }
 }
 var unsatisfied2IdStr=staffs.join();
 //console.log(society2IdStr);
	return unsatisfied2IdStr;
}

//以上是二级标签传的值
// 以下是三級標簽傳值
function needThreeSelectOnchang() {
	var val = "", staffs = [];            	
    for (var i = 0; i < $(".needThreeIdDetail li.selected").length; i++) {                   
            val=$("#needThreeId option:selected").eq(i).attr("value"); 
        if (val != '') {
            staffs.push(val);
        }
    }
    var need3IdStr=staffs.join();
    //console.log(need3IdStr);
	return need3IdStr;
}

function societyThreeSelectOnchang() {
    var three = "", threeArr = [];
    //循环的取出插件选择的元素(通过是否添加了selected类名判断)
    for (var j = 0; j < $(".societyThreeIdDetail li.selected").length; j++) {    
    	three=$("#societyThreeId option:selected").eq(j).attr("value"); 
        if (three != '') {
        	threeArr.push(three);
        }
    }
    var society3IdStr=threeArr.join();
    //console.log(society3IdStr);
    return society3IdStr;
}

function expenseThreeSelectOnchang(){
    var three = "", threeArr = [];
    //循环的取出插件选择的元素(通过是否添加了selected类名判断)
    for (var j = 0; j < $(".expenseThreeIdDetail li.selected").length; j++) {    
    	three=$("#expenseThreeId option:selected").eq(j).attr("value"); 
        if (three != '') {
        	threeArr.push(three);
        }
    }
    var expense3IdStr=threeArr.join();
    //console.log(expense3IdStr);
    return expense3IdStr;
}

function starsLevelThreeSelectOnchang(){
    var three = "", threeArr = [];
    //循环的取出插件选择的元素(通过是否添加了selected类名判断)
    for (var j = 0; j < $(".starsLevelThreeIdDetail li.selected").length; j++) {    
    	three=$("#starsLevelThreeId option:selected").eq(j).attr("value"); 
        if (three != '') {
        	threeArr.push(three);
        }
    }
    var starsLevel3IdStr=threeArr.join();
    //console.log(expense3IdStr);
    return starsLevel3IdStr;
}

//导出
function exportTable() {
	var fieldArr=[];
	var fieldnameArr=[];
	$('#table thead tr th').each(function () {
		var field = $(this).attr("data-field");
		if(field!=""){
			fieldArr.push(field);//获取字段
			fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
		}
	});
	var param  = JsontoUrldata(queryParamsB());
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
function refresh(){
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
    	offset: params.offset, //页码 
    	sortName:this.sortName,
    	sortOrder:this.sortOrder,
    	pageIndex : params.offset/params.limit + 1,
    	organization:$("#organization").val(),
        type: 1,
        jdtime1: $('#jdtime1').val(),
        jdtime2: $('#jdtime2').val(),
        shouli: $('#shouli').val(),
        gongju: $('#gongju').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        yytime1: $('#yytime1').val(),
        yytime2: $('#yytime2').val(),
        xiangmu: $('#xiangmu').val(),
        important: $('#important').val(),
        yewu: $('#yewu').val(),
        ywhf: $('#ywhf').val(),
        customer: $('#customer').val(),
        consumer: $('#consumer').val(),
        doorstatus: $('#doorstatus').val(),
        cjstatus: $('#cjstatus').val(),
        queryinput: $('#queryInput').val(),
        needTwo : needTwoSelectOnchang(),
        societyTwo : societyTwoSelectOnchang(),
        expenseTwo : expenseTwoSelectOnchang(),
        starsLevelTwo : starsLevelTwoSelectOnchang(), 
        unsatisfiedTwo : unsatisfiedTwoSelectOnchang(),
        
        needThree : needThreeSelectOnchang(),
        societyThree : societyThreeSelectOnchang(),
        expenseThree : expenseThreeSelectOnchang(),
        starsLevelThree : starsLevelThreeSelectOnchang(),
        jdtimelabel1: $('#jdtimelabel1').val(),
        jdtimelabel2: $('#jdtimelabel2').val(),
        askperson:$('#askpersonSearch').val()
    };
   // console.log(JSON.stringify(temp)+"1111111");
    return temp;  
}
function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	organization:$("#organization").val(),
        type: 1,
        jdtime1: $('#jdtime1').val(),
        jdtime2: $('#jdtime2').val(),
        shouli: $('#shouli').val(),
        gongju: $('#gongju').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        yytime1: $('#yytime1').val(),
        yytime2: $('#yytime2').val(),
        xiangmu: $('#xiangmu').val(),
        important: $('#important').val(),
        yewu: $('#yewu').val(),
        ywhf: $('#ywhf').val(),
        customer: $('#customer').val(),
        consumer: $('#consumer').val(),
        doorstatus: $('#doorstatus').val(),
        cjstatus: $('#cjstatus').val(),
        queryinput: $('#queryInput').val(),
        needTwo: needTwoSelectOnchang(),
        societyTwo:societyTwoSelectOnchang(),
        expenseTwo:expenseTwoSelectOnchang(),
        starsLevelTwo : starsLevelTwoSelectOnchang(), 
        unsatisfiedTwo : unsatisfiedTwoSelectOnchang(),
        needThree : needThreeSelectOnchang(),
        societyThree : societyThreeSelectOnchang(),
        expenseThree : expenseThreeSelectOnchang(),
        starsLevelThree : starsLevelThreeSelectOnchang(),
        jdtimelabel1: $('#jdtimelabel1').val(),
        jdtimelabel2: $('#jdtimelabel2').val(),
        askperson:$('#askpersonSearch').val()
    };
   // console.log(JSON.stringify(temp)+"2222222");
    return temp;  
}
//点击查询
function querySC() {
   /*  var jdtime1 = $("#jdtime1").val();
    var jdtime2 = $("#jdtime2").val();
    var shouli = $("#shouli").val();
    var gongju = $("#gongju").val();
    var yytime1 = $("#yytime1").val();
    var yytime2 = $("#yytime2").val();
    var xiangmu = $("#xiangmu").val();
    var important = $("#important").val();
    var yewu = $("#yewu").val(); //业务
    var doorstatus = $('#doorstatus').val();
    var cjstatus = $('#cjstatus').val();
    var queryInput = $("#queryInput").val();

    if (jdtime1 == "" && jdtime2 == "" && shouli == "" && gongju == "" && yytime1 == "" && yytime2 == "" && xiangmu == ""  && important == "" && yewu == "" && queryInput == "" && doorstatus == "" && cjstatus == "") {
        layer.alert('请选择查询条件' );
        return false;
    } */
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
//加载表格
//客户管理
function initTable(nums) {
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
		sidePagination: "server",//后端分页
        paginationShowPageGo: true,
        onLoadSuccess: function(data) { //加载成功时执行
//         	隐藏患者来源子分类
			var existornot=isExist(total);//资源隐藏判断条件ZY_LYCK
        	if(!existornot){
//         		console.log("------------此用户不可查看患者来源子分类");
        		$('#table').bootstrapTable('hideColumn', 'devchannel');
        		$('#table').bootstrapTable('hideColumn', 'nexttype');
        		$('#table').bootstrapTable('hideColumn', 'accepttool');        		
        	}else{
//         		console.log("------------此用户可查看");
        	}  
        	//console.log(JSON.stringify(data)+"999");
        	var tableList = data.nums[0];
            $("#zong").html(data.total);
            /* var sms = 0,
            cjs = 0,
            wsms = 0,
            wcjs = 0;
            for (var i = 0; i < tableList.length; i++) {
                if (tableList[i].doorstatus == "已上门") {
                    sms = sms + 1;
                } else {
                    wsms = wsms + 1;
                }
                if (tableList[i].cjstatus == "已成交") {
                    cjs = cjs + 1;
                } else {
                    wcjs = wcjs + 1;
                }
            } */
            if(tableList.doorstatus!=''){
            	$("#sm").html(tableList.doorstatus);	
            }else{
            	$("#sm").html(0);
            }
            if(tableList.cjstatus!=''){
	            $("#cj").html(tableList.cjstatus);
            }else{
            	$("#cj").html(0);
            }
            $("#wsm").html(Number(data.total)-Number(tableList.doorstatus));
            $("#wcj").html(Number(data.total)-Number(tableList.cjstatus));
            
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [
        {
            field: '',
            checkbox: true,
            formatter: stateFormatter
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
            title: '建档门诊',
            field: 'organizationname',
            align: 'center',
            
            visible: true,
            switchable: false,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                }else{
                	return "<span></span>";
                }
            }
        },
        {
            title: '上门状态',
            field: 'zdoorstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if (value == "1") {
                    return '<span class="label label-success">已上门</span>';
                } else {
                    return '<span class="label label-danger">未上门</span>';
                }
            }
        },
        {
            title: '本次上门',
            field: 'doorstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "1") {
                    return '<span class="label label-success">已上门</span>';
                } else {
                    return '<span class="label label-danger">未上门</span>';
                }
            }
        },
        {
            title: '本次成交',
            field: 'cjstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "1") {
                    return '<span class="label label-success">已成交</span>';
                } else {
                    return '<span class="label label-danger">未成交</span>';
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        },
        /* {
            title: '标签',
            field: 'labelname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	//console.log(value);
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        }, */
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }else{
                	return '<span></span>';
                }
            }
        },
        {
            title: '手机号2',
            field: 'phonenumber2',
            align: 'center',
            
            visible: false, // 不展示
            switchable: false,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '手机号',
            field: 'phonenumber1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    if (row.phonenumber1 != "" && row.phonenumber1 != null) {
                        return '<span title="' + row.phonenumber1 + '">' + row.phonenumber1 + '</span>';
                    } else if (row.phonenumber2 != "" && row.phonenumber2 != null && (row.phonenumber1 == "" || row.phonenumber1 == null)) {
                        return '<span title="' + row.phonenumber2 + '">' + row.phonenumber2 + '</span>';
                    } else {
                        return '<span></span>';
                    }
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '建档人',
            field: 'createuser',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="name"></span>';
                }
            }
        },
        {
            title: '咨询项目',
            field: 'askitem',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="source"></span>';
                }
            }
        },
        {
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span>' + value + '</span>';
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '预约时间',
            field: 'ordertime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span>' + value + '</span>';
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '到诊时间',
            field: 'guidetime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span>' + value + '</span>';
                }else{
                	return "<span></span>";
                }
            }
        },
        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="source"></span>';
                }
            }
        },
        {
            title: '来源子分类',
            field: 'nexttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span class="source"></span>';
                }
            }
        },
        {
            title: '省',
            field: 'provincename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>'
            }
        
        },
        {
            title: '市',
            field: 'cityname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>'
            }
        },
        {
            title: '区',
            field: 'townname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="name">'+value+'</span>'
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return '<span></span>';
                } else {
                    return '<span>'+value+'</span>';
                }
            }
        },
        {
            title: '星级',
            field: 'important',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>"+value+"</span>";
            }
        },
        {
            title: '商务通身份证',
            field: 'xueli',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return "<span></span>";
                }
            }
        },
        {
            title: '咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '受理类型',
            field: 'accepttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return "<span class='source'></span>";
                }
            }
        },
        {
            title: '受理工具',
            field: 'accepttool',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return "<span class='source'></span>";
                }
            }
        },
        {
            title: '咨询内容',
            field: 'askcontent',
            align: 'center',
            
            /* sortable: true, */
            formatter:function(value,row,index){  
		    	  if(value){
		    		  var showVal = value;
		    		  if(value.length > 6){
		    			  showVal = value.substring(0, 6) + '...';
		    		  }
		        	  html = '<span class="time" title="'+value+'">'+showVal+'</span>';
		              return html;  
		    	  }else{
		    		  return "<span></span>";
		    	  }
	  		}
        },
        {
            title: '患者备注',
            field: 'remark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark">' + value + '</span>';
                }else{
                	return "<span class='remark'></span>";
                }
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
        {
            title: '关联人',
            field: 'glr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '修改原因',
            field: 'glrremark',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark">' + value + '</span>';
                }else{
                	return '<span class="remark"></span>'
                }
            }
        },{
            title: '有无回访',
            field: 'ywhf',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                  	return '<span class="label label-danger">无回访</span>';
                }else{
                	return '<span class="label label-success">有回访</span>';
                }
            }
        }]
    }).on('click-row.bs.table',function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}

//复选框
function stateFormatter(value, row, index) {
    if (row.id === 2) {
        return {
            disabled: true,
            checked: true
        };
    }
    if (row.id === 0) {
        return {
            disabled: true,
            checked: true
        }
    }
    return value;
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//添加任务(推广计划)
function goAddRenwu() {
    selectedrows = getIdSelections();
    //selectedrows[0]
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要加入推广计划的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '添加推广计划',
            shadeClose: false,
            shade: 0.6,
            area: ['50%', '490px'],
            content: contextPath + '/KQDS_ExtensionAct/toExtensionAdd.act'
        });
    }
}

//添加回访
function goAddVisit() {
    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择需要回访的患者' );
    } else {
        layer.open({
            type: 2,
            title: '添加回访',
            shadeClose: false,
            shade: 0.6,
            area: ['550px', '480px'],
            content: contextPath+'/KQDS_VisitAct/toVisitAdd.act?lytype=usermanager&type=khgl&usercode=' + onclickrowOobj.usercode 
        });
    }
}
//修改关联人
function goeditGlr() {
    selectedrows = getIdSelections();
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要修改关联人的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '修改关联人',
            shadeClose: false,
            shade: 0.6,
            area: ['90%', '98%'],
            content: contextPath+'/KQDS_UserDocumentAct/toWdIndex.act'
        });
    }
}
//新建档案
function hzjd() {
    layer.open({
        type: 2,
        title: '患者建档',
        shadeClose: false,
        shade: 0.6,
        area: ['740px', '90%'],
        content: contextPath + '/KqdsUserdocumentMergeRecordAct/toHzjd_NetPlantDept.act'    
    });
}
function hzjdedit() {

    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择需要修改的患者' );
    } else {
    	var content = '';
    	if(onclickrowOobj.type == 1){
    		content = contextPath + '/KQDS_UserDocumentAct/toHzjd_Net_Edit.act?usercode=' + onclickrowOobj.usercode
    	}else{
    		content = contextPath + '/KQDS_UserDocumentAct/toHzjd_Edit.act?usercode=' + onclickrowOobj.usercode
    	}
        //修改资料
        layer.open({
            type: 2,
            title: '修改患者资料',
            shadeClose: false,
            shade: 0.6,
            area: ['740px', '90%'],
            content: content
        });
    }
}

//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

/* function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "xgglr") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="goeditGlr()">修改关联人</a>&nbsp;';
        } else if (listbutton[i].qxName == "scbb") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable();">生成报表</a>&nbsp;';
        } else if (listbutton[i].qxName == "zhiding_kefu") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="setKeFu();">指定客服</a>&nbsp;';
        }else if (listbutton[i].qxName == "add_label") {
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="addLabel();">存入标签</a>&nbsp;';
        }
    }
    $("#hzjd").before(menubutton1);
} */

function setKeFu() {
    selectedrows = getIdSelections();
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要指定客服的患者(可多选)' );
    } else {
        layer.open({
            type: 2,
            title: '指定客服',
            shadeClose: false,
            shade: 0.6,
            area: ['90%', '98%'],
            content: contextPath+'/KQDS_ChangeKeFuAct/toKefuIndex.act'
        });
    }
}
function addLabel(){
	layer.open({
		type: 2,
        title: '存入标签',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['25%', '250px'],
        content: contextPath +'/KQDS_LabelAct/toAddLabel.act'
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
		btnList	+= '{"qx":"kf_hzjd","name":"新建档案"},';
		btnList	+= '{"qx":"kf_xgda","name":"修改档案"},';
		btnList	+= '{"qx":"kf_tjtg","name":"添加推广"},';
		btnList	+= '{"qx":"xgglr","name":"修改关联人"},';
		btnList	+= '{"qx":"zhiding_kefu","name":"指定客服"},';
		btnList	+= '{"qx":"add_label","name":"存入标签"},';
		btnList	+= '{"qx":"scbb","name":"生成报表"},';
		btnList	+= '{"qx":"kf_tjhf","name":"添加回访"}';
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