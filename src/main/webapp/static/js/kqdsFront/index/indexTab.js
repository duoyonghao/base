/**
 * 此页面供主页面调用，主要方法为 Tab页的 网电预约、门诊预约、等待治疗、等待结账、已结账等使用
 * 此js被 jdzx_center.jsp ylzx_center.jsp zxzx_center.jsp 引用
 */

// 每个主界面的默认tab选中事件
function initclick() {
    var menuindex = 0;
    //console.log("权限标号=-=-=-"+personrole);
    if (personrole == "fa65e134-68e8-4025-bde5-37e50ef5f60c") { // 收费的
        menuindex = 4; // 默认打开等待结账
    } else {
        menuindex = 3; // 其他角色，打开等待治疗
    }
    var li = $(".columnBd").children("ul").children("li").eq(menuindex);
    li.attr({
        "class": "current"
    });

    li.trigger("click");
}

function initclick1() {
    //跳转等待治疗页面
    var li = $(".columnBd").children("ul").children("li").eq(2);
    li.attr({
        "class": "current"
    });

    li.trigger("click");
}

// 查询待办数量  ---------------------------- 应该改用定时器来进行计算
function initcount() {
    // 网电
    var wquerycounturl2 = "";
    if (wquerycounturl.indexOf("?") > 0) {
        wquerycounturl2 = wquerycounturl + '&searchValue=' + $("#searchValue").val();
    } else {
        wquerycounturl2 = wquerycounturl + '?searchValue=' + $("#searchValue").val();
    }
    $.axseY(wquerycounturl2, null,
        function (data) {
            // 网电
            $("#wdataCount").html(data.wdataCount);
            // 门诊
            $("#mdataCount").html(data.mdataCount);
            //手術
            $("#ssdataCount").html(data.ssdataCount);
            // 等待治疗
            $("#tdataCountd").html(data.tdataCountd);
            // 等待结账
            $("#ddataCount").html(data.ddataCount);
            // 已结账
            $("#ydataCount").html(data.ydataCount);
            // 今日患者
            $("#tdataCountj").html(data.tdataCountj);
        },
        function () {
        });
}

//-------------------------  接待中心 医疗中心  咨询中心  三个页面，统一调用 setHeight setWidth 方法
function setHeight() {

    // 这个页面，屏幕小会有横向滚动条，这个滚动条的高度是15
    // 这里可以可以根据屏幕分辨率，计算是否会出现滚动条
    var scrollHeight = 15;
    var windowHeight = $(window).height();
    var baseHeight = $(window).height() - 15;
    optAreaHeight = $('.operateModel').outerHeight();
    $('.lookInfoWrap .columnWrap').height(baseHeight); //-scrollHeight
    $('.lookInfoWrap .columnWrap .columnBd').outerHeight(baseHeight);
    //重置table的高度
    $("#table").bootstrapTable("resetView", {
        height: $(window).outerHeight() - $(".columnWrap .columnHd").outerHeight() - $("#gongzuol").outerHeight() - $(".operateModel").outerHeight() - $("#menuul").outerHeight() - 33
    });
    $('.tabIframe').height(baseHeight - 60);

}

/** ## 解决重复切换Tab 花屏的问题，经过定位发现，导致这种错误的原因是切换Tab导致 container 的宽度发生变化
 *  切换Tab都执行 setHeight方法，所以把重新设置 container的宽度代码放到 setHeight方法中执行   yangsen 17-4-26
 *  **/

var static_container_Width = null;

//计算主体的宽度
//setWidth()方法 抽到 tableHeaderWidth.js  文件里面了       将用到该方法的页面统一
/*
 * 门诊预约
 * param {
 * 	type:类型
 * 	id:'当前tab唯一ID'
 * }
 */
