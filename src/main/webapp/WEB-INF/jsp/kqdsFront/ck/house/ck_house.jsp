<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
	// 获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>仓库及类别</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/kqdsCss/kqdsCommon.css"/>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!-- 左侧树形仓库 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<!-- 权限按钮样式 -->
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/add_cost.css" /> --%>
</head>
<style type="text/css">
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
	.costBd{
		border:none;
	}
	.costBd .ztreeWrap{
		border-right:none;
		float: left;
		width: 13%
	}
	.costBd .ck_table{
		float: right;
		width: 85%
	}
	.costBut {
	   border-radius: 6px;
	   border: 1px solid #ddd;
	   padding: 0 10px;
	   height: 30px;
	   line-height: 30px;
	   color: #333;
	   background: #FAFAFB;
	   text-align: center;
	   margin-bottom: 2px;
	}
	.costBut label {
		float:left;
		font-weight: normal;
    	margin-right: 10px;
    	
	}
	.costBut label:hover,.costBut label:hover .webuploader-pick{
		color:#00A6C0;
		cursor:pointer;
	}
/* 	.kqdsCommonBtn { */
/* 	    padding: 0 10px; */
/*  	    border: none; */
/* 		background:none; */
/* 	} */

	.qxBtn label{
	    display: inline-block;
	    box-sizing: border-box;
	    height: 26px;
	    line-height: 26px;
	    background: #fff;
	    color: var(--system_color,#00A6C0);
	    outline: none;
	    padding: 0 15px;
	    border: 1px solid var(--system_color,#00A6C0);
	    border-radius: 3px;
	    margin-right: 3px;
	    text-decoration: none;
	    cursor: pointer;
	    text-align: center;
	}
	.position-bottom{
		text-align: left;
	}
	.hide{
		display: none;
	}
</style>
<body>
	<div class="costBd">		
		<div class="ztreeWrap" style="height:810px;">
			 <div class="ztree" style="height: 100%;overflow-y:auto;border:1px solid #ddd;border-radius:6px; ">
<!-- 			 	<div class="costBut" >  -->
<!-- 					<span class="">仓库：</span> -->
<!-- 					<a class="kqdsCommonBtn" id="add">新增</a> -->
<!-- 					<a class="kqdsCommonBtn" id="edit">修改</a> -->
<!-- 					<a class="kqdsCommonBtn" id="del">删除</a> -->
<!-- 				</div> -->
<!-- 			  	 <div class="costBut" id="cklb"></div>			  	  -->
				 <ul id="treeDemo" class="ztree"></ul>				
			 </div>
		</div>
		<div class="ck_table">
			<div class="tableBox" id="tableCk">
	       		<table id="table" class="table-striped table-condensed table-bordered" data-height="418"></table>
	    	</div>
	    	<div class="tableBox hide" id="tableLb1">
	       		<table id="categoryone" class="table-striped table-condensed table-bordered" data-height="418"></table>
	    	</div>
	    	<div class="tableBox hide" id="tableLb2">
	       		<table id="categorytwo" class="table-striped table-condensed table-bordered" data-height="418"></table>
	    	</div>
		</div>	 
		<div class="position-bottom" >
				<div class="clear2"></div>				
				<a class="kqdsCommonBtn" id="add">仓库新增</a>
				<a class="kqdsCommonBtn" id="edit">仓库修改</a>
				<a class="kqdsCommonBtn" id="del">仓库删除</a>
				<span class="qxBtn" id="cklb"></span>	
		</div>   
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- 左侧树形仓库 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<!-- 权限按钮方法 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var pageurl = contextPath+'/KQDS_Ck_HouseAct/selectList.act'; //仓库
// var $table = $('#table');
var categoryCk="";
var categoryone="";
$(function() {
    getButtonAllCurPage("<%=menuid%>");
	initTable();
	zTreeInit();
	$("#table").bootstrapTable("resetView",{
		height:$(window).height()-50
	});
	$(".ztreeWrap").css("height",$(window).height()-50);	
});
function zTreeInit() {
    //异步加载
    var url = contextPath + '/KQDS_Ck_GoodstypeAct/getSelectTreeAsync.act';
    var setting = {
        view: {
            showIcon: false // 去掉图标
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id", "name=n", "level=lv"],
            otherParam: {
                "otherParam": "zTreeAsyncTest"
            },
            dataFilter: ajaxDataFilterCk,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onclicktree
    };
    delete setting['check'];
    $.fn.zTree.init($("#treeDemo"), setting);
}

function ajaxDataFilterCk(treeId, parentNode, responseData) {
	initTable(responseData.retData);
    var tree = null;
    if (responseData.retState == "0") {
        tree = responseData.retData;
    }
    return tree;
};

function onclicktree(e, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var treeNode = treeObj.getSelectedNodes()[0];
//     console.log(JSON.stringify(treeNode)+"------treeNode");
    categoryCk = "";//
    categoryone = "";
    if (treeNode.isParent && treeNode.pId == null) { // 点击仓库查一级类别刷新一级
    		categoryCk="";
	    	categoryCk = treeNode.id;
			$("#tableLb1").removeClass("hide");
			$("#tableCk").addClass("hide");
			$("#tableLb2").addClass("hide");
 		    $('#categoryone').bootstrapTable('destroy'); 
 		   	initTableOne(categoryCk);
 		 //tabel--height
 			$("#categoryone").bootstrapTable("resetView",{
 				height:$(window).height()-50
 			});
// 			console.log(categoryCk+"----------1级查2级");
    } else if (!treeNode.isParent) {
        var parentNode2 = treeNode.getParentNode();
        var parentNode1 = parentNode2.getParentNode();
        if (parentNode1 == null) {
        } else { // 二级分类的id末级
        	layer.alert("末级类别,可进行类别修改、删除！");
        }
    } else { // 一级分类的id  点击一级查二级类别刷新二级
        var parentNode2 = treeNode.getParentNode();
        sshouse = parentNode2.id;
        categoryone = treeNode.id;
		$("#tableLb2").removeClass("hide");
        $("#tableCk").addClass("hide");
        $("#tableLb1").addClass("hide");
        $('#categorytwo').bootstrapTable('destroy'); 
		initTableTwo(categoryone);
		//tabel--height
		$("#categorytwo").bootstrapTable("resetView",{
			height:$(window).height()-50
		});
// 		 console.log(categoryone+"----------2级查3级");
    }
}

//仓库初始化
function initTable(){
	$("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
//         	console.log(JSON.stringify(data)+"-------initTableOne");
        },
        columns: [
         {
         	title : '序号',
         	align: "center",
         	width: 180,
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
            title: '仓库',
            field: 'housename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
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
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
//         console.log(JSON.stringify(onclickrow)+"---------------111111");
    });
}

// 一级类别初始化
function initTableOne(categoryCk){
// 	console.log(categoryCk+"------------categoryCk");
	var pageurl1 = contextPath + "/KQDS_Ck_GoodstypeAct/getBaseTypeSelect.act?sshouseid="+categoryCk;
	$("#categoryone").bootstrapTable({
        url: pageurl1,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
//         	console.log(JSON.stringify(data)+"-------initTableOne");
        },
        columns: [
         {
         	title : '序号',
         	align: "center",
         	width: 180,
         	formatter: function (value, row, index) {
         		/* return index + 1; */
         		var pageSize = $('#categoryone').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
             	var pageNumber = $('#categoryone').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
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
            title: '类别',
            field: 'goodstype',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
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
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#categoryone').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#categoryone').bootstrapTable('getData')[index];
//         console.log(JSON.stringify(onclickrow)+"------2222222");
    });
}

//二级类别初始化
function initTableTwo(categoryone){
	var pageurl2 = contextPath + "/KQDS_Ck_GoodstypeAct/getNextTypeSelect.act?baseid="+categoryone;
	$("#categorytwo").bootstrapTable({
        url: pageurl2,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
//         	console.log(JSON.stringify(data)+"-----------data");
        },
        columns: [
         {
         	title : '序号',
         	align: "center",
         	width: 180,
         	formatter: function (value, row, index) {
         		/* return index + 1; */
         		var pageSize = $('#categorytwo').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
             	var pageNumber = $('#categorytwo').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
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
            title: '类别',
            field: 'goodstype',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
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
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#categorytwo').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#categorytwo').bootstrapTable('getData')[index];
//         console.log(JSON.stringify(onclickrow)+'-----------------------333');
    });
}
// 增加--修改仓库刷新
function refreshCk() {
	var pageurl = contextPath+'/KQDS_Ck_HouseAct/selectList.act'; //仓库
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
	$("#tableCk").removeClass("hide");
	$("#tableLb1").addClass("hide");
	$("#tableLb2").addClass("hide");
}
// 增加--修改一级类别刷新
// function refreshOne(ckid) {
// 	console.log(ckid+"********ckid");
// 	var pageurl1 = contextPath + "/KQDS_Ck_GoodstypeAct/getBaseTypeSelect.act?sshouseid="+ckid;
//     $('#categoryone').bootstrapTable('refresh', {
//         'url': pageurl1
//     });
// 	$("#tableLb1").removeClass("hide");
// 	$("#tableCk").addClass("hide");
// 	$("#tableLb2").addClass("hide");
// 	$('#categoryone').bootstrapTable('destroy'); 
// 	$('#categorytwo').bootstrapTable('destroy'); 
// 	initTableOne(ckid);
// }
// // 增加--修改二级类别刷新
// function refreshTwo(ckid) {
// // 	console.log(ckid+"********ckid");
// 	var pageurl2 = contextPath + "/KQDS_Ck_GoodstypeAct/getNextTypeSelect.act?baseid="+ckid;
//     $('#categorytwo').bootstrapTable('refresh', {
//         'url': pageurl2
//     });
//     $('#categorytwo').bootstrapTable('destroy'); 
// 	$("#tableCk").addClass("hide");
// 	$("#tableLb1").addClass("hide");
// 	$("#tableLb2").removeClass("hide");
// }


//弹出一个iframe层
$('#add').on('click',
function() {
    layer.open({
        type: 2,
        title: '添加仓库',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['550px', '250px'],
        content: contextPath + '/KQDS_Ck_HouseAct/toSave.act'
    });
});
//弹出一个iframe层
$('#edit').on('click',
function() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    if (treeObj.getSelectedNodes().length == 0) {
        layer.alert('请选择仓库' );
        return;
    }
    var treeNode = treeObj.getSelectedNodes()[0];
    if (treeNode.isParent && treeNode.pId == null) { //仓库
        layer.open({
            type: 2,
            title: '修改仓库',
            maxmin: true,
            shadeClose: true,
            //点击遮罩关闭层
            area: ['550px', '250px'],
            content: contextPath + '/KQDS_Ck_HouseAct/toSave.act?seqId=' + treeNode.id
        });

    } else { 
    	 layer.alert('请选择仓库' );
    	 return;
    }

});
//弹出一个iframe层
$('#del').on('click',
function() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    if (treeObj.getSelectedNodes().length == 0) {
        layer.alert('请选择仓库' );
        return;
    }
    var treeNode = treeObj.getSelectedNodes()[0];
    if (treeNode.isParent && treeNode.pId == null) { //仓库
    //仓库下存在商品不能删除  
//     var urlhouse = contextPath+'/KQDS_Ck_GoodsAct/selectByhouse.act?houseid=' + treeNode.id;
    //仓库下存在一级不能删除  
//     console.log(treeNode.id+'-----------id');
   	var url1 = contextPath + "/KQDS_Ck_GoodstypeAct/getBaseTypeSelect.act?sshouseid="+treeNode.id; 
    $.axse(url1, null,
    function(data) {
        if (data.length > 0) {
            layer.alert('仓库下存在类别，请先删除类别！', {                  
                end: function() {
                    return false;
                }
            });
        } else {
            layer.confirm('确定删除？', {
                btn: ['删除', '放弃'] //按钮
            },
            function() {
                var url = contextPath+'/KQDS_Ck_HouseAct/deleteObj.act?seqId=' + treeNode.id;
                msg = layer.msg('加载中', {
                    icon: 16
                });
                $.axse(url, null,
                function(data) {
                    if (data.retState == "0") {
                        layer.alert('删除成功', {
                              
                        });
                        refreshCk();
                        zTreeInit();
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
    }else { 
   	 	layer.alert('请选择仓库' );
   		return;
 	}
});

// //弹出一个iframe层
// $('#del').on('click',
// function() {
//     var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
//     if (treeObj.getSelectedNodes().length == 0) {
//         layer.alert('请选择仓库' );
//         return;
//     }
//     var treeNode = treeObj.getSelectedNodes()[0];
//     if (treeNode.isParent && treeNode.pId == null) { //仓库
//     //仓库下存在商品不能删除
//     var urlhouse = contextPath+'/KQDS_Ck_GoodsAct/selectByhouse.act?houseid=' + treeNode.id;
//     $.axse(urlhouse, null,
//     function(data) {
//         if (data.data.length > 0) {
//             layer.alert('仓库下存在商品不能删除！', {
                  
//                 end: function() {
//                     return false;
//                 }
//             });
//         } else {
//             layer.confirm('确定删除？', {
//                 btn: ['删除', '放弃'] //按钮
//             },
//             function() {
//                 var url = contextPath+'/KQDS_Ck_HouseAct/deleteObj.act?seqId=' + treeNode.id;
//                 msg = layer.msg('加载中', {
//                     icon: 16
//                 });
//                 $.axse(url, null,
//                 function(data) {
//                     if (data.retState == "0") {
//                         layer.alert('删除成功', {
                              
//                         });
//                         refresh();
//                         zTreeInit();
//                     }
//                 },
//                 function() {
//                     layer.alert('删除失败！'  );
//                 });
//             });
//         }
//     },
//     function() {
//         layer.alert('查询失败！' );
//     });
//     }else { 
//    	 	layer.alert('请选择仓库' );
//    		return;
//  	}
// });

// 原来的仓库修改删除
// $('#edit').on('click',
// function() {
//     if (onclickrow == "" || onclickrow == null) {
//         layer.alert('请选择列表中的仓库' );
//         return false;
//     }
//     layer.open({
//         type: 2,
//         title: '修改仓库',
//         maxmin: true,
//         shadeClose: true,
//         //点击遮罩关闭层
//         area: ['550px', '250px'],
//         content: contextPath + '/KQDS_Ck_HouseAct/toSave.act?seqId=' + onclickrow.seqId
//     });
// });
// //弹出一个iframe层
// $('#del').on('click',
// function() {
//     if (onclickrow == "" || onclickrow == null) {
//         layer.alert('请选择列表中的仓库' );
//         return false;
//     }
//     //仓库下存在商品不能删除
//     var urlhouse = contextPath+'/KQDS_Ck_GoodsAct/selectByhouse.act?houseid=' + onclickrow.seqId;
//     $.axse(urlhouse, null,
//     function(data) {
//         if (data.data.length > 0) {
//             layer.alert('仓库下存在商品不能删除！', {
                  
//                 end: function() {
//                     return false;
//                 }
//             });
//         } else {
//             layer.confirm('确定删除？', {
//                 btn: ['删除', '放弃'] //按钮
//             },
//             function() {
//                 var url = contextPath+'/KQDS_Ck_HouseAct/deleteObj.act?seqId=' + onclickrow.seqId;
//                 msg = layer.msg('加载中', {
//                     icon: 16
//                 });
//                 $.axse(url, null,
//                 function(data) {
//                     if (data.retState == "0") {
//                         layer.alert('删除成功', {
                              
//                         });
//                         refresh();
//                         zTreeInit();
//                     }
//                 },
//                 function() {
//                     layer.alert('删除失败！'  );
//                 });
//             });
//         }
//     },
//     function() {
//         layer.alert('查询失败！' );
//     });
// });
</script>
</html>
