<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	String usercode = request.getParameter("usercode");
	if (usercode == null) {
		usercode = "";
	}
	/**
	 * 是否开启微信 0 不开启 1开启
	 */
	String is_open_wechat = YZSysProps.getProp(SysParaUtil.IS_OPEN_WECHAT);
	if(is_open_wechat == null){
		is_open_wechat = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>个人信息</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css" />
<!-- baseInfo.css 样式文件为 个人信息页面 专用 -->
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/baseInfo.css"> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/grxx.css">
<style>
	.aa{
	display: inline-block;
    box-sizing: border-box;
    line-height: 20px;
    background: #00a6c0;
    color: #fff;
    outline: none;
    padding-left: 10px;
    padding-right: 10px;
    border: 1px solid #00a6c0;
    border-radius: 30px;
    margin-right: 20px;
    text-decoration: none;
    cursor: pointer;
    text-align: center;
}
.bb{
	display: inline-block;
    box-sizing: border-box;
    line-height: 20px;
    background: #00a6c0;
    color: #fff;
    outline: none;
    padding-left: 10px;
    padding-right: 10px;
    border: 1px solid #00a6c0;
    border-radius: 30px;
    margin-right: 20px;
    text-decoration: none;
    cursor: pointer;
    text-align: center;
}
.aa>span{
	margin-left: 8px;
}
.namecoloc{
	display: inline-block;
	font-size: 14px;
    line-height: 26px;
    font-weight: bold;
    margin-left: 5px;
}
.alla{
	display: flex;
	flex-wrap:wrap;
	margin-left: 10px;
}
.alla>li{
	margin-bottom: 10px;
}
.labelfatherLi{
	width: 100%;
	border: 1px solid #eee;
}
#labelBtn{
	margin-top: 20px;
	margin-right: 0px;
    margin-left: 10px;
    padding: 0px 10px;
}
#patientLabel{
	width: 100%;
	color: #7C7C7C;
/*   	border: 1px solid red; */
}
#close{
	display:inline-block;
	width:30px;
	height: 30px;
	border: 1px solid red;
	font-size: 30px;
	right: 0px;
}

#seeAbout{
	display:inline-block;
			box-sizing:border-box;
			height:26px;
			line-height:26px;
			background:#00a6c0;
			color:#fff;
			outline:none;
			padding:0 15px;
			border:1px solid #00a6c0;
			border-radius:3px;
/* 		    margin-right: 20px; */
    		margin-right: -2px;
    		margin-bottom: 5px;
    		margin-left: 20px;
    		margin-top: 10px;
			text-decoration:none;
			cursor:pointer;
			text-align:center;		
}

#lableShow{
margin-top: 2%;
/* margin-left: 10%; */
}

