<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>患者建档-网电</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />

<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-select.min.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/hzjd.css">
</head>
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
    text-decoration: none;
    cursor: pointer;
    text-align: center;
}
.otherColor{
	background: #428bca;
	border: 1px solid #428bca;
}
.namecoloc{
	display: inline-block;
	font-size: 14px;
    line-height: 26px;
    font-weight: bold;
    margin-left: 5px;
    display: none;
}
.alla{
	display: flex;
	flex-wrap:wrap;
	margin-left: 10px;
}
.alla>li{
	margin-bottom: 10px;
	margin-left: 5px;
}
.labelfatherLi{
	width: 100%;
    margin: 10px 0 10px 10px;
}
.plus{
    line-height: 30px;
    margin: 0 auto;
    font-size: 24px;
    }
#lableShow{
      min-height: 90px;
  }
  .ageText{
	  	display: none;
	  	font-size: 12px;
	    position: absolute;
	    bottom: -43px;
	    left: 0px;
	    color: #a94442;
	  }
/* 搜索框select */
 	.select2:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
      width: 200px;   
     }  
.select2>.btn { 
    width: 200px; 
 	height:28px; 
 	border: solid 1px #e5e5e5; 
}
.sel3:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
      width: 110px;   
     }  
.sel3>.btn { 
    width: 110px; 
 	height:28px; 
 	border: solid 1px #e5e5e5; 
}
.sel3.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
    width: 110px !important; 
}
.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
    color: #999;
    height: 28px;
}
.pull-left {
   float: left !important;
   color: #333;
}
.btn-group > .btn:first-child:hover {
    background: white;
}
.provinceReg i.form-control-feedback {
    right: -30px; 
}
.bootstrap-select > .dropdown-toggle {
    z-index: 0; 
}
.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
   width: 200px;
}
.bootstrap-select > .dropdown-toggle {
   height: 28px;
}
</style>
<body>

<div class="containerNet">	<!-- .containerNet网电建档容器样式 -->
	<form id="defaultForm">
		<table class="tableContent">	<!--tableContent 布局样式  -->
			<tr>
				<td>	<!-- .impText 必填信息样式-->
					<span class="impText">所属门诊*</span>
				</td>
				<td>
					<select id="organization" name="organization">
                	</select>
				</td>
			</tr>
			
			<tr class="addVisitingDialog-content">	<!-- .addVisitingDialog-content 本身无样式 与星星功能有关 -->
				<td>	<!-- .impText 必填信息样式-->
					<span class="impText">患者编号*</span>
				</td>
				<td>
					<div class="form-group">	<!-- form-group与表单验证有关 -->
						<input type="text" name="usercode" id="usercode" readonly="readonly">
	                	<input type="hidden" name="type" id="type" value="1" readonly="readonly"><!-- 网电类型 -->
	               		<input type="hidden" name="important" id="important" readonly="readonly">
					</div>
				</td>&nbsp;&nbsp;
				<td>
					<span class="impText">客户等级*</span>
				</td>
				<td colspan="2" class="starsBox">	<!-- .starsBox星星的父元素 -->
					<span class="hc-icon icon20 stars-icon" value="1"></span>
                    <span class="hc-icon icon20 stars-icon" value="2"></span>
                    <span class="hc-icon icon20 stars-icon" value="3"></span>
                    <span class="hc-icon icon20 stars-icon" value="4"></span>
                    <span class="hc-icon icon20 stars-icon" value="5"></span>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="impText">姓名*</span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="username" id="username" placeholder="请输入姓名">
					</div>
				</td>
				<td>
					<span class="impText">性别*</span>
				</td>
				<td>
					<div class="form-group sexReg">	<!--.sexReg性别需要调整的样式  -->
						<input id="maleId" type="radio" name="sex" value="男"><label for="maleId" class="sexValue" style="font-weight: bold;font-size: 13px;" >男</label>
						<input id="femaleId" type="radio" name="sex" value="女"><label for="femaleId" class="sexValue" style="font-weight: bold;font-size: 13px;">女</label>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="impText">电话1*</span>
				</td>
				<td>
					<div class="form-group">	<!--.sel_short_inp_long  select元素与input组合时的样式 -->
		                <select class="sel_short_inp_long"  id="familyship" name="familyship">
		                    <option value="本人">本人</option>
		                    <option value="家人">家人</option>
		                </select>				<!--.sel_short_inp_long  select元素与input组合时的样式 -->
		                <input class="sel_short_inp_long" type="text" id="phonenumber1" name="phonenumber1" maxlength="11" placeholder="请输入11位电话号码"  onblur="phoneNumberCheck();"/>
					</div>
				</td>
				<td>
					<span class="comText">电话2</span>
				</td>
				<td>
					<div class="form-group">
		                <select class="sel_short_inp_long">
		                    <option>本人</option>
		                    <option>家人</option>
		                </select>
		                <input class="sel_short_inp_long" type="text" id="phonenumber2" name="phonenumber2" maxlength="11" placeholder="请输入11位电话号码" >
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!-- .comText选填文本 -->
					<span class="comText">出生年月</span>
				</td>
				<td class="relative">
					<div class="form-group">
						<input size="12" type="text" name="birthday" id="birthday" value="" placeholder="请选择出生年月"  class="birthDate" onchange="changeAge();" />
					</div>
				</td>
				<td>
					<span class="comText impText">年 龄*</span>
				</td>
				<td>
					<div class="form-group" style="position: relative;">
						<input type="text" id="age" name="age" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="3" onafterpaste="this.value=this.value.replace(/\D/g,'')" onclick="changeAge();" oninput="ageHint();" placeholder="请输入真实年龄" />
						<span class="ageText">您填写的患者年龄小于10！</span>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="impText">患者来源*</span>
				</td>
				<td class="relative">
					<div class="form-group">                                                                                                <!-- 有add参数就不显示禁用的 -->
