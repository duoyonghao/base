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
<title>咨询成交率</title>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/nodata.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>

<style>
	.searchWrap{
		padding-top:27px;
	}
	.formBox{
		padding-top:10px;
	}
	.chart{
		overflow: hidden;
 		margin-top: 20px;
	}
	.chart .containerLeft{
		width: 35%;
		margin: 10px 5% 0px 7%;
		float: left;
	}
	.chart .containerRightCharts{
		float: left;
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
	.fixed-table-container thead th .sortable {
    padding-right: 10px !important; 
}
</style>
</head>

<body>
<div id="container" >
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
               	<div class="kv">
               		<div class="kv">
						<label>查询日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" autocomplete="off"/> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" autocomplete="off" />
							</span>
						</div>
					</div>
                </div>
                <div class="kv hide" style="width:180px;" id='askperson_scbb'>
				        <label style="width:50px;">表：</label>
				        <select class="dict" name="askpersonScbb" id="askpersonScbb">
				        	<option value='各月总数据'>各月总数据表</option>
				        	<option value='成交科室'>成交科室表</option>
				        	<option value='种植体系统品牌'>种植体系统品牌表</option>
				        	<option value='成交项目对应治疗医生'>成交项目对应治疗医生表</option>
				        	<option value='业绩对应渠道分布'>业绩对应渠道分布表</option>
				        	<option value='患者消费区间'>患者消费区间表</option>
				        </select>	
			    </div>
			     <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
			    
            </div>
        </div> 
    <div class="tableHd">成交详情数据统计</div>
<!-- 各月总数据走势图 -->	
	<div class="chart" style="height: 400px;">
		<div class="containerLeft">
			<table id="tableTotal"  class="table-striped table-condensed table-bordered"  data-height="400">
			</table>
		</div>	  
	 	<div class="containerRightCharts" id="containerRight" style="width:45%"></div>
	</div>
	
<!--     成交科室分类	 -->
	<div class="chart" style=" height: 400px;">
		<div class="containerLeft">
			<table id="tableDepartment"  class="table-striped table-condensed table-bordered" data-height="400">
			</table>
		</div>	  
	 	<div class="containerRightCharts" id="containerRightDepartment" style="width:45%;"></div>
	</div>
<!-- 	种植体系统品牌分类 -->
	<div class="chart" style=" height: 400px">
		<div class="containerLeft">
			<table id="tableSystem"  class="table-striped table-condensed table-bordered"  data-height="400">
			</table>
		</div>	  
	 	<div class="containerRightCharts" id="containerRightSystem" style="width:45%"></div>
	</div>
<!-- 	成交项目对应治疗医生分类 -->
	<div class="chart" style=" height: 400px">
		<div class="containerLeft">
			<table id="tableDoctor"  class="table-striped table-condensed table-bordered"  data-height="400">
			</table>
		</div>	  
	 	<div class="containerRightCharts" id="containerRightDoctor" style="width:45%"></div>
	</div>
<!-- 	业绩对应渠道分布分类 -->
	<div class="chart" style=" height: 400px">
		<div class="containerLeft">
			<table id="tableChannel"  class="table-striped table-condensed table-bordered" data-height="400">
			</table>
		</div>	  
	 	<div class="containerRightCharts" id="containerRightChannel" style="width:45%"></div>
	</div>
<!-- 	患者消费区间分类 -->	
	<div class="chart" style="height: 400px">
		<div class="containerLeft">
			<table id="tableConsume"  class="table-striped table-condensed table-bordered" data-height="400">
			</table>
		</div>	  
	 	<div class="containerRightCharts" id="containerRightConsume" style="width:45%"></div>
	</div>
	

				 
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl=null;
var nowday;
var qxname= "";
var func = ['exportTable'];
var qxnameArr = ['askperson_scbb'];
$(function(){
	//initHosSelectList4Front('organization');// 连锁门诊下拉框	
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,"<%=menuid%>");
 	//时间选择
	$(".unitsDate input").datetimepicker({
		language:  'zh-CN',
		startView: 3,
		minView:3,
        autoclose : true,
		format: 'yyyy-mm',
		pickerPosition: "bottom-right"
	});
	nowday = getNowFormatDate().substring(0,7);
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	
	//获取当前页面所有按钮
    findDept("<%=menuid%>");
	
});

