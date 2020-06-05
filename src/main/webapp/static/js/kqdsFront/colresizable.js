//表格宽度拖拽
document.onreadystatechange = function () {      
    if(document.readyState=="complete") {     
    		try{
    			if($('#table').bootstrapTable('getData')){
    				//console.log("bootstrapTable");
    				//resizeInit();
    			}
	    	}catch(e){
	    		
	    	}
    }     
} 
//表格宽度拖拽
function resizeInit(){
	$('#table').colResizable({ 
		resizeMode:'overflow',
		onResize:onResize
	});
	onResize();
}
//表格宽度拖拽回掉方法
function onResize(){
	$('#table').bootstrapTable('resetView', {});
}