<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String type = request.getParameter("type");
	if (type == null || "".equals(type)) {
		type = "0";
	}

	String usercode = request.getParameter("usercode");
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>患者建档-网电</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/addVisting.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/hzjd.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style>
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 200px;   
      }  
	.searchSelect>.btn { 
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
</style>
</head>

<body>

<div class="containerNetEdit">	<!-- .containerNetEdit网电建档容器样式 -->
	<form id="defaultForm" style="padding-bottom:30px;">
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
						<input type="hidden" name="wdseqId" id="wdseqId"><!-- 网电主键 -->
						<input type="hidden" name="seqId" id="seqId">
		                <input type="hidden" name="organization" id="organization"><!-- 取当前患者的属性值，为了患者来源编辑时，赋值成功 -->
		                <input type="text" name="usercode" id="usercode" readonly="readonly">
		                <input type="hidden" name="doorstatus" id="doorstatus"><!-- 上门状态 -->
		                <input type="hidden" name="type" id="type" value="1" readonly="readonly">
		                <input type="hidden" name="important" id="important" readonly="readonly">
		                <input type="hidden" name="createuser" id="createuser">
					</div>
				</td>
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
					<div class="form-group">	<!-- form-group与表单验证有关 -->
						<input type="text" name="username" id="username" placeholder="请输入姓名">
					</div>
				</td>
				<td>
					<span class="impText">性别*</span>
				</td>
				<td>
					<div class="form-group sexReg">	<!--.sexReg性别需要调整的样式  -->
						<input id="maleId" type="radio" name="sex" value="男"><label for="maleId" class="sexValue">男</label>
						<input id="femaleId" type="radio" name="sex" value="女"><label for="femaleId" class="sexValue">女</label>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="impText">电话1*</span>
				</td>
				<td>
					<div class="form-group">	<!-- form-group与表单验证有关 -->
		                <select class="sel_short_inp_long" id="familyship" name="familyship">		<!--.sel_short_inp_long  select元素与input组合时的样式 -->
		                    <option value="本人">本人</option>
		                    <option value="家人">家人</option>
		                </select>			<!--.sel_short_inp_long  select元素与input组合时的样式 -->
		                <input class="sel_short_inp_long" type="text" id="phonenumber1" name="phonenumber1" maxlength="12" placeholder="请输入11位电话号码" >
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
		                <input class="sel_short_inp_long" type="text" id="phonenumber2" name="phonenumber2" maxlength="12" placeholder="请输入11位电话号码" >
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
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
					<input type="text" id="age" name="age" onclick="changeAge();" placeholder="请输入真实年龄" />
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="impText">患者来源*</span>
				</td>
				<td class="relative">
					<div class="form-group">
<!-- 						<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype', 'add');"> -->
<!-- 						</select> -->
						<select class="patients-source select2 dict searchSelect" source="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelectSearchfilter(deptId,'devchannel','nexttype', 'add');" data-live-search="true" title="">
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
						<select name="nexttype" id="nexttype" class="searchSelect" data-live-search="true" title="">
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
			<tr>
				<td>
					<span class="comText">紧急联系人</span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="emergencyContact" id="emergencyContact" placeholder="紧急联系人">
					</div>
				</td>
				<td>
					<span class="comText">紧急联系人电话</span>
				</td>
				<td>
					<div class="form-group">
						<input class="sel_short_inp_long" type="text" name="emergencyPhone" id="emergencyPhone" placeholder="请输入11位电话号码" maxlength="11">
					</div>
				</td>
			</tr>
			<!-- <tr>
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
			</tr> -->
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
					<input class="sel_address" type="text" placeholder="联系地址" name="address" id="address">
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
					<span class="comText">QQ/微信</span>
				</td>
				<td>
					<input type="text" placeholder="QQ/微信" id="qq" name="qq">
					<!-- <a href="javascript:void(0);"></a> -->
				</td>
				<td>
					<span class="comText">介绍人</span>
				</td>
				<td>
					<input type="hidden" name="introducer" id="introducer" value="" /> 
					<input type="text" class="whiteInp" id="introducerDesc" name="introducerDesc"  onclick="getuser()" placeholder="介绍人">
				</td>
			</tr>
			
			<tr>
				<td>
					<span id="kfrlable" class="comText">开发人</span>
				</td>
				<td>
					<div class="form-group">
					<input type="hidden" name="developer" id="developer"  class="form-control" />
			 		<input type="text" class="whiteInp" id="developerDesc" name="developerDesc" placeholder="开发人" onClick="javascript:xxbbValidate('<%=person.getUserPriv()%>','<%=person.getSeqId()%>');"></input>	
					</div>
				</td>
				<td id ="jdrtitle">
					<span class="comText">建档人</span>
				</td>
				<td id ="jdrinput">	<!-- .whiteInp  input白底鼠标移入的效果-->
					<input type="hidden" name="createuser" id="createuser"  class="form-control" />
		 			<input type="text"  id="createuserDesc" name="createuserDesc" placeholder="建档人" onClick="javascript:single_select_user(['createuser', 'createuserDesc']);"  ></input>	
				</td>
			</tr>
			
			<tr class="textareaTr1"><!-- textareaTr1 调整该行的行高   -->
				<td>
					<span class="comText">商务通身份证</span>
				</td>
				<td colspan="3">		<!-- passportNum 商务通身份证 特别调整样式-->
					<textarea class="passportNum" name="xueli" id="xueli" rows="1"  placeholder=""></textarea>
				</td>
			</tr>
			
			<tr style="height:5px;">
				<td colspan="4" class="relative">		<!--分隔线  -->
					<hr class="line" style="margin:2px 0 6px;">
				</td>
			</tr>

			<tr>
				<td>
					<span class="impText">受理类型*</span>
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
						<select class="dict searchSelect" tig="SLGJ" name="accepttool" id="accepttool" data-live-search="true" title="请选择">
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
					<input type="text" id="ordertime" name="ordertime" value="" readonly="readonly"  >
				</td>
			</tr>
			
			<tr class="textareaTr3" style="height:204px"><!-- textareaTr3 调整该行的行高   -->
				<td>
					<span class="impText">咨询内容*</span>
				</td>
				<td colspan="3">
					<div class="form-group">
						<textarea style="height:180px" name="askcontent" id="askcontent" rows="3" ></textarea>
					</div>
				</td>
			</tr>
		</table>
	</form>
	
	<div class="fixedBottomDiv">	<!--netBtnDiv按钮组样式  -->
		<a id="bzblk" href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()">保存</a>
	</div>
	
</div>
</body>

<script type="text/javascript">
//### 这个变量声明在下面，hzjd.js调用不到
var contextPath = '<%=contextPath%>';
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/initArea.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ui.datepicker-zh-CN.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/hzjd/hzjd.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<script type="text/javascript">
//当前登陆部门
var deptId = '<%=person.getDeptId()%>'; 
var intoPageDateTime = new Date();
var usercodechild = "";//接收layer子窗口传值
var usernamechild = "";//接收layer子窗口传值
var isyz = true;
var usercode = '<%=usercode%>';
//信息报备权限，即是否具备修改开发人员的权限  0可以 1不可以
var canEditKf = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag7_canEditKf, request)%>';
var canEditWd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag8_canEditWd, request)%>';
var canEditJd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag12_canEditJdr, request)%>';
var canEditPhone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag15_canEditPhone, request)%>';
var canEditHzly = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag16_canEditHzly, request)%>';
var isyx = parent.isyx;
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var static_userobj = null;
var lastnexttype;
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
	
	static_userobj = getBaseInfoByUserCode(usercode);
	lastnexttype=static_userobj.nexttype
