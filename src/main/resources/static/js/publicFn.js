/**
 * 查询员工数据
 */
function getStaff() {
    var staffList;
    $.ajax({
        type: "POST",
        url: getStaticInfoUrl,
        async: false,
        contentType: "application/x-www-form-urlencoded",
        data: {
            entityName: "Staff"
        },
        dataType: "json",
        success: function (data) {
            if (data.respCode == '0000') {
                if (!INPUT_UTIL.isNull(data.args)) {
                    staffList = data.args;
                }
            } else {
                Modal.alert(data.respDesc);
            }
        },
        error: function () {
            Modal.alert("静态数据查询AJAX请求失败！");
        }
    });
    return staffList;
}

/**
 * 时间戳转时间格式
 * @param timestamp
 * @returns {*}
 */
function timestampToTime(timestamp) {
    if(timestamp.toString().length==10){
        var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    }else if(timestamp.toString().length==13){
        var date = new Date(timestamp);
    }else{
        return timestamp;
    }
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()) + ':';
    s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
    return Y + M + D + h + m + s;
}