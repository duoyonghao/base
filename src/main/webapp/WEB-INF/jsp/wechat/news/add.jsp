<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>
<body>
<div class="printContainer"><!--排班的容器  -->
<form id="form1">
	<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
	<table class="tableLayout">
		<tr>
			<td>	<!--commonText信息字样的样式  -->
				<span class="commonText">信息分类<span style="color: red;">*</span></span>
			</td>
			<td>
				<div class="form-group">
					<!--.whiteInp 白底鼠标移入有小手的效果  -->
					<select class="select2 dict" tig="WECHAT_NEWSSORT" name="newstype" id="newstype"></select>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<span class="commonText">图文名称<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="newsname" id="newsname" placeholder="">
				</div>
			</td>
		</tr>
		<tr>
			<td>	<!--commonText信息字样的样式  -->
				<span class="commonText">排序号<span style="color: red;">*</span></span>
			</td>
			<td>
				<div class="form-group">
					<!--.whiteInp 白底鼠标移入有小手的效果  -->
					<input type="text" name="sortno" id="sortno" placeholder="" value="0">
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
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    initDictSelectByClass(); // 患者来源，职业
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            newstype: {
                validators: {
                    notEmpty: {
                        message: '请选择信息类别'
                    }
                }
            },
            newsname: {
                validators: {
                    notEmpty: {
                        message: '请填写图文名称'
                    }
                }
            },
            sortno: {
                validators: {
                    notEmpty: {
                        message: '请填写排序号'
                    }
                }
            }
        }
    }).on('success.form.bv',
    function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var param = $('#form1').serialize();
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/WXNewsAct/insertOrUpdate.act?' + param;
        $.axseSubmit(url, null,
        function() {},
        function(r) {
            if (r.retState == "0") {
                layer.alert('添加成功', {
                      
                },
                function(index) {
                    parent.refresh();
                    parent.layer.close(frameindex); //再执行关闭 */
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
