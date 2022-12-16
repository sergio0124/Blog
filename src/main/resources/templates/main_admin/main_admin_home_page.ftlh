<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Главная страница пользователя</title>
    <script src="/main_admin/search.js"></script>
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
    <label for="search">Поиск: </label><input type="text" id="search"
            <#if search??>
        value="${search}"
            </#if>>
    <button onclick="search()">Поиск</button>
</div>


<div>
    <button onclick="window.location.href='/main_admin/save_admin'">Зарегестрировать администратора</button>
</div>


<div>
    <div>Админы</div>

    <#if admins??>
        <#list admins as admin>
            <div>

                <div>
                    <div>Имя админа</div>
                    <div>${admin.username}</div>
                    <div>Дата регистрации</div>
                    <div>${admin.registrationDate}</div>
                    <button onclick=
                            "window.location.href='/main_admin/save_admin?userId=${admin.id}'"
                    >Изменить
                    </button>
                    <button onclick="window.location.href='/main_admin/delete_admin?userId=${admin.id}'">Удалить
                    </button>
                </div>

            </div>
        </#list>
    </#if>

</div>

<div>
    <#if page!=0>
        <button onclick="increase_page(-1)">Назад</button>
    </#if>
    <button id="page_num">${page}</button>
    <#if admins?size = 5>
        <button onclick="increase_page(1)">Далее</button>
    </#if>
</div>


</body>
</html>