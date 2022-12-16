<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Восстановление пароля</title>
    <script src="/recover_password.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script>
        function send_data() {
            let pass = document.getElementById("pass").value;
            let repass = document.getElementById("repass").value;
            if (pass != repass) {
                document.getElementById("massage").textContent = "Пароли не совпадают";
            } else {
                let data = {
                    "password": pass
                }
                let response = http_post(window.location.href, JSON.stringify(data));
                if (response[1] == 200) {
                    window.location.href = "/login";
                } else {
                    document.getElementById("massage").textContent = response[0];
                }
            }

        }

        function http_post(theUrl, inputData) {
            let xmlHttp = new XMLHttpRequest();
            xmlHttp.open("POST", theUrl, false);
            xmlHttp.setRequestHeader("Content-Type", "application/json");
            xmlHttp.send(inputData);
            return [xmlHttp.responseText, xmlHttp.status];
        }
    </script>
</head>
<body>

<#if message??>
    <div>${message}</div>
<#else>
    <div><label> Новый пароль: <input type="password" name="password" id="pass"/> </label></div>
    <div><label> Повторите пароль: <input type="password" name="password" id="repass"/> </label></div>
    <div>
        <button onclick="send_data()">
            Изменить пароль
        </button>
    </div>

    <div id="massage"></div>
</#if>


</body>
</html>