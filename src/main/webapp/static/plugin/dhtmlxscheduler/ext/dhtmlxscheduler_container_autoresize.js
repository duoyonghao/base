/*
This software is allowed to use under GPL or you need to obtain Commercial or Enterise License
to use it in non-GPL project. Please contact sales@dhtmlx.com for details
*/
(function() {
    scheduler.config.container_autoresize = !0;
    scheduler.config.month_day_min_height = 90;
    var p = scheduler._pre_render_events;
    scheduler._pre_render_events = function(e, c) {
        if (!scheduler.config.container_autoresize) return p.apply(this, arguments);
        var g = this.xy.bar_height,
        l = this._colsS.heights,
        a = this._colsS.heights = [0, 0, 0, 0, 0, 0, 0],
        b = this._els.dhx_cal_data[0],
        e = this._table_view ? this._pre_render_events_table(e, c) : this._pre_render_events_line(e, c);
        if (this._table_view) if (c) this._colsS.heights = l;
        else {
            var f = b.firstChild;
            if (f.rows) {
                for (var d = 0; d < f.rows.length; d++) {
                    a[d]++;
                    if (a[d] * g > this._colsS.height - 22) {
                        var h = f.rows[d].cells,
                        j = this._colsS.height - 22;
                        this.config.max_month_events * 1 !== this.config.max_month_events || a[d] <= this.config.max_month_events ? j = a[d] * g: (this.config.max_month_events + 1) * g > this._colsS.height - 22 && (j = (this.config.max_month_events + 1) * g);
                        for (var i = 0; i < h.length; i++) h[i].childNodes[1].style.height = j + "px";
                        a[d] = (a[d - 1] || 0) + h[0].offsetHeight
                    }
                    a[d] = (a[d - 1] || 0) + f.rows[d].cells[0].offsetHeight
                }
                a.unshift(0)
            } else if (!e.length && this._els.dhx_multi_day[0].style.visibility == "visible" && (a[0] = -1), e.length || a[0] == -1) {
                var n = f.parentNode.childNodes,
                m = (a[0] + 1) * g + 1 + "px";
                b.style.top = this._els.dhx_cal_navline[0].offsetHeight + this._els.dhx_cal_header[0].offsetHeight + parseInt(m, 10) + "px";
                b.style.height = this._obj.offsetHeight - parseInt(b.style.top, 10) - (this.xy.margin_top || 0) + "px";
                var k = this._els.dhx_multi_day[0];
                k.style.height = m;
                k.style.visibility = a[0] == -1 ? "hidden": "visible";
                k = this._els.dhx_multi_day[1];
                k.style.height = m;
                k.style.visibility = a[0] == -1 ? "hidden": "visible";
                k.className = a[0] ? "dhx_multi_day_icon": "dhx_multi_day_icon_small";
                this._dy_shift = (a[0] + 1) * g;
                a[0] = 0
            }
        }
        return e
    };
    var n = ["dhx_cal_navline", "dhx_cal_header", "dhx_multi_day", "dhx_cal_data"],
    o = function(e) {
        for (var c = 0,
        g = 0; g < n.length; g++) {
            var l = n[g],
            a = scheduler._els[l] ? scheduler._els[l][0] : null,
            b = 0;
            switch (l) {
            case "dhx_cal_navline":
            case "dhx_cal_header":
                b = parseInt(a.style.height, 10);
                break;
            case "dhx_multi_day":
                b = a ? a.offsetHeight: 0;
                b == 1 && (b = 0);
                break;
            case "dhx_cal_data":
                var b = Math.max(a.offsetHeight - 1, a.scrollHeight),
                f = scheduler.getState().mode;
                if (f == "month") {
                    if (scheduler.config.month_day_min_height && !e) var d = a.getElementsByTagName("tr").length,
                    b = d * scheduler.config.month_day_min_height;
                    if (e) a.style.height = b + "px"
                }
                if (scheduler.matrix && scheduler.matrix[f]) if (e) b += 2,
                a.style.height = b + "px";
                else for (var b = 2,
                h = scheduler.matrix[f], j = h.y_unit, i = 0; i < j.length; i++) b += !j[i].children ? h.dy: h.folder_dy || h.dy;
                if (f == "day" || f == "week") b += 2
            }
            c += b
        }
        scheduler._obj.style.height = c + "px";
        e || scheduler.updateView()
    },
    c = function() {
        var c = scheduler.getState().mode;
        o(); (scheduler.matrix && scheduler.matrix[c] || c == "month") && window.setTimeout(function() {
            o(!0)
        },
        1)
    };
    scheduler.attachEvent("onViewChange", c);
    scheduler.attachEvent("onXLE", c);
    scheduler.attachEvent("onEventChanged", c);
    scheduler.attachEvent("onEventCreated", c);
    scheduler.attachEvent("onEventAdded", c);
    scheduler.attachEvent("onEventDeleted", c);
    scheduler.attachEvent("onAfterSchedulerResize", c);
    scheduler.attachEvent("onClearAll", c)
})();