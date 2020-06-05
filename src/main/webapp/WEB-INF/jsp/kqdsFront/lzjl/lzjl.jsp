<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>流转记录</title><!-- 非弹框 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bingli.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<style>
.listBox .listHd > span {
    margin-right: 10px;
    
}
.listBox .number{
	width: 28px;
	line-height:20px;
	color:#333;
	background:#fff;
}
.listBox .listHd{
	padding:0 10px 0 0;
}
.listBox{
 margin-top:6px;

}
</style>
<body>
<div id="container_record" style="padding-top:15px;">
	<!-- <div class="titleDiv">
   		<div class="title">
   			流转记录
   		</div>
    </div> -->
    <div class="infoBd" id="datalist">
       
    </div>
<!--     <div class="tableBox"> -->
<!--         <div class="tableHeader">项目清单</div> -->
<!--         <table id="table" class="table-striped table-condensed table-bordered" data-height="350"></table> -->
<!--     </div> -->
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
$(function() {
    var onclickrowOobj = window.parent.onclickrowOobj;
    if (onclickrowOobj != null && onclickrowOobj != "" && onclickrowOobj != "null" && onclickrowOobj != "undefined") {
        getlzjl(onclickrowOobj);
        // 		showkdxm(onclickrowOobj);
    }
    //表格初始化
    //子页面高度传给父页面
    adjustTable();
    $(window).resize(adjustTable);

});
function showkdxm(onclickrowOobj) {
    //查询 开单记录
    var pageurl = '<%=contextPath%>/KQDS_CostOrderAct/selectByregno.act?regno=' + onclickrowOobj.seqId;
    $.ajax({
        type: "post",
        dataType: "json",
        url: pageurl,
        success: function(data) {
            var costnos = '';
            var list = data.data;
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    costnos += "\'" + list[i].costno + "\'" + ",";
                }
            }
        }
    });
}
function OrderDetail(costno) {
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno + '&type=lzjl';
    $("#table").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        columns: [{
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
                return '<span style="float:left; text-align:left;" class="time">' + value + '</span>';
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
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
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
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
            }
        },
        {
            title: '免除',
            field: 'voidmoney',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
            }
        },
        {
            title: '欠费金额',
            field: 'arrearmoney',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span style="float:right; text-align:right;">￥' + value + '</span>';
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
function adjustTable() {
    //var height = $('body').height();
    //window.parent.setparentHeight(height);
}

function getlzjl(onclickrowOobj) {
    //流转记录
    var pageurl1 = '<%=contextPath%>/KQDS_BCJLAct/selectPageLz.act';
    // 		var regno =onclickrowOobj.seqId;
    // 		var url = pageurl1+'?regno='+regno; 
    var url = pageurl1 + '?usercode=' + onclickrowOobj.usercode;
    $.axse(url, null,
    function(data) {
        var content = "";
        for (var i = 0; i < data.length; i++) {
            var zixun = "";
            if (data[i].askperson != "" && data[i].askperson != null) {
                // zixun = '<span>咨询：<span style="font-size:14px;">' + data[i].askperson + '</span></span>';
            }
            if(data[i].doctor != "" && data[i].doctor != null){
            	// zixun += '<span>医生：<span style="font-size:14px;">' + data[i].doctor + '</span></span>';
            }
            content += ' <div class="listBox">' 
            + '<div class="number"><span>' + (i + 1) + '、</span></div>' 
            + '<div class="listContent">' 
            + '<div class="listHd" style="height:20px;line-height:22px;">' 
            + '<span style="font-size:14px;">'+ data[i].bcmc + '  | </span>' 
            + '<span>操作人：<span style="font-size:14px;">' + getPerUserNameBySeqIdTB(data[i].createuser) + '</span></span>' + zixun + '<span>时间：' + data[i].createtime.substring(0,10) + '</span>' + '</div>' + '</div>' + '</div>';
        }
        $("#datalist").html(content);
    },
    function() {
        layer.alert('查询出错！' );
    });
}
function refreshlzjl() {
    var usercode = onclickrowOobj.usercode;
    $('#table1').bootstrapTable('refresh', {
        'url': pageurl1 + '?usercode=' + usercode
    });
}
</script>
</html>
