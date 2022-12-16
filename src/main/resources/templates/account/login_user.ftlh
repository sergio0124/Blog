<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Log in</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<#if RequestParameters.error??>
    Неверный логин или пароль
<#elseif RequestParameters.logout??>
    Вы вышли из своего аккаунта
<#elseif status??>
    <p>
        ${status}
    </p>
<#elseif message??>
    <p>
        ${message}
    </p>
</#if>

</div>
<p>Регистрация <a href="/registration"> здесь</a></p>
<p>Восстановление пароля <a href="/activate/recover_password"> здесь</a></p>

<form action="/authenticateUser" method="post">
    <div><label> Логин : <input type="text" name="username"/> </label></div>
    <div><label> Пароль : <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Войти"/></div>
</form>
</body>
</html>