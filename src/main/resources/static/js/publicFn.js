/**
 * 查询员工数据
 */
function getStaff(){
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