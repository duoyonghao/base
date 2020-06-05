<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<tr class="footTableTr">
	<td></td>
	<td></td>
	<td>医生：</td>
	<td>
		<input type="hidden" name="doctor" id="doctor"  class="form-control" />
		<input type="text"   id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'single');"  ></input>	
	</td>
	<td>助理：</td>
	<td>
		<input type="hidden" name="assistant" id="assistant"  class="form-control" />
		<input type="text"   id="assistantDesc" name="assistantDesc" placeholder="助理" readonly  onClick="javascript:single_select_user(['assistant', 'assistantDesc'],'single');"  ></input>	
	</td>
	<td>护士：</td>
	<td>
		<input type="hidden" name="nurse" id="nurse"  class="form-control" />
		<input type="text"   id="nurseDesc" name="nurseDesc" placeholder="护士" readonly  onClick="javascript:single_select_user(['nurse', 'nurseDesc'],'single');"  ></input>	
	</td>
</tr>