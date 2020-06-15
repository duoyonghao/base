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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" /> --%>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>

<title>科室领料</title>
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
	padding: 3px 0;
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
.searchWrap{
    padding: 18px 0px 8px;
}
.centerWrap{
	width:100% !important;
}
.tableBox{
	border-bottom: 1px solid #ddd;
    border-bottom-left-radius: 6px;
    border-bottom-right-radius: 6px;
    margin-bottom: 10px;
}
.columnBd{
	float: left;
	margin-left: 0.5%;
}
.left-type-tree{
	border:1px solid #ddd;
	border-radius: 6px;
	/* width:14.5%; */
	height:200px;
	float: left;
}

.select2-container .select2-selection--single {
    height: 26px;
}

.select2-container .select2-selection--single .select2-selection__rendered {
    padding-left: 3px;
}
/* 搜索框select */
.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 94px;   
      }  
.searchSelect>.btn { 
	    width: 94px; 
	 	height:26px; 
	 	border: solid 1px #e5e5e5; 
	}
.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
	    color: #999;
	    height: 26px;
	}
.pull-left {
    float: left !important;
    color: #333;
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
#center_context{
	overflow: visible;
}
.searchWrap,.formBox{
	overflow: visible;
}
.searchWrap .formBox>section>ul.conditions {
     overflow: visible;
     height: 35px;
     position: relative;
}
#center_context{
	overflow: hidden;
}
.dropdown-menu > li > a {
    padding: 0px 20px;
    box-sizing:border-box;
    font-size: 12px;
}
.btn {
	 padding: 4px 12px;
}
input[type="text"]:focus, select:focus, textarea:focus {
    box-shadow: 0 0 8px #00A6C0; 
}
.dropdown-menu > .active > a, .dropdown-menu > .active > a:hover, .dropdown-menu > .active > a:focus {
    background-color: #00A6C0;
}
#container{
	padding-left: 20px;
}
/* 标题 */
.title{
    font-size: 20px;
    height: 20px;
    line-height: 20px;
    display: block;
    margin: 16px 0px 10px 0px;
}
#bottomBarDdiv{
	margin: 20px;
}
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd">
                	<span class="title">科室领料</span>
                </div>
                <div id="center_context">
                	<div class="left-type-tree">
						<div id="treeDemo" class="ztree" style="height:100%;overflow-y: auto" ></div>
					</div> 
                	<div class="columnBd" >
                    	<div class="tableBox" >
                        	<table id="table" class="table-striped table-condensed table-bordered" data-height=""></table>
                    	</div>
                	</div>
                <!-- <div id="gongzuol">
                	<div class="columnBd">
                		<ul class="dataCountUl" id="dataCount">
                		</ul>
                	</div>
            	</div> -->
            </div>
                </div>
             
            <div class="searchWrap">
	                <!-- <div class="cornerBox">查询条件</div> -->
	                <div class="formBox">
	                	<section>
				    		<ul class="conditions">
				    			<li>
				    				<span>科&nbsp;&nbsp;&nbsp;&nbsp;室</span>
				    				<select id="deptpart" name="deptpart" tig="" class="searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 				    				 <select id="deptpart" name="deptpart"></select> -->
				    			</li>
				    			<li>
				    				<span>仓&nbsp;&nbsp;&nbsp;&nbsp;库</span>
		    						<select id="house" name="house"></select>
				    			</li>
				    			<li>
				    				<span>商品编号</span>
				    				<input type="text" id="goodscode" name="goodscode" placeholder="商品编号" autocomplete="off">
				    			</li>
				    			
				    			<li>
				    				<span>商品名称</span>
				    				<input type="text" id="goodsname" name="searchInput" placeholder="商品名称" autocomplete="off">
				    			</li>
				    			<li>
				    				<span>模糊查询</span>
				    				<input style="width:150px;" type="text" id="queryInput" class="queryInput" placeholder="科室/编号/名称" autocomplete="off">
				    			</li>
			    			</ul>
			    		</section>
			    		<div class="btnBar" id="bottomBarDdiv">
	                   		
			            </div>
	                </div>
	            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ksll/ksll.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>  --%>
</body>
<script type="text/javascript">
var apppath = apppath();
var nowday;
//初始化表头，返回空的数据
var nullUrl = apppath + '/UtilAct/intTableHeader.act';
var pageurl = apppath + '/HUDH_KSllAct/findAllKsllColorGoods.act';
var setting = { 
	    data: { 
	        simpleData: { 
	            enable: true
	        } 
	    },
	    view : {
	    	showIcon : false,
	    	showLine: true
	    },
	    callback:{  
	    	//beforeClick：用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作，默认值为null  
	        //beforeClick: beforeClick,  
	        //beforeAsync:用于捕获异步加载之前的事件回调函数，zTree 根据返回值确定是否允许进行异步加载，默认值为null  
	        //beforeAsync:beforeAsync,  
	        //用于捕获异步加载出现异常错误的事件回调函数  
	        //onAsyncError:onAsyncError,  
	        //用于捕获异步加载正常结束的事件回调函数  
	        //onAsyncSuccess:onAsyncSuccess  
	        //用于捕获节点被点击的事件回调函数  
	        onClick : zTreeOnClick//(注意大小写，若onClick写成了onclick那么点击时将没有任何反应)  
	    }
	};
