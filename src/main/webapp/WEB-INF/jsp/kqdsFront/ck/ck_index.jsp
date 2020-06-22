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
<title>仓库首页</title>
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
	   /*  position: relative;
	    display: inline-block;
	    cursor: pointer;
	    background: #fff;
	    padding: 0px 15px;
	    color: #00A6C0;
	    text-align: center;
	    border-radius: 3px;
	    overflow: inherit;
	    border:1px solid #00A6C0;
	    font-size: 14px;  */
	}
	.searchWrap{
		border:none;
	}
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
		/* border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd; */
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
		position: relative;
    	top: -8px;
    	padding-bottom: 78px;
    	background-color: white;
	}
	.searchWrap {
	    position: relative;
	    padding: 40px 10px 5px 10px;
	    border-radius: 5px;
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
</style>
</head>
<body>
<div id="container">
	<div class="costWrap">
		<div class="costHd" id="jcxx"></div>
		<div class="costBd">
			<div class="ztreeWrap">
				 <div class="searchWrap" style="padding: 0px;">
		            <input type="text" placeholder="搜索" name="search" id="search" class="searchInput" style="width: 100%;">
		            <a href="javascrpt:;" id="infosearch" class="hc-icon icon16 search-icon searchBtn" onclick="zTreeInit()"></a>
       			 </div>
				 <div class="ztree" style="height: 260px;overflow-y:auto;border:1px solid #ddd;border-radius:6px; ">
					 <ul id="treeDemo" class="ztree">
					 </ul>
				 </div>
			</div>
			<div class="tableBox">
				<table id="table" class="table-striped table-condensed table-bordered"></table>
			</div>
			
		</div>
	</div>
	<div class="formBox">
  	     <!--查询条件-->
        <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
            	<div class="kv">
            		<div class="kv-v">
                        <label>所属仓库：</label>
                        <select class="select2"  name="sshouse" id="sshouse"> </select>
                     </div>
                     <div class="kv-v">
                        <label>一级类别：</label>
                        <select class="select2"  name="basetype" id="basetype"> 
                        	<option value="">请选择</option>
                        </select>
                     </div>
                     <div class="kv-v">
                     	<label>二级类别：</label>
                         <select class="select2"  name="nexttype" id="nexttype">
                       		 <option value="">请选择</option>
                	  	 </select>
                    </div>
                    <div class="kv-v">
                   		<label>模糊查询：</label>
                        <input type="text" id="queryInput" class="searchInput" placeholder="请输入商品名称/规格 等">
                    </div>
                </div>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
            </div>
        </div>
		<div class="operateModel">
            <div class="optBox">
                <!-- <span class="cornerMark"></span> -->
                 <ul id="ul1">
	            </ul>
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
var listbutton = null;
var pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectList.act';
//初始化表头，返回空的数据
var nullUrl = contextPath + '/UtilAct/intTableHeader.act';
var onclickrow = ""; //选中行对象
var goodstype = "",
goodstypename = "",
sshouse = "",
sshousename = ""; //添加商品时获取选中的分类值
$(function() {
    // 获取当前页面所有按钮，并赋值给该页面的全局变量 listbutton
    getButtonAllCurPage("<%=menuid%>");
    getHouseSelect("sshouse");
    //getBaseTypeSelect('basetype');
    // 左侧树形目录初始化
    zTreeInit();
    initTable(); //加载表格
    // 计算主体的宽度，需要在页面加载完成后，再执行计算操作，否则页面会出问题
    //鼠标移入底部操作区 图标变蓝色
 	/* $("#ul1").on("mouseenter","li",function(){
 		
 		$(this).find("img").attr("src",contextPath+"/static/image/kqdsFront/img/icon/book2.png");
 	}); */
 	//鼠标移出底部操作区 图标变灰黑色
 	/* $("#ul1").on("mouseleave","li",function(){
 		
 		$(this).find("img").attr("src",contextPath+"/static/image/kqdsFront/img/icon/book.png");
 	}); */
    $(window).resize(function() {
        setHeight();
        setWidth();
        /* var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 95; */
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
$('#sshouse').change(function() {
    if ($(this).val()) { // 如果一级有值，再请求
    	getBaseTypeSelect('basetype', this.value);
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
    var pareurl1 = pageurl + "?queryInput=" + $("#queryInput").val() + "&basetype=" + $("#basetype").val() + "&nexttype=" + $("#nexttype").val() + "&sshouse=" + sshouse + "&goodstype=" + goodstype;
    $('#table').bootstrapTable('refresh', {
        'url': pareurl1
    });
}

//重写uploadutil.js中的fz()方法，实现该页面的自有业务
//该方法在文件上传成功后执行
function fz() {
    jQuery(".file-item").hide();
    zTreeInit();
    getHouseSelect("sshouse");
    getBaseTypeSelect('basetype');
    refresh();
}

//回访管理新参数
function queryCkParams(params) {
	/* console.log(JSON.stringify(params)+"-------------回访管理参数"); */
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        sshouse: $("#sshouse").val(),
        queryInput:$("#queryInput").val(),
        basetype:$("#basetype").val(),
        nexttype:$("#nexttype").val(),
        sshouse:sshouse,
    };
    return temp;
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
        pageSize: 50,
        pageList : [5, 25, 50, 100],//可以选择每页大小
      //在表格底部显示分页工具栏 
        singleSelect: true,
        paginationShowPageGo: true,
        queryParams: queryCkParams,
        cache: false,
        striped: true,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        onLoadSuccess: function(data) { //加载成功时执行
            /*滚动条出现 */
            setTableHeaderWidth(".tableBox");
            var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 140;
            $(".fixed-table-container").outerHeight(calculateHeight);
        	/* console.log(JSON.stringify(data)+"---------------haha"); */
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
		    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
			}
		},
         {
            title: '所属仓库',
            field: 'sshousename',
            align: 'center',
            sortable: true,
            formatter: function(value) {
                if (value) {
                    return '<span>' + value + '</span>'
                }
            }
        },
        /* {
            title: '库位',
            field: 'kuwei',
            align: 'center',

            formatter: function(value, row, index) {
                html = '<span class="name" >' + value + '</span>';
                return html;
            }
        }, */
        {
            title: '一级类别',
            field: 'firsttype',
            align: 'center',

            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>'
            }
        },
        {
            title: '二级类别',
            field: 'goodstypename',
            align: 'center',

            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>'
            }
        },
        {
            title: '商品编号',
            field: 'goodscode',
            align: 'center',

            sortable: true,
            formatter: function(value, row, index) {
                return "<span style='float:left;text-align:left;position:relative;top:-1px;'>" + value + "</span>"
            }
        },
        {
            title: '商品名称',
            field: 'goodsname',
            align: 'center',

            sortable: true,
            formatter: function(value) {
                return "<span style='width:fit-content;float:left;relative;top:-1px;text-align:left;'>" + value + "</span>"
            }
        },
        {
            title: '商品规格',
            field: 'goodsnorms',
            align: 'center',

            sortable: true,
            formatter: function(value) {
                return "<span style='width:fit-content;float:left;relative;top:-1px;text-align:left;'>" + value + "</span>"
            }
        },
        {
            title: '到期标识',
            field: 'yxdate',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	if(value){
            		var stringTime = value;
                    var timestamp2 = Date.parse(new Date(stringTime));
                    //console.log(timestamp2);
                    timestamp2 = timestamp2 / 1000;
                    var nowday = getNowFormatDate();
                    var timestamps = Date.parse(nowday);
            	    timestamps = timestamps / 1000;
                    //console.log(timestamp2+"---"+timestamps);
                	var time=timestamp2-timestamps;
               	 if (time<=864000) {
                     return '<img class="iscreatelclj" onclick="particulars(\''+row.seq_id+'\')" style="cursor: pointer;" src= ' +contextPath + '/static/image/kqdsFront/tag/caution.jpg/>';
                 }else{
                	 return '<img class="iscreatelclj" onclick="particulars(\''+row.seq_id+'\')" style="cursor: pointer;" src= ' +contextPath + '/static/image/kqdsFront/tag/particulars.jpg/>';
                 }
            	}else{
            		return '<span>-</span>';
            	}
           }
        },
        {
            title: '单位',
            field: 'goodsunit',
            align: 'center',

            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>'
            }
        },
        {
            title: '当前库存',
            field: 'nums',
            align: 'center',

            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>'
            }
        },
        {
            title: '单价',
            field: 'goodsprice',
            align: 'center',

            sortable: true,
            formatter: function(value) {
                return '<span>' + value + '</span>'
            }
        },
        {
            title: '当前库存金额',
            field: 'kcmoney',
            align: 'center',

            sortable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (Number(value) < 0) {
                    html = '<span style="position:relative;top:3px;font-weight:bold;color:red;font-size: 14px !important;" >' + value + '</span>';
                } else {
                    html = '<span>' + value + '';
                }
                return html;
            }
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',

            formatter: function(value, row, index) {
                html = '<span class="time" >' + value + '</span>';
                return html;
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
function zTreeInit() {
    //异步加载
    var search = $("#search").val();
    var url = contextPath + '/KQDS_Ck_GoodstypeAct/getSelectTreeAsync.act?search=' + search;
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
            dataFilter: ajaxDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onclicktree
    };
    delete setting['check'];
    $.fn.zTree.init($("#treeDemo"), setting);
}
function onclicktree(e, treeId, treeNode) {
    onclickrow = "";
    sshouse = "";
    goodstype = "";
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    }
    var pageurlnew = pageurl;
    if (treeNode.isParent && treeNode.pId == null) { // 仓库
        sshouse = treeNode.id;
    } else if (!treeNode.isParent) { // 没有下级分类
        var parentNode2 = treeNode.getParentNode();
        var parentNode1 = parentNode2.getParentNode();
        if (parentNode1 == null) { // 一级分类
            sshouse = parentNode2.id;
            goodstype = treeNode.id;
        } else { // 二级分类
            sshouse = parentNode1.id;
            goodstype = treeNode.id;
        }
    } else { // 有下级分类
        var parentNode2 = treeNode.getParentNode();
        sshouse = parentNode2.id;
        goodstype = treeNode.id;
    }
    refresh();
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
//2019-12-17 展示商品详情
function particulars(obj){
    layer.open({
        type: 2,
        title: '商品详情',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['90%', '350px'],
        content: contextPath + '/KQDS_Ck_Goods_In_DetailAct/toCkInGoodsDetailByGoodsuuid.act?goodsuuid='+obj
    });
}
</script>
</html>
