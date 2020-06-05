<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String reqUri = request.getRequestURI();
%>
<table style="position:relative;">
<th class="highTh" colspan="8">
<%
		if(!reqUri.contains("_detail.jsp")){
			%>
		<a onclick="blk_select('5')" href="javascript:void(0);" class="solidBtn">病案模板选取</a>
			<%
		}
	%>
种植修复
 </th>
<tr>
	<td>修复类型：</td>
	<td colspan="7"><input type="text"  name="restorationType" ></td>
	</td>
</tr>

<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">冠   桥：</td>
	<td><input type="text"  name="bridge_tmp"></td>
	<td><input type="text"  name="bridge_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="bridge_tmp" rows="3" ></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('bridge_tmp',this);"> 
				<%
 					}
 				%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="bridge_tmp"></td>
	<td><input type="text"  name="bridge_tmp"></td>
</tr><!--  tr end... -->


<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">覆盖义齿：</td>
	<td><input type="text"  name="overdenture_tmp"></td>
	<td><input type="text"  name="overdenture_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="overdenture_tmp" rows="3" ></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('overdenture_tmp',this);"> 
				<%
 					}
 				%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="overdenture_tmp"></td>
	<td><input type="text"  name="overdenture_tmp"></td>
</tr><!--  tr end... -->

<tr>
	<td>固  位：</td>
	<td colspan="7"><input type="text"  name="retention" ></td>
	</td>
</tr>


<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">粘  结：</td>
	<td><input type="text"  name="cemented_tmp"></td>
	<td><input type="text"  name="cemented_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="cemented_tmp" rows="3" ></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('cemented_tmp',this);"> 
				<%
 					}
 				%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="cemented_tmp"></td>
	<td><input type="text"  name="cemented_tmp"></td>
</tr><!--  tr end... -->

<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">螺  丝：</td>
	<td><input type="text"  name="screwed_tmp"></td>
	<td><input type="text"  name="screwed_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="screwed_tmp" rows="3"></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('screwed_tmp',this);"> 
				<%
			}
		%>
	</td>
</tr>

<tr>
	<td><input type="text"  name="screwed_tmp"></td>
	<td><input type="text"  name="screwed_tmp"></td>
</tr><!--  tr end... -->

<tr>
	<td>就诊记录：</td>
	<td colspan="7">
	<textarea rows="3"  name="appointmentRecord" ">
	</textarea>
	</td>
</tr>


<tr>
	<td>备注：</td>
	<td colspan="7">
	<textarea rows="3"  name="remark" ">
	</textarea>
	</td>
</tr>

<tr>
	<td>修复体编号（保修卡）</td>
	<td colspan="7">
	<textarea rows="3"  name="restorationNumber">
	</textarea>
	</td>
</tr>


</table>