<!-- 						<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype', 'add');"> -->
<!-- 						</select> -->
						<select class="patients-source select2 dict searchSelect" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelectSearch('devchannel','nexttype', 'add');" data-live-search="true" title="请选择">
						</select>
					</div>
				</td>
				<td>
					<span class="impText">子分类*</span>
				</td>
				<td>
					<div class="form-group">
<!-- 						<select name="nexttype" id="nexttype"> -->
<!-- 		                  <option value="">请选择</option> -->
<!-- 						</select> -->
						<select  class="searchSelect" name="nexttype" id="nexttype" data-live-search="true" title="请选择">
						</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="comText">职业</span>
				</td>
				<td><!-- .select2 dict本身无样式 与功能有关 -->
<!-- 					<select class="select2 dict" tig="job" name="profession" id="profession"> -->
<!-- 					</select> -->
					<select class="select2 dict searchSelect" tig="job" name="profession" id="profession" data-live-search="true" title="请选择">
					</select>
				</td>
				<td>
					<span class="comText">身份证号</span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="idcardno" id="idcardno" placeholder="身份证号" maxlength="18">
					</div>
				</td>
			</tr>
			
			<!-- 
			<tr>
				<td>
					<span class="impText">国家/地区*</span>
				</td>
				<td colspan="3">
					<div class="form-group provinceReg">.provinceReg 验证信息位置 调整样式
						<select class="sel3" id="province" name="province"></select> 
						<select class="sel3" id="city" name="city" ></select>
						<select class="sel3" id="town" name="town"></select>
					</div>
				</td>
			</tr> 
			-->
			<tr>
				<td>
					<span class="impText">地址信息*</span>
				</td>
				<td colspan="3">
					<div class="form-group provinceReg">
