<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/classImport.jsp" %>
<%
    YZPerson loginUser = SessionUtil.getLoginPerson(request);

    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }

    // 如果该参数有值，就进行可见人员条件过滤
    String isFilterByVisualStaff = request.getParameter("isFilterByVisualStaff");
    if (isFilterByVisualStaff == null) {
        isFilterByVisualStaff = "";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="<%=contextPath %>/static/css/kqdsFront/style.css"/>
    <link type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet"
          href="<%=contextPath%>/static/css/admin/person/multi_select/multi_select_left.css">

    <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
    <script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
    <script type="text/Javascript"
            src="<%=contextPath%>/static/js/admin/person/multi_select/multi_select_left.js"></script>
    <title>左侧树形目录</title>
    <script type="text/Javascript">
        var frameindex = null //先得到当前iframe层的索引
        var static_single_select_userid = null;
        var static_single_select_username = null;
        var static_single_select_userObj = null;
        var static_single_select_userDescObj = null;
        // 取父页面的值
        var static_select_userid_val = null;
        var static_select_username_val = null;
        var isFilterByVisualStaff = "<%=isFilterByVisualStaff %>";

        var userRetNameArray = parent.userRetNameArrayMulti; // 多选
        if (userRetNameArray && userRetNameArray.length == 2) {
            static_single_select_userid = userRetNameArray[0];
            static_single_select_username = userRetNameArray[1];
            static_single_select_userObj = parent.$("#" + static_single_select_userid);
            static_single_select_userDescObj = parent.$("#" + static_single_select_username);
        } else {
            static_single_select_userObj = parent.$("#user");
            static_single_select_userDescObj = parent.$("#userDesc");
        }

        // 赋值
        function setSelectVal() {
            var zTree = $.fn.zTree.getZTreeObj("treeList");
            var selectList = zTree.getCheckedNodes(true);
            // 获取要删除的用户id，以逗号分隔
            var seqIds = "";
            var names = "";
            for (var i = 0; i < selectList.length; i++) {
                if (selectList[i].id.indexOf('person') != -1) { // 人员node
                    var tmpId = (selectList[i].id).replace("person", ""); // 替换掉后台赋值的person
                    seqIds += tmpId + ",";
                    names += selectList[i].name + ",";
                }
            }
            seqIds = seqIds.substring(0, seqIds.length - 1);
            names = names.substring(0, names.length - 1);

            $(static_single_select_userObj).val(seqIds);
            $(static_single_select_userDescObj).val(names);

            // 重新取值，赋值
            static_select_userid_val = $(static_single_select_userObj).val(); // id
            static_select_username_val = $(static_single_select_userDescObj).val(); // name
        }

        function submit() {
            setSelectVal();
            parent.layer.close(frameindex); //再执行关闭 */
        }

        $(function () {
            if (parent && parent.layer) {
                frameindex = parent.layer.getFrameIndex(window.name);
            }
            // 取父页面的值
            static_select_userid_val = $(static_single_select_userObj).val(); // id
            static_select_username_val = $(static_single_select_userDescObj).val(); // name
            // 初始化树
            zTreeInit4All();

            // 初始化Ul li
            initUL();
        });

        function initUL() {
            var idArray = static_select_userid_val.split(',');
            var nameArray = static_select_username_val.split(',');
            $("#pul").empty();
            for (var i = 0; i < idArray.length; i++) {
                if (idArray[i] != null && idArray[i] != '') {
                    $("#pul").append("<li onclick=\"expandNode('" + idArray[i] + "')\">" + nameArray[i] + "</li>");
                }
            }
        }

        function expandNode(id) {
            var nodeId = "person" + id;
            var zTree = $.fn.zTree.getZTreeObj("treeList");
            var node = zTree.getNodeByParam("id", nodeId);

            if (node.isParent) { //
                zTree.selectNode(node); //指定选中ID的节点
                zTree.reAsyncChildNodes(node, "refresh", false);
                zTree.expandNode(node, true, false); //指定选中ID节点展开
            } else {
                var nodeParent = node.getParentNode();
                zTree.selectNode(nodeParent); //指定选中ID的节点
                zTree.reAsyncChildNodes(nodeParent, "refresh", false);
                zTree.expandNode(nodeParent, true, false); //指定选中ID节点展开
            }

        }

        /**
         * 一次加载所有部门、人员树
         */
        function zTreeInit4All() {
            var url = '<%=contextPath%>/YZPersonTreeAct/getPersonTree4All.act'; //?personIds=' + static_select_userid_val + '&isFilterByVisualStaff=' + isFilterByVisualStaff;
            var treeNodes = null;
            var data = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                "personIds": static_select_userid_val,
                "isFilterByVisualStaff": isFilterByVisualStaff
            };
            $.axse(url, data,

                function (data) {
                    treeNodes = data.retData;
                }

                ,

                function () {
                    layer.alert('加载失败！');
                }
            )
            ;

            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "pId",
                        rootPId: "0"
                    }
                }
            };

            setting['callback'] = {
                onClick: onclick,
                onCheck: zTreeOnCheck
            };

            setting['check'] = {
                enable: true
            };

            $.fn.zTree.init($("#treeList"), setting, treeNodes);

            var zTreeTmp = $.fn.zTree.getZTreeObj("treeList");
            /** 默认展开第一级和第二级  **/
            var allNodes = zTreeTmp.getNodes();
            for (var i = 0; i < allNodes.length; i++) {
                var node = allNodes[i];
                if (node.pId == 'orgParentId' || node.pId == '0') {
                    var findNode = zTreeTmp.getNodeByParam("id", node.id, null);
                    if (findNode && findNode.isParent == true) { // 没有子节点不要执行下面的代码，否则会导致程序终止
                        zTreeTmp.setting.callback.onClick(null, zTreeTmp.setting.treeId, findNode);//触发函数
                    }
                }
            }

        }

        function zTreeInit() {
            //异步加载
            var url = '<%=contextPath%>/YZPersonAct/getPersonTree4MultiSelect.act';
            var setting = {
                view: {
                    showIcon: true // 去掉图标
                },
                async: {
                    enable: true,
                    url: url,
                    autoParam: ["id", "name=n", "level=lv"],
                    otherParam: {
                        "personIds": static_select_userid_val // 已选中的人员ID集合，以逗号分隔
                    },
                    dataFilter: ajaxDataFilter,
                    type: "get"
                }
            };
            setting['callback'] = {
                onClick: onclick,
                onCheck: zTreeOnCheck
            };

            setting['check'] = {
                enable: true
            };

            $.fn.zTree.init($("#treeList"), setting);

        }

        // 选中，取消选中
        function zTreeOnCheck(event, treeId, treeNode) {
            if (treeNode.checked) {
                // 选中
            } else {
                // 取消选中
            }
            setSelectVal();

            // 初始化Ul li
            initUL();
        }

        //解析树 返回数据
        function ajaxDataFilter(treeId, parentNode, responseData) {
            var tree;
            if (responseData.retState == "0") {
                tree = responseData.retData;
            }
            return tree;
        };

        function onclick(e, treeId, treeNode) {
            var id = treeNode.id;
            if (treeNode.isParent) {
                var zTree = $.fn.zTree.getZTreeObj("treeList");
                zTree.expandNode(treeNode);
            } else {
                // 什么都不做
                var zTree = $.fn.zTree.getZTreeObj("treeList");
                zTree.checkNode(treeNode, !treeNode.checked, true);
                setSelectVal();
                initUL();
            }

        }
    </script>
</head>
<body>
<div id="roleChoice" style="float: left;">
    <p class="roleChoiceText"></p>
    <ul id="treeList" class="ztree"></ul>

</div>

<div id="plist" style="float: left;">
    <p class="roleChoiceText">已选人员：</p>
    <ul id="pul">

    </ul>
    <div class="btnGroup">
        <a class="kqdsSearchBtn" id="menuSetSave" href="javascript:void(0);" onclick="submit()">确 定</a>
    </div>
</div>

</body>
</html>