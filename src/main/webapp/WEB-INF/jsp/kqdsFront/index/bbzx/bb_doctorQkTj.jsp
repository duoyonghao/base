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
<title>营收统计</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />

<script src="http://cdn.hcharts.cn/highstock/highstock.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/nodata.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- jquery 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<style>
	#table tr:hover{
		border:1px solid blue;
		background-color: #a9ecc7;
	}
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.searchSelect>.btn { 
		    width: 110px; 
		 	height:24px; 
		 	border: solid 1px #e5e5e5; 
		}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
		    color: #999;
		    height: 24px;
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
								<select id="organization" name="organization"></select>
							</div>
					</div>
				</div>
				<div class="kv">
		            	<div class="kv hide" id='ysqk_bmlb'>
								<label>部门类别</label>
								<div class="kv-v " >
									<select id="deptCategory" name="deptCategory" onchange="findDeptPerson(this.value)"></select>
								</div>
						</div>
				</div>
				<div class="kv">		
						<div class="kv hide" style="width:200px;" id='ysqk_doctor'>
					        <label style="width:76px;">医生：</label>
					        <select class="dict searchSelect" tig="jzfl" name="recesort" id="recesort" data-live-search="true" title=""></select>	
<!-- 					        <select class="dict" tig="jzfl" name="recesort" id="recesort"></select>	 -->
				    	</div>
				</div>
               	<div class="kv">
               		<div class="kv">
						<label>收费时间</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始时间" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束时间" id="endtime" />
							</span>
						</div>
					</div>
                </div>
			     <div class="kv" style="width:250px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
			    
            </div>
        </div> 
    <div class="tableHd">营收统计</div>
     <div id="containerhh" style="width:100%" ></div>
      <!-- jquery 生成excel table-responsive table2excel -->
    <div class=" table2excel tableBox"  style="position:relative;overflow: hidden;">
    	<div class="tableHeader" style="position:absolute; top:0;left:0;overflow:hidden;">
    		<table id="tableHeader" class="table-striped table-condensed table-bordered" style="visibility:hidden;">
     	 		<tr>
	     	 		<td  style="text-align: center" rowspan="2">医生</td>
			 		<td  style="text-align: center" rowspan="2">咨询项目</td>
			 		<td  style="text-align: center" rowspan="2">咨询人次</td>
			 		<td  style="text-align: center" rowspan="2">占总咨询数(%)</td>
			 		<td  style="text-align: center" rowspan="2">成交人数</td>
			 		<td  style="text-align: center" rowspan="2">成功率(%)</td>
			 		<td  style="text-align: center" rowspan="2">占总成交人数(%)</td>
			 		<td  style="text-align: center" rowspan="2">未成交人数</td>
			 		<td  style="text-align: center" colspan="2">初诊人次</td>
			 		<td  style="text-align: center" colspan="2">复诊人次</td>
			 		<td  style="text-align: center" colspan="2">再消费人次</td>
			 		<td  style="text-align: center" colspan="2">复查人次</td>
			 		<td  style="text-align: center" colspan="2">其他人次</td>
			 		<td  style="text-align: center" rowspan="2">营收金额</td>
			 		<td  style="text-align: center" rowspan="2">预交金</td>
		 		</tr>
		 		<tr>
			 		<td  style="text-align: center">总人次</td>
			 		<td  style="text-align: center">成交</td>
			 		<td  style="text-align: center">总人次</td>
			 		<td  style="text-align: center">成交</td>
			 		<td  style="text-align: center">总人次</td>
			 		<td  style="text-align: center">成交</td>
			 		<td  style="text-align: center">总人次</td>
			 		<td  style="text-align: center">成交</td>
			 		<td  style="text-align: center">总人次</td>
			 		<td  style="text-align: center">成交</td>
		 		</tr>
		 		<tbody id="hiddenTable" style="visibility:hidden;" >
		 		
		 		</tbody>
        	</table>
    	</div>
    	
    	<div class="tableBody" style="overflow-y:auto;padding-bottom:2px; box-sizing:content-box;">
	     	 <table id="tableBody" class="table-striped table-condensed table-bordered" style="width: 100%">
	     	 		<tr>
		     	 		<td  style="text-align: center" rowspan="2">医生</td>
				 		<td  style="text-align: center" rowspan="2">咨询项目</td>
				 		<td  style="text-align: center" rowspan="2">咨询人次</td>
				 		<td  style="text-align: center" rowspan="2">占总咨询数(%)</td>
				 		<td  style="text-align: center" rowspan="2">成交人数</td>
				 		<td  style="text-align: center" rowspan="2">成功率(%)</td>
				 		<td  style="text-align: center" rowspan="2">占总成交人数(%)</td>
				 		<td  style="text-align: center" rowspan="2">未成交人数</td>
				 		<td  style="text-align: center" colspan="2">初诊人次</td>
				 		<td  style="text-align: center" colspan="2">复诊人次</td>
				 		<td  style="text-align: center" colspan="2">再消费人次</td>
				 		<td  style="text-align: center" colspan="2">复查人次</td>
				 		<td  style="text-align: center" colspan="2">其他人次</td>
				 		<td  style="text-align: center" rowspan="2">营收金额</td>
				 		<td  style="text-align: center" rowspan="2">预交金</td>
			 		</tr>
			 		<tr>
				 		<td  style="text-align: center">总人次</td>
				 		<td  style="text-align: center">成交</td>
				 		<td  style="text-align: center">总人次</td>
				 		<td  style="text-align: center">成交</td>
				 		<td  style="text-align: center">总人次</td>
				 		<td  style="text-align: center">成交</td>
				 		<td  style="text-align: center">总人次</td>
				 		<td  style="text-align: center">成交</td>
				 		<td  style="text-align: center">总人次</td>
				 		<td  style="text-align: center">成交</td>
			 		</tr>
			 		<tbody  id="table" >
			 		
			 		</tbody>
	         </table>
         </div>