//      console.log(static_userobj,"static_userobj")
	$("#organization").val(static_userobj.organization);
     
     
 	//默认选中北京市
  	$("#province").val("110000").trigger("change");
  	$("#city").val("110100").trigger("change");
  	$("#area").val("110101").trigger("change");
//   	$("#town").val("110101001").trigger("change");
  	
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
     
//  	showLocation();//省市级联
	initDictSelectByClassIsClient(deptId); //  患者来源（客户部门登录判断专用）
	initDictSelectByClass();// 受理类型、受理工具、咨询项目、患者来源、职业
	 //加载患者数据
    getDetail();
    getnetorder();
    //弹窗内星级评分
    jsJb();
    if(canEditKf != 1 && isyx != "1"){ // 没有权限并且，不是营销中心，则不允许设定开发人
    	$("#developerDesc").attr('readonly','readonly').attr('onclick','').click( eval(function(){
    		alert('无修改权限！');
    	})); 
    }
    
    if(canEditJd != 1){
    	$("#createuserDesc").attr('readonly','readonly').attr('onclick','').click( eval(function(){
    		alert('无修改权限！');
    	})); 
    }
    if(canEditWd != 1){
    	$("#organization").attr("disabled","disabled");
   		$("#accepttype").attr("disabled","disabled");
   		$("#accepttool").attr("disabled","disabled");
   		$("#askitem").attr("disabled","disabled");
   		$("#askcontent").attr("disabled","disabled");
   		$("#ordertime").attr("disabled","disabled");
   		$("#birthday").attr("disabled","disabled");
   		$("#age").attr("disabled","disabled");
   		$("#devchannel").attr("disabled","disabled");
   		$("#nexttype").attr("disabled","disabled");
   		$("#profession").attr("disabled","disabled");
   		$("#idcardno").attr("disabled","disabled");
   		//$("#province").attr("disabled","disabled");
   		//$("#city").attr("disabled","disabled");
   		//$("#town").attr("disabled","disabled");
   		//$("#address").attr("disabled","disabled");
   		$("#experience").attr("disabled","disabled");
   		$("#habit").attr("disabled","disabled");
   		$("#qq").attr("disabled","disabled");
   		$("#introducerDesc").attr("disabled","disabled");
   		$("#xueli").attr("disabled","disabled");
   		$(".starsBox").removeClass("starsBox");
    }
   	if(static_userobj.doorstatus != '0'){
   		$("#ordertime").attr("disabled","disabled");
   	}
   	$("#province").val(static_userobj.province);
   	$("#city").val(static_userobj.city);
  	$("#area").val(static_userobj.area);
    $("#town").val(static_userobj.town);

    
    //营销中心 开发人 必填
    if(isyx == "1"){
    	//验证表单
    	Yztable_net_yx();
    	// $("#kfrlable").addClass("impColor");
    }else{
    	Yztable_net();
    }
    //已上门患者
    /* if($("#doorstatus").val() == 1){
    	noEdit();
    } */
    noEdit();
    submit(); //submit()校验方法
    $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
});

