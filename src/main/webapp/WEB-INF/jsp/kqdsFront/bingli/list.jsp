<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String usercode = request.getParameter("usercode");
	if (usercode == null) {
		usercode = "";
	}

	String regno = request.getParameter("regno");
	if (regno == null) {
		regno = "";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	String perPriv = person.getUserPriv();

	//只有院长才能编辑病历
	String yuanzhang = SysParaUtil.getSysValueByName(request, SysParaUtil.PRIV_YUANZHANG_SEQID);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>种植二期病历管理</title>
<style type="text/css">
label {
	cursor: pointer;
}

</style>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/bingli/zhongzhi.js"></script>
</head>
<body>
<div id="toolbar">
	<button id="addBtn" class="btn btn-danger" style="display: none;">
		<i class="glyphicon glyphicon-plus"></i> 新增
	</button>
	<input type="radio" name="mtype_header" value="2" id="mtype_2" style="cursor: pointer;" checked="checked"><label for="mtype_2">种植一期</label>
	<input type="radio" name="mtype_header" value="3" id="mtype_3" style="cursor: pointer;"><label for="mtype_3">术后拆线</label>
	<input type="radio" name="mtype_header" value="4" id="mtype_4" style="cursor: pointer;"><label for="mtype_4">种植二期</label>
	<input type="radio" name="mtype_header" value="5" id="mtype_5" style="cursor: pointer;"><label for="mtype_5">修复</label>
	
</div>
<table id="table" class="table-striped table-condensed table-bordered"  data-height="500"></table>
<script type="text/javascript">
var static_usercode = '<%=usercode%>';
var static_regno = '<%=regno%>';
var static_perPriv = '<%=perPriv%>';
var static_yuanzhang = '<%=yuanzhang%>';
var static_personroleother = "<%=person.getUserPrivOther()%>"; // 辅助角色
var static_userId = '<%=person.getUserId()%>';
var static_table_url = '<%=contextPath%>/KQDS_MedicalRecordAct/selectPageByUsercod.act';
$(function() {
    gethisbl();
    
    if(static_regno){
    	$("#addBtn").css("display","inline");
    }
});

//弹出一个iframe层
$('#addBtn').on('click',
function() {
    var mtype = $('input[name="mtype_header"]:checked ').val(); //0 初诊  1 复诊 2种植一期 3 术后拆线
    var titleStr = "";
    var requrl = "";
    if (mtype == '2') {
    	 titleStr = "【种植一期】";
    	 requrl = contextPath+'/KQDS_BLK_ZhongzhiAct/toZhongZhiYiQi_Add.act?usercode=' + static_usercode + '&regno=' + static_regno;
    }
   
    if (mtype == '3') {
    	titleStr = "【种植复查】";
        requrl = contextPath+'/KQDS_BLK_ChaiXianAct/toZhongZhi_Suture_Removal_Add.act?usercode=' + static_usercode + '&regno=' + static_regno;
    }

    if (mtype == '4') {
    	titleStr = "【种植二期】";
        requrl = contextPath+'/KQDS_BLK_Zhongzhi2Act/toZhongZhiErQi_Add.act?usercode=' + static_usercode + '&regno=' + static_regno;
    }

    if (mtype == '5') {
    	titleStr = "【种植修复】";
        requrl =  contextPath+'/KQDS_BLK_XiuFuAct/toZhongZhi_XiuFu_Add.act?usercode=' + static_usercode + '&regno=' + static_regno;
    }
    
	layer.open({
        type: 2,
        title: '新建病历' + titleStr,
        maxmin: true,
        shadeClose: true,
        zIndex: 100,
        area: ['95%', '98%'],
        content: requrl
    });
    
    
});
$('input[name="mtype_header"]').change(function() {
	refresh();
});
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': static_table_url
    });
}
function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    		usercode: static_usercode,
    		mtype: $('input[name="mtype_header"]:checked ').val()
    };
    return temp;
}
function setHeight(){
	var tableHeight=$(window).outerHeight()-$("#toolbar").outerHeight();
	$("#table").bootstrapTable("resetView",{
		height:tableHeight-5
	});
}
function gethisbl() {
    $("#table").bootstrapTable({
        url: static_table_url,
        dataType: "json",
        singleSelect: false,
        striped: true,
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            detail(row.seqId, row.mtype);
        },
        columns: [{
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle'
        },{
            title: '患者姓名',
            field: 'username',
            align: 'center',
            valign: 'middle'
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
                } else if (value == "1") {
                    html = '<span class="label label-success">复诊</span>';
                } else if (value == "2") {
                    html = '<span class="label label-success">种植1期</span>';
                } else if (value == "3") {
                    html = '<span class="label label-success">术后拆线</span>';
                } else if (value == "4") {
                    html = '<span class="label label-success">种植二期</span>';
                } else if (value == "5") {
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
                    return "<center><button onclick=\"openAttach('" + row.usercode + "','" + static_regno + "')\" class=\"btn btn-xs btn-primary\"><i class=\"ace-icon fa fa-eye bigger-120\"></i></button></center>";
                } else {
                    return "";
                }

            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var x = "";
                if(static_regno){
                	x += '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:#00A6C0;" onclick="addblk(\'' + row.seqId + '\',\'' + row.mtype + '\',\'1\')">&nbsp;添加到自用</a>';
                    x += '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:#00A6C0;" onclick="addblk(\'' + row.seqId + '\',\'' + row.mtype + '\',\'0\')">&nbsp;添加到标准</a>';
                }
                x += '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:#00A6C0;" onclick="bingli_detail(\'' + row.seqId + '\',\'' + row.mtype + '\',\'detail\')">&nbsp;详情</a> ';
                x += '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:#00A6C0;" onclick="printHtmlTable(\'' + row.seqId + '\')">&nbsp;打印</a>';
                if (row.status == "1" && static_regno) { // 暂存的病历才可以恢复
                    x += '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="huifubingli(\'' + row.seqId + '\',\'' + row.mtype + '\')">&nbsp;恢复</a> ';
                }
                // 主角色或辅助角色是院长的话，可以编辑病历
                if (static_regno && 'admin' == static_userId || isStrInArrayStringEach(static_perPriv, static_yuanzhang) || isStrInArrayStringEach(static_yuanzhang, static_personroleother)) {
                    x += '<a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="huifubingli(\'' + row.seqId + '\',\'' + row.mtype + '\',\'edit\')">&nbsp;编辑</a> ';
                }

                return x;
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
            title: '创建人',
            field: 'createusername',
            align: 'center',
            sortable: true,
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
        }
        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
    });
}

