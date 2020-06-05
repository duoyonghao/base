<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%
	String contextPath = request.getContextPath();

	YZPerson person = SessionUtil.getLoginPerson(request);
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style>
	.searchWrap .btnBar{
		line-height:30px;
	}
	.searchWrap .btnBar .kqdsCommonBtn{
		padding:0 5px;
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
	.tableBox{
		border-bottom: 1px solid #ddd;
	}
	.fixed-table-pagination{
		border-top: 1px solid #ddd;
	}
	.pagination li{
		margin-left: 0px!important;
		height:auto!important;
	}
	.dropdown-menu{
		min-width: auto!important;
	    padding: 0px 0!important;
	}
	.dropdown-menu li{
		margin-left: 0px!important;
	}
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       	width: 110px;   
    }  
	.searchSelect>.btn { 
	    width: 110px; 
	 	height:26px; 
		border: solid 1px #e5e5e5; 
	}


	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
	    color: #999;
	    height: 26px;
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
	}
	.formBox{
		overflow: visible;
	}
	.searchWrap .formBox>section>ul.conditions {
 		height:100px; 
	    overflow: visible;
	    margin: 0;
	}
	.centerWrap .columnWrap .columnBd ul li.current {
    color: #00a6c0 !important;;
    border-bottom: 0px solid #00a6c0 !important;
    }
	.tableBox .fixed-table-container{
	    height: 550px !important; 
	}
	.tableBox1 .fixed-table-container{
	    height: 200px !important; 
	}
	.tableBox2 .fixed-table-container{
	    height: 200px !important; 
	}
	.tableBox3 .fixed-table-container{
	    height: 550px !important; 
	}
	#leftbox{
	  width:50% !important; 
	}
	.tableHd{
	margin: 5px;
    font-size: 16px;
    }
    .btn{
    background: #00a6c0;
    color: #fff;
    cursor:pointer;
    }
    
    .tooth_map{
 		width: 100%; 
	}
	.tooth_map>li{
		width:34%;
		height:50%;
		float:left;
	}
	.tooth_map>li:FIRST-CHILD{
		border-bottom: 1px solid black;
		border-right: 1px solid black;
	}
	.tooth_map>li:FIRST-CHILD+li{
		border-bottom: 1px solid black;
	}
	.tooth_map>li:FIRST-CHILD+li+li{
		border-right: 1px solid black;
	}
	.tooth_map>li>.tooth_input{
		display:block;
		width:100%;
		height:100%;
		padding:0px;
		border:0px;
		text-align: center;
		color: #00a6c0;
	}
	ul, ol {
    margin-bottom: 0px !important;
	}
	.fixed-table-container thead th .th-inner, .fixed-table-container tbody td .th-inner {
    padding: 8px 0 !important;
	}
	#table td>span.item, #dykdxm td>span.item, #table1 td>span.item, #table2 td>span.item{
    width: 140px;
    }
    #table td>div.orientation, #dykdxm td>div.orientation, #table1 td>div.orientation, #table2 td>div.orientation{
    width: 80px;
    }
</style>
</head>

<body> 
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div  style="float: left;width:55%;">
            <div class="columnWrap">
                <div class="columnBd">
                	<div class="tableHd">排队中
                	<div style="display: inline-block;margin-left: 45%;">
                	<button class="btn" onclick="show()">展示</button>
                	</div>
                	<div style="float: right;">
                	<button class="btn" onclick="transfer()">转移</button>
                	</div>
                	</div>
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                    <div class="tableHd">进行中</div>
				    <div class="tableBox1">
				        <table id="dykdxm" class="table-striped table-condensed table-bordered" ></table>
				    </div>
                </div>
            </div>
        </div>
        <!--右侧中心-->
        <div style="float: right;width:45%;padding-left: 15px;">
            <div class="columnWrap">
                <div class="columnBd">
                	<div class="tableHd">已完成
	                	<div style="display: inline-block;margin-left: 45%;">
	                	<button  class="btn" onclick="addVideo()">添加视频</button>
	                	</div>
                	</div>
                    <div class="tableBox3">
                        <table id="table1" class="table-striped table-condensed table-bordered"></table>
                    </div>
                    <div class="tableHd">超时</div>
				    <div class="tableBox2">
				        <table id="table2" class="table-striped table-condensed table-bordered" ></table>
				    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/okCopy.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<script type="text/javascript">
