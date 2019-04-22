$().ready(function () {
    getJobList();
    $('#searchBtn').unbind().bind('click', function () {
        var jobName = $.trim($('#job_name').val());
        getJobList(jobName);
    });

    // 新增工种
    $('#job_add_btn').unbind().bind('click', function () {
        $('#job_add_modal').modal({"closeViaDimmer": false});
    });
    $('#jobAddBtn').unbind().bind('click', function () {
        var jobName = $.trim($('#job_name_add').val());
        var jobDesc = $.trim($('#job_desc_add').val());
        var paramJson = {
            jobName: jobName,
            jobDesc: jobDesc
        };
        addJob(paramJson);
    });
    // 失效
    $('#jobInfoList').on('click', '.staff_unable_btn', function () {
        var job_id = $(this).parent().parent().parent().siblings('.job').attr("jobId");
        Modal.confirm("确认修改工种状态？", function () {
            $.ajax({
                type: "POST",
                url: updateJobStateUrl,
                contentType: "application/x-www-form-urlencoded",
                data: {
                    jobId: job_id,
                    state: 1
                },
                dataType: "json",
                success: function (data) {
                    if (data.respCode == '0000') {
                        Modal.alert("工种失效成功!", function () {
                            $('#searchBtn').trigger('click');
                        });
                    } else {
                        Modal.alert(data.respDesc);
                    }
                },
                error: function () {
                    Modal.alert("工种失效AJAX请求失败！");
                }
            });
        })
    });
    // 生效
    $('#jobInfoList').on('click', '.staff_able_btn', function () {
        var job_id = $(this).parent().parent().parent().siblings('.job').attr("jobId");
        Modal.confirm("确认修改工种状态？", function () {
            $.ajax({
                type: "POST",
                url: updateJobStateUrl,
                contentType: "application/x-www-form-urlencoded",
                data: {
                    jobId: job_id,
                    state: 0
                },
                dataType: "json",
                success: function (data) {
                    if (data.respCode == '0000') {
                        Modal.alert("工种生效成功!", function () {
                            $('#searchBtn').trigger('click');
                        });
                    } else {
                        Modal.alert(data.respDesc);
                    }
                },
                error: function () {
                    Modal.alert("工种生效AJAX请求失败！");
                }
            });
        })
    });

});

// 获取工种信息
function getJobList(jobName, state) {
    $.ajax({
        type: "POST",
        url: getJobListUrl,
        contentType: "application/x-www-form-urlencoded",
        data: {
            jobName: jobName,
            state: state,
            pageNo: 1,
            pageSize: 10
        },
        dataType: "json",
        success: function (data) {
            if (data.respCode == '0000') {
                $('#jobInfoList').empty();
                $('#page').empty();
                $('#total').text(data.total);
                if (!INPUT_UTIL.isNull(data.jobList)) {
                    if (data.total == 0) {
                        return;
                    }
                    var jobHtml = "";
                    $.each(data.jobList, function (i, item) {
                        if (item.state == 0) {
                            item.state = '有效';
                        } else if (item.state == 1) {
                            item.state = '失效';
                        }
                        jobHtml += "<tr><td class='job' jobId='"+item.jobId+"'>" + item.jobName + "</td><td>" + item.jobDesc + "</td><td>" + item.state + "</td><td>" +
                            '<div class="am-btn-toolbar">\n' +
                            '<div class="am-btn-group am-btn-group-xs">\n' +
                            '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_able_btn" style="color:#49acdd;"><span class="am-icon-wrench" style="color:#49acdd;"></span> 生效</button>\n' +
                            '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_unable_btn" style="color:#dd514c;"><span class="am-icon-trash-o" style="color:#dd514c;"></span> 失效</button>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</td></tr>';
                    });
                    $('#jobInfoList').html(jobHtml);
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
                                jobName: jobName,
                                state: state
                            }
                            $.ajax({
                                type: "POST",
                                url: getJobListUrl,
                                contentType: "application/x-www-form-urlencoded",
                                data: jsonParam,
                                dataType: "json",
                                success: function (data) {
                                    if (data.respCode == '0000') {
                                        var jobHtml = "";
                                        $.each(data.jobList, function (i, item) {
                                            if (item.state == 0) {
                                                item.state = '有效';
                                            } else if (item.state == 1) {
                                                item.state = '失效';
                                            }
                                            jobHtml += "<tr><td>" + item.jobName + "</td><td>" + item.jobDesc + "</td><td>" + item.state + "</td><td>" +
                                                '<div class="am-btn-toolbar">\n' +
                                                '<div class="am-btn-group am-btn-group-xs">\n' +
                                                '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_able_btn" style="color:#49acdd;"><span class="am-icon-wrench" style="color:#49acdd;"></span> 生效</button>\n' +
                                                '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_unable_btn" style="color:#dd514c;"><span class="am-icon-trash-o" style="color:#dd514c;"></span> 失效</button>\n' +
                                                '</div>\n' +
                                                '</div>\n' +
                                                '</td></tr>';
                                        });
                                        $('#jobInfoList').html(jobHtml);
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
            Modal.alert("工种信息列表查询AJAX请求失败！");
        }
    });
}

/**
 * 新增工种
 */
function addJob(paramJson) {
    $.ajax({
        type: "POST",
        url: addJobUrl,
        contentType: "application/x-www-form-urlencoded",
        data: paramJson,
        dataType: "json",
        success: function (data) {
            if (data.respCode == '0000') {
                Modal.alert("新增工种成功", function () {
                    $('.staff_detail_input').val('');
                    window.location.reload();
                });
            } else {
                Modal.alert(data.respDesc);
            }
        },
        error: function () {
            Modal.alert("新增工种AJAX请求失败！");
        }
    });
}