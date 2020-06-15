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
<title>批量添加商品</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrapSwitch/bootstrap-switch.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/ck/goodsIn/in_goods.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<style type="text/css">
	.goodsThead{
		height: 30px;
	    line-height: 16px; 
	    background-color: #00a6c0;
	    display: block;
	    color: white;
	    border-top-left-radius: 6px;
	    border-top-right-radius: 6px;
	}
	.goodsThead th{
		/* border:1px solid red; */
	}
	.goodsTable tbody {
		display: block;
		height: 270px;
		overflow-y: scroll;
		border-bottom: 1px solid #ddd;
		border-bottom-left-radius: 6px;
    	border-bottom-right-radius: 6px;
	}
	
	.goodsTable thead, tbody tr {
		display: table;
		width: 100%;
		table-layout: fixed;
	}
	
	.goodsTable thead {
		width: calc( 100% - 2em );
		/* background: #e9f5f7; */
	}
	
	.goodsTable {
		border-width: 1px 1px 1px 1px !important;
		/* border: 1px solid #ccc; */
	}
	
	.goodsTable tbody tr td {
		border-bottom: 1px solid #ccc;
		border-left: 1px solid #ccc;
		word-wrap:break-word;
		padding: 5px 0px;
	}
	.goodsTable thead tr th {
		border-width: 1px 1px 1px 1px !important;
		border-left: 1px solid #ccc;
	}
	.goodsTbody td input[type=text]{
		height: 22px;
    	border-radius: 0px;
    	text-align: center;
	}
	/* 提醒开合按钮 */
	.goodSwitch .bootstrap-switch{
		width: 90px;
    	height: 22px;
	}
	.bootstrap-switch-container{
		height: 22px;
	}
	.bootstrap-switch-container .bootstrap-switch-success{
		padding: 0px 8px;
	}
	.bootstrap-switch-container .bootstrap-switch-label{
		padding: 0px 8px;
		height: 22px;
	}
	.bootstrap-switch-container .bootstrap-switch-info{
		padding: 0px 8px;
	}
	.btns{
		text-align: center;
	}
	.btns #save{
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
	.kv .kv-v select{
		width: 170px;
	}
	/* *号验证 */
	.verify{
		color: red;
		/* margin: 0px 5px 0px 10px; */
	}
	.kv-v{
		/* border:1px solid red; */
	}
	.kv-v label{
		display: inline-block;
	}
	.kv-v select{
		display: inline-block;
		width: 95px;
	}
	.tableBox{
		margin-top: 12px;
	}
