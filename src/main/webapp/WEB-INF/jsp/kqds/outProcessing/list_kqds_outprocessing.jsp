<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">

<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/Javascript" src="<%=contextPath%>//static/js/kqds/outProcessingType/outprocessingtype.js"></script>
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<title>加工项目列表</title>
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
					<div id="toolbar">
						<!--dom结构部分-->
						<div class="child-inline-block-middle" id="uploader-demo">
							<!--用来存放item-->
							<input type="hidden" placeholder="" id="imgtype" name="imgtype"
								value="wjgitem">
							<div id="fileList" class="uploader-list"></div>
							<button id="parentIframe" class="kqdsSearchBtn bigBtn">
								<i class="glyphicon glyphicon-plus"></i> 新增
							</button>
							<a onclick="mbxz();" class="kqdsSearchBtn bigBtn" style="color: #fff;"><i
								class="glyphicon glyphicon-upload"></i> 模板下载</a>
							<a id="filePicker" class="kqdsSearchBtn bigBtn" style="vertical-align:middle;top:-3px;color: #fff;"><i
								class="glyphicon glyphicon-save"></i> 导入</a>
							<button onclick="exportTable()" class="kqdsSearchBtn bigBtn" >
								<i class="glyphicon glyphicon-open"></i> 导出
							</button>
							<button onclick="batchDelete()" class="kqdsSearchBtn bigBtn" >
							<i class="glyphicon glyphicon-remove"></i> 批量删除
							</button>
							<button onclick="deleteAll()" class="kqdsSearchBtn bigBtn" >
								<i class="glyphicon glyphicon-warning-sign"></i> 清空
							</button>
							加工厂： <select name="mrjgc" id="mrjgc"></select>
							加工分类：<select name="wjgfl" id="wjgfl"> </select>
							加工项目：<input type="text" name="wjgmc" id="wjgmc" >
							<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="float: right;">
							 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
							</button>
						</div>	
					</div>
					<div class="tableBox">
						<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>	
					</div>
				</div>
			</div>
	</section>
<script>
var organization = '<%=organization %>';
var pageurl = '<%=contextPath%>/KQDS_OutProcessingBackAct/selectPage.act';
$(function() {
    uploadfile(contextPath + "/FileUploadAct/uploadFile.act?module=evidence&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>");
    initFactorySelect4Back('mrjgc', '', '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>');
 
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
            if (data.rows.length == 0) {
                $("#filePicker").show();
            }
        setHeight();
        },
        sidePagination: "server",
        //服务端处理分页
        columns: [
		{field: ' ',checkbox: true,formatter: stateFormatter},
        {
            title: '项目名称',
            field: 'wjgmc',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单价',
            field: 'dj',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单位',
            field: 'dw',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '加工厂',
            field: 'factoryname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '项目分类',
            field: 'typename',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '收费分类',
            field: 'basetypename',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '收费分类子类',
            field: 'nexttpename',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '收费项目',
            field: 'treatitemname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '操作',
            field: ' ',
            align: 'center',
            formatter: function(value, row, index) {
                var menubutton = "";
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
                if (row.useflag == '1') {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_OUTPROCESSING\',\'' + row.seqId + '\',0);"><span style="color:green">启用</span></a>';
                } else {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_OUTPROCESSING\',\'' + row.seqId + '\',1);"><span style="color:red">禁用</span></a>';
                }
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\');"><span style="color:red">删除</span></a>';
                return menubutton;
            }
        }]
    });
    
    zTreeInit();
    /* 页面重置，重新设置table的高度 */
    $(window).resize(function() {
        setHeight();
    });
});
function queryParams(params) {
	var wjgfl = $('#wjgfl').val();
	if(!wjgfl){ // 该下拉框默认没有初始化
		wjgfl = "";  // 不这样处理，后台会取到字符 null
	}
    var temp = {
        offset: params.offset,
        limit: params.limit,
        organization: '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        mrjgc: $('#mrjgc').val(),
        wjgfl: wjgfl,
        wjgmc: $('#wjgmc').val()
    };
    return temp;
}
$('#mrjgc').change(function() {
    getTypeList("wjgfl", this.value);
});
function refresh() {

    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}

