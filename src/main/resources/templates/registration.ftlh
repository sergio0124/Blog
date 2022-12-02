<!DOCTYPE html>
<html>
<head>
    <title>Log in</title>
</head>
<body>

<#if error??>
    <p>
        ${error}
    </p>
</#if>


<div><label> Имя пользователя : <input type="text" id="registration_username"/> </label></div>

<div><label> Пароль: <input type="password" id="registration_password"/> </label></div>

<select id="registration_option">
    <option value="Creator">Создавать контент</option>
    <option value="User">Читать контент</option>
</select>

<div><input type="button" id="registration_submit" value="Регистрация"/></div>

<div id="response_message"></div>

</body>

<script language="JavaScript">
    submit = document.getElementById("registration_submit")
    username = document.getElementById("registration_username")
    password = document.getElementById("registration_password")
    select = document.getElementById("registration_option")
    message = document.getElementById("response_message")


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

        let data = {
            "username": name,
            "password": pass,
            "roles": [type]
        }
        let json = JSON.stringify(data);

        let response = http_post("/registration", json)
        let text = response[0]
        let status = response[1]

        if (status == 200) {
            data = {
                "username": name,
                "password": pass,
            }
            json = JSON.stringify(data);
            http_post("/authenticateUser", json);
            document.location.href = "/hello"
        }

        message.innerHTML = text;

    });
</script>

</html>