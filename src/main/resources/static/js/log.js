$().ready(function () {
    $('.search_input').css({"float": "left","margin-left":"20px"});
    $('#start_time').datetimepicker();
    $('#end_time').datetimepicker();
    getLogList();
    $('#searchBtn').unbind().bind('click', function () {
        var oper_no = $.trim($('#oper_no').val());
        var start_time = $.trim($('#start_time').val());
        var end_time = $.trim($('#end_time').val());
        if (!INPUT_UTIL.isNull(start_time) && !INPUT_UTIL.isNull(end_time)) {
            if (new Date(start_time) > new Date(end_time)) {
                Modal.alert("起始时间不能大于结束时间！");
                return;
            }
        }
        getLogList(oper_no, start_time, end_time);
    });

});

// 获取日志信息
function getLogList(operNo, startTime, endTime) {
    $.ajax({
        type: "POST",
        url: getLogListUrl,
        contentType: "application/x-www-form-urlencoded",
        data: {
            operNo: operNo,
            startTime: startTime,
            endTime: endTime,
            pageNo: 1,
            pageSize: 10
        },
        dataType: "json",
        success: function (data) {
            if (data.respCode == '0000') {
                $('#logInfoList').empty();
                $('#page').empty();
                $('#total').text(data.total);
                if (!INPUT_UTIL.isNull(data.logList)) {
                    if (data.total == 0) {
                        return;
                    }
                    var logHtml = "";
                    $.each(data.logList, function (i, item) {
                        logHtml += "<tr><td>" + item.operNo + "</td><td>" + item.url + "</td><td>" + timestampToTime(item.logTime) + "</td><td>" + item.logRequestInfo + "</td><td>" + item.logResponseInfo + "</td></tr>"
                    });
                    $('#logInfoList').html(logHtml);
                    // 分页判断 余数为0时 页数不需要加一
                    var pageTotal;
                    if (parseInt(data.total) % 10 == 0) { // 10条一页
                        pageTotal = parseInt(parseInt(data.total) / 10);
                    } else {
                        pageTotal = parseInt(parseInt(data.total) / 10) + 1;
                    }
                    $("#page").paging({
                        pageNo: 1,
                        totalPage: pageTotal,
                        totalSize: data.total,
                        callback: function (num) {
                            var jsonParam = {
                                pageNo: num,
                                pageSize: 10,
                                operNo: operNo,
                                startTime: startTime,
                                endTime: endTime
                            }
                            $.ajax({
                                type: "POST",
                                url: getLogListUrl,
                                contentType: "application/x-www-form-urlencoded",
                                data: jsonParam,
                                dataType: "json",
                                success: function (data) {
                                    if (data.respCode == '0000') {
                                        var logHtml = "";
                                        $.each(data.logList, function (i, item) {
                                            logHtml += "<tr><td>" + item.operNo + "</td><td>" + item.url + "</td><td>" + timestampToTime(item.logTime) + "</td><td>" + item.logRequestInfo + "</td><td>" + item.logResponseInfo + "</td></tr>"
                                        });
                                        $('#logInfoList').html(logHtml);
                                    }
                                }
                            });
                        }
                    })
                }
            } else {
                Modal.alert(data.respDesc);
            }
        },
        error: function () {
            Modal.alert("日志信息列表查询AJAX请求失败！");
        }
    });
}