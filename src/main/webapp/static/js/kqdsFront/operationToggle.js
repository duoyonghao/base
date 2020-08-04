//点击选项显示隐藏病历列表  father:父布局 li的class
function showHiddenCase(thi){
    var showVal = $(thi).parents(".hcd").find(".caseContiner").css("display");
    if(showVal=="none"){
        //先隐藏所有
        $(".caseContiner").each(function(i,obj){
            $(this).css("display","none");
        });
        $(thi).parents(".hcd").find(".caseContiner").css("display","block");
    }else{
        $(thi).parents(".hcd").find(".caseContiner").css("display","none");
    }
    var e=window.event || arguments.callee.caller.arguments[0];
    e.preventDefault();
    e.stopPropagation() ;
}

function toggleOperationCase(th){
    var versionNow = $(th).text();
    if(versionNow=="切换旧版"){
        $(th).text("切换新版");
        $(".operation_examine").css("display","block").prev().css("display","none");
    }else{
        $(th).text("切换旧版");
        $(".operation_examine_patients").css("display","block").next().css("display","none");
    }

}

//全局监听
document.addEventListener("click",function(event){
    event=event||window.event;
    var eve=event.target||eve.elementSrc;
    if(eve.className=='verification' || eve.className=='btnStyle'){
    }else{
        $(".caseContiner").each(function(i,obj){
            $(this).css("display","none");
        });
    }
});//所有组件添加点击事件