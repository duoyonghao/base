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
<title></title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />

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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<!-- jquery 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<style>
	.searchWrap{
		padding-top:27px;
	}
	.formBox{
		padding-top:10px;
	}
	#containerhh,#containerRight{
		float: left;
	}
	.chart{
		margin-top:40px;
		overflow: hidden;
	}
	.highcharts-credits{
		display: none;
	}
	.highcharts-title tspan{
		font-size: 18px;
	}
	.unitsDate input{
		text-align: center;	
	}
	.fixed-table-body{
		border: 1px solid #ddd;
		border-radius: 5px;
	}
	table td span.name {
	    display: block;
	    width: 100%;
	    text-align: center;
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
				</div>
				<div class="kv">
	            	<div class="kv hide" id='sjbb_bmlb'>
							<label>部门类别</label>
							<div class="kv-v " >
								<select id="deptCategory" name="deptCategory"></select>
							</div>
					</div>
				</div>
				<%--<div class="kv">--%>
	            	<%--<div class="kv">--%>
							<%--<label>日期查询</label>--%>
							<%--<div class="kv-v " >--%>
								<%--<select id="time" name="time" onchange="switchingTime()">--%>
									<%--<option value='日期' selected="selected">日</option>--%>
									<%--<option value='月份'>月</option>--%>
									<%--<option value='年份'>年</option>--%>
								<%--</select>--%>
							<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
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
							<span class="unitsDate monthInput" style="display:none;">
								<input type="text" placeholder="开始日期" class="starttime" autocomplete="off"/> <span>到</span>
								<input type="text" placeholder="结束日期" class="endtime" autocomplete="off"/>
							</span>
							<!-- 日选项框 -->
							<span class="unitsDate dayInput">
								<input type="text" placeholder="开始日期" class="starttime" autocomplete="off"/> <span>到</span>
								<input type="text" placeholder="结束日期" class="endtime" autocomplete="off"/>
							</span>
						</div>
					</div>
                </div>
			     <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
			    
            </div>
        </div>
    <%--<div class="tableHd">咨询基础数据统计</div>--%>
   	<div class="tableBox" style="">
           <table id="BaseDatatable" class="table-striped table-condensed table-bordered" data-height="200"></table>
    </div>
</div>
<div class="chart">
	<div id="containerhh" style="width:75%" ></div>
 	<div id="containerRight" style="width:25%"></div>
</div>
 
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/HUDH_DataAnalysisAct/bargainPerformance.act';
var nowday;
var allData;
var qxname= "";
var myColumns;
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
	/* var myDate = new Date(nowday);
    myDate.setMonth(myDate.getMonth()-2);
    $(".monthInput .starttime").val(myDate.Format("yyyy-MM")); */
    $(".monthInput .starttime").val(starttime.Format("yyyy-MM"));
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

	//查询中，禁止查询按钮点击 lutian
	$("#query").attr("disabled","disabled").css("background-color","#b3b0b0").css("border","1px solid #b3b0b0").css("cursor","no-drop").css("pointer-events","none");
	$("#query").text("查询中");
});
//jquery 导出excel 
function exportTable(){
	$("#BaseDatatable").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "咨询基础数据统计表",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
//	var sheet =XLSX.utils.table_to_sheet($('#BaseDatatable')[0]);
//	openDownloadDialog(sheet2blob(sheet), '咨询基础数据统计表.xlsx');
}

