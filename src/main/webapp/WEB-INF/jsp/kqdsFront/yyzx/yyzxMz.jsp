<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String pushusercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>回访列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bingli.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js"></script>
<style>
#centerWrap{
	padding:0 15px;
}
.titleDiv{
	box-sizing: border-box;
    color: #373737;
    font-family: "Microsoft YaHei";
    height: 30px;
    position: relative;
    border-bottom:1px solid #ddd;
    margin-bottom:5px;
}
.titleDiv .title{
	font-size: 18px;
    height: 20px;
    line-height: 20px;
    float: left;
    display: block;
    margin-top: 5px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
</style>


</head>
<body>
	<div id="centerWrap" style="padding-top:15px;">
		<!-- <div class="titleDiv">
			<span class="title"></span>
		</div> -->
		<div class="tableBox">
			<div class="tableHeader"></div>
			<table id="table"
				class="table-striped table-condensed table-bordered"
				data-height="460"></table>
		</div>
		<div class="buttonBar" >
		</div>
	</div>
</body>
<script type="text/javascript">
var pushusercode = "<%=pushusercode%>";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var contextPath = '<%=contextPath%>';
var onclickrowObj = "";
var pageurl = '<%=contextPath%>/KQDS_Hospital_OrderAct/selectYyxxByUsercode.act';
var onclickrow = "";
var regno = "";
var isdelreg = 0;
$(function() {
    if (pushusercode == "" || pushusercode == null || pushusercode == "null" || pushusercode == undefined) {
        //不是从	消息推送页面跳转过来的
        onclickrowObj = window.parent.onclickrowOobj;
        //如果选中的不是挂号记录
        if (onclickrowObj == "" || onclickrowObj.ifcost == null) {
            regno = onclickrowObj.regno;
        } else {
            regno = onclickrowObj.seqId;
            isdelreg = onclickrowObj.del;
        }
        if (onclickrowObj.usercode != "" && onclickrowObj.usercode != null) {
            var tableheaderstr = "";
            //展示按钮
            tableheaderstr += onclickrowObj.username;
            tableheaderstr += "(" + onclickrowObj.usercode + ")的预约记录";
            $(".title").html(tableheaderstr);
        }
    }
    inittable();
});



//加载表格
function inittable() {
    var url = pageurl;
    if (pushusercode == "" || pushusercode == null || pushusercode == "null" || pushusercode == undefined) {
        url += "?usercode=" + onclickrowObj.usercode;
    } else {
        //从	消息推送页面跳转过来的
        url += "?usercode=" + pushusercode;
    }
    $('#table').bootstrapTable({
        url: url,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(){
        	setHeight();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isdelete == "已取消") {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [{
            title: '门诊',
            field: 'organizationname',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },{
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                	 return '<span title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '手机号',
            field: 'phonenumber1',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return '<span class="phone" title="' + value + '">' + value + '</span>';
                } else {
                    return '-';
                }
            }
        },
        {
            title: '第一咨询',
            field: 'firstaskperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '医生',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '项目分类',
            field: 'orderitemtype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '上门状态',
            field: 'zdoorstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已上门") {
                    return '<span style="color:green">已上门</span>';
                } else {
                    return '<span style="color:red">未上门</span>';
                }
            }
        },
        {
            title: '本次上门',
            field: 'orderstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已上门") {
                    return '<span style="color:green">已上门</span>';
                } else {
                    return '<span style="color:red">未上门</span>';
                }
            }
        },
        {
            title: '本次成交',
            field: 'cjstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, s, index) {
                if (value == "已成交") {
                    return '<span style="color:green">已成交</span>';
                } else {
                    return '<span style="color:red">未成交</span>';
                }
            }
        },
        {
            title: '开始时间',
            field: 'starttime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + getDatetimeToMinutes(value) + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '结束时间',
            field: 'endtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + getDatetimeToMinutes(value) + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '到诊时间',
            field: 'ordertime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + getDatetimeToMinutes(value) + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '预约状态',
            field: 'isdelete',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '取消人',
            field: 'delperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '取消原因',
            field: 'delreason',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="remark" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '创建人',
            field: 'cjr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档人',
            field: 'jdr',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档时间',
            field: 'jdsj',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time" title="' + value + '">' + getDatetimeToDay(value) + '</span>';
                } else {
                    return '';
                }
            }
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
    var url = "";
    if (lytype == "isparent") {
        url = pageurl + "?usercode=" + pushusercode;
    } else {
        url = pageurl + "?usercode=" + onclickrowObj.usercode;
    }
    $('#table').bootstrapTable('refresh', {
        'url': url
    });
};
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".buttonBar").outerHeight()-50
	})
}
</script>
</html>