$("#query").click(function(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var data1 = Date.parse(starttime.replace(/-/g,   "/"));  
    var data2 = Date.parse(endtime.replace(/-/g,   "/"));  
    var datadiff = data2-data1;  
    var time = 365*24*60*60*1000;       
	if(starttime.length>0 && endtime.length>0){  
		if(datadiff<0||datadiff>time){
            layer.alert('开始时间应小于结束时间并且间隔小于一年！', {  end:function (){
           	 //修改结束日期为开始日期加31天
           	 var myDate = new Date(starttime);
           	 myDate.setMonth(myDate.getMonth() + 12);
           	 $("#endtime").val(myDate.Format("yyyy-MM"));
            }});
            return false;
         }
	} 
	/* 只能点击一次，10秒之后解绑 */
	$(this).attr("disabled","disabled").css("background-color","#b3b0b0").css("border","1px solid #b3b0b0").css("cursor","no-drop").css("pointer-events","none");
	setTimeout(function(){
		$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
	}, 5000);
	
	searchLabel();
});

//表格查询刷新
function searchLabel() {
	 $('#tableTotal').bootstrapTable('refresh', {
// 	        'url': pageurl
	    });
	 $('#tableDepartment').bootstrapTable('refresh', {
		 
	 });
	 $('#tableSystem').bootstrapTable('refresh', {
		 
	 });
	 $('#tableDoctor').bootstrapTable('refresh', {
		 
	 });
	 $('#tableChannel').bootstrapTable('refresh', {
		 
	 });
	 $('#tableConsume').bootstrapTable('refresh', {
		 
	 });
}