function hospitalOrderTable(type, tabId) {
    sessionStorage.setItem('tabID', tabId)
    onclickrowOobj = "";
    toptableclickval = 0;
    $("#gongzuol").css("display", "block");
    $("#dataCount").html("");

    initcount();

    var pageurl = contextPath + '/KQDS_Hospital_OrderAct/selectHospitalOrderList.act?querytype=' + type + '&searchValue=' + $("#searchValue").val();

    var tableObj = $('#table').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (pageurl == tableObj.url) { // 重复点击
            $('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
            return;
        } else { // 切换Tab
            $('#table').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }

    function queryParams(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset, //页码 
            sortName: this.sortName,
            sortOrder: this.sortOrder,
            pageIndex: params.offset / params.limit + 1,
        };
        return temp;
    }

    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",   //必须有
        queryParams: queryParams,
        cache: false,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList: [15, 20, 25],//可以选择每页大小
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页 
        onDblClickCell: function (field, value, row, td) {
            if (field == "iscreatelclj" && row.iscreatelclj != "" && row.iscreatelclj != null) {
                parent.Catalogue()
                parent.secondLevelDirectory()
                //console.log("---"+JSON.stringify(row));
                window.location.href = contextPath + '/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&userId=' + row.usercode + '&&username=' + row.username;
            }

        },
        onLoadSuccess: function (data) {
//            var tableList = data.rows;
            // 总记录数：    本次上门：   未上门：    成交：   未成交：
//            var sms = 0,
//            wsms = 0,
//            cjs = 0,
//            wcjs = 0;
//            for (var i = 0; i < tableList.length; i++) {
//                if (tableList[i].cjstatus == "1") {
//                    cjs = cjs + 1;
//                } else {
//                    wcjs = wcjs + 1;
//                }
//                if (tableList[i].orderstatus == "1") {
//                    sms = sms + 1;
//                } else {
//                    wsms = wsms + 1;
//                }
//            }
            /*var content ='<tr>'
             content += '<td style="width:12%;"><span style="color:blue;">总记录数:<lable>'+tableList.length+'</lable></span></td>';
             content += '<td style="width:12%;"><span style="color:blue;">本次上门数:<lable>'+sms+'</lable></span></td>';
             content += '<td style="width:12%;"><span style="color:blue;">未上门数:<lable>'+wsms+'</lable></span></td>';
             content += '<td style="width:12%;"><span style="color:blue;">成交数:<lable>'+cjs+'</lable></span></td>';
             content += '<td style="width:12%;"><span style="color:blue;">未成交数:<lable>'+wcjs+'</lable></span></td>';
             content += '</tr>';*/
            if (data.offset == 0) {
                $("#dataCount").html("");
                var content = '';
                content += '<li>总记录数:<span>' + data.total + '</span></li>';
                content += '<li>本次上门数:<span>' + data.sms + '</span></li>';
                content += '<li>未上门数:<span>' + (Number(data.total) - Number(data.sms)) + '</span></li>';
                content += '<li>成交数:<span>' + data.cjs + '</span></li>';
                content += '<li>未成交数:<span>' + (Number(data.total) - Number(data.cjs)) + '</span></li>';
                $("#dataCount").append(content);
            }
            setHeight();
            /*滚动条出现 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [
            {
                title: '序号',
                align: "center",
                width: 40,
                formatter: function (value, row, index) {
                    /* return index + 1; */
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                    return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                }
            },

            /*{
                title: '当前门诊',
                field: 'organizationname',
                align: 'center',
                valign: 'middle',
                sortable: true

            },*/
            {
                title: '预约咨询/医生',
                field: 'askpersonname',
                align: 'center',
                sortable: true,

                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },
            {
                title: '上门状态',
                field: 'zdoorstatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span style="color:green">已上门</span>';
                    } else {
                        return '<span style="color:red">未上门</span>';
                    }
                }
            },
            {
                title: '本次上门',
                field: 'orderstatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span style="color:green">已上门</span>';
                    } else {
                        return '<span style="color:red">未上门</span>';
                    }
                }
            },
            {
                title: '本次成交',
                field: 'cjstatus',
                align: 'center',
                sortable: true,
                formatter: function (value, s, index) {
                    if (value == 1) {
                        return '<span class="label label-success">已成交</span>';
                    } else {
                        return '<span class="label label-danger">未成交</span>';
                    }
                }
            },
            {
                title: '患者编号',
                field: 'usercode',
                align: 'center',
                visible: true,
                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time">' + value + '</span>';
                    }
                }
            },
            {
                title: '患者姓名',
                field: 'username',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="name">' + value + '</span>';
                    }
                }
            },
            {
                title: '患者标识',
                field: 'iscreatelclj',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    //console.log("门诊预约=" + JSON.stringify(row));
                    var iconhtml;
                    if (value != "" && value != null) {
                        iconhtml = '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
                    }
                    if (row.kefu) {
                        iconhtml += '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/customerservice.jpg/>';
                    }
                    return iconhtml;
                }
            },
            {
                title: '起始时间',
                field: 'starttime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time">' + value.substring(5) + '</span>';
                    }
                }
            },
            {
                title: '结束时间',
                field: 'endtime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time">' + value.substring(5) + '</span>';
                    }
                }
            },
            {
                title: '预约分类',
                field: 'ordertypename',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span>' + value + '</span>';
                    }
                }
            },
            {
                title: '预约项目分类',
                field: 'orderitemtypename',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span>' + value + '</span>';
                    }
                }
            },
            {
                title: '挂号导医',
                field: 'receiveno',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="name">' + value + '</span>';
                    } else {
                        return '<span>-</span>';
                    }
                }
            },
            {
                title: 'seqId',
                field: 'seqId',
                align: 'center',
                visible: false,
                switchable: false
            }]
    }).on('click-row.bs.table',
        function (e, row, element) {
            consultSelect = row;
            $('.success').removeClass('success'); // 去除之前选中的行的，选中样式
            $(element).addClass('success'); // 添加当前选中的 success样式用于区别
            var index = $('#table').find('tr.success').data('index'); // 获得选中的行
            onclickrowOobj = $('#table').bootstrapTable('getData')[index];
            // showpersoninfo(onclickrowOobj);//展示右侧个人信息
            $('#tabIframe').attr('src', $('#tabIframe').attr('src')); // 个人中心
            //togglemodel.switchDiv();
        });
}

