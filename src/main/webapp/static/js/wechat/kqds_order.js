/**
 * Created by Administrator on 2017/9/19.
 */
$(function() {
    setHeight();

});
function jump(path) {
    $(location).attr("href", path);
}
function closeDiv(closeIdName) {
    if (closeIdName) {
        $("#" + closeIdName).css("display", "none");
    }
}
function openDiv(openIdName) {
    if (openIdName) {
        $("#" + openIdName).css("display", "block");
    }
}
function setHeight() {
    var containerHeight = $(window).outerHeight() - $(".kqds-header").outerHeight();
    $(".kqds-content").outerHeight(containerHeight);
}