<!--wl整理  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);

	String currPersonId = person.getSeqId();
	String currPersonName = person.getUserName();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>入库申请单</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/ck/goodsIn/in_goods.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<style>
#table td>span{
	width:100%;
}
#table{
	table-layout:fixed;
}
.fixedHeaderDiv{
	overflow:hidden;
}
.fixedHeaderDiv table{
	table-layout:fixed;
}
.table-bordered,.table-bordered > thead > tr > th, .table-bordered > thead > tr > td{
	border:none;
}
#table td, #table1 td, #table2 td, #listTable td, #dykdxm td, #chufang td{
	border:none;
}
.formBox{
	width : 1000px;
}
</style>
</head>
<body>
<div id="container" onunload="checkLeave()">
    <div class="infoBd">
   		 <form class="form-horizontal" id="form1" style="border-bottom:1px solid #ddd;">
	        <div class="formBox">
	        <table class="kqds_table">
			    		<tr>
			    			<td style="text-align:right;">出库时间：</td>
			    			<td style="text-align:left;"> 
		    					 <input type="text"  class="unitsDate2" name="cktime" id="cktime">
			                </td>
			    			<td style="text-align:right;">出库方式：</td>
			    			<td style="text-align:left;"> 
		    					 <select  name="outtype" id="outtype">
			                    	<option value="">请选择</option>
			                    	<option value="0">换货出库</option>
		                    		<option value="2">退货出库</option>
		                    		<option value="4">其他出库</option>
		                    		<option value="6">备药出库</option>
			                     </select>
			                </td>
			    			<td style="text-align:right;">供应商：</td>
			    			<td style="text-align:left;">
								<select  name="supplier" id="supplier"></select>
							</td>
			    			
			    			<td style="text-align:right;">出库人：</td>
			    			<td style="text-align:left;">
			    				<input type="text"  class="hide" name="outUserId" id="outUserId" readonly="readonly">
		    					<input type="text"  name="outUserName" id="outUserName" autocomplete="off"  readonly="readonly">
			    			</td>
			    		</tr>
			    		<tr>
			    			<td style="text-align:right;">出库部门：</td>
			    			<td style="text-align:left;">
			    				<select name="outDept" id="outDept"></select>
			    			</td>
			    			<td style="text-align:right;">单据编号：</td>
			    			<td style="text-align:left;"> 
		    					 <input type="text" name="outcode" id="outcode" readonly="readonly">
			                </td>
			    			<td style="text-align:right;">附加说明：</td>
			    			<td style="text-align:left;">
								<input type="text" name="outremark" id="outremark" >
							</td>
			    			
			    			<td style="text-align:right;">摘要：</td>
			    			<td style="text-align:left;">
		    					<input type="text" name="zhaiyao" id="zhaiyao">
			    			</td>
			    		</tr>
			    	</table>
			    	<div class="buttonGroup">
			    		 <a href="javascript:void(0);" onclick="xzhw()" class="kqdsCommonBtn">选择药品</a>
			             <a href="javascript:void(0);" onclick="save()" class="kqdsSearchBtn">保&nbsp;&nbsp;存</a>
			    	</div>
	        </div>
        </form>
        <div class="databaseWrap">
	        <div class="biliHistory">
	            <div class="tableHd">商品明细</div>
	            <!--fixedFatherDiv 首行固定表格的父元素样式  -->
	            	<div class="fixedFatherDiv">
	            	<!-- fixedHeaderDiv 固定的表头 -->
			            <div class="fixedHeaderDiv">
			            	 <table class="table-striped table-condensed table-bordered" style="">
			            	    <thead style="height:30px; background: #00A6C0;color:white;">
									<tr>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>药品编号</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>国家编码</span></th>
										<th style="text-align: center; vertical-align: middle; width:150px"><span>化学名称</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle; width:50px"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>单价</span></th>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>库存数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>批号对应数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>出库数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>出库金额</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>备注</span></th>
									</tr>
								</thead>
				            </table>
			            </div>
			            <!-- fixedBodyDiv 滚动的内容 -->
			           <div class="fixedBodyDiv">
				            <table id="table" class="table-striped table-condensed table-bordered" style="width:100%;" onkeydown="keyDown(event)">
			            	    <thead style="height:30px; background: #0e7cc9;color:white;">
									<tr>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>药品编号</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>国家编码</span></th>
										<th style="text-align: center; vertical-align: middle; width:150px"><span>化学名称</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle; width:50px"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>单价</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>批号对应数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>库存数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>出库数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>出库金额</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>备注</span></th>
									</tr>
								</thead>
								<tbody style="background-color: #F0FFFF;text-align: center; height:345px; overflow-y:auto;"></tbody>
				            </table>
			          </div>
		          </div>
             <div class="tableBox">
		    	<table style="width: 100%"> 
		       			<tr>
		 				<td width="30%"><span style="padding-left:10px;color:#00A6C0;">共有记录<lable id="total1">0</lable>条</span></td>
		 				<td width="30%"><span style="color:#00A6C0;">数量小计：<lable id="innumall">0</lable></span></td>
		 				<td width="30%"><span style="color:#00A6C0;">金额小计：<lable id="rkmoneyall">0</lable></span></td>
		       		</tr> 
		       	</table>
   			 </div>
	    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>  --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/keyin.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript">
