var operNo = '';
$().ready(function(){
    operNo = $.session.get('operNo');

    // 获取个人信息
    getStaffInfo();

    // 修改按钮
    var reg_phone = /^1\d{10}$/;
    $('#staffBtn').unbind().bind('click',function(){
        $(this).hide();
        $('#staffUpdateBtn').show();
        $('.canUpdate').removeAttr("disabled");
    });

    $('#staffUpdateBtn').unbind().bind('click',function(){
        Modal.confirm("确认更新资料",function () {
            var phone = $.trim($('#phone').val());
            var email = $.trim($('#email').val());
            var remark = $.trim($('#remark').val());
            if(!reg_phone.test(phone)){
                Modal.alert("","请输入正确的手机号码！");
                return;
            }
            if(remark.length>250){
                Modal.alert("","简介字数请勿超过250个！");
                return;
            }

            var jsonParam = {
                "email":email,
                "phone":phone,
                "remark":remark,
                "staffId":staffId
            }
            $.ajax({
                type: "POST",
                url: updatePersonStaffInfo,
                contentType:"application/x-www-form-urlencoded",
                //contentType: "application/json; charset=utf-8",
                data: {
                    jsonStr:JSON.stringify(jsonParam)
                },
                dataType: "json",
                success: function(data){
                    if(data.respCode=='0000'){
                        $('#staffUpdateBtn').hide();
                        $('#staffBtn').show();
                        Modal.alert("","更新个人资料成功!",function(){
                            $('.canUpdate').attr("disabled","disabled");
                            $('#staffInfo').trigger('click');
                        });
                    }else{
                        Modal.alert("",data.respDesc);
                    }
                }
            });
        });
    });
});

function getStaffInfo(){
    $.ajax({
        type: "POST",
        url: getStaffInfoUrl,
        contentType:"application/x-www-form-urlencoded",
        //contentType: "application/json; charset=utf-8",
        data: {
            operNo:operNo
        },
        dataType: "json",
        success: function(data){
            if(data.respCode=='0000'){
                var staffInfo = data.staffInfo;
                $('#oper_no').val(staffInfo.operNo);
                $('#user_name').val(staffInfo.staffName);
                $('#job').val(staffInfo.job);
                $('#birthday').val(staffInfo.birthday);
                $('#sex').val(staffInfo.gender);
                $('#native_place').val(staffInfo.nativePlace);
                $('#education').val(staffInfo.education);
                $('#major').val(staffInfo.major);
                $('#induction_time').val(staffInfo.inductionTime);
                $('#email').val(staffInfo.email);
                $('#phone').val(staffInfo.telNumber);
                $('#remark').val(staffInfo.remark);
                staffId = staffInfo.staffId;
            }else{
                Modal.alert("",data.respDesc);
            }
        }
    });
}