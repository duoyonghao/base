function showLocation(province , city , town) {
	
	var loc	= new Location();
	var title	= ['省份' , '地级市' , '市、县、区'];
	$.each(title , function(k , v) {
		title[k]	= '<option value="">'+v+'</option>';
	})
	
	$('#province').append(title[0]);
	$('#city').append(title[1]);
	$('#town').append(title[2]);
	
	$('#province').change(function() {
		$('#city').empty();
		$('#city').append(title[1]);
		loc.fillOption('city' , '0,'+$('#province').val());
		$('#city').change()
	})
	
	$('#city').change(function() {
		$('#town').empty();
		$('#town').append(title[2]);
		loc.fillOption('town' , '0,' + $('#province').val() + ',' + $('#city').val());
	})
	
	$('#town').change(function() {
		$('input[@name=location_id]').val($(this).val());
	})
	
	if (province) {
		loc.fillOption('province' , '0' , province);
		
		if (city) {
			loc.fillOption('city' , '0,'+province , city);
			
			if (town) {
				loc.fillOption('town' , '0,'+province+','+city , town);
			}
		}
		
	} else {
		loc.fillOption('province' , '0');
	}
		
}

function showLocationSearch(province , city , town) {
	
	var loc	= new Location();
	var title	= ['省份' , '地级市' , '市、县、区'];
	$.each(title , function(k , v) {
		title[k]	= '<option value="">'+v+'</option>';
	})
	
	$('#province').append(title[0]);
	$('#city').append(title[1]);
	$('#town').append(title[2]);
	
	$('#province').change(function() {
		$('#city').empty();
		$('#city').append(title[1]);
		loc.fillOption('city' , '0,'+$('#province').selectpicker('val'));
		$('#city').change()
	})
	
	$('#city').change(function() {
		$('#town').empty();
		$('#town').append(title[2]);
		loc.fillOption('town' , '0,' + $('#province').selectpicker('val') + ',' + $('#city').selectpicker('val'));
	})
	
	$('#town').change(function() {
		$('input[@name=location_id]').selectpicker('val');
	})
	
	if (province) {
		loc.fillOption('province' , '0' , province);
		
		if (city) {
			loc.fillOption('city' , '0,'+province , city);
			
			if (town) {
				loc.fillOption('town' , '0,'+province+','+city , town);
			}
		}
		
	} else {
		loc.fillOption('province' , '0');
	}
		
}