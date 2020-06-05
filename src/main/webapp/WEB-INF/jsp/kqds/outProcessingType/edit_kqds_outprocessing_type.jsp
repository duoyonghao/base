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
<script type="text/Javascript" src="<%=contextPath%>//static/js/kqds/outProcessingType/outprocessingtype.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
<title>编辑加工项目分类</title>
</head>
<body>
<div class="addTypeContainer">
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">分类名称</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text"  name="typename" id="typename" placeholder="分类名称">
						<input type="hidden"  name="organization" id="organization" value="<%=ChainUtil.getOrganizationFromUrlCanNull(request) %>">
					</div>
				</td>
				<td>
					<span class="commonText">分类编号</span>
					
				</td>
				<td>
					<div class="form-group">
						<input readonly type="text" name="typeno" id="typeno" placeholder="分类编号">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">加工厂</span>
					
				</td>
				<td>
					<div class="form-group">
						<select name="processingfactory" id="processingfactory"></select>
					</div>
				</td>
				<td>
					<span class="commonText">启用/禁用</span>
					
				</td>
				<td>
					<div class="form-group">
						<select name="useflag" id="useflag">
							<option value="0" selected="selected">启用</option>
							<option value="1">禁用</option>
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
var seqId ="<%=seqId%>";
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
	initFactorySelect4Back("processingfactory",'','<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>');
	var detailurl = '<%=contextPath%>/KQDS_OutProcessing_TypeBackAct/selectDetail.act?seqId='+seqId;
	$.axse(detailurl,null, 
		function(data){
			loadData(data.data);
		},
	    function(){
   	    	layer.alert('查询出错！' );
 	    }
    );
    $("#form1").bootstrapValidator({
        message: '表单不合法',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },    
        fields: {
        	typename: {
                validators: {
                    notEmpty: {
                        message: '分类名称不能为空'
                    }
                }
            }, 
            typeno: {
                message: '分类编号验证失败',
                validators: {
                    notEmpty: {
                        message: '分类编号不能为空'
                    },
                    remote:{
                    	message: '分类编号已存在，请重新填写',
                        url : '<%=contextPath%>/KQDS_OutProcessing_TypeBackAct/yzTypeNo.act',
                        type: 'POST',//请求方式
                        data: function(validator) {
                            return {
                            	typeno: $('[name="typeno"]').val(),
                            	seqId:seqId
                            };
                         }
                    }
                }
            },
            processingfactory: {
                validators: {
                    notEmpty: {
                        message: '加工厂不能为空'
                    }
                   
                }
            }
        }
      
    }).on('success.form.bv', function(e) {
        var param = $('#form1').serialize();
        var url = '<%=contextPath%>/KQDS_OutProcessing_TypeBackAct/insertOrUpdate.act?'+param;
		$.axseSubmit(url,null,function(){},
              function(r){
			        if(r.retState=="0"){
			        	layer.alert('修改成功' );
			        	parent.refresh();
			        	parent.layer.close(frameindex); //再执行关闭 
			        }else{
			        	layer.alert('修改失败' + r.retMsrg);
			        }     
              },
              function(){
            	  layer.alert('修改失败' );
          	  }
       	);  
    });
});
</script>
</body>
</html>
