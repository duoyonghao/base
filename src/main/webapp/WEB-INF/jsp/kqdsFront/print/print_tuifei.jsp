<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	//默认 A5打印 ###########################################
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String cztime = request.getParameter("cztime");
	String memberno = request.getParameter("memberno");
	String username = request.getParameter("username");
	String money = request.getParameter("money");
	money = money.replaceAll(",", "");
	String givemoney = request.getParameter("givemoney");
	
	String tips = "退费确认单";

	String totalmoney = request.getParameter("totalmoney");
	
	String ygivemoney = request.getParameter("ygivemoney"); // 赠送余额
	String ymoney = request.getParameter("ymoney"); // 余额
	String ytotalmoney = request.getParameter("ytotalmoney");
	
	// 以下几个参数没有被引用
	String qtmoney = request.getParameter("qtmoney");
	String yhkmoney = request.getParameter("yhkmoney");
	String xjmoney = request.getParameter("xjmoney");
	String recordId = request.getParameter("recordId"); // 主键
	String tksh = request.getParameter("tksh"); // 退费申请列表传入 tksh=1
	if(tksh == null || "".equals(tksh)){
		tksh = "0"; 
	}
	String usercode = request.getParameter("usercode");
	// wxmoney
	// zfbmoney
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
<title>打印会员卡退费确认单</title>
<style type="text/css">
th {
	text-align: -internal-center;
}
/*打印按钮样式  */
.hykdy_bton {
	width: 120px;
	height: 30px;
	color: #0e7cc9;
	border-radius: 24px;
	border: 1px solid #0e7cc9;
	background-color: #fff;
}

.hykdy_bton:hover {
	width: 120px;
	height: 30px;
	background-color: #0e7cc9;
	color: #fff;
	border-radius: 24px;
	border: 1px solid #0e7cc9;
	outline: none;
	cursor: pointer;
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
	<div id="printArea" style="padding:0 15px;text-align: center;margin:0 auto;">
		<div style="margin-top: 5px; padding-bottom: 5px; ">
			<div style="height:35px;width:100%;text-align: center;">
				<span id="titles" style="font-size: 20px;font-weight:bold; /* margin:30px auto; */ display: block;"></span>
			</div>
			<!-- <div style="margin-left:30px;border-bottom: 1px solid #999;"></div> -->
			<table style="width: 100%; border-bottom:1px solid #333;">
				<tr>
					<th style="width:33%;text-align:left;">会员卡号: <%=memberno%></th>
					<th style="width:33%;text-align:left;">会员姓名: <%=username%></th>
					<th style="width:33%;text-align:left;">退费时间: <%=cztime%></th>
				</tr>
			</table>
			<table style="width: 100%; /* margin: 30px 0 30px 0; */ text-align: left;margin:0 auto;">
				<tr style="height:28px;">
					<td style="width:33%;text-align:left;font-size:14px;">本次退费充值金额：￥<%=money%></td>
					<td style="width:33%;text-align:left;font-size:14px;">本次退费赠送金额：￥<%=givemoney%></td>
					<td style="width:33%;text-align:left;font-size:14px;">本次退费小计：￥<%=totalmoney%></td>
				</tr>
				<tr style="height:28px;">
					<td style="width:33%;text-align:left;font-size:14px;">充值余额：￥<%=ymoney%></td>
					<td style="width:33%;text-align:left;font-size:14px;">赠送余额：￥<%=ygivemoney%></td>
					<td style="width:33%;text-align:left;font-size:14px;">余额小计：￥<%=ytotalmoney%></td>
				</tr>
			</table>
			<table style="width:100%;border-top:1px solid #333;">
				<tr align="left" style="height:32px;border-top:Black solid 1px;border-bottom:Black solid 1px;padding-top:5px; ">
			  		<td colspan="3" id="FKFS" style="text-align: left;font-size:14px;"></td>
			    </tr>
			    <tr align="left" style="height:32px;border-top:Black solid 1px;border-bottom:Black solid 1px;padding-top:5px; ">
			    	<td colspan="3">
			    		<span style="width:50%;float:left;font-size:14px;" id="deptAddress"></span>
			    		<span style="width:50%;float:left;font-size:14px;" id="telNo"></span>
			    	</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="text-align: center;margin-top: 60px; ">
		<%
		if("1".equals(printType)){ // a4
			%>
			<!-- <input value="打印" onclick="PrintNoBorderTableA4()" type="button"> -->
			<a href="javascript:;" onclick="PrintNoBorderTableA4()" class="kqdsCommonBtn">打印</a>
			<%
		}else{ // a5
			%>
			<!-- <input value="打印" onclick="PrintNoBorderTable()" type="button"> -->
			<a href="javascript:;" onclick="PrintNoBorderTable()" class="kqdsCommonBtn">打印</a>
			<%
		}
		%>
	</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<!--  jquery 打印插件 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.PrintArea.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script language="javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script>
var contextPath = "<%=contextPath%>";
var recordId = "<%=recordId%>";
var usercode = "<%=usercode%>";
var tksh = "<%=tksh%>";
$(function(){
	// 根据开单门诊编号，获取对应门诊信息
    var orginfo = getOrganizationInfoByOrgCode('<%=cost_organization%>');
    if(orginfo){
    	static_titles = orginfo.printName + '<%=tips%>';
    	$("#titles").html(static_titles);
    	$("#telNo").html("热线：" + orginfo.telNo);
    	$("#deptAddress").html("地址：" + orginfo.deptAddress);
    }
	
	printSfxm();
});
function print(){  
	$("#printArea").printArea(); 
} 

var LODOP; //声明为全局变
function PrintNoBorderTable(){
	LODOP=getLodop();  
	LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
	LODOP.SET_PRINT_PAGESIZE(1,2100,1485,"");
	//姓名时间栏目
	LODOP.ADD_PRINT_HTM(25,35,"90%","100%",document.getElementById("printArea").innerHTML);  
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);
	
	LODOP.PREVIEW();
};	


function PrintNoBorderTableA4(){
	LODOP=getLodop();  
	LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
	//A4 210*297 MM
	LODOP.SET_PRINT_PAGESIZE(1,2100,2970,"");
	//姓名时间栏目
	LODOP.ADD_PRINT_HTM(25,35,"90%","100%",document.getElementById("printArea").innerHTML);  
	LODOP.SET_PRINT_STYLEA(0,"LinkedItem",4);
    LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
	LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
	LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
	LODOP.SET_PRINT_STYLEA(0,"Horient",3);
	
	LODOP.PREVIEW();
};	
function printSfxm() {
	var detailurl = "";
	if(tksh == "1"){
		detailurl = '<%=contextPath%>/KQDS_Member_RecordAct/printSfxm.act?recordId=' + recordId+'&table=KQDS_MEMBER_RECORD_SH';
	}else{
		detailurl = '<%=contextPath%>/KQDS_Member_RecordAct/printSfxm.act?recordId=' + recordId+'&table=KQDS_Member_Record';
	}
    $.axse(detailurl, null,
    function(r) {
    	if(r.retState == '0'){
    		var FKFS = r.FKFS;
    		var content = '其中 ：';
    		for(var p in FKFS){
    			  content += '<span style="padding-left: 10px;">'+p+':<lable>'+Number(FKFS[p]).toFixed(2)+'</lable></span>';
    		}
    		$('#FKFS').append(content);
    	}
    },
    function() {
        layer.alert('查询出错！' );
    });
}
</script>
</body>
</html>
