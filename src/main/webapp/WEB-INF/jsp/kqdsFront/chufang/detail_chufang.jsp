<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);

	String static_seqId = request.getParameter("seqId"); // 编辑

	if (static_seqId == null) {
		static_seqId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>处方单明细</title><!-- 从右侧个人中心  中间 图标 进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/add_cost.css" />
<style>
	#table td,#table th{
		border:none;
	}
	#container {
	    overflow: hidden;
	    padding-bottom:0;
	}
</style>
</head>
<body>
<div id="container">
  <div id = "fytjDiv"><!-- ###费用添加start -->
         <div id="logo">
				<div style="margin-left: 235px;"><h2>北京海德堡联合口腔医院</h2></div>
				<!-- <div style="margin-top: -44px;margin-left: 249px;">
					<img alt="" src="/base/static/image/kqdsFront/img/chufang/hudh.jpg" style="width: 27px;">
				</div> -->
         </div>	
	<div class="costWrap" style="height: 520px;">
			<div id="hide" style="width: 719px; margin: auto; display: none; ">
				<table style="width: 719px; text-align: left; padding-top:5px; ">
					<tr>
						<td width="35%" id="usercode" style="font-size: 14"></td>
						<td width="35%" id="recipecode" style="font-size: 14"></td>
						<td width="35%" style="font-size: 14">日期：<%=YZUtility.getCurDateTimeStr().substring(0, 10)%></td>
					</tr>
					<tr>
						<td width="25%" id="username" style="font-size: 14"></td>
						<td width="20%" id="sex" style="font-size: 14"></td>
						<td width="20%" id="age" style="font-size: 14"></td>
					</tr>
				</table>
			</div>
			<div id="fangkuang">
				<div style="margin-left: 16px;margin-top: 12px;width: 180px;height: 307px;border:1px solid #444444;">
					<div style="margin-left: 20px;margin-top: 2px;"><b>临床诊断：</b>
					</div>
						<span style="margin-left: 20px;height: 28px;float: left;font-size:14px;font-weight:normal" id="remark"></span>
					<div style="font-weight: lighter;margin-left: 20px;margin-top: 245px;"><b>过敏试验：</b></div>
				</div>
				<div style="margin-left: 195px;margin-top: -307px;width: 539px;height: 307px;border:1px solid #444444;">
					<div style="height: 260px;">
						<table id="table"  data-height="250" style="width: 100%;text-align: center;">
							<thead  id="div2_table_header">
								<tr>
									<td style="text-align: center; vertical-align: middle;"><b>药品名称</b></td>
									<td style="text-align: center; vertical-align: middle;width:9%;"><b>数量</b></td>
									<td style="text-align: center; vertical-align: middle;width:9%;"><b>单位</b></td>
									<td style="text-align: center; vertical-align: middle;width:15%;"><b>频次</b></td>
									<td style="text-align: center; vertical-align: middle;width:16%;"><b>每次剂量</b></td>
									<td style="text-align: center; vertical-align: middle;width:16%;"><b>用法</b></td>
								</tr>
							</thead>
							<!-- <tbody style="font-size: small;text-align: center;border-bottom:1px solid #ddd;"></tbody> -->
							<tr style="font-size: small;text-align: center;border-bottom:1px solid #ddd;"></tr>   
						</table>
					</div>
					<div style="margin-top: 25px;margin-left: 165px;"><b>医师签名（签字或盖章有效）:____________</b></div> 
					<!-- <span><b>医师签名（盖章）:____________</b></span> -->
				</div>
			</div>
			
			<div style="margin-top: 2px;border-radius:6px;" id="div2">
			
				 <%-- <table class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;"> -->
					<!-- 
					<thead style="background: #00A6C0;color:white;" id="div2_table_header">
						<tr>
							<th style="text-align: center; vertical-align: middle;">名称</th>
							<th style="text-align: center; vertical-align: middle;width:10%;">数量</th>
							<th style="text-align: center; vertical-align: middle;width:10%;">单位</th>
							<th style="text-align: center; vertical-align: middle;width:15%;">用法</th>
							<th style="text-align: center; vertical-align: middle;width:15%;">单次用量</th>
							<th style="text-align: center; vertical-align: middle;width:15%;">用药途径</th>
						</tr>
					</thead>   
					-->
					<!-- <tbody style="background-color: #F0FFFF;text-align: center;border-bottom:1px solid #ddd;"></tbody> -->
						<!-- 
						<tr style="border: 0px;">
							<th width="100%" colspan="9" style="border: 0px;">&nbsp;</th>
						</tr>
						 <tr style="border: 0px;">
					    	<th width="100%" colspan="9" style="border: 0px;" align="left">
					    		<span style="height: 28px;float: left;font-size:14px;font-weight:normal" id="remark"></span>
					    	</th>
						</tr>  
						-->
					<tr id="tishi">
						    <th width="100%" colspan="9">
							    <span style="height: 28px;float: left;font-size:14px;font-weight:normal">
							    	<span id="yaofei">药费金额：</span>               
							        <span style="margin-left: 80px;"> 审核/调配签名（盖章）：</span>   
							    	<span style="margin-left: 130px;">核对/发药签名（盖章）：</span><br>
							    	药师提示：1、请遵医嘱服药；2、请在窗口点清药品；3、处方当日有效；4、发出药品不予退换。
							    </span>
						    </th>
						    
					    	<th width="100%" colspan="9" style="">日期：<%=YZUtility.getCurDateTimeStr().substring(0, 10)%> &nbsp;&nbsp;</th>
						</tr>
						<tr id="qz" align="right">
						</tr> 
				</table>  --%>
				
				<div>
					 <span style="height: 28px;float: left;font-size:14px;font-weight:normal">
						         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;药师提示：1、请遵医嘱服药；2、请在窗口点清药品；3、处方当日有效；4、发出药品不予退换。<br><br>
						   <span id="yaofei" style="margin-left: 19px;">药费金额：</span>               
						   <span style="margin-left: 80px;"> 审核/调配签名（盖章）：</span>   
						   <span style="margin-left: 130px;">核对/发药签名（盖章）：</span><br>
				     </span>
				</div>
				<%-- <div style="margin-left: 618px;">日期：<%=YZUtility.getCurDateTimeStr().substring(0, 10)%></div> --%>
			</div>
	</div>
	<div class="footBar">
		<a href="javascript:void(0);" class="kqdsCommonBtn" id="qrhj">打印</a>
		<a href="javascript:parent.layer.close(frameindex);" class="kqdsCommonBtn" id="cancel">取消</a>
	</div>
  </div> <!-- ###费用添加end -->
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/chufang/chufang.js"></script>
<script language="javascript" src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始
var static_seqId = "<%=static_seqId%>"; // ### 修改
var static_chuFangObj = null; // 处方对象
var static_costno = null;

