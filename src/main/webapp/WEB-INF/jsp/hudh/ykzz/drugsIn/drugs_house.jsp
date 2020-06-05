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
<title>仓库</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<style type="text/css">
.buttonBar .aBtn_big{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 80px;text-align: center;}
.buttonBar  .aBtn_big:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big2{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 88px;text-align: center;}
.buttonBar  .aBtn_big2:hover{color:#fff;background: #0e7cc9;}
.searchWrap{
	padding-top:35px;
}
.fixed-table-container thead th .sortable {
    padding-right: 0px;
}
</style>
<body>
<div id="container">
    <div class="main">
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" data-height="450" >
                    </table>
                </div>
            </div>
        <!--查询条件-->
        <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <table style="width: 100%;">
           		<tr>
           			<td style="text-align:right;"> 
	           			一级分类：
	           		</td>
           			<td style="text-align:left;"> 
	           			<select class="select2"  name="basetype" id="basetype" style="width: 120px"> </select>
	           		</td>
	           		<td style="text-align:right;"> 
	           			二级分类：
	           		</td>
	           		<td style="text-align:left;"> 
	           			<select class="select2"  name="nexttype" id="nexttype" style="width: 120px">
                       		 <option value="">请选择</option>
                	  	 </select>
	           		</td>
	           		<td style="text-align:right;"> 
	           			模糊查询：
	           		</td>
	           		<td style="text-align:left;"> 
	           			<input type="text" id="queryInput" class="searchInput" placeholder="请输入商品名称/规格  等">
	           		</td>
	           		<td style="text-align:right;"> 
	           			 <div class="btnBar">
			                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clear">清空</a>
			                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			            </div>
	           		</td>
           		</tr> 
            </table>
            
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript">
var apppath = apppath();
var $table = $('#table');
var pageurl = apppath + '/HUDH_YkzzAct/selectAllDrugsInfor.act';
var onclickrow = ""; //选中行对象
var number = 1;
$(function() {
	//初始化一级分类
	initSelectBaseType($("#basetype"));
	//选中一级分类获取对应的耳机分类
	$("#basetype").on("change",function (){
		$("#nexttype").val("");
		if($(this).val()) {
			initSelectNextType($("#nexttype"),$(this).val());
		}
	});
	
    initTable(pageurl); //加载表格
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
    
});

//查询
$('#query').on('click',function() {
   refresh();
});

function refresh() {
    $table.bootstrapTable('refresh', {
        'url': pageurl
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
$('#clear').on('click',function() {
    $("#queryInput").val("");
    $(".select2").val("");
});
//加载表格
function initTable(pageurl) {
    //加载表格 修改时间：2019/04/03 10:20   修改人：多永浩     修改内容:新增规格 
    $table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams : queryParams,
        cache: false,
        striped: true,
        columns: [
		{
    		title: 'id',
    		field: 'id',
    		align: 'center',
    		visible: false,
		},
        {
            title: '药品编号',
            field: 'order_no',
            align: 'center',
            sortable: true,
            formatter:function(value){
               return '<span>'+value+'</span>';
            }
        },
        {
            title: '国家编码',
            field: 'contry_code',
            align: 'center',
            formatter:function(value){
               return '<span>'+value+'</span>';
            }
        },
        {
            title: '一级分类',
            field: 'basetype',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '二级分类',
            field: 'nexttype',
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
            formatter: function(value, row, index) {
            	html = '<span>' + value + '</span>';
                return html;
            }
        },
        {
            title: '规格',
            field: 'pharm_spec',
            align: 'center',
            formatter: function(value, row, index) {
            	html = '<span>' + value + '</span>';
                return html;
            }
        },
        {
            title: '商品名',
            field: 'good_name',
            align: 'center',
            formatter: function(value, row, index) {
            	html = '<span>' + value + '</span>';
                return html;
            }
        },
        {
            title: '单位',
            field: 'company',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '结存均价',
            field: 'nuitPrice',
            align: 'center',
           
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '库存数量',
            field: 'drug_total_num',
            align: 'center',
           
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '结存金额',
            field: 'drgs_total_money',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }
        ]
    }).on('dbl-click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
        insertTodetail();
    });
}
//双击获取对象 增加商品明细
function insertTodetail() {
    var param = {
    		id: onclickrow.id, //id
    		drugs_typeone : onclickrow.drugs_typeone, //一级分类
    		drugs_typetwo: onclickrow.drugs_typetwo, //二级分类
    		basetype : onclickrow.basetype, //一级分类名称
    		nexttype: onclickrow.nexttype, //二级分类名称
    		chemistry_name: onclickrow.chemistry_name, //化学名
    		order_no : onclickrow.order_no, //编号
    		contry_code: onclickrow.contry_code, //国家编码
    		pharm_spec:onclickrow.pharm_spec, //规格
    		company: onclickrow.company, //单位
    		nuitPrice: onclickrow.nuitPrice, //结存均价
    		drug_total_num: onclickrow.drug_total_num, //数量
    		outnum : "230",
    		irows:1
    };
    parent.getDrugdBatchnum(param);
    //调用父页面 增加商品明细
    parent.addDoodsDetail(param);
    layer.alert('已添加商品！'  );
}
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchWrap').outerHeight();
    $('.extraBar .extraBd').height(baseHeight - 65);
    $('#table').bootstrapTable('resetView', {
        height: baseHeight - serachH - 5
    });

}
function add(){
	layer.open({
        type: 2,
        title: '添加商品',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['520px', '360px'],
        content: contextPath + '/KQDS_Ck_GoodsAct/toSave.act'
    });
}
</script>
</html>
