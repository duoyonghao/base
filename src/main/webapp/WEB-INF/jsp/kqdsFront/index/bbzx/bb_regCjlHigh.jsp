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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script>
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
								<select id="organization" name="organization"></select>
							</div>
					</div>
				</div>
               	<div class="kv">
               		<div class="kv">
						<label>挂号日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
                </div>
                <div class="kv" style="width:200px;">
				        <label style="width:76px;">挂号分类：</label>
				            <select class="dict" tig="ghfl" name="regsort" id="regsort" >
			                </select>	
			    </div>
			     <div class="kv" style="width:200px;">
				        <label style="width:76px;">就诊分类：</label>
				            <select class="dict" tig="jzfl" name="recesort" id="recesort" >
			                </select>	
			    </div>
			     <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
			    
            </div>
        </div> 
    <div class="tableHd">成交率统计</div>
    <div class="tableBox" style="overflow: auto;">
     	 <table id="table"  class="table-striped table-condensed table-bordered" style="width: 100%">
         </table>
         <div id="containerhh" style="width:100%; overflow:hidden;" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/KQDS_REGAct/selectCountCjlTable.act';
var nowday;
$(function(){
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
	initDictSelectByClass(); // 挂号分类、就诊分类
	//默认选中免费挂号  触发change事件 给挂号金额赋值
	$("#recesort option:eq(1)").prop("selected", 'selected');
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
	gettabledata();//表格
	getdata();//图表
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
function gettabledata(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var ghfl = $("#regsort").val();
	var jzfl = $("#recesort").val();
	var url = pageurl+"?starttime="+starttime+"&endtime="+endtime+"&ghfl="+ghfl+"&jzfl="+jzfl+'&organization='+$("#organization").val();
	 $.axse(url,null, 
             function(data){
		 		if(data!=null){
		 			if(data.length>0){
		 				$('#table').html("");
		 				var cznum = data[1];//人数
		 				var allnum = data[2];//成交人数
		 				var cjl = data[3];//成交率
		 				var date = data[0];//时间
		 				var hjnum = data[4];//合计人数
		 				var hjcjnum = data[5];//合计成交人数
		 				var hjcjl = data[6];//合计成交率
		 				var datetr='<tr><td ></td>';
		 				var cztr='<tr><td>人数</td>';
		 				var alltr='<tr><td style="width:90px;">成交人数</td>';
		 				var cjltr='<tr><td>成交率(%)</td>';
		 				datetr+='<td>合计</td>';
	 					cztr +='<td>'+hjnum+'</td>';
	 					alltr +='<td>'+hjcjnum+'</td>';
	 					cjltr +='<td>'+hjcjl+'</td>';
		 				for(var i=0;i<date.length;i++){
		 					datetr+='<td>'+date[i].substring(5)+'</td>';
		 					cztr +='<td>'+cznum[i]+'</td>';
		 					alltr +='<td>'+allnum[i]+'</td>';
		 					cjltr +='<td>'+cjl[i]+'</td>';
		 				}
		 				cztr += '</tr>';
	 					alltr += '</tr>';
	 					cjltr += '</tr>';
	 					$('#table').append(datetr).append(cztr).append(alltr).append(cjltr);
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
	var ghfl = $("#regsort").val();
	var jzfl = $("#recesort").val();
	var jsonXDate=[];
	var jsonD1=[];
	 $.ajax({
     	 url: contextPath+"/KQDS_REGAct/selectCountCjl.act?starttime="+starttime+"&endtime="+endtime+"&ghfl="+ghfl+"&jzfl="+jzfl+'&organization='+$("#organization").val(),
         context: document.body,
         success: function(data){
        	 console.log(data);
             jsonXDate = data.datearray;
             for(var cj=0;cj<data.cjlarray.length;cj++){
            	 jsonD1[cj] = parseFloat(Number(data.cjlarray[cj]).toFixed(2));
             }
             jsonD2 = data.fzcjlarray;
             if(data.datearray.length>0){    
            	 var chart = new Highcharts.Chart('containerhh', {
             	    title: {
             	        text: '成交率统计',
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
             	            text: '成交率'
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
             	    series: [
         	            {
	             	        name: $("#recesort").find("option:selected").text()+"成交率",
	             	        data: jsonD1
             	    	}
             	    ]
            	 });
             }                
           }
           
     });
}
</script>
</html>
