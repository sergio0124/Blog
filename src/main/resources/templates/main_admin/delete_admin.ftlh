<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Удаление пользователя</title>
    <script src="/main_admin/delete.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>


<div style="margin-bottom: 20px">
    <img onclick="window.location.href = '/'" src="../static/image/blog.png" alt="">
    <label>Пользователь ${user.username}</label>
    <button onclick="document.location.href = '/info'">Информация об аккаунте</button>
    <button onclick="document.location.href = '/logout'">Выйти из аккаунта</button>
</div>
<hr/>


<div>

    <div>Удаление пользователя:</div>
    <div>Имя админа</div>
    <div>${admin.username}</div>
    <div>Дата регистрации</div>
    <div>${admin.registrationDate}</div>

    <div>Уверены, что хотете удалить?</div>

    <button onclick="delete_admin()">Удалить</button>
    <button onclick="window.history.back()">Отмена</button>

</div>


</body>
</html>