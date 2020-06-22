<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.kqds.util.sys.chain.ChainUtil" %>

<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String type = request.getParameter("type");//type不为空 表示从费用明细 进入此打印页面
	if(type==null){
		type="";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>转诊医生</title><!--  从接待中心  下方按钮进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/addVisting.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
</head>
<body>
<form class="form-horizontal" id="form1">
<input type="hidden" id="costno" name="costno" >
<input type="hidden" id="regno" name="regno" >
	<div class="jiuzhen_register-content" style="width: 900px;">
			<div class="filterWrap">
				<div class="kv">
					<label>病人编号</label>
					<div class="kv-v">
						<input type="text" id="usercode" name="usercode" style="width: 120px;" readonly>
					</div>
				</div>
				<div class="kv">
					<label>姓名</label>
					<div class="kv-v">
						<input type="text" id="username" name="username" style="width: 120px;" readonly>
						<input type="hidden" id="age" name="age" style="width: 120px;" readonly>
					</div>
				</div>
				<div class="kv">
					<label>转诊医生</label>
					<div class="kv-v">
						<input type="hidden" name="doctor" id="doctor"  class="form-control" />
						<input type="text"   id="doctorDesc" name="doctorDesc" placeholder="医生" readonly style="width: 120px;" onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'single');"  ></input>	
					</div>
				</div>
			</div>
		 <div class="tableBox">
		 	<p	class="table-title orangeFont">收费项目</p>
		 	<div id="divkdxm">
		 	    <table  id="tabledayin" class="table-striped table-condensed table-bordered" ></table>
		 	</div>
         </div>
         <div class="register-bottom">
		   	<div class="formBox">
					<div class="kv">
						<label>总金额</label>
						<div class="kv-v">
							<input type="text"  class="orangeFont" id="totalcost" name="totalcost" readonly>
						</div>
					</div>
					<div class="kv">
						<label>免除金额</label>
						<div class="kv-v">
							<input type="text"  class="orangeFont" id="voidmoney" name="voidmoney" readonly>
						</div>
					</div>
					<div class="kv">
						<label>应收金额</label>
						<div class="kv-v">
							<input type="text"  class="orangeFont" id="shouldmoney" name="shouldmoney" readonly>
						</div>
					</div>
					<div class="kv">
						<label>缴费金额</label>
						<div class="kv-v">
							<input type="text"  class="orangeFont" id="actualmoney" name="actualmoney" readonly>
						</div>
					</div>
					<div class="kv">
						<label>欠费金额</label>
						<div class="kv-v">
							<input type="text"  class="orangeFont" id="arrearmoney" name="arrearmoney" readonly>
						</div>
					</div>
					<div class="kv" style="display: none;">
						<label>打折金额</label>
						<div class="kv-v">
							<input type="text"  class="orangeFont" id="discountmoney" name="discountmoney" readonly>
						</div>
					</div>
			</div>
         </div>
         <div class="position-bottom" >
         	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="save();" >保存</a>
         	<a href="javascript:void(0);" class="kqdsCommonBtn" id="qx" >取消</a>
         </div>
	</div>
	</form>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var onclickrowOobj2 ="";