/* 查询按钮 */
$("#query").click(function(){
    // myColumns=$('#BaseDatatable').bootstrapTable('getOptions').columns[0];
    // if($("#time").val()=="年份"){
    // 	myColumns[1].title="年份";
    // 	var starttime = $(".yearInput .starttime").val();
    // 	var endtime = $(".yearInput .endtime").val();
    // 	var data1 = Date.parse(starttime.replace(/-/g,   "/"));
    //     var data2 = Date.parse(endtime.replace(/-/g,   "/"));
    //     var datadiff = data2-data1;
    //     var time = 5*365*24*60*60*1000;
    // 	if(starttime.length>0 && endtime.length>0){
    // 		if(datadiff<0||datadiff>time){
    //             layer.alert('开始时间应小于结束时间并且间隔小于五年！', {  end:function (){
    //            	 //修改结束日期为开始日期加31天
    //            	 var myDate = new Date(starttime);
    //            	 myDate.setFullYear(myDate.getFullYear() + 4);
    //            	 $(".yearInput .endtime").val(myDate.Format("yyyy"));
    //             }});
    //             return false;
    //          }
    // 	}
    // }
    // else if($("#time").val()=="月份"){
    // 	myColumns[1].title="月份";
    // 	var starttime = $(".monthInput .starttime").val();
    // 	var endtime = $(".monthInput .endtime").val();
    // 	var data1 = Date.parse(starttime.replace(/-/g,   "/"));
    //     var data2 = Date.parse(endtime.replace(/-/g,   "/"));
    //     var datadiff = data2-data1;
    //     var time = 365*24*60*60*1000;
    // 	if(starttime.length>0 && endtime.length>0){
    // 		if(datadiff<0||datadiff>time){
    //             layer.alert('开始时间应小于结束时间并且间隔小于一年！', {  end:function (){
    //            	 //修改结束日期为开始日期加31天
    //            	 var myDate = new Date(starttime);
    //            	 myDate.setMonth(myDate.getMonth() + 12);
    //            	 $(".monthInput .endtime").val(myDate.Format("yyyy-MM"));
    //             }});
    //             return false;
    //          }
    // 	}
    // }else{
    // 	myColumns[1].title="日期";
    // 	var starttime = $(".dayInput .starttime").val();
    // 	var endtime = $(".dayInput .endtime").val();
    // 	var data1 = Date.parse(starttime.replace(/-/g,   "/"));
    //     var data2 = Date.parse(endtime.replace(/-/g,   "/"));
    //     var datadiff = data2-data1;
    //     var time = 31*24*60*60*1000;
    // 	if(starttime.length>0 && endtime.length>0){
    // 		if(datadiff<0||datadiff>time){
    //             layer.alert('开始时间应小于结束时间并且间隔小于一个月！', {  end:function (){
    //            	 //修改结束日期为开始日期加31天
    //            	 var myDate = new Date(starttime);
    //            	 myDate.setDate(myDate.getDate() + 31);
    //            	 $(".dayInput .endtime").val(myDate.Format("yyyy-MM-dd"));
    //             }});
    //             return false;
    //          }
    // 	}
    // }
    /* 只能点击一次，10秒之后解绑 */
	$(this).attr("disabled","disabled").css("background-color","#b3b0b0").css("border","1px solid #b3b0b0").css("cursor","no-drop").css("pointer-events","none");
	$(this).text("查询中");
	/*setTimeout(function(){
		$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
	}, 5000);*/
	
	searchLabel();
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
		    	organization:$("#organization").val()
		    };
	}else if($("#time").val()=="月份"){
		var temp = {
		    	starttime:$(".monthInput .starttime").val(),
		    	endtime:$(".monthInput .endtime").val(),
		    	deptCategory:$("#deptCategory").val(),
		    	qxname:qxname,
		    	organization:$("#organization").val()
		    };
	}else{
		var temp = {
		    	starttime:$(".dayInput .starttime").val(),
		    	endtime:$(".dayInput .endtime").val(),
		    	deptCategory:$("#deptCategory").val(),
		    	qxname:qxname,
		    	organization:$("#organization").val()
		    };
	}
    return temp;
}
//表格查询刷新
function searchLabel() {
	 $('#BaseDatatable').bootstrapTable('refreshOptions', {
	        'url': pageurl,
	        'columns':myColumns
	    });
}
/* 表格数据初始化 */
function gettabledata(){

	$('#BaseDatatable').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        onLoadSuccess: function(data) {
        	//console.log(JSON.stringify(data)+"-------数据加载成功返回数据");
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	//allData=data;
        	//getdata(); //基础数据成交占比(月)
        	//getdataRight(); //业绩占比,右边饼图
        },
        columns: [
            {
                title: '初诊总到院人数',
                field: 'numberOfFirstVisit',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },
            {
                title: '初诊成交人数（不含3项）',
                field: 'noContainsThreeUntradedFirstVisit',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            }, {
                title: '不含三项初诊成交率',
                field: 'noContainsThreeUntradedFirstVisit',
                align: 'center',
                formatter: function(value, row, index) {
                    var num;
                    if(Number(value)==0){
                        num=0;
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }else if(Number(row.numberOfFirstVisit)>0&&Number(value)>0){
                        num=Number(value)/Number(row.numberOfFirstVisit);
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }else if(Number(row.numberOfFirstVisit)>0&&Number(value)==0){
                        num=1;
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }
                }
            },
            {
                title: '初诊成交人数（含3项）',
                field: 'containsThreeUntradedFirstVisit',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },{
                title: '含三项初诊成交率',
                field: 'containsThreeUntradedFirstVisit',
                align: 'center',
                formatter: function(value, row, index) {
                    var num;
                    if(Number(value)==0){
                        num=0;
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }else if(Number(row.numberOfFirstVisit)>0&&Number(value)>0){
                        num=Number(value)/Number(row.numberOfFirstVisit);
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }else if(Number(row.numberOfFirstVisit)>0&&Number(value)==0){
                        num=1;
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }
                }
            },




            {
               title: '当月初诊业绩',
               field: 'monthlyFirstVisit',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                   }
               }
            },{
                title: '(当月新诊初诊成交业绩)',
                field: 'monthlyNewDiagnosisInitial',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                    }
                }
            },{
                title: '(当月新诊复诊成交业绩)',
                field: 'monthlyNewDiagnosisTurnover',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                    }
                }
            },{
                title: '(当月新诊再消费)',
                field: 'monthlyNewDiagnosisConsumption',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                    }
                }
            },
            {
               title: '复诊总业绩',
               field: 'assessmentResults',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                   }
               }
           },{
               title: '含三项未成交资源总数',
               field: 'containsThreeUntradedResources',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },
            {
               title: '含三项复诊人次',
               field: 'containsThreeUntradedSubsequent',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '含三项复诊成交率',
               field: 'containsThreeUntradedSubsequent',
               align: 'center',
               formatter: function(value, row, index) {
				   var num;
                   if(Number(value)==0){
                       num=0;
                       return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                   }else if(Number(row.containsThreeUntradedResources)>0&&Number(value)>0){
						num=Number(value)/Number(row.containsThreeUntradedResources);
                       return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
				   }else if(Number(row.containsThreeUntradedResources)>0&&Number(value)==0){
                       num=1;
                       return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
				   }
               }
           },{
                title: '不含三项未成交资源总数',
                field: 'noContainsThreeUntradedResources',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },
            {
                title: '不含三项复诊人次',
                field: 'noContainsThreeUntradedSubsequent',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },{
                title: '不含三项复诊成交率',
                field: 'noContainsThreeUntradedSubsequent',
                align: 'center',
                formatter: function(value, row, index) {
                    var num;
                    if(Number(value)==0){
                        num=0;
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }else if(Number(row.noContainsThreeUntradedResources)>0&&Number(value)>0){
                        num=Number(value)/Number(row.noContainsThreeUntradedResources);
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }else if(Number(row.noContainsThreeUntradedResources)>0&&Number(value)==0){
                        num=1;
                        return "<span class='name'>" + (num*100).toFixed(2) + "%</span>";
                    }
                }
            },
            {
               title: '非当月复诊业绩',
               field: 'notInMonthTurnover',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                   }
               }
           },{
                title: '(非当月复诊再消费)',
                field: 'notInMonthConsumptionByTurnover',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                    }
                }
            },{
                title: '(非当月新诊复诊)',
                field: 'notInMonthNewDiagnosisByTurnover',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value) {
                        return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                    }
                }
            },
           {
               title: '非当月再消费业绩',
               field: 'notInMonthConsumption',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + Number(value).toFixed(2) + "</span>";
                   }
               }
 	        }]
    });
} 

