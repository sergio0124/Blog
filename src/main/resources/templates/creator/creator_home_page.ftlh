<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Главная страница редактора</title>
    <script type="text/javascript" src="static/alert.js"></script>
</head>
<body>

<div>
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
        <button></button>
        <button>${page}</button>
        <button></button>
    </div>

</div>


</body>
</html>