// 网电预约
function netOrderTable(type, tabId) {
    sessionStorage.setItem('tabID', tabId)
    onclickrowOobj = "";
    toptableclickval = 0;
    $("#gongzuol").css("display", "block");
    $("#dataCount").html("");

    initcount();

    var pageurl = contextPath + '/KQDS_Net_OrderAct/selectNetOrderList.act?querytype=' + type + '&searchValue=' + $("#searchValue").val();

    var tableObj = $('#table').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (pageurl == tableObj.url) { // 重复点击
            $('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
            return;
        } else { // 切换Tab
            $('#table').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }

    function queryParams(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset, //页码 
            sortName: this.sortName,
            sortOrder: this.sortOrder,
            pageIndex: params.offset / params.limit + 1,
        };
        return temp;
    }

    $('#table').bootstrapTable({
        url: pageurl,
        queryParams: queryParams,
        dataType: "json",
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList: [10, 15, 20, 25],//可以选择每页大小
        //clickToSelect: false,
        singleSelect: true,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        paginationShowPageGo: true,
        onDblClickCell: function (field, value, row, td) {
            if (field == "iscreatelclj" && row.iscreatelclj != "" && row.iscreatelclj != null) {
                parent.Catalogue()
                parent.secondLevelDirectory()
                window.location.href = contextPath + '/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&username=' + row.username + '&&userId=' + row.usercode;
            }
        },
        onLoadSuccess: function (data) {
//            var tableList = data;
//            // 总记录数：    本次上门：   未上门：    成交：   未成交：
//            var sms = 0,
//            wsms = 0,
//            cjs = 0,
//            wcjs = 0;
//            for (var i = 0; i < tableList.length; i++) {
//                if (tableList[i].cjstatus == "1") {
//                    cjs = cjs + 1;
//                } else {
//                    wcjs = wcjs + 1;
//                }
//                if (tableList[i].doorstatus == "1") {
//                    sms = sms + 1;
//                } else {
//                    wsms = wsms + 1;
//                }
//            }
            $("#dataCount").html("");
            var content = '';
            content += '<li>总记录数:<span>' + data.total + '</span></li>';
            content += '<li>本次上门数:<span>' + data.cjstatus + '</span></li>';
            content += '<li>未上门数:<span>' + (Number(data.total) - Number(data.cjstatus)) + '</span></li>';
            content += '<li>成交数:<span>' + data.doorstatus + '</span></li>';
            content += '<li>未成交数:<span>' + (Number(data.total) - Number(data.doorstatus)) + '</span></li>';
            $("#dataCount").append(content);

            setHeight();
            /*滚动条出现*/
            setTableHeaderWidth(".tableBox");
        },
        rowStyle: function (row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isdelete == "1") {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
            {
                title: '序号',
                align: "center",
                width: 40,
                formatter: function (value, row, index) {
                    /* return index + 1; */
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                    return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                }
            },

            /*{
                title: '当前门诊',
                field: 'organizationname',
                align: 'center',
                valign: 'middle',
                sortable: true

            },*/
            {
                title: '上门状态',
                field: 'zdoorstatus',
                align: 'center',
                sortable: true,

                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span style="color:green">已上门</span>';
                    } else {
                        return '<span style="color:red">未上门</span>';
                    }
                }
            },
            {
                title: '本次上门',
                field: 'doorstatus',
                align: 'center',
                sortable: true,

                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span style="color:green">已上门</span>';
                    } else {
                        return '<span style="color:red">未上门</span>';
                    }
                }
            },
            {
                title: '本次成交',
                field: 'cjstatus',
                align: 'center',
                sortable: true,

                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">已成交</span>';
                    } else {
                        return '<span class="label label-danger">未成交</span>';
                    }
                }
            },
            {
                title: '预约时间',
                field: 'ordertime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (row.ordertime != "" && row.ordertime != null) {
                        var html = row.ordertime.substring(5);
                        return '<span>' + html + '</span>';
                    }
                }
            },
            {
                title: '患者编号',
                field: 'usercode',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='time'>" + value + "</span>";
                }
            },
            {
                title: '患者姓名',
                field: 'username',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '患者标识',
                field: 'iscreatelclj',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    //console.log("网电预约=" + JSON.stringify(row));
                    var iconhtml;
                    if (value != "" && value != null) {
                        iconhtml = '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
                    }
                    if (row.kefu) {
                        iconhtml += '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/customerservice.jpg/>';
                    }
                    return iconhtml;
                }
            },
            {
                title: '咨询日期',
                field: 'acceptdate',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span  class="time">' + value + '</span>';
                    }
                }
            },
            {
                title: '受理电话',
                field: 'acceptphone',
                align: 'center',
                visible: false,
                switchable: false,
                formatter: function (value, row, index) {
                    return "<span id='" + pjid + "' class='name'></span>";
                }
            },
            {
                title: '受理类型',
                field: 'accepttypename',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span>' + value + '</span>';
                    }
                }
            },
            {
                title: '受理工具',
                field: 'accepttoolname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span>' + value + '</span>';
                    }
                }
            },
            {
                title: '咨询项目',
                field: 'askitemname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span>' + value + '</span>';
                    }
                }
            },
            {
                title: '安排科室',
                field: 'regdeptname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span>' + value + '</span>';
                    }
                }
            },
            {
                title: '咨询',
                field: 'askpersonname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span  class="name">' + value + '</span>';
                    }
                }
            },
            {
                title: '医生',
                field: 'orderpersonname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span  class="name">' + value + '</span>';
                    }
                }
            },
            {
                title: '导医',
                field: 'daoyiname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span  class="name">' + value + '</span>';
                    }
                }
            },
            {
                title: '建档时间',
                field: 'jdtime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span  class="jdtime">' + value + '</span>';
                    }
                }
            },
            {
                title: '预约状态',
                field: 'isdelete',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == 1) {
                        html = '<span class="name" style="color:red;">已取消</span>';
                        return html;
                    } else {
                        return "<span class='name'>正常</span>";
                    }
                }
            }]
    }).on('click-row.bs.table',
        function (e, row, element) {
            //console.log("网电预约---单击事件");
            $('.success').removeClass('success'); // 去除之前选中的行的，选中样式
            $(element).addClass('success'); // 添加当前选中的 success样式用于区别
            var index = $('#table').find('tr.success').data('index'); // 获得选中的行
            onclickrowOobj = $('#table').bootstrapTable('getData')[index];
            //console.log("选中行数据===="+ononclickrowOobj);
            $('#tabIframe').attr('src', $('#tabIframe').attr('src')); // 个人中心
        }).on('dbl-click-cell.bs.table', function (e, row, element) {
        //console.log("网电预约---双击事件");
        //togglemodel.switchDiv();
    });
}

/**
 * 等待结账
 * @param status
 * @param type
 */
