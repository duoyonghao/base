var static_personTree_inputId = null; // 这个字段是存储已选中node的id值的input，用于编辑页面时，反向赋值
var personTree_url = contextPath + '/YZPersonAct/getPersonTree.act';
var personTree_setting = {
    view: {
        showIcon: true // 去掉图标
    },
    async: {
        enable: true,
        url: personTree_url,
        autoParam: ["id", "name=n", "level=lv"],
        otherParam: {
            "otherParam": "zTreeAsyncTest"
        },
        dataFilter: personTree_ajaxDataFilter,
        type: "get"
    }
};
//解析树 返回数据
function personTree_ajaxDataFilter(treeId, parentNode, responseData) {
    var tree;
    if (responseData.retState == "0") {
        tree = responseData.retData;
    }

    // 编辑时，反向赋值
    var tmpIdStr = null;
    if (static_personTree_inputId) {
        tmpIdStr = $("#" + static_personTree_inputId).val();
    }
    if (tmpIdStr) {
        var idArray = tmpIdStr.split(",");
        for (var i = 0; i < tree.length; i++) {
            var id = (tree[i].id).replace("person", ""); // 去掉人员id前面的person
            if (isStrInArray(id, idArray)) {
                tree[i].checked = true;
            } else {
                tree[i].checked = false;
            }
        }
    }

    return tree;
};

function personTree_onBodyDown(event) {
    var treeId = event.data;
    if (! (event.target.id == treeId || $(event.target).parents("#" + treeId).length > 0)) {
        personTree_hideMenu(treeId);
    }
}

function personTree_hideMenu(treeId) {
    $("#" + treeId).fadeOut("fast");
    $("body").unbind("mousedown", personTree_onBodyDown);

    if (static_personTree_inputId) {
        static_personTree_inputId = null; // 树隐藏后，赋null
    }
}

/**
 * 调用方法_显示人员树
 * @param hiddenId  存放数的id值
 */
function showPersonTree(selectId, treeId, hiddenId) {
    var selectObj = $("#" + selectId);
    var selectOffset = $("#" + selectId).offset();
    $("#" + treeId).css({
        left: selectOffset.left + "px",
        top: selectOffset.top + selectObj.outerHeight() + "px"
    }).slideDown("fast");

    // 传参
    $("body").bind("mousedown", treeId, personTree_onBodyDown);

    if (hiddenId) {
        static_personTree_inputId = hiddenId;
    }
}

/**
 * 调用方法_初始化树 ####################################### 单选
 * @param treeId
 * @param clickFunc  点击事件调用的方法
 */
function initPersonTree(treeId, clickFunc, inputId, hiddenId) {
    personTree_setting['callback'] = {
        onClick: clickFunc
    };
    $.fn.zTree.init($("#" + treeId), personTree_setting);

    // 浮动层随页面缩放而调整
    $(window).resize(function() {
        var display = $('#' + treeId).css('display');
        if (display != 'none') {
            showPersonTree(inputId, treeId, hiddenId);
        }
    });
}

/**
 * 调用方法_初始化树 ####################################### 多选
 * @param treeId
 * @param clickFunc  点击事件调用的方法
 * @inputId 的作用是为了窗口resize时，能实时调整树的位置
 */
function initPersonTreeMulti(treeId, checkFunc, inputId, hiddenId) {
    personTree_setting['callback'] = {
        onClick: function(e, treeId, treeNode) { // 多选树的点击事件
            if (treeNode.isParent) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                zTree.expandNode(treeNode);
            }
        },
        onCheck: checkFunc
    };
    personTree_setting['view'] = {
        showLine: false,
        selectedMulti: true,
        dblClickExpand: false
    };
    personTree_setting['check'] = {
        chkStyle: "checkbox",
        enable: true
    };
    $.fn.zTree.init($("#" + treeId), personTree_setting);

    // 浮动层随页面缩放而调整
    $(window).resize(function() {
        var display = $('#' + treeId).css('display');
        if (display != 'none') {
            showPersonTree(inputId, treeId, hiddenId);
        }
    });
}

/** ######################################### 赋值相关 **/
/**
 * 获取多选树，选中的节点name和id，以对象形式返回
 */
function getCheck4PersonTreeMulti(treeId) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    var checkedNodes = zTree.getCheckedNodes(true);
    var selectId = '';
    var selectName = '';
    for (var i = 0; i < checkedNodes.length; i++) {
        var checkNode = checkedNodes[i];
        if ((checkNode.id).indexOf('person') != -1) { // 只取人员 值,不取部门的
            var id = (checkNode.id).replace('person', '');
            selectId += id + ",";
            selectName += checkNode.name + ",";
        }
    }
    if (selectId.length > 0) {
        selectId = selectId.substring(0, selectId.length - 1);
    }
    if (selectName.length > 0) {
        selectName = selectName.substring(0, selectName.length - 1);
    }

    var dataObj = new Object();
    dataObj.selectId = selectId;
    dataObj.selectName = selectName;
    return dataObj;
}

/**
 * 人员树，单选
 * @param treeId
 * @returns {___dataObj1}
 */
function getClick4PersonTreeSingle(treeId, treeNode) {
    var dataObj = null;
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        zTree.expandNode(treeNode);
    } else {
        if (treeNode.id.indexOf('person') != -1) {
            dataObj = new Object();
            dataObj.clickId = (treeNode.id).replace('person', '');
            dataObj.clickName = treeNode.name;

            // 隐藏
            personTree_hideMenu(treeId);
        }
    }
    return dataObj;
}