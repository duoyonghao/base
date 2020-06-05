<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增赠送项目</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
</head>
<body>
<div class="giveItemContainer">
	<form id="form1">
		<input type="hidden" class="form-control" name="itemno" id="itemno">
		<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">项目名称</span>
					
				</td>
				<td>
					<div class="form-group">
						<input class="whiteInp" type="text" name="itemname" id="itemname" placeholder="" onclick="getsfitem()">
					</div>
				</td>
				<td>
					<span class="commonText">排序号</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="sortno" id="sortno" placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">单位</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="unit" id="unit" placeholder="" readonly>
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
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var itemno = "";
var itemname = "";
var unit = "";
var unitprice = "";
$(function() {
	formValidate();
});
function formValidate() {
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            itemname: {
                validators: {
                    notEmpty: {
                        message: '请选择收费项目'
                    }
                }
            },
            sortno: {
                validators: {
                    notEmpty: {
                        message: '请填写排序号'
                    }
                }
            },
        }
    });
}

$('#itemname').on('change',
function() {
    var bootstrapValidator = $("#form1").data('bootstrapValidator');

    if (bootstrapValidator.options.fields.itemname) {
        bootstrapValidator.updateStatus('itemname', 'NOT_VALIDATED').validateField('itemname'); //错误提示信息变了  
    }
});

$('#save').on('click',
function() {
    var flag = formSubmitValidate('form1'); //submit()校验方法
    if (!flag) {
        return false;
    }

    var param = $('#form1').serialize();
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
            layer.alert('添加成功'  ,
            function(index) {
                parent.refresh();
                parent.layer.close(frameindex); //再执行关闭 */
            });
        } else {
            layer.alert('添加失败：' + r.retMsrg  );
        }
    },
    function() {
        layer.alert('添加失败'  );
    });
});

// 选择收费项目
function getsfitem() {
    layer.open({
        type: 2,
        title: '选择收费项目',
        shadeClose: true,
        shade: 0.6,
        area: ['666px', '80%'],
        content: contextPath + '/KQDS_Give_ItemBackAct/toGetsfitem.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        //iframe的url
        end: function(index, layero) {
            $("#itemno").val(itemno);
            $("#itemname").val(itemname);
            $("#itemname").trigger('change');
            $("#unit").val(unit);
            $("#unitprice").val(unitprice);
        },
    });
}
</script>

</body>
</html>
