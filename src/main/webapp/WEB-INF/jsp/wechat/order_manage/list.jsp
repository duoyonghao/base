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
<title>微信预约管理</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" /> --%>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/weixinModule.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
html{
	height:100%;
}

.listWrap{
	margin:0;
}
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #f5f5f5;}
#gongzuol{margin-bottom: 15px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
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
.kqds_table{
	width:90%;
	align:center;
	/* margin-left: auto; */
	margin-right: auto;
}
	
.kqds_table  td { 
	color: #666;
	padding: 3px 5px 5px 5px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	font-weight: normal;
	line-height: 28px;
}
	
.kqds_table  select { 
	height: 28px;
	width:120px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}
input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}

.searchWrap .btnBar{		/*按钮组右浮动  */
	position:absolute;
	right:10px;
	bottom:10px;
	width:auto;
}
.searchWrap .btnBar .aBtn{
	float:left;
	margin:0 3px;
	display:block;
	color:#0E7CC9;
	background:#fff;
	height:28px;
	padding:0 15px;
	line-height:28px;
	border-radius:16px;
	text-align:center;
	border:1px solid #0E7CC9;
	font-size:13px;
}
.searchWrap .btnGroups .aBtn:hover{
	cursor:pointer;
	color:#fff;
	background:#0E7CC9;
}
.buttonBar{
	margin-top:17px;
}
.listWrap, .searchWrap{
	border:none;
}
.clearfix{
	border-top:1px solid #ddd;
	/* border-bottom:1px solid #ddd; */
	padding-left:10px;
}
.fixed-table-pagination div.pagination, .fixed-table-pagination .pagination-detail{
	margin-left:5px;
}
.operateDiv{
	text-align:center;
	padding-bottom:3px;
}
.operateDiv a{
	display:inline-block;
	margin:0 2px;    
	position: relative;
    top: 2px;
}
.fixed-table-container{
	
	
	border-radius: 6px;
} 
.fixed-table-pagination{
	overflow:hidden;
}
.fixed-table-header{
	border-radius:6px;
}
.fixed-table-body{
	border-bottom-left-radius:6px;
	border-bottom-right-radius:6px;
	border-bottom:1px solid #ddd;
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
}
</style>
<body>
	<div id="container">
		<div class="main">
			<div class="listWrap">
				<div class="listHd">
					<span class="title">预约管理列表</span>
				</div>
				<div class="listBd">
					<div class="tableBox">
						<table id="table" class="table-striped table-condensed table-bordered"></table>
					</div>
					<!-- <div id="tableBarDiv" class="buttonBar">
					</div> -->
				</div>
			</div>
			<!--查询条件-->
			<!-- <div class="searchWrap">
				<div class="cornerBox">查询条件</div>
				<div class="formBox">
					<table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">患者预约日期：</td>
				    			<td style="text-align:left;"> 
				    					<span class="unitsDate">
				    						<input type="text" placeholder="患者预约日期" id="orderdate" class="datetime"/> 
				                        </span>
				                </td>
				                <td style="text-align:right;">确认预约日期：</td>
				    			<td style="text-align:left;"> 
				    					<span class="unitsDate">
				    						<input type="text" placeholder="确认预约日期" id="confirmdate" class="datetime"/> 
				                        </span>
				                </td>
				                <td style="width: 80%;"></td>
				    		</tr>
				    	</table>
				    	
				    	<div class="btnBar">
				    		<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a> 
							<a href="javascript:void(0);" class="kqdsCommonBtn" id="query">查询</a> 
						</div>
				</div>
				
			</div> -->
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
<script type="text/javascript" src="<%=contextPath%>/static/js/wechat/kqds_wechat_order.js"></script>
</body>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var static_deleteurl = 'weixin/act/account/WXAccountAct/deleteBySeqId.act?1=1';
$(function() {

	var nowday = getNowFormatDate();
	$("#orderdate").val(nowday);
	
	getPageList();
    
    //查询条件 创建时间
    $(".datetime").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    //计算主体的宽度
    
    $(window).resize(function() {
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});

function detail(seqId) {
    var index = layer.open({
        type: 2,
        title: '预约详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['510px', '400px'],
        content: contextPath + '/WXCostOrderAct/toDetail.act?seqId=' + seqId
    });
}

//查询
$('#query').on('click',
function() {
    var orderdate = $("#orderdate").val();
    var confirmdate = $("#confirmdate").val();
    //加载表格
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
//清空
$('#clear').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		limit: params.limit,
            offset: params.offset,
	        orderdate: $("#orderdate").val(),
	        confirmdate: $("#confirmdate").val()
    };
    return temp;
}
function getPageList() {
    $("#table").bootstrapTable({
        url: contextPath + "/" + wxOrderObj.selectPage,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        cache: false,
        sidePagination: "server",
        onLoadSuccess:function(data){
        	setHeight();
        },
        //服务端处理分页
        columns: [
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '预约门诊',
            field: 'organizationname',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '微信昵称',
            field: 'nickname',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '手机号码',
            field: 'phonenumber1',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                if (value != null && value != "") {
                    return '<span>'+value+'</span>';
                } else {
                    return '<span>'+row.phonenumber+'</span>';
                }
            }
        },
        {
            title: '患者预约时间',
            field: ' ',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                return '<span>'+row.orderdate + " " + row.ordertime+'</span>';
            }
        },
        {
            title: '确认预约时间',
            field: 'confirmtime',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '预约项目',
            field: 'askitemname',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '咨询内容',
            field: 'askcontent',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return '<span style="width:300px;text-align:left;float:left;position:relative;top:-1px;" class="common">'+value+'</span>'
            }
        },
        {
            title: '预约状态',
            field: 'accounttype',
            align: 'center',
            
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                return getOrderStatus(row.orderstatus);
            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            
            formatter: function(value, row, index) {
                var menubutton = "";
                menubutton += "<span class='operateDiv'>";
                if (0 == row.orderstatus) {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="auditor(\'' + row.seqId + '\',1,\'' + row.usercode + '\',\'' + row.openid + '\',\'' + row.bindstatus + '\')">确认</a> ';
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="auditor(\'' + row.seqId + '\',2,\'' + row.usercode + '\',\'' + row.openid + '\',\'' + row.bindstatus + '\')" style="color:red;">拒绝</a> ';
                }
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                menubutton += "</span>"
                return menubutton;
            }
        }]
    });
}

