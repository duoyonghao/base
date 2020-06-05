<%@page import="com.kqds.entity.sys.YZDict"%>
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
	String organization = ChainUtil.getCurrentOrganizationName(request);
	if(organization == null){
		organization = "海德堡联和口腔医院";
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
	.div{
		text-align: center;
	}
</style>
</head>
<body>
<div id="container">
  <div id = "fytjDiv"><!-- ###费用添加start -->	
	<div class="costWrap">
			<div id="hide" style="width: 100%;margin: auto;display: none;">
				<!-- <table style="width: 100%;/*  text-align: left; *//* border-top:Black solid 1px; */border-bottom:Black solid 1px;padding-top:5px; ">
					<tr style="text-align: center;">
						<td width="25%" id="username"></td>
						<td width="20%" id="sex" ></td> 卡号的取值改为 患者编号 yangsen
						<td width="35%" id="recipecode" style="font-size: 14"></td>
					</tr>
					<tr style="text-align: center;">
						<td width="20%" id="age">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td width="35%" id="usercode"></td>
					</tr>
				</table> -->
				<span id="username" style="margin-left: 55px;"></span>
				<span id="sex" style="margin-left: 116px;"></span>
				<span id="recipecode" style="margin-left: 175px;"></span><br>
				<span id="age" style="margin-left: 55px;"></span>
				<span id="usercode" style="margin-left: 128px;"></span>
				<hr style="color: black;height: 1px">
			</div>
			
			<div id="mark" style="width: 100%;">
				<table style="width: 100%; text-align: left;border-bottom:Black solid 1px;">
					<tr>
						<th width="100%" colspan="9" style="border: 0px;" align="left"><span style="height: 28px;float: left;font-size:14px;font-weight:normal" id="remark"></span></th>
					</tr>
				</table>
			</div>
			
			<div style="border-radius:6px;" id="div2">
				<table id="table" class="table-striped table-condensed table-bordered" data-height="250" style="border-bottom:Black solid 1px; width: 100%;text-align: center;">
					<thead id="div2_table_header">
						<tr>
							<!-- 
							<td style="text-align: center; vertical-align: middle;">药品名称</td>
							<td style="text-align: center; vertical-align: middle;width:10%;">单位</td>
							<td style="text-align: center; vertical-align: middle;width:10%;">数量</td>
							<td style="text-align: center; vertical-align: middle;width:15%;">频次</td>
							<td style="text-align: center; vertical-align: middle;width:15%;">每次剂量</td>
							<td style="text-align: center; vertical-align: middle;width:15%;">用法</td>
							<td style="text-align: center; vertical-align: middle;width:10%;">单价</td>
							<td style="text-align: center; vertical-align: middle;width:10%;">金额</td> 
							-->
							<td style="text-align: center; vertical-align: middle;position: relative;"><span style="position: absolute;left: 0px;font-weight: bold;">Rp:</span><b>药品名称</b></td>
							<td style="text-align: center; vertical-align: middle;width:5%;"><b>数量</b></td>
							<td style="text-align: center; vertical-align: middle;width:5%;"><b>单位</b></td>
							<td style="text-align: center; vertical-align: middle;width:11%;"><b>频次</b></td>
							<td style="text-align: center; vertical-align: middle;width:15%;"><b>每次剂量</b></td>
							<td style="text-align: center; vertical-align: middle;width:8%;"><b>用法</b></td>
							<td style="text-align: center; vertical-align: middle;width:15%;"><b>规格</b></td>
							<td style="text-align: center; vertical-align: middle;width:7%;"><b>单价</b></td>
							<td style="text-align: center; vertical-align: middle;width:9%;"><b>金额</b></td>
						</tr>
					</thead>
					
					<tbody style="text-align: center;border-bottom:1px solid #ddd;"></tbody>
					<tfoot>
						<!-- 
						<tr style="border: 0px;">
							<th width="100%" colspan="9" style="border: 0px;">&nbsp;</th>
						</tr> -->
						<!-- 
						<tr style="border: 0px;">
					    	<th width="100%" colspan="9" style="border: 0px;" align="left"><span style="height: 28px;float: left;font-size:14px;font-weight:normal" id="remark"></span></th>
						</tr> -->
						<!-- 
						<tr id="tishi">
						    <th width="100%" colspan="9">
						    	<span style="height: 28px;float: left;font-size:14px;font-weight:normal">
						    	药师提示：
						    	1、请遵医嘱服药；2、请在窗口点清药品；3、处方当日有效；发出药品不予退换。
						    	</span>
						    </th>
						</tr> -->
						<tr id="qz">
					    	<td width="100%" colspan="9" style="text-align: right;">处方医生（签字或签章有效）：<u><span id="doctor" style="width: 150px;display: inline-block;text-align: left;"></span></u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=YZUtility.getCurDateTimeStr().substring(0, 10)%> &nbsp;&nbsp;</td>
						</tr>
					</tfoot>
				</table>
				
				<div id="qq" style="margin-left: 15px;">
					<div>
						<span>发药药房：<%=organization %>药房</span>
					</div>
					<div style="margin-top: -17px;margin-left: 280px;">
						<span>打印时间：<%=YZUtility.getCurDateTimeStr().substring(0, 10)%></span>
					</div>
					<!-- <div style="margin-top: -40px;margin-left: 667px;">
						<h3>（底联）</h3>
					</div> -->
				</div>
				<div id="weixin" style="margin-left: 15px;">
					<div id="yaofei">药费：</div>
					<div style="margin-top: -18px;margin-left: 145px;">计价/审核：</div>
					<div style="margin-top: -17px;margin-left: 293px;">调配：</div>
					<div style="margin-top: -19px;margin-left: 451px;">核对/发药：</div>
					<!-- <div style="margin-top: -19px;margin-left: 600px;">有无过敏史：
						<input type="checkbox" name='hobby' value='0'>有&nbsp;&nbsp;
	            		<input type="checkbox" name='hobby' value='1'>无
	            	</div> -->
				</div>
				<div id="tishi" style="margin-left: 15px;">
					<span style="height: 28px;float: left;font-size:14px;font-weight:normal">
						    	药师提示：
						    	1、请遵医嘱服药；2、请在窗口点清药品；3、处方当日有效；4、发出药品不予退换。
					</span>
				</div>
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
<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始
var static_seqId = "<%=static_seqId%>"; // ### 修改
var static_chuFangObj = null; // 处方对象
var static_costno = null;
var r = null;
var static_titles = null;
var static_usercode = null;
$(function() {
    if (static_seqId != "") { //修改费用单
        static_chuFangObj = getChufangObjBySeqId(static_seqId);
    	// 备注赋值
    	$("#remark").html("病情及诊断：" + static_chuFangObj.cf.remark);
    	$("#doctor").html("&nbsp;&nbsp;&nbsp;&nbsp;"+static_chuFangObj.doctorName+"&nbsp;&nbsp;&nbsp;&nbsp;");
        static_costno = static_chuFangObj.cf.costno;
        static_usercode = static_chuFangObj.cf.usercode;
        getChuFangDetailList(static_chuFangObj.cfdetailList);
    }
});

function refreshData(){ //让innerHTML获取的内容包含input和select(option)的最新值
    var allInputObject=document.body.getElementsByTagName("input");
    for (i = 0; i < allInputObject.length; i++) {
        if(allInputObject[i].type=="checkbox")  {
                if (allInputObject[i].checked ) 
                       allInputObject[i].setAttribute("checked","checked"); 
                       else
                    allInputObject[i].removeAttribute("checked");
        } else     if(allInputObject[i].type=="radio")  {
                if (allInputObject[i].checked ) 
                       allInputObject[i].setAttribute("checked","checked"); 
                       else
                    allInputObject[i].removeAttribute("checked");
        }else allInputObject[i].setAttribute("value",allInputObject[i].value);
    };
    for (i = 0; i < document.getElementsByTagName("select").length; i++) {
        var sl=document.getElementsByTagName("select")[i];
        for (j = 0; j < sl.options.length; j++) {
        if (sl.options[j].selected) 
                     sl.options[j].setAttribute("selected","selected");
        else sl.options[j]=new Option(sl.options[j].text,sl.options[j].value);
        };
    };
}; 

function getChuFangDetailList(cfdetailList) {
    for (var i = 0; i < cfdetailList.length; i++) {
        tdindex++;
        var tablehtml = "";
        var tabledata = cfdetailList[i];
        //alert(JSON.stringify(tabledata));
        //console.log(tabledata);
        tablehtml += "<tr>";
        //删除按钮0
        tablehtml += '<td style="height:30px;border-right: 1px solid #ddd;display:none;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,\'' + tabledata.isqfreal + '\',\'' + tabledata.seqId + '\')"><span style="color:red;">删除</span></a></td>';
        //项目编号1
        tablehtml += '<td style="display:none;">' + tabledata.itemno + '</td>';
        //治疗项目2
        tablehtml += '<td><span style="float:left; text-align:left;width: 100%;text-align: center;" class="time" title=' + tabledata.itemname + '>' + tabledata.itemname + '</span></td>';
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
        //药品规格
        tablehtml += '<td>' + tabledata.pharm_spec + '</td>';
        //单价8
        tablehtml += '<td>' + tabledata.unitpri + '</td>';
        //金额
        tablehtml += '<td>' + tabledata.jine + '</td>';
        //金额
        //ID  8
        tablehtml += '<td style="display:none;">' + tabledata.seqId + '</td>';
        tablehtml += "</tr>";

        $("#table").find('tbody').append(tablehtml);
    }

    //$("#table").find('tbody').append(tmp);
}

$('#qrhj').on('click',
function() {
	refreshData();
    // 根据开单门诊编号，获取对应门诊信息
    var orginfo = getOrganizationInfoByOrgCode('<%=ChainUtil.getCurrentOrganization(request)%>');
    if (orginfo) {
        static_titles = orginfo.printName + "处方单";
    }

    var userObj = getHzNameByusercodeTB(static_usercode);
    $("#username").html("姓名：" + userObj.username);
    $("#sex").html("性别 ：" + userObj.sex);
    $("#age").html("年龄 ：" + userObj.age);
    $("#usercode").html("卡号：" + userObj.usercode);

    $("#div2_table_header").css("color", "black");

    PrintNoBorderTableA4();

    //$("#div2_table_header").css("color", "white");
});

window.onload = function(){
	//var seqId = static_seqId;
	var costno = static_costno;
	//alert(static_costno);
	var url = contextPath + "/KQDS_ChuFangAct/getRecipeCodeById.act"
	var param = {costno:costno};
	$.axseSubmit(url, param, function() {}, function(r) {
		//alert(JSON.stringify(r));
		//console.log(r);
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
            //html += "<option value=''>请选择</option>";
            html += "";
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
    LODOP.PRINT_INIT("UNIC管理系统_LODOP打印控件");
    //A4 210*297 MM
    LODOP.SET_PRINT_PAGESIZE(1, 2100, 2970, "");
	
    LODOP.ADD_PRINT_HTM(120, 0, "100%", "100%", document.getElementById("mark").innerHTML);
    
    //表格
    LODOP.ADD_PRINT_TABLE(160, 0, "100%", "100%", document.getElementById("div2").innerHTML);
    
    //页眉(抬头)
    LODOP.ADD_PRINT_TEXT(30, 250, 310, 20, static_titles);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 15);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1); //固定标题,设置卫页眉页脚
    LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
    
    LODOP.ADD_PRINT_HTM(375, 0, "100%", "100%", document.getElementById("qq").innerHTML);
    LODOP.ADD_PRINT_HTM(400, 0, "100%", "100%", document.getElementById("weixin").innerHTML);
    LODOP.ADD_PRINT_HTM(425, 0, "100%", "100%", document.getElementById("tishi").innerHTML);
    
    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
    //姓名时间栏目
    LODOP.ADD_PRINT_HTM(70, 7, "100%", "100%", document.getElementById("hide").innerHTML);
    LODOP.SET_PRINT_STYLEA(0, "LinkedItem", 4);
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 3);
  //LODOP.ADD_PRINT_HTM(1, "90%", 300, 100, "页码：<font color='#0000ff' ><span tdata='pageNO'>#</span>/<span tdata='pageCount'>#</span></font>");
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
    LODOP.SET_PRINT_STYLEA(0, "Horient", 1);
    LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);

    LODOP.PREVIEW();
};
</script>
</html>
