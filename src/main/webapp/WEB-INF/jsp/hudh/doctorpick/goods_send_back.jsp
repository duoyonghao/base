<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
   String seqId = request.getParameter("seqId");
   if(seqId==null){
	   seqId="";
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>商品类别</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/kqdsFront/css/bootstrapSwitch.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrapSwitch/bootstrap-switch.css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/kqdsCss/kqdsCommon.css"/>
<style>
	table.tableLayout{/* table布局内容距离页面顶端30px */
		margin-top:15px;
	}
	.commonText{	/*数据项的样式  */
		padding: 0 10px;
	}
	table.tableLayout select,table.tableLayout input[type="text"]{
		width:140px;
	}
	table.tableLayout tr{
		height:36px;
	}
</style>
</head>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    	<form class="form-horizontal"  id="form1">
    		<input type="hidden" class="form-control" name="seqId" id="seqId" >
	        <table class="tableLayout" id="initialize">
		     	<tbody>
		     		<tr>
		     			<td>
		     				<span class="commonText">商品名称<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="good_name" id="good_name" disabled="disabled">
		     			</td>
		     			
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">商品编号<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="goods_no" id="goods_no" disabled="disabled">
		     			</td>
		     		</tr>
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText">商品规格<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="goods_spec" id="goods_spec" disabled="disabled">
		     			</td>
		     			
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="company" id="company" disabled="disabled">
		     			</td>
		     		</tr>
		     		<tr>
		     			<td>
		     				<span class="commonText">商品批号<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="batchnum" id="batchnum" disabled="disabled">
		     			</td>
		     			
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">批号数量<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="quantity" id="quantity" disabled="disabled">
		     			</td>
		     		</tr>
		     		<tr>
		     			<!-- <td>
		     				<span class="commonText">商品单价<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="goods_unitprice" id="goods_unitprice">
		     			</td> -->
		     			
		     			<td>
		     				<span class="commonText">退还数量<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				 <input type="text" name="nums" id="nums">
		     			</td>
		     			
		     			<!-- <td>
		     				<span class="commonText" style="padding-left:20px;">退还金额<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<input type="text" name="sendbackamount" id="sendbackamount">
		     			</td> -->
		     			
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">所属仓库<span style="color: red;">*</span></span>
		     			</td>
		     			<td>                                          <!-- 该属性为不可选 -->
		     				<select name="storage_id" id="storage_id" disabled="disabled">
		     			 		<option value="">请选择</option>
		     				</select>
		     			</td>
		     		</tr>
		     		
		     		<!-- <tr>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">供应商<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     			 	<select name="supplier_id" id="supplier_id">
		     			 		<option value="">请选择</option>
		     			 	</select>
		     			</td> 
		     			
		     		</tr> -->
		     		
		     		<tr>
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">退还科室<span style="color: red;">*</span></span>
		     			</td>
		     			<td>
		     				<select name="keshi_id" id="keshi_id" disabled="disabled">
		     			 		<option value="">请选择</option>
		     			 	</select>
		     			</td>
		     			
		     			<td>
		     				<span class="commonText" style="padding-left:20px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</span>
		     			</td>
		     			<td>
		     				<textarea rows="" cols="" id="remark" name="remark" style="width: 140px;border-radius:0.5em;"></textarea>
		     			</td>
		     		</tr>
		     		<tr style="display:none">
		     			<td>
		     				<input type="text" name="phids" id="phids">
		     			</td>
		     		</tr>
		     	</tbody>
	     	</table>   
     	</form>
     	<div class="position-bottom" >
			<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()" id="tijiao">保存</a>
		</div>
    </div>
</div>

<div id="menuContent" class="menuContent" style="background:#DDDDDD;display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapSwitch/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript">
var apppath = apppath();
var seqId = "<%=seqId%>";
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	addSupplier();//动态获取供应商
	addHouse();//动态获取所属仓库
	addKeshi();//动态获取科室
	//location.reload();
	sendBackGoods();//初始化
});

function addHouse(){
	var url = contextPath + "/KQDS_Ck_HouseAct/selectList.act";
	var param = { id : null };
	$.axseSubmit(url, param, function() {}, function(r) {
	  //alert(JSON.stringify(r));
	  //alert(r.length);
	  for (var i = 0; i < r.length; i++) {
			  $("#storage_id").append(
					'<option value='+ r[i].seqId + '>' + r[i].housename + '</option>' 
			  );
	  }
	}, function() {
		
	});
}

