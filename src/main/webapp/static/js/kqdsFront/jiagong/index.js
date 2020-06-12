$(function(){
		/*全冠数据*/
		var fullCrownObj=[{
					id:"01",
					name:"二氧化锆全冠"
				},{
					id:"02",
					name:"纯钛冠"
				},{
					id:"03",
					name:"镍铬金属冠"
				},{
					id:"04",
					name:"弹性瓷"
				},{
					id:"05",
					name:"玻璃陶瓷全冠"
				},{
					id:"06",
					name:"贵金素冠（黄金）"
				},{
					id:"07",
					name:"钴铬金素冠"
				},{
					id:"08",
					name:"铸瓷"
				},{
					id:"09",
					name:"树脂冠"
				},{
					id:"10",
					name:"钛合金冠"
				},{
					id:"11",
					name:"磷酸钙全瓷"
				},{
					id:"12",
					name:"美学诊断蜡型"
				}];
		var fullCrownHtml="";
		for (var i = 0; i < fullCrownObj.length; i++) {
			fullCrownHtml+="<li rel="+fullCrownObj[i].id+" title="+fullCrownObj[i].name+">"+fullCrownObj[i].name+"</li>";
		}
		$("#fullCrownUl").html(fullCrownHtml);
		
		/*种植系统数据*/
		var plantSystemObj=[{
					id:"Dentium",
					name:"Dentium"
				},{
					id:"ICX",
					name:"ICX"
				},{
					id:"OSSTEM",
					name:"OSSTEM"
				},{
					id:"Nobelpharma",
					name:"Nobelpharma"
				},{
					id:"ITI",
					name:"ITI"
				},{
					id:"Nobel-Replace",
					name:"Nobel-Replace"
				},{
					id:"HIOSSEN",
					name:"HIOSSEN"
				},{
					id:"Nobel-Active",
					name:"Nobel-Active"
				},{
					id:"Camlog",
					name:"Camlog"
				}];
		var plantSystemHtml="";
		for (var i = 0; i < plantSystemObj.length; i++) {
			plantSystemHtml+="<li rel="+plantSystemObj[i].id+" title="+plantSystemObj[i].name+">"+plantSystemObj[i].name+"</li>";
		}
		$("#plantSystemUl").html(plantSystemHtml);
		
		/*烤瓷冠数据*/
		var PFMCrownObj=[{
					id:"二氧化锆全瓷",
					name:"二氧化锆全瓷"
				},{
					id:"镍铬烤瓷",
					name:"镍铬烤瓷"
				},{
					id:"二氧化铝全瓷",
					name:"二氧化铝全瓷"
				},{
					id:"钴铬烤瓷",
					name:"钴铬烤瓷"
				},{
					id:"铸瓷",
					name:"铸瓷"
				},{
					id:"纯钛烤瓷",
					name:"纯钛烤瓷"
				},{
					id:"玻璃陶瓷",
					name:"玻璃陶瓷"
				},{
					id:"贵金素烤瓷",
					name:"贵金素烤瓷"
				},{
					id:"烤塑（聚合瓷）",
					name:"烤塑（聚合瓷）"
				},{
					id:"钛合金烤瓷",
					name:"钛合金烤瓷"
				}];
		var PFMCrownHtml="";
		for (var i = 0; i < PFMCrownObj.length; i++) {
			PFMCrownHtml+="<li rel="+PFMCrownObj[i].id+" title="+PFMCrownObj[i].name+">"+PFMCrownObj[i].name+"</li>";
		}
		$("#PFMCrownUl").html(PFMCrownHtml);
		
		/*贴面数据*/
		var veneerObj=[{
					id:"烤瓷贴面",
					name:"烤瓷贴面"
				},{
					id:"聚合瓷贴面",
					name:"聚合瓷贴面"
				},{
					id:"铸瓷贴面",
					name:"铸瓷贴面"
				},{
					id:"弹性瓷贴面",
					name:"弹性瓷贴面"
				},{
					id:"玻璃陶瓷贴面",
					name:"玻璃陶瓷贴面"
				},{
					id:"二氧化锆贴面",
					name:"二氧化锆贴面"
				}];
		var veneerHtml="";
		for (var i = 0; i < veneerObj.length; i++) {
			veneerHtml+="<li rel="+veneerObj[i].id+" title="+veneerObj[i].name+">"+veneerObj[i].name+"</li>";
		}
		$("#veneerUl").html(veneerHtml);
		
		/*嵌体数据*/
		var incrustationObj=[{
					id:"铸瓷嵌体",
					name:"铸瓷嵌体"
				},{
					id:"二氧化锆嵌体",
					name:"二氧化锆嵌体"
				},{
					id:"聚合瓷嵌体",
					name:"聚合瓷嵌体"
				},{
					id:"镍铬嵌体",
					name:"镍铬嵌体"
				},{
					id:"弹性瓷嵌体",
					name:"弹性瓷嵌体"
				},{
					id:"钴铬嵌体",
					name:"钴铬嵌体"
				},{
					id:"玻璃陶瓷嵌体",
					name:"玻璃陶瓷嵌体"
				},{
					id:"纯钛嵌体",
					name:"纯钛嵌体"
				},{
					id:"黄金嵌体",
					name:"黄金嵌体"
				}];
		var incrustationHtml="";
		for (var i = 0; i < incrustationObj.length; i++) {
			incrustationHtml+="<li rel="+incrustationObj[i].id+" title="+incrustationObj[i].name+">"+incrustationObj[i].name+"</li>";
		}
		$("#incrustationUl").html(incrustationHtml);
		
		/*桩核数据*/
		var dowelCrownObj=[{
					id:"二氧化锆桩核",
					name:"二氧化锆桩核"
				},{
					id:"镍铬桩核",
					name:"镍铬桩核"
				},{
					id:"钴铬桩核",
					name:"钴铬桩核"
				},{
					id:"纯钛桩核",
					name:"纯钛桩核"
				}];
		var dowelCrownHtml="";
		for (var i = 0; i < dowelCrownObj.length; i++) {
			dowelCrownHtml+="<li rel="+dowelCrownObj[i].id+" title="+dowelCrownObj[i].name+">"+dowelCrownObj[i].name+"</li>";
		}
		$("#dowelCrownUl").html(dowelCrownHtml);
		
		/*活动修复数据*/
		var activityRepairObj=[{
					id:"普通胶托排牙",
					name:"普通胶托排牙"
				},{
					id:"隐形义齿（弹性义齿）",
					name:"隐形义齿（弹性义齿）"
				},{
					id:"钴铬支架排牙",
					name:"钴铬支架排牙"
				},{
					id:"压模保持器",
					name:"压模保持器"
				},{
					id:"钛合金支架排牙",
					name:"钛合金支架排牙"
				},{
					id:"牙合垫",
					name:"牙合垫"
				},{
					id:"纯钛支架排牙",
					name:"纯钛支架排牙"
				},{
					id:"夜磨牙垫",
					name:"夜磨牙垫"
				},{
					id:"即刻负重胶托排牙",
					name:"即刻负重胶托排牙"
				},{
					id:"种植简易桥架排牙",
					name:"种植简易桥架排牙"
				},{
					id:"维它灵支架排牙",
					name:"维它灵支架排牙"
				},{
					id:"个性化托盘",
					name:"个性化托盘"
				},{
					id:"BPD支架排牙",
					name:"BPD支架排牙"
				}];
		var activityRepairHtml="";
		for (var i = 0; i < activityRepairObj.length; i++) {
			activityRepairHtml+="<li rel="+activityRepairObj[i].id+" title="+activityRepairObj[i].name+">"+activityRepairObj[i].name+"</li>";
		}
		$("#activityRepairUl").html(activityRepairHtml);
		
		/*根内附着体数据*/
		var EAttachmentObj=[{
					id:"磁性附着体",
					name:"磁性附着体"
				},{
					id:"按扣式附着体",
					name:"按扣式附着体"
				},{
					id:"插槽式附着体",
					name:"插槽式附着体"
				},{
					id:"连杆式附着体",
					name:"连杆式附着体"
				}];
		var EAttachmentHtml="";
		for (var i = 0; i < EAttachmentObj.length; i++) {
			EAttachmentHtml+="<li rel="+EAttachmentObj[i].id+" title="+EAttachmentObj[i].name+">"+EAttachmentObj[i].name+"</li>";
		}
		$("#EAttachmentUl").html(EAttachmentHtml);
		
		/*冠外附着体数据*/
		var EPAttachmentsObj=[{
					id:"套筒式附着体",
					name:"套筒式附着体"
				},{
					id:"扣式附着体",
					name:"扣式附着体"
				},{
					id:"槽式附着体",
					name:"槽式附着体"
				},{
					id:"锁制式附着体",
					name:"锁制式附着体"
				}];
		var EPAttachmentsHtml="";
		for (var i = 0; i < EPAttachmentsObj.length; i++) {
			EPAttachmentsHtml+="<li rel="+EPAttachmentsObj[i].id+" title="+EPAttachmentsObj[i].name+">"+EPAttachmentsObj[i].name+"</li>";
		}
		$("#EPAttachmentsUl").html(EPAttachmentsHtml);
		
		/*冠内附着体数据*/
		var IPAttachmentsObj=[{
					id:"槽式附着体",
					name:"槽式附着体"
				}];
		var IPAttachmentsHtml="";
		for (var i = 0; i < IPAttachmentsObj.length; i++) {
			IPAttachmentsHtml+="<li rel="+IPAttachmentsObj[i].id+" title="+IPAttachmentsObj[i].name+">"+IPAttachmentsObj[i].name+"</li>";
		}
		$("#IPAttachmentsUl").html(IPAttachmentsHtml);
		
		/*定位器数据*/
		var LocatorObj=[{
					id:"正畸托槽定位器",
					name:"正畸托槽定位器"
				},{
					id:"支抗钉",
					name:"支抗钉"
				}];
		var LocatorHtml="";
		for (var i = 0; i < LocatorObj.length; i++) {
			LocatorHtml+="<li rel="+LocatorObj[i].id+" title="+LocatorObj[i].name+">"+LocatorObj[i].name+"</li>";
		}
		$("#LocatorUl").html(LocatorHtml);
		
		/*导板数据*/
		var guidePlateObj=[{
					id:"种植导板报告",
					name:"种植导板报告"
				},{
					id:"全程导板",
					name:"全程导板"
				},{
					id:"单程导板",
					name:"单程导板"
				},{
					id:"定位导板",
					name:"定位导板"
				},{
					id:"去骨导板",
					name:"去骨导板"
				}];
		var guidePlateHtml="";
		for (var i = 0; i < guidePlateObj.length; i++) {
			guidePlateHtml+="<li rel="+guidePlateObj[i].id+" title="+guidePlateObj[i].name+">"+guidePlateObj[i].name+"</li>";
		}
		$("#guidePlateUl").html(guidePlateHtml);
		
		/*椅旁数据*/
		var CERECObj=[{
					id:"口扫",
					name:"口扫"
				},{
					id:"瓷睿刻",
					name:"瓷睿刻"
				}];
		var CERECHtml="";
		for (var i = 0; i < CERECObj.length; i++) {
			CERECHtml+="<li rel="+CERECObj[i].id+" title="+CERECObj[i].name+">"+CERECObj[i].name+"</li>";
		}
		$("#CERECUl").html(CERECHtml);
		
		/*正畸修复数据*/
		var OrthodonticsRepairObj=[{
					id:"隐形矫治器",
					name:"隐形矫治器"
				},{
					id:"牙合垫（导弓）",
					name:"牙合垫（导弓）"
				},{
					id:"舌簧矫治器",
					name:"舌簧矫治器"
				},{
					id:"功能矫治器",
					name:"功能矫治器"
				},{
					id:"隐形保持器（透明压模保持器）",
					name:"隐形保持器（透明压模保持器）"
				},{
					id:"缺隙保持器",
					name:"缺隙保持器"
				},{
					id:"Hawley保持器",
					name:"Hawley保持器"
				},{
					id:"后牙带环",
					name:"后牙带环"
				},{
					id:"附加腭杆/舌杆",
					name:"附加腭杆/舌杆"
				},{
					id:"Nance弓",
					name:"Nance弓"
				},{
					id:"夜磨牙垫（软/硬）",
					name:"夜磨牙垫（软/硬）"
				},{
					id:"扩弓器",
					name:"扩弓器"
				},{
					id:"运动护口胶",
					name:"运动护口胶"
				}];
		var OrthodonticsRepairHtml="";
		for (var i = 0; i < OrthodonticsRepairObj.length; i++) {
			OrthodonticsRepairHtml+="<li rel="+OrthodonticsRepairObj[i].id+" title="+OrthodonticsRepairObj[i].name+">"+OrthodonticsRepairObj[i].name+"</li>";
		}
		$("#OrthodonticsRepairUl").html(OrthodonticsRepairHtml);
		
		var sh_languages = {};

		function sh_isEmailAddress(a) {
			return /^mailto:/.test(a) ? !1 : a.indexOf("@") !== -1
		}

		function sh_setHref(a, b, c) {
			var d = c.substring(a[b - 2].pos, a[b - 1].pos);
			d.length >= 2 && d.charAt(0) === "<" && d.charAt(d.length - 1) === ">" && (d = d.substr(1, d.length - 2)), sh_isEmailAddress(d) && (d = "mailto:" + d), a[b - 2].node.href = d
		}

		function sh_konquerorExec(a) {
			var b = [""];
			return b.index = a.length, b.input = a, b
		}

		function sh_highlightString(a, b) {
			if(/Konqueror/.test(navigator.userAgent) && !b.konquered) {
				for(var c = 0; c < b.length; c++)
					for(var d = 0; d < b[c].length; d++) {
						var e = b[c][d][0];
						e.source === "$" && (e.exec = sh_konquerorExec)
					}
				b.konquered = !0
			}
			var f = document.createElement("a"),
				g = document.createElement("span"),
				h = [],
				i = 0,
				j = [],
				k = 0,
				l = null,
				m = function(b, c) {
					var d = b.length;
					if(d === 0) return;
					if(!c) {
						var e = j.length;
						if(e !== 0) {
							var m = j[e - 1];
							m[3] || (c = m[1])
						}
					}
					if(l !== c) {
						l && (h[i++] = {
							pos: k
						}, l === "sh_url" && sh_setHref(h, i, a));
						if(c) {
							var n;
							c === "sh_url" ? n = f.cloneNode(!1) : n = g.cloneNode(!1), n.className = c, h[i++] = {
								node: n,
								pos: k
							}
						}
					}
					k += d, l = c
				},
				n = /\r\n|\r|\n/g;
			n.lastIndex = 0;
			var o = a.length;
			while(k < o) {
				var p = k,
					q, r, s = n.exec(a);
				s === null ? (q = o, r = o) : (q = s.index, r = n.lastIndex);
				var t = a.substring(p, q),
					u = [];
				for(;;) {
					var v = k - p,
						w, x = j.length;
					x === 0 ? w = 0 : w = j[x - 1][2];
					var y = b[w],
						z = y.length,
						A = u[w];
					A || (A = u[w] = []);
					var B = null,
						C = -1;
					for(var D = 0; D < z; D++) {
						var E;
						if(D < A.length && (A[D] === null || v <= A[D].index)) E = A[D];
						else {
							var F = y[D][0];
							F.lastIndex = v, E = F.exec(t), A[D] = E
						}
						if(E !== null && (B === null || E.index < B.index)) {
							B = E, C = D;
							if(E.index === v) break
						}
					}
					if(B === null) {
						m(t.substring(v), null);
						break
					}
					B.index > v && m(t.substring(v, B.index), null);
					var G = y[C],
						H = G[1],
						I;
					if(H instanceof Array)
						for(var J = 0; J < H.length; J++) I = B[J + 1], m(I, H[J]);
					else I = B[0], m(I, H);
					switch(G[2]) {
						case -1:
							break;
						case -2:
							j.pop();
							break;
						case -3:
							j.length = 0;
							break;
						default:
							j.push(G)
					}
				}
				l && (h[i++] = {
					pos: k
				}, l === "sh_url" && sh_setHref(h, i, a), l = null), k = r
			}
			return h
		}

		function sh_getClasses(a) {
			var b = [],
				c = a.className;
			if(c && c.length > 0) {
				var d = c.split(" ");
				for(var e = 0; e < d.length; e++) d[e].length > 0 && b.push(d[e])
			}
			return b
		}

		function sh_addClass(a, b) {
			var c = sh_getClasses(a);
			for(var d = 0; d < c.length; d++)
				if(b.toLowerCase() === c[d].toLowerCase()) return;
			c.push(b), a.className = c.join(" ")
		}

		function sh_extractTagsFromNodeList(a, b) {
			var c = a.length;
			for(var d = 0; d < c; d++) {
				var e = a.item(d);
				switch(e.nodeType) {
					case 1:
						if(e.nodeName.toLowerCase() === "br") {
							var f;
							/MSIE/.test(navigator.userAgent) ? f = "\r" : f = "\n", b.text.push(f), b.pos++
						} else b.tags.push({
							node: e.cloneNode(!1),
							pos: b.pos
						}), sh_extractTagsFromNodeList(e.childNodes, b), b.tags.push({
							pos: b.pos
						});
						break;
					case 3:
					case 4:
						b.text.push(e.data), b.pos += e.length
				}
			}
		}

		function sh_extractTags(a, b) {
			var c = {};
			return c.text = [], c.tags = b, c.pos = 0, sh_extractTagsFromNodeList(a.childNodes, c), c.text.join("")
		}

		function sh_mergeTags(a, b) {
			var c = a.length;
			if(c === 0) return b;
			var d = b.length;
			if(d === 0) return a;
			var e = [],
				f = 0,
				g = 0;
			while(f < c && g < d) {
				var h = a[f],
					i = b[g];
				h.pos <= i.pos ? (e.push(h), f++) : (e.push(i), b[g + 1].pos <= h.pos ? (g++, e.push(b[g]), g++) : (e.push({
					pos: h.pos
				}), b[g] = {
					node: i.node.cloneNode(!1),
					pos: h.pos
				}))
			}
			while(f < c) e.push(a[f]), f++;
			while(g < d) e.push(b[g]), g++;
			return e
		}

		function sh_insertTags(a, b) {
			var c = document,
				d = document.createDocumentFragment(),
				e = 0,
				f = a.length,
				g = 0,
				h = b.length,
				i = d;
			while(g < h || e < f) {
				var j, k;
				e < f ? (j = a[e], k = j.pos) : k = h;
				if(k <= g) {
					if(j.node) {
						var l = j.node;
						i.appendChild(l), i = l
					} else i = i.parentNode;
					e++
				} else i.appendChild(c.createTextNode(b.substring(g, k))), g = k
			}
			return d
		}

		function sh_highlightElement(a, b) {
			sh_addClass(a, "sh_sourceCode");
			var c = [],
				d = sh_extractTags(a, c),
				e = sh_highlightString(d, b),
				f = sh_mergeTags(c, e),
				g = sh_insertTags(f, d);
			while(a.hasChildNodes()) a.removeChild(a.firstChild);
			a.appendChild(g)
		}

		function sh_getXMLHttpRequest() {
			if(window.ActiveXObject) return new ActiveXObject("Msxml2.XMLHTTP");
			if(window.XMLHttpRequest) return new XMLHttpRequest;
			throw "No XMLHttpRequest implementation available"
		}

		function sh_load(language, element, prefix, suffix) {
			if(language in sh_requests) {
				sh_requests[language].push(element);
				return
			}
			sh_requests[language] = [element];
			var request = sh_getXMLHttpRequest(),
				url = prefix + "sh_" + language + suffix;
			request.open("GET", url, !0), request.onreadystatechange = function() {
				if(request.readyState === 4) try {
					if(!!request.status && request.status !== 200) throw "HTTP error: status " + request.status;
					eval(request.responseText);
					var elements = sh_requests[language];
					for(var i = 0; i < elements.length; i++) sh_highlightElement(elements[i], sh_languages[language])
				} finally {
					request = null
				}
			}, request.send(null)
		}

		function sh_highlightDocument(a, b) {
			var c = document.getElementsByTagName("pre");
			for(var d = 0; d < c.length; d++) {
				var e = c.item(d),
					f = sh_getClasses(e);
				for(var g = 0; g < f.length; g++) {
					var h = f[g].toLowerCase();
					if(h === "sh_sourcecode") continue;
					if(h.substr(0, 3) === "sh_") {
						var i = h.substring(3);
						if(i in sh_languages) sh_highlightElement(e, sh_languages[i]);
						else if(typeof a == "string" && typeof b == "string") sh_load(i, e, a, b);
						else throw 'Found <pre> element with class="' + h + '", but no such language exists';
						break
					}
				}
			}
		}
		var sh_requests = {};
		sh_languages.javascript = [
			[
				[/\/\/\//g, "sh_comment", 1],
				[/\/\//g, "sh_comment", 7],
				[/\/\*\*/g, "sh_comment", 8],
				[/\/\*/g, "sh_comment", 9],
				[/\b(?:abstract|break|case|catch|class|const|continue|debugger|default|delete|do|else|enum|export|extends|false|final|finally|for|function|goto|if|implements|in|instanceof|interface|native|new|null|private|protected|prototype|public|return|static|super|switch|synchronized|throw|throws|this|transient|true|try|typeof|var|volatile|while|with)\b/g, "sh_keyword", -1],
				[/(\+\+|--|\)|\])(\s*)(\/=?(?![*\/]))/g, ["sh_symbol", "sh_normal", "sh_symbol"], -1],
				[/(0x[A-Fa-f0-9]+|(?:[\d]*\.)?[\d]+(?:[eE][+-]?[\d]+)?)(\s*)(\/(?![*\/]))/g, ["sh_number", "sh_normal", "sh_symbol"], -1],
				[/([A-Za-z$_][A-Za-z0-9$_]*\s*)(\/=?(?![*\/]))/g, ["sh_normal", "sh_symbol"], -1],
				[/\/(?:\\.|[^*\\\/])(?:\\.|[^\\\/])*\/[gim]*/g, "sh_regexp", -1],
				[/\b[+-]?(?:(?:0x[A-Fa-f0-9]+)|(?:(?:[\d]*\.)?[\d]+(?:[eE][+-]?[\d]+)?))u?(?:(?:int(?:8|16|32|64))|L)?\b/g, "sh_number", -1],
				[/"/g, "sh_string", 10],
				[/'/g, "sh_string", 11],
				[/~|!|%|\^|\*|\(|\)|-|\+|=|\[|\]|\\|:|;|,|\.|\/|\?|&|<|>|\|/g, "sh_symbol", -1],
				[/\{|\}/g, "sh_cbracket", -1],
				[/\b(?:Math|Infinity|NaN|undefined|arguments)\b/g, "sh_predef_var", -1],
				[/\b(?:Array|Boolean|Date|Error|EvalError|Function|Number|Object|RangeError|ReferenceError|RegExp|String|SyntaxError|TypeError|URIError|decodeURI|decodeURIComponent|encodeURI|encodeURIComponent|eval|isFinite|isNaN|parseFloat|parseInt)\b/g, "sh_predef_func", -1],
				[/\b(?:applicationCache|closed|Components|content|controllers|crypto|defaultStatus|dialogArguments|directories|document|frameElement|frames|fullScreen|globalStorage|history|innerHeight|innerWidth|length|location|locationbar|menubar|name|navigator|opener|outerHeight|outerWidth|pageXOffset|pageYOffset|parent|personalbar|pkcs11|returnValue|screen|availTop|availLeft|availHeight|availWidth|colorDepth|height|left|pixelDepth|top|width|screenX|screenY|scrollbars|scrollMaxX|scrollMaxY|scrollX|scrollY|self|sessionStorage|sidebar|status|statusbar|toolbar|top|window)\b/g, "sh_predef_var", -1],
				[/\b(?:alert|addEventListener|atob|back|blur|btoa|captureEvents|clearInterval|clearTimeout|close|confirm|dump|escape|find|focus|forward|getAttention|getComputedStyle|getSelection|home|moveBy|moveTo|open|openDialog|postMessage|print|prompt|releaseEvents|removeEventListener|resizeBy|resizeTo|scroll|scrollBy|scrollByLines|scrollByPages|scrollTo|setInterval|setTimeout|showModalDialog|sizeToContent|stop|unescape|updateCommands|onabort|onbeforeunload|onblur|onchange|onclick|onclose|oncontextmenu|ondragdrop|onerror|onfocus|onkeydown|onkeypress|onkeyup|onload|onmousedown|onmousemove|onmouseout|onmouseover|onmouseup|onpaint|onreset|onresize|onscroll|onselect|onsubmit|onunload)\b/g, "sh_predef_func", -1],
				[/(?:[A-Za-z]|_)[A-Za-z0-9_]*(?=[ \t]*\()/g, "sh_function", -1]
			],
			[
				[/$/g, null, -2],
				[/(?:<?)[A-Za-z0-9_\.\/\-_~]+@[A-Za-z0-9_\.\/\-_~]+(?:>?)|(?:<?)[A-Za-z0-9_]+:\/\/[A-Za-z0-9_\.\/\-_~]+(?:>?)/g, "sh_url", -1],
				[/<\?xml/g, "sh_preproc", 2, 1],
				[/<!DOCTYPE/g, "sh_preproc", 4, 1],
				[/<!--/g, "sh_comment", 5],
				[/<(?:\/)?[A-Za-z](?:[A-Za-z0-9_:.-]*)(?:\/)?>/g, "sh_keyword", -1],
				[/<(?:\/)?[A-Za-z](?:[A-Za-z0-9_:.-]*)/g, "sh_keyword", 6, 1],
				[/&(?:[A-Za-z0-9]+);/g, "sh_preproc", -1],
				[/<(?:\/)?[A-Za-z][A-Za-z0-9]*(?:\/)?>/g, "sh_keyword", -1],
				[/<(?:\/)?[A-Za-z][A-Za-z0-9]*/g, "sh_keyword", 6, 1],
				[/@[A-Za-z]+/g, "sh_type", -1],
				[/(?:TODO|FIXME|BUG)(?:[:]?)/g, "sh_todo", -1]
			],
			[
				[/\?>/g, "sh_preproc", -2],
				[/([^=" \t>]+)([ \t]*)(=?)/g, ["sh_type", "sh_normal", "sh_symbol"], -1],
				[/"/g, "sh_string", 3]
			],
			[
				[/\\(?:\\|")/g, null, -1],
				[/"/g, "sh_string", -2]
			],
			[
				[/>/g, "sh_preproc", -2],
				[/([^=" \t>]+)([ \t]*)(=?)/g, ["sh_type", "sh_normal", "sh_symbol"], -1],
				[/"/g, "sh_string", 3]
			],
			[
				[/-->/g, "sh_comment", -2],
				[/<!--/g, "sh_comment", 5]
			],
			[
				[/(?:\/)?>/g, "sh_keyword", -2],
				[/([^=" \t>]+)([ \t]*)(=?)/g, ["sh_type", "sh_normal", "sh_symbol"], -1],
				[/"/g, "sh_string", 3]
			],
			[
				[/$/g, null, -2]
			],
			[
				[/\*\//g, "sh_comment", -2],
				[/(?:<?)[A-Za-z0-9_\.\/\-_~]+@[A-Za-z0-9_\.\/\-_~]+(?:>?)|(?:<?)[A-Za-z0-9_]+:\/\/[A-Za-z0-9_\.\/\-_~]+(?:>?)/g, "sh_url", -1],
				[/<\?xml/g, "sh_preproc", 2, 1],
				[/<!DOCTYPE/g, "sh_preproc", 4, 1],
				[/<!--/g, "sh_comment", 5],
				[/<(?:\/)?[A-Za-z](?:[A-Za-z0-9_:.-]*)(?:\/)?>/g, "sh_keyword", -1],
				[/<(?:\/)?[A-Za-z](?:[A-Za-z0-9_:.-]*)/g, "sh_keyword", 6, 1],
				[/&(?:[A-Za-z0-9]+);/g, "sh_preproc", -1],
				[/<(?:\/)?[A-Za-z][A-Za-z0-9]*(?:\/)?>/g, "sh_keyword", -1],
				[/<(?:\/)?[A-Za-z][A-Za-z0-9]*/g, "sh_keyword", 6, 1],
				[/@[A-Za-z]+/g, "sh_type", -1],
				[/(?:TODO|FIXME|BUG)(?:[:]?)/g, "sh_todo", -1]
			],
			[
				[/\*\//g, "sh_comment", -2],
				[/(?:<?)[A-Za-z0-9_\.\/\-_~]+@[A-Za-z0-9_\.\/\-_~]+(?:>?)|(?:<?)[A-Za-z0-9_]+:\/\/[A-Za-z0-9_\.\/\-_~]+(?:>?)/g, "sh_url", -1],
				[/(?:TODO|FIXME|BUG)(?:[:]?)/g, "sh_todo", -1]
			],
			[
				[/"/g, "sh_string", -2],
				[/\\./g, "sh_specialchar", -1]
			],
			[
				[/'/g, "sh_string", -2],
				[/\\./g, "sh_specialchar", -1]
			]
		];

		sh_highlightDocument();

		/*//种植系统
		$("#plantSystemBtn").xMenu({
			width: 500,
			eventType: "click", //事件类型 支持focus click hover
			dropmenu: "#plantSystemOptions"//, //弹出层
			//hiddenID: "selectposhidden" //隐藏域ID		
		});
		//固定修复
		$("#GdRepairBtn").xMenu({
			width: 500,
			eventType: "click", //事件类型 支持focus click hover
			dropmenu: "#GdRepairOptions"//, //弹出层
			//hiddenID: "selectdeptidden" //隐藏域ID		
		});
		//活动修复
		$("#HdRepairBtn").xMenu({
			width: 500,
			eventType: "click", //事件类型 支持focus click hover
			dropmenu: "#HdRepairOptions"//, //弹出层
			//hiddenID: "selectdeptidden" //隐藏域ID		
		});
		//联合修复（精密义齿）
		$("#LhRepairBtn").xMenu({
			width: 500,
			eventType: "click", //事件类型 支持focus click hover
			dropmenu: "#LhRepairOptions"//, //弹出层
			//hiddenID: "selectdeptidden" //隐藏域ID		
		});
		//数字化修复
		$("#SZHRepairBtn").xMenu({
			width: 500,
			eventType: "click", //事件类型 支持focus click hover
			dropmenu: "#SZHRepairOptions"//, //弹出层
			//hiddenID: "selectdeptidden" //隐藏域ID		
		});
		//正畸修复
		$("#ZJRepairBtn").xMenu({
			width: 500,
			eventType: "click", //事件类型 支持focus click hover
			dropmenu: "#ZjRepairOptions"//, //弹出层
			//hiddenID: "selectdeptidden" //隐藏域ID		
		});*/

});