function queryParams(params) {
    var temp = {
    	starttime:$("#starttime").val(),
    	endtime:$("#endtime").val(),
    	deptCategory:$("#deptCategory").val(),
    	qxname:qxname,
    	organization:$("#organization").val()
    };
    //console.log(JSON.stringify(temp)+"-----");
    return temp;
}
//jquery 导出excel 
function exportTable(){
	var cc=$('#askpersonScbb').val();
	if(cc=="各月总数据"){
		$("#tableTotal").table2excel({
			exclude: ".noExl",//带noExlclass的行不会被输出到excel中
			name: "Excel Document Name",
			filename: "各月总数据表",
			exclude_img: true,
			exclude_links: true,
			exclude_inputs: true
		});
		//var sheet1 =XLSX.utils.table_to_sheet($('#tableBase')[0]);
		//openDownloadDialog(sheet2blob(sheet1), '咨询基础数据汇总表.xlsx');
		
	}else if(cc=="成交科室"){
		$("#tableDepartment").table2excel({
			exclude: ".noExl",//带noExlclass的行不会被输出到excel中
			name: "Excel Document Name",
			filename: "成交科室表",
			exclude_img: true,
			exclude_links: true,
			exclude_inputs: true
		});
		//var sheet2 =XLSX.utils.table_to_sheet($('#table')[0]);
		//openDownloadDialog(sheet2blob(sheet2), '咨询业绩汇总表.xlsx');
	}else if(cc=="种植体系统品牌"){
		$("#tableSystem").table2excel({
			exclude: ".noExl",//带noExlclass的行不会被输出到excel中
			name: "Excel Document Name",
			filename: "种植体系统品牌表",
			exclude_img: true,
			exclude_links: true,
			exclude_inputs: true
		});
		//var sheet2 =XLSX.utils.table_to_sheet($('#table')[0]);
		//openDownloadDialog(sheet2blob(sheet2), '咨询业绩汇总表.xlsx');
	}else if(cc=="成交项目对应治疗医生"){
		$("#tableDoctor").table2excel({
			exclude: ".noExl",//带noExlclass的行不会被输出到excel中
			name: "Excel Document Name",
			filename: "成交项目对应治疗医生表",
			exclude_img: true,
			exclude_links: true,
			exclude_inputs: true
		});
		//var sheet2 =XLSX.utils.table_to_sheet($('#table')[0]);
		//openDownloadDialog(sheet2blob(sheet2), '咨询业绩汇总表.xlsx');
	}else if(cc=="业绩对应渠道分布"){
		$("#tableChannel").table2excel({
			exclude: ".noExl",//带noExlclass的行不会被输出到excel中
			name: "Excel Document Name",
			filename: "业绩对应渠道分布表",
			exclude_img: true,
			exclude_links: true,
			exclude_inputs: true
		});
		//var sheet2 =XLSX.utils.table_to_sheet($('#table')[0]);
		//openDownloadDialog(sheet2blob(sheet2), '咨询业绩汇总表.xlsx');
	}else if(cc=="患者消费区间"){
		$("#tableConsume").table2excel({
			exclude: ".noExl",//带noExlclass的行不会被输出到excel中
			name: "Excel Document Name",
			filename: "患者消费区间表",
			exclude_img: true,
			exclude_links: true,
			exclude_inputs: true
		});
		//var sheet2 =XLSX.utils.table_to_sheet($('#table')[0]);
		//openDownloadDialog(sheet2blob(sheet2), '咨询业绩汇总表.xlsx');
	}
}
//总数据table	
function gettabledataTotal (){
$('#tableTotal').bootstrapTable({
    url :contextPath+"/HUDH_DataAnalysisAct/volumeTransaction.act",
    dataType: "json",
    queryParams: queryParams,
    cache: false,
    striped: true,
    pagination: true,//是否显示分页（*）
    pageSize: 15,
    pageList : [15, 20, 25],//可以选择每页大小
    paginationShowPageGo: true,
    onDblClickCell: function(field, value, row, td) {
    	 
    	
    },
    onLoadSuccess: function(data) {
        //console.log(JSON.stringify(data)+"------各月总数据");
        getTotalData(data);
    },
    columns: [
	{
	title : '序号',
	align: "center",
	width: 40,
	formatter: function (value, row, index) {
 		var pageSize = $('#tableTotal').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
    	var pageNumber = $('#tableTotal').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
	}
	},
	{
	title : '月份',
	 field: 'month',
	    align: 'center',
	    formatter: function (value, row, index) {
	    	if (value) {
             return "<span class='name'>" + value + "</span>";
         }
	    }
	},
	{
    	title : '总业绩',
    	 field: 'performance',
 	    align: 'center',
 	    formatter: function (value, row, index) {
 	    	// console.log(""+value)
 	    	if (value) {
                 return "<span class='name'>" + value + "</span>";
             }
 	    }
   	},
	{
	    title : '减免及打折金额',
	    field: 'discount',
	    align: 'center',
	    formatter: function (value, row, index) {
	    	if (value) {
                return "<span class='name'>" + value + "</span>";
            }
	    }
	},
    {
        title: '预交金',
        field: 'money',
        align: 'center',
        formatter: function(value, row, index) {
            if (value) {
                return "<span class='name'>" + value + "</span>";
            }
        }
    },
    {
        title: '退款',
        field: 'tkMoney',
        align: 'center',
        formatter: function(value, row, index) {
            if (value) {
                return "<span class='name'>" + value + "</span>";
            }
        }
    }
    ]
});

}
//      成交科室分类	 
function gettableOffic(){
	$('#tableDepartment').bootstrapTable({
	    url: contextPath+"/HUDH_DataAnalysisAct/DepartmentPerformance.act",
	   queryParams: queryParams,
	    dataType: "json",
	    cache: false,
	    striped: true,
	    pagination: true,//是否显示分页（*）
	    pageSize: 15,
	    pageList : [15, 20, 25],//可以选择每页大小
	    paginationShowPageGo: true,
	    onDblClickCell: function(field, value, row, td) {
	    	 
	    	
	    },
	    onLoadSuccess: function(data) {
	    	//console.log(JSON.stringify(data)+"---------成交科室返回数据");
	    	getdataDepartment(data);
	    },
	    columns: [
		{
		title : '序号',
		align: "center",
		width: 40,
		formatter: function (value, row, index) {
	 		var pageSize = $('#tableDepartment').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	    	var pageNumber = $('#tableDepartment').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		}
		},
		{
	    	title : '成交科室',
	    	 field: 'name',
	 	    align: 'center',
	 	    formatter: function (value, row, index) {
	 	    	if (value) {
	                 return "<span class='name'>" + value + "</span>";
	             }
	 	    }
	   	},
		{
		    title : '成交业绩',
		    field: 'department',
		    align: 'center',
		    sortable: true,
		    formatter: function (value, row, index) {
		    	if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
		    }
		},
	    {
	        title: '业绩占比',
	        field: 'proportion',
	        align: 'center',
	        sortable: true,
	        formatter: function(value, row, index) {
	            if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
	        }
	    }
	    ]
	});
}
//种植体系统
function gettableSystem(){
	//console.log("种植体系统数据初始化");
	$('#tableSystem').bootstrapTable({
	    url: contextPath+"/HUDH_DataAnalysisAct/implantStatistics.act",
	    queryParams: queryParams,
	    dataType: "json",
	    cache: false,
	    striped: true,
	    pagination: true,//是否显示分页（*）
	    pageSize: 15,
	    pageList : [15, 20, 25],//可以选择每页大小
	    paginationShowPageGo: true,
	    onDblClickCell: function(field, value, row, td) {
	    	 
	    	
	    },
	    onLoadSuccess: function(data) {

	    	//console.log(JSON.stringify(data)+"--------种植体系统data");
	    	getdataRightSystem(data);
	    },
	    columns: [
		{
		title : '序号',
		align: "center",
		width: 40,
		formatter: function (value, row, index) {
	 		var pageSize = $('#tableSystem').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	    	var pageNumber = $('#tableSystem').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		}
		},
		{
	    	title : '种植体系统品牌',
	    	 field: 'ImplantName',
	 	    align: 'center',
	 	    formatter: function (value, row, index) {
	 	    	if (value) {
	                 return "<span class='name'>" + value + "</span>";
	             }
	 	    }
	   	},
		{
		    title : '成交数量',
		    field: 'ImplantNum',
		    align: 'center',
		    formatter: function (value, row, index) {
		    	if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
		    }
		},
	    {
	        title: '业绩占比',
	        field: 'proportion',
	        align: 'center',
	        sortable: true,
	        formatter: function(value, row, index) {
	            if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
	        }
	    }
	    ]
	});

}
// 成交项目对应治疗医生分类
function gettableDoctor(){
	$('#tableDoctor').bootstrapTable({
	    url: contextPath+"/HUDH_DataAnalysisAct/doctorStatistics.act",
	   	queryParams: queryParams,
	    dataType: "json",
	    cache: false,
	    striped: true,
	    pagination: true,//是否显示分页（*）
	    pageSize: 15,
	    pageList : [15, 20, 25],//可以选择每页大小
	    paginationShowPageGo: true,
	    onDblClickCell: function(field, value, row, td) {
	    	 
	    },
	    onLoadSuccess: function(data) {
	    	//console.log(JSON.stringify(data)+"------------医生业绩占比返回数据");
	    	getdataRightDoctor(data);
	    },
	    columns: [
		{
		title : '序号',
		align: "center",
		width: 40,
		formatter: function (value, row, index) {
	 		var pageSize = $('#tableDoctor').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	    	var pageNumber = $('#tableDoctor').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		}
		},
		{
	    	title : '医生',
	    	 field: 'name',
	 	    align: 'center',
	 	    formatter: function (value, row, index) {
	 	    	if (value) {
	                 return "<span class='name'>" + value + "</span>";
	             }
	 	    }
	   	},
		{
		    title : '成交业绩',
		    field: 'doctor',
		    align: 'center',
		    sortable: true,
		    formatter: function (value, row, index) {
		    	if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
		    }
		},
	    {
	        title: '业绩占比',
	        field: 'zdoctor',
	        align: 'center',
	        sortable: true,
	        formatter: function(value, row, index) {
	            if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
	        }
	    }
	    ]
	});
}

