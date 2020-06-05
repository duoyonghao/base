<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>新增快捷回复</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
<style>
	.tableLayout{
		margin:0;
		width:460px;
	}
	.tableLayout tr.textareaTr {
	    height: 150px;
	}
</style>
</head>

<body>
<form class="container" class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
	<table class="tableLayout">
		<tr>
			<td>
				<span class="commonText">回复分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select class="dict" tig="WECHAT_KEYWORD" name="msgtype" id="msgtype" ></select>
				</div>
			</td>
		</tr>
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">内容</span>
			</td>
			<td>
				<div class="form-group">
					<textarea rows="7" class="longTextarea" name="msgcontent" id="msgcontent"></textarea>
				</div>
			</td>
		</tr>
	</table>
	<div class="btnGroup">
		<button id="save" class="kqdsCommonBtn">提交</button>
	</div>
</form>

<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
	 initDictSelectByClass();
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },    
        fields: {
        	msgtype: {
                validators: {
                    notEmpty: {
                        message: '回复分类不能为空'
                    }
                }
            },
            msgcontent: {
                validators: {
                    notEmpty: {
                        message: '内容不能为空'
                    }
                }
            }
        }
      
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var param = $('#form1').serialize();
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/WXQuickMsgAct/insertOrUpdate.act?'+param;
        var msg;
         $.axseSubmit(url,null, 
				  function(){
			 			 msg=layer.msg('加载中', {icon: 16});
    	  		  },
	              function(r){
					  	layer.close(msg);
				        if(r.retState=="0"){
				        	layer.alert('添加成功', {
								  
								end: function () {
									parent.refresh();
						        	parent.layer.close(frameindex); //再执行关闭 
								}
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
