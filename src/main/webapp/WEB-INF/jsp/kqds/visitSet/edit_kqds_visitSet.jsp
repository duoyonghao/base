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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
<title>修改回访设置</title>
</head>
<body>
<div class="outprocessingContainer">
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">回访分类</span>
					
				</td>
				<td>
					<div class="form-group">
						<select class="select2 dict" tig="HFFL" name="hffl" id="hffl" >
						</select>
					</div>
				</td>
				<td>
					<span class="commonText">回访角色</span>
					
				</td>
				<td>
					<div class="form-group">
						<select class="select2 priv" name="userpriv" id="userpriv" org="<%=ChainUtil.getOrganizationFromUrl(request)%>">
						</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">回访目的</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="purpose" id="purpose" placeholder="回访目的">
					</div>
				</td>
				<td>
					<span class="commonText">回访备注</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="remark" id="remark" placeholder="回访备注">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">间隔天数</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="jgday" id="jgday" placeholder="间隔天数">
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
    intPrivSelectByClassWithCommon();
    var detailurl = '<%=contextPath%>/KQDS_visitSetAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
    	initDictSelectByClassOrg(data.data.organization);
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
        	hffl: {
                validators: {
                    notEmpty: {
                        message: '请选择回访分类'
                    }
                }
            },
            purpose: {
                validators: {
                    notEmpty: {
                        message: '请填写回访目的'
                    }
                }
            },
            remark: {
                validators: {
                    notEmpty: {
                        message: '请填写回访备注'
                    }
                }
            },
            jgday: {
                validators: {
                	notEmpty: {
                        message: '请填写间隔天数'
                    },
                    numeric: {
                        message: '间隔天数只能为数字'
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
        var url = '<%=contextPath%>/KQDS_visitSetAct/insertOrUpdate.act?' + param;
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
