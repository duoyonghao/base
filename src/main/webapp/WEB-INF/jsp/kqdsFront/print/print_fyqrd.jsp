<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	//合并测试
	// 默认 A5打印 ###########################################
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String actualmoney = request.getParameter("actualmoney");
	String arrearmoney = request.getParameter("arrearmoney");
	String ymoney = request.getParameter("ymoney");
	//剩余总欠费
	String arrearmoneyAll = request.getParameter("arrearmoneyAll");
	String costno = request.getParameter("costno");
	String doctor = request.getParameter("doctor");
	String titles = request.getParameter("titles");
	String username = request.getParameter("username");
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	java.util.Date currentTime = new java.util.Date();//得到当前系统时间  
	String str_date1 = formatter.format(currentTime); //将日期时间格式化  
	String sftime = request.getParameter("sftime");//开单时间
	if(sftime == null){
		sftime = YZUtility.getCurDateTimeStr();
	}
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode = "";
	}
	
	// 开单门诊
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
<script language="javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<style type="text/css">
span {
	font-size: 16px;
}

table,th{
	border:none;
	height:18px;
	font-weight:normal
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
#receipt span{
font-size: 14px;
}

</style>
</head> 
<body style="padding: 0px 5%;">   
	<div id="printArea" width="100%">
		<div style="text-align: center;width: 95%;margin: auto;">
			<span id="titles" style="font-size: 20px;"></span><!-- 抬头 -->
		</div>
		<div id="hide" style="padding:10px 5px 0;margin:0 auto;">
			<table style="width: 100%;border-collapse:collapse; /* border-top:Black solid 1px;  border-bottom:#333 solid 1px; padding-top:5px;  */">
				<tr style="">
					<th width="" align="left" style="border-bottom:#333 solid 1px;">姓名: <%=username%></th>
					<th width="" align="left" style="border-bottom:#333 solid 1px;">卡号: <%=usercode%></th> <!-- 卡号的取值改为 患者编号 yangsen -->
					<th width="" align="left" style="border-bottom:#333 solid 1px;">时间: <%=sftime%></th>
					<th width="" align="left" style="border-bottom:#333 solid 1px;">医生: <%=doctor%></th>
				</tr>
			</table>
		</div>
		
		<div id="div2" style=" padding:0 5px; /**/margin:0 auto;/* margin-top: 5px; */">
			<table  border=0 cellSpacing=0 cellPadding=0  width="100%"  bordercolor="#000000" style="border-collapse:collapse;margin: auto;">
				<thead >
					<tr>
						<th width="25%" align="left" style="border-bottom:#333 solid 1px;">治疗项目</th>
						<th width="15%" align="center" style="border-bottom:#333 solid 1px;">单位</th >
						<th width="15%"  align="center" style="border-bottom:#333 solid 1px;">数量</th>
						<th width="15%" align="center" style="border-bottom:#333 solid 1px;">应收</th>
						<th width="15%" align="center" style="border-bottom:#333 solid 1px;">欠费金额</th>
						<th width="15%" align="center" style="border-bottom:#333 solid 1px;">缴费金额</th>
					</tr>
				</thead>
				<tbody id="tbody" style="">
				
				</tbody>
				<tfoot>	
					  <tr align="left" style="/* border-top:Black solid 1px;border-bottom:Black solid 1px; */padding-top:5px; ">
					     <%--  <td  colspan="1" align="left"> 剩余总欠费:<%=arrearmoneyAll%></td>
						  <td  colspan="2" >本次应付：<%=ymoney%></td>
						  <td colspan="2" >本次缴费：<%=actualmoney%> </td>
						  <td colspan="4" >本次欠费:<%=arrearmoney%> </td> --%>
						  <td colspan="9" style="text-align:left;"> 
							  <table  style="border-collapse:collapse;width:100%;">
							  	<tr>
							  		<td style="padding:0 10px 0 0 ;line-height:26px;" id="botton_fy">
							  			<span style="padding-right:10px;float:left;font-size:14px;">剩余总欠费：<%=arrearmoneyAll%></span>
							  			<span style="padding-right:10px;float:left;font-size:14px;">本次应付：<%=ymoney%></span>
							  			<span style="padding-right:10px;float:left;font-size:14px;">本次缴费：<%=actualmoney%></span>
							  			<span style="padding-right:10px;float:left;font-size:14px;">本次欠费：<%=arrearmoney%></span>
						  			</td>
							  	</tr>
							  </table>
						  </td>
					  </tr>
					  <tr align="left" style="/* border-top:Black solid 1px;border-bottom:Black solid 1px; */padding-top:5px; ">
					  		<td colspan="9" id="FKFS" style="text-align: left;"></td>
					  </tr>
					  <tr  align="left" style="/* border-top:Black solid 1px;border-bottom:Black solid 1px; */padding-top:5px; ">
					  		<td colspan="9" id="SYJE" style="text-align: left;"></td>
					  </tr>
					  <tr id="tishi">
					    <th width="100%" colspan="9" style="line-height:26px;"><span style="height: 28px;float: left;font-size:12px;font-weight:normal">尊敬的客户，为了能向您提供优质的服务，请确认医生已向您详细介绍了本次治疗的项目及相关费用，并征得您的同意。</span></th>
					  </tr>
					   <tr  id="qz" align="right">
					    <th width="100%" colspan="9" style="text-align: right;">客户签名：____________________　</th>
					  </tr>
				</tfoot>
			</table>
