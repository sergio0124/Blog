<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
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
<script src="js/register.js"></script>

</script>

</html>