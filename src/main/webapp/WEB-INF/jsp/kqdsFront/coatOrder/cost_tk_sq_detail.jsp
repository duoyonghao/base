<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	String refundId = request.getParameter("refundId");
	String status = request.getParameter("status");
	String usercode = request.getParameter("usercode");
	String username = request.getParameter("username");
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
        	<span class="titleText">退费明细处理</span>
        	<div class="tableBox" id="divkdxm">
			    <table id="table" class="table-striped table-condensed table-bordered" data-height="150"></table>
        	 </div>
		</div>
		<footer class="fixedBottomDiv">
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="sqtk">确认退款</a>
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
var pageurl = '<%=contextPath%>/KQDS_Refund_DetailAct/selectWithNopage4.act';
var usercode ="<%=usercode%>";
var username ="<%=username%>";
var refundId = "<%=refundId%>";
var status = "<%=status%>";
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
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-110
	})
}
function OrderDetail(costno){
	$("#table").bootstrapTable({
		url:pageurl, 
		queryParams:queryParams,
		dataType: "json",
		cache: false, 
		onLoadSuccess:function(data){
			setHeight();
			//付款方式（排除已禁用）
			getFkfsFieldUse();
		},
		striped: true,
		      columns: [
						{title: '退款项目',field: 'itemname',align: 'center',valign: 'middle',formatter:function(value,row,index){
		               			if(value){
		               				if(value.length > 10){
		               					return "<span title='" + value + "' style='cursor:pointer;width:160px;'>" + value.substring(0,10) + "...</span>"
		               				}else{
		               					return "<span title='" + value + "' style='cursor:pointer;width:160px;'>" + value + "</span>"
		               				}
		               			}else{
		               				return "";
		               			}
							}	
						},
		                {title: '退款数量',field: 'tknum',align: 'center',valign: 'middle'},
		                {title: '退款金额',field: 'tkmoney',align: 'center',valign: 'middle',
		                	   formatter:function(value,row,index){
			                	  return '<span id="tkmoney'+index+'">'+value+'</span>';
		                	  }		
		                },
		                {
		                	title: '退款原因',
		                	field: 'remark',
		                	align: 'center',
		                	valign: 'middle',
		                	formatter:function(value){
		                		return "<span class='remark'>"+value+"</span>";
		                	}
		                	
		                },
		                {title: '现金',field: 'payxj',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;" value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'xj\');"  id="xj'+index+'">';
		                	  }	
		                },
		                {title: '银行卡',field: 'paybank',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'yhk\');"  id="yhk'+index+'">';
		                	  }	
		                },
		                {title: '医保',field: 'payyb',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'yb\');"  id="yb'+index+'">';
		                	  }	
		                },
		                {title: '赠送',field: 'payother2',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'zs\');"  id="zs'+index+'">';
		                	  }	
		                },
		                {title: '微信',field: 'paywx',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'wx\');"  id="wx'+index+'">';
		                	  }	
		                }, 
		                {title: '支付宝',field: 'payzfb',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'zfb\');"  id="zfb'+index+'">';
		                	  }	
		                }, 
		                {title: '么么贷',field: 'paymmd',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'mmd\');"  id="mmd'+index+'">';
		                	  }	
		                }, 
		                {title: '百度分期',field: 'paybdfq',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'bdfq\');"  id="bdfq'+index+'">';
		                	  }	
		                }, 
		                {title: '其他',field: 'payother1',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'other\');"  id="other'+index+'">';
		                	  }	
		                },
		                {title: '代金券',field: 'paydjq',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'djq\');"  id="djq'+index+'">';
		                	  }	
		                },
		                {title: '积分使用',field: 'payintegral',align: 'center',valign: 'middle',
		                	 formatter:function(value,row,index){
			                	  return '<input  type="number" min="1" style="width:70px;float:left; text-align:center;padding-left:10px;"  value="'+value+'" onfocus="this.select()" onchange="editqkze(\''+index+'\',\'integral\');"  id="integral'+index+'">';
		                	  }	
		                }
		          ]
		  });
}
$('#sqtk').on('click', function(){
	var ssmoney = 0;
	var listData = $('#table').bootstrapTable('getData');
	for(var i=0;i<listData.length;i++){
		 var payxj=0,paybank=0,payyb=0,paywx=0,payzfb=0,paymmd=0,paybdfq=0,payother1=0;
		 if($("#xj"+i).val()){
			 payxj = $("#xj"+i).val();
		 }
		 if($("#yhk"+i).val()){
			 paybank = $("#yhk"+i).val();
		 }
		 if($("#yb"+i).val()){
			 payyb = $("#yb"+i).val();
		 }
		 if($("#wx"+i).val()){
			 paywx = $("#wx"+i).val();
		 }
		 if($("#zfb"+i).val()){
			 payzfb = $("#zfb"+i).val();
		 }
		 if($("#mmd"+i).val()){
			 paymmd = $("#mmd"+i).val();
		 }
		 if($("#bdfq"+i).val()){
			 paybdfq = $("#bdfq"+i).val();
		 }
		 if($("#other"+i).val()){
			 payother1 = $("#other"+i).val();
		 }
		 ssmoney += Number(payxj)+Number(paybank)+Number(payyb)+Number(paywx)+Number(payzfb)+Number(paymmd)+Number(paybdfq)+Number(payother1);
	}
	var cantuidan = yzTuiKuanJF(Number(ssmoney).toFixed(2));
	if(!cantuidan){
   		var msg = "该费用单产生的积分已被使用，是否继续退款？ ";
    	    layer.confirm(msg, {
    	        btn: ['继续', '放弃'] 
    	    },
    	    function() {
    	    	qrtk();
    	    });
   	}else{
   		qrtk();
   	}
});
function yzTuiKuanJF(ssmoney){
	var cantuidan = null;
    var url = contextPath+'/KQDS_RefundAct/yzTuiKuanJF.act?ssmoney=' +ssmoney+'&usercode='+usercode;
    $.axse(url, null,
    function(data) {
   	 if(data.retState=="0"){
       	 cantuidan = data.dataJF;
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
function qrtk(){
	//创建退款单
    var paramOrder = {
    		seqId:refundId,
    		status:status
	    };
    //循环获取表格中项目
    var list = [];
    var listData = $('#table').bootstrapTable('getData');
	for(var i=0;i<listData.length;i++){
		 var payxj=0,paybank=0,payyb=0,paywx=0,payzfb=0,paymmd=0,paybdfq=0,paydjq=0,payintegral=0,payother1=0,payother2=0;
		 if($("#xj"+i).val()){
			 payxj = $("#xj"+i).val();
		 }
		 if($("#yhk"+i).val()){
			 paybank = $("#yhk"+i).val();
		 }
		 if($("#yb"+i).val()){
			 payyb = $("#yb"+i).val();
		 }
		 if($("#wx"+i).val()){
			 paywx = $("#wx"+i).val();
		 }
		 if($("#zfb"+i).val()){
			 payzfb = $("#zfb"+i).val();
		 }
		 if($("#mmd"+i).val()){
			 paymmd = $("#mmd"+i).val();
		 }
		 if($("#bdfq"+i).val()){
			 paybdfq = $("#bdfq"+i).val();
		 }
		 if($("#djq"+i).val()){
			 paydjq = $("#djq"+i).val();
		 }
		 if($("#integral"+i).val()){
			 payintegral = $("#integral"+i).val();
		 }
		 if($("#other"+i).val()){
			 payother1 = $("#other"+i).val();
		 }
		 if($("#zs"+i).val()){
			 payother2 = $("#zs"+i).val();
		 }
		 var tkmoney = $("#tkmoney"+i).html();
		 var tfall = Number(payxj)+Number(paybank)+Number(payyb)+Number(paywx)+Number(payzfb)+Number(paymmd)+Number(paybdfq)+Number(paydjq)+Number(payintegral)+Number(payother1)+Number(payother2);
		 if(Number(tfall).toFixed(2)!= Number(tkmoney).toFixed(2)){
				layer.alert('退费金额不等于退费方式的总和！', {
					  
				});
				return false;
		 }
		 var detailparam = {
				 seqId:listData[i].seqId,
				 payxj:payxj,
				 payyjj:0,
				 paybank:paybank,
				 payyb:payyb,
				 paymmd:paymmd,
				 paybdfq:paybdfq,
				 paydjq:paydjq,
				 payintegral:payintegral,
				 payother1:payother1,
				 payother2:payother2, 
				 paywx:paywx,
				 payzfb:payzfb,
		    };
		 list.push(detailparam);
	}
	var data = JSON.stringify(list);
	paramOrder.listDetail = data;
	paramOrder.usercode=usercode;
	paramOrder.username=username;
    var pageurl = '<%=contextPath%>/KQDS_RefundAct/editState.act';
    $.axse(pageurl, paramOrder,
    function(r) {
        if (r.retState == "0") {
        	layer.alert('退款成功', {  end : function (){
        		var detailurl = '<%=contextPath%>/KQDS_UserDocumentAct/getSsje.act?usercode='+usercode;
           	    $.axseY(detailurl, null,function(data) { parent.refresh();
                parent.layer.close(frameindex);},function() {});
        	}});
        } else {
            layer.alert(r.retMsrg  );
            parent.refresh();
            parent.layer.close(frameindex);
        }
    },
    function() {
        layer.alert(r.retMsrg );
        parent.refresh();
        parent.layer.close(frameindex);
    });
}
function editqkze(i,name){
	var newobj = $('#table').bootstrapTable('getData')[i];
	 var xj=0,yhk=0,yb=0,wx=0,zfb=0,mmd=0,bdfq=0,djq=0,integral=0,other=0,zs=0;
	 if($("#xj"+i).val()){
		 xj = $("#xj"+i).val();
	 }
	 if($("#yhk"+i).val()){
		 yhk = $("#yhk"+i).val();
	 }
	 if($("#yb"+i).val()){
		 yb = $("#yb"+i).val();
	 }
	 if($("#wx"+i).val()){
		 wx = $("#wx"+i).val();
	 }
	 if($("#zfb"+i).val()){
		 zfb = $("#zfb"+i).val();
	 }
	 if($("#mmd"+i).val()){
		 mmd = $("#mmd"+i).val();
	 }
	 if($("#bdfq"+i).val()){
		 bdfq = $("#bdfq"+i).val();
	 }
	 if($("#djq"+i).val()){
		 djq = $("#djq"+i).val();
	 }
	 if($("#integral"+i).val()){
		 integral = $("#integral"+i).val();
	 }
	 if($("#other"+i).val()){
		 other = $("#other"+i).val();
	 }
	 if($("#zs"+i).val()){
		 zs = $("#zs"+i).val();
	 }
	var tkmoney = $("#tkmoney"+i).html();
	var tfall = Number(xj)+Number(yhk)+Number(yb)+Number(zs)+Number(wx)+Number(zfb)+Number(mmd)+Number(bdfq)+Number(other)+Number(djq)+Number(integral);
	if(Number(tfall).toFixed(2)<0){
		layer.alert('请填写退款方式！', {
			  
		});
		return false;
	}
	var isflag = Number(tkmoney).toFixed(2)-Number(tfall).toFixed(2);
	if(isflag<0){
		$('#'+name+i).val(0);
		layer.alert('退费金额不等于退费方式的总和！', {
			  
		});
		return false;
	}
}
</script>
</html>
