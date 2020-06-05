<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
   String usercode = request.getParameter("usercode");
   if(usercode==null){
	   usercode="";
   }
   String regno = request.getParameter("regno");
   if(regno==null){
	   regno="";
   }
   
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>咨询记录</title><!-- 挂号页面 点击 咨询记录按钮进入  -->
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
var regno = "<%=regno %>";
var pageurl = '<%=contextPath%>/KQDS_ReceiveInfoAct/NoselectPage.act?usercode=' + usercode + '&regno=' + regno;
$(function() {
    gethisbl();
});
function gethisbl() {
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        },
        columns: [{
            title: '到院门诊',
            field: 'organizationname',
            align: 'center',
            
            visible: true,
            switchable: false,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '成交状态',
            field: 'regno',
            align: 'center',
            
            formatter: function(value, row, index) {
                var data = getStatus(value);
                var status = data.substring(0, data.indexOf("-"));
                return '<span>'+status+'</span>';
            }
        },
        {
            title: '消费状态',
            field: 'regno',
            align: 'center',
            
            formatter: function(value, row, index) {
                var data = getStatus(value);
                var status = data.substring(data.indexOf("-") + 1);
                return '<span>'+status+'</span>';
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            formatter: function(value, row, index) {
                return "<span  class='name'>" + value + "</span>";
            }
        },
        {
            title: '挂号分类',
            field: 'recesortname',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span>'+value+'</span>';
            }
        },
        {
            title: '咨询',
            field: 'askpersonname',
            align: 'center',
            
            formatter: function(value, row, index) {
                return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '接诊状态',
            field: 'askstatus',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value == 0) {
                    return "<span>未接诊</span>";
                } else {
                    return "<span>已接诊</span>";
                }
            }
        },
        {
            title: '就诊科室',
            field: 'deptnoname',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span>'+value+'</span>';
            }
        },
        {
            title: '情况备注',
            field: 'detaildesc',
            align: 'center',
            
            formatter: function(value, row, index) {
		        if (value != "" && value != null) {
		            return '<span title="' + value + '" style="width:100px;cursor:pointer;">' + value + '</span>';
		        }
		    }
        },
        {
            title: '咨询时间',
            field: 'createtime',
            align: 'center',
            formatter: function(value, row, index) {
                return '<span>'+value.substring(5, 10)+'</span>';
            }
        }]
    });
}
function getStatus(regno) {
    var regObj = getRegObjBySeqId(regno);

    var status = "未成交",
    ifcost = "未收费";

    if (regObj.cjstatus == 1) {
        status = "已成交";
    }
    if (regObj.ifcost == 1) {
        ifcost = "已收费";
    }

    return status + "-" + ifcost;;
}
</script>

</html>
