<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Страница пользователя</title>
    <script src="/user/subscribe.js"></script>
    <script src="/user/read_post.js"></script>
    <script src="/pages.js"></script>
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
    <div>Пользователь ${user.username}</div>
</div>

<div>
    <div>Дата регистрации ${user.registrationDate}</div>
</div>


<button onclick="subscribe(this, ${user.id})">
    <#if subscribed=true>
        Отписаться от пользователя
    <#else>
        Подписаться на пользователя
    </#if>
</button>


<div>

    <div>Посты</div>

    <#if posts??>
        <#list posts as post>
            <div>

                <div>
                    <label>Название ${post.title}</label>
                </div>

                <div>
                    <label>Дата: ${post.date}</label>
                </div>

                <div>
                    <label>Описание: ${post.description}</label>
                </div>

                <button onclick="read_post(${post.id})">
                    Читать пост
                </button>

            </div>
        </#list>
    </#if>

    <div>
        <#if page!=0>
            <button onclick="increase_page(-1)">Назад</button>
        </#if>
        <button id="page_num">${page}</button>
        <#if posts?size = 5>
            <button onclick="increase_page(1)">Далее</button>
        </#if>
    </div>

</div>


</body>
</html>