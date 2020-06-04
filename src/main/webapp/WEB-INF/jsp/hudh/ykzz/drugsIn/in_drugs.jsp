<!--wl整理  -->
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

</style>
</head>
<body>
<div id="container" onunload="checkLeave()">
    <div class="infoBd">
   		 <form class="form-horizontal" id="form1" style="border-bottom:1px solid #ddd;">
	        <div class="formBox">
	        <table class="kqds_table">
			    		<tr>
			    			<td style="text-align:right;">入库时间：</td>
			    			<td style="text-align:left;"> 
		    					 <input type="text"  class="unitsDate2" name="rktime" id="rktime">
			                </td>
			    			<td style="text-align:right;">入库方式：</td>
			    			<td style="text-align:left;"> 
		    					 <select  name="intype" id="intype">
			                    	<option value="">请选择</option>
			                    	<option value="0">采购入库</option>
		                    		<option value="2">换货入库</option>
		                    		<option value="4">其他入库</option>
			                     </select>
			                </td>
			    			<td style="text-align:right;">供应商：</td>
			    			<td style="text-align:left;">
								<select  name="supplier" id="supplier"></select>
							</td>
			    			
			    			<td style="text-align:right;">进货时间：</td>
			    			<td style="text-align:left;">
		    					<input type="text"  class="unitsDate2" name="stocktime" id="stocktime" autocomplete="off">
			    			</td>
			    		</tr>
			    		<tr>
			    			<td style="text-align:right;">单据编号：</td>
			    			<td style="text-align:left;"> 
		    					 <input type="text" name="incode" id="incode" readonly="readonly">
			                </td>
			    			<td style="text-align:right;">附加说明：</td>
			    			<td style="text-align:left;">
								<input type="text" name="inremark" id="inremark" >
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
			            	 <table class="table-striped table-condensed table-bordered" style=" ">
			            	    <thead style="height:30px; background: #00A6C0;color:white;">
									<tr>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>药品编号</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>国家编码</span></th>
										<th style="text-align: center; vertical-align: middle; width:150px"><span>化学名称</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:50px"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>单价</span></th>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>金额</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>入库备注</span></th>
										<th style="text-align: center; vertical-align: middle; width:110px"><span>生产日期</span></th>
										<th style="text-align: center; vertical-align: middle; width:110px"><span>有效期</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>注册证号</span></th>
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
										<th style="text-align: center; vertical-align: middle; width:50px"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>单价</span></th>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>金额</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>入库备注</span></th>
										<th style="text-align: center; vertical-align: middle; width:110px"><span>生产日期</span></th>
										<th style="text-align: center; vertical-align: middle; width:110px"><span>有效期</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>注册证号</span></th>
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
<script type="text/javascript">
var tdindex = 0;
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var nowday;
var apppath = apppath();



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
	initSelectsupplier($("#supplier"));
    $("#incode").val(gettimestr());
    $(".unitsDate2").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
    $("#rktime").val(nowday);
    
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
//从货物列表页面（goosd_house.jsp），调用改方法
var a=0;
var rows=0;
function addDoodsDetail(detail){
	 tdindex++;
	 rows+=detail.irows;
	 //显示条数
	 var total1  = $("#total1").html();
     $("#total1").html(Number(total1)+1);
	 var tablehtml = "";
	 tablehtml += "<tr style='' rowIndex="+tdindex+">";
	 //操作0
     tablehtml += '<td style="width:60px;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
     //类别2
     //药品编号1
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span title='+detail.order_no +'>'+detail.order_no+'</span></td>';
   	 //国家编码2
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span title='+detail.contry_code +'>'+detail.contry_code + '</span></td>';
     //化学名称3
     tablehtml += '<td style="width:150px;" style="text-align:center;"><span title='+detail.chemistry_name +'>' + detail.chemistry_name + '</span></td>';
     //一级类别4
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span>' + detail.basetype+ '</span></td>';
     //二级类别5
     tablehtml += '<td style="width:120px;" style="text-align:center;"><span>'+detail.nexttype+'</span></td>';
     //单位6
     tablehtml += '<td style="width:50px;" style="text-align:center;"><span>'+detail.company+'</span></td>';
     //单价7
     tablehtml += '<td style="width:100px;"><input type="tel" style="width:100%;text-align:center;" onchange="editPrice(\'inprice\','+tdindex+');" onkeyup="num(this)" onfocus="this.select()" name="inprice'+tdindex+'" id="inprice'+tdindex+'"></td>';
     //tablehtml += '<td style="width:100px;" style="text-align:center;"><span id="inprice'+tdindex+'"></span></td>';
     //数量8
     tablehtml += '<td style="width:60px;"><input type="tel" style="width:100%; text-align:center;" oninput="if(value>100000)value=100000;if(value.length>6)value=value.slice(0,6);if(value<0)value=0" onchange="editPrice(\'innum\','+tdindex+');" onfocus="this.select()" name="innum'+tdindex+'" id="innum'+tdindex+'"></td>';
     //金额9
     tablehtml += '<td style="width:100px;"><span name="rkmoney'+tdindex+'" id="rkmoney'+tdindex+'"></span></td>';
     //tablehtml += '<td style="width:100px;"><input type="text" style="width:100%;text-align:center;" onchange="editPrice(\'rkmoney\','+tdindex+');" onfocus="this.select()" name="rkmoney'+tdindex+'" id="rkmoney'+tdindex+'"></td>';
     //入库备注10
     tablehtml += '<td style="width:120px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="inremark" id="inremark"></td>';
     //生产日期11
     tablehtml += '<td style="width:100px;"><input type="text" style="width:100%; text-align:center;" class="unitsDate" name="createdate" id="createdate"></td>';
     //有效期12
     tablehtml += '<td style="width:100px;"><input type="text" style="width:100%; text-align:center;" class="unitsDate" name="yxdate" id="yxdate"></td>';
     //批号13
     tablehtml += '<td style="width:100px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="ph" id="ph"></td>';
     //注册证号14
     tablehtml += '<td style="width:100px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="zczh" id="zczh"></td>';
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
function num(obj){
	obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}
function getDrugdBatchnum(param) {
	console.log("此方法避免药品入库报错！");
}
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
   // var i = obj.parentNode.parentNode.rowIndex;
    var i = obj.parentNode.parentNode.getAttribute("rowIndex");
    var rkmoneysub =Number($("#rkmoneyall").text())-Number($("#rkmoney"+i).html());
    var innumsub =Number($("#innumall").text())-Number($("#innum"+i).val());
    var totalsub =Number($("#total1").text())-1;
    //console.log("数量"+innumsub);
    //console.log("条数"+totalsub);
    //console.log("总价"+rkmoneysub);
    $("#total1").html(Number(totalsub));
    $("#innumall").html(Number(innumsub));
	$("#rkmoneyall").html(rkmoneysub.toFixed(3));
    
    //document.getElementById('table').deleteRow(i);
    $(obj).parents("tr").remove();
    //updatexj();
}
/* function editPrice(id,index){
	 var innum = $("#innum"+index).val();
	 var rkmoney = $("#rkmoney"+index).val();
	 if(id=="innum"){
		 if(judgeSignNum(innum)==false){
			 layer.alert('数量格式不正确！' );
			return false;
		 }
	 }
	 if(id=="rkmoney"){
		 if(judgeSignFloatNum(rkmoney)==false){
				layer.alert('金额格式不正确！', {
		              
		        });
		        return false;
		 }
	 }
	 if(innum!="" && rkmoney!=""){
		 var inprice = (rkmoney/innum).toFixed(2);
		 $("#inprice"+index).html(inprice);
	 }
	 updatexj();
} */
function editPrice(id,index){
	 var innum = $("#innum"+index).val();
	 var inprice = $("#inprice"+index).val();
	 if(id=="innum"){
		 if(innum==0){
			 layer.alert('数量格式不正确！' );
			 $("#innum"+index).val("");
			return false;
		 }
		 if(judgeSignNum(innum)==false){
			 layer.alert('数量格式不正确！' );
			 $("#innum"+index).val("");
			return false;
		 }
	 }
	 if(id=="inprice"){
		 if(inprice==0){
			layer.alert('单价格式不正确！');
			$("#inprice"+index).val("");
		    return false;
		 }
		 if(judgeSignFloatNum(inprice)==false){
				layer.alert('单价格式不正确！');
				$("#inprice"+index).val("");
		        return false;
		 }
	 }
	 if(innum!="" && inprice!="" && typeof(innum) != "undefined" && innum != null && typeof(inprice) != "undefined" && inprice != null){
		 //console.log(innum+"------------------"+inprice);
		 var rkmoney = (inprice*innum).toFixed(3);
		 /* if(rkmoney<0){
			 rkmoney=0-rkmoney;
		 } */
		 $("#rkmoney"+index).html(rkmoney);
		 if(a==0){
			//rows = $("#table tbody tr").length;
			// console.log("a=0"+rows);
			 var innumall=0,rkmoneyall=0;
			 for(var i=1;i<=rows;i++){
				//console.log($("#rkmoney"+i).text()+"-----------hhahah");
				 //if(rkmoney != null && rkmoney != ""){
					 rkmoneyall += Number($("#rkmoney"+i).text());
				 //} 
					 if(innum != null && innum != ""){
						 innumall += Number($("#innum"+i).val());
					 }
			 }
				//console.log(Number(innumall)+"------------总数量");
			 $("#innumall").html(Number(innumall));
			 $("#rkmoneyall").html(Number(rkmoneyall).toFixed(3));
			 a+=1;
		 }else if(a>0){
			 //console.log("a>0"+rows);
		 var innumall=0,rkmoneyall=0;
		 for(var i=1;i<=rows;i++){
			//console.log($("#rkmoney"+i).text()+"-----------hhahah");
			 //if(rkmoney != null && rkmoney != ""){
				 rkmoneyall += Number($("#rkmoney"+i).text());
			 //} 
				 if(innum != null && innum != ""&&!isNaN(Number($("#innum"+i).val()))){
					// console.log("!-----------------------------NaN");
					 innumall += Number($("#innum"+i).val());
				 }
		 }
			//console.log(Number(innumall)+"------------总数量");
		 $("#innumall").html(Number(innumall));
		 $("#rkmoneyall").html(Number(rkmoneyall).toFixed(3));
		 }
	 }
	 /* if(innum!="" && inprice!=""){
		 var rkmoney = (inprice*innum).toFixed(3);
		 if(rkmoney<0){
			 rkmoney=0-rkmoney;
		 }
		 $("#rkmoney"+index).html(rkmoney);
	 }
	 updatexj(); */
}
function updatexj(){
	 //改变出库单价（领用出库、其他）
	 var rows = $("#table tbody tr").length;
	 if(rows > 0) {
		 rows = findMaxIndex(); //获取当前列表中最大的下标进行循环，防止删除后导致下标未更新金额计算错误
	 }
	 var innumall=0,rkmoneyall=0;
	 for(var i=1;i<=rows;i++){
		 /* var innum = $("#innum"+i).val();
		 var rkmoney = $("#rkmoney"+i).val();
		 if(innum != null && innum != ""){
			 innumall += Number(innum);
		 }
		 if(rkmoney != null && rkmoney != ""){
			 rkmoneyall += Number(rkmoney);
		 } */
		 var innum = $("#innum"+i).val();
		 var inprice = $("#inprice"+i).val();
		 var rkmoney = (inprice*innum).toFixed(3);
		 if(rkmoney<0){
			 rkmoney=0-rkmoney;
		 }
		 if(innum != null && innum != ""){
			 innumall += Number(innum);
		 }
		 if(rkmoney != null && rkmoney != ""){
			 rkmoneyall += Number(rkmoney);
		 }
	 }
	 $("#innumall").html(Number(innumall));
	 $("#rkmoneyall").html(Number(rkmoneyall).toFixed(3));
}
/* function findMaxIndex(){
	var maxTd = $("#table tbody tr:last").find("input[name^='rkmoney']");
	return maxTd.attr("name").replace("rkmoney","");
} */
function findMaxIndex(){
	var maxTd = $("#table tbody tr:last").find("span[name^='rkmoney']");
	return maxTd.attr("name").replace("rkmoney","");
}
function save() {
    //验证
    var supplier = $("#supplier").val();
    var intype = $("#intype").val();
    var rktime = $("#rktime").val();
    var stocktime = $("#stocktime").val();
    if ($("#table tr").length < 2) {
        layer.alert('请选择入库药品' );
        return false;
    }
    if (intype == "" || intype == null) {
        layer.alert('请选择入库方式' );
        return false;
    } 
    if (rktime == "" || rktime == null) {
        layer.alert('请选择入库时间' );
        return false;
    }
    if (supplier == "" || supplier == null) {
        layer.alert('请选择供应商'  );
        return false;
    }
    
   //循环获取表格中项目
   var flag= true;//验证入库商品中必填项单价 和 数量  true是填写  false存在未填写
   var flagStyle= true;//验证入库商品中必填项单价（非负浮点数） 和 数量（正整数）的格式  true是正确  false存在不正确
   var flagBatch=true;
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
              } else if ($(this).index() == 6) {
                 	//单位
                 	var company = $(this).find("span").html();
                  paramDetail.company = company;
                } else if ($(this).index() == 7) {
              		//商品单价
           	 		var nuitPrice = $(this).find("input").val();
	               	if(nuitPrice == ""){
	               		flag = false;
	               		return false;
	               	}else{
	               		if(judgeSignFloatNum(nuitPrice)==false && judgeSignFloatNum(nuitPrice)==false){
	               			flagStyle = false;
	               			return false;
	               		}
	               	}
              	//var nuitPrice = $(this).find("span").html();
                paramDetail.nuitPrice = nuitPrice;
              } else if ($(this).index() == 8) {
	               	//数量
	               	var quantity = $(this).find("input").val();
	               	if(quantity == ""){
	               		flag = false;
	               		return false;
	               	}else{
						if(judgeSignNum(quantity)==false){
							flagStyle = false;
	               			return false;
	               		}
	               	} 
                   paramDetail.quantity = quantity;
               } else if ($(this).index() == 9) {
	               	//金额
            	 	/* var amount = $(this).find("input").val();
	               	if(amount == ""){
	               		flag = false;
	               		return false;
	               	}else{
	               		if(judgeSignFloatNum(amount)==false && judgeSignFloatNum(amount)==false){
	               			flagStyle = false;
	               			return false;
	               		}
	               	} */
	               	var amount = $(this).find("span").html();
                  	paramDetail.amount = amount;
              } else if ($(this).index() == 11) {
                  //有效期
                 	paramDetail.createdate = $(this).find("input").val();
              } else if ($(this).index() == 12) {
                   //有效期
               	paramDetail.valid = $(this).find("input").val();
              } else if ($(this).index() == 13) {
                   //批号
                paramDetail.batchnum = $(this).find("input").val();
              } else if ($(this).index() == 14) {
               	   //注册证号
              	paramDetail.regisnum = $(this).find("input").val();
              } else if ($(this).index() == 10) {
                   //入库备注
               	paramDetail.remark = $(this).find("input").val();
               } 
           });
           list.push(paramDetail);
       });
   });
   var orderno1;
   var batchnum1;
   var a;
   for(var i in list) {
	   orderno1=list[i].orderno;
	   batchnum1=list[i].batchnum;
	   a=i;
	   //console.log('药品编号i：'+orderno1+ '药品批号i：'+batchnum1+'循环次数：'+a+'数量i：'+list[i].quantity);
	   for(var i in list){
		   if(i!=a){
			   if(orderno1==list[i].orderno){
				   if(batchnum1==list[i].batchnum){	   
			   		//console.log('药品编号i1：'+list[i].orderno+ '药品批号i1：'+list[i].batchnum+'数量i1:'+list[i].quantity);
			   		flagBatch = false;
			   		break;
				   }
			   }
		   }
	   }
	}
   if(!flagBatch){
	   layer.alert('入库商品中相同商品存在相同批号，请修改填写！', {
           
       });
       return false;
   }
   if(!flag){
   	   layer.alert('入库商品中存在数量、金额未填写，请补充填写！', {
                
          });
          return false;
   }
   if(!flagStyle){
   	   layer.alert('入库商品中存在数量、单价格式不正确！', {
                
          });
          return false;
   }
   //入库明细相关参数
   var data = JSON.stringify(list);
   data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
   var param = {
   	   paramDetail:data,	
   	   rktime: $("#rktime").val(),
       intype: $("#intype").val(),
       supplier: $("#supplier").val(),
       stocktime: $("#stocktime").val(),
       incode: $("#incode").val(),
       inremark: $("#inremark").val(),
       zhaiyao: $("#zhaiyao").val(),
       status: 0
   };
   var url = apppath + '/HUDH_YkzzDrugsInAct/insertDrugsIn.act';
   $.axseSubmit(url, param,
   function() {},
   function(r) {
       if (r.retState == "0") {
           layer.alert('保存成功', {
                 
               end: function() {
            	/* saveGoodsGjTx(); */
               	var conidnex =layer.confirm('是否打印入库单？', {
		 		    btn: ['打印','关闭'] //按钮
				}, function(){
				    layer.close(conidnex);
				    openDayin($("#incode").val());	
				   	parent.layer.close(frameindex); //再执行关闭
				}, function(){
					parent.layer.close(frameindex); //再执行关闭
				});
               } 
           });
       } else if(r.retState == "100"){
    	     $("#incode").val(gettimestr());
	    	 layer.alert('入库单号已存在，系统已自动重新获取，请重新保存。', {
	               
	         });
    	 
       } else {
           layer.alert('保存失败', {
                 
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
    var printSet = getPrintType("药品入库单");
    var costurl = "";
    // 默认 a5
    costurl = apppath + '/KQDS_Print_SetAct/toInGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&incode=' + incode;
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
</script>
</html>
