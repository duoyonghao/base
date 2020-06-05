<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String reqUri = request.getRequestURI();
%>

<table style="position:relative;">
<th class="highTh" colspan="8">
	<%
		if(!reqUri.contains("_detail.jsp")){
			%>
			<a onclick="blk_select('3')" href="javascript:void(0);" class="solidBtn">病案模板选取</a>
			<%
		}
	%>
	
	术后拆线
</th>
<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">种植体情况：</td>
	<td><input type="text"  name="conditionImplants_tmp"></td>
	<td><input type="text"  name="conditionImplants_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="conditionImplants_tmp" rows="3" ></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('conditionImplants_tmp',this);"> 
				<%
			}
		%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="conditionImplants_tmp"></td>
	<td><input type="text"  name="conditionImplants_tmp"></td>
</tr><!--  tr end... -->

<tr>
	<td>软组织愈合情况：</td>
	<td colspan="7"><input type="text"  name="healingTissue" ></td>
</tr>

<tr>
	<td>备注：</td>
	<td colspan="7">
	<textarea rows="5"  name="remark" ></textarea>
	</td>
</tr>

</table>