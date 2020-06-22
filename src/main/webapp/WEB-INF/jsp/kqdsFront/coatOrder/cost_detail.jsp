<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String costno = request.getParameter("costno");
	if (costno == null) {
		costno = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>费用详情</title><!-- 右侧个人中心  费用详情 tab 进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<style>
.fixed-table-container{
	border-left:none;
}
.recommendedBar{
	margin-top:0px;
	padding:5px 0;
	text-align:left;
	line-height:32px;
}
.table-striped > tbody > tr:nth-child(odd) {
    background: #eef9fe;
}
.titleDiv{
	box-sizing: border-box;
    color: #373737;
    font-family: "Microsoft YaHei";
    height: 30px;
    position: relative;
    margin-bottom: 5px;
    border-bottom:1px solid #ddd;
}
.titleDiv .title{
	font-size: 18px;
    height: 20px;
    line-height: 20px;
    float: left;
    display: block;
    margin-top: 5px;
    
}
#container{
	padding:0 15px;
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
</style>
</head>
<body>
<div id="container">
	<!-- <div class="titleDiv" style="margin-bottom:0;">
		<span class="title">费用详情</span>
	</div>  -->
	<div class="titleDiv">
		<span class="title" id="topyxzl" style="font-size:14px;color:#777;font-weight:bold;"></span>
	</div> 
   <!--  <div class="tableHd" id="topyxzl">费用详情</div>-->
    <div class="tableBox" id="tableFather">
        <table id="table" class="table-striped table-condensed table-bordered" data-height="170"></table>
    </div>
	<div class="titleDiv">
		<span class="title" style="font-size:14px;color:#777;font-weight:bold;">详细项目清单</span>
	</div>
    <!-- <div class="tableHd">详细项目清单</div> -->
    <div class="tableBox" id="divkdxm">
        <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="160"></table>
    </div>
    
    <div class="titleDiv">
		<span class="title"  style="font-size:14px;color:#777;font-weight:bold;">处方单</span>
	</div>
    <!-- <div class="tableHd">处方单</div> -->
    <div class="tableBox" id="chufangDiv">
        <table id="chufang" class="table-striped table-condensed table-bordered" data-height="155"></table>
    </div>
    
    
	<div class="recommendedBar" id="recommendedBarDiv">
	</div>
	<!-- 
	<div style="margin-top: -35px;margin-left: 553px;">
    	<button class="kqdsCommonBtn" id="doctorPick">医生领料</button> 添加医生领料按钮
	</div>
	<div style="margin-top: -26px;margin-left: 643px;">
    	<button class="kqdsCommonBtn" id="doctorSearch">领料查询</button> 添加医生领料按钮
	</div> -->
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = '<%=contextPath%>/KQDS_CostOrderAct/getByRegnoNopage.act';
var personId = "<%=person.getSeqId()%>";
var onclickrowOobj = "";  // 这个对象，是指页面左侧列表中选择的对象，比如接待中心等待结账列表、等待治疗列表
var onclickrowOobj2 = ""; // 这个对象就是当前页面的cost order费用单对象
var canEditCost = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag9_canEditCost, request)%>"; //是否具备修改他人费用单的权限 0不可以 1 可以
var canZheKouOnly = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag17_canZheKouOnly, request)%>"; // 只能修改折扣
var listbutton = window.parent.listbutton; //父页面传值
//alert(listbutton.length);
var menuid = window.parent.menuid;

