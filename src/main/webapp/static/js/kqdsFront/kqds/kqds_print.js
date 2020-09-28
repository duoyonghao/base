/**
 * 根据jspname查询是a4还是a5
 * 1 a4，2 a5
 * @param jspname
 * @returns {Number}
 */
function getPrintType(printname) {
    var printset = null; // 默认a5
    var url = contextPath + '/KQDS_Print_SetAct/getPrintTypeByUrl.act?printname=' + printname;
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
            printset = data.data;
        } else {
            layer.alert(data.retMsrg + ',默认使用A5设置进行打印'  );
        }
    },
    function() {
        layer.alert('查询失败！' );
    });
    return printset;
}

/**
 * 打印Html表单
 */
function printHtmlTable(seqId) {
    /*	var bdhtml=window.document.body.innerHTML;
		var printData= jQuery("#" + tableId).html();
		window.document.body.innerHTML = printData;
		window.print();
		window.location.reload();*/
    window.location.href = contextPath + '/PrintAct/zhongZhiBingliPdf4Print.act?seqId=' + seqId;
    return false;

}