function getOrderlist(status, type, tabId) {
    sessionStorage.setItem('tabID', tabId)
    onclickrowOobj = "";
    toptableclickval = 2;
    $("#gongzuol").css("display", "block");
    $("#dataCount").html("");

    initcount();

    var pageurl = contextPath + '/KQDS_CostOrderAct/getListByStatus.act?status=' + status + '&querytype=' + type + '&searchValue=' + $("#searchValue").val();

    var tableObj = $('#table').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (pageurl == tableObj.url) { // 重复点击
            $('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
            return;
        } else { // 切换Tab
            $('#table').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }

    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList: [15, 20, 25],//可以选择每页大小
        paginationShowPageGo: true,
        onDblClickCell: function (field, value, row, td) {
            if (field == "iscreatelclj" && row.iscreatelclj != "" && row.iscreatelclj != null) {
                parent.Catalogue()
                parent.secondLevelDirectory()
                window.location.href = contextPath + '/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&username=' + row.username + '&&userId=' + row.usercode;
            }
        },
        onLoadSuccess: function (data) {

            var tableList = data;
            var totalcost = 0;
            var voidmoney = 0;
            var shouldmoney = 0;
            var y2 = 0;
            var actualmoney = 0;
            for (var i = 0; i < tableList.length; i++) {
                totalcost += Number(tableList[i].totalcost);
                voidmoney += Number(tableList[i].voidmoney);
                shouldmoney += Number(tableList[i].shouldmoney);
                y2 += Number(tableList[i].y2);
                actualmoney += Number(tableList[i].actualmoney);
            }

            var content = '';
            content += '<li>合计:<span>' + Number(totalcost).toFixed(2) + '</span></li>';
            content += '<li>免除:<span>' + Number(voidmoney).toFixed(2) + '</span></li>';
            content += '<li>应收:<span>' + Number(shouldmoney).toFixed(2) + '</span></li>';
            content += '<li>欠费:<span>' + Number(y2).toFixed(2) + '</span></li>';
            content += '<li>缴费:<span>' + Number(actualmoney).toFixed(2) + '</span></li>';
            $("#dataCount").append(content);

            setHeight();
            /*滚动条出现*/
            setTableHeaderWidth(".tableBox");
        },
        columns: [
            {
                title: '序号',
                align: "center",
                width: 40,
                formatter: function (value, row, index) {
                    /* return index + 1; */
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                    return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                }
            },
            /*        {
                title: '当前门诊',
                field: 'organizationname',
                align: 'center',
                valign: 'middle',
                sortable: true
            },*/
            {
                title: '就诊分类',
                field: 'recesortname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span>' + value + '</span>';
                    }
                }

            },
            {
                title: '挂号分类',
                field: 'regsortname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return '<span>' + value + '</span>';
                    }
                }
            },
            {
                title: '患者编号',
                field: 'usercode',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='time'>" + value + "</span>";
                }
            },
            {
                title: '患者姓名',
                field: 'username',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            },
            {
                title: '患者标识',
                field: 'iscreatelclj',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    //console.log("等待结账=" + JSON.stringify(row));
                    var iconhtml;
                    if (value != "" && value != null) {
                        iconhtml = '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
                    }
                    if (row.kefu) {
                        iconhtml += '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/customerservice.jpg/>';
                    }
                    return iconhtml;
                }
            },
            {
                title: '合计',
                field: 'totalcost',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="money">￥' + value + '</span>';
                }
            },
            {
                title: '免除金额',
                field: 'voidmoney',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="money">￥' + value + '</span>';
                }
            },
            {
                title: '应收金额',
                field: 'shouldmoney',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="money">￥' + value + '</span>';
                }
            },
            {
                title: '欠费金额',
                field: 'y2',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (Number(value) != 0) {
                        return '<span class="money" style="color:red;">￥' + value + '</span>';
                    } else {
                        return '<span class="money">￥' + value + '</span>';
                    }
                }
            },
            {
                title: '缴费金额',
                field: 'actualmoney',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="money">￥' + value + '</span>';
                }
            },
            {
                title: '成交情况',
                field: 'status',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value >= 2) {
                        return '<span class="label label-success">已成交</span>';
                    } else if (value == 1) {
                        return '<span class="label label-info">已开单</span>';
                    } else {
                        return '<span class="label label-danger">未成交</span>';
                    }
                }
            },
            {
                title: '咨询',
                field: 'askpersonname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }

            },
            {
                title: '医生',
                field: 'doctorname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            //{title: '二期医生',field: 'techpersonname',align: 'center',valign: 'middle',sortable: true},
            {
                title: '挂号医生',
                field: 'regdoctorname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '客服',
                field: 'kefuname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '开单时间',
                field: 'createtime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time">' + value.substring(5) + '</span>';
                    }
                }
            },
            {
                title: '开单人',
                field: 'createusername',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },
            {
                title: '挂号人员',
                field: 'ghcreateusername',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },
            {
                title: '助理',
                field: 'nurse',
                align: 'center',
                visible: false,
                formatter: function (value) {
                    return "<span>" + value + "</span>"
                }
            },
            {
                title: '挂号导医',
                field: 'receiveno',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    } else {
                        return '<span>-</span>';
                    }
                }
            }]
    }).on('click-row.bs.table',
        function (e, row, element) {
            $('.success').removeClass('success'); // 去除之前选中的行的，选中样式
            $(element).addClass('success'); // 添加当前选中的 success样式用于区别
            var index = $('#table').find('tr.success').data('index'); // 获得选中的行
            onclickrowOobj = $('#table').bootstrapTable('getData')[index];

            $('#tabIframe').attr('src', $('#tabIframe').attr('src')); // 个人中心
            //togglemodel.switchDiv();
        });
}

