<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String timeMonth=(String)request.getAttribute("timeMonth");
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

<script src="http://cdn.hcharts.cn/highstock/highstock.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highstock.js"></script> --%>
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
	<div id="containerdayTotal" style="width:95%;" ></div>
</div>
 
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var timeMonth="<%=timeMonth%>";
var pageurl = '';
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
     	 data: {timeMonth:timeMonth},
         context: document.body,
         success: function(data){ 
        	 var chart = new Highcharts.Chart('containerdayTotal', {
     			title: {
     				text: timeMonth+'总数据走势图'
     			},
     			subtitle: {
     				text: ''
     			},
     			xAxis: {
     				 categories: ['一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月']
     			},
     			yAxis: {
     				title: {
     					text: '金额'
     				}
     			},
     			legend: {
     				layout: 'vertical',
     				align: 'right',
     				verticalAlign: 'middle'
     			},
     			plotOptions: {
     			},
     			series: [{
     				name: '总业绩',
     				data: [490000, 710000, 1060000, 1290000, 1440000, 1760000, 1350000, 1480000, 2160000, 1940000, 950000, 540000]
     			}, {
     				name: '减免及打折金额',
     				data: [83000, 78000, 980000, 930000, 106000, 84000, 10500, 10400, 9100, 8300, 10600, 9200]
     			}, {
     				name: '预交金',
     				data: [48000, 38000, 39000, 41000, 47000, 40800, 59000, 59000, 52000, 60500, 59000, 51000]
     			}, {
     				name: '退款',
     				data: [4200, 3300, 3400, 3900, 5200, 7500, 5700, 6000, 4700, 3900, 4600, 5100]
     			}],
     			responsive: {
     				rules: [{
     					condition: {
     						maxWidth: 500
     					},
     					chartOptions: {
     						legend: {
     							layout: 'horizontal',
     							align: 'center',
     							verticalAlign: 'bottom'
     						}
     					}
     				}]
     			}
     	 	});
         }
           
     });
}



</script>
</html>
