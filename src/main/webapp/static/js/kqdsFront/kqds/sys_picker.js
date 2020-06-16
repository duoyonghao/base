/****************************** 人员选择相关 ****************************************/

var userRetNameArray = null; // 单选
var userRetNameArrayMulti = null; // 多选
var userRetNameArrayDeptId = null; // 部门ID数组选择
var deptRetNameArray = null; // 单选
var deptRetNameArrayMulti = null; // 多选
var dictRetNameArrayMulti = null; // 多选
/**
 * 单选人员(showleave 0 或空 不显示离职人员  1显示离职人员)
 */
function single_select_user(retArray, isSingle,showleave) {
    userRetNameArray = retArray;
    var url = contextPath + "/YZPersonAct/toSingleIndex.act?1=1&showleave="+showleave;
    if ('single' == isSingle) {
        url += '&isSingle=single';
    }

    openLayerDialogResize(url, 530, 400); // layer.open方式打开，实现方式为iframe
}

/**
 * 多选人员
 */
function multi_select_user(retArray) {
    userRetNameArrayMulti = retArray;
    var url = contextPath + "/YZPersonAct/toMultiLeft.act";
    openLayerDialogResize(url, 530, 400); // layer.open方式打开，实现方式为iframe
}

/** 可见人员条件过滤 **/
function multi_select_userVisual(retArray) {
    userRetNameArrayMulti = retArray;
    var url = contextPath + "/YZPersonAct/toMultiLeft.act?isFilterByVisualStaff=isFilterByVisualStaff";
    openLayerDialogResize(url, 530, 400); // layer.open方式打开，实现方式为iframe
}
/**
 * 多选人员
 */
function multi_select_hz(retArray) {
    userRetNameArrayMulti = retArray;
    var url = contextPath + "/YZPersonAct/toHzSelectIndex.act";
    openLayerDialogResizeNosize(url, '80%', '80%'); // layer.open方式打开，实现方式为iframe
}
/**
 * 根据部门ID数组，选择人员【挂号页面，高级选择使用】
 * @param retArray
 */
function deptid_select_user(retArray, deptIds) {
    userRetNameArrayDeptId = retArray;
    var url = contextPath + "/YZPersonAct/toIdIndex.act?deptIds=" + deptIds;
    openLayerDialogResize(url, 530, 400); // layer.open方式打开，实现方式为iframe
}

/**
 * 多选部门
 * @param retArray
 */
function multi_select_dept(retArray) {
    deptRetNameArrayMulti = retArray;
    var url = contextPath + "/YZDeptAct/toSelectMultiIndex.act";
    openLayerDialogResizeDept(url, 530, 400); // layer.open方式打开，实现方式为iframe
}


/**
 * 多选数据字典-收费项目分类
 * @param retArray
 */
function multi_select_dict(retArray) {
	dictRetNameArrayMulti = retArray;
    var url = contextPath + "/YZDictAct/toSelectMultiIndex.act";
    openLayerDialogResizeDept(url, 530, 400); // layer.open方式打开，实现方式为iframe
}

/****************************** 人员选择相关 END ****************************************/