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

	YZPerson person = SessionUtil.getLoginPerson(request);
	String usercode = request.getParameter("usercode");
	if (usercode == null) {
		usercode = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>信息报备</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/hzjd.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style>
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	    width: 200px;   
	      }  
	.searchSelect>.btn { 
		width: 200px; 
		height:28px; 
		border: solid 1px #e5e5e5; 
		}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
	    color: #999;
	    height: 28px;
		}
	.pull-left {
	    float: left !important;
	    color: #666;
		}
	.btn-group > .btn:first-child:hover {
	    background: white;
	 }
	.bootstrap-select.btn-group .dropdown-toggle .filter-option {
	    margin-top: -3px;
	}
	.btn-group > .btn:first-child:hover {
	    background: white;
	}
	.searchWrap .text {
	    position: static !important; 
	    left: 0px;
	    top: 9px;
	    color: rgb(31, 32, 33);
	}

</style>
</head>
<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					
					<tr class="addVisitingDialog-content">	<!--addVisitingDialog 本身无样式 绑定了星星的点击事件  -->
						<td>	<!--impText必填项样式  -->
							<span class="impText">病历号*</span>
						</td>
						<td>
							<input type="hidden" name="seqId" id="seqId">
			            	<input type="hidden" name="type" id="type">
			                <input type="text" name="usercode" id="usercode" readonly="readonly">
			                <input type="hidden" name="important" id="important">
			                <input type="hidden" name="createuser" id="createuser">
					 	</td>
					 	<td colspan="2" class="starsBox">	<!--starsBox 本身无样式 绑定了星星的点击事件  只有在该类下的星星被点击才有用-->
					 		<span class="hc-icon icon20 stars-icon" value="1"></span>
					 		<span class="hc-icon icon20 stars-icon" value="2"></span>
					 		<span class="hc-icon icon20 stars-icon" value="3"></span>
					 		<span class="hc-icon icon20 stars-icon" value="4"></span>
					 		<span class="hc-icon icon20 stars-icon" value="5"></span>
					 	</td>
					</tr>
					
					<tr>
						<td> 	<!--impText必填项样式  -->
							<span class="impText">姓 名*</span>
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
							<div class="form-group sexReg"> 	<!--form-group与表单验证有关系  sexReg性别的 提示符号 与提示文本 位置调整   -->
								<!--id=maleId id=femaleId 均为了配合label标签点击操作 没特殊用途    -->
								<input id="maleId" type="radio" name="sex" value="男"><label for="maleId" class="sexValue">男</label>
								<input id="femaleId" type="radio" name="sex" value="女"><label for="femaleId" class="sexValue">女</label>
							</div>
						</td>
					</tr>
					
					<tr>
						<td>	<!--impText必填项样式  -->
							<span class="impText">电话1*</span>
						</td>
						<td>
							<div class="form-group">
								<select class="sel_short_inp_long">	<!--.sel_short_inp_long select与input组合时的样式  -->
									<option>本人</option>
									<option>家人</option>
								</select>
								<input class="sel_short_inp_long" type="text" id="phonenumber1" name="phonenumber1" maxlength="12" /> 
							</div>	
						</td>
						<td>	<!--.comText 选填项样式-->
							<span class="comText">电话2</span>
						</td>
						<td>
							<div class="form-group">
								<select class="sel_short_inp_long">	<!--.sel_short_inp_long select与input组合时的样式  -->
									<option>本人</option>
									<option>家人</option>
								</select>
								<input class="sel_short_inp_long" type="text" id="phonenumber2" name="phonenumber2" maxlength="12" /> 
							</div>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="comText">出生年月</span>
						</td>
						<td style="position:relative;">
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
<!-- 								<select class="select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype','add');"></select> -->
								<select class="select2 dict searchSelect" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelectSearch('devchannel','nexttype','add');" data-live-search="true" title="请选择"></select>
							</div>
						</td>
						<td>
							<span class="impText">子分类*</span>
						</td>
						<td>
							<div class="form-group">
<!-- 								<select class="select2 dict" name="nexttype" id="nexttype"> -->
<!-- 									<option value="">请选择</option> -->
<!-- 								</select> -->
								<select class="select2 dict searchSelect" name="nexttype" id="nexttype" data-live-search="true" title="请选择">
								</select>								
							</div>
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="comText">职业</span>
						</td>
						<td><!-- .select2 dict本身无样式 与功能有关 -->
