$().ready(function(){
    var operNo = $.session.get('operNo');
    $('#searchBtn').unbind().bind('click',function(){
        var start_month = $.trim($('#start_month').val());
        var end_month = $.trim($('#end_month').val());
        if(!INPUT_UTIL.isNull(start_month) && !INPUT_UTIL.isNull(end_month)){
            if(new Date(start_month)>new Date(end_month)){
                Modal.alert("起始时间不能大于结束时间！");
                return;
            }
        }
        $.ajax({
            type: "POST",
            url: getSalaryInfoUrl,
            contentType:"application/x-www-form-urlencoded",
            data: {
                operNo:operNo,
                startMonth:start_month,
                endMonth:end_month,
                pageNo:1,
                pageSize:10
            },
            dataType: "json",
            success: function(data){
                if(data.respCode=='0000'){
                    if(!INPUT_UTIL.isNull(data.staffList)){
                        $('#page').empty();
                        $('#total').text(data.total);
                        if(data.total==0){
                            return;
                        }
                        var salaryHtml = "";
                        $.each(data.staffList,function(i,item){
                            salaryHtml += "<tr><td>"+item.yearMonth+"</td><td>"+item.monthMoney+"</td><td>"+item.salaryDesc+"</td></tr>"
                        });
                        $('#salaryInfoList').html(salaryHtml);
                        // 分页判断 余数为0时 页数不需要加一
                        var pageTotal;
                        if(parseInt(data.total)%10==0){ // 10条一页
                            pageTotal = parseInt(parseInt(data.total)/10);
                        }else{
                            pageTotal = parseInt(parseInt(data.total)/10)+1;
                        }
                        $("#page").paging({
                            pageNo:1,
                            totalPage: pageTotal,
                            totalSize: data.total,
                            callback: function(num) {
                                var jsonParam = {
                                    pageNo:num,
                                    pageSize:10,
                                    operNo:operNo,
                                    startMonth:start_month,
                                    endMonth:end_month
                                }
                                $.ajax({
                                    type: "POST",
                                    url: getSalaryInfoUrl,
                                    contentType:"application/x-www-form-urlencoded",
                                    data:jsonParam,
                                    dataType: "json",
                                    success: function(data){
                                        if(data.respCode=='0000'){
                                            var salaryHtml = "";
                                            $.each(data.staffList,function(i,item){
                                                salaryHtml += "<tr><td>"+item.yearMonth+"</td><td>"+item.monthMoney+"</td><td>"+item.salaryDesc+"</td></tr>"
                                            });
                                            $('#salaryInfoList').html(salaryHtml);
                                        }
                                    }
                                });
                            }
                        })
                    }else{
                        $('#salaryInfoList').html("");
                        $('#total').text("0");
                    }
                }else{
                    Modal.alert(data.respDesc);
                }
            },
            error:function(){
                Modal.alert("薪资查询AJAX请求失败！");
            }
        });
    });
});