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
<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
<meta charset="utf-8" />
<title>药库首页</title>
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/ykzz/ykzz.css">
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<!-- 跳转页面 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<body>
<div id="container">
	<div class="drugs-top">
		<div class="costHd top-opt-list">
			<label id="house" onclick="topBtn.typeManager()" style="cursor:pointer">类别管理</label>
			<label id="supplier" onclick="topBtn.manuManager()" style="cursor:pointer">供应商管理</label>
			<label id="manufacturers" onclick="topBtn.manufacManager()" style="cursor:pointer">生产商管理</label>
			<label id="add" onclick="topBtn.drugsManager()" style="cursor:pointer">添加药品</label>
			<label id="update" onclick="topBtn.updateManager()" style="cursor:pointer">修改药品</label>
			<label id="delete" onclick="topBtn.deleteManager()" style="cursor:pointer">删除药品</label>
			<label id="download" onclick="topBtn.downloadTemplate()" style="cursor:pointer">模板下载</label>
			<label id="import" onclick="topBtn.importDrugs()" style="cursor:pointer">
				药品导入
				<!-- <input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" id="upfile"/> -->
			</label>
			<!-- <div style="margin-top: -35px;margin-left: 479px;">
				<form action="/HUDH_YkzzAct/FileUploadAct" method="post" enctype="multipart/form-data">
					<input type="file" name="file">
					<input type="submit" value="upload"/>
				</form>
				<button class="kqdsCommonBtn">上传excel文件</button>
			</div> -->
			<label id="drugsForbidden" onclick="topBtn.forbiddenManager()" style="cursor:pointer">药品停用</label>
			<label id="drugsRecover" onclick="topBtn.recoverManager()" style="cursor:pointer">恢复停用</label>
		</div>
	</div>
	<div class="drugs-context">
		<div class="left-type-tree">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<div class="right-drugs-list">
			<div class="fixed-table-body">
				<table id="table" class="table-striped table-condensed table-bordered" data-height="418"></table>
			</div>
		</div>
	</div>
	<div class="drugs-bottom">
		<div class="drugs-bottom-context">
			<div class="cornerBox select-label">查询条件</div>
			<div class="select-btnBar">
            	<div class="kv">
            		<div class="kv-v">
                   		<label>药品名称：</label>
                        <input type="text" id="queryInput" class="searchInput" placeholder="化学名/商品名">
                    </div>
                     <div class="kv-v">
                        <label>一级类别：</label>
                        <select class="select2" name="basetype" id="basetype">
                        </select>
                     </div>
                     <div class="kv-v">
                        <label>二级类别：</label>
                        <select class="select2" name="nexttype" id="nexttype">
                        	<option value="">请选择</option>
                        </select>
                     </div>
                    <div class="kv-v" style="float: right;margin-right: 20px;">
                   		<a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
                   		<a href="javascript:void(0);" class="kqdsSearchBtn" onclick = "query();">查询</a>
                    </div>
                </div>
            </div>
            <div class="bottom-btns">
            	<div class="bottom-opts">
                 	<ul id="ul1">
	             		<li name="jhsqd" onclick="bottomBtn.drugsInWare();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">药品入库</li>
	             		<li name="jhcx" onclick="bottomBtn.drugsInWareExamine();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">入库审核</li>
	             		<li name="rksh" onclick="bottomBtn.drugsWareInSearch();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">入库查询</li>
	             		<!-- <li name="jhmx"><img src="/base/static/image/kqdsFront/img/icon/book2.png">入库明细</li> -->
	             		<li name="lysqd" onclick="bottomBtn.drugsCostWare();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">患者领药</li>
	             		<li name="lycx" onclick="bottomBtn.drugsCostWareRecord();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">发药记录</li>
	             		<li name="lycx" onclick="bottomBtn.drugsCostWareDetail();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">发药明细</li>
	             		<li name="hzty" onclick="bottomBtn.drugsCostReturn();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">患者退药</li>
	             		<li name="tymx" onclick="bottomBtn.drugsCostReturnDetail();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">退药明细</li>
	             		<li name="lymx" onclick="bottomBtn.drugsOutWare();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">药品出库</li>
	             		<li name="dqkc" onclick="bottomBtn.drugsWareOutSearch();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">出库查询</li>
	             		<li name="scbb" onclick="exportTable();"><img src="/base/static/image/kqdsFront/img/icon/book2.png">生成报表</li>
	             		<!-- <li name="gyscx"><img src="/base/static/image/kqdsFront/img/icon/book2.png">供应商查询</li>
	             		<li name="bj_search"><img src="/base/static/image/kqdsFront/img/icon/book2.png">报警查询</li> -->
	             	</ul>
            	</div>
            </div>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var onclickrow = ""; //选中行对象
