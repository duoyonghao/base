<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.kqds.util.sys.chain.ChainUtil" %>
<%
	//合并测试
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String costno = request.getParameter("costno");
	if(costno==null){
		costno="";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>补打单据/补打明细</title><!-- 该页面不是真实打印页面 从接待中心  下方按钮进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/cost_list.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script language="javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
</head>
<style type="text/css">
#printArea span {
	font-size: 16px;
}

#printArea table,th{
	border:none;
	height:18px;
	font-weight:normal
} 
#printArea td{
	text-align:center;
	border: none solid #000;
	height:18px;
	font-weight:normal;
	font-size:14px
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
.kv > label{
	width:65px;
}
</style>
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
					<label>医生</label>
					<div class="kv-v">
						<input type="text" id="doctor" name="doctor" style="width: 120px;" readonly>
					</div>
				</div>
			</div>
		 <div class="tableBox">
		 	<p	class="table-title orangeFont">收费项目</p>
		 	<div id="divkdxm">
		 	    <table  id="tabledayin" class="table-striped table-condensed table-bordered" ></table>
		 	</div>
		 	 <table style="width:100%;text-align: left;"> 
		 	 	<tr>
		 	 		<td id="FKFS" style="text-align: left;"></td>
		 	 	</tr>
		 	 	<tr>
		 	 		<td id="SYJE" style="text-align: left;"></td>
		 	 	</tr>
        	</table> 
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
						<label>欠费</label>
						<div class="kv-v">
							<input type="text"  class="orangeFont" id="arrearmoney" name="arrearmoney" readonly>
						</div>
					</div>
					<div class="kv">
						<label>剩余总欠费</label>
						<div class="kv-v">
							<input type="text"  class="orangeFont" id="arrearmoneyAll" name="arrearmoneyAll" readonly>
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
         	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="printfyqrd();" >打印确认单</a>
         	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="printfymxd();" >打印明细单</a>
         	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="printviewfymxd();">打印明细单预览</a>
         	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="printviewfyqrd();">打印确认单预览</a>
         </div>
	</div>
	</form>
	<!-- 打印内容 -->
	<div id="printArea" width="100%" style="display: none;">
		<div style="text-align: center;width: 95%;margin: auto;">
			<span id="titles" style="font-size: 20px;"></span><!-- 抬头 -->
		</div>
		<div id="hide" style="width: 95%;margin: auto;">
			<table style="width: 100%; text-align: left;border-top:Black solid 1px;border-bottom:Black solid 1px;padding-top:5px; ">
				<tr id="printtr">
				</tr>
			</table>
		</div>
		<div id="div2" style="margin-top: 5px;">
			<table  border=0 cellSpacing=0 cellPadding=0  width="95%"  bordercolor="#000000" style="border-collapse:collapse;margin: auto;">
				<thead>
					<tr>
						<th width="25%" align="left">治疗项目</th>
						<th width="15%" align="center">单位</th >
						<th width="15%"  align="center">数量</th>
						<th width="15%" align="center">应收</th>
						<th width="15%" align="center">欠费金额</th>
						<th width="15%" align="center">缴费金额</th>
					</tr>
				</thead>
				<tbody id="tbody">
				
				</tbody>
				<tfoot>	
					  <tr align="left" id="printtr2" style="border-top:Black solid 1px;border-bottom:Black solid 1px;padding-top:5px; ">
					      
					  </tr>
					  <tr  align="left" style="border-top:Black solid 1px;border-bottom:Black solid 1px;padding-top:5px; ">
					  		<td colspan="9" id="FKFSPrint" style="text-align: left;"></td>
					  </tr>
					  <tr  align="left" style="border-top:Black solid 1px;border-bottom:Black solid 1px;padding-top:5px; ">
					  		<td colspan="9" id="SYJEPrint" style="text-align: left;"></td>
					  </tr>
					  <tr id="tishi">
					    <th width="100%" colspan="9"><span style="height: 28px;float: left;font-size:12px;font-weight:normal">尊敬的客户，为了能向您提供优质的服务，请确认医生已向您详细介绍了本次治疗的项目及相关费用，并征得您的同意。</span></th>
					  </tr>
					   <tr  id="qz" align="right">
					    <th width="100%" colspan="9" style="text-align: right;">客户签名：____________________　</th>
					  </tr>
				</tfoot>
			</table>
		</div>
	</div>
	
	
