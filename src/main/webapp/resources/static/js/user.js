
function SetData(res, who, when, type, enableBorderColor, enableBackgroundColor) {
    var labels = [];
    var datas = [];
    var borderColor = [];
    var backgroundColor = [];
    for (var i = 0; i < res.length; i++) {
        labels.push(res[i].label);
        datas.push(res[i].data);
    }

    if (enableBorderColor) {
        borderColor = ['#D3D3D3', '#D3D3D3'];
    }
    if (enableBackgroundColor) {
        backgroundColor = ['#808080', '#F7F7F7'];
    }

    var ctxFirst = document.getElementById(who).getContext("2d");
    var dataFirst = {
        datasets : [{
            label : when,
            data : datas,
            borderColor : borderColor,
            backgroundColor : backgroundColor
        }],
        labels : labels
    };
    new Chart(ctxFirst, {
        type : type,
        data : dataFirst
    });
}

$(document).ready(function () {
    $.ajax(
        {
            type:"get",
            url:"/query/user/fixed-percentage?date=now",
            dataType:"json",
            success:function (result) {
                $("#first_for_user").remove();
                $("#panelChart").append('<canvas id="first_for_user"></canvas>');
                SetData(result, "first_for_user", '当日', 'pie', true, true);
            },
            error:function () {
                alert("error");
            }
        }
    );

    $.ajax(
        {
            type:"get",
            url:"/query/user/fixed-situation",
            dataType:"json",
            success:function (result) {
                SetData(result, "second_for_user", '本周', 'horizontalBar', false, false);
            },
            error:function () {
                alert("error");
            }
        }
    );

//点击事件
    $("#query_now").click(function () {
        $.ajax(
            {
                type:"get",
                url:"/query/user/fixed-percentage?date=now",
                dataType:"json",
                success:function (result) {
                    $("#first_for_user").remove();
                    $("#panelChart").append('<canvas id="first_for_user" width="400px" height="200px"></canvas>');
                    SetData(result, "first_for_user", '本周', 'pie', true, true);
                },
                error:function () {
                    alert("error");
                }
            }
        );
    });

    $("#query_yesterday").click(function () {
        $.ajax(
            {
                type:"get",
                url:"query/user/fixed-percentage?date=yesterday",
                dataType:"json",
                success:function (result) {
                    $("#first_for_user").remove();
                    $("#panelChart").append('<canvas id="first_for_user" width="400px" height="200px"></canvas>');
                    SetData(result, "first_for_user", '昨日', 'pie', true, true);
                },
                error:function () {
                    alert("error");
                }
            }
        );
    });

    $("#query_week").click(function () {
        $.ajax(
            {
                type:"get",
                url:"query/user/fixed-percentage?date=week",
                dataType:"json",
                success:function (result) {
                    $("#first_for_user").remove();
                    $("#panelChart").append('<canvas id="first_for_user" width="400px" height="200px"></canvas>');
                    SetData(result, "first_for_user", '本周', 'pie', true, true);
                },
                error:function () {
                    alert("error");
                }
            }
        );
    });



    // //签到业务
    // $("#signin_button").click(function () {
    //     $(".content_first_form").hidden = true;
    //     $(".signin_info").hidden = false;
    //
    //     $.ajax(
    //         {
    //             type:"post",
    //             url:"/user/signin",
    //             dataType:"json",
    //             success:function (result) {
    //                 alert(result);
    //                 if (result) {
    //                     $(".signin_info > h3").text("签到成功！");
    //                     $(".signin_info a").href("baidu.com").text("LinkerLau");
    //
    //                 } else {
    //                     $(".signin_info > h3").text("签到失败！");
    //                 }
    //             }
    //         }
    //     );
    // });

});








// //图表
// var ctxFirst = document.getElementById("first_for_user").getContext("2d");
// var ctxSecond = document.getElementById("second_for_user").getContext("2d");
//
// var dataFirst = {
//     datasets : [{
//         data : [70, 30],
//         borderColor : ['#adc', '#bcd'],
//         backgroundColor : ['#808080', '#F7F7F7']
//     }],
//     labels : [
//         '已签',
//         '未签'
//     ]
// };
// var dataSecond = {
//     labels: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
//     datasets: [
//         {
//             label: '上午',
//             fillColor: "rgba(220,220,220,0.5)",
//             strokeColor: "rgba(220,220,220,0.8)",
//             highlightFill: "rgba(220,220,220,0.75)",
//             highlightStroke: "rgba(220,220,220,1)",
//             data: [1, 0, 1, 1]
//         },
//         {
//             label: '下午',
//             fillColor: "rgba(151,187,205,0.5)",
//             strokeColor: "rgba(151,187,205,0.8)",
//             highlightFill: "rgba(151,187,205,0.75)",
//             highlightStroke: "rgba(151,187,205,1)",
//             data: [1, 0, 1, 0]
//         }
//     ]
// };
//
// var optionsSecond = {
//     responsive: true,
//     scales: {
//         xAxes: [{
//             ticks:{
//                 stepSize : 1
//             },
//             stacked: true,
//             gridLines: {
//                 lineWidth: 0,
//                 color: "rgba(255,255,255,0)"
//             }
//         }],
//         yAxes: [{
//             barPercentage : 0.4,
//             stacked: true,
//             ticks: {
//                 min: 0,
//                 stepSize: 1
//             }
//         }]
//     }
// };
//
//
// new Chart(ctxFirst, {
//     type : 'pie',
//     data : dataFirst
// });
// new Chart(ctxSecond, {
//     type : 'horizontalBar',
//     data : dataSecond,
//     options : optionsSecond
// });