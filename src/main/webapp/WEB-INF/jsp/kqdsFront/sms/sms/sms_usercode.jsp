<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>短信记录</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
	#container{
		padding:0 15px;
	}
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
	
</style>
<body>
<div id="container">
    <div class="main" style="padding-top:15px;">
        <div class="listWrap">
        	<!-- <div class="titleDiv">
        		<div class="title">
        			短信记录列表
        		</div>
        	</div> -->
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" data-height="450"></table>
                </div>
            </div>
        </div>
       <div class="buttonBar" >
			<a href="javascript:void(0);" id="tjhf" class="kqdsCommonBtn" onclick="goshowVisit()">详情</a>
			<a href="javascript:void(0);" id="tjhf" class="kqdsSearchBtn" onclick="tosend()">发送</a>
			<a href="javascript:void(0);" id="xghf" class="kqdsCommonBtn" onclick="goEditVisit()">修改</a> 
		</div> 
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/jgzx/jg/jg.js"></script>
</body>
<script type="text/javascript">
var listbutton = parent.listbutton;
var onclickrow = "";
var contextPath = '<%=contextPath%>';
var pageurl = '<%=contextPath%>/KQDS_SmsAct/NoselectPage.act';
var onclickrowobj = window.parent.onclickrowOobj; //父页面传值;
var perseqId = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
$(function() {
    //加载表格
    inittable(pageurl);
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	usercode: onclickrowobj.usercode
    };
    return temp;
}
function inittable(pageurlsno) {
    //加载表格
    $('#table').bootstrapTable({
        url: pageurlsno,
        dataType: "json",
        singleSelect: false,
        cache: false,
        striped: true,
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [{
            title: '门诊',
            field: 'organizationname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '短信类别',
            field: 'smstypename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '二级类别',
            field: 'smsnexttypename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '发送状态',
            field: 'sendstatename',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '发送时间',
            field: 'sendtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span class='time'>"+value+"</span>";
            }
        },
        {
            title: '发送方式',
            field: 'smsstatename',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
    }).on('click-row.bs.table',
    	    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
    });
}
function refresh() {
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
};
function tosend(){
	parent.layer.open({
	       type: 2,
	       title: '短信发送',
	       shadeClose: false,
	       shade: 0.6,
	       area: ['650px', '550px'],
	       content: contextPath + '/KQDS_SmsAct/toSendSms.act?usercode='+onclickrowobj.usercode+'&sendphone='+onclickrowobj.phonenumber1
	});
}
function goshowVisit(){
	 if (onclickrow.seqId == null || onclickrow.seqId == "") {
	        layer.alert('请选择一条短信记录', {
	              
	        });
	        return false;
	 }
	 parent.layer.open({
	       type: 2,
	       title: '短信内容',
	       maxmin: true,
	       shadeClose: true,
	       // 点击遮罩关闭层
	       area: ['550px', '520px'],
	       content: '<%=contextPath%>/KQDS_SmsAct/toSendSmsDetail.act?seqId=' + onclickrow.seqId
	    });
}
function goEditVisit() {
    //已发送的不能再修改
    if (onclickrow.seqId == null || onclickrow.seqId == "") {
        layer.alert('请选择需要修改的短信'  );
        return false;
    }
    if (onclickrow.sendstate == "1") {
        layer.alert('短信已发送,无法修改'  );
        return false;
    }
    if (perseqId != onclickrow.createuser) {
        layer.alert('只能操作自己的短信记录'  );
        return false;
    }
    parent.layer.open({
       type: 2,
       title: '编辑短信',
       maxmin: true,
       shadeClose: true,
       // 点击遮罩关闭层
       area: ['550px', '520px'],
       content: '<%=contextPath%>/KQDS_SmsAct/toSendSmsEdit.act?seqId=' + onclickrow.seqId
    });
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchWrap').outerHeight();
    $('.extraBar .extraBd').height(baseHeight - 65);
    $('#listTable').bootstrapTable('resetView', {
        height: baseHeight - serachH - 162
    });
}
</script>
</html>
