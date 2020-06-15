<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<% 
	String seqId=request.getParameter( "seqId"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>修改模板</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>

<body>
<form class="container" class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>">
	<input type="hidden" class="form-control" name="seqId" id="seqId" value="<%=seqId%>">
	<table class="tableLayout">
		<tr>
			<td>
				<span class="commonText">短信名称<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="smsname" id="smsname" placeholder="短信名称">
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<span class="commonText">短信分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select class="dict" tig="dxfl" name="smstype" id="smstype" ></select>
				</div>
			</td>
			<td>
				<span class="commonText">二级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="smsnexttype" id="smsnexttype" >
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<span class="commonText">自动发送模板</span>
				
			</td>
			<td>
				<div class="form-group">
					<select name="isdsmodel" id="isdsmodel" >
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</td>
			<td>
				<span class="commonText">排序号</span>
			</td>
			<td>
				<div class="form-group">
					<input type="text" name="sortno" id="sortno" placeholder="请输入数字">
				</div>
			</td>
		</tr>
		<tr><td colspan="4"><span style="color: red;">注：同一分类存在多个自动发送模板时，按照排序号顺序默认发送</span></td></tr>
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">内容</span>
				
			</td>
			<td colspan="3">
				<div class="form-group">
					<textarea rows="7" class="longTextarea" name="smscontent" id="smscontent"></textarea>
				</div>
			</td>
		</tr>
	</table>
	<div class="btnGroup">
		<button id="save" class="kqdsCommonBtn">提交</button>
	</div>
</form>

<script>
var seqId = "<%=seqId%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
	 initDictSelectByClass();
	 var url = 'KQDS_Sms_ModelAct/selectDetail.act';
     var pageParam = {
     		seqId: seqId
         };
     var returnData = getDataFromServer(url, pageParam);
     if (returnData.retState == 0) {
    	 var detaildata = returnData.data;
    	 $("#smsname").val(detaildata.smsname);
    	 $("#smstype").val(detaildata.smstype).trigger("change");
    	 $("#smsnexttype").val(detaildata.smsnexttype);
    	 $("#sortno").val(detaildata.sortno);
    	 $("#isdsmodel").val(detaildata.isdsmodel);
    	 $("#smscontent").val(detaildata.smscontent);
     } else {
         layer.alert('查询失败！', {
               
         });
     }
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },    
        fields: {
        	smstype: {
                validators: {
                    notEmpty: {
                        message: '短信分类不能为空'
                    }
                }
            },
            smsnexttype: {
                validators: {
                    notEmpty: {
                        message: '二级分类不能为空'
                    }
                }
            },
            smsname: {
                validators: {
                    notEmpty: {
                        message: '短信名称不能为空'
                    }
                }
            },
            smscontent: {
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
        var url = '<%=contextPath%>/KQDS_Sms_ModelAct/insertOrUpdate.act?'+param;
        var msg;
         $.axseSubmit(url,null, 
				  function(){
			 			 msg=layer.msg('加载中', {icon: 16});
    	  		  },
	              function(r){
					  	layer.close(msg);
				        if(r.retState=="0"){
				        	layer.alert('保存成功', {
								  
								end: function () {
									parent.refresh();
						        	parent.layer.close(frameindex); //再执行关闭 
								}
							});
				        }else{
				        	layer.alert('保存失败' );
				        }     
	              },
	              function(){
	            	  layer.alert('保存失败' );
	          	  }
	        );  
    
    });
});
$('#smstype').change(function() {
	getSubDictSelectByParentCode(this.value,'smsnexttype');
});
</script>
</body>
</html>
