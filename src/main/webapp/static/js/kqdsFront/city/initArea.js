//var cityCode = $("#city").val();
//var areaCode = $("#area").val();


function prov(){
	var url = contextPath + '/HUDH_ProviceAct/findAll.act';
	var param = { id : null };
	
	$.axseSubmit(url, param, function() {}, function(r) {
	 // alert(JSON.stringify(r));
	  for (var i = 0; i < r.length; i++) {
		   $("#province").append(
			    '<option value='+ r[i].provinceCode + '>' + r[i].provinceName + '</option>' 		    
		   );
	  }
	  $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.19--licc
	}, function() {
		
	});
	$("#province").val("110000");
}

function initCity(){
	$("#city").html("");
	$("#area").html("");
	$("#town").html("");
	var proviceCode = $('#province').val();
	//alert(JSON.stringify(proviceCode));
	var url = contextPath + '/HUDH_CityAct/findCityByProviceCode.act';
    var param = { proviceCode : proviceCode };
	$.axseSubmit(url, param, function() {}, function(r) {
//	  alert(JSON.stringify(r));
		$("#city").append('<option value="">请选择</option>');
	  for (var i = 0; i < r.length; i++) {
			  $("#city").append(
					'<option value='+ r[i].cityCode + '>' + r[i].cityName + '</option>' 
			  );
	  }
	  $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.19--licc
	}, function() {
		
	});
}

function initArea(){
	$("#area").html("");
	$("#town").html("");
	var cityCode = $("#city").val();
//	console.log(cityCode,"cityCodecityCode")
	var url = contextPath + '/HUDH_AreaAct/findAreaByCityCode.act';
	var param = { cityCode : cityCode };
	$.axseSubmit(url, param, function() {}, function(r) {
   // alert(JSON.stringify(r));
		$("#area").append('<option value="">请选择</option>');
		for (var i = 0; i < r.length; i++) {
			$("#area").append(
					'<option value='+ r[i].areaCode + '>' + r[i].areaName + '</option>' 
			);
		}
		$('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.19--licc
	}, function() {
		
	});
}

function initStreet(){
	$("#town").html("");
	var areaCode = $("#area").val();
//	console.log(areaCode,+"######################");
	var url = contextPath + '/HUDH_StreetAct/findStreetByAreaCode.act';
	var param = { areaCode : areaCode };
	$.axseSubmit(url, param, function() {}, function(r) {
	  //alert(JSON.stringify(r));
		$("#town").append('<option value="">请选择</option>');
		for (var i = 0; i < r.length; i++) {
			$("#town").append(
					'<option value='+ r[i].streetCode + '>' + r[i].streetName + '</option>' 
			);
		}
		$('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.19--licc
	}, function() {
		
	});
}


