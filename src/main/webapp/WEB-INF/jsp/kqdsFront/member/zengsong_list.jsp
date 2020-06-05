<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String static_docpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_DOCTOR_SEQID);
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>右侧个人中心区域-赠送项目</title><!-- 首页-右侧个人中心区域-赠送项目 图标进入 -->
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
.titleDiv{
	box-sizing: border-box;
    color: #373737;
    font-family: "Microsoft YaHei";
    height: 30px;
    position: relative;
    margin-bottom: 5px;
    border-bottom:1px solid #ddd;
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
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
</style>
<body>
<div id="container">
	<div class="titleDiv">
		<span class="title" style="font-size:14px;color:#777;font-weight:bold;">赠送项目</span>
	</div>
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered" data-height="250"></table>
    </div>
    <div class="titleDiv">
		<span class="title" style="font-size:14px;color:#777;font-weight:bold;">赠送使用情况</span>
	</div>
    <div class="tableBox">
        <table id="table2" class="table-striped table-condensed table-bordered" data-height="250"></table>
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

</body>
<script type="text/javascript">
var listbutton = parent.listbutton;
var contextPath = '<%=contextPath%>';
var $table = $('#table');
var zxpageurl = contextPath + '/KQDS_GiveItem_GiveRecordAct/getGiveRecordByMemberno.act';
var sypageurl = contextPath + '/KQDS_GiveItem_UseRecordAct/getUseRecordByUsercodeAndDoctor.act';
var onclickrowobj = window.parent.onclickrowOobj; //父页面传值;
var perseqId = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var docpriv = "<%=static_docpriv%>";
$(function() {

    //判断如果是医生 则根据医生查询分配给医生的赠送项目
    //if (! isStrInArrayStringEach(personrole,docpriv)) {
        var zxpageurl1 = zxpageurl + '?usercode=' + onclickrowobj.usercode;
        //加载咨询看的表格
        initgivetable(zxpageurl1);
    //} else {
        var sypageurl1 = sypageurl + '?usercode=' + onclickrowobj.usercode;
        //加载医生看的表格 并操作
        initusetable(sypageurl1);
    //}

    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});

//咨询看的表格 赠送项目记录
function initgivetable(zxpageurl1) {
    $("#table").bootstrapTable({
        url: zxpageurl1,
        dataType: "json",
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [{
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '项目编号',
            field: 'itemno',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>'+value+'</span>';
            }
        },
        {
            title: '项目名称',
            field: 'itemname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>'+value+'</span>';
            }
        },
        {
            title: '赠送数量',
            field: 'zsnum',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span id="zsnum' + index + '">' + value + '</span>';
            }
        },
        {
            title: '已使用',
            field: 'usenum',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span id="synum' + index + '">' + value + '</span>';
            }
        },
        {
            title: '剩余数量',
            field: 'nownum',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span id="nownum' + index + '">' + value + '</span>';
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
                return '<span title="' + value + '">' + value + '</span>';
            }
        }]
    });
}

//医生看的表格 使用赠送项目记录
function initusetable(sypageurl1) {
    //加载表格
    $("#table2").bootstrapTable({
        url: sypageurl1,
        dataType: "json",
        singleSelect: false,
        cache: false,
        striped: true,
        columns: [
		{
		    title: '操作',
		    field: 'pkcode',
		    align: 'center',
		    formatter: function(value, row, index) {
		        var buttonstr = '';
		        for (var i = 0; i < listbutton.length; i++) {
	                if (row.status != '已操作') {
	                	if(row.doctorid==perseqId && listbutton[i].qxName == "caozuo"){
	                		 //状态为未操作时显示按钮
		                    buttonstr = '<a href="javascript:void(0);" style="color:red;" onclick="caozuo(\'' + row.seqId + '\')">操作</a>';
	                	}
	                }else{
	                	buttonstr = '<a href="javascript:void(0);" style="color:#00A6C0;">已操作</a>';
	                }
		        }
		        return "<span style='width:80px;'>" + buttonstr + "</span>";
		    }
		},       
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return value;
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
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return value;
            }
        },
        {
            title: '本次使用数量',
            field: 'synum',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return value;
            }
        },
        {
            title: '使用人',
            field: 'createuser',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" >' + value + '</span>';
            }
        },
        {
            title: '使用时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time">' + value + '</span>';
            }
        },
        {
            title: '状态',
            field: 'status',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" style="color:red;">' + value + '</span>';
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
            title: '操作时间',
            field: 'cztime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time">' + value + '</span>';
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false
        }
        ]
    });
}

//已操作该赠送项目
function caozuo(seqId) {
    var url = '<%=contextPath%>/KQDS_GiveItem_UseRecordAct/insertOrUpdate.act?seqId=' + seqId;
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    window.location.reload();
                }
            });
        }
    },
    function() {
        layer.alert('操作失败！' );
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
