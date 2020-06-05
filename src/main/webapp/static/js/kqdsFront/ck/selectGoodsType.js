
function selectGoodsTypezTreeInit() {
    //异步加载
    var url = contextPath + '/KQDS_Ck_GoodstypeAct/getSelectTreeNohouseAsync.act';
    var setting = {
        view: {
            showIcon: false // 去掉图标
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id"],
            dataFilter: ajaxDataFilter,
            type: "get"
        }
    };
    setting['callback'] = {
        onClick: onClick
    };
    $.fn.zTree.init($("#treeDemo"), setting);

    showMenu();
}
function ajaxDataFilter(treeId, parentNode, responseData) {
    var tree = null;
    if (responseData.retState == "0") {
        tree = responseData.retData;
    }
    return tree;
};
function showMenu() {
    var cityObj = $("#goodstypename");
    var cityOffset = $("#goodstypename").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
//        top: cityOffset.top + cityObj.outerHeight() + "px"
        bottom: $(window).outerHeight() - cityOffset.top  + "px"
    }).slideDown();

    $("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (! (event.target.id == "goodstypename" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
        hideMenu();
    }
}
function onClick(e, treeId, treeNode) {
	if (treeNode.isParent) {//选择叶子节点 才赋值
		$("#goodstypename").val(treeNode.name);
    	$("#goodstype").val(treeNode.id);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    } else {
    	$("#goodstypename").val(treeNode.name);
    	$("#goodstype").val(treeNode.id);
    }
}