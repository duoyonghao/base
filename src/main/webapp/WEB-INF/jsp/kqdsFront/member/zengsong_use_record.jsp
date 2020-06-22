<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String memberno = request.getParameter("memberno");
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>赠送项目-使用记录</title>

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
						赠送项目使用记录
					</div>
				</div>
				<!-- <div class="listHd">
					<span class="hc-icon icon20 home-icon"></span>赠送项目使用记录
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
								<input type="hidden" id="memberno" />
								<input type="text" placeholder="开始日期" id="starttime" class="datetime"/> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" class="datetime"/>
							</div>
						</div>
					</div>
					<div class="kv">
						<label>医生</label>
						<div class="kv-v">
							<input type="hidden" name="doctor" id="doctor"  value="" class="form-control"/>
							<input type="text" id="doctorDesc" name="doctorDesc" value="" placeholder="医生" onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"  readonly></input>
						</div>
					</div>
					<div class="kv">
						<label>护士</label>
						<div class="kv-v">
							<input type="hidden" name="nurse" id="nurse" value="" class="form-control"/>
							<input type="text" id="nurseDesc" name="nurseDesc" value="" placeholder="护士" onClick="javascript:single_select_user(['nurse', 'nurseDesc'],'',1);"  readonly></input>
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
						<a href="javascript:void(0);" class="kqdsCommonBtn" id="dayin">打印</a>
						<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a> 
						<a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="query();">查询</a> 
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
var memberno = '<%=memberno%>';
var contextPath = '<%=contextPath%>';
var perseqId = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var pageurl = '<%=contextPath%>/KQDS_GiveItem_UseRecordAct/getUserRecordByMemberno.act';
var menuid = "<%=menuid%>";
var qxnameArr = ['syzsjl_scbb'];
var func = ['exportTable'];	
$(function() {
	//获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
    inittable(); //加载表格
    //查询条件 创建时间
    $(".datetime").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });

    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
});

// 获取选中的行
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}

//打印
$('#dayin').on('click',
function() {
    var selectedrows = getIdSelections();
    if (selectedrows.length == 0) {
        layer.alert('请勾选至少一个打印项目' );
    }

    var usercode = selectedrows[0].usercode;
    var seqIds = '';
    for (var i = 0; i < selectedrows.length; i++) {
        seqIds += selectedrows[i].seqId + ',';
    }

    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("使用赠送单");
    var costurl = "";
    // 默认 a5
    costurl = contextPath + '/KQDS_Print_SetAct/toZengSongPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&usercode=' + usercode + '&seqIds=' + seqIds;
    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: costurl
    });
});

//查询
function query() {
    var queryInput = $("#queryInput").val();
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var doctor = $("#doctor").val();
    var nurse = $("#nurse").val();
    if (queryInput == "" && starttime == "" && endtime == "" && doctor == "" && nurse == "") {
        layer.alert('请选择查询条件' );
        return false;
    }
    memberno = ""; //清空memberno
    //加载表格
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}

//清空
$('#clear').on('click',
function() {
    memberno = ""; //清空会员卡号
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        queryInput: $('#queryInput').val(),
        doctor: $('#doctor').val(),
        nurse: $('#nurse').val(),
        memberno: memberno
    };
    return temp;
}
function inittable() {
	/* wl---计算talbe的高度*/
	var tableHeight=0;/* 计算table需要的高度 */
	/* 根据当前页面 计算出table需要的高度 */
	tableHeight=$(window).height()-$(".searchWrap").outerHeight()-$(".listHd").outerHeight()-20;
	/* 框架要使用改table */
	$(".tableBox").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	/* wl---结束*/
	
	
    //加载表格
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        singleSelect: false,
        cache: false,
        striped: true,
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [
		{
		    field: ' ',
		    checkbox: true
		},
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" >' + value + '</span>';
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" >' + value + '</span>';
            }
        },

        {
            title: '项目名称',
            field: 'itemname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="width:fit-content;float:left;position:relative;top:-1px;text-align:center;" class="time" title="' + value + '">' + value + '</span>';
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
            title: '本次使用数量',
            field: 'synum',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '赠送人',
            field: 'createuser',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" >' + value + '</span>';
            }
        },
        {
            title: '赠送时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '状态',
            field: 'status',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == '已操作') {
                    return '<span class="name" style="color:green;">已操作</span>';
                } else {
                    return '<span class="name" style="color:red;">未操作</span>';
                }
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" >' + value + '</span>';
            }
        },
        {
            title: '护士',
            field: 'nurse',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" >' + value + '</span>';
            }
        },
        {
            title: '操作时间',
            field: 'cztime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>' + value + '</span>';
            }
        }]
    });
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    /* var baseHeight = $(window).height() - 15,
    serachH = $('.searchWrap').outerHeight();
    $('.listWrap').height(baseHeight - serachH - 10);
    $('.listWrap .listBd').height($('.listWrap').height() - $('.listWrap .listHd').height() - 20);
    $(".fixed-table-container").height($('.listWrap .listBd').height() - $('.listWrap .columnBd .buttonBar').height() - 50); */
    $(".fixed-table-container").height($(window).height()-$(".searchWrap").outerHeight()-$(".titleDiv").outerHeight()-50);
}
</script>
</html>