//查看图片
function openAttach(usercode, regno) {
    parent.layer.open({
        type: 2,
        title: '附件列表',
        shadeClose: true,
        area: ['800px', '550px'],
        content: contextPath + '/KQDS_MedicalRecordAct/toAttachList.act?usercode=' + usercode
    });
}

/*#################################恢复病历#######################################*/
/**
 * flag 值为 edit时，表示编辑  值为detail时表示详情
 */
function huifubingli(seqId, mtype, flag) {
    var winTitle = "恢复病历";
    var titleStr = "";
    var requrl = '';

    if (mtype == '2') {
    	titleStr = "【种植一期】";
        requrl = contextPath+'/KQDS_BLK_ZhongzhiAct/toZhongZhiYiQi_Huifu.act';
    }
    
    if (mtype == '3') {
    	titleStr = "【种植复查】";
        requrl = contextPath+'/KQDS_BLK_ChaiXianAct/toZhongZhi_Suture_Removal_Huifu.act';
    }

    if (mtype == '4') {
    	titleStr = "【种植二期】";
        requrl = contextPath+'/KQDS_BLK_Zhongzhi2Act/toZhongZhiErQi_Huifu.act';
    }

    if (mtype == '5') {
    	titleStr = "【种植修复】";
        requrl = contextPath+'/KQDS_BLK_XiuFuAct/toZhongZhi_XiuFu_Huifu.act';
    }

    requrl += '?seqId=' + seqId + '&mtype=' + mtype + '&usercode=' + static_usercode;

    if (flag == 'edit') {
        requrl += '&editFlag=' + flag;
        winTitle = "编辑病历";
    }

    layer.open({
        type: 2,
        title: winTitle + titleStr,
        shade: 0.6,
        shadeClose: false,
        area: ['95%', '98%'],
        content: requrl
    });
}

/**
 * 病历详情
 */
function bingli_detail(seqId, mtype, flag) {
    var winTitle = "病历详情";
    var titleStr = "";
    var requrl = '';

    if (mtype == '2') {
    	titleStr = "【种植一期】";
        requrl = contextPath+'/KQDS_BLK_ZhongzhiAct/toZhongZhiYiQi_Detail.act';
    }
    
    if (mtype == '3') {
    	titleStr = "【种植复查】";
        requrl = contextPath+'/KQDS_BLK_ChaiXianAct/toZhongZhi_Suture_Removal_Detail.act';
    }

    if (mtype == '4') {
    	titleStr = "【种植二期】";
        requrl = contextPath+'/KQDS_BLK_Zhongzhi2Act/toZhongZhiErQi_Detail.act';
    }

    if (mtype == '5') {
    	titleStr = "【种植修复】";
        requrl = contextPath+'/KQDS_BLK_XiuFuAct/toZhongZhi_XiuFu_Detail.act';
    }

    requrl += '?seqId=' + seqId + '&mtype=' + mtype + '&usercode=' + static_usercode;

    layer.open({
        type: 2,
        title: winTitle + titleStr,
        shade: 0.6,
        shadeClose: false,
        area: ['95%', '98%'],
        content: requrl
    });
}
</script>
</body>
</html>
