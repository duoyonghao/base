
/**
 * 打开模态窗口,能改变窗口大小
 */
function openDialogResize(actionUrl, width, height) {
    var locX = (screen.width - width) / 2;
    var locY = (screen.height - height) / 2;
    var attrs = null;

    attrs = "status:no;directories:yes;scroll:yes;Resizable=yes;";
    attrs += "dialogWidth:" + width + "px;";
    attrs += "dialogHeight:" + height + "px;";
    attrs += "dialogLeft:" + locX + "px;";
    attrs += "dialogTop:" + locY + "px;";

    var attr = "height=" + height + "px, width=" + width + "px, top=" + locY + "px, left=" + locX + "px, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no";
    return window.open(actionUrl, 'newwindow', attr);

}

// layer 弹框
function openLayerDialogResize(actionUrl, width, height) {
    layer.open({
        type: 2,
        title: '人员选择',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: ['550px', '450px'],
        content: actionUrl
    });
}

//layer 弹框
function openLayerDialogResizeNosize(actionUrl, width, height) {
    layer.open({
        type: 2,
        title: '人员选择',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: [width, height],
        content: actionUrl
    });
}

function openLayerDialogResizeDept(actionUrl, width, height) {
    layer.open({
        type: 2,
        title: '部门选择',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: ['550px', '450px'],
        content: actionUrl
    });
}