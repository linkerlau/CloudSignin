var state= {
    title: "title",
    url: "#"
};

$(document).ready(function () {
    window.history.pushState(state, "title", "#");
    window.addEventListener("popstate", function (ev) {
        history.pushState(state, "title", "#");
    }, false);
});