</body>
<script type="text/javascript">
var LODOP; //声明为全局变
var contextPath = "<%=contextPath%>";
var num = 1;
var allhkxm=true;//是否改费用单下全是还款项目
var doctorname="",sftime="",username="";
var costno="<%=costno%>";
var cost_organization = null;
var orginfo = "";
var static_titles= "";
$(function(){
	OrderDetail();
	getAlldata();
	printSfxm();
});
//根据costno查询剩余总欠费
function searchSyzqf(usercode,sftime) {
    var zs = 0.0;
    var param = {
    		usercode: usercode,
    		sftime:sftime
    };
    var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/searchZqfByusercode.act';
    $.axse(pageurl, param,
    function(r) {
            zs = r;
    },
    function() {});
    return zs;
}
function getAlldata(){
	var costorderObj = getCostOrderObjBySeqId(costno);
	 $("#nurse").val(costorderObj.nurse);
     $("#costno").val(costorderObj.costno);
	 $("#regno").val(costorderObj.regno);
	 $("#totalcost").val(costorderObj.totalcost);
	 $("#voidmoney").val(costorderObj.voidmoney);
	 $("#shouldmoney").val(costorderObj.shouldmoney);
	 $("#actualmoney").val(costorderObj.actualmoney);
	 $("#arrearmoney").val(costorderObj.y2);
	 $("#discountmoney").val(costorderObj.discountmoney);
	 $('#usercode').val(costorderObj.usercode);
	 $('#username').val(costorderObj.username);
     bindPerUserNameBySeqIdTB("doctor", costorderObj.doctor);
     doctorname = $("#doctor").val();
     sftime = costorderObj.sftime;
     username = costorderObj.username;
     cost_organization = costorderObj.organization; // 开单门诊
     // 根据开单门诊编号，获取对应门诊信息
     orginfo = getOrganizationInfoByOrgCode(cost_organization);
	
     var arrearmoneyAll = searchSyzqf(costorderObj.usercode,costorderObj.sftime);
	 $("#arrearmoneyAll").val(arrearmoneyAll);
	 //预交金余额
	 getSymoneyByUsercode(costorderObj.usercode,costorderObj.sftime);
	 
	 var printtr = '<th>姓名:'+costorderObj.username+'</th><th>卡号: '+costorderObj.usercode+'</th><th>时间:'+sftime+'</th><th>医生:'+doctorname+'</th>';
	 $("#printtr").append(printtr);
	 var printtr2 = '<td colspan="1" align="left">剩余总欠费:'+arrearmoneyAll+'</td>td colspan="2">本次应付: '+costorderObj.shouldmoney+'</td><td colspan="2">本次缴费:'+costorderObj.actualmoney+'</td><td colspan="4">本次欠费:'+costorderObj.y2+'</td>';
	 $("#printtr2").append(printtr2);
}
function printSfxm() {
    var detailurl = contextPath + '/KQDS_CostOrder_DetailAct/printSfxm.act?costno=' + costno;
    $.axse(detailurl, null,
    function(r) {
    	if(r.retState == '0'){
    		var FKFS = r.FKFS;
    		var content = '<span style="color:#00A6C0;padding-left: 10px;">其中：</span>';
    		var contentPrint = '其中 ：';
    		for(var p in FKFS){
    			  content += '<span style="color:#00A6C0;padding-left: 10px;">'+p+':<lable>'+Number(FKFS[p]).toFixed(2)+'</lable></span>';
                  contentPrint += '<span style="padding-left: 10px;">' + p + ':<lable>' + Number(FKFS[p]).toFixed(2) + '</lable></span>';
    		}
    		$('#FKFS').append(content);
    		$('#FKFSPrint').append(contentPrint);
    	}
    },
    function() {
        layer.alert('查询出错！' );
    });
}
function getSymoneyByUsercode(usercode,sftime) {
    var detailurl = contextPath + '/KQDS_MemberAct/getSymoneyByUsercode.act?usercode=' + usercode+'&sftime='+sftime;
    $.axse(detailurl, null,
    function(r) {
    	if(r.retState == '0'){
    		var member = r.member;
    		var content = '';
    		var contentPrint = '';
    		var zmoney = 0;
    		for(var p in member){
    			  content += '<span style="color:#00A6C0;padding-right: 10px;float: left;">'+p+'：<lable>'+Number(member[p]).toFixed(2)+'</lable></span>';
                  contentPrint += '<span style="padding-right: 10px;float: left;">' + p + '：<lable>' + Number(member[p]).toFixed(2) + '</lable></span>';
                  /* zmoney += Number(member[p]).toFixed(2); */
                  zmoney += parseFloat(member[p]);
    		}
    		zmoney = Number(zmoney).toFixed(2);
    		if(content!=''){
    			content = '<span style="color:#00A6C0;padding-right: 10px;float: left;">余额合计：<lable>'+zmoney+'</lable>，其中：</span>'+content;
    			contentPrint = '<span style="padding-right: 10px;float: left;">余额合计：<lable>'+zmoney+'</lable>，其中：</span>'+contentPrint;
    		}
    		$('#SYJE').append(content);
    		$('#SYJEPrint').append(contentPrint);
    	}
    },
    function() {
        layer.alert('查询出错！' );
    });
}
function OrderDetail(){
	var detailurl  = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno='+costno;
	$("#tabledayin").bootstrapTable({
		url:detailurl, 
		dataType: "json",
		cache: false, 
		striped: true,
		onLoadSuccess: function(data) { //加载成功时执行
			 if (data && data.length > 0) {
	            //有数据,开始拼接html
	            var str = "";
	            for (var n = 0; n < data.length; n++) {
	                var obj = data[n];
	                var ys = Number(obj.subtotal) - Number(obj.voidmoney);
	                str += "<tr>" + "   <td width=\"25%\" style=\"text-align: left;\">" + obj.itemname + "</td >" + 
	                "   <td width=\"15%\" style=\"text-align: center;\">" + obj.unit + "</td >" +
	                "	<td width=\"15%\"  style=\"text-align: center;\">" + obj.num + "</td >" + 
	                "	<td width=\"15%\" style=\"text-align: center;\">" + Number(ys).toFixed(2) + "</td >" + 
	                "	<td width=\"15%\" style=\"text-align: center;\">" + obj.y2 + "</td >" + 
	                "	<td width=\"15%\" style=\"text-align: center;\">" + obj.paymoney + "</td>" + "  </tr>";
	            }
	            $("#tbody").append(str);
	        }
	    },
		      columns: [
		              	{title: '治疗项目',field: 'itemname', align: 'center',valign: 'middle',sortable: true,
			              	  formatter:function(value,row,index){  
			              			return '<span style="float:left; text-align:left;" class="time">'+value+'</span>' ;
		                	  }  	
		              	},
						{title: '单位',field: 'unit', align: 'center',valign: 'middle',sortable: true},
						{title: '单价',field: 'unitprice',align: 'center',valign: 'middle', sortable: true,
							  formatter:function(value,row,index){  
			              			return '<span style="text-align:center;">￥'+value+'</span>' ;
		                	  }  
			             },
			             {title: '数量',field: 'num',align: 'center',valign: 'middle'},
			             {title: '折扣%',field: 'discount', align: 'center',valign: 'middle',sortable: true},
						 {title: '应收',field: 'subtotal',align: 'center',valign: 'middle',sortable: true,
				                  formatter:function(value,row,index){  
				                	  var ys = Number(row.subtotal) - Number(row.voidmoney);
				                	  return '<span style=" text-align:center;">￥'+Number(ys).toFixed(2)+'</span>' ;
			                	  } 
		              	 },
		              	 {title: '欠费金额',field: 'arrearmoney',align: 'center',valign: 'middle',sortable: true,
				                  formatter:function(value,row,index){  
				                	  return '<span style=" text-align:center;">￥'+value+'</span>' ;
			                	  } 
		              			},
		              	{title: '缴费金额',field: 'paymoney', align: 'center',valign: 'middle',sortable: true,
				                  formatter:function(value,row,index){ 
				                	  return '<span style=" text-align:center;">￥'+value+'</span>' ;
			                	  } 
		              	}
		          ]
		  });
}
$('#qx').on('click', function(){
	var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(frameindex); //再执行关闭 */
});
//费用明细单
function printfymxd(){
	$("#qz").hide();
    $("#tishi").hide();
    $("#sj").hide();
    if(orginfo){
     	static_titles = orginfo.printName + "费用明细单";
     	$("#titles").html(static_titles);
    }
	LODOP = getLodop();
    LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 1485, "");

    //表格
    LODOP.ADD_PRINT_TABLE(70, "3%", "95%", "280", document.getElementById("div2").innerHTML);

    //页眉
    LODOP.ADD_PRINT_TEXT(3, 250, 300, 20, static_titles);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); //固定标题,设置卫页眉页脚
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    //姓名时间栏目
    LODOP.ADD_PRINT_HTM(25, "3%", "95%", "100%", document.getElementById("hide").innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "LinkedItem", 4);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 3);
    LODOP.ADD_PRINT_HTM(1, 550, 300, 100, "页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);

    LODOP.PRINT();
}
//费用确认单
function printfyqrd(){
	if(orginfo){
     	static_titles = orginfo.printName + "费用确认单";
     	$("#titles").html(static_titles);
    }
	LODOP = getLodop();
    LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 1485, "");

    //表格
    LODOP.ADD_PRINT_TABLE(70, "3%", "95%", "280", document.getElementById("div2").innerHTML);

    //页眉
    LODOP.ADD_PRINT_TEXT(3, 250, 300, 20, static_titles);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); //固定标题,设置卫页眉页脚
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    //姓名时间栏目
    LODOP.ADD_PRINT_HTM(25, "3%", "95%", "100%", document.getElementById("hide").innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "LinkedItem", 4);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 3);
    LODOP.ADD_PRINT_HTM(1, 550, 300, 100, "页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);

    LODOP.PRINT();
}
//费用明细单
function printviewfymxd(){
	var ymoney = Number($("#shouldmoney").val());
	openDayin('费用明细单',ymoney);
}
//费用确认单
function printviewfyqrd(){
	var ymoney = Number($("#shouldmoney").val());
  //弹出打印页面
  openDayin('缴费单',ymoney);
}

function openDayin(titles, ymoney) {
    var usercodeLocal = $('#usercode').val();
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("费用确认单");
    var costurl = "";
    // 默认 a5
    costurl = contextPath + '/KQDS_Print_SetAct/toFyqrdPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&titles=' + titles + '&actualmoney=' + $('#actualmoney').val() + '&arrearmoney=' + $('#arrearmoney').val() + '&ymoney=' + ymoney + '&costno=' + costno + '&costno2=' + costno + '&username=' + username + '&sftime=' + sftime + '&doctor=' + doctorname + '&usercode=' + usercodeLocal;
    if (cost_organization) {
        costurl += '&cost_organization=' + cost_organization + '&arrearmoneyAll=' + $("#arrearmoneyAll").val();
    }
    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['960px', '550px'],
        content: costurl
    });
}
</script>

</html>
