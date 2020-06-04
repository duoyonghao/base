<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会员中心-赠送项目查询</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<style type="text/css">

.buttonBar .aBtn_big {  /* 按钮为四个字的用这个样式 */
	display: inline-block;
	padding: 0 15px;
	height: 28px;
	border: solid 1px #0e7cc9;
	color: #0e7cc9;
	border-radius: 15px;
	line-height: 28px;
	width: 88px;
	text-align: center;
}

.buttonBar  .aBtn_big:hover {
	color: #fff;
	background: #0e7cc9;
}
.searchWrap .btnBar{		/*按钮组右浮动  */
	float:right;
	overflow:hidden;
}
.searchWrap .btnBar .aBtn{
	margin:0 3px;
	display:block;
	float:left;
}
.searchWrap .kv input[type=text].searchInput{	/* 搜索框的宽度 */
	width:160px;
}
.mhqueryDiv{
	margin-left:30px;
}
.listWrap{
	border:none;
}
.searchWrap{
	padding:49px 10px 8px 10px;
	/* border-bottom:0; */
}
/* input[type="text"]:focus,select:focus,textarea:focus{
    box-shadow: 0 0 8px rgb(49, 165, 247);
    border-color:transparent;
} */
.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
</style>
<body>
	<div id="container">
		<div class="main">
			<div class="listWrap">
				<div  class="titleDiv" style="padding-left:10px;">
					<div class="title">
						赠送项目查询
					</div>
				</div>
				<!-- <div class="listHd">
					<span class="hc-icon icon20 home-icon"></span>赠送项目查询
				</div> -->
				<div class="listBd">
					<div class="tableBox">
						<table id="table"
							class="table-striped table-condensed table-bordered" data-height="400"></table>
					</div>
<!-- 					<div class="buttonBar"> -->
<!-- 					</div> -->
				</div>
			</div>
			<!--查询条件-->
			<div class="searchWrap">
				<div class="cornerBox">查询条件</div>
				<div class="formBox">
					<div class="kv">
						<label>创建时间</label>
						<div class="kv-v">
							<div class="unitsDate"  style="width:300px;">
								<input type="text" placeholder="开始日期" id="starttime" class="datetime"/> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" class="datetime"/>
							</div>
						</div>
					</div>
					<div class="kv mhqueryDiv">
						<!-- <label></label> -->
						<div class="kv-v">
							<input type="text" id="queryInput" class="searchInput" placeholder="请输入患者姓名/项目名称" >
							<span class="orangeFont">*可模糊查询</span>
						</div>
					</div>
					<div class="btnBar">
						<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
						<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
	<!-- 					<a href="javascript:void(0);" class="kqdsCommonBtn" id="dayin">打印</a>  -->
	<!-- 					<a href="javascript:void(0);" class="kqdsCommonBtn" id="editnum">修改数量</a> -->
	<!-- 					<a href="javascript:void(0);" class="kqdsCommonBtn" id="editnumfinish" style="display: none;">修改完成</a> -->
					</div>
				</div>
				

			</div>
		</div>
	</div>
	
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</body>
<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var perseqId = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var $table = $('#table');
var pageurl = contextPath + '/KQDS_GiveItem_GiveRecordAct/getGiveRecordByMemberno.act';
// var onclickrow = "";//选中行对象
var menuid = "<%=menuid%>";
var qxnameArr = ['zsxm_scbb'];
var func = ['exportTable'];
var nowday;
$(function(){
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
	nowday = getNowFormatDate();
	$("#starttime").val(nowday);
	$("#endtime").val(nowday);
	
	inittable();//加载表格
    //查询条件 创建时间
 	$(".datetime").datetimepicker({
 		language:  'zh-CN',  
 		minView:2,
        autoclose : true,
 		format: 'yyyy-mm-dd',
 		pickerPosition: "top-right"
 	});
    
    //计算主体的宽度
    setHeight();
    $(window).resize(function () {
        setHeight();
    });
});