<!-- 2019.7.25  licc 明细单打印 -->
			<div id='receipt'>
			<span>收据一式三联：白联为记账联 、红联为客户联、 黄联为存根联</span>			
			</div>
		</div>		
	</div>
	
	<div style="text-align: center;margin-top:10px;">
		<%
		if("1".equals(printType)){ // a4
			%>
			<input value="打印" onclick="PrintNoBorderTableA4()" class="kqdsCommonBtn" type="button">
			<%
		}else{ // a5
			%>
			<input value="打印" id="printBtn" onclick="myPreviewAll()" class="kqdsCommonBtn" type="button">
			<%
		}
	%>
	</div>
	<% %>
<script>
var contextPath = "<%=contextPath %>";
var costno = "<%=costno %>";
var sftime = "<%=sftime %>";
var titles = "<%=titles %>";
var usercode = "<%=usercode %>";
var static_titles = null;
$(function() {
    if (titles == "费用明细单") {
        //隐藏部分
        $("#qz").hide();
        $("#tishi").hide();
        $("#sj").hide();
    }
    OrderDetail();
    printSfxm();
    //预交金余额
	getSymoneyByUsercode(usercode,sftime);
    // 根据开单门诊编号，获取对应门诊信息
    var orginfo = getOrganizationInfoByOrgCode('<%=cost_organization%>');
    if(orginfo){
    	static_titles = orginfo.printName + titles;
    	$("#titles").html(static_titles);
    }
});

function print() {
    $("#div2").printArea();
}

//2019.7.25  licc 明细单打印
/* 打印本页面方法 */
function myPreviewAll(){
	LODOP=getLodop();  
	LODOP.PRINT_INIT("费用明细单");
	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4"); //规定纸张大小；使用A4纸
	var htmlStyle="<style>#printBtn{display:none;}</style>";
	var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
	LODOP.ADD_PRINT_HTM(10,10,"100%","100%",html);
	LODOP.PREVIEW();	
};

/* ##打印控件使用说明## 学习地址：http://www.lodop.net/demo.html  http://blog.sina.com.cn/s/blog_721e77e50100q45u.html */

