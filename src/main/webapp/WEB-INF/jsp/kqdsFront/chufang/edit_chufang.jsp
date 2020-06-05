<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);

	String static_seqId = request.getParameter("seqId"); // 编辑

	if (static_seqId == null) {
		static_seqId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>开具处方单</title><!-- 从右侧个人中心  中间 图标 进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/add_cost.css" />

</head>
<body>
	<div style="height: 98%;width: 100%;overflow-y:auto;border-radius:6px;">
		<table id="table" class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;">
			<thead style="background: #00A6C0 ;color:white; ">
				<tr>
					<th style="text-align: center; vertical-align: middle;width:22%;">治疗项目</th>
					<th style="text-align: center; vertical-align: middle;width:5%;">数量</th>
					<th style="text-align: center; vertical-align: middle;width:5%;">单位</th>
					
					<th style="text-align: center; vertical-align: middle;width:5%;">用法</th>
					<th style="text-align: center; vertical-align: middle;width:10%;">单次用量</th>
					<th style="text-align: center; vertical-align: middle;width:10%;">用药途径</th>
				</tr>
			</thead>
			<tbody style="background-color: #F0FFFF;text-align: center;"></tbody>
		</table>
		<table style="width: 100%;text-align: center;margin-top: 10px;">
			<tr>
			<td style="text-align: center; vertical-align: middle;width:5%;height:30px;">
				备注：
			</td>
			<td colspan="6" style="text-align: left; vertical-align: middle;width:95%;height:30px;">
				<textarea rows="2" style="text-align: left; vertical-align: middle;width:95%;height:30px;" id="remark" name="remark"></textarea>
			</td>
			</tr>
		</table>
	</div>
	<div class="footBar">
		<a href="javascript:void(0);" class="kqdsSearchBtn" id="qrhj">确认</a>
		<a href="javascript:parent.layer.close(frameindex);" class="kqdsCommonBtn" id="cancel">取消</a>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/chufang/chufang.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始
var static_seqId = "<%=static_seqId%>"; // ### 修改
var static_chuFangObj = null; // 处方对象
var static_costno = null;
$(function() {
    if (static_seqId != "") { //修改费用单
        static_chuFangObj = getChufangObjBySeqId(static_seqId);
    
    	// 备注字段赋值
    	$("#remark").val(static_chuFangObj.cf.remark);
    
        static_costno = static_chuFangObj.cf.costno;
        getChuFangDetailList(static_chuFangObj.cfdetailList);
    }
});

function getChuFangDetailList(cfdetailList) {
    for (var i = 0; i < cfdetailList.length; i++) {
        tdindex++;
        var tablehtml = "";
        var tabledata = cfdetailList[i];
        tablehtml += "<tr style='border: 1px solid #ddd;'>";
        //删除按钮0
        tablehtml += '<td style="height:30px;border-right: 1px solid #ddd;display:none;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,\'' + tabledata.isqfreal + '\',\'' + tabledata.seqId + '\')"><span style="color:red;">删除</span></a></td>';

        //项目编号1
        tablehtml += '<td style="display:none;">' + tabledata.itemno + '</td>';
        //治疗项目2
        tablehtml += '<td style="border-right: 1px solid #ddd;"><span style="float:left; text-align:left;" class="time" title=' + tabledata.itemname + '>' + tabledata.itemname + '</span></td>';
        //数量3
        tablehtml += '<td style="border-right: 1px solid #ddd;">' + tabledata.num + '</td>';
        //单位4
        tablehtml += '<td style="border-right: 1px solid #ddd;">' + tabledata.unit + '</td>';
        //用法5
        tablehtml += '<td style="border-right: 1px solid #ddd;">' + getDictSelectHtml("CHUFANG_YF", tabledata.cfusage) + '</td>';
        //用量6
        tablehtml += '<td style="border-right: 1px solid #ddd;"><input  type="text" min="1" style="width:180px;float:left; text-align:center;"  id="cfuselevel' + tdindex + '" value="' + tabledata.cfuselevel + '" ></td>';
        //用药途径7
        tablehtml += '<td style="border-right: 1px solid #ddd;">' + getDictSelectHtml("CHUFANG_YYTJ", tabledata.cfusemethod) + '</td>';
        //ID  8
        tablehtml += '<td style="display:none;">' + tabledata.seqId + '</td>';
        tablehtml += "</tr>";

        $("#table").find('tbody').append(tablehtml);
    }
}

$('#qrhj').on('click',
function() {
    if ($("#table tr").length > 1) {
        layer.confirm('确定修改此处方单？', {
            btn: ['是', '否'] //按钮
        },
        function() {
            editChuFang();
        });
    } else {
        layer.alert('请选择收费项目' );
    }
});

function getDictSelectHtml(dictType, defaultVal) {
    var html = "<select>";
    var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + dictType;
    $.axse(url, null,
    function(data) {
    	html += "<option value=''>请选择</option>";
        var list = data.list;
        if (list != null && list.length > 0) {
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                if (optionStr.seqId == defaultVal) {
                    html += "<option value='" + optionStr.seqId + "' selected='selected'>" + optionStr.dictName + "</option>";
                } else {
                    html += "<option value='" + optionStr.seqId + "' >" + optionStr.dictName + "</option>";
                }
            }
        }
        html += "</select>";
    },
    function() {
        layer.alert('查询出错！'  );
    });

    return html;
}
</script>
</html>
