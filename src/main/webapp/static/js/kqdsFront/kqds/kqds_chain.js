

/**
 * 根据id 初始化门诊下拉框，用于口腔大师前端页面
 * @param id
 */
function initHosSelectList4Front(id, defVal) {
    var defaultVal = ""; // 默认选中值
    var serverData = getDataFromServer("ChainAct/getHosList.act");

    if (defVal) {
        defaultVal = defVal;
    } else {
        if (serverData.current) {
            defaultVal = serverData.current; // 当前用户所登录的门诊
        }
    }

    initSelectByList(id, serverData.list, 'deptCode', 'deptName', defaultVal);

}

/**
 * 门诊列表，不做权限过滤，比如网电建档
 * @param id
 * @param defVal
 */
function initHosSelectListNoCheck(id, defVal) {
    var defaultVal = ""; // 默认选中值
    var serverData = getDataFromServer("ChainAct/getHosList.act?nocheck=nocheck");

    if (defVal) {
        defaultVal = defVal;
    } else {
        if (serverData.current) {
            defaultVal = serverData.current; // 当前用户所登录的门诊
        }
    }

    initSelectByList(id, serverData.list, 'deptCode', 'deptName', defaultVal);

}

/**
 * 数据字典，创建时，可选择公用选项
 * @param id
 * @param defVal
 */
function initHosSelectListNoCheckWithCommon(id, defVal) {
    var defaultVal = ""; // 默认选中值
    var serverData = getDataFromServer("ChainAct/getHosList.act?nocheck=nocheck");

    if (defVal) {
        // defaultVal = defVal;
    } else {
        if (serverData.current) {
            // defaultVal = serverData.current; // 当前用户所登录的门诊
        }
    }

    var comObj = new Object(); // 构建对象
    comObj.deptCode = "";
    comObj.deptName = "公用";
    
    var array = serverData.list;
    array.unshift(comObj); // 数组前添加一个或多个元素
    
    initSelectByList(id, serverData.list, 'deptCode', 'deptName', defaultVal);

}

/**
 * 下拉框包含 请选择 选项
 * @param id
 * @param defVal
 */
function initHosSelectListNoCheckWithNull(id, defVal) {
    var defaultVal = ""; // 默认选中值
    var serverData = getDataFromServer("ChainAct/getHosList.act?nocheck=nocheck");

    if (defVal) {
        defaultVal = defVal;
    }

    initSelectByListWithNull(id, serverData.list, 'deptCode', 'deptName', defaultVal);

}


//根据门诊编号获取门诊信息
function getOrganizationInfoByOrgCode(organization) {
    var serverData = getDataFromServer("ChainAct/getOrganizationInfoByOrgCode.act?organization=" + organization);
    if (serverData) {
        return serverData.data;
    } else {
        return null;
    }
}