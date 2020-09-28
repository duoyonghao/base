
//获取上传 图片根目录
function getImgFolder() {
    var imgurlstr = "";
    //获取路径
    var url = contextPath + '/KQDS_ImageDataAct/getImgUrl.act';
    $.axse(url, null,
    function(data) {
        imgurlstr = data.data;
    },
    function() {
        layer.alert('查询出错！' );
    });
    return imgurlstr;
}