// 已结账
function getPayOrderlist(type, tabId) {
    sessionStorage.setItem('tabID', tabId)
    onclickrowOobj = "";
    toptableclickval = 3;
    $("#gongzuol").css("display", "block");
    $("#dataCount").html("");

    initcount();

    // 后台action 根据isSelectCurrentOrg=1 ，给organization赋当前所在门诊的值，进行sql条件过滤
    var pageurl = contextPath + '/KQDS_CostOrderAct/getAllyjz.act?isSelectCurrentOrg=1&starttime=' + getNowFormatDate() + '&endtime=' + getNowFormatDate() + '&querytype=' + type + '&searchValue=' + $("#searchValue").val();

    var tableObj = $('#table').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (pageurl == tableObj.url) { // 重复点击
            $('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
            return;
        } else { // 切换Tab
            $('#table').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }

    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList: [15, 20, 25],//可以选择每页大小
        //sidePagination: "server",
        paginationShowPageGo: true,
        onDblClickCell: function (field, value, row, td) {
            if (field == "iscreatelclj" && row.iscreatelclj != "" && row.iscreatelclj != null) {
                parent.Catalogue()
                parent.secondLevelDirectory()
                window.location.href = contextPath + '/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&username=' + row.username + '&&userId=' + row.usercode;
            }
        },
        onLoadSuccess: function (data) {
            var tableList = data;
            var totalcost = 0;
            var voidmoney = 0;
            var shouldmoney = 0;
            var y2 = 0;
            var actualmoney = 0;
            for (var i = 0; i < tableList.length; i++) {
                totalcost += Number(tableList[i].totalcost);
                voidmoney += Number(tableList[i].voidmoney);
                shouldmoney += Number(tableList[i].shouldmoney);
                y2 += Number(tableList[i].y2);
                actualmoney += Number(tableList[i].actualmoney);
            }

            var content = '';
            content += '<li>合计:<span>' + Number(totalcost).toFixed(2) + '</span></li>';
            content += '<li>免除:<span>' + Number(voidmoney).toFixed(2) + '</span></li>';
            content += '<li>应收:<span>' + Number(shouldmoney).toFixed(2) + '</span></li>';
            content += '<li>欠费:<span>' + Number(y2).toFixed(2) + '</span></li>';
            content += '<li>缴费:<span>' + Number(actualmoney).toFixed(2) + '</span></li>';
            $("#dataCount").append(content);

            setHeight();
            /*滚动条出现*/
            setTableHeaderWidth(".tableBox");
        },
        rowStyle: function (row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (Number(row.actualmoney) < 0 || row.actualmoney.indexOf("-") >= 0) {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
            {
                title: '序号',
                align: "center",
                width: 40,
                formatter: function (value, row, index) {
                    /* return index + 1; */
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                    return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                }
            },
            /*        {
                title: '当前门诊',
                field: 'organizationname',
                align: 'center',
                valign: 'middle',
                sortable: true
            },*/
            {
                title: '收费时间',
                field: 'sftime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value.indexOf("null") > -1) {
                        return "";
                    } else {
                        var sftime = value.substring(0, 16);
                        return '<span class="time">' + sftime + '</span>';
                    }
                }
            },
            {
                title: '患者编号',
                field: 'usercode',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='time'>" + value + "</span>";
                }
            },
            {
                title: '患者姓名',
                field: 'username',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            },
            {
                title: '患者标识',
                field: 'iscreatelclj',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    //console.log("已结账=" + JSON.stringify(row));
                    var iconhtml;
                    if (value != "" && value != null) {
                        iconhtml = '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
                    }
                    if (row.kefu) {
                        iconhtml += '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/customerservice.jpg/>';
                    }
                    return iconhtml;
                }
            },
            {
                title: '成交情况',
                field: 'cjstatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == "已成交") {
                        return '<span class="label label-success">已成交</span>';
                    } else {
                        return '<span class="label label-danger">未成交</span>';
                    }
                }
            },
            {
                title: '合计',
                field: 'totalcost',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="money">￥' + value + '</span>';
                }
            },
            {
                title: '免除金额',
                field: 'voidmoney',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="money">￥' + value + '</span>';
                }
            },
            {
                title: '应收金额',
                field: 'shouldmoney',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="money">￥' + value + '</span>';
                }
            },
            {
                title: '欠费金额',
                field: 'y2',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (Number(value) != 0) {
                        return '<span class="money" style="color:red;">￥' + value + '</span>';
                    } else {
                        return '<span class="money">￥' + value + '</span>';
                    }
                }
            },
            {
                title: '缴费金额',
                field: 'actualmoney',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="money">￥' + value + '</span>';
                }
            },
            {
                title: '咨询',
                field: 'askperson',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },
            {
                title: '医生',
                field: 'doctorname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name'>" + value + "</span>";
                    }
                }
            },
            //{title: '二期医生',field: 'techperson',align: 'center',valign: 'middle',sortable: true},
            {
                title: '挂号医生',
                field: 'regdoctorname',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    if (value) {
                        return '<span>' + value + '</span>'
                    } else {
                        return '';
                    }
                }
            },
            {
                title: '客服',
                field: 'kefuname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '患者来源',
                field: 'devchannel',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return '<span>' + value + '</span>'
                }

            },
            {
                title: '来源子分类',
                field: 'nexttype',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="source">' + value + '</span>';
                }
            },
            {
                title: '开单时间',
                field: 'createtime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    html = '<span>' + value.substring(5) + '</span>';
                    return html;
                }
            },
            {
                title: '开单人',
                field: 'createuser',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '收费人',
                field: 'sfuser',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '挂号分类',
                field: 'regsort',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '挂号人员',
                field: 'ghcreateuser',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '挂号导医',
                field: 'receiveno',
                align: 'center',
                sortable: true,

                formatter: function (value, row, index) {
                    if (value) {
                        return '<span class="name" title="' + value + '">' + value + '</span>';
                    } else {
                        return '<span class="name">-</span>';
                    }
                }
            },
            {
                title: '建档人',
                field: 'jduser',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span class='name'>" + value + "</span>";
                }
            },
            {
                title: '建档时间',
                field: 'jdtime',
                align: 'center',
                sortable: true,

                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        html = '<span class="time">' + value + '</span>';
                        return html;
                    }
                }
            },
            {
                title: 'seqId',
                field: 'seqId',
                align: 'center',
                visible: false,
                switchable: false
            }]
    }).on('click-row.bs.table',
        function (e, row, element) {
            $('.success').removeClass('success'); // 去除之前选中的行的，选中样式
            $(element).addClass('success'); // 添加当前选中的 success样式用于区别
            var index = $('#table').find('tr.success').data('index'); // 获得选中的行
            onclickrowOobj = $('#table').bootstrapTable('getData')[index];
            // showpersoninfo(onclickrowOobj);//展示右侧个人信息
            $('#tabIframe').attr('src', $('#tabIframe').attr('src')); // 个人中心
            //togglemodel.switchDiv();
        });

    $(".columns").hide();
}

/**
 *
 * @param status
 *            0 等待治疗/挂号   1 已开单 2 已缴费 5今日患者
 * @param type
 *            all 查当天所有， 否则，查当天自己的
 */
