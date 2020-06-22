/*选人 多选  的js*/
$(function(){
		setHeight();
		setWidth();
		$("#pul").on("click","li",tickle);
		$(window).resize(function(){
			setHeight();
			setWidth();
		});
	});
	function tickle(){
		$(this).addClass("tickle").siblings().removeClass("tickle");
	}
	function setHeight(){
		var treeHeight=$(window).height()-$(".roleChoiceText").outerHeight()-15;
		$("#treeList").outerHeight(treeHeight);
		$("#pul").outerHeight(treeHeight-40);
	}
	function setWidth(){
		var useableWidth=$(window).width()-20;
		var treeWidth=Math.floor(useableWidth/100*40);
		var pulWidth=Math.floor(useableWidth/100*60);
		$("#roleChoice").outerWidth(treeWidth);
		$("#plist").outerWidth(pulWidth);
	}