// 业绩对应渠道分布分类
function gettableChannel(){
	$('#tableChannel').bootstrapTable({
	    url: contextPath+"/HUDH_DataAnalysisAct/Devchannel.act",
	    queryParams: queryParams,
	    dataType: "json",
	    cache: false,
	    striped: true,
	    pagination: true,//是否显示分页（*）
	    pageSize: 15,
	    pageList : [15,20,25],//可以选择每页大小
	    paginationShowPageGo: true,
	    onDblClickCell: function(field, value, row, td) {
	    	 
	    	
	    },
	    onLoadSuccess: function(data) {
	    	getdataRightChannel(data)
	        //console.log("-----"+JSON.stringify(data));
	    },
	    columns: [
		{
		title : '序号',
		align: "center",
		width: 40,
		formatter: function (value, row, index) {
	 		var pageSize = $('#tableChannel').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	    	var pageNumber = $('#tableChannel').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		}
		},
		{
	    	title : '渠道',
	    	 field: 'DevChannelName',
	 	    align: 'center',
	 	    formatter: function (value, row, index) {
	 	    	if (value) {
	                 return "<span class='name'>" + value + "</span>";
	             }
	 	    }
	   	},
		{
		    title : '成交业绩',
		    field: 'performance',
		    align: 'center',
		    sortable: true,
		    formatter: function (value, row, index) {
		    	if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
		    }
		},
	    {
	        title: '业绩占比',
	        field: 'proportion',
	        align: 'center',
	        sortable: true,
	        formatter: function(value, row, index) {
	            if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
	        }
	    }
	    ]
	});

}