var costno1 = "";  //获取费用单号
//alert(costno1);
var isdrugs1 = ""; //获取判断药品的标识
//alert(isdrugs1);
//综合查询页面传值
var regno = "";
var isdelreg = 0;
var costno = "<%=costno%>";
var defaultindex = 0;
var static_isyjjitem = 0;
var jzcx_invoice_value_Flag=false;
$(function() {
    //综合页面跳转过来 不需要展示开单按钮
    onclickrowOobj = window.parent.onclickrowOobj;
    //如果选中的不是挂号记录
    if (onclickrowOobj == "" || onclickrowOobj.ifcost == null) {
        regno = onclickrowOobj.regno;
    } else {
        regno = onclickrowOobj.seqId;
        isdelreg = onclickrowOobj.del;
    }
    getButtonPower();
    getlist();
    OrderDetail("null");
    //子页面高度传给父页面
    adjustTable();
    $(window).resize(adjustTable);
    $("#dykdxm  tbody").html("");
    chuFangList();
});


 
function getlist() {
    var url = pageurl + "?usercode=" + onclickrowOobj.usercode+"&access=1";//不需要可见人员过滤，查询全部费用
    /* 根据当前页面 计算出table需要的高度 */
	/*wl----首次加载时 计算table高 */
	 var tableHeight=getTableHeight();/* 计算table需要的高度 */
	$("#tableFather").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	/*wl----首次加载时 计算table高度————————————结束  */
    $("#table").bootstrapTable({
        url: url,
        dataType: "json", 
        onLoadSuccess: function(data) { //加载成功时执行
        	//alert(JSON.stringify(data));
        	//console.log(data);
        	if(defaultindex==0){
        		defaultindex = 1;
        	}
        	$("#table").find("tr:eq("+defaultindex+") td:eq(0)").click();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth("#tableFather");
            var ys=0,qf=0,jf=0;
        	for(var i=0;i<data.length;i++){
        		if(data[i].status == 2 && data[i].isyjjitem == 0){//预交金单不算
        			ys += Number(data[i].shouldmoney);
            		qf += Number(data[i].y2);
            		jf += Number(data[i].actualmoney);
        		}
        	}
        	
        	var topyxzl="应收：" + ys.toFixed(2) + " &nbsp;&nbsp;缴费：" + jf.toFixed(2) + "&nbsp;&nbsp;<i style='font-size:14px;font-style:normal;color:red;font-family: Microsoft YaHei;'> 欠费：" + qf.toFixed(2)+"</i>";
        	$("#topyxzl").html(topyxzl);
        	if(jzcx_invoice_value_Flag){
	        	$.ajax({
	        		url : '<%=contextPath%>/HUDH_InvoiceDetailAct/selectInvoiceDetailValueByUsercode.act',
	    			type : "POST",
	    			dataType : "json",
	    			data : {
	    				usercode:onclickrowOobj.usercode
	    			},
	    			success : function(data) {
	    				var value=(Number(data.invoiceValue)-Number(data.dishonourInvoiceValue)).toFixed(2);
	    				topyxzl="应收：" + ys.toFixed(2) + " &nbsp;&nbsp;缴费：" + jf.toFixed(2) + "&nbsp;&nbsp;<i style='font-size:14px;font-style:normal;color:red;font-family: Microsoft YaHei;'> 欠费：" + qf.toFixed(2)+"&nbsp;&nbsp;总开票："+data.invoiceValue+"&nbsp;&nbsp;开票："+value+"&nbsp;&nbsp;作废："+data.dishonourInvoiceValue+"</i>";
	    				$("#topyxzl").html(topyxzl);
	    			}
	        	});
        	}
        	
        },
        rowStyle: function (row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.costno==costno) {
            	strclass = 'success';//欠费
            	defaultindex = index + 1;
            } 
            if (Number(row.actualmoney) <0 || row.actualmoney.indexOf("-")>=0) {
        		strclass = 'danger';//欠费
        	}else {
        		
        	}
            return { classes: strclass };
        },
        columns: [{
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            
            visible: false,
            switchable: false,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '开单时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span>' + value.substring(0) + '</span>';
                return html;
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '费用类型',
            field: 'type',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (row.status == "0") {
                    return '<span style="color:red">费用计划</span>';
                } else {
                    return '<span style="color:green">确认划价</span>';
                }
            }
        },
        {
            title: '成交情况',
            field: 'cjstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "1") {
                    return '<span style="color:green">已成交</span>';
                } else {
                    return '<span style="color:red">未成交</span>';
                }
            }
        },
        {
            title: '状态',
            field: 'status',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value >= 2) {
                    return '<span style="color:green">已结账</span>';
                } else if (value == 1) {
                    return '<span style="color:red">已开单</span>';
                } else {
                    return '<span style="color:red">未成交</span>';
                }
            }
        },
        {
	        title: '就诊医生',
	        field: 'doctorname',
	        align: 'center',
	        
	        formatter: function(value, row, index) {
	            if (value != "" && value != null) {
	                return "<span class='name'>"+value+"</span>";
	            }else{
	            	return "<span>-</span>";
	            }
	        }
	    },
	    {
	        title: '修复医生',
	        field: 'repairdoctorname',
	        align: 'center',
	        
	        formatter: function(value, row, index) {
	            if (value != "" && value != null) {
	                return "<span class='name'>"+value+"</span>";
	            }else{
	            	return "<span>-</span>";
	            }
	        }
	    },
        {
            title: '合计',
            field: 'totalcost',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span class='money'>"+value+"</span>";
            }
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span class='money'>"+value+"</span>";
            }
        },
        {
            title: '应收金额',
            field: 'shouldmoney',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span class='money'>"+value+"</span>";
            }
        },
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (Number(value) != 0) {
                    return '<span class="money" style="color:red">' + value + '</span>';
                } else {
                    return '<span>'+value+'</span>';
                }
            }
        },
        {
            title: '缴费金额',
            field: 'actualmoney',
            align: 'center',
            
            sortable: true,
            formatter:function(value, row, index){
            	return "<span class='money'>"+(Number(row.actualmoney)-Number(row.payyjj))+"</span>";
            }
        },
        {
            title: '预交金使用',
            field: 'payyjj',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span class='money'>"+value+"</span>";
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '开单人',
            field: 'createusername',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'<span>';
            }
        },
        {
            title: '类型',
            field: 'isback',
            align: 'center',
            formatter: function(value, row, index) {
                if (value == 1) {
                    return '<span style="color:red">退单</span>';
                }else{
                	return '<span>-</span>'
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        OrderDetail(onclickrowOobj2.costno); //"\'"+onclickrowOobj2.costno+"\'"
        costno1 = onclickrowOobj2.costno;   //获取费用单号
        isdrugs1 = onclickrowOobj2.isdrugs;
        // 处方单
        chuFangList(onclickrowOobj2.costno);
    });
}

