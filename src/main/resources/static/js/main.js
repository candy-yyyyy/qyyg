$().ready(function () {
    var userName = $.session.get('userName');
    var operNo = $.session.get('operNo');
    if (INPUT_UTIL.isNull(userName)) {
        Modal.alert("提示", "请先登录！", function () {
            window.location.href = 'login.html';
        });
        return;
    }

    $('.userName').text(' ' + userName);
    $('.logOut').unbind('click').bind('click', function () {
        $.session.remove('userName');
        window.location.href = 'login.html';
    });

    $('.link').unbind('click').bind('click', function () {
        $('#titleText').text($(this).text());
        var srcHtml = $(this).attr('srcHtml');
        $("#index").hide();
        $('#iframemPage').attr('src', srcHtml);
        $('#iframemPage').show();
    });

    getNoticeListInfo();
    getMessageListInfo(1);

    $('#messageBtn').unbind('click').bind('click', function () {
        var messageText = $('#messageText').val();
        messageText = $.trim(messageText);
        if (INPUT_UTIL.isNull(messageText)) {
            Modal.alert("", "留言不能为空！");
            return;
        }
        Modal.confirm("确认留言？", function () {
            if (messageText.length > 50) {
                Modal.alert("", "留言不能超过50字！");
                return;
            }
            $.ajax({
                type: "POST",
                url: addMessageUrl,
                contentType: "application/x-www-form-urlencoded",
                data: {
                    "content": messageText,
                    "operNo": operNo
                },
                dataType: "json",
                success: function (data) {
                    if (data.respCode == '0000') {
                        getMessageListInfo(1);
                        $('#messageText').val('');
                    } else {
                        Modal.alert("", data.respDesc);
                    }
                }
            });
        });
    });

    // 考勤按钮
    getAttendance(operNo);

    // 新闻公告发布
    var E = window.wangEditor;
    var editor = new E('#editor');
    // 或者 var editor = new E( document.getElementById('editor') )
    editor.create();

    $('#editor').css('text-align', 'left');
    $('#createNotice').unbind().bind('click', function () {
        $('#notice_modal').modal({"closeViaDimmer": false});
    });
    $('#notice_confirm_btn').unbind().bind('click', function () {
        var noticeContent = editor.txt.html();
        var noticeTitle = $.trim($('#notice_title').val());
        $.ajax({
            type: "POST",
            url: addNoticeUrl,
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            data: {
                operNo: operNo,
                noticeTitle: noticeTitle,
                noticeContent: noticeContent
            },
                success: function (data) {
                if (data.respCode == '0000') {
                    Modal.alert("发布成功！", function () {
                        // 清空输入框
                        $('#notice_title').val('');
                        editor.txt.text('');
                        window.location.reload();
                    });
                } else {
                    Modal.alert(data.respDesc);
                }
            },
            error: function () {
                Modal.alert("新增公告AJAX请求失败！");
            }
        });
    });

    // 新闻公告点击事件
    $('#newsNotice').on('mouseover mouseout click', '.notice-css', function () {
        if (event.type == "mouseover") {
            //鼠标悬浮
            $(this).css('color', '#0e90d2');
        } else if (event.type == "mouseout") {
            //鼠标离开
            $(this).css('color', 'black');
        } else if (event.type == "click") {
            var noticeId = $(this).attr('notice_id');

        }
    });
});

