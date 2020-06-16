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
<title>记录列表</title><!-- 从咨询中心 > 转诊按钮 进入  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.min.css" />

<style type="text/css">
/*去掉position: fixed;使转诊按钮不置底 */
.position-bottom{position:inherit;right:15px;left: 15px;background: #fff;padding-bottom: 20px;text-align: center;}
</style>
</head>
<body>
<div id="container">
<!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
            	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable()">生成报表</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn search" id="query">查询</a>
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
						<label>指定日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv" >
						 <label>指定患者</label>
						<div class="kv-v">
							   <input type="text" placeholder="患者编号/姓名" id="queryinput" name="queryinput">
						</div>
					</div>
					<div class="kv ">
			            <label>指定人</label>
			            <div class="kv-v">
							 <input type="hidden" name="createuser" id="createuser"  class="form-control" />
							 <input type="text"  class="form-control" id="createuserDesc" name="createuserDesc" placeholder="指定人" readonly onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"  ></input>	
			        	</div>
      	 			 </div>
      	 			 <div class="kv ">
			            <label>当前客服</label>
			            <div class="kv-v">
							 <input type="hidden" name="toper" id="toper"  class="form-control" />
							 <input type="text"  class="form-control" id="toperDesc" name="toperDesc" placeholder="当前客服" readonly onClick="javascript:single_select_user(['toper', 'toperDesc'],'',1);"  ></input>	
			       		 </div>
			       	</div>
                </div>
            </div>
        </div> 
    <div class="tableBox">
       <table id="table" class="table-striped table-condensed table-bordered" data-height="450"></table>
    </div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var pageurl = '<%=contextPath%>/KQDS_ChangeKeFuAct/selectNoPage.act';
//初始化表头，返回空的数据
var nullUrl = '<%=contextPath%>/UtilAct/intTableHeader.act';
var qxnameArr = ['glr_scbb'];
var func = ['exportTable'];
$(function() {
    //获取当前页面所有按钮
    //getButtonPowerByPriv(qxnameArr, func);
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "buttom-right"
    });
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    getlzjl(nullUrl);
    
    setHeight();
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        queryinput: $('#queryinput').val(),
        createuser: $('#createuser').val(),
        toper: $('#toper').val()
    };
    return temp;
}
function getlzjl(requrl) {
    //流转记录
    $('#table').attr("data-height", $(window).height() - 100);

    $('#table').bootstrapTable({
        url: requrl,
        dataType: "json",
        queryParams: queryParams,
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
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '修改前客服',
            field: 'oldper',
            align: 'center',
            valign: 'middle',
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
            title: '当前客服',
            field: 'toper',
            align: 'center',
            valign: 'middle',
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
            title: '指定原因',
            field: 'remark',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time">' + value + '</span>';
                }
            }
        },
        {
            title: '指定人',
            field: 'xgr',
            align: 'center',
            valign: 'middle',
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
            title: '指定时间',
            field: 'xgsj',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="time">' + value + '</span>';
                } else {
                    return "";
                }
            }
        }]
    });
}
$('#query').on('click',
function() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
function openLayerDialogResize(actionUrl, width, height) {
    layer.open({
        type: 2,
        title: '人员选择',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: ['550px', '450px'],
        content: actionUrl
    });

}

//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    $('#table2').bootstrapTable('resetView', {
        height: baseHeight - 150
    });
}

function refresh() {
    if (parent.initTable) {
        parent.initTable(0); // 重新加载等待治疗列表
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
</script>

</html>