var parameter=0;//状态参数 0可通过websocket进行刷新1通过点击按钮刷新
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = contextPath + '/Kqds_JhAct/select.act';
var pageurl1 = contextPath + '/Kqds_JhAct/selectFy.act';
var floor="";
var contextPath = "<%=contextPath%>";
var $table = $("#table");
var $dykdxm = $("#dykdxm");
var id="";
var regid="";
var l=0;
var s=0;

$(function() {

    getButtonAllCurPage("<%=menuid%>");

});

function getButtonPower() {
	for (var i = 0; i < listbutton.length; i++) {
    	if (listbutton[i].qxName == "A") {
    		floor=listbutton[i].qxName;
    		getlist();
    	}else if(listbutton[i].qxName == "B"){
    		floor=listbutton[i].qxName;
    		getlist();
    	}
    }
}
function show() {
	if('<%=ChainUtil.getCurrentOrganization(request)%>'=='HUDH'){
	    window.open("http://192.168.3.24:8081/base/Kqds_JhAct/toExhibition.act?floor="+floor);
	}else if('<%=ChainUtil.getCurrentOrganization(request)%>'=='HUDX'){
		window.open("http://192.168.3.24:8080/base/Kqds_JhAct/toExhibition.act?floor="+floor);
	}
}
//患者转移楼层
function transfer(){
	if(s==0){
		var ids=[];
		var numbers=[];
		$.map($table.bootstrapTable('getSelections'),
			    function(row) {
					ids.push(row.id);
					numbers.push(row.number);
			        //return row;
			    });
		$.map($dykdxm.bootstrapTable('getSelections'),
			    function(row) {
					ids.push(row.id);
					numbers.push(row.number);
			        //return row;
			    });
		if(ids.length==0){
			layer.alert('请选择患者!');
			return false;
		}else{
			layer.confirm("是否确定操作？",{
				   btn: ['确认', '取消'],
				   closeBtn:0
			}, function (index) {
				s=1;
				layer.msg('玩命加载中&nbsp;&nbsp;<img src='+contextPath + '/static/image/kqdsFront/tag/await.gif/>',{time:60*1000},function() {
					//回调
					})
					updateParameter();
					var url = contextPath + '/Kqds_JhAct/updateFloor.act';
					$.ajax({
				        type: "POST",
				        url: url,
				        data: {ids:JSON.stringify(ids),numbers:JSON.stringify(numbers),floor:floor},
				        dataType: "json",
				        success: function(r){
				        	if(r.retState == "0"){
				        		s=0;
					        	refresh();
				        		layer.alert('转移成功!');
				        	}else{
				        		s=0;
				        		layer.alert('转移失败!');
				        	}
				        }
					});
				  }, function(index){
			});
		}
	}
	
}
//获取消息总数量
/* function setHeight(){
	var windowHeight=$(window).outerHeight();
	var tableHeight=(windowHeight-$(".searchWrap").outerHeight()-60)/3;//本页面有两个table
	$("#table").bootstrapTable("resetView",{
		height: tableHeight
	})
	$("#dykdxm").bootstrapTable("resetView",{
		height: tableHeight
	})
} */
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	status:1,
    	floor:floor,
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1
    };
    return temp;
}
//进行中患者
function queryParams1(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		status:2,
   		floor:floor
    };
    return temp;
}
//完成患者
function queryParams2(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		status:3,
   		floor:floor,
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1//当前页面,默认是上面设置的1(pageNumber)
    };
    return temp;
}
//超时患者
function queryParams3(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		floor:floor,
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1 //当前页面,默认是上面设置的1(pageNumber)
    };
    return temp;
}

