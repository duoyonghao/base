<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String reqUri = request.getRequestURI();
%>
<table style="position:relative;">
<th colspan="8" class="highTh"  align="center">
<%
		if(!reqUri.contains("_detail.jsp")){
			%>
			<a onclick="blk_select('4')" href="javascript:void(0);" class="solidBtn">病案模板选取</a>
			<%
		}
	%>
种植Ⅱ期手术</th>
<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">术前检查：</td>
	<td><input type="text"  name="preoperativeExamination_tmp"></td>
	<td><input type="text"  name="preoperativeExamination_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="preoperativeExamination_tmp" rows="3"></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('preoperativeExamination_tmp',this);"> 
				<%
 					}
 				%>
		
	</td>
</tr>
<tr>
	<td><input type="text"  name="preoperativeExamination_tmp"></td>
	<td><input type="text"  name="preoperativeExamination_tmp"></td>
</tr><!--  tr end... -->

<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">邻牙情况：</td>
	<td><input type="text"  name="teethCondition_tmp"></td>
	<td><input type="text"  name="teethCondition_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="teethCondition_tmp" rows="3"></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('teethCondition_tmp',this);"> 
				<%
 					}
 				%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="teethCondition_tmp"></td>
	<td><input type="text"  name="teethCondition_tmp"></td>
</tr><!--  tr end... -->

<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">种植区牙龈情况：</td>
	<td><input type="text"  name="implantgumCondition_tmp"></td>
	<td><input type="text"  name="implantgumCondition_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="implantgumCondition_tmp" rows="3" ></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('implantgumCondition_tmp',this);"> 
				<%
 					}
 				%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="implantgumCondition_tmp"></td>
	<td><input type="text"  name="implantgumCondition_tmp"></td>
</tr><!--  tr end... -->

<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">种植体暴露：</td>
	<td><input type="text"  name="implantExposure_tmp"></td>
	<td><input type="text"  name="implantExposure_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="implantExposure_tmp" rows="3" ></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('implantExposure_tmp',this);"> 
				<%
 					}
 				%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="implantExposure_tmp"></td>
	<td><input type="text"  name="implantExposure_tmp"></td>
</tr><!--  tr end... -->

<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2" style="width:20%;">种植体X体线检查：</td>
	<td><input type="text"  name="xrayExamination_tmp"></td>
	<td><input type="text"  name="xrayExamination_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="xrayExamination_tmp" rows="3" ></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('xrayExamination_tmp',this);"> 
				<%
			}
		%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="xrayExamination_tmp"></td>
	<td><input type="text"  name="xrayExamination_tmp"></td>
</tr><!--  tr end... -->



<tr>
	<td>其他：</td>
	<td colspan="7"><input type="text"  name="others" ></td>
</tr>
<tr>
	<td>手术记录：</td>
	<td colspan="7">
	<textarea rows="5"  name="surgicalRecord" ></textarea>
	</td>
</tr>

<tr>
	<td>备注：</td>
	<td colspan="7">
	<textarea rows="5"  name="remark" ></textarea>
	</td>
</tr>

</table>