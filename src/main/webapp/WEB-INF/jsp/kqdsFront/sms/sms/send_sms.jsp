<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		  usercode = "";
	}
	String sendphone = request.getParameter("sendphone");
	if(sendphone == null){
		  sendphone = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>短信模板</title>
<!-- 点击右侧个人中心  中间病历 图标 页面， 进入后点击  常用病历库按钮 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/fhbuttonTree.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<style>
	.kuBtn{
		margin: 0 3% 0 0;
	}.infoBd{
	font-size: 12px;
	border-bottom: solid 0px #0e7cc9;
	padding:0 10px 10px;
}

.formBox{
	overflow: hidden;
	padding: 10px 0;
	margin-bottom:10px;
	width:100%;
	margin:0 auto;
}

.formBox .kv{float:left;margin:0 10px 10px 0;}
.kv > label{margin-right: 10px;} /* 重写style.css中的label样式 */

.aBtn{display: inline-block;margin-right:10px;padding: 0 20px;height: 28px;line-height: 28px;border: solid 1px #0e7cc7;border-radius:15px;color: #0e7cc7;}
.aBtn:hover{cursor: pointer;color: #fff;background: #117cca;text-decoration: none;}
	
</style>
</head>
<body>
	<div style="float: left;width:29%;margin-left:1%;">
		<ul id="treeDemo" class="ztree" style="width:98%;margin-top:0;"></ul>
	</div>
	<div class="infoBd" style="float: left;width:69%;margin-right:1%;">
	<div class="formBox">
			<form  id="form1">
		   		<input type="hidden"  name="usercode" id="usercode">
		   		<input type="hidden"  name="sendphone" id="sendphone">
		   		<input type="hidden"  name="smstype" id="smstype">
		   		<input type="hidden"  name="smsnexttype" id="smsnexttype">				
		        <div class="kv">
		          <label>发送时间</label>
		          <div class="kv-v">
				         <input id="ssfs" checked="checked" type="radio" name="smsstate" value="0"><label>实时发送</label>
						 <input id="dsfs" type="radio" name="smsstate" value="1"><label>定时发送</label>	
		    	  </div>
				</div>
				<div class="kv">
		          <div class="kv-v">
	              		 <input type="text" style="display: none;" placeholder="发送时间" id="sendtime" name="sendtime" readonly class="birthDate">
		    	  </div>
				</div>
				<div class="kv">
				   	<div class="kv-v">
				       	<textarea  style="width: 420px;" name="smscontent" id="smscontent" rows="7" placeholder="" ></textarea>	
					</div>
				</div>
			</form>
			</div>
			<div class="buttonBar" style="text-align: center;">
				<a href="javascript:void(0);" onclick="saveform()" class="kqdsCommonBtn">保存</a>
	       </div>
	</div>
<script>
var perseqId = "<%=person.getSeqId()%>";
var rows = window.parent.selectedrows;
var usercode = "<%=usercode %>",sendphone="<%=sendphone %>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    zTreeInit();
    $(".birthDate").datetimepicker({
	    language: 'zh-CN',
	    format: 'yyyy-mm-dd hh:ii:ss',
	    minView: 0,
	    autoclose: true,
	    //选中之后自动隐藏日期选择框   
	    pickerPosition: "buttom-right"
	});
	if(usercode == ""){
		if(rows){
			for(var i=0;i<rows.length;i++){
				usercode += rows[i].usercode+",";
				sendphone += rows[i].phonenumber1+",";
			}
			if(usercode.length>0){
				usercode = usercode.substring(0,usercode.length-1);
				sendphone = sendphone.substring(0,sendphone.length-1);
				$("#usercode").val(usercode);
				$("#sendphone").val(sendphone);
			}
		}
	}else{
		$("#usercode").val(usercode);
		$("#sendphone").val(sendphone);
	}
    /*树自适应高度  */
    selfAdaptive();
});
/*树自适应高度  */
function selfAdaptive(){
	var treeHeight=$(window).height()-$(".btnFather").outerHeight()-10;
	$("#treeDemo").outerHeight(treeHeight-$(".searchWrap").outerHeight());
	$("#blcontent").outerHeight(treeHeight);
}
function zTreeInit() {
    var zNodes;
    var url = '<%=contextPath%>/KQDS_Sms_ModelAct/getSelectModelTree.act';
    $.axse(url, null,
    function(data) {
        zNodes = eval(data.tree);
    },
    function() {
        layer.alert('加载失败！' );
    });
    var setting = {
   		data: {
   			simpleData: {
   				enable:true,
   				idKey: "id",
   				pIdKey: "pId",
   				rootPId: "0"
   			}
   		},
    };
    setting['callback'] = {
        onClick: onCheck
    };
    setting['check'] = {
        enable: false
    };
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
}
function getFont(treeId, node) {
	return node.font ? node.font : {};
}
function onCheck(e, treeId, treeNode) {
    if (treeNode.isParent || treeNode.level == 1) { // 第二层也不请求后台
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    } else {
        treeNode.check_Focus = true;
        treeNode.checked = true;
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.updateNode(treeNode);
        var detailurl = '<%=contextPath%>/KQDS_Sms_ModelAct/selectDetail.act?seqId=' + treeNode.id;
        $.axse(detailurl, null,
        function(data) {
        	$("#smscontent").html(data.data.smscontent);
        	$("#smstype").val(data.data.smstype);
        	$("#smsnexttype").val(data.data.smsnexttype);
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}
$("input:radio[name=smsstate]").change(function() {
    if ($(this).val() == 1) {
    	var sendtime = document.getElementById("sendtime");
    	sendtime.style.display="block";
    } else {
    	var sendtime = document.getElementById("sendtime");
    	sendtime.style.display="none";
    	sendtime.value="";
    }
});
function saveform() {
	var smsstate = $('input[name="smsstate"]:checked ').val();
	if(smsstate == 1){
		 if ($("#sendtime").val() == "") {
		        layer.alert('请选择发送时间', {
		              
		        });
		        return false;
		 }
	}
    if ($("#smscontent").val() == "") {
        layer.alert('请编辑短信内容' );
        return false;
    }
    var param = $('#form1').serialize();
    var url = 'KQDS_SmsAct/insertOrUpdate.act';
    var returnData = getDataFromServer(url, param);
    if (returnData.retState == 0) {
    	layer.alert('保存成功', {
	  	      
	  	    end: function () {
		  	    try{
  	    		parent.$("#tabIframe")[0].contentWindow.refresh();
  	    	}catch(e){
  	    		parent.refresh();
  	    	}
  	    	parent.layer.close(frameindex); //再执行关闭
	  	    }
  	    });
    } else {
        layer.alert(returnData.retMsrg  );
    }
}
</script>
</body>
</html>
