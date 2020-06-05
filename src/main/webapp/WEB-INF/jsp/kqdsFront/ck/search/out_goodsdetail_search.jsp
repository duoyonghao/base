<!--wl整理  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
      contextPath = "/kqds";
  }
//获取从左侧菜单点击带过来的菜单id
String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>出库明细</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
	.fixed-table-container{
		border:none;
	}
	.tableBox{
		/*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    }
    .searchWrap .kv{
    	height:30px;
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
<style type="text/css">
td>span.source{width:auto !important;} 
</style>
<body>
<div id="container">
    <div class="tableHd">出货清单</div>
    <div class="tableBox" id="divkdxm">
        <table id="table" class="table-striped table-condensed table-bordered" data-height="400"></table>
    </div>
    <div class="tableBox">
    	<table style="width: 100%"> 
       		<tr>
 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total">0</lable>条</span></td>
 				<td width="30%"><span style="color:#00A6C0;">数量：<lable id="nums">0</lable></span></td>
 				<td width="30%"><span style="color:#00A6C0;">金额：<lable id="allmoney">0</lable></span></td>
       		</tr> 
       	</table>
    </div>
       <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
            	<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
            </div>
            <div class="formBox">
               	<div class="">
               		<div class="kv">
						<label>录单日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv">
		                <label>出库方式</label>
		                <div class="kv-v">
		                    <select  name="outtype" id="outtype">
		                    	<option value="">请选择</option>
		                    	<option value="1">领用出库</option>
		                    	<option value="3">换货出库</option>
		                    	<option value="5">退货出库</option>
		                    	<option value="7">其他出库</option>
		                    </select>
		                </div>
		            </div>
               		<div class="kv">
						<label>出库仓库</label>
						<div class="kv-v">
							<select  name="outhouse" id="outhouse"></select>
						</div>
					</div>
					  <div class="kv">
		                <label>供应商</label>
		                <div class="kv-v">
		                    <select  name="supplier" id="supplier"></select>
		                </div>
		            </div>
					<div class="kv">
		                <label>领用部门</label>
		                <div class="kv-v">
		                 <select name="sqdeptid" id="sqdeptid" ></select>
		                </div>
		            </div>
					 <div class="kv">
						<label>领料人</label>
						<div class="kv-v">
							  <input type="hidden" name="llr" id="llr" placeholder="领料人" title="领料人" class="form-control" />
							  <input type="text"   id="llrDesc" name="llrDesc" placeholder="领料人" readonly style="width: 120px;"onClick="javascript:single_select_user(['llr', 'llrDesc'],'single',1);"  ></input>	
						</div>
					</div>
					 <div class="kv">
						<label>申请医生</label>
						<div class="kv-v">
							  <input type="hidden" name="sqdoctor" id="sqdoctor" placeholder="申请医生" title="申请医生" class="form-control" />
							  <input type="text"   id="sqdoctorDesc" name="sqdoctorDesc" placeholder="申请医生" readonly style="width: 120px;"onClick="javascript:single_select_user(['sqdoctor', 'sqdoctorDesc'],'single',1);"  ></input>	
						</div>
					</div>
					<div class="kv ">
						<label>商品类别</label>
						<div class="kv-v">
							 <input type="hidden" name="goodstype" id="goodstype" />
				             <input type="text" name="goodstypename" id="goodstypename" readonly="readonly" onclick="selectGoodsTypezTreeInit();">
						</div>
					</div>
					<div class="kv" >
						<label>模糊查询</label>
						<div class="kv-v">
							  <input type="text" name="goodsname" id="goodsname" placeholder="商品名称/编号" style="width: 150px;"/>
						</div>
					</div>
                </div>
            </div>
        </div> 
</div>
<div id="menuContent" class="menuContent" style="background:#DDDDDD;display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>	
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl;
var nowday;
var qxnameArr = ['ck_ckmx_scbb'];
var func = ['exportTable'];
var menuid=<%=menuid%>;
$(function() {
// 	getDeptSelect("sqdeptid");
	getSupplierSelectKeshi("sqdeptid");//搜索初始化
	getButtonPowerByPriv(qxnameArr,func);//根据角色查询权限按钮
    initDeptSelectByTypesAndClass('<%=ChainUtil.getCurrentOrganization(request)%>');
    getHouseSelect("outhouse");
    getSupplierSelect2("supplier");
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    if(menuid==603100){
    	pageurl = contextPath + '/KQDS_Ck_Goods_Out_DetailAct/inSearchList.act?type=2';
    }else{
    	pageurl = contextPath + '/KQDS_Ck_Goods_Out_DetailAct/inSearchList.act?type=1';
    }
    OrderDetail();
    
    $(window).resize(function() {
//      setHeight();
     setWidth();
     
//     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 95; 
     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 60;
     $(".fixed-table-container").outerHeight(calculateHeight);
 });
    
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	outtype: $('#outtype').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        goodstype : $('#goodstype').val(),
        outhouse: $('#outhouse').val(),
        supplier: $('#supplier').val(),
        sqdeptid: $('#sqdeptid').val(),
        llr: $('#llr').val(),
        sqdoctor: $('#sqdoctor').val(),
        goodsname: $('#goodsname').val()
    };
    return temp;
}
$('#query').on('click',
function() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#supplier").select2("val", " ");
    $("#sqdeptid").select2("val", " ");
});
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
	location.href = pageurl+"&flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
function setHeight(){
	var windowHeight=$(window).outerHeight();
	var tableHeight=windowHeight-$(".searchWrap").outerHeight()-58
	$("#table").bootstrapTable("resetView",{
		height: tableHeight
	})
	
}
function OrderDetail() {
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	$("#total").html(data.length);
        	var nums=0,allmoney=0;
	        for(var i=0;i<data.length;i++){
	        	allmoney += Number(data[i].ckmoney);
	        	nums += Number(data[i].outnum);
	        }
        	$("#allmoney").html(allmoney.toFixed(3));
        	$("#nums").html(nums);
        	setHeight()
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
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
            title: '单据编号',
            field: 'outcode',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
//         {
//             title: '库位',
//             field: 'kuwei',
//             align: 'center',
//             formatter: function(value, row, index) {
//             	html = '<span class="source" title="'+value+'">' + value + '</span>';
//                 return html;
//             }
//         },
        {
            title: '入库时间',
            field: 'addtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span>' + value.substring(0,10) + '</span>';
                return html;
            }
        },
        {
            title: '入库编号',
            field: 'addnumber',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	return "<span style='float:left;position:relative;top:-1px;text-align:left;'>"+value+"</span>";
            }
        },
        {
            title: '出库仓库',
            field: 'housename',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '一级类别',
            field: 'firsttype',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
		{
		    title: '二级类别',
		    field: 'goodstypename',
		    align: 'center',
		    formatter: function(value, row, index) {
            	html = '<span class="name" title="'+value+'">' + value + '</span>';
                return html;
            }
		},
		{
            title: '商品编号',
            field: 'goodscode',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	return "<span style='float:left;position:relative;top:-1px;text-align:left;'>"+value+"</span>";
            }
        },
        {
            title: '商品名称',
            field: 'goodsname',
            align: 'center',
            formatter: function(value, row, index) {
            	html = '<span style="width:auto;float:left;position:relative;top:-1px;text-align:left;" title="'+value+'">' + value + '</span>';
                return html;
            }
        },
        {
            title: '规格',
            field: 'goodsnorms',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span style='float:left;position:relative;top:-1px;text-align:left;'>"+value+"</span>";
            }
        },
        {
            title: '单位',
            field: 'goodsunit',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '单价',
            field: 'outprice',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '出库数量',
            field: 'outnum',
            align: 'center',
            formatter:function(value,row,index){
            	if(Number(value)<0){
            		return '<span style="color:red;">'+value+'</span>';
            	}else{
            		return '<span>'+value+'</span>';
            	}
            }
        },
        {
            title: '出库金额',
            field: 'ckmoney',
            align: 'center',
            formatter:function(value,row,index){
            	if(Number(value)<0){
            		return '<span class="money" style="color:red;">'+value+'</span>';
            	}else{
            		return '<span class="money">'+value+'</span>';
            	}
            }
        },
        {
            title: '批号',
            field: 'ph',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '出库方式',
            field: 'outtype',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span class='money'>"+value+"</span>";
            }
        },
        {
            title: '供应商',
            field: 'suppliername',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span class='source'>"+value+"</span>";
            }
        },
        {
            title: '领料人',
            field: 'llr',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '录单时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span>' + value + '</span>';
                return html;
            }
        },
        {
            title: '出库时间',
            field: 'cktime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span>' + value.substring(0,10) + '</span>';
                return html;
            }
        },
        {
            title: '领用部门',
            field: 'sqdeptid',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '领用医生',
            field: 'sqdoctor',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '申请备注',
            field: 'sqremark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span class="remark" title="' + value + '">' + value + '</span>';
                return html;
            }
        }
       ]
    });
}
</script>
</html>
