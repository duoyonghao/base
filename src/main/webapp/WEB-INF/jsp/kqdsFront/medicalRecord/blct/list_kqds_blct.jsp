<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>病历词条管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<!-- 后台维护页面 -->
<style type="text/css">
	.leftDiv{
		width:150px;
		float:left;
		height:100%;
		/* background:beige; */
		border:1px solid #ddd;
		overflow-y:auto;
	}
	.rightDiv{
		margin-left:160px;
		/* background:deepskyblue; */
		height:100%;
		padding-right:10px;
		overflow-y:auto;
	}
	.kqdsSearchBtn{
		padding:0 10px;
	}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">

<section class="content">
	<div>
		<div class="leftDiv">
			<div id="treeDemo" class="ztree"></div>
		</div>
	 	<div class="rightDiv">
 			<div id="toolbar" style="overflow:hidden;">
				<button id="parentIframe" class="kqdsSearchBtn bigBtn" >
				       <i class="glyphicon glyphicon-plus"></i> 新增
				</button>
				<button id="jinyong" class="kqdsSearchBtn" onclick="updateFlagBySeqIds(1);">
				<i class="glyphicon glyphicon-remove"></i> 禁用
				</button>
				<button id="qiyong" class="kqdsSearchBtn" onclick="updateFlagBySeqIds(0);">
					<i class="glyphicon glyphicon-ok"></i> 启用
				</button>
				<span class="commonText">词条类别：</span><select class="dict" tig="blct124" name="cttype" id="cttype"></select>
				<span class="commonText">二级分类：</span> <select class="select2"  name="ctnexttype" id="ctnexttype"><option value="">请选择</option></select>
				<span class="commonText">词条名称：</span><input type="text" name="ctname" id="ctname" >
				<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="margin:0 0 0 5px;">
				 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
				</button>
			</div>
			<div class="tableBox">
				<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
			</div>
		</div>
	</div>
</section>
<script>
var pageurl = '<%=contextPath%>/YZBlctAct/selectPage.act';
var static_updateurl = 'YZBlctAct/updateFlagBySeqIds.act?1=1';
$(function() {
	initDictSelectByClass();
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        toolbar: '#toolbar',
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        sidePagination: "server",
        //服务端处理分页
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
        },{
            title: '一级类别',
            field: 'cttypename',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '二级类别',
            field: 'ctnexttypename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return value;
                }
            }
        },
        {
            title: '词条内容',
            field: 'ctname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true
        },
        {
            title: '是否禁用',
            field: 'useflag',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "<span style='color:#00A6C0;'>启用</span>";
                }
                if (value == "1") {
                    return "<span style='color:red;'>禁用</span>";
                }
            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var html = "";
                if ("<%=person.getSeqId()%>" == 1) {
                	html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="bzblkEdit(\'' + row.seqId + '\',\'' + row.organization + '\')"><span style="color:#00A6C0;">编辑</span></a> ';
                	html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red;">删除</span></a> ';
                }
                html += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                return html;
            }
        }]
    });
    
    zTreeInit();
    
    /* 页面重置，重新设置table的高度 */
    $(window).resize(function(){
		setHeight(); 
	});
});
function queryParams(params) {
    var temp = { 
   		offset : params.offset,
       	limit :	 params.limit,
        organization: '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        ctname: $('#ctname').val(),
        cttype: $('#cttype').val(),
        ctnexttype: $('#ctnexttype').val()
    };
    return temp;
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}
$('#cttype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
		initCostSortSelect2Level('ctnexttype',this.value);
	}
});
//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
  	    function(row) {
  	        return row;
	});
}
//复选框
function stateFormatter(value, row, index) {
    return {
        disabled: false,
        checked: false
    }
}
/**
 *@param flag 0 启用  1禁用
 */
function updateFlagBySeqIds(flag) {
    var opername = null;
    if ("0" == flag) {
        opername = "启用"
    }
    if ("1" == flag) {
        opername = "禁用"
    }

    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选要' + opername + '的记录', {
        });
        return false;
    }

    // 获取要删除的用户id，以逗号分隔
    var seqIds = "";
    for (var i = 0; i < selectList.length; i++) {
        seqIds += selectList[i].seqId + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);

    //询问框
    layer.confirm('确定' + opername + '？', {
        btn: [opername, '取消'] //按钮
    },
    function() {
        var reqUrl = static_updateurl + "&seqId=" + seqIds + "&flag=" + flag;
        var serverData = getDataFromServer(reqUrl);
        if (serverData) {
            layer.alert(opername + '成功', {
                end: function() {
                    refreshTable();
                }
            });
        }
    });
}

function zTreeInit() {
    //异步加载
    var url = contextPath + '/YZDictAct/getDictTree4Blct.act';

    var setting = {
            view: {
                showIcon: true // 去掉图标
            },
            async: {
                enable: true,
                url: url,
                autoParam: ["id", "name=n", "level=lv"],
                otherParam: {
                    "otherParam": "zTreeAsyncTest",
                    "organization": "<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>"
                },
                dataFilter: ajaxDataFilter,
                type: "get"
            }
        };
    setting['callback'] = {
        onClick: onclick
    };
    $.fn.zTree.init($("#treeDemo"), setting);
}
//解析树 返回数据
function ajaxDataFilter(treeId, parentNode, responseData) {
    var tree;
    if (responseData.retState == "0") {
        tree = responseData.retData;
    }
    return tree;
};
function onclick(e, treeId, treeNode) {
    var url = "";
    if (treeNode.level == 0) { // 一级分类
    	$("#cttype").val(treeNode.id);
    	$("#cttype").trigger("change");
    	$("#ctnexttype").val("");
        
    } else { // 二级分类
    	$("#cttype").val(treeNode.pId);
    	$("#cttype").trigger("change");
    	$("#ctnexttype").val(treeNode.id);
	}

    refresh(); // 执行查询

}

function detail(id,organization) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getCurrentOrganizationName(request)%>：病历词条详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '300px'],
        content: contextPath+'/YZBlctAct/toBlctDetail.act?seqId=' + id +'&organization='+organization
    });
}
function del(id) {

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/YZBlctAct/deleteObj.act?seqId=' + id;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                });
                refresh();
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });
}

//弹出一个iframe层
$('#parentIframe').on('click',
function() {
    layer.open({
        type: 2,
        title: '<%=ChainUtil.getCurrentOrganizationName(request)%>：新增病历词条',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        /*   offset: 'ct', */
        area: ['600px', '300px'],
        content: contextPath+'/YZBlctAct/toBlctAdd.act?cttype='+$("#cttype").val()+'&ctnexttype='+$("#ctnexttype").val()+'&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});

//编辑病历
function bzblkEdit(seqId,organization) {
 layer.open({
     type: 2,
     title: '编辑病历词条',
     maxmin: true,
     shadeClose: true,
     //点击遮罩关闭层
     zIndex: 211111,
     area: ['600px', '300px'],
     content: contextPath+'/YZBlctAct/toBlctEdit.act?seqId=' + seqId +'&organization='+organization
 });
}
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight();
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight-40
	})
	$(".leftDiv").outerHeight(tableHeight);
}
</script>



</body>
</html>
