<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String seqId=request.getParameter( "seqId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>编辑计划</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
</head>
<body>
<!--回访结果弹窗-->
<div class="container extraExtenDetail"><!-- .extraExtenDetail 推广详情页面特别添加的样式 -->
    <table id="table">
		<tbody>
			<tr>
				<td>
					<span class="information">患者编号</span>
				</td>
				<td>
					<input type="text" id="usercode" name="usercode" readonly>
				</td>
				<td>
					<span class="information">患者姓名</span>
				</td>
				<td>
					<input type="text" id="username" name="username" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">患者性别</span>
				</td>
				<td>
					<input type="text" id="sex" name="sex" readonly>
				</td>
				<td>
					<span class="information">患者年龄</span>
				</td>
				<td>
					<input type="text" id="age" name="age" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">患者手机</span>
				</td>
				<td>
					<input type="text" id="phonenumber1" name="phonenumber1" readonly>
				</td>
				<td>
					<span class="information">会员</span>
				</td>
				<td>
					<input type="text" id="memberno" name="memberno" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">回访人</span>
				</td>
				<td>
					<input type="text" id="visitor" name="visitor" readonly>
				</td>
				<td>
					<span class="information">截止时间</span>
				</td>
				<td>
					 <input type="text" id="visittime" name="visittime" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">完成状态</span>
				</td>
				<td>
					<input type="text" id="status" name="status" readonly>
				</td>
				<td>
					<span class="information">完成时间</span>
				</td>
				<td>
					 <input type="text" id="finishtime" name="finishtime" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">计划名称</span>
				</td>
				<td>
					<input type="text" id="jhname" name="jhname" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">创建人</span>
				</td>
				<td>
					<input type="text" id="createuser" name="createuser" readonly>
				</td>
				<td>
					<span class="information">创建时间</span>
				</td>
				<td>
					 <input type="text" id="createtime" name="createtime" readonly>
				</td>
			</tr>
			<tr class="tr_Hf"><!--tr_Hf该行的行高调整 -->
				<td>
					<span class="information" style="color:#00A6C0;">计划备注</span>
				</td>
				<td colspan="5">
					<textarea rows="3" name="remark" id="remark" placeholder=""></textarea>
				</td>
			</tr>
			<tr class="tr_Hf"><!--tr_Hf该行的行高调整 -->
				<td>
					<span class="information">患者备注</span>
				</td>
				<td colspan="5">
					<textarea rows="3" name="remark" id="remark" placeholder="" readonly></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="text-align: center;">
					<a class="kqdsSearchBtn" onclick="submitu()" id="tijiao">提交</a>
				</td>
			</tr>
		</tbody>
    </table>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var seqId = '<%=seqId%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function () {
	initDictSelectByClass(); // 回访分类、满意度
	//提交人默认为当前用户
	var detailurl = '<%=contextPath%>/KQDS_ExtensionAct/selectDetail.act?seqId='+seqId;
	$.axse(detailurl,null, 
        function(data){
	 		loadData(data.data[0]);
	 		$("#remark").val(data.data[0].extenremark);
		},
		function(){
			layer.alert('查询出错！' );
		}
	);
});

//提交
function submitu(){
	//验证
	var remark = $("#remark").val();
	var a={
		seqId:seqId,
		remark : remark
	};
	 var url = '<%=contextPath%>/KQDS_ExtensionAct/editTgjh.act';
     $.axseSubmit(url,a, 
		function(){
			 layer.msg('加载中', {icon: 16});
	  	},
       function(r){
   	        if(r.retState=="0"){
   	        	layer.alert('编辑成功', {  end:function(){
   	        		parent.refresh();
   	        		var index = parent.layer.index; //获取当前弹层的索引号
   	        		parent.layer.close(index); //关闭当前弹层
   	        	}});
   	        }else{
   	        	layer.alert('编辑失败' );
   	        }     
           },
           function(){
         	    layer.alert('编辑失败' );
		}
	); 
}
</script>
</html>
