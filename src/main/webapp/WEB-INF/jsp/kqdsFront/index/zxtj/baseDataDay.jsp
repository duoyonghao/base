<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String timeMonth=(String)request.getAttribute("timeMonth");
	String qxname=(String)request.getAttribute("qxname");
	String deptCategory=(String)request.getAttribute("deptCategory");
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

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
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
<style>
	.searchWrap{
		padding-top:27px;
	}
	.formBox{
		padding-top:10px;
	}	
	.chart{
 	margin-top:80px; 
	overflow: hidden;
	}
 .highcharts-credits{
	display: none;
}
.highcharts-title tspan{
	font-size: 18px;
}
</style>
</head>

<body>
<div id="container" >
    <div class="tableHd">各月咨询基础数据统计</div>
  
</div>
<div class="chart" style="margin-left: 3%;">
	<div id="containerhh" style="width:95%;" ></div>
</div>
 
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var timeMonth="<%=timeMonth%>";
var qxname="<%=qxname%>";
var deptCategory="<%=deptCategory%>";
var pageurl = contextPath+'/HUDH_DataAnalysisAct/findCJStatisticsByDay.act';
var nowday;
$(function(){
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
 	//时间选择
	$(".unitsDate input").datetimepicker({
		language:  'zh-CN',  
		minView:2,
        autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	getdata();//图表
});


function getdata(){
	 $.ajax({
     	 url: pageurl,
     	 data: {timeMonth:timeMonth,deptCategory:deptCategory,qxname:qxname},
         context: document.body,
         success: function(data){ 
        	 //console.log(JSON.stringify(data)+"----------data");
        	 	var xDataArr=[];//x轴日期数据
        		var czMoneyArr=[];//初诊业绩
        		var czAllPersonArr=[];//初诊总人数
        		var czwcjPersonArr=[];//初诊未成交人数
        		var czcjPersonArr=[];//初诊成交人数
        		var fzMoneyArr=[];//复诊业绩
        		var fzAllPersonArr=[];//复诊总人数
        		var fzwcjPersonArr=[];//复诊未成交人数
        		var fzcjPersonArr=[];//复诊成交人数
        		var zxfMoneyArr=[];//再消费业绩
        		var zxfAllPersonArr=[];//再消费总人数
        		var zxfwcjPersonArr=[];//再消费未成交人数
        		var zxfcjPersonArr=[];//再消费成交人数
        		var monthAllMoney=[];//当月总业绩
        		
        		for (var i = 0; i < data.length; i++) {
        			if(data[i].name=="合计"){
        				continue;
        			}
        			xDataArr.push(data[i].day); //x轴日期数据
        			czMoneyArr.push(Number(data[i].czpaymoney)); //初诊业绩
        			czAllPersonArr.push(Number(data[i].czperson)); //初诊总人数
        			czwcjPersonArr.push(Number(data[i].czperson)-Number(data[i].czcjperson)); //初诊未成交人数
        			czcjPersonArr.push(Number(data[i].czcjperson)); //初诊成交人数
        			fzMoneyArr.push(Number(data[i].fzpaymoney)); //复诊业绩
        			fzAllPersonArr.push(Number(data[i].fzperson)); //复诊总人数
        			fzwcjPersonArr.push(Number(data[i].fzperson)-Number(data[i].fzcjperson)); //复诊未成交人数
        			fzcjPersonArr.push(Number(data[i].fzcjperson)); //复诊成交人数
        			zxfMoneyArr.push(Number(data[i].zxfpaymoney)); //再消费业绩
        			zxfAllPersonArr.push(Number(data[i].zxfperson)); //再消费总人数
        			zxfwcjPersonArr.push(Number(data[i].zxfperson)-Number(data[i].zxfcjperson)); //再消费未成交人数
        			zxfcjPersonArr.push(Number(data[i].zxfcjperson)); //再消费成交人数
        			monthAllMoney.push(Number(data[i].czpaymoney)+Number(data[i].fzpaymoney)+Number(data[i].zxfpaymoney)); //再消费未成交人数
        			
        		}
        		var dataLength=data.length<=10 ? data.length-1 : 10;
            	 var chart = new Highcharts.Chart('containerhh', {
            		 chart: {
            			 type: 'column',
            			},
            			title: {
            				text: timeMonth+'基础数据成交占比'
            			},
            			xAxis: {
            				categories: xDataArr,
            				max:dataLength
            			},
            			scrollbar: {
            				enabled: true
            			},
						yAxis: [{
							title: {
								text: '人数'
							}
						}, {
							opposite: true,
							title: {
								text: '金额(元)'
							}
						}],
            			tooltip: {
            				pointFormatter: function () {
            					if(this.series.name=="初诊成交人次" || this.series.name=="复诊成交人次"|| this.series.name=="再消费成交人次" ||this.series.name=="当天初诊业绩" ||this.series.name=="当天复诊业绩" ||this.series.name=="当天再消费业绩"){
            						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>' +
             						'('+this.percentage.toFixed(2)+'%)<br/>';
            					}else if(this.series.name=="当天总业绩"){
            						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>'            						
            					}
            				},
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
            				name: '当天初诊业绩',
            				data: czMoneyArr,
            				stack: 'male3',
            				pointPlacement: 0.1,
            				color:"rgba(144,237,125,1)",
            				yAxis: 1
            			},{
            				name: '当天复诊业绩',
            				data: fzMoneyArr,
            				stack: 'male3',
            				pointPlacement: 0.1,
            				color:"rgba(124,181,236,1)",
            				yAxis: 1
            			},{
            				name: '当天再消费业绩',
            				data: zxfMoneyArr,
            				stack: 'male3',
            				pointPlacement: 0.1,
            				color:"rgba(67,67,72,1)",
            				yAxis: 1
            			},{
            				name: '初诊总人数',
            				type: 'spline',
            				data: czAllPersonArr,
            				pointPlacement: -0.32,
              				color:"rgba(144,237,125,1)"
            			},{
            				name: '复诊总人数',
            				type: 'spline',
            				data: fzAllPersonArr,
            				marker: {
            					enabled: false
            				},
            				pointPlacement: -0.11,
            				color:"rgba(124,181,236,1)"
            			},{
            				name: '再消费总人数',
            				type: 'spline',
            				data: zxfAllPersonArr,
            				marker: {
            					enabled: false
            				},
							color:"rgba(67,67,72,1)",
            				pointPlacement: 0.109
            			},{
            				name: '当天总业绩',
            				type: 'spline',
            				data: monthAllMoney,
            				marker: {
            					enabled: false
            				},
							color:"#00A6C0",
							yAxis: 1,
            				pointPlacement: 0.32
            			}
            			
            		]
            	 });
           }
           
     });
}



</script>
</html>
