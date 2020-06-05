<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String reqUri = request.getRequestURI();
%>
<script type="text/javascript">
$(function() {
	
});
</script>
<table>
<th style="position:relative;" class="highTh" colspan="8">
		<%
			if(!reqUri.contains("_detail.jsp")){
				%>
				<a onclick="blk_select('2')" href="javascript:void(0);" class="solidBtn">病案模板选取</a>
				<%
			}
		%>
	种植专科病人个人资料登记
</th>
<tr>
	<td >患者姓名：</td>
	<td id="username"></td>
	<td>性别：</td>
	<td id="sex"></td>
	<td>年龄：</td>
	<td id="age"></td>
	<td>病历号：</td>
	<td id="meid"></td>
</tr>
<tr>
	<td>家庭住址：</td>
	<td colspan="7" id="adress"></td>
</tr>
<tr>
	<td>电话：</td>
	<td colspan="3" id="telephone"></td>
	<td>Email：</td>
	<td colspan="3" id="email"></td>
</tr>
<tr>
	<td>药物过敏：</td>
	<td colspan="7">
		<textarea rows="1" cols="" style="width: 100%;"  id="allergy" name="allergy"></textarea>
	</td>
</tr>
</table>
