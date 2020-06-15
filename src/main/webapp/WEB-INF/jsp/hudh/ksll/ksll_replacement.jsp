<!--wl整理  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>材料退回</title>
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
			    			<td style="text-align:right;">退还时间：</td>
			    			<td style="text-align:left;"> 
		    					 <input type="text"  class="unitsDate2" name="createtime" id="createtime" readonly="readonly">
			                </td>
			                <td style="text-align:right;">退还人：</td>
			    			<td style="text-align:left;">
			    				<input type="text" name="creatorId" id="creatorId" class="hide">
								<input type="text" name="creatorName" id="creatorName" readonly="readonly">
							</td>
			    			<td style="text-align:right;"><font color="red">*</font>&nbsp;&nbsp;退还科室：</td>
			    			<td style="text-align:left;"> 
		    					 <select  name="deptpart" id="deptpart"></select>
			                </td>
			                </td>
			                <td style="text-align:right;"><font color="red">*</font>&nbsp;&nbsp;退还楼层：</td>
			                <td style="text-align:left;"> 
		    					 <select  name="floor" id="floor">
		    					 	<option value="1">负一层</option>
		    					 	<option value="2">二层</option>
		    					 </select>
			                </td>
			                <td style="text-align:right;"><font color="red">*</font>&nbsp;&nbsp;退还原因：</td>
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
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>操作</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>所属仓库</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>科室库存</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>退还数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>批号数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>批号</span></th>
										<th style="padding-right:24px; text-align: center; vertical-align: middle;width:auto;"><span>退还备注</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto; display:none"><span>商品编号</span></th>
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
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>商品名称</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>规格</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>科室库存</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>退还数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>单位</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>批号数量</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>批号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>退还备注</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto; display:none"><span>商品编号</span></th>
										<th style="text-align: center; vertical-align: middle;width:auto;"><span>新增</span></th>
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
    initDept($("#deptpart"));//初始化领料部门
    
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
    $("#sshouse").change(function(){
    	$('#table').find('tbody').html("");
    	$("#total1").html(0);
    });
  //更换科室，置空商品列表
    $("#deptpart").change(function(){
    	$('#table').find('tbody').html("");
    	$("#total1").html(0);
    });
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
	 tdindex++;
	 //显示条数
	 var total1  = $("#total1").html();
     $("#total1").html(Number(total1)+1);
	 var tablehtml = "";
	 tablehtml += "<tr style=''>";
	 //操作
     tablehtml += '<td style="width:10%;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)" id="'+tdindex+'"><span style="color:red;">删除</span></a></td>';
   	 //所属仓库1
     tablehtml += '<td style="width:10%;text-align:center;"><span id="goodscode'+tdindex+'">' + detail.housename + '</span></td>';
     //商品编号1
     tablehtml += '<td style="width:10%;text-align:center;"><span id="goodscode'+tdindex+'">' + detail.goodscode + '</span></td>';
     //商品名称2
     tablehtml += '<td style="width:10%;text-align:center;"><span id="goodsname'+tdindex+'">' + detail.goodsname + '</span></td>';
     //规格3
     tablehtml += '<td style="width:10%;text-align:center;"><span id="goodsnorms'+tdindex+'">'+detail.goodsnorms+'</span></td>';
     //库存数量4
     tablehtml += '<td style="width:10%;"><span id="kcnums'+tdindex+'">'+detail.kcnums+'</span></td>';
     //领用数量5
     tablehtml += '<td style="width:10%;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'nums\','+tdindex+')" name="nums'+tdindex+'" id="nums'+tdindex+'"></td>';
     //单位6
     tablehtml += '<td style="width:10%;"><span id="goodsunit'+tdindex+'">'+detail.goodsunit+'</span></td>';
   	 //批号数量7
     tablehtml += '<td style="width:10%;"><span class="phnum" id="phnum'+tdindex+'"></span></td>';
     //批号8
     tablehtml += '<td style="width:10%;"><select type="text" style="width:100%; text-align:center;height: 20px;" onchange="selectAllPh(this,'+tdindex+')" name="ph'+tdindex+'" id="ph'+tdindex+'" class="ph"></select></td>';
     //出库备注9
     tablehtml += '<td style="width:10%;"><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="goodsremark" id="goodsremark"></td>';
     //商品Id10
     tablehtml += '<td style="width:10%; display:none"><span class="goodsdetailid" id="goodsdetailid'+tdindex+'">'+detail.goodsdetailid+'</span></td>';
     //新增明细10+1
 	 tablehtml += '<td style="width:120px;"><button type="button" disabled="disabled" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
 	 //下标11+1
 	 tablehtml += '<td style="display:none;"><span id="index'+tdindex+'">'+tdindex+'</span></td>';
    
 	 tablehtml += "</tr>";
     $('#table').find('tbody').append(tablehtml);
     initChukuNum(tdindex,detail.goodscode); // 初始化出库批号
}
//初始化出库批号下拉框  2019/12/06 lutian
function initChukuNum(tdindex,goodscode){
	var phObj=$('#table').find('tbody').find("tr").eq(tdindex-1); //当前行下拉框元素
	var url = contextPath + "/HUDH_KSllAct/selectAllGoodPhByGoodCode.act";
	var param = {
			goodscode : goodscode,
			deptpart:$("#deptpart").val()
	};
    $.axse(url, param,
    function(data) {
        var list = data;
        if (list != null) {
            if (list.length > 0) {
                var phnum;
                var inprice;
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    phObj.find(".ph").append(
    					'<option value='+ optionStr.phnum + '>' + optionStr.ph + '</option>' 
    				);
                    if(j==0){
                    	phnum=optionStr.phnum; // 批号数量
                    }
                }
                $("#phnum"+tdindex).html(phnum);
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
//根据出库批号查询数量
function selectAllPh(obj,tdindex) {
	var phnum = $(obj).find("option:selected").val(); // 批号数量
	$("#phnum"+tdindex).val(phnum);
	var nums=$("#nums"+tdindex).val();
	if(nums){
		checknums("nums",tdindex);
	}
}
//删除行
function deltr(obj) {
	//判断自身下标的数值
    var k = obj.parentNode.parentNode.rowIndex;
    var i=Number($(obj).attr("id"));
    var j=Number($("#index"+i).html());
	if(i<j){
		//删除所有
		var goodscode=$(obj).parent().parent().parent().find("td").eq(2).text();
		$("#table").find("tbody").find("tr").each(function(i,obj){
			var goodscodeAdd=$(this).find("td").eq(2).text();
			if(goodscodeAdd==goodscode){
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
	var deptpart = $("#deptpart").val();
	if(!deptpart) {
		layer.alert("请选择退还科室");
		return;
	}
	/* if(!sshouse) {
		layer.alert("请选择退还仓库");
		return;
	} */
	layer.open({
        type: 2,
        title: '货物列表',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['90%', '450px'],
        content: contextPath + '/HUDH_KSllViewAct/toReplacementHouse.act?deptpart='+deptpart
    });
}
function checknums(id,i){
	var kcnums = $("#kcnums"+i).html();
	var nums = $("#nums"+i).val();
	var ph =$("#ph"+i+" option:selected").text();
	var phlength =$("#ph"+i).find("option").length;
	var phnum = $("#ph"+i).val();
	var goodscode=$("#goodscode"+i).html();
	var n=0;
	$("#table").find("tbody").find("tr").each(function(i,obj){
		var goodscodeAdd=$(this).find("td").eq(2).text();
		if(goodscodeAdd==goodscode){
			n= n+ Number($(this).find("td").eq(5).find("input").val());
		}
	});
	if(n>kcnums){
		layer.alert('当前商品出库数量多于退还数量，请查询后填写！', {
              
	     });
		 $("#nums"+i).val("");
		 return false;
	}
	if(id == "nums"){
		if(judgeSign(nums)==false){
			 layer.alert('退还数量必须为正整数！', {
		     });
			 $("#nums"+i).val("");
			 return false;
		}else{
			if(Number(nums)>Number(kcnums)){
				 layer.alert('库存不足！', {
			     });
				 $("#nums"+i).val("");
				 return false;
			}
			if(Number(nums)>Number(phnum)&&phlength==1){
				 layer.alert('库存不足！', {
			     });
				 $("#nums"+i).val("");
				 return false;
			}
		}
	}
	if(phlength>1&&Number(nums)>Number(phnum)&&phnum!=null){
		var j=$("#index"+i).html();
		if(i==j){
			layer.confirm("当前批号数量不足，是否添加新的批号信息？",{
			   btn: ['确认', '取消'],
			   closeBtn:0
			  }, function (index) {
			   //把当前批号数据锁定
			   $("#nums"+i).val(phnum);
		       	addDeatil(i);
			    updatexj();
				layer.close(index);
			  }, function(index){
				$("#nums"+i).val(phnum);
	        	updatexj();
				layer.close(index);
			  });
		}else{
			layer.alert('出库数量多于批号数量！', {
	              
		     });
			$("#nums"+i).val(phnum);
			 return false;
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
    var sshouse = $("#sshouse").val();
    var inremark = $("#inremark").val();
    var floor = $("#floor").val();
    if ($("#table tr").length < 2) {
        layer.alert('请选择退还商品' );
        return false;
    }
    if (createtime == "" || createtime == null) {
        layer.alert('请填写退还时间' );
        return false;
    }
    /*
     if (sshouse == "" || sshouse == null) {
        layer.alert('请填写退还仓库' );
        return false;
    } */
    if (deptpart == "" || deptpart == null) {
        layer.alert('请选择退还科室' );
        return false;
    }
    if (inremark == "" || inremark == null) {
        layer.alert('请输入退还原因' );
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
                if ($(this).index() == 3) {
	               	//商品名称3
	               	var goodsname = $(this).find("span").html();
                    paramDetail.goodsname = goodsname;
                }   else if ($(this).index() == 2) {
	               	//商品编号4
	               	var goodscode = $(this).find("span").html();
                    paramDetail.goodscode = goodscode;
                }  else if ($(this).index() == 4) {
	               	//规格5
	               	var goodsnorms = $(this).find("span").html();
                    paramDetail.goodsnorms = goodsnorms;
                }   else if ($(this).index() == 6) {
 	               	//领用数量
 	               	var nums = $(this).find("input").val();
 	               	if(nums == ""){
 	               		flag = false;
 	               		msg = "领用数量不能为空";
 	               		return false;
 	               	}else{
 						if(judgeSign(nums)==false){
 							flag = false;
 							msg = "领用数量应为整数";
 	               			return false;
 	               		}
 	               	} 
                    paramDetail.nums = nums;
                } else if ($(this).index() == 7) {
	               	//单位
	               	var goodsunit = $(this).find("span").html();
                    paramDetail.goodsunit = goodsunit;
                } else if ($(this).index() == 10) {
	               	//出库备注
	               	var goodsremark = $(this).find("input").val();
                    paramDetail.goodsremark = goodsremark;
                }else if ($(this).index() == 9) {
	               	//批号
	               	paramDetail.ph = $(this).find("select option:selected").text();
                } else if ($(this).index() == 11) {
	               	//出库备注
	               	var goodsdetailid = $(this).find("input").val();
                    paramDetail.goodsdetailid = goodsdetailid;
                }
                paramDetail.floor = $("#floor").val();
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
    
    var creatorId = $("#creatorId").val();
    var inremark = $("#inremark").val();
    var param = {
    	paramDetail:data,
    	createtime: createtime,
    	deptpart: deptpart,
    	creatorId : creatorId,
    	inremark: inremark,
    	sshouse : sshouse,
    	floor:floor,
    	type : 1 //标识科室退还
    };
    var url = contextPath + '/HUDH_KSllAct/saveReplaceMentData.act';
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
    	    layer.alert('保存失败!!!', {
	        });
        }else {
            layer.alert('保存失败!!'  );
        }
    },
    function() {
        layer.alert('保存失败!' );
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
		var addid=$(this).find("td").eq(10).find("button").attr("id");
		if(addid==$("#add"+index).attr("id")){
			newTr = tb.insertRow(i+2);
		}
	});
	//操作
    newTr.insertCell().innerHTML = '<td ><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)" id="'+tdindex+'"><span style="color:red;">删除</span></a></td>';
    //商品名称1
    newTr.insertCell().innerHTML = '<td ><span id="goodsname'+tdindex+'">' + $("#goodsname"+index).html()+ '</span></td>';
    //商品编号2
    newTr.insertCell().innerHTML = '<td ><span id="goodscode'+tdindex+'">' + $("#goodscode"+index).html()+ '</span></td>';
    //规格3
    newTr.insertCell().innerHTML = '<td ><span id="goodsnorms'+tdindex+'">'+$("#goodsnorms"+index).html()+'</span></td>';
    //库存数量4
    newTr.insertCell().innerHTML = '<td ><span id="kcnums'+tdindex+'">'+$("#kcnums"+index).html()+'</span></td>';
    //领用数量5
    newTr.insertCell().innerHTML = '<td ><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" onchange="checknums(\'nums\','+tdindex+')" name="nums'+tdindex+'" id="nums'+tdindex+'"></td>';
    //单位6
    newTr.insertCell().innerHTML = '<td ><span>'+ $("#goodsunit"+index).html()+'</span></td>';
  	//批号数量7
    newTr.insertCell().innerHTML = '<td ><span class="phnum" id="phnum'+tdindex+'"></span></td>';
    //批号8
    newTr.insertCell().innerHTML = '<td ><select type="text" style="width:100%; text-align:center;height: 20px;" onchange="selectAllPh(this,'+tdindex+')" name="ph'+tdindex+'" id="ph'+tdindex+'" class="ph"></select></td>';
    //出库备注9
    newTr.insertCell().innerHTML = '<td ><input type="text" style="width:100%; text-align:center;" onfocus="this.select()" name="goodsremark" id="goodsremark"></td>';
    //新增明细10
	newTr.insertCell().innerHTML = '<td ><button type="button" disabled="disabled" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
    //下标11
	newTr.insertCell().innerHTML = '<td ><span id="index'+tdindex+'">'+index+'</span></td>';
   	$("#goodsname"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodscode"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodsnorms"+tdindex).parent().css({"white-space":"nowrap","text-overflow":"ellipsis","overflow":"hidden"});
 	$("#goodsname"+tdindex).parent().attr("title", $("#goodsname"+index).html());
 	$("#goodscode"+tdindex).parent().attr("title", $("#goodscode"+index).html());
 	$("#goodsnorms"+tdindex).parent().attr("title", $("#goodsnorms"+index).html());
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
}

</script>
</html>
