<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<#if error??>
    <p>
        ${error}
    </p>
</#if>


<div><label> Имя пользователя : <input type="text" id="registration_username"/> </label></div>

<div><label> Пароль: <input type="password" id="registration_password"/> </label></div>

<div><label> Почта: <input type="email" id="registration_mail"/> </label></div>

<select id="registration_option">
    <option value="CREATOR">Создавать контент</option>
    <option value="USER">Читать контент</option>
</select>

<div><input type="button" id="registration_submit" value="Регистрация"/></div>

<div id="response_message"></div>


</body>
<script src="register.js"></script>

</script>

</html>