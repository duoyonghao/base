<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}

	String deptId = request.getParameter("deptId");
	if(deptId == null){
		deptId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>标签库管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/person/list.css"/>
<!-- 文件上传 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<!-- 文件上传 END-->
</head>
<body style="">
	<div style="float: left; width: 100%; margin-right: 1%; padding:0 15px;">
		<div class="col-sm-12">
			<div id="toolbar">
				<button id="labelAdd" class="kqdsSearchBtn bigBtn" onclick="labelAdd();">
					<i class="glyphicon glyphicon-plus"></i> 新增
				</button>
				<button id="labelUpdate" class="kqdsSearchBtn bigBtn"  onclick="lableUpdate();">
					<i class="glyphicon glyphicon-minus"></i> 修改
				</button>
				<button id="deletePerson" class="kqdsSearchBtn bigBtn" onclick="labelDelete();">
					<i class="glyphicon glyphicon-remove"></i> 删除
				</button>
				
				<span class="commonText">标签名：</span>
				<input type="text" name="labelname" id="labelname" placeholder=" 标签名模糊查询" />
				
				<div style="display: inline-block;">
					<span class="impText">一级分类：</span>
					<div class="form-group" style="display: inline-block;">
						<select class="select2 dict" tig="hzly" name="firstLabelSelect" id="firstLabelSelect" onchange="getFirstlabel(this.value,'secondLabelSelect')">
						</select>
					</div>
				</div>
				<div style="display: inline-block;">
					<span class="impText">二级分类：</span>
					<div class="form-group" style="display: inline-block;">
						<select class="select2 dict" name="secondLabelSelect" id="secondLabelSelect">
						</select>
					</div>
				</div>		
				
				<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clearQuery()">清空</a>
				<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="exportTable()">生成报表</a>
				<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="searchLabel()">查询</a>
			</div>
		</div>
		<div class="tableBox">
			<table id="table" data-toolbar="#toolbar" data-height="500"></table>
		</div>
	</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- 文件上传 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<!-- 文件上传 END-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script>
var contextPath = "<%=contextPath %>";
var static_deptid = "<%=deptId %>";
var pageurl = "<%=contextPath%>/KQDS_LabelAct/findLabelAll.act";
var parentlabelId;
var selectObj; //当前选中的标签对象

$(function() {
	getPageList();//初始化标签
    //initClassify();
	$(window).resize(function(){
		setHeight();
	}); 
	getFirstlabel("1","firstLabelSelect"); //初始化一级标签
});

function getFirstlabel(parentId,obj) {
	 var url = contextPath + '/KQDS_LabelAct/findLabel.act';
	    var param = {
	    	parentId : parentId //要删除ID
	    };
	    $.axseSubmit(url,param,function() {},function(r) {
	     var firstLabelHtml="<option value=''>请选择</option>";
	     for (var i = 0; i < r.length; i++) {
			 firstLabelHtml+="<option value="+r[i].seqId+">"+r[i].leveLabel+"</option>";
		}
	     $("#"+obj).html(firstLabelHtml);
	    },function(r){
	    });
}

// 清空
function clearQuery(){
	$("#labelname").val("");
}

// 查询按钮
function searchLabel() {
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}



//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//复选框
function stateFormatter(value, row, index) {
    if (row.userId === 'admin') {
        return {
            disabled: true,
            checked: false
        };
    }
    return row;
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        labelname:$("#labelname").val(),//标签名模糊查询
        firstLabelSelect:$("#firstLabelSelect").val(),//一级分类
        secondLabelSelect:$("#secondLabelSelect").val()//二级分类
    };
    return temp;
}
function setHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".bs-bars").outerHeight()-50-$(".pagination-detail").outerHeight();
	$(".fixed-table-container").outerHeight(tableHeight);
}
function getTableHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".bs-bars").outerHeight()-10-$(".pagination-detail").outerHeight();
	return tableHeight;
}

//带参数刷新表格
function refreshTable(){
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}


function getPageList() {
	var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" data-toolbar="#toolbar" data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams:queryParams,
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        toolbar: '#toolbar',
        cache: false,
        striped: true,
        sidePagination: "server",
        onLoadSuccess:function(data){
        	//console.log(JSON.stringify(data)+"---------list数据");
        },
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
        },
        {
            title : '序号',
            align: "center",
            formatter: function (value, row, index) {
             /* return index + 1; */
             var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        },
        {
            title: '标签名称',
            field: 'levelabel',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        
        {
            title: '创建人Id',
            field: 'createuser',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: false,
            editable: true,
        },
        {
            title: '创建人',
            field: 'createname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '父级标签Id',
            field: 'parentid',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: false,
            editable: true,
        },
        {
            title: '父级标签',
            field: 'parentname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            sortable: true,
            editable: true,
        }]
    });
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

    /* if (parent.leftFrame) {
        parent.leftFrame.refreshNode(static_deptid);
    } */
}
//content: contextPath+'/KQDS_UserDocumentAct/labelAdd.jsp?deptId=' + static_deptid
//标签添加方法
function labelAdd(){
	var selectList = getIdSelections();
	if(selectList.length){
		parentlabelId=selectList[0].seqId;
		levelabel=selectList[0].levelabel;
		//console.log("父级标签="+levelabel);
	}
	
	if (selectList.length > 1) {
        layer.alert('只能选一个标签添加子分类标签' );
        return false;
    }
	layer.open({
        type: 2,
        title: '新增标签',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: contextPath+'/KQDS_LabelAct/toLabelAdd.act'
    });
}

//标签修改方法
function lableUpdate() {
	var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选要修改的标签' );
        return false;
    }
    if (selectList.length > 1) {
        layer.alert('只能修改一个标签' );
        return false;
    }
    selectObj=selectList;
	layer.open({
        type: 2,
        title: '修改标签',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: contextPath+'/KQDS_LabelAct/toLabelUpdate.act'
    });
}
//标签删除方法
function labelDelete() {
    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选要删除的标签' );
        return false;
    }
    // 获取要删除的用户id，以逗号分隔
    var seqIds = [];
    for (var i = 0; i < selectList.length; i++) {
        seqIds.push(selectList[i].seqId);
    }
    //console.log(seqIds+"---------要删除的ID传参打印");
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var reqUrl = contextPath + '/KQDS_LabelAct/deLabel.act';
        var param = {
        	seqId : seqIds //要删除ID
        };
        $.axseSubmit(reqUrl,param,function() {},function(r) {
        	layer.alert("删除成功！", {
                end: function() {
                	refresh(); //刷新父页面
                	refreshTable();
                	//window.location.reload(); //刷新父页面
                    //var frameindex = parent.layer.getFrameIndex(window.name);
                    //parent.layer.close(frameindex); //再执行关闭
                }
          	});
        },function(r){
        	layer.alert("删除失败！");
        });
    });
}

//导出
function exportTable() {
	loadedData = [];
	nowpage = 0;
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr);
}


</script>

</body>
</html>