</style>
</head>
<body>
	<div class="container">
		<!-- <div class="titleDiv" style="border-bottom:none;">
			<span class="title">个人信息</span>
		</div> -->
		<ul class="indivdualMessageUl" style="padding-top:15px;border-top:none;">
			<li>
				<div>
					<span>患者编号：</span>
					<i id="usercode" ></i>
				</div>
				<div style="display: none;">
					<span>病历号：</span>
					<i id="blcode" ></i>
				</div>
			</li>
			<li>
				<div>
					<span>患者姓名：</span>
					<i id="username" >
						
					</i>
					<%
						if("1".equals(is_open_wechat)){
							%>
					<b id="wechatId" style="display: none;" class="weixinIcon icon-weixin" onclick="openWeChat();"></b>
						<%
						}
					%>
				</div>
				
				<div>
					<span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 别：</span>
					<i id="sex" ></i>
				</div>
			</li>
			<li>
				<div>
					<span>出生年月：</span>
					<i id="birthday"></i>
				</div>
				<div>
					<span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 龄：</span>
					<i id="age"></i>
				</div>
			</li>
			<li>
				<div>
					<span>电&nbsp;&nbsp;&nbsp;&nbsp; 话1：</span>
					<i id="phonenumber1"></i>
				</div>
				<div>
					<span>电&nbsp;&nbsp;&nbsp;&nbsp; 话2：</span>
					<i id="phonenumber2"></i>
				</div>
			</li>
			<li id="tool">
				<div>
					<span>患者来源：</span>
					<i id="devchannelname"></i>
				</div>
				<div>
					<span>子&nbsp;&nbsp;分&nbsp;类：</span>
					<i id="nexttypename"></i>
				</div>
			</li>
			<li>
				<div>
					<span>职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 业：</span>
					<i id="professionname"></i>
				</div>
				<div>
					<span>客户星级：</span>
					<i id="memberlevel"></i>
				</div>
			</li>
			<li>
				<div>
					<span>实收金额：</span>
					<i id="totalpay"></i>
				</div>
				<div>
					<span>洁牙习惯：</span>
					<i id="habit"></i>
				</div>
			</li>
		</ul>
		<ul class="indivdualMessageUl">
			<li>
				<div>
					<span>充值余额：</span>
					<i id="money"></i>
				</div>
				<div>
					<span>赠送余额：</span>
					<i id="givemoney"></i>
				</div>
			</li>
		</ul>
		<ul class="indivdualMessageUl">
			<li>
				<div>
					<span>积分余额：</span>
					<i id="integral"></i>
				</div>
			</li>
		</ul>
		
		<ul class="indivdualMessageUl">
			<li>
				<div>
					<span>建&nbsp;&nbsp;档&nbsp;人：</span>
					<i id="createusername"></i>
				</div>
				<div>
					<span>介&nbsp;&nbsp;绍&nbsp;人：</span>
					<i id="introducername"></i>
				</div>
			</li>
			
			<li>
				<div>
					<span>开&nbsp;&nbsp;发&nbsp;人：</span>
					<i id="developername"></i>
				</div>
				<div>
					<span>客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服：</span>
					<i id="kefuname"></i>
				</div>
			</li>
			
			<li>
				<div>
					<span>第一咨询：</span>
					<i id="askpersonname"></i>
				</div>
				<div>
					<span>医&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生：</span>
					<i id="doctorname"></i>
				</div>
			</li>
			
			<li>
				<div>
					<span>建档导医：</span>
					<i id="guidername"></i>
				</div>
				<div>
				<!-- 修改内容:新增字段 修改时间:2019/04/11 11:45 修改人:多永浩   -->
					<span>修复医生：</span>
					<i id="repairdoctor"></i>
				</div>
			</li>
		</ul>
		
		<ul class="indivdualMessageUl">
			<li>
				<div class="fullWidth">
					<span>就诊经历：</span>
					<i id="experience"></i>
				</div>
				
			</li>
			
			<li>
				<div class="fullWidth">
					<span>联系地址：</span>
					<i id="address"></i>
				</div>
			</li>
			
			<li>
				<div class="fullWidth">
					<span>身份证号：</span>
					<i id="idcardno"></i>
				</div>
			</li>
			
			<li>
				<div class="fullWidth">
					<span>国家地区：</span>
					<i id="showprovinceandcity"></i>
				</div>
			</li>
			
			<li>
				<div class="fullWidth">
					<span>QQ/微信：</span>
					<i id="qq"></i>
				</div>
			</li>
			
			<li>
				<div class="fullWidth">
					<span>商务通身份证：</span>
					<i id="xueli"></i>
				</div>
			</li>
			
			<li>
				<div class="fullWidth">
					<span>患者印象：</span>
					<i id="userimpress"></i>
				</div>
			</li>
			
			<li>
				<div class="fullWidth">
					<span>第一句话：</span>
					<i id="firstword"></i>
				</div>
			</li>
			<!-- 添加爱好展示 -->
			<!-- <li>
				<div class="fullWidth">
					<span>爱好：</span>
					<i id="hobby"></i>
				</div>
			</li> -->
			<li>
				<div class="fullWidth">
					<span>介绍人次：</span>
					<i id="introducerNum"></i>
				</div>
			</li>
			<li>
				<div class="fullWidth">
					<span>参加本院活动：</span>
					<i id="activity"></i>
				</div>
			</li>
			<!-- 添加介绍人次展示 -->
			<li>
				<div class="fullWidth">
					<span style="width:65px;">备注：</span>
					<i id="remark"></i>
				</div>
			</li>
		</ul>
		<!-- <span id="seeAbout" onclick="openSee()" >点击查看患者标签</span>	  -->
		<div id="patientLabel">	              
           	<span class="impText">患者标签：</span>
            	<botton id="labelBtn" class="aa hide" onclick="openPatientTag()" >新增标签</botton>
				<botton id="labelBtn3" class="aa hide" onclick="closeIt()">关闭</botton>
            	<!-- <botton id="labelBtn2" class="aa" onclick="labelBtn2()">编辑标签</botton>   --> 
            	<botton id="labelBtn3" class="aa hide">提交</botton>
			<div style="border-radius: 4px;margin-bottom: 20px;max-width: 750px;">
			    <ul id="lableShow">
			    	<li class="labelfatherLi">	
						<div class="namecoloc"><span id="expense">消费标签</span>:</div>
						<ul class="alla" id="expenseShow">
						</ul>
					</li>
					<li class="labelfatherLi">	
						<div class="namecoloc"><span id="society">社会标签</span>:</div>
						<ul class="alla" id="societyShow">
						</ul>
					</li>
					<li class="labelfatherLi">	
						<div class="namecoloc"><span id="need">需求标签</span>:</div>
						<ul class="alla" id="needShow">
						</ul>
					</li>
			    </ul>
			</div>			
		</div>
		<div >
			<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.deleteLclj(this);" id="lclj_sc">删除</a>
            <a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="buttonFun.editLclj(this);" id="lclj_edit">编辑</a>
		</div>
		<div id="barcodediv" class="barcodediv"> <!-- barcodediv没有选择患者时 条形码时隐藏的 -->
			<div id="print" class="print">
				<span id="pbarcode"><img id="barcode"></span>
				<a id="printBarcode" class="printBarcode">打 印</a> <!--.printBarcode 打印按钮的样式 -->
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<!--  jquery 打印插件 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.PrintArea.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var onclickrowOobj = "";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var usercode = '<%=usercode%>';
var listbutton = window.parent.listbutton;
var static_userseqid = '<%=person.getSeqId() %>';
var loc = new Location();
var baseinfo = getBaseInfoByUserCode(usercode);
var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