/******************医生领料********************/

/* $('#ysll').on('click', function() {
      parent.layer.open({
    	  type: 2,
          title: '医生领料',
          shadeClose: false,
          shade: 0.6,
          area: ['90%', '85%'],
          content: contextPath + '/HUDH_Goods_DoctorPickViewAct/toDoctorPick.act?costno1=' + costno1 
      });
  });  */
  
function ysll() {
	if (isdrugs1 == 1) {
		layer.alert("该费用单项目为药品，不能领料！");
	} else {
	    parent.layer.open({
	  	  type: 2,
	        title: '医生领料',
	        shadeClose: false,
	        shade: 0.6,
	        area: ['90%', '85%'],
	        content: contextPath + '/HUDH_Goods_DoctorPickViewAct/toDoctorPick.act?costno1=' + costno1 
	    });
	}  
}  
/******************医生领料********************/
/******************领料查询********************/
  
 /* $('#doctorSearch').on('click', function() {
      parent.layer.open({
    	  type: 2,
          title: '领料查询',
          shadeClose: false,
          shade: 0.6,
          area: ['90%', '85%'],
          content: contextPath + '/HUDH_Goods_DoctorPickViewAct/toPickSearch.act?costno1=' + costno1 
      });
  });  */
 function llcx() {
	    parent.layer.open({
	    	type: 2,
	          title: '领料查询',
	          shadeClose: false,
	          shade: 0.6,
	          area: ['90%', '85%'],
	          content: contextPath + '/HUDH_Goods_DoctorPickViewAct/toPickSearch.act?costno1=' + costno1 
	    });
	}  
/******************领料查询********************/

