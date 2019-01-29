$().ready(function(){
    var userName = $.session.get('userName');
    var operNo = $.session.get('operNo');
    if(INPUT_UTIL.isNull(userName)){
        Modal.alert("提示","请先登录！",function(){
            window.location.href = 'login.html';
        });
        return;
    }

    $('.userName').text(' '+userName);
    $('.logOut').unbind().bind('click',function(){
        $.session.remove('userName');
        window.location.href = 'login.html';
    });

    $('.link').unbind().bind('click',function(){
        $('#titleText').text($(this).text());
        var srcHtml = $(this).attr('srcHtml');
        $("#index").hide();
        $('#iframemPage').attr('src',srcHtml);
        $('#iframemPage').show();
    });

    getNoticeListInfo();
    getMessageListInfo(1);

    $('#messageBtn').unbind().bind('click',function(){
        var messageText = $('#messageText').val();
        messageText = $.trim(messageText);
        if(INPUT_UTIL.isNull(messageText)){
            Modal.alert("","留言不能为空！");
            return;
        }
        Modal.confirm("确认留言？",function(){
            if(messageText.length>50){
                Modal.alert("","留言不能超过50字！");
                return;
            }
            $.ajax({
                type: "POST",
                url: addMessageUrl,
                contentType:"application/x-www-form-urlencoded",
                data:{
                    "content":messageText,
                    "operNo":operNo
                },
                dataType: "json",
                success: function(data){
                    if(data.respCode=='0000'){
                        getMessageListInfo(1);
                        $('#messageText').val('');
                    }else{
                        Modal.alert("",data.respDesc);
                    }
                }
            });
        });
    });
});

// 公告列表
function getNoticeListInfo(){
    $.ajax({
        type: "POST",
        url: getNoticeListInfoUrl,
        contentType:"application/x-www-form-urlencoded",
        //contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){
            if(data.respCode=='0000'){
               var str = '';
               $.each(data.args,function(i,item){
                    str += '<li><div class="admin-task-meta" style="font-size:15px;"> 发表于 '+item.createTime+' by '+item.noticeAuthor+'</div><div class="admin-task-bd" style="font-size:18px;">'+item.noticeTitle+'</div></li>';
               });
                $('.admin-content-task').html(str);
            }
        }
    });
}

// 留言列表
function getMessageListInfo(num){
    $.ajax({
        type: "POST",
        url: getMessageListInfoUrl,
        contentType:"application/x-www-form-urlencoded",
        data:{
          "pageNo":num,
          "pageSize":"3"
        },
        dataType: "json",
        success: function(data){
            if(data.respCode=='0000'){
                if(data.args.total>0){
                    var str = '';
                    $.each(data.args.messageList,function(i,item){
                        str += '<li class="am-comment">' +
                            '<div class="am-comment-main">\n' +
                            '<header class="am-comment-hd">\n' +
                            '<div class="am-comment-meta"><a href="#" class="am-comment-author">'+item.staffName+'</a> 评论于 <time>'+item.createTime+'</time></div>\n' +
                            '</header>\n' +
                            '<div class="am-comment-bd"><p style="word-wrap:break-word">'+item.content+'</p>\n' +
                            ' </div>\n' +
                            '</div>\n' +
                            '</li>';
                    });
                    $('.admin-content-comment').html(str);
                    // 分页判断 余数为0时 页数不需要加一
                    var pageTotal;
                    if(parseInt(data.args.total)%3==0){
                        pageTotal = parseInt(parseInt(data.args.total)/3);
                    }else{
                        pageTotal = parseInt(parseInt(data.args.total)/3)+1;
                    }
                    $("#page").paging({
                        pageNo:1,
                        totalPage: pageTotal,
                        totalSize: data.args.total,
                        callback: function(num) {
                            $.ajax({
                                type: "POST",
                                url: getMessageListInfoUrl,
                                contentType:"application/x-www-form-urlencoded",
                                data:{
                                    "pageNo":num,
                                    "pageSize":"3"
                                },
                                dataType: "json",
                                success: function(data){
                                    if(data.respCode=='0000'){
                                        var str = '';
                                        $.each(data.args.messageList,function(i,item){
                                            str += '<li class="am-comment">' +
                                                '<div class="am-comment-main">\n' +
                                                '<header class="am-comment-hd">\n' +
                                                '<div class="am-comment-meta"><a href="#" class="am-comment-author">'+item.staffName+'</a> 评论于 <time>'+item.createTime+'</time></div>\n' +
                                                '</header>\n' +
                                                '<div class="am-comment-bd"><p style="word-wrap:break-word">'+item.content+'</p>\n' +
                                                ' </div>\n' +
                                                '</div>\n' +
                                                '</li>';
                                        });
                                        $('.admin-content-comment').html(str);
                                    }
                                }
                            });
                        }
                    })
                }
            }
        }
    });
}