</style>
</head>
<body>
<div id="container" onunload="checkLeave()">
    <div class="infoBd">
        <div class="databaseWrap">
	        <div class="biliHistory">
	        	<!-- 单选仓库 -->
	        	<!-- <div class="kv" style="margin: 10px 0px 0px 15px;">
            		<div class="kv-v">
                        <label><font class="verify">*</font>所属仓库：</label>
                        <select class="select2"  name="sshouse" id="sshouse"> </select>
                     </div>
                     <div class="kv-v">
                        <label><font class="verify">*</font>一级类别：</label>
                        <select class="select2"  name="basetype" id="basetype">
                        	<option value="">请选择</option>
                        </select>
                     </div>
                     <div class="kv-v">
                     	<label><font class="verify">*</font>二级类别：</label>
                         <select class="select2"  name="nexttype" id="nexttype">
                       		 <option value="">请选择</option>
                	  	 </select>
                    </div>
                </div> -->
	            <div class="tableHd">商品明细</div>
	            <!-- table表格 -->
	            <table width="100%" style="/* table-layout:fixed; */" id="goodsTable" class="goodsTable" border="0" cellspacing="1" cellpadding="0">
					<thead class="goodsThead">
						<tr>
							<th style="text-align: center; vertical-align: middle; width:60px;"><span>操作</span></th>
							<th style="text-align: center; vertical-align: middle; width:180px;"><span><font class="verify">*</font>所属仓库</span></th>
							<th style="text-align: center; vertical-align: middle; width:180px;"><span><font class="verify">*</font>一级类别</span></th>
							<th style="text-align: center; vertical-align: middle; width:180px;"><span><font class="verify">*</font>二级类别</span></th>
							<th style="text-align: center; vertical-align: middle; width:100px;"><span><font class="verify">*</font>商品编号</span></th>
							<th style="text-align: center; vertical-align: middle; width:200px;"><span><font class="verify">*</font>商品名称</span></th>
							<th style="text-align: center; vertical-align: middle; width:60px;"><span>排序号</span></th>
							<th style="text-align: center; vertical-align: middle; width:120px;"><span><font class="verify">*</font>商品规格</span></th>
							<th style="text-align: center; vertical-align: middle; width:50px;"><span><font class="verify">*</font>单位</span></th>
							<th style="text-align: center; vertical-align: middle; width:100px;"><span>下限值</span></th>
							<th style="text-align: center; vertical-align: middle; width:120px;"><span>报警提醒</span></th>
							<th style="text-align: center; vertical-align: middle; width:100px;"><span>上限值</span></th>
							<th style="text-align: center; vertical-align: middle; width:120px;"><span>报警提醒</span></th>
							<!-- <th style="text-align: center; vertical-align: middle; width:120px;"><span>库位</span></th> -->
							<th style="text-align: center; vertical-align: middle; width:150px;"><span>备注</span></th>
							<th style="text-align: center; vertical-align: middle; width:50px;"><span>新增</span></th> 
						</tr>
					</thead>
					<tbody class="goodsTbody"></tbody>
				</table> 
	            
             <div class="tableBox">
		    	<table style="width: 100%"> 
		       			<tr>
		 				<td width="30%"><span style="padding-left:10px;color:#00A6C0;">共有记录<lable id="total1">0</lable>条</span></td>
		 				<!-- <td width="30%"><span style="color:#00A6C0;">数量小计：<lable id="innumall">0</lable></span></td>
		 				<td width="30%"><span style="color:#00A6C0;">金额小计：<lable id="rkmoneyall">0</lable></span></td> -->
		       		</tr> 
		       	</table>
   			 </div>
   			 <div class="btns">
   			 	<button id="save" onclick="save();">保存</button>
   			 </div>
	    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapSwitch/bootstrap-switch.min.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>  --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>   <!-- 原仓库相关js -->
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/keyin.js"></script> --%> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var tdindex = 0;
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var nowday;
$(function() {
	addDoodsDetail("");//初始化追加
	
    $('[name="mingj"]').bootstrapSwitch({  
	    onText:"开启",  
	    offText:"关闭",  
	    onColor:"success",  
	    offColor:"info",  
	    size:"normal",  
	    onSwitchChange:function(event,state){  
	        if(state==true){  
	            $(this).val("1");  
	        }else{  
	            $(this).val("0");  
	        }  
	    }  
	})
	$('[name="maxgj"]').bootstrapSwitch({  
	    onText:"开启",  
	    offText:"关闭",  
	    onColor:"success",  
	    offColor:"info",  
	    size:"normal",  
	    onSwitchChange:function(event,state){  
	        if(state==true){  
	            $(this).val("1");  
	        }else{  
	            $(this).val("0");  
	        }  
	    }  
	})
    
    $(".unitsDate2").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    /*页面大小改变时 计算并设置table的高度 */
    $(window).resize(function(){
    	resizeTableHeight();
    });
    /*内容与表头一起滚动  */
    $(".fixedBodyDiv").scroll(function(){
    	$(".fixedHeaderDiv").scrollLeft($(this).scrollLeft());
    });
});
/* 所属仓库 */
$("#sshouse").change(function(){
	$(".goodsTbody").html(""); //清空追加
});
/* 一级类别 */
$("#basetype").change(function(){
	$(".goodsTbody").html(""); //清空追加
});
/* 计算 设置table数据表的高度 */
function resizeTableHeight(){
	var tableHeight=0;
    tableHeight=$(window).height()-$(".formBox").outerHeight()-62;
    $(".fixedBodyDiv").outerHeight(tableHeight);
    /* 出现自动出现滚动条时 会导致表头内容对不齐  现改为 开始就出现滚动条  */
    $(".fixedHeaderDiv table").outerWidth($(".fixedHeaderDiv").outerWidth()-15);/* 表头留出右边15px的滚动条出现距离 */
}