function OrderDetail(costno) {
	static_isyjjitem = 0;
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno;
    /* 根据当前页面 计算出table需要的高度 */
	/*wl----首次加载时 计算table高 */
	 var tableHeight=getTableHeight();/* 计算table需要的高度 */
	$("#divkdxm").html("<table id='dykdxm' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	
	/*wl----首次加载时 计算table高度————————————结束  */
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(data){
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth("#divkdxm");
        	if(data.length>0){
        		 for (var i = 0; i < data.length; i++) {
        	            var tabledata = data[i];
        	            if(tabledata.isyjjitem == 1){
        	            	static_isyjjitem = 1;
        	            }
        	     }
        	}
        	 $(".unitsDate").datetimepicker({
         		language:  'zh-CN',  
         		minView:0,
         	    autoclose : true,
         		format: 'yyyy-mm-dd hh:ii:ss',
         		pickerPosition: "bottom-right"
         	});
        },
        columns: [
        {
		    title: '治疗状态',
		    field: 'kaifa',
		    valign: 'middle',
		    align: 'center',
		    formatter: function(value, row, index) {
		    	var buttonstr = "<span>未治疗</span>";
		    	if(onclickrowOobj2.status == 2){
		    		if(canEditCost == '1'){
		    			if(row.kaifa == '已治疗'){
                    		buttonstr = '<select style="width:65px;" onchange="changekaifa(\'' + row.seqId + '\',this,\'kaifa\')">'+
      	                    '<option value="未治疗">未治疗</option>'+
      	                    '<option value="已治疗" selected>已治疗</option>'+
      	                    '</select>';
                    	}else{
                    		buttonstr = '<select style="width:65px;" onchange="changekaifa(\'' + row.seqId + '\',this,\'kaifa\')">'+
    	                    '<option value="未治疗" selected>未治疗</option>'+
    	                    '<option value="已治疗">已治疗</option>'+
    	                    '</select>';
                    	}
		    		}else if(row.doctor == personId || isToday(row.zltime)){
		    			if(row.kaifa != '已治疗'){
                    		buttonstr = '<select style="width:65px;" onchange="changekaifa(\'' + row.seqId + '\',this,\'kaifa\')">'+
      	                    '<option value="未治疗" selected>未治疗</option>'+
      	                    '<option value="已治疗">已治疗</option>'+
      	                    '</select>';
                    	}else{
                    		if(isToday(row.zltime)){
                    			buttonstr = '<select style="width:65px;" onchange="changekaifa(\'' + row.seqId + '\',this,\'kaifa\')">'+
          	                    '<option value="未治疗">未治疗</option>'+
          	                    '<option value="已治疗" selected>已治疗</option>'+
          	                    '</select>';
                    		}else{
                    			buttonstr = '<span>已治疗</span>';
                    		}
                    	}
		    		}else{
		    			if(row.kaifa == '已治疗'){
	                    	buttonstr = '<span>已治疗</span>';
	                    }
		    		}
		    	}else{
		    		if(row.kaifa == '已治疗'){
                    	buttonstr = '<span>已治疗</span>';
                    }
		    	}
		        return buttonstr;
		    }
		},{
            title: '操作时间',
            field: 'zltime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	var buttonstr = '<span>'+value+'</span>';
		    	if(onclickrowOobj2.status == 2){
		    		if(canEditCost == '1'){
                    	buttonstr = '<input type="text" value="'+value+'" style="width:135px; text-align:center;" class="unitsDate" onchange="changekaifa(\'' + row.seqId + '\',this,\'zltime\')" >';
		    		}else if(row.doctor == personId){
		    			if(!value){
			    			buttonstr = '<input type="text"  style="width:135px; text-align:center;" class="unitsDate" onchange="changekaifa(\'' + row.seqId + '\',this,\'zltime\')" >';
		    			}else{
		    				if(isToday(row.zltime)){
				    			buttonstr = '<input type="text" value="'+value+'" style="width:135px; text-align:center;" class="unitsDate" onchange="changekaifa(\'' + row.seqId + '\',this,\'zltime\')" >';
	                		}
		    			}
		    		}
		    	}
		        return buttonstr;
            }
        },{
            title: '项目编号',
            field: 'itemno',
            align: 'center',
            visible: false,
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var html = '<span style="float:left;position:relative;top:-1px;" title="' + value + '">';
                if (row.istk == 1) {
                    html += '<span class="label label-info">退款</span>';
                } else {
                    if (Number(row.y2) < 0) {
                        html += '<span class="label label-warning">还款</span>';
                    } else if (Number(row.y2) > 0 && onclickrowOobj2.status == 2) {
                        html += '<span class="label label-danger">欠款</span>';
                    } else if (Number(row.y2) == 0 && row.isqfreal == 1) {
                        html += '<span class="label label-warning">还款</span>';
                    }
                }
                html += value + '</span>';
                return html;
            }
        },
        {
	        title: '就诊医生',
	        field: 'doctorname',
	        align: 'center',
	        
	        formatter: function(value, row, index) {
	        	 if (value) {
		                return "<span class='name'>"+value+"</span>";
		         }else{
		        	 return "<span>-</span>";
		         }
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
                return '<span>'+"￥" + row.unitprice +'</span>';
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
            title: '折扣%',
            field: 'discount',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + row.subtotal+'</span>';
            }
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + row.voidmoney+'</span>';
            }
        },
        {title: '应收金额',field: 'ys',align: 'center',sortable: true,
			formatter:function(value,row,index){
				var ys = Number(row.paymoney)+Number(row.arrearmoney);
				if(Number(row.y2)<= 0  &&  row.qfbh!="" ){//还款
					ys = "0.00";
				}
				return '<span class="money">￥'+ys+'</span>' ;
			}
		},
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + row.y2+'</span>';
            }
        },
        {
            title: '缴费金额',
            field: 'paymoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" +( Number(row.paymoney)-Number(row.payyjj))+'</span>';
            }
        },
        {
            title: '预交金使用',
            field: 'payyjj',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">'+"￥" + (row.payyjj)+'</span>';
            }
        }
        ]
    });
}
function changekaifa(seqId,value,field) {
	layer.confirm('确认更改？', {
	    btn: ['确定', '取消'] //按钮
	},
	function() {
		update(seqId,$(value).val(),field);
	    layer.closeAll('dialog');
	},
	function() {
		OrderDetail(onclickrowOobj2.costno);
	    layer.closeAll('dialog');
	});
}
function update(seqId,value,field) {
	var param={
			seqId:seqId
	};
	if(field=='kaifa'){
		param.kaifa=value;
	}else{
		param.zltime=value;
	}
    var url = contextPath+'/KQDS_CostOrder_DetailAct/update.act';
    $.axse(url, param,
    function(data) {
        if (data.retState == "0") {
            layer.alert('操作成功', {
                end: function() {
                    window.location.reload();
                }
            });
        }
    },
    function() {
        layer.alert('操作失败！' );
    });
}
function chuFangList(costno) {
    //$("#chufangDiv").html('<table id="chufang" class="table-striped table-condensed table-bordered" data-height="150"></table>');
    var detailurl = '<%=contextPath%>/KQDS_ChuFangAct/getListByCostNo.act?costno=' + costno;
    /*wl----首次加载时 计算table高 */
	 var tableHeight=getTableHeight();/* 计算table需要的高度 */
	$("#chufangDiv").html("<table id='chufang' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	
	/*wl----首次加载时 计算table高度————————————结束  */
    $("#chufang").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onDblClickCell: function(field, value, row, td) {
            //双击事件 调用提交回访结果方法 
            detail_chufang(row.seqId);
        },
        onLoadSuccess:function(){
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth("#chufangDiv");
        },
        columns: [
        {
	        title: '医生',
	        field: 'doctor',
	        align: 'center',
	        
	        formatter: function(value, row, index) {
	        	 if (row.doctor != "" && row.doctor != null) {
		                var pjid = "table" + index + "chufangCreateUser";
		                bindPerUserNameBySeqIdYB(pjid, value);
		                return "<span id='" + pjid + "' class='name'></span>";
		         }
	        }
	    },
        {
	        title: '创建人',
	        field: 'createuser',
	        align: 'center',
	        
	        formatter: function(value, row, index) {
	        	 if (row.doctor != "" && row.doctor != null) {
		                var pjid = "table" + index + "createuser";
		                bindPerUserNameBySeqIdYB(pjid, value);
		                return "<span id='" + pjid + "' class='name'></span>";
		         }
	        }
	    },
	    {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var menubutton = "";
                if(row.status == 0){
                	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit_chufang(\'' + row.seqId + '\')">编辑</a> ';
                	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="jinyong_chufang(\'' + row.seqId + '\')">作废</a> ';
                }else{
                	menubutton += '已作废 ';
                }
                return '<span>'+menubutton+'</span>';
            }
        },{
            title: '打印',
            field: 'print',
            align: 'center',
            formatter: function(value, row, index) {
                var menubutton = "";
                if(row.status == 0){
                	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail_chufang(\'' + row.seqId + '\')">详情</a> ';
                }else{
                	menubutton += '- ';
                }
                return '<span>'+menubutton+'</span>';
            }
        }]
    });
}

