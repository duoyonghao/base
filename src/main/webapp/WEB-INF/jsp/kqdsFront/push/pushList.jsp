<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/inc/header.jsp" %>
<%
//是否超时 1超时提醒 0工作提醒
  String iscs = request.getParameter("iscs");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>推送记录列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
	
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<style type="text/css">
/* #table td {
	height:30px;
} */
#table tr{
	cursor:pointer;
}
#table td>span.label,
#table1 td>span.label,
#table2 td>span.label,
#listTable td>span.label,
#dykdxm td>span.label,
#chufang td>span.label
{padding:0 10px;box-sizing:border-box;font-size:13px;}
.label-primary{
	/* background:#FC4F41;
	color:#fff; */
	background:#00A6C0 ;
	padding:0 10px;
	font-weight:normal;
	border-radius:16px;
}
.pagination > .active > a, .pagination > .active > span, .pagination > .active > a:hover, .pagination > .active > span:hover, .pagination > .active > a:focus, .pagination > .active > span:focus{
	background-color: #00A6C0 ;
    border-color: #00A6C0 ;
}
.pagination > li > a, .pagination > li > span{
	color:#00A6C0;
}
#toolbar{
	padding-bottom:5px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	
}
</style>  
</head>
<body class="hold-transition skin-blue sidebar-mini">
<section class="content">
	<!-- <div class="row" > -->
 		<div class="col-sm-12">
         <div id="toolbar">
	         <span class="commonText">分类：</span>
	         <select name="notifytype" id="notifytype">
	         	<option value="">请选择</option>
	         	<option value="挂号">挂号</option>
	         	<option value="回访">回访</option>
	         	<option value="推广">推广</option>
	         	<option value="加工">加工</option>
	         	<option value="缴费">缴费</option>
	         	<option value="就诊提醒">就诊提醒</option>
	         	<option value="开单">开单</option>
	         	<option value="门诊">门诊</option>
	         	<option value="退费">退费</option>
	         	<option value="预收退款">预收退款</option>
	         </select>
			 <span class="commonText">推送状态：</span>
			  <select name="pcpushed" id="pcpushed">
	         	<option value="">请选择</option>
	         	<option value="已推送">已推送</option>
	         	<option value="未推送">未推送</option>
	         </select>
	         <span class="commonText">是否已读：</span>
			  <select name="pcpushreaded" id="pcpushreaded">
	         	<option value="">请选择</option>
	         	<option value="已读">已读</option>
	         	<option value="未读">未读</option>
	         </select>
			 <button id="btn_edit" onclick="qbyd()" type="button" class="kqdsCommonBtn" style="float: right;">
			 	<span aria-hidden="true"></span>全部已读
			 </button>
			 <button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn" style="float: right;margin-right: 10px;">
			 	<!-- <span class="kqdsCommonBtn" aria-hidden="true"></span> -->查询
			 </button>
         </div>
         <div class="tableBox">
         	<table id="table" class="table-striped table-condensed table-bordered" data-height="450">
			</table>
         </div>
			
		</div>
	</div>
