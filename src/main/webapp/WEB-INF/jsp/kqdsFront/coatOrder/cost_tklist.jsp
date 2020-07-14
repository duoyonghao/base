<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>	
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
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
<title>退款列表</title><!-- 接待中心  下方退款按钮进入  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<style>
.clear2{
	background:none;
	margin:0;
}
.position-bottom{
	border-top:1px solid #117CCA;	
	margin-top:10px;
	padding-top:10px;
	position:absolute;
	padding-bottom: 0;
	left:0;
	bottom:0;
}
.jiuzhen_register-content{
	padding-bottom:0;
	margin-top:0;
}
#divkdxm{/* 退款明细的table 与table的高度同时调整    这里加高度 是因为 不生成table时 是没有高度的 */
	
}
.filterWrap{
	padding-bottom:8px;
}
.filterWrap .aBtn{
	float:right;
    font-size: 13px;
    padding: 0 15px;
   	margin: 0 5px;
}
label{
	margin-bottom:0;
}
.filterWrap{
	padding:5px 0;
}
.fixed-table-container thead th .sortable{
	padding-right:8px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
html,body{
	height:100%;
}
</style>
</head>
<body>
		<div class="jiuzhen_register-content" style="width: 98%;height:100%;position:relative;">
		<div class="filterWrap">
			
			<div class="kv searchKv">
				<label>所属门诊</label>
	            <div class="kv-v" style="float:left;margin:0 10px 0 10px;">
					<select id="organization" name="organization"></select>
	       		</div>
				<label>模糊查询</label>
	            <div class="kv-v" style="float:left;margin:0 10px 0 10px;">
					<input style="width:120px;" type="text" id="searchValue" name="searchValue">
	       		 </div>
	       		 
	       		 <label style="float:left;margin:0 10px 0 0;">退款状态</label>
	             <div class="kv-v">
					<select style="width:120px;" id="status">
						<option value="">请选择</option>
						<option value="1">申请中</option>
						<option value="2">已同意</option>
						<option value="3">未同意</option>
						<option value="4">已退款</option>
					</select>
	       		 </div>
	             <div class="kv-v">
	             <span style="margin:0 10px 0 10px;">申请时间</span>
	                 <span class="unitsDate">
	                     <input type="text" style="width:120px;" placeholder="开始日期" id="starttime" readonly>
	                     <span>到</span>
	                     <input type="text" style="width:120px;" placeholder="结束日期" id="endtime" readonly>
	                 </span>
	             </div>
	            <div style="float:right;">
		            <a href="javascript:void(0);" id="exportTable" class="kqdsCommonBtn" onclick="exportTable();" style="margin-left:20px;">生成报表</a>
					<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
					<a id="searchHzda" href="javascript:void(0);" class="kqdsSearchBtn">查 询</a>
	            </div>
			</div>
			<div class="kv">
				
			</div>
		</div>
		 <h1	class="table-title">退款清单</h1>
		 <div class="tableBox">
			    <table id="table" class="table-striped table-condensed table-bordered" data-height="180"></table>
         </div>
         <h1 class="table-title">退款明细</h1>
         <div class="tableBox" id="divkdxm">
			    <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="170"></table>
         </div>
         <div class="position-bottom" id="bottomBarDdiv">
         	<div class="clear2"></div>
         	
         </div>
	</div> 
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var pageurl = '<%=contextPath%>/KQDS_RefundAct/selectWithNopage.act';
var onclickrowOobj = "";
var isClick = true;

$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
    //1、表格初始化
    $("#tkezSpan").hide();
    //当前日期
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    getButtonPower();
    getlist();
    OrderDetail();
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "buttom-right"
    });
});
$('#clean').on('click',
function() {
    $(".filterWrap :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
$('#searchHzda').on('click',
function() {
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
    $('#dykdxm').html("");
});


function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        searchValue: $('#searchValue').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        organization: $('#organization').val(),
        tkstatus: $('#status').val()
    };
    return temp;
}
function getlist() {
    $("#table").bootstrapTable({
        url: pageurl,
        queryParams: queryParams,
        dataType: "json",
        onLoadSuccess:function(data){
			//解除查询按钮禁用 lutian
			if(data){
				$("#searchHzda").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","auto").css("pointer-events","auto");
				$("#searchHzda").text("查询");
			}
        	setHeight();
        },
        columns: [{
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '退款状态',
            field: 'status',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var html = "";
                if (value == 1) {
                    html = '<span style="color:red">申请中</span>';
                } else if (value == 2) {
                    html = '<span style="color:red">已同意</span>';
                } else if (value == 3) {
                    html = '<span style="color:red">未同意</span>';
                } else if (value == 4) {
                    html = '<span style="color:green">已退款</span>';
                }
                return html;
            }
        },
        {
            title: '退款总额',
            field: 'tkze',
            align: 'center',
            formatter: function(value, row, index) {
                if (Number(value) != 0) {
                    return '<span style="color:red">-' + value + '</span>';
                } else {
                    return '<span>'+value+'</span>';
                }
            }
        },
        {
            title: '申请时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = value.substring(5);
                return '<span>'+html+'</span>';
            }
        },
        {
            title: '申请人',
            field: 'createusername',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '应收金额',
            field: 'shouldmoney',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '欠费金额',
            field: 'arrearmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (Number(value) != 0) {
                    return '<span style="color:red">' + value + '</span>';
                } else {
                    return value;
                }
            }
        },
        {
            title: '缴费金额',
            field: 'actualmoney',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '赠送金额',
            field: 'zsmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                //var zs = searchOrderZs(row.costno);
                return '<span>'+value+'</span>';
            }
        },
        {
            title: '实收金额',
            field: 'ssmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                //var ss = Number(row.actualmoney) - Number(searchOrderZs(row.costno));
                return '<span>'+ value + '</span>';
            }
        }
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $("#tkezSpan").show();
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        OrderDetail(onclickrowOobj.seqId); // 
    });
}
function OrderDetail(refundid) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered"></table>');
    var detailurl = '<%=contextPath%>/KQDS_Refund_DetailAct/selectWithNopage.act?refundid=' + refundid;

    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) {
        	if(onclickrowOobj.status != 4){
        		$('#dykdxm').bootstrapTable('hideColumn', 'payxj');
        		$('#dykdxm').bootstrapTable('hideColumn', 'paybank');
        		$('#dykdxm').bootstrapTable('hideColumn', 'payyb');
        		$('#dykdxm').bootstrapTable('hideColumn', 'payother2');
        		$('#dykdxm').bootstrapTable('hideColumn', 'paywx');
        		$('#dykdxm').bootstrapTable('hideColumn', 'payzfb');
        		$('#dykdxm').bootstrapTable('hideColumn', 'paymmd');
        		$('#dykdxm').bootstrapTable('hideColumn', 'paybdfq');
        		$('#dykdxm').bootstrapTable('hideColumn', 'paydjq');
        		$('#dykdxm').bootstrapTable('hideColumn', 'payintegral');
        		$('#dykdxm').bootstrapTable('hideColumn', 'payother1');
        	}
        	setHeight();
        	//付款方式赋值
	        getFkfsField('dykdxm');
        },
        columns: [
		{
		    field: ' ',
		    checkbox: true
		},          
        {
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var html = '<span class="time" title="' + value + '">';
                if (row.y2 < 0) {
                    html += '<span class="label label-warning">还款</span>';
                } else if (row.y2 > 0) {
                    html += '<span class="label label-danger">欠款</span>';
                } else if (Number(row.y2) == 0 && row.isqfreal == 1) {
                    html += '<span class="label label-warning">还款</span>';
                }
                html += value + '</span>';
                return html;
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>'+row.unitprice+'</span>';
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
            title: '折扣',
            field: 'discount',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span>'+row.subtotal+'</span>';
            }
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span>'+row.voidmoney+'</span>';
            }
        },
        {title: '应收金额',field: 'ys',align: 'center',sortable: true,
			formatter:function(value,row,index){
				var ys = Number(row.subtotal)+Number(row.voidmoney);
				return '<span>'+ys.toFixed(2)+'</span>';
			}
		},
        {
            title: '欠费金额',
            field: 'arrearmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>'+row.arrearmoney+'</span>';
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span>'+row.paymoney+'</span>';
            }
        },
        {
            title: '赠送金额',
            field: 'zsmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                //var zs = secrchZs(row.orderdetailno);
                return '<span>'+value+'</span>';
            }
        },
        {
            title: '实收金额',
            field: 'ssmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                //var ss = (Number(row.paymoney) - Number(secrchZs(row.orderdetailno))).toFixed(2);
                return '<span>'+value+'</span>';
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '退款数量',
            field: 'tknum',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '退款金额',
            field: 'tkmoney',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (Number(value) != 0) {
                    return '<span style="color:red">-' + value + '</span>';
                } else {
                    return '<span>'+value+'</span>';
                }
            }
        },
        {
            title: '现金',
            field: 'payxj',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '银行卡',
            field: 'paybank',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '医保',
            field: 'payyb',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '赠送',
            field: 'payother2',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '微信',
            field: 'paywx',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '支付宝',
            field: 'payzfb',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '么么贷',
            field: 'paymmd',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '百度分期',
            field: 'paybdfq',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '代金券',
            field: 'paydjq',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '积分',
            field: 'payintegral',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '其他',
            field: 'payother1',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '退款原因',
            field: 'remark',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }]
    });
}
function refresh() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
//根据costno查询赠送金额
function searchOrderZs(costno) {
    var zs = 0.0;
    var param = {
        costno: costno
    };
    var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/searchOrderZs.act';
    $.axse(pageurl, param,
    function(r) {
        if (zs != "") {
            zs = r;
        }
    },
    function() {});
    return zs;
}
//根据orderdetail  id查询赠送金额
function secrchZs(seqId) {
    var zs = 0.0;
    var param = {
        seqId: seqId
    };
    var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/selectDetail.act';
    $.axse(pageurl, param,
    function(r) {
        if (zs != "") {
            zs = r.data.payother2;
        }
    },
    function() {});
    return zs;
}
function sqtk() {
    layer.open({
        type: 2,
        title: '申请退款',
        shadeClose: false,
        //点击遮罩关闭层
        area: ['95%', '95%'],
        content: contextPath+'/KQDS_RefundAct/toTkSq.act'
    });
}
function edittk() {
	 if (onclickrowOobj.status == 4) {
	        layer.alert('该退款单已退款，不能修改！' );
	        return false;
	 }
	 if (onclickrowOobj.createuser != "<%=person.getSeqId()%>") {
	        layer.alert('只能修改自己的退款申请！' );
	        return false;
	 }
     layer.open({
        type: 2,
        title: '修改退款',
        shadeClose: false,
        area: ['95%', '95%'],
        content: contextPath+'/KQDS_RefundAct/toTkSqEdit.act?refundId='+onclickrowOobj.seqId
     });
}
function sqsp(status) {
    if (onclickrowOobj.status != 1) {
        layer.alert('请选择申请中的退款记录！' );
        return false;
    }
    var remark = "";
    if (status == 3) { //不同意
        layer.prompt({
            title: '不同意原因',
            formType: 0
        },
        function(pass, index) {
            layer.close(index);
            remark = pass;
            sh(status, remark);
        });
    } else {
    	//同意退款时验证 收费项目的退费总金额是否超过实收总金额
    	/* var list = $("#dykdxm").bootstrapTable('getData');//获取表格的所有内容行  
    	var tkmoney = 0;
    	for(var i=0;i<list.length;i++){
    		var param = {
    		   		 usercode : list[i].usercode,
    		   		 itemno : list[i].itemno,
    		   		 qfbh : list[i].qfbh
    		};
    		tkmoney +=  Number(Yztkmoney(param));
    	}
    	if((Number(onclickrowOobj.tkze) - Number(tkmoney))>0){
    		layer.alert('该收费项目的退款金额大于缴费金额！', {
				  
			});
			return false;
    	} */
        sh(status, remark);
    }

}
function Yztkmoney(param){
	var sszje=0;
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/checkTf.act';
	$.axse(detailurl, param,
			function(data) {
				sszje = Number(data.symoney);
			},
			function() {
				layer.alert('查询出错！', {
					  
				});
	});
	return sszje;
}
function sh(status, remark) {
    var param = {
        seqId: onclickrowOobj.seqId,
        status: status,
        remark: remark
    };
    var pageurl = '<%=contextPath%>/KQDS_RefundAct/editState.act';
    $.axse(pageurl, param,
    function(r) {
        if (r.retState == "0") {
            layer.alert('审核成功'  );
            refresh();
        } else {
            layer.alert(r.retMsrg  );
        }
    },
    function() {
        layer.alert(r.retMsrg );
    });
}
function toqrtk(status){
	 if (onclickrowOobj.status != 2) {
	        layer.alert('请选择已同意的退款记录！' );
	        return false;
	 }
	 layer.open({
	        type: 2,
	        title: '退款处理',
	        shadeClose: false,
	        //点击遮罩关闭层
	        area: ['95%', '70%'],
	        content: contextPath+'/KQDS_RefundAct/toTkSqDetail.act?refundId='+onclickrowOobj.seqId+'&status='+status+'&usercode='+onclickrowOobj.usercode+'&username='+onclickrowOobj.username
	    });
}
function deltk(){
	 if (onclickrowOobj.status == 4) {
	        layer.alert('该退款单已退款，不能删除！' );
	        return false;
	 }
	 if (onclickrowOobj.createuser != "<%=person.getSeqId()%>") {
	        layer.alert('只能删除自己的退款申请！' );
	        return false;
		}
	 //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_RefundAct/deleteObj.act?seqId=' + onclickrowOobj.seqId;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                });
                refresh();
            }else{
            	 layer.alert(data.retMsrg, {
                       
                 });
            	 refresh();
            }
        },
        function() {
            layer.alert('删除失败！'  );
            refresh();
        });
    });
}
function getButtonPower() {
    //根据用户角色获取按钮权限
    var pageurl = '<%=contextPath%>/YZButtonAct/getButtonListByPriv.act';
    $.axse(pageurl, null,
    function(r) {
        if (r.retState == "0") {
            var listbutton = r.retData;
            var menubutton1 = "";
            var bt1 = "";
            var bt2 = "";
            var bt3 = "";
            var bt4 = "";
            var bt5 = "";
            var bt6 = "";
            for (var i = 0; i < listbutton.length; i++) {
                if (listbutton[i].qxName == "tksq") {
                	bt1 = '<a class="kqdsCommonBtn"  onclick="sqtk()">申请退款</a>';
                } else if (listbutton[i].qxName == "modify_tk") {
                	bt2 = '<a class="kqdsCommonBtn"  onclick="edittk()">修改退款</a>';
                } else if (listbutton[i].qxName == "delete_tk") {
                	bt3 = '<a class="kqdsCommonBtn" onclick="deltk()">删除退款</a>';
                } else if (listbutton[i].qxName == "jjtk") {
                	bt4 = '<a class="kqdsCommonBtn"  onclick="sqsp(3)">拒绝申请</a>';
                } else if (listbutton[i].qxName == "qrtk") {
                	bt5 = '<a class="kqdsSearchBtn"  onclick="toqrtk(4)">确认退款</a>';
                } else if (listbutton[i].qxName == "tytk") {
                	bt6 = '<a class="kqdsCommonBtn"  onclick="sqsp(2)">同意申请</a>';
                }
            }
            // 这样写的目的是，按钮有序
            menubutton1 += (bt1 + bt2 + bt3 + bt6 + bt4 + bt5);
            menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" id="dayin" onclick="localPrint()">打印</a>';
            $("#bottomBarDdiv").append(menubutton1);
            
        } else {
            layer.alert('退款失败'  );
        }
    },
    function() {
        layer.alert('退款失败' );
    });
}

