<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	// 默认 A5打印 ###########################################
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode = "";
	}
	
	String seqIds = request.getParameter("seqIds"); // 多个id，逗号分隔
	if(seqIds == null){
		seqIds = "";
	}
	
	if(seqIds.endsWith(",")){
		seqIds = seqIds.substring(0,seqIds.length() - 1);
	}
	
	String titles = "退费确认单";
	String cost_organization = request.getParameter("cost_organization");
	if(cost_organization == null){
		cost_organization = "";
	}
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
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
		<div style="text-align: center;width: 95%;margin: 18px;">
			<span id="titles" style="font-size: 20px;font-weight:bold;"></span>
		</div>
		<div id="hide" style="width:100%;margin: auto;">
			<table style="width:100%;font-weight:bold;border-bottom:1px solid #333;">
				<tr>
					<th style="text-align:left;font-size:14px;">
						姓名:&nbsp;&nbsp;&nbsp;&nbsp;
       			<span id="username" style="font-weight:bold;font-size:14px;"></span>&nbsp;&nbsp;&nbsp;&nbsp;
       			退款时间:&nbsp;&nbsp;&nbsp;&nbsp;<span id="tktime" style="font-weight:bold;font-size:14px;"></span>
					</th>
				</tr>
			</table>
		</div>
		<div id="div2" >
			<table  border=0 cellSpacing=0 cellPadding=0  width="100%"  bordercolor="#000000" style="border-collapse:collapse;margin: auto;">
				<thead>
					<tr>
						<th style="border-bottom:1px solid #333;height:26px;font-size:14px;" width="25%" align="left">治疗项目</th>
						<th style="border-bottom:1px solid #333;height:26px;font-size:14px;" width="15%" align="center">单位</th >
						<th style="border-bottom:1px solid #333;height:26px;font-size:14px;" width="10%"  align="center">单价</b></th>
						<th style="border-bottom:1px solid #333;height:26px;font-size:14px;" width="10%"  align="center">数量</th>
						<th style="border-bottom:1px solid #333;height:26px;font-size:14px;" width="15%" align="center">缴费金额</th>
						<th style="border-bottom:1px solid #333;height:26px;font-size:14px;" width="10%" align="center">退费数量</th>
						<th style="border-bottom:1px solid #333;height:26px;font-size:14px;" width="15%" align="center">退费金额</th>
					</tr>
				</thead>
				<tbody id="tbody">
				
				</tbody>
				<tfoot>
					  <tr align="left" style="border-top:Black solid 1px;border-bottom:Black solid 1px;padding-top:5px;font-size:14px; ">
					  </tr>
					  <tr id="tishi">
					    <th width="100%" colspan="9"><span style="font-size:14px;font-weight:normal"></span></th>
					  </tr>
					  <tr  id="qz" align="right">
					    <th width="100%" colspan="9" style="text-align: right;font-size:14px;">客户签名：____________________　</th>
					  </tr>
				</tfoot>
			</table>
		</div>
	</div>
	<div style="text-align: center;">
		<%
		if("1".equals(printType)){ // a4
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
<script>
var contextPath = '<%=contextPath %>';
var seqIds = "<%=seqIds %>";
var usercode = "<%=usercode %>";
var titles = "<%=titles %>";
var cost_organization = "<%=cost_organization%>";
$(function() {
    if(cost_organization){
    	// 根据开单门诊编号，获取对应门诊信息
        var orginfo = getOrganizationInfoByOrgCode(cost_organization);
        if(orginfo){
        	static_titles = orginfo.printName + titles;
        	$("#titles").html(static_titles);
        }
    }
	
    getGiveItemList();
    getJbxx();
});

function print() {
    $("#div2").printArea();
}

/* ##打印控件使用说明## 学习地址：http://www.lodop.net/demo.html  http://blog.sina.com.cn/s/blog_721e77e50100q45u.html */

var LODOP; //声明为全局变
function PrintNoBorderTable() {
    LODOP = getLodop();
    LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 1485, "");

    //表格
    LODOP.ADD_PRINT_TABLE(70, "15", "95%", "280", document.getElementById("div2").innerHTML);

    //页眉
    LODOP.ADD_PRINT_TEXT(3, 250, 300, 20, "<%=ChainUtil.getOrgPrintName(request)%><%=titles%>");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); //固定标题,设置卫页眉页脚
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    //姓名时间栏目
    LODOP.ADD_PRINT_HTM(45, "18", "95%", "100%", document.getElementById("hide").innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "LinkedItem", 4);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 3);
    LODOP.ADD_PRINT_HTM(1, 550, 300, 100, "页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);

    LODOP.PREVIEW();
};

