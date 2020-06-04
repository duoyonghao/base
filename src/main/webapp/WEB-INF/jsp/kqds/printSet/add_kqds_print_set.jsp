<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增打印设置</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>
<body>
<div class="printContainer"><!--排班的容器  -->
	<form id="form1">
		<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">排序号<span style="color: red;">*</span></span>
				</td>
				<td>
					<div class="form-group">
						<!--.whiteInp 白底鼠标移入有小手的效果  -->
						<input type="text" name="sortno" id="sortno" placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">打印名称<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<select id="printname" name="printname">
							<option value="">请选择</option>
							<option value="费用确认单">费用确认单</option>
							<option value="项目退费确认单">项目退费确认单</option>
							<option value="会员充值单">会员充值单</option>
							<option value="会员卡退费确认单">会员卡退费确认单</option>
							<option value="加工单">加工单</option>
							<option value="打印病历">病历单</option>
							<option value="商品入库单">商品入库单</option>
							<option value="商品出库单">商品出库单</option>
							<option value="使用赠送单">使用赠送单</option>
							<option value="转赠单">转赠单</option>
							<option value="药品入库单">药品入库单</option>
							<option value="药品出库单">药品出库单</option>
							<option value="领料出库单">领料出库单</option>
							<option value="领料明细单">领料明细单</option>
						</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="commonText">打印类型<span style="color: red;">*</span></span>
				</td>
				<td>
					<div class="form-group">
						<select id="printtype" name="printtype">
							<option value="">请选择</option>
							<option value="1">A4</option>
							<option value="2">A5</option>
						</select>
					</div>
				</td>
				
				<td>
					<span class="commonText">打印地址<span style="color: red;">*</span></span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="printurl" id="printurl" placeholder="">
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
$(function () {
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },    
        fields: {
            sortno: {
                validators: {
                    notEmpty: {
                        message: '请填写排序号'
                    }
                }
            },
        	printname: {
                validators: {
                    notEmpty: {
                        message: '请填写打印名称'
                    }
                }
            },
            printtype: {
                validators: {
                    notEmpty: {
                        message: '请选择打印类型'
                    }
                }
            },
            printurl: {
                validators: {
                    notEmpty: {
                        message: '请填写打印地址'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var param = $('#form1').serialize();
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/KQDS_Print_SetBackAct/insertOrUpdate.act?'+param;
         $.axseSubmit(url,null, 
			  function(){
   	  		  },
              function(r){
			        if(r.retState=="0"){
			        	layer.alert('添加成功', {
						},function(index){
							parent.refresh();
							parent.layer.close(frameindex); //再执行关闭 */
						});
			        }else{
			        	layer.alert('添加失败' );
			        }     
              },
              function(){
            	  layer.alert('添加失败' );
          	  }
        );  
    
    });
});

</script>



</body>
</html>
