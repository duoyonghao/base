<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>加工类型配置管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/add_cost.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style type="text/css">
	#container {
		width: 100%;
		padding-bottom: 80px;
	}
	.costHd label {
		float:left;
	}
	.costHd label:hover,.costHd label:hover .webuploader-pick{
		color:#00A6C0;
		cursor:pointer;
	}
	.webuploader-pick {
		font-size:13px;
		color:#333;
	}
/* 	.searchWrap{ */
/* 		border:none; */
/* 	} */
	.operateModel .optBox ul li{float: left;width:8%;min-width:105px; margin-bottom:10px;font-size:14px;font-family: "Microsoft YaHei";color: #3f3f3f;cursor: pointer;-webkit-transition: all .3s; transition: all .3s;}
	.tableBox{
		margin:0;
		border:none;
		overflow:visible;
	} 
	.costBd{
		
	}
	.fixed-table-container{
		border-bottom:none;
	}
	.costBd .ztreeWrap{
		border-right:none;
	}
	.operateModel{
		padding:0;
	}
	.operateModel{
		border:1px solid #ddd;
		border-radius:6px;
	}
	.operateModel .optBox {
	    padding: 45px 25px 0;
	}
	#ul1 img{
		width: 20px;
	    height: 21px;
	   /*  margin-top: 4px; */
	    margin-right: 2px;
	    display: block;
	    float: left;
	}
	.fixed-table-container{
		border-radius: 6px;
		overflow: hidden;
		position: relative;
    	top: -8px;
    	padding-bottom: 78px;
    	background-color: white;
	}
	.costBd{
		border:none;
	}
	.fixed-table-header{
		border-top-left-radius:6px;
		border-top-right-radius:6px;
	}
	.fixed-table-body{
		border-bottom-left-radius:6px;
		border-bottom-right-radius:6px;
		border-bottom:1px solid #ddd;
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
	}
	.tableBox{
		position: relative;
	}
	.fixed-table-container{
		overflow: visible;
	}
	.fixed-table-pagination{
	    display: block;
   		width: 100%;
    	position: absolute;
   		left: 0px;
    	bottom: -60px;
    	border: 1px solid #ddd;
    	border-radius: 6px;
    	/* background-color: white; */
	}
	.tableBox {
	    top: 8px;
	    margin: 0;
	    border: none;
	    overflow: visible;
	}
	.btnLeft,.btnBar{
		float: left;
	}
	.kv{
		margin-right: 5px;
	}
</style>
</head>
<body>
<div id="container">
	<div class="costWrap">
			<div class="formBox">
	  	     <!--增删改查条件-->
	        <div class="">
<!-- 	            <div class="cornerBox">查询条件</div> -->
				<div class="btnLeft">
					<a href="javascript:void(0);" class="kqdsCommonBtn" id="add" onclick="addOneClassify()">添加一级</a>
	                <a href="javascript:void(0);" class="kqdsCommonBtn" id="add" onclick="addClassify()">添加类别</a>
	                <a href="javascript:void(0);" class="kqdsSearchBtn" id="del" onclick="delClassify()">删除类别</a>
	                <a href="javascript:void(0);" class="kqdsSearchBtn" id="edit" onclick="editClassify()">修改类别</a>
	            </div>
	            <div class="btnBar">
	            	<div class="kv">
	                     <div class="kv-v">
	                        <label>一级类别：</label>
	                        <select class="select2"  name="basetype" id="basetype"> </select>
	                     </div>
	                     <div class="kv-v">
	                     	<label>二级类别：</label>
	                         <select class="select2"  name="nexttype" id="nexttype">
	                       		 <option value="">请选择</option>
	                	  	 </select>
	                    </div>
	                    <div class="kv-v">
	                   		<label>模糊查询：</label>
	                        <input type="text" id="queryInput" class="searchInput" placeholder="请输入。。。">
	                    </div>
	                </div>
	                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
	                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
	            </div>
	        </div>
		</div>
<!-- 		<div class="costHd" id="jcxx"></div> -->
		<div class="costBd">
			<div class="ztreeWrap">
			 <div class="ztree" style="height: 260px;border:1px solid #ddd;border-radius:6px; ">
					 <ul id="treeDemo" class="ztree"></ul> 
				 </div>
			</div>
			<div class="tableBox">
				<table id="table" class="table-striped table-condensed table-bordered"></table>
			</div>
			
		</div>
	</div>
	
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
//从parent页面，获取到该页面的所有按钮，并转换成Object对象
// var listbutton = null;
var pageurl = contextPath + '/KQDS_MACHININGTypeAct/findPrimary.act';
var pid;