/**
 * 根据html元素的 class标签，初始化字典下拉框
 * 请求方式为 同步
 */
function initDictSelectByClassIsClient(deptId,operFlag) {
    if ($(".dict").length > 0) {
        for (var i = 0; i < $(".dict").length; i++) {
            var dict = $(".dict").eq(i);
            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
            var type = dict.attr("source");
            var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
            $.axse(url, null,
            function(data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        if(deptId=="23fe2c29-cac3-43ab-b4ef-ef70c4ab85c5"){
//        本页面专用                 	 客户部登录判断
                        	if(optionStr.seqId=="lpyjs106"){
                        		dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                        	}else{
                        		dict.append("<option value='" + optionStr.seqId + "'>" + "****" + "</option>");
                        	}                       	                        	
                        }else{
                        	dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                        }
                        
                    }
                }
                if ('triggerChange' == operFlag) { // 触发Onchange事件 #### add by yangsen 
                    $(dict).trigger('change'); // 和 连锁代码片段的 $("#organization").change 配合使用
                }
            },
            function() {
                layer.alert('查询出错！'  );
            });
        }
    }
}
/**
 * 查询字典子分类列表【如患者来源子分类】 搜索select框（+刷新） 
 * @param parentSelectId
 * @param selectId
 * @param isAdd  如果isAdd有值，说明是新增页面； 目前后台的数据字典有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
 */
