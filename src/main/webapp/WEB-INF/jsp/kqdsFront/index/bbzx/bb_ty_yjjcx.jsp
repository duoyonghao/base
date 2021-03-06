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
						<label>操作时间</label>
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
    <div class="tableHd">预交金查询</div>
      <!-- jquery 生成excel table-responsive table2excel -->
    <div class="table2excel tableBox"  style="position:relative;">
    	<div class="tableHeader" style="position:absolute; top:0;left:0;height:55px;overflow:hidden;">
    		<table id="tableHeader" class="table-striped table-condensed table-bordered" style="visibility:hidden;">
    			<tr>
	     	 		<td  style="text-align: center">预交金</td>
			 		<td  style="text-align: center">充值</td>
			 		<td  style="text-align: center">赠送</td>
			 		<td  style="text-align: center">合计</td>
		 		</tr>
		 		<tbody id="hiddenTable" style="visibility:hidden;" >
		 		
		 		</tbody>
	       	</table>
    	</div>
 		<div class="tableBody">
 			<table id="tableBody" class=" table-striped table-condensed table-bordered" style="width: 100%;padding-bottom:2px; box-sizing:content-box;">
     	 		<tr>
	     	 		<td  style="text-align: center">预交金</td>
			 		<td  style="text-align: center">充值</td>
			 		<td  style="text-align: center">赠送</td>
			 		<td  style="text-align: center">合计</td>
		 		</tr>
		 		<tbody id="table" >
		 		
		 		</tbody>
         </table>
 		</div>
     	 
         <div id="containerhh" style="width:100%" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/KQDS_ScbbAct/selectCount_yjjcx.act';
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['yjjcx_scbb'];
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
<!-- jquery 导出excel -->
function exportTable(){
	$(".table2excel .tableBody").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "预交金查询",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
}
function gettabledata(){
	$('#table').html("");
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var url = pageurl+"?starttime="+starttime+"&endtime="+endtime+"&organization="+$("#organization").val();
	 $.axse(url,null, 
             function(data){
			    data = data.data;
		 		if(data!=null){
		 			if(data.length>0){
		 				$('#table').html("");
		 				var content = "";
		 				for(var i=0;i<data.length;i++){
	 						content+='<tr>';
		 					content+='<td style="text-align: center">'+data[i].name+'</td>';
		 					content+='<td style="text-align: center">'+data[i].money+'</td>';
		 					content+='<td style="text-align: center">'+data[i].givemoney+'</td>';
		 					content+='<td style="text-align: center">'+data[i].total+'</td>';
		 					content+='</tr>';
		 				}
	 					$('#table').append(content);
	 					$("#hiddenTable").append(content);
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
</script>
</html>