$(function() {
    // 获取当前页面所有按钮，并赋值给该页面的全局变量 listbutton
    getButtonAllCurPage("<%=menuid%>");
    // 左侧树形目录初始化
    zTreeInit();
    initTable(); //加载表格
    $(window).resize(function() {
        setHeight();
        setWidth();
       var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 140;
        $(".fixed-table-container").outerHeight(calculateHeight);
        $("#table").data("height", calculateHeight);
    });
});
$('#basetype').change(function() {
    if ($(this).val()) { // 如果一级有值，再请求
        getNextTypeSelect('nexttype', this.value);
    }
});
//查询按钮onclik事件
$('#query').on('click',
function() {
    //var sshouse = $("#sshouse").val();
    //var pareurl1 = pageurl + "?queryInput=" + $("#queryInput").val() + "&basetype=" + $("#basetype").val() + "&nexttype=" + $("#nexttype").val() + "&sshouse=" + sshouse;
    $('#table').bootstrapTable('refresh', {
        'url': pageurl,
    });
});

//清空
$('#clear').on('click',
function() {
    $("#queryInput").val("");
});

function refresh() {
	var pareurl1= contextPath + '/KQDS_MACHININGTypeAct/findPrimary.act?parentId='+pid;
    $('#table').bootstrapTable('refresh', {
        'url': pareurl1
    });
}