function initTable(status, type, recesort, tabId) {
    sessionStorage.setItem('tabID', tabId)
    onclickrowOobj = "";
    toptableclickval = 1;
    $("#gongzuol").css("display", "block");
    $("#dataCount").html("");
//    console.log(existornot+'------------------existornot');
    initcount();
    // 初始化表格所在div
    if (!recesort) {
        recesort = "";
    }
    var pageurl = contextPath + '/KQDS_REGAct/selectDzlNopage.act?status=' + status + '&querytype=' + type + '&searchValue=' + $("#searchValue").val() + '&recesort=' + recesort;

    var tableObj = $('#table').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (pageurl == tableObj.url) { // 重复点击
            $('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
            return;
        } else { // 切换Tab
            $('#table').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }

    function queryParams(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset, //页码 
            sortName: this.sortName,
            sortOrder: this.sortOrder,
            pageIndex: params.offset / params.limit + 1,
        };
        return temp;
    }

    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList: [15, 20, 25],//可以选择每页大小
        queryParams: queryParams,//传入查询参数
        sidePagination: "server",//后端分页
        paginationShowPageGo: true,
        onDblClickCell: function (field, value, row, td) {
            if (field == "iscreatelclj" && row.iscreatelclj != "" && row.iscreatelclj != null) {
                parent.Catalogue()
                parent.secondLevelDirectory()
                window.location.href = contextPath + '/ClinicPathControllerAct/toLcljPageFetchInfo.act?menuId=600309&&username=' + row.username + '&&userId=' + row.usercode;
            }
        },
        onLoadSuccess: function (data) { // 加载成功时执行
            $("#dataCount").html("");
//        	判断登录
            var existornot = isExist(total);
            if (existornot) {

            } else {
                $('#table').bootstrapTable('hideColumn', 'devchannelname');
                $('#table').bootstrapTable('hideColumn', 'nexttypename');
            }
            var tableList = data.data;
            var content = '';
            for (var prop in data.jzfl) {
                //初始化底部gongzuol显示文本与id对应关系,用作检索
                var jzflId = "";
                if (prop == "初诊") {
                    jzflId = "20";
                } else if (prop == "复诊") {
                    jzflId = "21";
                } else if (prop == "复查") {
                    jzflId = "22";
                } else if (prop == "再消费") {
                    jzflId = "23";
                } else if (prop == "团购") {
                    jzflId = "dcbd1614-2c26-4624-844c-ea2bc72f2f67";
                } else if (prop == "其他") {
                    jzflId = "256";
                }
                var className = "";
                if (jzflId && recesort && jzflId == recesort) {
                    className = "current";
                }
                content += '<li id="' + jzflId + '" onclick="dataCountClick(this,' + status + ',\'' + type + '\')" class="' + className + '">' + prop + ' ' + data.jzfl[prop] + '</li>'
            }

            $("#dataCount").append(content);

            setHeight();
            /*滚动条出现设置表头的宽度*/
            setTableHeaderWidth(".tableBox");
        },
        onLoadError: function () { // 加载失败时执行
            layer.msg("加载数据失败", {
                time: 1500,

            });
        },
        rowStyle: function (row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (Number(row.del) > 0) {
                strclass = 'warning'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
            {
                title: '序号',
                align: "center",
                width: 40,
                formatter: function (value, row, index) {
                    /* return index + 1; */
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                    return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                }
            },
            /*        {
                title: '当前门诊',
                field: 'organizationname',
                align: 'center',
                valign: 'middle',
                sortable: true

            },*/
            {
                title: '操作',
                field: 'jh',
                align: 'center',
                formatter: function (value, row, index) {
                    var html;
                    if (value == 1) {
                        html = '<span id="jh' + index + '"><a href="javascript:void(0);" style="color:#0d7ccd;" onclick="jh(\'' + row.usercode + '\',\'' + row.username + '\',\'' + row.sex + '\',\'' + row.askperson + '\',\'' + row.age + '\',\'' + row.seqId + '\',\'' + index + '\')">拍片</a></span>';
                    } else if (value == 2) {
                        html = '<span style="color:tomato;">放射科排队中</span>';
                    } else if (value == 3) {
                        html = '<span style="color:tomato;">放射科进行中</span>';
                    } else if (value == 4) {
                        html = '<span style="color:tomato;">放射科已完成</span>';
                    } else {
                        html = '<span id="overtime' + index + '"><a href="javascript:void(0);" style="color:#0d7ccd;" onclick="overtime(\'' + row.seqId + '\',\'' + row.askperson + '\')">放射科已超时</a></span>';
                    }
                    return html;
                }
            },
            {
                title: 'seqId',
                field: 'seqId',
                align: 'center',
                visible: false,
                switchable: false,
                formatter: function (value) {
                    return '<span>' + value + '</span>'
                }
            },
            {
                title: 'del',
                field: 'del',
                align: 'center',
                visible: false,
                switchable: false,
                formatter: function (value) {
                    return '<span>' + value + '</span>'
                }
            },
            {
                title: '挂号时间',
                field: 'createtime',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    html = '<span class="time">' + value.substring(5) + '</span>';
                    return html;
                }
            },
            {
                title: '就诊分类',
                field: 'recesortname',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return '<span>' + value + '</span>';
                }

            },
            {
                title: '挂号分类',
                field: 'regsortname',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return '<span>' + value + '</span>';
                }
            },
            {
                title: '患者编号',
                field: 'usercode',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<span>" + value + "</span>";
                }
            },

            {
                title: '患者姓名',
                field: 'username',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            },
            {
                title: '患者标识',
                field: 'iscreatelclj',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    //console.log("等待治疗=" + JSON.stringify(row) + "...row");
                    var iconhtml;
                    if (value != "" && value != null) {
                        iconhtml = '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/clinical.jpg/>';
                    }
                    if (row.kefu) {
                        iconhtml += '<img class="iscreatelclj" src= ' + contextPath + '/static/image/kqdsFront/tag/customerservice.jpg/>';
                    }
                    return iconhtml;
                }
            },
            {
                title: '患者性别',
                field: 'sex',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return '<span>' + value + '</span>';
                }
            },
            /*{
                title: '就诊状态',
                field: '',
                align: 'center',
                sortable: true,
                formatter: function(value) {
                    return '<img style="width:13px;" src="/base/static/image/index/label/lclj_icon.jpg"/>';
                }
            },*/
            {
                title: '年龄',
                field: 'age',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == "0" || value == 0) {
                        return "";
                    } else {
                        return '<span>' + value + '</span>';
                    }
                }
            },
            {
                title: '成交状态',
                field: 'cjstatus',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    var html = "";
                    if (value == "0") {
                        html = '<span class="label label-danger">未成交</span>';
                    } else if (value == "1") {
                        html = '<span class="label label-success">已成交</span>';
                    }
                    return html;
                }
            },
            {
                title: '咨询',
                field: 'askpersonname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name' title='" + value + "'>" + value + "</span>";
                    }
                }
            },
            {
                title: '医生',
                field: 'doctorname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name' title='" + value + "'>" + value + "</span>";
                    }
                }
            },
            {
                title: '修复医生',
                field: 'repairdoctorname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name' title='" + value + "'>" + value + "</span>";
                    }
                }
            },
            {
                title: '客服',
                field: 'kefuname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name' title='" + value + "'>" + value + "</span>";
                    }
                }
            },
            {
                title: '情况备注',
                field: 'remark',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null && value != "null") {
                        return "<span class='remark' title='" + value + "'>" + value + "</span>";
                    } else {
                        return '<span>-</span>';
                    }
                }
            },
            {
                title: '患者来源',
                field: 'devchannelname',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return '<span>' + value + '</span>';
                }
            },
            {
                title: '来源子分类',
                field: 'nexttypename',
                align: 'center',
                sortable: true,
                formatter: function (value) {
                    return '<span>' + value + '</span>';
                }
            },
            {
                title: '病历',
                field: 'ifmedrecord',
                align: 'center',
                formatter: function (value, row, index) {
                    var html = "";
                    if (row.ifmedrecord == "1") {
                        html = '<span class="label label-success">已填写</span>';
                    } else {
                        html = '<span class="label label-danger">未填写</span>';
                    }
                    return html;
                }
            },
            {
                title: '缴费',
                field: 'ifcost',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    var html = "";
                    if (row.ifcost == "1") {
                        html = '<span class="label label-success">已缴费</span>';
                    } else {
                        html = '<span class="label label-danger">未缴费</span>';
                    }
                    return html;
                }
            }

            ,
            {
                title: '挂号人员',
                field: 'createusername',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name' title='" + value + "'>" + value + "</span>";
                    }
                }
            },
            {
                title: '挂号导医',
                field: 'receivenoname',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        return "<span class='name' title='" + value + "'>" + value + "</span>";
                    } else {
                        return '<span>-</span>';
                    }
                }
            },
            {
                title: '修改/撤销',
                field: 'editreason',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    var html = "";
                    if (value != "" && value != null && row.del == "0") { //修改
                        html = '<span><a href="javascript:void(0);" style="color:#0d7ccd;" onclick="showEditreason(\'' + row.seqId + '\')">修改详情</a></span>';
                    }
                    if (value != "" && value != null && row.del == "1") { //修改
                        html = '<span><a href="javascript:void(0);" style="color:#0d7ccd;" onclick="showEditreason(\'' + row.seqId + '\')">撤销详情</a></span>';
                    }
                    return html;
                }
            }]
    }).on('click-row.bs.table',
        function (e, row, element) {
            $('.success').removeClass('success'); // 去除之前选中的行的，选中样式
            $(element).addClass('success'); // 添加当前选中的 success样式用于区别
            var index = $('#table').find('tr.success').data('index'); // 获得选中的行
            onclickrowOobj = $('#table').bootstrapTable('getData')[index];
            selectOrderNumber = $('#table').bootstrapTable('getData')[index].orderNumber;//获取患者临床路径信息表中的订单编号
            username = $('#table').bootstrapTable('getData')[index].username;//获取当前选中患者的姓名
            //alert(username);
            // showpersoninfo(onclickrowOobj);//展示右侧个人信息
            $('#tabIframe').attr('src', $('#tabIframe').attr('src')); // 个人中心
////        判断登录者为前台时隐藏患者来源及子分类
//        var tableObjcolumns = $('#table').bootstrapTable('getOptions').columns[0];
//        console.log(JSON.stringify(tableObjcolumns)+"-----------------tableObjcolumns");

        });

    $(".columns").hide();
}

