<!--wl整理  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String deptpart = request.getParameter("deptpart");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>商品出库</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
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
	.kqds_table input[type="text"]{
		
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
			    			<td style="text-align:right;">领用时间：</td>
			    			<td style="text-align:left;"> 
		    					 <input type="text"  class="unitsDate2" name="createtime" id="createtime">
			                </td>
			                <td style="text-align:right;">领用人：</td>
			    			<td style="text-align:left;">
			    				<input type="text" name="creatorId" id="creatorId" class="hide">
								<input type="text" name="creatorName" id="creatorName" readonly="readonly">
							</td>
			    			<td style="text-align:right;"><font color="red">*</font>&nbsp;&nbsp;领用科室：</td>
			    			<td style="text-align:left;"> 
		    					 <select  name="deptpart" id="deptpart"></select>
			                </td>
							<td style="text-align:right;"> 
	           					领料楼层：
	           				</td>
           					<td style="text-align:left;"> 
	           					<select name="floor" id="floor" style="width: 120px">
	           						<option value="">请选择</option>
	           						<option value="1" selected>负一层</option>
	           						<option value="2">二楼</option>
	           					</select>
	           				</td>
<!-- 			                <td style="text-align:right;"><font color="red">*</font>&nbsp;&nbsp;领用仓库：</td> -->
<!-- 			                <td style="text-align:left;">  -->
<!-- 		    					 <select  name="sshouse" id="sshouse"></select> -->
<!-- 			                </td> -->
			                <td style="text-align:right;">附加说明：</td>
			    			<td style="text-align:left;">
								<input type="text" name="inremark" id="inremark" >
							</td>
			    		</tr>
			    	</table>
	        </div>
        </form>
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
										<th style="text-align: center; vertical-align: middle;width:5%"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>仓库</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>库存数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>领用数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>单位</span></th>
										<th style="padding-right:24px; text-align: center; vertical-align: middle;width:10%"><span>出库备注</span></th>
										<th style="padding-right:24px; text-align: center; vertical-align: middle;width:10%"><span>商品单价</span></th>
									</tr>
								</thead>
				            </table>
			            </div>
			            <!-- fixedBodyDiv滚动的内容 -->
			           <div class="fixedBodyDiv">
				            <table id="table" class="table-striped table-condensed table-bordered" style="width:100%;" onkeydown="keyDown(event)">
			            	    <thead style="height:30px; background: #00A6C0 ;color:white;">
									<tr>
										<th style="text-align: center; vertical-align: middle;width:5%"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>仓库</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>一级类别</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>二级类别</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>库存数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>领用数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>出库备注</span></th>
										<th style="text-align: center; vertical-align: middle;width:10%"><span>商品单价</span></th>
									</tr>
								</thead>
								<tbody style="background-color: #F0FFFF;text-align: center; height:345px; overflow-y:auto;"></tbody>
				            </table>
			          </div>
		          </div>
	        </div>
	        <div class="tableBox">
		    	<table style="width: 50%"> 
		       			<tr>
		 				<td width="20%"><span style="color:#00A6C0;">共有记录<lable id="total1">0</lable>条</span></td>
		 				<td width="20%"><span style="color:#00A6C0;">数量小计：<lable id="innumall">0</lable></span></td>
		       		</tr> 
		       	</table>
   			 </div>
	    </div>
	    <div class="buttonGroup">
			<a href="javascript:void(0);" onclick="xzhw()" class="kqdsCommonBtn">选择商品</a>
			<a href="javascript:void(0);" onclick="save()" class="kqdsSearchBtn">保&nbsp;&nbsp;存</a>
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/keyin.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ksll/ksll.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var deptpart = "<%=deptpart %>";
var currentUserId = '<%=person.getSeqId()%>';
var currentUserUser = '<%=person.getUserName()%>';
var tdindex = 0;
var nowday;
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	
    $(".unitsDate2").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    initDept();//初始化领料部门
    initHouse($("#sshouse"));//初始化领料仓库
    nowday = getNowFormatDate();
    $("#createtime").val(nowday);
    $("#creatorId").val(currentUserId);
    $("#creatorName").val(currentUserUser);
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
    //更换仓库，置空商品列表
   /*  $("#sshouse").change(function(){
    	$('#table').find('tbody').html("");
    	$("#total1").html(0);
    }); */
});

/* 计算 设置table数据表的高度 */
function resizeTableHeight(){
	var tableHeight=0;
    tableHeight=$(window).height()-$(".formBox").outerHeight()-17-52-20;
    $(".fixedBodyDiv").outerHeight(tableHeight);
    /* 出现自动出现滚动条时 会导致表头内容对不齐  现改为 开始就出现滚动条  */
    $(".fixedHeaderDiv table").outerWidth($(".fixedHeaderDiv").outerWidth()-15);/* 表头留出右边15px的滚动条出现距离 */
}

