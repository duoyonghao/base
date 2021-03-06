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
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
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
/* 		    line-height: 12px; */
		}
	.pull-left {
	    float: left !important;
	    color: #333;
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
	.searchWrap{
		overflow: visible;
 		height: 80px; 
	}
	.searchWrap .formBox {
		height:30px;
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
					<div class="kv">
						<label>消费门诊</label>
						<div class="kv-v">
							 <select id="organization" name="organization"></select>
						</div>
					</div>
					<div class="kv" id ="askDiv">
						<label>咨询</label>
						<div class="kv-v">
						<select id="askperson" name="askperson" tig="" class="searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 							 <select  id="askperson" name="askperson"> -->
<!-- 							 </select> -->
						</div>
					</div>
                </div>
            </div>
        </div> 
    <div class="tableHd">收款详细</div>
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
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
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
$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框	
	  //咨询 下拉列表
	 initPersonSelectByDeptTypeAndVisual("askperson","<%=ConstUtil.DEPT_TYPE_0%>");
	// 咨询根据下拉门诊改变
	$('#organization').change(function() {
		initPersonSelectByDeptTypeAndVisual("askperson","<%=ConstUtil.DEPT_TYPE_0%>",$("#organization").val());
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
    $('.searchSelect').selectpicker("refresh");//咨询部门初始化刷新--2019-10-26 licc
});
function queryParams(params) {
    var temp = {
        limit: params.limit,
        offset: params.offset,
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        askperson: $('#askperson').val()
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
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
	 $(".searchSelect li.selected").empty();
	 $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.26--licc	
});
//导出
function exportTable() {
// 	console.log(JSON.stringify(li)+"-----进来了");
    var li = $(".dropdown-menu").children("li").last();
    li.trigger("click");
}
function setHeight(){
	$("#dykdxm").bootstrapTable('resetView',
		{
			height:$(window).outerHeight()-$(".searchWrap").outerHeight()-$(".tableHd").outerHeight()+42
		}	
	);
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
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#query").text("查询");
			}
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
