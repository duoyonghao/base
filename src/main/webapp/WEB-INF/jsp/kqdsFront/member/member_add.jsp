<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String type = request.getParameter("type");
	if(type == null || "".equals(type)){
		type="0";
	}
	
	String usercode = request.getParameter("usercode");
	String username = request.getParameter("username");
	//获取是否开启免密支付
	String HYK_NO_PASSWORD = SysParaUtil.getSysValueByName(request, SysParaUtil.HYK_NO_PASSWORD);
	if (HYK_NO_PASSWORD == null) {
		HYK_NO_PASSWORD = "1"; //1需要密码  0 免密 
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会员卡办理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/member/member.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
</head>
<style>
input[type=password]{
    padding: 0 10px;
    width: 160px;
    height: 28px;
    line-height: 28px;
    border: solid 1px #e5e5e5;
    border-radius: 3px;
    -webkit-transition: all .3s;
    transition: all .3s;
}
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 160px;   
	      }  
	.searchSelect>.btn { 
		    width: 160px; 
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
</style>
<body>	<!--  -->
	<div class="memberAddContainer"> <!-- .memberAddContainer 会员卡办理页面容器 -->
		<form id="defaultForm">
			<input type="hidden" id="memberstatus" name="memberstatus" value="1">
			<table  class="tableLayout" id="kqds_table">	<!-- .tableLayout 布局样式  -->
				<tr>
					<td> <!-- .commonText 信息文本的样式 -->
						<span class="commonText">选择患者：</span>
					</td>
					<td>	<!-- .whiteInp鼠标移入有小手效果 -->
						<input class="whiteInp" type="text" id="usercodeDesc" name="usercodeDesc" placeholder="单击此处选择患者" onclick="getuser()"/> 
					</td>
					<td>
						<span class="commonText">编<span style="visibility:hidden;">占位</span>号：</span>
					</td>
					<td>
						<input type="text" name="usercode" id="usercode" placeholder="请选择患者" title="请选择患者"  value="" readonly/> 
					</td>
				</tr>
				
				<tr>
					<td>	<!-- .commonText 信息文本的样式 -->
						<span class="commonText">接诊咨询：</span>
					</td>
					<td>
						<select id="askperson" name="askperson" class="searchSelect" data-live-search="true" title="请选择"></select>
<!-- 						<select  name="askperson" id="askperson" ></select> -->
					</td>
					<td>	<!-- .commonText 信息文本的样式 -->
						<span class="commonText">挂号分类：</span>
					</td>
					<td>
						<select class="dict" tig="ghfl" name="regsort" id="regsort"></select>
					</td>
				</tr>
				<tr>
					<td>
						<span class="commonText">IC卡号：</span>
					</td>
					<td>
						<input type="text" id="icno" name="icno"> 
					</td>
					<td>
						<span class="commonText">备<span style="visibility:hidden;">占位</span>注：</span>
					</td>
					<td>
						<input type="text" id="remark" name="remark" > 
					</td>
					<input type="hidden" id="memberno" name="memberno" > 
				</tr>
				<%if(HYK_NO_PASSWORD.equals("1")){ %>
				<tr>	
					<td>
						<span class="commonText">密<span style="visibility:hidden;">占位</span>码：</span>
					</td>
					<td>
						<input type="text" id="password" name="password" > 
					</td>
					<td>
						<span class="commonText">确认密码：</span>
					</td>
					<td>
						<input type="text" id="password2" name="password2" > 
					</td>
				</tr>
				<%} %>
				<tr>		
					<td>
						<span class="commonText">会员类型：</span>
					</td>
					<td>
						<select class="patients-source select2 dict" tig="HYKFL" name="memberlevel" id="memberlevel">
						</select> 
					</td>
					<td>
						<span class="commonText">折扣：</span>
					</td>
					<td>
						<input type="text"  id="discount" name="discount" value="100" readonly="readonly">
					</td>
				</tr>
				<tr>		
					<td>
						<span class="commonText">折扣截止日期：</span>
					</td>
					<td>
						<input type="text"  id="discountdate" name="discountdate" class="unitsDate"/>
					</td>
				</tr>
				<tr>	
					<td>
						<span class="commonText">充值金额：</span>
					</td>
					<td>
						<input type="text" id="xiaoji" name="xiaoji" value="0">
					</td>
					<td>
						<span class="commonText">赠送金额：</span>
					</td>
					<td>
						<input type="text" id="givemoney" name="givemoney" value="0"> 
					</td>
				</tr>
				
				<tr>
					<td>
						<span class="commonText">充值方式：</span>
					</td>
					<td colspan="3">	<!-- shortSel 付款方式select的样式 -->
						<select class="shortSel" name="paytype" id="paytype1" ></select>	
						<!-- longInp 付款金额input的样式 -->
						<input class="longInp" type="text" placeholder="" name="money" id="money1"  value="0">
						<button type="button" class="btn btn-default btn-sm" id="addtype" onclick="addrow()">
					         <span class="glyphicon glyphicon-plus"></span>
					    </button>	
					</td>
				</tr>
			</table>
			<input type="hidden" name="returnmoney" id="returnmoney" value="0">
			<input type="hidden" name="returntype" id="returntype" value="0">
		</form>
	</div> <!-- .fixedBottomDiv 固定底部的三个按钮 -->
	<footer class="fixedBottomDiv">
		<div class="clear2"></div>
       	<a class="kqdsCommonBtn" id="close">取 消</a>
       	<a class="kqdsCommonBtn" id="clean">清 空</a>
       	<a class="kqdsSearchBtn" style="background:#0E7CC9;color:#fff;" id="submit">确 定</a>
	</footer>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/kqds_member_binding.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/kqds_member_add.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">

var usercode = '<%=usercode%>';
var username = '<%=username%>';
var contextPath = '<%=contextPath%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var usercodechild,usernamechild;

$(function(){
	initMemberFkfsSelect("paytype1");//充值付款方式
	 //时间选择
    $(".unitsDate").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
	//咨询 下拉列表
	initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");
	initDictSelectByClass(); // 会员类型
	if(usercode != "" && usercode != null && usercode != "null" && usercode != "undefined"){
		$("#usercode").val(usercode);
		$("#usercodeDesc").val(username);
		//获取患者当天最新的 挂号咨询 挂号分类（如果当天没有挂号，提示先挂号）
    	getRegxx(usercode);
    	var canKaika = Yzkaika($("#usercode").val());
    	if(!canKaika){
    		layer.alert('该患者存在会员卡未绑定IC卡，请先绑定'  );
    		return false;
    	}
	}
	$('.searchSelect').selectpicker("refresh");//咨询搜索初始化刷新2019.10.19--licc
});
$('#memberlevel').change(function() {
	var data = getDictByDictSeqId($(this).val())
	if(data){ // 如果一级有值，再请求
		$("#discount").val(data.remark);
	}
});
$('#clean').on('click', function(){
	$("#defaultForm")[0].reset();
//  清空搜索下拉框
	$(".searchSelect li.selected").removeClass("selected");
  	$(".searchSelect button .pull-left").text("请选择");
});
$('#submit').on('click',
function() {
    $("#memberno").val($("#icno").val());
    var flag = submit();
    if (!flag) {
        return false;
    }
    /*是否有付款方式 没有选择的 如果有就提示  提交失败*/
	if($("select.shortSel").val()=="" || $(".shortSel").val()==null){
		layer.alert('请选择付款方式', {
			  
		});
		return false;
	}
    var msg = '接诊咨询：' + $("#askperson").find("option:selected").text() + '<br/>确定填写无误并开卡？';
    layer.confirm(msg, {
        btn: ['确认', '放弃']
    },
    function() {
        var param = $('#defaultForm').serialize();
        var returnmoney = $("#returnmoney").val();
        var returntype = $("#returntype").val();
        var url = contextPath + '/KQDS_MemberAct/insertOrUpdate.act?' + param + '&moneys=' + returnmoney + '&types=' + returntype;
        $.axse(url, null,
        function(r) {
            var retdata = r.retData;
            if (r.retState == "0") {
                layer.alert('办理成功', {
                    
                    end: function() {
                        layer.confirm('是否打印会员卡充值缴费单？', {
                            btn: ['确认', '放弃']
                        },
                        function() {
                            var params = param.split('&');
                            var memberno, usercode, username, money, givemoney = "0";
                            for (var i = 0; i < params.length; i++) {
                                // 循环获取对应参数
                                if (params[i].indexOf("memberno") > -1) {
                                    memberno = params[i].split('=')[1];
                                }
                                if (params[i].indexOf("usercodeDesc") > -1) {
                                    username = decodeURI(params[i].split('=')[1]);
                                }
                                if (params[i].indexOf("money") > -1) {
                                    money = params[i].split('=')[1];
                                }
                                if (params[i].indexOf("givemoney") > -1) {
                                    givemoney = params[i].split('=')[1];
                                }
                                if (params[i].indexOf("returntype") > -1) {
                                    returntype = params[i].split('=')[1];
                                }
                                if (params[i].indexOf("returnmoney") > -1) {
                                    returnmoney = params[i].split('=')[1];
                                }
                            }
                            money = Number(retdata.qtmoney) + Number(retdata.yhkmoney) + Number(retdata.xjmoney) + Number(retdata.zfbmoney) + Number(retdata.wxmoney) + Number(retdata.mmdmoney) + Number(retdata.bdfqmoney);
                            var ctotalmoney = money + Number(givemoney);
                            // 先查询fyqrd页面在打印设置里
                            // 是a4还是a5，如果是a4则跳转a4界面
                            var printSet = getPrintType("会员卡充值单");
                            // ## 第一次打开的时候，本次充值金额 和
                            // 充值余额相等，其他类推 ##
                            hyurl = contextPath + '/KQDS_Print_SetAct/toHyczPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&cztime=' + retdata.creatTime + '&memberno=' + memberno + '&username=' + username + '&money=' + Number(money).toFixed(2) + '&givemoney=' + Number(givemoney).toFixed(2) + '&totalmoney=' + Number(ctotalmoney).toFixed(2) + '&ymoney=' + Number(money).toFixed(2) + '&usercode=' + $("#usercode").val() + '&recordId=' + retdata.recordId + '&ygivemoney=' + Number(givemoney).toFixed(2) + '&ytotalmoney=' + Number(ctotalmoney).toFixed(2) + '&cost_organization=<%=ChainUtil.getCurrentOrganization(request) %>';
                            // 弹出打印页面
                            parent.layer.open({
                                type: 2,
                                title: '打印',
                                shadeClose: true,
                                // 点击遮罩关闭层
                                area: ['1080px', '550px'],
                                content: hyurl
                            });
                            if (parent.query) {
                                parent.query();
                            }
                            parent.layer.close(frameindex); //再执行关闭 */
                        },
                        function() {
                            if (parent.query) {
                                parent.query();
                            }
                            parent.layer.close(frameindex); //再执行关闭 */
                        });
                    }
                });
            } else {
                layer.alert(r.retMsrg, {
                    
                });
            }
        },
        function() {
            layer.alert(r.retMsrg, {
            });
        });
    },
    function() {});
});
$('#close').on('click', function(){
	parent.layer.close(frameindex); //执行关闭
});


function submit(){
	if($("#usercode").val() == "" || $("#usercode").val() == null){
		layer.alert('请选择患者', {
			  
		});
		return false;
	}
	var canKaika = Yzkaika($("#usercode").val());
	if(!canKaika){
		layer.alert('该患者存在会员卡未绑定IC卡，请先绑定' );
		return false;
	}
	if($("#askperson").val() == "" || $("#askperson").val() == null){
		layer.alert('请选择接诊咨询', {
			  
		});
		return false;
	}
	if($("#regsort").val() == "" || $("#regsort").val() == null){
		layer.alert('请选择挂号分类', {
			  
		});
		return false;
	}
	if($("#memberno").val() == "" || $("#memberno").val() == null){
		layer.alert('请输入会员卡号', {
			  
		});
		return false;
	}else if($("#memberno").val().length < 6){ // 页面校验是最少6位
		layer.alert('您输入的会员卡号长度不够', {
			  
		});
		return false;
	}
	if (judgeSign($("#discount").val()) == true) {  // 验证数量是否为正数
        if(Number($("#discount").val())>100){
        	 layer.alert('折扣不能大于100' );
             return false;
        }
    } else {
        layer.alert('折扣必须为正数' );
        return false;
    }
	if($("#icno").val() == "" || $("#icno").val() == null){
		layer.alert('请绑定IC卡', {
			  
		});
		return false;
	}
	if(<%=HYK_NO_PASSWORD%> == '1'){
		if($("#password").val() == "" || $("#password").val() == null){
			layer.alert('请输入密码', {
				  
			});
			return false;
		}
		if($("#password2").val() == "" || $("#password2").val() == null){
			layer.alert('请确认密码', {
				  
			});
			return false;
		}
		if($("#password2").val() != $("#password").val()){
			layer.alert('两次密码不一致', {
				  
			});
			return false;
		}
	}
	var testz = /^\d+(\.\d+)?$/;//金额格式正则
	
	var xiaoji = $("#xiaoji").val();//开卡充值金额
	if(xiaoji == "" || xiaoji == null){
		layer.alert('请填写充值金额' );
		return false;
	}else if(!testz.test(xiaoji)){
		layer.alert('充值金额格式不正确' );
		return false;
	}
	
	var xiaojimoney = 0;//计算总金额
	
	if($("#money1").val() != "" || $("#money1").val() != null){
		if(!testz.test($("#money1").val())){
			layer.alert('充值方式输入有误', {
				  
			});
			return false;
		}
	}
	if($("#givemoney").val() != "" || $("#givemoney").val() != null){
		var testz = /^\d+(\.\d+)?$/;
		if(!testz.test($("#givemoney").val())){
			layer.alert('赠送余额输入有误', {
				  
			});
			return false;
		}
	}
	if(!checkmemberno()){
		layer.alert('会员卡已存在,请重新输入', {
			  
		});
		return false;
	}
	
	var typestrs = "";//支付类型 汉字集合
	var moneys = "";//金额数组
	
	var money = $("#money1").val();
	
	if(money == ""){
		layer.alert('请填写金额'  );
		return false;
	}else if(!testz.test(money)){
		layer.alert('金额格式不正确'  );
		return false;
	}
	
	xiaojimoney = Number(xiaojimoney) + Number(money);
	
	var typestr = $("#paytype1").val();
	
	typestrs = typestrs + typestr + ",";//拼接类型数组
	moneys = moneys + money + ",";
	
	var FKFSlength = $("input[name='money']").length;
	if(FKFSlength > 1){
		
		var idnums2 = "";
		$("input[name='money']").each(function (){
			var idstr = $(this).attr("id");
			if(idstr == "money1"){
				return;
			}
			var idnum = Number(idstr.substring(5,idstr.length));
			idnums2 = idnums2 + idnum + ",";
		});
		
		//多种付款方式时循环获取
		var idnums22 = idnums2.split(",");
		for(var i = 0; i<idnums22.length; i++){
			
			if(idnums22[i] == ""){
				continue;
			}
			
			var typei = $("#paytype" + idnums22[i]).val();
			var moneyi = $("#money" + idnums22[i]).val();
			var typestri = $("#paytype" + idnums22[i]).val();
			
			var reg = new RegExp("^.*"+typestri+".*$");
			if(reg.test(typestrs)){
				layer.alert('请选择不同的付款方式' );
	 			return false;
			}
			
			if(moneyi == ""){
				layer.alert('请填写金额'  );
	 			return false;
			}else if(!testz.test(moneyi)){
	    		layer.alert('金额格式不正确'  );
	    		return false;
			}
			
			typestrs = typestrs + typestri + ",";
			moneys = moneys + moneyi + ",";
			xiaojimoney = xiaojimoney + Number(moneyi);//小计
		}
	}
	
	if(xiaojimoney != Number(xiaoji)){
		layer.alert("您填写的开卡-充值金额与充值方式总和不一致,请核实!"  );
		return false;
	}
	
	//禁用保存按钮 以防网络卡顿等原因导致的可点击多次 后台就会保存多个会员卡信息
	$("#tijiao").attr("disabled",true);
	
	$("#returnmoney").val(moneys);
	$("#returntype").val(typestrs);
	
	return true;
}

//选择患者列表
function getuser(){
	//选择患者
    parent.layer.open({
        type: 2,
        title: '选择患者',
        shadeClose: true,
        shade: 0.6,
        area: ['600px', '70%'],
        content: contextPath + '/KQDS_UserDocumentAct/toUserList.act?typechoose=1',
        end: function(index, layero){
        	$("#usercode").val(parent.usercodechild);
        	$("#usercodeDesc").val(parent.usernamechild);
        	//获取患者当天最新的 挂号咨询 挂号分类（如果当天没有挂号，提示先挂号）
        	getRegxx(parent.usercodechild);
        	var canKaika = Yzkaika($("#usercode").val());
        	if(!canKaika){
        		layer.alert('该患者存在会员卡未绑定IC卡，请先绑定', {
                      
                });
        	}
        },
    });
}
</script>
</html>
