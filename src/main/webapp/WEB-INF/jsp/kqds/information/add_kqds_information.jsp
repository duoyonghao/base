<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!-- 配置文件 -->
<script type="text/javascript" src="<%=contextPath%>/third-party/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="<%=contextPath%>/third-party/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="<%=contextPath%>/third-party/ueditor/lang/zh-cn/zh-cn.js" charset="utf-8"></script>
<!-- 实例化编辑器 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增信息库</title>
<!-- 使用 后台-收费项目添加的样式表 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>
<body>
<form class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
	<div class="box-body">
		
		<table class="tableLayout">
			<tr>	
				<td>
					<span class="commonText">标题<span style="color: red;">*</span></span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" class="form-control" name="title" id="title" placeholder="标题">
					</div>
				</td>
				<td>
					<span class="commonText">信息分类<span style="color: red;">*</span></span>
				</td>
				<td>
					<select class="select2 dict" tig="XXKFL" name="type" id="type">
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<textarea name="content" id="content"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div class="btnGroup">
		<button id="save" class="kqdsCommonBtn">提交</button>
	</div>
</form>
<script>
var ue = "";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {

    ue = UE.getEditor('content', {
        initialFrameHeight: 280,
        initialFrameWidth: 650,
        elementPathEnabled: false,
        toolbars: [['fullscreen', 'undo', 'redo', 'bold', 'indent', 'italic']]
    });

    initDictSelectByClassOrg('<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>');

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
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/KQDS_InformationBackAct/insertOrUpdate.act';
        var msg;
        var urldata = {
            title: $("#title").val(),
            type: $("#type").val(),
            organization: $("#organization").val(),
            content: ue.getContent()
        };
        $.axseSubmit(url, urldata,
        function() {
            msg = layer.msg('加载中', {
                icon: 16
            });
        },
        function(r) {
            layer.close(msg);
            if (r.retState == "0") {
            	 layer.alert('保存成功', {
		                
		              end: function() {
		            		parent.refresh();
				        	parent.layer.close(frameindex); //再执行关闭 
		              }
		          });
            } else {
                layer.alert('添加失败', {
                      
                });
            }
        },
        function() {
            layer.alert('添加失败'  );
        });

    });

});
</script>

</body>
</html>