/** 微信相关 **/
var openid = null;
var bindstatus = null;
$(function() {
    //隐藏右侧个人信息 职业、省份城市等下拉框
    if (usercode == "") {
        onclickrowOobj = window.parent.onclickrowOobj;
        if (onclickrowOobj != null && onclickrowOobj != "" && onclickrowOobj != "null" && onclickrowOobj != "undefined") {
            usercode = onclickrowOobj.usercode;
        }
    }
    //getButtonPower();//初始化按钮
    initDictSelectByClass(); // 职业
    if (usercode != '') {
    	$("#wechatId").css("display","block"); /** 微信标识 **/
    	
        showpersoninfo(usercode);
    }else{
    	$("#wechatId").css("display","none"); /** 微信标识 **/
    }
    setIndivdualWidth();      //功能： 计算右侧  '患者印象' '第一句话' '备注' 三个字段过多时 能正常换行
});
function setIndivdualWidth(){ //功能： 计算右侧  '患者印象' '第一句话' '备注' 三个字段过多时 能正常换行
	$(".indivdualMessageUl .fullWidth").each(function(i,item){
		var fullDivWidth=$(this).outerWidth();
		var spanWidth=$(this).find("span").outerWidth();
		$(this).find("i").outerWidth(fullDivWidth-spanWidth-2);
	});
}
$("#printBarcode").click(function() {
    $("#pbarcode").printArea();
});

var LODOP; //声明为全局变
function PrintNoBorderTable() {
    LODOP = getLodop();
    LODOP.ADD_PRINT_IMAGE(30, 150, "80%", "80%", $("#pbarcode").html());
    LODOP.PREVIEW();
};


//判断当前人员权限是否为前台
var total=load.concat(allowed);
function isExist(total) {
    var exist = {};
    for(var i in total) {
        if(exist[total[i]]) {
            return true;
        }
        exist[total[i]] = true;
    }
    return false;
}
var existornot=isExist(total);
var devchannelname = baseinfo.devchannelname
if(!existornot){
	if(devchannelname == '未知渠道介绍' || devchannelname == '医院员工' || devchannelname == '种植-市场' || devchannelname == '渠道转诊' || devchannelname == '老患者介绍' || devchannelname == '陪同来诊' || devchannelname == '朋友介绍' || devchannelname == '自然到诊' || devchannelname == '内部员工开发'){
    	
  	} else{
 		$('#tool').attr('style', 'display:none');
 	}
}else{
	
	
}