</section>
<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = '<%=contextPath%>/KQDS_PushAct/selectPage.act';
var $table = $('#table');
var onclickrowOobj = "";
var iscs = '<%=iscs%>';
function getTableHeight(){
	var tableHeight=$(window).outerHeight()-$("#toolbar").outerHeight()-$(".clearfix").outerHeight();
	return tableHeight;
}
$(function() {
	var tableHeight=getTableHeight()-10;
    $(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        pagination: true,//是否显示分页（*）
	    pageSize: 30,
	    pageList : [15, 20, 25,30],//可以选择每页大小
        cache: false,
        clickToSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onLoadSuccess: function(data) { //加载成功时执行
        },
        columns: [{
            title: '分类',
            field: 'notifytype',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span class="label label-primary">' + row.notifytype + '</span>';
            },

        },
        {
            title: '内容',
            field: 'content',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span style="width:100%;float:left; text-align:left;">' + value + '</span>';
            }
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '推送状态',
            field: 'pcpushed',
            align: 'center',
            
            formatter: function(value, row, index) {
                var html = "";
                if (row.pcpushed == "0") {
                    //未推送
                    html += '<span class="label label-danger">未推送</span>';
                } else {
                    //已推送
                    html += '<span class="label label-info">已推送</span>';
                }
                return html;
            }
        },
        {
            title: '是否已读',
            field: 'pcpushreaded',
            align: 'center',
            
            formatter: function(value, row, index) {
                var html = "";
                if (row.pcpushreaded == "0") {
                    //未推送
                    html += '<span class="label label-danger">未读</span>';
                } else {
                    //已推送
                    html += '<span class="label label-info">已读</span>';
                }
                return html;
            }
        },
        {
            title: '推送类型',
            field: 'isnowpush',
            align: 'center',
            
            formatter: function(value, row, index) {
                var html = "";
                if (row.isnowpush == "0") {
                    //未推送
                    html += '<span class="label label-danger">即时推送</span>';

                } else {
                    //已推送
                    html += '<span class="label label-info">定时推送</span>';
                }
                return html;
            }
        },
        {
            title: '定时推送时间',
            field: 'targetpushtime',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        //设置为 已推送，已读
        var senddata = {};
        senddata.seqId = onclickrowOobj.seqId;
        senddata.pcpushreaded = "1";
        var date = '<%=YZUtility.getCurDateTimeStr() %>';
        senddata.pcpushreadedtime = date;
        updateReadStatus(senddata);
        var type = onclickrowOobj.notifytype;
        var url = contextPath + onclickrowOobj.remindurl;
        getPushAllnum(); // 更新 首页右上角的 消息未读数值
        if (type == "挂号") {
            //等待治疗列表
            //parent.document.getElementById("iframe").contentWindow.initTable(0);
        } else if (type == "开单") {
            //等待治疗列表
            //parent.document.getElementById("iframe").contentWindow.getOrderlist(1);
        } else if (type == "缴费") {
            //今日患者
            //parent.document.getElementById("iframe").contentWindow.initTable(5);
        } else if (type == "门诊") {
            parent.layer.open({
                type: 2,
                title: type,
                shadeClose: true,
                shade: 0.6,
                area: ['90%', '90%'],
                content: url,
                //iframe的url
            });
        } else if (type == "回访" || type == "回访超时") {
            parent.layer.open({
                type: 2,
                title: '回访记录',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ['1000px', '98%'],
                content: url + '&type=isparent&noadd=1&is_Wd_oper=1'
            });
        } else if (type == "推广" || type == "推广超时") {

		} else if (type == "加工") {
            parent.layer.open({
                type: 2,
                title: '加工单',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ['90%', '90%'],
                content: url
            });
        } else if (type == "退费") {
            parent.layer.open({
                type: 2,
                title: '退款申请',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ['90%', '95%'],
                content: url
            });
        } else if (type == "就诊提醒") {
            var jztx = $(parent.document.getElementById("iframe"));
            jztx.attr({
                "src": contextPath + '/KQDS_JzqkAct/toTx.act?menuId=600311'
            });
            parent.layer.close(frameindex); //再执行关闭 */
        } else if (type == "库存报警") {
            parent.layer.open({
                type: 2,
                title: '库存报警',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ['90%', '90%'],
                content: contextPath + '/KQDS_Ck_Goods_DetailAct/toGoodsgJ.act'
            });
        } else {
            parent.layer.open({
                type: 2,
                title: type,
                shadeClose: true,
                shade: 0.6,
                area: ['90%', '95%'],
                content: url,
                //iframe的url
            });
        }
    });
});
function queryParams(params) {
    var temp = {
        offset: params.offset,
        limit: params.limit,
        iscs: iscs,
        notifytype: $('#notifytype').val(),
        pcpushreaded: $('#pcpushreaded').val(),
        pcpushed: $('#pcpushed').val()
    };
    return temp;
}
function getPushAllnum() {
    var url = '<%=contextPath%>/KQDS_PushAct/selectPageNum.act';
    $.axseSubmit(url, null, null,
    function(data) {
        parent.parent.setPushNum(data.total);
    },
    function() {

});
}
//全部已读
function qbyd() {
    //var tableList = $('#table').bootstrapTable('getData');
    var seqIds = "";
//  for (var i = 0; i < tableList.length; i++) {
//      if (tableList[i].pcpushed == 1 && tableList[i].pcpushreaded == 0) {
//      	seqIds += "\'"+tableList[i].seqId+"\'" +","
//      }else if(tableList[i].pcpushed == 0 && tableList[i].pcpushreaded == 0){
//      	seqIds += "\'"+tableList[i].seqId+"\'" +","
//      }
//  }
   
    yts(seqIds);
}
//已推送操作
function yts(seqIds) {
    //设置为 已推送，已读
    var senddata = {};
    //这里是分页 全部已读，如果想所有的设置全部已读  不传seqId
   // senddata.seqId = seqIds;
    var date = '<%=YZUtility.getCurDateTimeStr() %>';
    senddata.pcpushedtime = date;
    updateReadStatus(senddata);
    refresh();
}
function refresh() {
    getPushAllnum();
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });

}
function updateReadStatus(sendata) {
    var url = '<%=contextPath%>/KQDS_PushAct/updateReadStatus.act';
    $.axseSubmit(url, sendata,
    function(data) {
        if (data.retState == "0") {
            getPush();
        } else {

		}
    },
    function() {

});
}
</script>



</body>
</html>
