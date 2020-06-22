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
<title>会员卡退费</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/addVisting.css" />
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

	#输入框缩短一些
	input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}

</style>
<body>
<div style="text-align:center;">
    <form id="defaultForm">
    	<input type="hidden" name="seqId" id="seqId"/>
    	
    	<table class="kqds_table" id="kqds_table">
    		<tr>
    			<td style="width:10%;text-align:right;">编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
    			<td style="width:20%;text-align:left;"><span id="usercode"></span></td>
    			
    			<td style="width:10%;text-align:right;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
    			<td style="width:20%;text-align:left;"><span id="username"></span></td>
    			
    			<td style="width:10%;text-align:right;">卡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
    			<td style="width:30%;text-align:left;"><span id="memberno"></span></td>
    		</tr>
    		<tr>
    			<td style="width:10%;text-align:right;">接诊咨询：</td>
    			<td style="width:20%;text-align:left;">
    				<select  name="askperson" id="askperson" ></select>
    			</td>
    			
    			<td style="width:6%;text-align:right;">挂号分类：</td>
    			<td style="width:20%;text-align:left;" colspan="3">
    				<select class="dict" tig="ghfl" name="regsort" id="regsort"></select>
    			</td>
    		</tr>
    		<tr>
    			<td style="text-align:right;">赠送余额：</td>
    			<td style="text-align:left;"><span id="zyue"></span></td>
    			
    			<td style="text-align:right;">充值余额：</td>
    			<td style="text-align:left;"><span id="yue"></span></td>
    			
    			<td style="text-align:right;">余额小计：</td>
    			<td style="text-align:left;"><span id="totalmoney"></span></td>
    		</tr>
    		
    		<tr>
    		
    			<td style="text-align:right;">退费-赠送：</td>
    			<td style="text-align:left;">
    				<input type="text" id="givemoney" name="givemoney" value="0" style="width:80%;">
    			</td>
    			
    			<td style="text-align:right;">退费-充值：</td>
    			<td style="text-align:left;">
    				<input type="text" id="xiaoji" name="xiaoji" value="0" style="width:80%;">
    			</td>
    			
    			<td style="text-align:right;">退费方式：</td>
    			<td style="text-align:left;">
    				<select name="paytype" id="paytype1" style="width:75px">
					</select>
					<input type="text" placeholder="" name="money" id="money1" style="width:100px" value="0">
					<button type="button" class="btn btn-default btn-sm" id="addtype" onclick="addrow()">
				         <span class="glyphicon glyphicon-plus"></span>
				    </button>
    			</td>
    		</tr>
    	</table>
    	<table class="kqds_table" >
    		<tr>
    			<td style="text-align:right;width:10%;">退费原因：</td>
    			<td style="text-align:left;" colspan="5">
    				<input type="text" id="tfremark" name="tfremark" value="" style="width:51%;"><span style="color: red;font-size:12px;">*</span>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="6" style="color: red;font-size: 16px;font-weight: bold;">* 注：退费金额只能填写0或者负数</td>
    		</tr>
    	</table>
    	
   		<a id="searchHfzx" href="javascript:void(0);" class="kqdsCommonBtn" onclick="tijiao()">提交</a>
    		
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

var seqId = '<%=seqId%>';

var contextPath = '<%=contextPath%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var onclickrow = window.parent.onclickrowOobj;

