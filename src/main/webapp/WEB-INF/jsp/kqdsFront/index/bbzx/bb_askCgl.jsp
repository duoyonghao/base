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
<!-- jquery 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<style>
	.tableBody{
		overflow-y:auto;
		
	}
	#table tr:hover{
		border:1px solid blue;
		background-color: #a9ecc7;
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
      <!-- jquery 生成excel table-responsive table2excel -->
    <div class="table2excel tableBox"  style="position:relative;">
    	<div class="tableHeader" style="position:absolute; top:0;left:0;height:55px;overflow:hidden;">
    		<table id="tableHeader" class="table-striped table-condensed table-bordered" style="visibility:hidden;">
    			<tr>
	     	 		<td  style="text-align: center" rowspan="2">接诊咨询</td>
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
			 		<td  style="text-align: center" rowspan="2">实收金额</td>
			 		<td  style="text-align: center" rowspan="2">收款金额</td>
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
		 		<tbody id="hiddenTable" style="visibility:hidden;border:1px solid pink;" >
		 		
		 		</tbody>
	       	</table>
    	</div>
 		<div class="tableBody">
 			<table id="tableBody" class=" table-striped table-condensed table-bordered" style="width: 100%;padding-bottom:2px; box-sizing:content-box;">
     	 		<tr>
	     	 		<td  style="text-align: center" rowspan="2">接诊咨询</td>
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
			 		<td  style="text-align: center" rowspan="2">实收金额</td>
			 		<td  style="text-align: center" rowspan="2">收款金额</td>
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
		 		<tbody id="table" >
		 		
		 		</tbody>
         </table>
 		</div>
     	 
         <div id="containerhh" style="width:100%;" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/KQDS_ScbbAct/selectCountBB.act';
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['ask_scbb'];
var func = ['exportTable'];
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
	
	gettabledata();//表格
	
});
$("#query").click(function(){
	gettabledata();//表格
});
//jquery 导出excel
function exportTable(){
	$(".table2excel .tableBody").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "咨询情况按项目统计表",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
}
function gettabledata(){
	$('#table').html("");
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var url = pageurl+"?starttime="+starttime+"&endtime="+endtime+"&depttype=0&organization="+$("#organization").val();
	 $.axse(url,null, 
             function(data){
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
			 					content+='<td style="text-align: center">'+data[i].ssje+'</td>';
			 					content+='<td style="text-align: center">'+data[i].skje+'</td>';
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
			 					content+='<td style="text-align: center">'+data[i].ssje+'</td>';
			 					content+='<td style="text-align: center">'+data[i].skje+'</td>';
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
			 					content+='<td style="text-align: center">'+data[i].ssje+'</td>';
			 					content+='<td style="text-align: center">'+data[i].skje+'</td>';
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
	 					 //$(".tableBody").outerHeight(135);
	 					/*var tableBody=$(".tableBody").outerHeight();
	 					var tableBodyson=$("#tableBody").outerHeight(); */
		 			}
		 		}
		 		$(".tableBody").outerHeight($(window).outerHeight()-$(".searchWrap").outerHeight()-$(".cornerBox").outerHeight()-15);
		 		setWidth();
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
$("#tableHeader").on('click-row.bs.table',
	function(e, row, element) {
		console.log("hahhaha");
    	$('.success').removeClass('success'); //去除之前选中的行的，选中样式
   		$(element).addClass('success'); //添加当前选中的 success样式用于区别
});
</script>
</html>