function getSubDictSelectSearchfilter(deptId,parentSelectId, selectId, isAdd) {
    var parentSeqId = $("#" + parentSelectId).val();
    var dict = $("#" + selectId);
    if (parentSeqId == null || parentSeqId == '') { // 如果切换门诊，导致重新初始化一级目录
        dict.empty();
        $('#'+selectId).selectpicker('refresh');
        dict.append("<option value=''>请选择</option>");
        return false; // 终止执行
    }
    var url = contextPath + "/YZDictAct/getSubListByParentSeqId.act?parentSeqId=" + parentSeqId;
//     if (isAdd) {
//         url += '&isAdd=1';
//     }
    var organization = $("#organization").val(); // ### 容错处理
    if (organization) {
        url += '&organization=' + organization;
    }
    dict.empty();
    $('#'+selectId).selectpicker('refresh');
    dict.append("<option value=''>请选择</option>");
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            var list = data.list;
            if (list != null && list.length > 0) {
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    if(deptId=="23fe2c29-cac3-43ab-b4ef-ef70c4ab85c5"){
	                      if(optionStr.parentCode=="lpyjs106"){
// 	         本页面专用           	客户部登录判断
								if(optionStr.dictCode!=lastnexttype&&optionStr.useflag==1){
									 dict.append("<option value='" + optionStr.seqId + "' disabled>" + optionStr.dictName + "</option>");
								}else{
									 dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
								}
	                    	 
	                      }else{
	                    	  if(optionStr.dictCode!=lastnexttype&&optionStr.useflag==1){
	                    		  dict.append("<option value='" + optionStr.seqId + "' disabled>" + "****" + "</option>");
	                    	  }else{
	                    		  dict.append("<option value='" + optionStr.seqId + "'>" + "****" + "</option>");
	                    	  }
	                      }
                    }else{
                    	if(optionStr.dictCode!=lastnexttype&&optionStr.useflag==1){
                    		 dict.append("<option value='" + optionStr.seqId + "' disabled>" + optionStr.dictName + "</option>");
                    	}else{
                    		 dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                    	}
                    	
                    }                   
                }
                $('#'+selectId).selectpicker("refresh");//患者来源子分类下拉框添加搜索功能 
            }
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
function noEdit(){
	if(canEditPhone != 1){
		if($("#phonenumber2").val()!=""){
			$("#phonenumber2").attr("readonly","readonly");
		}
		if($("#phonenumber1").val()!=""){
			$("#phonenumber1").attr("readonly","readonly");
		}
	}
	if(canEditHzly != 1){
		$("#devchannel option").each(function(){
			if($(this).val() != $("#devchannel").val()){
				$(this).remove();
			}
		});
		$("#nexttype option").each(function(){
			if($(this).val() != $("#nexttype").val()){
				$(this).remove();
			}
		});
	}
}
function yzyy(){
	   //验证
	   var ordertime = $("#ordertime").val();
	   if (ordertime != '') {
	       // 预约时间对象
	       var ordertimeObj = new Date(ordertime + ':00'); // 2017-05-04 17:10
	
	       if (isyz && intoPageDateTime.getTime() >= ordertimeObj.getTime()) {
	           layer.alert('预约日期不得早于操作时间'  );
	           return false;
	       }
	       return true;
	   }else{
		   return true;
	   }
	}
//查询网电预约
function getnetorder(){
	var detailurl = contextPath+'/KQDS_Net_OrderAct/selectFirstByUsercode.act?usercode='+usercode;
	$.axse(detailurl,null, 
	           function(data){
					if(data.wdinfo){
						var dvalue = data.wdinfo;
						$("#wdseqId").val(dvalue.seqId);
						$("#accepttype").val(dvalue.accepttype);
						$("#accepttool").val(dvalue.accepttool);
						$("#askitem").val(dvalue.askitem);
						$("#askcontent").val(dvalue.askcontent);
						$("#ordertime").val(dvalue.ordertime);
						if(dvalue.ordertime!=''){
							isyz = false;
						}
					}
	           },
	           function(){
	         	  layer.alert('查询出错！' );
	       	  }
	);
}
function submitu(){
	/* if(!submit()){
		return false;	
	} */

	if(!yzyy()){
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
	}else if(age>0 && age<=10){
		//console.log("------年龄年龄");
		layer.alert('提示：需填写患者真实年龄，您填写的患者年龄小于10！' );
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
		layer.alert('所在城市必须选择！' );
		return false;
	}
	var area=$("#area").val();
	if(!area){
		layer.alert('患者所在城市区域必须选择！' );
		return false;
	}
	var town=$("#town").val();
	if(!town){
		layer.alert('患者所在城市街道必须选择！' );
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
    var url = '<%=contextPath%>/KQDS_UserDocumentAct/insert.act?'+param;
	 $.axse(url,null,
              function(r){
			        if(r.retState=="0"){
			        	layer.open({
			        		content: '修改成功',
			        		closeBtn: 0, 
			        		end: function () {
			        			parent.querySC();
			        			parent.layer.close(frameindex); //再执行关闭
							}
						});
			        }else{
			        	layer.alert(r.retMsrg );
			        }     
              },
              function(){
            	  layer.alert(r.retMsrg );
          	  }
        );
}
</script>
<style type="text/css">
/* 这里写样式是因为出生年月，后面增加了日期图标，如果不写下面的样式，出生年月会占两行 */
button{
    font: normal;
    line-height:normal;
}
</style>
</html>
