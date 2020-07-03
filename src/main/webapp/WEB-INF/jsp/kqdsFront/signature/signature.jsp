<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String category = request.getParameter("category");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="initial-scale=1.0, target-densitydpi=device-dpi" /><!-- this is for mobile (Android) Chrome -->
<meta name="viewport" content="initial-scale=1.0, width=device-height"><!--  mobile Safari, FireFox, Opera Mobile  -->
<script src="/base/static/js/kqdsFront/index/modernizr.js"></script>
<style type="text/css">

	div {
		margin-top:1em;
		margin-bottom:1em;
	}
	input {
		padding: .5em;
		margin: .5em;
	}
	select {
		padding: .5em;
		margin: .5em;
	}

	#signatureparent {
		color:black;
		background-color:darkgrey;
		/*max-width:600px;*/
		padding:20px;
	}

	/*This is the div within which the signature canvas is fitted*/
	#signature {
		border: 2px dotted black;
		background-color:lightgrey;
	}

	/* Drawing the 'gripper' for touch-enabled devices */
	html.touch #content {
		float:left;
		width:92%;
	}
	html.touch #scrollgrabber {
		float:right;
		width:4%;
		margin-right:2%;
		background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAAFCAAAAACh79lDAAAAAXNSR0IArs4c6QAAABJJREFUCB1jmMmQxjCT4T/DfwAPLgOXlrt3IwAAAABJRU5ErkJggg==)
	}
	html.borderradius #scrollgrabber {
		border-radius: 1em;
	}
</style>
</head>
<body>
<div>
<div id="content">
	<div id="signatureparent">
		<div id="signature"></div>
	</div>
	<div id="tools" style="text-align: center;">
	</div>
</div>
</div>
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript">
jQuery.noConflict()
</script>
<script>
(function($) {
	var topics = {};
	$.publish = function(topic, args) {
	    if (topics[topic]) {
	        var currentTopic = topics[topic],
	        args = args || {};

	        for (var i = 0, j = currentTopic.length; i < j; i++) {
	            currentTopic[i].call($, args);
	        }
	    }
	};
	$.subscribe = function(topic, callback) {
	    if (!topics[topic]) {
	        topics[topic] = [];
	    }
	    topics[topic].push(callback);
	    return {
	        "topic": topic,
	        "callback": callback
	    };
	};
	$.unsubscribe = function(handle) {
	    var topic = handle.topic;
	    if (topics[topic]) {
	        var currentTopic = topics[topic];

	        for (var i = 0, j = currentTopic.length; i < j; i++) {
	            if (currentTopic[i] === handle.callback) {
	                currentTopic.splice(i, 1);
	            }
	        }
	    }
	};
})(jQuery);