// 患者消费区间分类
function gettableExpense(){
	$('#tableConsume').bootstrapTable({
	    url: contextPath+"/HUDH_DataAnalysisAct/consumptionInterval.act",
	   queryParams: queryParams,
	    dataType: "json",
	    cache: false,
	    striped: true,
	    pagination: true,//是否显示分页（*）
	    pageSize: 15,
	    pageList : [15, 20, 25],//可以选择每页大小
	    paginationShowPageGo: true,
	    onDblClickCell: function(field, value, row, td) {
	    	 
	    	
	    },
	    onLoadSuccess: function(data) {
	        getdataRightConsume(data);
	    },
	    columns: [
		{
		title : '序号',
		align: "center",
		width: 40,
		formatter: function (value, row, index) {
	 		var pageSize = $('#tableConsume').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
	    	var pageNumber = $('#tableConsume').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
	    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		}
		},
		{
	    	title : '消费区间',
	    	 field: 'name',
	 	    align: 'center',
	 	    formatter: function (value, row, index) {
	 	    	if (value) {
	                 return "<span class='name'>" + value + "</span>";
	             }
	 	    }
	   	},
		{
		    title : '成交人次',
		    field: 'num',
		    align: 'center',
		    formatter: function (value, row, index) {
		    	if (value) {
	                return "<span class='name'>" + value + "</span>";
	            }
		    }
		}    
	    ]
	});
}

