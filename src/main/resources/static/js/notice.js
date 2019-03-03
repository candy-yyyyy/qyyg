$().ready(function () {
    var userName = $.session.get('userName');
    $('.userName').text(' ' + userName);
    $('.logOut').unbind('click').bind('click', function () {
        $.session.remove('userName');
        window.location.href = 'login.html';
    });

    $('.link').unbind('click').bind('click', function () {
        var srcHtml = $(this).attr('srcHtml');
        $.session.set("forwardFlag",srcHtml);
        window.location.href = "../main.html";
    });
    var notice_id = getQueryString("notice_id");
    $.ajax({
        type: "POST",
        url: getNoticeUrl,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        data: {
            noticeId: parseInt(notice_id)
        },
        success: function (data) {
            if (data.respCode == '0000') {
                var content = data.notice.noticeContent;
                var title = data.notice.noticeTitle;
                var create_time = data.notice.createTime;
                var author = data.notice.noticeAuthor;

                $('#title h1').text(title);
                $('#notice_info').html(content);
                $('#publish_info').text("发布时间："+create_time+" 作者："+author);
            } else {
                Modal.alert(data.respDesc);
            }
        },
        error: function () {
            Modal.alert("查询新闻公告AJAX请求失败！");
        }
    });


});

// 获取url参申诉
function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}