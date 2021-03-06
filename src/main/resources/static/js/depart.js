$().ready(function () {
    getDepartList();
    $('#searchBtn').unbind().bind('click', function () {
        var departName = $.trim($('#depart_name').val());
        getDepartList(departName);
    });

    // 新增部门
    $('#depart_add_btn').unbind().bind('click', function () {
        // 获取有效的员工
        var staffList = getStaff();
        var appendStr = '';
        $.each(staffList, function (i, item) {
            appendStr += "<option value='" + item.staffName + "'>" + item.staffName + "</option>";
        });
        $('#depart_manager_add').html(appendStr);
        $('#depart_add_modal').modal({"closeViaDimmer": false});
    });
    $('#departAddBtn').unbind().bind('click', function () {
        var departName = $.trim($('#depart_name_add').val());
        var departDesc = $.trim($('#depart_desc_add').val());
        var departManager = $.trim($('#depart_manager_add').val());
        var paramJson = {
            departmentName: departName,
            departmentDesc: departDesc,
            departmentManager: departManager
        };
        addDepart(paramJson);
    });

    // 失效
    $('#departInfoList').on('click', '.staff_unable_btn', function () {
        var depart_id = $(this).parent().parent().parent().siblings('.depart').attr("depart_id");
        Modal.confirm("确认修改部门状态？", function () {
            $.ajax({
                type: "POST",
                url: updateDepartStateUrl,
                contentType: "application/x-www-form-urlencoded",
                data: {
                    departId: depart_id,
                    state: 1
                },
                dataType: "json",
                success: function (data) {
                    if (data.respCode == '0000') {
                        Modal.alert("部门失效成功!", function () {
                            $('#searchBtn').trigger('click');
                        });
                    } else {
                        Modal.alert(data.respDesc);
                    }
                },
                error: function () {
                    Modal.alert("部门失效AJAX请求失败！");
                }
            });
        })
    });
    // 生效
    $('#departInfoList').on('click', '.staff_able_btn', function () {
        var depart_id = $(this).parent().parent().parent().siblings('.depart').attr("depart_id");
        Modal.confirm("确认修改部门状态？", function () {
            $.ajax({
                type: "POST",
                url: updateDepartStateUrl,
                contentType: "application/x-www-form-urlencoded",
                data: {
                    departId: depart_id,
                    state: 0
                },
                dataType: "json",
                success: function (data) {
                    if (data.respCode == '0000') {
                        Modal.alert("部门生效成功!", function () {
                            $('#searchBtn').trigger('click');
                        });
                    } else {
                        Modal.alert(data.respDesc);
                    }
                },
                error: function () {
                    Modal.alert("部门生效AJAX请求失败！");
                }
            });
        })
    });
});

// 获取部门信息
function getDepartList(departName, state) {
    $.ajax({
        type: "POST",
        url: getDepartListUrl,
        contentType: "application/x-www-form-urlencoded",
        data: {
            departmentName: departName,
            state: state,
            pageNo: 1,
            pageSize: 10
        },
        dataType: "json",
        success: function (data) {
            if (data.respCode == '0000') {
                $('#departInfoList').empty();
                $('#page').empty();
                $('#total').text(data.total);
                if (!INPUT_UTIL.isNull(data.departList)) {
                    if (data.total == 0) {
                        return;
                    }
                    var departHtml = "";
                    $.each(data.departList, function (i, item) {
                        if (item.state == 0) {
                            item.state = '有效';
                        } else if (item.state == 1) {
                            item.state = '失效';
                        }
                        departHtml += "<tr><td class='depart' depart_id='"+item.departmentId+"'>" + item.departmentName + "</td><td>" + item.departmentManager + "</td><td>" + item.departmentDesc + "</td><td>" + item.state + "</td><td>" +
                            '<div class="am-btn-toolbar">\n' +
                            '<div class="am-btn-group am-btn-group-xs">\n' +
                            '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_able_btn" style="color:#49acdd;"><span class="am-icon-wrench" style="color:#49acdd;"></span> 生效</button>\n' +
                            '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_unable_btn" style="color:#dd514c;"><span class="am-icon-trash-o" style="color:#dd514c;"></span> 失效</button>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            "</td></tr>";
                    });
                    $('#departInfoList').html(departHtml);
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
                                departmentName: departName,
                                state: state
                            }
                            $.ajax({
                                type: "POST",
                                url: getDepartListUrl,
                                contentType: "application/x-www-form-urlencoded",
                                data: jsonParam,
                                dataType: "json",
                                success: function (data) {
                                    if (data.respCode == '0000') {
                                        var departHtml = "";
                                        $.each(data.departList, function (i, item) {
                                            if (item.state == 0) {
                                                item.state = '有效';
                                            } else if (item.state == 1) {
                                                item.state = '失效';
                                            }
                                            departHtml += "<tr><td class='depart' depart_id='"+item.departmentId+"'>" + item.departmentName + "</td><td>" + item.departmentManager + "</td><td>" + item.departmentDesc + "</td><td>" + item.state + "</td><td>" +
                                                '<div class="am-btn-toolbar">\n' +
                                                '<div class="am-btn-group am-btn-group-xs">\n' +
                                                '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_able_btn" style="color:#49acdd;"><span class="am-icon-wrench" style="color:#49acdd;"></span> 生效</button>\n' +
                                                '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_unable_btn" style="color:#dd514c;"><span class="am-icon-trash-o" style="color:#dd514c;"></span> 失效</button>\n' +
                                                '</div>\n' +
                                                '</div>\n' +
                                                "</td></tr>";
                                        });
                                        $('#departInfoList').html(departHtml);
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
            Modal.alert("部门信息列表查询AJAX请求失败！");
        }
    });
}

/**
 * 新增部门
 */
function addDepart(paramJson) {
    $.ajax({
        type: "POST",
        url: addDepartUrl,
        contentType: "application/x-www-form-urlencoded",
        data: paramJson,
        dataType: "json",
        success: function (data) {
            if (data.respCode == '0000') {
                Modal.alert("新增部门成功", function () {
                    $('.staff_detail_input').val('');
                    window.location.reload();
                });
            } else {
                Modal.alert(data.respDesc);
            }
        },
        error: function () {
            Modal.alert("新增部门AJAX请求失败！");
        }
    });
}