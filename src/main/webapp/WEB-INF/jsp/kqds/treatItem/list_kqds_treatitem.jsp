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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<title>收费项目管理</title>
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
            <div id="toolbar">
		        <!--dom结构部分-->
				<div class="child-inline-block-middle" id="uploader-demo">
				    <!--用来存放item-->
				    <input type="hidden" placeholder="" id="imgtype" name="imgtype" value="costorderitem">
					<div id="fileList" class="uploader-list"></div>
					<button id="parentIframe" class="kqdsSearchBtn bigBtn" >
					       <i class="glyphicon glyphicon-plus"></i> 新增
					</button>
					<button onclick="mbxz()" class="kqdsSearchBtn bigBtn" >
						<i class="glyphicon glyphicon-circle-arrow-down"></i> 导入模板
					</button>
					<a id="filePicker"  class="kqdsSearchBtn bigBtn" style="vertical-align:middle;top:-3px;color: #fff;"><i class="glyphicon glyphicon-save"></i> 导入</a>
					<button onclick="exportTable()" class="kqdsSearchBtn bigBtn" >
						<i class="glyphicon glyphicon-open"></i> 导出
					</button>
					<button onclick="batchDelete()" class="kqdsSearchBtn bigBtn" >
						<i class="glyphicon glyphicon-remove"></i> 批量删除
					</button>
					<button onclick="deleteAll()" class="kqdsSearchBtn bigBtn" >
						<i class="glyphicon glyphicon-warning-sign"></i> 清空
					</button>
					<span class="commonText">一级分类：</span> <select class=" select2"  name="basetype" id="basetype"></select>
					<span class="commonText">二级分类：</span> <select class=" select2"  name="nexttype" id="nexttype"><option value="">请选择</option></select>
					<span class="commonText">项目名称：</span> <input type="text" name="treatitemname" id="treatitemname" >
					<button id="btn_edit" onclick="refresh()" type="button" style="float:right;margin:2px 0 0 5px;" class="kqdsSearchBtn bigBtn" >
					 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
					</button>
				</div>
   			</div>
   			 	
			<div class="leftDiv">
				<div id="treeDemo" class="ztree"></div>
			</div>
   			 <div class="rightDiv">
				<div class="tableBox">
					<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
				</div>
			</div>
          </div>
        </section>
<script>
var organization = '<%=organization %>';
var pageurl = '<%=contextPath%>/KQDS_TreatItemBackAct/selectPage.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
var $table = $('#table');
$(function() {
	initCostSortSelect1LevelOrg('basetype',organization);
    uploadfile(contextPath + "/FileUploadAct/uploadFile.act?module=evidence&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>");
   
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,
        singleSelect: false,
        toolbar: '#toolbar',
        striped: true,
        cache: false,
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
            title: '项目编号',
            field: 'treatitemno',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '项目名称',
            field: 'treatitemname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '折扣',
            field: 'discount',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '特殊项目',
            field: 'istsxm',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '领料拆分',
            field: 'issplit',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == '是') {
                    var html = "";
                    html = '<span title="' + value + '" style="color:red;">' + value + '</span>';
                    return html;
                } else {
                    return value;
                }
            }
        },
        {
            title: '禁用',
            field: 'useflag',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == '是') {
                    var html = "";
                    html = '<span title="' + value + '" style="color:red;">' + value + '</span>';
                    return html;
                } else {
                    return value;
                }
            }
        },
        {
            title: '项目分类',
            field: 'isyjjitem',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '一级分类',
            field: 'basetype',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '二级分类',
            field: 'nexttype',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
         /* {
            title: '项目介绍',
            field: 'xmjs',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != null) {
                    var html = "";
                    html = '<span title="' + value + '">' + value.substring(0, 20) + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '优惠信息',
            field: 'yhxx',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != null) {
                    var html = "";
                    html = '<span title="' + value + '">' + value.substring(0, 20) + '</span>';
                    return html;
                } else {
                    return "";
                }

            }
        },  */
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
            visible: false,
            switchable: false
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            visible: false,
            switchable: false,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return "<span  class='name'>" + value + "</span>";
                }
            }
        },
        {
            title: '操作',
            field: ' ',
            align: 'center',
            width: '125px',
            formatter: function(value, row, index) {
                var menubutton = "";
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
                
                if (row.useflag == '是') {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_TREATITEM\',\'' + row.seqId + '\',0);"><span style="color:green">启用</span></a>';
                } else {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_TREATITEM\',\'' + row.seqId + '\',1);"><span style="color:red">禁用</span></a>';
                }
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\');"><span style="color:red">删除</span></a>';
                
                return menubutton;
            }
        }]
    });
    
    zTreeInit();
    
    /* 页面重置，重新设置table的高度 */
    $(window).resize(function(){
		setHeight(); 
	});
});

