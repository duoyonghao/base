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
<title>收费项目详情</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/treatItem/treatitem.css" />	
</head>
<body>
<form class="container" class="form-horizontal" id="form1">
	<input type="hidden" class="form-control" name="seqId" id="seqId">
	<table class="tableLayout">
		<tr>
			<td>
				<span class="commonText">项目编号<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div>
					<input type="text"  name="treatitemno" id="treatitemno" placeholder="项目编号">
				</div>
			</td>
			<td>
				<span class="commonText">项目名称<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div>
					<input type="text"  name="treatitemname" id="treatitemname" placeholder="项目名称">
				</div>
			</td>
		</tr>
		
		<tr>
			<td>
				<span class="commonText">一级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div>
					<select name="basetype" id="basetype" >
					</select>
				</div>
			</td>
			<td>
				<span class="commonText">二级分类<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div>
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
				<div>
					<input type="text" name="unit" id="unit" placeholder="单位">
				</div>
			</td>
			<td>
				<span class="commonText">单价<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div>
					<input type="text" name="unitprice" id="unitprice" placeholder="单价">
				</div>
			</td>
		</tr>	
		
		<tr>
			<td>
				<span class="commonText">折扣<span style="color: red;">*</span></span>
				
			</td>
			<td>
				<div>
					<input type="text" name="discount" id="discount" placeholder="折扣">
				</div>	
			</td>
			<td>
				<span class="commonText">特殊项目<span style="color: red;">*</span></span>
			</td>
			<td>
				<div>
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
				<textarea rows="2" class="longTextarea" name="xmjs" id="xmjs" placeholder=""></textarea>
			</td>
		</tr>
		
		<tr class="textareaTr">
			<td>
				<span class="commonText longInp">优惠信息</span>
				
			</td>
			<td colspan="3">
				<textarea rows="2" class="longTextarea" name="yhxx" id="yhxx" placeholder=""></textarea>
			</td>
		</tr>
	</table>
</form>
<script>
var seqId ="<%=seqId%>";
var organization = '<%=organization %>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
	initCostSortSelect1LevelOrg('basetype', organization);

    $('#basetype').change(function() {
        if ($(this).val()) { // 如果一级有值，再请求
            initCostSortSelect2LevelOrg('nexttype', this.value, organization);
        }
    });
	  var detailurl = '<%=contextPath%>/KQDS_TreatItemBackAct/selectDetail.act?seqId='+seqId;
	  $.axse(detailurl,null, 
              function(data){
				   var nexttype = data.data.nexttype;
		           var basetype = data.data.basetype;
		           $('#basetype').val(basetype);
		           $('#basetype').trigger('change');
		           $('#nexttype').val(nexttype);
		  		   loadData(data.data);
              },
              function(){
            	  layer.alert('查询出错！' );
          	  }
        );
     readonly();
});
</script>
</body>
</html>
