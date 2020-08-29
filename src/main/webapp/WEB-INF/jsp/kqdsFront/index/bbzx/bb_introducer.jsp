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
<title></title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>


<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highstock.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<!-- jquery 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>

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
	table tr{
		height:29.5px !important;
	}
	.fixed-table-container {
	    height: 100% !important;
	}
	.fixed-table-body{
		height: 100% !important;
	}
	.tableBox2 .bootstrap-table{
		height: 310px;
	}
	.tableBox .bootstrap-table,.tableBox1 .bootstrap-table{
		height: 655px;
	}
	input[type="text"].queryInp2 {
		width: 500px;
		float: left;
		color: #666;
	}
</style>
</head>

<body>
<div id="container">
	<!--查询条件-->
	<div class="searchWrap">
		<div class="cornerBox">查询条件 <a href="javascript:void(0);" class="kqdsSearchBtn hidden" id="scbb">生成报表</a></div>
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
						<label>上下级</label>
						<div class="kv-v">
							<select id="grade" name="grade">
								<option value="superior">上级</option>
								<option value="subordinate" selected>下级</option>
							</select>
						</div>
					</div>
				</div>
                <div class="kv">
               		<div class="kv">
						<label>建档日期</label>
						<div class="kv-v">
							<span class="unitsDate czInput">
								<input type="text" placeholder="开始日期" class="starttime" autocomplete="off"/> <span>到</span>
								<input type="text" placeholder="结束日期" class="endtime" autocomplete="off"/>
							</span>
						</div>
					</div>
                </div>
				<div class="kv">
					<div class="kv">
						<label>模糊查询</label>
						<div class="kv-v">
							<input class="queryInp2" type="text" id="searchValue" name="searchValue" placeholder="姓名/编号/手机号"/>
						</div>
					</div>
				</div>

				<div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
			     		<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
			    <%--<div class="kv" style="width:660px; margin:0 auto;text-align:left; ">--%>
					<%--<span id="topyxzl" style=" font-size:14px;color:#777;font-weight:bold;"></span>--%>
					<%--<br>--%>
					<%--<span id="topyxzl1" style="font-size:14px;color:#777;font-weight:bold;"></span>--%>
				<%--</div> --%>
            </div>
        </div> 
        <div class="tableBox" style="float: left;width: 30%;">
           <table id="BaseDatatable" class="table-striped table-condensed table-bordered"></table>
	    </div>
	    <div class="tableBox1" style="float: left;width: 30%;">
	           <table id="table" class="table-striped table-condensed table-bordered"></table>
	    </div>
 
	    <!--右侧信息浏览-->
		<div style="float: left;width: 40%;">
			<div id="container">
			   <!--  <div class="tableHd" id="topyxzl">费用详情</div>-->
			    <div class="tableBox2" id="tableFather">
			        <table id="table1" class="table-striped table-condensed table-bordered" data-height="180"></table>
			    </div>
				<div class="titleDiv">
					<span class="title" style="font-size:14px;color:#777;font-weight:bold;">详细项目清单</span>
				</div>
			    <!-- <div class="tableHd">详细项目清单</div> -->
			    <div class="tableBox2" id="divkdxm">
			        <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="160"></table>
			    </div>
				</div>
				<div class="titleDiv">
					<span class="title"  style="font-size:14px;color:#777;font-weight:bold;">接诊</span>
				</div>
				<div class="tableBox2" id="receptionDiv">
					<table id="reception" class="table-striped table-condensed table-bordered" data-height="155"></table>
				</div>
			</div>
		</div>
