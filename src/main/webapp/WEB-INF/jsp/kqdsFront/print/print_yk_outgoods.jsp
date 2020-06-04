<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
	//合并测试
	// 默认 A5打印 ###########################################
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String outcode = request.getParameter("outcode");
	// 1 a4 2 a5
	String printType = request.getParameter("printType");
	if(printType == null || "".equals(printType)){
		printType = "2"; // 默认a5
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>打印</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.PrintArea.js"></script>
<script language="javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<style type="text/css">
span {
	font-size: 16px;
}

table,th{
	border:none;
	height:18px;
	font-weight:normal;
	font-size: 14px !important;
} 
td{
	text-align:center;
	border: none solid #000;
	height:18px;
	font-weight:normal;
	font-size:14px
}
.allborder td,.allborder th{
	/* border:1px solid #999; */
	border-left:none;
	border-top:none;
	text-align:center;
}
.kqdsCommonBtn{/* 通用按钮     白底蓝字按钮 */
	display:inline-block;
	box-sizing:border-box;
	height:26px;
	line-height:26px;
	background:#fff;
	color:#00A6C0;
	outline:none;
	padding:0 15px;
	border:1px solid #0054B5;
	border-radius:3px;
	margin-right:3px;
	text-decoration:none;
	cursor:pointer;
	text-align:center;
}
.kqdsCommonBtn:active{
	opacity:0.5;
}
</style>
</head> 
<body>   
	<div id="printArea" width="100%">
		<div style="text-align: center;width: 95%;margin: auto;">
			<span id="titles" style="font-size: 20px;"></span>
		</div>
		<div id="hide" style="width: 95%;margin: auto;">
			
		</div>
		<div id="div2" style="padding:0 10px;" >
			<table  border=0 cellSpacing=0 cellPadding=0  width="100%"  bordercolor="#000000" style="border-collapse:collapse;margin: auto;">
				<thead>
					<tr  style="height: 50px;">
						<td id="rkorder" colspan="10" style="text-align:left;"></td>
					</tr>
					<tr class="allborder" style="border-bottom:none;">
						<th width="11%" align="center" style="font-size:14px;">类别</th >
						<th width="12%" align="center" style="font-size:14px;">编号</b></th>
						<th width="18%" align="center" style="font-size:14px;">名称</th>
						<th width="11%" align="center" style="font-size:14px;">规格</th>
						<th width="6%" align="center" style="font-size:14px;">单位</th>
						<th width="12%" align="center" style="font-size:14px;">数量</th>
						<th width="12%" align="center" style="font-size:14px;">单价</th>
						<th width="18%" align="center" style="font-size:14px;">金额</th>
					</tr>
				</thead>
				<tbody id="tbody" class="allborder" style="/* border:1px solid #999;border-top:none; */">
				
				</tbody>
				<tfoot>	
					  <tr  id="qz" align="right" style="height: 70px;font-size:14px;">
					    <th colspan="10" style="text-align: left; font-size:14px;">
						    <span style="font-size:14px;">经手人：<%=person.getUserName() %></span>　
						   <!--  <span style="font-size:14px;">领料人：_____________　</span> -->
						    <span style="font-size:14px;">复核人：_____________　</span>
						</th>    
					  </tr>
				</tfoot>
			</table>
		</div>
	</div>
	<div style="text-align: center;">
		<%
		if("0".equals(printType)){ // a4
			%>
			<!-- <input value="打印" onclick="PrintNoBorderTableA4()" type="button"> -->
			<a href="javacript:;" class="kqdsCommonBtn" onclick="PrintNoBorderTableA4()">打印</a>
			<%
		}else{ // a5
			%>
			<!-- <input value="打印" onclick="PrintNoBorderTable()" type="button"> -->
			<a href="javacript:;" class="kqdsCommonBtn" onclick="PrintNoBorderTable()">打印</a>
			<%
		}
	%>
	</div>
	<% %>
<script>
var outcode="<%=outcode%>";
var parentid = "";
var serverData;
$(function(){
	serverData=getOrganizationInfoByOrgCode('<%=ChainUtil.getCurrentOrganization(request)%>');
	if(serverData.printName!=''){
		$("#titles").html(serverData.printName+"药品出库单");
	}else{
		$("#titles").html("药品出库单");
	}
	rkOrder();
	OrderDetail();
});

function print(){  
	$("#div2").printArea(); 
}  


/* ##打印控件使用说明## 学习地址：http://www.lodop.net/demo.html  http://blog.sina.com.cn/s/blog_721e77e50100q45u.html */

var LODOP; //声明为全局变
function PrintNoBorderTable(){
	LODOP=getLodop();  
	LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
	LODOP.SET_PRINT_PAGESIZE(1,2100,1485,"");
	
	//表格
	LODOP.ADD_PRINT_TABLE(35,"3%","95%","280",document.getElementById("div2").innerHTML);
	
	//页眉
	LODOP.ADD_PRINT_TEXT(13,250,200,20,serverData.printName+"商品出库单");  
    LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);//固定标题,设置卫页眉页脚
	LODOP.SET_PRINT_STYLEA(0,"Horient",2);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	//姓名时间栏目
	LODOP.ADD_PRINT_HTM(25,"3%","95%","100%",document.getElementById("hide").innerHTML);  
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
	/* LODOP.ADD_PRINT_HTM(1,550,300,100,"页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>"); */
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);	
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);		
	
	LODOP.PREVIEW();
};		