function localPrint() {
    var selectedrows = $.map($("#dykdxm").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
    if (selectedrows.length == 0 || selectedrows[0].id == 'dykdxm') {
        layer.alert('请勾选至少一个打印项目' );
    }

    var usercode = selectedrows[0].usercode;
    var seqIds = '';
    for (var i = 0; i < selectedrows.length; i++) {
        seqIds += selectedrows[i].seqId + ',';
    }

    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("项目退费确认单");
    var costurl = "";
    // 默认 a5
    costurl = contextPath + '/KQDS_Print_SetAct/toTkPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&usercode=' + usercode + '&seqIds=' + seqIds + '&cost_organization=<%=ChainUtil.getCurrentOrganization(request) %>';
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
function setHeight(){
	var height=($(window).outerHeight()-170)/2;
	$("#table").bootstrapTable("resetView",{
		height:height
	})
	$("#dykdxm").bootstrapTable("resetView",{
		height:height
	})
}

var loadIndex='';
function download() {
	layer.msg('数据导出中，请等待');
	//loadIndex = layer.load(0, {shade: false});
	isClick = false;
}
function disload() {
	layer.close(loadIndex);
	layer.msg('数据导出完毕');
	isClick = true;
}
//点击导出
function exportTable() {
	if(isClick) {
		isClick = false;
		// console.log("生成报表")
		var fieldArr = [];
		var fieldnameArr = [];
		$('#table thead tr th').each(function() {
			var field = $(this).attr("data-field");
			if (field != "") {
				fieldArr.push(field); //获取字段
				fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
			}
		});
		var param = JsontoUrldata(queryParams());
		var url = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
		download();
		var xhr = new XMLHttpRequest();
		xhr.open('GET', url, true);    // 也可用POST方式
		xhr.responseType = "blob";
		xhr.onload = function () {
			if (this.status === 200) {
				var blob = this.response;
				// if (navigator.msSaveBlob == null) {
				var a = document.createElement('a');
				//var headerName = xhr.getResponseHeader("Content-disposition");
				//var fileName = decodeURIComponent(headerName).substring(20);
				a.download = "项目退款查询";
				a.href = URL.createObjectURL(blob);
				$("body").append(a);    // 修复firefox中无法触发click
				a.click();
				URL.revokeObjectURL(a.href);
				$(a).remove();
				// } else {
				//     navigator.msSaveBlob(blob, "信息查询");
				// }
			}
			disload();
		};
		xhr.send();
	}
}
</script>
</html>
