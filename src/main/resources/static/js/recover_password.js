function send_data() {
    let pass = document.getElementById("pass").value;
    let repass = document.getElementById("repass").value;
    if (pass != repass) {
        document.getElementById("massage").textContent = "Пароли не совпадают";

    } else {
        let data = {
            "password": pass
        }
        http_post(window.location.toString(), JSON.stringify(data));
    }

}

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}


function send_login() {
    let login = document.getElementById("login").value;
    window.location.href = window.location.href + "?login=" + login;
}