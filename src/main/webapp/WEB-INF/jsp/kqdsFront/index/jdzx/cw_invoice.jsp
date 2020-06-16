<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<title>Insert title here</title>
<style type="text/css">
	#invoice{
		height: 100%;
		padding-bottom: 70px;
	}
	#invoice>.invoicefather{
		margin-bottom: 5px;
	}
	/* 发票item */
	#invoice>.invoicefather .invoiceItem{
		overflow: hidden;
		border: 1px solid #ddd;
	    margin-bottom: 5px;
	    padding-top: 5px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem{
		display:block;
		float: left;
		margin-bottom: 5px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem>label{
		font-size: 12px;
	    font-weight: normal;
	    margin-left: 15px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem>input{
		/* width: 240px; */
		text-align: center;
		height: 28px;
    	line-height: 28px;
    	padding: 0;
    	/* padding: 0 10px; */
    	border-radius: 3px;
    	border: solid 1px #e5e5e5;
	}
	/* 添加发票按钮 */
	#invoice .invoice_btn{
		display: inline-block;
	    box-sizing: border-box;
	    height: 26px;
	    line-height: 26px;
	    background: #00a6c0;
	    color: #fff;
	    outline: none;
	    padding: 0 15px;
	    border: 1px solid #00a6c0;
	    border-radius: 3px;
	    margin-right: 3px;
	    text-decoration: none;
	    cursor: pointer;
	    text-align: center;
	}
	.btns{
		width: 100%;
		text-align: center;
	    background-color: white;
	    height: 50px;
	    padding-top: 13px;
	    border-top: 1px solid #ddd;
	    position: fixed;
	    left: 0px;
	    bottom: 0px;
	}
	.invoiceItem .retreatText{
		color:red;
		font-size: 13px;
		line-height: 28px;
    	margin-left: 15px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem>.invoiceTime{
		width: 80px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem>.invoiceNum{
		width: 105px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem>.invoiceMoney{
		width: 70px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem>.invoicePerson{
		width: 245px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem>.taxpayerNum{
		width: 180px;
	}
	#invoice>.invoicefather .invoiceItem>.inputItem>.invoiceDetail{
		width: 205px;
	}
	.sequence{
		font-size: 14px;
   	 	line-height: 26px;
   	 	margin-left: 5px;
	}
</style>
</head>
<body>
	 <div id="invoice" style="margin-top: 5px;">
	 	<ul id="invoiceinit" class="invoicefather">
	 		<!-- <li class="invoiceItem">
				<div class="inputItem">
					<label for="">开票时间：</label><input class="invoiceTime" type="text" style="width: 140px;"/>
				</div>
				<div class="inputItem">
					<label for="">票号：</label><input class="invoiceNum" type="text" style="width: 140px;"/>
				</div>
				<div class="inputItem">
					<label for="">开票总金额：</label><input class="invoiceMoney" type="text"/>
				</div>
				<div class="inputItem">
					<label for="">开票人：</label><input class="invoicePerson" type="text"/>
				</div>
				<div class="inputItem">
					<label for="">纳税人识别号：</label><input class="taxpayerNum" type="text"/>
				</div>
				<div class="inputItem">
					<label for="">明细：</label><input class="invoiceDetail" type="text" style="width: 270px;"/>
				</div>
				<div class="inputItem">
	 				<button class="invoice_btn invoice_retreat" style="margin-left: 10px;" onclick="invoiceRetreat(this);">退票</button>
	 				<span class="retreatText">已退</span>
	 			</div>
	 		</li> -->
	 	</ul>
	 	<ul id="invoicelist" class="invoicefather">
	 		<li class="invoiceItem" id="invoiceItemFirst">
	 			<div class="inputItem"><span class="sequence"></span></div>
	 			<div class="inputItem">
	 				<button class="invoice_btn" style="margin-left: 10px;" onclick="invoicedelete(this);">删除</button>
	 			</div>
	 			<div class="inputItem">
	 				<label style="color:red;" for="">*开票时间：</label><input class="invoiceTime" type="text" placeholder="必填"/>
	 			</div>
	 			<div class="inputItem">
	 				<label style="color:red;" for="">*票号：</label><input class="invoiceNum" type="text" placeholder="必填"/>
	 			</div>
	 			<div class="inputItem">
	 				<label style="color:red;" for="">*开票总金额：</label><input class="invoiceMoney" type="text" placeholder="必填"/>
	 			</div>
	 			<div class="inputItem">
	 				<label for="">开票人：</label><input class="invoicePerson" type="text"/>
	 			</div>
	 			<div class="inputItem">
	 				<label for="">纳税人识别号：</label><input class="taxpayerNum" type="text"/>
	 			</div>
	 			<div class="inputItem">
	 				<label for="">明细：</label><input class="invoiceDetail" type="text"/>
	 			</div>
	 		</li>
	 	</ul>
	 	<div class="btns">
	 		<button class="invoice_btn" onclick="invoiceadd();">添加发票</button>
		 	<button class="invoice_btn" onclick="invoicesave();">保存</button>
		 	<button class="invoice_btn" onclick="invoiceupdate();">修改</button>
		 	<button class="invoice_btn" onclick="invoiceRetreatAll();">全部作废</button>
		 	<button class="invoice_btn" onclick="leading();">批量导入</button>
	 	</div>
     </div>
     
     <!-- 追加的布局 -->
 	<div id="appendHtmlStr" style="display:none;">
 		<li class="invoiceItem">
 			<div class="inputItem"><span class="sequence"></span></div>
 			<div class="inputItem">
 				<button class="invoice_btn" style="margin-left: 10px;" onclick="invoicedelete(this);">删除</button>
 			</div>	
 			<div class="inputItem">
 				<label style="color:red;" for="">*开票时间：</label><input class="invoiceTime" type="text" placeholder="必填"/>
 			</div>
 			<div class="inputItem">
 				<label style="color:red;" for="">*票号：</label><input class="invoiceNum" type="text" placeholder="必填"/>
 			</div>
 			<div class="inputItem">
 				<label style="color:red;" for="">*开票总金额：</label><input class="invoiceMoney" type="text" placeholder="必填"/>
 			</div>
 			<div class="inputItem">
 				<label for="">开票人：</label><input class="invoicePerson" type="text"/>
 			</div>
 			<div class="inputItem">
 				<label for="">纳税人识别号：</label><input class="taxpayerNum" type="text"/>
 			</div>
 			<div class="inputItem">
 				<label for="">明细：</label><input class="invoiceDetail" type="text"/>
 			</div>
 		</li>
 	</div>
     
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var selectedrows=window.parent.selectedrows;
var nowday;
var invoiceList=[]; //存储新增发票信息对象数组
var invoiceUpdateList=[]; //存储修改发票信息对象数组
var xgStutas=0;//修改状态判断
var layerStutas=0;//用来判断弹窗
$(function(){
	//发票时间选择
    $(".invoiceTime").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
    $(".invoiceTime").val(nowday);
    invoiceinit();//初始化判断此用户有没有开过发票
    $("#invoiceinit").find("input").each(function(i,ele){
    	$(ele).attr("disabled","disabled");
    });
    //发票加序号
    $("#invoice").find("li").each(function(i,ele){
    	$(ele).find(".sequence").text((i+1)+".");
    });
});
	
// 查询此患者有没有开发票 
function invoiceinit(){
	var url = contextPath + '/HUDH_InvoiceDetailAct/selectInvoiceDetailByUsercode.act';
    var param = {
    		usercode:selectedrows[0].usercode
    };
    $.axseSubmit(url,param,function() {},function(data) {
    	//console.log(JSON.stringify(data)+"--------查询发票返回数据");
    	if(data.length>0){
    		$("#invoiceItemFirst").remove();//如果查询出数据则新增发票先隐藏
    		for (var i = 0; i < data.length; i++) {
    			if(data[i].dishonour==0){
    				var invoiceinitHtml='<li class="invoiceItem invoiceRetreatItem" seqid='+data[i].seqId+'>';
    			}else{
    				var invoiceinitHtml='<li class="invoiceItem" seqid='+data[i].seqId+'>';
    			}
    			invoiceinitHtml+='<div class="inputItem"><span class="sequence"></span></div>';
    			if(data[i].dishonour==0){
					invoiceinitHtml+='<div class="inputItem">';
					invoiceinitHtml+='<button class="invoice_btn invoice_retreat" style="margin-left: 10px;" onclick="invoiceRetreat(this);">作废</button>';
					invoiceinitHtml+='<span class="retreatText" style="display:none;">已作废</span>';
					invoiceinitHtml+='</div>';
				}else if(data[i].dishonour==1){
					invoiceinitHtml+='<div class="inputItem">';
					invoiceinitHtml+='<span class="retreatText">已作废</span>';
					invoiceinitHtml+='</div>';
				}
				invoiceinitHtml+='<div class="inputItem">';
				invoiceinitHtml+='<label style="color:red;" for="">*开票时间：</label><input class="invoiceTime" type="text" placeholder="必填" value='+data[i].invoiceTime+'>';
				invoiceinitHtml+='</div>';
				invoiceinitHtml+='<div class="inputItem">';
				invoiceinitHtml+='<label style="color:red;" for="">*票号：</label><input class="invoiceNum" type="text" placeholder="必填" value='+data[i].dutyParayraph+'>';
				invoiceinitHtml+='</div>';
				invoiceinitHtml+='<div class="inputItem">';
				invoiceinitHtml+='<label style="color:red;" for="">*开票总金额：</label><input class="invoiceMoney" type="text" placeholder="必填" value='+data[i].invoiceValue+'>';
				invoiceinitHtml+='</div>';
				invoiceinitHtml+='<div class="inputItem">';
				invoiceinitHtml+='<label for="">开票人：</label><input class="invoicePerson" type="text"  value='+data[i].drawer+'>';
				invoiceinitHtml+='</div>';
				invoiceinitHtml+='<div class="inputItem">';
				invoiceinitHtml+='<label for="">纳税人识别号：</label><input class="taxpayerNum" type="text"  value='+data[i].taxpayerNumber+'>';
				invoiceinitHtml+='</div>';
				invoiceinitHtml+='<div class="inputItem">';
				invoiceinitHtml+='<label for="">明细：</label><input class="invoiceDetail" type="text" value='+data[i].invoiceDetail+'>';
				invoiceinitHtml+='</div>';
	 			invoiceinitHtml+='</li>';
	 			$("#invoiceinit").append(invoiceinitHtml);
			}
    	}   
    },function(data){
    	//layer.alert("保存失败！");
    });
}

	
// 添加发票按钮事件
function invoiceadd(){
	var liLength=$("#invoice").find("li").length;// 页面中已存在的li长度
    var liMaxIndex=$("#invoice").find("li").eq(length-1).find(".sequence").text();// li最大序号
	$("#appendHtmlStr").find(".sequence").text((Number(liMaxIndex)+1)+".");
	var appendStr=$("#appendHtmlStr").html();
	$("#invoicelist").append(appendStr);
	//发票时间选择
    $(".invoiceTime").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });
    $(".invoiceTime").val(nowday);
};
// 删除发票事件 
function invoicedelete(obj){
	document.getElementById("invoicelist").removeChild(obj.parentNode.parentNode);
}
//内容非空验证
function contentVerify(obj){
	var objValue=obj.value;
	var objTitle=obj.previousSibling.innerText.substring(0,(obj.previousSibling.innerText.length)-1);
	if(!objValue){
		layer.open({
			 title: '提示',
			 content: objTitle+'不能为空!',
			 end:function(){
				 //console.log("验证验证验证");
				 /* obj.focus(); */
			 }
		});
		return;
	}
}
//保存发票
function invoicesave(){
	var verifyIndex=-1;
	$("#invoicelist").find(".invoiceTime").each(function(i,ele){
		if(!$(ele).val()){
			verifyIndex="开票时间不能为空!";
		}
	});
	$("#invoicelist").find(".invoiceNum").each(function(i,ele){
		if(!$(ele).val()){
			verifyIndex="票号不能为空!";
		}
	});
	$("#invoicelist").find(".invoiceMoney").each(function(i,ele){
		var money=Number($(ele).val());
		if(money){
			if(money<0){
				verifyIndex="开票总金额必须大于零!";
			}
		}else{
			verifyIndex="开票总金额不能为空!";
		}
	});
	$("#invoiceinit").find(".invoiceTime").each(function(i,ele){
		if(!$(ele).val()){
			verifyIndex="开票时间不能为空!";
		}
	});
	$("#invoiceinit").find(".invoiceNum").each(function(i,ele){
		if(!$(ele).val()){
			verifyIndex="票号不能为空!";
		}
	});
	$("#invoiceinit").find(".invoiceMoney").each(function(i,ele){
		var money=Number($(ele).val());
		if(money){
			if(money<0){
				verifyIndex="开票总金额必须大于零!";
			}
		}else{
			verifyIndex="开票总金额不能为空!";
		}
	});
	if(verifyIndex!=-1){
		layer.alert(verifyIndex);
		return;
	} 
	//修改请求
	if(xgStutas==1){
		/* 修改参数存储 */
		$("#invoiceinit").find(".invoiceRetreatItem").each(function(i,ele){
			var invoiceUpdeteObj={};
			invoiceUpdeteObj.seqId=$(ele).attr("seqid");//发票seqid
			invoiceUpdeteObj.invoiceTime=$(ele).find(".invoiceTime").val();//开票时间
			invoiceUpdeteObj.dutyParayraph=$(ele).find(".invoiceNum").val();//票号
			invoiceUpdeteObj.invoiceValue=$(ele).find(".invoiceMoney").val();//开票总金额
			invoiceUpdeteObj.drawer=$(ele).find(".invoicePerson").val();//开票人
			invoiceUpdeteObj.taxpayerNumber=$(ele).find(".taxpayerNum").val();//纳税人识别号
			invoiceUpdeteObj.invoiceDetail=$(ele).find(".invoiceDetail").val();//明细
			invoiceUpdateList.push(invoiceUpdeteObj);
		});
		//console.log(JSON.stringify(invoiceUpdateList)+"--------修改参数");
		var url = contextPath + '/HUDH_InvoiceDetailAct/updateInvoiceDetail.act';
		var dataUpdate = JSON.stringify(invoiceUpdateList);
		dataUpdate = encodeURIComponent(dataUpdate); 
	    var param = {
	    		invoiceUpdateList :dataUpdate, //要删除ID
	    		usercode:selectedrows[0].usercode
	    };
	    $.axseSubmit(url,param,function() {},function(data) {
	    	//console.log(data);
	    	if(data){
	    		//console.log("修改发票----------");
	    		layerStutas=1;
	    	}   
	    },function(data){
	    	layer.alert("修改失败！");
	    });
	}
	
	
	/* 新增参数存储 */
	$("#invoicelist").find("li").each(function(i,ele){
		var invoiceObj={};
		invoiceObj.invoiceTime=$(ele).find(".invoiceTime").val();//开票时间
		invoiceObj.dutyParayraph=$(ele).find(".invoiceNum").val();//票号
		invoiceObj.invoiceValue=$(ele).find(".invoiceMoney").val();//开票总金额
		invoiceObj.drawer=$(ele).find(".invoicePerson").val();//开票人
		invoiceObj.taxpayerNumber=$(ele).find(".taxpayerNum").val();//纳税人识别号
		invoiceObj.invoiceDetail=$(ele).find(".invoiceDetail").val();//明细
		invoiceList.push(invoiceObj);
	});
	//console.log(JSON.stringify(invoiceList)+"-----------新增参数");
	//保存请求
	if(invoiceList.length>0){
		var url = contextPath + '/HUDH_InvoiceDetailAct/insertInvoiceDetail.act';
		var data = JSON.stringify(invoiceList);
		   data = encodeURIComponent(data); 
	    var param = {
	    		invoiceList :data, //要删除ID
	    		usercode:selectedrows[0].usercode
	    };
	    $.axseSubmit(url,param,function() {},function(data) {
	    	//console.log(data);
	    	if(data){
	    		layerStutas=1;
	    	}   
	    },function(data){
	    	layer.alert("保存失败！");
	    });
	}
	// 判断弹窗 
	if(layerStutas==1){
		layer.alert("保存成功！",{
			end:function(){
				window.parent.refreshTable(); //带参数刷新父页面table
				var frameindex = parent.layer.getFrameIndex(window.name);
	            parent.layer.close(frameindex); //再执行关闭
			}
		}); 
	}else{
		layer.alert("保存失败！");
	}
	
};
//修改发票
function invoiceupdate(){
	xgStutas=1;//点击修改，改变状态
	$("#invoiceinit").find(".invoiceRetreatItem").find("input").each(function(i,ele){
    	$(ele).removeAttr("disabled");
    });
	//发票时间选择
    $(".invoiceTime").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });
};
// 作废
function invoiceRetreat(obj){
	//console.log($(obj).parents(".invoiceItem").html()+"------父布局");
	//console.log($(obj),"$(obj)")
	var invoiceRetreatObj={};
	invoiceRetreatObj.seqId=$(obj).parents(".invoiceItem").attr("seqid");//发票seqid
	invoiceRetreatObj.invoiceTime=$(obj).parents(".invoiceItem").find(".invoiceTime").val();//开票时间
	invoiceRetreatObj.dutyParayraph=$(obj).parents(".invoiceItem").find(".invoiceNum").val();//票号
	invoiceRetreatObj.invoiceValue=$(obj).parents(".invoiceItem").find(".invoiceMoney").val();//开票总金额
	invoiceRetreatObj.drawer=$(obj).parents(".invoiceItem").find(".invoicePerson").val();//开票人
	invoiceRetreatObj.taxpayerNumber=$(obj).parents(".invoiceItem").find(".taxpayerNum").val();//纳税人识别号
	invoiceRetreatObj.invoiceDetail=$(obj).parents(".invoiceItem").find(".invoiceDetail").val();//明细
	/*var value=0-Number(invoiceRetreatObj.invoiceValue);
	var invoiceRetreatHtml='<li class="invoiceItem" seqid='+invoiceRetreatObj.seqId+'>';
	invoiceRetreatHtml+='<div class="inputItem">';
	invoiceRetreatHtml+='<label for="">开票时间：</label><input class="invoiceTime" type="text" value='+invoiceRetreatObj.invoiceTime+' disabled="disabled" style="width: 140px;">';
	invoiceRetreatHtml+='</div>';
	invoiceRetreatHtml+='<div class="inputItem">';
	invoiceRetreatHtml+='<label for="">票号：</label><input class="invoiceNum" type="text" value='+invoiceRetreatObj.dutyParayraph+' disabled="disabled" style="width: 140px;">';
	invoiceRetreatHtml+='</div>';
	invoiceRetreatHtml+='<div class="inputItem">';
	invoiceRetreatHtml+='<label for="">开票总金额：</label><input class="invoiceMoney" value='+value.toFixed(2)+' type="text" disabled="disabled">';
	invoiceRetreatHtml+='</div>';
	invoiceRetreatHtml+='<div class="inputItem">';
	invoiceRetreatHtml+='<label for="">开票人：</label><input class="invoicePerson" value='+invoiceRetreatObj.drawer+' disabled="disabled" type="text">';
	invoiceRetreatHtml+='</div>';
	invoiceRetreatHtml+='<div class="inputItem">';
	invoiceRetreatHtml+='<label for="">纳税人识别号：</label><input class="taxpayerNum" value='+invoiceRetreatObj.taxpayerNumber+' disabled="disabled" type="text">';
	invoiceRetreatHtml+='</div>';
	invoiceRetreatHtml+='<div class="inputItem">';
	invoiceRetreatHtml+='<label for="">明细：</label><input class="invoiceDetail" value='+invoiceRetreatObj.invoiceDetail+' type="text" disabled="disabled" style="width: 270px;">';
	invoiceRetreatHtml+='</div>';
	invoiceRetreatHtml+='</li>';
	$(obj).parents(".invoiceItem").after(invoiceRetreatHtml); */
	// console.log(JSON.stringify(invoiceRetreatObj)+"---------退票对象");
	var url = contextPath + '/HUDH_InvoiceDetailAct/updateDishonourInvoiceDetail.act';
	var dataRetreatObj = JSON.stringify(invoiceRetreatObj);
	dataRetreatObj = encodeURIComponent(dataRetreatObj); 
    var param = {
    		dataRetreatObj :dataRetreatObj, //要删除ID
    		usercode:selectedrows[0].usercode
    };
    $.axseSubmit(url,param,function() {},function(data) {
    	//console.log(data);
    	if(data){
    		$(obj).parents(".invoiceItem").find(".invoice_retreat").css("display","none");
    		$(obj).parents(".invoiceItem").find(".retreatText").css("display","inline-block");
    		$(obj).parents(".invoiceItem").find("input").attr("disabled","disabled");
    		$(obj).parents(".invoiceItem").removeClass("invoiceRetreatItem");
    		layer.alert("作废成功！");
    	}   
    },function(data){
    	layer.alert("作废失败！");
    });
}

//全部作废
function invoiceRetreatAll(obj) {
	
	layer.confirm('请确定是否全部作废？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
    	var url = contextPath + '/HUDH_InvoiceDetailAct/updateDishonourInvoiceDetailAll.act';
   	 	var param = {
   	    		usercode:selectedrows[0].usercode
   	    };
   	 //console.log(param,"param")
   	 $.axseSubmit(url,param,function() {},function(data) {
   	    	
	    	$(obj).parents(".invoiceItem").find(".invoice_retreat").css("display","none");
	    	$(obj).parents(".invoiceItem").find(".retreatText").css("display","inline-block");
	    	$(obj).parents(".invoiceItem").find("input").attr("disabled","disabled");
	    	$(obj).parents(".invoiceItem").removeClass("invoiceRetreatItem");
	    	layer.alert("作废成功！", {
	                
	            end: function() {
//    	                    if (parent.leftFrame) {
//    	                        parent.leftFrame.refreshNode(static_deptParent);
//    	                    }
   	            window.location.reload();
   	            }
   	        });
//    	 			
   	    },function(data){ 
   	    	layer.alert("作废失败！");
   	    });
    });
	
	
// 	 window.location.reload();
}
function leading(){
	layer.open({
        type: 2,
        title: '导入',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['25%', '250px'],
        content: contextPath +'/HUDH_InvoiceDetailAct/toInvoiceLeading.act'
    });
}
</script>     
</body>
</html>