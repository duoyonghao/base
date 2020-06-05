<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
	String sheetno = request.getParameter("sheetno");
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>加工单详情</title>
<!-- jd_index.jsp页面进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
.fixed-table-container thead th .sortable{
	padding-right:8px;
}
.formBox{
	padding-top:10px;
}
#container{
	padding:5px;
}
</style>
</head>
<body>
<div id="container">
    <div class="main">
        <div class="listWrap">
        	<div class="costListHd">
                <h4>加工项目明细</h4>
            </div>
            <!-- <div class="listHd"><span class="hc-icon icon20 home-icon"></span>加工项目明细</div> -->
            <div class="listBd">
                <div class="tableBox">
                    <table id="listTable" class="table-striped table-condensed table-bordered" data-height="450"></table>
                </div>
                    <label><input type="radio" name="type" value="试戴" disabled="disabled">试戴</label>
                    <label><input type="radio" name="type" value="返工" disabled="disabled">返工</label>
                    <label><input type="radio" name="type" value="单冠" disabled="disabled">单冠</label>
                    <label><input type="radio" name="type" value="联冠" disabled="disabled">联冠</label>
                    <label><input type="radio" name="type" value="加急" disabled="disabled">加急</label>
                <div class="formBox">
	                <div class="kv">
	                    <label style="width:70px;">设计要求：</label>
	                    <div class="kv-v">
	                        <textarea id="yaoqiu" rows="3" style="font-size:14px;height:60px;width:400px;line-height: 15px;" readonly></textarea>
	                    </div>
	                </div>
                </div>
                <div class="buttonBar">
                </div>
            </div>
        </div>
    </div>
    <!--查询条件-->
    <div class="searchWrap">
        <div class="cornerBox">基本资料</div>
        <div class="btnBar">
        </div>
        <div class="formBox">
        	<div class="kv">
                <label>姓名</label>
                <div class="kv-v">
                	<input type="text" id="username" readonly>
                </div>
            </div>
            <div class="kv">
                <label>患者编号</label>
                <div class="kv-v">
                	<input type="text" id="usercode" readonly style="width:110px;">
                </div>
            </div>
            <div class="kv">
                <label>性别</label>
                <div class="kv-v">
                	<input type="text" id="sex" readonly>
                </div>
            </div>
            <div class="kv">
                <label>出生年月</label>
                <div class="kv-v">
                	<input type="text" id="birthday" readonly style="width:110px;">
                </div>
            </div>
            <div class="kv">
                <label>手机</label>
                <div class="kv-v">
                	<input type="text" id="phonenumber1" readonly style="width:110px;">
                </div>
            </div>
            <div class="btnBar">
				<a href="javascript:void(0);" class="kqdsCommonBtn" id="dayin">打印</a>
			</div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/jgzx/jg/jg.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var usercode = '<%=usercode%>'; //患者编号
var sheetno = '<%=sheetno%>'; //加工单号
var $table = $('#listTable');
var savesheeturl = '<%=contextPath%>/KQDS_OutProcessingSheetAct/insertOrUpdate.act';
var pageurl = '<%=contextPath%>/KQDS_OUTPROCESSING_SHEET_DETAILAct/getListNoPage.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var onclickrow = parent.onclickrow;
$(function() {

    //查询加工单详情
    var sheetdetail = '<%=contextPath%>/KQDS_OutProcessingSheetAct/selectDetail.act?seqId=' + sheetno;
    $.axse(sheetdetail, null,
    function(data) {
        $('#yaoqiu').val(data.data.yaoqiu);
        $("input[name=type]").each(function() {
            if ($(this).val() == data.data.type) {
                $(this).attr("checked", true);
            }
        });
    },
    function() {
        layer.alert('查询加工单信息失败！' );
    });

    //查询患者详情
    var baseinfo = getBaseInfoByUserCode(usercode);
	if(baseinfo){
		$('#username').val(baseinfo.username);
        $('#usercode').val(baseinfo.usercode);
        $('#sex').val(baseinfo.sex);
        $('#birthday').val(baseinfo.birthday);
        if (canlookphone == 0) {
            $('#phonenumber1').val(baseinfo.phonenumber1);
        }
	}

    showtable(); //加载表格 加工项目列表
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
});

//加载表格
function showtable() {
    var pageurl1 = pageurl + '?outprocessingsheetno=' + sheetno;
    $table.bootstrapTable({
        url: pageurl1,
        cache: false,
        striped: true,
        clickToSelect: true,
        singleSelect: false,
        onLoadSuccess: function(data) { //加载成功时执行
        	
        },
        columns: [{
            title: '项目编号',
            field: 'wjgxmbh',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: false,
        },

        {
            title: '项目名称',
            field: 'outprocessingname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="float:left;text-align:left;" >' + value + '</span>';
            }
        },
        {
            title: '项目分类',
            field: 'typename',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '加工厂',
            field: 'factoryname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '数量',
            field: 'nums',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '单位',
            field: 'utils',
            align: 'center',
            valign: 'middle',
            sortable: true
        },

        {
            title: '牙位',
            field: 'toothbit',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null || value == "" || value == "null" || value == "undefined") {
                    return "-";
                }
                return '<span >' + value + '</span>';
            }
        },
        {
            title: '色号',
            field: 'colorsize',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null || value == "" || value == "null" || value == "undefined") {
                    return "-";
                }
                return '<span >' + value + '</span>';
            }
        },
        {
            title: '制作要求',
            field: 'zzyq',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null || value == "" || value == "null" || value == "undefined") {
                    return "-";
                }
                return '<span class="remark" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },

        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
    });
}


//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchWrap').outerHeight();
    var formH = $('.formBox').outerHeight();
    $('.costList').height(baseHeight - serachH - 25);
    $('.costListBd').height(baseHeight - serachH - 80);
    $('#listTable').bootstrapTable('resetView', {
        height: baseHeight - $(".costListHd").outerHeight()-$(".searchWrap").outerHeight()-$(".formBox").outerHeight()-85
    });
}

</script>
</html>