var tdindex = 0;
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var nowday;
var apppath = apppath();
var currPersonId = '<%=currPersonId%>';
var currPersonName = '<%=currPersonName%>';
var batchNum;
/*********************修改的方法*********************/
//选择商品
function xzhw() {
    layer.open({
        type: 2,
        title: '药品列表',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['90%', '450px'],
        content: apppath + '/HUDH_YkzzViewAct/toDrugsHouse.act'
    });
}
/*********************修改的方法*********************/





$(function() {
// 	getDeptSelect("outDept");
	getSupplierSelectKeshi("outDept");
	initSelectsupplier($("#supplier"));
    $("#outcode").val(gettimestr());
    $(".unitsDate2").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
    $("#cktime").val(nowday);
    $("#outUserId").val(currPersonId);
    $("#outUserName").val(currPersonName);
    
    /* 计算并设置table的高度 */
    resizeTableHeight();
    /*页面大小改变时 计算并设置table的高度 */
    $(window).resize(function(){
    	resizeTableHeight();
    });
    /*内容与表头一起滚动  */
    $(".fixedBodyDiv").scroll(function(){
    	$(".fixedHeaderDiv").scrollLeft($(this).scrollLeft());
    })
});
/* 计算 设置table数据表的高度 */
function resizeTableHeight(){
	var tableHeight=0;
    tableHeight=$(window).height()-$(".formBox").outerHeight()-62;
    $(".fixedBodyDiv").outerHeight(tableHeight);
    /* 出现自动出现滚动条时 会导致表头内容对不齐  现改为 开始就出现滚动条  */
    $(".fixedHeaderDiv table").outerWidth($(".fixedHeaderDiv").outerWidth()-15);/* 表头留出右边15px的滚动条出现距离 */
}
//更换仓库，置空商品列表
/* $("#inhouse").change(function(){
	$('#table').find('tbody').html("");
	$("#total1").html(0);
}); */