function getlist() {

    $('#table').bootstrapTable({
        url: pageurl1,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 20,
        pageList : [15, 20, 30],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        /* search: true, */
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
        	l=data.total;
        	if(data.total>0){
	        	id=data.rows[0].id;
	        	regid=data.rows[0].regid;	
        	} 
         	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
            OrderDetail();
            OvertimeDetail();
    	    getAccomplishList();
        },
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
        },
        {
			title: '序号',
            field: 'floor',
            align: 'center',
			formatter: function (value, row, index) {
				if(Number(row.number)<=9){
					return '<span>'+value+'0'+row.number+'</span>';
				}else{
					return '<span>'+value+row.number+'</span>';
				}
			}
		}, {
            title: '治疗项目',
            field: 'items',
            align: 'center',
            formatter:function(value){
            	return '<span class="item" title="' + value + '">'+value+'</span>';
            }
        },{
            title: '小牙片牙位',
            field: '',
            width:80,
            formatter:function(value, row, index){
            	return '<div class="orientation"><ul class="tooth_map"><li><input value="'+row.uplefttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.uperrighttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.leftlowertoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.lowrighttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li></ul></div>';
            }
        },{
            title: '关节',
            field: 'joint',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '手术状态',
            field: 'surgery_status',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            formatter:function(value){
            	
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '患者姓名',
            field: 'patient_name',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '性别',
            field: 'sex',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '出生日期',
            field: 'birthday',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '年龄',
            field: 'age',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '电话',
            field: 'phone',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '助理',
            field: 'askpersonname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '操作',
            field: '',
            align: 'center',
            formatter:function(value, row, index){
            	var html = '<span class="chufuzhenclass" style="color:#0d7ccd;cursor:pointer;text-decoration:underline;" onclick="precedence(\''+row.id +'\')" style="width:60px">优先</span>';
            	return html;
            }
        }]
    }).on('click-row.bs.table',function(e, row, element) {
        
    });
}

//复选框
function stateFormatter(value, row, index) {
    if (row.id === 2) {
        return {
            disabled: true,
            checked: true
        };
    }
    if (row.id === 0) {
        return {
            disabled: true,
            checked: true
        }
    }
    return value;
}

