//table初始化后，计算table是否产生滚动条 如果出现滚动条则调整tableheader的宽度

function setTableHeaderWidth(str){/*传入table父元素的class名字*/
	
	var tableContent=Math.floor($(str+" .fixed-table-body table").outerHeight()-$(str+" .fixed-table-body table thead").outerHeight());
	var tableBody=Math.floor($(str+" .fixed-table-body").innerHeight());
	/*滚动条出现时*/
	
	if(tableContent>tableBody){
		$(str+" .fixed-table-header").width($(str+" .fixed-table-container").width()-16);
		
	}else{
		$(str+" .fixed-table-header").width($(str+" .fixed-table-container").width());
		
	}
	
}
/* 接待中心 类似页面的宽度布局 很多页面用到该方法*/
function setWidth() {
    var baseW = $(window).outerWidth() - 44;
    var rightModelDisplay=$(".lookInfoWrap .columnBd").css("display");/*获得当前右侧模块的状态 */
    if(rightModelDisplay=="none"){/*如果右侧模块没有显示*/
    	$(".centerWrap").width(baseW-70);/*如果左侧模块占满*/
    	//$(".centerWrap").width(baseW);/*如果左侧模块占满*/
    }else{
    	$('.centerWrap,.lookInfoWrap').width(Math.floor(baseW / 2));/*如果左右模块各一半*/
    	//$('.centerWrap,.lookInfoWrap').width(Math.floor(baseW+25));/*如果左右模块各一半*/
    }
    
    // 设置  optBox宽度，50是左右各padding 25px;
    /*$('.operateModel .optBox').width($('.operateModel').width() - 50);*/
    /*static_container_Width = $("#container").width();*/
    
    /*计算是否产生滚动条*/
    setTableHeaderWidth(".tableBox");
}

//setHeight()函数移动到tableHeaderWidth.js
function setHeight() {
    var baseHeight = $(window).height() - 15;  //获得容器的高度  
    $(".lookInfoWrap .columnWrap").outerHeight(baseHeight); //设置右侧区域的高度
    $(".lookInfoWrap .columnWrap .columnBd").outerHeight(baseHeight); //设置右侧区域的高度
    $("#table").bootstrapTable('resetView', {
        height: baseHeight - $(".centerWrap .columnHd").outerHeight() - $("#gongzuol").outerHeight() - $(".searchWrap").outerHeight()-10   
    });
    $('.tabIframe').outerHeight(baseHeight - 60);   //计算右侧iframe 内嵌即 个人信息等页面的高度
}