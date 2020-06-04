<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
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
<title>放射科计时统计</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/admin.css">

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
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
	.tableBox .fixed-table-container{
	    height: 450px !important; 
	}
	.tableBox1 .fixed-table-container{
	    height: 300px !important; 
	}
	input[type=text]{
      width: 115px;
    }
    select{
      width: 115px;
    }
     .bootstrap-select:not([class*="span"]):not([class*="col-"]):not([class*="form-control"]) {
	    width: 115px;
	}
	tr td{
	padding: 0 5px;
	}
	
</style>
</head>

<body>
<div id="container" >
            <table class="kqds_table">
		         <tr>
		          <td style="text-align:right;">所属门诊</td>
		          <td style="text-align:left;"> 
		            <select id="organization" name="organization" onchange="selectItems()"></select>
		          </td>
		          <td style="text-align:right;">楼层</td>
		          <td style="text-align:left;"> 
		            <select id="floor" name="floor"></select>
		          </td>
		          <td style="text-align:right;">操作内容</td>
		          <td style="text-align:left;" class="itemsdiv">
					<select id="items" name="items" class="items" multiple data-live-search="true" title="请选择" onchange="itemsSelectOnchang()"></select>
		       	  </td>
		          <td style="text-align:right;">状态</td>
		          <td style="text-align:left;">
		           	<select id="surgery" name="surgery">
						<option value="all">请选择</option>
						<option value="初诊">初诊</option>
						<option value="手术">手术</option>
						<option value="术后">术后</option>
						<option value="复查">复查</option>
					</select>
		          </td>
		          <td style="text-align:right;">查询日期</td>
		          <td style="text-align:left;height: 100%;">
		          <div>
			          <span class="unitsDate dayInput">
			          <input type="text" placeholder="开始日期" class="starttime" autocomplete="off" />
			          </span>
			          <span style="margin: 10px;line-height: 28px;">
			                             到
			          </span>
			          <span class="unitsDate dayInput">
			          <input type="text" placeholder="结束日期" class="endtime" autocomplete="off" style="float: right;"/>
			          </span>
		          </div>
		          </td>
		          <td style="width:300px; margin:0 auto;text-align:center; ">
		          	<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
           			<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="exportTable()" id="sjbb_scbb">生成报表</a>
                	<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
		          </td>
		         </tr>
	         </table>
    <div class="tableHd" style="padding: 0 10px;">完成统计</div>
   	<div class="tableBox">
           <table id="BaseDatatable" class="table-striped table-condensed table-bordered"></table>
    </div>
    <div class="tableHd" style="padding: 0 10px;">超时统计</div>
   	<div class="tableBox1" style="">
           <table id="BaseDatatable1" class="table-striped table-condensed table-bordered"></table>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds_person_select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highstock.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<!-- jquery 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>

<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/Kqds_JhAct/selectTime.act';
var pageurl1 = contextPath+'/Kqds_JhAct/selectTimeByDel.act';
var nowday;
var static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
$(function(){
	//initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	// 连锁门诊下拉框
    initHosSelectListNoCheckWithNull('organization', static_organization);
	//获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    $('.items').selectpicker();
    selectItems();
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
	
});
$('#clean').on('click',
function() {
    var rgvalue = '<%=ChainUtil.getCurrentOrganization(request)%>';
    $("#floor").val('all');
    $("#surgery").val('all');
    $("#organization").val(rgvalue);
    var starttime = getNowFormatDate();
	starttime = new Date(starttime);
	$(".dayInput .starttime").val(starttime.Format("yyyy-MM-dd"));
	$(".dayInput .endtime").val(nowday);
});
//jquery 导出excel 
function exportTable(){
	$("#BaseDatatable").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "放射科数据统计表",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
//	var sheet =XLSX.utils.table_to_sheet($('#BaseDatatable')[0]);
//	openDownloadDialog(sheet2blob(sheet), '咨询基础数据统计表.xlsx');
}

function selectItems(){
	var url = contextPath + '/YZDictAct/getListByParentCode.act';
	$.ajax({
	    type: "POST",
	    url: url,
	    data: {parentCode:'fskxm591',organization:$("#organization").val()},
	    dataType: "json",
	    success: function(r){
	    	if(r.retState==0){
	    		//$("#items").html("<option value='all'>所有项目</option>");
	    		/* for (var i = 0; i < r.list.length; i++) {
		    		var itemsList = r.list[i].dictName.split(",");
	    	    	$("#items").append("<option value='"+itemsList[0]+"'>"+itemsList[0]+"</option>");
	    	    } */
	    		//请求成功时处理
		        $("#items").html("");
		       	for (var i = 0; i < r.list.length; i++) {
		       		var itemsList = r.list[i].dictName.split(",");
		        	$("#items").append("<option value ="+itemsList[0]+">"+itemsList[0]+"</option>")
				}
		        $('.items').selectpicker('refresh'); 
	    	}
	    }
	}); 
}

/* 查询按钮 */
$("#query").click(function(){
	searchLabel();
});