$('#sshouse').change(function() {
    if ($(this).val()) { // 如果一级有值，再请求
    	getBaseTypeSelect('basetype', this.value);
    }
});

$('#basetype').change(function() {
    if ($(this).val()) { // 如果一级有值，再请求
        getNextTypeSelect('nexttype', this.value);
    }
});

//从货物列表页面（goosd_house.jsp），调用改方法
var a=0;
var rows=0;
function addDoodsDetail(goodnum){
	 tdindex++;
	 //显示条数
	 /* var total1  = $("#total1").html();
     $("#total1").html(Number(total1)+1); */
	 var tablehtml = "";
	 tablehtml += '<tr>';
	 //操作
	 tablehtml += '<td style="text-align: center; vertical-align: middle;width:60px;"><a href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
	 //所属仓库
	 tablehtml += '<td style="text-align: center; vertical-align: middle;width:180px;"><div class="kv-v"><label><font class="verify">*</font>所属仓库：</label><select class="select2"  name="sshouse" id="sshouse'+tdindex+'" onchange="initBasetype(this);"></select></div></td>';
	 //一级类别
	 tablehtml += '<td style="text-align: center; vertical-align: middle;width:180px;"><div class="kv-v"><label><font class="verify">*</font>一级类别：</label><select class="select2"  name="basetype" id="basetype'+tdindex+'" onchange="initNexttype(this);"><option value="">请选择</option></select></div></td>';
	 //二级类别
	 tablehtml += '<td style="text-align: center; vertical-align: middle;width:180px;"><div class="kv-v"><label><font class="verify">*</font>二级类别：</label><select class="select2"  name="nexttype" id="nexttype'+tdindex+'" onchange="setgoodNum(this);"><option value="">请选择</option></select></div></td>';
	 //商品编号
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:100px"><input type="text" id="goodscode'+tdindex+'" name="goodscode" readonly="readonly" style="width:90px;" value="'+goodnum+'"/></td>';
	 //商品名称
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:200px"><input type="text" name="goodsname" id="goodsname'+tdindex+'" style="width: 185px;"></td>';
	 //排序号
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:60px"><input type="text" name="sortno" id="sortno'+tdindex+'" style="width: 50px;"></td>';
	 //商品规格
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:120px"><input type="text" name="goodsnorms" id="goodsnorms'+tdindex+'" style="width: 110px;"></td>';
	 //单位
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:50px"><input type="text" name="goodsunit" id="goodsunit'+tdindex+'" style="width: 40px;"></td>';
	 //下限值
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:100px"><input type="text" disabled name="minnums" id="minnums'+tdindex+'" style="width: 90px;"></td>';
	 //报警提醒
	 tablehtml += '<td class="goodSwitch" style="text-align: center; vertical-align: middle; width:120px"><input id="mingj'+tdindex+'" name="mingj" id="goodsname" type="checkbox" /></td>';
	 //上限值
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:100px"><input type="text" disabled name="maxnums" id="maxnums'+tdindex+'" style="width: 90px;"></td>';
	 //报警提醒
	 tablehtml += '<td class="goodSwitch" style="text-align: center; vertical-align: middle; width:120px"><input id="maxgj'+tdindex+'" name="maxgj" type="checkbox" /></td>';
	 //库位
	 /* tablehtml += '<td style="text-align: center; vertical-align: middle; width:120px"><input type="text" name="kuwei" id="kuwei'+tdindex+'" style="width: 110px;"></td>'; */
	 //备注
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:150px"><input type="text" name="remark" id="remark'+tdindex+'" style="width: 140px;"></td>';
	 //新增
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:50px"><button type="button" id="add'+tdindex+'" onclick="addDeatilNewRow('+tdindex+')">+</button></td>';
     tablehtml += '</tr>';
     $('.goodsTbody').append(tablehtml);
     getHouseSelect("sshouse"+tdindex); //初始化仓库
     $('[name="mingj"]').bootstrapSwitch({  
  	    onText:"开启",  
  	    offText:"关闭",  
  	    onColor:"success",  
  	    offColor:"info",  
  	    size:"normal",  
  	    onSwitchChange:function(event,state){  
  	        if(state==true){  
  	            $(this).val("1");  
  	          	$(this).parents("tr").find('[name="minnums"]').removeAttr("disabled");
  	        }else{  
  	            $(this).val("0");  
  	          	$(this).parents("tr").find('[name="minnums"]').attr("disabled","disabled");
  	        }  
  	    }  
  	});
  	$('[name="maxgj"]').bootstrapSwitch({  
  	    onText:"开启",  
  	    offText:"关闭",  
  	    onColor:"success",  
  	    offColor:"info",  
  	    size:"normal",  
  	    onSwitchChange:function(event,state){ 
  	        if(state==true){  
  	            $(this).val("1"); 
  	          	$(this).parents("tr").find('[name="maxnums"]').removeAttr("disabled");
  	        }else{  
  	            $(this).val("0"); 
  	         	$(this).parents("tr").find('[name="maxnums"]').attr("disabled","disabled");
  	        }  
  	    }  
  	});
  	$("#total1").text($(".goodsTbody").find("tr").length); //共有记录
}