//从货物列表页面（goosd_house.jsp），调用改方法
function addDoodsDetail(detail){
 	//console.log(JSON.stringify(detail)+'-----------detail');
	 tdindex++;
	 //显示条数
	 var total1  = $("#total1").html();
//      $("#total1").html(Number(total1)+1);     
 	$("#total1").html(Number(total1)+(detail.length));
 	
// 	 var tablehtml = "";

	 for(var i=0;i<detail.length;i++){

	 var tablehtml = "";
		
	 tdindex=detail[i].irows;
		
	 tablehtml += "<tr style=''>";
	 //操作
     tablehtml += '<td style="width:10%;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
	 //仓库1
	 tablehtml += '<td style="width:10%;"><span  sshouseid='+detail[i].sshouse+'>'+detail[i].sshousename+'</span></td>';
	 //一级类别1+1
     tablehtml += '<td style="width:10%;"><span  firsttypeid='+detail[i].firsttypeid+' >'+detail[i].firsttype+'</span></td>';
     //二级类别2+1
     tablehtml += '<td style="width:10%;"><span  goodstypeid='+detail[i].goodstype+'>'+ detail[i].goodstypename + '</span></td>';
     //商品名称3+1
      tablehtml += '<td style="width:10%;text-align:center;"><span style="">' + detail[i].goodsname + '</span></td>';
     //商品编号4+1
     tablehtml += '<td style="width:10%;text-align:center;"><span>' + detail[i].goodscode + '</span></td>';
     //规格5+1
     tablehtml += '<td style="width:10%;text-align:center;"><span>'+detail[i].goodsnorms+'</span></td>';
     //库存数量6+1
     tablehtml += '<td style="width:10%;"><span id="kcnums'+tdindex+'">'+detail[i].kcnums+'</span></td>';
     //领用数量7+1
     tablehtml += '<td style="width:10%;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'nums\','+tdindex+')" name="nums'+tdindex+'" id="nums'+tdindex+'"></td>';
     //单位8+1
     tablehtml += '<td style="width:10%;"><span>'+detail[i].goodsunit+'</span></td>';
     //出库备注9+1
     tablehtml += '<td style="width:10%;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="goodsremark" id="goodsremark"></td>';
     //商品主键10+1
 	 tablehtml += '<td style="display:none;">' + detail[i].goodsid + '</td>';
 	 //商品单价 11+1
 	 tablehtml += '<td style="width:10%;display:none;"><span>'+detail[i].goodsprice+'</span></td>';
     tablehtml += "</tr>";
     $('#table').find('tbody').append(tablehtml);
	 }
}
//删除行
function deltr(obj) {
    var i = obj.parentNode.parentNode.rowIndex;
    document.getElementById('table').deleteRow(i);
    updatexj();
}
//选择商品
function xzhw() {
// 	var sshouse = $("#sshouse").val();
// 	if(!sshouse) {
// 		layer.alert("请先选择领用仓库");
// 		return;
// 	}
	layer.open({
        type: 2,
        title: '货物列表',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['90%', '450px'],
		content: contextPath + '/HUDH_KSllViewAct/toGoodsHouse.act?type=0&&type1='+$("#floor").val(),
    });
}
function checknums(id,index){
	var kcnums = $("#kcnums"+index).html();
	var nums = $("#nums"+index).val();
	if(id == "nums"){
		if(judgeSign(nums)==false){
			 layer.alert('领用数量必须为正整数！', {
		     });
			 $("#nums"+index).val("");
			 return false;
		}else{
			if(Number(nums)>Number(kcnums)){
				 layer.alert('库存不足！', {
			     });
				 $("#nums"+index).val("");
				 return false;
			}
		}
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
	 var innumall=0;
	 for(var i=1;i<=rows;i++){
		 var innum = $("#nums"+i).val();
		 if(innum != null && innum != ""){
			 innumall += Number(innum);
		 }
	 }
	 $("#innumall").html(Number(innumall));
}
function findMaxIndex(){
	var maxTd = $("#table tbody tr:last").find("input[name^='nums']");
	return maxTd.attr("name").replace("nums","");
}
function save() {
    //验证
    var createtime = $("#createtime").val();
    var deptpart = $("#deptpart").val();
//     var sshouse = $("#sshouse").val();
    if ($("#table tr").length < 2) {
        layer.alert('请选择出库商品' );
        return false;
    }
    if (createtime == "" || createtime == null) {
        layer.alert('请填写领用时间' );
        return false;
    }
//     if (sshouse == "" || sshouse == null) {
//         layer.alert('请填写领用仓库' );
//         return false;
//     }
    if (deptpart == "" || deptpart == null) {
        layer.alert('请选择领用科室' );
        return false;
    }
    //循环获取表格中项目
    var flag= true;//验证出库商品中必填项数量  true是填写  false存在未填写
    var msg;
    var list = [];
    $('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            var paramDetail = {};
            $(this).find('td').each(function() {
                if ($(this).index() == 11) {
                    //商品主键 10
                    paramDetail.goodsdetailId = $(this).text();
                } else if ($(this).index() == 12) {
 	               	//商品单价 11
 	               	var goodPrice = $(this).find("span").html();
                    paramDetail.goodPrice = goodPrice;
                }  else if ($(this).index() == 1) {
 	               	//商品仓库
 	               	//var sshouse = $(this).find("span").html();
 	               	var sshouse = $(this).find("span").attr("sshouseid");
                    paramDetail.house = sshouse;
 	               	//paramDetail.sshouseid = sshouseid;
                } 	else if ($(this).index() == 2) {
 	               	//var basetype = $(this).find("span").html();
 	               	var basetype = $(this).find("span").attr("firsttypeid");
                    paramDetail.basetype = basetype;
                    //paramDetail.basetypeid = basetypeid;
                } else if ($(this).index() == 3) {
	               	//二级类别2
	               	//var nexttype = $(this).find("span").html();
	               	var nexttype = $(this).find("span").attr("goodstypeid");
                    paramDetail.nexttype = nexttype;
                    //paramDetail.nexttypeid = nexttypeid;
                } else if ($(this).index() == 4) {
	               	//商品名称3
	               	var goodsname = $(this).find("span").html();
                    paramDetail.goodsname = goodsname;
                }   else if ($(this).index() == 5) {
	               	//商品编号4
	               	var goodscode = $(this).find("span").html();
                    paramDetail.goodscode = goodscode;
                }  else if ($(this).index() == 6) {
	               	//规格5
	               	var goodsnorms = $(this).find("span").html();
                    paramDetail.goodsnorms = goodsnorms;
                }   else if ($(this).index() == 8) {
 	               	//领用数量
 	               	var nums = $(this).find("input").val();
 	               	if(nums == ""){
 	               		flag = false;
 	               		msg = "领用数量不能为空";
 	               		return false;
 	               	}else{
 						if(judgeSignNum(nums)==false){
 							flag = false;
 							msg = "领用数量应为整数";
 	               			return false;
 	               		}
 	               	} 
                    paramDetail.nums = nums;
                }  else if ($(this).index() == 9) {
	               	//单位
	               	var goodsunit = $(this).find("span").html();
                    paramDetail.goodsunit = goodsunit;
                }  else if ($(this).index() == 10) {
	               	//出库备注
	               	var goodsremark = $(this).find("input").val();
                    paramDetail.goodsremark = goodsremark;
                }
            });
            //console.log("一级类别="+paramDetail.basetype);
            list.push(paramDetail);
        });
    });
    if(!flag){
    	layer.alert(msg, {
        });
        return false;
    }
    //console.log(JSON.stringify(list)+'---------list');
    //return;
    //入库明细相关参数
    var data = JSON.stringify(list);
    data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
    
    var creatorId = $("#creatorId").val();
    var inremark = $("#inremark").val();
    var floor = $("#floor").val();
    var param = {
    	paramDetail:data,
    	createtime: createtime,
    	deptpart: deptpart,
    	creatorId : creatorId,
    	inremark: inremark,
    	floor: floor

    };
