function update_username() {
    if (!confirm("В результете действия будет изменен логин, а для использования ресурса потребуется аутентификация." +
        "Уверены ли вы в своём выборе?")) {
        return;
    }
    let username = document.getElementById("ll").value;
    let data = {
        "username": username
    };
    let json = JSON.stringify(data);

    let code = http_post("/update_user", json);
    alert(code[0]);
    window.location.href = "/logout";
}

function update_password() {
    if (!confirm("В результете действия будет изменен пароль, а для использования ресурса потребуется аутентификация." +
        "Уверены ли вы в своём выборе?")) {
        return;
    }
    let new_password = document.getElementById("pnp").value;
    let data = {
        "password": new_password
    };
    let json = JSON.stringify(data);

    let code = http_post("/update_user", json);
    alert(code[0]);
    window.location.href = "/logout";
}

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}