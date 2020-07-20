/*function showFile(){
	var seqId =$("#form1 input[name=seqId]").val();
 	if(""!=seqId && null!=seqId){
 		var attachmentId = $("#form1 input[name=attachmentid]").val();
		var attachmentName = $("#form1 input[name=attachmentname]").val();
		//附件填充
		fullAttach(attachmentName,attachmentId,"active");
	} 
}
function yxzl(){
 	*//******************************************上传文件start******************************************************//*
 	//初始化方法，填入页面隐藏的 附件id的id值 
 	initParam("attachmentid","attachmentname");
 	uploadfile(contextPath+"/FileUploadAct/uploadFile.act?module=active");
	*//******************************************上传文件end******************************************************//*
}
*/

function refresh() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}

var loadIndex='';
function download() {
    layer.msg('数据导出中，请等待');
    //loadIndex = layer.load(0, {shade: false});
    isClick = false;
}
function disload() {
    layer.close(loadIndex);
    layer.msg('数据导出完毕');
    isClick = true;
}
//导出
function exportTable() {
    if(isClick) {
        isClick = false;
         console.log("生成报表")
        var fieldArr = [];
        var fieldnameArr = [];
        $('#table thead tr th').each(function() {
            var field = $(this).attr("data-field");
            if (field != "") {
                fieldArr.push(field); //获取字段
                fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
            }
        });
        var param = JsontoUrldata(queryParams());
        var url = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
        download();
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);    // 也可用POST方式
        xhr.responseType = "blob";
        xhr.onload = function () {
            if (this.status === 200) {
                var blob = this.response;
                // if (navigator.msSaveBlob == null) {
                var a = document.createElement('a');
                //var headerName = xhr.getResponseHeader("Content-disposition");
                //var fileName = decodeURIComponent(headerName).substring(20);
                a.download = "赠送记录";
                a.href = URL.createObjectURL(blob);
                $("body").append(a);    // 修复firefox中无法触发click
                a.click();
                URL.revokeObjectURL(a.href);
                $(a).remove();
                // } else {
                //     navigator.msSaveBlob(blob, "信息查询");
                // }
            }
            disload();
        };
        xhr.send();
    }
}
//清空
function cleanLeft() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
}
function cleanRight() {
    $("#form1 :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    
    var fileList = jQuery("#fileList"); // 清空
    fileList.empty();
}

//选择患者
function getuser() {
    layer.open({
        type: 2,
        title: '选择患者',
        shadeClose: true,
        shade: 0.6,
        area: ['550px', '80%'],
        content: contextPath + '/KQDS_UserDocumentAct/toUserList.act',
        end: function(index, layero) {
            if (usercodechild != "" && usernamechild != "") {
                $("#username").val(usercodechild);
                $("#usernameDesc").val(usernamechild);
            }
        },
    });
}

//右侧清空
function clean() {
    $("#fileList").empty();
    //重置页面附件
    picArray = [];
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#form1 input[name=seqId]").val("");
    $("#form1 input[name=attachmentid]").val("");
    $("#form1 input[name=attachmentname]").val("");
}

//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}

function skmx() {
    var usercodes = "";
    var checkrows = getIdSelections();
    if (checkrows.length < 1) {
        layer.alert('请选择记录'  );
        return false;
    }

    for (var i = 0; i < checkrows.length; i++) {
        usercodes += checkrows[i].kehu + ",";
    }
    usercodes = usercodes.substring(0, usercodes.length - 1);
    layer.open({
        type: 2,
        title: '收款明细',
        shadeClose: false,
        shade: 0.6,
        area: ['98%', '95%'],
        content: contextPath + '/KQDS_ScbbAct/toBb_Sfmx_All_Wdperson_Trcc.act?usercodes=' + usercodes + '&menuId=' + menuid
    });
}
function zxqk(table) {
    var seqIds = "";
    var checkrows = getIdSelections();
    if (checkrows.length < 1) {
        layer.alert('请选择记录'  );
        return false;
    }

    for (var i = 0; i < checkrows.length; i++) {
        seqIds += checkrows[i].seqId + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);
    layer.open({
        type: 2,
        title: '咨询情况',
        shadeClose: false,
        shade: 0.6,
        area: ['80%', '85%'],
        content: contextPath + '/KQDS_ScbbAct/toBb_Zxqk.act?seqIds=' + seqIds + '&table=' + table + '&menuId=' + menuid
    });
}
function xxcx() {
    var usercodes = "";
    var checkrows = getIdSelections();
    if (checkrows.length < 1) {
        layer.alert('请选择记录'  );
        return false;
    }

    for (var i = 0; i < checkrows.length; i++) {
        usercodes += checkrows[i].kehu + ",";
    }
    usercodes = usercodes.substring(0, usercodes.length - 1);
    layer.open({
        type: 2,
        title: '信息明细',
        shadeClose: false,
        shade: 0.6,
        area: ['80%', '85%'],
        content: contextPath + '/KQDS_ScbbAct/toBb_Xxcx.act?usercodes=' + usercodes + '&menuId=' + menuid
    });
}
function tsccqk(table) {
    var seqIds = "";
    var checkrows = getIdSelections();
    if (checkrows.length < 1) {
        layer.alert('请选择记录'  );
        return false;
    }

    for (var i = 0; i < checkrows.length; i++) {
        seqIds += checkrows[i].seqId + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);
    layer.open({
        type: 2,
        title: '活动投入产出情况',
        shadeClose: false,
        shade: 0.6,
        area: ['80%', '85%'],
        content: contextPath + '/KQDS_ScbbAct/toBb_Trsc.act?seqIds=' + seqIds + '&table=' + table
    });
}