</script>
<script src="/base/static/js/kqdsFront/index/jSignature.min.noconflict.js"></script>
<script>
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
(function($){

$(document).ready(function() {
	/* var arguments = {
			            "decor-color": "transparent",//去除默认画布上那条横线
			            lineWidth: '3'
			        };
	$("#signature").jSignature(arguments); */
	var $sigdiv = $("#signature").jSignature({'UndoButton':true,lineWidth: '5'})
	, $tools = $('#tools')
	, $extraarea = $('#displayarea')
	, pubsubprefix = 'jSignature.demo.'

	/* var export_plugins = $sigdiv.jSignature('listPlugins','export')
	, chops = ['<select>']
	, name
	for(var i in export_plugins){
		if (export_plugins.hasOwnProperty(i)){
			name = export_plugins[i]
			chops.push('<option value="' + name + '">' + name + '</option>')
		}
	}
	chops.push('</select>')

	$(chops.join('')).bind('change', function(e){
		if (e.target.value !== ''){
			var data = $sigdiv.jSignature('getData', e.target.value)

			$.publish(pubsubprefix + 'formatchanged')
			if (typeof data === 'string'){
				$('textarea', $tools).val(data)
			} else if($.isArray(data) && data.length === 2){
				$('textarea', $tools).val(data.join(','))
				$.publish(pubsubprefix + data[0], data);
			} else {
				try {
					$('textarea', $tools).val(JSON.stringify(data))
				} catch (ex) {
					$('textarea', $tools).val('Not sure how to stringify this, likely binary, format.')
				}
			}
		}
	}).appendTo($tools) */

	$('<input type="button" value="确定">').bind('click', function(e){
		var data = $sigdiv.jSignature('getData', "default")
		if("<%=category%>"=="修复"){
			parent.repairSignature=data;
			parent.addRepairSignature();
			parent.layer.close(frameindex); //再执行关闭
		}else if("<%=category%>"=="种植"){
			parent.signature=data;
			parent.addSignature();
			parent.layer.close(frameindex); //再执行关闭
		}else if("<%=category%>"=="患者"){
			parent.patientsignature=data;
			parent.addPatientSignature();
			parent.layer.close(frameindex); //再执行关闭
		}else if("<%=category%>"=="患者1"){
			parent.patientsignature1=data;
			parent.addPatientSignature1();
			parent.layer.close(frameindex); //再执行关闭
		}else if("<%=category%>"=="护士1"){
            parent.nursesignature1=data;
            parent.addNurseSignature1();
            parent.layer.close(frameindex); //再执行关闭
        }else if("<%=category%>"=="护士2"){
            parent.nursesignature2=data;
            parent.addNurseSignature2();
            parent.layer.close(frameindex); //再执行关闭
        }
	}).appendTo($tools)
	$('<input type="button" value="清空">').bind('click', function(e){
		$sigdiv.jSignature('reset')
	}).appendTo($tools)

	/* $('<div><textarea style="width:100%;height:7em;"></textarea></div>').appendTo($tools) */

	/* $.subscribe(pubsubprefix + 'formatchanged', function(){
		$extraarea.html('')
	})

	$.subscribe(pubsubprefix + 'image/svg+xml', function(data) {

		try{
			var i = new Image()
			i.src = 'data:' + data[0] + ';base64,' + btoa( data[1] )
			$(i).appendTo($extraarea)
		} catch (ex) {

		}

		var message = [
			"If you don't see an image immediately above, it means your browser is unable to display in-line (data-url-formatted) SVG."
			, "This is NOT an issue with jSignature, as we can export proper SVG document regardless of browser's ability to display it."
			, "Try this page in a modern browser to see the SVG on the page, or export data as plain SVG, save to disk as text file and view in any SVG-capabale viewer."
           ]
		$( "<div>" + message.join("<br/>") + "</div>" ).appendTo( $extraarea )
	});

	$.subscribe(pubsubprefix + 'image/svg+xml;base64', function(data) {
		var i = new Image()
		i.src = 'data:' + data[0] + ',' + data[1]
		$(i).appendTo($extraarea)

		var message = [
			"If you don't see an image immediately above, it means your browser is unable to display in-line (data-url-formatted) SVG."
			, "This is NOT an issue with jSignature, as we can export proper SVG document regardless of browser's ability to display it."
			, "Try this page in a modern browser to see the SVG on the page, or export data as plain SVG, save to disk as text file and view in any SVG-capabale viewer."
           ]
		$( "<div>" + message.join("<br/>") + "</div>" ).appendTo( $extraarea )
	});

	$.subscribe(pubsubprefix + 'image/png;base64', function(data) {
		var i = new Image()
		i.src = 'data:' + data[0] + ',' + data[1]
		$('<span><b>As you can see, one of the problems of "image" extraction (besides not working on some old Androids, elsewhere) is that it extracts A LOT OF DATA and includes all the decoration that is not part of the signature.</b></span>').appendTo($extraarea)
		$(i).appendTo($extraarea)
	});

	$.subscribe(pubsubprefix + 'image/jsignature;base30', function(data) {
		$('<span><b>This is a vector format not natively render-able by browsers. Format is a compressed "movement coordinates arrays" structure tuned for use server-side. The bonus of this format is its tiny storage footprint and ease of deriving rendering instructions in programmatic, iterative manner.</b></span>').appendTo($extraarea)
	});*/

	if (Modernizr.touch){
		$('#scrollgrabber').height($('#content').height())
	}

})

})(jQuery)
</script>
</body>
</html>