/* 基础数据成交占比(月) */
function getdata(){
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
	
	for (var i = 0; i < allData.length; i++) {
		if(allData[i].name=="合计"){
			continue;
		}
		xDataArr.push(allData[i].month); //x轴日期数据
		czMoneyArr.push(Number(allData[i].czpaymoney)); //初诊业绩
		czAllPersonArr.push(Number(allData[i].czperson)); //初诊人数
		czwcjPersonArr.push(Number(allData[i].czperson)-Number(allData[i].czcjperson)); //初诊未成交人数
		czcjPersonArr.push(Number(allData[i].czcjperson)); //初诊成交人数
		fzMoneyArr.push(Number(allData[i].fzpaymoney)); //复诊业绩
		fzAllPersonArr.push(Number(allData[i].fzperson)); //复诊人数
		fzwcjPersonArr.push(Number(allData[i].fzperson)-Number(allData[i].fzcjperson)); //复诊未成交人数
		fzcjPersonArr.push(Number(allData[i].fzcjperson)); //复诊成交人数
		zxfMoneyArr.push(Number(allData[i].zxfpaymoney)); //再消费业绩
		zxfAllPersonArr.push(Number(allData[i].zxfperson)); //复查人数
		zxfwcjPersonArr.push(Number(allData[i].zxfperson)-Number(allData[i].zxfcjperson)); //再消费未成交人数
		zxfcjPersonArr.push(Number(allData[i].zxfcjperson)); //再消费成交人数
		monthAllMoney.push(Number(allData[i].czpaymoney)+Number(allData[i].fzpaymoney)+Number(allData[i].zxfpaymoney)); //当月总业绩
		
	}
	
		 var maxNum;
		 if(xDataArr.length<=5){
			 maxNum=xDataArr.length-1;
		 }else{
			 maxNum=(xDataArr.length)/2;
		 }
	
      	 var chart = new Highcharts.Chart('containerhh', {
      		 chart: {
      			type: 'column',
      			},
      			title: {
      				text: '基础成交数据('+$("#time option:selected").text()+')'
      			},
      			xAxis: {
      				categories: xDataArr,
        				max:maxNum,
//         				labels:{
//         					step:2,
//         					style:{textOverflow: 'none'}
//         				}
      			},
       			scrollbar: {
     				enabled: true
     			},
				yAxis: [{
					title: {
						text: '人<br/>次',
	   					rotation : 2
					}
				}, {
					opposite: true,
					title: {
						text: '金额<br/>(元)',
	   					rotation : 3
					}
				}],
      			tooltip: {
      				pointFormatter: function () {
      					if(this.series.name=="初诊成交人次" || this.series.name=="复诊成交人次"|| this.series.name=="再消费成交人次" ||this.series.name=="当月初诊业绩" ||this.series.name=="当月复诊业绩" ||this.series.name=="当月再消费业绩"){
      						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>' +
       						'('+this.percentage.toFixed(2)+'%)<br/>';
      					}else if(this.series.name=="当月总业绩"){
      						return '<span>'+this.series.name+'</span>: <b>'+this.y.toFixed(2)+'</b>'            						
      					}
      				},
      				shared: true
      			},
      			plotOptions: {
      				column: {
      					stacking: 'normal'
      				},
      				series: {
      					cursor: 'pointer',
      					events: {
      						click: function (event) {
      							var timeMonth=event.point.category;
      							if(timeMonth.length<10){
      								layer.open({
          						  		title:timeMonth+"基础数据成交占比",
          						  		type:2,
          						  		closeBtn:1,
          						  		content:contextPath + "/HUDH_DataAnalysisAct/toBb_BaseDataDay.act?timeMonth="+timeMonth+"&deptCategory="+$("#deptCategory").val()+"&qxname="+qxname,
          						  		area:['80%','80%'],
          						  		cancel: function(){ 
          						  		},
          						  		end:function(){
          						  		}
          						  	}); 
      							}
      						}
      					}
      				}
      			},
      			series: [{
      				name: '初诊未成交人次',
      				data: czwcjPersonArr,
      				stack: 'male',
      				color:"rgba(144,237,125,0.6)",
      				pointPlacement: -0.1
      			}, {
      				name: '初诊成交人次',
      				data: czcjPersonArr,
      				stack: 'male',
      				color:"rgba(144,237,125,1)",
      				pointPlacement: -0.1
      			}, {
      				name: '复诊未成交人次',
      				data: fzwcjPersonArr,
      				stack: 'male1',
      				color:"rgba(124,181,236,0.6)",
      				pointPlacement: -0.04
      			},{
      				name: '复诊成交人次',
      				data: fzcjPersonArr,
      				stack: 'male1',
      				color:"rgba(124,181,236,1)",
      				pointPlacement: -0.04
      			}, {
      				name: '再消费未成交人次',
      				data: zxfwcjPersonArr,
      				stack: 'male2',
      				color:"rgba(67,67,72,0.6)",
      				pointPlacement: 0.03
      			},{
      				name: '再消费成交人次',
      				data: zxfcjPersonArr,
      				stack: 'male2',
      				color:"rgba(67,67,72,1)",
      				pointPlacement: 0.03
      			},{
      				name: '当月初诊业绩',
      				data: czMoneyArr,
      				stack: 'male3',
      				pointPlacement: 0.1,
      				color:"rgba(144,237,125,1)",
      				yAxis: 1
      			},{
      				name: '当月复诊业绩',
      				data: fzMoneyArr,
      				stack: 'male3',
      				pointPlacement: 0.1,
      				color:"rgba(124,181,236,1)",
      				yAxis: 1
      			},{
      				name: '当月再消费业绩',
      				data: zxfMoneyArr,
      				stack: 'male3',
      				pointPlacement: 0.1,
      				color:"rgba(67,67,72,1)",
      				yAxis: 1
      			},{
      				name: '初诊人次',
      				type: 'spline',
      				data: czAllPersonArr,
      				pointPlacement: -0.32,
      				dashStyle:'longdash',
      				visible:false,
        			color:"rgba(144,237,125,1)"
      			},{
      				name: '复诊人次',
      				type: 'spline',
      				data: fzAllPersonArr,
      				marker: {
      					symbol: 'triangle'
      				},
      				dashStyle:'longdash',
      				visible:false,
      				pointPlacement: -0.11,
      				color:"rgba(124,181,236,1)"
      			},{
      				name: '复查人次',
      				type: 'spline',
      				data: zxfAllPersonArr,
      				marker: {
      					symbol: 'square'
      				},
      				dashStyle:'longdash',
      				visible:false,
					color:"rgba(67,67,72,1)",
      				pointPlacement: 0.109
      			},{
      				name: '当月总业绩',
      				type: 'spline',
      				data: monthAllMoney,
      				marker: {
      					symbol: 'diamond'
      				},
					color:"#00A6C0",
					yAxis: 1,
      				pointPlacement: 0.32
      			}
      			
      		]
      	 });
}

