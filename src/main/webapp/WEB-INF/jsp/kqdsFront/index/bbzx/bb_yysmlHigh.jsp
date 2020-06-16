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
<title>预约上门率统计</title>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

<style type="text/css">
html{
	overflow:hidden;
}
/* 查询条件标签样式 */
.queryText{
	height:30px;width:65px;
	line-height:30px;text-align:center;
	font-size:14px;color:#333;
	background: #fff;
}
/* 重写样式 */
.searchWrap{position: relative;padding: 0px 0px 8px 0px;}
#table td,#table th{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    font-size:12px;
}
.formBox {
	padding:6px 0 0;
	border-top:1px solid #ddd;
}
</style>

</head>
<body>
<div id="container">
	<!--查询条件-->
	<div class="searchWrap">
		<table style="width: 100%; height: 35px;">
			<tr>
				<td class="queryText">
					查询条件
				</td>
				<td >
					&nbsp;&nbsp;
					<input type="radio" id="menzhen" name="tabraido" checked="checked" style="cursor: pointer;"/>
					<label for="menzhen"  style="cursor: pointer;">&nbsp;门诊预约&nbsp;</label>
					
					<input type="radio" id="wangdian" name="tabraido"  style="cursor: pointer;"/>
					<label for="wangdian"  style="cursor: pointer;">&nbsp;网电预约&nbsp;</label>  
				</td>
			</tr>
		</table>
     	
     	<div id="menzhenquery">
	        <div class="formBox">
		     	<div class="kv">
		        	<div class="kv">
						<label>预约日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
		        </div>
		        <div class="kv" style="width:200px;">
					<label style="width:76px;">预约分类：</label>
					<div class="kv-v">
						<span class="unitsDate">
						<select class="dict" tig="yyfl" name="ordertype" id="ordertype">
				   		</select>
			   		</div>
				</div>
				 <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
			        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
	        </div>
     	</div>
     	
     	<div id="wangdianquery" style="display: none;">
	        <div class="formBox">
		     	<div class="kv">
		        	<div class="kv">
						<label>预约日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="wdstarttime" /><span>到</span>
								<input type="text" placeholder="结束日期" id="wdendtime" />
							</span>
						</div>
					</div>
		        </div>
		         <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
					<a href="javascript:void(0);" class="aBtn search" id="wdquery">查询</a>
			    </div>
	        </div>
     	</div>
	</div>
    <div class="tableHd">预约上门率统计</div>
    <div class="tableBox">
    	<div style="overflow:auto;">
    		<table id="table" class="table-striped table-condensed table-bordered" style="width: 100%" data-height="150"></table>
    	</div>
       	<div id="containerhh" style="width: 100%;height: 380px;"></div>
       	<table id="wdtable" class="table-striped table-condensed table-bordered" style="width: 100%;display: none;" data-height="150"></table>
       	<div id="wdcontainerhh"  style="width: 99%;display: none;"></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/KQDS_Hospital_OrderAct/selectCountYySmlTable.act';
