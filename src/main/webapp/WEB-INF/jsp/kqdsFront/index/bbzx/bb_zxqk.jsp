<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	
	String seqIds = request.getParameter("seqIds");
	if(seqIds == null){
		seqIds = "";
	}
	String table = request.getParameter("table");
	if(table == null){
		table = "";
	}
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
						<label>时间</label>
						<div class="kv-v">
							<div class="unitsDate" style="width:300px;">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</div>
						</div>
					</div>
                </div>
            </div>
        </div> 
    <div class="tableHd" id="titleHtml">活动统计</div>
    <div class="tableBox" id="divkdxm">
        <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="450"></table>
    </div>
    <div id="buttonBar"> 
        <table style="width:95%;text-align: left;"> 
    	</table> 
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
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var seqIds = "<%=seqIds%>";
var table = "<%=table%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = '';
var Fieldname = '';
if(table == "KQDS_ACTIVITY_RECORD"){
	pageurl = '<%=contextPath%>/KQDS_Activity_RecordAct/getWdOrdertj.act';
	$("#titleHtml").val('活动统计');
	Fieldname = '活动名称';
}else if(table == "KQDS_DEINDUSTRYRECORD"){
	pageurl = '<%=contextPath%>/Kqds_DeindustryRecordAct/getWdOrdertj.act';
	$("#titleHtml").val('异业统计');
	Fieldname = '企业名称';
}else if(table == "KQDS_MEDIA_RECORD"){
	pageurl = '<%=contextPath%>/Kqds_MediaRecordAct/getWdOrdertj.act';
	$("#titleHtml").val('媒体统计');
	Fieldname = '媒体名称';
}
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['hdjl_zxqk_scbb'];
var func = ['exportTable'];
$(function() {
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
});
function queryParams(params) {
    var temp = {
    	seqIds: seqIds,
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val()
    };
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
});
//导出
function exportTable() {
	  var li = $(".dropdown-menu").children("li").last();
	  li.trigger("click");
}
function setHeight(){
	$("#dykdxm").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".searchWrap").outerHeight()+10
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
        	 var tableList = data;
        	 var hj={};
        	 var ldxj = 0,yyxj = 0,yyl = 0,dyrsxj = 0,dzl = 0,skjexj = 0,cjrsxj = 0,cjl = 0,rjxf = 0,outmoneyxj = 0;
             for (var i = 0; i < tableList.length; i++) {
            	 ldxj += Number(tableList[i].ldnums);
            	 yyxj += Number(tableList[i].yynums);
            	 dyrsxj += Number(tableList[i].yysmnums);
            	 skjexj += Number(tableList[i].skje);
            	 cjrsxj += Number(tableList[i].cjnums);
            	 outmoneyxj += Number(tableList[i].outmoney);
             }
             hj.createtime = "合计";
             hj.username = "";
             hj.ldnums = ldxj;
             hj.yynums = yyxj;
             hj.yysmnums = dyrsxj;
             hj.skje = skjexj.toFixed(2);
             hj.cjnums = cjrsxj;
             hj.outmoney = outmoneyxj;
             //预约率 = 预约人数/录单量
             if(Number(ldxj)==0){
            	 hj.yyl = "0.0%";
             }else{
            	 hj.yyl = Number(yyxj*100/ldxj).toFixed(2)+"%";
             }
             //到诊率 = 到院人数/录单量
             if(Number(ldxj)==0){
            	 hj.dzl = "0.0%";
             }else{
            	 hj.dzl = Number(dyrsxj*100/ldxj).toFixed(2)+"%";
             }
             //成交率 = 成交人数/到院人数
             if(Number(dyrsxj)==0){
            	 hj.cjl = "0.0%";
             }else{
            	 hj.cjl = Number(cjrsxj*100/dyrsxj).toFixed(2)+"%";
             }
             //人均消费 = 收款金额/到院人数
             if(Number(dyrsxj)==0){
            	 hj.rjxf = "0.0";
             }else{
            	 hj.rjxf = Number(skjexj/dyrsxj).toFixed(2);
             }
             //投入产出比 = 投入金额/收款金额
             if(Number(outmoneyxj)==0){
            	 hj.trccb = "1:0.00";
             }else{
            	 hj.trccb = "1:"+Number(skjexj/outmoneyxj).toFixed(2);
             }
             data.push(hj);
             $("#dykdxm").bootstrapTable("load",data);
             setHeight();
        },
        columns: [
        {
            title: '时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span class="">' + value + '</span>';
            }
        },
        {
            title: Fieldname,
            field: 'username',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span class="">' + value + '</span>';
            }
        },
        {
            title: '录单量',
            field: 'ldnums',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '预约人数',
            field: 'yynums',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '预约率',
            field: 'yyl',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '到院人数',
            field: 'yysmnums',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '到诊率',
            field: 'dzl',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '成交人数',
            field: 'cjnums',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '成交率',
            field: 'cjl',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '收款金额',
            field: 'skje',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '人均消费',
            field: 'rjxf',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '投入金额',
            field: 'outmoney',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '投入产出比',
            field: 'trccb',
            align: 'center',
            valign: 'middle'
        }
       
        ]
    });
}
</script>
</html>