var batchNumoption;
function getDrugdBatchnum(param) {
	var batchnumUrl = apppath + '/HUDH_YkzzDrugsInAct/findBatchnumByOrderno.act';
//	var param = {orderno : "DRUG000018"};
	$.axseSubmit(batchnumUrl, param, function() {}, function(r) {
		/* console.log(param);	 */
		batchNumoption=r;
		//console.log(batchNumoption);
	}, function() {
		layer.alert("查询出错！");
	});
}
//从货物列表页面（goosd_house.jsp），调用改方法
function addDoodsDetail(detail){
	 /* console.log(detail);
	 alert(JSON.stringify(detail)); */
	 tdindex++;
	 //显示条数
	 var total1  = $("#total1").html();
     $("#total1").html(Number(total1)+1);
	 var tablehtml = "";
	 tablehtml += "<tr style=''>";
	 //操作0
     tablehtml += '<td style="width:60px;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
     //类别2
     //药品编号1
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span>'+detail.order_no+'</span></td>';
   	 //国家编码2
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span>'+detail.contry_code + '</span></td>';
     //化学名称3
     tablehtml += '<td style="width:150px;" style="text-align:center;"><span>' + detail.chemistry_name + '</span></td>';
     //一级类别4
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span>' + detail.basetype+ '</span></td>';
     //二级类别5
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span>'+detail.nexttype+'</span></td>';
     //规格 6    修改时间：2019/04/03 10:20   修改人：多永浩     修改内容:新增规格 
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span>'+detail.pharm_spec+'</span></td>';
     //单位7
     tablehtml += '<td style="width:50px;" style="text-align:center;"><span>'+detail.company+'</span></td>';
     //单价8
     
     //tablehtml += '<td style="width:100px;" style="text-align:center;" name="nuitPrice'+tdindex+'" id="nuitPrice'+tdindex+'"><span>'+detail.nuitPrice+'</span></td>';
     
     tablehtml += '<td style="width:100px;" style="text-align:center;"><select style="width: 100%;text-align: center;height: 21px;" disabled="disabled" name="nuitPrice'+tdindex+'" id="nuitPrice'+tdindex+'"><option value="0">'+detail.nuitPrice+'</option>';
     //console.log(batchNumoption);
     for (var i = 0; i < batchNumoption.batchnum.length; i++) {
		tablehtml += '<option value='+'id='+batchNumoption.batchnum[i].id+(i+1)+'>'+batchNumoption.batchnum[i].nuitPrice+'</option>'; 
	 } 
     tablehtml += '</select></td>';
     
     //库存数量9
     tablehtml += '<td style="width:100px;" style="text-align:center;"  name="kcnum'+tdindex+'" id="kcnum'+tdindex+'"><span>'+detail.drug_total_num+'</span></td>';
     
     //批号10
     //tablehtml += '<td style="width:100px;"><input type="text" readonly="readonly" style="width:100%;text-align:center;" onfocus="this.select()" name="batchnum' + tdindex + '" id="batchnum'+tdindex+'"></td>';
     /* tablehtml += '<td style="width:50px;" style="text-align:center;"><select style="width: 100%;text-align: center;height: 21px;" name="batchnum" id="batchnum"><option>-请选择批号-</option></select></td>'; */
     tablehtml += '<td style="width:50px;" style="text-align:center;"><select style="width: 100%;text-align: center;height: 21px;" name="batchnum'+tdindex+'" id="batchnum'+tdindex+'" onchange="batchNumselected(this)"><option value="0">-请选择批号-</option>';
    // console.log(batchNumoption);
     for (var i = 0; i < batchNumoption.batchnum.length; i++) {
     //console.log(batchNumoption[i].batchnum);
		tablehtml += '<option value='+'id='+batchNumoption.batchnum[i].id+(i+1)+'>'+batchNumoption.batchnum[i].batchnum+'</option>'; 
	 } 
     tablehtml += '</select></td>';
     /* $("#batchnum"+tdindex).change(function(){
    	 console.log($("#batchnum"+tdindex+":selected").val());
     }); */
 	 
     //药品批号对应数量11
     tablehtml += '<td style="width:50px;" style="text-align:center;"><select style="width: 100%;text-align: center;height: 21px;" disabled="disabled" name="batchToNumber'+tdindex+'" id="batchToNumber'+tdindex+'"><option value="0">-请选择批号-</option>';
     //console.log(batchNumoption);
     for (var i = 0; i < batchNumoption.batchnum.length; i++) {
		tablehtml += '<option value='+'id='+batchNumoption.batchnum[i].id+(i+1)+'>'+batchNumoption.batchnum[i].batchnoNum+'</option>'; 
	 } 
     tablehtml += '</select></td>';
     
     
     
     //出库数量12
     tablehtml += '<td style="width:60px;"><input type="text" style="width:100%; text-align:center;" onchange="editPrice(\'outnum\','+tdindex+');" onfocus="this.select()" name="outnum'+tdindex+'" id="outnum'+tdindex+'"></td>';
     
     //出库金额13
     tablehtml += '<td style="width:100px;"><input type="text" readonly="readonly" style="width:100%;text-align:center;" onfocus="this.select()" name="ckmoney'+tdindex+'" id="ckmoney'+tdindex+'"></td>';
     
     //入库备注14
     tablehtml += '<td style="width:120px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="outremark" id="outremark"></td>';
     //商品主键15
 	 tablehtml += '<td style="display:none;">' + detail.id + '</td>'; 
 	 //一级分类id16
 	 tablehtml += '<td style="display:none;">' + detail.drugs_typeone + '</td>';
 	 //二级分类id17
 	 tablehtml += '<td style="display:none;">' + detail.drugs_typetwo + '</td>';
     tablehtml += "</tr>";
     $('#table').find('tbody').append(tablehtml);
     $(".unitsDate").datetimepicker({
    		language:  'zh-CN',  
    		minView:2,
    	    autoclose : true,
    		format: 'yyyy-mm-dd',
    		pickerPosition: "bottom-right"
     });
}
/* 药品对应数量联动方法 */
function batchNumselected(obj){
	 //console.log("进入选中方法");
	 var batchNumSelectedIndex=$(obj).val();
	 //console.log(batchNumSelectedIndex);
	 //console.log($(obj).attr("id"));
	 var batchNumIndex=$(obj).attr("id").substring(8);
	 //console.log(batchNumIndex);
	 $("#batchToNumber"+batchNumIndex).val(batchNumSelectedIndex);
	 $("#nuitPrice"+batchNumIndex).val(batchNumSelectedIndex);
};
/**
 * 给出库药品批号赋值
 */
