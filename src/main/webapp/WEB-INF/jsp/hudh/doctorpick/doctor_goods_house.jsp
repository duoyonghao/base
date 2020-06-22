<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String sshouse = request.getParameter("sshouse");
	if(sshouse == null ){
		sshouse = "";
	}
	String type = request.getParameter("type");//0 出库（隐藏添加商品按钮） 1 入库
	if(type == null ){
		type = "1";
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
</head>
<style type="text/css">
.buttonBar .aBtn_big{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 80px;text-align: center;}
.buttonBar  .aBtn_big:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big2{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 88px;text-align: center;}
.buttonBar  .aBtn_big2:hover{color:#fff;background: #0e7cc9;}
.searchWrap{
	padding-top:35px;
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
           			<!-- 
           			<td style="text-align:right;"> 
	           			一级类别：
	           		</td>
           			<td style="text-align:left;"> 
	           			<select class="select2"  name="basetype" id="basetype" style="width: 120px"> </select>
	           		</td>
	           		<td style="text-align:right;"> 
	           			二级类别：
	           		</td>
	           		<td style="text-align:left;"> 
	           			<select class="select2"  name="nexttype" id="nexttype" style="width: 120px">
                       		 <option value="">请选择</option>
                	  	 </select>
	           		</td> -->
	           		<td style="text-align:right;"> 
	           			模糊查询：
	           		</td>
	           		<td style="text-align:left;"> 
	           			<input type="text" id="queryInput" class="searchInput" placeholder="请输入商品编号、名称/规格/科室">
	           		</td>
	           		<!-- 
	           		<td style="text-align:right;"> 
	           			模糊查询：
	           		</td>
	           		<td style="text-align:left;"> 
	           			<input type="text" id="queryInput" class="searchInput" placeholder="请输入商品名称/规格  等">
	           		</td> -->
	           		<td style="text-align:right;"> 
	           			 <div class="btnBar">
	           			  	<!-- <a href="javascript:void(0);" class="kqdsCommonBtn" id="add" onclick="add();">添加商品</a> -->
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var sshouse = '<%=sshouse%>';
var deptpart = '<%=request.getAttribute("deptpart")%>';
//alert(deptpart);
var type = '<%=type%>';
var $table = $('#table');
var pageurl = contextPath + '/HUDH_KSllAct/findAllKsllColorGoods.act';
var onclickrow = ""; //选中行对象
var number = 1;
$(function() {
	if(type=="0"){
		$("#add").hide();
	}
	getBaseTypeSelect('basetype');
	pageurl = pageurl + "?sshouse=" + sshouse;
    initTable(pageurl); //加载表格
    //查询
    $('#query').on('click',
    function() {
        var queryInput = $("#queryInput").val();
        pageurl1 = pageurl + '&queryInput=' + queryInput+"&basetype="+$("#basetype").val()+"&nexttype="+$("#nexttype").val();
        $('#table').bootstrapTable('refresh', {
            'url': pageurl1
        });
    });
    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
    //initTable();//初始化表格
    refresh();
});

$('#basetype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
		getNextTypeSelect('nexttype',this.value);
	}
});
//清空
$('#clear').on('click', function() {
    $("#queryInput").val("");
});

function refresh() {
    $table.bootstrapTable('refresh', {
        'url': pageurl
    });
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
   		deptpart : deptpart,
   		initialize:1
    };
    return temp;
}

//加载表格
function initTable() {
    //加载表格
    $table.bootstrapTable({
    	url: pageurl,
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [5, 25, 50, 75],//可以选择每页大小
      //在表格底部显示分页工具栏 
        singleSelect: true,
        paginationShowPageGo: true,
        queryParams: queryParams,
        cache: false,
        striped: true,
        sidePagination: "server",
        onLoadSuccess:function(data){
        	//alert(JSON.stringify(data));
        },
        columns: [
       	{
            title: '所属仓库',
            field: 'housename',
            align: 'center',
           
            sortable: true,
            formatter:function(value){
            	if(value){
            		return '<span>'+value+'</span>';
            	}else{
            		return '<span>-</span>';
            	}
            	
            }
        },
        {
            title: '科室',
            field: 'deptpartname',
            align: 'center',
           
            formatter: function(value, row, index) {
            	html = '<span class="name" title="'+value+'">' + value + '</span>';
                return html;
            }
        },
        /* {
            title: '一级类别',
            field: 'firsttype',
            align: 'center',
           
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '二级类别',
            field: 'goodstypename',
            align: 'center',
           
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },  */
        {
            title: '商品编号',
            field: 'goodscode',
            align: 'center',
           
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '商品名称',
            field: 'goodsname',
            align: 'center',
            formatter: function(value, row, index) {
            	html = '<span>'+value+'</span>';
                return html;
            }
        },
        {
            title: '商品规格',
            field: 'goodsnorms',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '单位',
            field: 'goodsunit',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '备注',
            field: 'inremark',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        /* {
            title: '结存均价',
            field: 'goodsprice',
            align: 'center',
           
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }, */
        {
            title: '库存数量',
            field: 'nums',
            align: 'center',
           
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        /* {
            title: '结存金额',
            field: 'kcmoney',
            align: 'center',
           
            formatter: function(value, row, index) {
          	  var html = "";
          	  if(Number(value)<0){
          		  html = '<span style="position:relative;top:3px;font-weight:bold;color:red;font-size: 14px !important;" >' + value + '</span>';
          	  }else{
          		  html =  '<span>'+value+'</span>' ;
          	  }
                return html;
            }
        } */
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
	var flag = true;
	parent.$('#table').find('tbody').each(function() {
	       $(this).find('tr').each(function() {
	           $(this).find('td').each(function() {
	        	   if ($(this).index() == 4) {
	        		   if(onclickrow.goodscode == $(this).find("span").html()) {
	        			   flag = false; 
	        			   return flag;
	        		   }
	        	   } 
	           });
	       });
	});
	if(flag) {
		var param = {
		    	goodsuuid: onclickrow.id,//商品seq_id
		        goodsprice: onclickrow.goodsprice,//商品价格
		        nums: onclickrow.nums,//商品数量
		        housename : onclickrow.housename,//##########所属仓库
		        goodsname: onclickrow.goodsname,//商品名称
		        goodscode: onclickrow.goodscode,//商品编号
		        //goodstype: onclickrow.goodstype,
		        deptpartname: onclickrow.deptpartname,//############科室
		        //sshouse: onclickrow.sshouse,
		        //sshousename: onclickrow.sshousename,
		        goodsnorms: onclickrow.goodsnorms,//商品规格
		        //supplyinformation: onclickrow.supplyinformation,//最近采购价
		        goodsunit: onclickrow.goodsunit//商品单位
		 };
		 //调用父页面 增加商品明细
		 parent.addDoodsDetail(param);
		 layer.alert('已添加商品！');
	} else {
		layer.alert('请勿重复添加！'); 
		return;
	}
    
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
