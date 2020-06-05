<!-- wl整理 -->
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>

<title>接诊查询</title>
 <!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
/*工作量表格 ，单独写*/
	.kqds_table  td { 
		color: #666;
		padding: 2px 3px 3px 3px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		font-weight: normal;
		line-height: 28px;
	}
/*查询条件中的样式  */
.searchWrap .formBox>section>ul.conditions>li{
	padding: 3px 3px;
}
.searchWrap .formBox>section>ul.conditions>li>span{
	width:62px;
	text-align:right;
}
.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
.searchWrap .formBox>section>ul.conditions>li>select{
	width:94px;
}
@media screen and (max-width:1390px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:55px;
		text-align:right;
		font-size:11px;
		height:24px;
		line-height:24px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:82px;
		font-size:11px;
		padding:0 3px 0 5px;
		height:24px;
		line-height:24px;
	}
}
@media screen and (max-width:1100px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:51px;
		text-align:right;
		font-size:10px;
		height:22px;
		line-height:22px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:73px;
		font-size:10px;
		padding:0 3px 0 5px;
		height:22px;
		line-height:22px;
	}
}
.centerWrap .columnWrap .columnBd ul{
	overflow: visible;
}
.centerWrap .columnWrap .columnBd ul li{
	margin-left: 0px;
}
.centerWrap .columnWrap {
    margin-bottom: 0px;
}
.fixed-table-pagination .btn-group .dropdown-menu{
	min-width: auto;
}
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd">
                	<span class="title">发票信息查询</span>
                </div>
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height=""></table>
                    </div>
                </div>
            </div>
            <div id="gongzuol">
                <div class="columnBd">
                	<ul class="dataCountUl" id="dataCount">
	                		
                	</ul>
                </div>
            </div>
             
            <div class="searchWrap">
	                <!-- <div class="cornerBox">查询条件</div> -->
	                <div class="searchToggleBtnGroup">
	                	<span class="ptcx checked">
	                		通用查询
	                	</span>
	                </div>
	                <div class="formBox">
	                	<section>
				    		<ul class="conditions">
				    			<li>
				    				<span>所属门诊</span>
				    				 <select id="organization" name="organization"></select>
				    			</li>
				    			<li>
				    				<span>模糊查询</span>
				    				<input type="text" id="searchValue" class="searchInput" placeholder="姓名/编号" >
				    			</li>
				    			<li>
				    				<span>开票金额</span>
		    						<input type="text" id="invoicestartvalue" name="invoicestartvalue" placeholder="开始金额">
				    			</li>
				    			<li>
				    				<span style="width: 20px;">到</span>
				    				<input type="text" id="invoiceendvalue" name="invoiceendvalue" placeholder="结束金额" >
				    			</li>
				    			
				    			<li>
				    				<span>开票时间</span>
		    						<input type="text" id="invoicestarttime" name="invoicestarttime" placeholder="开始日期" readonly  class="birthDate">
				    			</li>
				    			<li>
				    				<span style="width: 20px;">到</span>
				    				<input type="text" id="invoiceendtime" name="invoiceendtime" placeholder="结束日期" readonly   class="birthDate">
				    			</li>
			    			</ul>
			    		</section>
			    		<div class="btnBar" id="bottomBarDdiv">
	                   		
			            </div>
	                </div>
	            </div>
        </div>
        
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var nowday;
//初始化表头，返回空的数据
var nullUrl = contextPath + '/UtilAct/intTableHeader.act';
var pageurl = contextPath + '/HUDH_InvoiceDetailAct/selectInvoiceDetailByTime.act';
$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
	var tmpOrganization = $("#organization").val();
   	nowday = getNowFormatDate();
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
     //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    //绑定两个时间选择框的chage时间
    $("#invoicestarttime,#invoiceendtime").change(function() {
        timeCompartAndFz("invoicestarttime", "invoiceendtime");
    });
    //4、表格初始化
    initTable(pageurl);
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });

});

