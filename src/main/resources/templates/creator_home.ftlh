<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Главная страница редактора</title>
</head>
<body>

<div>
    <button onclick="document.location.href = '/user/info'">Информация об аккаунте</button>
</div>

<div>
    <button onclick="document.location.href = '/creator/new'">Добавить пост</button>
</div>

<div>
    <div>Посты</div>

    <#if posts??>
        <#list posts as post>
            <div onclick="go_create(${post.getId()})">

                <div>
                    <label>Название ${post.getTitle()}</label>
                </div>

                <div>
                    <label>Дата: ${post.getDate()}</label>
                </div>

                <div>
                    <label>Описание: ${post.getDescription()}</label>
                </div>


            </div>
        </#list>
    </#if>

</div>

</body>


<script type="text/javascript" src="open_post_for_ref.js"></script>

</html>