<!-- 						<select class="sel3" id="province" name="province" onchange="initCity()"></select>  -->
<!-- 						<select class="sel3" id="city" name="city" onchange="initArea()"></select> -->
<!-- 						<select class="sel3" id="area" name="area" onchange="initStreet()"></select> -->
<!-- 						<select class="sel3" id="town" name="town" ></select> -->
						<select class="sel3 searchSelect" id="province" name="province" onchange="initCity()" data-live-search="true" title="请选择"></select> 
						<select class="sel3 searchSelect" id="city" name="city" onchange="initArea()" data-live-search="true" title="请选择"></select>
						<select class="sel3 searchSelect" id="area" name="area" onchange="initStreet()" data-live-search="true" title="请选择"></select>
						<select class="sel3 searchSelect" id="town" name="town" data-live-search="true" title="请选择"></select>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<span class="comText">详细地址</span>
				</td>
				<td colspan="3">
					<input class="sel_address" type="text" placeholder="详细地址" name="address" id="address">
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="comText">就诊经历</span>
				</td>
				<td>
					<input type="text" placeholder="就诊经历" id="experience" name="experience">
				</td>
				<td>
					<span class="comText">洁牙习惯</span>
				</td>
				<td>
					<select id="habit" name="habit">
						<option value="">请选择</option>
						<option value="有">有</option>
						<option value="无">无</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<span class="impText">是否他人介绍*</span>
				</td>
				<td>
					<div class="form-group">
						<select class="dict" name="introduce" id="introduce">
							<option value="">-请选择-</option>
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</div>
				</td>
				<td>
					<span class="comText">介绍人</span>
				</td>
				<td>
					<input type="hidden" name="introducer" id="introducer" value="" /> 
					<input type="text" class="whiteInp" id="introducerDesc" name="introducerDesc" onclick="getuser()" placeholder="介绍人">
				</td>
			</tr>
			
			<tr>
				<td>
					<span id="kfrlable" class="comText">开发人</span>
				</td>
				<td>
					<div class="form-group">
					<input type="hidden" name="developer" id="developer"  class="form-control" />
			 		<input type="text" class="whiteInp" id="developerDesc" name="developerDesc"  placeholder="开发人" onClick="javascript:xxbbValidate();">	
			 		</div>
				</td>
				 <!-- <td>
				     <span id="kfrlable" class="comText">爱好</span>
				 </td>
				 <td>
				      <div class="form-group">

				        <select id="hobby" name="hobby" tig="ah693" class="dict selectpicker" multiple data-live-search="true">
						    
					  	</select>
				      </div>
               	 </td> -->
			</tr>
			
			<!-- 添加社会标签 -->
			<tr>
				<td>
					<span class="comText">参加本院活动</span>
				</td>
				<td>
					<!-- <input type="text" placeholder="参加本院活动" id="activity" name="activity"> -->
					<div class="form-group">
						<select class="dict" tig="glhd238" name="activity" id="activity" >
						</select>
					</div>
				</td>
				<td>
					<span class="comText">QQ/微信</span>
				</td>
				<td>
					<input type="text" placeholder="QQ/微信" id="qq" name="qq">
					<!-- <a href="javascript:void(0);"></a> -->
				</td>
				<!-- <td>
					<span class="comText">是否介绍他人</span>
				</td>
				<td>
					<input type="text" placeholder="是否介绍他人" id="introduce" name="introduce">
				</td> -->
			</tr>
			
			<tr class="textareaTr1"><!-- textareaTr1 调整该行的行高   -->
				<td>
					<span class="comText">商务通身份证</span>
				</td>
				<td colspan="3">
					<textarea class="passportNum" name="xueli" id="xueli" rows="1"  placeholder=""></textarea>
				</td>
			</tr>
			
			<tr style="height:5px;">
				<td colspan="4" class="relative">		<!--分隔线  -->
					<hr class="line" style="margin:2px 0 5px;">
				</td>
			</tr>

			<tr>
				<td>
					<span class="impText">投放项目*</span>
				</td>
				<td>
					<div class="form-group">
						<select class="dict" tig="SLLX" name="accepttype" id="accepttype" >
						</select>
					</div>
				</td>
				<td>
					<span class="impText">受理工具</span>
				</td>
				<td>
					<div class="form-group">
						<select class="dict" tig="SLGJ" name="accepttool" id="accepttool" >
						</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="impText">咨询项目*</span>
				</td>
				<td>
					<div class="form-group">
						<select class="dict" tig="ZXXM" name="askitem" id="askitem" >
						</select>
					</div>
				</td>
				<td>
					<span class="comText">预约日期</span>
				</td>
				<td>
					<input type="text" id="ordertime" name="ordertime" value="" readonly="readonly" >
				</td>
			</tr>
			
			<tr class="textareaTr3" style="height:204px;"><!-- textareaTr3 调整该行的行高   -->
				<td>
					<span class="impText">咨询内容*</span>
				</td>
				<td colspan="3">
					<div class="form-group">
						<textarea style="height:180px" name="askcontent" id="askcontent" rows="3" ></textarea>
					</div>
				</td>
			</tr>
			
			
			<tr id="conceal" class="textareaTr3" style="width:527px;position: static;"><!-- textareaTr3 调整该行的行高   -->
                <td>
                	<span class="impText">患者标签*</span>
                </td>
				<td colspan="3">
					<div style="border-radius: 4px;border: solid 1px #e5e5e5;margin-bottom: 20px;max-width: 559px;">
					    <ul id="lableShow">
					    	<li class="labelfatherLi">	
								<div class="namecoloc">消费标签:</div>
								<ul class="alla" id="expenseShow">
								</ul>
							</li>
							<li class="labelfatherLi">	
								<div class="namecoloc">社会标签:</div>
								<ul class="alla" id="societyShow">
								</ul>
							</li>
							<li class="labelfatherLi">	
								<div class="namecoloc">需求标签:</div>
								<ul class="alla" id="needShow">
								</ul>
							</li>
					    </ul>
					</div>
				</td>	
			 <td style="padding-left: 0;">
                	<button id="labelBtn" class="aa" onclick="openPatientTag();">
                	 <span class="plus">+</span>
                	</button>
                </td>				
			</tr>						
		</table>
	</form>

	<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
		<div class="clear2"></div>
       	<a class="kqdsCommonBtn" id="clean">清 空</a>
       	<a class="kqdsCommonBtn" id="close">取 消</a>
       	<a class="kqdsSearchBtn" id="submit">确 定</a>
	</div>
	
