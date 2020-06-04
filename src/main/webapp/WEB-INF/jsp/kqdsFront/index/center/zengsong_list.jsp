<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	String static_docpriv = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_DOCTOR_SEQID);
	String usercode = request.getParameter("usercode");
	if (usercode == null) {
		usercode = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>赠送项目</title><!-- 首页-右侧个人中心区域-赠送项目 图标进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<style type="text/css">
.buttonBar .aBtn_big{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 88px;text-align: center;}
.buttonBar .aBtn_big:hover{color:#fff;background: #0e7cc9;}
.listWrap{
	margin:0;
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
    <div class="main">
        <div class="listWrap">
        	<div  class="titleDiv" style="padding-left:10px;">
				<div class="title">
					赠送项目列表
				</div>
			</div>
            <!-- <div class="listHd"><span class="hc-icon icon20 home-icon"></span>赠送项目列表</div> -->
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" data-height="450"></table>
                </div>
            </div>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

</body>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var zxpageurl = contextPath + '/KQDS_GiveItem_GiveRecordAct/getGiveRecordByMemberno.act';
var sypageurl = contextPath + '/KQDS_GiveItem_UseRecordAct/getUseRecordByUsercodeAndDoctor.act';
var perseqId = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var usercode = "<%=usercode%>";
var docpriv = "<%=static_docpriv%>";
$(function() {
	
    if (usercode == "") {
        layer.alert('请选择患者' );
        return;
    }

    //判断如果是医生 则根据医生查询分配给医生的赠送项目
    if (! isStrInArrayStringEach(personrole,docpriv)) {
        var zxpageurl1 = zxpageurl + '?usercode=' + usercode;
        //加载咨询看的表格
        initgivetable(zxpageurl1);
    } else {
        var sypageurl1 = sypageurl + '?usercode=' + usercode + '&doctor=' + perseqId;
        //加载医生看的表格 并操作
        initusetable(sypageurl1);
    }

    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
});
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".titleDiv").outerHeight()-15
	});
}
//咨询看的表格 赠送项目记录
function initgivetable(zxpageurl1) {
    $("#table").bootstrapTable({
        url: zxpageurl1,
        dataType: "json",
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        columns: [
                 
        {title: '项目编号',field: 'itemno',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                return value;
            }
        },
        {title: '项目名称',field: 'itemname',align: 'center', valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {title: '单位',field: 'unit',align: 'center',valign: 'middle',sortable: true},
        {title: '赠送数量',field: 'zsnum',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                return '<span id="zsnum' + index + '">' + value + '</span>';
            }
        },
        {title: '已使用',field: 'usenum',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                return '<span id="synum' + index + '">' + value + '</span>';
            }
        },
        {title: '剩余数量',field: 'nownum',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
            	return '<span id="nownum' + index + '">' + value + '</span>';
            }
        },
        {title: '赠送人',field: 'createuser',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
		        return '<span class="name" >'+value+'</span>';
		    }
        },
        {title: '赠送时间',field: 'createtime',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        }]
    });
}

//医生看的表格 使用赠送项目记录
function initusetable(sypageurl1) {
    //加载表格
    $('#table').bootstrapTable({
        url: sypageurl1,
        dataType: "json",
        singleSelect: false,
        cache: false,
        striped: true,
        columns: [
                  { title: '患者编号',field: 'usercode',align: 'center',valign: 'middle',sortable: true},
        {title: '患者姓名',field: 'username',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
		        return '<span class="name" >'+value+'</span>';
		    }
        },
        {title: '项目名称',field: 'itemname',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {title: '单位',field: 'unit',align: 'center',valign: 'middle',sortable: true},
        {title: '本次使用数量',field: 'nownum',align: 'center',valign: 'middle',sortable: true},
        {title: '使用人',field: 'createuser',align: 'center', valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
		        return '<span class="name" >'+value+'</span>';
		    }
        },
        {title: '使用时间',field: 'createtime',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time">' + value + '</span>';
            }
        },
        {title: '状态',field: 'status',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                if (value == '已操作') {
                    return '<span class="name" style="color:green;">已操作</span>';
                } else {
                    return '<span class="name" style="color:red;">未操作</span>';
                }
            }
        },
        {title: '操作时间',field: 'cztime',align: 'center',valign: 'middle',sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time">' + value + '</span>';
            }
        }]
    });
}


</script>
</html>