<!-- 							<select class="select2 dict" tig="job" name="profession" id="profession"></select> -->
							<select class="select2 dict searchSelect" tig="job" name="profession" id="profession" data-live-search="true" title="请选择"></select>
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
					<!-- <tr>
						<td>
							<span class="comText">过敏史</span>
						</td>
						<td colspan="3">
							<input class="sel_address" type="text" placeholder="过敏史" name="drugllergy" id="drugllergy">
						</td>
					</tr>
					<tr>
						<td>
							<span class="comText">既往史</span>
						</td>
						<td colspan="3">
							<input class="sel_address" type="text" placeholder="既往史" name="medicalhistory" id="medicalhistory">
						</td>
					</tr> -->
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
						</td>
						<td>
							<span class="comText">介绍人</span>
						</td>
						<td>	<!-- .whiteInp  input白底鼠标移入的效果-->
							<input type="hidden" name="introducer" id="introducer" value="" /> 
							<input type="text" class="whiteInp" id="introducerDesc" name="introducerDesc"  onclick="getuser()" placeholder="介绍人">
						</td>
					</tr>
					
					<tr>
						<td>
							<span class="comText">开发人</span>
						</td>
						<td>	<!-- .whiteInp  input白底鼠标移入的效果-->
							<input type="hidden" name="developer" id="developer"  class="form-control" />
					 		<input type="text" class="whiteInp" id="developerDesc" name="developerDesc"  placeholder="开发人" onClick="javascript:xxbbValidate('<%=person.getUserPriv()%>','<%=person.getSeqId()%>');">	
						</td>
						<td>
							<span class="comText">建档导医</span>
						</td>
						<td>	<!-- .whiteInp  input白底鼠标移入的效果-->
							<input type="hidden" name="guider" id="guider"  />
							<input type="text" class="whiteInp" id="guiderDesc" name="guiderDesc" placeholder="建档导医" onClick="javascript:single_select_user(['guider', 'guiderDesc'],'single');" >
						</td>
					</tr>
					
					<!-- <tr>
						<td>
							<span class="comText">车牌号</span>
						</td>
						<td>	
							<input type="text" name="platenumber" id="platenumber" placeholder="车牌号">	
						</td>
						
					</tr> -->
					
					<tr class="textareaTr3" style="height:96px;"><!-- textareaTr3 调整该行的行高   -->
						<td>
							<span class="comText">患者印象</span>
						</td>
						<td colspan="3">
							<div class="form-group">
								<textarea name="userimpress" id="userimpress" rows="3" placeholder=""></textarea>
							</div>
						</td>
					</tr>
					
					<tr class="textareaTr1" style="height:52px;"><!-- textareaTr1 调整该行的行高   -->
						<td>
							<span class="comText">第一句话</span>
						</td>
						<td colspan="3">
							<textarea name="firstword" id="firstword" rows="1" placeholder=""></textarea>
						</td>
					</tr>
					
					<tr class="textareaTr1" style="height:58px;"><!-- textareaTr1 调整该行的行高   -->
						<td>
							<span class="comText">备注</span>
						</td>
						<td colspan="3">
							<textarea name="remark" id="remark" rows="1" placeholder=""></textarea>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ui.datepicker-zh-CN.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/hzjd/hzjd.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var usercode = '<%=usercode%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
// 信息报备权限，即是否具备修改开发人员的权限  0可以 1不可以
var canEditKf = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag7_canEditKf, request)%>';
$(function() {
    showLocation(); //省市级联
    //默认选中湖南省
    $("#province").val("1226").trigger("change");
    
    initDictSelectByClass(); // 患者来源，职业
    if (usercode) {
        //加载患者数据
        getDetail();
    } else {
        getUserCode(); //获取病人编号
    }

    //弹窗内星级评分
    jsJb();
    //验证表单
    Yztable_xxbb();
     $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-11.13 licc
});
function submitu() {
    if (!submit()) {
        return false;
    }
    var phonenumber1 = $("#phonenumber1").val();
	var phonenumber2 = $("#phonenumber2").val();
	if(phonenumber1 == phonenumber2){
		layer.alert('手机号码1和手机号码2不能填写相同的值！' );
		return false;
	}
    var param = $('#defaultForm').serialize();
    var url = '<%=contextPath%>/KQDS_UserDocumentAct/insert.act?' + param;
    $.axse(url, null,
    function(r) {
        if (r.retState == "0") {
            layer.alert('报备成功', {
                  
                end: function() {
                    try {
                        parent.searchHzda();
                        parent.layer.close(frameindex); //再执行关闭
                    } catch(e) {
                        parent.layer.close(frameindex); //再执行关闭
                    }
                }
            });
        } else if (r.retState == "100") { // 患者编号重复
            getUserCode(); //获取病人编号
            layer.alert('患者编号已存在，系统已自动获取新的病历号，请再次进行提交操作。'  );

        } else {
            layer.alert('报备失败'  );
        }
    },
    function() {
        layer.alert('报备失败' );
    });
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