//刷新整个页面
function refresh() {
	$("#table").bootstrapTable('refresh', {
        'url': contextPath + "/" + wxOrderObj.selectPage
    });
}
/**
 * 审核 
 */
function auditor(seqId, orderstatus, usercode, openid,bindstatus) {
    if (1 == orderstatus) {
        if (usercode && bindstatus == 0) {
            /** 已经绑定过用户 **/
            layer.open({
                type: 2,
                title: '审核',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ["650px", '450px'],
                content: contextPath+'/WXCostOrderAct/toNetOrder4wechat.act?1=1&wxOrderSeqId=' + seqId + '&usercode=' + usercode
            });
        } else {
            layer.open({
                type: 2,
                title: '审核',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ["750px", '80%'],
                content: contextPath+'/WXCostOrderAct/toHzjd4wechat.act?1=1&wxOrderSeqId=' + seqId + '&openid=' + openid
            });
        }
    }

    if (2 == orderstatus) {
        layer.prompt({
            title: '请输入拒绝原因：',
            formType: 0
        },
        function(auditorremark, index) {
            layer.close(index);
            var param = {
                seqId: seqId,
                orderstatus: orderstatus,
                auditorremark: auditorremark
            };
            var rtData = getDataFromServer(wxOrderObj.auditor, param);
            if (rtData.retState == 0) {
                layer.alert('操作成功！', {
                      
                    end: function() {
                        refresh();
                    }
                });
            }
        });
    }
}
//计算左侧表格高度保证一屏展示
function setHeight() {
   $("#table").bootstrapTable("resetView",{
	   pagination: true,
	   height:$(window).outerHeight()-$(".listWrap .listHd").outerHeight()-$(".clearfix").outerHeight()
   });
}
</script>
</html>