</div>
</body>

<script type="text/javascript">
//### 这个变量声明在下面，hzjd.js调用不到
var contextPath = '<%=contextPath%>';
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-select.min.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>  --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/initArea.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/hzjd/hzjd.js"></script>

<script type="text/javascript">
 
$(window).on('load', function () {     
$('.selectpicker').selectpicker({
     'selectedText': 'cat'
      });
    });
var intoPageDateTime = new Date();
var usercodechild = "";//接收layer子窗口传值
var usernamechild = "";//接收layer子窗口传值
var isyx = parent.isyx;
var onclickrowobj = window.parent.onclickrowOobj; //父页面传值
//信息报备权限，即是否具备修改开发人员的权限  0可以 1不可以
var canEditKf = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag7_canEditKf, request)%>';
var frameSelfindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var labelUsercode;
$(function(){
	prov();
	initCity();
	initArea();
	initStreet();
	/** ########################################################### 连锁相关  **/
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	if($("#organization").val()){
		getUserCode($("#organization").val());//获取病人编号
	}
	$("#organization").change(function(){
		var currSelect = $(this).val();
		if(currSelect){
			getUserCode(currSelect);//获取病人编号
		}else{
			$("#usercode").val('');
		}
	});   
	$("#ordertime").datetimepicker({
       	language:  'zh-CN',  
   	    minView:0,
        format: 'yyyy-mm-dd hh:ii',
   	    autoclose : true,//选中之后自动隐藏日期选择框   
   	    pickerPosition: "bottom-right"
    });
	/** ########################################################### 连锁相关  end **/
	
    //showLocation();//省市级联
 	//默认选中北京市
  	$("#province").val("110000").trigger("change");
  	$("#city").val("110100").trigger("change");
  	$("#area").val("110101").trigger("change");
//   $("#town").val("110101001").trigger("change");
  	
  		$('#province').on('click', function(){
  	 	     var val = $('#city option:eq(1)').val()
  	 	     $("#city option[value='" + val + "']").attr("selected", true);
 	 	 	   var cityName = $("#city").val()
 		 	   initArea(cityName)
 		 	   var areaName = $('#area option:eq(1)').val()
 	 	 	   $("#area option[value='" + areaName + "']").attr("selected", true);
 			   initStreet(townName)
 		 	   var townName = $('#town option:eq(1)').val()
 		 	   $("#town option[value='" + townName + "']").attr("selected", true);
  	 	})
 	
	initDictSelectByClass();// 受理类型、受理工具、咨询项目、患者来源、职业
	
    //弹窗内星级评分
    jsJb();
    if(canEditKf != 1 && isyx != "1"){ // 没有权限并且，不是营销中心，则不允许设定开发人
    	$("#developerDesc").attr('readonly','readonly').attr('onclick','').click( eval(function(){
    		alert('无设定权限！');
    	})); 
    }
    //营销中心 开发人 必填
    if(isyx == "1"){
    	//验证表单
    	Yztable_net_yx();
    	// $("#kfrlable").addClass("impColor");
    }else{
    	Yztable_net();
    }
    
    
    labelUsercode=$("#usercode").val(); //向标签页面传患者usercode 2019/8/23 lutain
    $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
});
$('#clean').on('click', function(){
	var usercode = $('input[name=usercode]').val();
	$("#defaultForm")[0].reset();//核心
	$('input[name=usercode]').val(usercode);
	$('input[name=developer]').val("");
	$('input[name=introducer]').val("");
	$('input[name=important]').val("");
    $(".selectpicker").find("li").attr("class","");
    $(".pull-left").text("");
    $("#expenseShow").html("");
    $("#societyShow").html("");
    $("#needShow").html("");
	
    
	$("#province").val("110000").trigger("change");
	//点击清除默认值改变为北京，从新调用
	prov();
	initCity();
	initArea();
	initStreet();
	$(".stars-icon").each(function(i, n) {
        $(this).attr('class', 'hc-icon icon20 stars-icon');
    });
	$("#defaultForm").data('bootstrapValidator').resetForm();
});

