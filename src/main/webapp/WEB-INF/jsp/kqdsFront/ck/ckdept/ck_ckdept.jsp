<!--wl整理完  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>出库部门</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/kqdsCss/kqdsCommon.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
	.fixed-table-container thead th .sortable{/*覆盖样式 表头右边距导致无法居中的问题  */
		padding-right:8px;
	}
	.fixed-table-body tbody tr:nth-child(odd){/* 表奇数行，淡蓝色 */
		background:#f0ffff;
	}
	.position-bottom {
		border-top: 0px solid #ddd;
	}
	.fixed-table-container {
	    border-left: 1px solid #ddd;
	    border-right: 1px solid #ddd;
	    border-bottom: 1px solid #ddd;
	    border-radius: 6px;
	    overflow: hidden;
	}
</style>
</head>
<body>
    <div class="tableBox">
       <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>
	<div class="position-bottom" >
		<div class="clear2"></div>
		<a class="kqdsCommonBtn" id="add">新增</a>
		<a class="kqdsCommonBtn" id="edit">修改</a>
		<a class="kqdsCommonBtn" id="del">删除</a>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var pageurl = contextPath+'/KQDS_Ck_CkdeptAct/selectList.act';
var $table = $('#table');
var onclickrow = ""; //选中行对象
$(function() {
	initTable();
    $(window).resize(function() {
//      setHeight();
     setWidth();
     
//     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 95; 
     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 60;
     $(".fixed-table-container").outerHeight(calculateHeight);
 });
});
function initTable(){
	$table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(){
        	$table.bootstrapTable('resetView', {
                height: $(window).height() - 50
            });
        },
        columns: [
         {
           	title : '序号',
           	align: "center",
           	width: 170,
           	formatter: function (value, row, index) {
           		/* return index + 1; */
           		var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
               	var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
               	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
           	}
           },
        {
            title: '排序号',
            field: 'sortno',
            align: 'center',
            
            sortable: true,
		    formatter:function(value){
		    	return '<span>'+value+'</span>';
		    }
        },          
        {
            title: '部门',
            field: 'deptname',
            align: 'center',
            
            sortable: true,
		    formatter:function(value){
		    	return '<span>'+value+'</span>';
		    }
        },
        /* {
            title: '门诊',
            field: 'organization',
            align: 'center',
            
            sortable: true
        }, */
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
		    formatter:function(value){
		    	return '<span>'+value+'</span>';
		    }
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
		    formatter:function(value){
		    	return '<span>'+value+'</span>';
		    }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
    });
}
function refresh() {

    $table.bootstrapTable('refresh', {
        'url': pageurl
    });

}
//弹出一个iframe层
$('#add').on('click',
function() {
    layer.open({
        type: 2,
        title: '添加部门',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['550px', '250px'],
        content: contextPath + '/KQDS_Ck_CkdeptAct/toSave.act'
    });
});
//弹出一个iframe层
$('#edit').on('click',
function() {
    if (onclickrow == "" || onclickrow == null) {
        layer.alert('请选择部门' );
        return false;
    }
    layer.open({
        type: 2,
        title: '修改部门',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['550px', '250px'],
        content: contextPath + '/KQDS_Ck_CkdeptAct/toSave.act?seqId=' + onclickrow.seqId
    });
});
//弹出一个iframe层
$('#del').on('click',
function() {
    //部门存在出库商品不能删除
    var urlhouse = contextPath+'/KQDS_Ck_Goods_OutAct/selectBydeptid.act?houseid=' + onclickrow.seqId;
    $.axse(urlhouse, null,
    function(data) {
        if (data.data.length > 0) {
            layer.alert('部门下存在出库商品不能删除！', {
                  
                end: function() {
                    return false;
                }
            });
        } else {
            layer.confirm('确定删除？', {
                btn: ['删除', '放弃'] //按钮
            },
            function() {
                var url = contextPath+'/KQDS_Ck_CkdeptAct/deleteObj.act?seqId=' + onclickrow.seqId;
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
    },
    function() {
        layer.alert('查询失败！' );
    });
});
</script>
</html>
