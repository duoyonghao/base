<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String openid = request.getParameter("openid");
	if(openid == null ){
		openid = "";  
	}
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>患者建档-微信客服</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/hzjd.css">
</head>
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
					<span class="impText">病历号*</span>
				</td>
				<td>
					<div class="form-group">	<!-- form-group与表单验证有关 -->
						<input type="text" name="usercode" id="usercode" readonly="readonly">
	                	<input type="hidden" name="type" id="type" value="1" readonly="readonly">
	               		<input type="hidden" name="important" id="important" readonly="readonly">
	               		<input type="hidden" name="openid" id="openid" readonly="readonly">
					</div>
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
						<input type="text" name="username" id="username">
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
					<div class="form-group">	<!--.sel_short_inp_long  select元素与input组合时的样式 -->
		                <select class="sel_short_inp_long">
		                    <option>本人</option>
		                    <option>家人</option>
		                </select>				<!--.sel_short_inp_long  select元素与input组合时的样式 -->
		                <input class="sel_short_inp_long" type="text" id="phonenumber1" name="phonenumber1" maxlength="12">
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
		                <input class="sel_short_inp_long" type="text" id="phonenumber2" name="phonenumber2" maxlength="12">
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
					<span class="comText">年 龄</span>
				</td>
				<td>
					<input type="text" id="age" name="age" placeholder="" onclick="changeAge();"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="impText">患者来源*</span>
				</td>
				<td class="relative">
					<div class="form-group">
						<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');">
						</select>
					</div>
				</td>
				<td>
					<span class="impText">子分类*</span>
				</td>
				<td>
					<div class="form-group">
						<select name="nexttype" id="nexttype">
		                  <option value="">请选择</option>
						</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="comText">职业</span>
				</td>
				<td><!-- .select2 dict本身无样式 与功能有关 -->
					<select class="select2 dict" tig="job" name="profession" id="profession">
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
					<span class="impText">国家/地区*</span>
				</td>
				<td colspan="3">
					<div class="form-group provinceReg"><!-- .provinceReg 验证信息位置 调整样式 -->
						<select class="sel3" id="province" name="province"></select> 
						<select class="sel3" id="city" name="city" ></select>
						<select class="sel3" id="town" name="town"></select>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<span class="comText">联系地址</span>
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
					<input type="text" class="whiteInp" id="introducerDesc" name="introducerDesc" onclick="getuser()" placeholder="介绍人">
				</td>
			</tr>
			
			<tr>
				<td>
					<span id="kfrlable" class="comText">开发人</span>
				</td>
				<td>
					<input type="hidden" name="developer" id="developer"  class="form-control" />
			 		<input type="text" class="whiteInp" id="developerDesc" name="developerDesc"  placeholder="开发人" onClick="javascript:xxbbValidate();">	
				</td>
				
			</tr>
			
			<tr class="textareaTr1"><!-- textareaTr1 调整该行的行高   -->
				<td>
					<span class="comText">商务通身份证</span>
				</td>
				<td colspan="3">
					<textarea class="passportNum" name="xueli" id="xueli" rows="1"  placeholder=""></textarea>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" class="relative">		<!--分隔线  -->
					<hr class="line">
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
					<input type="text" id="ordertime" name="ordertime" value=""  >
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
		</table>
	</form>
	
	<div class="fixedBottomDiv" style="height: 70px; z-index:100;"><!--底部三个按钮所在父元素 固定在底部样式  -->
		<div class="clear2"></div>
       	<a style="margin-top:10px;" class="kqdsCommonBtn" id="submit">确 定</a>
	</div>
	<div>
	
	</div>
	
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/hzjd/hzjd.js"></script>

<script type="text/javascript">
var intoPageDateTime = new Date();
var wxOrderPhonenumber = null;
var usercodechild = ""; //接收layer子窗口传值
var usernamechild = ""; //接收layer子窗口传值
var openid = $.getUrlVar('openid');
//信息报备权限，即是否具备修改开发人员的权限  0可以 1不可以
var canEditKf = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag7_canEditKf, request)%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	if(!openid){
		layer.alert('参数错误：openid不存在' );
        return false;
	}else{
		$("#openid").val(openid);
	}
	
    /** ########################################################### 连锁相关  **/
    initHosSelectListNoCheck('organization'); // 连锁门诊下拉框
    if ($("#organization").val()) {
        getUserCode($("#organization").val()); //获取病人编号
    }
    $("#organization").change(function() {
        var currSelect = $(this).val();
        if (currSelect) {
            getUserCode(currSelect); //获取病人编号
        } else {
            $("#usercode").val('');
        }
    });
    $("#ordertime").datetimepicker({
        language: 'zh-CN',
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });
    /** ########################################################### 连锁相关  end **/

    showLocation(); //省市级联
    //默认选中河南省
    $("#province").val("1226").trigger("change");
    initDictSelectByClass(); // 受理类型、受理工具、咨询项目、患者来源、职业
    //弹窗内星级评分
    jsJb();
    if(canEditKf != 1){
    	$("#developerDesc").attr('readonly','readonly').attr('onclick','').click( eval(function(){
    		alert('无设定权限！');
    	})); 
    }
    Yztable_net();

    //获取微信注册患者信息
    getWxDetail();
    submit();
});
function getWxDetail() {
    $('#devchannel option:contains(网络)').each(function() {
        if ($(this).text() == '网络') {
            $(this).attr('selected', true);
        }
    });
    $('#devchannel').trigger("change");
    $('#nexttype option:contains(微信)').each(function() {
        if ($(this).text() == '微信') {
            $(this).attr('selected', true);
        }
    });
    $('#accepttool option:contains(公众微信号)').each(function() {
        if ($(this).text() == '公众微信号') {
            $(this).attr('selected', true);
        }
    });
    $('#accepttype option:contains(网络咨询)').each(function() {
        if ($(this).text() == '网络咨询') {
            $(this).attr('selected', true);
        }
    });

}
$('#submit').on('click',
function() {
    var flag = submit(); //submit()校验方法
    if (!flag) {
        return false;
    }
    if ($("#ordertime").val() != "") {
        var ordertimeObj = new Date($("#ordertime").val() + ':00'); // 2017-05-04 17:10
        if (intoPageDateTime.getTime() >= ordertimeObj.getTime()) {
            layer.alert('预约日期不得早于操作时间'  );
            return false;
        }
    }
    var usercode_param = $("#usercode").val();
    var phonenumber1 = $("#phonenumber1").val();
	var phonenumber2 = $("#phonenumber2").val();
	if(phonenumber1 == phonenumber2){
		layer.alert('手机号码1和手机号码2不能填写相同的值！' );
		return false;
	}
    var param = $('#defaultForm').serialize();
    param = param + '&openid=' + openid;
    var url = contextPath + '/WXUserDocAct/insert4kefu.act?' + param;
    $.axse(url, null,
    function(r) {
        if (r.retState == "0") {
            layer.alert('创建成功', {
                  
                end: function() {
                	parent.createDocEvent(usercode_param); // 建档事件
                    var frameindex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameindex); //再执行关闭
                    
                }
            });
            return true;
        } else if (r.retState == "100") { // 患者编号重复
            getUserCode(); //获取病人编号
            layer.alert('患者编号已存在，系统已自动获取新的病历号，请再次进行提交操作。'  );

        } else {
        	layer.alert('建档失败：' + r.retMsrg, {
            return false;
        }
    },
    function() {
        layer.alert('建档失败' );
        return false;
    });
});
$('#close').on('click',
function() {
    parent.layer.close(frameindex); //执行关闭
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
