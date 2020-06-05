<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
   String usercode = request.getParameter("usercode");
   String status = request.getParameter("status");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>历史病历</title><!-- 挂号和费用添加页面  病历历史 按钮进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
</style>
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
var status = "<%=status %>";
var pageurlhis = '<%=contextPath%>/KQDS_MedicalRecordAct/selectPageByUsercod.act?usercode=' + usercode + '&status=' + status;
$(function() {
    gethisbl();
});
function gethisbl() {
    $("#table").bootstrapTable({
        url: pageurlhis,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        },
        columns: [{
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>"
            }
        },
        {
            title: '病历种类',
            field: 'mtype',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (value == "0") {
                    html = '<span class="label label-success">初诊</span>';
                }
                if (value == "1") {
                    html = '<span class="label label-success">复诊</span>';
                }
                if (value == "2") {
                    html = '<span class="label label-success">种植一期</span>';
                }
                if (value == "3") {
                    html = '<span class="label label-success">种植复查</span>';
                }
                if (value == "4") {
                    html = '<span class="label label-success">种植二期</span>';
                }
                if (value == "5") {
                    html = '<span class="label label-success">种植修复</span>';
                }
                return html;
            }
        },
        {
            title: '影像资料',
            field: 'attachmentid',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var attachId = row.attachmentid;
                var attachmentName = row.attachmentname;
                if (attachId != "") {
                    return "<center><button onclick=\"openAttach('" + usercode + "')\" class=\"btn btn-xs btn-primary\"><i class=\"ace-icon fa fa-eye bigger-120\"></i></button></center>";
                } else {
                    return "";
                }
            }
        },
        {
            title: 'attachmentname',
            field: 'attachmentname',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
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
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '创建人',
            field: 'createusername',
            align: 'center',
            sortable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var x = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                return x;
            }
        }]
    });
}
function detail(id) {
    parent.layer.open({
        type: 2,
        title: '详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['920px', '440px'],
        content: contextPath + '/KQDS_MedicalRecordAct/toMedicalrecordDetail.act?seqId=' + id
    });
}
function openAttach(attachId, attachmentName) {
    parent.layer.open({
        type: 2,
        title: '附件列表',
        shadeClose: true,
        shade: false,
        maxmin: true,
        //开启最大化最小化按钮
        area: ['800px', '440px'],
        content: contextPath + '/KQDS_MedicalRecordAct/toAttachList.act?usercode=' + usercode
    });
}
</script>

</html>
