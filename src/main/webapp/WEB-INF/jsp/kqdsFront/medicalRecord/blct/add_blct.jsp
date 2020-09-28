<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
	String cttype = request.getParameter("cttype");
	if(cttype == null){
		cttype = "";
	}
	String ctnexttype = request.getParameter("ctnexttype");
	if(ctnexttype == null){
		ctnexttype = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增词条</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>

<body>
<form class="container" class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
	<table class="tableLayout">
		<tr>
			<td>
				<span class="commonText">一级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="cttype" id="cttype" class="dict" tig="blct124">
					</select>
				</div>
			</td>
			<td>
				<span class="commonText">二级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="ctnexttype" id="ctnexttype" >
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<span class="impText">排序号</span>
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="orderno" id="orderno" value="0"></div>
			</td>
			<td>
				<span class="impText">是否禁用</span>
			</td>
			<td>
				<div class="form-group">
					<select name="flag" id="flag" style="width: 100%;">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</td>
		</tr>
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">词条内容<span style="color: red;">*</span></span>
				
			</td>
			<td colspan="3">
				<div class="form-group">
					<textarea rows="2" class="longTextarea" name="ctname" id="ctname" ></textarea>
				</div>
			</td>
		</tr>
	</table>
	<div class="btnGroup">
		<button id="save" class="kqdsCommonBtn">提交</button>
	</div>
</form>

<script>
var organization = '<%=organization %>';
var cttype = '<%=cttype %>';
var ctnexttype = '<%=ctnexttype %>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	initDictSelectByClassOrg(organization);
	$('#cttype').val(cttype).trigger('change');
    $('#ctnexttype').val(ctnexttype);
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            ctname: {
                message: '词条内容验证失败',
                validators: {
                    notEmpty: {
                        message: '词条内容不能为空'
                    }
                }
            },
            cttype: {
                validators: {
                    notEmpty: {
                        message: '一级分类不能为空'
                    }
                }
            },
            ctnexttype: {
                validators: {
                    notEmpty: {
                        message: '二级分类不能为空'
                    }
                }
            },
            orderno: {
                validators: {
                	numeric: {
                        message: '只能输入数字'
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
        var url = '<%=contextPath%>/YZBlctAct/insertOrUpdate.act?' + param;
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
                layer.alert('添加成功', {
                    end: function() {
                        parent.refresh();
                        parent.layer.close(frameindex); //再执行关闭 
                    }
                });
            } else {
                layer.alert('添加失败');
            }
        },
        function() {
            layer.alert('添加失败：' + data.retMsrg);
        });

    });
});
$('#cttype').change(function() {
    if ($(this).val()) { // 如果一级有值，再请求
        getSubDictSelectByParentCode(this.value,'ctnexttype');
    }
});
</script>



</body>
</html>