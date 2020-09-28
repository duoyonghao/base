<!--wl整理  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	
	String usercode = request.getParameter("usercode");
	if(usercode == null ){
	 usercode = "";  // 配合js 的非空判断，这里不这样写，则js 需进行  usercode != "null"的判断
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>领料申请单</title>
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
			    			<td style="text-align:right;">领料时间：</td>
			    			<td style="text-align:left;"> 
		    					 <input type="text"  class="unitsDate2" name="rktime" id="rktime">
			                </td>
			                
			    			<!-- 
			    			<td style="text-align:right;">入库方式：</td>
			    			<td style="text-align:left;"> 
		    					 <select  name="intype" id="intype">
			                    	<option value="">请选择</option>
			                    	<option value="0">采购入库</option>
			                    	<option value="2">换货入库</option>
			                    	<option value="4">其他入库</option>
			                     </select>
			                </td> -->
			    			<!-- <td style="text-align:right;">供应商：</td>
			    			<td style="text-align:left;">
								<select  name="supplier" id="supplier"></select>
							</td> -->
							
			    			<td style="text-align:right;">进货时间：</td>
			    			<td style="text-align:left;">
		    					<input type="text"  class="unitsDate2" name="stocktime" id="stocktime">
			    			</td>
			    			 
			    			<td style="text-align:right;">所在科室：</td>
			    			<td style="text-align:left;">
		    					<select  name="keshi_id" id="keshi_id">
		    						<option value="">请选择</option>
		    					</select>
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
			    		 <a href="javascript:void(0);" onclick="xzhw()" class="kqdsCommonBtn">选择商品</a>
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
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>所属仓库</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>科室</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>科室数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>领用数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>领料备注</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>有效期</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>批号数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto"><span>新增</span></th>
									</tr>
								</thead>
				            </table>
			            </div>
			            <!-- fixedBodyDiv 滚动的内容 -->
			           <div class="fixedBodyDiv">
				            <table id="table" class="table-striped table-condensed table-bordered" style="width:100%;" onkeydown="keyDown(event)">
			            	    <thead style="height:30px; background: #00A6C0;color:white;">
									<tr>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>所属仓库</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>科室</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>科室数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>领用数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>领料备注</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>有效期</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>批号数量</span></th>
										<th style="text-align: center; vertical-align: middle; width:auto;"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto"><span>新增</span></th>
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
		 				<!-- <td width="30%"><span style="color:blue;">金额小计：<lable id="rkmoneyall">0</lable></span></td> -->
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/keyin.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var usercode = "<%=usercode%>";
var onclickrowOobj = window.parent.onclickrowOobj; //父页面传值
var costno1 = '<%=request.getAttribute("costno1")%>';
//alert(costno1);
var tdindex = 0;
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var nowday;
$(function() {
	getSupplierSelectKeshi("keshi_id");//搜索初始化
    getSupplierSelect2("supplier");
    getHouseSelect("inhouse");
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
    //addKeshi();
  	//更换仓库，置空商品列表
    $("#keshi_id").change(function(){
    	$('#table').find('tbody').html("");
    	$("#total1").html(0);
    }); 
});

function addKeshi() {
	var url = contextPath + "/KQDS_Ck_CkdeptAct/selectList.act";
	var param = { id : null };
	$.axseSubmit(url, param, function() {}, function(r) {
		  //alert(JSON.stringify(r));
		  //alert(r.length);
		  for (var i = 0; i < r.length; i++) {
				  $("#keshi_id").append(
						'<option value='+ r[i].seqId + '>' + r[i].deptname + '</option>' 
				  );
		  }
		}, function() {
			
	});
}


/* 计算 设置table数据表的高度 */
function resizeTableHeight(){
	var tableHeight=0;
    tableHeight=$(window).height()-$(".formBox").outerHeight()-62;
    $(".fixedBodyDiv").outerHeight(tableHeight);
    /* 出现自动出现滚动条时 会导致表头内容对不齐  现改为 开始就出现滚动条  */
    $(".fixedHeaderDiv table").outerWidth($(".fixedHeaderDiv").outerWidth()-15);/* 表头留出右边15px的滚动条出现距离 */
}

//从货物列表页面（goosd_house.jsp），调用该方法
function addDoodsDetail(detail){
	 //alert(detail);
	 //alert(JSON.stringify(detail));
	 tdindex++;
	 //显示条数
	 var total1  = $("#total1").html();
     $("#total1").html(Number(total1)+1);
	 var tablehtml = "";
	 tablehtml += "<tr style=''>";
	 //操作0
     tablehtml += '<td style="width:auto;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)" id="'+tdindex+'"><span style="color:red;">删除</span></a></td>';
     //所属仓库1
     tablehtml += '<td style="width:auto;"><span id="housename'+tdindex+'">' + detail.housename + '</span></td>';
   	 //科室2
     tablehtml += '<td style="width:auto;"><span id="deptpartname'+tdindex+'">' + detail.deptpartname + '</span></td>';
     //商品名称3
     tablehtml += '<td style="width:auto;"><span id="goodsname'+tdindex+'">' + detail.goodsname + '</span></td>';
     //商品编号4
     tablehtml += '<td style="width:auto;"><span id="goodscode'+tdindex+'">' + detail.goodscode + '</span></td>';
     //规格5
     tablehtml += '<td style="width:auto;"><span id="goodsnorms'+tdindex+'">' + detail.goodsnorms + '</span></td>';
     //单位6
     tablehtml += '<td style="width:auto;"><span id="goodsunit'+tdindex+'">' + detail.goodsunit + '</span></td>';
     //科室商品数量 7
     tablehtml += '<td style="width:auto;"><span id="nums'+tdindex+'">' + detail.nums + '</span></td>';//科室商品数量
     //领用数量8
     tablehtml += '<td style="width:auto;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'quantity\','+tdindex+')" name="quantity'+tdindex+'" id="quantity'+tdindex+'"></td>';
     //领料备注9
     tablehtml += '<td style="width:auto;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="inremark" id="inremark"></td>';
     //有效期10
     tablehtml += '<td style="width:auto;"><input type="text" style="width:100%; text-align:center;" class="unitsDate" name="yxdate" id="yxdate'+tdindex+'"></td>';
     //批号数量11
     tablehtml += '<td style="width:auto;"><span id="phnum'+tdindex+'" class="phnum"></span></td>';//科室商品批号数量;
   	 //批号12
     tablehtml += '<td style="width:auto;"><select type="text" style="width:100%; text-align:center;height: 20px;" onchange="selectAllPh(this,'+tdindex+')" name="ph'+tdindex+'" id="ph'+tdindex+'" class="ph"></select></td>';
     //商品主键13
 	 tablehtml += '<td style="display:none;"><span id="goodsuuid'+tdindex+'">' + detail.goodsuuid + '</span></td>';
 	 //新增明细14
 	 tablehtml += '<td style="width:120px;"><button type="button" disabled="disabled" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
 	 //下标15
 	 tablehtml += '<td style="display:none;"><span id="index'+tdindex+'">'+tdindex+'</span></td>';
     tablehtml += "</tr>";
     $('#table').find('tbody').append(tablehtml);
     initChukuNum(tdindex,detail.goodscode); // 初始化出库批号
     $(".unitsDate").datetimepicker({
    		language:  'zh-CN',  
    		minView:2,
    	    autoclose : true,
    		format: 'yyyy-mm-dd',
    		pickerPosition: "bottom-right"
    	});
}
//初始化出库批号下拉框  2019/12/06 lutian
function initChukuNum(tdindex,goodscode){
	var phObj=$('#table').find('tbody').find("tr").eq(tdindex-1); //当前行下拉框元素
	var url = contextPath + "/HUDH_KSllAct/selectAllGoodPhByGoodCode.act";
	var param = {
			goodscode : goodscode,
			deptpart:$("#keshi_id").val()
	};
    $.axse(url, param,
    function(data) {
        var list = data;
        if (list != null) {
            if (list.length > 0) {
                var phnum;
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    phObj.find(".ph").append(
    					'<option value='+ optionStr.phnum + '>'+  optionStr.ph + '</option>' 
    				);
                    if(j==0){
                    	phnum=optionStr.phnum; // 批号数量
                    }
                }
                phObj.find(".phnum").html(phnum);
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
/* function getZczh(goodsuuid){
	var jobj = '';
	var url = contextPath+'/KQDS_Ck_Goods_InAct/selectZczh.act?goodsuuid=' + goodsuuid;
    $.axse(url, null,
    function(data) {
    	jobj = data;
    },
    function() {
    });
    return jobj;
} */
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
	//var keshi_id = $("#keshi_id").val();
	var deptpart = $("#keshi_id").val();
	if (!deptpart) {
		layer.alert("请选择科室！");
	} else if( deptpart) {//#################校验###################
	    layer.open({
	        type: 2,
	        title: '货物列表',
	        maxmin: true,
	        shadeClose: false,
	        //点击遮罩关闭层
	        area: ['90%', '450px'],
	        content: contextPath + '/HUDH_Goods_DoctorPickViewAct/toGoodsList.act?deptpart=' + deptpart
	    });
	} 
}
function checknums(id,i){
	 var nums = $("#nums"+i).html();
	 var innum = $("#quantity" + i).val();
	 var phnum = $("#phnum"+i).html();
	 var goodsuuid = $("#goodsuuid"+i).html();
	 var phlength =$("#ph"+i).find("option").length;
	 if(id == "quantity"){
		if(judgeSign(innum) == false){
				 layer.alert('领用数量必须为正整数！', {
			     });
				 $("#quantity"+i).val("");
				 return false;
		} else{
			if(Number(innum)>Number(nums)){
				layer.alert('库存不足！', {
				});
			    $("#quantity"+i).val(nums);
			    return false;
			}else if(Number(innum)>Number(phnum)){
				if(phlength>1&&phnum!=null){
					var j=$("#index"+i).html();
					if(i==j){
					layer.confirm("当前批号数量不足，是否添加新的批号信息？",{
						   btn: ['确认', '取消'],
						   closeBtn:0
						  }, function (index) {
						   //把当前批号数据锁定
						    $("#quantity"+i).val(phnum);
							$("#ph"+i).attr("disabled","disabled"); 
					       	$("#add"+i).attr("disabled","disabled");
					       	addDeatil(i);
							layer.close(index);
						  }, function(index){
						   	//取消本次操作
						   	//更改出库数量为批号数量,单价为批号单价，金额为批号总金额
							//根据出库数量和编号更改
							$("#quantity"+i).val(phnum);
				        	updatexj();
							layer.close(index);
						  });
					}else{
						layer.alert('领用数量多于批号数量！', {
				              
					     });
						 $("#quantity"+i).val(phnum);
						 return false;
					}
				}else{
					layer.alert('库存不足！', {
					});
				    $("#quantity"+i).val(phnum);
				    return false;
				}
			}
		}
	 }
	var n=0;
	$("#table").find("tbody").find("tr").each(function(i,obj){
		var goodsidAdd=$(this).find("td").eq(13).text();
		if(goodsidAdd==goodsuuid){
			n= n+ Number($(this).find("td").eq(8).find("input").val());
		}
	});
	if(n>nums){
		layer.alert('领用数量多于库存数量，请查询后填写！', {
              
	     });
		$("#quantity"+i).val("");
		return false;
	}
	updatexj();
}

function updatexj(){
	 //改变出库单价（领用出库、其他）
	 var rows = $("#table tbody tr").length;
	 $("#total1").html(rows);
	 if(rows > 0) {
		 rows = findMaxIndex(); //获取当前列表中最大的下标进行循环，防止删除后导致下标未更新金额计算错误
	 }
	 var innumall = 0;
	 for( var i = 1; i <= rows; i++ ){
		  var innum = $("#quantity" + i).val();
		  if(innum != null && innum != ""){
			  innumall += Number(innum);
		  }
	 }
	 $("#innumall").html(Number(innumall));
}

function findMaxIndex(){
	var maxTd = $("#table tbody tr:last").find("input[name^='quantity']");
	return maxTd.attr("name").replace("quantity","");
}
/**
 * 保存数据
 */
function save() {
    //验证
    var supplier = $("#supplier").val();
    var zhaiyao = $("#zhaiyao").val();
    var inhouse = $("#inhouse").val();
    var intype = $("#intype").val();
    var rktime = $("#rktime").val();
    var stocktime = $("#stocktime").val();
    if ($("#table tr").length < 2) {
        layer.alert('请选择入库商品' );
        return false;
    }
    /* if (intype == "" || intype == null) {
        layer.alert('请选择入库方式' );
        return false;
    }  
    if (inhouse == "" || inhouse == null) {
        layer.alert('请选择收货仓库' );
        return false;
    } */
    /* if (intype == 4) {
        if (zhaiyao == "" || zhaiyao == null) {
            layer.alert('请填写摘要'  );
            return false;
        }
    } else if (intype == 2) {
        if (supplier == "" || supplier == null) {
            layer.alert('请选择供货商'  );
            return false;
        }
        if (zhaiyao == "" || zhaiyao == null) {
            layer.alert('请填写摘要'  );
            return false;
        }
    } else {
        if (supplier == "" || supplier == null) {
            layer.alert('请选择供货商'  );
            return false;
        }
    } */
   //循环获取表格中项目
   var flag= true;//验证入库商品中必填项单价 和 数量  true是填写  false存在未填写
   var flagStyle= true;//验证入库商品中必填项单价（非负浮点数） 和 数量（正整数）的格式  true是正确  false存在不正确
   var list = [];
   $('#table').find('tbody').each(function() {
       $(this).find('tr').each(function() {
           var paramDetail = {};
           $(this).find('td').each(function() {
        	   if ($(this).index() == 1) {
        		   //所属仓库
        		   var housename = $(this).find("span").html();
                   paramDetail.housename = housename;
        	   } else if ($(this).index() == 2) {
        		   //科室
        		   var deptpartname = $(this).find("span").html();
                   paramDetail.deptpartname = deptpartname;
        	   } else if ($(this).index() == 3) {
        		   //商品名称
        		   var goodsname = $(this).find("span").html();
                   paramDetail.goodsname = goodsname;
        	   } else if ($(this).index() == 4) {
        		   //商品编号
        		   var goodscode = $(this).find("span").html();
                   paramDetail.goodscode = goodscode;
        	   } else if ($(this).index() == 5) {
        		   //商品规格
        		   var goodsnorms = $(this).find("span").html();
                   paramDetail.goodsnorms = goodsnorms;
        	   } else if ($(this).index() == 6) {
        		   //商品单位
        		   var goodsunit = $(this).find("span").html();
                   paramDetail.goodsunit = goodsunit;
        	   } else if ($(this).index() == 7) {
        		   //科室商品数量 7
        		   var nums = $(this).find("span").html();
                   paramDetail.nums = nums; 
               } else if ($(this).index() == 8) {
	               	//数量
	               	var quantity = $(this).find("input").val();
	               	if(quantity == ""){
	               		flag = false;
	               		return false;
	               	}else{
						if(judgeSign(quantity)==false){
							flagStyle = false;
	               			return false;
	               		}
	               	} 
                   paramDetail.quantity = quantity;
               } else if ($(this).index() == 9) {
                   //入库备注
               	paramDetail.remark = $(this).find("input").val();
              } else if ($(this).index() == 10) {
                   //有效期
               	paramDetail.createdate = $(this).find("input").val();
              }else if ($(this).index() == 12) {
                  //批号
            	paramDetail.batchnum= $(this).find("select option:selected").text();
              } else if ($(this).index() == 13) {
            	   //商品id
            	paramDetail.goodsuuid = $(this).find("span").html();
              }
           });
           //alert(JSON.stringify(paramDetail));
           list.push(paramDetail);
       });
   });
   if(!flag){
   	   layer.alert('入库商品中领用数量未填写，请填写！', {
                
       });
       return false;
   }
   /* if(!flagStyle){
   	   layer.alert('入库商品中存在数量、单价格式不正确！', {
                
          });
          return false;
   }  */
   //入库明细相关参数
   var data = JSON.stringify(list);
   data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
   //alert(data);
   var param = {
   	   paramDetail: data,	
       seqId: $("#seqId").val(),
       intype: $("#intype").val(),
       supplier: supplier,
       inhouse: inhouse,
       incode: $("#incode").val(),
       inremark: $("#inremark").val(),
       rktime : rktime,
       zhaiyao: zhaiyao,
       stocktime : stocktime,
       costno1 : costno1,
       deptpart:$("#keshi_id").find("option:selected").val()
   };
   var url = contextPath + '/HUDH_Goods_DoctorPickInAct/insertGoogsPick.act';
   $.axseSubmit(url, param,
   function() {},
   function(r) {
       if (r.retState == "0") {
           layer.alert('保存成功', {
               end: function() {
            	saveGoodsGjTx();
               	var conidnex =layer.confirm('是否打印领料单？', {
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
           layer.alert(r.retMsrg, {
                 
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
    var printSet = getPrintType("领料出库单");
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
	$("#ph"+index).attr("disabled","disabled"); 
	$("#add"+index).attr("disabled","disabled");
	tdindex = $("#table").find("tbody").find("tr").length;
	tdindex++;
	 //显示条数
    $("#total1").html(tdindex); //共有记录
	 var tb = document.getElementById("table");
	 var newTr;//添加新行，trIndex就是要添加的位置
		$("#table").find("tbody").find("tr").each(function(i,obj){
			var addid=$(this).find("td").eq(14).find("button").attr("id");
			if(addid==$("#add"+index).attr("id")){
				newTr = tb.insertRow(i+2);
			}
		});
     //操作0
     newTr.insertCell().innerHTML= '<td ><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)" id="'+tdindex+'"><span style="color:red;">删除</span></a></td>';
     //所属仓库1
     newTr.insertCell().innerHTML= '<td ><span id="housename'+tdindex+'">' + $("#housename"+index).html()+ '</span></td>';
   	 //科室2
     newTr.insertCell().innerHTML= '<td ><span id="deptpartname'+tdindex+'">' + $("#deptpartname"+index).html() + '</span></td>';
     //商品名称3
     newTr.insertCell().innerHTML= '<td ><span id="goodsname'+tdindex+'">' + $("#goodsname"+index).html() + '</span></td>';
     //商品编号4
     newTr.insertCell().innerHTML= '<td ><span id="goodscode'+tdindex+'">' + $("#goodscode"+index).html() + '</span></td>';
     //规格5
     newTr.insertCell().innerHTML= '<td ><span id="goodsnorms'+tdindex+'">' + $("#goodsnorms"+index).html() + '</span></td>';
     //单位6
     newTr.insertCell().innerHTML= '<td ><span id="goodsunit'+tdindex+'">' + $("#goodsunit"+index).html() + '</span></td>';
     //科室商品数量 7
     newTr.insertCell().innerHTML= '<td ><span id="nums'+tdindex+'">' + $("#nums"+index).html() + '</span></td>';//科室商品数量
     //领用数量8
     newTr.insertCell().innerHTML= '<td ><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'quantity\','+tdindex+')" name="quantity'+tdindex+'" id="quantity'+tdindex+'"></td>';
     //领料备注9
     newTr.insertCell().innerHTML= '<td ><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="inremark" id="inremark"></td>';
     //有效期10
     newTr.insertCell().innerHTML= '<td ><input type="text" style="width:100%; text-align:center;" class="unitsDate" name="yxdate" id="yxdate'+tdindex+'"></td>';
     //批号数量11
     newTr.insertCell().innerHTML= '<td ><span id="phnum'+tdindex+'" class="phnum"></span></td>';//科室商品批号数量;
   	 //批号12
     newTr.insertCell().innerHTML= '<td ><select type="text" style="width:100%; text-align:center;height: 20px;" onchange="selectAllPh(this,'+tdindex+')" name="ph'+tdindex+'" id="ph'+tdindex+'" class="ph"></select></td>';
     //商品主键13
 	 newTr.insertCell().innerHTML= '<td ><span id="goodsuuid'+tdindex+'">' + $("#goodsuuid"+index).html()+ '</span></td>';
 	 //新增明细14
 	 newTr.insertCell().innerHTML= '<td ><button type="button" disabled="disabled" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
 	 //下标15
 	 newTr.insertCell().innerHTML= '<td ><span id="index'+tdindex+'">'+index+'</span></td>';

 	$("#goodsname"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodscode"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodsnorms"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodsname"+tdindex).parent().attr("title", $("#goodsname"+index).html());
 	$("#goodscode"+tdindex).parent().attr("title", $("#goodscode"+index).html());
 	$("#goodsnorms"+tdindex).parent().attr("title", $("#goodsnorms"+index).html());
 	$("#goodsuuid"+tdindex).parent().css("display","none");
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
 	$("#phnum"+tdindex).html($("#ph"+tdindex+" option:selected").val());
 	$(".unitsDate").datetimepicker({
		language:  'zh-CN',  
		minView:2,
	    autoclose : true,
		format: 'yyyy-mm-dd',
		pickerPosition: "bottom-right"
	});
}
function selectAllPh(obj,tdindex) {
	var phObj=$('#table').find('tbody').find("tr").eq(tdindex-1); //当前行出库批号数量
	var phnum = $(obj).find("option:selected").val(); // 批号数量
	phObj.find(".phnum").html(phnum);
	var quantity=phObj.find("#quantity"+tdindex).val();
	if(quantity){
		checknums("quantity",tdindex);
	}
}
</script>
</html>