function jh(usercode, username, sex, askperson, age, seqid, i) {
    layer.open({
        type: 2,
        title: 'X线预约单',
        shadeClose: true,
        shade: 0.6,
        area: ['1000px', '500px'],
        content: contextPath + '/Kqds_JhAct/toAppointment.act?usercode=' + usercode + '&username=' + username + '&sex=' + sex + '&askperson=' + askperson + '&age=' + age + '&regid=' + seqid + '&index=' + i
    });
}

function overtime(seqid, askperson) {
    layer.open({
        type: 2,
        title: '超时原因',
        shadeClose: true,
        shade: 0.6,
        area: ['900px', '700px'],
        content: contextPath + '/Kqds_JhAct/toZxSickOvertime.act?regid=' + seqid + '&askperson=' + askperson
    });
}

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        sortName: this.sortName,//排序名称
        sortOrder: this.sortOrder,//排序类型
        limit: params.limit,   //页面大小
        offset: params.offset, //页码
        pageIndex: params.offset / params.limit + 1,
    };
    return temp;
}

/*--------------------------------------*/
/**
 * 手术室当天预约
 * recesort 参数未知作用 2019/12/03 18:30
 */
function initTableB(status, type, tabId) {
    sessionStorage.setItem('tabID', tabId)
    onclickrowOobj = "";
    toptableclickval = 1;
    //$("#ssdataCount").html("");

    initcount();
    // 初始化表格所在div
//      if(!recesort){
//      	recesort = "";
//     }
    var pageurl = contextPath + '/KQDS_RoomAct/selectNoPage.act?querytype=' + type + '&searchValue=' + $("#searchValue").val();
    var tableObj = $('#table').bootstrapTable('getOptions');
    if (tableObj.length == undefined) { // 如果length 存在，则说明是第一次加载，bootstrap table还没初始化
        if (pageurl == tableObj.url) { // 重复点击
            $('#table').bootstrapTable('refresh', {
                'url': pageurl
            });
            return;
        } else { // 切换Tab
            $('#table').bootstrapTable('destroy'); // 销毁bootstrap,tab切换无效
        }
    }

    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParamsB,
        pageSize: 30,
        pageList: [15, 20, 25, 30],//可以选择每页大小
        singleSelect: false,
        striped: true,
        cache: false,
        clickToSelect: false,
        paginationShowPageGo: true,
        sidePagination: "server",//后端分页
        onLoadSuccess: function (data) {
            $("#shdataCountd").text(data.length);
            setHeight();
            /*table出现滚动条时 表头进行调整*/
            setTableHeaderWidth(".tableBox");
            $("#dataCount").html("");
        },
        onDblClickCell: function (field, value, row, td) {
            //双击事件 调用提交回访结果方法
            goToUserCenterPage(row.usercode);
        },
        rowStyle: function (row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.isdeletename == "已取消") {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        //basic', 'all', 'selected'.
        columns: [
            {
                title: '序号',
                align: "center",
                width: 40,
                formatter: function (value, row, index) {
                    /* return index + 1; */
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                    return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                }
            },
            {
                title: '手术室',
                field: 'roomname',
                align: 'center',

                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time" title="' + value + '">' + value + '</span>';
                    } else {
                        return '';
                    }
                }
            }, {
                title: '手术状态',
                field: 'roomstatusname',
                align: 'center',

                sortable: true,
                formatter: function (value) {
                    return '<span>' + value + '</span>';
                }
            }, {
                title: '患者编号',
                field: 'usercode',
                align: 'center',

                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time" title="' + value + '">' + value + '</span>';
                    } else {
                        return '';
                    }
                }
            },
            {
                title: '患者姓名',
                field: 'username',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="name" title="' + value + '">' + value + '</span>';
                    } else {
                        return '';
                    }
                }
            },
            {
                title: '创建时间',//添加展示预约的时间  2019-8-27  syp
                field: 'createtime',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span style="width: 129px;" class="name" title="' + value + '">' + value + '</span>';
                    } else {
                        return '';
                    }
                }
            },
            /*{
                title: '手机号',
                field: 'phonenumber1',
                align: 'center',
                
                sortable: true,
                formatter: function(value, row, index) {
                    if (canlookphone == 0) {
                        return '<span class="time phone" title="' + value + '">' + value + '</span>';
                    } else {
                        return '-';
                    }
                }
            },*/
            {
                title: '医生',
                field: 'doctorname',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span class="name" title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '护士',
                field: 'nursename',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span class="name" title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '种植系统',
                field: 'zzxtname',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span class="time" title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '颗数',
                field: 'ks',
                align: 'center',

                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '开始时间',
                field: 'starttime',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time" title="' + value + '">' + value + '</span>';
                    } else {
                        return '';
                    }
                }
            },
            {
                title: '结束时间',
                field: 'endtime',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time" title="' + value + '">' + value + '</span>';
                    } else {
                        return '';
                    }
                }
            },
            {
                title: '备注',
                field: 'remark',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span class="remark" title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '预约状态',
                field: 'isdeletename',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span class="name" title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '取消人',
                field: 'delpersonname',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span class="name" title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '取消原因',
                field: 'delreason',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="remark" title="' + value + '">' + value + '</span>';
                    }
                }
            },
            {
                title: '创建人',
                field: 'createusername',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span class="name" title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '建档人',
                field: 'jdr',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value) {
                        html = '<span class="name" title="' + value + '">' + value + '</span>';
                        return html;
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '建档时间',
                field: 'jdsj',
                align: 'center',

                sortable: true,
                formatter: function (value, row, index) {
                    if (value != "" && value != null) {
                        return '<span class="time" title="' + value + '">' + value + '</span>';
                    } else {
                        return '';
                    }
                }
            }]
    }).on('click-row.bs.table',
        function (e, row, element) {
            $('.success').removeClass('success'); //去除之前选中的行的，选中样式
            $(element).addClass('success'); //添加当前选中的 success样式用于区别
            var index = $('#table').find('tr.success').data('index'); // 获得选中的行
            onclickrowOobj = $('#table').bootstrapTable('getData')[index];
            //console.log(JSON.stringify(onclickrowOobj)+"-------------选中的行数据哈哈哈啊哈哈");
        });
}