<!--          <div id="containerhh" style="width:100%" ></div> -->
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/KQDS_ScbbAct/selectCountDoctorBB.act';
var nowday;
var qxname="";
var menuid = "<%=menuid%>";
var qxnameArr = ['doctor_scbb'];
var func = ['exportTable'];
var status;
$(function(){
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
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
	//获取当前页面所有按钮
	status=0;
    findDept(menuid);
	//gettabledata();//表格
	//getdata();//图表	
	 $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-30 licc
});
$("#query").click(function(){
	gettabledata();//表格
	//getdata();//图表
});
//  jquery 导出excel 
function exportTable(){
	$(".table2excel .tableBody").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "医生咨询统计表",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
}
function gettabledata(){
	$('#table').html("");
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var deptCategory=$("#deptCategory").val();
	var recesort=$("#recesort").val();
	var url = pageurl+"?starttime="+starttime+"&endtime="+endtime+"&depttype=1&organization="+$("#organization").val()+"&deptCategory="+deptCategory+"&recesort="+recesort+"&qxname="+qxname;
	 $.axse(url,null, 
             function(data){
		      getdata(data);
		// console.log(JSON.stringify(data)+"-----");
			    data = data.rows;
		 		if(data!=null){
		 			if(data.length>0){
		 				$('#table').html("");
		 				var content = "";
		 				for(var i=0;i<data.length;i++){
		 					if(data[i].zxxm){
		 						content+='<tr>';
			 					content+='<td style="text-align: center">'+data[i].username+'</td>';
			 					content+='<td style="text-align: center">'+data[i].zxxm+'</td>';
			 					content+='<td style="text-align: center">'+data[i].zxnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].zzlzb+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].cjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].cgl+'</td>';
			 					content+='<td style="text-align: center">'+data[i].cglzb+'</td>';
			 					content+='<td style="text-align: center">'+data[i].wcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].czallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].czcjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fzallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fzcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].zxfallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].zxfcjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fcallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fccjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].qtallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].qtcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].skje+'</td>';
			 					content+='<td style="text-align: center">'+data[i].totalAdvance+'</td>';
			 					content+='</tr>';
		 					}else if(i == (data.length-1)){
		 						content+='<tr>';
		 						content+='<td style="text-align: center" colspan="2">总计</td>';	
		 						content+='<td style="text-align: center">'+data[i].zxnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].zzlzb+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].cjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].cgl+'</td>';
			 					content+='<td style="text-align: center">'+data[i].cglzb+'</td>';
			 					content+='<td style="text-align: center">'+data[i].wcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].czallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].czcjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fzallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fzcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].zxfallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].zxfcjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fcallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fccjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].qtallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].qtcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].skje+'</td>';
			 					content+='<td style="text-align: center">'+data[i].totalAdvance+'</td>';
			 					content+='</tr>';
		 					}else{
		 						content+='<tr>';
		 						content+='<td style="text-align: center" colspan="2">合计</td>';	
		 						content+='<td style="text-align: center">'+data[i].zxnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].zzlzb+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].cjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].cgl+'</td>';
			 					content+='<td style="text-align: center">'+data[i].cglzb+'</td>';
			 					content+='<td style="text-align: center">'+data[i].wcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].czallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].czcjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fzallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fzcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].zxfallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].zxfcjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fcallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].fccjnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].qtallnums+'</td>';
			 					content+='<td style="text-align: center">'+data[i].qtcjnums+'</td>';
			 					
			 					content+='<td style="text-align: center">'+data[i].skje+'</td>';
			 					content+='<td style="text-align: center">'+data[i].totalAdvance+'</td>';
			 					content+='</tr>';
		 					}
		 					
		 				}
		 				/* content+='<tr>';
	 					content+='<td colspan="2" style="text-align: center">总计</td>';
	 					content+='<td style="text-align: center">'+Number(yysr).toFixed(2)+'</td>';
	 					content+='<td style="text-align: center">100.00%</td>';
	 					content+='</tr>'; */
	 					$('#table').append(content);
	 					$("#hiddenTable").append(content);
	 					
		 			}
		 		}
		 		$(".tableBody").outerHeight($(window).outerHeight()-$(".searchWrap").outerHeight()-$(".cornerBox").outerHeight()-15);
				setWidth();//设置tableheade的表头和内容一样宽
             },
			function(){
           	    layer.alert('查询统计失败！' );
			}
       );
}
function setWidth(){
	$("#tableHeader").outerWidth($("#tableBody").outerWidth());
	$("#tableHeader").css("visibility","visible");
}


