<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Восстановление пароля</title>
    <script src="recover_password.js"></script>
    <script>
        function send_login() {
            let login = document.getElementById("login").value;
            window.location.href = window.location.href + "?login=" + login;
        }
    </script>
</head>
<body>

<#if message??>
    <div>${message}</div>
<#else>

    <div>
        <label for="login">Введите логин</label>
        <input id="login" type="text">
    </div>
    <div>
        <button onclick="send_login()">Начать восстановление пароля</button>
    </div>
</#if>


</body>
</html>