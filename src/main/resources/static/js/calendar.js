window.onload = function () {
    Rili()
}
var operNo = $.session.get('operNo');

//日历
function Rili() {
    var attendanceArr = [];
    $.ajax({
        type: "POST",
        url: getAttendanceListUrl,
        contentType:"application/x-www-form-urlencoded",
        async:false,
        dataType: "json",
        data:{
          operNo:operNo
        },
        success: function(data){
            if(data.respCode=='0000'){
                if(!INPUT_UTIL.isNull(data.args)){
                    $.each(data.args,function(i,item){
                        var obj = {datetime:new Date(item.year,item.month,item.day)};
                        attendanceArr.push(obj);
                    });
                }
            }
        },
        error:function(){
            Modal.alert("查询考勤记录AJAX请求失败！");
        }
    });
    $('#calendar').eCalendar({
        weekDays: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
        months: ['01', '02', '03', '04', '05', '06',
            '07', '08', '09', '10', '11', '12'
        ],
        events: attendanceArr/*[
            {
                datetime: new Date(2019, 2, 3)
            }
        ]*/
    });
};

(function($) {

    var eCalendar = function(options, object) {
        // Initializing global variables
        var adDay = new Date().getDate();
        var adMonth = new Date().getMonth();
        var adYear = new Date().getFullYear();
        var dDay = adDay;
        var dMonth = adMonth;
        var dYear = adYear;
        var instance = object;

        var settings = $.extend({}, $.fn.eCalendar.defaults, options);

        function lpad(value, length, pad) {
            if(typeof pad == 'undefined') {
                pad = '0';
            }
            var p;
            for(var i = 0; i < length; i++) {
                p += pad;
            }
            return(p + value).slice(-length);
        }

        var mouseOver = function() {
            $(this).addClass('c-nav-btn-over');
        };
        var mouseLeave = function() {
            $(this).removeClass('c-nav-btn-over');
        };

        var mouseOverEvent = function() {
            $(".c-event-list").scrollTop(0);
            $(this).addClass('c-event-over');
            var d = $(this).attr('data-event-day');
            $('div.c-event-item[data-event-day="' + d + '"]').addClass('c-event-over1').host;
            // $(".c-event-list").scrollTop($('div.c-event-item[data-event-day="' + d + '"]').position().top - $('div.c-event-item[data-event-day="' + d + '"]').height())
        };

        var mouseLeaveEvent = function() {
            $(this).removeClass('c-event-over')
            var d = $(this).attr('data-event-day');
            $('div.c-event-item[data-event-day="' + d + '"]').removeClass('c-event-over1');

        };
        var mouseOverItem = function() {
            $(this).addClass('c-event-over1');
            var d = $(this).attr('data-event-day');
            $('div.c-event[data-event-day="' + d + '"]').addClass('c-event-over');
        };
        var mouseLeaveItem = function() {
            $(this).removeClass('c-event-over1')
            var d = $(this).attr('data-event-day');
            $('div.c-event[data-event-day="' + d + '"]').removeClass('c-event-over');
        };

        var clickitem = function() {
            var d = $(this).attr('data-event-day');
            $('div.c-event-item[data-event-day="' + d + '"]').siblings().removeAttr("style")
            $('div.c-event-item[data-event-day="' + d + '"]').css({
                "font-weight": "700",
                "color": "#fff",
                "background": "-webkit-linear-gradient(left, #01c2e6 , #1160ff)",
                "background": " -o-linear-gradient(right, #01c2e6 , #1160ff)",
                "background": "-moz-linear-gradient(right, #01c2e6 , #1160ff)",
                "background": "linear-gradient(to right, #01c2e6 , #1160ff)"
            }).host;
            $('div.c-event-item[data-event-day="' + d + '"]').siblings().children().removeAttr("style")
            $('div.c-event-item[data-event-day="' + d + '"]').children().css("color", "white")
            $('div.c-event[data-event-day="' + d + '"]').siblings().removeAttr("style")
            $('div.c-event[data-event-day="' + d + '"]').css({
                "box-shadow": " 0 0 8px #cccccc",
                "font-weight": "700",
                "color": "#fff",
                "background": "-webkit-linear-gradient(left, #01c2e6 , #1160ff)",
                "background": " -o-linear-gradient(right, #01c2e6 , #1160ff)",
                "background": "-moz-linear-gradient(right, #01c2e6 , #1160ff)",
                "background": "linear-gradient(to right, #01c2e6 , #1160ff)"
            }).host;

        }

        var nextMonth = function() {
            if(dMonth < 11) {
                dMonth++;
            } else {
                dMonth = 0;
                dYear++;
            }
            print();
            if($(".c-day").is(".c-today")) {
                $(".c-month-top").html(dYear + "-" + settings.months[dMonth]);
                $(".c-month-center").html($(".c-today").text());
                //	            $(".c-month-bottom").html("有课");
            } else {
                $(".c-month-top").html(dYear);
                $(".c-month-center").html(settings.months[dMonth]);
                $(".c-month-bottom").html("");
            }

        };
        var previousMonth = function() {
            if(dMonth > 0) {
                dMonth--;
            } else {
                dMonth = 11;
                dYear--;
            }
            print();
            if($(".c-day").is(".c-today")) {
                $(".c-month-top").html(dYear + "-" + settings.months[dMonth]);
                $(".c-month-center").html($(".c-today").text());
                //	            $(".c-month-bottom").html("有课");
            } else {
                $(".c-month-top").html(dYear);
                $(".c-month-center").html(settings.months[dMonth]);
                $(".c-month-bottom").html("");
            }
        };

        function loadEvents() {
            if(typeof settings.url != 'undefined' && settings.url != '') {
                $.ajax({
                    url: settings.url,
                    async: false,
                    success: function(result) {
                        settings.events = result;
                    }
                });
            }
        }
        function mGetDate(year, month){
            var d = new Date(year, month, 0);
            return d.getDate();
        }

        function print() {
            loadEvents();
            var dWeekDayOfMonthStart = new Date(dYear, dMonth, 1).getDay();
            var dLastDayOfMonth = new Date(dYear, dMonth + 1, 0).getDate();
            var dLastDayOfPreviousMonth = new Date(dYear, dMonth + 1, 0).getDate() - dWeekDayOfMonthStart + 1;

            var cBody = $('<div/>').addClass('c-grid');
            var cEventsBody = $('<div/>').addClass('c-event-body');
            var cEventsTop = $('<div/>').addClass('c-event-top clearfix');
            var cNext = $('<div/>').addClass('c-next c-grid-title c-pad-top');
            var cMonth = $('<div/>').addClass('c-month c-grid-title c-pad-top');
            var cPrevious = $('<div/>').addClass('c-previous c-grid-title c-pad-top');
            var tips = $('<div/>').addClass('tips');
            var tips_1 = $('<div/>').addClass("tips_1").text("考勤");
            var tips_2 = $('<div/>').addClass("tips_2").text("当前");

            tips.append(tips_1);
            tips.append(tips_2);

            cPrevious.html(settings.textArrows.previous);

            cNext.html(settings.textArrows.next);

            cPrevious.on('mouseover', mouseOver).on('mouseleave', mouseLeave).on('click', previousMonth);
            cNext.on('mouseover', mouseOver).on('mouseleave', mouseLeave).on('click', nextMonth);

            cEventsTop.append(cPrevious);
            cEventsTop.append(cMonth);
            cEventsTop.append(cNext);
            for(var i = 0; i < settings.weekDays.length; i++) {
                var cWeekDay = $('<div/>').addClass('c-week-day c-pad-top');
                cWeekDay.html(settings.weekDays[i]);
                cBody.append(cWeekDay);
            }
            var day = 1;
            var dayOfNextMonth = 1;

            for(var i = 0; i < 42; i++) {
                var cDay = $('<div/>');
                if(i < dWeekDayOfMonthStart) {
                    cDay.addClass('c-day-previous-month c-pad-top');
                    //                  cDay.html(dLastDayOfPreviousMonth++);
                } else if(day <= dLastDayOfMonth) {
                    cDay.addClass('c-day c-pad-top');
                    if(day == dDay && adMonth == dMonth && adYear == dYear) {
                        cDay.addClass('c-today');
                    }
                    for(var j = 0; j < settings.events.length; j++) {
                        var d = settings.events[j].datetime;

                        if(d.getDate() == day && (d.getMonth() - 1) == dMonth && d.getFullYear() == dYear) {
                            cDay.addClass('c-event').attr('data-event-day', d.getDate());
                            // 去除日历上蓝色图标的鼠标事件
                            // cDay.on('mouseover', mouseOverEvent).on('mouseleave', mouseLeaveEvent).on('click', clickitem);
                        }
                    }
                    cDay.html(day++);
                } else {
                    cDay.addClass('c-day-next-month c-pad-top');
                    //                  cDay.html(dayOfNextMonth++);
                }
                cBody.append(cDay);
            }
            var eventList = $('<div/>').addClass('c-event-list');
            for(var i = 0; i < settings.events.length; i++) {
                var d = settings.events[i].datetime;
                if((d.getMonth() - 1) == dMonth && d.getFullYear() == dYear) {
                    var date = lpad(d.getMonth(), 2) + '/' + lpad(d.getDate(), 2) + ' ' + lpad(d.getHours(), 2) + ':' + lpad(d.getMinutes(), 2);
                    var item = $('<div/>').addClass('c-event-item');
                    var a = $('<a/>').addClass('c-event-item-a').attr('href', settings.events[i].href);;
                    var title = $('<div/>').addClass('title').html(date + '  ' + settings.events[i].title);
                    var 活动描述 = $('<div/>').addClass('活动描述').html(settings.events[i].活动描述);
                    item.attr('data-event-day', d.getDate());
                    item.on('mouseover', mouseOverItem).on('mouseleave', mouseLeaveItem).on('click', clickitem);

                    item.append(a);
                    a.append(title).append(活动描述);
                    eventList.append(item);

                }
            }

            $(instance).addClass('calendar');

            cEventsBody.append(eventList);
            $(instance).html(tips);
            $(instance).prepend(cBody);
            $(instance).prepend(cEventsTop);

            $(".c-event-item").addClass("clearfix");

            var cMontop = $("<div/>").addClass("c-month-top");
            cMonth.append(cMontop);
            cMontop.html(dYear + "-" + settings.months[dMonth]);
            var cMoncenter = $("<div/>").addClass("c-month-center");
            cMonth.append(cMoncenter);
            cMoncenter.html($(".c-today").text());
            var cMonbottom = $("<div/>").addClass("c-month-bottom");
            cMonth.append(cMonbottom);
            if($(".c-today").is(".c-event")) {
                // cMonbottom.html("有课");
                cMonbottom.html(" ");
            } else {
                cMonbottom.html(" ");
            }

        }

        return print();
    }

    $.fn.eCalendar = function(oInit) {
        return this.each(function() {
            return eCalendar(oInit, $(this));
        });
    };

    // plugin defaults
    $.fn.eCalendar.defaults = {
        weekDays: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
        months: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
        textArrows: {
            previous: '',
            next: ''
        },
        eventTitle: '',
        url: ''
    };

}(jQuery));