/* 基础数据成交占比(月) */
function getdata(data){

	var jsonXPerson=[];//横坐标医生
	var jsonCzCj=[];//初诊成交人次
	var jsonFzCj=[];//复诊成交人次
	var jsonZxfCj=[];//再消费成交人次
	var jsonFcCj=[];//复查成交人次
	var jsonQtCj=[];//其他成交人次
	var jsonCzWCj=[];//初诊未成交人次
	var jsonFzWCj=[];//复诊未成交人次
	var jsonZxfWCj=[];//再消费未成交人次
	var jsonFcWCj=[];//复查未成交人次
	var jsonQtWCj=[];//其他未成交人次
	var jsonZxRc=[];

        	var  chartData = data.rows;
        	//console.log(JSON.stringify(chartData)+"----");
        	 for (var i = 0; i < chartData.length; i++) {
        		 var flag=chartData[i].hasOwnProperty('username');
        		 if(flag===false){
//         			 医生
        			 var usename=chartData[i-1].username;
//         			 总人次
					var Allsum=Number(chartData[i].zxnums);
					//console.log(Allsum+"------");
        			 var czAll=Number(chartData[i].czallnums);
        			 var fzAll=Number(chartData[i].fzallnums);
        			 var zxfAll=Number(chartData[i].zxfallnums);
        			 var fcAll=Number(chartData[i].fcallnums);
        			 var qtAll=Number(chartData[i].qtallnums);
//         			 成交人次
        			 var czCj=Number(chartData[i].czcjnums);
        			 var fzCj=Number(chartData[i].fzcjnums);
        			 var zxfCj=Number(chartData[i].zxfcjnums);
        			 var fcCj=Number(chartData[i].fccjnums);
        			 var qtCj=Number(chartData[i].qtcjnums);
//         			 未成交人次
        			 var czWCj=czAll-czCj;
        			 var fzWCj=fzAll-fzCj;
        			 var zxWfCj=zxfAll-zxfCj;
        			 var fcWCj=fcAll-fcCj;
        			 var qtWCj=qtAll-qtCj;

					if(usename!==undefined){
//	         			 医生arr
 						 jsonXPerson.push(usename);
//  					咨询人次
						 jsonZxRc.push(Allsum);
// 	         			 成交人次arr
 	        			 jsonCzCj.push(czCj);
 	        			 jsonFzCj.push(fzCj);
 	        			 jsonZxfCj.push(zxfCj);
 	        			 jsonFcCj.push(fcCj);
 	        			 jsonQtCj.push(qtCj);
// 	         			 未成交人次arr
 	 					 jsonCzWCj.push(czWCj);
 	        			 jsonFzWCj.push(fzWCj);
 	        			 jsonZxfWCj.push(zxWfCj);
 	        			 jsonFcWCj.push(fcWCj);
 	        			 jsonQtWCj.push(qtWCj);
					}
        		 }           		 
        	 }

        	 var maxNum=jsonXPerson.length>10 ? 10 : jsonXPerson.length-1;
        	// console.log(jsonXPerson.length+"------person");
        	 
            	 var chart = new Highcharts.Chart('containerhh', {
            		 chart: {
            			 type: 'column',
            			},
            			title: {
            				text: '基础数据成交占比',
            				useHTML: true,
                            style: {
//                              color: '#FF00FF',      //字体颜色
                             "fontSize": "25px", 
                             fontWeight: 'bold'
                           }      
            			},
            			xAxis: {
            				categories: jsonXPerson,
            				max:maxNum
            			},
            			credits: {
                            enabled: false
                        },
            			scrollbar: {
            				enabled: true
            			},
            			yAxis: {
            				title: {
            					text: '人次'
            				}
            			},
            			tooltip: {
            				pointFormatter: function () {
            					//console.log(this.series+"------------this.series");            					
            					if(this.series.name=="初诊成交人次" || this.series.name=="复诊成交人次"|| this.series.name=="再消费成交人次" ||this.series.name=="复查成交人次" ||this.series.name=="其他成交人次"){
            						return '<span>'+this.series.name+'</span>: <b>'+this.y+'</b>' +
             						'('+this.percentage.toFixed(2)+'%)<br/>';
            					}
            				},
            				//:.0f 表示保留 0 位小数，详见教程：https://www.hcharts.cn/docs/basic-labels-string-formatting
            				shared: true
            			},
            			plotOptions: {
            				column: {
            					stacking: 'normal'
            				},
            			},
            			series: [{
            				name: '初诊未成交人次',
            				data: jsonCzWCj,
            				stack: 'male',
            				color:"rgba(144,237,125,0.5)",
            				visible:false
//             				pointPlacement: -0.1
            			}, {
            				name: '初诊成交人次',
            				data: jsonCzCj,
            				stack: 'male',
            				color:"rgba(144,237,125,1)",
//             				pointPlacement: -0.1
            			}, {
            				name: '复诊未成交人次',
            				data: jsonFzWCj,
            				stack: 'male1',
            				color:"rgba(124,181,236,0.5)",
            				visible:false
//             				pointPlacement: -0.04
            			},{
            				name: '复诊成交人次',
            				data: jsonFzCj,
            				stack: 'male1',
            				color:"rgba(124,181,236,1)",
//             				pointPlacement: -0.04
            			},{
            				name: '再消费未成交人次',
            				data: jsonZxfWCj,
            				stack: 'male2',
            				color:"rgba(67,67,72,0.5)",
            				visible:false
//             				pointPlacement: 0.03
            			},{
            				name: '再消费成交人次',
            				data: jsonZxfCj,
            				stack: 'male2',
            				color:"rgba(67,67,72,1)",
//             				pointPlacement: 0.03
            			},{
            				name: '复查未成交人次',
            				data: jsonFcWCj,
            				stack: 'male3',
            				color:"rgb(247, 163, 92,0.5)",
            				visible:false
//             				pointPlacement: -0.04
            			},{
            				name: '复查成交人次',
            				data: jsonFcCj,
            				stack: 'male3',
            				color:"rgb(247, 163, 92,1)",
//             				pointPlacement: -0.04
            			},{
            				name: '其他未成交人次',
            				data: jsonQtWCj,
            				stack: 'male4',
            				color:"rgb(128, 133, 233,0.5)",
            				visible:false
//             				pointPlacement: -0.04
            			},{
            				name: '其他成交人次',
            				data: jsonQtCj,
            				stack: 'male4',
            				color:"rgb(128, 133, 233,1)",
//             				pointPlacement: -0.04
            			}
//             			,{
//             				name: '咨询人次',
//             				type: 'spline',
//             				data: jsonZxRc,
//             				stack: 'male5',
//             				color:"pink",
// //             				pointPlacement: -0.04
//             			}              			
            		],
            		lang: {
                        noData: "暂无数据。。。"
                    },
                    noData: {
                        style: {
//                             fontWeight: 'bold',
                            fontSize: '20px',
                            color: '#00a6c0'
                        }
                    }
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
		btnList	+= '{"qx":"ysqk_bmlb","name":"部门类别"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"ysqk_doctor","name":"医生"}';
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
        			qxname=listbutton[i].qxName;
        		}
        	}
            if(qxname!=("")){
        		$.ajax({
        		    url:contextPath+"/SysDeptPrivAct/findDeptNameByButtonName.act",    //请求的url地址
        		    data:{qxname:qxname},
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
        	}else{
        		$("#deptCategory").html("<option value='personage'>请选择</option>");
        		$("#recesort").html("<option value='personage'>个人</option>");
        		if(status==0){
        			status=1;
        			gettabledata(); //表格数据初始化
        		}
        		
        	}
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
	    data:{deptid:deptid,qxname:qxname},
	    dataType:"json",   //返回格式为json
	    type:"post",   //请求方式
	    success:function(data){
	        //请求成功时处理
		    $("#recesort").html("");
		    $("#recesort").html("<option value='all'>全部员工</option>");
		    for (var i = 0; i < data.length; i++) {
		       $("#recesort").append("<option value ="+data[i].seqid+">"+data[i].username+"</option>")
			}
		    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-11-12 licc
		    if(status==0){
    			status=1;
    			gettabledata(); //表格数据初始化
    		}
	    }
	});
}

</script>
</html>