function OrderDetail() {
	   $("#dykdxm").bootstrapTable({
	        url: pageurl,
	        dataType: "json",
	        queryParams: queryParams1,
	        onLoadSuccess: function(data) { //加载成功时执行
	        	if(data.length==1&&id!=""&&l>0){
	        		updateStatus1(id,regid,2);
	        	}else if(data.length==0&&id!=""&&l>0){
	        		updateStatus1(id,regid,2);
	        	}
	        	setWidth();
	        	setHeight();
	        	/*表格出现滚动条时 调整表头*/
	            setTableHeaderWidth(".tableBox1");
	        },
	        columns: [
			{
	            field: ' ',
	            checkbox: true,
	            formatter: stateFormatter
        	},
			{
				title: '序号',
	            field: 'floor',
	            align: 'center',
				formatter: function (value, row, index) {
					if(Number(row.number)<=9){
						return '<span>'+value+'0'+row.number+'</span>';
					}else{
						return '<span>'+value+row.number+'</span>';
					}
				}
			}, {
	            title: 'id',
	            field: 'id',
	            align: 'center',
	            visible: false,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },{
	            title: '治疗项目',
	            field: 'items',
	            align: 'center',
	            formatter:function(value){
	            	return '<span class="item" title="' + value + '">'+value+'</span>';
	            }
	        },{
	            title: '小牙片牙位',
	            field: '',
	            width:80,
	            formatter:function(value, row, index){
	            	return '<div class="orientation"><ul class="tooth_map"><li><input value="'+row.uplefttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.uperrighttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.leftlowertoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.lowrighttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li></ul></div>';
	            }
	        },{
	            title: '关节',
	            field: 'joint',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '手术状态',
	            field: 'surgeryStatus',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },{
	            title: '患者编号',
	            field: 'usercode',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '患者姓名',
	            field: 'patient_name',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },{
	            title: '性别',
	            field: 'sex',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },{
	            title: '出生日期',
	            field: 'birthday',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },{
	            title: '年龄',
	            field: 'age',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },{
	            title: '电话',
	            field: 'phone',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '助理',
	            field: 'askpersonname',
	            align: 'center',
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
	        {
	            title: '操作',
	            field: '',
	            align: 'center',
	            formatter:function(value, row, index){

	            	var html = '<span class="chufuzhenclass" style="color:#0d7ccd;cursor:pointer;text-decoration:underline;" onclick="updateStatus(\'' + row.id +'\',\'' + row.regid+ '\',\'' + 3+ '\')" style="width:60px">完成</span>';
	            	html+='&nbsp;&nbsp;&nbsp;&nbsp;<span class="chufuzhenclass" style="color:red;cursor:pointer;text-decoration:underline;" onclick="del(\'' + row.id +'\',\'' + row.regid+'\',\'' + row.usercode+'\',\''+row.patient_name+'\',\''+row.sex+'\',\''+row.askpersonname+'\',\''+row.age+'\',\''+row.phone+'\',\''+row.floor+'\',\''+row.number+'\')" style="width:60px">超时</span>';
	            	return html;
	            }
	        }]
	    });
}   
function OvertimeDetail() {   
	var url = contextPath + '/Kqds_JhAct/selectdelFy.act';
    $('#table2').bootstrapTable({
        url: url,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams3,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 10,
        pageList : [5, 10, 15],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
         	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox2");
        },
        columns: [{
			title: '序号',
            field: 'floor',
            align: 'center',
			formatter: function (value, row, index) {
				if(Number(row.number)<=9){
					return '<span>'+value+'0'+row.number+'</span>';
				}else{
					return '<span>'+value+row.number+'</span>';
				}
			}
		}, {
           title: 'id',
           field: 'id',
           align: 'center',
           visible: false,
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       }, {
           title: '操作',
           field: '',
           align: 'center',
           formatter:function(value, row, index){
        	   var html = '<span class="chufuzhenclass" style="color:#0d7ccd;cursor:pointer;text-decoration:underline;" onclick="updateStatusByDel(\'' + row.id +'\',\'' + row.regid+ '\',\'' + 0+ '\',\'' + row.floor+'\',\'' + row.organization+'\',\'' + row.number+'\')" style="width:60px">重新排队</span>';
        	   return html;
           }
       },{
           title: '治疗项目',
           field: 'items',
           align: 'center',
           formatter:function(value){
        	   return '<span class="item" title="' + value + '">'+value+'</span>';
        	   
           }
       },{
           title: '小牙片牙位',
           field: '',
           width:80,
           formatter:function(value, row, index){
           	return '<div class="orientation"><ul class="tooth_map"><li><input value="'+row.uplefttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.uperrighttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.leftlowertoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.lowrighttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li></ul></div>';
           }
       },{
           title: '关节',
           field: 'joint',
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },
       {
           title: '手术状态',
           field: 'surgery_status',
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },{
           title: '患者编号',
           field: 'usercode', 
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },
       {
           title: '患者姓名',
           field: 'patient_name',
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },{
           title: '性别',
           field: 'sex',
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },{
           title: '出生日期',
           field: 'birthday',
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },{
           title: '年龄',
           field: 'age',
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },
       {
           title: '电话',
           field: 'phone',
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },
       {
           title: '助理',
           field: 'askpersonname',
           align: 'center',
           formatter:function(value){
           	return '<span>'+value+'</span>';
           }
       },
       {
           title: '超时原因',
           field: 'remark',
           align: 'center',
           formatter:function(value, row, index){
        	   return '<span title="'+value+'">'+value+'</span>';
           }
       }]
    });
}
//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