function phoneNumberCheck(){
	 var phonenumber1 = $("#phonenumber1").val();
	 if(!(/\d{11}$/.test(phonenumber1))){
	  layer.alert("请输入正确的电话号码！",function(){
	    $("#phonenumber1").val("");
	    layer.closeAll('dialog');
	   }); 
	 }
	}

function cleatBtn(obj){
	obj.parentNode.remove()
}


$('#submit').on('click', function(){

	 if($("#ordertime").val() != ""){
		 var ordertimeObj = new Date($("#ordertime").val() + ':00'); // 2017-05-04 17:10
		 if(intoPageDateTime.getTime() >= ordertimeObj.getTime()){
				layer.alert('预约日期不得早于操作时间' );
				return false;
		 }
	 }


	 if($("#ordertime").val() != ""){
		 var ordertimeObj = new Date($("#ordertime").val() + ':00'); // 2017-05-04 17:10
		 if(intoPageDateTime.getTime() >= ordertimeObj.getTime()){
				layer.alert('预约日期不得早于操作时间' );
				return false;
		 }
	 }

 	var hobby = $("#hobby").val();
 	//console.log(hobby,"hobbyhobbyhobby")
 	var activity = $("#activity").val();
 	//是否必选校验
 	var introduce = $("#introduce").val(); 	
 	if(introduce == ""){
    	layer.alert("请选择是否他人介绍！");
        return false;
    } 	
 	var introducer = $("#introducer").val();
 	if (introduce == "是") {
 		if (introducer == "" || introducer == null || introducer == "null" || introducer == "undefined") {
	 		layer.alert("如果有介绍人，请选择介绍人！");
	 		return false;
 		}
 	}
//  var hobby = ah.toString();
	if($("#ordertime").val() != ""){
		var ordertimeObj = new Date($("#ordertime").val() + ':00'); // 2017-05-04 17:10
		if(intoPageDateTime.getTime() >= ordertimeObj.getTime()){
			layer.alert('预约日期不得早于操作时间' );
			return false;
		}
	}
	
   var star = document.getElementsByClassName('hc-icon icon20 stars-icon hc-active')
   if (!star.length){
	 layer.alert('客户等级必须填写！' );
	 return false;	
   }
	
	var username = $("#username").val();
	if(!username){
		layer.alert('患者姓名必须填写！' );
		return false;
	}
	var val = $('input:radio[name="sex"]:checked').val();
    if(val==null){
    	layer.alert("请选择性别！");
        return false;
    }
	var phonenumber1 = $("#phonenumber1").val();
	var phonenumber2 = $("#phonenumber2").val();
	if(phonenumber1 == phonenumber2){
		layer.alert('手机号码1和手机号码2不能填写相同的值！' );
		return false;
	}

	// 2019/8/4  lutian 患者年龄校验必填
	/* var age=$("#age").val();
	if(!age){
		layer.alert('患者年龄必须填写！' );
		return false;
	} */
	var age=$("#age").val();
	if(!age || age<=0){
		layer.alert('患者年龄必须填写且大于零！' );
		return false;
	}
	
	var devchannel = $("#devchannel").val();
	if(!devchannel){
		layer.alert('患者来源必须选择！' );
		return false;
	}
	var nexttype = $("#nexttype").val();
	if(!nexttype){
		layer.alert('患者来源子分类必须选择！' );
		return false;
	}
	var province=$("#province").val();
	if(!province){
		layer.alert('省份必须选择！' );
		return false;
	}
	var city=$("#city").val();
	if(!city){
		layer.alert('城市必须选择！' );
		return false;
	}
	var area=$("#area").val();
	if(!area){
		layer.alert('城市区域必须选择！' );
		return false;
	}
	var town=$("#town").val();
	if(!town){
		layer.alert('街道必须选择！' );
		return false;
	}
	//2019-8-12 syp 
	var accepttype = $("#accepttype").val();
	if(!accepttype){
		layer.alert('投放项目必须选择！' );
		return false;
	}
	var accepttool = $("#accepttool").val();
	if(!accepttool){
		layer.alert('受理工具必须选择！' );
		return false;
	}
	var askitem = $("#askitem").val();
	if(!askitem){
		layer.alert('咨询项目必须选择！' );
		return false;
	}
	var askcontent = $("#askcontent").val();
	if(!askcontent){
		layer.alert('咨询内容必须填写！' );
		return false;
	}
	 var param = $('#defaultForm').serialize();

	 //患者标签参数
	 var labelAllArr=[];
	 var exploit;
	 var exploit1;
	 var exploitId;
	 var exploitId1;
	 var exploitStatus;
	 var exploitStatus1;
	 if($("#societyShow").html()){
		 $("#societyShow").find("li").each(function(i,ele){
			  var societyObj={};
			  societyObj.opinion= $(this).attr("title");
			  societyObj.labelThreeName= $(this).attr("value");
			  societyObj.labelThreeId= $(this).attr("id");
			  societyObj.labelTwoName= $(this).attr("slname");
			  societyObj.labelTwoId= $(this).attr("slid");
			  societyObj.labelOneName= "社会标签";
			  societyObj.labelOneId= "13543c4d-f81e-4251-87a1-f07984022e9f";
			  labelAllArr.push(societyObj);
		 });
	 }
	 if($("#needShow").html()){
		 $("#needShow").find("li").each(function(i,ele){
			  var needObj={};
			  needObj.labelThreeName= $(this).attr("value");
			  needObj.labelThreeId= $(this).attr("id");
			  needObj.labelTwoName= $(this).attr("slname");
			  //可开发项目
			  if($(this).attr("slid")=="61223947-c0da-4d0f-a1ff-429232d10a3c"){
				  exploitStatus=1;
				  exploit=$(this).attr("privelist");
				  exploitId=$(this).attr("id");
			  }
			 //可开发空间
			  if($(this).attr("slid")=="3d6b7239-c964-4714-8020-4dcc15ed1f5d"){
				  exploitStatus1=2;
				  exploit1=$(this).attr("privelist");
				  exploitId1=$(this).attr("id");
			  }
			  needObj.labelTwoId= $(this).attr("slid");
			  needObj.labelOneName= "需求标签";
			  needObj.labelOneId= "a5cb2fa0-952f-4e45-b5e9-c6be12f4baf0";
			  labelAllArr.push(needObj);
		 });
	 }
	 if($("#expenseShow").html()){
		 $("#expenseShow").find("li").each(function(i,ele){
			  var expenseObj={};
			  expenseObj.labelThreeName= $(this).attr("value");
			  expenseObj.labelThreeId= $(this).attr("id");
			  expenseObj.labelTwoName= $(this).attr("slname");
			  expenseObj.labelTwoId= $(this).attr("slid");
			  expenseObj.labelOneName= "消费标签";
			  expenseObj.labelOneId= "ad2eae81-310c-45f0-a667-8a8f2383b168";
			  labelAllArr.push(expenseObj);
		 });
	 }
	 var labelAllArr = JSON.stringify(labelAllArr);
	 exploit = JSON.stringify(exploit);
	 exploit1 = JSON.stringify(exploit1);
	 exploit = encodeURIComponent(exploit);
	 exploit1 = encodeURIComponent(exploit1);
	 labelAllArr = encodeURIComponent(labelAllArr);
     var url = contextPath + '/KQDS_UserDocumentAct/insert.act?' + param;
     var usercode = $("#usercode").val();
     var params = { hobby : hobby,
    		 		labelAllArr : labelAllArr,
    		 		exploit:exploit,
    		 		exploit1:exploit1,
    		 		exploitId:exploitId,
    		 		exploitId1:exploitId1,
    		 		exploitStatus:exploitStatus,
    		 		exploitStatus1:exploitStatus1
				};

     $.axse(url, params,
     function(r) {
         if (r.retState == "0") {
       			layer.open( {
       				content: '创建成功',
       				closeBtn: 0,
                end: function() {
                	 try{
			  	    		parent.$("#table")[0].contentWindow.refresh();
			  	    	}catch(e){
			  	    		parent.refresh();
			  	    	}
                    layer.confirm('是否添加回访记录', {
           				closeBtn: 0,
                        btn: ['是', '否']
                    },
                    function() {
                        layer.closeAll('dialog');
                        // 根据姓名、手机号查询刚刚创建的患者
                        var usercode = $("#usercode").val();
                        parent.layer.open({
                        	type: 2,
               	            title: '添加回访',
               	            shadeClose: false,
               	            shade: 0.6,
               	            area: ['550px', '480px'],
                            content: contextPath + '/KQDS_VisitAct/toVisitAdd.act?lytype=usermanager&type=khgl&usercode=' + usercode
                        });
                        var frameindex= parent.layer.getFrameIndex(window.name);
                        parent.layer.close(frameindex); //执行关闭
                    },
                    function() {
                    	var frameindex= parent.layer.getFrameIndex(window.name);
                   	 	parent.layer.close(frameindex); //执行关闭
                    });
                }
            });
             return true;
         } else if(r.retState == "100"){ // 患者编号重复
        	 getUserCode();//获取病人编号
        	 layer.alert('患者编号已存在，系统已自动获取新的病历号，请再次进行提交操作。', {
                   
             });
        	 
         }else{
        	 layer.alert('建档失败：' + r.retMsrg, {
                   
             });
             return false;
         }
     },
     function() {
         layer.alert('建档失败', {
               
         });
         return false;
     });
});
$('#close').on('click', function(){
	parent.layer.close(frameSelfindex); //执行关闭
});
</script>
<style type="text/css">
/* 这里写样式是因为出生年月，后面增加了日期图标，如果不写下面的样式，出生年月会占两行 */
button{
    font: normal;
    line-height:normal;
}
</style>
</html>