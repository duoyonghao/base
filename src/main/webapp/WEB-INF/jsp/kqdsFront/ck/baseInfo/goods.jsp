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
	/* 权限按钮 */
	.costHd{
		border: 0px solid #ddd;
		margin-top: 10px;
	}
	.costHd label{
		display: inline-block;
	    box-sizing: border-box;
	    height: 26px;
	    line-height: 26px;
	    background: #fff;
	    color: #00a6c0;
	    outline: none;
	    padding: 0 15px;
	    border: 1px solid #00a6c0;
	    border-radius: 3px;
	    margin-right: 3px;
	    text-decoration: none;
	    cursor: pointer;
	    text-align: center;
	}
	.webuploader-pick{
		color: #00a6c0;
	}
</style>
</head>
<body>
<div id="container">
	<div class="costWrap">
			<div class="tableBox" style="padding-top: 8px;">
				<table id="table" class="table-striped table-condensed table-bordered"></table>
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
        <!--  权限按钮 -->
        <div class="costHd" id="jcxx" style="width:742px;margin-top: 10px;"></div> 
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
var pageurl = '';
//初始化表头，返回空的数据
var nullUrl = contextPath + '/UtilAct/intTableHeader.act';
var onclickrow = ""; //选中行对象
var goodstype = "",
goodstypename = "",
sshouse = "",
sshousename = ""; //添加商品时获取选中的分类值
var onclickrowOobj="";//向商品资料页面传选中商品对象
var menuid=<%=menuid%>;
$(function() {
    // 获取当前页面所有按钮，并赋值给该页面的全局变量 listbutton
    getButtonAllCurPage("<%=menuid%>");
    if(menuid==603100){
    	pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectList.act?type=2';
    	getHouseSelect("sshouse");
    }else{
    	pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectList.act?type=1';
    	getHouseSelect("sshouse");
    }
    $('#sshouse').change(function() {
        if ($(this).val()) { // 如果一级有值，再请求
         getBaseTypeSelect('basetype', this.value);
        }
    });
        // 左侧树形目录初始化
//     zTreeInit();
    initTable(); //加载表格
    $(window).resize(function() {
//         setHeight();
        setWidth();
        
        var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 100;
        $(".fixed-table-container").outerHeight(calculateHeight);
        $(".costWrap").outerHeight(calculateHeight+60);
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
        $('#table').bootstrapTable('refresh', {
        'url': pageurl,
    });
});

//清空
$('#clear').on('click',
function() {
	$("#sshouse").val("");
	$("#basetype").val("");
	$("#nexttype").val("");
    $("#queryInput").val("");
});

function refresh() {
	//console.log(goodstype+"----------goodstype");
    var pareurl1 = pageurl + "&queryInput=" + $("#queryInput").val() + "&basetype=" + $("#basetype").val() + "&nexttype=" + $("#nexttype").val() + "&sshouse=" + sshouse + "&goodstype=" + goodstype;
    $('#table').bootstrapTable('refresh', {
        'url': pareurl1
    });
}

//重写uploadutil.js中的fz()方法，实现该页面的自有业务
//该方法在文件上传成功后执行
function fz() {
    jQuery(".file-item").hide();
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
        	//console.log(JSON.stringify(data)+"---------------haha"); 
            /*滚动条出现 */
            setTableHeaderWidth(".tableBox");
            var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 100;
            $(".fixed-table-container").outerHeight(calculateHeight);
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
    }).on('dbl-click-row.bs.table',
    function(e, row, element) {
		//console.log(JSON.stringify(row)+"--------双击行");
		var index = $('#table').find('tr.success').data('index'); //获得选中的行
		onclickrowOobj = $('#table').bootstrapTable('getData')[index];
		if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
            layer.alert('请选择商品', {
                  
            });
            return false;
        }
        // 修改资料
        layer.open({
            type: 2,
            title: '商品资料',
            shadeClose: false,
            shade: 0.6,
            area: ['750px', '65%'],
            content: contextPath + '/KQDS_Ck_GoodsAct/toCkBaseInfoGoodShow.act'
        });
    });
}
function unallowable(status){
	if (onclickrow == "") {
		if(status==0){
			layer.alert('请选择需启用的商品！' );
		}else{
			layer.alert('请选择需禁用的商品！' );
		}
        return false;
    }
	var seqId=onclickrow.seqId;
	var data={	seqId:seqId,
    			status:status}
	var url = contextPath + '/KQDS_Ck_GoodsAct/unallowable.act';
	$.axse(url, data,
	    function(data) {
	        if (data.retState == "0") {
	        	initTable();
	        }else{
	        	//请求出错处理
		    	layer.alert(data.retMsrg);
	        }
	    },
	   function(){
	        
	    })
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
function allocatingOutbound(){
	 layer.open({
	        type: 2,
	        title: '商品调拨',
	        maxmin: false,
	        shadeClose: false,
	        //点击遮罩关闭层
	        area: ['95%', '750px'],
	        content: contextPath + '/KQDS_Ck_GoodsAct/toCkOutGoods.act?menuid='+menuid
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
