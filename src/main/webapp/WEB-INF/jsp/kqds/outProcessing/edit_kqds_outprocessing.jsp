<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String seqId = request.getParameter("seqId");
	String organization = request.getParameter("organization");
	if (organization == null) {
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqds/outProcessingType/outprocessingtype.js"></script>
<title>编辑加工项目</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />
</head>
<body>
<div class="outprocessingContainer"><!--加工厂的容器  -->
	<form id="form1">
		<input type="hidden" class="form-control" name="seqId" id="seqId">
		<table class="tableLayout">
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">加工厂<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<select name="mrjgc" id="mrjgc"> </select>
					</div>
				</td>
				<td>
					<span class="commonText">项目分类<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<select name="wjgfl" id="wjgfl"> </select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">项目编号<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="wjgxmbh" id="wjgxmbh" readonly placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">项目名称<span style="color: red;">*</span></span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="wjgmc" id="wjgmc" placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">单位</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="dw" id="dw" placeholder="">
					</div>
				</td>
				<td>
					<span class="commonText">单价</span>
					
				</td>
				<td>
					<div class="form-group">
						<input type="text" name="dj" id="dj" placeholder="">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">收费分类</span>
					
				</td>
				<td>
					<div class="form-group">
						<select name="basetype" id="basetype" >
						</select>
					</div>
				</td>
				<td>
					<span class="commonText">收费分类子类</span>
					
				</td>
				<td>
					<div class="form-group">
						<select name="nexttype" id="nexttype" >
						</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">收费项目</span>
					
				</td>
				<td>
					<div class="form-group">
						<select class="select2" name="dysfxm" id="dysfxm">
						</select>
					</div>
				</td>
			</tr>
			
			<tr class="textareaTr"><!-- .textareaTr 调整textarea所在行的行高-->
				<td>	<!--commonText信息字样的样式  -->
					<span class="commonText">备注</span>
					
				</td>
				<td colspan="3">
					<div class="form-group">
						<textarea class="longTextarea" name="remark" id="remark" rows="3" placeholder=""></textarea>
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
var seqId ="<%=seqId%>";
var organization = '<%=organization %>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
	initCostSortSelect1LevelOrg('basetype', organization);

    $('#basetype').change(function() {
        if ($(this).val()) { // 如果一级有值，再请求
            initCostSortSelect2LevelOrg('nexttype', this.value, organization);
        }
    });
    
	initFactorySelect4Back("mrjgc",'','<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>');
	var detailurl = '<%=contextPath%>/KQDS_OutProcessingBackAct/selectDetail.act?seqId='+seqId;
	$.axse(detailurl,null, 
		function(data){
			var nexttype = data.data.nexttype;
	        var basetype = data.data.basetype;
	        $('#basetype').val(basetype);
	        $('#basetype').trigger('change');
	        $('#nexttype').val(nexttype);
		
			initSfxmSelect('dysfxm',data.data.basetype,data.data.nexttype);
			getTypeList("wjgfl",data.data.mrjgc);
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
            wjgmc: {
                validators: {
                    notEmpty: {
                        message: '加工项目名称不能为空'
                    }
                }
            }, 
            dj: {
                validators: {
                    regexp: {
                    	regexp:/^(\d*\.)?\d+$/,
                        message: '单价格式不正确'
                    }
                }
            },
            mrjgc: {
                validators: {
                    notEmpty: {
                        message: '加工厂不能为空'
                    }
                   
                }
            },
            wjgfl: {
                validators: {
                    notEmpty: {
                        message: '项目分类不能为空'
                    }
                   
                }
            }
        }
      
    }).on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var param = $('#form1').serialize();
        var bv = $form.data('bootstrapValidator');
        var url = '<%=contextPath%>/KQDS_OutProcessingBackAct/insertOrUpdate.act?'+param;
        var msg;
		$.axseSubmit(url,null, 
			  function(){
		 			 msg=layer.msg('加载中', {icon: 16});
     	  		  },
              function(r){
				  	layer.close(msg);
			        if(r.retState=="0"){
			        	layer.alert('修改成功' );
			        	parent.refresh();
			        	parent.layer.close(frameindex); //再执行关闭 
			        }else{
			        	layer.alert('修改失败' );
			        }     
              },
              function(){
            	  layer.alert('修改失败' );
          	  }
       	);  
    });
});
//选择收费分类(父类后) 查询对应子分类的数据
$('#basetype').change(function(){ 
	if($(this).val()){ // 如果一级有值，再请求
		initCostSortSelect2Level('nexttype',this.value);
	}
});

$('#nexttype').change(function(){
	var basetypeval = $('#basetype').val();
	initSfxmSelect('dysfxm',basetypeval,this.value);
});
$('#mrjgc').change(function(){
	getTypeList("wjgfl",this.value);
});
</script>
</body>
</html>