/* var batchnumUrl = apppath + '/HUDH_YkzzDrugsOutAct/getDrugsInBatchnum.act';
var outnum = $("#outnum").val();
//alert(outnum);
var param = {outnum : outnum, order_no : order_no};
function getDrugdBatchnum() {
	$.axseSubmit(batchnumUrl, param, function() {}, function(r) {
		if (r.retState == "0") {
			batchNum = r.dataBatchnum;
        } else {
            layer.alert('添加失败：' + r.retMsrg, {//后台抛出的异常信息在前台展示
            	end: function() {
                	parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
                }
            });
        }
//		alert(JSON.stringify(batchNum));
	}, function() {
		layer.alert("查询出错！");
	});
} */

/* function getDrugdBatchnum(param) {
	var batchnumUrl = apppath + '/HUDH_YkzzDrugsInAct/findBatchnumByOrderno.act';
//	var param = {orderno : "DRUG000018"};
	$.axseSubmit(batchnumUrl, param, function() {}, function(r) {
		console.log(param);	
		var optionvalues=r;
		 for (var i = 0; i < r.length; i++) {
				console.log(JSON.stringify(r[i].batchnum));
			  $("#batchnum").append(
				  '<option>' + r[i].batchnum + '</option>' 
			  );
	    } 
	}, function() {
		layer.alert("查询出错！");
	});
}   */

/* function getZczh(goodsid){
	var jobj = '';
	var url = apppath +'/KQDS_Ck_Goods_InAct/selectZczh.act?goodsid=' + goodsid;
    $.axse(url, null,
    function(data) {
    	jobj = data;
    },
    function() {
    });
    return jobj;
}
 *///删除行
