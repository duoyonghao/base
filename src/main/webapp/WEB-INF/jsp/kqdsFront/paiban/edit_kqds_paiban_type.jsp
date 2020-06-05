<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
   String seqId = request.getParameter("seqId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>编辑排班类型</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>
<body>
<div class="paibanContainer"><!--排班的容器  -->
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">上班时间<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<!-- .birthDate与弹出时间选择框功能有关系           .whiteInp 白底鼠标移入有小手的效果  -->
						<input type="text" class="birthDate whiteInp" name="startdate" id="startdate"  placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">下班时间<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<!-- .birthDate与弹出时间选择框功能有关系   .whiteInp 白底鼠标移入有小手的效果 -->
						<input class="birthDate whiteInp" type="text" class="birthDate" name="enddate" id="enddate" placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="commonText">排班类型<span style="color: red;">*</span></span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="typename" id="typename" placeholder="" >
					</div>
				</td>
			</tr>
		</table>
		<div class="btnGroup">
			<button id="save" class="kqdsCommonBtn">提交</button>
		</div>
	</form>
</div>	
<script>
var seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'hh:ii',
        startView: 0,
        minView: 0,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });
    var detailurl = '<%=contextPath%>/KQDS_Paiban_TypeBackAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        loadData(data.data);
    },
    function() {
        layer.alert('查询出错！' );
    });
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {

}
    }).on('success.form.bv',
    function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var param = $('#form1').serialize();
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/KQDS_Paiban_TypeBackAct/insertOrUpdate.act?' + param;
        var msg;
        $.axseSubmit(url, null,
        function() {
            msg = layer.msg('加载中', {
                icon: 16
            });
        },
        function(r) {
            layer.close(msg);
            if (r.retState == "0") {
                layer.alert('修改成功', {
                      
                });
                parent.refresh();
                parent.layer.close(frameindex); //再执行关闭 
            } else {
                layer.alert('修改失败', {
                      
                });
            }
        },
        function() {
            layer.alert('修改失败'  );
        });
    });
});
</script>
</body>
</html>
