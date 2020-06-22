<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	String refundId = request.getParameter("refundId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>退款处理</title><!-- 接待中心  下方退款按钮进入 退款列表， 点击 退款 进入申请页面  -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/costOrder/refundRequest.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
</head>
<body>
	<div class="refundRequestContanier"> <!-- refundRequestContanier 退款申请样式 -->
		<div class="sectionDiv">
        	<span class="titleText">修改退费</span>
        	<div class="tableBox" id="divkdxm">
			    <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="150"></table>
        	 </div>
        	 <div id="buttonBar"> 
                     <table style="width:90%;text-align: center;"> 
                 		<tr>
                			<td style="width:12%" align="right"><span id="tkezSpan" style="color:#00A6C0;">退款总额:<lable id="tkze">0</lable></span></td>
                 		</tr> 
                 	</table> 
         	</div> 
		</div>
		<footer class="fixedBottomDiv">
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="sqtk">确认</a>
		</footer>
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
var contextPath = "<%=contextPath%>";
var personrole = "<%=person.getUserPriv()%>";
var pageurl = '<%=contextPath%>/KQDS_Refund_DetailAct/selectWithNopage.act';
var refundId = "<%=refundId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function(){
 //1、表格初始化
	OrderDetail(); /* 详细项目清单初始化  目的:显示出表头 */
});
function queryParams(params) {
    var temp = {  
    		refundid:refundId
    };
    return temp;
}
function OrderDetail(costno){
	$("#dykdxm").bootstrapTable({
		url:pageurl, 
		queryParams:queryParams,
		dataType: "json",
		cache: false, 
		striped: true,
		onLoadSuccess: function(data) { //加载成功时执行
			var tkje = 0;
			for(var i=0;i<data.length;i++){
				var dataone = data[i];
				tkje += Number(dataone.tkmoney);
			}
			$("#tkze").html(tkje.toFixed(2));    
        },
	      columns: [
	                {title: '治疗项目',field: 'itemname', align: 'center',valign: 'middle',sortable: true,
				    		  formatter:function(value,row,index){  
			                	  var html = '<span class="time" style="float:left; text-align:left;" title="'+value+'">' ;
			                	  if(row.y2<0){
				                	  html += '<span class="label label-warning">还款</span>';
				                  }else if(row.y2>0){
				                	  html += '<span class="label label-danger">欠款</span>';
				                  }else if(Number(row.y2) == 0 && row.isqfreal == 1) {
				                	  html += '<span class="label label-warning">还款</span>';
				                  }
			                	  html+=  value+'</span>';
				                  return html; 
		                	  }  
				      },
					{title: '单位',field: 'unit', align: 'center',valign: 'middle',sortable: true},
					{title: '单价',field: 'unitprice',align: 'center',valign: 'middle', sortable: true,
				                  formatter:function(value,row,index){  
				                	  return "￥"+ row.unitprice;
			                	  } 
		             },
		             {title: '数量',field: 'num',align: 'center',valign: 'middle'},
		             {title: '折扣',field: 'discount', align: 'center',valign: 'middle'},
					 {title: '应收',field: 'subtotal',align: 'center',valign: 'middle',
			                  formatter:function(value,row,index){  
			                	  return "￥"+ (Number(row.subtotal)-Number(row.voidmoney).toFixed(2));
		                	  } 
	              	 },
	              	{title: '缴费金额',field: 'paymoney', align: 'center',valign: 'middle',
			                  formatter:function(value,row,index){ 
			                	  return "￥"+(row.paymoney);
		                	  } 
	              	},
	                {title: 'seqId',field: 'seqId',align: 'center',valign: 'middle',visible:false,switchable:false},
	                {title: '退款数量',field: 'tknum',align: 'center',valign: 'middle',
	                	 formatter:function(value,row,index){
		                	  return '<input  type="number" min="1" style="width:100%;float:left; height:24px;text-align:center;border: solid 1px #e5e5e5;"  onfocus="this.select()"  value="'+value+'" id="tknum'+index+'">';
	                	  }	
	                },
	                {title: '退款金额',field: 'tkmoney',align: 'center',valign: 'middle',
	                	 formatter:function(value,row,index){
		                	  return '<input  type="number" min="1" style="width:100%;float:left; height:24px;text-align:center;border: solid 1px #e5e5e5;"  onfocus="this.select()" oldvalue="'+value+'" value="'+value+'" onchange="editqkze(\''+index+'\',\'other\');"  id="tkmoney'+index+'">';
	                	  }	
	                },
	                {title: '退款原因',field: 'remark',align: 'center',valign: 'middle',
	                	 formatter:function(value,row,index){
		                	  return '<input  type="text" min="1" style="width:100%;float:left; text-align:center; height:24px;"  onfocus="this.select()" value="'+value+'"  id="tkremark'+index+'">';
	                	  }	
	                }
	          ]
	  });
}
$('#sqtk').on('click', function(){
	var tkze = $("#tkze").html();
	var tkyy = false;//退款原因： 只要由一项退款原因填写了 则通过验证
	var tsqk = false;//全部免除的项目也可以提款（防止开错项目的）
	var list = $('#dykdxm').bootstrapTable('getData');
	for(var i=0;i<list.length;i++){
		 var tkmoney = $("#tkmoney"+i).val();
		 var tkremark = $("#tkremark"+i).val();
		 if((Number(list[i].paymoney)==0 || Number(tkmoney)==0) && tkremark!=""){
			 tsqk = true;
			 tkyy =true;
			 continue;
		 }
		 if(tkmoney!=""){
			 if(tkremark!=""){
				 tkyy =true;
			 }
		 }else{
			 continue;
		 }
	}	 
	if(!tkyy){
		 tkyy =true;
			layer.alert('请填写退款原因' );
			return false;
	}
	if(!tsqk){
		if(tkze!=""){
			if(Number(tkze)==0){
				layer.alert('没有选择退款项' );
				return false;
			}
		}else{
			layer.alert('没有选择退款项' );
			return false;
		}
	}
	//创建退款单
    var paramOrder = {
    		refundId:refundId,
    		tkze:tkze
	    };
    //循环获取表格中项目
    var list = [];
    var listData = $('#dykdxm').bootstrapTable('getData');
	for(var i=0;i<listData.length;i++){
		 var tknum = $("#tknum"+i).val();
		 var tkmoney = $("#tkmoney"+i).val();
		 var tkremark = $("#tkremark"+i).val();
		 var flag = (Number(listData[i].paymoney)==0 || Number(tkmoney)==0 )&& tkremark!="";
		 if(!Number(tkmoney)>0 && !flag){
			 continue;
		 }
		 var param = {
				 seqId:listData[i].seqId,
				 tknum:tknum,
				 tkmoney:tkmoney,
				 remark:tkremark
		    };
		 list.push(param);
	}
	var data = JSON.stringify(list);
	paramOrder.listDetail = data;
	var detailurl = '<%=contextPath%>/KQDS_RefundAct/updateTk.act';
	$.axse(detailurl, paramOrder,
			function(data) {
				if(data.retState==0){
		        	layer.alert('修改成功', {
		                  
		                end: function() {
		                	parent.refresh();
				        	parent.layer.close(frameindex); //再执行关闭  
		                }
		            });
				}
			},
			function() {
				layer.alert('修改失败' );
			});
});
function editqkze(index,name){
	var newobj = $('#dykdxm').bootstrapTable('getData')[index];
    var param = {
   		 usercode : newobj.usercode,
   		 itemno : newobj.itemno,
   		 qfbh : newobj.qfbh,
   		 detailId : newobj.orderdetailno
	    };
    var sszje=0;//实收总金额  
	var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/checkTf.act';
	$.axse(detailurl, param,
			function(data) {
				sszje = Number(data.symoney);
			},
			function() {
				layer.alert('查询出错！', {
					  
				});
			});
	//判断 修改的收费项目的退费总金额是否超过实收总金额
	//收费项目的退费总金额
	var tkmoney = $("#tkmoney"+index).val();
	if(Number(tkmoney).toFixed(2)<0){
		var oldpayvalue = $("#tkmoney" + index).attr("oldvalue");
        $("#tkmoney" + index).val(oldpayvalue);
		layer.alert('退款金额不能小于0！', {
			  
		});
		return false;
	}
	if((Number(tkmoney).toFixed(2)-Number(sszje).toFixed(2))>0){
			var oldpayvalue = $("#tkmoney" + index).attr("oldvalue");
	        $("#tkmoney" + index).val(oldpayvalue);
			layer.alert('该收费项目的退款金额大于缴费金额！', {
				  
			});
			return false;
	}
	//遍历 计算 收费单的 退款总金额
	var list = $('#dykdxm').bootstrapTable('getData');
	var newtkze =0;
	for(var i=0;i<list.length;i++){
		newtkze += Number($("#tkmoney"+i).val());
	}
	$("#tkze").html(newtkze.toFixed(2));
}
</script>
</html>