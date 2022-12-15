function go_report(post_id) {
    window.location.href = "report_post/" + post_id;
}

function send_report() {
    let report = document.getElementById("report_type_select").value;
    let data = {
        "reportType": report
    }
    let response = http_post(document.location.href, JSON.stringify(data));
    if (response[1] == 200) {
        document.location.href = "/";
    } else {
        document.getElementById("message").innerHTML = response[0];
    }
}


function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.response, xmlHttp.status];
}