var wdpageurl = contextPath+'/KQDS_Net_OrderAct/selectCountYySmlTable.act';
var nowday;
$(function(){
	initDictSelectByClass(); // 预约分类
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
	//绑定两个时间选择框的chage时间
	$("#starttime,#endtime").change(function(){
		timeCompartAndFz("starttime","endtime");
    });
	$("#wdstarttime").val(nowday);
	$("#wdendtime").val(nowday);
	//绑定两个时间选择框的chage时间
	$("#wdstarttime,#wdendtime").change(function(){
		timeCompartAndFz("wdstarttime","wdendtime");
    });
	
	gettabledata();//表格
	getdata();//图表
	
	//网电
	getwdtabledata();//表格
	getwddata();//图表
});
$("#query").click(function(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var data1 = Date.parse(starttime.replace(/-/g,   "/"));
    var data2 = Date.parse(endtime.replace(/-/g,   "/"));
    var datadiff = data2-data1;
    var time = 31*24*60*60*1000;
	if(starttime.length>0 && endtime.length>0){
          if(datadiff<0||datadiff>time){
             layer.alert('开始时间应小于结束时间并且间隔小于31天！', {  end:function (){
            	 //修改结束日期为开始日期加31天
            	 var myDate = new Date(starttime);
            	 myDate.setDate(myDate.getDate() + 31);
            	 $("#endtime").val(myDate.Format("yyyy-MM-dd"));
             }});
             return false;
          }
	}
	getdata();//图表
	gettabledata();//表格
});
$("#wdquery").click(function(){
	var starttime = $("#wdstarttime").val();
	var endtime = $("#wdendtime").val();
	var data1 = Date.parse(starttime.replace(/-/g,   "/"));
    var data2 = Date.parse(endtime.replace(/-/g,   "/"));
    var datadiff = data2-data1;
    var time = 31*24*60*60*1000;
	if(starttime.length>0 && endtime.length>0){
          if(datadiff<0||datadiff>time){
        	  layer.alert('开始时间应小于结束时间并且间隔小于31天！', {  end:function (){
             	  //修改结束日期为开始日期加31天
             	  var myDate = new Date(starttime);
             	  myDate.setDate(myDate.getDate() + 31);
             	  $("#wdendtime").val(myDate.Format("yyyy-MM-dd"));
              }});
             return false;
          }
	}
	getwddata();//图表
	getwdtabledata();//表格
});
function gettabledata(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var ordertype = $("#ordertype").val();
	 var url = pageurl+"?starttime="+starttime+"&endtime="+endtime+"&ordertype="+ordertype;
	 $.axse(url,null, 
             function(data){
		 		if(data!=null){
		 			if(data.length>0){
		 				$('#table').html("");
		 				var yysum = data[6];//预约总人数
		 				var yysumqx = data[7];//取消预约上门总人数
		 				var yysumreal = data[8];//实际预约上门总人数
		 				var yysmsum = data[9];//预约上门总人数
		 				var smlsum = data[10];//总上门率
		 				
		 				var yynum = data[1];//预约人数
		 				var yynumqx = data[2];//取消预约人数
		 				var yynumreal = data[3];//实际预约人数
		 				var allnum = data[4];//预约上门人数
		 				var sml = data[5];//上门率
		 				var date = data[0];//时间
		 				var datetr='<tr><td style="width:84px;text-align:center;"></td><td style="width:45px;text-align:center;">小计</td>';
		 				var yytr='<tr><td>预约人数</td><td>'+yysum+'</td>';
		 				var yytrqx='<tr><td>取消预约人数</td><td>'+yysumqx+'</td>';
		 				var yytrreal='<tr><td>实际预约人数</td><td>'+yysumreal+'</td>';
		 				var alltr='<tr><td>上门人数</td><td>'+yysmsum+'</td>';
		 				var smltr='<tr><td>上门率(%)</td><td>'+smlsum+'</td>';
		 				for(var i=0;i<date.length;i++){
		 					datetr+='<td>'+date[i].substring(5)+'</td>';
		 					yytr  +='<td>'+yynum[i]+'</td>';
		 					yytrqx  +='<td>'+yynumqx[i]+'</td>';
		 					yytrreal  +='<td>'+yynumreal[i]+'</td>';
		 					alltr +='<td>'+allnum[i]+'</td>';
		 					smltr +='<td>'+sml[i]+'</td>';
		 				}
		 				yytr  +='</tr>';
		 				yytrqx  +='</tr>';
		 				yytrreal  +='</tr>';
	 					alltr +='</tr>';
	 					smltr +='</tr>';
	 					$('#table').append(datetr).append(yytr).append(yytrqx).append(yytrreal).append(alltr).append(smltr);
		 			}
		 		}
             },
             function(){
				layer.alert('查询统计失败！' );
         	 }
       );
}
function getdata(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var ordertype = $("#ordertype").val();
	var jsonXDate=[];
	var jsonD1=[];
	 $.ajax({
     	 url: contextPath+"/KQDS_Hospital_OrderAct/selectCountYySml.act?starttime="+starttime+"&endtime="+endtime+"&ordertype="+ordertype,
         context: document.body,
         success: function(data){
             jsonXDate = data.datearray;
             for(var cj=0;cj<data.smlarray.length;cj++){
            	 jsonD1[cj] = parseFloat(Number(data.smlarray[cj]).toFixed(2));
             }
             if(data.datearray.length>0){    
            	 var chart = new Highcharts.Chart('containerhh', {
             	    title: {
             	        text: '门诊预约上门率',
             	        x: -20
             	    },
             	    credits:{
                 	     enabled:false // 禁用版权信息
                 	 }, 
             	    xAxis: {
             	        categories: jsonXDate
             	    },
             	    yAxis: {
             	    	max:100, // 定义Y轴 最大值  
             	        title: {
             	            text: '上门率'
             	        },
             	        plotLines: [{
             	            value: 0,
             	            width: 1,
             	            color: '#808080'
             	        }]
             	    },
             	    tooltip: {
             	        valueSuffix: '（%）'
             	    },
             	    legend: {
	             	   	align: 'center',
						verticalAlign: 'bottom',
						x: 0, //距离x轴的距离
						y: 0 //距离Y轴的距离
             	    },
             	    plotOptions: {
             	    	line: {
             	            dataLabels: {
             	               enabled: true
             	            }
             	         }
             	    },
             	    series: [{
             	        name: '上门率',
             	        data: jsonD1
             	    }]
            	 });
             }                
           }
           
     });
}

