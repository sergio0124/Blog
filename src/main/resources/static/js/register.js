submit = document.getElementById("registration_submit");
username = document.getElementById("registration_username");
password = document.getElementById("registration_password");
select = document.getElementById("registration_option");
email = document.getElementById("registration_mail");
message = document.getElementById("response_message");


function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}


submit.addEventListener('click', function () {

    let type = select.value;
    let pass = password.value;
    let name = username.value;
    let mail = email.value;

    let data = {
        "username": name,
        "password": pass,
        "mail": mail,
        "roles": [type]
    }
    let json = JSON.stringify(data);

    let response = http_post("/registration", json)
    let text = response[0]

    message.innerHTML = text;
});