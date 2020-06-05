function loading(id,obj){
    //检查是否到最后一夜
    if(nowpage < maxpage){
    	nowpage = nowpage + 1;
    	var html = '';
        var opt = {
    	        url: pageurl,
    	        silent: true,
    	        query:{
    	        	offset : nowpage*pagesize
    	        }
    	    };
        $('#'+id).bootstrapTable('refresh', opt);
        $(obj).removeAttr("onclick");
    	$("#msg").removeClass("glyphicon glyphicon-search");
    	$("#msg").toggleClass("glyphicon glyphicon-cloud-download");
        $("#msg").html('正在加载。。。'); 
    }
}

function showdata(id,data){
	if(data && data.length>0){
		for(var i=0;i<data.length;i++){
			loadedData.push(data[i]);
		}
	    $("#"+id).bootstrapTable("load",loadedData);
	    //显示指定行
	    var parameter = {
    	        index: 16,
    	};
	    $('#table').bootstrapTable('scrollTo', 24*nowpage*pagesize);
	    //获取table外div的宽度
	    var widthDiv = $(".tableBox").width();
	    if(maxpage>1 && (maxpage-1)>nowpage){
    		var jsonnums = JSONLength(data[0]);
    		$("#"+id).append('<tr onclick="loading(\''+id+'\',this)" class="no-records-found"><td style="text-align:left;" colspan="'+jsonnums+'"><font style="vertical-align: inherit;cursor:pointer;padding-left: '+widthDiv/2+'px;"><span id="msg" class="glyphicon glyphicon-search">点击加载更多</span></font></td></tr>')
    	}else if(maxpage>1 && (maxpage-1)==nowpage){
    		layer.msg('已加载全部内容！',{
    			time:2000,
    			offset: ['35%', widthDiv/2]
    		});
    	}
	}else{
		 $("#"+id).bootstrapTable("load",loadedData);
	}
}

function JSONLength(obj) {
	var size = 0, key;
	for (key in obj) {
			if (obj.hasOwnProperty(key)) size++;
	}
	return size;
}