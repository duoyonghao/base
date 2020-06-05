$(function() {
    var _editArea = $('#editArea');

    //显示隐藏发送按钮      
    /*var _editAreaInterval;
    $('#editArea').focus(function() {
        var _this = $(this),
        html;
        _editAreaInterval = setInterval(function() {
            html = _this.html();
            if (html.length > 0) {
                $('#web_wechat_pic').hide();
                $('#btn_send').show();
            } else {
                $('#web_wechat_pic').show();
                $('#btn_send').hide();
            }
        },
        200);
    });

    $('#editArea').blur(function() {
        clearInterval(_editAreaInterval);
    });*/

    //显示隐藏表情栏
    $('.web_wechat_face').click(function() {
        $('.box_ft_bd').toggleClass('hide');
        resetMessageArea();
    });

    //切换表情主题
    $('.exp_hd_item').click(function() {
        var _this = $(this),
        i = _this.data('i');
        $('.exp_hd_item,.exp_cont').removeClass('active');
        _this.addClass('active');
        $('.exp_cont').eq(i).addClass('active');
        resetMessageArea();
    });
    $('.box_ft_bd').mouseleave(function(){  /*鼠标离开 选择界面时候，关闭表情弹框*/
    	$(".box_ft_bd").addClass(" hide");
    })
    //选中表情
    $('.exp_cont button').click(function() {
        var _this = $(this);
        var html = '<img class="' + _this[0].className + '" title="' + _this.html() + '" src="../../static/image/kqdsFront/wechat/chat/spacer.gif"/>';
        /*_editArea.html(_editArea.html() + html);*/
        add(html);
        //$('#web_wechat_pic').hide();
       // $('#btn_send').show();
        //add();
        $(".box_ft_bd").addClass(" hide");//选择一个表情以后 关闭表情选择面板
    });

    resetMessageArea();

    function sendMsg(str) {}

    function resetMessageArea() {
        $('#messageList').animate({
            'scrollTop': 999
        },
        500);
    }
});