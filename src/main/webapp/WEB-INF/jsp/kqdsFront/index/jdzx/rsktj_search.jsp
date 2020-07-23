<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	//收费角色
	String sfpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_SHOUFEI_SEQID); // 获取收费角色
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>日收款查询</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
a:hover{
	text-decoration:none;
}
</style>
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
						<label>收款日期</label>
						<div class="kv-v">
							<div class="unitsDate" style="width:300px;">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</div>
						</div>
					</div>
					<div class="kv">
						<label>消费门诊</label>
						<div class="kv-v">
							 <select id="organization" name="organization"></select>
						</div>
					</div>
					 <div class="kv" id ="skrDiv">
						<label>收款人</label>
						<div class="kv-v">
							 <select id="skr" name="skr" class="priv" tag="<%=sfpriv%>"><!--  tag 存放角色对应的值 -->
							 </select>
						</div>
					</div>
                </div>
            </div>
        </div> 
    <div class="tableHd">收款详细</div>
    <div class="tableBox" id="divkdxm" style="overflow: hidden;border-radius:8px;border-top-left-radius: 6px;border-top-right-radius: 6px;border-left: 1px solid #ddd;border-right: 1px solid #ddd;border-bottom:1px solid #ddd;">
        <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="450"></table>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var personPriv = "<%=person.getSeqId()%>";
var userPriv = "<%=person.getUserPriv()%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/getRsktj.act';
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['rskcx_scbb'];
var func = ['exportTable'];
var isClick = true;
$(function() {
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
	// 根据角色和可见人员，初始化人员下拉框
	initPersonSelectByRoleAndVisual($("#organization").val());
	
	$('#organization').change(function() {
		initPersonSelectByRoleAndVisual($("#organization").val());
	});
	
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    OrderDetail();
    //隐藏bootstrap导出按钮
    $(".columns").hide();
    /* $(window).resize(function(){
       	$(".fixed-table-container").height($(".fixed-table-container").height()+30+"px");
    }); */
});

function queryParams(params) {
    var temp = {
    	organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        skr: $('#skr').val()
    };
    return temp;
}
$('#query').on('click',
function() {
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    $('#dykdxm').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
	var rgvalue = '<%=ChainUtil.getCurrentOrganization(request)%>';
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val(rgvalue);
});


var loadIndex='';
function download() {
	layer.msg('数据导出中，请等待');
	//loadIndex = layer.load(0, {shade: false});
	isClick = false;
}
function disload() {
	layer.close(loadIndex);
	layer.msg('数据导出完毕');
	isClick = true;
}
function exportTable(){
	/*$("#dykdxm").table2excel({
		exclude: ".noExl",//带noExlclass的行不会被输出到excel中
		name: "Excel Document Name",
		filename: "日收款查询（财务）",
		exclude_img: true,
		exclude_links: true,
		exclude_inputs: true
	});*/
	if(isClick) {
		isClick = false;
		// console.log("生成报表")
		var fieldArr = [];
		var fieldnameArr = [];
		$('#dykdxm thead tr th').each(function () {
			var field = $(this).attr("data-field");
			if (field != "") {
				fieldArr.push(field);//获取字段
				fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
			}
		});
		var param = JsontoUrldata(queryParams());
		//location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
		var url = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
		download();
		var xhr = new XMLHttpRequest();
		xhr.open('GET', url, true);    // 也可用POST方式
		xhr.responseType = "blob";
		xhr.onload = function () {
			if (this.status === 200) {
				var blob = this.response;
				// if (navigator.msSaveBlob == null) {
				var a = document.createElement('a');
				//var headerName = xhr.getResponseHeader("Content-disposition");
				//var fileName = decodeURIComponent(headerName).substring(20);
				a.download = "费用查询";
				a.href = URL.createObjectURL(blob);
				$("body").append(a);    // 修复firefox中无法触发click
				a.click();
				URL.revokeObjectURL(a.href);
				$(a).remove();
				// } else {
				//     navigator.msSaveBlob(blob, "信息查询");
				// }
			}
			disload();
		};
		xhr.send();
	}
}

function getTableHeight(){
	var tableHeight=$(window).outerHeight()-$(".tableHd").outerHeight()-$(".searchWrap").outerHeight();
	return tableHeight;
}

function OrderDetail() {
    $("#skr").val(personPriv);
    var tableHeight=getTableHeight()+40;
    $(".tableBox").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="'+tableHeight+'"></table>');
    $("#dykdxm").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        showExport: true,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        //是否显示导出
        exportDataType: "basic",
        mergeCells: {
            index: 4,
            field: 'xh',
            colspan: 0,
            rowspan: 3
        },
     	 //点击分页器触动方法
        onPageChange:function(){
        	//加了分页器之后高度自适应
           	//$(".fixed-table-container").height($(".fixed-table-container").height()+30+"px");
        },
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
//         	console.log(JSON.stringify(data)+"---------data");
            $(".birthDate").datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                minView: 2,
                autoclose: true,
                //选中之后自动隐藏日期选择框   
                pickerPosition: "bottom-right"
            });
          //加了分页器之后高度自适应
           //$(".fixed-table-container").height($(".fixed-table-container").height()+30+"px");
        },
        rowStyle: function(row, index) {
            if (row.xm == "当日收款金额") { //低于下限
                return {
                    css: {
                        "font-weight": "bold",
                        "font-size": "15px"
                    }
                };
            }else{
            	 return {
                     css: {
                         "color": "black"
                     }
                 };
            }
        },
        columns: [{
            title: '序号',
            field: 'xh',
            align: 'center',
            valign: 'middle',
            formatter:function(value){
            	return "<span>"+value+"</span>"
            }
        },
        {
            title: '项目',
            field: 'xm',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span style="float:left; text-align:right;">' + value + '</span>';
            }
        },
        {
            title: '退费前实收金额',
            field: 'sfje',
            align: 'center',
            valign: 'middle',
            formatter:function(value){
            	return "<span style='width:130px;'>"+value+"</span>"
            }
        },
        {
            title: '退费金额',
            field: 'tkje',
            align: 'center',
            valign: 'middle',
            formatter:function(value){
            	return "<span style='width:130px;'>"+value+"</span>"
            }
        },
        {
            title: '退费后实收金额',
            field: 'ssje',
            align: 'center',
            valign: 'middle',
            formatter:function(value){
            	return "<span style='width:130px;'>"+value+"</span>"
            }
        },
        {
            title: '赠送金额',
            field: 'zsje',
            align: 'center',
            valign: 'middle',
            formatter:function(value){
            	return "<span style='width:130px;'>"+value+"</span>"
            }
        },
        {
            title: '缴费小计',
            field: 'jfxj',
            align: 'center',
            valign: 'middle',
            formatter:function(value){
            	return "<span style='width:130px;'>"+value+"</span>"
            }
        },
        ]
    }).on('click-row.bs.table',
        	function(e, row, element) {
   		$('.success').removeClass('success'); //去除之前选中的行的，选中样式
    	$(element).addClass('success'); //添加当前选中的 success样式用于区别
});
}
function getButtonPower() {
	var menubutton1 = "";
	for (var i = 0; i < listbutton.length; i++) {
		if (listbutton[i].qxName == "fycx_scbb") {
			menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable();">生成报表</a>';
		}
	}
	$("#clean").before(menubutton1);
	setHeight();
}
</script>
</html>
