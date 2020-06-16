
function handleAjax(url, param, type) {
    return ajax(url, param, type).then(function(resp) {
        // 成功回调
        if (resp.result) {
            return resp.data; // 直接返回要处理的数据，作为默认参数传入之后done()方法的回调
        } else {
            return $.Deferred().reject(resp.msg); // 返回一个失败状态的deferred对象，把错误代码作为默认参数传入之后fail()方法的回调
        }
    },
    function(err) {
        // 失败回调
    });
}
/**
 * ajax封装 url 发送请求的地址 data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(),
 * "state": 1} dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text successfn 成功回调函数
 * errorfn 失败回调函数
 */
$.axseSubmit = function(url, data, beforeSubmitfn, successfn, errorfn) {
    data = (data == null || data == "" || typeof(data) == "undefined") ? {
        "date": new Date().getTime()
    }: data;
    $.ajax({ // Jquery中的ajax在默认不写async情况下，请求为异步请求；即：async:true;
        type: "post",
        traditional: true,//传参为数组时，属性在这里设置，后台接受数组格式数据
        data: data,
        url: url,
        async: false,
        // ################## modify by yangsen 2017-6-28 11:31 改为同步提交，避免连续点击导致多次提交!!
        // 默认为true，异步
        dataType: "json",
        beforeSubmit: function() { // -----------------------------------------这个方法没有生效
            beforeSubmitfn();
        },
        success: function(data) {
            successfn(data);
        },
        error: function(e) {
            errorfn(e);
        }
    });
};

/**
 * 同步ajax请求方法，没有beforeSubmit参数
 */
$.axse = function(url, data, successfn, errorfn) {
    data = (data == null || data == "" || typeof(data) == "undefined") ? {
        "date": new Date().getTime()
    }: data;
    $.ajax({
        type: "post",
        traditional: true,//传参为数组时，属性在这里设置
        data: data,
        url: url,
        async: false,
        // async是异步的意思，设置为false则该方法是同步的
        dataType: "json",
        success: function(data) {
            successfn(data);
        },
        error: function(e) {
            errorfn(e);
        }
    });
};
/**
 * 异步ajax请求方法，没有beforeSubmit参数
 */
$.axseY = function(url, data, successfn, errorfn) {
    data = (data == null || data == "" || typeof(data) == "undefined") ? {
        "date": new Date().getTime()
    }: data;
    $.ajax({
        type: "post",
        data: data,
        url: url,
        async: true,
        // async是异步的意思，设置为false则该方法是同步的
        dataType: "json",
        success: function(data) {
            successfn(data);
        },
        error: function(e) {
            if (errorfn) { // 增加容错处理
                errorfn(e);
            }
        }
    });
};

/**
 * 根据请求的Url 从服务器端获取json object
 * @param id
 * @param reqUrl
 */
function getDataFromServer(reqUrl, params) {
    var url = contextPath + "/" + reqUrl;
    var serverData = null;

    $.axse(url, params,
    function(data) {
    	//console.log("登录状态="+JSON.stringify(data));
        if (data.retState == "0") {
            serverData = data;
        }else if(data.retState == "2"){
        	serverData = data;
        }else {
            if (data.retMsrg) {
                layer.alert(data.retMsrg  );
            } else if (data.rtMsrg) {
                layer.alert(data.rtMsrg  );
            } else {
                layer.alert("从服务器获取数据失败"  );
            }

        }
    },
    function() {
    	layer.alert("网络错误！", {
    		end : function(){
    			logout();
    	}});
    });

    return serverData;
}

/**
 * 根据请求的Url 从服务器端获取json object
 * @param id
 * @param reqUrl
 */
function getDataFromServerNoAlert(reqUrl, params) {
    var url = contextPath + "/" + reqUrl;
    var serverData = null;

    $.axse(url, params,
    function(data) {
    	serverData = data;
    },
    function() {
    	layer.alert("网络错误！", {
    		end : function(){
    			logout();
    	}});
    });
    return serverData;
}

/**
 * 异步请求
 * @param reqUrl
 * @param params
 * @returns
 */
function getDataFromServerY(reqUrl, params) {
    var url = contextPath + "/" + reqUrl;
    var serverData = null;

    $.axseY(url, params,
    function(data) {
        if (data.retState == "0") {
            serverData = data;
        } else {
            if (data.retMsrg) {
                layer.alert(data.retMsrg  );
            } else if (data.rtMsrg) {
                layer.alert(data.rtMsrg  );
            } else {
                layer.alert("从服务器获取数据失败"  );
            }

        }
    },
    function() {
        layer.alert("网络错误！", {
    		end : function(){
    			logout();
    	}});
    });

    return serverData;
}
//tologin
function tologin(){
	var isapp = localStorage.getItem("isapp");
    var tmpParam = "";
    if (isapp == '1') {
        tmpParam = "?isapp=1";
    }
    localStorage.setItem("isapp", '0'); // 要清空才行
    window.location.replace(contextPath + "/login.jsp" + tmpParam);
}

/**
 * 根据请求的Url 从服务器端获取json object list 
 * @param id
 * @param reqUrl
 */
function getDataListFromServer(reqUrl, params) {
    var url = contextPath + "/" + reqUrl;
    var serverData = null;

    $.axse(url, params,
    function(data) {
        if (data) {
            serverData = data;
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return serverData;
}


/***********************通用删除************************************/
function deleteByUrlCommon(requrl, delMsg) {
    var returnObj = null;
    var noteMsg = "确定删除？";
    if (delMsg) {
        noteMsg = delMsg;
    }
    // 点击确定，会执行 r == true内的方法，点击取消，会直接返回  returnObj，也就是 null
    // 这里不要使用 layer.confirm，这个方法是非阻塞的，会先return ，点击确定后，再执行 确定对应的 代码段
    var r = confirm(noteMsg);
    if (r == true) {
        $.axse(requrl, null,
        function(data) {
            returnObj = data;
            // return data; 这里及时直接返回，还是会执行 if(r == true) 代码段后面的方法，所以不要return 
        },
        function() {
            layer.alert('删除失败！'  );
        });
    }
    return returnObj;
}

