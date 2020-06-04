<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String outprocessingsheetno = request.getParameter("outprocessingsheetno");
	String doctorno = request.getParameter("doctorno"); // 医生编号
	String fajiantime = request.getParameter("fajiantime"); // 发件时间
	String processingfactory = request.getParameter("processingfactory"); // 加工单位
	String type = request.getParameter("type"); // 类型
	String yaoqiu = request.getParameter("yaoqiu"); // 要求
	String usercode = request.getParameter("usercode");
	
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
<title>加工单打印</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.PrintArea.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<style type="text/css">
span {
	font-size: 18px;
}



td {
	border: none solid #000;
	height: 20px;
	font-weight: normal;
	font-size: 18px
}

th {
	border: none solid #000;
	height: 20px;
	font-size: 18px
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
		<div style="height:40px;text-align: center;width: 100%;margin: auto;">
			<span id="titles" style="font-size: 20px;"></span>
		</div>
		
		
		<div id="hide" style="width: 100%;margin: 0 auto;text-align:center;">
		
			<!-- <div style="border-bottom: 1px solid #999;"></div> -->
		
			<table style="width: 100%;text-align: left;border-bottom:1px solid #333;">
				<tr>
					<th align="left" with="90"  style="font-size:14px;">
						加工单位：
					</th>	
					<th colspan="5" align="left" id="processingfactorySpan" style="/* text-decoration:underline; */font-size:14px;">
					</th>
				</tr>
			
				<tr>
					<th width="90px;" align="left" style="font-size:14px;">患者姓名：</<th><th width="70px;" id="usernameSpan" align="left" style="font-size:14px;"></<th>
					<th width="60px;" align="right" style="font-size:14px;">性别:</th><th style="font-size:14px;" width="40px;" id="sexSpan" align="left"></<th>
					<th width="60px;" align="right" style="font-size:14px;">年龄:</th><th style="font-size:14px;" width="40px;" id="ageSpan" align="left"></<th>
					<th width="60px;" align="right" style="font-size:14px;">医生: </th><th style="font-size:14px;" width="70px;" id="doctornoSpan" align="left"></<th>
					<th width="100px;" align="right" style="font-size:14px;">发件时间：</th><th style="font-size:14px;" width="180px;" id="sendtime" align="left"><%=fajiantime%></th>
				</tr>
			</table>
		
			<!-- <div style="border-bottom: 1px solid #999;"></div> -->
		</div>
		<div id="div2">
			<table border=0 cellSpacing=0 cellPadding=0 width="100%" id="dataTable"
				bordercolor="#000000" style="border-collapse: collapse;margin: auto;">
				<thead>
					<tr>
						<th style="border-bottom:1px solid #333;font-size:14px;" align="left" width="25%">项目名称</th>
						<th style="border-bottom:1px solid #333;font-size:14px;" width="10%">数量</th>
						<th style="border-bottom:1px solid #333;font-size:14px;" width="10%">单位</th>
						<th style="border-bottom:1px solid #333;font-size:14px;" width="15%">牙位</th>
						<th style="border-bottom:1px solid #333;font-size:14px;" width="10%">色号</th>
						<th style="border-bottom:1px solid #333;font-size:14px;" width="30%">制作要求</th>
					</tr>
				</thead>
				<tbody id="jglist">
				</tbody>
				<tfoot align="left">
					<tr>
						<td style="height:5px;" colspan="6">
							<div style="border-bottom: 1px solid #999;"></div>
						</td>
					</tr> 
					
					<tr>
						<td align="left" colspan="4" style="height:32px;font-size:14px;">
							加工类型：
							<span align="left" style="/* text-decoration:underline; */font-size:14px;">
								<%=type%>
							</span>
						</td>	
						<td align="left" colspan="2" style="height:32px;font-size:14px;">
							经手人：____________________
						</td>	
					</tr>
					
					<tr align="left">
						<td colspan="6" >
							<div style="float:left;font-size:14px;">设计要求：</div>
							<div style="text-decoration:underline;float:left;font-size:14px;"><%=yaoqiu%></div>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	</div>
	<div style="text-align: center;margin-top:10px;">
		<%
		if("1".equals(printType)){ // a4
			%>
			<!-- <input value="打印" onclick="PrintNoBorderTableA4()" type="button"> -->
			<a href="javascript:;" onclick="PrintNoBorderTableA4()" class="kqdsCommonBtn">打印</a>
			<%
		}else{ // a5
			%>
			<!-- <input value="打印" onclick="PrintNoBorderTable()" type="button"> -->
			<a href="javascript:;" onclick="PrintNoBorderTableA4()" class="kqdsCommonBtn">打印</a>
			<%
		}
	%>
	</div>
<script>
var contextPath = '<%=contextPath%>';
var processingfactory = '<%=processingfactory%>';
var doctorno = '<%=doctorno%>';
var outprocessingsheetno = '<%=outprocessingsheetno%>';
var usercode = '<%=usercode%>';
var static_titles = null;
$(function() {
	
    // 根据开单门诊编号，获取对应门诊信息
    var orginfo = getOrganizationInfoByOrgCode('<%=cost_organization%>');
    if(orginfo){
    	static_titles = orginfo.printName + "加工单";
    	$("#titles").html(static_titles);
    }

    if (processingfactory != null && processingfactory != '') {
        $("#processingfactorySpan").html(processingfactory);
    }

    if (doctorno != null && doctorno != '') {
        $("#doctornoSpan").html(doctorno);
    }

    if (usercode != null && usercode != '') {
        $("#usernameSpan").html(getHzNameByusercodeTB(usercode).username);
    }

    if (outprocessingsheetno != null && outprocessingsheetno != '') {
        loadlist4print(outprocessingsheetno);
    }
    
    var baseinfo = getBaseInfoByUserCode(usercode);
	if(baseinfo){
		$('#sexSpan').html(baseinfo.sex);
        $('#ageSpan').html(baseinfo.age);
        $('#username').html(baseinfo.username);
	}
    
    
});

function loadlist4print(outprocessingsheetno) {
    var content = "";
    var pageurl = '<%=contextPath%>/KQDS_OUTPROCESSING_SHEET_DETAILAct/getListNoPage.act?outprocessingsheetno=' + outprocessingsheetno;
    $.axse(pageurl, null,
    function(data) {
        var datalength = data.length;
        if (datalength > 0) {
            for (var i = 0; i < datalength; i++) {
                var t = " "; //牙位
                var c = " "; //色号
                var z = " "; //制作要求
                //	    	  			  var s = " ";//设计要求
                if (data[i].toothbit != null && data[i].toothbit != "" && data[i].toothbit != "undefined") {
                    t = data[i].toothbit;
                }
                if (data[i].colorsize != null && data[i].colorsize != "" && data[i].colorsize != "undefined") {
                    c = data[i].colorsize;
                }
                if (data[i].zzyq != null && data[i].zzyq != "" && data[i].zzyq != "undefined") {
                    z = data[i].zzyq;
                }
                //	    	  			  if(data[i].sjyq != null && data[i].sjyq != "" && data[i].sjyq != "undefined"){
                //	    	  				  t = data[i].sjyq;
                //	    	  			  }
                content = content + "<tr><td  width=\"40%\" style=\"text-align: left;height:22px;font-size:14px;\">" 
                + data[i].outprocessingname + "</td><td width=\"10%\" style=\"text-align: center;font-size:14px;\">" 
                + data[i].nums + "</td><td width=\"10%\" style=\"text-align: center;font-size:14px;\">" 
                + data[i].utils + "</td><td width=\"10%\" style=\"text-align: center;font-size:14px;\">" 
                + t + "</td><td width=\"10%\" style=\"text-align: center;font-size:14px;\" >" + c 
                + "</td><td width=\"20%\" style=\"text-align: center;font-size:14px;\">" + z + "</td></tr>";
            }
        }

        $("#jglist").append(content);

    },
    function() {
        layer.alert('查询失败！'  );
    });
}
var LODOP; //声明为全局变
function PrintNoBorderTable() {
	
	
	
    LODOP = getLodop();
    LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 1485, "a5");
    //表格
    LODOP.ADD_PRINT_TABLE(70, 10, "100%", 280, document.getElementById("div2").innerHTML);
    //页眉
    LODOP.ADD_PRINT_TEXT(3, 250, 300, 20, static_titles);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); //固定标题,设置卫页眉页脚
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    //姓名时间栏目
    LODOP.ADD_PRINT_HTM(25, 10, "100%", "100%", document.getElementById("hide").innerHTML);
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
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 2970, "a4");
    //表格
    LODOP.ADD_PRINT_TABLE(70, 2, "100%", "100%", document.getElementById("div2").innerHTML);
    //页眉
    LODOP.ADD_PRINT_TEXT(3, 250, 300, 20, static_titles);
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

function print() {
    $("#printArea").printArea();
}
</script>
</html>
