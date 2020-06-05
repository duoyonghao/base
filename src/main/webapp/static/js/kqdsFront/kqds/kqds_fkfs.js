//用于查询页面表头title的值（包括禁用的）
function getFkfsField(id) {
	if(!id){
		id="table";
	}
    var detailurl = contextPath + '/YZFkfsAct/selectNoPage.act';
    $.axse(detailurl, null,
    function(r) {
    	if(r.length>0){
    		var tableList = $('#'+id).bootstrapTable('getData');
    		for(var i=0;i<r.length;i++){
    			$('#'+id+' thead tr th').each(function() {
    		        var field = $(this).attr("data-field");
    		        if (field == r[i].costfield || field == r[i].memberfield) {
    		            $(this).children()[0].innerText=r[i].payname; //获取字段中文
    		            /*$('#'+id).bootstrapTable('hideColumn', field);
    		            for (var k = 0; k < tableList.length; k++) {
    		            	num++;
    		            	var data = tableList[k];
    		            	if(Number(data[field])!=0){
    		            		$('#'+id).bootstrapTable('showColumn', field);
    		            		break;
    		            	} 
    		            }*/
    		        }
    		    });
    		}
    	}
    },
    function() {
       
    });
}
//用于项目退款填写退款金额表头title的值（不包括禁用的）
function getFkfsFieldUse(id) {
	if(!id){
		id="table";
	}
    var detailurl = contextPath + '/YZFkfsAct/selectNoPage.act';
    $.axse(detailurl, null,
    function(r) {
    	if(r.length>0){
    		for(var i=0;i<r.length;i++){
    			$('#'+id+' thead tr th').each(function() {
    		        var field = $(this).attr("data-field");
    		        if (field == r[i].costfield || field == r[i].memberfield) {
    		            $(this).children()[0].innerText=r[i].payname; //获取字段中文
    		            if(r[i].useflag == 1){
    		            	$('#'+id).bootstrapTable('hideColumn', field);
    		            }
    		        }
    		    });
    		}
    	}
    },
    function() {
       
    });
}