var selectId = "";
var apppath = apppath();
var contextPath = "<%=contextPath%>";
var pageurl =  '<%=contextPath%>/HUDH_YkzzAct/selectDrugsInforByConditionQuery.act';
var setting = { 
	    data: { 
	        simpleData: { 
	            enable: true
	        } 
	    },
	    view : {
	    	showIcon : false,
	    	showLine: true
	    },
	    callback:{  
	    	//beforeClick：用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作，默认值为null  
	        //beforeClick: beforeClick,  
	        //beforeAsync:用于捕获异步加载之前的事件回调函数，zTree 根据返回值确定是否允许进行异步加载，默认值为null  
	        //beforeAsync:beforeAsync,  
	        //用于捕获异步加载出现异常错误的事件回调函数  
	        //onAsyncError:onAsyncError,  
	        //用于捕获异步加载正常结束的事件回调函数  
	        //onAsyncSuccess:onAsyncSuccess  
	        //用于捕获节点被点击的事件回调函数  
	        onClick : zTreeOnClick//(注意大小写，若onClick写成了onclick那么点击时将没有任何反应)  
	    }
	}; 
var zNodes;

/************************ zTree返回节点数据类型实例 ***********************/
 /* 	var zNodes = [{ 
	    id: "HZ123456789", 
	    pId: 0, 
	    name: "父节点1 - 展开", 
	    open: false 
	}, 
	{ 
	    id: 11, 
	    pId: "HZ123456789", 
	    name: "父节点11 - 折叠" 
	},
	{ 
	    id: 2, 
	    pId: 0, 
	    name: "父节点2 - 折叠" 
	},
	{ 
	    id: 22, 
	    pId: 2, 
	    name: "父节点22 - 折叠" 
	}]; */
/************************ zTree返回节点数据类型实例 ***********************/
$(function(){
	initSelectBaseType($("#basetype"));
	$("#basetype").on("change",function (){
		initSelectNextType($("#nexttype"),$("#basetype").val());
	});
	getDrugsContextHeight();
	//初始化左侧树结构
	initzTreeData();
	$.fn.zTree.init($("#treeDemo"), setting, zNodes); 
	//初始化药品表格
	initTable();
})

//初始化中间表格的高度
function getDrugsContextHeight(){
	var tableHeight = $(document.body).height()-$(".drugs-top").height()-$(".drugs-bottom").height();
	$(".drugs-context").height(tableHeight);
	$("#table").attr("data-height",tableHeight-1);
}

function zTreeOnClick(event, treeId, treeNode) {
    $("#inp").val("id="+treeNode.id + ", name=" + treeNode.name + ",pId=" + treeNode.pId);
    var basetype;
    var nexttype;
    if(!treeNode.pId) { //一级类别
    	basetype = treeNode.id;
    }else { //二级分类
    	basetype = treeNode.pId;
    	nexttype = treeNode.id;
    }
    var opt = {
            url: pageurl,
            silent: true,
            query:{
            	pagenum : 1,
               	queryInput: "",
               	basetype: basetype,
               	nexttype: nexttype,
            }
        };
    
    $('#table').bootstrapTable('refresh', opt);
}

function initzTreeData(){
	$.ajax({
		url: apppath + "/HUDH_YkzzTypeAct/findzTreeList.act",
		type:"POST",
		dataType:"json",
		async:false,
		data:{},
		success:function(result){
			zNodes = result;
		}
	});
}

//查询
function query() {
   refresh();
}

function refresh(){
	 $('#table').bootstrapTable('refresh', {
	     'url': pageurl,
	 });
}

function queryParams(params) {
   var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   	pagenum : 1,
   	queryInput: $('#queryInput').val(),
   	basetype: $('#basetype').val(),
   	nexttype: $('#nexttype').val(),
   };
   return temp;
}