//     console.log(JSON.stringify(param)+'---------param');
//     return;
    var url = contextPath + '/HUDH_KSllAct/saveKsllData.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
        if (r.retState == "0") {
        	layer.alert('保存成功', {
                end: function() {
                	parent.layer.close(frameindex);
                	parent.location.reload(); 
                } 
            });
        } else if(r.retState == "100"){ 
    	    layer.alert('保存失败', {
	        });
        }else {
            layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('保存失败' );
    });
}
function gettimestr() {
    var today = new Date();
    var year = today.getFullYear();
    var month = (today.getMonth() + 1) < 10 ? '0' + (today.getMonth() + 1) : (today.getMonth() + 1);
    var day = (today.getDate()) < 10 ? '0' + (today.getDate()) : (today.getDate());
    var hours = (today.getHours()) < 10 ? '0' + (today.getHours()) : (today.getHours());
    var minutes = (today.getMinutes()) < 10 ? '0' + (today.getMinutes()) : (today.getMinutes());
    var seconds = (today.getSeconds()) < 10 ? '0' + (today.getSeconds()) : (today.getSeconds());
    var str = "JH" + year + month + today.getDate() + hours + minutes + seconds;
    return str;

}
/**
 * 初始化领料科室
 */
function initDept(){
	$.ajax({
		url: contextPath + '/HUDH_KSllAct/findCkDept.act',
		type:"POST",
		dataType:"json",
		success:function(result){
			if(result) {
				var html;
				html+='<option value="">请选择</option>';
				for(var index in result) {
					html+='<option value="'+result[index].seq_id+'">'+result[index].deptname+'</option>';
				}
				$("#deptpart").append(html);
				if(deptpart!=null){
				    $("#deptpart").val(deptpart);
				}
			}
		}
	});
}
</script>
</html>