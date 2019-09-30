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
    $("#body_content_chart").remove();
    $("#body_content").append('<canvas id="body_content_chart" height="400px" width="400px"></canvas>');

    var res = eval($("#res").val());
    SetData(res, "body_content_chart", '指定日期', 'horizontalBar', false, false);
});