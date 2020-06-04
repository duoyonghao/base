<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String canDelCk = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag18_canDelCk, request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>科室退货</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
	
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
.bootstrap-table .table{text-align: center;}
</style>
</head>
<body>
<div id="container">
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>

    <div class="tableHd">退还物品明细</div>
    <div class="tableBox1" id="divkdxm" >
        <table id="dykdxm" class="table-striped table-condensed table-bordered"></table>
    </div>
    <div class="tableBox">
    	<table style="width: 100%"> 
       		<tr>
 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total">0</lable>条</span></td>
 				<td width="30%"><span style="color:#00A6C0;">数量：<lable id="nums">0</lable></span></td>
 				<!-- <td width="30%"><span style="color:#00A6C0;">金额：<lable id="allmoney">0</lable></span></td> -->
       		</tr> 
       	</table>
    </div>
       <!--查询条件-->
     <div class="searchWrap" >
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="query" onclick="refresh()">查询</a>
                <!-- <a href="javascript:void(0);" class="kqdsSearchBtn" onclick="openDayin();" >今日药单</a> -->
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
						<label>退还日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv">
		                <label>退还科室</label>
		                <div class="kv-v">
		                    <select id="deptpart" name="deptpart"></select>
		                </div>
	           		</div>
					<div class="kv">
		                <label>退还人</label>
		                <div class="kv-v">
		                    <input type="text"  name="hzname" id="hzname"/>
		                </div>
	           		</div>
	           		<div class="kv">
		                <label>退还状态</label>
		                <div class="kv-v">
		                    <select  name="status" id="status">
		                    	<option value="">请选择</option>
		                    	<option value="0" selected>待审核</option>
		                    	<option value="1">通过</option>
		                    	<option value="3">未通过</option>
		                    </select>
		                </div>
		            </div>
                </div>
            </div>
        </div> 
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ksll/ksll.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var apppath = apppath();
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = apppath + '/HUDH_KSllAct/findReplaceMentListNoDelete.act';
var onclickrowOobj2 = "";
var goodsDetail = "";
var nowday;
//允许删除仓库出入库记录
var canDelCk = "<%=canDelCk%>";
var parentId;
var menuid = parent.menuid;
var type;
$(function() {
// 	initDept($("#deptpart"));
 	getSupplierSelectKeshi("deptpart");//搜索初始化
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });

 	if(menuid == 603100){
 		type = '2';
 	}else{
 		type = '1';
 	}
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    getlist();
    //OrderDetail();
    $(window).resize(function() {
//      setHeight();
     setWidth();
     
//     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 95; 
     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 60;
     $(".fixed-table-container").outerHeight(calculateHeight/2);
 });
});
function setHeight(){
	var windowHeight=$(window).outerHeight();
	var tableHeight=(windowHeight-$(".searchWrap").outerHeight()-60)/2;//本页面有两个table
	$("#table").bootstrapTable("resetView",{
		height: tableHeight
	})
	$("#dykdxm").bootstrapTable("resetView",{
		height: tableHeight
	})
}
function getlist() {
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [
		{
    		title: '操作',
    		field: ' ',
    		align: 'center',
    		formatter: function(value, row, index) {
    			var html = '';
    			if(row.status == '0'){
    				html = '<span><a  class="agree" href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;pointer-events:none;" onclick="updateStus(\'' + row.id + '\',1)">同意</a>&nbsp;&nbsp;&nbsp; '+
    				'<a class="noAgree" href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;pointer-events:none;" onclick="updateStus(\'' + row.id + '\',3)">不同意</a> </span>';
    			}else {
    				html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:black;" onclick="">--</a> </span>';
    			}
       		 	return html;
    		}
		},
		{
            title: 'id',
            field: 'id',
            visible: false
        },
        {
            title: '退还科室',
            field: 'deptpartname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '退还人',
            field: 'creatorname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '退还时间',
            field: 'createtime',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '附加信息',
            field: 'remark',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '状态',
            field: 'status',
            align: 'center',
            formatter:function(value){
            	if(value == '0'){
            		return '<span class="label-danger" >待审核</span>';
            	}else if(value == '1'){
            		return '<span style="color:green">通过</span>';
            	}else if(value == '3'){
            		return '<span class="label-danger">未通过</span>';
            	}
            	
            }
        }
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        $('#table').find("tbody").find("tr").eq(index).find(".agree").css("pointer-events","auto");
        $('#table').find("tbody").find("tr").eq(index).find(".noAgree").css("pointer-events","auto")
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        parentId = onclickrowOobj2.id;
        OrderDetail(onclickrowOobj2.id); 
    });
}
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#deptpart").select2("val", " ");
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        deptpart: $('#deptpart').val(),
        hzname: $('#hzname').val(),
        status: $('#status').val(),
        type: type
    };
    return temp;
}
function refresh(){
	//点击查询时，置空入库明细列表
	$('#dykdxm').find('tbody').html("");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function OrderDetail(parentId) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="230"></table>');
    var detailurl = apppath + '/HUDH_KSllAct/findReplaceMentDetailByParentId.act?parentId='+parentId+'&status='+$("#status").val()+'&type=1';
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	$("#total").html(data.length);
        	var nums=0;
        	//allmoney=0;
	        for(var i=0;i<data.length;i++){
	        	/* allmoney += Number(data[i].subtotal); */
	        	nums += Number(data[i].cknums);
	        }
        	/* $("#allmoney").html(allmoney.toFixed(3)); */
        	$("#nums").html(nums);
        	setHeight();
        	/*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox1");
        },
        columns: [
			{
			    title: '操作',
			    field: '',
			    align: 'center',
			    sortable: true,
			    formatter:function(value,item, index){
			    	return '<span>-</span>';
			    }
			},
			{
			    title: '商品编号',
			    field: 'goodscode',
			    align: 'center',
			    sortable: true,
	            formatter:function(value,item, index){
	            	return '<span id="goodscode'+index+'">'+value+'</span>';
	            }
			},
			{
			    title: '商品名称',
			    field: 'goodsname',
			    align: 'center',
			    formatter:function(value,item, index){
	            	return '<span id="goodsname'+index+'">'+value+'</span>';
	            }
			},
			{
			    title: '规格',
			    field: 'goodsnorms',
			    align: 'center',
	            formatter:function(value,item, index){
	            	return '<span class="money" id="goodsnorms'+index+'">'+value+'</span>';
	            }
			},
			{
			    title: '退还数量',
			    field: 'cknums',
			    align: 'center',
			    
	            formatter:function(value,item, index){
	            	return '<span id="cknums'+index+'">'+value+'</span>';
	            }
			},
			{
			    title: '单位',
			    field: 'goodsunit',
			    align: 'center',
			    
	            formatter:function(value,item, index){
	            	return '<span id="goodsunit'+index+'">'+value+'</span>';
	            }
			},
			{
			    title: '退货数量',
			    field: '',
			    align: 'center',
			    
	            formatter:function(value,item, index){
	            	if(item.returnnums){
	            		return '<input type="text" readonly="readonly" style="width:80px;height:20px;line-height:20px;" name="nums'+index+'" id="nums'+index+'" value="'+item.returnnums+'">';
	            	}else{
	            		if(item.outList){
	            			if(item.outList.length<=1){
		            		return '<input type="text" style="width:80px;height:20px;line-height:20px;" onfocus="this.select()" onchange="checknums(\'outnum\','+index+')" value="'+item.cknums+'" name="nums'+index+'" id="nums'+index+'">';
	            			}else{
	            				
				            	return '<input type="text" style="width:80px;height:20px;line-height:20px;" onfocus="this.select()" onchange="checknums(\'outnum\','+index+')" name="nums'+index+'" id="nums'+index+'">';
	            			}
		            	}else{
			            	return '<input type="text" style="width:80px;height:20px;line-height:20px;" onfocus="this.select()" onchange="checknums(\'outnum\','+index+')" name="nums'+index+'" id="nums'+index+'" value="'+item.cknums+'">';
		            	}
	            	}
	            }
			},
			{
			    title: '剩余数量',
			    field: 'outList',
			    align: 'center',
			    
	            formatter:function(value,item, index){
	            	if(item.phnum){
	            		return '<span id="phnum'+index+'">'+item.phnum+'</span>';
	            	}else{
		            	if(value){
		            		return '<span id="phnum'+index+'">'+value[0].phnum+'</span>';
		            	}
	            	}
	            }
			},
			{
			    title: '领料数量',
			    field: 'outList',
			    align: 'center',
			    
	            formatter:function(value,item, index){
	            	if(item.llnum){
	            		return '<span id="llnum'+index+'">'+item.llnum+'</span>';
	            	}else{
		            	if(value){
		            		return '<span id="llnum'+index+'">'+value[0].llnum+'</span>';
		            	}
	            	}
	            }
			},
			{
			    title: '批号/单价/入库编号',
			    field: 'outList',
			    align: 'center',
			    
	            formatter:function(value,item, index){
	            	if(item.replacementph){
	            		var html='<option>' + item.replacementph+'/'+item.outprice+'/'+item.addnumber +'</option>';
	            		return '<select style="width: 250px;" id="ph'+index+'">'+html+'</select>';
	            	}else{
	            		if(value){
		                    var html='<option value='+ value[0].addnumber+'/'+value[0].ph +'/'+value[0].goodscode+ '/'+value[0].price+'  llnum='+value[0].llnum+ ' ph='+value[0].ph+'>' + value[0].ph+'/'+value[0].price+'/'+value[0].addnumber + '</option>';
		            		for (var j = 1; j < value.length; j++) {
			                    var optionStr = value[j];
		            			html+='<option value='+ optionStr.addnumber+'/'+optionStr.ph +'/'+optionStr.goodscode+ '/'+optionStr.price+' llnum='+optionStr.llnum+ ' ph='+optionStr.ph+'>' + optionStr.ph+'/'+optionStr.price+'/'+optionStr.addnumber + '</option>';
		            		}
			            	return '<select style="width: 250px;" onchange="selectAllPh(this,'+index+')" id="ph'+index+'">'+html+'</select>';
		            	}
	            	}
	            }
			},
			{
			    title: '附加说明',
			    field: 'goodsremark',
			    align: 'center',
			    formatter:function(value,item, index){
	            	return '<span class="money" id="goodsremark'+index+'">'+value+'</span>';
	            }
			},
			{
			    title: '新增',
			    field: '',
			    align: 'center',
	            formatter:function(value,item, index){
	            	return'<button type="button" id="add'+index+'" onclick="addDeatil('+index+')">+</button>';
	            }
			},
			{
			    title: '',
			    field: '',
			    align: 'center',
	            formatter:function(value,item, index){
	            	return '<span id="index'+index+'" style="display:none;">'+index+'</span>';
	            }
			},]
    });
}

function openDayin() {
    if (onclickrowOobj2 == "") {
        layer.alert('请选择需要打印的入库单！', {
            
        });
        return false;
    }
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("药品入库单");
    var costurl = "";
    // 默认 a5
    costurl = apppath + '/KQDS_Print_SetAct/toInGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&incode=' + onclickrowOobj2.incode;
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
//库房审批修改数据状态
function updateStus(id,flag){
    //循环获取表格中项目
    var list = [];
    var nums=0;
    $('#dykdxm').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            var paramDetail = {};
            $(this).find('td').each(function() {
                if ($(this).index() == 6) {
                    //退货数量
                    paramDetail.nums = $(this).find("input").val();
                    nums=Number(nums)+Number($(this).find("input").val());
                } else if ($(this).index() == 9) {
 	               	//入库信息
 	               	var addnumber = $(this).find("select").val();
 	               	if(addnumber){
 	               	paramDetail.parameter=addnumber;
 	               	}else{
 	               	paramDetail.parameter="";
 	               	}
                }else if ($(this).index() == 1) {
 	               	//编号
 	               	var goodscode = $(this).find("span").html();
 	               	paramDetail.goodscode=goodscode;
                }else if ($(this).index() == 2) {
 	               	//名称
 	               	var goodsname = $(this).find("span").html();
 	               	paramDetail.goodsname=goodsname;
                }else if ($(this).index() == 3) {
 	               	//规格
 	               	var goodsnorms = $(this).find("span").html();
 	               	paramDetail.goodsnorms=goodsnorms;
                }else if ($(this).index() == 4) {
 	               	//退还数量
 	               	var cknums = $(this).find("span").html();
 	               	paramDetail.cknums=cknums;
                }else if ($(this).index() == 5) {
 	               	//单位
 	               	var goodsunit = $(this).find("span").html();
 	               	paramDetail.goodsunit=goodsunit;
                }else if ($(this).index() == 8) {
 	               	//领料数量
 	               	var llnum = $(this).find("span").html();
 	               	paramDetail.llnum=llnum;
                }else if ($(this).index() == 7) {
 	               	//剩余数量
 	               	var phnum = $(this).find("span").html();
 	               	paramDetail.phnum=phnum;
                }else if ($(this).index() == 10) {
 	               	//附加说明
 	               	var goodsremark = $(this).find("span").html();
 	               	paramDetail.goodsremark=goodsremark;
                }
                paramDetail.type = type;
            });
            list.push(paramDetail);
        });
    });
    var num=Number($("#nums").html());
    if(nums<num){
    	layer.alert('退货数量和退还总数量不符，请查询修改！' );
        return;
    }
    //入库明细相关参数
    var data = JSON.stringify(list);
    data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
	$.axseSubmit(
		apppath + '/HUDH_KSllAct/updateReplacementStatus.act', 
		{id : id,status : flag,parentId : parentId,paramDetail:data,type:type} ,
		function() {},
		function(data) {
			layer.alert(data.retMsrg, {
	            end: function() {
	            	refresh();
	            } 
	        });
	},function(r){
	 	layer.alert('操作失败，请联系管理员');
 	});
}
function selectAllPh(obj,tdindex) {
	$("#outnum"+tdindex).html($(obj).find("option:selected").attr("outnum"));
	$("#createtime"+tdindex).html($(obj).find("option:selected").attr("createtime"));
	var outnum=$(obj).find("option:selected").attr("outnum");
	var nums=$("#nums"+tdindex).val();
	if(Number(nums)>Number(outnum)){
		$("#nums"+tdindex).val(outnum);
	}
}
function addDeatil(index){
	var optionlength =$("#ph"+index).find("option").length;
	if(optionlength<=1){
		$("#add"+index).attr("disabled","disabled");
		return false;
	}
	$("#ph"+index).attr("disabled","disabled"); 
	$("#add"+index).attr("disabled","disabled");
	tdindex = $("#dykdxm").find("tbody").find("tr").length;
	tdindex++;
	 //显示条数
    $("#total").html(tdindex); //共有记录
	 var tb = document.getElementById("dykdxm");
     var newTr;//添加新行，trIndex就是要添加的位置
		$("#dykdxm").find("tbody").find("tr").each(function(i,obj){
			var addid=$(this).find("td").eq(11).find("button").attr("id");
			if(addid==$("#add"+index).attr("id")){
				newTr = tb.insertRow(i+2);
			}
		});
   	 //操作
     newTr.insertCell().innerHTML = '<td class="testTd"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this)" id="'+tdindex+'"><span style="color:red;">删除</span></a></td>';
  	 //商品编号0
     newTr.insertCell().innerHTML = '<td><span id="goodscode'+tdindex+'">'+$("#goodscode"+index).html()+'</span></td>';
     //商品名称1
     newTr.insertCell().innerHTML = '<td><span id="goodsname'+tdindex+'">'+$("#goodsname"+index).html()+'</span></td>';
     //规格2
     newTr.insertCell().innerHTML = '<td><span id="goodsnorms'+tdindex+'">' +$("#goodsnorms"+index).html()+ '</span></td>';
     //退还数量3
     newTr.insertCell().innerHTML = '<td><span id="cknums'+tdindex+'">' + $("#cknums"+index).html()+ '</span></td>';
     //单位4
     newTr.insertCell().innerHTML = '<td><span id="goodsunit'+tdindex+'">' + $("#goodsunit"+index).html() + '</span></td>';
     //退货数量5
     newTr.insertCell().innerHTML = '<td><input type="text" style="width:80px;height:20px;line-height:20px;" onfocus="this.select()" onchange="checknums(\'outnum\','+tdindex+')" name="nums'+tdindex+'" id="nums'+tdindex+'"></td>';
     //剩余数量6  
     newTr.insertCell().innerHTML = '<td><span id="phnum'+tdindex+'"></span></td>';
     //领料数量7  
     newTr.insertCell().innerHTML = '<td><span id="llnum'+tdindex+'"></span></td>';
     //领料时间8
     newTr.insertCell().innerHTML = '<td><span id="addtime'+tdindex+'"></span></td>';
     //批号及单价9
     newTr.insertCell().innerHTML = '<td><select style="width: 250px;" onchange="selectAllPh(this,'+tdindex+')" id="ph'+tdindex+'"></select></td>';
     //附加说明10
     newTr.insertCell().innerHTML = '<td><span id="goodsremark'+tdindex+'">'+$("#goodsremark"+index).html()+'</span></td>';
     //新增明细11
 	 newTr.insertCell().innerHTML ='<td><button type="button" disabled="disabled" id="add'+tdindex+'" onclick="addDeatil('+tdindex+')">+</button></td>';
 	 //下标12
 	 newTr.insertCell().innerHTML ='<td><span id="index'+tdindex+'">'+index+'</span></td>';
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
 	$("#thnums"+tdindex).html($("#ph"+tdindex+" option:selected").attr("llnum"));
 	$("#addtime"+tdindex).html($("#ph"+tdindex+" option:selected").attr("addtime"));
}
//删除行
function deltr(obj) {
	//判断自身下标的数值
    var k = obj.parentNode.parentNode.rowIndex;
    var i=Number($(obj).attr("id"));
    var j=Number($("#index"+i).html());
	if(i>j){
		$("#add"+j).removeAttr("disabled","disabled");
		$("#ph"+j).removeAttr("disabled","disabled"); 
		$("#index"+j).html(j);
		document.getElementById('dykdxm').deleteRow(k);
	    tdindex = $("#dykdxm").find("tbody").find("tr").length;
	    $("#total").html(tdindex); //共有记录
	}else{
	    document.getElementById('dykdxm').deleteRow(k);
	    tdindex = $("#dykdxm").find("tbody").find("tr").length;
	    $("#total").html(tdindex); //共有记录
	}
}
function checknums(id,i){
		//退还数量
		var cknums = $("#cknums"+i).html();
		//剩余数量
		var phnum = $("#phnum"+i).html();
		//退货数量
		var nums = $("#nums"+i).val();
		var goodscode=$("#goodscode"+i).html();
		var ph=$("#ph"+i+" option:selected").attr("ph");
		var j=$("#index"+i).html();
		if(id == "outnum"){
			if(judgeSign(nums)==false){
				 layer.alert('出库数量必须为正整数！', {
			              
			     });
				 $("#nums"+i).val(0);
				 return false;
			}else{
				if(Number(nums)>Number(cknums)){
					 layer.alert('退货数量多于退还数量！', {
				              
				     });
					 $("#nums"+i).val(0);
					 return false;
				}else if(Number(nums)>Number(phnum)){
					if(i==j){
						layer.confirm("退货数量多于批号剩余数量,是否添加新的批号信息？",{
							   btn: ['确认', '取消'],
							   closeBtn:0
							  }, function (index) {
								  $("#nums"+i).val(phnum);
								  addDeatil(i);
								  layer.close(index);
							  }, function(index){
								  $("#nums"+i).val(phnum);
								  layer.close(index);
						});
					}else{
						layer.alert('退货数量多于批号剩余数量！', {
				              
					     });
						 $("#nums"+i).val(phnum);
						 return false;
					}
				}
			}
		}
		var n=0;
		$("#dykdxm").find("tbody").find("tr").each(function(i,obj){
			var goodscodes=$(this).find("td").eq(1).text();
			var phs=$(this).find("td").eq(10).find("option:selected").attr("ph");
			if(goodscodes==goodscode&&ph==phs){
				n= n+ Number($(this).find("td").eq(6).find("input").val());
			}
		});
		if(n>cknums){
			layer.alert('当前商品退货数量多于退还数量，请查询后填写！', {
	              
		     });
			 $("#nums"+i).val(0);
			 return false;
		}
}
</script>
</html>
