<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
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
</head>
<style type="text/css">
	.kqds_btn{
		height:30px;
		line-height:30px;
		padding: 5px 30px;
		color: #fff;
		border-radius:15px;
		border:none;
		outline:none;
		background: #0e7cc9;
		margin-right: 170px;
	}
	.kqds_btn:hover,.kqds_btn:focus{color: #FFFFFF;text-decoration: none;background: #40a4ea;}
	
	.kqds_table{
		width:90%;
		align:center;
		margin-left: auto;
		margin-right: auto;
		margin-top: 5px;
		border-collapse:separate; 
		border-spacing:0px 4px;
	}
	
	.kqds_table  td { 
		color: #666;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		font-weight: normal;
		line-height: 28px;
	}
	
	.kqds_table  select { 
		height: 28px;
		width:212px;
		border: solid 1px #e5e5e5;
		border-radius: 3px;
	}
	.kqds_table{
		
	}
	.kqds_table tr{
		height:30px;
	}
	.kqds_table tr td:first-child{
		padding-left:56px;
	}
</style>
<body>
<div style="text-align:center;">
    <form id="defaultForm">
    	<input type="hidden" name="seqId" id="seqId"/>
    	
    	<table class="kqds_table">
    	
    		<tr>
    			<td style="text-align:right;">转赠人姓名：</td>
    			<td style="text-align:left;">
    				<input type="text" name="usercodeDesc" id="usercodeDesc" placeholder="请选择转赠人" title="请选择转赠人" class="form-control" value=""  onclick="getuserandmemberno1()"/>
    			</td>
    			
    			<td style="text-align:right;">受赠人姓名：</td>
    			<td style="text-align:left;">
    				<input type="text" name="usercode1Desc" id="usercode1Desc" placeholder="请选择受赠人" title="请选择受赠人" class="form-control" value=""  onclick="getuserandmemberno2()"/>
    			</td>
    			
    		</tr>
    		
    		<tr>
    			<td style="text-align:right;">转赠人编号：</td>
    			<td style="text-align:left;">
    				<input type="text" name="usercode" id="usercode"  class="form-control" value="" readonly/>
    			</td>
    			
    			<td style="text-align:right;">受赠人编号：</td>
    			<td style="text-align:left;">
    				<input type="text" name="usercode1" id="usercode1"  class="form-control" value="" readonly/>
    			</td>
    			
    		</tr>
    		
    		<tr>
    			
    			<td style="text-align:right;">转赠卡号：</td>
    			<td style="text-align:left;">
    				<select name="memberno" id="memberno">
						<option>请选择</option>
					</select>
    			</td>
    			
    			<td style="text-align:right;">受赠卡号：</td>
    			<td style="text-align:left;">
    				<select name="memberno1" id="memberno1">
						<option>请选择</option>
					</select>
				</td>
    			
    		</tr>
    		
    		<tr>
    			<td style="text-align:right;">卡内充值余额：</td>
    			<td style="text-align:left;">
    				<span id="yue1" name="yue1" style="font-size:15px;color:green;"></span>
    			</td>
    			
    			<td style="text-align:right;">转赠-充值金额：</td>
    			<td style="text-align:left;">
    				<input class="form-control" type="text" id="money1" name="money1" placeholder="请选择转赠-充值金额" value="0"/>
    			</td>
    			
    		</tr>
    		
    		<tr>
    			
    			<td style="text-align:right;">卡内赠送余额：</td>
    			<td style="text-align:left;">
    				<span id="yue2" name="yue2" style="font-size:15px;color:green;"></span>
    			</td>
    			
    			<td style="text-align:right;">转赠-赠送金额：</td>
    			<td style="text-align:left;">
    				<input class="form-control" type="text" id="money2" name="money2" placeholder="请选择转赠-赠送金额" value="0"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td style="text-align:right;">转赠备注：</td>
    			<td style="text-align:left;" colspan="5">
    				<input type="text" id="zzremark" name="zzremark" value="" style="width:595px;"><span style="color: red;font-size:12px;">*</span>
    			</td>
    		</tr>
    		
    		<tr><td colspan="4" align="center"><span style="color: red;font-size: 15px;">*转赠金额只能填写大于0的数字</span></td></tr>
    		<tr>
    			<td colspan="4" align="center">
    				<a style="text-align: center;margin: 0 auto;" id="searchHfzx" href="javascript:void(0);" class="kqdsSearchBtn" onclick="tijiao()">提交</a>
    			</td>
    		</tr>
    	</table>
        
    </form>
</div>
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var onclickrow = window.parent.onclickrowOobj;

$(function(){
	//父页面选中行  则自动赋值 
	if(onclickrow != "" && onclickrow != null){
		$("#usercode").val(onclickrow.usercode);
		$("#usercodeDesc").val(onclickrow.username);
		$("#memberno").val(onclickrow.memberno);
		$("#yue1").html(onclickrow.money);
		$("#yue2").html(onclickrow.givemoney);
		intMemberCardListByUserCode4No("memberno",$("#usercode").val());//会员下拉
	}
});
$("#memberno").change(function(){
	 var url = contextPath + '/KQDS_MemberAct/selectDetailByMemberno.act?memberno=' + $("#memberno").val();
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null && list.length > 0) {
          	var member = list[0];
          	$("#yue1").html(member.money);
    		$("#yue2").html(member.givemoney);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
});
function tijiao(){
	
	var memberno = $("#memberno").val();
	if(memberno == "" || memberno == null){
		layer.alert('请选择 转赠 的会员卡！', {  });
		return false;
	}
	
	var memberno1 = $("#memberno1").val();
	if(memberno1 == "" || memberno1 == null){
		layer.alert('请选择 受赠 的会员卡！', { });
		return false;
	}
	if(memberno == memberno1){
		layer.alert('转赠的会员卡与受赠的会员卡不能相同！', {  });
		return false;
	}
	var testmoney = /^\d+(\.\d+)?$/;//金额格式正则
	
	var yue1 = $("#yue1").html();
	var yue2 = $("#yue2").html();
	var money1 = $("#money1").val();
	if(money1 == "" || money1 == null){
		layer.alert('请填写转赠-充值金额！', {  });
		return false;
	}else if(!testmoney.test(money1)){
		layer.alert('转赠-充值金额 格式不正确' );
		return false;
	}else if(!testmoney.test(money1)){
		layer.alert('转赠-充值金额 格式不正确' );
		return false;
	}
	var money2 = $("#money2").val();
	if(money2 == "" || money2 == null){
		layer.alert('请填写转赠-赠送金额！', {  });
		return false;
	}else if(!testmoney.test(money2)){
		layer.alert('转赠-赠送金额 格式不正确' );
		return false;
	}
	
	var zzremark = $("#zzremark").val();
    if(zzremark == "" || zzremark == null){
    	layer.alert('请填写转赠备注' );
		return false;
    }
	
	yue1 = Number(yue1);
	yue2 = Number(yue2);
	money1 = Number(money1);
	money2 = Number(money2);
	//验证余额是否大于用户填写的转赠金额
	if(yue1 < money1){
		layer.alert('充值余额不足，请重新填写转赠-充值金额' );
		return false;
	}
	if(yue2 < money2){
		layer.alert('赠送余额不足，请重新填写转赠-赠送金额' );
		return false;
	}
	
	var usercode = $("#usercode").val();
	var usercode1 = $("#usercode1").val();
	var username = $("#usercodeDesc").val();
	var username1 = $("#usercode1Desc").val();
	
	//验证成功
	var param = "memberno=" + memberno
			  + "&memberno1=" + memberno1
			  + "&usercode=" + usercode
			  + "&usercode1=" + usercode1
			  + "&username=" + username
			  + "&username1=" + username1
			  + "&money1=" + money1
			  + "&money2=" + money2
			  + "&zzremark=" + zzremark
	;
	var url = '<%=contextPath%>/KQDS_MemberAct/zhuanzeng.act?';
    var msg;
    $.axseSubmit(url,param, 
 	function(){
		 msg=layer.msg('加载中', {icon: 16});
  		  },
          function(r){
       if(r.retState=="0"){
	       	layer.alert('转赠成功', {  end:function(){
	       		parent.window.location.reload();
	       	}});
       }else{
       	layer.alert('转赠失败' );
       }     
          },
          function(){
        	  layer.alert('转赠失败' );
      	  }
    );
	
}

//转赠人信息
function getuserandmemberno1(){
	//查询患者
	layer.open({
        type: 2,
        title: '选择患者',
        shadeClose: true,
        shade: 0.6,
        area: ['80%', '90%'],
        content: contextPath+'/KQDS_UserDocumentAct/toUserList.act',
        end: function(index, layero){
        	$("#usercode").val(usercodechild);
        	$("#usercodeDesc").val(usernamechild);
        	//查询会员卡号
        	//getmemerno(usercodechild,"memberno",1);
        	intMemberCardListByUserCode4No("memberno",$("#usercode").val());//会员下拉
        	//清空子页面传值
        	usercodechild = "";
        	usernamechild = "";
        },
    });
}

//接收人信息
function getuserandmemberno2(){
	//查询患者
	layer.open({
        type: 2,
        title: '选择患者',
        shadeClose: true,
        shade: 0.6,
        area: ['80%', '80%'],
        content: contextPath+'/KQDS_UserDocumentAct/toUserList.act?typechoose=1',
        end: function(index, layero){
        	$("#usercode1").val(usercodechild);
        	$("#usercode1Desc").val(usernamechild);
        	//查询会员卡号
        	//getmemerno(usercodechild,"memberno1",2);
        	intMemberCardListByUserCode4No("memberno1",$("#usercode1").val());//会员下拉
        },
    });
}

//根据患者编号查询会员编号 
function getmemerno(usercode,tagid,type){
	//查询会员卡号
	var url = contextPath + '/KQDS_MemberAct/selectDetailByUsercode.act?usercode=' + usercode;
	$.axse(url, null, function(data) {
		var list = data.data;
		if (list != null) {
			if (list.length > 0) {
				$("#" + tagid).val(list[0].memberno);
				//type=1 转赠会员 需要显示余额和赠送余额
				if(type == 1){					
					$("#yue1").html(list[0].money);
					$("#yue2").html(list[0].givemoney);
				}
				//判断选择的会员卡是否相同
				var memberno = $("#memberno").val();
				var memberno1 = $("#memberno1").val();
				if(memberno == memberno1){
					layer.alert('不能选择同一个会员卡，请重新选择！', {  });
				}
			}else{
				//清空会员卡等信息 防止第一次选择的有会员卡 第二次选择的没有会员卡 值会残留
// 				$("#" + tagid).val("");
// 				$("#yue1").html("");
// 				$("#yue2").html("");
				$("#usercode1Desc").val("");
				$("#usercode1").val("");
				$("#memberno1").val("");
				layer.alert('您选择的患者没有办理会员卡，请重新选择！', {  });
			}
		}else{
			//清空会员卡等信息 防止第一次选择的有会员卡 第二次选择的没有会员卡 值会残留
// 			$("#" + tagid).val("");
// 			$("#yue1").html("");
// 			$("#yue2").html("");
				$("#usercode1Desc").val("");
				$("#usercode1").val("");
				$("#memberno1").val("");
			layer.alert('您选择的患者没有办理会员卡，请重新选择！', {  });
		}
	}, function() {
		layer.alert('查询出错！', {
		});
	});
}

</script>
</html>