function PrintNoBorderTableA4(){
	LODOP=getLodop();  
	LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
	//A4 210*297 MM
	LODOP.SET_PRINT_PAGESIZE(1,2100,2970,"");
	
	//表格
	LODOP.ADD_PRINT_TABLE(35,0,"95%","100%",document.getElementById("div2").innerHTML);
	//页眉
	LODOP.ADD_PRINT_TEXT(13,250,"100",20,serverData.printName+"商品出库单");  
    LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);//固定标题,设置卫页眉页脚
	LODOP.SET_PRINT_STYLEA(0,"Horient",2);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	//姓名时间栏目
	LODOP.ADD_PRINT_HTM(25,3,"100%","100%",document.getElementById("hide").innerHTML);  
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
	LODOP.ADD_PRINT_HTM(1,550,300,100,"页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",1);	
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);		
	
	LODOP.PREVIEW();
};	
function rkOrder(){
	var detailurl  = '<%=contextPath%>/HUDH_YkzzDrugsOutAct/findAllDrugsOut.act?accurateOutcode='+outcode;
	$.axse(detailurl, null, function(data) {
		if(data&&data.length>0){
			//有数据,开始拼接html
			var str="";
			for(var n=0;n<data.length;n++){
				var obj =data[n];
				str+="   <span  style=\"text-align: left;float:left;font-size:14px;\">单据编号："+
					""+obj.outcode+"</span >"+
					"<span  style=\"text-align: left;margin-left: 15px;float:left;font-size:14px;\">出库时间："+
					""+obj.cktime.substring(0,10)+"</span >"+
					"<span  style=\"text-align: center;margin-left: 15px;float:left;font-size:14px;\">出库方式："+
					""+getIntype(obj.outtype)+"</span >"+
					"<span  style=\"text-align: center;margin-left: 15px;float:left;font-size:14px;\">供应商："+
					""+obj.manu_name+"</span >"+
					"<span style=\"text-align: center;float:left;margin-left: 15px;font-size:14px;\">附加说明："+
					""+obj.outremark+"</span >"+
					"<span  style=\"text-align: center;margin-left: 15px;float:left;font-size:14px;\">摘要："+
					""+obj.zhaiyao+"</span >";
				parentid = obj.id;
			}
			$("#rkorder").append(str);
		}
	}, function() {
		layer.alert('查询出错！', {
		});
	});
	
}

function getIntype(intype){
	if(intype == 0) {
		return "换货出库";
	}else if(intype == 2){
		return "退货出库";
	}else if(intype == 4){
		return "其他出库";
	}else if(intype == 6){
		return "备药出库";
	}
}
function OrderDetail(){
	var detailurl  = '<%=contextPath%>/HUDH_YkzzDrugsOutAct/findDetailByParendId.act?parentid='+parentid;
	$.axse(detailurl, null, function(data) {
		if(data&&data.length>0){
			//有数据,开始拼接html
			var str="";
			var nums=0,zmomey=0;
			for(var n=0;n<data.length;n++){
				var obj =data[n];
				str+="<tr>"+
				"   <td  style=\"text-align: center;font-size:14px;\">"+obj.drugs_typetwo_name+"</td >"+
				"   <td  style=\"text-align: center;font-size:14px;\">"+obj.orderno+"</td >"+
				"   <td  style=\"text-align: center;font-size:14px;\">"+obj.chemistry_name+"</td >"+
				"   <td  style=\"text-align: center;font-size:14px;\">"+obj.pharm_spec+"</td >"+
				"	<td  style=\"text-align: center;font-size:14px;\">"+obj.company+"</td >"+
				"	<td  style=\"text-align: center;font-size:14px;\">"+obj.ck_quantity+"</td >"+
				"	<td  style=\"text-align: center;font-size:14px;\">"+Number(obj.nuit_price).toFixed(3)+"</td >"+
				"	<td  style=\"text-align: center;font-size:14px;\">"+Number(obj.amount).toFixed(3)+"</td >"+
				"  </tr>";
				nums += Number(obj.ck_quantity);
				zmomey += Number(obj.amount);
			}
		$("#tbody").append(str);
		var toolhtml ="<tr>"+
		"   <td  style=\"text-align: center;\"></td >"+
		"   <td  style=\"text-align: center;\"></td >"+
		"   <td  style=\"text-align: center;\"></td >"+
		"   <td  style=\"text-align: center;\"></td >"+
		"	<td  style=\"text-align: center;\"></td >"+
		"	<td  style=\"text-align: center;font-size:14px;\">小计："+Number(nums)+"</td >"+
		"	<td  style=\"text-align: center;\"></td >"+
		"	<td  style=\"text-align: center;font-size:14px;\">小计："+Number(zmomey).toFixed(3)+"</td>"+
		"  </tr>";
		$("#tbody").append(toolhtml);		
		}
	}, function() {
		layer.alert('查询出错！', {
		});
	});
	
}
</script>
</body>
</html>
