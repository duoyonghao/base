<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
   String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>牙模记录</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<body>
    <div class="tableBox">
       <table id="table" class="table-striped table-condensed table-bordered" data-height="318"></table>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var usercode = "<%=usercode %>";
var pageurl = '<%=contextPath%>/KQDS_OutProcessingSheetAct/selectPageWjg.act';
$(function() {
    getwjg();
});
function getwjg() {
    //流转记录
    var url = pageurl + '?usercode=' + usercode;
    $('#table').bootstrapTable({
        url: url,
        dataType: "json",
        cache: false,
        striped: true,
        columns: [{
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
            	if("未发件" == row.statusname){
            		return row.createtime;
            	}
            	if("已发件" == row.statusname){
            		return row.fajiantime;
            	}
            	if("已回件" == row.statusname){
            		return row.huijiantime;
            	}
            	if("戴走" == row.statusname){
            		return row.pickuptime;
            	}
            	if("返工" == row.statusname){
            		return row.fangongtime;
            	}
            	if("作废" == row.statusname){
            		return row.zuofeitime;
            	}
            	
            	return "-";
            	
            }
        },
        /* {
            title: '回件时间',
            field: 'pickuptime',
            align: 'center',
            valign: 'middle'
        }, */
        {
            title: '加工单状态',
            field: 'statusname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var t = "";
                if (value == "未发件") {
                    t = '<span style="color:red;">' + value + '</span>';
                } else if (value == "戴走") {
                    t = '<span style="color:green;">' + value + '</span>';
                } else {
                    t = '<span>' + value + '</span>';
                }
                return t;
            }
        },
        {
            title: '医生',
            field: 'doctornoname',
            align: 'center',
            valign: 'middle'
        }]
    });
}
</script>

</html>