var static_titles = null;
var static_usercode = null;
$(function() {
    if (static_seqId != "") { //修改费用单
        static_chuFangObj = getChufangObjBySeqId(static_seqId);
    	// 备注赋值
    	$("#remark").html(static_chuFangObj.cf.remark);
    	
        static_costno = static_chuFangObj.cf.costno;
        //alert(static_costno);
        static_usercode = static_chuFangObj.cf.usercode;
        //alert(static_usercode);
        getChuFangDetailList(static_chuFangObj.cfdetailList);
    }
});

function getChuFangDetailList(cfdetailList) {
    for (var i = 0; i < cfdetailList.length; i++) {
        tdindex++;
        var tablehtml = "";
        var tabledata = cfdetailList[i];
        //alert(JSON.stringify(tabledata));
        console.log(tabledata);
        tablehtml += "<tr>";
        //删除按钮0
        tablehtml += '<td style="height:30px;border-right: 1px solid #ddd;display:none;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,\'' + tabledata.isqfreal + '\',\'' + tabledata.seqId + '\')"><span style="color:red;">删除</span></a></td>';

        //项目编号1
        tablehtml += '<td style="display:none;">' + tabledata.itemno + '</td>';
        //治疗项目2
        tablehtml += '<td><span style="float:left; text-align:left;" class="time" title=' + tabledata.itemname + '>' + tabledata.itemname + '</span></td>';
        //数量3
        tablehtml += '<td>' + tabledata.num + '</td>';
        //单位4
        tablehtml += '<td>' + tabledata.unit + '</td>';
        //用法5
        tablehtml += '<td>' + getDictSelectHtml("CHUFANG_YF", tabledata.cfusage) + '</td>';
        //用量6
        tablehtml += '<td>' + tabledata.cfuselevel + '</td>';
        //用药途径7
        tablehtml += '<td>' + getDictSelectHtml("CHUFANG_YYTJ", tabledata.cfusemethod) + '</td>';
        //ID  8
        tablehtml += '<td style="display:none;">' + tabledata.seqId + '</td>';
        tablehtml += "</tr>";

        $("#table").find('tbody').append(tablehtml);
    }

    $("#table").find('tbody').append(tmp);
}