function zTreeInit() {
    //异步加载
    var url = contextPath + '/YZDictCostAct/getTreatSortTree.act';

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
    	$("#basetype").val(treeNode.id);
    	$("#basetype").trigger("change");
    	$("#nexttype").val("");
        
    } else { // 二级分类
    	$("#basetype").val(treeNode.pId);
    	$("#basetype").trigger("change");
    	$("#nexttype").val(treeNode.id);
	}

    refresh(); // 执行查询

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
        var url = '<%=contextPath%>/KQDS_TreatItemBackAct/deleteAll.act?organization=' + organization;
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
		layer.alert('请选择要删除的收费项目' );
		return false;
	}
	
	// 调用删除方法
	del(seqId);
}

function del(seqId) {
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_TreatItemBackAct/deleteObject.act?seqId=' + seqId;
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


function queryParams(params) {
    var temp = { 
   		offset : params.offset,
       	limit :	 params.limit,
        organization: organization,
        basetype: $('#basetype').val(),
        nexttype: $('#nexttype').val(),
        treatitemname:$('#treatitemname').val()
    };
    return temp;
}
$('#basetype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
		initCostSortSelect2Level('nexttype',this.value);
	}
});
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}
//模板下载
function mbxz() {
    location.href = contextPath + "/KQDS_TreatItemBackAct/excelStandardTemplateOut.act";
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
    var basetype= $('#basetype').val();
    var nexttype= $('#nexttype').val();
    var treatitemname=$('#treatitemname').val();
    var params = "&organization="+organization+"&basetype="+basetype+"&nexttype="+nexttype+"&treatitemname="+treatitemname;
    location.href = pageurl + "&flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr)+params;
}

function edit(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改收费项目',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        /* offset: ['0', "300px"], */
        area: ['600px', '470px'],
        content: contextPath+'/KQDS_TreatItemBackAct/toEdit.act?seqId=' + id + '&organization=' + organization
    });
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：收费项目详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        /* offset: 'ct', */
        area: ['600px', '450px'],
        content: contextPath+'/KQDS_TreatItemBackAct/toDetail.act?seqId=' + id + '&organization=' + organization
    });
}
//重写uploadutil.js中的fz()方法，实现该页面的自有业务
//该方法在文件上传成功后执行
function fz() {
    jQuery(".file-item").hide();
    zTreeInit();
    initCostSortSelect1LevelOrg('basetype',organization);
    refresh();
}
//弹出一个iframe层
$('#parentIframe').on('click',
function() {
    layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增收费项目',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        /*   offset: 'ct', */
        area: ['600px', '470px'],
        content: contextPath+'/KQDS_TreatItemBackAct/toNewAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});
  
//复选框
function stateFormatter(value, row, index) {
    <%-- if ("admin" != "<%=person.getUserId()%>") {
        return {
            disabled: true,
            checked: false
        };
    } --%>
   
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
		height:tableHeight
	})
	$(".leftDiv").outerHeight(tableHeight);
}
</script>



</body>
</html>
