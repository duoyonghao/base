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
    <title>回访详情</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
</head>
<body>
<!--回访结果弹窗-->
<div class="container">
    <table id="table">
		<tbody>
			<tr>
				<td>
					<span class="information">病人编号</span>
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
					<span class="information">患者手机</span>
				</td>
				<td>
					<input type="text" id="telphone" name="telphone" readonly>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">回访时间</span>
				</td>
				<td>
					 <input type="text" id="visittime" name="visittime" placeholder="请选择时间" readonly>
				</td>
				<td>
					<span class="information">回访分类</span>
				</td>
				<td><!-- class="dict select2"绑定了回访分类的加载 -->
					<select class="dict select2" tig="HFFL" name="hffl" id="hffl"></select>
				</td>
			</tr>
			<tr>
				<td>
					<span class="information">回访人员</span>
				</td>
				<td>
					<input type="hidden" name="visitor" id="visitor" placeholder="回访人员" title="回访人员" value=""/>
					<input type="text" id="visitorDesc" name="visitorDesc" placeholder="回访人员" readonly ></input>	
				</td>
			</tr>
			<tr class="tr_Hf"><!--tr_Hf该行的行高调整 -->
				<td>
					<span class="information">回访要点</span>
				</td>
				<td colspan="5">
					<textarea rows="3" name="hfyd" id="hfyd" placeholder="回访要点" readonly></textarea>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="information">满意度</span>
				</td>
				<td><!-- class="dict select2"绑定了回访分类的加载 -->
					<select class="select2 dict" tig="hfmyd" name="myd" id="myd"></select>
				</td>
				<td>
					<span class="information">提交人员</span>
				</td>
				<td><!-- class="dict select2"绑定了回访分类的加载 -->
					<input type="hidden" name="postperson" id="postperson" placeholder="提交人员" title="提交人员" value=""/>
					<input type="text" id="postpersonDesc" name="postpersonDesc" placeholder="提交人员" readonly >
				</td>
			</tr>
			<tr class="tr_Hf"><!--tr_Hf该行的行高调整 -->
				<td>
					<span class="information">回访结果</span>
				</td>
				<td colspan="5">
					<textarea rows="3" name="hfresult" id="hfresult" placeholder="" ></textarea>
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
	var detailurl = '<%=contextPath%>/KQDS_VisitAct/selectDetail.act?seqId='+seqId;
	$.axse(detailurl,null, 
        function(data){
	 		loadData(data.data);
	 		if(!data.data.postperson == "" && data.data.postperson != null){
		 		bindPerUserNameBySeqIdTB("postpersonDesc",document.getElementById("postperson").value);
	 		}
	 		if(!data.data.visitor == "" && data.data.visitor != null){
		 		bindPerUserNameBySeqIdTB("visitorDesc",document.getElementById("visitor").value);
	 		}
		},
		function(){
	       	  layer.alert('查询出错！' );
		}
	);
	 readonly();
});
</script>
</html>