//各月总数据走势图
function getTotalData(data){
		var totalData=data;
		//console.log(JSON.stringify(totalData)+"-----------赋值赋值赋值");
		var xDataArr=[];//x轴日期数据
		var discountMoney=[];//减免及打折金额 (暂无返回数据？？？)
		var monthAllMoney=[];//当月总业绩
		var yjMoney=[];//预交金
		var refundMoney=[];//退款
		
		for (var i = 0; i < totalData.length; i++) {
			xDataArr.push(totalData[i].month); //x轴日期数据
			discountMoney.push(Number(totalData[i].discount)); //减免及打折金额
			monthAllMoney.push(Number(totalData[i].performance)); //当月总业绩
			yjMoney.push(Number(totalData[i].yjMoney)); //预交金
			refundMoney.push(Number(totalData[i].tkMoney)); //退款
			
		}
	   	 var chart = new Highcharts.Chart('containerRight', {
	   			title: {
	   				text: '各月总数据走势图'
	   			},
	   			subtitle: {
	   				text: ''
	   			},
	   			xAxis: {
	   				 categories: xDataArr
	   			},
	   			yAxis: {
	   				title: {
	   					text: '金<br/>额',
	   					rotation : 2
	   				}
	   			},
	   			legend: {
	   				layout: 'vertical',
	   				align: 'right',
	   				verticalAlign: 'middle'
	   			},
	   			series: [{
	   				name: '总业绩',
	   				data: monthAllMoney
	   			}, {
	   				name: '减免及打折金额',
	   				data: discountMoney
	   			}, {
	   				name: '预交金',
	   				data: yjMoney
	   			}, {
	   				name: '退款',
	   				data: refundMoney
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
	   			},
	   			lang: {
       	            noData: "暂无数据！"
       	        },
       	        noData: {
       	            style: {
//        	                fontWeight: 'bold',
       	                fontSize: '20px',
       	                color: '#00a6c0'
       	            }
       	        }
	   	 });
}
//各成交科室业绩占比
function getdataDepartment(data){
	var departmentData=data;
	//console.log(JSON.stringify(departmentData)+"------成交科室占比初始化");
	var departmentArr=[];
	for (var i = 0; i < departmentData.length; i++) {
		var departmentObj={};
		departmentObj.name=departmentData[i].name;
		departmentObj.y=Number(departmentData[i].department);
		departmentArr.push(departmentObj);
	}
       	 var chart = new Highcharts.Chart('containerRightDepartment', {
       		 chart: {
       				plotBackgroundColor: null,
       				plotBorderWidth: null,
       				plotShadow: false,
       				type: 'pie'
       			},
       			title: {
       				text: '各成交科室业绩占比'
       			},
       			tooltip: {
       				//pointFormat: '{point.name}: <b>{point.percentage:.2f}%</b>'
       			},
        	   plotOptions: {
        			pie: {
        				allowPointSelect: true,
        				size: 260,
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
        			name: ' ',
        			colorByPoint: true,
        			data: departmentArr
        		}],
        		lang: {
       	            noData: "暂无数据！"
       	        },
       	        noData: {
       	            style: {
//        	                fontWeight: 'bold',
       	                fontSize: '20px',
       	                color: '#00a6c0'
       	            }
       	        }
       	 });
}
//种植体品牌数据初始化
function getdataRightSystem(data){
	var systemData=data;
	var systemArr=[];
	for(var i=0;i<systemData.length;i++){
		var systemObj={};
		systemObj.name=systemData[i].ImplantName;
		systemObj.y=Number(systemData[i].ImplantNum);
		systemArr.push(systemObj)
	}
       	 var chart = new Highcharts.Chart('containerRightSystem', {
       		 chart: {
       				plotBackgroundColor: null,
       				plotBorderWidth: null,
       				plotShadow: false,
       				type: 'pie'
       			},
       			title: {
       				text: '各种植体系业绩占比'
       			},
       			tooltip: {
       				//pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
       			},
        	   plotOptions: {
        			pie: {
        				allowPointSelect: true,
        				cursor: 'pointer',
        				size: 260,
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
        			name: ' ',
        			colorByPoint: true,
        			data: systemArr
        		}],
        		lang: {
       	            noData: "暂无数据！"
       	        },
       	        noData: {
       	            style: {
//        	                fontWeight: 'bold',
       	                fontSize: '20px',
       	                color: '#00a6c0'
       	            }
       	        }
       	 });
}
//containerRightDoctor
function getdataRightDoctor(data){
	var doctorData=data;
	// console.log(JSON.stringify(doctorData)+"-------医生初始化图表数据");
	var doctorArr=[];
	for (var i = 0; i < doctorData.length; i++) {
		var doctorObj={};
		doctorObj.name=doctorData[i].name;
		doctorObj.y=Number(doctorData[i].doctor);
		doctorArr.push(doctorObj);
	}
	
       	 var chart = new Highcharts.Chart('containerRightDoctor', {
       		 chart: {
       				plotBackgroundColor: null,
       				plotBorderWidth: null,
       				plotShadow: false,
       				type: 'pie'
       			},
       			title: {
       				text: '各医生业绩占比'
       			},
       			tooltip: {
       				//pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
       			},
        	   plotOptions: {
        			pie: {
        				allowPointSelect: true,
        				cursor: 'pointer',
        				size: 260,
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
        			name: ' ',
        			colorByPoint: true,
        			data: doctorArr
        		}],
        		lang: {
       	            noData: "暂无数据！"
       	        },
       	        noData: {
       	            style: {
//        	                fontWeight: 'bold',
       	                fontSize: '20px',
       	                color: '#00a6c0'
       	            }
       	        }
       	 });
}
//业绩对应渠道分布分类图表
function getdataRightChannel(data){
	var channelData=data;
	 //console.log(JSON.stringify(channelData)+"-------渠道初始化图表数据");
	 var channelArr=[];
	 for (var i = 0; i < channelData.length; i++) {
		 var channelObj={};
		 channelObj.name=channelData[i].DevChannelName;
		 channelObj.y=Number(channelData[i].performance);
		 channelArr.push(channelObj);
	 }
       	 var chart = new Highcharts.Chart('containerRightChannel', {
       		 chart: {
       				plotBackgroundColor: null,
       				plotBorderWidth: null,
       				plotShadow: false,
       				type: 'pie'
       			},
       			title: {
       				text: '各渠道业绩占比'
       			},
       			tooltip: {
       				//pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
       			},
        	   plotOptions: {
        			pie: {
        				allowPointSelect: true,
        				cursor: 'pointer',
        				size: 260,
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
        			name: ' ',
        			colorByPoint: true,
        			data:channelArr
        		}],
        		lang: {
       	            noData: "暂无数据！"
       	        },
       	        noData: {
       	            style: {
//        	                fontWeight: 'bold',
       	                fontSize: '20px',
       	                color: '#00a6c0'
       	            }
       	        }

       	 });
}
//患者消费区间图表
function getdataRightConsume(data){
		var consumeData=data;
		var consumeArr=[];
		 for (var i = 0; i < consumeData.length; i++) {
			var consumeObj={};
			consumeObj.name=consumeData[i].name;
			consumeObj.y=Number(consumeData[i].num);
 			consumeArr.push(consumeObj);
		}
       	 var chart = new Highcharts.Chart('containerRightConsume', {
       		 chart: {
       				plotBackgroundColor: null,
       				plotBorderWidth: null,
       				plotShadow: false,
       				type: 'pie'
       			},
       			credits: {//去掉右下角水印链接
       	            enabled: false
       	        },
       			title: {
       				text: '各消费区间分布'
       			},
       			tooltip: {
       				//pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
       			},
        	   plotOptions: {
        			pie: {
        				allowPointSelect: true,
        				cursor: 'pointer',
        				size: 260,
        				dataLabels: {
        					enabled: true,
         					format: '<b>{point.name}</b><br/><b>{point.y}人</b>',
        					style: {
        						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
        					}
        				}
        			}
        		},
        	   series: [{
        			name: ' ',
        			colorByPoint: true,
        			data: consumeArr,

        		}],
//       		   lang: {
//       	            noData: "没有数据哦!"
//       	        },
//       	        noData: {
//       	            style: {
//       	                fontWeight: 'bold',
//       	                fontSize: '15px',
//       	                color: '#303030'
//       	            }
//       	        }

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
		btnList	+= '{"qx":"sjbb_bmlb","name":"部门类别"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"askperson_scbb","name":"生成报表"}';
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
        		        gettabledataTotal();// 各月总数据表数据table
        		    	gettableOffic();// 成交科室表数据table
        		    	gettableSystem();//种植体系统品牌表数据table
        		    	gettableDoctor();//医生table
        		    	gettableChannel();//渠道table
        		    	gettableExpense();//消费table
        		    }
        		});
        	}else{
        		$("#deptCategory").html("<option value='personage'>请选择</option>");
        		gettabledataTotal();// 各月总数据表数据table
        		gettableOffic();// 成交科室表数据table
        		gettableSystem();//种植体系统品牌表数据table
        		gettableDoctor();//医生table
        		gettableChannel();//渠道table
        		gettableExpense();//消费table
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
