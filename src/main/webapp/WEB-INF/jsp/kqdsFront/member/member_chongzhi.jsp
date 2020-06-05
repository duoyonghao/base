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
	String seqId = request.getParameter("seqId");
	String usercode = request.getParameter("usercode");
	String memberno = request.getParameter("memberno");
	String money = request.getParameter("money");
	String zmoney = request.getParameter("zmoney");
	
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>会员卡充值</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/member/member.css"/>
	<!-- select搜索筛选 -->
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
	
<style type="text/css">
		
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 80px;   
	      }  
	.searchSelect>.btn { 
		    width: 80px; 
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
	
	.searchWrap .text {
		    position: static !important; 
		    left: 0px;
		    top: 9px;
		    color: rgb(31, 32, 33);
		}
		
	/* 	解決标签查询中下拉框悬浮 */
	.searchModule>section>ul.conditions {
     overflow: visible; 
    margin: 0;
	}
	.searchModule>section{
		height: 50px
	}
	
</style>	
</head>

<body>
	<div class="chongzhiContainer"> <!--.chongzhiContainer 充值页面容器  -->
		<form id="defaultForm">
			<input type="hidden" name="seqId" id="seqId"/>
			<table class="tableLayout" id="kqds_table"> <!--.tableLayout table布局样式  -->
				<tr>
					<td> 
						<span class="commonText">编<span style="visibility:hidden;">占位</span>号：</span>
					</td>
					<td>
						<span id="usercode"></span>
					</td>
					<td> 
						<span class="commonText">姓<span style="visibility:hidden;">占位</span>名：</span>
					</td>
					<td>
						<span id="username"></span>
					</td>
					<td> 
						<span class="commonText">卡<span style="visibility:hidden;">占位</span>号：</span>
					</td>
					<td>
						<span id="memberno"></span>
					</td>
				</tr>	
				
				<tr>
					<td> 
						<span class="commonText">接诊咨询：</span>
					</td>
					<td>
						<select id="askperson" name="askperson" class="searchSelect"  data-live-search="true"  title="请选择"></select>
<!-- 						<select  name="askperson" id="askperson" ></select> -->
					</td>
					<td> 
						<span class="commonText">挂号分类：</span>
					</td>
					<td>
						<select class="dict" tig="ghfl" name="regsort" id="regsort"></select>
					</td>
					
				</tr>	
				<tr>
					<td> 
						<span class="commonText">备<span style="visibility:hidden;">占位</span>注：</span>
					</td>
					<td colspan="5">
						<input class="beizhuInp" type="text" style="width:524px;" id="content" name="content">
					</td>
				</tr>
				<tr>
					<td> 
						<span class="commonText">赠送余额：</span>
					</td>
					<td>
						<span id="zyue"></span>
					</td>
					<td> 
						<span class="commonText">充值余额：</span>
					</td>
					<td>
						<span id="yue"></span>
					</td>
					<td> 
						<span class="commonText">余额小计：</span>
					</td>
					<td>
						<span id="totalmoney"></span>
					</td>
				</tr>
				
				<tr>
					<td>
						<span class="commonText">赠送金额：</span>
					</td>
					<td>
						<input type="text" id="givemoney" name="givemoney" value="0" >
					</td>
					<td> 
						<span class="commonText">充值金额：</span>
					</td>
					<td>
						<input type="text" id="xiaoji" name="xiaoji" value="0" >
					</td>
					<td> 
						<span class="commonText">充值方式：</span>
					</td>
					<td style=width:230px;">
						<select class="shortSel" name="paytype" id="paytype1"></select>
						<input class="longInp" type="text" placeholder="" name="money" id="money1" value="0">
						<button type="button" class="btn btn-default btn-sm" id="addtype" onclick="addrow()">
					         <span class="glyphicon glyphicon-plus"></span>
					    </button>
					</td>
				</tr>
			</table>
		</form>
		<footer class="fixedBottomDiv">
			<a id="searchHfzx" href="javascript:void(0);" class="kqdsSearchBtn" onclick="tijiao()">提交</a> 
		</footer>
	</div>
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/kqds_member_binding.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">

var seqId = '<%=seqId%>';
var usercode1 = '<%=usercode%>';
var memberno1 = '<%=memberno%>';
var money1 = '<%=money%>';
var zmoney1 = '<%=zmoney%>';
var contextPath = '<%=contextPath%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var onclickrow = window.parent.onclickrowOobj;

$(function(){
	$("#seqId").val(seqId);
	initMemberFkfsSelect("paytype1");//充值付款方式
	//咨询 下拉列表
	initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");
	initDictSelectByClass(); // 会员类型
	var tmoney = 0;
	if(usercode1 != "" && usercode1 != null && usercode1 != "null" && usercode1 != "undefined"){
		$("#usercode").html(usercode1);
		$("#username").html(getNameByusercodesTB(usercode1)[0].username);
		$("#memberno").html(memberno1);
		$("#yue").html(Number(money1));
		$("#zyue").html(Number(zmoney1));//赠送余额
		tmoney = Number(money1) + Number(zmoney1);
	}else{
		$("#usercode").html(onclickrow.usercode);
		$("#username").html(getNameByusercodesTB(onclickrow.usercode)[0].username);
		$("#memberno").html(onclickrow.memberno);
		$("#yue").html(Number(onclickrow.money));
		$("#zyue").html(Number(onclickrow.givemoney));//赠送余额
		tmoney = Number(onclickrow.money) + Number(onclickrow.givemoney);
	}
	$("#totalmoney").html(tmoney.toFixed(2));//余额小计
	getRegxx($("#usercode").html());
	var canKaika = Yzkaika($("#usercode").html());
	if(!canKaika){
		layer.alert('该患者存在会员卡未绑定IC卡，请先绑定' );
	}
	 $('.searchSelect').selectpicker("refresh");//咨询部门初始化刷新--2019-10-24 licc
});

//删除付款方式
function delrow(thisnum){
	
	$("#FKFS" + thisnum).remove();
	
	if($("input[name='money']").length == 1){
		$('#addtype').show();
	}else{
		//判断页面里是否还有加号 没有则在最后一个div里添加
		if($("input[name='deltype']").length == 0){
			var idnum=0;
			$("input[name='money']").each(function (){
				var idstr = $(this).attr("id");
				idnum = Number(idstr.substring(5,idstr.length));
			});
			$('#FKFS' + idnum).find("button[name='addtype']").show();	
		}
	}
	
}

//添加付款方式
function addrow(){
	
	var idnum=0;
	$("input[name='money']").each(function (){
		var idstr = $(this).attr("id");
		idnum = Number(idstr.substring(5,idstr.length));
	});
	if($("input[name='money']").length == 1){
		$('#addtype').hide();
		$('#deltype').show();
	}else{
		$('#addtype'+idnum).hide();
		$('#deltype'+idnum).show();
	}
	
	var htmls = '<tr id="FKFS'+(idnum+1)+'">'
		 +'		<td colspan="5">&nbsp;</td>'
		 +'		<td>'
		 +'			<select class="shortSel" name="paytype" id="paytype'+(idnum+1)+'">'
		 +'			</select>'
		 +'			<input class="longInp" type="text" placeholder="金额" id="money'+(idnum+1)+'" name="money">'
		 +'			<button type="button" class="btn btn-default btn-sm" class="addtype'+(idnum+1)+'" name="addtype" id="addtype'+(idnum+1)+'" onclick="addrow('+(idnum+1)+')">'
		 +'		         <span class="glyphicon glyphicon-plus"></span>'
		 +'		    </button>'
		 +'		    <button type="button" class="btn btn-default btn-sm" class="deltype'+(idnum+1)+'" name="deltype" id="deltype'+(idnum+1)+'" onclick="delrow('+(idnum+1)+')">'
		 +'		         <span class="glyphicon glyphicon-minus"></span>'
		 +'		    </button>'
		 +'		</td>'
		 +'	</tr>';
		 
	$('#kqds_table').append(htmls);
	
	//获取paytype1下拉内容 
	var selstr = $("#paytype1").html();
	$("#paytype" + (idnum+1)).html(selstr);
}

function tijiao (){
	
	var canKaika = Yzkaika($("#usercode").html());
	if(!canKaika){
		layer.alert('该患者存在会员卡未绑定IC卡，请先绑定' );
		return false;
	}
	
    var testz = /^\d+(\.\d+)?$/;//金额格式正则
	
	var xiaoji = $("#xiaoji").val();//充值金额
	if(xiaoji == "" || xiaoji == null){
    	layer.alert('请填写充值金额' );
		return false;
    }else{
    	var testz = /^\d+(\.\d+)?$/;
    	if(!testz.test(xiaoji)){
    		layer.alert('充值金额格式不正确' );
    		return false;
    	}
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
	/*是否有付款方式 没有选择的 如果有就提示  提交失败*/
	if($("select.shortSel").val()=="" || $(".shortSel").val()==null){
		layer.alert('请选择付款方式', {
			  
		});
		return false;
	}
	var xiaojimoney = 0;//计算总金额
	
	var money = $("#money1").val();
	if(money == ""){
		layer.alert('请填写金额' );
		return false;
	}else if(!testz.test(money)){
		layer.alert('金额格式不正确' );
		return false;
	}
	
	xiaojimoney = Number(xiaojimoney) + Number(money);
	
	var givemoney = $("#givemoney").val();
    if(givemoney != "" && givemoney != null){
    	var testz = /^\d+(\.\d+)?$/;
    	if(!testz.test(givemoney)){
    		layer.alert('赠送金额格式不正确' );
    		return false;
    	}
    }
	
    var typestrs = "";//支付类型 汉字集合
	var moneys = "";//金额数组
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
				layer.alert('请填写金额' );
	 			return false;
			}else if(!testz.test(moneyi)){
	    		layer.alert('金额格式不正确' );
	    		return false;
			}
			
			typestrs = typestrs + typestri + ",";
			moneys = moneys + moneyi + ",";
			xiaojimoney = xiaojimoney + Number(moneyi);//小计
		}
	}
	
	if(xiaojimoney != Number(xiaoji)){
		layer.alert("您填写的充值金额与充值方式总和不一致,请核实!" );
		return false;
	}
	 var msg = '接诊咨询：'+$("#askperson").find("option:selected").text()+'<br/>是否进行充值？' 
     layer.confirm(msg, {
         btn: ['确认', '放弃']
     },function(){
 		save(moneys,typestrs);
 	 }, function(){
 	 });
}
function save(moneys, typestrs) {
    var param = $('#defaultForm').serialize();
    param += '&type=chongzhi';
    var url = '<%=contextPath%>/KQDS_MemberAct/insertOrUpdate.act?' + param + '&moneys=' + moneys + '&types=' + typestrs;
    var msg;
    $.axseSubmit(url, null,
    function() {
        msg = layer.msg('加载中', {
            
        });
    },
    function(r) {
        var retdata = r.retData;
        if (r.retState == "0") {
            layer.alert('充值成功', {
                end: function() {
                    var conidnex = layer.confirm('是否打印会员卡充值缴费单吗？', {
                        btn: ['打印', '关闭'] //按钮
                    },
                    function() {
                        layer.close(conidnex);
                        var memberno = $("#memberno").html();
                        var username = $("#username").html();
                        var yue = $("#yue").html(); //余额
                        var zyue = $("#zyue").html(); //赠送余额
                        /* 	var totalmoney = $("#totalmoney").html();//余额小计 */
                        var money = $("#xiaoji").val();
                        var givemoney = $("#givemoney").val();
                        money = Number(retdata.qtmoney) + Number(retdata.yhkmoney) + Number(retdata.xjmoney) + Number(retdata.zfbmoney) + Number(retdata.wxmoney) + Number(retdata.mmdmoney) + Number(retdata.bdfqmoney);
                        var ctotalmoney = money + Number(givemoney);
                        var ymoney = Number(yue) + money;
                        var ygivemoney = Number(zyue) + Number(givemoney);
                        var ytotalmoney = ymoney + ygivemoney;

                        //先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
                        var printSet = getPrintType("会员卡充值单");
                        // 默认A5打印
                        var hyurl = contextPath + '/KQDS_Print_SetAct/toHyczPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&cztime=' + retdata.creatTime + '&memberno=' + memberno + '&username=' + username + '&money=' + Number(money).toFixed(2) + '&givemoney=' + Number(givemoney).toFixed(2) + '&totalmoney=' + Number(ctotalmoney).toFixed(2) + '&usercode=' + $("#usercode").html() + '&ymoney=' + Number(ymoney).toFixed(2) + '&recordId=' + retdata.recordId + '&ygivemoney=' + Number(ygivemoney).toFixed(2) + '&ytotalmoney=' + Number(ytotalmoney).toFixed(2) + '&cost_organization=<%=ChainUtil.getCurrentOrganization(request) %>';
                        // 弹出打印页面
                        top.layer.open({
                            type: 2,
                            title: '打印',
                            shadeClose: true,
                            //点击遮罩关闭层
                            area: ['1060px', '550px'],
                            content: hyurl
                        });

                        parent.window.location.reload();
                    },
                    function() {
                        parent.window.location.reload();
                    });
                }
            });
        } else {
            layer.alert('充值失败', {
                
            });
        }
    },
    function() {
        layer.alert('充值失败', {
            
        });
    });
}
//当天最新挂号信息
function getRegxx(usercode) {
    var detailurl = contextPath + '/KQDS_REGAct/selectToDayNewDetail.act?usercode=' +usercode;
    $.axse(detailurl, null,
    function(r) {
        if (r.retState == "0") {
            $('#askperson').val(r.askperson);
            $('#regsort').val(r.regsort);
        }else{
        	layer.alert(r.retMsrg  );
        }
    },
    function() {
        layer.alert(r.retMsrg );
    });
}
</script>
</html>
