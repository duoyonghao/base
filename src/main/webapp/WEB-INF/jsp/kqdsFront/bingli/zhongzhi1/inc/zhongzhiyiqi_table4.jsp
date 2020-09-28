<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<table>
<th colspan="8">种植Ⅰ期手术</th>
<tr>
	<td>牙龈厚度（mm) </td>
	<td colspan="7"><input type="text"  name="gumThickness"></td>
</tr>
<tr>
	<td>牙槽嵴顶宽度（mm) </td>
	<td colspan="7"><input type="text"  name="alveolarCrestWidth" ></td>
</tr>
<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2">种植体植入情况：</td>
	<td><input type="text"  name="detailsOfImpants_tmp"></td>
	<td><input type="text"  name="detailsOfImpants_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea name="detailsOfImpants_tmp" rows="3"></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('detailsOfImpants_tmp',this);"> 
				<%
			}
		%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="detailsOfImpants_tmp"></td>
	<td><input type="text"  name="detailsOfImpants_tmp"></td>
</tr><!--  tr end... -->

<tr>
	<td>条码粘贴：</td>
	<td colspan="7"><input type="text"  name="barcodeStick" ></td>
</tr>

<tr>
	<td>术后医嘱：</td>
	<td colspan="7">
	<textarea rows="5"  name="postoperativeAdvice" >咬纱布或者纱布卷30~60分钟，唾液可吞下，术后进流食或半流食1周，术后必要时使用抗生素3-7天，用漱口水含漱，保持口腔卫生。</textarea>
	</td>
</tr>
<tr>
	<td>备注：</td>
	<td colspan="7"><input type="text"  name="stageRemark" ></td>
</tr>

</table>
