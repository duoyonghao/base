<!--wl整理  -->
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
<title>入库申请单</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
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
	width:100%;
	overflow:auto;
} 
.kqds_table {
    width: auto;
}
.searchWrap {
    padding: 10px 10px 8px 10px;
}
.searchWrap .kv select {
	height: 28px;
    width: 130px;
    border: solid 1px #e5e5e5;
    border-radius: 3px;
}
.kv > label {
	width: 75px;
}
</style>
</head>
<body>
<div id="container" onunload="checkLeave()">
    <div class="infoBd">
   		
        <div class="databaseWrap">
	        <div class="biliHistory">
	            <div class="tableHd">商品明细</div>
	            <!--fixedFatherDiv 首行固定表格的父元素样式  -->
	            	<div class="fixedFatherDiv">
	            	<!-- fixedHeaderDiv 固定的表头 -->
			            <div class="fixedHeaderDiv">
			            	 <table class="table-striped table-condensed table-bordered">
			            	    <thead style="height:30px; background: #00A6C0;color:white;">
									<tr>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle; width:90px"><span>仓库</span></th>
										<th style="text-align: center; vertical-align: middle; width:90px"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:90px"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:150px"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle; width:90px"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle; width:50px"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>最近采购价</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>单价</span></th>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>金额</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>入库备注</span></th>
										<th style="text-align: center; vertical-align: middle; width:110px"><span>有效期</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>注册证号</span></th>
										<th style="padding-right:24px; text-align: center; vertical-align: middle; width:135px"><span>产地</span></th>
										<th style="text-align: center; vertical-align: middle; width:40px"><span>新增</span></th>
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
										<th style="text-align: center; vertical-align: middle; width:90px"><span>仓库</span></th>
										<th style="text-align: center; vertical-align: middle; width:90px"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:90px"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle; width:150px"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle; width:90px"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle; width:50px"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>最近采购价</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>单价</span></th>
										<th style="text-align: center; vertical-align: middle; width:60px"><span>数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>金额</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>入库备注</span></th>
										<th style="text-align: center; vertical-align: middle; width:110px"><span>有效期</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle; width:100px"><span>注册证号</span></th>
										<th style="text-align: center; vertical-align: middle; width:120px"><span>产地</span></th>
										<th style="text-align: center; vertical-align: middle; width:40px"><span>新增</span></th>
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
	    <div class="searchWrap"> 
	        <div class="formBox">
 				<div class="">
               		<div class="kv">
						<label>入库时间：</label>
						<div class="kv-v">
							 <input type="text"  class="unitsDate2" name="rktime" id="rktime">
						</div>
					</div>
					
               		<div class="kv">
						<label>入库方式：</label>
						<div class="kv-v">
							 <select  name="intype" id="intype">
			                    	<option value="">请选择</option>
			                    	<option value="2">换货入库</option>
			                    	<option value="4">其他入库</option>
			                    	<option value="9">二次入库</option>
			                 </select>
						</div>
					</div>
		            <div class="kv">
		                <label>单据编号：</label>
		                <div class="kv-v">
		                    <input type="text" name="incode" id="incode" readonly="readonly">
		                </div>
		            </div>
					<div class="kv" >
						<label>附加说明：</label>
						<div class="kv-v">
							<input type="text" name="inremark" id="inremark" >
						</div>
					</div>
					<div class="kv" >
						<a href="javascript:void(0);" onclick="xzhw()" class="kqdsCommonBtn">选择商品</a>
					</div>	
					<div class="kv" >
						<a href="javascript:void(0);" onclick="save()" class="kqdsSearchBtn">保&nbsp;&nbsp;存</a>
					</div>					
                </div>
	        </div>
	    </div>    
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/keyin.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var tdindex = 0;
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var nowday;
var menuid = "<%=menuid%>";
$(function() {
    getSupplierSelect2("supplier");
    getHouseSelect("inhouse");
    $("#incode").val(gettimestr());
    $(".unitsDate2").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
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
    });
});
/* 计算 设置table数据表的高度 */
function resizeTableHeight(){
	var tableHeight=0;
    tableHeight=$(window).outerHeight()-$(".searchWrap").outerHeight()-62;
    $(".fixedBodyDiv").outerHeight(tableHeight);
    /* 出现自动出现滚动条时 会导致表头内容对不齐  现改为 开始就出现滚动条  */
    $(".fixedHeaderDiv table").outerWidth($(".fixedHeaderDiv").outerWidth()-15);/* 表头留出右边15px的滚动条出现距离 */
}
//从货物列表页面（goosd_house.jsp），调用改方法
var a=0;
var rows=0;
function addDoodsDetail(detail){
// 	console.log(JSON.stringify(detail)+"--------------");
	 //默认注册证号
	 var inpArr=[];
	 var zczhArr=[];
	 for(var i=0;i<detail.length;i++){
		 var jobj_detail = getZczh(detail[i].goodsid);
		 inpArr.push(jobj_detail.inprice);	
		 zczhArr.push(jobj_detail.zczh);
	 }
	 var zczh = [];
	 var cd = "";
	 var beforeinprice = [];
	 rows+=detail.irows;

	 if(inpArr){
		 for(var i=0;i<inpArr.length;i++){
			 var inpi=Number(inpArr[i]);
			 beforeinpri =inpi.toFixed(3);
			 if(beforeinpri=="NaN"){
				 beforeinpri="/"
			 }			 
			 beforeinprice.push(beforeinpri);
		 }		 
	 }
	 if(zczhArr){
		 for(var i=0;i<zczhArr.length;i++){
			 var zczh_detail=zczhArr[i];
			 if(zczh_detail == undefined){
				 zczh_detail="/";
			 }			 
			 zczh.push(zczh_detail);
		 }		 
	 }

	 //显示条数
	 var total1  = $("#total1").html();
     $("#total1").html(Number(total1)+(detail.length));
     
	 var tablehtml = "";
	 
	 for(var i=0;i<detail.length;i++){
		 
	 tdindex=detail[i].irows;
		
	 tablehtml += "<tr style='' rowIndex="+tdindex+">";
	 //操作0
     tablehtml += '<td style="width:60px;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
     //新增仓库1
     tablehtml += '<td style="width:90px;"><span id="sshousename'+tdindex+'">'+detail[i].sshousename+'</span></td>';
     //一级类别1+1
     tablehtml += '<td style="width:90px;"><span id="firsttype'+tdindex+'">'+detail[i].firsttype+'</span></td>';
   	 //二级类别2+1
     tablehtml += '<td style="width:90px;"><span id="goodstypename'+tdindex+'">'+detail[i].goodstypename + '</span></td>';
     //商品名称3+1
     tablehtml += '<td style="width:150px;"><span style="text-align:left;" id="goodsname'+tdindex+'">' + detail[i].goodsname + '</span></td>';
     //商品编号4+1
     tablehtml += '<td style="width:90px;"><span style="text-align:left;" id="goodscode'+tdindex+'">' + detail[i].goodscode + '</span></td>';
     //规格5+1
     tablehtml += '<td style="width:120px;"><span style="text-align:left;" id="goodsnorms'+tdindex+'">'+detail[i].goodsnorms+'</span></td>';
     //单位6+1
     tablehtml += '<td style="width:50px;"><span id="goodsunit'+tdindex+'">'+detail[i].goodsunit+'</span></td>';
     //最近采购价7+1
     tablehtml += '<td style="width:50px;"><span id="beforeinprice'+tdindex+'">'+beforeinprice[i]+'</span></td>';
     //单价8+1
     tablehtml += '<td style="width:100px;"><input type="tel" style="width:100%;text-align:center;" onchange="editPrice(\'inprice\',this,'+tdindex+');" onfocus="this.select()" onkeyup="num(this)" name="inprice'+tdindex+'" id="inprice'+tdindex+'"></td>';
     //数量9+1
     tablehtml += '<td style="width:60px;"><input type="tel" style="width:100%; text-align:center;" onchange="editPrice(\'innum\',this,'+tdindex+');"  oninput="if(value>100000)value=100000;if(value.length>6)value=value.slice(0,6);if(value<0)value=0" onfocus="this.select()" name="innum'+tdindex+'" id="innum'+tdindex+'"></td>';
     //金额10+1
     tablehtml += '<td style="width:100px;"><span name="rkmoney'+tdindex+'"  id="rkmoney'+tdindex+'"></span></td>';
     //入库备注11+1
     tablehtml += '<td style="width:120px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="inremark" id="inremark"></td>';
     //有效期12+1
     tablehtml += '<td style="width:100px;"><input type="text" style="width:100%; text-align:center;" class="unitsDate" name="yxdate" id="yxdate"></td>';
     //批号13+1
     tablehtml += '<td style="width:100px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="editPh(this,'+tdindex+');" class="ph" name="ph" id="ph'+tdindex+'"></td>';
     //注册证号14+1
     tablehtml += '<td style="width:100px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="zczh" value="'+zczh[i]+'" id="zczh'+tdindex+'"></td>';
     //产地15+1
     tablehtml += '<td style="width:120px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="cd"  value="'+cd+'" id="cd'+tdindex+'"></td>';
     //商品主键16+1
 	 tablehtml += '<td style="display:none;"><span id="goodsid'+tdindex+'">' + detail[i].goodsid + '</span></td>';     
 	 //新增明细17+1
 	 tablehtml += '<td style="width:60px;"><button type="button" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
 	 
     tablehtml += "</tr>";
	 }
	 
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
function getZczh(goodsid){
	var jobj = '';
	var url = contextPath+'/KQDS_Ck_Goods_InAct/selectZczh.act?goodsid=' + goodsid;
    $.axse(url, null,
    function(data) {
    	jobj = data;
    },
    function() {
    });
    return jobj;
}
//删除行
function deltr(obj) {
    var i = obj.parentNode.parentNode.getAttribute("rowIndex");
    var rkmoneysub =Number($("#rkmoneyall").text())-Number($("#rkmoney"+i).text());
    var innumsub =Number($("#innumall").text())-Number($("#innum"+i).val());
    var totalsub =Number($("#total1").text())-1;
    $("#total1").html(Number(totalsub));
    $("#innumall").html(Number(innumsub));
	$("#rkmoneyall").html(rkmoneysub.toFixed(3));
	$(obj).parents("tr").remove();
}
//选择商品
function xzhw() {
	var inhouse = $("#inhouse").val();
    layer.open({
        type: 2,
        title: '货物列表',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['90%', '450px'],
        content: contextPath + '/KQDS_Ck_HouseAct/toGoodsHouse.act?type=0',
    });
}

function editPrice(id,obj,index){
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
		 var k = obj.parentNode.parentNode.rowIndex;
		 var ph=$("#ph"+index).val();
		 var goodsid=$("#goodsid"+index).html();
		 $("#table").find("tbody").find("tr").each(function(i,obj){
			 if(i!=k-1){
				 var goodsidAdd=$(this).find("td").eq(17).text();
				 if(goodsidAdd==goodsid){
					 var price=$(this).find("td").eq(9).find("input").val();
					 if(Number(inprice)==Number(price)){
						 var phAdd=$(this).find("td").eq(14).find("input").val();
						 if(ph==phAdd){
							 layer.alert('相同批号单价重复，请查询后填写！', {
					              
						     });
							 $("#inprice"+index).val("");
							 return false;
						 }
					 }
					 
				 }
			 }
		 });
		 if(judgeSignFloatNum(inprice)==false){
				layer.alert('单价格式不正确！');
				$("#inprice"+index).val("");
		        return false;
		 }
	 }
	 if(innum!="" && inprice!="" && typeof(innum) != "undefined" && innum != null && typeof(inprice) != "undefined" && inprice != null){
		 var rkmoney = (inprice*innum).toFixed(3);
		 $("#rkmoney"+index).html(rkmoney);
		 if(a==0){
			rows = $("#table tbody tr").length;
			 var innumall=0,rkmoneyall=0;
			 for(var i=1;i<=rows;i++){
				rkmoneyall += Number($("#rkmoney"+i).text());
				if(innum != null && innum != ""){
					innumall += Number($("#innum"+i).val());
				 }
			 }
			 $("#innumall").html(Number(innumall));
			 $("#rkmoneyall").html(Number(rkmoneyall).toFixed(3));
			 a+=1;
		 }else if(a>0){
			 rows = $("#table tbody tr").length;
		 var innumall=0,rkmoneyall=0;
		 	for(var i=1;i<=rows;i++){
				 rkmoneyall += Number($("#rkmoney"+i).text());
				 if(innum != null && innum != ""&&!isNaN(Number($("#innum"+i).val()))){
					 innumall += Number($("#innum"+i).val());
				 }
		 	}
		 $("#innumall").html(Number(innumall));
		 $("#rkmoneyall").html(Number(rkmoneyall).toFixed(3));
		 }
	 }
}
function editPh(obj,index){
	var k = obj.parentNode.parentNode.rowIndex;
	var ph=$("#ph"+index).val();
	var goodsid=$("#goodsid"+index).html();
	var inprice=$("#inprice"+index).val();
	$("#table").find("tbody").find("tr").each(function(i,obj){
		if(i!=k-1){
			var goodsidAdd=$(this).find("td").eq(17).text();
			if(goodsidAdd==goodsid){
				var phAdd=$(this).find("td").eq(14).find("input").val();
				if(ph==phAdd){
					var price=$(this).find("td").eq(9).find("input").val();
					if(Number(inprice)==Number(price)){
						layer.alert('批号重复，请查询后填写！', {
				              
					     });
						 $("#ph"+index).val("");
						 return false;
					}
				}
			}
		}
	});
}
function updatexj(){
	 //改变出库单价（领用出库、其他）
	 var rows = $("#table tbody tr").length;
	 if(rows > 0) {
		 rows = findMaxIndex(); //获取当前列表中最大的下标进行循环，防止删除后导致下标未更新金额计算错误
	 }
	 var innumall=0,rkmoneyall=0;
	 for(var i=1;i<=rows;i++){
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
function findMaxIndex(){
	var maxTd = $("#table tbody tr:last").find("span[name^='rkmoney']");
	return maxTd.attr("name").replace("rkmoney","");
}
function save() {
    //验证
    rows=0;
    var supplier = $("#supplier").val();
    var zhaiyao = $("#zhaiyao").val();
    var intype = $("#intype").val();
    var rktime = $("#rktime").val();
    if ($("#table tr").length < 2) {
        layer.alert('请选择入库商品' );
        return false;
    }
    if (intype == "" || intype == null) {
        layer.alert('请选择入库方式' );
        return false;
    } 
   //循环获取表格中项目
   var flag= true;//验证入库商品中必填项单价 和 数量  true是填写  false存在未填写
   var flagDate= true;//验证入库商品中必填项有效期 和 批号  true是填写  false存在未填写
   var flagStyle= true;//验证入库商品中必填项单价（非负浮点数） 和 数量（正整数）的格式  true是正确  false存在不正确
   var list = [];
   $('#table').find('tbody').each(function() {
       $(this).find('tr').each(function() {
           var paramDetail = {};
           $(this).find('td').each(function() {
        	   $(this).eq(17).css("border","1px solid red");
               if ($(this).index() == 17) {
                   //商品主键
                   paramDetail.goodsuuid = $(this).text();
               } else if ($(this).index() == 9) {
	               	//商品单价
           	 		var inprice = $(this).find("input").val();
                    paramDetail.inprice = inprice;
               } else if ($(this).index() == 10) {
	               	//数量
	               	var innum = $(this).find("input").val();
	               	if(innum == ""){
	               		flag = false;
	               		return false;
	               	}else{
						if(judgeSignNum(innum)==false){
							flagStyle = false;
	               			return false;
	               		}
	               	} 
                   paramDetail.innum = innum;
               } else if ($(this).index() == 11) {
	               	var rkmoney = $(this).find("span").html();
                  	paramDetail.rkmoney = rkmoney;
              } else if ($(this).index() == 13) {
                   //有效期
               	paramDetail.yxdate = $(this).find("input").val();
               	var yxdate = $(this).find("input").val();
              } else if ($(this).index() == 14) {
                   //批号
                paramDetail.ph = $(this).find("input").val();
                var ph = $(this).find("input").val();
                if(ph == ""){
                	flagDate = false;
//                 	layer.alert('入库商品中存在批号未填写，请补充填写！', {
                        
//                     });
               		return false;
               	}
              } else if ($(this).index() == 15) {
               	   //注册证号
              	paramDetail.zczh = $(this).find("input").val();
              } else if ($(this).index() == 16) {
             	   //产地
               	paramDetail.cd = $(this).find("input").val();
               } else if ($(this).index() == 12) {
                   //入库备注
               	paramDetail.sqremark = $(this).find("input").val();
               } 
               paramDetail.type = '2';
           });
           console.log(JSON.stringify(paramDetail));
           list.push(paramDetail);
       });
   });
   if(!flag){
   	   layer.alert('入库商品中存在数量、单价未填写，请补充填写！', {
                
          });
          return false;
   }
   if(!flagDate){
   	   layer.alert('入库商品中存在有效期、批号未填写，请补充填写！', {
                
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
       seqId: $("#seqId").val(),
       intype: $("#intype").val(),
       supplier: supplier,
       incode: $("#incode").val(),
       inremark: $("#inremark").val(),
       rktime : rktime,
       zhaiyao: zhaiyao,
       type:"2"
   };
   var url = contextPath + '/KQDS_Ck_Goods_InAct/insertOrUpdate.act';
   $.axseSubmit(url, param,
   function() {},
   function(r) {
       if (r.retState == "0") {
           layer.alert('保存成功', {
                 
               end: function() {
            	saveGoodsGjTx();
               	var conidnex =layer.confirm('是否打印入库单？', {
		 		    btn: ['打印','关闭'] //按钮
				}, function(){
				    layer.close(conidnex);
				    openDayin($("#incode").val());	
				    $('#table').find('tbody').html("");
			     	$("#total1").html(0);
				}, function(){
				    $('#table').find('tbody').html("");
			     	$("#total1").html(0);
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
    var printSet = getPrintType("商品入库单");
    var costurl = "";
    // 默认 a5
    costurl = contextPath + '/KQDS_Print_SetAct/toInGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&incode=' + incode;
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

function addDeatil(index){
	tdindex = $("#table").find("tbody").find("tr").length;
	tdindex++;
	 //显示条数
    $("#total1").html(tdindex); //共有记录
	 var tb = document.getElementById("table");
     var newTr;//添加新行，trIndex就是要添加的位置
	$("#table").find("tbody").find("tr").each(function(i,obj){
		var addid=$(this).find("td").eq(18).find("button").attr("id");
		if(addid==$("#add"+index).attr("id")){
			newTr = tb.insertRow(i+2);
		}
	});
	//操作0
    newTr.insertCell().innerHTML = '<td ><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
    //仓库1
    newTr.insertCell().innerHTML = '<td ><span id="sshousename'+tdindex+'">'+$("#sshousename"+index).html()+'</span></td>';
    //一级类别1+1
    newTr.insertCell().innerHTML = '<td ><span id="firsttype'+tdindex+'">'+$("#firsttype"+index).html()+'</span></td>';
  	 //二级类别2+1
    newTr.insertCell().innerHTML = '<td ><span id="goodstypename'+tdindex+'">'+$("#goodstypename"+index).html()+ '</span></td>';
    //商品名称3+1
    newTr.insertCell().innerHTML = '<td ><span style="text-align:left;" id="goodsname'+tdindex+'">' + $("#goodsname"+index).html() + '</span></td>';
    //商品编号4+1
    newTr.insertCell().innerHTML = '<td ><span style="text-align:left;" id="goodscode'+tdindex+'">' +$("#goodscode"+index).html()+ '</span></td>';
    //规格5+1
    newTr.insertCell().innerHTML = '<td ><span style="text-align:left;" id="goodsnorms'+tdindex+'">'+$("#goodsnorms"+index).html()+'</span></td>';
    //单位6+1
    newTr.insertCell().innerHTML = '<td ><span id="goodsunit'+tdindex+'">'+$("#goodsunit"+index).html()+'</span></td>';
    //最近采购价7+1
    newTr.insertCell().innerHTML = '<td ><span id="beforeinprice'+tdindex+'">'+$("#beforeinprice"+index).html()+'</span></td>';
    //单价8+1
    newTr.insertCell().innerHTML = '<td ><input type="tel" style="width:100%;text-align:center;" onchange="editPrice(\'inprice\',this,'+tdindex+');" onfocus="this.select()" onkeyup="num(this)" name="inprice'+tdindex+'" id="inprice'+tdindex+'"></td>';
    //数量9+1
    newTr.insertCell().innerHTML = '<td ><input type="tel" style="width:100%; text-align:center;" onchange="editPrice(\'innum\',this,'+tdindex+');"  oninput="if(value>100000)value=100000;if(value.length>6)value=value.slice(0,6);if(value<0)value=0" onfocus="this.select()" name="innum'+tdindex+'" id="innum'+tdindex+'"></td>';
    //金额10+1
    newTr.insertCell().innerHTML = '<td ><span name="rkmoney'+tdindex+'"  id="rkmoney'+tdindex+'"></span></td>';
    //入库备注11+1
    newTr.insertCell().innerHTML = '<td ><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="inremark" id="inremark"></td>';
    //有效期12+1
    newTr.insertCell().innerHTML = '<td ><input type="text" style="width:100%; text-align:center;" class="unitsDate" name="yxdate" id="yxdate"></td>';
    //批号13+1
    newTr.insertCell().innerHTML = '<td ><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="editPh(this,'+tdindex+');" class="ph" name="ph" id="ph'+tdindex+'"></td>';
    //注册证号14+1
    newTr.insertCell().innerHTML = '<td ><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="zczh" value="'+$("#zczh"+index).val()+'" id="zczh'+tdindex+'""></td>';
    //产地15+1
    newTr.insertCell().innerHTML = '<td ><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="cd" value="'+$("#cd"+index).val()+'" id="cd'+tdindex+'""></td>';
    //商品主键16+1
	newTr.insertCell().innerHTML = '<td ><span id="goodsid'+tdindex+'">' +$("#goodsid"+index).html()+ '</span></td>';     
	//新增明细17+1
	newTr.insertCell().innerHTML = '<td ><button type="button" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
	 
  	$("#goodsname"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodscode"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodsnorms"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodsname"+tdindex).parent().attr("title", $("#goodsname"+index).html());
 	$("#goodscode"+tdindex).parent().attr("title", $("#goodscode"+index).html());
 	$("#goodsnorms"+tdindex).parent().attr("title", $("#goodsnorms"+index).html());
 	$("#goodsid"+tdindex).parent().css("display","none");
    $(".unitsDate").datetimepicker({
		language:  'zh-CN',  
		minView:2,
	    autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
}
</script>
</html>