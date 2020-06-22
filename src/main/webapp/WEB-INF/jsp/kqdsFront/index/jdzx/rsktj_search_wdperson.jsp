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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style>
	html{
		height:100%;
	}
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
        width: 100px;   
    }  
	.searchSelect>.btn { 
	   	width: 100px; 
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
	.searchWrap{
	 	overflow: visible;
	 	height: 75px;
	}
	.formBox{
		overflow: visible;
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
					<div class="kv" id ="askDiv">
						<label>建档人</label>
						<div class="kv-v">
<!-- 							 <select  id="wdperson" name="wdperson"> -->
<!-- 							 </select> -->
							 <select  class="searchSelect" id="wdperson" name="wdperson" data-live-search="true" title="请选择">
							 </select>
						</div>
					</div>
					<div class="kv">
						<label>消费门诊</label>
						<div class="kv-v">
							 <select id="organization" name="organization"></select>
						</div>
					</div>
                </div>
            </div>
        </div> 
    <!-- <div class="tableHd">收款详细</div> -->
    <div class="tableBox" id="divkdxm">
        <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="450"></table>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var personPriv = "<%=person.getSeqId()%>";
var userPriv = "<%=person.getUserPriv()%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/getRsktj4WD.act';
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['rskcx_scbb'];
var func = ['exportTable'];
var isyx = "";
$(function() {
	initHosSelectListNoCheck('organization'); // 连锁门诊下拉框	
	isyx = parent.isyx;
	//咨询 下拉列表
	if(isyx == "1"){ // 1 是营销
		initPersonSelectByDeptTypeAndVisual("wdperson","<%=ConstUtil.DEPT_TYPE_3%>");
	}else{ // 2 是网电
		initPersonSelectByDeptTypeAndVisual("wdperson","<%=ConstUtil.DEPT_TYPE_2%>");
	}
	
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
    $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
});
function queryParams(params) {
    var temp = {
    	organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val()
    };
    if(isyx == "1"){ // 1 是营销 
    	temp.yxperson = $('#wdperson').val();
	}else{ // 2 是网电
		temp.wdperson = $('#wdperson').val();
	}
    return temp;
}
$('#query').on('click',
function() {
    $('#dykdxm').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
	var rgvalue = '<%=ChainUtil.getCurrentOrganization(request)%>';
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val(rgvalue);
    $(".searchSelect li.selected").empty();//清空1
    $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
});
//导出
function exportTable() {
    var li = $(".dropdown-menu").children("li").last();
    li.trigger("click");
}
function setHeight(){
	$("#dykdxm").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".searchWrap").outerHeight()+35
	});
}
function OrderDetail() {
    $("#dykdxm").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        showExport: true,
        //是否显示导出
        exportDataType: "basic",
        mergeCells: {
            index: 4,
            field: 'xh',
            colspan: 0,
            rowspan: 3
        },
        onLoadSuccess: function(data) { //加载成功时执行
            $(".birthDate").datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                minView: 2,
                autoclose: true,
                //选中之后自动隐藏日期选择框   
                pickerPosition: "bottom-right"
            });
            setHeight();
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
    });
}
</script>
</html>