/* 仓库change事件 */
function initBasetype(thi){
	$(thi).parents("tr").find("input[name='goodscode']").val("");  //清空商品编号
	var lastSshouseId=$(thi).attr("lastSshouse"); //上一次选中仓库Id
	var nowSshouseId=$(thi).val();//当前选中仓库
	$(thi).attr("lastSshouse",nowSshouseId);
	var thisIndex=$(thi).attr("id").substring(7);
	if ($(thi).val()) { // 如果一级有值，再请求
    	getBaseTypeSelect('basetype'+thisIndex, $(thi).val()); //初始化一级类别
    }else{
    	$(thi).parents("tr").find("select[name='basetype']").html("<option value=''>请选择</option>");  //清空一级类别
    	$(thi).parents("tr").find("select[name='nexttype']").html("<option value=''>请选择</option>");  //清空二级类别
    	$(thi).parents("tr").find("input[name='goodscode']").val("");  //清空商品编号
    }
	
	//更换仓库
	var alreadySshouse=[];
	$(".goodsTbody").find("tr").each(function(i,obj){
		if($(this).find("select[name='sshouse']").val()==lastSshouseId){
			alreadySshouse.push(i); //保存已经存在的相同仓库
		}
	});
	
	if(lastSshouseId){
		var goodNum=getNumber(lastSshouseId); //自动生成的商品编号
		var goodChar=goodNum.substring(0,2); //编号仓库英文
		var goodNumber=parseInt(goodNum.substring(2)); //编号数字
		for (var i=0;i<alreadySshouse.length;i++) {
			var setGoodnum="";
			if(goodNumber<10){
				setGoodnum=goodChar+"000"+goodNumber;
			}else if(goodNumber<100){
				setGoodnum=goodChar+"00"+goodNumber;
			}else if(goodNumber<1000){
				setGoodnum=goodChar+"0"+goodNumber;
			}else{
				setGoodnum=goodChar+goodNumber;
			}
			$(".goodsTbody").find("tr").eq(alreadySshouse[i]).find("input[name='goodscode']").val(setGoodnum);
			goodNumber++;
		}
	}
}
//初始化二级类别
function initNexttype(thi){
	var thisIndex=$(thi).attr("id").substring(8);
	getBaseTypeSelect('nexttype'+thisIndex, $(thi).val()); //初始化二级类别
	if ($(thi).val()) { // 如果一级有值，再请求
    	getBaseTypeSelect('nexttype'+thisIndex, $(thi).val()); //初始化二级类别
    }else{
    	$(thi).parents("tr").find("select[name='nexttype']").html("<option value=''>请选择</option>");  //清空二级类别
    	//$(thi).parents("tr").find("input[name='goodscode']").val("");  //清空商品编号
    } 
}
//商品编号赋值
function setgoodNum(thi){
	var sshouseId=$(thi).parents("tr").find("select[name='sshouse']").val(); // 当前选中的仓库id
	/* if(!$(thi).val()){
    	$(thi).parents("tr").find("input[name='goodscode']").val("");  //清空商品编号
    } */
	var alreadySshouse=[];
	$(".goodsTbody").find("tr").each(function(i,obj){
		if($(this).find("select[name='sshouse']").val() && $(this).find("select[name='sshouse']").val()==sshouseId){
			alreadySshouse.push(i); //保存已经存在的相同仓库
		}
	});
	var goodNum=getNumber(sshouseId); //自动生成的商品编号
	var goodChar=goodNum.substring(0,2); //编号仓库英文
	var goodNumber=parseInt(goodNum.substring(2)); //编号数字
	for (var i=0;i<alreadySshouse.length;i++) {
		var setGoodnum="";
		if(goodNumber<10){
			setGoodnum=goodChar+"000"+goodNumber;
		}else if(goodNumber<100){
			setGoodnum=goodChar+"00"+goodNumber;
		}else if(goodNumber<1000){
			setGoodnum=goodChar+"0"+goodNumber;
		}else{
			setGoodnum=goodChar+goodNumber;
		}
		$(".goodsTbody").find("tr").eq(alreadySshouse[i]).find("input[name='goodscode']").val(setGoodnum);
		goodNumber++;
	}
}

