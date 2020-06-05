//删除付款方式
function delrow(thisnum){
	
	$("#FKFS" + thisnum).remove();
	
	if($("input[name='money']").length == 1){
		$('#addtype').show();
	}else{
		//判断页面里是否还有加号 没有则在最后一个div里添加
		if($("input[name='deltype']").length == 0){
			var idnum=0;
			$("input[name='money']").each(function (){
				var idstr = $(this).attr("id");
				idnum = Number(idstr.substring(5,idstr.length));
			});
			$('#FKFS' + idnum).find("button[name='addtype']").show();	
		}
	}
	
}

//添加付款方式
function addrow(){
	var idnum=0;
	$("input[name='money']").each(function (){
		var idstr = $(this).attr("id");
		idnum = Number(idstr.substring(5,idstr.length));
	});
	if($("input[name='money']").length == 1){
		$('#addtype').hide();
		$('#deltype').show();
	}else{
		$('#addtype'+idnum).hide();
		$('#deltype'+idnum).show();
	}
	
	var htmls = '<tr id="FKFS'+(idnum+1)+'">'
	 +'		<td><span class="commonText">充值方式：</span></td>'
	 +'		<td colspan="3">'
	 +'			<select class="shortSel" name="paytype" id="paytype'+(idnum+1)+'">'
	 +'			</select>'
	 +'			<input class="longInp" type="text" placeholder="金额" id="money'+(idnum+1)+'" name="money" >'
	 +'			<button type="button" class="btn btn-default btn-sm" class="addtype'+(idnum+1)+'" name="addtype" id="addtype'+(idnum+1)+'" onclick="addrow('+(idnum+1)+')">'
	 +'		         <span class="glyphicon glyphicon-plus"></span>'
	 +'		    </button>'
	 +'		    <button type="button" class="btn btn-default btn-sm" class="deltype'+(idnum+1)+'" name="deltype" id="deltype'+(idnum+1)+'" onclick="delrow('+(idnum+1)+')">'
	 +'		         <span class="glyphicon glyphicon-minus"></span>'
	 +'		    </button>'
	 +'		</td>'
	 +'	</tr>';
	$('#kqds_table').append(htmls);
	
	//获取paytype1下拉内容 
	var selstr = $("#paytype1").html();
	$("#paytype" + (idnum+1)).html(selstr);
	
}


//验证患者是否已办理过会员卡
function checkIsMemberByUsercode(){
	var ff = false;
	var usercode = $("#usercode").val();
	var url = contextPath+'/KQDS_MemberAct/checkIsMemberByUsercode.act?usercode=' + usercode;
	$.axse(url,null, 
          function(data){
			  if(data.data == 0){
				  ff = true;
	       	  }
          },
          function(){
      	  }
	);
	return ff;
}

//验证会员卡号是否已存在
function checkmemberno(){
	var ff = false;
	var memberno = $("#memberno").val();
	var url = contextPath+'/KQDS_MemberAct/checkMemberno.act?memberno=' + memberno;
	$.axse(url,null, 
          function(data){
			  if(data.data == "0"){
				  ff = true;
	       	  }
          },
          function(){
      	  }
	);
	return ff;
}

//当天最新挂号信息
function getRegxx(usercode) {
    var detailurl = contextPath + '/KQDS_REGAct/selectToDayNewDetail.act?usercode=' +usercode;
    $.axse(detailurl, null,
    function(r) {
        if (r.retState == "0") {
            $('#askperson').val(r.askperson);
            $('#regsort').val(r.regsort);
        }else{
        	layer.alert(r.retMsrg  );
        }
    },
    function() {
        layer.alert(r.retMsrg );
    });
}