$('#qrhj').on('click',
function() {

    // 根据开单门诊编号，获取对应门诊信息
    var orginfo = getOrganizationInfoByOrgCode('<%=ChainUtil.getCurrentOrganization(request)%>');
    if (orginfo) {
        static_titles = orginfo.printName + "处方单";
    }

    var userObj = getHzNameByusercodeTB(static_usercode);
    //alert(JSON.stringify(userObj));
    $("#username").html("姓名：" + userObj.username);
    $("#sex").html("性别：" + userObj.sex);
    $("#age").html("年龄：" + userObj.age);
    $("#usercode").html("病历号：" + userObj.usercode);
    //$("#recipecode").html("处方号：" + userObj.recipecode);
    //$("#yaofei").html(userObj.totalcost);

    //$("#div2_table_header").css("color", "black");

    PrintNoBorderTableA4();

    //$("#div2_table_header").css("color", "white");
});


window.onload = function(){
	//var seqId = static_seqId;
	var costno = static_costno;
	alert(static_costno);
	var url = contextPath + "/KQDS_ChuFangAct/getRecipeCodeById.act"
	var param = {costno:costno};
	$.axseSubmit(url, param, function() {}, function(r) {
		//alert(JSON.stringify(r));
		$("#recipecode").html("处方号：" + r.recipecode);
		$("#yaofei").html("药费金额：" + r.totalcost);
	}, function() {
		
	});
} 

function getDictSelectHtml(dictType, defaultVal) {
    var html = "";
    var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + dictType;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            html += "<option value=''>请选择</option>";
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                if (optionStr.seqId == defaultVal) {
                    html = optionStr.dictName;
                }
            }
        }
        html += "";
    },
    function() {
        layer.alert('查询出错！'  );
    });

    return html;
}

function PrintNoBorderTableA4() {
    LODOP = getLodop();
    LODOP.SET_PRINT_STYLEA(0 , "Bold", 0);
    LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
    //A4 210*297 MM
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 2970, "");
	
    //logo
    LODOP.ADD_PRINT_HTM(17, 1, "100%", "100%", document.getElementById("logo").innerHTML);
    
    //姓名时间栏目
    LODOP.ADD_PRINT_HTM(65, 20, "100%", "100%", document.getElementById("hide").innerHTML);
    LODOP.SET_PRINT_STYLEA(0 , "Bold", 0);
    
    //方框
    LODOP.ADD_PRINT_HTM(109, 5, "100%", "100%", document.getElementById("fangkuang").innerHTML);
    
    //提示及审核
    LODOP.ADD_PRINT_HTM(435, 0, "100%", "100%", document.getElementById("div2").innerHTML);
    
    //页眉
    /* 
    LODOP.ADD_PRINT_TEXT(3, 250, 300, 20, static_titles);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); //固定标题,设置为页眉页脚
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1); */
    
    //姓名时间栏目
    /*
    LODOP.ADD_PRINT_HTM(35, 3, "100%", "100%", document.getElementById("hide").innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "LinkedItem", 4);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 3);
    LODOP.ADD_PRINT_HTM(1, "90%", 300, 100, "页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 1);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 0);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); */

    LODOP.PREVIEW();
};
</script>
</html>
