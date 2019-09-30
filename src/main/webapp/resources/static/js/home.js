
function SetData(res, who, when, type, enableBorderColor, enableBackgroundColor) {
    const labels = [];
    const datas = [];
    let borderColor = [];
    let backgroundColor = [];
    for (let i = 0; i < res.length; i++) {
        labels.push(res[i].label);
        datas.push(res[i].data);
    }

    if (enableBorderColor) {
        borderColor = ['#D3D3D3', '#D3D3D3'];
    }
    if (enableBackgroundColor) {
        backgroundColor = ['#808080', '#F7F7F7'];
    }

    const ctxFirst = document.getElementById(who).getContext("2d");
    const dataFirst = {
        datasets: [{
            label: when,
            data: datas,
            borderColor: borderColor,
            backgroundColor: backgroundColor
        }],
        labels: labels
    };
    new Chart(ctxFirst, {
        type : type,
        data : dataFirst
    });
}

//图表
$(document).ready(function(){

    //直接加载当日的数据
    $.ajax(
        {
            type:"get",
            url:"/query/home/fixed-percentage?date=now",
            dataType:"json",
            success:function (result) {
                $("#first").remove();
                $("#panelChart").append('<canvas id="first" ></canvas>');
                SetData(result, "first", '当日', 'pie', true, true);
            },
            error:function () {
                alert("error");
            }
        }
    );
    $.ajax(
        {
            type:"get",
            url:"/query/home/fixed-situation?date=now",
            dataType:"json",
            success:function (result) {
                $("#second").remove();
                $("#content_chooses").append('<canvas id=second height="400px" width="400px"></canvas>');
                SetData(result, "second", '当日', 'horizontalBar', false, false);
            },
            error:function() {
                alert("error");
            }
        }
    );

    //绑定点击事件

    //左边图表
    $("#query_now").click(function () {
        $.ajax(
            {
                type:"get",
                url:"/query/home/fixed-percentage?date=now",
                dataType:"json",
                success:function (result) {
                    $("#first").remove();
                    $("#panelChart").append('<canvas id="first" ></canvas>');
                    SetData(result, "first", '当日', 'pie', true, true);
                },
                error:function () {
                    alert("error");
                }
            }
        );}
    );
    $("#query_yesterday").click(function () {
        $.ajax(
            {
                type: "get",
                url: "/query/home/fixed-percentage?date=yesterday",
                dataType: "json",
                success: function (result) {
                    $("#first").remove();
                    $("#panelChart").append('<canvas id="first" ></canvas>');
                    SetData(result, "first", '昨日', 'pie', true, true);
                },
                error: function () {
                    alert("error");
                }
            }
        );}
    );
    $("#query_week").click(function () {
        $.ajax(
            {
                type:"get",
                url:"/query/home/fixed-percentage?date=week",
                dataType:"json",
                success:function (result) {
                    $("#first").remove();
                    $("#panelChart").append('<canvas id="first" ></canvas>');
                    SetData(result, "first", '本周', 'pie', true, true);
                },
                error:function () {
                    alert("error");
                }
            }
        );
    });

    //右边图表
    $("#situation_now").click(function () {
        $.ajax(
            {
                type:"get",
                url:"/query/home/fixed-situation?date=now",
                dataType:"json",
                success:function (result) {
                    $("#second").remove();
                    $("#content_chooses").append('<canvas id="second" width="400px" height="400px"></canvas>');
                    SetData(result, "second", '当日', 'horizontalBar', false, false);
                },
                error:function() {
                    alert("error");
                }
            }
        );}
    );
    $("#situation_yesterday").click(function () {
        $.ajax(
            {
                type:"get",
                url:"/query/home/fixed-situation?date=yesterday",
                dataType:"json",
                success:function (result) {
                    $("#second").remove();
                    $("#content_chooses").append('<canvas id="second" width="400px" height="400px"></canvas>');
                    SetData(result, "second", '昨日', 'horizontalBar', false, false);
                },
                error:function() {
                    alert("error");
                }
            }
        );}
    )
});

// function SpecialJson(data) {
//     alert(data);
//     var temp = data + "";
//     temp = temp.replace(/"([^"]*)"/g, "'$1'");
//     temp = temp.replace(/'([^']*)'(:)/g ,"$1$2");
//     return temp;
// }
// var datas = JSON.stringify(chartData);
// var res = SpecialJson(datas);
// alert(res);



//
// function changeSigninStatus() {
//     var url = "/logined/signin";
//     var data = "";
//     myAjax("POST", url, true, data, "form",
//         function () {
//             alert("sign in successfully");
//         },
//         function (state) {
//             alert("sign in error");
//             alert(state)
//         }
//     );
// }
//
// // 无刷新请求：ajax，配合js局部刷新页面数据，异步请求
// function myAjax(method,url,async,data,type,onSuccess,onError) {
//     // 1.判断浏览器是否支持XMLHttpRequest
//     var http = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
//     // 2.创建连接
//     http.open(method,url,async);
//
//     // 5.判断数据类型：定制请求头部
//     if (type === "form"){
//         http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//     }else if (type === "json"){
//         http.setRequestHeader("Content-Type","application/json");
//     }
//
//     // 3.发送
//     http.send(data); // post
//     // 4.回调一个函数
//     http.onreadystatechange = function () {
//         // 5.判断是否完成 0 准备 1开始 2发送 3完毕 4完成
//         if (http.readyState === 4){
//             // 6.判断是否成功
//             if (http.status === 200){
//                 // http.responseText: 接受响应信息
//                 onSuccess(http.responseText) // 自定义回调
//             }else {
//                 onError(http.status)
//             }
//         }
//     }
//
// }
//
// function Person(name) {
//     this.name = name;
//     this.sayName = function () {
//
//     }
// }
//
// var f = new Person("fangfang");
// f.sex = "male";
// f.getSex = function () {
//     alert(f.sex);
// };
//
//
// f.getSex();