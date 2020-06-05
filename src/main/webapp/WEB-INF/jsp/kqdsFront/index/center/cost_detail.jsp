<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	  contextPath = "/kqds";
	}
	
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode="";
	}
	String iframtype = request.getParameter("iframtype");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>费用详情</title><!-- 右侧个人中心  费用详情 tab 进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<style>
	#container{
		padding-bottom:15px;
	}
</style>
</head>
<body>
<div id="container">
    <div class="tableHd" id="topyxzl">费用详情</div>
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>
    </div>

    <div class="tableHd">详细项目清单</div>
    <div class="tableBox" id="divkdxm">
        <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="250"></table>
    </div>
    <div class="buttonBar" style="margin-top: 10px;">
		<div class="recommendedBar" id="recommendedBarDiv">
		</div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = '<%=contextPath%>/KQDS_CostOrderAct/getByRegnoNopage.act';
var onclickrowOobj2 = ""; //表格点击获取到的对象
var usercode = "<%=usercode%>";


$(function() {
	if(usercode == ''){
		 layer.alert('请选择患者', {
	           
	     },function(){
	    	 return false;
	     });
	 }else{
		pageurl = pageurl + '?usercode=' + usercode + '&access=1';
		// 加载列表
		getlist();
	 }
	
	
	$("#dykdxm  tbody").html("");
	
    setHeight();
    $(window).resize(setHeight);
   
    //子页面高度传给父页面
    setHeight();
    $(window).resize(setHeight);
    
});
function getlist() {
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        onLoadSuccess: function(data) { //加载成功时执行
            $("#table tbody tr td:eq(0)").click();
            var ys=0,qf=0,jf=0;
        	for(var i=0;i<data.length;i++){
        		ys += Number(data[i].shouldmoney);
        		qf += Number(data[i].y2);
        		jf += Number(data[i].actualmoney);
        	}
            $("#topyxzl").html("费用详情 - &nbsp;&nbsp;应收：" + ys.toFixed(2) + " &nbsp;&nbsp;缴费：" + jf.toFixed(2) + "&nbsp;&nbsp;<span style='color:red;line-height: 30px;font-family: Microsoft YaHei;font-size: 14px;'> 欠费：" + qf.toFixed(2)+"</span>");
        },
        columns: [{
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '费用类型',
            field: 'type',
            align: 'center',
            valign: 'middle',
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
            valign: 'middle',
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
            valign: 'middle',
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
            title: '合计',
            field: 'totalcost',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '应收金额',
            field: 'shouldmoney',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (Number(value) != 0) {
                    return '<span style="color:red">' + value + '</span>';
                } else {
                    return value;
                }
            }
        },
        {
            title: '缴费金额',
            field: 'actualmoney',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '开单时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span class="time" >' + value.substring(5) + '</span>';
                return html;
            }
        },
        {
            title: '开单人',
            field: 'createusername',
            align: 'center'
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        OrderDetail(onclickrowOobj2.costno); 
        //"\'"+onclickrowOobj2.costno+"\'"
    });
}
function OrderDetail(costno) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="250"></table>');
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno;

    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        columns: [
        {
		    title: '治疗状态',
		    field: 'kaifa',
		    valign: 'middle',
		    align: 'center',
		    formatter: function(value, row, index) {
		    	var buttonstr = "<span>未治疗</span>";
                if(row.kaifa == '已治疗'){
                	buttonstr = '<span>已治疗</span>';
                }
		        return buttonstr;
		    }
		},{
            title: '治疗时间',
            field: 'zltime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span>' + value + '</span>';
                return html;
            }
        },{
            title: '项目编号',
            field: 'itemno',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                var html = '<span class="time" title="' + value + '">';
                if (row.istk == 1) {
                    html += '<span class="label label-info">退款</span>';
                } else {
                    if (row.y2 < 0) {
                        html += '<span class="label label-warning">还款</span>';
                    } else if (row.y2 > 0 && onclickrowOobj2.cjstatus == 1) {
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
            title: '单位',
            field: 'unit',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + row.unitprice;
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '折扣%',
            field: 'discount',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + row.subtotal;
            }
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return "￥" + row.voidmoney;
            }
        },
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + row.y2;
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + (row.paymoney);
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        }]
    });
}
//调整表格高度
function setHeight() {
    var height = $('body').height();
}
</script>
</html>