function queryParams(params) {
	var temp = {
	    starttime:$(".dayInput .starttime").val(),
	    endtime:$(".dayInput .endtime").val(),
	    floor:$("#floor").val(),
	    organization:$("#organization").val(),
	    items:itemsSelectOnchang(),
	    surgery:$("#surgery").val(),
	    limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1 //当前页面,默认是上面设置的1(pageNumber)
	};
    return temp;
}
function itemsSelectOnchang() {

	var val = "", staffs = [];            	
    //循环的取出插件选择的元素(通过是否添加了selected类名判断)
    for (var i = 0; i < $(".itemsdiv li.selected").length; i++) {                   
            val=$("#items option:selected").eq(i).attr("value"); 
        if (val != '') {
            staffs.push(val);
        }
    }
    var need2IdStr=staffs.join();
	return need2IdStr;
}
//表格查询刷新
function searchLabel() {
	 $('#BaseDatatable').bootstrapTable('refresh', {
	      'url': pageurl
	 });
	 $('#BaseDatatable1').bootstrapTable('refresh', {
	      'url': pageurl1
	 });
}
/* 表格数据初始化 */
function gettabledata(){
	$('#BaseDatatable').bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 15,
        pageList : [10, 15, 20],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        /* search: true, */
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) {
        	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [
	   		{
	   	    	title : '序号',
	   	    	align: "center",
	   	    	width: 40,
	   	    	formatter: function (value, row, index) {
	   	     		var pageSize = $('#BaseDatatable').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	   	        	var pageNumber = $('#BaseDatatable').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	   	        	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
	   	    	}
	   	   	},
	   		{
	   		    title :'状态',
	   		    field: 'status',
	   		    align: 'center',
	   		    formatter: function (value, row, index) {
	   		    	if (value==3) {
	                    return "<span class='name'>完成</span>";
	                }else if(value==2){
	                	return "<span class='name'>超时</span>";
	                }
	   		    }
	   		},{
	   		    title: '楼层',
	   		    field: 'floor',
	   		    align: 'center',
	   		    formatter: function (value, row, index) {
	   		    	if (value=="A") {
	                    return "<span class='name'>一楼</span>";
	                }else if(value=="B"){
	                	return "<span class='name'>二楼</span>";
	                }
	   		    }
   			},
            {
               title: '姓名',
               field: 'patientName',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '排队-进行(分钟)',
               field: 'starttime',
               align: 'center',
               formatter: function(value, row, index) {
				   if(0<parseInt(Number(value)/1000/60)){
                	   return "<span class='name'>" + parseInt(Number(value)/1000/60)+"分"+Number(value)/1000%60+"秒" + "</span>";
                   }else{
                	   return "<span class='name'>"+Number(value)/1000%60+"秒" + "</span>";
                   }
               }
           },
            {
               title: '进行-完成(分钟)',
               field: 'endtime',
               align: 'center',
               formatter: function(value, row, index) {
            	   if(0<parseInt(Number(value)/1000/60)){
                	   return "<span class='name'>" + parseInt(Number(value)/1000/60)+"分"+Number(value)/1000%60+"秒" + "</span>";
                   }else{
                	   return "<span class='name'>"+Number(value)/1000%60+"秒" + "</span>";
                   }
               }
           }]
    });
} 
/* 表格数据初始化 */
function gettabledata1(){
	$('#BaseDatatable1').bootstrapTable({
        url: pageurl1,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 10,
        pageList : [10, 20, 30],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        /* search: true, */
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) {
        	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox1");
        },
        columns: [
	   		{
	   	    	title : '序号',
	   	    	align: "center",
	   	    	width: 40,
	   	    	formatter: function (value, row, index) {
	   	     		var pageSize = $('#BaseDatatable').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	   	        	var pageNumber = $('#BaseDatatable').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	   	        	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
	   	    	}
	   	   	},
	   		{
	   		    title :'状态',
	   		    field: 'status',
	   		    align: 'center',
	   		    formatter: function (value, row, index) {
	   		    	if (value==3) {
	                    return "<span class='name'>完成</span>";
	                }else if(value==2){
	                	return "<span class='name'>超时</span>";
	                }
	   		    }
	   		},{
	   		    title: '楼层',
	   		    field: 'floor',
	   		    align: 'center',
	   		    formatter: function (value, row, index) {
	   		    	if (value=="A") {
	                    return "<span class='name'>一楼</span>";
	                }else if(value=="B"){
	                	return "<span class='name'>二楼</span>";
	                }
	   		    }
   			},
            {
               title: '姓名',
               field: 'patientName',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '排队-进行(分钟)',
               field: 'starttime',
               align: 'center',
               formatter: function(value, row, index) {
            	   if(0<parseInt(Number(value)/1000/60)){
                	   return "<span class='name'>" + parseInt(Number(value)/1000/60)+"分"+Number(value)/1000%60+"秒" + "</span>";
                   }else{
                	   return "<span class='name'>"+Number(value)/1000%60+"秒" + "</span>";
                   }
               }
           },
            {
               title: '进行-超时(分钟)',
               field: 'endtime',
               align: 'center',
               formatter: function(value, row, index) {
            	   if(0<parseInt(Number(value)/1000/60)){
                	   return "<span class='name'>" + parseInt(Number(value)/1000/60)+"分"+Number(value)/1000%60+"秒" + "</span>";
                   }else{
                	   return "<span class='name'>"+Number(value)/1000%60+"秒" + "</span>";
                   }
               }
           }]
    });
} 

/**
 *  设置按钮权限操作 
 */
function getButtonPower() {
	if(listbutton.length>0){
		$("#floor").html("<option value='all'>请选择</option>");
		for (var i = 0; i < listbutton.length; i++) {
	    	if (listbutton[i].qxName == "A") {
	    		$("#floor").append("<option value='"+listbutton[i].qxName+"'>一楼</option>");
	    	}else if(listbutton[i].qxName == "B"){
	    		$("#floor").append("<option value='"+listbutton[i].qxName+"'>二楼</option>");
	    	}
	    }	
		gettabledata();
		gettabledata1();
	}
}
   
</script>
</html>
