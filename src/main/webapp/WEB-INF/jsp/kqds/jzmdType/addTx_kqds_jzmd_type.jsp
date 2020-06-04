<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String fid = request.getParameter("fid");
	String cid = request.getParameter("cid");
    String seqId = request.getParameter("seqId");
    if(seqId == null){
    	seqId = "";
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>修改就诊目的</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
</head>
<body>
<div class="jzmdContainer">
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">就诊目的</span> 
				</td>
				<td>
					<div class="form-group">
						<select class="select2 dict" tig="JZMD" name="jzmd" id="jzmd" disabled="disabled">
						</select>
					</div>
				</td>
				<td>
					<span class="commonText">下级就诊目的</span>
					
				</td>
				<td>
					<div class="form-group">
						<select  name="jzmdchildname" id="jzmdchildname" disabled="disabled"></select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">提醒就诊</span>
					
				</td>
				<td>
					<div class="form-group">
						<select class="patients-source" name="txjzmd" id="txjzmd"></select>
					</div>
				</td>
				<td>
					<span class="commonText">有效天数</span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="yxday" id="yxday" placeholder="有效天数">
						<input type="hidden" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrl(request)%>">
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
var fid = "<%=fid%>";
var cid = "<%=cid%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    if(seqId != ""){
    	getDetail();  /** 下拉框的初始化放到getDetail方法中执行了 **/
    }else{
    	initDictSelectByClassOrg('<%=ChainUtil.getOrganizationFromUrl(request)%>');
    }
    
    $("#jzmd").val(fid).trigger("change");
    $("#jzmdchildname").val(cid);
    
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            jzmd: {
                validators: {
                    notEmpty: {
                        message: '请选择就诊目的父级分类'
                    }
                }
            },
            jzmdchildname: {
                validators: {
                    notEmpty: {
                        message: '请填写就诊目的子分类名称'
                    }
                }
            },
            yxday: {
                validators: {
                    numeric: {
                        message: '天数只能为数字'
                    }
                }
            }
        }

    }).on('success.form.bv',
    function(e) {
    	$("#jzmd").removeAttr("disabled");
        $("#jzmdchildname").removeAttr("disabled");
        e.preventDefault();
        var $form = $(e.target);
        var param = $('#form1').serialize();
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/KQDS_Jzmd_TypeBackAct/insertOrUpdate.act?' + param;
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
$('#jzmd').change(function() {
	getSubDictSelectByParentCodeNoOrg(this.value,'jzmdchildname');
	getSubDictSelectByParentCodeNoOrg(this.value,'txjzmd');
});
function getDetail(){
	var detailurl = '<%=contextPath%>/KQDS_Jzmd_TypeBackAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
    	initDictSelectByClassOrg(data.data.organization); // 传入门诊编号
    	
        loadData(data.data);
    },
    function() {
        layer.alert('查询出错！' );
    });
}
</script>
</body>
</html>
