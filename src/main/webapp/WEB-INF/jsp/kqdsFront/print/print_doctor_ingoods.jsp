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
						<td id="rkorder" colspan="10" style="border-bottom:1px solid #333;text-align:left;"></td>
					</tr>
					<tr class="allborder" style="height: 30px;border-bottom:none;">
						<td width="7%" align="center" style="border-bottom:1px solid #333; font-size: 12px">商品编号</td>
						<td width="15%" align="center" style="border-bottom:1px solid #333; font-size: 12px">商品名称</td>
						<td width="7%" align="center" style="border-bottom:1px solid #333; font-size: 12px">规格</td>
						<td width="7%" align="center" style="border-bottom:1px solid #333; font-size: 12px">单位</td>
						<td width="10%" align="center" style="border-bottom:1px solid #333; font-size: 12px">领料单价</td>
						<td width="9%" align="center" style="border-bottom:1px solid #333; font-size: 12px">领料数量</td>
						<!-- <td width="11%" align="center" style="border-bottom:1px solid #333; font-size: 12px">领料金额</td> -->
						<td width="8%" align="center" style="border-bottom:1px solid #333; font-size: 12px">有效期</td>
						<td width="10%" align="center" style="border-bottom:1px solid #333; font-size: 12px">领料备注</td>
						<td width="9%" align="center" style="border-bottom:1px solid #333; font-size: 12px">领料人</td > 
						<td width="7%" align="center" style="border-bottom:1px solid #333;border-top:1px solid #333; font-size: 12px">批号</td>
					</tr>
				</thead>
				<tbody id="tbody" class="allborder" style="/* border:1px solid #999; */border-top:none; font-size: 12px">
				</tbody>
				<tfoot>		
					  <tr  id="qz" align="right" style="height: 50px;">
					    <td colspan="10" style="text-align: left;">
						    <span style="font-size: 12px">经手人：<%=person.getUserName() %></span>　
						    <span style="font-size: 12px">仓管员：_____________　</span>
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
var serverData;
$(function(){
	serverData=getOrganizationInfoByOrgCode('<%=ChainUtil.getCurrentOrganization(request)%>');
	if(serverData.printName!=''){
		$("#titles").html(serverData.printName+"医生领料单");
	}else{
		$("#titles").html("医生领料单");
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
	LODOP.ADD_PRINT_TEXT(3,250,300,20,serverData.printName+"医生领料单"); 
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
	LODOP.ADD_PRINT_TEXT(3,250,"100",20,serverData.printName+"商品入库单");  
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
	var detailurl  = '<%=contextPath%>/HUDH_Goods_DoctorPickInAct/findGoodsDoctorPickByIncode.act?incode='+incode;
	$.axse(detailurl, null, function(data) {
		if(data&&data.length>0){
			//有数据,开始拼接html
			var str="";
			for(var n=0;n<data.length;n++){
				var obj =data[n];
				//alert(JSON.stringify(obj));
				//console.log(obj);
				str+="<span  style=\"text-align: left;float:left;margin-right:15px; font-size: 12px\">单据编号："+
					""+obj.incode+"</span >"+
					"<span  style=\"text-align: left;float:left;margin-right:15px; font-size: 12px\">入库仓库："+
					""+obj.housename+"</span >"+
					"<span  style=\"text-align: left;margin-left: 15px;float:left;margin-right:15px; font-size: 12px\">入库时间："+
					""+obj.rktime.substring(0,10)+"</span >"+
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;入库方式："+
					""+obj.intype+"</span >"+ "<br>"+
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">供应商："+
					""+obj.suppliername+"</span >"+  
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">附加说明："+
					""+obj.inremark+"</span >"+
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;摘要："+ 
					"" + obj.zhaiyao+"</span >" + 
					"<span  style=\"text-align: center;float:left;margin-right:15px; font-size: 12px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进货时间："+ 
					"" + obj.stocktime+"</span >";
			}
			$("#rkorder").append(str);
		}
	}, function() {
		layer.alert('查询出错！', {
		});
	});
	
}
function OrderDetail(){
	var detailurl  = '<%=contextPath%>/GoodsDoctorPickInDetailAct/findDoctorPickInDetailByIncode.act?incode='+incode;
	$.axse(detailurl, null, function(data) {
		if(data&&data.length>0){
			//有数据,开始拼接html
			var str="";
			var nums=0,zmomey=0;
			for(var n=0;n<data.length;n++){
				var obj =data[n];
				//alert(JSON.stringify(obj));
				//console.log(obj);
				str+="<tr>"+
				"   <td  style=\"text-align: center;font-size: 10px\">"+obj.goodscode+"</td >"+
				"   <td  style=\"text-align: center;font-size: 10px\">"+obj.goodsname+"</td >"+
				"   <td  style=\"text-align: center;font-size: 10px\">"+obj.goodsnorms+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.goodsunit+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.nuitprice+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.quantity+"</td >"+
				//"	<td  style=\"text-align: center;font-size: 10px\">"+obj.amount+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.createdate+"</td>"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.remark+"</td >"+
				"   <td  style=\"text-align: center;font-size: 10px\">"+obj.userName+"</td >"+
				"	<td  style=\"text-align: center;font-size: 10px\">"+obj.batchnum+"</td >"+
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
		"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;font-size: 10px\">小计："+Number(nums)+"</td >"+
		//"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;font-size: 10px\">小计："+Number(zmomey).toFixed(2)+"</td >"+
		"	<td  style=\"text-align: center;border-top:1px solid #333;height:32px;\"></td>"+
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