/* 业绩占比 */
function getdataRight(){
	var czpaymoney=Number(allData[allData.length-1].czpaymoney); //初诊业绩
	var fzpaymoney=Number(allData[allData.length-1].fzpaymoney); //复诊业绩
	var zxfpaymoney=Number(allData[allData.length-1].zxfpaymoney); //再消费业绩
  	var chart = new Highcharts.Chart('containerRight', {
  		 chart: {
  				plotBackgroundColor: null,
  				plotBorderWidth: null,
  				plotShadow: false,
  				type: 'pie'
  			},
  			title: {
  				text: '业绩占比'
  			},
  			tooltip: {
  				pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
  			},
   	   		plotOptions: {
	   			pie: {
	   				allowPointSelect: true,
	   				cursor: 'pointer',
	   				dataLabels: {
	   					enabled: true,
	   					format: '<b>{point.name}</b><br/>{point.percentage:.2f} %',
	   					style: {
	   						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	   					}
	   				}
	   			}
	   		},
	   	   series: [{
	    		name: '业绩',
	   			colorByPoint: true,
	   			data:[
					{ 	
						name:'初诊',
					    y:czpaymoney,
						color:"rgba(144,237,125,1)"  
					},
					{ 	
						name:'复诊',
					    y:fzpaymoney,
						color:"rgba(124,181,236,1)"  
					},
					{ 	
						name:'再消费',
					    y:zxfpaymoney,
						color:"rgba(67,67,72,1)"  
					}
				]
	   		}]
	  	 });
}

/**
 *  设置按钮权限操作 
 */
function getButtonPower(){
	//console.log("执行按钮");
	var menubutton1 = "";
	// 创建一个数组，存放listbutton的qxName 
	var listbuttonArray = new Array();
	for ( var i = 0; i < listbutton.length; i++) {
		listbuttonArray[i] = listbutton[i].qxName;
	}
	/* 按钮 */
	var btnList =  '[';
		btnList	+= '{"qx":"sjbb_bmlb","name":"部门类别"}'; // 最后一个不要逗号
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
    	//console.log(JSON.stringify(data)+"------**------");
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
        		    	//console.log("权限名称"+qxname);
        		        //请求成功时处理
        		        $("#deptCategory").html("<option value='all'>所有部门</option>");
        		        for (var i = 0; i < data.length; i++) {
        		        $("#deptCategory").append("<option value ="+data[i].id+">"+data[i].deptname+"</option>")
        				}
        		        gettabledata(); //表格数据初始化
        		    }
        		});
        	}else{
        		$("#deptCategory").html("<option value='personage'>请选择</option>");
        		gettabledata(); //表格数据初始化
        	}
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
	
}
</script>
</html>
