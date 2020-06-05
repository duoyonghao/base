<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
  String seqId = request.getParameter("seqId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>指定接诊咨询</title><!-- 从咨询中心 > 转诊按钮 进入转诊列表 > 点击转诊按钮 进入   -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
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
	        <div class="kv">
	          <label>接诊咨询</label>
	          <div class="kv-v">
              		<select  name="askperson" id="askperson" ></select>
	    	  </div>
			</div>
		</form>
	    </div>
	     <div class="buttonBar" style="text-align: center;">
				<a href="javascript:void(0);" onclick="saveform()" class="kqdsSearchBtn">保存</a>
	      </div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/core/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/core/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var seqId = "<%=seqId %>";
$(function() {
	initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");
});
function saveform() {
    if ($("#askperson").val() == "") {
        layer.alert('请选择咨询人员' );
        return false;
    }
    var a = {
    		seqId: seqId,
    		askperson:$("#askperson").val()
    };
    var url = '<%=contextPath%>/KQDS_Member_RecordAct/setAskperson.act';
    $.axseSubmit(url, a,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('修改成功', {
                  
                end: function() {
                	parent.refresh(); // 刷新页面
                    var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(frameindex); //再执行关闭 */
                   
                }
            });
        } else {
            layer.alert('修改失败'  );
        }
    },
    function() {
        layer.alert('修改失败' );
    });
}
</script>

</html>