//带参数刷新表格
function refreshTable(){
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function initTable(requrl) {
    $('#table').bootstrapTable({
        url: requrl,
        queryParams: queryParamsB,
        dataType: "json",
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        //clickToSelect: false,
        singleSelect: true,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        paginationShowPageGo: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	var content = '';
           	content += '<li>总开票金额:<span>'+Number(data.allinvoicevalue).toFixed(2)+'</span></li>';
           	content += '<li>开票金额:<span>'+Number(data.invoicevalue).toFixed(2)+'</span></li>';
           	content += '<li>作废金额:<span>'+Number(data.dishonourvalue).toFixed(2)+'</span></li>';
           	$("#dataCount").html(content);

        	//分页加载
        	/* showdata("table",data.rows); */
        	//计算主体的宽度
            setWidth();
            setHeight();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (Number(row.dishonour) > 0) {
                strclass = 'warning'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [{
            title : '序号',
            align: "center",
            width: 40,
            formatter: function (value, row, index) {
             /* return index + 1; */
             var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        },
        {
            title: '开票时间',
            field: 'invoice_time',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>' + value+ '</span>';
            }
        },
        {
            title: '票号',
            field: 'duty_parayraph',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>' + value + '</span>';
            }
        },{
            title: '标识',
            field: 'dishonour',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	var html = "";
            	if (value==1) {
                    html='<img class="iscreatelclj" style="height: 18px;width: 18px;margin-top: 3px;" src= ' +contextPath + '/static/image/kqdsFront/tag/cancellation.jpg/>';
                }
            	 return html;
            },
            cellStyle:{
        		css:{"display":"flex","flex-direction": "row"}
        	}
        },
        {
            title: '开票人',
            field: 'drawer',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name">' + value + '</span>';
            }
        },
        {
            title: '开票金额',
            field: 'invoice_value',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name">' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '纳税人识别号',
            field: 'taxpayer_number',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != null || value != '') {
                   return '<span>' + value + '</span>';
                } else {
                   return '<span>-</span>';
                }
            }
        },
        {
            title: '明细',
            field: 'invoice_detail',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	if (value != null || value != "") {
                    return '<span>' + value + '</span>';
                    
                 } else {
                    return '<span>-</span>';
                 }
            }
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>'+value+'</span>';
            }
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '修改人',
            field: 'updateuser',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	if(row.dishonour==0){
            		return "<span>"+value+"</span>";
            	}else{
            		return "<span>-</span>";
            	}
            }
        },
        {
            title: '修改时间',
            field: 'updatetime',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	if(row.dishonour==0){
            		return "<span >"+value+"</span>";
            	}else{
            		return "<span>-</span>";
            	}
            }
        },{
            title: '作废人',
            field: 'updateuser',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	if(row.dishonour==1){
            		return "<span>"+value+"</span>";
            	}else{
            		return "<span>-</span>";
            	}
            }
        },
        {
            title: '作废时间',
            field: 'updatetime',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	if(row.dishonour==1){
            		return "<span >"+value+"</span>";
            	}else{
            		return "<span>-</span>";
            	}
            }
        }
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}

function shuaxin(){
	/* $('#table').bootstrapTable('refresh', {
        'url': pageurl
    }); */
	window.location.reload();
}


function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		organization: $('#organization').val(),
        searchValue: $('#searchValue').val(),
        invoicestarttime: $('#invoicestarttime').val(),
        invoiceendtime: $('#invoiceendtime').val(),
        invoicestartvalue: $('#invoicestartvalue').val(),
        invoiceendvalue: $('#invoiceendvalue').val(),
    };
    if(nowday != null){
    	temp.invoicestarttime = nowday;
    	temp.invoiceendtime = nowday;
    }
    return temp;
}
function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
   		organization: $('#organization').val(),
        searchValue: $('#searchValue').val(),
        sortName:this.sortName,
    	sortOrder:this.sortOrder,
    	invoicestarttime: $('#invoicestarttime').val(),
        invoiceendtime: $('#invoiceendtime').val(),
        invoicestartvalue: $('#invoicestartvalue').val(),
        invoiceendvalue: $('#invoiceendvalue').val(),
    };
    if(nowday != null){
    	temp.invoicestarttime = nowday;
    	temp.invoiceendtime = nowday;
    }
    return temp;
}

function searchHzda() {
	loadedData = [];
	$("#dataCount").html(''); 
	nowpage = 0;
	nowday = null;
    var searchValue = $('#searchValue').val();
    var invoicestarttime = $('#invoicestarttime').val();
    var invoiceendtime = $('#invoiceendtime').val();
    if (searchValue == "" &&invoicestarttime=="" && invoiceendtime=="") {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function clean() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#regsort").val("").trigger("change");
    $("#cjstatus").val("").trigger("change");
    $("#devchannelSearch").val("").trigger("change");
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
}
//导出发票
function exporttable() {
	var fieldArr=["number","cancellation","invoice_kind","invoice_code","duty_parayraph","usercode","drawer","taxpayer_number","invoice_time","invoice_month","invoice_value","invoice_detail","createtime","createuser","modifier","filemtime","invalid_person","invalid_time"];

	var fieldnameArr=["序号","是否作废","发票种类","发票代码","发票号码","患者编号","购方名称","购方税号","开票时间","所属月份","合计金额","主商品名称","创建时间","创建人","修改人","修改时间","作废人","作废时间"];

	var param  = JsontoUrldata(queryParams());
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}

function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
       if (listbutton[i].qxName == "kpcx_scbb") {
           menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exporttable()">生成报表</a>';
       } 
    }
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>';
    $("#bottomBarDdiv").append(menubutton1);

    setHeight();
}
</script>
</html>