function addSupplier(){
	var url = contextPath + "/KQDS_Ck_SupplierAct/selectList.act";
	var param = { id : null };
	$.axseSubmit(url, param, function() {}, function(r) {
		  //alert(JSON.stringify(r));
		  //alert(r.length);
		  for (var i = 0; i < r.length; i++) {
				  $("#supplier_id").append(
						'<option value='+ r[i].seqId + '>' + r[i].suppliername + '</option>' 
				  );
		  }
		}, function() {
			
	});
}

function addKeshi() {
	var url = contextPath + "/KQDS_Ck_CkdeptAct/selectList.act";
	var param = { id : null };
	$.axseSubmit(url, param, function() {}, function(r) {
		  //alert(JSON.stringify(r));
		  //alert(r.length);
		  for (var i = 0; i < r.length; i++) {
				  $("#keshi_id").append(
						'<option value='+ r[i].seqId + '>' + r[i].deptname + '</option>' 
				  );
		  }
		}, function() {
			
	});
}
var goodsuuid1 = '<%=request.getAttribute("goodsuuid1")%>';
var id = '<%=request.getAttribute("id")%>';
//alert(goodsuuid1);
//提交
function submitu() {
	var goodsname = $("#good_name").val();
	var goodscode = $("#goods_no").val();
	var goods_unitprice = $("#goods_unitprice").val();
	var housename = $("#storage_id").val();
	var goodsunit = $("#company").val();
	var nums = $("#nums").val();
	var quantity = $("#quantity").val();
	if(judgeSign(nums) == false){
		 layer.alert('退还数量必须为正整数！', {
	     });
		 $("#nums").val("");
		 return false;
	} else if(Number(nums)>Number(quantity)){
		layer.alert('退还数量大于批号数量！', {
	     });
		 $("#nums").val("");
		 return false;
	}
	var sendBackAmount = nums * goods_unitprice;
	//alert(sendBackAmount);
	$("#sendbackamount").val(sendBackAmount);
	var goodsnorms = $("#goods_spec").val();
	var storage_id = $("#storage_id").val();
	var supplier_id = $("#supplier_id").val();
	var keshi_id = $("#keshi_id").val();
	var remark = $("#remark").val();
    //验证
    var param = {
    	id : id,
    	goodsuuid1 : goodsuuid1,
    	goodsname: goodsname,
    	goodscode: goodscode,
    	goods_unitprice : goods_unitprice,
    	sendBackAmount : sendBackAmount,
    	housename: housename,
    	goodsunit: goodsunit,
    	nums: nums,
    	goodsnorms : goodsnorms,
    	storage_id :storage_id,
    	supplier_id : supplier_id,
    	keshi_id : keshi_id,
    	remark : remark,
    	phids:$("#phids").val(),
    	batchnum:$("#batchnum").val()
    };
    var url = contextPath + '/HUDH_Goods_Pick_Send_BackAct/insertGoodsPickSendBack.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
    	//alert(JSON.stringify(r));
        if (r.retState == "0") {
            layer.alert('退还成功', {
                end: function() {
                	parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
                }
            });
        } else {
            layer.alert('退还失败：'  + r.retMsrg , {//后台抛出的异常信息在前台展示
            	end: function() {
                	parent.location.reload(); //刷新父页面
	                var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
                }
            });
        }
    },
    function() {
        layer.alert('退还失败' );
    });
}

/**
 * 点击赋值
 */
function sendBackGoods() {
	var url = contextPath + "/GoodsDoctorPickInDetailAct/findDoctorPickInDetailById.act";
	var param = {id : id};
	$.axseSubmit(url, param, function() {},
	function(r) {
		//alert(JSON.stringify(r));
		//console.log(JSON.stringify(r));
		$("#good_name").val(r.goodsname);
		$("#goods_no").val(r.goodscode);
		$("#goods_unitprice").val(r.nuitprice);
		$("#goods_spec").val(r.goodsnorms);
		$("#company").val(r.goodsunit);
		//$("#nums").val(r.quantity);
		//$("#supplier_id").val(r.quantity);
		$("#storage_id").val(r.house);
		$("#keshi_id").val(r.deptpart);
		$("#batchnum").val(r.batchnum);
		$("#quantity").val(r.quantity);
		$("#phids").val(r.phids);
	},
	function() {
		layer.alert('退还失败' );
	});
		    
} 

 
</script>
</html>
