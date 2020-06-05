<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
   String seqId = request.getParameter("seqId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>编辑赠送项目</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
</head>
<body>
<div class="giveItemContainer">
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">项目编号</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="itemno" id="itemno" placeholder="" readonly>
					</div>
				</td>
				<td>
					<span class="commonText">项目名称</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="itemname" id="itemname" placeholder="" readonly>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">单位</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="unit" id="unit" placeholder="" readonly>
					</div>
				</td>
				<td>
					<span class="commonText">单价</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="unitprice" id="unitprice" placeholder="" readonly>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">排序号</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="sortno" id="sortno" placeholder="">
					</div>
				</td>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">是否禁用</span>
					
				</td>
				<td>
					<div class="form-group">
						<select name="useflag" id="useflag" >
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</td>
			</tr>
			
			
		</table>
		<div class="btnGroup"><!--.btnGroup 是.abtn按钮的父元素 -->
			<button id="save" class="kqdsCommonBtn">提交</button>
		</div>
	</form>
</div>
<script>
var seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    var detailurl = '<%=contextPath%>/KQDS_Give_ItemBackAct/selectDetail.act?seqId=' + seqId;
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
        fields: {}
    }).on('success.form.bv',
    function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var param = $('#form1').serialize();
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/KQDS_Give_ItemBackAct/insertOrUpdate.act?' + param;
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
            	layer.alert('修改失败：' + r.retMsrg  );
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
