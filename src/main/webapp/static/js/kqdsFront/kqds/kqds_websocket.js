var KqdsWebSocket = function(json) {
    let options = {
        uri: "#",
        onOpen: function(event) {
            // 自定义WSC连接事件：服务端与前端连接成功后触发
            // console.log("WebSocket连接成功：" + event)
        },
        onMessage: function(event) {
            // 自定义WSC消息接收事件：服务端向前端发送消息时触发
            // console.log(event)
        },
        onError: function(event) {
            // 自定义WSC异常事件：WSC报错后触发
            // console.log("WebSocket连接发生错误：" + event)
        },
        onClose: function(event) {
            // 自定义WSC关闭事件：WSC关闭后触发
            // console.log("WebSocket连接关闭：" + event)
        }
    };
    $.extend(true, options, json);

    let websocket;
    var websocket_url = "ws://" + localhostPahtless + projectName + options.uri;
    if ('WebSocket' in window) {
        websocket = new WebSocket(websocket_url);
    } else {
    	alert('当前浏览器不支持websocket');
    	return false;
    }

    websocket.onopen = function(evnt) {

        options.onOpen(evnt);
    };
    websocket.onmessage = function(evnt) {

        options.onMessage(evnt);
    };
    websocket.onerror = function(evnt) {

        options.onError(evnt);
    };
    websocket.onclose = function(evnt) {

        options.onClose(evnt);
    };

    websocket.onunload = function(event) {
        websocket.close();
    };

    websocket.onbeforeunload = function(event) {
        websocket.close();
    };

    return websocket;
}