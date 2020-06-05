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
	String incode = request.getParameter("incode");
	// 1 a4 2 a5
	String printType = request.getParameter("printType");
	if(printType == null || "".equals(printType)){
		printType = "2"; // 默认a5
	}
	String status =request.getParameter("status");
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
.kqdsCommonBtn{	
	display: inline-block;
    box-sizing: border-box;
    height: 26px;
    line-height: 26px;
    background: #fff;
    color: #00A6C0;
    outline: none;
    padding: 0 15px;
    border: 1px solid #0054B5;
    border-radius: 3px;
    margin-right: 3px;
    text-decoration: none;
    cursor: pointer;
    text-align: center;
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
		<div id="hide" style="width: 95%;margin: auto; font-size: 10px">
			
		</div>
		<div id="div2" >
			<table  border=0 cellSpacing=0 cellPadding=0  width="100%"  bordercolor="#000000" style="border-collapse:collapse;margin: auto;">
				<thead>
					<tr  style="height: 50px;">
						<td id="rkorder" colspan="13" style="border-bottom:1px solid #333;text-align:left;"></td>
					</tr>
					<tr class="allborder" style="height: 30px;border-bottom:none;">
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">商品名称</td >
						<td width="7%" align="center" style="border-bottom:1px solid #333; font-size: 12px">剂型</td>
						<td width="7%" align="center" style="border-bottom:1px solid #333; font-size: 12px">单位</td>
						<td width="7%" align="center" style="border-bottom:1px solid #333; font-size: 12px">规格</td>
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">生产厂家</td>
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">批号</td>
						<td width="7%" align="center" style="border-bottom:1px solid #333; font-size: 12px">入库数量</td>
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">入库单价</td>
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">入库金额</td>
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">零售价</td>
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">生产日期</td>
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">有效期</td>
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">批准文号</td>
					</tr>
				</thead>
				<tbody id="tbody" class="allborder" style="/* border:1px solid #999; */border-top:none; font-size: 12px">
				</tbody>
				<tfoot>		
					  <tr  id="qz" align="right" style="height: 50px;">
					    <td colspan="13" style="text-align: left;">
						    <span style="font-size: 12px">经手人：<%=person.getUserName() %></span>　
						    <!-- <span style="font-size: 12px">仓管员：_____________　</span> -->
						    <span style="font-size: 12px">复核人：_____________　</span>
						</td>    
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
var incode="<%=incode%>";
var status="<%=status%>";
var parentid = "";
var serverData;
$(function(){
	serverData=getOrganizationInfoByOrgCode('<%=ChainUtil.getCurrentOrganization(request)%>');
	if(serverData.printName!=''){
		$("#titles").html(serverData.printName+"药品入库单");
	}else{
		$("#titles").html("药品入库单");
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
	LODOP.ADD_PRINT_TEXT(3,250,300,20,serverData.printName+"药品入库单"); 
	LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);//固定标题,设置卫页眉页脚
	LODOP.SET_PRINT_STYLEA(0,"Horient",2);
	LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	//姓名时间栏目
	LODOP.ADD_PRINT_HTM(25,"3%","95%","100%",document.getElementById("hide").innerHTML);  
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",5);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);	
	//LODOP.ADD_PRINT_HTM(1,550,300,100,"页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
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
	LODOP.ADD_PRINT_TABLE(35,0,"100%","100%",document.getElementById("div2").innerHTML);
	//页眉
	LODOP.ADD_PRINT_TEXT(3,250,"100",20,serverData.printName+"药品入库单");  
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
	var detailurl  = '<%=contextPath%>/HUDH_YkzzDrugsInAct/findAllDrugsIn.act?accurateIncode='+incode;
	$.axse(detailurl, null, function(data) {
		if(data&&data.length>0){
			//有数据,开始拼接html
			var str="";
			for(var n=0;n<data.length;n++){
				var obj =data[n];
				//alert(obj);
				//console.log(obj);
				str+="<span  style=\"text-align: left;float:left;margin-right:15px; font-size: 12px\">单据编号："+
					""+obj.incode+"</span >"+
					"<span  style=\"text-align: left;margin-left: 15px;float:left;margin-right:15px; font-size: 12px\">入库时间："+
					""+obj.rktime.substring(0,10)+"</span >"+
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入库方式："+
					""+getIntype(obj.intype)+"</span >"+
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">供应商："+
					""+obj.manu_name+"</span >"+   "<br>"+
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">附加说明："+
					""+obj.inremark+"</span >"+
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;摘要："+ 
					"" + obj.zhaiyao+"</span >";
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
		return "采购入库";
	}else if(intype == 2){
		return "换货入库";
	}else if(intype == 4){
		return "其他入库";
	}
}
function OrderDetail(){
	/* if(status==1){ */
		var detailurl  = '<%=contextPath%>/HUDH_YkzzDrugsInAct/findDetailByParendId.act?parentid='+parentid;
	<%-- }else{
		var detailurl  = '<%=contextPath%>/HUDH_YkzzDrugsInAct/findDetailByAuditParendId.act?parentid='+parentid;	
	} --%>
	$.axse(detailurl, null, function(data) {
		if(data&&data.length>0){
			//有数据,开始拼接html
			var str="";
			var nums=0,zmomey=0;
			for(var n=0;n<data.length;n++){
				var obj =data[n];
				str+="<tr>"+
				"   <td  style=\"text-align: center;font-size: 10px\">"+obj.good_name+"</td >"+
				"   <td  style=\"text-align: center;font-size: 10px\">"+obj.pharm_dos+"</td >"+
				"   <td  style=\"text-align: center;font-size: 10px\">"+obj.company+"</td >"+
				"   <td  style=\"text-align: center;font-size: 10px\">"+obj.pharm_spec+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.manufacturers_name+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.batchnum+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.quantity+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.nuitprice+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.amount+"</td>"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.drug_retail_price+"</td>"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.createdate+"</td>"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.valid+"</td>"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.contry_code+"</td >"+
				"  </tr>";
				nums = Number(obj.quantity)+Number(nums);
				zmomey = Number(obj.amount)+Number(zmomey);
			}
		$("#tbody").append(str);
		var toolhtml ="<tr>"+
		"   <td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
		"   <td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
		"   <td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
		"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
		"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
		"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
		"	<td  colspan=\"2\" style=\"text-align: left;border-top:1px solid #333;height:32px;\">小计："+Number(nums)+"</td >"+
		"	<td  colspan=\"2\" style=\"text-align: left;border-top:1px solid #333;height:32px;\">小计："+Number(zmomey).toFixed(3)+"</td >"+
		"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
		"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
		"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td >"+
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