function zTreeInit() {
    //异步加载
    var url = contextPath + '/YZDictJGAct/getJgTreeAsync.act';

    var setting = {
        view: {
            showIcon: false // 去掉图标
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id", "name=n", "level=lv"],
            otherParam: {
                "otherParam": "zTreeAsyncTest",
                "organization": organization
            },
            dataFilter: ajaxDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onclick4Tree
    };
    $.fn.zTree.init($("#treeDemo"), setting);
}

function onclick4Tree(e, treeId, treeNode) {
    var url = "";
    if (treeNode.level == 0) { // 一级分类
    	$("#mrjgc").val(treeNode.id);
    	$("#mrjgc").trigger("change");
    	$("#wjgfl").val("");
    } else { // 二级分类
    	$("#mrjgc").val(treeNode.pId);
    	$("#mrjgc").trigger("change");
    	$("#wjgfl").val(treeNode.id);
	}

    refresh(); // 执行查询

}

function edit(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改加工项目',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '480px'],
        content: contextPath+'/KQDS_OutProcessingBackAct/toEdit.act?seqId=' + id + '&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：加工项目详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '420px'],
        content: contextPath+'/KQDS_OutProcessingBackAct/toDetail.act?seqId=' + id + '&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
}


/**
 * 清空
 */
function deleteAll() {
    //询问框
    layer.confirm('确定清空？', {
        btn: ['确定', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_OutProcessingBackAct/deleteAll.act?organization=' + organization;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
            	var msg = '清空成功';
            	if(data.retMsrg){
            		msg = data.retMsrg;
            	}
                layer.alert(msg, {
                      
                });
                refresh();
            }else{
            	layer.alert('清空失败：' + data.retMsrg  );
            }
        },
        function() {
            layer.alert('清空失败！'  );
        });
    });
}

/**
 * 批量删除
 */
function batchDelete() {
	var selectRows = getIdSelections();
	var seqId = "";
	$.each(selectRows,function(index){
		seqId += selectRows[index].seqId + ",";
	});
	
	if(!seqId){
		layer.alert('请选择要删除的加工项目' );
		return false;
	}
	
	// 调用删除方法
	del(seqId);
}

function del(id) {
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_OutProcessingBackAct/deleteObj.act?seqId=' + id;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
        	if (data.retState == "0") {
            	var msg = '删除成功';
            	if(data.retMsrg){
            		msg = data.retMsrg;
            	}
                layer.alert(msg, {
                      
                });
                refresh();
            }else{
            	layer.alert('删除失败：' + data.retMsrg  );
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });
}
//模板下载
function mbxz() {
    location.href = contextPath + "/KQDS_OutProcessingBackAct/excelStandardTemplateOut.act";
}
//重写uploadutil.js中的fz()方法，实现该页面的自有业务
//该方法在文件上传成功后执行
function fz() {
  jQuery(".file-item").hide();
  zTreeInit();
  initFactorySelect4Back('mrjgc', '', '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>');
  refresh();
}
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
    var organization = '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
    var mrjgc = $('#mrjgc').val();
    var wjgfl = $('#wjgfl').val();
    var wjgmc = $('#wjgmc').val();
    
    var params = "&organization=" + organization + "&mrjgc=" + mrjgc + "&wjgmc=" + wjgmc;
    if(wjgfl){ // 该下拉框默认没有初始化
    	params += ("&wjgfl=" + wjgfl)
    }
    
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + params;
}

//弹出一个iframe层
$('#parentIframe').on('click',
function() {
    layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增加工项目',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '480px'],
        content: contextPath+'/KQDS_OutProcessingBackAct/toNewAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});

//复选框
function stateFormatter(value, row, index) {
    return value;
}

//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
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