//优先排号
function precedence(id1){
	if(s==0){
		if(l==1||id==id1){
			layer.alert('当前患者已是优先排队!');
			return false;
		}
		layer.confirm("是否确定操作？",{
			   btn: ['确认', '取消'],
			   closeBtn:0
		}, function (index) {
				s=1;
				layer.msg('玩命加载中&nbsp;&nbsp;<img src='+contextPath+'/static/image/kqdsFront/tag/await.gif/>',{time:60*1000},function() {
				//回调
				})
				updateParameter();
				var url = contextPath + '/Kqds_JhAct/precedence.act';
				$.ajax({
			        type: "POST",
			        url: url,
			        data: {id:id1,floor:floor},
			        dataType: "json",
			        success: function(r){
			        	if(r.retState == "0"){
			        		s=0;
			        		layer.alert('操作成功!');
			        	    refresh();
			        	}else{
			        		s=0;
			        		layer.alert('操作失败!');
			        	}
			        }
				});
			}, function(index){
		});
		
	}
}
//操作修改状态
function updateStatus(id,regid,status){
	if(s==0){
		layer.confirm("是否确定操作？",{
			   btn: ['确认', '取消'],
			   closeBtn:0
		}, function (index) {
			s=1;
				layer.msg('玩命加载中&nbsp;&nbsp;<img src='+contextPath+'/static/image/kqdsFront/tag/await.gif/>',{time:60*1000},function() {
				//回调
				})
				updateParameter();
				var url = contextPath + '/Kqds_JhAct/update.act';
				$.ajax({
			        type: "POST",
			        url: url,
			        data: {id:id,status:status,regid:regid},
			        dataType: "json",
			        success: function(r){
			        	if(r.retState == "0"){
			        		s=0;
			        		layer.alert('操作成功!');
			        	    refresh();
			        	}else{
			        		s=0;
			        		layer.alert('操作失败!');
			        	}
			        }
				});
			  }, function(index){
		});
		
	}
}
//操作修改状态
function updateStatusByDel(id,regid,status,floor,organization,number){
	if(s==0){
		layer.confirm("是否确定操作？",{
			   btn: ['确认', '取消'],
			   closeBtn:0
		}, function (index) {
			s=1;
				layer.msg('玩命加载中&nbsp;&nbsp;<img src='+contextPath+'/static/image/kqdsFront/tag/await.gif/>',{time:60*1000},function() {
				//回调
				})
				updateParameter();
				var url = contextPath + '/Kqds_JhAct/update.act';
				$.ajax({
			        type: "POST",
			        url: url,
			        data: {id:id,status:status,regid:regid,floor:floor,organization:organization,number:number},
			        dataType: "json",
			        success: function(r){
			        	if(r.retState == "0"){
			        		s=0;
			        		layer.alert('操作成功!');
			        	    refresh();
			        	}else{
			        		s=0;
			        		layer.alert('操作失败!');
			        	}
			        }
				});
			  }, function(index){
		});
		
	}
}
//修改状态
function updateStatus1(id,regid,status){
	updateParameter();
	var url = contextPath + '/Kqds_JhAct/update.act';
	$.ajax({
        type: "POST",
        url: url,
        data: {id:id,status:status,regid:regid},
        dataType: "json",
        success: function(r){
        	if(r.retState == "0"){
        	    refresh();
        	}else{
        		layer.alert('操作失败!');
        	}
        }
	});
}
function del(id,regid,usercode,username,sex,askpersonname,age,phone,floor,number){
	if(s==0){
		layer.open({
	        type: 2,
	        title: '超时原因',
	        shadeClose: false,
	        shade: 0.6,
	        closeBtn:1,
	        area: ['550px', '480px'],
	        content: contextPath + '/Kqds_JhAct/toOvertime.act?id='+id+'&regid='+regid
	        		+'&usercode=' + usercode+'&username='+username +'&sex='+sex+'&askpersonname='
	        		+askpersonname+'&age='+age+'&phone='+phone+'&floor='+floor+'&number='+number,
       		end: function () {
       			s=0;
       			}
	    }); 
			
	}
}
function updateParameter(){
    parameter=1;
};
function refresh(){
    parameter=0;
    var id="";
    var regid="";
    var l=0;
    //加载表格
    $('#table').bootstrapTable('refresh', {
        'url': pageurl1
    });
    $('#dykdxm').bootstrapTable('refresh', {
        'url': pageurl
    });
    $('#table2').bootstrapTable('refresh', {
        'url': contextPath + '/Kqds_JhAct/selectdelFy.act'
    });
    $('#table1').bootstrapTable('refresh', {
        'url': pageurl1
    });
};