function jinyong_chufang(seqId){
	var tmp_chuFangObj = getChufangObjBySeqId(seqId);
	if (tmp_chuFangObj.cf.createuser != personId) {
        layer.alert('不可以作废其他人的处方单' );
        return false;
	}
	
	 layer.confirm('确定禁用此处方单？', {
         btn: ['是', '否'] //按钮
     },
     function() {
    	var serverData = getDataFromServer("KQDS_ChuFangAct/jinYongChuFang.act?seqId=" + seqId);
   		if(serverData.retState == "0"){
   			 layer.alert('操作成功', {
   		              
   		        });
   			 refresh();
   		}else{
   			 layer.alert('操作失败', {
   		              
   		        });
   		}
     });
}

//查看详情
function detail_chufang(seqId) {
	var requrl = contextPath + '/KQDS_ChuFangAct/toDetail_ChuFang.act?1=1';
	requrl += '&seqId=' + seqId;
	parent.layer.open({
        type: 2,
        title: '处方单明细',
        shade: 0.6,
        shadeClose: false,
        area: ['43%', '70%'],//调整宽度
        content: requrl
       });
}

//编辑处方单
function edit_chufang(seqId) {
	
	var tmp_chuFangObj = getChufangObjBySeqId(seqId);
	if (tmp_chuFangObj.cf.createuser != personId) {
        layer.alert('不可以修改其他人的处方单' );
        return false;
	}
	
	var requrl = contextPath + '/KQDS_ChuFangAct/toEdit_ChuFang.act?1=1';
	requrl += '&seqId=' + seqId;
	parent.layer.open({
        type: 2,
        title: '编辑处方单',
        shade: 0.6,
        shadeClose: false,
        area: ['75%', '70%'],
        content: requrl
       });
}

