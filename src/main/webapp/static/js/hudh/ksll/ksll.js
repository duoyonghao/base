/**
 * 初始化领料科室
 */
function initDept($obj){
	$.ajax({
		url: contextPath + '/HUDH_KSllAct/findCkDept.act',
		type:"POST",
		dataType:"json",
		success:function(result){
			if(result) {
				var html;
				html+='<option value="">请选择</option>';
				for(var index in result) {
					html+='<option value="'+result[index].seq_id+'">'+result[index].deptname+'</option>';
				}
				$obj.append(html);
			}
		}
	});
}

/**
 * 初始化领料科室
 */
function initDeptSearch($obj){
	$.ajax({
		url: contextPath + '/HUDH_KSllAct/findCkDept.act',
		type:"POST",
		dataType:"json",
		success:function(result){
			if(result) {
				var html;
				html+='<option value="">请选择</option>';
				for(var index in result) {
					html+='<option value="'+result[index].seq_id+'">'+result[index].deptname+'</option>';
				}
				$obj.append(html);
				$obj.selectpicker("refresh");
			}
		}
	});
}
/**
 * 初始化领料仓库
 */
function initHouse($obj){
	$.ajax({
		url: contextPath + '/HUDH_KSllAct/findCkHouse.act',
		type:"POST",
		dataType:"json",
		success:function(result){
			var houseList = result.houseList;
			if(houseList) {
				var html;
				html+='<option value="">请选择</option>';
				for(var index in houseList) {
					html+='<option value="'+houseList[index].seqId+'">'+houseList[index].housename+'</option>';
				}
				$obj.append(html);
			}
		}
	});
}