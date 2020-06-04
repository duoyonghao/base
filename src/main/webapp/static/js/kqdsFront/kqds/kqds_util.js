/**
 * 更改特定数据的禁用 启用状态
 * @param tableName
 * @param seqId
 * @param useFlag
 */
function changeUseFlag(tableName, seqId, useFlag) {

    var notemsg = "启用";
    if (useFlag == 1) {
        notemsg = "禁用";
    }

    //询问框
    layer.confirm('确定' + notemsg + '？', {
        btn: ['确定', '放弃'] //按钮
    },
    function() {
        var url = contextPath + '/UtilAct/changeUseFlag.act?tableName=' + tableName + '&seqId=' + seqId + '&useFlag=' + useFlag;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('操作成功', {
                      
                });
                refresh();
            }
        },
        function() {
            layer.alert('操作失败！'  );
        });
    });
}

/**
 * 表单提交前验证合法性
 * @returns {Boolean}
 */
function formSubmitValidate(formid) {
    var returnbool = true;
    var data = $('#' + formid).data('bootstrapValidator');
    if (data) {
        // 修复记忆的组件不验
        data.validate(); // 手动对表单进行校验，经过测试，不写此方法，直接执行isValid()方法也可行 
        if (!data.isValid()) {
            returnbool = false;
        }
    }
    return returnbool;
}

/**
 * 六位随机数
 * @returns {String}
 */
function sixRandom() {
    var mm = Math.random();
    var six = "";
    if (mm > 0.1) {
        six = Math.round(mm * 1000000);
    } else {
        mm += 0.1;
        six = Math.round(mm * 1000000);
    }
    return six;
}