//是否选择类别
$("#nexttype").change(function(){
	var sshouse=$("#sshouse").val();
	var goodNum=getNumber(sshouse); //自动生成的商品编号
	if($(this).val()){
		$(".goodsTbody").html("");
		addDoodsDetail(goodNum);//初始化追加
	}else{
		$(".goodsTbody").html("");
		$("#total1").text(0); //共有记录
	}
});

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
	$("#total1").text($(".goodsTbody").find("tr").length); //共有记录
	setgoodNum(obj); //商品编号重新赋值
	//全部删除默认添加新的一行
	if($(".goodsTbody").find("tr").length==0){
		addDoodsDetail("");
	}
}

/* "+" 号按钮 */
function addDeatilNewRow(index){
	 var rowCount=$(".goodsTbody").find("tr").length;//一共添加的行数
	 var lastIndex=$(".goodsTbody").find("tr").eq(rowCount-1).find("td").eq(4).find("input").attr("id").substring(9);//最后一行下标
	 tdindex=parseInt(lastIndex)+1;
	 var tablehtml = "";
	 tablehtml += '<tr>';
	 //操作
	 tablehtml += '<td style="text-align: center; vertical-align: middle;width:60px;"><a href="javascript:void(0);" onclick="deltr(this)"><span style="color:red;">删除</span></a></td>';
	 //所属仓库
	 tablehtml += '<td style="text-align: center; vertical-align: middle;width:180px;"><div class="kv-v"><label><font class="verify">*</font>所属仓库：</label><select class="select2"  name="sshouse" id="sshouse'+tdindex+'" onchange="initBasetype(this);"></select></div></td>';
	 //一级类别
	 tablehtml += '<td style="text-align: center; vertical-align: middle;width:180px;"><div class="kv-v"><label><font class="verify">*</font>一级类别：</label><select class="select2"  name="basetype" id="basetype'+tdindex+'" onchange="initNexttype(this);"><option value="">请选择</option></select></div></td>';
	 //二级类别
	 tablehtml += '<td style="text-align: center; vertical-align: middle;width:180px;"><div class="kv-v"><label><font class="verify">*</font>二级类别：</label><select class="select2"  name="nexttype" id="nexttype'+tdindex+'" onchange="setgoodNum(this);"><option value="">请选择</option></select></div></td>';
	 //商品编号
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:100px"><input type="text" id="goodscode'+tdindex+'" name="goodscode" readonly="readonly" style="width:90px;" value=""/></td>';
	 //商品名称
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:200px"><input type="text" name="goodsname" id="goodsname'+tdindex+'" style="width: 185px;"></td>';
	 //排序号
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:60px"><input type="text" name="sortno" id="sortno'+tdindex+'" style="width: 50px;"></td>';
	 //商品规格
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:120px"><input type="text" name="goodsnorms" id="goodsnorms'+tdindex+'" style="width: 110px;"></td>';
	 //单位
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:50px"><input type="text" name="goodsunit" id="goodsunit'+tdindex+'" style="width: 40px;"></td>';
	 //下限值
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:100px"><input type="text" disabled name="minnums" id="minnums'+tdindex+'" style="width: 90px;"></td>';
	 //报警提醒
	 tablehtml += '<td class="goodSwitch" style="text-align: center; vertical-align: middle; width:120px"><input id="mingj'+tdindex+'" name="mingj" id="goodsname" type="checkbox" /></td>';
	 //上限值
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:100px"><input type="text" disabled name="maxnums" id="maxnums'+tdindex+'" style="width: 90px;"></td>';
	 //报警提醒
	 tablehtml += '<td class="goodSwitch" style="text-align: center; vertical-align: middle; width:120px"><input id="maxgj'+tdindex+'" name="maxgj" type="checkbox" /></td>';
	 //库位
	 /* tablehtml += '<td style="text-align: center; vertical-align: middle; width:120px"><input type="text" name="kuwei" id="kuwei'+tdindex+'" style="width: 110px;"></td>'; */
	 //备注
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:150px"><input type="text" name="remark" id="remark'+tdindex+'" style="width: 140px;"></td>';
	 //新增
	 tablehtml += '<td style="text-align: center; vertical-align: middle; width:50px"><button type="button" id="add'+tdindex+'" onclick="addDeatilNewRow('+tdindex+')">+</button></td>';
     tablehtml += '</tr>';
     $('.goodsTbody').append(tablehtml);
     getHouseSelect("sshouse"+tdindex); //初始化仓库
     $('[name="mingj"]').bootstrapSwitch({  
   	    onText:"开启",  
   	    offText:"关闭",  
   	    onColor:"success",  
   	    offColor:"info",  
   	    size:"normal",  
   	    onSwitchChange:function(event,state){  
   	        if(state==true){  
   	            $(this).val("1");  
   	          	$(this).parents("tr").find('[name="minnums"]').removeAttr("disabled");
   	        }else{  
   	            $(this).val("0");  
   	          	$(this).parents("tr").find('[name="minnums"]').attr("disabled","disabled");
   	        }  
   	    }  
   	});
   	$('[name="maxgj"]').bootstrapSwitch({  
   	    onText:"开启",  
   	    offText:"关闭",  
   	    onColor:"success",  
   	    offColor:"info",  
   	    size:"normal",  
   	    onSwitchChange:function(event,state){ 
   	        if(state==true){  
   	            $(this).val("1"); 
   	          	$(this).parents("tr").find('[name="maxnums"]').removeAttr("disabled");
   	        }else{  
   	            $(this).val("0"); 
   	         	$(this).parents("tr").find('[name="maxnums"]').attr("disabled","disabled");
   	        }  
   	    }  
   	});
 	$("#total1").text($(".goodsTbody").find("tr").length); //共有记录
}