//开处方单
function addChufang() {
    if (onclickrowOobj2 == "") {
        layer.alert('请选择开单项' );
        return false;
    }
    //请求后台 验证该单的状态
    var costorderObj = getCostOrderObjBySeqId(onclickrowOobj2.costno);
    var serverData = getDataFromServer("KQDS_ChuFangAct/countCF.act?costno=" + onclickrowOobj2.costno);
	if(serverData){
		if(serverData.count > 0){
			layer.alert('同一个费用单只能开具一个处方，如需重开，请先禁用已开具的处方！', {
				  
			});
			return false;
		}
	}
	
	var requrl = contextPath + '/KQDS_ChuFangAct/toAdd_ChuFang.act?1=1';
	requrl += '&costno=' + costorderObj.costno;
	parent.layer.open({
        type: 2,
        title: '开具处方单',
        shade: 0.6,
        shadeClose: false,
        area: ['75%', '70%'],
        content: requrl
    });
}

//开单
function kd() {
	if (onclickrowOobj2 == "") {
	        layer.alert('请选择开单项',{} );
	        return false;
	}
	if (onclickrowOobj2.status == "2") {
        layer.alert('只能对未结账的费用单进行操作！' );
        return false;
    }
	if (onclickrowOobj2.createuser != personId && canEditCost != '1') {
        layer.alert('不可以修改其他人开单内容' );
        return false;
	}
    //请求后台 验证改单的状态
    var costorderObj = getCostOrderObjBySeqId(onclickrowOobj2.costno);
   	if(costorderObj.status=="2"){
		 layer.alert('该费用单已结账' );
		 refresh();
	}else{
		var requrl = contextPath + '/KQDS_CostOrderAct/toDetail_AddCost.act?1=1';
		if("1" == canZheKouOnly){ // 只能修改折扣
			requrl += "&zhekou=1";
		}
		
		requrl += '&costno=' + costorderObj.costno;
		requrl += '&isback=' + costorderObj.isback;
		parent.layer.open({
	        type: 2,
	        title: '费用添加',
	        shade: 0.6,
	        shadeClose: false,
	        area: ['95%', '98%'],
	        content: requrl
        });
	}
}