function getwdtabledata(){
	var starttime = $("#wdstarttime").val();
	var endtime = $("#wdendtime").val();
	 var url = wdpageurl+"?starttime="+starttime+"&endtime="+endtime;
	 $.axse(url,null, 
             function(data){
		 		if(data!=null){
		 			if(data.length>0){
		 				$('#wdtable').html("");
		 				var yynum = data[1];//预约人数
		 				var allnum = data[2];//预约上门人数
		 				var sml = data[3];//上门率
		 				var date = data[0];//时间
		 				
		 				var yysum = data[4];//预约总人数
		 				var yysmsum = data[5];//预约上门总人数
		 				var smlsum = data[6];//总上门率
		 				
		 				var datetr='<tr><td></td><td style="width:45px;text-align:center;">小计</td>';
		 				var yytr='<tr><td style="width:84px;">预约人数</td><td>'+yysum+'</td>';
		 				var alltr='<tr><td>上门人数</td><td>'+yysmsum+'</td>';
		 				var smltr='<tr><td>上门率(%)</td><td>'+smlsum+'</td>';
		 				for(var i=0;i<date.length;i++){
		 					datetr+='<td>'+date[i].substring(5)+'</td>';
		 					yytr  +='<td>'+yynum[i]+'</td>';
		 					alltr +='<td>'+allnum[i]+'</td>';
		 					smltr +='<td>'+sml[i]+'</td>';
		 				}
		 				yytr  +='</tr>';
	 					alltr +='</tr>';
	 					smltr +='</tr>';
	 					$('#wdtable').append(datetr).append(yytr).append(alltr).append(smltr);
		 			}
		 		}
             },
             function(){
				layer.alert('查询统计失败！' );
         	 }
       );
}
function getwddata(){
	var starttime = $("#wdstarttime").val();
	var endtime = $("#wdendtime").val();
	var jsonXDate=[];
	var jsonD1=[];
	 $.ajax({
     	 url: contextPath+"/KQDS_Net_OrderAct/selectCountYySml.act?starttime="+starttime+"&endtime="+endtime,
         context: document.body,
         success: function(data){
             jsonXDate = data.datearray;
             jsonD1 = data.smlarray;
             if(data.datearray.length>0){    
            	 var chart = new Highcharts.Chart('wdcontainerhh', {
             	    title: {
             	        text: '网电预约上门率',
             	        x: -20
             	    },
             	    credits:{
                 	     enabled:false // 禁用版权信息
                 	 }, 
             	    xAxis: {
             	        categories: jsonXDate
             	    },
             	    yAxis: {
             	    	max:100, // 定义Y轴 最大值  
             	        title: {
             	            text: '上门率'
             	        },
             	        plotLines: [{
             	            value: 0,
             	            width: 1,
             	            color: '#808080'
             	        }]
             	    },
             	    tooltip: {
             	        valueSuffix: '（%）'
             	    },
             	    legend: {
	             	   	align: 'center',
						verticalAlign: 'bottom',
						x: 0, //距离x轴的距离
						y: 0 //距离Y轴的距离
             	    },
             	    plotOptions: {
             	    	line: {
             	            dataLabels: {
             	               enabled: true
             	            }
             	         }
             	    },
             	    series: [{
             	        name: '上门率',
             	        data: jsonD1
             	    }]
            	 });
             }                
           }
           
     });
}

//展示门诊
$("#menzhen").click(function(){
	
	$("#menzhenquery").show();
	$("#table").show();
	$("#containerhh").show();
	$("#wangdianquery").hide();
	$("#wdtable").hide();
	$("#wdcontainerhh").hide();
});
//展示网电
$("#wangdian").click(function(){
	
	$("#wangdianquery").show();
	$("#wdtable").show();
	$("#wdcontainerhh").show();
	$("#menzhenquery").hide();
	$("#table").hide();
	$("#containerhh").hide();
});
</script>
</html>
