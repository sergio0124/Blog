<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Страница пользователя</title>
    <script src="subscribe.js"></script>
    <script src="read_post.js"></script>
    <script src="pages.js"></script>
</head>
<body>

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