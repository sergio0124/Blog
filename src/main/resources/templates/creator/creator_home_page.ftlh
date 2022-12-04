<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Главная страница редактора</title>
    <script src="open_user_info.js"></script>
    <script src="pages.js"></script>
</head>
<body>

<div>
    <div>Пользователь ${user.username}</div>
    <button onclick="document.location.href = '/user/info'">Информация об аккаунте</button>
</div>

<div>
    <button onclick="document.location.href = '/creator/work_on_post'">Добавить пост</button>
</div>


<div>

    <div>Посты</div>

    <#if posts??>
        <#list posts as post>
            <div onclick="go_create(${post.id})">

                <div>
                    <label>Название ${post.title}</label>
                </div>

                <div>
                    <label>Дата: ${post.date}</label>
                </div>

                <div>
                    <label>Описание: ${post.description}</label>
                </div>

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