function deltr(obj) {
    var i = obj.parentNode.parentNode.rowIndex;
    document.getElementById('table').deleteRow(i);
    updatexj();
}
function editPrice(id,index){
	 var kcnum = $("#kcnum"+index).find("span").text();
	 var outnum = $("#outnum"+index).val();
	 var batchnum = $("#batchnum"+index).find("option:selected").text();
	 var batchToNumber = $("#batchToNumber"+index).find("option:selected").text();
	 //var nuitPrice = $("#nuitPrice"+index).find("span").text();
	 var nuitPrice = $("#nuitPrice"+index).find("option:selected").text();
	 /* if(id=="outnum"){
		 if(judgeSignNum(outnum)==false){
			 layer.alert('数量格式不正确！' );
			return false;
		 }
	 } */
	 if(judgeSignNum(outnum)==false){
		 layer.alert('数量格式不正确！' );
		 $("#outnum"+index).val("");
		 return false;
	 }
	 if(parseInt(outnum) > parseInt(kcnum)) {
		 layer.alert('库存不足！' );
		 $("#outnum"+index).val("");
		 $("#ckmoney"+index).val("");
		 return false;
	 }
	 $("#ckmoney"+index).val(Number(outnum * nuitPrice).toFixed(3));
	 /* if (batchnum) {
		 layer.alert("请先选择出库药品批号，再填写出库数量！");
		 return false;
	 } */
	 if (parseInt(outnum) > parseInt(batchToNumber)) {
		 layer.alert("出库数量大于入库批号对应的数量，请从新填写出库数量！");
		 $("#outnum"+index).val("");
		 $("#ckmoney"+index).val("");
		 return false;
	 }
	 updatexj();
}
function updatexj(){
	 //改变出库单价（领用出库、其他）
	 var rows = $("#table tbody tr").length;
	 if(rows > 0) {
		 rows = findMaxIndex(); //获取当前列表中最大的下标进行循环，防止删除后导致下标未更新金额计算错误
	 }
	 var innumall=0,rkmoneyall=0;
	 for(var i=1;i<=rows;i++){
		 var outnum = $("#outnum"+i).val();
		 var ckmoney = $("#ckmoney"+i).val();
		 if(outnum != null && outnum != ""){
			 innumall += Number(outnum);
		 }
		 if(ckmoney != null && ckmoney != ""){
			 rkmoneyall += Number(ckmoney);
		 }
	 }
	 $("#innumall").html(Number(innumall));
	 $("#rkmoneyall").html(Number(rkmoneyall).toFixed(3));
} 
function findMaxIndex(){
	var maxTd = $("#table tbody tr:last").find("input[name^='ckmoney']");
	return maxTd.attr("name").replace("ckmoney","");
}
function save() {
    //验证
    var supplier = $("#supplier").val();
    var outtype = $("#outtype").val();
    var cktime = $("#cktime").val();
    var outDept = $("#outDept").val();
    if ($("#table tr").length < 2) {
        layer.alert('请选择出库药品' );
        return false;
    }
    if (outtype == "" || outtype == null) {
        layer.alert('请选择出库方式' );
        return false;
    } 
    if (cktime == "" || cktime == null) {
        layer.alert('请选择出库时间' );
        return false;
    }
    if (supplier == "" || supplier == null) {
        layer.alert('请选择供应商'  );
        return false;
    }
    /* if(outtype == "6") { //备货出库必须选择出库部门
    	if (outDept == "" || outDept == null) {
            layer.alert('备货出库，请选择出库部门' );return false;
        }
    } */
    if (outDept == "" || outDept == null) {
        layer.alert('备货出库，请选择出库部门' );return false;
    }
   //循环获取表格中项目
   var flag= true;//验证入库商品中必填项单价 和 数量  true是填写  false存在未填写
   var flagStyle= true;//验证入库商品中必填项单价（非负浮点数） 和 数量（正整数）的格式  true是正确  false存在不正确
   var list = [];
   $('#table').find('tbody').each(function() {
       $(this).find('tr').each(function() {
           var paramDetail = {};
           $(this).find('td').each(function() {
               if ($(this).index() == 15) {
                   //商品主键
                   paramDetail.drugsId = $(this).text();
               } else if ($(this).index() == 1) {
	               	//编号
	               	var orderno = $(this).find("span").html();
                    paramDetail.orderno = orderno;
               } else if ($(this).index() == 2) {
	               	//国家编码
	               	var contryCode = $(this).find("span").html();
                   	paramDetail.contryCode = contryCode;
              } else if ($(this).index() == 3) {
	               	//化学名称
	               	var chemistryName = $(this).find("span").html();
                  	paramDetail.chemistryName = chemistryName;
              } else if ($(this).index() == 16) {
	               	//一级类别
	               	var drugsTypeone = $(this).text();
                 	paramDetail.drugsTypeone = drugsTypeone;
              } else if ($(this).index() == 17) {
               	//二级类别
               	var drugsTypetwo = $(this).text();
                paramDetail.drugsTypetwo = drugsTypetwo;
              } else if ($(this).index() == 7) {
                 	//单位
                  var company = $(this).find("span").html();
                  paramDetail.company = company;
              } else if ($(this).index() == 8) {
              	//商品单价
              	var nuitPrice = $(this).find("option:selected").text();
                paramDetail.nuitPrice = nuitPrice;   
              }else if ($(this).index() == 9) {
                	//库存数量
                	var kcQuantity = $(this).find("span").html();
                  	paramDetail.kcQuantity = kcQuantity;
                } else if ($(this).index() == 12) {
	               	//出库数量
	               	var ckQuantity = $(this).find("input").val();
	               	if(ckQuantity == ""){
	               		flag = false;
	               		return false;
	               	}else{
						if(judgeSign(ckQuantity)==false){
							flagStyle = false;
	               			return false;
	               		}
	               	} 
                   paramDetail.ckQuantity = ckQuantity;
               } else if ($(this).index() == 13) {
	               	//出库金额
            	 	var amount = $(this).find("input").val();
	               	if(amount == ""){
	               		flag = false;
	               		return false;
	               	}else{
	               		if(judgeSignFloatNum(amount)==false && judgeSignFloatNum(amount)==false){
	               			flagStyle = false;
	               			return false;
	               		}
	               	}
                  paramDetail.amount = amount;
              } else if ($(this).index() == 10) {
                  //批号
                  var batchnum = $(this).find("option:selected").text();
                  paramDetail.batchnum = batchnum;    
              } else if ($(this).index() == 14 ) {
                   //入库备注
               	paramDetail.remark = $(this).find("input").val();
              } else if ($(this).index() == 11) {
                   //批号对应数量
                   var batchToNumber = $(this).find("option:selected").text();
                   var batchToId = $(this).find("option:selected").val();
                   paramDetail.batchToNumber = batchToNumber;
                   paramDetail.batchToId = batchToId;
              }
           });
           list.push(paramDetail);
       });
   });
   if(!flag){
   	   layer.alert('出库商品中存在数量、单价未填写，请补充填写！', {
                
          });
          return false;
   }
   if(!flagStyle){
   	   layer.alert('填写格式有误，必须为正整数！', {
                
          });
          return false;
   }
   //出库明细相关参数
   var data = JSON.stringify(list);
   data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
   var param = {
   	   paramDetail:data,	
   	   cktime: $("#cktime").val(),
   	   outtype: $("#outtype").val(),
       supplier: $("#supplier").val(),
       outcode: $("#outcode").val(),
       outremark: $("#outremark").val(),
       zhaiyao: $("#zhaiyao").val(),
       outDept: $("#outDept").val(),
       status: 0
   };
   var url = apppath + '/HUDH_YkzzDrugsOutAct/insertDrugsOut.act';
   $.axseSubmit(url, param,
   function() {},
   function(r) {
       if (r.retState == "0") {
           layer.alert('保存成功', {
                 
               end: function() {
            	/* saveGoodsGjTx(); */
               	var conidnex =layer.confirm('是否打印出库单？', {
		 		    btn: ['打印','关闭'] //按钮
				}, function(){
				    layer.close(conidnex);
				    openDayin($("#outcode").val());	
				   	parent.layer.close(frameindex); //再执行关闭
				}, function(){
					parent.layer.close(frameindex); //再执行关闭
				});
               } 
           });
       } else if(r.retState == "100"){
    	     $("#outcode").val(gettimestr());
	    	 layer.alert('入库单号已存在，系统已自动重新获取，请重新保存。', {
	               
	         });
    	 
       } else {
    	   layer.alert('添加失败：' + r.retMsrg, {//后台抛出的异常信息在前台展示
	           	end: function() {
	               	parent.location.reload(); //刷新父页面
		            var frameindex = parent.layer.getFrameIndex(window.name);
		            parent.layer.close(frameindex); //再执行关闭
	            }
           });
       }
   },
   function() {
       layer.alert('保存失败', {
             
       });
   });
}
function openDayin(incode) {
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("药品出库单");
    var costurl = "";
    // 默认 a5
    costurl = apppath + '/KQDS_Print_SetAct/toInGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&outcode=' + incode;
    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: costurl
    });
}
//出库部门(调用的是商品出库部门)
function getDeptSelect(name) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_CkdeptAct/selectList.act";
    $.axse(url, null,
    function(data) {
        var list = data;
        if (list != null) {
            if (list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    if(list.length==1){
                        dict.append("<option value='" + optionStr.seqId + "' selected = 'selected'>" + optionStr.deptname + "</option>");
                    }else{
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptname + "</option>");
                    }
                }
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}
</script>
</html>
