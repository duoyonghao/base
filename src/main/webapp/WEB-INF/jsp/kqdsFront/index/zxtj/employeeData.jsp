<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>咨询成交率</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highstock.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<style>
	.table-bordered {
		border: 0px solid #ddd;
	}
	/* 咨询业绩汇总 */
	.performance_total{
		overflow: hidden;
	}
	.performance_total .tableBox{
		float: left;
		width: 30%;
		margin-top: 6px;
	}
	.performance_total #container_chart{
		float: right;
		width:70%;
	}
	#container_chart{
		height: 400px;
		max-width:100%;
		margin: 0px auto;
	}
	.highcharts-title tspan{
		font-size: 18px;
	}
	.highcharts-series-0 .highcharts-color-0{
		fill:#00a6c0;
	}
	.highcharts-series-0 .highcharts-point{
		fill:#00a6c0;
	}
	/* 咨询基础数据汇总 */
	#container_basechart{
		height: 400px;
		max-width:100%;
		margin: 40px auto 0px;
	}
	.highcharts-title tspan{
		font-size: 18px;
	}
	.highcharts-credits{
		display: none;
	}
	 #container_basechart .highcharts-series-0 .highcharts-color-0{
	 	fill: rgba(144,237,125,0.6);
	 }
	 #container_basechart .highcharts-series-0 .highcharts-point{
	 	fill: rgba(144,237,125,0.6);
	 }
	 #container_basechart .highcharts-series-1 .highcharts-color-1{
	 	fill: rgba(144,237,125,1);
	 }
	 #container_basechart .highcharts-series-1 .highcharts-point{
	 	fill: rgba(144,237,125,1);
	 }
	 #container_basechart .highcharts-series-2 .highcharts-color-2{
	 	fill: rgba(124,181,236,0.6);
	 }
	 #container_basechart .highcharts-series-2 .highcharts-point{
	 	fill: rgba(124,181,236,0.6);
	 }
	 #container_basechart .highcharts-series-3 .highcharts-color-3{
	 	fill: rgba(124,181,236,1);
	 }
	 #container_basechart .highcharts-series-3 .highcharts-point{
	 	fill: rgba(124,181,236,1);
	 }
	 #container_basechart .highcharts-series-4 .highcharts-color-4{
	 	fill: rgba(67,67,72,0.6);
	 }
	 #container_basechart .highcharts-series-4 .highcharts-point{
	 	fill: rgba(67,67,72,0.6);
	 }
	 #container_basechart .highcharts-series-5 .highcharts-color-5{
	 	fill: rgba(67,67,72,1);
	 }
	 #container_basechart .highcharts-series-5 .highcharts-point{
	 	fill: rgba(67,67,72,1);
	 }
	 .unitsDate input{
		text-align: center;	
	}
	table td span.name {
	    display: block;
	    width: 100%;
	    text-align: center;
	}
	.fixed-table-body {
   		border: 1px solid #ddd;
    	border-radius: 5px;
	}
	
	
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.searchSelect>.btn { 
		    width: 110px; 
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
	.searchWrap , .formBox{
	    overflow: visible; 
	}
 	.searchWrap{ 
 		height: 78px; 
 	} 

</style>
</head>

<body>
<div id="container">
	<!--查询条件-->
	<div class="searchWrap">
		<div class="cornerBox">查询条件</div>
            <div class="formBox">
            	<div class="kv">
	            	<div class="kv">
							<label>所属门诊</label>
							<div class="kv-v">
								<select id="organization" name="organization" onchange="findDept(<%=menuid%>)"></select>
							</div>
					</div>
					<div class="kv">
		            	<div class="kv hide" id='sjbb_bmlb'>
								<label>部门类别</label>
								<div class="kv-v " >
									<select id="deptCategory" name="deptCategory" onchange="findDeptPerson(this.value)"></select>
								</div>
						</div>
					</div>
					<div class="kv hide" style="width:200px;" id='sjbb_zxry'>
				        <label style="width:76px;">咨询人员：</label>
				        <select id="recesort" name="recesort" tig="jzfl" class="dict searchSelect"  data-live-search="true" title=""></select>
<!-- 				        <select class="dict" tig="jzfl" name="recesort" id="recesort"></select>	 -->
			    	</div>
				</div>
               	<div class="kv">
	            	<div class="kv">
							<label>日期查询</label>
							<div class="kv-v " >
								<select id="time" name="time" onchange="switchingTime()">
									<option value='日期'>日</option>
									<option value='月份' selected="selected">月</option>
									<option value='年份'>年</option>
								</select>
							</div>
					</div>
				</div>
               	<div class="kv">
               		<div class="kv">
						<label>查询日期</label>
						<div class="kv-v">
							<!-- 年选项框 -->
							<span class="unitsDate yearInput"  style="display:none;">
								<input type="text" placeholder="开始日期" class="starttime" autocomplete="off"/> <span>到</span>
								<input type="text" placeholder="结束日期" class="endtime" autocomplete="off"/>
							</span>
							<!-- 月选项框 -->
							<span class="unitsDate monthInput">
								<input type="text" placeholder="开始日期" class="starttime" autocomplete="off"/> <span>到</span>
								<input type="text" placeholder="结束日期" class="endtime" autocomplete="off"/>
							</span>
							<!-- 日选项框 -->
							<span class="unitsDate dayInput" style="display:none;">
								<input type="text" placeholder="开始日期" class="starttime" autocomplete="off"/> <span>到</span>
								<input type="text" placeholder="结束日期" class="endtime" autocomplete="off"/>
							</span>
						</div>
					</div>
                </div>
                <div class="kv hide" style="width:180px;" id='askperson_scbb'>
				        <label style="width:50px;">表：</label>
				        <select class="dict" name="askpersonScbb" id="askpersonScbb">
				        	<option value='咨询基础数据汇总表'>咨询基础数据汇总表</option>
				        	<option value='咨询业绩汇总表'>咨询业绩汇总表</option>
				        </select>	
			    </div>
			    <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
			    
            </div>
        </div> 
    <div class="tableHd">咨询基础数据汇总</div>
    <div class="tableBox" id="tableBaseBox" style="overflow: auto;">
    	<div class="tableBox" style="overflow: auto;">
	     	 <table id="tableBase" class="table-striped table-condensed table-bordered" style="width: 100%;border:1px solid #ddd;" data-height="300">
	         </table>
         </div>
         <div id="container_basechart" style="width:100%; overflow:hidden;" ></div>
    </div>
    <div class="tableHd" style="/* border-top: 1px solid #ddd; */margin-top: 30px;">咨询业绩汇总</div>
    <div class="performance_total">
    	<div class="tableBox" >
	     	 <table id="table"  class="table-striped table-condensed table-bordered" style="width: 100%;" data-height="400">
	         </table>
    	</div>
    	<div id="container_chart" style="overflow:hidden;" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/HUDH_DataAnalysisAct/findAllCJStatisticsByMonth.act';
var nowday;
var qxname="";
var func = ['exportTable'];
var qxnameArr = ['askperson_scbb'];
$(function(){
	//initHosSelectList4Front('organization');// 连锁门诊下拉框	
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,"<%=menuid%>");
	var starttime = getNowFormatDate();
	starttime = new Date(starttime);
	// 日时间选择
	$(".dayInput input").datetimepicker({
		language:  'zh-CN',
		minView:2,
	    autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	}); 
	// 日初始化时间框
	nowday = getNowFormatDate();
	$(".dayInput .starttime").val(starttime.Format("yyyy-MM-dd"));
	$(".dayInput .endtime").val(nowday);
	
	// 月时间选择
	$(".monthInput input").datetimepicker({
		language:  'zh-CN',
		startView: 3,
		minView:3,
        autoclose : true,
		format: 'yyyy-mm',
		pickerPosition: "bottom-right"
	}); 
	// 月初始化时间框
	var myDate = new Date(nowday);
    myDate.setMonth(myDate.getMonth()-2);
    $(".monthInput .starttime").val(myDate.Format("yyyy-MM"));
	$(".monthInput .endtime").val(nowday.substring(0,7));
	
	// 年时间选择
	$(".yearInput input").datetimepicker({
		language:  'zh-CN',
		startView: 4,
		minView:4,
        autoclose : true,
		format: 'yyyy',
		pickerPosition: "bottom-right"
	}); 
	// 年初始化时间框
	$(".yearInput .starttime").val(starttime.Format("yyyy"));
	$(".yearInput .endtime").val(nowday.substring(0,4));
  	//获取当前页面所有按钮
    findDept("<%=menuid%>");
    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-30 licc
});
$("#query").click(function(){
    if($("#time").val()=="年份"){
    	//console.log("年份验证");
    	var starttime = $(".yearInput .starttime").val();
    	var endtime = $(".yearInput .endtime").val();
    	var data1 = Date.parse(starttime.replace(/-/g,   "/"));  
        var data2 = Date.parse(endtime.replace(/-/g,   "/"));  
        var datadiff = data2-data1;
        var time = 5*365*24*60*60*1000;
    	if(starttime.length>0 && endtime.length>0){  
    		if(datadiff<0||datadiff>time){
                layer.alert('开始时间应小于结束时间并且间隔小于五年！', {  end:function (){
               	 //修改结束日期为开始日期加31天
               	 var myDate = new Date(starttime);
               	 myDate.setFullYear(myDate.getFullYear() + 4);
               	 $(".yearInput .endtime").val(myDate.Format("yyyy"));
                }});
                return false;
             }
    	}
    }
    else if($("#time").val()=="月份"){
    	//console.log("月份验证");
    	var starttime = $(".monthInput .starttime").val();
    	var endtime = $(".monthInput .endtime").val();
    	var data1 = Date.parse(starttime.replace(/-/g,   "/"));  
        var data2 = Date.parse(endtime.replace(/-/g,   "/"));  
        var datadiff = data2-data1;
        var time = 365*24*60*60*1000; 
    	if(starttime.length>0 && endtime.length>0){  
    		if(datadiff<0||datadiff>time){
                layer.alert('开始时间应小于结束时间并且间隔小于一年！', {  end:function (){
               	 //修改结束日期为开始日期加31天
               	 var myDate = new Date(starttime);
               	 myDate.setMonth(myDate.getMonth() + 12);
               	 $(".monthInput .endtime").val(myDate.Format("yyyy-MM"));
                }});
                return false;
             }
    	}
    }else{
    	//console.log("日期验证");
    	var starttime = $(".dayInput .starttime").val();
    	var endtime = $(".dayInput .endtime").val();
    	var data1 = Date.parse(starttime.replace(/-/g,   "/"));  
        var data2 = Date.parse(endtime.replace(/-/g,   "/"));  
        var datadiff = data2-data1;
        var time = 31*24*60*60*1000; 
    	if(starttime.length>0 && endtime.length>0){  
    		if(datadiff<0||datadiff>time){
                layer.alert('开始时间应小于结束时间并且间隔小于一个月！', {  end:function (){
               	 //修改结束日期为开始日期加31天
               	 var myDate = new Date(starttime);
               	 myDate.setDate(myDate.getDate() + 31);
               	 $(".dayInput .endtime").val(myDate.Format("yyyy-MM-dd"));
                }});
                return false;
             }
    	}
    }
    /* 只能点击一次，10秒之后解绑 */
// 	$(this).attr("disabled","disabled").css("background-color","#b3b0b0").css("border","1px solid #b3b0b0").css("cursor","no-drop").css("pointer-events","none");
// 	setTimeout(function(){
// 		$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
// 	}, 5000);

	searchLabel();
	blockornone();
});
function switchingTime(){
	if($("#time").val()=="年份"){
		//console.log("年份");
		$(".yearInput").css("display","inline-block");
		$(".monthInput").css("display","none");
		$(".dayInput").css("display","none");
	}else if($("#time").val()=="月份"){
		//console.log("月份");
		$(".monthInput").css("display","inline-block");
		$(".yearInput").css("display","none");
		$(".dayInput").css("display","none");
	}else{
		//console.log("日期");
		$(".dayInput").css("display","inline-block");
		$(".monthInput").css("display","none");
		$(".yearInput").css("display","none");
	}
}
function queryParams(params) {
	if($("#time").val()=="年份"){
		var temp = {
		    	starttime:$(".yearInput .starttime").val(),
		    	endtime:$(".yearInput .endtime").val(),
		    	deptCategory:$("#deptCategory").val(),
		    	qxname:qxname,
		    	recesort:$("#recesort").val(),
		    	organization:$("#organization").val()
		    };
	}else if($("#time").val()=="月份"){
		var temp = {
		    	starttime:$(".monthInput .starttime").val(),
		    	endtime:$(".monthInput .endtime").val(),
		    	deptCategory:$("#deptCategory").val(),
		    	qxname:qxname,
		    	recesort:$("#recesort").val(),
		    	organization:$("#organization").val()
		    };
	}else{
		var temp = {
		    	starttime:$(".dayInput .starttime").val(),
		    	endtime:$(".dayInput .endtime").val(),
		    	deptCategory:$("#deptCategory").val(),
		    	qxname:qxname,
		    	recesort:$("#recesort").val(),
		    	organization:$("#organization").val()
		    };
	}
    return temp;
}
//表格查询刷新
function searchLabel() {
	 $('#tableBase').bootstrapTable('refresh', {
	        'url': pageurl
	    });
	 $('#table').bootstrapTable('refresh', {
	        'url': pageurl
	    });
}
//jquery 导出excel 
function exportTable(){
	var cc=$('#askpersonScbb').val();
	if(cc=="咨询基础数据汇总表"){
		$("#tableBase").table2excel({
			exclude: ".noExl",//带noExlclass的行不会被输出到excel中
			name: "Excel Document Name",
			filename: "咨询基础数据汇总表",
			exclude_img: true,
			exclude_links: true,
			exclude_inputs: true
		});
		//var sheet1 =XLSX.utils.table_to_sheet($('#tableBase')[0]);
		//openDownloadDialog(sheet2blob(sheet1), '咨询基础数据汇总表.xlsx');
		
	}else{
		$("#table").table2excel({
			exclude: ".noExl",//带noExlclass的行不会被输出到excel中
			name: "Excel Document Name",
			filename: "咨询业绩汇总表",
			exclude_img: true,
			exclude_links: true,
			exclude_inputs: true
		});
		//var sheet2 =XLSX.utils.table_to_sheet($('#table')[0]);
		//openDownloadDialog(sheet2blob(sheet2), '咨询业绩汇总表.xlsx');
	}
}
/* 咨询基础数据表格初始化 */
function gettableBasedata(){
	//console.log("表格初始化--------");
	var tableObj=$('#tableBase').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        showColumns: true,
        onLoadSuccess: function(data) {
        	//console.log("数据---"+JSON.stringify(data));
			$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto").text("查询");
        	getBasedata(data);// 咨询基础数据图表初始化 (成交占比)
        	var myColumns=$('#table').bootstrapTable('getOptions').columns[0];
        	// console.log(JSON.stringify(myColumns)+"--------myColumnslalal");
        },
        columns: [
		{
   	    	title : '序号',
   	    	align: "center",
   	    	width: 40,
   	    	formatter: function (value, row, index) {
   	     		var pageSize = $('#tableBase').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
   	        	var pageNumber = $('#tableBase').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
   	        	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
   	    	}
   	   	},
   		{
   		    title :'日期',
   		    field: 'month',
   		    align: 'center',
   		    formatter: function (value, row, index) {
   		    	if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
   		    }
   		},{
   		    title : '名称',
   		    field: 'name',
   		    align: 'center',
   		    formatter: function (value, row, index) {
   		    	if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
   		    }
   		},
            {
               title: '初诊业绩',
               field: 'czpaymoney',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                   }
               }
           },{
               title: '初诊人数',
               field: 'czperson',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },
            {
               title: '初诊成交人数',
               field: 'czcjperson',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '初诊成交率',
               field: 'czcjratio',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" +(Number(value)*100).toFixed(2)+ "%</span>";
                   }
               }
           },
            {
               title: '复诊业绩',
               field: 'fzpaymoney',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                   }
               }
           },{
               title: '复诊人数',
               field: 'fzperson',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },
            {
               title: '复诊成交人数',
               field: 'fzcjperson',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '复诊成交率',
               field: 'fzcjratio',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + (Number(value)*100).toFixed(2) + "%</span>";
                   }
               }
           },
            {
               title: '再消费业绩',
               field: 'zxfpaymoney',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                   }
               }
           },{
               title: '复查人次',
               field: 'zxfperson',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },
            {
               title: '再消费成交人次',
               field: 'zxfcjperson',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },
            {
               title: '再消费成交率',
               field: 'zxfcjratio',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + (Number(value)*100).toFixed(2) + "%</span>";
                   }
               }
           },
           {
              title: '药费',
              field: 'drugsmoney',
              align: 'center',
              formatter: function(value, row, index) {
/*             	  var myColumns=$('#tableBase').bootstrapTable('getOptions').columns[0];
              	  var totalMoney=(Number(row.czpaymoney)+Number(row.fzpaymoney)+Number(row.zxfpaymoney)).toFixed(2);
              	  for (var i=0;i<myColumns.length;i++) {
					if(myColumns[i].field=="czpaymoney" && myColumns[i].visible==false){
						totalMoney=(Number(row.fzpaymoney)+Number(row.zxfpaymoney)).toFixed(2);
					}
				  }
              	  return "<span class='name'>" + totalMoney + "</span>"; */
                   if (value) { 
                      return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                   } 
              }
	        },
	        {
	            title: '检查费',
	            field: '',
	            align: 'center',
	            formatter: function(value, row, index) {
	                if (row) {
	                    return "<span class='name'>" + (Number(row.czjcfmoney)+Number(row.fzjcfmoney)+Number(row.zxfjcfmoney)).toFixed(2) + "</span>";
	                }
	            }
	     	},{
				title: '预交金总额',
				field: 'cmoney',
				align: 'center',
				formatter: function(value, row, index) {
				    if (value) {
				        return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
				    }
				}
	    	},{
		        title: '预交金退费',
		        field: 'tcmoney',
		        align: 'center',
		        formatter: function(value, row, index) {
		            if (value) {
		                return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
		            }
		        }
	  		},{
		      title: '预交金已使用',
		      field: '',
		      align: 'center',
		      formatter: function(value, row, index) {
		          if (row) {
		              return "<span class='name'>" + (Number(row.czyjjmoney)+Number(row.fzyjjmoney)+Number(row.zxfyjjmoney)).toFixed(2) + "</span>";
		          }
		      }
			},{
			    title: '预交金未使用',
			    field: '',
			    align: 'center',
			    formatter: function(value, row, index) {
			        if (row) {
			            return "<span class='name'>" + (Number(row.cmoney)+Number(row.tcmoney)-Number(row.czyjjmoney)-Number(row.fzyjjmoney)-Number(row.zxfyjjmoney)).toFixed(2) + "</span>";
			        }
			    }
			},{
	            title: '项目业绩',
	            field: 'xmpaymoney',
	            align: 'center',
	            formatter: function(value, row, index) {
	                if (value) {
	                    return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
	                }
	            }
	      	},
	       	{
	            title: '业绩总计(项目+预交金)',
	            field: '',
	            align: 'center',
	            formatter: function(value, row, index) {
	                if (row) {
	                    return "<span class='name'>" + (Number(row.xmpaymoney)+Number(row.cmoney)+Number(row.tcmoney)).toFixed(2) + "</span>";
	                }
	            }
	      }]
    });
}
// 关于loading的状态
function blockornone(){
	var container= $("#tableBaseBox").find(".fixed-table-loading").css("display");
	if(container=="block"){		
		 $("#query").attr("disabled","disabled").css("background-color","#b3b0b0").css("border","1px solid #b3b0b0").css("cursor","no-drop").css("pointer-events","none").text("查询中");
	}	
}
/* 咨询业绩表格数据初始化 */
function gettabledata(){
	//console.log("表格初始化--------");
	$('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        queryParams: queryParams,
        onLoadSuccess: function(data) {
            //console.log(JSON.stringify(data)+"-----------人均消费");
            getdata(data);//咨询基础数据图表
        },
        columns: [
		{
	    	title : '序号',
	    	align: "center",
	    	width: 40,
	    	formatter: function (value, row, index) {
	     		var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	        	var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	        	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
	    	}
	   	},
		{
		    title : '现场咨询',
		    field: 'name',
		    align: 'center',
		    formatter: function (value, row, index) {
		    	if (value) {
                    return "<span class='name'>" + value + "</span>";
                }
		    }
		},
        {
            title: '成交业绩',
            field: '',
            align: 'center',
            formatter: function(value, row, index) {
            	if (row) {
                    return "<span class='name'>" + (Number(row.xmpaymoney)+Number(row.cmoney)+Number(row.tcmoney)).toFixed(2) + "</span>";
                }
            }
        },
        {
            title: '人均消费',
            field: 'czcjperson',
            align: 'center',
            formatter: function(value, row, index) {
            	var persons=Number(row.czcjperson) + Number(row.fzcjperson) + Number(row.zxfcjperson);
            	var perCapita=((Number(row.xmpaymoney)+Number(row.cmoney)+Number(row.tcmoney)) / persons).toFixed(2);
            	if (value) {
            		if(persons==0){
            			return "<span class='name'>0</span>";
            		}else{
            			return "<span class='name'>" + perCapita + "</span>";
            		}
                }
            }
        }]
    });
}
/* 咨询基础数据汇总初始化 */
function getBasedata(data){
	var xDataArr=[];//x轴日期数据
	var czMoneyArr=[];//初诊业绩
	var czAllPersonArr=[];//初诊（总）人数
	var czwcjPersonArr=[];//初诊未成交人数
	var czcjPersonArr=[];//初诊成交人数
	var fzMoneyArr=[];//复诊业绩
	var fzAllPersonArr=[];//复诊（总）人数
	var fzwcjPersonArr=[];//复诊未成交人数
	var fzcjPersonArr=[];//复诊成交人数
	var zxfMoneyArr=[];//再消费业绩
	var zxfAllPersonArr=[];//复查（再消费总）人数
	var zxfwcjPersonArr=[];//再消费未成交人数
	var zxfcjPersonArr=[];//再消费成交人数
	var monthAllMoney=[];//当月总业绩
	
	for (var i = 0; i < data.length; i++) {
		/* if(data[i].month=="-"){
			continue;
		} */
		xDataArr.push(data[i].name); //x轴日期数据
		czMoneyArr.push(Number(data[i].czpaymoney)); //初诊业绩
		czAllPersonArr.push(Number(data[i].czperson)); //初诊人数
		czwcjPersonArr.push(Number(data[i].czperson)-Number(data[i].czcjperson)); //初诊未成交人数
		czcjPersonArr.push(Number(data[i].czcjperson)); //初诊成交人数
		fzMoneyArr.push(Number(data[i].fzpaymoney)); //复诊业绩
		fzAllPersonArr.push(Number(data[i].fzperson)); //复诊人数
		fzwcjPersonArr.push(Number(data[i].fzperson)-Number(data[i].fzcjperson)); //复诊未成交人数
		fzcjPersonArr.push(Number(data[i].fzcjperson)); //复诊成交人数
		zxfMoneyArr.push(Number(data[i].zxfpaymoney)); //再消费业绩
		zxfAllPersonArr.push(Number(data[i].zxfperson)); //复查人数
		zxfwcjPersonArr.push(Number(data[i].zxfperson)-Number(data[i].zxfcjperson)); //再消费未成交人数
		zxfcjPersonArr.push(Number(data[i].zxfcjperson)); //再消费成交人数
		monthAllMoney.push((Number(data[i].xmpaymoney)+Number(data[i].cmoney)+Number(data[i].tcmoney)));  //当月总业绩
		
	}
	var maxLength=data.length>10 ? 10 : data.length-1;
	var chart = new Highcharts.Chart('container_basechart', {
		 chart: {
				type: 'column'
			},
			title: {
				text: '成交占比'
			},
			xAxis: {
				categories: xDataArr,
		      	max:maxLength,		      		      	
			},
			scrollbar: {
				enabled: true
			},
			yAxis: [{
				title: {
					text: '人<br/>数',
					rotation : 2
				}
			}, {
				opposite: true,
				title: {
					text: '金<br/>额',
					rotation : 2
				}
			}],
			tooltip: {
// 				pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>' +
// 				'({point.percentage:.2f}%)<br/>', 
				pointFormatter: function () {
            					//console.log(this.series+"------------this.series");            					
            					if(this.series.name=="初诊成交人次" || this.series.name=="复诊成交人次"|| this.series.name=="再消费成交人次" ||this.series.name=="初诊业绩" ||this.series.name=="复诊业绩" ||this.series.name=="再消费业绩"){
            						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>' +
             						'('+this.percentage.toFixed(2)+'%)<br/>';
            					}else if(this.series.name=="总业绩"){
            						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>'            						
            					}
            				},
				//:.0f 表示保留 0 位小数，详见教程：https://www.hcharts.cn/docs/basic-labels-string-formatting
				shared: true
			},
			plotOptions: {
				column: {
					stacking: 'normal'
				}
			},
			series: [{
				name: '初诊未成交人次',
				data: czwcjPersonArr,
				stack: 'male'
			}, {
				name: '初诊成交人次',
				data: czcjPersonArr,
				stack: 'male'
			}, {
				name: '复诊未成交人次',
				data: fzwcjPersonArr,
				stack: 'male1'
			},{
				name: '复诊成交人次',
				data: fzcjPersonArr,
				stack: 'male1'
			}, {
				name: '再消费未成交人次',
				data: zxfwcjPersonArr,
				stack: 'male2'
			},{
				name: '再消费成交人次',
				data: zxfcjPersonArr,
				stack: 'male2'
			},{
				name: '初诊业绩',
				data: czMoneyArr,
				stack: 'male3',
				color:"rgba(144,237,125,1)",
				yAxis: 1
			},{
				name: '复诊业绩',
				data: fzMoneyArr,
				stack: 'male3',
				color:"rgba(124,181,236,1)",
				yAxis: 1
			},{
				name: '再消费业绩',
				data: zxfMoneyArr,
				stack: 'male3',
				color:"rgba(67,67,72,1)",
				yAxis: 1
			},{
				name: '总业绩',
				data: monthAllMoney,
				color:"#00A6C0",
				yAxis: 1,
				visible:false
			}
			]
	 });
}
/* 咨询业绩图表数据初始化 */
function getdata(data){
	var xDataArr=[];//x轴日期数据
	var monthAllMoney=[];//当月总业绩
	var perCapitaArr=[];//人均消费
	
	for (var i = 0; i < data.length; i++) {
		/* if(data[i].month=="-"){
			continue;
		} */
		xDataArr.push(data[i].name); //x轴日期数据
		monthAllMoney.push((Number(data[i].xmpaymoney)+Number(data[i].cmoney)+Number(data[i].tcmoney))); //当月总业绩
		var persons=Number(data[i].czcjperson) + Number(data[i].fzcjperson) + Number(data[i].zxfcjperson);//成交消费人数
    	var perCapita=((Number(data[i].xmpaymoney)+Number(data[i].cmoney)+Number(data[i].tcmoney)) / persons).toFixed(2); //人均消费
   		if(persons==0){
   			perCapitaArr.push("0");
   		}else{
   			perCapitaArr.push(Number(perCapita));
   		}
	}
	var maxLength=data.length>10 ? 10 : data.length-1;
	var chart = new Highcharts.Chart('container_chart', {
		 chart: {
				type: 'column'
			},
			title: {
				text: '咨询基础数据汇总'
			},
			xAxis: {
				categories: xDataArr,
		      	max:maxLength		      	
			},
			scrollbar: {
				enabled: true
			},
			yAxis: [{
				title: {
					text: '金额<br/>（业绩）',
					rotation : 2
				}
			}, {
				opposite: true,
				title: {
					text: '金额<br/>（人均）',
					rotation : 2
				}
			}],
			plotOptions: {
				column: {
					borderRadius: 5
				}
			},
			tooltip: {
				pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>' +
				'<br/>',
				//:.0f 表示保留 0 位小数，详见教程：https://www.hcharts.cn/docs/basic-labels-string-formatting
				shared: true
			},			
			series: [{
				name:'成交业绩',
				data: monthAllMoney
			},{
				name:'人均消费',
				data: perCapitaArr,
				yAxis: 1
			}]
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
		btnList	+= '{"qx":"sjbb_bmlb","name":"部门类别"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"sjbb_zxry","name":"咨询人员"},';
		btnList	+= '{"qx":"askperson_scbb","name":"生成报表"}';
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
//查询部门
function findDept(menuId){
	//console.log("执行部门");
    var url = contextPath + '/YZSystemAct/getButtonList.act?menuId=' + menuId;
    $.axseY(url, null,
    function(data) {
    	//console.log(data);
        if (data.retState == "0") {
            listbutton = data.retData;
            // 加载该页面的所有可操作按钮，每个页面一共有多少按钮是相对固定的，在此基础上，通过获取当前登录用户的权限按钮，来进行界面展示
            getButtonPower();
            for ( var i = 0; i < listbutton.length; i++) {
        		if(listbutton[i].qxName.search("dept")==0){
        			if(qxname==''){
	        			qxname=listbutton[i].qxName;        				
        			}else{
        				qxname=qxname+","+listbutton[i].qxName;     
        			}
        		}
        	}
            if(qxname!=("")){
        		$.ajax({
        		    url:contextPath+"/SysDeptPrivAct/findDeptNameByButtonName.act",    //请求的url地址
        		    data:{qxname:qxname,organization:$("#organization").val()},
        		    dataType:"json",   //返回格式为json
        		    type:"post",   //请求方式
        		    success:function(data){
        		    	//console.log("权限名称"+JSON.stringify(data));
        		        //请求成功时处理
        		        $("#deptCategory").html("<option value='all'>所有部门</option>");
        		        for (var i = 0; i < data.length; i++) {
        		        $("#deptCategory").append("<option value ="+data[i].id+">"+data[i].deptname+"</option>")
        				}
        		        findDeptPerson("all");
        		    }
        		});
        	}
            //else{
        		//$("#deptCategory").html("<option value='personage'>请选择</option>");
        		//gettabledata(); //表格数据初始化
        	//}
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
//查询部门人员
function findDeptPerson(id){
	var deptid;
	if(id=="all"){
		deptid="all";
	}else{
		deptid=id;
	}
	$.ajax({
	    url:contextPath+"/SysDeptPrivAct/findPersonByDeptId.act",    //请求的url地址
	    data:{deptid:deptid,qxname:qxname,organization:$("#organization").val()},
	    dataType:"json",   //返回格式为json
	    type:"post",   //请求方式
	    success:function(data){
	        //请求成功时处理
		    $("#recesort").html("");
		    $("#recesort").html("<option value='all'>全部员工</option>");  
		    for (var i = 0; i < data.length; i++) {
		       $("#recesort").append("<option value ="+data[i].seqid+">"+data[i].username+"</option>")
			}
		    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-29 licc
		    gettableBasedata(); //表格数据初始化
		    blockornone();
		    gettabledata();// 咨询基础数据表格初始化 
		    
	    }
	});
}
</script>
</html>