var LODOP; //声明为全局变
function PrintNoBorderTable() {
    LODOP = getLodop();
    LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 1485, "");

    //表格
    LODOP.ADD_PRINT_TABLE(60, "3%", "95%", "280", document.getElementById("div2").innerHTML);
    
    //页眉
    LODOP.ADD_PRINT_TEXT(3, 250, 350, 20, static_titles);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); //固定标题,设置卫页眉页脚
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    //姓名时间栏目
    LODOP.ADD_PRINT_HTM(35, "3%", "95%", 25, document.getElementById("hide").innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "LinkedItem", 4);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 3);
    //LODOP.ADD_PRINT_HTM(1, 550, 300, 100, "页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
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
function printSfxm() {
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/printSfxm.act?costno=' + costno;
    $.axse(detailurl, null,
    function(r) {
        if (r.retState == '0') {
            var FKFS = r.FKFS;
            var content = '其中 ：';
            for (var p in FKFS) {
                content += '<span style="padding-left: 10px;font-size:14px;">' + p + '：<lable>' + Number(FKFS[p]).toFixed(2) + '</lable></span>';
            }
            $('#FKFS').append(content);
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
    		var zmoney = 0;
    		for(var p in member){
    			  content += '<span style="padding-right: 10px;float: LEFT;font-size:14px;">'+p+'：<lable>'+Number(member[p]).toFixed(2)+'</lable></span>';
    			  zmoney += Number(member[p]).toFixed(2);
    		}
    		zmoney = Number(zmoney).toFixed(2);
    		if(content!=''){
    			content = '<span style="padding-right: 10px;float: left;font-size:14px;">余额合计：<lable>'+zmoney+'</lable>，其中：</span>'+content;
    		}
    		$('#SYJE').append(content);
    	}
    },
    function() {
        layer.alert('查询出错！' );
    });
}
function OrderDetail() {
    var detailurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/NoselectPage.act?costno=' + costno;
    $.axse(detailurl, null,
    function(data) {
        if (data && data.length > 0) {
            //有数据,开始拼接html
            var str = "";
            var flag = checkIsJm(data);
            if(flag == 1) {
            	$("#botton_fy").prepend('<span style="padding-right:10px;float:left;font-size:14px;">团购套餐价：<%=ymoney%></span>')
            	for (var n = 0; n < data.length; n++) {
                    var obj = data[n];
                    var ys = Number(obj.subtotal) - Number(obj.voidmoney);
                    if(obj.itemname.indexOf("团购套餐") == -1) {
                    	if(n==(data.length-1)){
                       	 str += "<tr>" + "   <td width=\"25%\" style=\"text-align: left;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.itemname + "</td >" + 
                            "   <td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.unit + "</td >" +
                            "	<td width=\"15%\"  style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.num + "</td >" + 
                            "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + Number(ys).toFixed(2) + "</td >" + 
                            "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.y2 + "</td >" + 
                            "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">--</td>" + "  </tr>";
                       }else{
                       	str += "<tr>" + "   <td width=\"25%\" style=\"text-align: left;height:20px;font-size:12px;\">" + obj.itemname + "</td >" + 
                           "   <td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;\">" + obj.unit + "</td >" +
                           "	<td width=\"15%\"  style=\"text-align: center;height:20px;font-size:12px;\">" + obj.num + "</td >" + 
                           "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;\">" + Number(ys).toFixed(2) + "</td >" + 
                           "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;\">" + obj.y2 + "</td >" + 
                           "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;\">--</td>" + "  </tr>";
                       }
                    }
                }
            }else {
            	for (var n = 0; n < data.length; n++) {
                    var obj = data[n];
                    var ys = Number(obj.subtotal) - Number(obj.voidmoney);
                    if(n==(data.length-1)){
                    	 str += "<tr>" + "   <td width=\"25%\" style=\"text-align: left;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.itemname + "</td >" + 
                         "   <td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.unit + "</td >" +
                         "	<td width=\"15%\"  style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.num + "</td >" + 
                         "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + Number(ys).toFixed(2) + "</td >" + 
                         "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.y2 + "</td >" + 
                         "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;border-bottom:1px solid #333;\">" + obj.paymoney + "</td>" + "  </tr>";
                    }else{
                    	str += "<tr>" + "   <td width=\"25%\" style=\"text-align: left;height:20px;font-size:12px;\">" + obj.itemname + "</td >" + 
                        "   <td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;\">" + obj.unit + "</td >" +
                        "	<td width=\"15%\"  style=\"text-align: center;height:20px;font-size:12px;\">" + obj.num + "</td >" + 
                        "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;\">" + Number(ys).toFixed(2) + "</td >" + 
                        "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;\">" + obj.y2 + "</td >" + 
                        "	<td width=\"15%\" style=\"text-align: center;height:20px;font-size:12px;\">" + obj.paymoney + "</td>" + "  </tr>";
                    }
                   
                }
            }
            
            
            $("#tbody").append(str);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 临时处理正畸团购套餐打折
 */
function checkIsJm(data){
	var flag = 0;
	for (var n = 0; n < data.length; n++) {
		if((data[n].itemname).indexOf("团购套餐") != -1) {
			flag = 1;
		}
	}
	return flag;
}
</script>
</body>
</html>