</div> 
</body>
<script type="text/javascript" >
var contextPath = "<%=contextPath%>";
var pageurl = contextPath+'/KQDS_UserDocumentAct/introducerStatistics.act';//查询介绍人
var url =contextPath+ "/KQDS_UserDocumentAct/introducerStatisticsDetail.act";//查询上下级
var myColumns;
var menuid = "<%=menuid%>";
var usercode;//这个对象，是指页面左侧列表中选择的患者编号
var onclickrowOobj2 = ""; // 这个对象就是当前页面的cost order费用单对象
var defaultindex = 0;
var costno="";
var canEditCost = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag9_canEditCost, request)%>"; //是否具备修改他人费用单的权限 0不可以 1 可以
$(function(){
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	var starttime = getNowFormatDate();
	// 时间选择
	$(".czInput input").datetimepicker({
		language:  'zh-CN',
		minView:2,
	    autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
    $(".starttime").val(starttime);
    $(".endtime").val(starttime);
	gettabledata();
	getlist();
    //getButtonAllCurPage(menuid);
    getCostList();
    OrderDetail("null");
    //子页面高度传给父页面
    adjustTable();
    $(window).resize(adjustTable);
    $("#dykdxm  tbody").html("");
    receptionList();
});
$("#scbb").click(function(){
    exportTable();
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
//jquery 导出excel 
function exportTable(){
    if(isClick) {
        isClick = false;
        // console.log("生成报表")
        loadedData = [];
        nowpage = 0;
        var fieldArr = [];
        var fieldnameArr = [];
        $('#BaseDatatable thead tr th').each(function() {
            var field = $(this).attr("data-field");
            if (field != "") {
                //fieldArr.push(field); //获取字段
                fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
            }
        });
        var param = JsontoUrldata(queryParamsB());
        var url = pageurl + "?flag=exportTable&fieldArr=[\"0\",\"usercode\",\"username\",\"askpersonname\",\"doctorname\",\"repairdoctorname\",\"yjjmoney\",\"ssmoney\",\"totalpay\"]" + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
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
                a.download = "患者趋势";
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
/* 查询按钮 */
$("#query").click(function(){
    myColumns=$('#BaseDatatable').bootstrapTable('getOptions').columns[0];
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
	searchLabel();
});
function queryParamsB(params) {
	var temp = {
		organization:$("#organization").val(),
		starttime:$(".starttime").val(),
		endtime:$(".endtime").val(),
        grade:$("#grade").val(),
        searchValue: $('#searchValue').val()
	};
    return temp;
}
function queryParams(params) {
	var temp = {
		limit: params.limit,   //页面大小
		offset: params.offset, //页码
		pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
		starttime:$(".starttime").val(),
		endtime:$(".endtime").val(),
		organization:$("#organization").val(),
        grade:$("#grade").val(),
        searchValue: $('#searchValue').val()
	};
    return temp;
}
function queryParamsDetail(params) {
    var temp = {
        limit: params.limit,   //页面大小
        offset: params.offset, //页码
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        usercode:usercode,
        grade:$("#grade").val()
    };
    return temp;
}
//表格查询刷新
function searchLabel() {
	 $('#BaseDatatable').bootstrapTable('refreshOptions', {
	        'url': pageurl,
	        'columns':myColumns
	    });
}
$('#clean').on('click',
		function() {
			$(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
		    var rgvalue = '<%=ChainUtil.getCurrentOrganization(request)%>';
		    $("#organization").val(rgvalue);
            $("#grade").val("subordinate");
            var starttime = getNowFormatDate();
            $(".starttime").val(starttime);
            $(".endtime").val(starttime);
		});
/* 表格数据初始化 */
function gettabledata(){
	$('#BaseDatatable').bootstrapTable({
        url: pageurl,
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 20,
        pageList : [10, 20, 30],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        /* search: true, */
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) {
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	setWidth();
            setHeight();
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
   		    title :'患者编号',
   		    field: 'usercode',
   		    align: 'center',
   		    formatter: function (value, row, index) {
                 return "<span class='name'>" + value + "</span>";
   		    }
   		},{
   		    title : '患者姓名',
   		    field: 'username',
   		    align: 'center',
   		    formatter: function (value, row, index) {
                 return "<span class='name'>" + value + "</span>";
   		    }
   		},
        {
            title: '手机号',
            field: 'phonenumber1',
            align: 'center',
            formatter: function(value, row, index) {
                if (value){
                    return "<span class='name'>" + value + "</span>";
                } else{
                    return "<span class='name'></span>";
                }
            }
        },{
            title: '性别',
            field: 'sex',
            align: 'center',
            formatter: function(value, row, index) {
                if (value){
                    return "<span class='name'>" + value + "</span>";
                } else{
                    return "<span class='name'></span>";
                }
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            formatter: function(value, row, index) {
                if (value){
                    return "<span class='name'>" + value + "</span>";
                } else{
                    return "<span class='name'></span>";
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('#BaseDatatable .success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#BaseDatatable').find('tr.success').data('index'); //获得选中的行
        usercode = $('#BaseDatatable').bootstrapTable('getData')[index].usercode;
        search();
    });
}
function search() {
    $('#table').bootstrapTable('refreshOptions', {
        'url': url
    });
}
function getlist() {
	/*wl----首次加载时 计算table高度————————————结束  */
    $("#table").bootstrapTable({
        url: url,
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParamsDetail,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 20,
        pageList : [10, 20, 30],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        /* search: true, */
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页
        onLoadSuccess: function(data) { //加载成功时执行
        	/*表格载入时，设置表头的宽度 */
            setWidth();
            setHeight();
            setTableHeaderWidth(".tableBox1");
        },
        columns: [{
            title : '序号',
            align: "center",
            width: 40,
            formatter: function (value, row, index) {
                var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        },
            {
                title :'患者编号',
                field: 'usercode',
                align: 'center',
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },{
                title : '患者姓名',
                field: 'username',
                align: 'center',
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '手机号',
                field: 'phonenumber1',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value){
                        return "<span class='name'>" + value + "</span>";
                    } else{
                        return "<span class='name'></span>";
                    }
                }
            },{
                title: '性别',
                field: 'sex',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value){
                        return "<span class='name'>" + value + "</span>";
                    } else{
                        return "<span class='name'></span>";
                    }
                }
            },
            {
                title: '年龄',
                field: 'age',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value){
                        return "<span class='name'>" + value + "</span>";
                    } else{
                        return "<span class='name'></span>";
                    }
                }
            }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('#table .success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        var usercode = $('#table').bootstrapTable('getData')[index].usercode;
        getCostList(usercode);
        // 接诊
        receptionList(usercode);
    });
}
function getCostList(usercode) {
    $("#dykdxm  tbody").html("");
    $("#reception  tbody").html("");
    var url =contextPath+ "/KQDS_CostOrderAct/getByRegnoNopage.act?usercode="+usercode+"&access=1";//不需要可见人员过滤，查询全部费用
    /* 根据当前页面 计算出table需要的高度 */
    /*wl----首次加载时 计算table高 */
    var tableHeight=getTableHeight();/* 计算table需要的高度 */
    $("#tableFather").html("<table id='table1' class='table-striped table-condensed table-bordered'></table>");
    /*wl----首次加载时 计算table高度————————————结束  */
    $("#table1").bootstrapTable({
        url: url,
        dataType: "json",
        onLoadSuccess: function(data) { //加载成功时执行
            //alert(JSON.stringify(data));
            //console.log(data);
            if(defaultindex==0){
                defaultindex = 1;
            }
            $("#table1").find("tr:eq("+defaultindex+") td:eq(0)").click();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth("#tableFather");
            <%--var ys=0,qf=0,jf=0;--%>
            <%--for(var i=0;i<data.length;i++){--%>
                <%--if(data[i].status == 2 && data[i].isyjjitem == 0){//预交金单不算--%>
                    <%--ys += Number(data[i].shouldmoney);--%>
                    <%--qf += Number(data[i].y2);--%>
                    <%--jf += Number(data[i].actualmoney);--%>
                <%--}--%>
            <%--}--%>

            <%--var topyxzl="应收：" + ys.toFixed(2) + " &nbsp;&nbsp;缴费：" + jf.toFixed(2) + "&nbsp;&nbsp;<i style='font-size:14px;font-style:normal;color:red;font-family: Microsoft YaHei;'> 欠费：" + qf.toFixed(2)+"</i>";--%>
            <%--$("#topyxzl").html(topyxzl);--%>
            <%--if(jzcx_invoice_value_Flag){--%>
                <%--$.ajax({--%>
                    <%--url : '<%=contextPath%>/HUDH_InvoiceDetailAct/selectInvoiceDetailValueByUsercode.act',--%>
                    <%--type : "POST",--%>
                    <%--dataType : "json",--%>
                    <%--data : {--%>
                        <%--usercode:onclickrowOobj.usercode--%>
                    <%--},--%>
                    <%--success : function(data) {--%>
                        <%--var value=(Number(data.invoiceValue)-Number(data.dishonourInvoiceValue)).toFixed(2);--%>
                        <%--topyxzl="应收：" + ys.toFixed(2) + " &nbsp;&nbsp;缴费：" + jf.toFixed(2) + "&nbsp;&nbsp;<i style='font-size:14px;font-style:normal;color:red;font-family: Microsoft YaHei;'> 欠费：" + qf.toFixed(2)+"&nbsp;&nbsp;总开票："+data.invoiceValue+"&nbsp;&nbsp;开票："+value+"&nbsp;&nbsp;作废："+data.dishonourInvoiceValue+"</i>";--%>
                        <%--$("#topyxzl").html(topyxzl);--%>
                    <%--}--%>
                <%--});--%>
            <%--}--%>
        },
        rowStyle: function (row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.costno==costno) {
                strclass = 'success';//欠费
                defaultindex = index + 1;
            }
            if (Number(row.actualmoney) <0 || row.actualmoney.indexOf("-")>=0) {
                strclass = 'danger';//欠费
            }else {

            }
            return { classes: strclass };
        },
        columns: [{
            title: '病人编号',
            field: 'usercode',
            align: 'center',

            visible: false,
            switchable: false,
            formatter:function(value){
                return '<span>'+value+'</span>';
            }
        },
            {
                title: '开单时间',
                field: 'createtime',
                align: 'center',
                sortable: true,
                formatter: function(value, row, index) {
                    html = '<span>' + value.substring(0) + '</span>';
                    return html;
                }
            },
            {
                title: '姓名',
                field: 'username',
                align: 'center',

                sortable: true,
                formatter: function(value, row, index) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            },
            {
                title: '费用类型',
                field: 'type',
                align: 'center',

                sortable: true,
                formatter: function(value, row, index) {
                    if (row.status == "0") {
                        return '<span style="color:red">费用计划</span>';
                    } else {
                        return '<span style="color:green">确认划价</span>';
                    }
                }
            },
            {
                title: '成交情况',
                field: 'cjstatus',
                align: 'center',

                sortable: true,
                formatter: function(value, row, index) {
                    if (value == "1") {
                        return '<span style="color:green">已成交</span>';
                    } else {
                        return '<span style="color:red">未成交</span>';
                    }
                }
            },
            {
                title: '状态',
                field: 'status',
                align: 'center',

                sortable: true,
                formatter: function(value, row, index) {
                    if (value >= 2) {
                        return '<span style="color:green">已结账</span>';
                    } else if (value == 1) {
                        return '<span style="color:red">已开单</span>';
                    } else {
                        return '<span style="color:red">未成交</span>';
                    }
                }
            },
            {
                title: '就诊医生',
                field: 'doctorname',
                align: 'center',

                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },
            {
                title: '修复医生',
                field: 'repairdoctorname',
                align: 'center',

                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },
            {
                title: '合计',
                field: 'totalcost',
                align: 'center',

                sortable: true,
                formatter:function(value){
                    return "<span class='money'>"+value+"</span>";
                }
            },
            {
                title: '免除金额',
                field: 'voidmoney',
                align: 'center',

                sortable: true,
                formatter:function(value){
                    return "<span class='money'>"+value+"</span>";
                }
            },
            {
                title: '应收金额',
                field: 'shouldmoney',
                align: 'center',

                sortable: true,
                formatter:function(value){
                    return "<span class='money'>"+value+"</span>";
                }
            },
            {
                title: '欠费金额',
                field: 'y2',
                align: 'center',

                sortable: true,
                formatter: function(value, row, index) {
                    if (Number(value) != 0) {
                        return '<span class="money" style="color:red">' + value + '</span>';
                    } else {
                        return '<span>'+value+'</span>';
                    }
                }
            },
            {
                title: '缴费金额',
                field: 'actualmoney',
                align: 'center',

                sortable: true,
                formatter:function(value, row, index){
                    return "<span class='money'>"+(Number(row.actualmoney)-Number(row.payyjj)).toFixed(3)+"</span>";
                }
            },
            {
                title: '预交金使用',
                field: 'payyjj',
                align: 'center',

                sortable: true,
                formatter:function(value){
                    return "<span class='money'>"+value+"</span>";
                }
            },
            {
                title: 'seqId',
                field: 'seqId',
                align: 'center',

                visible: false,
                switchable: false
            },
            {
                title: '开单人',
                field: 'createusername',
                align: 'center',
                formatter:function(value){
                    return '<span>'+value+'<span>';
                }
            },
            {
                title: '类型',
                field: 'isback',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value == 1) {
                        return '<span style="color:red">退单</span>';
                    }else{
                        return '<span>-</span>'
                    }
                }
            }]
    }).on('click-row.bs.table',
        function(e, row, element) {
            $('.success').removeClass('success'); //去除之前选中的行的，选中样式
            $(element).addClass('success'); //添加当前选中的 success样式用于区别
            var index = $('#table1').find('tr.success').data('index'); //获得选中的行
            onclickrowOobj2 = $('#table1').bootstrapTable('getData')[index];
			OrderDetail(onclickrowOobj2.costno); //"\'"+onclickrowOobj2.costno+"\'"
        });
}
function OrderDetail(costno) {
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno;
    /* 根据当前页面 计算出table需要的高度 */
	/*wl----首次加载时 计算table高 */
	 var tableHeight=getTableHeight();/* 计算table需要的高度 */
	$("#divkdxm").html("<table id='dykdxm' class='table-striped table-condensed table-bordered'></table>");
	
	/*wl----首次加载时 计算table高度————————————结束  */
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(data){
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth("#divkdxm");
        	if(data.length>0){
        		 for (var i = 0; i < data.length; i++) {
        	            var tabledata = data[i];
        	            if(tabledata.isyjjitem == 1){
        	            	static_isyjjitem = 1;
        	            }
        	     }
        	}
        },
        columns: [
        {
		    title: '治疗状态',
		    field: 'kaifa',
		    valign: 'middle',
		    align: 'center',
		    formatter: function(value, row, index) {
		    	var buttonstr = "<span>未治疗</span>";
		    	if(onclickrowOobj2.status == 2){
		    		if(canEditCost == '1'){
		    			if(row.kaifa == '已治疗'){
                    		buttonstr = '<select style="width:65px;" onchange="changekaifa(\'' + row.seqId + '\',this,\'kaifa\')">'+
      	                    '<option value="未治疗">未治疗</option>'+
      	                    '<option value="已治疗" selected>已治疗</option>'+
      	                    '</select>';
                    	}else{
                    		buttonstr = '<select style="width:65px;" onchange="changekaifa(\'' + row.seqId + '\',this,\'kaifa\')">'+
    	                    '<option value="未治疗" selected>未治疗</option>'+
    	                    '<option value="已治疗">已治疗</option>'+
    	                    '</select>';
                    	}
		    		}else if(row.doctor == personId || isToday(row.zltime)){
		    			if(row.kaifa != '已治疗'){
                    		buttonstr = '<select style="width:65px;" onchange="changekaifa(\'' + row.seqId + '\',this,\'kaifa\')">'+
      	                    '<option value="未治疗" selected>未治疗</option>'+
      	                    '<option value="已治疗">已治疗</option>'+
      	                    '</select>';
                    	}else{
                    		if(isToday(row.zltime)){
                    			buttonstr = '<select style="width:65px;" onchange="changekaifa(\'' + row.seqId + '\',this,\'kaifa\')">'+
          	                    '<option value="未治疗">未治疗</option>'+
          	                    '<option value="已治疗" selected>已治疗</option>'+
          	                    '</select>';
                    		}else{
                    			buttonstr = '<span>已治疗</span>';
                    		}
                    	}
		    		}else{
		    			if(row.kaifa == '已治疗'){
	                    	buttonstr = '<span>已治疗</span>';
	                    }
		    		}
		    	}else{
		    		if(row.kaifa == '已治疗'){
                    	buttonstr = '<span>已治疗</span>';
                    }
		    	}
		        return buttonstr;
		    }
		},{
            title: '操作时间',
            field: 'zltime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	var buttonstr = '<span>'+value+'</span>';
		    	if(onclickrowOobj2.status == 2){
		    		if(canEditCost == '1'){
                    	buttonstr = '<input type="text" value="'+value+'" style="width:135px; text-align:center;" class="unitsDate" onchange="changekaifa(\'' + row.seqId + '\',this,\'zltime\')" >';
		    		}else if(row.doctor == personId){
		    			if(!value){
			    			buttonstr = '<input type="text"  style="width:135px; text-align:center;" class="unitsDate" onchange="changekaifa(\'' + row.seqId + '\',this,\'zltime\')" >';
		    			}else{
		    				if(isToday(row.zltime)){
				    			buttonstr = '<input type="text" value="'+value+'" style="width:135px; text-align:center;" class="unitsDate" onchange="changekaifa(\'' + row.seqId + '\',this,\'zltime\')" >';
	                		}
		    			}
		    		}
		    	}
		        return buttonstr;
            }
        },{
            title: '项目编号',
            field: 'itemno',
            align: 'center',
            visible: false,
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var html = '<span style="float:left;position:relative;top:-1px;" title="' + value + '">';
                if (row.istk == 1) {
                    html += '<span class="label label-info">退款</span>';
                } else {
                    if (Number(row.y2) < 0) {
                        html += '<span class="label label-warning">还款</span>';
                    } else if (Number(row.y2) > 0 && onclickrowOobj2.status == 2) {
                        html += '<span class="label label-danger">欠款</span>';
                    } else if (Number(row.y2) == 0 && row.isqfreal == 1) {
                        html += '<span class="label label-warning">还款</span>';
                    }
                }
                html += value + '</span>';
                return html;
            }
        },
        {
	        title: '就诊医生',
	        field: 'doctorname',
	        align: 'center',
	        
	        formatter: function(value, row, index) {
	        	 if (value) {
		                return "<span class='name'>"+value+"</span>";
		         }else{
		        	 return "<span>-</span>";
		         }
	        }
	    },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>'+"￥" + row.unitprice +'</span>';
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '折扣%',
            field: 'discount',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + row.subtotal+'</span>';
            }
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + row.voidmoney+'</span>';
            }
        },
        {title: '应收金额',field: 'ys',align: 'center',sortable: true,
			formatter:function(value,row,index){
				var ys = Number(row.paymoney)+Number(row.arrearmoney);
				if(Number(row.y2)<= 0  &&  row.qfbh!="" ){//还款
					ys = "0.00";
				}
				return '<span class="money">￥'+ys+'</span>' ;
			}
		},
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + row.y2+'</span>';
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + (Number(row.paymoney)-Number(row.payyjj))+'</span>';
            }
        },
        {
            title: '预交金使用',
            field: 'payyjj',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + (row.payyjj)+'</span>';
            }
        }
        ]
    });
}

