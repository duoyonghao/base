<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
  String seqId = request.getParameter("seqId");
  if(seqId == null){
	  seqId = "";
  }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>短信发送</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<style type="text/css">

.infoBd{
	font-size: 12px;
	border-bottom: solid 0px #0e7cc9;
	padding:0 10px 10px;
}

.formBox{
	overflow: hidden;
	padding: 10px 0;
	margin-bottom:10px;
	width:500px;
	margin:0 auto;
}

.formBox .kv{float:left;margin:0 10px 10px 0;}
.kv > label{margin-right: 10px;} /* 重写style.css中的label样式 */

.aBtn{display: inline-block;margin-right:10px;padding: 0 20px;height: 28px;line-height: 28px;border: solid 1px #0e7cc7;border-radius:15px;color: #0e7cc7;}
.aBtn:hover{cursor: pointer;color: #fff;background: #117cca;text-decoration: none;}

</style>

</head>
<body>
	<div class="infoBd">
		<div class="formBox">
			<form  id="form1">
		   		<input type="hidden"  name="usercode" id="usercode">
		   		<input type="hidden"  name="sendphone" id="sendphone">
		   		<input type="hidden"  name="smstype" id="smstype">
		   		<input type="hidden"  name="smsnexttype" id="smsnexttype">			
				<input type="hidden" class="form-control" name="seqId" id="seqId" value="<%=seqId%>">	
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
				       	<textarea  style="width: 500px;" name="smscontent" id="smscontent" rows="7" placeholder="" ></textarea>	
					</div>
				</div>
			</form>
	    </div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var seqId = "<%=seqId %>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	var url = 'KQDS_SmsAct/selectDetail.act';
    var pageParam = {
    		seqId: seqId
        };
    var returnData = getDataFromServer(url, pageParam);
    if (returnData.retState == 0) {
   		var detaildata = returnData.data;
   		if(detaildata.smsstate == "1"){
   			var sendtime = document.getElementById("sendtime");
   	    	sendtime.style.display="block";
   		}
   		loadData(detaildata);
    } else {
        layer.alert('查询失败！'  );
    }
    readonly();
});
</script>

</html>
