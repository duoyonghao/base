<!--wl整理  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
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
<title>退药明细</title>
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
</style>
</head>
<body>
<div id="container">
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
						<label>开药日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
               		<div class="kv">
						<label>患者编号</label>
						<div class="kv-v">
							<input type="text" name="usercode" id="usercode" />
						</div>
					</div>
					  <div class="kv">
		                <label>患者名称</label>
		                <div class="kv-v">
		                    <input type="text" name="username" id="username" />
		                </div>
		            </div>
					<div class="kv">
		                <label>药品编号</label>
		                <div class="kv-v">
		                 <input type="text" name="itemno" id="itemno" />
		                </div>
		            </div>
					 <div class="kv">
		                <label>药品名称</label>
		                <div class="kv-v">
		                 <input type="text" name="itemname" id="itemname" />
		                </div>
		            </div>
                </div>
            </div>
        </div> 
    <div class="tableHd">退货清单</div>
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
var pageurl = contextPath + '/HUDH_YkzzDrugsInExamineAct/findDugsReturnDetail.act';
var nowday;
var qxnameArr = ['ck_ckmx_scbb'];
var func = ['exportTable'];
$(function() {
	getDeptSelect("sqdeptid");
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
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    OrderDetail();
    
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        usercode : $('#usercode').val(),
        username: $('#username').val(),
        itemno: $('#itemno').val(),
        itemname: $('#itemname').val()
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
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
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
	        	allmoney += Number(data[i].paymoney);
	        	nums += Number(data[i].num);
	        }
        	$("#allmoney").html(allmoney.toFixed(3));
        	$("#nums").html(nums);
        	setHeight()
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [ {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '药品编号',
            field: 'itemno',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span>'+value+'</span>';
            }
        },{
            title: '药品名称',
            field: 'itemname',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
		{
		    title: '单位',
		    field: 'unit',
		    align: 'center',
		    formatter: function(value, row, index) {
		    	return '<span>'+value+'</span>';
            }
		},{
            title: '药品单价',
            field: 'unitprice',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '退药数量',
            field: 'returndrugsnum',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '药品批号',
            field: 'batchno',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '小计',
            field: 'returnmoney',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span>"+Number(value).toFixed(3)+"</span>";
            }
        },
        {
            title: '退费金额',
            field: 'returnmoney',
            align: 'center',
            formatter:function(value,row,index){
            	return "<span>"+Number(value).toFixed(3)+"</span>";
            }
        },
        {
            title: '入库时间',
            field: 'returntime',
            align: 'center',
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        }
       ]
    });
}
</script>
</html>