function receptionList(usercode) {
    var detailurl = '<%=contextPath%>/KQDS_REGAct/selectJzByUsercode.act?usercode=' + usercode;
    /*wl----首次加载时 计算table高 */
    var tableHeight=getTableHeight();/* 计算table需要的高度 */
    $("#receptionDiv").html("<table id='reception' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");

    /*wl----首次加载时 计算table高度————————————结束  */
    $("#reception").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(data){
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth("#receptionDiv");
        },
        columns: [
            {
                title: '患者编号',
                field: 'usercode',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },
            {
                title: '患者姓名',
                field: 'username',
                align: 'center',

                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },
            {
                title: '医生',
                field: 'doctorname',
                align: 'center',
                formatter:function(value){
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },
            {
                title: '就诊科室',
                field: 'regdept',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },{
                title: '第一咨询',
                field: 'firstaskpersonname',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },{
                title: '咨询',
                field: 'askpersonname',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },{
                title: '客服',
                field: 'kefuname',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },{
                title: '成交',
                field: 'cjstatus',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value == "1") {
                        return "<span class='name'>成交</span>";
                    }else{
                        return "<span class='name'>未成交</span>";
                    }
                }
            },{
                title: '就诊分类',
                field: 'recesortname',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            },{
                title: '挂号分类',
                field: 'regsortname',
                align: 'center',
                formatter: function(value, row, index) {
                    if (value != "" && value != null) {
                        return "<span class='name'>"+value+"</span>";
                    }else{
                        return "<span>-</span>";
                    }
                }
            }]
    });
}

function getTableHeight(){
	var tableHeight=($(window).height()-$(".titleDiv").outerHeight()*3-20)/3;
	return Math.floor(tableHeight);
}
//调整表格高度
function adjustTable() {
    var height = $('body').height();
    //window.parent.setparentHeight(height);
    var tableHeight=getTableHeight();
    $(".fixed-table-container").outerHeight(tableHeight);
    
    /*表格载入时，设置表头的宽度 */
    setTableHeaderWidth("#tableFather");
    /*表格载入时，设置表头的宽度 */
    setTableHeaderWidth("#divkdxm");

    /*表格载入时，设置表头的宽度 */
    setTableHeaderWidth("#receptionDiv");
}
function getButtonPower() {
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "scbb") {
            $("#scbb").removeClass("hidden");
        }
    }
}
</script>
</html>
