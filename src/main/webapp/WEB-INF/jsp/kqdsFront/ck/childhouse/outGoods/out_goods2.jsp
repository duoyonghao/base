
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	String goodsinid = request.getParameter("goodsinid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>商品出库</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/datepicker/datepicker3.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--与入库使用相同的css  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/ck/goodsIn/in_goods.css"/>
<style>
	.formBox{
		width:90%;
	}
	.kqds_table1{
		margin: auto;
	}
	.kqds_table1 td{
	  	padding: 3px 2px 5px 2px; 
	}
	.kqds_table1 select {
	    height: 28px;
	    width: 130px;
	    border: solid 1px #e5e5e5;
	    border-radius: 3px;
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
	.searchWrap {
	    padding: 10px 10px 8px 10px;
	}
	.searchWrap .kv select {
		height: 28px;
	    width: 130px;
	    border: solid 1px #e5e5e5;
	    border-radius: 3px;
	}
</style>
</head>
<body>
<div id="container" onunload="checkLeave()">
    <div class="infoBd">
        <div class="databaseWrap">
	        <div class="biliHistory">
	            <div class="tableHd">货物明细</div>
	            <!--fixedFatherDiv 首行固定表格的父元素样式  -->
	            <div class="fixedFatherDiv">
	            	<!-- fixedHeaderDiv固定的表头 -->
			            <div class="fixedHeaderDiv">
			            	 <table class="table-striped table-condensed table-bordered" style="width:100%;">
			            	    <thead style="height:30px; background: #00A6C0 ;color:white;">
									<tr>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>所属仓库</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>单价</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>库存数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>出库数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>出库金额</span></th>
										<th style="padding-right:24px; text-align: center; vertical-align: middle;width:auto;"><span>出库备注</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>出库批号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>批号数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>新增</span></th>
									</tr>
								</thead>
				            </table>
			            </div>
			            <!-- fixedBodyDiv滚动的内容 -->
			           <div class="fixedBodyDiv">
				            <table id="table" class="table-striped table-condensed table-bordered" style="width:100%;" onkeydown="keyDown(event)">
			            	    <thead style="height:30px; background: #00A6C0 ;color:white;">
									<tr>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>所属仓库</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>单价</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>库存数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>出库数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>出库金额</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>出库备注</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>出库批号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>批号数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>新增</span></th>
									</tr>
								</thead>
								<tbody style="background-color: #F0FFFF;text-align: center; height:345px; overflow-y:auto;"></tbody>
				            </table>
			          </div>
		          </div>
	        </div>
	        <div class="tableBox">
		    	<table style="width: 100%"> 
		       			<tr>
		 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total1">0</lable>条</span></td>
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
						<label>出库时间：</label>
						<div class="kv-v">
							<input type="text"  class="unitsDate2" name="cktime" id="cktime">
						</div>
					</div>
					<div class="kv">
		                <label>单据编号：</label>
		                <div class="kv-v">
		                    <input type="text" name="outcode" id="outcode" readonly="readonly">
		                </div>
		            </div>
               		<div class="kv">
						<label>出库方式：</label>
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
						<label>部门：</label>
						<div class="kv-v">
							 <select class="dept" name="sqdeptid" id="sqdeptid" ></select>
						</div>
					</div>
					 <div class="kv">
						<label>领料人：</label>
						<div class="kv-v">
							 <input type="hidden" name="llr" id="llr" class="form-control" />
							 <input type="text"   id="llrDesc" name="llrDesc" placeholder="领料人" readonly style="width: 130px;"onClick="javascript:single_select_user(['llr', 'llrDesc'],'single');"  ></input>	
						</div>
					</div>
					<div class="kv ">
						<label>领用医生：</label>
						<div class="kv-v">
							<input type="hidden" name="sqdoctor" id="sqdoctor" class="form-control" />
							<input type="text"   id="sqdoctorDesc" name="sqdoctorDesc" placeholder="领料医生" readonly style="width: 130px;"onClick="javascript:single_select_user(['sqdoctor', 'sqdoctorDesc'],'single');"  ></input>	
						</div>
					</div>
					<div class="kv" >
						<label>附加说明：</label>
						<div class="kv-v">
							<input type="text" name="outremark" id="outremark" >
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
<!--         </form> -->
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/keyout.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var goodsinid = "<%=goodsinid%>";
var tdindex = 0;
var menuid = parent.menuid;
var type;
var nowday;
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	if(menuid == 603100){
		type = '2';
	}else{
		type = '1';
	}
	getSupplierSelect2("supplier");
	getHouseSelect("outhouse");
	getSupplierSelectKeshi("sqdeptid");//初始化部门
    $("#outcode").val(gettimestr());
    $(".unitsDate2").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    nowday = getNowFormatDate();
    $("#cktime").val(nowday);
    
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
    tableHeight=$(window).outerHeight()-$(".searchWrap").outerHeight()-52;
    $(".fixedBodyDiv").outerHeight(tableHeight);
    /* 出现自动出现滚动条时 会导致表头内容对不齐  现改为 开始就出现滚动条  */
    $(".fixedHeaderDiv table").outerWidth($(".fixedHeaderDiv").outerWidth()-15);/* 表头留出右边15px的滚动条出现距离 */
}

//更换仓库，置空商品列表
$("#outhouse").change(function() {
	$('#table').find('tbody').html("");
	$("#total1").html(0);
}); 
//从货物列表页面（goosd_house.jsp），调用改方法
function addDoodsDetail(detail){
	 //显示条数
	var total1  = $("#total1").html();
	$("#total1").html(Number(total1)+(detail.length));

	 for(var i=0;i<detail.length;i++){
	var tablehtml = "";
	tdindex=detail[i].irows;
     
	 tablehtml += "<tr>";
	 //操作0
     tablehtml += '<td style="width:60px;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)" id="'+tdindex+'"><span style="color:red;">删除</span></a></td>';
     //所属仓库5
     tablehtml += '<td style="width:120px;"><span id="sshousename'+tdindex+'">' + detail[i].sshousename + '</span></td>';
	 //类别1
     tablehtml += '<td style="width:120px;"><span id="firsttype'+tdindex+'">'+detail[i].firsttype+'</span></td>';
     //类别2
     tablehtml += '<td style="width:120px;"><span id="goodstypename'+tdindex+'">'+ detail[i].goodstypename + '</span></td>';
     //商品名称3
      tablehtml += '<td style="width:150px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" title="' + detail[i].goodsname + '"><span id="goodsname'+tdindex+'" >' + detail[i].goodsname + '</span></td>';
     //商品编号4
     tablehtml += '<td style="width:120px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" title="' + detail[i].goodscode + '"><span id="goodscode'+tdindex+'">' + detail[i].goodscode + '</span></td>';
     //规格6
     tablehtml += '<td style="width:120px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" title="' + detail[i].goodsnorms + '"><span id="goodsnorms'+tdindex+'">'+detail[i].goodsnorms+'</span></td>';
     //单位7
     tablehtml += '<td style="width:50px;"><span id="goodsunit'+tdindex+'">'+detail[i].goodsunit+'</span></td>';
     //单价8
     tablehtml += '<td style="width:100px;"><input class="price" readonly="readonly" type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'goodsprice\','+tdindex+')" name="goodsprice'+tdindex+'" id="goodsprice'+tdindex+'" value="'+detail[i].goodsprice+'" oldvalue="'+detail[i].goodsprice+'"></td>';
     //库存数量9
     tablehtml += '<td style="width:80px;"><span id="nums'+tdindex+'">'+detail[i].nums+'</span></td>';
     //出库数量10
     tablehtml += '<td style="width:80px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'outnum\','+tdindex+')" name="outnum'+tdindex+'" id="outnum'+tdindex+'"></td>';
     //出库金额11
     tablehtml += '<td style="width:100px;"><input type="text" readonly="readonly" style="width:100%;text-align:center;" name="ckmoney'+tdindex+'" id="ckmoney'+tdindex+'"></td>';
     //出库备注12
     tablehtml += '<td style="width:120px;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="outremark" id="outremark"></td>';
     //商品主键13
 	 tablehtml += '<td style="display:none;" id="goodsid'+tdindex+'">' + detail[i].goodsid + '</td>';
 	 //商品批号 14
 	 tablehtml += '<td style="width:120px;"><select type="text" style="width:100%; text-align:center;height: 20px;" onchange="selectAllPh(this,'+tdindex+')" name="ph'+tdindex+'" id="ph'+tdindex+'" class="ph"></select></td>';
 	 //商品批号数量  15
 	 tablehtml += '<td style="width:120px;"><input type="text" readonly="readonly" style="width:100%; text-align:center;height: 20px;" name="phnum" id="phnum'+tdindex+'" class="phnum"></td>';
 	 //新增明细16
 	 tablehtml += '<td style="width:120px;"><button type="button" disabled="disabled" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
 	 //结存单价17
 	 tablehtml += '<td style="display:none;"><span id="price'+tdindex+'">'+detail[i].goodsprice+'</span></td>';
 	 //下标18
 	 tablehtml += '<td style="display:none;"><span id="index'+tdindex+'">'+tdindex+'</span></td>';
    
 	 tablehtml += "</tr>";
 	 $('#table').find('tbody').append(tablehtml);
 	 initChukuNum(tdindex,detail[i].goodsid);  // 初始化出库批号
	 }
     //判断出库方式：换货 退货 单价是可编辑的，其他的不可编辑
     var outtype = $("#outtype").val();
     if(outtype =="3" || outtype=="5"){
    	 $(".price").removeAttr("readonly");
     }
}

//初始化出库批号下拉框  2019/12/06 lutian
function initChukuNum(tdindex,goodsid){
	var phObj=$('#table').find('tbody').find("tr"); //当前行下拉框元素
	var url = contextPath + "/KQDS_Ck_Goods_In_DetailAct/selectAllGoodPhByGoodCode.act?type=2";
	var param = {
		goodsuuid : goodsid
	};
    $.axse(url, param,
    function(data) {
    	//console.log(JSON.stringify(data)+"--------------下拉框返回值");
        var list = data;
        if (list != null) {
            if (list.length > 0) {
                var phnum;
//                 var inprice;
                var phHtml="";
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
					phHtml+="<option value="+list[j].phnum+">"+list[j].ph+"</option>";
                	}
                $("#ph"+tdindex).html(phHtml); //出库批号
                $("#phnum"+tdindex).val(list[0].phnum); //批号数量
                var phlength =$("#ph"+tdindex).find("option").length;
                if(phlength>1){
                	$("#add"+tdindex).removeAttr("disabled","disabled"); 
                }
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

$("#outtype").change(function(){
	  if($(this).val() == "3" || $(this).val() == "5"){
		  $(".price").removeAttr("readonly");
	  }else{
		  //设置单价不可编辑
		  $(".price").attr("readonly","readonly");
		  //重置单价
		  $(".price").each(function() {
		        var oldvalue = $(this).attr("oldvalue");
		        $(this).val(oldvalue);
		        $(this).change();
		  });
	  }
});
//删除行
function deltr(obj) {
	//判断自身下标的数值
	var k = obj.parentNode.parentNode.rowIndex;
    var i=Number($(obj).attr("id"));
    var j=Number($("#index"+i).html());
	if(i<j){
		//删除所有
		var goodsid=$(obj).parent().parent().parent().find("td").eq(13).text();
		$("#table").find("tbody").find("tr").each(function(i,obj){
			var goodsidAdd=$(this).find("td").eq(13).text();
			if(goodsidAdd==goodsid){
				$(this).remove();
			}
		});
	}else if(i>j){
		$("#add"+j).removeAttr("disabled","disabled");
		$("#ph"+j).removeAttr("disabled","disabled"); 
		$("#index"+j).html(j);
		document.getElementById('table').deleteRow(k);
	    updatexj();
	    tdindex = $("#table").find("tbody").find("tr").length;
	    $("#total1").html(tdindex); //共有记录
	}else{
	    document.getElementById('table').deleteRow(k);
	    updatexj();
	    tdindex = $("#table").find("tbody").find("tr").length;
	    $("#total1").html(tdindex); //共有记录
	}
}

//选择商品
function xzhw() {
    	layer.open({
            type: 2,
            title: '货物列表',
            maxmin: true,
            shadeClose: false,
            //点击遮罩关闭层
            area: ['95%', '600px'],
            content: contextPath + '/KQDS_Ck_HouseAct/toGoodsHouse.act?type=0'
        });
}
function checknums(id,i){
	//出库编号
	//出库数量
	var outnums = $("#outnum"+i).val();
	var goodscode=$("#goodscode"+i).html();
	var goodsid=$("#goodsid"+i).html();
	var goodsprice = $("#goodsprice"+i).val();
	var ph =$("#ph"+i+" option:selected").text();
	var nums = $("#nums"+i).html();
	var phlength =$("#ph"+i).find("option").length;
	var phnum = $("#ph"+i).val();
	if(id == "outnum"){
		if(judgeSign(outnums)==false){
			 layer.alert('出库数量必须为正整数！', {
		              
		     });
			 $("#outnum"+i).val(0);
			 $("#ckmoney"+i).html("");
			 return false;
		}else{
			if(phnum!=null){
				if(Number(outnums)>Number(phnum)&&phlength==1){
					 layer.alert('库存不足！', {
				              
				     });
					 $("#outnum"+i).val(0);
					 $("#ckmoney"+i).html("");
					 return false;
				}else if(Number(outnums)>Number(nums)){
					layer.alert('库存不足！', {
			              
				     });
					 $("#outnum"+i).val(0);
					 $("#ckmoney"+i).html("");
					 return false;
				}
			}else{
				if(Number(outnums)>Number(nums)){
				 layer.alert('库存不足！', {
			              
			     });
				 $("#outnum"+i).val(0);
				 $("#ckmoney"+i).html("");
				 return false;
			}}
			
		}
	}
	var url = contextPath + '/KQDS_Ck_Goods_OutAct/findGoodsprice.act?type=2';
	if(phlength>1&&Number(outnums)>Number(phnum)&&phnum!=null){
		var j=$("#index"+i).html();
		if(i==j){
		layer.confirm("当前批号数量不足，是否添加新的批号信息？",{
			   btn: ['确认', '取消'],
			   closeBtn:0
			  }, function (index) {
			   //把当前批号数据锁定
			   $("#outnum"+i).val(phnum);
			   var dnum=outnums-phnum;
				$.ajax({
			        type: "POST",
			        url: url,
			        data: {outnums:phnum,goodsuuid:goodsid,ph:ph},
			        dataType: "json",
			        success: function(r){
			        	var appendHtml;
				        if(r.numlist){
				        	appendHtml="<span class='numlist' style='display:none;'>"+JSON.stringify(r.numlist)+"</span>";
				        }else{
				        	appendHtml="<span class='numlist' style='display:none;'>[]</span>";
				        }
				    	var numlistSpan=$("#outnum"+i).parent("td").find("span.numlist");
				    	numlistSpan.remove();
				    	$("#outnum"+i).parent().append(appendHtml);
				    	if($("#goodsprice"+i).val()==0){
			        		$("#ckmoney"+i).val(Number(r.ckmoney).toFixed(3));
			        	}
				       	$("#goodsprice"+i).val(Number(r.goodsprice).toFixed(3));
				       	$("#ph"+i).attr("disabled","disabled"); 
				       	$("#add"+i).attr("disabled","disabled");
				       	addDeatil(i);
						layer.close(index);
			        }
				});
			  }, function(index){
			   	//取消本次操作
			   	//更改出库数量为批号数量,单价为批号单价，金额为批号总金额
				//根据出库数量和编号更改
				$("#outnum"+i).val(phnum);
					$.ajax({
				         type: "POST",
				         url: url,
				         data: {outnums:phnum,goodsuuid:goodsid,ph:ph},
				         dataType: "json",
				         success: function(r){
				        	var appendHtml;
						    if(r.numlist){
						        appendHtml="<span class='numlist' style='display:none;'>"+JSON.stringify(r.numlist)+"</span>";
						    }else{
						        appendHtml="<span class='numlist' style='display:none;'>[]</span>";
						    }var numlistSpan=$("#outnum"+i).parent("td").find("span.numlist");
					    	numlistSpan.remove();
					    	$("#outnum"+i).parent().append(appendHtml);
				        	if($("#goodsprice"+i).val()==0){
				        		$("#ckmoney"+i).val(Number(r.ckmoney).toFixed(3));
				        	}
				        	$("#goodsprice"+i).val(Number(r.goodsprice).toFixed(3));
				        	updatexj();
							layer.close(index);
				         }
			     	});
			  });
		}else{
			layer.alert('出库数量多于批号数量！', {
	              
		     });
			 $("#outnum"+i).val(0);
			 $("#ckmoney"+i).html("");
			 return false;
		}
	}else{
		if(phnum!=null){
			//根据出库数量和编号更改
			 $.ajax({
		         type: "POST",
		         url: url,
		         data: {outnums:outnums,goodsuuid:goodsid,ph:ph},
		         dataType: "json",
		         success: function(r){
		        	 var appendHtml;
		        	if(r.numlist){
		        		appendHtml="<span class='numlist' style='display:none;'>"+JSON.stringify(r.numlist)+"</span>";
		        	}else{
		        		appendHtml="<span class='numlist' style='display:none;'>[]</span>";
		        	}
			    	var numlistSpan=$("#outnum"+i).parent("td").find("span.numlist");
			    	numlistSpan.remove();
			    	$("#outnum"+i).parent().append(appendHtml);
		        	if($("#goodsprice"+i).val()==0){
		        		$("#ckmoney"+i).val(Number(r.ckmoney).toFixed(3));
		        	}
		        	$("#goodsprice"+i).val(Number(r.goodsprice).toFixed(3));
		        	updatexj();
		         }
		     });
		}else{
			//根据出库数量和编号更改
			 $.ajax({
		         type: "POST",
		         url: url,
		         data: {outnums:outnums,goodsuuid:goodsid},
		         dataType: "json",
		         success: function(r){
		        	var appendHtml="<span class='numlist' style='display:none;'>[]</span>";
				    var numlistSpan=$("#outnum"+i).parent("td").find("span.numlist");
				    numlistSpan.remove();
				    $("#outnum"+i).parent().append(appendHtml);
		        	if($("#goodsprice"+i).val()==0){
		        		$("#ckmoney"+i).val(r.ckmoney);
		        	}
		        	$("#goodsprice"+i).val(r.goodsprice);
		        	updatexj();
		         }
		     });
		}
	}
}


function updatexj(){
	 //改变出库单价（领用出库、其他）
	 var rows = $("#table tbody tr").length;
	 if(rows > 0) {
		 rows = findMaxIndex(); //获取当前列表中最大的下标进行循环，防止删除后导致下标未更新金额计算错误
	 }
	 var innumall=0,rkmoneyall=0;
	 for(var i=1;i<=rows;i++){
		 var innum = $("#outnum"+i).val();
		 var rkmoney = $("#ckmoney"+i).val();
		 //console.log("dddd"+rkmoney);
		 if(innum != null && innum != ""){
			 innumall += Number(innum);
		 }
		 if(rkmoney != null && rkmoney != ""){
			 rkmoneyall += Number(rkmoney);
		 }
	 }
	 $("#innumall").html(Number(innumall));
	 //console.log(Number(rkmoneyall).toFixed(3));
	 $("#rkmoneyall").html(Number(rkmoneyall).toFixed(3));
}
function findMaxIndex(){
	var maxTd = $("#table tbody tr:last").find("input[name^='ckmoney']");
	return maxTd.attr("name").replace("ckmoney","");
}
function save() {
    //验证
    var outhouse = $("#outhouse").val();
    var llr = $("#llr").val();
    var outtype = $("#outtype").val();
    var sqdoctor = $("#sqdoctor").val();
    var zhaiyao = $("#zhaiyao").val();
    var supplier = $("#supplier").val();
    var cktime = $("#cktime").val();
    var sqdeptid = $("#sqdeptid").val();
    if ($("#table tr").length < 2) {
        layer.alert('请选择出库商品' );
        return false;
    }
    if (outtype == "" || outtype == null) {
        layer.alert('请选择出库方式' );
        return false;
    } 
	/* if (outhouse == "" || outhouse == null) {
        layer.alert('请选择发货仓库' );
        return false;
    } */
	if (sqdeptid == "" || sqdeptid == null) {
        layer.alert('请选择出库部门'  );
        return false;
    }
    if (outtype != 3 && outtype != 5) {//出换货出库和退货出库其他需要填写领料人
        if (llr == "" || llr == null) {
            layer.alert('请填写领料人'  );
            return false;
        }
    }
    /* if (zhaiyao == "" || zhaiyao == null) {
        layer.alert('请填写摘要'  );
        return false;
    } */
    //循环获取表格中项目
    var flag= true;//验证出库商品中必填项数量  true是填写  false存在未填写
    var msg;
    var list = [];
    var list1 = [];
    $('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            var paramDetail = {};
            $(this).find('td').each(function() {
                if ($(this).index() == 13) {
                    //商品主键
                    paramDetail.goodsuuid = $(this).text();
                } else if ($(this).index() == 8) {
 	               	//商品单价
 	               	var outprice = $(this).find("input").val();
                    paramDetail.outprice = outprice;
                }else if ($(this).index() == 9) {
 	               	//商品库存
 	               	var outprice = $(this).find("input").val();
                    paramDetail.outprice = outprice;
                } else if ($(this).index() == 10) {
 	               	//数量
 	               	var outnum = $(this).find("input").val();
 	               	var numlist = $(this).find("input").parent().find("span").text();
 	               	if(outnum == ""){
 	               		flag = false;
 	               		msg = "出库数量不能为空";
 	               		return false;
 	               	}else{
 						if(judgeSignNum(outnum)==false){
 							flag = false;
 							msg = "出库数量应为整数";
 	               			return false;
 	               		}
 	               	} 
 	               	var pp=JSON.parse(numlist);
 	               	if(pp.length>0){
 	               		for (var i = 0; i < pp.length; i++) {
		 	               list1.push(pp[i]);
						}
 	               	}
                    paramDetail.outnum = outnum;
                }  else if ($(this).index() == 11) {
	               	//出库金额
	               	var ckmoney = $(this).find("input").val();
                    paramDetail.ckmoney = ckmoney;
                }  else if ($(this).index() == 12) {
                    //出库备注
                	paramDetail.sqremark = $(this).find("input").val();
                } else if ($(this).index() == 14) {//新增 
                    //出库商品批号
                	paramDetail.ph = $(this).find("select option:selected").text();
                } else if ($(this).index() == 15) {//新增 
                    //出库商品批号数量 
                	paramDetail.phnum = $(this).find("input").val();
                } 
                paramDetail.type = type;
            });
            list.push(paramDetail);
        });
    });
    if(!flag){
    	   layer.alert(msg, {
                 
           });
           return false;
    }
    //入库明细相关参数
    var data = JSON.stringify(list);
    data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
    var data1 = JSON.stringify(list1);
    data1 = encodeURIComponent(data1); //编码 参数里有特殊符号时需要编码
    var outhouse = $("#outhouse").val();
    var llr = $("#llr").val();
    var outtype = $("#outtype").val();
    var sqdoctor = $("#sqdoctor").val();
    var zhaiyao = $("#zhaiyao").val();
    var supplier = $("#supplier").val();
    var sqdeptid = $("#sqdeptid").val();
    var param = {
    	paramDetail:data,
    	paramDetail1:data1,
        outtype: outtype,
        sqdoctor: sqdoctor,
        supplier:supplier,
        llr: llr,
        type:type,
        sqdeptid :sqdeptid,
        cktime : cktime,
        //outhouse: outhouse,
        outcode: $("#outcode").val(),
        outremark: $("#outremark").val(),
        zhaiyao: zhaiyao
    };
    var url = contextPath + '/KQDS_Ck_Goods_OutAct/insertOrUpdate.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', { 
                end: function() {
                	saveGoodsGjTx();
                	var conidnex =layer.confirm('是否打印出库单？', {
    		 		    btn: ['打印','关闭'] //按钮
    				}, function(){
    				    layer.close(conidnex);
    				    openDayin($("#outcode").val());	
//     				   	parent.layer.close(frameindex); //再执行关闭
   			      		$('#table').find('tbody').html("");
   			      		$("#total1").html(0);
    				}, function(){
//     					parent.layer.close(frameindex); //再执行关闭
   			      		$('#table').find('tbody').html("");
   			      		$("#total1").html(0);
    				});
                }
            });
        } else if(r.retState == "100"){ 
        	$("#outcode").val(gettimestr());
    	    layer.alert('出库单号已存在，系统已自动重新获取，请重新保存。', {
	               
	        });
	 
        }else {
            layer.alert('保存失败');
        }
    },
    function(r) {
    	 if (r.status == 200) {
             layer.alert('保存成功', { 
                 end: function() {
                 	saveGoodsGjTx();
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
     	    layer.alert('出库单号已存在，系统已自动重新获取，请重新保存。', {
 	               
 	        });
 	 
         }else {
             layer.alert('保存失败');
         }
    });
}
function openDayin(outcode) {
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("商品出库单");
    var costurl = "";
    // 默认 a5
    costurl = contextPath + '/KQDS_Print_SetAct/toOutGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&outcode=' + outcode;
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

//根据出库批号查询数量
function selectAllPh(obj,tdindex) {
	var phObj=$('#table').find('tbody').find("tr").eq(tdindex-1); //当前行出库批号数量
	var phnum = $(obj).find("option:selected").val(); // 批号数量
	var inprice = $(obj).find("option:selected").attr("inprice"); // 批号单价
	phObj.find(".phnum").val(phnum);
	var outnum=phObj.find("#outnum"+tdindex).val();
	if(outnum){
		checknums("outnum",tdindex);
	}
}
function addDeatil(index){
	$("#ph"+index).attr("disabled","disabled"); 
	$("#add"+index).attr("disabled","disabled");
	tdindex = $("#table").find("tbody").find("tr").length;
	tdindex++;
	 //显示条数
    $("#total1").html(tdindex); //共有记录
	 var tb = document.getElementById("table");
     var newTr;//添加新行，trIndex就是要添加的位置
	$("#table").find("tbody").find("tr").each(function(i,obj){
		var addid=$(this).find("td").eq(16).find("button").attr("id");
		if(addid==$("#add"+index).attr("id")){
			newTr = tb.insertRow(i+2);
		}
	});
   //操作
     newTr.insertCell().innerHTML = '<td><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)" id="'+tdindex+'"><span style="color:red;">删除</span></a></td>';
     //所属仓库3
     newTr.insertCell().innerHTML = '<td><span id="sshousename'+tdindex+'">' + $("#sshousename"+index).html() + '</span></td>';
	 //类别1
     newTr.insertCell().innerHTML = '<td><span id="firsttype'+tdindex+'">'+$("#firsttype"+index).html()+'</span></td>';
     //类别2
     newTr.insertCell().innerHTML = '<td><span id="goodstypename'+tdindex+'">'+$("#goodstypename"+index).html()+'</span></td>';
     //商品名称0
     newTr.insertCell().innerHTML = '<td><span id="goodsname'+tdindex+'">' +$("#goodsname"+index).html()+ '</span></td>';
     //商品编号1
     newTr.insertCell().innerHTML = '<td><span id="goodscode'+tdindex+'">' + $("#goodscode"+index).html()+ '</span></td>';
     //规格4
     newTr.insertCell().innerHTML = '<td><span id="goodsnorms'+tdindex+'">'+ $("#goodsnorms"+index).html()+'</span></td>';
     //单位5
     newTr.insertCell().innerHTML = '<td><span id="goodsunit'+tdindex+'">'+$("#goodsunit"+index).html()+'</span></td>';
     //单价6
     newTr.insertCell().innerHTML = '<td><input class="price" readonly="readonly" type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'goodsprice\','+tdindex+')" name="goodsprice'+tdindex+'" id="goodsprice'+tdindex+'" value="'+$("#price"+index).html()+'" oldvalue="'+$("#price"+index).html()+'"></td>';
     //库存数量7
     newTr.insertCell().innerHTML = '<td><span id="nums'+tdindex+'">'+$("#nums"+index).html()+'</span></td>';
     //出库数量8
     newTr.insertCell().innerHTML = '<td><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'outnum\','+tdindex+')" name="outnum'+tdindex+'" id="outnum'+tdindex+'"></td>';
     //出库金额9
     newTr.insertCell().innerHTML = '<td><input type="text" readonly="readonly" style="width:100%;text-align:center;" name="ckmoney'+tdindex+'" id="ckmoney'+tdindex+'"></td>';
     //出库备注10
     newTr.insertCell().innerHTML = '<td><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="outremark" id="outremark"></td>';
     //商品主键11
 	 newTr.insertCell().innerHTML = '<td><span id="goodsid'+tdindex+'">' +$("#goodsid"+index).html() + '</span></td>';
 	 //商品批号 12
 	 newTr.insertCell().innerHTML = '<td><select type="text" style="width:100%; text-align:center;height: 20px;" onchange="selectAllPh(this,'+tdindex+')" name="ph'+tdindex+'" id="ph'+tdindex+'" class="ph"></select></td>';
 	 // 商品批号数量  13
 	 newTr.insertCell().innerHTML = '<td><input type="text" readonly="readonly" style="width:100%; text-align:center;height: 20px;" name="phnum" id="phnum'+tdindex+'" class="phnum"></td>';
 	 //新增明细
 	 newTr.insertCell().innerHTML ='<td><button type="button" disabled="disabled" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
 	 //结存单价
 	 newTr.insertCell().innerHTML ='<td><span id="price'+tdindex+'">'+$("#price"+index).html()+'</span></td>';
 	 //下标
 	 newTr.insertCell().innerHTML ='<td><span id="index'+tdindex+'">'+index+'</span></td>';
 	$("#goodsname"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodscode"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodsnorms"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodsname"+tdindex).parent().attr("title", $("#goodsname"+index).html());
 	$("#goodscode"+tdindex).parent().attr("title", $("#goodscode"+index).html());
 	$("#goodsnorms"+tdindex).parent().attr("title", $("#goodsnorms"+index).html());
 	$("#goodsid"+tdindex).parent().css("display","none");
 	$("#price"+tdindex).parent().css("display","none");
 	$("#index"+tdindex).parent().css("display","none");
 	$("#index"+index).html(tdindex);
 	var ph=$("#ph"+index+" option:selected").text();
 	var phs=$("#ph"+index).html();
 	var phss=phs.split("/option>");
 	var phsss='';
 	for (var i = 0; i < phss.length; i++) {
		if(phss[i]!=''&&phss[i].indexOf(">"+ph+"<") == -1){
			phsss+=phss[i]+"/option>";
		}
	}
 	$("#ph"+tdindex).html(phsss);
 	$("#phnum"+tdindex).val($("#ph"+tdindex+" option:selected").val());
 	//判断出库方式：换货 退货 单价是可编辑的，其他的不可编辑
    var outtype = $("#outtype").val();
    if(outtype =="3" || outtype=="5"){
   	 $(".price").removeAttr("readonly");
    }
}
</script>
</html>
