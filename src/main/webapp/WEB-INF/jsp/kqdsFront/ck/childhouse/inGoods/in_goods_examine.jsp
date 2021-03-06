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
<title>入库查询</title>
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
</style>
</head>
<body>
<div id="container">
  
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>

    <div class="tableHd">商品列表</div>
    <div class="tableBox1" id="divkdxm" >
        <table id="dykdxm" class="table-striped table-condensed table-bordered" ></table>
    </div>
    <div class="tableBox">
    	<table style="width: 100%"> 
       		<tr>
 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total">0</lable>条</span></td>
 				<td width="30%"><span style="color:#00A6C0;">数量：<lable id="nums">0</lable></span></td>
 				<td width="30%"><span style="color:#00A6C0;">金额：<lable id="allmoney">0</lable></span></td>
       		</tr> 
       	</table>
    </div>
    
    <div class="tableHd">验收记录</div>
    <div class="tableBox1" id="divshjl" >
        <table id="shjl" class="table-striped table-condensed table-bordered" ></table>
    </div>
     <!--查询条件-->
     	<div class="searchWrap" >
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
            	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="openDayin();" >打印</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="refresh()">查询</a>
                <!-- <a href="javascript:void(0);" class="kqdsSearchBtn" id="editor" onclick="editor()">编辑</a> -->
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
						<label>录单日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv">
		                <label>入库方式</label>
		                <div class="kv-v">
		                    <select  name="intype" id="intype">
		                    	<option value="">请选择</option>
			                    <option value="2">换货入库</option>
			                    <option value="4">其他入库</option>
			                    <option value="9">二次入库</option>
		                    </select>
		                </div>
		            </div>
	           		 <div class="kv" >
						<label>单据编号</label>
						<div class="kv-v">
							  <input type="text" name="incode" id="incode"  class="form-control" />
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/okCopy.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var apppath = apppath();
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = apppath + '/KQDS_Ck_Goods_InAct/goodsInSelectList.act?type=2';
var pageurl1 = '';
var onclickrowOobj2 = "";
var goodsDetail = "";
var nowday;
var isAdmin = "false";
var adminName = "";
var menuid = parent.menuid;
//允许删除仓库出入库记录
var canDelCk = "<%=canDelCk%>";
$(function() {
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    getlist();
    OrderDetail();
    ShjlDetail(); 
    checkIdadmin();
});
function setHeight(){
	var windowHeight=$(window).outerHeight();
	var tableHeight=(windowHeight-$(".searchWrap").outerHeight()-60)/3;//本页面有两个table
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
		    title: '审核',
		    field: ' ',
		    align: 'center',
		    
		    formatter: function(value, row, index) {
		    	var html = '';
		    	
			    html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:green;" onclick="addExamineRemark(\'' + row.seq_id+','+row.incode + '\')">审核</a> </span>';
		        return html;
		    }
		},
		{
            title: 'id',
            field: 'id',
            visible: false
        },
        {
            title: '单据编号',
            field: 'incode',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '入库方式',
            field: 'intype',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '供应商',
            field: 'suppliername',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '附加说明',
            field: 'inremark',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '摘要',
            field: 'zhaiyao',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '入库人',
            field: 'createuser',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '录单时间',
            field: 'createtime',
            align: 'center',
            formatter: function(value, row, index) {
                html = '<span >' + value.substring(0,10) + '</span>';
                return html;
            }
        },
        {
            title: '入库时间',
            field: 'rktime',
            align: 'center',
            formatter: function(value, row, index) {
                html = '<span >' + value.substring(0,10) + '</span>';
                return html;
            }
        },
        {
            title: '进货时间',
            field: 'stocktime',
            align: 'center',
            formatter: function(value, row, index) {
                html = '<span >' + value.substring(0,10) + '</span>';
                return html;
            }
        },
        {
		    title: '操作',
		    field: ' ',
		    align: 'center',
		    
		    formatter: function(value, row, index) {
		    	var html = '';
			    html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="delAll(\'' + row.seq_id + '\')">删除</a> </span>';
		        return html;
		    }
		}, 
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        OrderDetail(onclickrowOobj2.seq_id); 
        ShjlDetail(onclickrowOobj2.seq_id); 
    });
}
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		intype: $('#intype').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        supplier: $('#supplier').val(),
        incode:$('#incode').val(),
        stock_starttime: $('#stock_starttime').val(),
        stock_endtime:$('#stock_endtime').val(),
        check_status:0,
        status:0
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
function OrderDetail(parentid) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="230"></table>');
    var detailurl = apppath + '/KQDS_Ck_Goods_In_DetailAct/inDetailSelectByIncode.act?incode=' + onclickrowOobj2.incode;
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	$("#total").html(data.length);
        	var nums=0,allmoney=0;
	        for(var i=0;i<data.length;i++){
	        	allmoney += Number(data[i].rkmoney);
	        	nums += Number(data[i].innum);
	        }
        	$("#allmoney").html(allmoney.toFixed(3));
        	$("#nums").html(nums);
        	setHeight();
        	/*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox1");
        	//console.log(JSON.stringify(data)+"-----------------dat");
        },
        columns: [
			{
			    title: '操作',
			    field: ' ',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
			    	html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="goodsEdit(\'' + index + '\')">编辑</a> </span>';
			        return html;
			    }
			}, 
	        {
	            title: '所属仓库',
	            field: 'housename',
	            align: 'center',
	            
	            sortable: true,
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
	        },
			{
			    title: '一级类别',
			    field: 'firsttype',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	            	html = '<span class="name" title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
			    title: '二级类别',
			    field: 'goodstypename',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	            	html = '<span class="name" title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
			    title: '商品编号',
			    field: 'goodscode',
			    align: 'center',
			    
			    sortable: true,
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '商品名称',
			    field: 'goodsname',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	                html = '<span class="name" title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
			    title: '入库数量',
			    field: 'innum',
			    align: 'center',
	            formatter:function(value){
	            	if(Number(value)<0){
	            		return '<span class="money" style="color:red;">'+value+'</span>';
	            	}else{
	            		return '<span class="money">'+value+'</span>';
	            	}
	            }
			},
			{
			    title: '入库单价',
			    field: 'inprice',
			    align: 'center',
	            formatter:function(value){
	            	return '<span class="money">'+value+'</span>';
	            }
			},
			{
			    title: '入库金额',
			    field: 'rkmoney',
			    align: 'center',
	            formatter:function(value){
	            	if(Number(value)<0){
	            		return '<span class="money" style="color:red;">'+value+'</span>';
	            	}else{
	            		return '<span class="money">'+value+'</span>';
	            	}
	            }
			},
			{
			    title: '单位',
			    field: 'goodsunit',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '入库时间',
			    field: 'rktime',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '商品规',
			    field: 'goodsnorms',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '入库编号',
			    field: 'incode',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	                html = '<span title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
			    title: '有效期',
			    field: 'yxdate',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	                html = '<span title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
	            title: '批号',
	            field: 'ph',
	            align: 'center',
	            
	            formatter: function(value, row, index) {
	            	if(value){
	            		html = '<span class="" title="' + value + '">' + value + '</span>';
	                    return html;
	            	}
	            }
	        },
	        {
	            title: '注册证号',
	            field: 'zczh',
	            align: 'center',
	            
	            formatter: function(value, row, index) {
	            	if(value){
	            		html = '<span class="" title="' + value + '">' + value + '</span>';
	                    return html;
	            	}
	            }
	        },
	        {
			    title: '供应商',
			    field: 'suppliername',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	                html = '<span title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
			    title: '入库备注',
			    field: 'sqremark',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
			        html = '<span class="" title="' + value + '">' + value + '</span>';
			        return html;
			    }
			}]
    });
}

function ShjlDetail(parentid) {
	$("#divshjl").html('<table id="shjl" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    var detailurl = apppath + '/HUDH_Ck_Goods_In_CheckAct/findGoodsInExamineByInId.act?goodsinid=' + onclickrowOobj2.seq_id;
    $("#shjl").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        	/*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox1");
        },
        columns: [
			{
			title: 'id',
			field: 'id',
			align: 'center',
			visible: false,
			},
			{
   	 			title: '验收日期',
    			field: 'checkdate',
    			align: 'center',
    			formatter: function(value, row, index) {
       			 html = '<span class="name" title="'+value+'">' + value.substring(0,10) + '</span>';
       			 return html;
    			}
			},
			{
    			title: '验收人',
    			field: 'checkusername',
    			align: 'center',
    			formatter:function(value){
    				return '<span>'+value+'</span>';
    			}
			},
	        {
	            title: '外包装有无破损异常',
	            field: 'packing',
	            align: 'center',
	            sortable: true,
	            formatter:function(value){
	            	if(value == 1) {
			    		return '<span class="name" title="通过" style="color:green;">通过</span>';
			    	}else {
			    		return '<span class="name" title="异常" style="color:red;">异常</span>';
			    	}
	            }
	        },
			{
			    title: '合格证',
			    field: 'certificate',
			    align: 'center',
			    formatter: function(value, row, index) {
			    	if(value == 1) {
			    		return '<span class="name" title="通过" style="color:green;">通过</span>';
			    	}else {
			    		return '<span class="name" title="异常" style="color:red;">异常</span>';
			    	}
	            }
			},
			{
			    title: '验收报告',
			    field: 'report',
			    align: 'center',
			    sortable: true,
	            formatter:function(value){
	            	if(value == 1) {
			    		return '<span class="name" title="通过" style="color:green;">通过</span>';
			    	}else {
			    		return '<span class="name" title="异常" style="color:red;">异常</span>';
			    	}
	            }
			},
			{
			    title: '备注',
			    field: 'remark',
			    align: 'center',
			    formatter: function(value, row, index) {
			        html = '<span class="" title="' + value + '">' + value + '</span>';
			        return html;
			    }
			},
			{
			    title: '验收结果',
			    field: 'result',
			    align: 'center',
			    formatter: function(value, row, index) {
			    	if(value == 1) {
			    		return '<span class="name" title="通过" style="color:green;">✔</span>';
			    	}else {
			    		return '<span class="name" title="不通过" style="color:red;">✘</span>';
			    	}
			    }
			}]
    });
}

function openDayin() {
    if (onclickrowOobj2 == "") {
        layer.alert('请选择需要打印的入库单！', {
            
        });
        return false;
    }
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("商品入库单");
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
function goodsEdit(index){
	goodsDetail = $('#dykdxm').bootstrapTable('getData')[index];
	// 弹出打印页面
	layer.open({
		type: 2,
		title: '修改入库明细',
		shadeClose: true, //点击遮罩关闭层
		area : ['90%' , '90%'],
		content: apppath+'/KQDS_Ck_Goods_In_DetailAct/toCkInGoodsDetailEdit.act'
	});
}
//删除为审核通过的信息
function delAll(id){
	var paramOrder = {
			seq_id : onclickrowOobj2.seq_id//入库单主键
	}
	//询问框
	  layer.confirm('确定删除？', {
	    btn: ['删除','放弃'] //按钮
	  }, function(){
		  var url = apppath + '/KQDS_Ck_Goods_InAct/deleteGoodsInById.act';
		    $.axse(url, paramOrder,
		    function(r) {
		        if (r.retState == "0") {
		            layer.alert(r.retMsrg, {
		                  
		                end: function() {
		                	refresh();
		                }
		            });
		        } else{
		        	layer.alert(r.retMsrg, {
			              
			        });
		        }
		    },
		    function() {
		        layer.alert('请求失败', {
		              
		        });
		    });
	  });
 }
 //审核
 function addExamineRemark(even){
		var flag= true;//验证入库商品中必填项单价 和 数量  true是填写  false存在未填写
		var flagDate= true;//验证入库商品中必填项有效期 和 批号  true是填写  false存在未填写
		$('#dykdxm').find('tbody').each(function() {
		       $(this).find('tr').each(function() {
		           $(this).find('td').each(function() {
		               if ($(this).index() == 13) {
		            	   yxdate = $(this).find("span").html();
		            	   if(yxdate==""){
		            		flag= false;
		                  	return false;
		            	   }
		               } else if ($(this).index() == 14) {
		            	 //商品主键
		                   ph = $(this).find("span").html();
		            	 if(ph==""){
		            		flagDate = false;
		                	return false;
		            	 }
		               }
		           });
		       });
		   });
	 var index=even.indexOf(",");
	 var goodsinid=even.substring(0,index);
	 var incode=even.substring(index+1,even.length);
	 if(!adminName) {
		 layer.alert("请联系系统管理员配置仓库审核人员");
		 return;
	 }
	 if(isAdmin == "false") {
		 layer.alert("无权限操作,请联系"+adminName+"审核");
		 return;
	 }else if(isAdmin == "true") {
		 layer.open({
				type: 2,
				title: '入库审核',
				shadeClose: true, //点击遮罩关闭层
				area : ['40%' , '45%'],
				content: apppath+'/HUDH_Ck_Goods_In_CheckAct/toGoodsInCollate.act?goodsinid=' + goodsinid+'&incode='+incode
		});
	 }
 }
 
 //判断当前人员是否是管理员
 function checkIdadmin (){
	 var url = apppath + '/KQDS_Ck_Goods_InAct/checkIsAdmin.act';
	 $.ajax({
			url: url,
			type:"POST",
			dataType:"json",
			success:function(result){
				isAdmin = result.result;
				adminName = result.adminName;
			}
		});
 }
 
 function editor() {
	 if(!onclickrowOobj2) {
		 layer.alert("请选择需要修改的数据！");
		 return;
	 }
	 layer.open({
		type: 2,
		title: '入库编辑',
		shadeClose: true, //点击遮罩关闭层
		area : ['90%' , '95%'],
		content: contextPath + '/KQDS_Ck_Goods_InAct/toCkInGoodsDetail.act?goodsinid=' + onclickrowOobj2.seq_id
	});
 }
</script>
</html>