var zNodes;
var initialize=0;
$(function() {
	setTableWidth();
    nowday = getNowFormatDate();
    //获取当前页面所有按钮
	getButton();
    //绑定两个时间选择框的chage时间
    //4、表格初始化
    initTable(pageurl);
//      initDept($("#deptpart"));//初始化领料部门
	initDeptSearch($("#deptpart"));
    initHouse($("#house"));//初始化领料仓库
    
    $(window).resize(function() {
    	setTableWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
        setTreeHeight();
    });

    //初始化左侧树结构
	initzTreeData();
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-26 licc
});

var btnFun  = {
	//领料
	picking : function (){
		layer.open({
			type : 2,
			title : '领料',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '90%', '90%' ],
			content : apppath + '/HUDH_KSllViewAct/toKsllGoods.act?deptpart='+$('#deptpart').val()
		});
	},
	//领料
	pickingRecode : function (){
		layer.open({
			type : 2,
			title : '领料查询',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '90%', '90%' ],
			content : apppath + '/HUDH_KSllViewAct/toKsllRecode.act'
		});
	},
	replacement : function (){
		layer.open({
			type : 2,
			title : '材料退回',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '90%', '90%' ],
			content : apppath + '/HUDH_KSllViewAct/toKsllReplacement.act'
		});
	},
	replacementCode : function (){
		layer.open({
			type : 2,
			title : '退还记录',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '90%', '90%' ],
			content : apppath + '/HUDH_KSllViewAct/toKsllReplacementRecode.act'
		});
	},
	consum : function (){
		layer.open({
			type : 2,
			title : '内部消耗',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '90%', '90%' ],
			content : apppath + '/HUDH_KSllViewAct/toKsllConsum.act'
		});
	},
	consumCode : function (){
		layer.open({
			type : 2,
			title : '内部消耗记录',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '90%', '90%' ],
			content : apppath + '/HUDH_KSllViewAct/toKsllConsumRecode.act'
		});
	},
}

function initTable(requrl) {
    $('#table').bootstrapTable({
        url: requrl,
        queryParams: queryParams,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 30,
        pageList : [15, 30, 50,100],//可以选择每页大小
      //在表格底部显示分页工具栏 
        singleSelect: true,
        paginationShowPageGo: true,
        cache: false,
        striped: true,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        onLoadSuccess: function(data) { //加载成功时执行
        	initialize=1;
        	//分页加载
        	/* showdata("table",data.rows); */
        	//计算主体的宽度
            setWidth();
            setHeight();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
            setTreeHeight();
        	
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (Number(row.del) > 0) {
                strclass = 'warning'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
		{
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
            title: 'id',
            field: 'id',
            align: 'center',
            visible: false,
            switchable: false
        }, 
        {
            title: '领料时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>' + value + '</span>';
            }
        }, 
        {
            title: '商品编号',
            field: 'goodscode',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '商品名称',
            field: 'goodsname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '规格',
            field: 'goodsnorms',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: false,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '科室库存',
            field: 'nums',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '单位',
            field: 'goodsunit',
            align: 'center',
            
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '所属仓库',
            field: 'housename',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '科室',
            field: 'deptpartname',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
            
        },
        {
            title: '领料人',
            field: 'userName',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
            
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}

function zTreeOnClick(event, treeId, treeNode) {
	$('#deptpart').selectpicker("val",treeNode.id);
	/* clean(); */
	var opt = {
            url: pageurl,
            silent: true,
            query:{
            	deptpart:treeNode.id,
            }
    };
    $('#table').bootstrapTable('refresh', opt);
}

function shuaxin(){
	/* $('#table').bootstrapTable('refresh', {
        'url': pageurl
    }); */
	window.location.reload();
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		limit: params.limit,   //页面大小
            offset: params.offset, //页码 
            pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
    		deptpart:$('#deptpart').val(),
    		house: $('#house').val(),
    		goodscode: $('#goodscode').val(),
    		goodsname: $('#goodsname').val(),
    		queryInput: $('#queryInput').val(),
    		initialize:initialize
    };
    return temp;
}
function searchHzda() {
    var deptpart = $('#deptpart').val();
    var house = $("#house").val();
    var goodscode = $('#goodscode').val();
    var goodsname = $('#goodsname').val();
    var queryInput = $('#queryInput').val();
    if(!deptpart && !house && !goodscode && !goodsname && !queryInput ) {
    	layer.alert("请输入查询条件");
    	return;
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

function getButton() {
	var menubutton1 = "";
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="btnFun.picking();">领料</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="btnFun.pickingRecode();">领料查询</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="btnFun.replacement()">材料退回</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="btnFun.replacementCode()">退还记录</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="btnFun.consum()">内部消耗</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="btnFun.consumCode()">内部消耗记录</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="searchHzda()">查询</a>';
    $("#bottomBarDdiv").append(menubutton1);
    setHeight();
}

function setTableWidth(){
	var windowWidth=$(window).width();
	var width=windowWidth-44;
	$(".columnBd").width(width * 0.85);
	$(".left-type-tree").width(width * 0.145);
	$(".searchWrap").width(width);
}

function setTreeHeight(){
	$(".left-type-tree").height($(".tableBox").height());
}

function initzTreeData(){
	$.ajax({
		url: apppath + "/HUDH_KSllAct/findCkDeptTreeData.act",
		type:"POST",
		dataType:"json",
		async:false,
		data:{},
		success:function(result){
			zNodes = result;
		}
	});
}
</script>
</html>