// 公告列表
function getNoticeListInfo() {
    $.ajax({
        type: "POST",
        url: getNoticeListInfoUrl,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: {
            pageNo: 1,
            pageSize: 6
        },
        success: function (data) {
            if (data.respCode == '0000') {
                if (data.total > 0) {
                    var str = '';
                    $.each(data.noticeList, function (i, item) {
                        str += '<li><div class="admin-task-meta" style="font-size:15px;"> 发表于 ' + item.createTime + ' by ' + item.noticeAuthor + '</div><div class="admin-task-bd" style="font-size:18px;"><span class="notice-css" notice_id="' + item.id + '">' + item.noticeTitle + '</span></div></li>';
                    });
                    $('.admin-content-task').html(str);
                    // 分页判断 余数为0时 页数不需要加一
                    var pageTotal;
                    if (parseInt(data.total) % 5 == 0) {
                        pageTotal = parseInt(parseInt(data.total) / 6);
                    } else {
                        pageTotal = parseInt(parseInt(data.total) / 6) + 1;
                    }
                    $("#page1").paging({
                        pageNo: 1,
                        totalPage: pageTotal,
                        totalSize: data.total,
                        callback: function (num) {
                            $.ajax({
                                type: "POST",
                                url: getNoticeListInfoUrl,
                                contentType: "application/x-www-form-urlencoded",
                                data: {
                                    "pageNo": num,
                                    "pageSize": 6
                                },
                                dataType: "json",
                                success: function (data) {
                                    if (data.respCode == '0000') {
                                        if (data.total > 0) {
                                            var str = '';
                                            $.each(data.noticeList, function (i, item) {
                                                str += '<li><div class="admin-task-meta" style="font-size:15px;"> 发表于 ' + item.createTime + ' by ' + item.noticeAuthor + '</div><div  class="admin-task-bd" style="font-size:18px;"><span class="notice-css" notice_id="' + item.id + '">' + item.noticeTitle + '</span></div></li>';
                                            });
                                            $('.admin-content-task').html(str);
                                        }
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

// 留言列表
function getMessageListInfo(num) {
    $.ajax({
        type: "POST",
        url: getMessageListInfoUrl,
        contentType: "application/x-www-form-urlencoded",
        data: {
            "pageNo": num,
            "pageSize": "3"
        },
        dataType: "json",
        success: function (data) {
            if (data.respCode == '0000') {
                if (data.args.total > 0) {
                    var str = '';
                    $.each(data.args.messageList, function (i, item) {
                        str += '<li class="am-comment">' +
                            '<div class="am-comment-main">\n' +
                            '<header class="am-comment-hd">\n' +
                            '<div class="am-comment-meta"><a href="#" class="am-comment-author">' + item.staffName + '</a> 评论于 <time>' + item.createTime + '</time></div>\n' +
                            '</header>\n' +
                            '<div class="am-comment-bd"><p style="word-wrap:break-word">' + item.content + '</p>\n' +
                            ' </div>\n' +
                            '</div>\n' +
                            '</li>';
                    });
                    $('.admin-content-comment').html(str);
                    // 分页判断 余数为0时 页数不需要加一
                    var pageTotal;
                    if (parseInt(data.args.total) % 3 == 0) {
                        pageTotal = parseInt(parseInt(data.args.total) / 3);
                    } else {
                        pageTotal = parseInt(parseInt(data.args.total) / 3) + 1;
                    }
                    $("#page").paging({
                        pageNo: 1,
                        totalPage: pageTotal,
                        totalSize: data.args.total,
                        callback: function (num) {
                            $.ajax({
                                type: "POST",
                                url: getMessageListInfoUrl,
                                contentType: "application/x-www-form-urlencoded",
                                data: {
                                    "pageNo": num,
                                    "pageSize": "3"
                                },
                                dataType: "json",
                                success: function (data) {
                                    if (data.respCode == '0000') {
                                        var str = '';
                                        $.each(data.args.messageList, function (i, item) {
                                            str += '<li class="am-comment">' +
                                                '<div class="am-comment-main">\n' +
                                                '<header class="am-comment-hd">\n' +
                                                '<div class="am-comment-meta"><a href="#" class="am-comment-author">' + item.staffName + '</a> 评论于 <time>' + item.createTime + '</time></div>\n' +
                                                '</header>\n' +
                                                '<div class="am-comment-bd"><p style="word-wrap:break-word">' + item.content + '</p>\n' +
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

function getAttendance(operNo) {
    $.ajax({
        type: "POST",
        url: getAttendanceTodayUlr,
        contentType: "application/x-www-form-urlencoded",
        data: {
            "operNo": operNo
        },
        dataType: "json",
        success: function (data) {
            if (data.respCode == '0000') {
                if (INPUT_UTIL.isNull(data.args)) {   // 没有值表示今天还没有考勤 按钮为上班打卡

                    $('#attendanceBtn').unbind().bind('click', function () {
                        $.ajax({
                            type: "POST",
                            url: addAttendanceUrl,
                            contentType: "application/x-www-form-urlencoded",
                            data: {
                                "operNo": operNo
                            },
                            dataType: "json",
                            success: function (data) {
                                if (data.respCode == '0000') {
                                    Modal.alert(data.respDesc, function () {
                                        window.location.reload();
                                    });
                                } else {
                                    Modal.alert("", data.respDesc);
                                }
                            },
                            error: function () {
                                Modal.alert("上班打卡AJAX请求失败！");
                            }
                        });
                    });


                } else {  // 有值表示今天已考勤
                    if (INPUT_UTIL.isNull(data.args.attendanceEtime)) {   // 需要下班打卡
                        $('#attendanceBtn').text('下班打卡');
                        $('#attendanceBtn').unbind().bind('click', function () {
                            $.ajax({
                                type: "POST",
                                url: updateAttendanceUrl,
                                contentType: "application/x-www-form-urlencoded",
                                data: {
                                    "attendanceId": data.args.attendanceId
                                },
                                dataType: "json",
                                success: function (data) {
                                    if (data.respCode == '0000') {
                                        Modal.alert(data.respDesc, function () {
                                            window.location.reload();
                                        });
                                    } else {
                                        Modal.alert("", data.respDesc);
                                    }
                                },
                                error: function () {
                                    Modal.alert("下班打卡AJAX请求失败！");
                                }
                            });
                        })


                    } else {  // 今日打卡完毕
                        $('#attendanceBtn').text('今日考勤完毕！');
                        $('#attendanceBtn').css('cursor', 'auto');
                    }
                }
            } else {
                Modal.alert("", data.respDesc);
            }
        },
        error: function () {
            Modal.alert("查询考勤记录AJAX请求失败！");
        }
    });
}