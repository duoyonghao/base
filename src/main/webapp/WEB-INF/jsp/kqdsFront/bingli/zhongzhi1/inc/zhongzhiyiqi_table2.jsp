<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<table id="zhongzhi">
<th colspan="8">术前检查及准备</th>
<tr><!-- 可增加行you can add lines  -->
	<td rowspan="2">牙列情况：</td>
	<td><input type="text"  name="dentition_tmp"></td>
	<td><input type="text"  name="dentition_tmp"></td>
	<td rowspan="2" colspan="4">
		<textarea rows="3"  name="dentition_tmp"></textarea>
	</td>
	<td rowspan="2">
		<%
			if(YZUtility.isNullorEmpty(detailFlag)){
		%>
				<input type="button" value="增加行" onclick="addRow('dentition_tmp',this);"> 
				<%
			}
		%>
	</td>
</tr>
<tr>
	<td><input type="text"  name="dentition_tmp"></td>
	<td><input type="text"  name="dentition_tmp"></td>
</tr>
<tr>
	<td>软组织情况：</td>
	<td colspan="7"><input type="text"  name="softTissue" ></td>
</tr>
<tr>
	<td>硬组织情况：</td>
	<td colspan="7"><input type="text"  name="hardTissue" ></td>
</tr>

<tr>
	<td rowspan="3">CBCT检查：</td>
	<td>骨高度（mm）：</td>
	<td><input type="text"  name="boneHeight"></td>
	<td>骨宽度（mm）：</td>
	<td><input type="text"  name="boneWidth"></td>
	<td>骨密度（hu）：</td>
	<td colspan="2"><input type="text"  name="boneDensity"></td>
	<!-- <td></td> -->
</tr>
<tr>
	<td colspan="2" > 距离上颌窦高度：</td>
	<td>左（mm）：</td>
	<td><input type="text"  name="maxillarySinusLeft"></td>
	<td>右（mm）：</td>
	<td colspan="2"><input type="text"  name="maxillarySinusRight"></td>
	<!-- <td></td> -->
</tr>
<tr>
	<td colspan="2" >距离下牙槽神经管高度：</td>
	<td>左（mm）：</td>
	<td><input type="text"  name="boneNerveLeft"></td>
	<td>右（mm）：</td>
	<td colspan="2" ><input type="text"  name="boneNerveRight"></td>
	
</tr> <!--  tr end... -->


<tr>
	<td rowspan="6" >全身情况：</td>
	<td>高血压：</td>
	<td><input type="text"  name="hypertension"></td>
	<td><input type="text"  name="hypertensionYear"  style="width:60px;">年</td>
	<td>是否服药?： </td>
	<td><input type="text"  name="hypertensionMedication"></td>
	<td>是否控制?： </td>
	<td><input type="radio" name="hypertensionControl" value="1" id="hypertensionControl_1"> <label for="hypertensionControl_1" style="cursor: pointer;">是</label>
		<input type="radio" name="hypertensionControl" value="0" id="hypertensionControl_0"> <label for="hypertensionControl_0" style="cursor: pointer;">否</label></td>
</tr>
<tr>
	<td>心脏病：</td>
	<td><input type="text"  name="cardiopathy"></td>
	<td><input type="text"  name="cardiopathyYear"  style="width:60px;">年</td>
	<td colspan="4">是否常备药在身边?： 
	 <input type="radio" name="cardiopathyMedication" value="1" id="cardiopathyMedication_1"> <label for="cardiopathyMedication_1" style="cursor: pointer;">是</label>
	 <input type="radio" name="cardiopathyMedication" value="0" id="cardiopathyMedication_0"> <label for="cardiopathyMedication_0" style="cursor: pointer;">否</label></td>
</tr>
<tr>
	<td>糖尿病 ：</td>
	<td>
		<input type="radio" name="diabetesIf" value="1" id="diabetesIf_1"><label for="diabetesIf_1" style="cursor: pointer;">有</label>
		<input type="radio" name="diabetesIf" value="0" id="diabetesIf_0"><label for="diabetesIf_0" style="cursor: pointer;">无</label>
	</td>
		
	<td></td>
	<td colspan="3">
		怎么控制：
		<input type="radio" name="diabetesHow" value="1" id="diabetesHow_1"><label for="diabetesHow_1" style="cursor: pointer;">饮食</label>
		<input type="radio" name="diabetesHow" value="2" id="diabetesHow_2"><label for="diabetesHow_2" style="cursor: pointer;">口服药</label>
		<input type="radio" name="diabetesHow" value="3" id="diabetesHow_3"><label for="diabetesHow_3" style="cursor: pointer;">针剂</label>
	</td>
	<td>是否控制?：<br>
		<input type="radio" name="diabetesControl" value="1" id="diabetesControl_1"><label for="diabetesControl_1" style="cursor: pointer;">是</label>
		<input type="radio" name="diabetesControl" value="0" id="diabetesControl_0"><label for="diabetesControl_0" style="cursor: pointer;">否</label></td>
</tr>
<tr>
	<td colspan="7">传染性疾病：<input type="text"  name="infectiousDisease">   </td>
</tr>
<tr>
	<td colspan="7">代谢性疾病：<input type="text"  name="metabolicDisease"></td>
</tr>
<tr>
	<td colspan="7">服药情况：<input type="text"  name="medication"></td>
</tr><!--  tr end... -->
<tr>
	<td>其他：</td>
	<td colspan="7"><input type="text"  name="others" ></td>
</tr>
<tr>
	<td>实验室检查：</td>
	<td colspan="7"><input type="text"  name="labExamination" value="见化验报告see analysis report 。" ></td>
</tr>

</table>
