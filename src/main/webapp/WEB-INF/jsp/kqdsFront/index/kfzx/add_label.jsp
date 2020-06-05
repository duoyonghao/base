<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	YZPerson person = SessionUtil.getLoginPerson(request);
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String importMsg = "";  
	if(request.getSession().getAttribute("msg")!=null){  
	   importMsg = request.getSession().getAttribute("msg").toString();
	   //out.println(importMsg);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>添加查询标签</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- 标签查询 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-select.min.css" />

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
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-select.min.js"></script>
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
		width:82px;
		font-size:11px;
		padding:0 3px 0 5px;
		height:24px;
		line-height:24px;
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
</style>
</head>

<body>
<div id="container">
         <div class="formBox">
			<ul class="conditions">
				<li class="unitsDate commonUse">
					<input type="date" placeholder="开始日期" id="jdtime1">
	            </li>	    				
			</ul>
         </div>
         <div class="btnBar" style="border-top:none;" id ="recommendedBarDiv" >
             <a href="javascript:void(0);" class="kqdsCommonBtn clean" id="clear">清空</a>
             <a href="javascript:void(0);" class="kqdsSearchBtn search" onclick="querySC();">查询</a>
         </div>
</div>
</body>
<script type="text/javascript">
function querySC(){
	//console.log("时间："+$("#jdtime1").val());
	var url = contextPath + '/KQDS_LabelAct/setLabel.act';
	var time = $("#jdtime1").val();
    var param = {
    		time :time
    };
    $.axseSubmit(url,param,function() {},function(data) {
    	console.log(data);
    		if(data.retState=="0"){
    			layer.alert(data.retMsrg);
    		}
    },function(data){
    	layer.alert("失败！");
    });
}

</script>
</html>