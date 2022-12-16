<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Сохранение администратора</title>
    <script src="create.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<div>Добавление администратора</div>

<#if message??>
    <div>${message}</div>
</#if>

<#if admin??>
    <div id="admin_id" style="display: none">${admin.id}</div>
</#if>

<div><label> Имя пользователя : <input type="text" id="username"
        <#if admin??>
            value = "${admin.username}"
        </#if>
        /> </label></div>

<div><label> Пароль: <input type="password" id="password"/> </label></div>

<div><input type="button" onclick="create()" value="Сохранить"/></div>

</body>
</html>