<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增收费项目</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>

<body>
<form class="container" class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
	<table class="tableLayout">
		<tr>
			<td>
				<span class="commonText">项目编号<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<input type="text"  name="treatitemno" id="treatitemno" placeholder="项目编号">
				</div>
			</td>
			<td>
				<span class="commonText">项目名称<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<input type="text"  name="treatitemname" id="treatitemname" placeholder="项目名称">
				</div>
			</td>
		</tr>
		
		<tr>
			<td>
				<span class="commonText">一级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="basetype" id="basetype" >
					</select>
				</div>
			</td>
			<td>
				<span class="commonText">二级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="nexttype" id="nexttype" >
					</select>
				</div>
			</td>
		</tr>
		
		<tr>
			<td>
				<span class="commonText">单位<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="unit" id="unit" placeholder="单位">
				</div>
			</td>
			<td>
				<span class="commonText">单价<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="unitprice" id="unitprice" placeholder="单价">
				</div>
			</td>
		</tr>	
		
		<tr>
			<td>
				<span class="commonText">折扣<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<input type="text"  name="discount" id="discount" placeholder="折扣">
				</div>	
			</td>
			<td>
				<span class="commonText">特殊项目<span style="color: red;">*</span></span>
			</td>
			<td>
				<div class="form-group">
					<select name="istsxm" id="istsxm" style="width: 100%;">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</td>
		</tr>	
		
		<tr>
			<td>
				<span class="commonText">领料拆分<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<select name="issplit" id="issplit" style="width: 100%;">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
			<td>
				<span class="commonText">项目分类</span>
			</td>
			<td>
				<div class="form-group">
					<select name="isyjjitem" id="isyjjitem" style="width: 100%;">
						<option value="0">请选择</option>
						<option value="1">预交金</option>
						<option value="2">挂号</option>
						<option value="3">治疗费</option>
						<option value="4">生成回访</option>
					</select>
				</div>
			</td>
		</tr>
		
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">项目介绍</span>
				
			</td>
			<td colspan="3">
				<textarea rows="2" class="longTextarea" name="xmjs" id="xmjs"></textarea>
			</td>
		</tr>
		
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">优惠信息</span>
				
			</td>
			<td colspan="3">
				<textarea rows="2" class="longTextarea" name="yhxx" id="yhxx" ></textarea>
			</td>
		</tr>
	</table>
	<div class="btnGroup">
		<button id="save" class="kqdsCommonBtn">提交</button>
	</div>
</form>

<script>
var organization = '<%=organization %>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    initCostSortSelect1LevelOrg('basetype', organization);

    var autoCodeObj = getDataFromServer('KQDS_TreatItemBackAct/getAutoCode4Add.act?');
    //alert(JSON.stringify(autoCodeObj));
    //console.log(autoCodeObj);
    if (autoCodeObj) {
        if (autoCodeObj.autoCode) {
            $("#treatitemno").val(autoCodeObj.autoCode);
        }
    }

    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            treatitemno: {
                message: '项目编号验证失败',
                validators: {
                    notEmpty: {
                        message: '项目编号不能为空'
                    },
                    remote: {
                        message: '项目编号已存在，请更换',
                        url: '<%=contextPath%>/KQDS_TreatItemBackAct/yzByTreatItemNo.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                treatitemno: $('[name="treatitemno"]').val()
                            };
                        }
                    }
                }
            },
            treatitemname: {
                validators: {
                    notEmpty: {
                        message: '项目名称不能为空'
                    }
                }
            },
            basetype: {
                validators: {
                    notEmpty: {
                        message: '一级分类不能为空'
                    }
                }
            },
            nexttype: {
                validators: {
                    notEmpty: {
                        message: '二级分类不能为空'
                    }
                }
            },
            unit: {
                validators: {
                    notEmpty: {
                        message: '单位不能为空'
                    }
                }
            },
            unitprice: {
                validators: {
                    notEmpty: {
                        message: '单价不能为空'
                    },
                    regexp: {
                        /* regexp: /^(\d*\.)?\d+$/, */
                        regexp:	/^(\-|\+)?\d+(\.\d+)?$/,
                        message: '单价格式不正确'
                    }

                }
            },
            discount: {
                validators: {
                    notEmpty: {
                        message: '折扣不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+\.{0,1}[0-9]{0,2}$/,
                        message: '折扣格式不正确'
                    },
                    between:{
                    	message: '折扣值在0~100之间',
                    	min:0,
                    	max:100
                    }

                }
            },
            istsxm: {
                validators: {
                    notEmpty: {
                        message: '请选择是否为特殊项目'
                    }

                }
            }

        }

    }).on('success.form.bv',
    function(e) {
        e.preventDefault();
        var discount = $("#discount").val();
        if(Number(discount) > 100){
        	layer.alert('折扣不能大于100', {});
        	return false;
        }
        
        var isyjjitem = $("#isyjjitem").val();
        if("3" == isyjjitem){ /** 治疗费，单价必须为100 **/
        	if(Number(discount) != 100){
            	layer.alert('治疗费折扣必须为100', {});
            	return false;
            }
        }
        
        var $form = $(e.target);
        var param = $('#form1').serialize();
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/KQDS_TreatItemBackAct/insertOrUpdate.act?' + param;
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

    $('#basetype').change(function() {
        if ($(this).val()) { // 如果一级有值，再请求
            initCostSortSelect2LevelOrg('nexttype', this.value, organization);
        }
    });
});
</script>



</body>
</html>