var num = 1;
var allhkxm=true;//是否改费用单下全是还款项目
var type="<%=type%>";
var costno;
var usercode;
$(function(){
	if(type=="1"){// 表示从费用明细 进入此打印页面
		onclickrowOobj2=parent.$("#tabIframe")[0].contentWindow.onclickrowOobj2;
	}else{
		onclickrowOobj2=window.parent.onclickrowOobj;
	}
	costno = "\'"+onclickrowOobj2.costno+"\'";
	usercode = onclickrowOobj2.usercode;
	OrderDetail();
	getAlldata();
});
function getAlldata(){
		 $("#costno").val(onclickrowOobj2.costno);
		 $("#regno").val(onclickrowOobj2.regno);
		 $("#totalcost").val(onclickrowOobj2.totalcost);
		 $("#voidmoney").val(onclickrowOobj2.voidmoney);
		 $("#shouldmoney").val(onclickrowOobj2.shouldmoney);
		 $("#actualmoney").val(onclickrowOobj2.actualmoney);
		 $("#arrearmoney").val(onclickrowOobj2.y2);
		 $("#discountmoney").val(onclickrowOobj2.discountmoney);
		 $('#usercode').val(onclickrowOobj2.usercode);
		 $('#username').val(onclickrowOobj2.username);
		 $("#doctor").val(onclickrowOobj2.doctor);
		 if(onclickrowOobj2.doctor != undefined && onclickrowOobj2.doctor.trim() != ""){//把医生带过来
			 bindPerUserNameBySeqIdTB("doctorDesc",onclickrowOobj2.doctor);
		 }
}
function OrderDetail(){
	var detailurl  = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno='+costno;
	$("#tabledayin").bootstrapTable({
		url:detailurl, 
		dataType: "json",
		cache: false, 
		striped: true,
		      columns: [
		              	{title: '治疗项目',field: 'itemname', align: 'center',valign: 'middle',sortable: true,
			              	  formatter:function(value,row,index){  
			              			return '<span style="float:left; text-align:left;" class="time">'+value+'</span>' ;
		                	  }  	
		              	},
						{title: '单位',field: 'unit', align: 'center',valign: 'middle',sortable: true},
						{title: '单价',field: 'unitprice',align: 'center',valign: 'middle', sortable: true,
							  formatter:function(value,row,index){  
			              			return '<span style="float:right; text-align:right;">￥'+value+'</span>' ;
		                	  }  
			             },
			             {title: '数量',field: 'num',align: 'center',valign: 'middle'},
			             {title: '折扣%',field: 'discount', align: 'center',valign: 'middle',sortable: true},
						 {title: '小计',field: 'subtotal',align: 'center',valign: 'middle',sortable: true,
				                  formatter:function(value,row,index){  
				                	  return '<span style="float:right; text-align:right;">￥'+value+'</span>' ;
			                	  } 
		              	 },
		              	 {title: '欠费金额',field: 'arrearmoney',align: 'center',valign: 'middle',sortable: true,
				                  formatter:function(value,row,index){  
				                	  return '<span style="float:right; text-align:right;">￥'+value+'</span>' ;
			                	  } 
		              			},
		              	{title: '缴费金额',field: 'paymoney', align: 'center',valign: 'middle',sortable: true,
				                  formatter:function(value,row,index){ 
				                	  return '<span style="float:right; text-align:right;">￥'+value+'</span>' ;
			                	  } 
		              	},
		              	{title: '免除',field: 'voidmoney', align: 'center',valign: 'middle',
				                  formatter:function(value,row,index){  
				                	  return '<span style="float:right; text-align:right;">￥'+value+'</span>' ;
			                	  } 
		              	}
		          ]
		  });
}
$('#qx').on('click', function(){
	var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(frameindex); //再执行关闭 */
});
//保存医生
function save(){
	var regno = $("#regno").val();
	var oldDoctor = onclickrowOobj2.doctor;
	var doctor = $("#doctor").val();
	var costno = $("#costno").val();
	var url = '<%=contextPath%>/KQDS_REGAct/zzDoctor.act?regno='+regno+'&oldDoctor='+oldDoctor+'&doctor='+doctor + '&costno='+costno;
	$.axse(url,null,
             function(r){
	    	    if(r.retState=="0"){
	    	    	layer.alert('转诊医生成功！', {  end : function (){
	    	    			var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				        	parent.getPayOrderlist();
				        	parent.layer.close(frameindex); //再执行关闭 */
		        	}});
		        }else{
		        	layer.alert('转诊医生失败！' );
		        } 
             },
             function(){
           	  layer.alert('转诊医生失败！' );
         	  }
    );
}
</script>

</html>