/*--------------------------------------*/

//初始化gongzuol中的点击事件刷新当前列表
function dataCountClick(thi, status, type) {
    initTable(status, type, $(thi).attr("id"));
}

/** ########################################################### 高级查询开始  **/
//var pageurl = contextPath + '/KQDS_REGAct/selectDzlNopage.act'
//根据受理工具类型条件查询到诊患者
function query() {
    refresh();
}

function refresh() {
    alert($('#gongju1').val());
    alert($('#jdtime1').val());
    alert($('#jdtime2').val());
    $('#table').bootstrapTable('refresh', {
        'url': pageurl,
    });
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        pagenum: 1,
        gongju1: $('#gongju1').val(),
        jdtime1: $('#jdtime1').val(),
        jdtime2: $('#jdtime2').val(),
        //gongju1 : "dsgg947"
        //gongju : "网络大夜",
    };
    return temp;
}

/** ########################################################### 高级查询结束  **/


//查看修改挂号原因
function showEditreason(seqId) {
    layer.open({
        type: 2,
        title: '挂号修改原因',
        shadeClose: true,
        shade: 0.6,
        area: ['490px', '460px'],
        content: contextPath + '/KQDS_REGAct/toEditReason.act?seqId=' + seqId
    });
}

function changeMenu() {

}
