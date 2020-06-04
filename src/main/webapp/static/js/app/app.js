//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8083
var localhostPaht = curWwwPath.substring(0, pos);
// 获取带"/"的项目名，如：/uimcardprj
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
//获取主机地址，如： http://localhost:8083
var localhostPaht = curWwwPath.substring(0, pos);

/*杨森 add 17-8-16*/
var contextPath = localhostPaht + projectName;

//不带http   localhost:8083
var localhostPahtless = localhostPaht.substring(localhostPaht.lastIndexOf("/") + 1);


document.write('<script src="' + contextPath + '/static/js/app/plugin/jquery.form.js"><\/script>');

/** jquery 扩展 **/
$.extend({
    getUrlVars: function() {
        var vars = [],
        hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for (var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    },
    getUrlVar: function(name) {
        return $.getUrlVars()[name];
    }
});





/**************************************************开发环境引用以下JS**********************************/
document.write('<script src="' + contextPath + '/app/js/kqds/login.js"><\/script>');

/**************************************************部署环境引用以下JS[合并切压缩过的JS]**********************************/



/**************************************************END!!!!!!!!!!**********************************/


//禁用右键菜单
document.oncontextmenu = DisableRightClick;
function DisableRightClick() {
    return false;
}

//禁止页面选择，防止数据拷贝导出
$(function() {
    document.onselectstart = function() {
        return event.srcElement.type == "text";
    };
});

function readonly() {
    $("input").attr("readonly", "readonly");
    $("textarea").attr("readonly", "readonly");
    $("select").attr("disabled", true);
}

//解析树 返回数据
function ajaxDataFilter(treeId, parentNode, responseData) {
    var tree;
    if (responseData.retState == "0") {
        tree = responseData.retData;
    }
    return tree;
};

function isJSON(str) {
  if (typeof str == 'string') {
      try {
          JSON.parse(str);
          return true;
      } catch(e) {
          return false;
      }
  }
}