$(function(){
	initMemberFkfsSelect("paytype1");//充值付款方式
	//咨询 下拉列表
	initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");
	initDictSelectByClass(); // 会员类型
	$("#seqId").val(seqId);
	$("#usercode").html(onclickrow.usercode);
	$("#username").html(getNameByusercodesTB(onclickrow.usercode)[0].username);
	$("#memberno").html(onclickrow.memberno);
	$("#yue").html(onclickrow.money);
	$("#zyue").html(onclickrow.givemoney);//赠送余额
	var tmoney = Number(onclickrow.money) + Number(onclickrow.givemoney);
	$("#totalmoney").html(tmoney.toFixed(2));//余额小计
	getRegxx($("#usercode").html());
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
	 +'		<td style="text-align:left;">'
	 +'			<select name="paytype" id="paytype'+(idnum+1)+'" style="width:75px">'
	 +'			</select>'
	 +'			<input type="text" placeholder="金额" id="money'+(idnum+1)+'" name="money" style="width:100px">'
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
	//验证表单
	var testfs = /^((-\d+(\.\d+)?)|(0+(\.0+)?))$/;
	
	var xiaoji = $("#xiaoji").val();//退费充值金额
    if(xiaoji == "" || xiaoji == null){
    	layer.alert('请填写退费-充值金额' );
		return false;
    }else{
    	if(xiaoji > 0){
    		layer.alert("退费-充值金额只能填写负数或0" );
    		return false;
    	}else if(!testfs.test(xiaoji)){
       		layer.alert('退费-充值金额格式不正确' );
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
    var givemoney = $("#givemoney").val();
    if(givemoney != "" && givemoney != null){
    	if(givemoney > 0){
    		layer.alert("退费-赠送金额只能填写负数或0" );
    		return false;
    	}else if(!testfs.test(givemoney)){
       		layer.alert('退费-赠送金额格式不正确' );
       		return false;
       	}
    }else{
    	$("#givemoney").val(0);
    	givemoney = $("#givemoney").val();
    }
	
	var zyue = Number($("#zyue").html());//赠送余额
	var yue = Number($("#yue").html());//余额
	if((Number(givemoney) + zyue) < 0){
		layer.alert('赠送余额不足' );
   		return false;
	} else if((Number(xiaoji) + yue) < 0){
		layer.alert('余额不足' );
   		return false;
	}
	
	var xiaojimoney = 0;//计算总金额
	
    var money = $("#money1").val();
    if(money == "" || money == null){
    	layer.alert('请填写退费方式' );
		return false;
    }else{
    	if(money > 0){
    		layer.alert("退费方式只能填写负数或0" );
    		return false;
    	}else if(!testfs.test(money)){
       		layer.alert('退费方式格式不正确' );
       		return false;
       	}
    }
    
    xiaojimoney = Number(xiaojimoney) + Number(money);
    
    var testz = /^\d+(\.\d+)?$/;//金额格式正则
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
			
			if(moneyi == "" || moneyi == null){
		    	layer.alert('请填写退费金额' );
				return false;
		    }else{
		    	if(moneyi > 0){
		    		layer.alert("退费金额只能填写负数或0" );
		    		return false;
		    	}else if(!testfs.test(moneyi)){
		       		layer.alert('退费金额格式不正确' );
		       		return false;
		       	}
		    }
			
			typestrs = typestrs + typestri + ",";
			moneys = moneys + moneyi + ",";
			xiaojimoney = Number(xiaojimoney) + Number(moneyi);
			
		}
	}
	
	if(Number(xiaojimoney).toFixed(2) != Number(xiaoji).toFixed(2)){
		layer.alert("您填写的退费-充值金额与退费方式总和不一致,请核实!" );
		return false;
	}
    
    var tfremark = $("#tfremark").val();
    if(tfremark == "" || tfremark == null){
    	layer.alert('请填写退费原因' );
		return false;
    }
    
    var param = $('#defaultForm').serialize();
    param+='&type=tuifei';
    
    var url = '<%=contextPath%>/KQDS_MemberAct/tksqMember.act?'+ param + '&moneys=' + moneys + '&types=' + typestrs;
    var msg;
    $.axseSubmit(url,null, 
 	function(){
		 msg=layer.msg('加载中', {icon: 16});
  		  },
          function(r){
       if(r.retState=="0"){
	       	layer.alert('申请成功', {  end:function(){
	       		parent.closePage();
	       	}});
       }else{
       	layer.alert('申请失败' );
       }     
          },
          function(){
        	  layer.alert('申请失败' );
      	  }
    );
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