//点击左侧表格 展示右侧个人信息
function showpersoninfo(usercode) {
    //清空国家地区
    $("#showprovinceandcity").html("");

    var baseinfo = getBaseInfoByUserCode(usercode);
    //console.log(baseinfo,+"<<<>???");
    if (baseinfo) {
    	loadData(baseinfo); //自动加载数据
    	
    	if(baseinfo.idcardno){
    		var idCard=baseinfo.idcardno;
    		idCard = idCard.replace(/(\d*)([0-9a-zA-Z]{6})/,"$1******");
    		$("#idcardno").text(idCard);
        }
    	
        if (baseinfo.barcode == null || baseinfo.barcode == "") {
            $("#barcodediv").hide();
        } else {
            $("#barcodediv").show();
            $("#barcode").attr("src", "data:image/png;base64," + baseinfo.barcode);
        }

        if (baseinfo.age != "0") {
            $("#age").html(baseinfo.age);
        }
//         if (baseinfo.activity != "") {
            $("#activity").html(baseinfo.activity);
//         }

        //如果用户被禁止查看患者手机号 则不显示
        if (canlookphone == 1) {
            $("#phonenumber1").html("");
            $("#phonenumber2").html("");
        }

        $("#memberlevel").html(baseinfo.important); //星级
        $("#totalpay").html(Number(baseinfo.totalpay).toFixed(2)); //总消费金额
        //国家sel
        var province = "",
        city = "",
        area = "",
        town = "",
        pct = "";
//      console.log(baseinfo,"baseinfo.province.length")
        if(baseinfo.province.length == "6"){
        	province =	baseinfo.provinceName
        	 pct += province;
        } else if (baseinfo.province != null && baseinfo.province != "") {
                 province = loc.find('0')[baseinfo.province];
                 pct += province;
        }

        if(baseinfo.city.length == "6"){
        	city =	baseinfo.cityName
        	pct += "-" + city;
        }else if (baseinfo.city != null && baseinfo.city != "") {
            city = loc.find('0,' + baseinfo.province)[baseinfo.city];
            pct += "-" + city;
        }
        
        if(baseinfo.area.length == "6"){
        	area = baseinfo.areaName
        	pct += "-" + area;
        }else if (baseinfo.area != null && baseinfo.area != "") {
        	area = loc.find('0,' + baseinfo.province)[baseinfo.area];
            pct += "-" + area;
        }
        
        if(baseinfo.town.length == "9"){
        	town = baseinfo.townName    
        	  pct += "-" + town;
        }else if (baseinfo.town != null && baseinfo.town != "") {
            town = loc.find('0,' + baseinfo.province + ',' + baseinfo.city)[baseinfo.town];
            pct += "-" + town;
        }
        //从下拉或者Input中获取数据 并展示
        $("#showprovinceandcity").html(pct);

        // -- 会员卡信息
        $("#money").html(baseinfo.money);
        $("#givemoney").html(baseinfo.givemoney);
		
        /** 微信标识 **/
        openid = baseinfo.openid;
        bindstatus = baseinfo.bindstatus;
        
        if(usercode){
        	if(openid && bindstatus == 0){
        		/*有微信绑定就高亮展示*/
            	$("#wechatId").css("color","#51C332");
            	/* $("#wechatId").addClass("active"); */
            }else{
            	$("#wechatId").css("color","#acacac"); 
            	/* 无绑定就灰色展示 */
            	/* $("#wechatId").removeClass("active");*/
            }
        }else{
        	$("#wechatId").css("display","none");
        }
        if(baseinfo.labelList){
        	var expense = $("#expense").text();
        	var society = $("#society").text();
        	var need = $("#need").text();
        	var expenseHtml="";
        	var societyHtml="";
        	var needHtml="";
        	
        	for(var i=0;i<baseinfo.labelList.length;i++){
        		var labelOne=baseinfo.labelList[i].labelOneName;
        		var labelTwo=baseinfo.labelList[i].labelTwoName;
        		var labelThree=baseinfo.labelList[i].labelThreeName;
        		var labelTwoId=baseinfo.labelList[i].labelTwoId;
        		var labelThreeId=baseinfo.labelList[i].labelThreeId;
        		
        		if(labelOne===expense){        			
        			$("#expenseShow").append('<li class="aa" id='+labelThreeId+' slid='+labelTwoId+'  slname='+labelTwo+' value='+labelThree+'>'+labelThree+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn" style="display:none">X</span></li>');    				
//         			expenseHtml=window.parent.$("#expenseShow").html()+'<li class="aa" id='+labelThreeId+' slid='+labelTwoId+'  slname='+labelTwo+' value='+labelThree+'>'+labelThree+'<span onclick="cleatBtn(this)" id="cleatBtn">X</span></li>';
//     				window.parent.$("#expenseShow").html(expenseHtml);
    				
        		}
        		if(labelOne===society){
					$("#societyShow").append('<li class="aa" id='+labelThreeId+' slid='+labelTwoId+'  slname='+labelTwo+' value='+labelThree+'>'+labelThree+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn" style="display:none">X</span></li>');    				
// 					societyHtml=window.parent.$("#societyShow").html()+'<li class="aa" id='+labelThreeId+' slid='+labelTwoId+'  slname='+labelTwo+' value='+labelThree+'>'+labelThree+'<span onclick="cleatBtn(this)" id="cleatBtn">X</span></li>';
//     				window.parent.$("#societyShow").html(societyHtml);
        			
        		}
        		if(labelOne===need){
					$("#needShow").append('<li class="aa" id='+labelThreeId+' slid='+labelTwoId+'  slname='+labelTwo+' value='+labelThree+'>'+labelThree+'<span onclick="cleatBtn(this)" id="cleatBtn" class="cleatBtn" style="display:none">X</span></li>');    				
// 					needHtml=window.parent.$("#needShow").html()+'<li class="aa" id='+labelThreeId+' slid='+labelTwoId+'  slname='+labelTwo+' value='+labelThree+'>'+labelThree+'<span onclick="cleatBtn(this)" id="cleatBtn">X</span></li>';
//     				window.parent.$("#needShow").html(needHtml);
    				
        			
        		}        		        		
        	}
        }
     
    }

}
 	

//删除三级标签
function cleatBtn(obj){	
	obj.parentNode.remove()
}

//关闭按钮

function closeIt(){
	 $("#patientLabel").hide();
}

//编辑按钮
function labelBtn2(){
	$(".cleatBtn").css("display","inline-block");
 	$("#labelBtn3").removeClass("hide");
 	$("#labelBtn2").addClass("hide");
 	$("#labelBtn").removeClass("hide");
}
//提交按钮
$('#labelBtn3').on('click', function(){
	$(".cleatBtn").css("display","none");
	$("#labelBtn2").removeClass("hide");
	$("#labelBtn3").addClass("hide");
	$("#labelBtn").addClass("hide");
	 //患者标签参数
	 var labelAllArr=[];
	 if($("#expenseShow").html()){
		 $("#expenseShow").find("li").each(function(i,ele){
			  var expenseObj={};	 
			  expenseObj.labelOneName= "消费标签";
			  expenseObj.labelThreeName= $(ele).attr("value");	
			  expenseObj.labelTwoName= $(ele).attr("slname");	
			  labelAllArr.push(expenseObj);
		 });
	 }
	 if($("#societyShow").html()){
		 $("#societyShow").find("li").each(function(i,ele){
			
			  var societyObj={};  
			  societyObj.labelOneName= "社会标签";
			  societyObj.labelThreeName= $(ele).attr("value");	
			  societyObj.labelTwoName= $(ele).attr("slname");				  					  
			  labelAllArr.push(societyObj);
			  
		 });
	 }
	 if($("#needShow").html()){
		 $("#needShow").find("li").each(function(i,ele){
			  var needObj={};
			  needObj.labelOneName= "需求标签";
			  needObj.labelThreeName= $(ele).attr("value");	
			  needObj.labelTwoName= $(ele).attr("slname");	
			  labelAllArr.push(needObj);
		 });
	 }
	// console.log(JSON.stringify(labelAllArr)+"--------------------");
	 //return;
	var userName= $("#username").text();
	var userCode = $("#usercode").text();
	var labelAllArr = JSON.stringify(labelAllArr);	
    var url = contextPath + '/KQDS_UserDocumentAct/updateLabel.act?usercode=' + usercode;  
    var params = { 
    			   userName : userName,
    			   userCode : userCode,
   		 		   labelAllArr : labelAllArr};

    $.axseSubmit(url, params, function() {}, function(r) {
		layer.alert(r.retMsrg, {
            end: function() {
          	
            }
      });
	}, function(r) {
		layer.alert(r.retMsrg, {
            end: function() {
            
            }
      });
	});
    
	
	
})

/**
 * 跳转到患者标签界面
 * @param usercode
 */
// function openPatientTag() {
// 	layer.open({
//         type: 2,
//         title: '患者标签',
//         shadeClose: true,
//         shade: 0.6,
//         area: ['680px', '450px'],
//         content: contextPath + '/KQDS_UserDocumentAct/toOpenPatientTagAdd.act?'
//     });
    
// }

function qygx() {
    parent.layer.open({
        type: 2,
        title: '亲友关系',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['830px', '450px'],
        content: contextPath + '/KQDS_ParticipantAct/toAdd.act?usercode=' + usercode,
        cancel: function() {
            $('#tabIframe').attr('src', contextPath + '/KQDS_ParticipantAct/toQygxList.act');
        }
    });
}
//重新获取所有患者的实收金额
function getSsje() {
    var msg = '重新获取所有患者的实收金额？';
    layer.confirm(msg, {
        btn: ['确认', '放弃']
    },
    function() {
        var detailurl = '<%=contextPath%>/KQDS_UserDocumentAct/getSsje.act';
        $.axseY(detailurl, null,
        function(data) {
            if (data.retState == '0') {
                layer.alert('已重新获取！', {
                      
                });
            } else {
                layer.alert('重新获取失败！', {
                      
                });
            }
        },
        function() {
            layer.alert('重新获取失败！'  );
        });
    },
    function() {
        layer.alert('重新获取失败！' );
    });
}


/******************** 微信相关 ********************/

/** 点击患者微信标识  **/
function openWeChat(){
	if (usercode != '') {
        // showpersoninfo(usercode);
    }
	if(openid && (bindstatus == 0)){ // 已绑定微信公众号
		// 判断是否有其他同事，正在和此患者聊天
		var rtData = getDataFromServer("WXSocketAct/check4Chat.act?openid=" + openid);
		
		if(rtData.userid){
			layer.alert('该患者正在与' + rtData.userid + '进行聊天！' );
			return false;
		}else{
			layer.open({
	            type: 2,
	            title: '微信',
	            maxmin: true,
	            shadeClose: true,
	            //点击遮罩关闭层
	            area: ['80%', '80%'],
	            content: contextPath + '/wechat/chat/index.html?openid=' + openid + '&userseqid=' + static_userseqid
	        });
		}
		
		
	}else{
		qrCodeCreate(); // 没有绑定，则弹出二维码，让其扫描绑定
	}
}

/** 微信相关 **/
function qrCodeCreate(){
	var url = contextPath + '/wechat/chat/bindQRcode.html?usercode=' + usercode;
	layer.open({
        type: 2,
        title: '扫码绑定微信公众号',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['500px', '500px'],
        content: url
    });
}

function refresh(){
	window.location.reload();
}

//按钮管理
/* function getButtonPower() {
    var menubutton1 = "",menubutton2 = "";
    for (var i = 0; i < listbutton.length; i++) {
    	//console.log("12456"+listbutton[i].qxName);
    	if (listbutton[i].qxName == "label_edit") {
    		menubutton1 += '<a href="javascript:void(0);" id="labelBtn2" class="bb" onclick="labelBtn2()">编辑标签</a>';
        }else if(listbutton[i].qxName == "label_examine"){
        	menubutton2 += '<a href="javascript:void(0);" id="seeAbout" class="bb" onclick="examineLabel()">查看标签</a>';
        }
    }
    $(".impText").append(menubutton1);
    $("#examine").append(menubutton2);
}
}
} */
 
</script>
</html>