//查询
$('#query').on('click', function(){
   	var queryInput = $("#queryInput").val();
   	var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    if(queryInput == "" && starttime == "" && endtime == ""){
   		layer.alert('请选择查询条件' );
 		return false;
  		}
    //加载表格
    $('#table').bootstrapTable('refresh',{'url':pageurl}); 
});

//清空
$('#clear').on('click', function(){
	$(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");//核心
});
function exportTable(){
	var fieldArr=[];
	var fieldnameArr=[];
	$('#table thead tr th').each(function () {
		var field = $(this).attr("data-field");
		if(field!=""){
			fieldArr.push(field);//获取字段
			fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
		}
	});
	var param  = JsontoUrldata(queryParams());
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
function queryParams(params) {
    var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		starttime:$('#starttime').val(),
    		endtime:$('#endtime').val(),
    		queryInput:$('#queryInput').val()
    };
    return temp;
}
function inittable(){
	/* wl---计算talbe的高度*/
	var tableHeight=0;/* 计算table需要的高度 */
	/* 根据当前页面 计算出table需要的高度 */
	tableHeight=$(window).height()-$(".searchWrap").outerHeight()-$(".listHd").outerHeight()-20;
	/* 框架要使用改table */
	$(".tableBox").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	/* wl---结束*/
	
	//加载表格
    $("#table").bootstrapTable({
		url:pageurl, 
		dataType: "json",
		queryParams:queryParams,
		clickToSelect: true,
        singleSelect: true,
		cache: false, 
		striped: true,
		onLoadSuccess:function(){
			setHeight();
		},
		columns: [
				{title: 'seqId',field: 'seqId',align: 'center',visible: false,switchable: false},
				{title: 'itemno',field: 'itemno',align: 'center',visible: false,switchable: false},
				{title: '患者编号',field: 'usercode',align: 'center',sortable: true,
				formatter:function(value){
					return '<span>'+value+'</span>';
				}	
				},
				{title: '患者姓名',field: 'username',align: 'center',sortable: true,
					formatter:function(value){
						return '<span>'+value+'</span>';
					}	},
				{title: 'unitprice',field: 'unitprice',align: 'center',visible: false,switchable: false,
						formatter:function(value){
							return '<span>'+value+'</span>';
						}	
					},
				{title: '项目名称',field: 'itemname',align: 'center',sortable: true,
				    formatter: function(value, row, index) {
				        return '<span class="time" style="width:fit-content;float:left;position:relative;top:-1px;text-align:center;" title="'+value+'">'+value+'</span>';
				    }
				},
				{title: '单位',field: 'unit',align: 'center', sortable: true,
					formatter:function(value){
						return '<span>'+value+'</span>';
					}	},
				{title: '赠送数量',field: 'zsnum',align: 'center',sortable: true,
						formatter:function(value){
							return '<span>'+value+'</span>';
						}	},
				{title: '已使用数量',field: 'usenum',align: 'center',sortable: true,
							formatter:function(value){
								return '<span>'+value+'</span>';
							}	},
				{title: '剩余数量',field: 'nownum',align: 'center',sortable: true,
								formatter:function(value){
									return '<span>'+value+'</span>';
								}	},
				{title: '赠送人',field: 'createuser',align: 'center',sortable: true,
					  formatter: function(value, row, index) {
			                if (value != "" && value != null) {
			                    return '<span class="name" >' + value + '</span>';
			                }
			            }
				},
				{title: '赠送时间',field: 'createtime',align: 'center',sortable: true,
				    formatter:function(value,row,index){  
						return '<span class="time" title="'+value+'">'+value+'</span>';
					}
				}
       ]
	});
}

function refresh(){
	
	$('#table').bootstrapTable('refresh',{'url':pageurl});
}

//计算左侧表格高度保证一屏展示
function setHeight() {
	 $(".fixed-table-container").height($(window).height()-$(".searchWrap").outerHeight()-$(".titleDiv").outerHeight()-50);  
}
</script>
</html>
