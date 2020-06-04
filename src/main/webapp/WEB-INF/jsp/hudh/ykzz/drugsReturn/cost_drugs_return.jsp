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
<title>患者退药</title>
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
						<label>开单日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv">
		                <label>患者姓名</label>
		                <div class="kv-v">
		                    <input type="text"  name="hzname" id="hzname"/>
		                </div>
	           		</div>
                </div>
            </div>
        </div> 
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>

    <div class="tableHd">药品列表</div>
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
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var apppath = apppath();
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = apppath + '/HUDH_YkzzDrugsInAct/findAllCostOrderReturn.act';
var onclickrowOobj2 = "";
var goodsDetail = "";
var nowday;
var selectBatchnumAll;
var costseqIdArr;

//允许删除仓库出入库记录
var canDelCk = "<%=canDelCk%>";
$(function() {
	initSelectsupplier($("#supplier"));
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
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
            title: 'seq_id',
            field: 'seq_id',
            visible: false
        },
        {
            title: 'costno',
            field: 'costno',
            visible: false
        },
        {
            title: '开单时间',
            field: 'createtime',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '患者姓名',
            field: 'hzname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '应缴金额',
            field: 'shouldmoney',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '实缴金额',
            field: 'actualmoney',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '开单人',
            field: 'createname',
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
            	if(value == '2'){
            		return '<span style="color:green">已结账</span>';
            	}else if(value == '0'){
            		return '<span class="label-danger">未结账</span>';
            	}else {
            		return '<span>费用计划</span>';
            	}
            	
            }
        }
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        OrderDetail(onclickrowOobj2.costno); 
    });
}
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        hzname: $('#hzname').val(),
        issend:1
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

function OrderDetail(costno) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="230"></table>');
    var detailurl = apppath + '/HUDH_YkzzDrugsInAct/findCostOrderDetailByCostno1.act?costno='+costno;
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	$("#total").html(data.length);
        	var nums=0,allmoney=0;
	        for(var i=0;i<data.length;i++){
	        	allmoney += Number(data[i].subtotal);
	        	nums += Number(data[i].num);
	        }
        	$("#allmoney").html(allmoney.toFixed(3));
        	$("#nums").html(nums);
        	setHeight();
        	/*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox1");
        },
        columns: [
			{
			    title: '',
			    field: 'seqId',
			    align: 'center',
			    sortable: true,
			    class:"costseq_id",
			    formatter:function(value){
			    	return '<span style="display:none;" id="costseq_id">'+value+'</span>';
			    }
			},
			{
			    title: '操作',
			    field: '',
			    align: 'center',
			    sortable: true,
				formatter: function(value, row, index) {
			    	var html = '';
				    html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="grantDrugs(this)">退药</a> </span>';
			        return html;
			    }
			},
			{
			    title: '药品化学名',
			    field: 'itemname',
			    align: 'center',
			    sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '退药数量',
			    field: 'num',
			    align: 'center',
			    formatter:function(value, row, index){
	            	//return '<span>'+value+'</span>';
	            	return '<input type="text" style="width:10%; text-align:center;" value='+value+'  id="outnum'+index+'">';
	            }
			},
			{
			    title: '取药数量',
			    field: 'num',
			    align: 'center',
			    formatter:function(value){
	            	return '<span id="getnum">'+value+'</span>';
	            }
			},
			{
			    title: '单价',
			    field: 'unitprice',
			    align: 'center',
	            formatter:function(value){
	            	return '<span class="money">'+value+'</span>';
	            }
			},
			{
			    title: '单位',
			    field: 'unit',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '',
			    field: 'id',
			    align: 'center',
				formatter:function(value){
					return '<span style="display:none;" id="selectIdAll">'+value+'</span>';
					
        		}
			},
			{
			    title: '批号',
			    field: 'batchno',
			    align: 'center',
				formatter:function(value){
					return '<span id="selectBatchnumAll">'+value+'</span>';
					
        		}
			},
			{
			    title: '小计',
			    field: 'subtotal',
			    align: 'center',
			    formatter:function(value){
	            	return '<span class="money">'+value+'</span>';
	            }
			}]
    });
}
function grantDrugs(event){
	var costseqIdArr=$(event).parent().parent().prev().children().html();
	var getnum=$(event).parent().parent().next().next().next().children().html();
	var outnum = $(event).parent().parent().next().next().children().val();
	var selectIdAll=$(event).parent().parent().next().next().next().next().next().next().children().html();
	var selectBatchnumAll=$(event).parent().parent().next().next().next().next().next().next().next().children().html();
	//var getnum=$("#getnum").html();
    //var outnum = $("#outnum").val();
	//var selectIdAll=$("#selectIdAll").html();
	//var selectBatchnumAll=$("#selectBatchnumAll").html();
	//var costseqIdArr=$("#costseq_id").html();
	var paramOrder ={
			outnum:outnum,
			selectBatchnumAll : selectBatchnumAll,
			selectIdAll : selectIdAll,
			costseqIdArr : costseqIdArr
	}
	//验证
	if (outnum == "" || outnum == null|| outnum==0) {
        layer.alert('请填写退药数量!'  );
        return false;
    }
    if (getnum < outnum) {
        layer.alert('退药数量超出范围,请重新填写!'  );
        return false;
    }
    if (outnum<0) {
        layer.alert('退药数量为负数,请重新填写!'  );
        return false;
    }
    if(judgeSignNum(outnum)==false){
		 layer.alert('数量格式不正确,请重新填写！' );
		 return false;
	 }
	//询问框
	  layer.confirm('确定退药？', {
	    btn: ['确定','放弃'] //按钮
	  }, function(){
		  
		  	var url = apppath + '/HUDH_YkzzDrugsInAct/returnDrugs.act';
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

</script>
</html>
