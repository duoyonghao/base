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
<title>患者领药</title>
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
	           		<div class="kv">
		                <label>状态</label>
		                <div class="kv-v">
		                    <select  name="status" id="status" value="2">
		                    	<option value="">请选择</option>
		                    	<option value="0">未结账</option>
		                    	<option value="2" selected>已结账</option>
		                    </select>
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
var pageurl = apppath + '/HUDH_YkzzDrugsInAct/findAllCostOrder.act';
var onclickrowOobj2 = "";
var goodsDetail = "";
var nowday;
var costno1;
var selectBatchnumAll;
var selectIdAll;
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

function getDrugsno(){
	var url = contextPath + "/HUDH_YkzzDrugsInAct/findCostOrderDetailByCostno.act"
	var param = {costno:costno1};
	$.axseSubmit(url, param, function() {}, function(r) {
		//alert(JSON.stringify(r));
		//console.log(r);
		var drugsNo = r.itemno;
		//alert(drugsNo);
	}, function() {
		layer.alert("查询错误！");
	});
}

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
        onClickCell: function(field, value, row, td) {
            // 2019/7/16 lutian 只有单击操作单元格才调用发药方法
            if(field=="caozuo"){
            	if(row.status=='2'){
            		grantDrugs(row.costno);
            	}else{
            		if(row.status=='0'){
            			layer.alert('此条记录未结账，无法发药！', {
        		              
        		        });
            		}else{
            			layer.alert('此条记录为费用计划，无法发药！', {
      		              
        		        });
            		}
            	}
            }
  		},
        columns: [{
		    title: '操作',
		    field: 'caozuo',
		    align: 'center',
		    
		    formatter: function(value, row, index) {
		    	var html = '';
		    	html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;">发药</a> </span>';
			    /* html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="grantDrugs(\'' + row.costno + '\')">发药</a> </span>';  */
		        return html;
		    }
		},
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
        costno1 = onclickrowOobj2.costno;
        OrderDetail(onclickrowOobj2.costno);
        //getDrugsno();//点击选中行获取药品编号
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
        status:$('#status').val(),
        issend:0
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
    var detailurl = apppath + '/HUDH_YkzzDrugsInAct/findCostOrderDetailByCostno.act?costno='+costno;
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
        	
        	/* 得到批号select上选中的值以及数据库中的Id */
            $(".batchnumselect").find("select").each(function(i,obj){
				$(this).change(function(){
					var batchnumAndId = batchnumselect();
					selectBatchnumAll = batchnumAndId.batchnumselectValjoin;
					selectIdAll = batchnumAndId.batchnumselectIdjoin;
					//console.log(batchnumAndId.batchnumselectValjoin);
					//console.log(batchnumAndId.batchnumselectIdjoin);
				});
			});
        	/* 费用明细ID数组 */
            costseqIdArr=[];
			for (var i = 0; i < $(".costseq_id").length; i++) {
				$(".costseq_id").eq(i).css("display","none");
				if($(".costseq_id").eq(i).find("span").text()){
					costseqIdArr.push($(".costseq_id").eq(i).find("span").text());
				}
			}
			//console.log(costseqIdArr);
        },
        columns: [
			{
			    title: '费用明细ID',
			    field: 'seq_id',
			    align: 'center',
			    sortable: true,
			    class:"costseq_id",
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
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
			    title: '数量',
			    field: 'num',
			    align: 'center',
			    formatter:function(value){
	            	return '<span>'+value+'</span>';
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
			    title: '明细表id',
			    field: 'id',
			    align: 'center',
			    visible: false,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '批号',
			    field: 'batchnum',
			    align: 'center',
			    class:"batchnumselect",
			    formatter:function(value,row,index){
			    	var selecthtml;
			  		var selectid=row.id;
					var medicineNumArr=value.split(",");
					var selectidArr=selectid.split(",");
					selectidArr.pop();
					medicineNumArr.pop();
//					console.log(selectidArr);
//					console.log(medicineNumArr);
					selecthtml='<select id="batchnum'+index+'" name="" onchange="selectgetOptionId(this,'+index+')"><option value="">请选择</option>';
					  		for (var i = 0; i < medicineNumArr.length; i++) {
					  			selecthtml+='<option value="'+selectidArr[i]+'">'+medicineNumArr[i]+'</option>';
					  		}
	            	   		
	            	selecthtml+='</select>';
					return selecthtml;
            	}
			},
			{
			    title: '批号数量',
			    field: 'batchnoNum',
			    align: 'center',
			    formatter:function(value,row,index){
	            	return '<input readonly="readonly" class="money" id="batchnoNum'+index+'" style="text-align: center;" value=""/>';
	            }
			},
			{
			    title: '小计',
			    field: 'subtotal',
			    align: 'center',
			    formatter:function(value){
	            	return '<span class="money">'+value+'</span>';
	            }
			},
			{
			    title: '库存数量',
			    field: 'drug_total_num',
			    align: 'center',
			    formatter:function(value){
	            	return '<span class="">'+value+'</span>';
	            }
			}]
    });
}

/* 根据批号id查出批号数量 */
function selectgetOptionId(obj,index){
	var optionSelectedId=$(obj).find("option:selected").attr("value");
	/* console.log(optionSelectedId+"-----------得到ID"); */
	var url = apppath + '/HUDH_YkzzDrugsInAct/findYkzzDrugsInDatailByInDetail.act';
	var paramOrder = {
		id : optionSelectedId
	};
	$.axse(url, paramOrder,
	    function(r) {
		/* console.log(r); */
	        if (r.retState == "0") {
	        	$("#batchnoNum"+index).val(r.data.batchnoNum);
	            /* layer.alert(r.retMsrg, {
	                  
	                end: function() {
	                	refresh();
	                }
	            }); */
	        } else{
	        	layer.alert(r.retMsrg, {
		              
		        });
	        }
	    },
	    function() {
	        layer.alert('请求失败', {
	              
	        });
	    });
}

//得到所有选中的批号以及数据库中Id方法
function batchnumselect(){
	var batchnumselectValjoin=[];
	var batchnumselectIdjoin=[];
	var batchnumselectObj=$(".batchnumselect").find("select");
	for (var i = 0; i < batchnumselectObj.length; i++) {
		batchnumselectValjoin.push(batchnumselectObj.eq(i).find("option:selected").text());
		batchnumselectIdjoin.push(batchnumselectObj.eq(i).val());
	}
	return {batchnumselectValjoin,batchnumselectIdjoin};
};

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
function grantDrugs(costno){
	var paramOrder ={
			costno : costno,//费用号
			selectBatchnumAll : selectBatchnumAll,
			selectIdAll : selectIdAll,
			costseqIdArr : costseqIdArr
	}
	//询问框
	  layer.confirm('确定发药？', {
	    btn: ['确定','放弃'] //按钮
	  }, function(){
		  var url = apppath + '/HUDH_YkzzDrugsInAct/grantDrugs.act';
		    $.axse(url, paramOrder,
		    function(r) {
		        if (r.retState == "0") {
		            layer.alert(r.retMsrg, {
		                  
		                end: function() {
		                	selectBatchnumAll='';
		                	selectIdAll='';
		                	costseqIdArr='';
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
