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
<!-- 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<style>
	#container_chart{
		height: 400px;
		max-width:100%;
		margin: 40px auto 0;
	}
	.highcharts-title tspan{
		font-size: 18px;
	}
	.highcharts-series-0 .highcharts-graph{
		stroke:#00a6c0;
	}
	.highcharts-series-0 .highcharts-point{
		fill:#00a6c0;
	}
	/*模拟对角线*/
	.specialth{
    	border: 1px solid red;
    	position: relative;
    	padding: 0px;
    }
    .out{
        width: 115%;
        border-top: 1px solid #ddd;
        transform: translateX(-14px) rotate(10deg);
    }
    b{
       	font-style: normal;
    	display: block;
   	 	position: absolute;
    	top: 0px;
    	right: 25px;
    }
    em{
        font-style: normal;
    	display: block;
    	position: absolute;
    	bottom: 0px;
    	left: 25px;
    }
    .table-bordered > thead > tr > th, .table-bordered > thead > tr > td{
    	border-bottom-width: 1px;
    }
    #table th,td{
    	text-align: center;
    }
    .unitsDate input{
		text-align: center;	
	}
		.highcharts-credits{
		display: none;
	}
	table td span.name {
	    display: block;
	    width: 100% !important;
	    text-align: center;
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
								<select id="organization" name="organization" onchange="findDept(<%=menuid%>)"></select>
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
					<div class="kv" style="width:200px;">
				        <label style="width:76px;">查询分类：</label>
				            <select class="dict" tig="jzfl" name="recesort" id="recesort" >
				            	<option value="zymoney">总业绩</option>
				            	<option value="zmoney">总预交金</option>
				            	<option value="symoney">使用预交金</option>
				            	<option value="tfmoney">退费预交金</option>
				            	<option value="jmdzmoney">减免及打折金额</option>
				            	<option value="tkmoney">退款</option>
				            	<option value="ypmoney">药品</option>
				            	<option value="jcxmmoney">检查项目</option>
			                </select>	
			    	</div>
				</div>
               	<div class="kv">
               		<div class="kv">
						<label>查询日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" autocomplete="off"/> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" autocomplete="off"/>
							</span>
						</div>
					</div>
                </div>
			     <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
			    
            </div>
        </div> 
    <div class="tableHd">总数据年度对比统计</div>
    <div class="tableBox" style="border: 1px solid #ddd;border-radius: 5px;">
           <table id="TotalDatatable" class="table-striped table-condensed table-bordered" ></table>
    </div>
    <div id="container_chart" style="width:100%; overflow:hidden;" ></div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath + '/HUDH_DataAnalysisAct/findTotalMoneyByMonth.act';
var nowday;
var allData;
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
		startView: 4,
		minView:4,
        autoclose : true,
		format: 'yyyy',
		pickerPosition: "bottom-right"
	});
	nowday = getNowFormatDate().substring(0,4);
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	$('#recesort option:selected') .val("zymoney");
	//获取当前页面所有按钮
    findDept("<%=menuid%>");
});
/* 查询按钮点击事件 */
$("#query").click(function(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var selectLabel=$('#recesort option:selected') .val();
	var data1 = Date.parse(starttime.replace(/-/g,   "/"));  
    var data2 = Date.parse(endtime.replace(/-/g,   "/"));  
    var datadiff = data2-data1;  
    var time = 3*365*24*60*60*1000; 
//     console.log(selectLabel+'----');
	if(starttime.length>0 && endtime.length>0){  
		if(datadiff<0||datadiff>time){			
            layer.alert('开始时间应小于结束时间并且间隔小于三年！', {  end:function (){
           	 //修改结束日期为开始日期加31天
           	 var myDate = new Date(starttime);
           	 myDate.setFullYear(myDate.getFullYear() + 2);
           	 $("#endtime").val(myDate.Format("yyyy"));
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

function queryParams(params) {
    var temp = {
    	starttime:$("#starttime").val(),
    	endtime:$("#endtime").val(),
    	selectLabel:$('#recesort option:selected') .val(),
    	deptCategory:$("#deptCategory").val(),
    	qxname:qxname,
    	organization:$("#organization").val()
    };
    return temp;
//     console.log(temp+"------temp");
}
//表格查询刷新
function searchLabel() {
	 $('#TotalDatatable').bootstrapTable('refresh', {
	        'url': pageurl
	    });
}
//jquery 导出excel 
function exportTable(){
	$("#TotalDatatable").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "总数据年度对比统计表",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});
//	var sheet =XLSX.utils.table_to_sheet($('#TotalDatatable')[0]);
//	openDownloadDialog(sheet2blob(sheet), '总数据年度对比统计表.xlsx');
}
/* 表格数据初始化 */
function gettabledata(){
	$('#TotalDatatable').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        onLoadSuccess: function(data) {
        	//console.log(JSON.stringify(data,+"1")+"-------数据加载成功返回数据");
        	//console.log(JSON.stringify(data)+"-------数据加载成功返回数据11111");
        	allData=data;
        	getdata(); //基础数据成交占比(月) 
        },
        columns: [
   		{
   	    	title : '序号',
   	    	align: "center",
   	    	width: 40,
   	    	formatter: function (value, row, index) {
   	     		var pageSize = $('#TotalDatatable').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
   	        	var pageNumber = $('#TotalDatatable').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
   	        	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
   	    	}
   	   	},
   		{
   		    title : '查询分类',
   		    field: 'selectType',
   		    align: 'center',
   		    formatter: function (value, row, index) {
   		    	if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
   		    }
   		},{
   		    title : '年份',
   		    field: 'year',
   		    align: 'center',
   		    formatter: function (value, row, index) {
   		    	if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
   		    }
   		},{
               title: '一月',
               field: 'totalMoney1',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '二月',
               field: 'totalMoney2',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '三月',
               field: 'totalMoney3',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '四月',
               field: 'totalMoney4',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '五月',
               field: 'totalMoney5',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '六月',
               field: 'totalMoney6',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '七月',
               field: 'totalMoney7',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '八月',
               field: 'totalMoney8',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '九月',
               field: 'totalMoney9',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '十月',
               field: 'totalMoney10',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '十一月',
               field: 'totalMoney11',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '十二月',
               field: 'totalMoney12',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name'>" + value + "</span>";
                   }
               }
           },{
               title: '年度总金额',
               field: 'totalYearMoney',
               align: 'center',
               formatter: function(value, row, index) {
                   if (value) {
                       return "<span class='name' style='width:120px'>" + value + "</span>";
                   }
               }
           }
           ]
    });
}
/* 图表数据初始化 */
function getdata(){
	var title=allData[0].selectType;//类标题	
 	var series = [];
	for (var i = 0; i < allData.length; i++) {
	//	console.log(allData[i]);
		var obj={};
		var dataArr=[];	
		dataArr.push(Number(allData[i]["totalMoney1"]));
		dataArr.push(Number(allData[i]["totalMoney2"]));
		dataArr.push(Number(allData[i]["totalMoney3"]));
		dataArr.push(Number(allData[i]["totalMoney4"]));
		dataArr.push(Number(allData[i]["totalMoney5"]));
		dataArr.push(Number(allData[i]["totalMoney6"]));
		dataArr.push(Number(allData[i]["totalMoney7"]));
		dataArr.push(Number(allData[i]["totalMoney8"]));
		dataArr.push(Number(allData[i]["totalMoney9"]));
		dataArr.push(Number(allData[i]["totalMoney10"]));
		dataArr.push(Number(allData[i]["totalMoney11"]));
		dataArr.push(Number(allData[i]["totalMoney12"]));
		
		obj.name=allData[i].year;
		obj.data=dataArr;
		series.push(obj);
		//console.log(JSON.stringify(series)+"-----");		
	}
	
	
	var chart = Highcharts.chart('container_chart', {
		chart: {
			type: 'spline'
		},
		title: {
			text: title+'——年度对比走势图'
		},
		xAxis: {
			categories: ['一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月']
		},
		yAxis: {
			title: {
				text: '金<br/>额',
				rotation : 2
			}
		},
		tooltip: {
			crosshairs: true,
			shared: true
		},
		plotOptions: {
			spline: {
				marker: {
					radius: 4,
					lineColor: '#666666',
					lineWidth: 1
				}
			}
		},
		series : series
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
		btnList	+= '{"qx":"sjbb_bmlb","name":"部门类别"}'; // 最后一个不要逗号
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
        		        gettabledata(); //表格
        		    }
        		});
        	}else{
        		$("#deptCategory").html("<option value='personage'>请选择</option>");
        		gettabledata(); //表格
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
