/*业务管理 各管理页面的 数据表table 自适应高度的js*/
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight()-20;
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight
	})
	$(".leftDiv").outerHeight(tableHeight);
}
function getTableHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".clearfix").outerHeight()-$("#toolbar").outerHeight()-5;
	return tableHeight;
}