function getAccomplishList() {

    $('#table1').bootstrapTable({
        url: pageurl1,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams2,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 20,
        pageList : [15, 20, 30],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        /* search: true, */
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
         	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox3");
        },
        columns: [/* {
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
        }, */
        {
			title: '序号',
            field: 'floor',
            align: 'center',
			formatter: function (value, row, index) {
				if(Number(row.number)<=9){
					return '<span>'+value+'0'+row.number+'</span>';
				}else{
					return '<span>'+value+row.number+'</span>';
				}
			}
		}, {
            title: '治疗项目',
            field: 'items',
            align: 'center',
            formatter:function(value){
            	return '<span class="item" title="' + value + '">'+value+'</span>';
            	
            }
        },{
            title: '小牙片牙位',
            field: '',
            width:80,
            formatter:function(value, row, index){
            	return '<div class="orientation"><ul class="tooth_map"><li><input value="'+row.uplefttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.uperrighttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.leftlowertoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li><li><input value="'+row.lowrighttoothbit+'" class="tooth_input" type="text" readonly="readonly"/></li></ul></div>';
            }
        },{
            title: '关节',
            field: 'joint',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '手术状态',
            field: 'surgery_status',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '患者姓名',
            field: 'patient_name',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '性别',
            field: 'sex',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '出生日期',
            field: 'birthday',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '年龄',
            field: 'age',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '电话',
            field: 'phone',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '助理',
            field: 'askpersonname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }]
    }).on('click-row.bs.table',function(e, row, element) {
        
    });
}

function copyText() {
	var e = document.getElementById("copy");
    e.select(); // 选择对象
    document.execCommand("Copy"); // 执行浏览器复制命令
    alert("内容复制成功！");
}

function addVideo(){
	layer.open({
        type: 2,
        title: '添加视频',
        shadeClose: true,
        shade: 0.6,
        area: ['60%', '80%'],
        content: contextPath + '/Kqds_Jh_VideoAct/toJhVideo.act' 
    });
}

var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
	 if('<%=ChainUtil.getCurrentOrganization(request)%>'=='HUDH'){
        websocket = new WebSocket("ws://127.0.0.1:8080/base/WSwebsocket");
	 }else{
		 websocket = new WebSocket("ws://192.168.3.24:8080/base/WSwebsocket");
	 }
}
else {
	 alert('当前浏览器 Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function () {
    //setMessageInnerHTML("WebSocket连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function () {
    //setMessageInnerHTML("WebSocket连接成功");
}

//接收到消息的回调方法
websocket.onmessage = function (event) {
	if(parameter==0){
		   var data = event.data.split(",");
		   var j=0;
		   for(var i = 0; i < data.length; i++){
		    	if(data[i]=='<%=ChainUtil.getCurrentOrganization(request)%>'){
		    		j+=1;
		    	}else if(data[i]==floor){
		    		j+=1;
		    	}
		   }
		   if(j==data.length){
			//刷新页面
			refresh();
		   }
	}
    //setMessageInnerHTML(event.data);
}

//连接关闭的回调方法
websocket.onclose = function () {
    //setMessageInnerHTML("WebSocket连接关闭");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

//将消息显示在网页上
/* function setMessageInnerHTML(innerHTML) {
	
} */

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send() {
    var message = document.getElementById('text').value;
    websocket.send(message);
}  

</script>
</html>