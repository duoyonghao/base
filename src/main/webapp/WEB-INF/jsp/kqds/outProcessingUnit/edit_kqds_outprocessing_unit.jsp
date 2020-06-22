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
<title>修改加工厂</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
</head>
<body>
<div class="outprocessingContainer"><!--加工厂的容器  -->
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">加工厂编码</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="code" id="code" readonly placeholder="">
						<input type="hidden"  name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request) %>">
					</div>
				</td>
				<td>
					<span class="commonText">名称</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="name" id="name" placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">地址</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="address" id="address" placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">联系电话</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="phone" id="phone" placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">备注</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="remark" id="remark" placeholder="">
					</div>
				</td>
				<td>
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
		<div class="btnGroup">
			<button id="save" class="kqdsCommonBtn">提交</button>
		</div>
	</form>
</div>
<script>
var seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    var detailurl = '<%=contextPath%>/KQDS_OutProcessing_UnitBackAct/selectDetail.act?seqId=' + seqId;
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
        	code: {
                message: '请填写加工厂编码',
                validators: {
                    notEmpty: {
                        message: '请填写加工厂编码'
                    },
                    remote: {
                        message: '加工厂编码已存在',
                        url: contextPath + '/KQDS_OutProcessing_UnitBackAct/checkCode.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                            	code: $('[name="code"]').val(),
                            	seqId: $('[name="seqId"]').val()
                            };
                        }
                    }
                }
            },
            name: {
                validators: {
                    notEmpty: {
                        message: '请填写加工厂名称'
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
        var url = '<%=contextPath%>/KQDS_OutProcessing_UnitBackAct/insertOrUpdate.act?' + param;
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
                layer.alert(r.retMsrg, {
                      
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
