function update_username() {
    let old_password = document.getElementById("lp").value;
    let username = document.getElementById("ll").value;
    let data = {
        "oldPassword": old_password,
        "username": username
    };
    let json = JSON.stringify(data);

    let code = http_post("/update_user", json);
    alert(code[0]);
    window.location.href = "/logout";
}

function update_password() {
    let old_password = document.getElementById("pop").value;
    let new_password = document.getElementById("pnp").value;
    let data = {
        "oldPassword": old_password,
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