//清空
$('#clear').on('click',
function() {
    $("#queryInput").val("");
    $("#basetype").val("");
    $("#nexttype").val("");
});

function initTable(){
	$("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 50,
        pageList : [5, 25, 50, 100],//可以选择每页大小
        queryParams : queryParams,
        clickToSelect: true,
        paginationShowPageGo: true,
        onDblClickCell: function(field, value, row, td) {
            typeBtn.selectChilds();
        },
        /* onLoadSuccess: function(data) { //加载成功时执行
       	 	var tableList = $('#table').bootstrapTable('getData');
            $("#size").html(tableList.length);
            setHeight();
        }, */
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.userflag == "1") {//药品停用，danger颜色予以提示
                strclass = 'danger';  //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
		{
		    title : '序号',
		    align: "center",
		    width: 40,
		    formatter: function (value, row, index) {
		     /* return index + 1; */
		     var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
		     var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
 //		     return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号		 
				return index + 1;
		    }
		   },
        {
            title: 'id',
            field: 'id',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '编号',
            field: 'order_no',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '批准文号',
            field: 'contry_code',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '药品名',
            field: 'good_name',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '化学名',
            field: 'chemistry_name',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '规格',
            field: 'pharm_spec',
            align: 'center',
            sortable: true,
            editable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '到期标识',
            field: 'valid',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	//2019-08-14 lwg 药品有效期30天内做出提醒
            	var stringTime = value;
                var timestamp2 = Date.parse(new Date(stringTime));
                //console.log(timestamp2);
                timestamp2 = timestamp2 / 1000;
                var nowday = getNowFormatDate();
                //console.log(nowday);
                var timestamps = Date.parse(nowday);
        	    timestamps = timestamps / 1000;
            	var time=timestamp2-timestamps;
           	 if (time<=2592000) {
                 return '<img class="iscreatelclj" onclick="particulars(\''+row.order_no+'\')" style="cursor: pointer;" src= ' +contextPath + '/static/image/kqdsFront/tag/caution.jpg/>';
             }else{
            	 return '<img class="iscreatelclj" onclick="particulars(\''+row.order_no+'\')" style="cursor: pointer;" src= ' +contextPath + '/static/image/kqdsFront/tag/particulars.jpg/>';
             }
           }
        },
        /* {
            title: '类型',
            field: 'basetype',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            },
        }, */
        {
            title: '类型',
            field: 'nexttype',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            },
        },
        {
            title: '分类',
            field: 'classify',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	if (value == 1) {
	            	return "<span>"+ '高危药品' +"</span>";
            	} else if (value == 2) {
            		return "<span>"+ '抗菌素' +"</span>";
            	} else if (value == 3) {
            		return "<span>"+ '其他' +"</span>";
            	} 
            	
            }
        
        },
        {
            title: '包装单位',
            field: 'packing_unit',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            },
        },
        {
            title: '包装数量',
            field: 'packing_num',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            },
        },
        {
            title: '含量单位',
            field: 'content_unit',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            },
        },
        {
            title: '含量系数',
            field: 'content_coe',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            },
        },
        {
            title: '基本单位',
            field: 'company_min',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            },
        },
        {
            title: '剂型',
            field: 'pharm_dos',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            }
        
        },
        
        {
            title: '库存数量',
            field: 'drug_total_num',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            }
        
        },
        {
            title: '零售价',
            field: 'drug_retail_price',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return "<span>"+value+"</span>";
            }
        
        }
        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        selectId = onclickrow = $('#table').bootstrapTable('getData')[index].id;
       // console.log(index);
    });
}
//2019-08-14 展示药物详情
function particulars(obj){
    layer.open({
        type: 2,
        title: '药品详情',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['98%', '350px'],
        content: contextPath + '/HUDH_YkzzDrugsInAct/toYkzzDrugsInDetailByOrderno.act?orderno='+obj
    });
}
//导出
function exportTable() {
	var fieldArr=[];
	var fieldnameArr=[];
	$('#table thead tr th').each(function () {
		var field = $(this).attr("data-field");
		if(field!=""){
			fieldArr.push(field);//获取字段
			fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
		}
	});
	var param  = JsontoUrldata(queryParams());
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
</script>
</html>