function getNumber(id){
	var autoGoodNum="";
	$.ajax({
	    url:contextPath + '/KQDS_Ck_GoodsAct/getNumber.act',    //请求的url地址
	    dataType:"json",   //返回格式为json
	    //async:true,//请求是否异步，默认为异步，这也是ajax重要特性
	    data:{"id":id},    //参数值
	    type:"GET",   //请求方式
	    async:false,
	    success:function(data){
	        //请求成功时处理
	    	if(data.goodscode){
	    		autoGoodNum=data.goodscode;
	    	}
	    }
	});
	return autoGoodNum;
}

//保存方法
function save() {
	var goodArr=[];//商品数据数组
	var verify=0; //验证
    $(".goodsTbody").find("tr").each(function(i,obj){
    	var goodObj={};//商品对象
    	goodObj.sshouse=$(this).find("td").eq(1).find("select[name='sshouse']").val(); //所属仓库
    	goodObj.goodstypeFirst=$(this).find("td").eq(2).find("select[name='basetype']").val(); //一级类别
    	goodObj.goodstype=$(this).find("td").eq(3).find("select[name='nexttype']").val(); //二级类别
    	goodObj.goodscode=$(this).find("td").eq(4).find("input").val();//商品编号
    	goodObj.goodsname=$(this).find("td").eq(5).find("input").val();//商品名称
    	goodObj.sortno=$(this).find("td").eq(6).find("input").val();//排序号
    	goodObj.goodsnorms=$(this).find("td").eq(7).find("input").val();//商品规格
    	goodObj.goodsunit=$(this).find("td").eq(8).find("input").val();//单位
    	goodObj.minnums=$(this).find("td").eq(9).find("input").val();//下限值
     	goodObj.mingj=$(this).find("td").eq(10).find('input[name="mingj"]').val();//报警提醒
    	goodObj.maxnums=$(this).find("td").eq(11).find("input").val();//上限值
    	goodObj.maxgj=$(this).find("td").eq(12).find('input[name="maxgj"]').val();//报警提醒
    	// goodObj.kuwei=$(this).find("td").eq(10).find("input").val();//库位
    	goodObj.remark=$(this).find("td").eq(13).find("input").val();//备注
    	goodArr.push(goodObj);
    	
    	if(!goodObj.goodsunit){
    		verify=7;
    	}
    	if(!goodObj.goodsnorms){
    		verify=6;
    	}
    	if(!goodObj.goodsname){
    		verify=5;
    	}
    	if(!goodObj.goodscode){
    		verify=4;
    	}
    	
    	if(!goodObj.goodstype){
    		verify=3;
    	}
    	if(!goodObj.goodstypeFirst){
    		verify=2;
    	}
    	if(!goodObj.sshouse){
    		verify=1;
    	}
    	
    });
	if(verify==7){
		layer.alert("商品单位不能为空！");
		return;
	}else if(verify==6){
		layer.alert("商品规格不能为空！");
		return;
	}else if(verify==5){
		layer.alert("商品名称不能为空！");
		return;
	}else if(verify==4){
		layer.alert("商品编号不能为空！");
		return;
	}else if(verify==3){
		layer.alert("二级类别不能为空！");
		return;
	}else if(verify==2){
		layer.alert("一级类别不能为空！");
		return;
	}else if(verify==1){
		layer.alert("仓库不能为空！");
		return;
	}
    var goodArr = JSON.stringify(goodArr);
    //console.log(goodArr+"------------goodArr");
    goodArr = encodeURIComponent(goodArr);
    var param = {
    	goods : goodArr
    };
    var url = contextPath + '/KQDS_Ck_GoodsAct/bathCommodities.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
                    parent.refresh();
                    parent.layer.close(frameindex); //再执行关闭
                }
            });
        } else {
            layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('保存失败' );
    });
}

</script>
</html>