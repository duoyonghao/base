/*
This software is allowed to use under GPL or you need to obtain Commercial or Enterise License
to use it in non-GPL project. Please contact sales@dhtmlx.com for details
*/
scheduler.attachEvent("onTemplatesReady",function(){function e(d,b,a,c){for(var i=b.getElementsByTagName(d),h=a.getElementsByTagName(d),g=h.length-1;g>=0;g--)if(a=h[g],c){var f=document.createElement("SPAN");f.className="dhx_text_disabled";f.innerHTML=c(i[g]);a.parentNode.insertBefore(f,a);a.parentNode.removeChild(a)}else if(a.disabled=!0,b.checked)a.checked=!0}var r=scheduler.config.lightbox.sections,k=null,n=scheduler.config.buttons_left.slice(),o=scheduler.config.buttons_right.slice();scheduler.attachEvent("onBeforeLightbox",
function(d){if(this.config.readonly_form||this.getEvent(d).readonly){this.config.readonly_active=!0;for(var b=0;b<this.config.lightbox.sections.length;b++)this.config.lightbox.sections[b].focus=!1}else this.config.readonly_active=!1,scheduler.config.buttons_left=n.slice(),scheduler.config.buttons_right=o.slice();var a=this.config.lightbox.sections;if(this.config.readonly_active){for(var c=!1,b=0;b<a.length;b++)if(a[b].type=="recurring"){k=a[b];this.config.readonly_active&&a.splice(b,1);break}!c&&
!this.config.readonly_active&&k&&a.splice(a.length-2,0,k);for(var i=["dhx_delete_btn","dhx_save_btn"],h=[scheduler.config.buttons_left,scheduler.config.buttons_right],b=0;b<i.length;b++)for(var g=i[b],f=0;f<h.length;f++){for(var e=h[f],l=-1,j=0;j<e.length;j++)if(e[j]==g){l=j;break}l!=-1&&e.splice(l,1)}}this.resetLightbox();return!0});var p=scheduler._fill_lightbox;scheduler._fill_lightbox=function(){var d=this.getLightbox();if(this.config.readonly_active)d.style.visibility="hidden",d.style.display=
"block";var b=p.apply(this,arguments);if(this.config.readonly_active)d.style.visibility="",d.style.display="none";if(this.config.readonly_active){var a=this.getLightbox(),c=this._lightbox_r=a.cloneNode(!0);c.id=scheduler.uid();e("textarea",a,c,function(a){return a.value});e("input",a,c,!1);e("select",a,c,function(a){return!a.options.length?"":a.options[Math.max(a.selectedIndex||0,0)].text});a.parentNode.insertBefore(c,a);m.call(this,c);scheduler._lightbox&&scheduler._lightbox.parentNode.removeChild(scheduler._lightbox);
this._lightbox=c;if(scheduler.config.drag_lightbox)c.firstChild.onmousedown=scheduler._ready_to_dnd;this.setLightboxSize();c.onclick=function(a){var b=a?a.target:event.srcElement;if(!b.className)b=b.previousSibling;if(b&&b.className)switch(b.className){case "dhx_cancel_btn":scheduler.callEvent("onEventCancel",[scheduler._lightbox_id]),scheduler._edit_stop_event(scheduler.getEvent(scheduler._lightbox_id),!1),scheduler.hide_lightbox()}}}return b};var m=scheduler.showCover;scheduler.showCover=function(){this.config.readonly_active||
m.apply(this,arguments)};var q=scheduler.hide_lightbox;scheduler.hide_lightbox=function(){if(this._lightbox_r)this._lightbox_r.parentNode.removeChild(this._lightbox_r),this._lightbox_r=this._lightbox=null;return q.apply(this,arguments)}});