//加载表格
function initTable() {
    /*wl----首次加载时 计算table高度  */

    var tableHeight = 0;
    /* 计算table需要的高度 */
    /* 根据当前页面 计算出table需要的高度 */
    tableHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 103;
    /* 框架要使用改table */
    $(".tableBox").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='" + tableHeight + "'></table>");

    /*wl----首次加载时 计算table高度————————————结束  */
    //加载表格
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [5, 25, 50, 100],//可以选择每页大小
      //在表格底部显示分页工具栏 
        singleSelect: true,
        striped: true,
        sidePagination: "client",//分页方式：client客户端分页，server服务端分页（*）
        onLoadSuccess: function(data) { //加载成功时执行
        	//console.log("返回数据="+JSON.stringify(data));
            /*滚动条出现 */
            setTableHeaderWidth(".tableBox");
            var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 140;
            $(".fixed-table-container").outerHeight(calculateHeight);
        },
        columns: [
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
            title: '修复类型',
            field: 'typename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '创建人',
            field: 'createuser',
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
            title: '上级Id',
            field: 'parentid',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: false,
            editable: true,
        },
        {
            title: '上级类型',
            field: 'parentname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '门诊',
            field: 'organization',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '详细说明（备注）',
            field: 'remark',
            align: 'center',
            sortable: true,
            editable: true,
        },{
        	title: '操作',
            field: 'useflag',
            align: 'center',
            sortable: true,
            editable: true,
            formatter:function(value, row, index){
            	if (row.useflag==1) {
            		return '<button class="kqdsCommonBtn" sqid="'+row.seqId+'" onclick="disabledOrnot(this,'+row.useflag+')">启用</button>';		               
		          } else{	        				        	
		        	return '<button class="kqdsCommonBtn" sqid="'+row.seqId+'" onclick="disabledOrnot(this,'+row.useflag+')">禁用</button>';	
		          }            
		      }
            } ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
    });
}
function zTreeInit() {
	var zTreeObj;
	var url = contextPath + '/KQDS_MACHININGTypeAct/initPrimary.act';
    var setting = {
	        view: {
	            showIcon: false // 去掉图标
	        },
	        callback: {
                onClick: groupzTreeOnClick,//点击节点加载子节点
                }
		};
    $.ajax({   
        type:"GET",   
        url:url, //从服务器拿东西，地址自己写  
        dataType:"json",  
        //下面这个函数是在ajax请求成功后才执行的，其中的参数data，就是
        success:function(data){
        	var dat=JSON.stringify(data.retData);
//         	console.log(dat+"----------dat");
         	 $.fn.zTree.init($("#treeDemo"), setting, data.retData);          	 
        } 
    });
//点击节点
    function groupzTreeOnClick(event, treeId, treeNode) {
		 pid=treeNode.id;
 //     判断该节点下是否有节点，没有就加载节点
      	var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var node = treeObj.getNodeByTId(treeNode.tId);
//       	console.log(pid+"----该节点pid-----");
      	refresh();
      if(node.children == null || node.children == "undefined"){  
         $.ajax({  
               url : url,  
               type : 'get',//请求方式：get  
               data: {parentId:pid},
               dataType : 'json',//数据传输格式：json  
               error : function() {  
                   $.messager.alert("警告",'亲，请求失败！');  
                 },  
                 success : function(data) { 
//                  	console.log(JSON.stringify(data)+"=======data");
                     if(data!=null && data!=""){
                       newNode = treeObj.addNodes(node, data.retData);  
                     }
                 }   
           });
     }; 
     
}
    
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

//禁用启用
function disabledOrnot(thi,status){
	 var disabledOrnotId=$(thi).attr("sqid"); //当前ID
	 var params = {
			 seqId:disabledOrnotId,
			 useflag:status
    };
// 	return;
   var url = contextPath + "/KQDS_MACHININGTypeAct/insertOrUpdate.act";
   $.axse(url, params,
           function(data) {
   		    layer.alert("更改成功！", {
   	            end: function() {
   	            	window.location.reload(); //刷新父页面
   	            }
   	        });
           },
           function() {
               layer.alert('改变失败！', {
               });
           });
}

//添加一级分类
function addOneClassify() {
    layer.open({
        type: 2,
        title: '修复类型',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath + '/KQDS_MACHININGTypeAct/toOneClassify.act'
    });
}
//添加二三级分类
function addClassify() {
	 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	  if (treeObj.getSelectedNodes().length == 0) {
	      layer.alert('请选择分类');
	      return;
	  }
	  var treeNode = treeObj.getSelectedNodes()[0];	 
	  if(treeNode.level==3){
		  layer.alert("此为末级");
		  return;
	  }
	  var parentId =treeNode.id,
         pername = treeNode.name,	
	  	 level=treeNode.level;
		  layer.open({
	       type: 2,
	       title: '添加类别',
	       maxmin: true,
	       shadeClose: true,
	       //点击遮罩关闭层
	       area: ['550px', '350px'],
	       content: contextPath + '/KQDS_MACHININGTypeAct/toAddProcess.act?parentId=' + parentId + '&pername=' + pername+"&level="+level
	   });
}

//修改分类
function editClassify() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    if (treeObj.getSelectedNodes().length == 0) {
        layer.alert('请选择分类' );
        return;
    }
    var treeNode = treeObj.getSelectedNodes()[0];
    var seqId = "",
    pername = "";
    if (treeNode.isParent && treeNode.pId == null) { //仓库
        layer.alert('请选择分类' );
        return;
    } else if (!treeNode.isParent) { // 没有下级分类--叶子节点
        var parentNode2 = treeNode.getParentNode();
        var parentNode1 = parentNode2.getParentNode();
        if (parentNode1 == null) { // 一级分类
            seqId = treeNode.id;
            pername = parentNode2.name;
        } else {
            seqId = treeNode.id;
            pername = parentNode2.name;
        }
    } else { // 一级分类
        var ck = treeNode.getParentNode();
        seqId = treeNode.id;
        pername = ck.name;
    }

    layer.open({
        type: 2,
        title: '修改类别',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['550px', '250px'],
        content: contextPath + '/KQDS_Ck_GoodstypeAct/toSave.act?seqId=' + seqId + '&pername=' + pername
    });
}
//删除分类
function delClassify() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    if (treeObj.getSelectedNodes().length == 0) {
        layer.alert('请选择分类' );
        return;
    }
    var treeNode = treeObj.getSelectedNodes()[0];
    var seqId = "";
    if (treeNode.isParent && treeNode.pId == null) { //仓库
        layer.alert('请选择分类' );
        return;
    } else { //没有下级分类--叶子节点
        seqId = treeNode.id;
    }
    // 改分类下存在商品不能删除
    if (!delleteGoodsYz(seqId)) {
        layer.alert('该分类下（包括其他仓库）存在商品不能删除' );
        return;
    }

    layer.confirm('确定删除分类及子分类？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = contextPath + '/KQDS_Ck_GoodstypeAct/delete.act?seqId=' + seqId;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                });
                zTreeInit();
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });
}
//点击导出
$('#export').on('click',
function() {
    var li = $(".dropdown-menu").children("li").last();
    li.trigger("click");
});
//删除
function claqdDel(id) {
    var url = '<%=contextPath%>/kqds/act/goodsin/Kqds_goods_inAct/deleteObj.act?seqId=' + id;
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {}
    },
    function() {

});
}
</script>
</html>