function PrintNoBorderTableA4() {
    LODOP = getLodop();
    LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
    //A4 210*297 MM
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 2970, "");

    //表格
    LODOP.ADD_PRINT_TABLE(70, 0, "100%", "100%", document.getElementById("div2").innerHTML);
    //页眉
    LODOP.ADD_PRINT_TEXT(3, 250, 300, 20, "<%=ChainUtil.getOrgPrintName(request)%><%=titles%>");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); //固定标题,设置卫页眉页脚
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    //姓名时间栏目
    LODOP.ADD_PRINT_HTM(25, 3, "100%", "100%", document.getElementById("hide").innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "LinkedItem", 4);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 3);
    LODOP.ADD_PRINT_HTM(1, 550, 300, 100, "页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);

    LODOP.PREVIEW();
};

function getGiveItemList() {

	var tmp_refundid = null;
	
    var idArray = seqIds.split(",");
    var str = "";
    for (var i = 0; i < idArray.length; i++) {
        if (idArray[i] == '') {
            continue;
        }

        var detailurl = '<%=contextPath%>/KQDS_Refund_DetailAct/selectDetail.act?seqId=' + idArray[i];
        $.axse(detailurl, null,
        function(data) {
            if (data.retState == 0) {
            	
            	
                //有数据,开始拼接html
				var obj = data.data;
                
				cost_organization = obj.organization; // 赠送门诊编号
                
				tmp_refundid = obj.refundid;
                
				str += "<tr>" 
					+ "   <td width=\"\" style=\"text-align: left;height:24px;font-size:14px;\">" + obj.itemname + "</td >" 
					+ "   <td width=\"15%\" style=\"text-align: center;height:24px;font-size:14px;\">" + obj.unit + "</td >" 
					+ "   <td width=\"10%\"  style=\"text-align: center;height:24px;font-size:14px;\">" + obj.unitprice + "</td >" 
					+ "	  <td width=\"10%\"  style=\"text-align: center;height:24px;font-size:14px;\">" + obj.num + "</td >" 
					+ "	  <td width=\"15%\" style=\"text-align: center;height:24px;font-size:14px;\">" + obj.paymoney + "</td >" 
					+ "	  <td width=\"10%\" style=\"text-align: center;height:24px;font-size:14px;\">" + obj.tknum + "</td >" 
					+ "	  <td width=\"15%\" style=\"text-align: center;height:24px;font-size:14px;\">-" + obj.tkmoney + "</td >" + "  </tr>";
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });

    }
    
    if(tmp_refundid){
        var reqUrl = '<%=contextPath%>/KQDS_RefundAct/selectDetail.act?seqId=' + tmp_refundid;
        $.axse(reqUrl, null,
        function(data) {
            if (data.retState == 0) {
            	if(data.data.tktime){
            		$("#tktime").html(data.data.tktime);
            	}
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }

    $("#tbody").append(str);

}
//基本信息
function getJbxx() {
	var baseinfo = getBaseInfoByUserCode(usercode);
	if(baseinfo){
		$('#username').html(baseinfo.username);
	}
}
</script>
</body>
</html>