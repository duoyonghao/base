/*业务管理 各管理页面的 tabIframe1 自适应高度的js*/
function setHeight(){
	var frameHeight=$(window).height()-$(".titleText").outerHeight()-$(".layui-tab-title").outerHeight()-25;
	$("#tabIframe1").outerHeight(frameHeight);
}
