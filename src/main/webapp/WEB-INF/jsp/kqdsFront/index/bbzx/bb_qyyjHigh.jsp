<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>挂号统计</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />


<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
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
							<select id="organization" name="organization"></select>
						</div>
					</div>
               		<div class="kv">
						<label>统计月份</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" />
							</span>
						</div>
					</div>
                </div>
                 <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
            </div>
        </div> 
    <div class="tableHd">全院业绩</div>
    <div class="tableBox">
        <div id="containerhh" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var nowday;
$(function(){
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
 	//时间选择
	$(".unitsDate input").datetimepicker({
		 format: 'yyyy-mm',
         weekStart: 1,
         autoclose: true,
         startView: 3,
         minView: 3,
         forceParse: false,
         language: 'zh-CN'
    });
	nowday = getNowMonth();
	$("#starttime").val(nowday);
	getdata();
});
$("#query").click(function(){
	getdata();
});
function getdata(){
	var starttime = $("#starttime").val();
	 $.ajax({
     	 url: contextPath+"/KQDS_CostOrder_DetailAct/selectCountBBQylr.act?starttime="+starttime+'&organization='+$("#organization").val(),
         context: document.body,
         success: function(data){
       		 var jsonXDate=[];
       		 var jsonD1=[];  
       		 var jsonD2=[];  
             var xz = data.listxz;
             var all = data.listAll;   
             var xznums = data.xz;
             var allnums = data.all;
             for(var i=0;i<all[0].length;i++){
            	 jsonD1[i]=all[0][i+1];
             }
             for(var i=0;i<xz[0].length;i++){
            	 jsonD2[i]=xz[0][i+1];
             }
             if(all.length>0){    
                 for(var i=0;i<jsonD1.length;i++){
                     jsonXDate.push(i+1);
                 }
                 var chart = new Highcharts.Chart('containerhh', {
                	    title: {
                	        text: starttime+'应收：'+allnums+"（元） <br/>"+starttime+'缴费：'+xznums+"（元）",
                	        x: -20
                	    },
                	   /*  subtitle: {
                	        text: nowday+'缴费：'+xznums+"（元）",
                	        x: -20
                	    }, */
                	    credits:{
                    	     enabled:false // 禁用版权信息
                    	 }, 
                	    xAxis: {
                	        categories: jsonXDate
                	    },
                	    yAxis: {
                	        title: {
                	            text: '金额'
                	        },
                	        plotLines: [{
                	            value: 0,
                	            width: 1,
                	            color: '#808080'
                	        }]
                	    },
                	    tooltip: {
                	        valueSuffix: '（元）'
                	    },
                	    legend: {
                	        layout: 'vertical',
                	        align: 'right',
                	        verticalAlign: 'middle',
                	        borderWidth: 0
                	    },
                	    plotOptions: {
                	    	line: {
                	            dataLabels: {
                	               enabled: true
                	            }
                	         }
                	    },
                	    series: [{
                	        name: '应收',
                	        data: jsonD1
                	    }, {
                	        name: '缴费',
                	        data: jsonD2
                	    }]
                	});
             }                
           }
           
     });
}
</script>
</html>