function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl + '?usercode=' + onclickrowOobj.usercode
    });
    $("#dykdxm  tbody").html("");
}
//添加费用时，页面关闭，直接删除
function delNotx(costno) {
    var url = '<%=contextPath%>/KQDS_CostOrderAct/deleteObj.act?seqId=' + costno;
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            refresh();
        }
    },
    function() {});
}
function del() {
	if (onclickrowOobj2 == "") {
        layer.alert('请选择删除项');
        return false;
    }
    if (onclickrowOobj2.status >= 2) {
        layer.alert('该费用单已结账，无法删除！' );
        return false;
    }
    if (onclickrowOobj2.createuser != personId && canEditCost != '1') {
        layer.alert('不可以删除其他人开单内容' );
        return false;
	}
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_CostOrderAct/deleteObj.act?seqId=' + onclickrowOobj2.seqId;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功');
                refresh();
            }else{
            	 layer.alert(data.retMsrg, {
                       
                 });
            	 refresh();
            }
        },
        function() {
            layer.alert('删除失败！');
            refresh();
        });
    });
}
function tuidan() {
	if(static_isyjjitem == 1){
		var canTuidan = true;
		canTuidan = yzTuiDanYjj(onclickrowOobj2.seqId);
		if(canTuidan){
	    	tuidanReq();
	    }
	}else{
		var data = yzTuiDan(onclickrowOobj2.seqId,onclickrowOobj2.usercode);
		if(data!=null){
			var dataJF = data.dataJF;
		   	if(!dataJF){
	       		var msg = "该费用单产生的积分已被使用，是否继续退单？ ";
	        	    layer.confirm(msg, {
	        	        btn: ['继续', '放弃'] 
	        	    },
	        	    function() {
	        	    	if(data.data){
	        		    	tuidanReq();
	        		    }
	        	    });
		   	}else{
		   		if(data.data){
			    	tuidanReq();
			    }
		   	}
		}
	}
}
function tuidanReq(){
	layer.prompt({
        title: '输入退单原因，并确认',
        formType: 0
    },
    function(backremark, index) {
        layer.close(index);
        var url = contextPath+'/KQDS_CostOrderAct/tuiDan.act?costno=' + onclickrowOobj2.seqId+'&backremark='+backremark;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('退单成功，请重新结账！', {
                      end : function (){
                    	  refresh();
      	                parent.window.location.reload();
                    }
                });
            }else{
            	 layer.alert(data.retMsrg, {
                       
                 });
            	 refresh();
            }
        },
        function() {
            layer.alert('退单失败！'  );
            refresh();
        });
    });
}
function yzTuiDan(costno,usercode){
	 var cantuidan = null;
	 //当天的费用单才可以退单
     //当天费用单存在一下几种情况可以退回到等待结账
     //1、无欠款
     //2、最新的还款或欠费
     //当天的费用单包含退费项目的，不可以使用退单功能
     var url = '<%=contextPath%>/KQDS_CostOrderAct/yzTuiDan.act?costno=' +costno+'&usercode='+usercode;
     $.axse(url, null,
     function(data) {
    	 if(data.retState=="0"){
        	 cantuidan = data;
    	 }else{
    		 layer.alert(data.retMsrg, {
                   
             });
    	 }
     },
     function() {
        layer.alert(data.retMsrg  );
     });
     return cantuidan;
}
function yzTuiDanYjj(costno){
	var cantuidan = false;
	//验证该费用单结账后，该患者是否使用卡号为usercode的会员卡 结过账
    var url = '<%=contextPath%>/KQDS_CostOrderAct/yzTuiDanYjj.act?costno=' +costno;
    $.axse(url, null,
    function(data) {
   	 if(data.retState=="0"){
       	 cantuidan = data.data;
   	 }else{
   		 layer.alert(data.retMsrg  );
   	 }
    },
    function() {
       layer.alert(data.retMsrg, {
             
       });
    });
    return cantuidan;
}
function tuidanSf(){
	var canTuidan = yzTuiDanSf(onclickrowOobj2.seqId);
    if(canTuidan){
    	 parent.layer.open({
	            type: 2,
	            title: '结账',
	            shadeClose: false,
	            shade: 0.6,
	            area: ['960px', '90%'],
	            content: contextPath + '/Kqds_PayCostAct/toCostListing.act?costno='+onclickrowOobj2.costno+'&usercode='+onclickrowOobj2.usercode+'&regno='+onclickrowOobj2.regno 
	     });
    }
}
function yzTuiDanSf(costno){
	var cantuidan = false;
	//退单的费用单才能在此结账
    var url = '<%=contextPath%>/KQDS_CostOrderAct/yzTuiDanSf.act?costno=' + costno;
    $.axse(url, null,
    function(data) {
   	 if(data.retState=="0"){
       	 cantuidan = data.data;
   	 }else{
   		 layer.alert(data.retMsrg  );
   	 }
    },
    function() {
       layer.alert(data.retMsrg, {
             
       });
    });
    return cantuidan;
}
//退单查询
function tuidanSearch() {
    parent.layer.open({
        type: 2,
        title: '退单查询',
        shadeClose: true,
        shade: 0.6,
        area: ['90%', '90%'],
        content: '<%=contextPath%>/KQDS_CostOrder_DetailAct/toCostDetail_Tuidan.act?menuId='+menuid
    });
}
function getButtonPower() {
    var menubutton1 = "",menubutton2="",menubutton3="",menubutton4="";
    for (var i = 0; i < listbutton.length; i++) {
    	//alert(listbutton[i].qxName);
    	//console.log(listbutton[i].qxName);
    	if (listbutton[i].qxName == "kd_chufang" && isdelreg == 0) {
    		menubutton1 += '    <a href="javascript:void(0);" id="kd_chufang" class="kqdsCommonBtn" onclick="addChufang()">开处方单</a>';
        } else if (listbutton[i].qxName == "fyxq_kd" && isdelreg == 0) {
            menubutton1 += '    <a href="javascript:void(0);" id="kd" class="kqdsCommonBtn" onclick="kd()">修改</a>';
        } else if (listbutton[i].qxName == "fyxq_del" && isdelreg == 0) {
            menubutton1 += '    <a href="javascript:void(0);" id="del" class="kqdsCommonBtn" onclick="del()">删除</a>';
        } else if (listbutton[i].qxName == "ylhz_llcx" && isdelreg == 0) {
        	menubutton1 += '    <a href="javascript:void(0);" id="llcx" class="kqdsCommonBtn" onclick="llcx()">领料查询</a>';//添加领料查询按钮
        } else if (listbutton[i].qxName == "llcx" && isdelreg == 0) {
        	menubutton1 += '    <a href="javascript:void(0);" id="ysll" class="kqdsCommonBtn" onclick="ysll()">医生领料</a>';//添加医生领料按钮
        } else if (listbutton[i].qxName == "fyxq_bddj" && isdelreg == 0) {
            menubutton1 += '    <a href="javascript:void(0);" id="kd" class="kqdsCommonBtn" onclick="bdqrd()">打印单据</a>';
        } else if (listbutton[i].qxName == "fyxq_zzys" && isdelreg == 0) {
            menubutton1 += '    <a href="javascript:void(0);" id="kd" class="kqdsCommonBtn" onclick="zzys()">转诊医生</a>';
        } else if (listbutton[i].qxName == "fyxq_tuidan" && isdelreg == 0) {
        	menubutton2 += '    <a href="javascript:void(0);" id="tuidan" class="kqdsCommonBtn" onclick="tuidan()">退单</a>';
        } else if (listbutton[i].qxName == "fyxq_tuidansf" && isdelreg == 0) {
        	menubutton3 += '    <a href="javascript:void(0);" id="tuidanSf" class="kqdsCommonBtn" onclick="tuidanSf()">退单结账</a>';
        } else if (listbutton[i].qxName == "fyxq_tuidansearch" && isdelreg == 0) {
        	menubutton4 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="tuidanSearch()">退单查询</a>';
        }else if(listbutton[i].qxName == "jzcx_invoice_value"){
        	jzcx_invoice_value_Flag = true;
        }
    }
    $("#recommendedBarDiv").append(menubutton1);
    $("#recommendedBarDiv").append(menubutton2);
    $("#recommendedBarDiv").append(menubutton3);
    $("#recommendedBarDiv").append(menubutton4);
}
function zzys() {
    if (onclickrowOobj2 == null || onclickrowOobj2.costno == null || onclickrowOobj2.costno == "undefined") {
        layer.alert('请选择转诊费用单！' );
        return false;
    }
    parent.layer.open({
        type: 2,
        title: '转诊医生',
        shadeClose: false,
        shade: 0.6,
        area: ['90%', '90%'],
        content: contextPath + '/KQDS_ChangeDoctorAct/toZz_Index.act?costno=' + onclickrowOobj2.costno + '&usercode=' + onclickrowOobj2.usercode
    });
}
function bdqrd() {
    if (onclickrowOobj2 == null || onclickrowOobj2.costno == null || onclickrowOobj2.costno == "undefined") {
        layer.alert('请选择补打收据的费用单！' );
    } else {
        if (onclickrowOobj2.status != "2") {
            layer.alert('请选择已结账的费用单！'  );
        } else {
            parent.layer.open({
                type: 2,
                title: '补打单据',
                shadeClose: false,
                shade: 0.6,
                area: ['960px', '90%'],
                content: contextPath + '/Kqds_PayCostAct/toCostListingPrint.act?costno=' + onclickrowOobj2.costno 
            });
        }
    }
}
function getTableHeight(){
	var tableHeight=($(window).height()-$(".recommendedBar").outerHeight()-$(".titleDiv").outerHeight()*3-20)/3;
	
	return Math.floor(tableHeight);
}
//调整表格高度
function adjustTable() {
    var height = $('body').height();
    //window.parent.setparentHeight(height);
    var tableHeight=getTableHeight();
    $(".fixed-table-container").outerHeight(tableHeight);
    
    /*表格载入时，设置表头的宽度 */
    setTableHeaderWidth("#tableFather");
    /*表格载入时，设置表头的宽度 */
    setTableHeaderWidth("#divkdxm");
    /*表格载入时，设置表头的宽度 */
    setTableHeaderWidth("#chufangDiv");
}
</script>
<!-- 表格宽度拖拽  -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/colresizable.js"></script>
</html>
